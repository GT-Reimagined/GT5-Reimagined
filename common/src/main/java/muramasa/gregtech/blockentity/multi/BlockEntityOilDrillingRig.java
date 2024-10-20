package muramasa.gregtech.blockentity.multi;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import muramasa.antimatter.blockentity.multi.BlockEntityMultiMachine;
import muramasa.antimatter.capability.IFilterableHandler;
import muramasa.antimatter.capability.machine.MultiMachineEnergyHandler;
import muramasa.antimatter.gui.GuiInstance;
import muramasa.antimatter.gui.IGuiElement;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.gui.event.GuiEvents;
import muramasa.antimatter.gui.event.IGuiEvent;
import muramasa.antimatter.gui.widget.InfoRenderWidget;
import muramasa.antimatter.gui.widget.WidgetSupplier;
import muramasa.antimatter.integration.jeirei.renderer.IInfoRenderer;
import muramasa.antimatter.machine.MachineState;
import muramasa.antimatter.machine.event.MachineEvent;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.util.Utils;
import muramasa.antimatter.util.int3;
import muramasa.gregtech.data.GT5RBlocks;
import muramasa.gregtech.gui.ButtonOverlays;
import muramasa.gregtech.worldgen.OilSpoutEntry;
import muramasa.gregtech.worldgen.OilSpoutSavedData;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;
import tesseract.FluidPlatformUtils;
import tesseract.TesseractGraphWrappers;

import java.util.List;

import static muramasa.antimatter.gui.ICanSyncData.SyncDirection.SERVER_TO_CLIENT;
import static muramasa.gregtech.data.GT5RBlocks.MINING_PIPE;
import static muramasa.gregtech.data.GT5RBlocks.MINING_PIPE_THIN;

public class BlockEntityOilDrillingRig extends BlockEntityMultiMachine<BlockEntityOilDrillingRig> implements IFilterableHandler, IMiningPipeTile {
    boolean foundBottom = false;
    boolean stopped = false;
    boolean pullingUp;
    int euPerTick;
    int cycle = 160;
    int progress = 0;
    BlockPos miningPos;
    OilSpoutEntry oilEntry = null;

    public BlockEntityOilDrillingRig(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        miningPos = new int3(pos, this.getFacing(state)).back(1).immutable().below();
    }

    @Override
    public void onFirstTick() {
        super.onFirstTick();
        if (foundBottom){
            LongList positions = new LongArrayList();
            for (int y = miningPos.getY(); y < this.getBlockPos().getY(); y++) {
                positions.add(BlockPos.asLong(miningPos.getX(), y, miningPos.getZ()));
            }
            MiningPipeStructureCache.add(this.level, this.getBlockPos(), positions);
        }
    }

