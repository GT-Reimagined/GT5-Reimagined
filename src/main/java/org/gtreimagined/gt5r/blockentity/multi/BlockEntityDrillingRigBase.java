package org.gtreimagined.gt5r.blockentity.multi;

import brachy.modularui.factory.SidedPosGuiData;
import brachy.modularui.screen.ModularPanel;
import brachy.modularui.screen.UISettings;
import brachy.modularui.value.sync.BooleanSyncValue;
import brachy.modularui.value.sync.PanelSyncManager;
import brachy.modularui.widgets.CycleButtonWidget;
import brachy.modularui.widgets.ToggleButton;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import org.gtreimagined.gt5r.mui.GT5RGuiTextures;
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityMultiMachine;
import org.gtreimagined.gtlib.capability.IFilterableHandler;
import org.gtreimagined.gtlib.gui.GuiInstance;
import org.gtreimagined.gtlib.gui.IGuiElement;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.mui.GTGuiTextures;
import org.gtreimagined.gtlib.util.Utils;
import org.gtreimagined.gtlib.util.int3;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gt5r.gui.ButtonOverlays;

import java.util.List;

import static org.gtreimagined.gt5r.data.GT5RBlocks.MINING_PIPE;
import static org.gtreimagined.gt5r.data.GT5RBlocks.MINING_PIPE_THIN;

public abstract class BlockEntityDrillingRigBase<T extends BlockEntityDrillingRigBase<T>> extends BlockEntityMultiMachine<T> implements IMiningPipeTile, IFilterableHandler {
    protected boolean foundBottom = false;
    protected boolean stopped = false;
    protected boolean pullingUp;
    protected BlockPos miningPos;
    protected int euPerTick;
    protected int cycle = 160;
    protected int inactiveTicks = 0;
    public BlockEntityDrillingRigBase(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        miningPos = new int3(pos, this.getFacing(state)).back(1).immutable().below();
    }

    @Override
    public void onFirstTickServer(Level level, BlockPos pos, BlockState state) {
        super.onFirstTickServer(level, pos, state);
        if (foundBottom){
            LongList positions = new LongArrayList();
            for (int y = miningPos.getY(); y < this.getBlockPos().getY(); y++) {
                positions.add(BlockPos.asLong(miningPos.getX(), y, miningPos.getZ()));
            }
            MiningPipeStructureCache.add(this.level, this.getBlockPos(), positions);
        }
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
        if (inactiveTicks > 2){
            if (getMachineState() == MachineState.ACTIVE){
                setMachineState(MachineState.IDLE);
            }
        }
        boolean wasStopped = false;
        if (stopped && level.getGameTime() % 200 == 0){
            wasStopped = true;
            stopped = false;
        }
        if (!validStructure || stopped) return;
        ItemStack stack = itemHandler.map(i -> i.getHandler(SlotType.STORAGE).getStackInSlot(0)).orElse(ItemStack.EMPTY);
        if ((stack.getItem() == GT5RBlocks.MINING_PIPE_THIN.asItem() || foundBottom || pullingUp) && energyHandler.map(e -> e.getEnergy() >= euPerTick).orElse(false)){
            if (pullingUp){
                if (level.getGameTime() % 5 != 0) return;
                BlockState block = level.getBlockState(miningPos.above());
                if (block.getBlock() == MINING_PIPE){
                    boolean success = false;
                    if (itemHandler.map(i -> i.canOutputsFit(new ItemStack[]{new ItemStack(MINING_PIPE_THIN)})).orElse(false)){
                        itemHandler.ifPresent(i -> i.addOutputs(new ItemStack(MINING_PIPE_THIN)));
                        success = true;
                    } else if (itemHandler.map(i -> i.getHandler(SlotType.STORAGE).getStackInSlot(0).getCount() + 1 < i.getHandler(SlotType.STORAGE).getSlotLimit(0)).orElse(false)){
                        itemHandler.ifPresent(i -> i.getHandler(SlotType.STORAGE).insertItem(0, new ItemStack(MINING_PIPE_THIN), false));
                        success = true;
                    }
                    if (success){
                        if (foundBottom){
                            foundBottom = false;
                            MiningPipeStructureCache.remove(level, this.getBlockPos());
                        }
                        miningPos = miningPos.above();
                        level.setBlock(miningPos, Blocks.AIR.defaultBlockState(), 3);
                        if (miningPos.getY() + 1 < this.getBlockPos().getY()){
                            level.setBlock(miningPos.above(), MINING_PIPE.defaultBlockState(), 3);
                        }
                        setActive();
                        energyHandler.ifPresent(e -> e.extractEu(euPerTick, false));
                    } else inactiveTicks++;
                } else inactiveTicks++;
            } else if (!foundBottom){
                if (level.getGameTime() % 20 != 0) return;
                MineResult breakResult = mineBelowBlock(level, miningPos, true, getMiningPickaxe());
                if (breakResult == BlockEntityDrillingRigBase.MineResult.PIPE_BROKEN){
                    return;
                }
                setActive();
                energyHandler.ifPresent(e -> e.extractEu(euPerTick, false));
                if (breakResult == BlockEntityDrillingRigBase.MineResult.FOUND_BOTTOM || breakResult == MineResult.FOUND_BOTTOM_MINING_PIPE){
                    foundBottom = true;
                    LongList positions = new LongArrayList();
                    for (int y = miningPos.getY(); y < this.getBlockPos().getY(); y++) {
                        positions.add(BlockPos.asLong(miningPos.getX(), y, miningPos.getZ()));
                    }
                    MiningPipeStructureCache.add(this.level, this.getBlockPos(), positions);
                }
                if (!wasStopped) {
                    miningPos = miningPos.below();
                }
                if (breakResult == BlockEntityDrillingRigBase.MineResult.FOUND_OBSTRUCTION){
                    stopped = true;
                    return;
                }
                if (breakResult == BlockEntityDrillingRigBase.MineResult.FOUND_MINEABLE || breakResult == MineResult.FOUND_BOTTOM) {
                    stack.shrink(1);
                }
            } else {
                run(level, pos, state);
            }
        }
    }

