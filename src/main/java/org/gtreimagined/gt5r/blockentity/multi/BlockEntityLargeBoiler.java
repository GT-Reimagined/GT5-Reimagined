package org.gtreimagined.gt5r.blockentity.multi;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.block.BlockCasing;
import org.gtreimagined.gt5r.blockentity.multi.BlockEntityCombustionEngine.CombustionEngineWidget;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtcore.item.ItemSelectorTag;
import org.gtreimagined.gtlib.block.BlockBasic;
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityMultiMachine;
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler;
import org.gtreimagined.gtlib.gui.GuiInstance;
import org.gtreimagined.gtlib.gui.IGuiElement;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.widget.InfoRenderWidget;
import org.gtreimagined.gtlib.gui.widget.WidgetSupplier;
import org.gtreimagined.gtlib.integration.xei.renderer.IInfoRenderer;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.event.IMachineEvent;
import org.gtreimagined.gtlib.machine.event.MachineEvent;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.texture.Texture;

import static org.gtreimagined.gt5r.data.Materials.DistilledWater;
import static org.gtreimagined.gt5r.data.Materials.Steam;
import static org.gtreimagined.gtlib.gui.ICanSyncData.SyncDirection.SERVER_TO_CLIENT;
import static org.gtreimagined.gtlib.machine.Tier.*;

public class BlockEntityLargeBoiler extends BlockEntityMultiMachine<BlockEntityLargeBoiler> {
    private int euPerTick = 0;
    private int efficiency = 0;


    public BlockEntityLargeBoiler(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.recipeHandler.set(() -> new MachineRecipeHandler<>(this){


            private int efficiencyIncrease;
            private int integratedCircuitConfig = 0; //Steam output is reduced by 1000L per config
            private int excessFuel = 0; //Eliminate rounding errors for fuels that burn half items
            private int excessProjectedEU = 0; //Eliminate rounding errors from throttling the boiler
            boolean explode = false;
            @Override
            public boolean consumePower(boolean simulate) {
                if (processingBlocked) return false;
                int tGeneratedEU = (int) (euPerTick * 2L * efficiency / 10000L);
                if (tGeneratedEU > 0 && !simulate) {
                    int amount = (tGeneratedEU + 160) / 160;
                    fluidHandler.ifPresent(f -> {
                        if (f.drainInput(Materials.Water.getLiquid(amount), FluidAction.EXECUTE).getAmount() == amount || f.drainInput(DistilledWater.getLiquid(amount), FluidAction.EXECUTE).getAmount() == amount) {
                            f.addOutputs(Steam.getGas(tGeneratedEU));
                            tile.onMachineEvent(MachineEvent.FLUIDS_OUTPUTTED);
                        } else {
                            explode = true;
                        }
                    });

                }
                return true;
            }

            @Override
            public void checkRecipe() {
                super.checkRecipe();
                itemHandler.ifPresent(i -> {
                    ItemStack circuit = i.getHandler(SlotType.STORAGE).getStackInSlot(0);
                    if (circuit.getItem() instanceof ItemSelectorTag intCircuit){
                        if (intCircuit.circuitId > 0 && intCircuit.circuitId <= 24){
                            integratedCircuitConfig = intCircuit.circuitId;
                        }
                    } else {
                        if (integratedCircuitConfig != 0){
                            integratedCircuitConfig = 0;
                        }
                    }
                });
            }

            @Override
            public void onServerUpdate() {
                if (explode){
                    explodeMultiblock();
                    return;
                }
                super.onServerUpdate();
                if (tile.machineState == MachineState.ACTIVE && efficiency < 10000){
                    efficiency += efficiencyIncrease;
                    if (efficiency > 10000) efficiency = 10000;
                } else if (tile.machineState != MachineState.ACTIVE && efficiency > 0){
                    efficiency -= Math.min(efficiency, 1000);
                }
            }

            @Override
            protected void calculateDurations() {
                maxProgress = activeRecipe.getDuration();
                if (activeRecipe.hasInputItems()){
                    this.excessFuel += (int) activeRecipe.getPower();
                    this.maxProgress += this.excessFuel / 80;
                    this.excessFuel %= 80;
                }
                this.maxProgress = adjustBurnTimeForConfig(runtimeBoost(maxProgress));
                euPerTick = adjustEUtForConfig(getEUt());
                this.efficiencyIncrease = getEfficiencyIncrease() * Math.max(activeRecipe.getSpecialValue(), 1);
            }

            @Override
            public void resetRecipe() {
                super.resetRecipe();
                euPerTick = 0;
                this.efficiencyIncrease = 0;
            }

            private int adjustEUtForConfig(int rawEUt) {
                int adjustedSteamOutput = rawEUt - 25 * integratedCircuitConfig;
                return Math.max(adjustedSteamOutput, 25);
            }

            private int adjustBurnTimeForConfig(int rawBurnTime) {
                if (efficiency < 10000) {
                    return rawBurnTime;
                }
                int adjustedEUt = Math.max(25, getEUt() - 25 * integratedCircuitConfig);
                int adjustedBurnTime = rawBurnTime * getEUt() / adjustedEUt;
                this.excessProjectedEU += getEUt() * rawBurnTime - adjustedEUt * adjustedBurnTime;
                adjustedBurnTime += this.excessProjectedEU / adjustedEUt;
                this.excessProjectedEU %= adjustedEUt;
                return adjustedBurnTime;
            }

            @Override
            public void onMachineEvent(IMachineEvent event, Object... data) {
                super.onMachineEvent(event, data);
                if (event == SlotType.STORAGE){
                    checkRecipe();
                }
            }

            @Override
            public CompoundTag serialize() {
                CompoundTag tag = super.serialize();
                tag.putInt("excessProjectedEu", this.excessProjectedEU);
                tag.putInt("excessFuel", excessFuel);
                tag.putInt("efficiency", efficiency);
                return tag;
            }

            @Override
            public void deserialize(CompoundTag nbt) {
                super.deserialize(nbt);
                this.excessProjectedEU = nbt.getInt("excessProjectedEu");
                this.excessFuel = nbt.getInt("excessFuel");
                efficiency = nbt.getInt("efficiency");
            }
        });
    }

