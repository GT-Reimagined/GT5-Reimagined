package org.gtreimagined.gt5r.blockentity.multi;

import brachy.modularui.screen.viewport.ModularGuiContext;
import brachy.modularui.theme.WidgetThemeEntry;
import brachy.modularui.value.sync.BooleanSyncValue;
import brachy.modularui.value.sync.IntSyncValue;
import brachy.modularui.value.sync.LongSyncValue;
import brachy.modularui.value.sync.PanelSyncManager;
import org.gtreimagined.gtlib.capability.machine.MultiMachineEnergyHandler;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.event.MachineEvent;
import org.gtreimagined.gtlib.machine.types.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import org.gtreimagined.gt5r.worldgen.OilSpoutEntry;
import org.gtreimagined.gt5r.worldgen.OilSpoutSavedData;
import org.gtreimagined.gtlib.mui.widgets.GTInfoRenderWidget;
import org.gtreimagined.gtlib.util.Utils;
import org.gtreimagined.gtlib.util.int2;

import static org.gtreimagined.gt5r.blockentity.multi.BlockEntityDrillingRigBase.MineResult.*;
import static org.gtreimagined.gt5r.data.GT5RBlocks.MINING_PIPE;
import static org.gtreimagined.gt5r.data.GT5RBlocks.MINING_PIPE_THIN;

public class BlockEntityOilDrillingRig extends BlockEntityDrillingRigBase<BlockEntityOilDrillingRig> {
    int progress = 0;
    OilSpoutEntry oilEntry = null;

    public BlockEntityOilDrillingRig(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    int outputTicker = 0;

    @Override
    public void run(Level level, BlockPos pos, BlockState state) {
        if (oilEntry == null){
            oilEntry = OilSpoutSavedData.getOrCreate((ServerLevel) level).getFluidVeinWorldEntry(SectionPos.blockToSectionCoord(this.miningPos.getX()), SectionPos.blockToSectionCoord(this.miningPos.getZ()));
        }
        if (oilEntry.getFluid() == null) return;
        FluidStack fluidHolder = new FluidStack(oilEntry.getFluid().fluid(), oilEntry.getCurrentYield());
        if (outputTicker > 0){
            outputTicker--;
            return;
        }
        if (progress == 0){
            if (!fluidHandler.map(f -> f.fillOutput(fluidHolder, FluidAction.SIMULATE) == oilEntry.getCurrentYield()).orElse(false)){
                outputTicker = 40;
                this.setMachineState(MachineState.IDLE);
                return;
            }
        }
        setActive();
        energyHandler.ifPresent(e -> e.extractEu(euPerTick, false));
        if (++progress == cycle){
            progress = 0;
            if (fluidHandler.map(f -> f.fillOutput(fluidHolder, FluidAction.SIMULATE) == oilEntry.getCurrentYield()).orElse(false)){
                fluidHandler.ifPresent(f -> f.fillOutput(fluidHolder, FluidAction.EXECUTE));
                onMachineEvent(MachineEvent.FLUIDS_OUTPUTTED);
                oilEntry.decreaseLevel();
            }
        }
    }

    protected MineResult mineBelowBlock(Level level, BlockPos pos, boolean dropBlock, ItemStack item) {
        BlockState blockstate = level.getBlockState(pos);
        BlockState aboveBlockState = level.getBlockState(pos.above());
        if (aboveBlockState.getBlock() != MINING_PIPE && pos.getY() + 1 != this.getBlockPos().getY()){
            resetMiningPos();
            return PIPE_BROKEN;
        }
        if (blockstate.getDestroySpeed(level, pos) < 0) {
            return FOUND_OBSTRUCTION;
        } else {
            if (!(blockstate.getBlock() instanceof BaseFireBlock)) {
                level.levelEvent(2001, pos, Block.getId(blockstate));
            }
            boolean miningPipe = blockstate.getBlock() == MINING_PIPE || blockstate.getBlock() == MINING_PIPE_THIN;

            if (!miningPipe){
                if (!mineBlock(level, pos, dropBlock, item)) {
                    return FOUND_OBSTRUCTION;
                }
            }
            boolean flag = blockstate.getBlock() == MINING_PIPE || level.setBlock(pos, MINING_PIPE.defaultBlockState(), 3, 512);
            if (flag && pos.getY() + 1 < this.getBlockPos().getY()) {
                level.setBlock(pos.above(), MINING_PIPE_THIN.defaultBlockState(), 11);
            }
            BlockState belowBlockState = level.getBlockState(pos.below());
            if (belowBlockState.getBlock() == Blocks.BEDROCK || belowBlockState.getBlock() == Blocks.VOID_AIR){
                return miningPipe ? FOUND_BOTTOM_MINING_PIPE : FOUND_BOTTOM;
            }
            return miningPipe ? FOUND_MINING_PIPE : FOUND_MINEABLE;
        }
    }

    @Override
    public void afterStructureFormed() {
        super.afterStructureFormed();
        this.energyHandler.ifPresent(e -> {
            int tier = ((MultiMachineEnergyHandler<?>) e).getAccumulatedPower().getIntegerId();
            this.euPerTick = 3 * (1 << (tier << 1));
            this.cycle = (int) (160 * (tier == 0 ? 2 : Math.pow(0.5, tier - 1)));
        });
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("progress", progress);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.progress = nbt.getInt("progress");
    }


    @Override
    public int2 getPos() {
        return new int2(10, 10);
    }

    @Override
    public void drawInfo(GTInfoRenderWidget widget, ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {

        widget.drawText(context, widgetTheme, 0, 0, this.getDisplayName(), 0xFAFAFF);
        if (getMachineState() != MachineState.ACTIVE) {
            widget.drawText(context, widgetTheme, 0, 8, Utils.literal("Inactive."), 0xFAFAFF);
        } else {
            BlockPos currentPos = widget.getSyncedValue("currentPos", Long.class).map(BlockPos::of).orElse(null);
            if (widget.getSyncedValue("foundBottom", Boolean.class).orElse(false)){
                widget.drawText(context, widgetTheme, 0, 8, Utils.literal("Progress: " +
                        widget.getSyncedValue("progress", Integer.class).orElse(0) + "/" +
                        widget.getSyncedValue("maxPogress", Integer.class).orElse(0)), 0xFAFAFF);
            } else if (widget.getSyncedValue("stopped", Boolean.class).orElse(false) && currentPos != null){
                widget.drawText(context, widgetTheme, 0, 8, Utils.literal("Can't mine at: " + currentPos), 0xFAFAFF);
                widget.drawText(context, widgetTheme, 0, 16, Utils.literal("Y: " + currentPos.getY()), 0xFAFAFF);
            } else if (currentPos != null){
                widget.drawText(context, widgetTheme, 0, 8, Utils.literal("Mining Position at: "), 0xFAFAFF);
                widget.drawText(context, widgetTheme, 0, 16, Utils.literal("Y: " + currentPos.getY()), 0xFAFAFF);
            }
        }
    }

    @Override
    public void registerSyncHandlers(PanelSyncManager manager) {
        manager.syncValue("currentPos", new LongSyncValue(() -> this.miningPos.asLong()));
        manager.syncValue("stopped", new BooleanSyncValue(() -> this.stopped));
        manager.syncValue("foundBottom", new BooleanSyncValue(() -> this.foundBottom));
        manager.syncValue("progress", new IntSyncValue(() -> this.progress));
        manager.syncValue("maxProgress", new IntSyncValue(() -> this.cycle));
    }
}
