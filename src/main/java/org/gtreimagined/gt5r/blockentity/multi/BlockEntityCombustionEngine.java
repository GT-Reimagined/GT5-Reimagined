package org.gtreimagined.gt5r.blockentity.multi;

import com.mojang.blaze3d.vertex.PoseStack;
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityMultiMachine;
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler;
import org.gtreimagined.gtlib.gui.GuiInstance;
import org.gtreimagined.gtlib.gui.IGuiElement;
import org.gtreimagined.gtlib.gui.widget.InfoRenderWidget;
import org.gtreimagined.gtlib.gui.widget.WidgetSupplier;
import org.gtreimagined.gtlib.integration.xei.renderer.IInfoRenderer;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.recipe.ingredient.FluidIngredient;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

import java.util.Collections;
import java.util.List;

import static org.gtreimagined.gtlib.gui.ICanSyncData.SyncDirection.SERVER_TO_CLIENT;
import static org.gtreimagined.gt5r.data.Materials.Lubricant;
import static org.gtreimagined.gt5r.data.Materials.Oxygen;

public class BlockEntityCombustionEngine extends BlockEntityMultiMachine<BlockEntityCombustionEngine> {

    long lastEu = 0;
    long lastConsumption = 0;
    int startup = 0;

    public BlockEntityCombustionEngine(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.recipeHandler.set(() -> new MachineRecipeHandler<>(this){

            int lubeTicker = 0;

            @Override
            protected boolean consumeGeneratorResources(boolean simulate) {
                boolean boostEU = fluidHandler.map(f -> f.drainInput(Oxygen.getGas(2), FluidAction.SIMULATE).getAmount() == 2).orElse(false);
                int fuelConsumption = (int) (boostEU ? (4096 / activeRecipe.getPower()) : (2048 / activeRecipe.getPower()));
                int lubeConsume = boostEU ? 2 : 1;
                if ((lubeTicker == 72 || simulate) &&!fluidHandler.map(f -> f.drainInput(Lubricant.getLiquid(lubeConsume), FluidAction.SIMULATE).getAmount() == lubeConsume).orElse(false)) {
                    if (!simulate && startup > 0) startup = 0;
                    return false;
                }
                int toConsume = fuelConsumption * activeRecipe.getInputFluids().get(0).getAmount();
                if (fluidHandler.map(f -> !f.consumeAndReturnInputs(List.of(activeRecipe.getInputFluids().get(0).copy(toConsume)), simulate).isEmpty()).orElse(false)){
                    if (!simulate) {
                        if (startup > 100) startup = 100;
                        fluidHandler.ifPresent(f -> {
                            f.drainInput(Oxygen.getGas(2), FluidAction.EXECUTE);
                            if (lubeTicker == 72) f.drainInput(Lubricant.getLiquid(lubeConsume), FluidAction.EXECUTE);
                        });
                        lastConsumption = toConsume;
                        long euPerTick = boostEU ? 6144 : 2048;
                        lastEu = startup < 20 ? 0 : (long)(euPerTick * ((float)startup / 100));
                        energyHandler.ifPresent(e -> {
                            e.insertInternal(lastEu, simulate);
                            if (lastEu > e.getOutputVoltage()){
                                explodeMultiblock();
                            }
                        });

                        if (startup < 100){
                            startup ++;
                        }
                        lubeTicker++;
                        if (lubeTicker > 72) lubeTicker = 0;
                    }
                    return true;
                }
                if (!simulate && startup > 0) startup = 0;
                return false;
            }

            @Override
            protected boolean canRecipeContinue() {
                boolean canContinue = super.canRecipeContinue();
                boolean boostEU = fluidHandler.map(f -> f.drainInput(Oxygen.getGas(2), FluidAction.SIMULATE).getAmount() == 2).orElse(false);
                FluidIngredient ingredient = activeRecipe.getInputFluids().get(0);
                int fuelConsumption = (int) (boostEU ? (4096 / activeRecipe.getPower()) : (2048 / activeRecipe.getPower()));
                ingredient = ingredient.copy(fuelConsumption);
                FluidIngredient finalIngredient = ingredient;
                return canContinue && (!activeRecipe.hasInputFluids() || tile.fluidHandler.map(t -> !t.consumeAndReturnInputs(Collections.singletonList(finalIngredient), true).isEmpty()).orElse(false));
            }

            @Override
            protected boolean validateRecipe(IRecipe r) {
                boolean canContinue = super.validateRecipe(r);
                boolean boostEU = fluidHandler.map(f -> f.drainInput(Oxygen.getGas(2), FluidAction.SIMULATE).getAmount() == 2).orElse(false);
                FluidIngredient ingredient = r.getInputFluids().get(0);
                int fuelConsumption = (int) (boostEU ? (4096 / r.getPower()) : (2048 / r.getPower()));
                ingredient = ingredient.copy(fuelConsumption);
                FluidIngredient finalIngredient = ingredient;
                return canContinue && (!r.hasInputFluids() || tile.fluidHandler.map(t -> !t.consumeAndReturnInputs(Collections.singletonList(finalIngredient), true).isEmpty()).orElse(false));
            }

            @Override
            protected void recipeFailure() {
                super.recipeFailure();
                activeRecipe = null;
                checkRecipe();
            }

            @Override
            public CompoundTag serialize() {
                CompoundTag nbt = super.serialize();
                nbt.putInt("startup", startup);
                nbt.putInt("lubeTicker", lubeTicker);
                return nbt;
            }

            @Override
            public void deserialize(CompoundTag nbt) {
                super.deserialize(nbt);
                startup = nbt.getInt("startup");
                lubeTicker = nbt.getInt("lubeTicker");
            }
        });
    }

