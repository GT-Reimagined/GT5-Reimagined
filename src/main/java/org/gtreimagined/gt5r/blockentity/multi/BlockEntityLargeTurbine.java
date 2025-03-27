package org.gtreimagined.gt5r.blockentity.multi;

import com.mojang.blaze3d.vertex.PoseStack;
import org.gtreimagined.gtlib.block.BlockBasic;
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityMultiMachine;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler;
import org.gtreimagined.gtlib.gui.GuiInstance;
import org.gtreimagined.gtlib.gui.IGuiElement;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.widget.InfoRenderWidget;
import org.gtreimagined.gtlib.gui.widget.WidgetSupplier;
import org.gtreimagined.gtlib.integration.jeirei.renderer.IInfoRenderer;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.event.IMachineEvent;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gt5r.items.ItemTurbineRotor;

import static org.gtreimagined.gtlib.data.AntimatterMaterialTypes.ROD_LONG;
import static org.gtreimagined.gtlib.gui.ICanSyncData.SyncDirection.SERVER_TO_CLIENT;
import static org.gtreimagined.gtlib.machine.Tier.EV;
import static org.gtreimagined.gtlib.machine.Tier.HV;
import static org.gtreimagined.gt5r.data.Materials.DistilledWater;
import static org.gtreimagined.gt5r.data.Materials.Steam;

public class BlockEntityLargeTurbine extends BlockEntityMultiMachine<BlockEntityLargeTurbine> {

    /**
     * SYNC-DATA
     */
    protected int baseEfficiency, optFlow;
    protected double realOptFlow = 0;
    protected long lastEU = 0;