    public BlockBasic getCasing(){
        if (tier == LV){
            return GT5RBlocks.BRONZE_PLATED_BRICK_CASING;
        } else if (tier == MV){
            return GT5RBlocks.SOLID_STEEL_CASING;
        } else if (tier == HV){
            return GT5RBlocks.TITANIUM_CASING;
        }
        return GT5RBlocks.TUNGSTENSTEEL_CASING;
    }

    public int getEUt(){
        if (tier == LV){
            return 400;
        } else if (tier == MV){
            return 600;
        } else if (tier == HV){
            return 800;
        }
        return 1000;
    }

    public int getEfficiencyIncrease(){
        if (tier == LV){
            return 16;
        } else if (tier == MV){
            return 12;
        } else if (tier == HV){
            return 8;
        }
        return 4;
    }

    int runtimeBoost(int time) {
        if (tier == LV) return time * 2;
        int dividend = tier == MV ? 150 : tier == HV ? 130 : 120;
        return time * dividend / 100;
    }

    public Block getFireboxCasing(){
        if (tier == LV){
            return GT5RBlocks.BRONZE_FIREBOX_CASING;
        } else if (tier == MV){
            return GT5RBlocks.STEEL_FIREBOX_CASING;
        } else if (tier == HV){
            return GT5RBlocks.TITANIUM_FIREBOX_CASING;
        }
        return GT5RBlocks.TUNGSTENSTEEL_FIREBOX_CASING;
    }

