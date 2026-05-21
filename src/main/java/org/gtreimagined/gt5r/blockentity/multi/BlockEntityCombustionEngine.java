package org.gtreimagined.gt5r.blockentity.multi;

import brachy.modularui.screen.viewport.ModularGuiContext;
import brachy.modularui.theme.WidgetThemeEntry;
import brachy.modularui.value.sync.IntSyncValue;
import brachy.modularui.value.sync.LongSyncValue;
import brachy.modularui.value.sync.PanelSyncManager;
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityMultiMachine;
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.mui.widgets.GTInfoRenderWidget;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.recipe.ingredient.FluidIngredient;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import org.gtreimagined.gtlib.util.Utils;
import org.gtreimagined.gtlib.util.int2;

import java.util.Collections;
import java.util.List;

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
            public boolean consumeGeneratorInputs(boolean simulate) {
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
    public void drawInfo(GTInfoRenderWidget widget, ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {
        widget.drawText(context, widgetTheme, 0, 0, this.getDisplayName(), 0xFAFAFF);
        if (getMachineState() != MachineState.ACTIVE) {
            widget.drawText(context, widgetTheme, 0, 8, Utils.literal("Inactive."), 0xFAFAFF);
        } else {
            widget.drawText(context, widgetTheme, 0, 8, Utils.literal("EU/t: " +
                    widget.getSyncedValue("lastEu", Long.class).orElse(0L)), 0xFAFAFF);
            int startup = widget.getSyncedValue("startup", Integer.class).orElse(0);
            widget.drawText(context, widgetTheme, 0, 16, Utils.literal("Startup progress: " +(((float)startup / 100) * 100) + "%"), 0xFAFAFF);
            widget.drawText(context, widgetTheme, 0, 24, Utils.literal("Current: " +
                    widget.getSyncedValue("currentConsumption", Long.class) + " L/t"), 0xFAFAFF);
        }
    }

    @Override
    public void registerSyncHandlers(PanelSyncManager manager) {
        manager.syncValue("lastEu", new LongSyncValue(() -> this.lastEu));
        manager.syncValue("currentConsumption", new LongSyncValue(() -> this.lastConsumption));
        manager.syncValue("startup", new IntSyncValue(() -> this.startup));
    }
}