    public BlockEntityLargeTurbine(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        recipeHandler.set(() ->
                new MachineRecipeHandler<BlockEntityLargeTurbine>(this) {
                    @Override
                    protected boolean validateRecipe(IRecipe r) {
                        boolean hasRotor = itemHandler.map(i -> i.getHandler(SlotType.STORAGE).getStackInSlot(0).getItem() instanceof ItemTurbineRotor).orElse(false);
                        boolean inputFluids = r.hasInputFluids();
                        return hasRotor && inputFluids;
                    }

                    @Override
                    public void checkRecipe() {
                        super.checkRecipe();
                        ItemStack stack = itemHandler.map(i -> i.getHandler(SlotType.STORAGE).getStackInSlot(0)).orElse(ItemStack.EMPTY);
                        ItemTurbineRotor rotor = stack.getItem() instanceof ItemTurbineRotor rotor1 ? rotor1 : null;
                        if (rotor != null){
                            baseEfficiency = (int) (rotor.getEfficiency() * 100);
                            optFlow = (int) (rotor.getSpeed() * 50);
                        } else {
                            if (lastRecipe != null ){
                                resetRecipe();
                            }
                        }

                    }

                    @Override
                    public void onMachineEvent(IMachineEvent event, Object... data) {
                        super.onMachineEvent(event, data);
                        if (event == SlotType.STORAGE){
                            ItemStack stack = itemHandler.map(i -> i.getHandler(SlotType.STORAGE).getStackInSlot(0)).orElse(ItemStack.EMPTY);
                            ItemTurbineRotor rotor = stack.getItem() instanceof ItemTurbineRotor rotor1 ? rotor1 : null;
                            if (rotor == null) {
                                resetRecipe();
                                setMachineState(MachineState.IDLE);
                            } else {
                                checkRecipe();
                            }
                        }
                    }

                    @Override
                    public void resetRecipe() {
                        super.resetRecipe();
                        baseEfficiency = 0;
                        optFlow = 0;
                        realOptFlow = 0;
                    }

                    @Override
                    protected boolean consumeGeneratorResources(boolean simulate) {
                        long newPower = fluidIntoPower(optFlow, baseEfficiency, simulate);
                        if (newPower == 0){
                            lastEU = newPower;
                            return false;
                        }
                        if (simulate) return true;
                        long difference = newPower - lastEU; // difference between current output and new output

                        // Magic numbers: can always change by at least 10 eu/t, but otherwise by at most 1 percent of the difference in power level (per tick)
                        // This is how much the turbine can actually change during this tick
                        int maxChangeAllowed = Math.max(10, (int) Math.ceil(Math.abs(difference) * 0.01));

                        if (Math.abs(difference) > maxChangeAllowed) { // If this difference is too big, use the maximum allowed change
                            long change = maxChangeAllowed * (difference > 0 ? 1 : -1); // Make the change positive or negative.
                            lastEU += change; // Apply the change
                        } else
                            lastEU = newPower;

                        energyHandler.ifPresent(e -> {
                            if (lastEU > e.getOutputVoltage()){
                                explodeMultiblock();
                                return;
                            }
                            e.insertInternal(lastEU, false);
                        });
                        if (getLevel() != null && getLevel().getGameTime() % 1000 == 0 && getLevel().random.nextInt(2) == 0){
                            itemHandler.ifPresent(i -> {
                                ItemStack stack = i.getHandler(SlotType.STORAGE).getStackInSlot(0);
                                ItemStack compare = stack.copy();
                                if(stack.getItem() instanceof ItemTurbineRotor rotor && stack.hurt(1, getLevel().random, null)){
                                    var materialType = rotor.getAntimatterToolType().getMaterialTypeItem();
                                    var material = rotor.getPrimaryMaterial(compare);
                                    ItemStack broken = materialType != null && material.has(materialType) ? materialType.get(material, 1) : ROD_LONG.get(rotor.getRodMaterial(), 1);
                                    i.getHandler(SlotType.STORAGE).setStackInSlot(0, broken);
                                }
                            });
                        }
                        return true;
                    }

                    long fluidIntoPower(int aOptFlow, int aBaseEff, boolean simulate){
                        int tEU = 0;
                        long fuelValue = activeRecipe.getPower();
                        long actualOptimalFlow = tile.getMachineTier() == EV ? aOptFlow / fuelValue : aOptFlow;
                        long totalFlow = 0; // Byproducts are based on actual flow
                        int flow = 0;
                        int remainingFlow = (int) (actualOptimalFlow * 1.25f); // Allowed to use up to 125% of optimal flow.  Variable required outside of loop for multi-hatch scenarios.
                        realOptFlow = tile.getMachineTier() == HV ? ((actualOptimalFlow / 2) / (0.5)) : actualOptimalFlow;
                        MachineFluidHandler<?> handler = fluidHandler.orElse(null);
                        if (handler == null) return 0;
                        boolean empty = true;
                        for (int i = 0; i < handler.getInputTanks().getTanks() && remainingFlow > 0; i++) { // loop through each hatch; extract inputs and track totals.
                            FluidStack fluidHolder = handler.getInputTanks().getFluidInTank(i);
                            if (activeRecipe.getInputFluids().get(0).matches(fluidHolder)) {
                                flow = fluidHolder.getAmount(); // Get all (steam) in hatch
                                flow = Math.min(flow, Math.min(remainingFlow, (int) (actualOptimalFlow * 1.25f))); // try to use up to 125% of optimal flow w/o exceeding remainingFlow
                                if (!simulate){
                                    handler.getInputTanks().drain(Utils.ca(flow, fluidHolder), FluidAction.EXECUTE);
                                }
                                remainingFlow -= flow; // track amount we're allowed to continue depleting from hatches
                                totalFlow += flow; // track total input used
                                empty = false;
                            }
                        }
                        if (empty) return 0;

                        tEU = (int) (Math.min((float) actualOptimalFlow, totalFlow));
                        if (tile.getMachineTier() != EV){
                            int waterToOutput = tile.getMachineTier() == HV ? useWater(totalFlow / 160.0f) : (int) totalFlow;
                            FluidStack fluid = tile.getMachineTier() == HV ? DistilledWater.getLiquid(waterToOutput) : Steam.getGas(waterToOutput);
                            if (!simulate) handler.fillOutput(fluid, FluidAction.EXECUTE);
                        } else {
                            tEU *= fuelValue;
                        }
                        int multiplier = tile.getMachineTier() == HV ? 2 : 1;

                        if ((totalFlow > 0 || tile.getMachineTier() == EV) && totalFlow != actualOptimalFlow) {
                            float efficiency = 1.0f - Math.abs(((totalFlow - (float) actualOptimalFlow) / actualOptimalFlow));
                            if(totalFlow>actualOptimalFlow){efficiency = 1.0f;}
                            if (efficiency < 0)
                                efficiency = 0; // Can happen with really ludicrously poor inefficiency.
                            tEU *= efficiency;
                            tEU = Math.max(1, (int)((long)tEU * (long)aBaseEff / (10000L * multiplier)));
                        } else {
                            tEU = (int)((long)tEU * (long)aBaseEff / (10000L * multiplier));
                        }

                        return tEU;
                    }

                    float water;
                    private int useWater(float input) {
                        water = water + input;
                        int usage = (int) water;
                        water = water - (int) usage;
                        return usage;
                    }
                });
    }

    public BlockBasic getCasing(){
        if (tier == HV) {
            return GT5RBlocks.STEEL_TURBINE_CASING;
        } else if (tier == EV) {
            return GT5RBlocks.STAINLESS_STEEL_TURBINE_CASING;
        } else {
            return GT5RBlocks.TITANIUM_TURBINE_CASING;
        }
    }

    @Override
    public BlockBasic getHatchBlock(BlockPos pos) {
        return getCasing();
    }

    @Override
    public int drawInfo(InfoRenderWidget.MultiRenderWidget instance, PoseStack stack, Font renderer, int left, int top) {
        int size = super.drawInfo(instance, stack, renderer, left, top);
        if (this.getMachineState() == MachineState.ACTIVE) {
            LargeTurbineWidget wid = (LargeTurbineWidget) instance;
            renderer.draw(stack, "Current: " + wid.currentConsumption + " mb/t", left, top + size, 0xFAFAFF);
            renderer.draw(stack, "Optimal: " + wid.recommendedConsumption + " mb/t", left, top + size + 8, 0xFAFAFF);
            renderer.draw(stack, "EU generation: " + wid.lastEU, left, top + size + 16, 0xFAFAFF);
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
            BlockEntityLargeTurbine turbine = (BlockEntityLargeTurbine) gui.handler;
            gui.syncLong(() -> (long) turbine.realOptFlow, i -> this.currentConsumption = i, SERVER_TO_CLIENT);
            gui.syncLong(() -> (long) turbine.optFlow, i -> this.recommendedConsumption = i, SERVER_TO_CLIENT);
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