    protected void setActive(){
        inactiveTicks = 0;
        if (getMachineState() == MachineState.IDLE) {
            setMachineState(MachineState.ACTIVE);
        }
    }

    protected abstract MineResult mineBelowBlock(Level level, BlockPos pos, boolean dropBlock, ItemStack item);

    protected abstract void run(Level level, BlockPos pos, BlockState state);

    protected boolean mineBlock(Level level, BlockPos pos, boolean dropBlock, ItemStack item){
        BlockState state = level.getBlockState(pos);
        if (state.isAir()) return true;
        BlockEntity blockentity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        //BlockEve event = new BlockEvent.BreakEvent(level, pos, blockstate, entity instanceof Player player ? player : null);
        //MinecraftForge.EVENT_BUS.post(event);
            /*if (event.isCanceled()){
                return false;
            }*/
        if (dropBlock) {
            if (level instanceof ServerLevel serverLevel) {
                List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, blockentity, null, item);
                if (itemHandler.map(i -> i.canOutputsFit(drops.toArray(ItemStack[]::new))).orElse(false)){
                    itemHandler.ifPresent(i -> i.addOutputs(drops.toArray(ItemStack[]::new)));
                } else {
                    drops.forEach(i -> Block.popResource(level, pos, i));
                }
                state.spawnAfterBreak(serverLevel, pos, ItemStack.EMPTY, false);
            }
        }
        return level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
    }

    protected ItemStack getMiningPickaxe(){
        return Items.NETHERITE_PICKAXE.getDefaultInstance();
    }

    @Override
    public void onRemove() {
        super.onRemove();
        MiningPipeStructureCache.remove(this.level, this.getBlockPos());
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("foundBottom", foundBottom);
        tag.putBoolean("pullingUp", pullingUp);
        tag.putLong("miningPos", miningPos.asLong());
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.foundBottom = nbt.getBoolean("foundBottom");
        this.pullingUp = nbt.getBoolean("pullingUp");
        this.miningPos = BlockPos.of(nbt.getLong("miningPos"));
    }

    @Override
    public void onGuiEvent(IGuiEvent event, Player playerEntity) {
        if (event.getFactory() == GuiEvents.EXTRA_BUTTON) {
            int[] data = ((GuiEvents.GuiEvent)event).data;
            if (data[1] == 0) {
                pullingUp = !pullingUp;
                playerEntity.displayClientMessage(Utils.literal((pullingUp ? "Currently pulling up mining pipes" : "No longer pulling up mining pipes")), false);
            }
        }
    }

    @Override
    public void addWidgets(ModularPanel<?> panel, SidedPosGuiData sidedPosGuiData, PanelSyncManager panelSyncManager, UISettings uiSettings) {
        panelSyncManager.syncValue("pulling_up", new BooleanSyncValue(() -> pullingUp, b -> pullingUp = b).allowC2S());
        panel.child(new ToggleButton().pos(152, 23).size(18).syncHandler("pulling_up")
                .overlay(GT5RGuiTextures.PULL_UP));
    }

    @Override
    public boolean test(SlotType<?> slotType, int slot, ItemStack stack) {
        return slotType != SlotType.STORAGE || stack.getItem() == MINING_PIPE_THIN.asItem();
    }

    @Override
    public void onMiningPipeUpdate(BlockPos miningPipePos) {
        BlockState pipe = level.getBlockState(miningPipePos);
        if (pipe.getBlock() != MINING_PIPE && pipe.getBlock() != MINING_PIPE_THIN && !pullingUp){
            resetMiningPos();
        }
    }

    protected void resetMiningPos(){
        foundBottom = false;
        BlockPos centerPos = miningPos.atY(this.getBlockPos().getY()).below();
        while (true){
            BlockState state = level.getBlockState(centerPos);
            if (state.getBlock() == MINING_PIPE || state.getBlock() == MINING_PIPE_THIN){
                centerPos = centerPos.below();
                continue;
            }
            break;
        }
        miningPos = centerPos;
        MiningPipeStructureCache.remove(level, this.getBlockPos());
    }

    public enum MineResult {
        FOUND_BOTTOM, FOUND_OBSTRUCTION, FOUND_MINING_PIPE, FOUND_BOTTOM_MINING_PIPE, FOUND_MINEABLE, PIPE_BROKEN
    }
}