    int outputTicker = 0;

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
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
                    } else if (itemHandler.map(i -> i.getHandler(SlotType.STORAGE).getItem(0).getCount() + 1 < i.getHandler(SlotType.STORAGE).getSlotLimit(0)).orElse(false)){
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
                        if (getMachineState() == MachineState.IDLE) setMachineState(MachineState.ACTIVE);
                        energyHandler.ifPresent(e -> e.extractEu(euPerTick, false));
                    } else if (getMachineState() == MachineState.ACTIVE){
                        setMachineState(MachineState.IDLE);
                    }
                } else if (getMachineState() == MachineState.ACTIVE){
                    setMachineState(MachineState.IDLE);
                }
            } else if (!foundBottom){
                if (level.getGameTime() % 20 != 0) return;
                MineResult breakResult = destroyBlock(level, miningPos, true, null, Items.NETHERITE_PICKAXE.getDefaultInstance());

                if (breakResult == MineResult.PIPE_BROKEN){
                    return;
                }


                if (getMachineState() == MachineState.IDLE) setMachineState(MachineState.ACTIVE);
                energyHandler.ifPresent(e -> e.extractEu(euPerTick, false));

                if (breakResult == MineResult.FOUND_BOTTOM){
                    foundBottom = true;
                    LongList positions = new LongArrayList();
                    for (int y = miningPos.getY(); y < this.getBlockPos().getY(); y++) {
                        positions.add(BlockPos.asLong(miningPos.getX(), y, miningPos.getZ()));
                    }
                    MiningPipeStructureCache.add(this.level, this.getBlockPos(), positions);
                    return;
                }
                if (!wasStopped) {
                    miningPos = miningPos.below();
                }


                if (breakResult == MineResult.FOUND_OBSTRUCTION){
                    stopped = true;
                    return;
                }
                if (breakResult == MineResult.FOUND_MINEABLE) {
                    stack.shrink(1);
                }
            } else {
                if (oilEntry == null){
                    oilEntry = OilSpoutSavedData.getOrCreate((ServerLevel) level).getFluidVeinWorldEntry(SectionPos.blockToSectionCoord(this.miningPos.getX()), SectionPos.blockToSectionCoord(this.miningPos.getZ()));
                }
                if (oilEntry.getFluid() == null) return;
                FluidHolder fluidHolder = FluidPlatformUtils.createFluidStack(oilEntry.getFluid().fluid(), oilEntry.getCurrentYield() * TesseractGraphWrappers.dropletMultiplier);
                if (outputTicker > 0){
                    outputTicker--;
                    return;
                }
                if (progress == 0){
                    if (!fluidHandler.map(f -> f.fillOutput(fluidHolder, true) == oilEntry.getCurrentYield() * TesseractGraphWrappers.dropletMultiplier).orElse(false)){
                        outputTicker = 40;
                        this.setMachineState(MachineState.IDLE);
                        return;
                    }
                }

                if (getMachineState() != MachineState.ACTIVE){
                    setMachineState(MachineState.ACTIVE);
                }
                energyHandler.ifPresent(e -> e.extractEu(euPerTick, false));
                if (++progress == cycle){
                    progress = 0;
                    if (fluidHandler.map(f -> f.fillOutput(fluidHolder, true) == oilEntry.getCurrentYield() * TesseractGraphWrappers.dropletMultiplier).orElse(false)){
                        fluidHandler.ifPresent(f -> f.fillOutput(fluidHolder, false));
                        onMachineEvent(MachineEvent.FLUIDS_OUTPUTTED);
                        oilEntry.decreaseLevel();
                    }
                }
            }
        } else {
            if (getMachineState() == MachineState.ACTIVE) setMachineState(MachineState.IDLE);
        }
    }

    public MineResult destroyBlock(Level level, BlockPos pos, boolean dropBlock, @Nullable Entity entity, ItemStack item) {
        BlockState blockstate = level.getBlockState(pos);
        BlockState aboveBlockState = level.getBlockState(pos.above());
        if (aboveBlockState.getBlock() != MINING_PIPE && pos.getY() + 1 != this.getBlockPos().getY()){
            resetMiningPos();
            return MineResult.PIPE_BROKEN;
        }
        if (blockstate.getBlock() == Blocks.BEDROCK || blockstate.getBlock() == Blocks.VOID_AIR){
            return MineResult.FOUND_BOTTOM;
        }
        if (blockstate.getDestroySpeed(level, pos) < 0) {
            return MineResult.FOUND_OBSTRUCTION;
        } else {
            FluidState fluidstate = level.getFluidState(pos);
            if (!(blockstate.getBlock() instanceof BaseFireBlock)) {
                level.levelEvent(2001, pos, Block.getId(blockstate));
            }
            boolean miningPipe = blockstate.getBlock() == MINING_PIPE || blockstate.getBlock() == MINING_PIPE_THIN;

            CompoundTag tag = item.getTag();
            BlockEntity blockentity = blockstate.hasBlockEntity() ? level.getBlockEntity(pos) : null;
            //BlockEve event = new BlockEvent.BreakEvent(level, pos, blockstate, entity instanceof Player player ? player : null);
            //MinecraftForge.EVENT_BUS.post(event);
            /*if (event.isCanceled()){
                return false;
            }*/
            if (dropBlock && !miningPipe) {
                if (level instanceof ServerLevel serverLevel) {
                    List<ItemStack> drops = Block.getDrops(blockstate, serverLevel, pos, blockentity, null, item);
                    if (itemHandler.map(i -> i.canOutputsFit(drops.toArray(ItemStack[]::new))).orElse(false)){
                        itemHandler.ifPresent(i -> i.addOutputs(drops.toArray(ItemStack[]::new)));
                    } else {
                        drops.forEach(i -> Block.popResource(level, pos, i));
                    }
                    blockstate.spawnAfterBreak(serverLevel, pos, ItemStack.EMPTY);
                }
            }

            boolean flag = blockstate.getBlock() == MINING_PIPE || level.setBlock(pos, MINING_PIPE.defaultBlockState(), 3, 512);
            if (flag && pos.getY() + 1 < this.getBlockPos().getY()) {
                level.setBlock(pos.above(), MINING_PIPE_THIN.defaultBlockState(), 11);
                //level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(entity, blockstate));
            }

            return miningPipe ? MineResult.FOUND_MINING_PIPE : MineResult.FOUND_MINEABLE;
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
        tag.putInt("progress", progress);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.foundBottom = nbt.getBoolean("foundBottom");
        this.pullingUp = nbt.getBoolean("pullingUp");
        this.miningPos = BlockPos.of(nbt.getLong("miningPos"));
        this.progress = nbt.getInt("progress");
    }

    @Override
    public void onGuiEvent(IGuiEvent event, Player playerEntity) {
        if (event.getFactory() == GuiEvents.EXTRA_BUTTON) {
            int[] data = ((GuiEvents.GuiEvent)event).data;
            if (data[1] == 0) {
                pullingUp = !pullingUp;
                playerEntity.sendMessage(Utils.literal((pullingUp ? "Currently pulling up mining pipes" : "No longer pulling up mining pipes")), playerEntity.getUUID());
            }
        }
    }

    @Override
    public void addWidgets(GuiInstance instance, IGuiElement parent) {
        super.addWidgets(instance, parent);
        instance.addSwitchButton(152, 23, 18, 18, ButtonOverlays.PULL_UP_OFF, ButtonOverlays.PULL_UP_ON, h -> ((BlockEntityOilDrillingRig)h).pullingUp, false);
    }

    @Override
    public WidgetSupplier getInfoWidget() {
        return OilInfoWidget.build().setPos(10, 10);
    }

    @Override
    public int drawInfo(InfoRenderWidget.MultiRenderWidget instance, PoseStack stack, Font renderer, int left, int top) {
        OilInfoWidget oilInfoWidget = (OilInfoWidget) instance;
        renderer.draw(stack, this.getDisplayName().getString(), left, top, 16448255);
        if (getMachineState() != MachineState.ACTIVE) {
            renderer.draw(stack, "Inactive.", left, top + 8, 16448255);
            return 16;
        } else if (instance.drawActiveInfo()) {
            if (oilInfoWidget.foundBottom){
                renderer.draw(stack, "Progress: " + instance.currentProgress + "/" + instance.maxProgress, left, top + 8, 16448255);
                return 16;
            } else if (oilInfoWidget.stopped && oilInfoWidget.currentPos != null){
                renderer.draw(stack, "Can't mine at: " + oilInfoWidget.currentPos, left, top + 8, 16448255);
                renderer.draw(stack, "Y: " + oilInfoWidget.currentPos.getY(), left, top + 16, 16448255);
                return 24;
            } else if (oilInfoWidget.currentPos != null){
                renderer.draw(stack, "Mining Position at: ", left, top + 8, 16448255);
                renderer.draw(stack, "Y: " + oilInfoWidget.currentPos.getY(), left, top + 16, 16448255);
                return 24;
            }
        }
        return 8;
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

    private void resetMiningPos(){
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

    enum MineResult {
        FOUND_BOTTOM, FOUND_OBSTRUCTION, FOUND_MINING_PIPE, FOUND_MINEABLE, PIPE_BROKEN
    }

    public static class OilInfoWidget extends InfoRenderWidget.MultiRenderWidget {
        BlockPos currentPos;
        boolean stopped;
        boolean foundBottom;


        protected OilInfoWidget(GuiInstance gui, IGuiElement parent, IInfoRenderer<MultiRenderWidget> renderer) {
            super(gui, parent, renderer);
        }

        @Override
        public void init() {
            BlockEntityOilDrillingRig m = (BlockEntityOilDrillingRig) gui.handler;
            gui.syncLong(() -> m.miningPos.asLong(), l -> currentPos = BlockPos.of(l), SERVER_TO_CLIENT);
            gui.syncBoolean(() -> m.stopped, s -> stopped = s, SERVER_TO_CLIENT);
            gui.syncBoolean(() -> m.foundBottom, b -> foundBottom = b, SERVER_TO_CLIENT);
            gui.syncInt(() -> m.progress, i -> currentProgress = i, SERVER_TO_CLIENT);
            gui.syncInt(() -> m.cycle, i -> maxProgress = i, SERVER_TO_CLIENT);
        }

        public static WidgetSupplier build() {
            return builder((a, b) -> new OilInfoWidget(a, b, (IInfoRenderer) a.handler));
        }
    }
}
