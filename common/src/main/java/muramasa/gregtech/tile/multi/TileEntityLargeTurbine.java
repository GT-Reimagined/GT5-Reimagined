package muramasa.gregtech.tile.multi;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import muramasa.antimatter.capability.machine.MachineRecipeHandler;
import muramasa.antimatter.gui.GuiInstance;
import muramasa.antimatter.gui.IGuiElement;
import muramasa.antimatter.gui.widget.InfoRenderWidget;
import muramasa.antimatter.gui.widget.WidgetSupplier;
import muramasa.antimatter.integration.jeirei.renderer.IInfoRenderer;
import muramasa.antimatter.machine.MachineState;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.recipe.IRecipe;
import muramasa.antimatter.recipe.Recipe;
import muramasa.antimatter.recipe.ingredient.FluidIngredient;
import muramasa.antimatter.tile.multi.TileEntityMultiMachine;
import muramasa.antimatter.util.Utils;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collections;
import java.util.List;

import static muramasa.antimatter.gui.ICanSyncData.SyncDirection.SERVER_TO_CLIENT;
import static muramasa.gregtech.data.Materials.DistilledWater;

public class TileEntityLargeTurbine extends TileEntityMultiMachine<TileEntityLargeTurbine> {

    /**
     * SYNC-DATA
     */
    protected long lastConsume = 0;
    protected long recipeConsumption = 0;
    protected long lastEU = 0;

    public TileEntityLargeTurbine(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        recipeHandler.set(() ->
                new MachineRecipeHandler<>(this) {

                    private IRecipe sourceRecipe;
                    private double efficiency;

                    @Override
                    protected boolean validateRecipe(IRecipe r) {
                        return true;
                    }

                    @Override
                    public IRecipe findRecipe() {
                        IRecipe r = super.findRecipe();
                        if (r == null) return null;
                        sourceRecipe = r;
                        //Source recipe contains fluid amounts, map to turbine
                        List<FluidIngredient> stacks = r.getInputFluids();
                        if (stacks.size() == 0) return null;
                        //ItemStack t = tile.itemHandler.map(tt -> tt.getSpecial()).orElse(ItemStack.EMPTY);
                        //if (!(t.getItem() instanceof ItemTurbine)) return null;
                        // ItemTurbine turbine = (ItemTurbine) t.getItem();
                        int flow = 120;//turbine.optimalEUT;
                        efficiency = 1.15;//turbine.efficency;
                        long toConsume = calculateGeneratorConsumption(flow, sourceRecipe);
                        TileEntityLargeTurbine.this.recipeConsumption = (int) toConsume;
                        return Utils.getFluidPoweredRecipe(Collections.singletonList(stacks.get(0).copy((int) toConsume)),
                                new FluidHolder[]{DistilledWater.getLiquid(stacks.get(0).getAmount())},// Arrays.stream(sourceRecipe.getOutputFluids()).map(tt -> new FluidStack(tt.getFluid(), (int) (tt.getAmount()*toConsume))).toArray(FluidStack[]::new),
                                1, flow, 1);
                    }

                    @Override
                    protected boolean consumeGeneratorResources(boolean simulate) {
                        if (!activeRecipe.hasInputFluids()) {
                            throw new RuntimeException("Missing fuel in active generator recipe!");
                        }
                        //boolean shouldRun = tile.energyHandler.map(h -> h.insert((long)(tile.getMachineType().getMachineEfficiency()*(double)tile.getMachineTier().getVoltage()),true) > 0).orElse(false);
                        ///if (!shouldRun) return false;
                        long recipeAmount = activeRecipe.getInputFluids().get(0).getAmount();
                        long toConsume = recipeAmount; // calculateGeneratorConsumption(tile.getMachineTier().getVoltage(), activeRecipe);// (long) ((double)tile.getMachineTier().getVoltage() / (activeRecipe.getPower() /(double) Objects.requireNonNull(activeRecipe.getInputFluids())[0].getAmount()));
                        long consumed = tile.fluidHandler.map(h -> {
                        /*
                            How much wiggle room? So, at optimal flow : generate regular. Otherwise, dampen by a factor of 1/(optimal-current) or 1/(current-optimal). Allow
                            consuming up to 1.5x optimal
                         */
                            FluidIngredient input = activeRecipe.getInputFluids().get(0);
                            long amount = input.drainedAmount((long) (toConsume * 1.5), h, true, true);

                            if (amount > 0) {
                                if (!simulate) {
                                    input.drain(amount, h, true, false);
                                    TileEntityLargeTurbine.this.lastConsume = amount;
                                }
                                return amount;
                            }
                            return 0L;
                        }).orElse(0L);
                        if (consumed > 0) {
                            if (consumed < recipeAmount) consumed *= Math.pow(1d / (recipeAmount - consumed), 0.04);
                            if (consumed > recipeAmount) consumed *= Math.pow(1d / (consumed - recipeAmount), 0.04);
                            //Input energy
                            long finalConsumed = consumed;
                            //Ignore the actual inserted amount a la multiblock.
                            if (!simulate) tile.energyHandler.ifPresent(handler -> {
                                long eu = (long) (efficiency * activeRecipe.getPower() * finalConsumed / recipeAmount);
                                Utils.addEnergy(handler, eu);
                                TileEntityLargeTurbine.this.lastEU = eu;
                            });
                            return true;
                        }
                        return false;
                    }
                });
    }

    @Override
    public int drawInfo(InfoRenderWidget.MultiRenderWidget instance, PoseStack stack, Font renderer, int left, int top) {
        int size = super.drawInfo(instance, stack, renderer, left, top);
        if (this.getMachineState() == MachineState.ACTIVE) {
            LargeTurbineWidget wid = (LargeTurbineWidget) instance;
            renderer.draw(stack, "Current: " + wid.currentConsumption + " mb/t", left, top + size, 16448255);
            renderer.draw(stack, "Optimal: " + wid.recommendedConsumption + " mb/t", left, top + size + 8, 16448255);
            renderer.draw(stack, "EU generation: " + wid.lastEU, left, top + size + 16, 16448255);
            return size + 24;
        }
        return size;
    }


    public static class LargeTurbineWidget extends InfoRenderWidget.MultiRenderWidget {

        public long currentConsumption = 0;
        public long lastEU = 0;
        public long recommendedConsumption = 0;

        protected LargeTurbineWidget(GuiInstance gui, IGuiElement parent, IInfoRenderer<MultiRenderWidget> renderer) {
            super(gui, parent, renderer);
        }

        @Override
        public void init() {
            super.init();
            TileEntityLargeTurbine turbine = (TileEntityLargeTurbine) gui.handler;
            gui.syncLong(() -> turbine.lastConsume, i -> this.currentConsumption = i, SERVER_TO_CLIENT);
            gui.syncLong(() -> turbine.recipeConsumption, i -> this.recommendedConsumption = i, SERVER_TO_CLIENT);
            gui.syncLong(() -> turbine.lastEU, i -> this.lastEU = i, SERVER_TO_CLIENT);
        }

        public static WidgetSupplier build() {
            return builder((a, b) -> new LargeTurbineWidget(a, b, (IInfoRenderer<MultiRenderWidget>) a.handler));
        }

        @Override
        public boolean drawActiveInfo() {
            return false;
        }
    }

    @Override
    public WidgetSupplier getInfoWidget() {
        return LargeTurbineWidget.build().setPos(10, 10);
    }
}