    @Override
    public int drawInfo(InfoRenderWidget.MultiRenderWidget instance, PoseStack stack, Font renderer, int left, int top) {
        CombustionEngineWidget widget = (CombustionEngineWidget) instance;
        renderer.draw(stack, this.getDisplayName().getString(), left, top, 0xFAFAFF);
        if (getMachineState() != MachineState.ACTIVE) {
            renderer.draw(stack, "Inactive.", left, top + 8, 0xFAFAFF);
            return 16;
        } else if (instance.drawActiveInfo()) {
            renderer.draw(stack, "EU/t: " + widget.lastEU, left, top + 8, 0xFAFAFF);
            renderer.draw(stack, "Startup progress: " + (((float)widget.startup / 100) * 100) + "%", left, top + 16, 0xFAFAFF);
            renderer.draw(stack, "Current: " + widget.currentConsumption + " mb/t", left, top + 24, 0xFAFAFF);
            return 32;
        }
        return 8;
    }

    @Override
    public WidgetSupplier getInfoWidget() {
        return CombustionEngineWidget.build().setPos(10,10);
    }

    public static class CombustionEngineWidget extends InfoRenderWidget.MultiRenderWidget {

        public long currentConsumption = 0;
        public long lastEU = 0;
        public int startup = 0;

        protected CombustionEngineWidget(GuiInstance gui, IGuiElement parent, IInfoRenderer<MultiRenderWidget> renderer) {
            super(gui, parent, renderer);
        }

        @Override
        public void init() {
            super.init();
            BlockEntityCombustionEngine turbine = (BlockEntityCombustionEngine) gui.handler;
            gui.syncLong(() -> turbine.lastEu, i -> this.lastEU = i, SERVER_TO_CLIENT);
            gui.syncInt(() -> turbine.startup, i -> this.startup = i, SERVER_TO_CLIENT);
            gui.syncLong(() -> turbine.lastConsumption, i -> currentConsumption = i, SERVER_TO_CLIENT);
        }

        public static WidgetSupplier build() {
            return builder((a, b) -> new CombustionEngineWidget(a, b, (IInfoRenderer<MultiRenderWidget>) a.handler));
        }

    }
}