    public Block getPipeCasing(){
        if (tier == LV){
            return GT5RBlocks.BRONZE_PIPE_CASING;
        } else if (tier == MV){
            return GT5RBlocks.STEEL_PIPE_CASING;
        } else if (tier == HV){
            return GT5RBlocks.TITANIUM_PIPE_CASING;
        }
        return GT5RBlocks.TUNGSTENSTEEL_PIPE_CASING;
    }

    public Texture getTextureForHatches(Direction dir, BlockPos hatchPos){
        if (hatchPos.getY() != this.getBlockPos().getY()) return super.getTextureForHatches(dir, hatchPos);
        String prefix = tier == LV ? "bronze" : tier == MV ? "steel" : tier == HV ? "titanium" : "tungstensteel";
        return new Texture(GT5Reimagined.ID, "block/casing/" + prefix + "_firebox");
    }

    @Override
    public BlockBasic getHatchBlock(BlockPos hatchPos) {
        if (hatchPos.getY() != this.getBlockPos().getY()) return this.getCasing();
        String prefix = tier == LV ? "bronze" : tier == MV ? "steel" : tier == HV ? "titanium" : "tungstensteel";
        return GT5Reimagined.get(BlockCasing.class, prefix + "_firebox_casing");
    }

    @Override
    public int drawInfo(InfoRenderWidget.MultiRenderWidget instance, PoseStack stack, Font renderer, int left, int top) {
        renderer.draw(stack, this.getDisplayName().getString(), left, top, 0xFAFAFF);
        if (!(instance instanceof LargeBoilerInforWidget w)) return 8;
        if (getMachineState() != MachineState.ACTIVE) {
            renderer.draw(stack, "Inactive.", left, top + 8, 0xFAFAFF);
            return 16;
        } else if (instance.drawActiveInfo()) {
            int tGeneratedSteam = (int) (instance.euT * 2L * w.efficiency / 10000L);
            renderer.draw(stack, "Progress: " + instance.currentProgress + "/" + instance.maxProgress, left, top + 8, 0xFAFAFF);
            renderer.draw(stack, "Overclock: " + instance.overclock, left, top + 16, 0xFAFAFF);
            renderer.draw(stack, "Steam/t: " + tGeneratedSteam, left, top + 24, 0xFAFAFF);
            return 32;
        }
        return 8;
    }

    @Override
    public WidgetSupplier getInfoWidget() {
        return LargeBoilerInforWidget.build().setPos(10, 10);
    }

    private static class LargeBoilerInforWidget extends InfoRenderWidget.MultiRenderWidget{
        int efficiency;

        protected LargeBoilerInforWidget(GuiInstance gui, IGuiElement parent, IInfoRenderer<MultiRenderWidget> renderer) {
            super(gui, parent, renderer);
        }

        @Override
        public void init() {
            super.init();
            BlockEntityMultiMachine<?> m = (BlockEntityMultiMachine<?>) gui.handler;
            gui.syncInt(() -> m.recipeHandler.map(MachineRecipeHandler::getCurrentProgress).orElse(0), i -> this.currentProgress = i, SERVER_TO_CLIENT);
            gui.syncInt(() -> m.recipeHandler.map(MachineRecipeHandler::getMaxProgress).orElse(0), i -> this.maxProgress = i, SERVER_TO_CLIENT);
            gui.syncInt(() -> m.recipeHandler.map(MachineRecipeHandler::getOverclock).orElse(0), i -> this.overclock = i, SERVER_TO_CLIENT);
            if (m instanceof BlockEntityLargeBoiler b){
                gui.syncInt(() -> b.euPerTick, i -> this.euT = i, SERVER_TO_CLIENT);
                gui.syncInt(() -> b.efficiency, i -> this.efficiency = i, SERVER_TO_CLIENT);
            }
        }

        public static WidgetSupplier build() {
            return builder((a, b) -> new LargeBoilerInforWidget(a, b, (IInfoRenderer<MultiRenderWidget>) a.handler));
        }
    }

}
