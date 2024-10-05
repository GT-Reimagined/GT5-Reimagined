package muramasa.gregtech.blockentity.single;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import muramasa.antimatter.Ref;
import muramasa.antimatter.blockentity.BlockEntityCache;
import muramasa.antimatter.capability.IFilterableHandler;
import muramasa.antimatter.capability.machine.MachineEnergyHandler;
import muramasa.antimatter.gui.GuiInstance;
import muramasa.antimatter.gui.IGuiElement;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.gui.event.GuiEvents;
import muramasa.antimatter.gui.event.IGuiEvent;
import muramasa.antimatter.machine.event.IMachineEvent;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.tool.AntimatterToolType;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import muramasa.antimatter.util.Utils;
import muramasa.gregtech.data.GT5RItems;
import muramasa.gregtech.gui.ButtonOverlays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import tesseract.TesseractCapUtils;
import tesseract.api.item.ExtendedItemContainer;

import java.util.List;
import java.util.Objects;

import static muramasa.antimatter.machine.MachineFlag.EU;

public class BlockEntityItemFilter extends BlockEntityLimitedOutput<BlockEntityItemFilter> implements IFilterableHandler {
    boolean blacklist = false;
    boolean nbt = true;
    boolean outputRedstone = false;
    boolean invertRedstone = false;
    boolean emitEnergy = false;
    public BlockEntityItemFilter(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        if (type.has(EU)) {
            energyHandler.set(() -> new MachineEnergyHandler<BlockEntityItemFilter>(this, 0L, this.getMachineTier().getVoltage() * 66L, this.getMachineTier().getVoltage(), this.getMachineTier().getVoltage(), 1, 1){
                @Override
                public boolean canOutput(Direction direction) {
                    return super.canOutput(direction) && direction == tile.getFacing().getOpposite() && tile.emitEnergy;
                }
            });
        }
    }

    @Override
    public InteractionResult onInteractServer(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, @Nullable AntimatterToolType type) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == GT5RItems.DataStick){
            if (stack.getTagElement("displaySlots") == null){
                CompoundTag displaySlots = stack.getOrCreateTagElement("displaySlots");
                this.itemHandler.ifPresent(i -> {
                    i.getHandler(SlotType.DISPLAY_SETTABLE).serialize(displaySlots);
                });
                displaySlots.putString("machineType", this.getMachineType().getLoc().toString());
                level.playSound(null, pos, SoundEvents.UI_BUTTON_CLICK, SoundSource.BLOCKS, 10.f, 1.0f);
                return InteractionResult.SUCCESS;
            } else {
                CompoundTag displaySlots = stack.getTagElement("displaySlots");
                if (!displaySlots.isEmpty() && displaySlots.getString("machineType").equals(this.getMachineType().getLoc().toString())){
                    this.itemHandler.ifPresent(i -> i.getHandler(SlotType.DISPLAY_SETTABLE).deserialize(displaySlots));
                    level.playSound(null, pos, SoundEvents.UI_BUTTON_CLICK, SoundSource.BLOCKS, 10.f, 1.0f);
                    return InteractionResult.SUCCESS;
                }

            }
        }
        return super.onInteractServer(state, world, pos, player, hand, hit, type);
    }

    @Override
    public boolean test(SlotType<?> slotType, int slot, ItemStack stack) {
        if (slotType == SlotType.STORAGE){
            boolean hasItem = itemHandler.map(h -> {
                List<Item> list = new ObjectArrayList<>();
                ExtendedItemContainer outputs = h.getHandler(SlotType.DISPLAY_SETTABLE);
                for (int i = 0; i < outputs.getContainerSize(); i++) {
                    ItemStack slotStack = outputs.getItem(i);
                    if (!slotStack.isEmpty()) {
                        if (slotStack.getItem() == stack.getItem()){
                            if (!nbt || Objects.equals(slotStack.getTag(), stack.getTag())) {
                                list.add(slotStack.copy().getItem());
                            }
                        }
                    }
                }
                return list.isEmpty() == blacklist;
            }).orElse(false);
            return hasItem;
        }
        return true;
    }

    @Override
    public void onGuiEvent(IGuiEvent event, Player playerEntity) {
        if (event.getFactory() == GuiEvents.EXTRA_BUTTON) {
            int[] data = ((GuiEvents.GuiEvent)event).data;
            switch (data[1]) {
                case 0:
                    emitEnergy = !emitEnergy;
                    playerEntity.sendMessage(Utils.literal( (emitEnergy ? "Emit energy to output side" : "Don't emit energy")), playerEntity.getUUID());
                    AntimatterPlatformUtils.markAndNotifyBlock(level, this.getBlockPos(), this.level.getChunkAt(this.getBlockPos()), this.getBlockState(), this.getBlockState(), 1, 512);
                    break;
                case 1:
                    outputRedstone = !outputRedstone;
                    playerEntity.sendMessage(Utils.literal( (outputRedstone ? "Emit redstone if slots contain something" : "Don't emit redstone")), playerEntity.getUUID());
                    AntimatterPlatformUtils.markAndNotifyBlock(level, this.getBlockPos(), this.level.getChunkAt(this.getBlockPos()), this.getBlockState(), this.getBlockState(), 1, 512);
                    break;
                case 2:
                    invertRedstone = !invertRedstone;
                    playerEntity.sendMessage(Utils.literal( (invertRedstone ? "I" : "Don't i") + "nvert redstone"), playerEntity.getUUID());
                    AntimatterPlatformUtils.markAndNotifyBlock(level, this.getBlockPos(), this.level.getChunkAt(this.getBlockPos()), this.getBlockState(), this.getBlockState(), 1, 512);
                    break;
                case 3:
                    blacklist = !blacklist;
                    playerEntity.sendMessage(Utils.literal( (blacklist ? "I" : "Don't i") + "nvert filter"), playerEntity.getUUID());
                    break;
                case 4:
                    nbt = !nbt;
                    playerEntity.sendMessage(Utils.literal( (nbt ? "NBT has to match" : "Ignore NBT")), playerEntity.getUUID());
                    break;
            }
        }
    }

    @Override
    public void addWidgets(GuiInstance instance, IGuiElement parent) {
        super.addWidgets(instance, parent);
        instance.addSwitchButton(8, 63, 16, 16, ButtonOverlays.ENERGY_OFF, ButtonOverlays.ENERGY_ON, h -> ((BlockEntityItemFilter)h).emitEnergy, true);
        instance.addSwitchButton(26, 63, 16, 16, ButtonOverlays.REDSTONE_CONTROL_OFF, ButtonOverlays.REDSTONE_CONTROL_ON, h -> ((BlockEntityItemFilter)h).outputRedstone, true);
        instance.addSwitchButton(44, 63, 16, 16, ButtonOverlays.INVERT_REDSTONE_OFF, ButtonOverlays.INVERT_REDSTONE_ON, h -> ((BlockEntityItemFilter)h).invertRedstone, true);
        instance.addSwitchButton(62, 63, 16, 16, ButtonOverlays.BLACKLIST_OFF, ButtonOverlays.BLACKLIST_ON, h -> ((BlockEntityItemFilter)h).blacklist, true);
        instance.addSwitchButton(80, 63, 16, 16, ButtonOverlays.NBT_OFF, ButtonOverlays.NBT_ON, h -> ((BlockEntityItemFilter)h).nbt, true);
    }
    

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
        if (getCover(this.getFacing().getOpposite()).isEmpty()){
            this.processItemOutput();
        }
    }
    @Override
    public void onMachineEvent(IMachineEvent event, Object... data) {
        super.onMachineEvent(event, data);
        if ((event == SlotType.IT_OUT || event == SlotType.IT_IN) && outputRedstone && !this.getLevel().isClientSide()){
         //   level.updateNeighborsAt(this.getBlockPos(), this.getBlockState().getBlock());
            AntimatterPlatformUtils.markAndNotifyBlock(level, this.getBlockPos(), this.level.getChunkAt(this.getBlockPos()), this.getBlockState(), this.getBlockState(), 1, 512);
        }
    }

    protected boolean processItemOutput() {
        Direction outputDir = this.getFacing().getOpposite();
        BlockEntity adjTile = BlockEntityCache.getBlockEntity(this.getLevel(), this.getBlockPos().relative(outputDir));
        if (adjTile == null) return false;
        boolean[] booleans = new boolean[1];
        booleans[0] = false;
        TesseractCapUtils.INSTANCE.getItemHandler(adjTile, outputDir.getOpposite()).ifPresent(adjHandler -> {
            booleans[0] = this.itemHandler.map(h -> transferItems(h.getHandler(SlotType.STORAGE), adjHandler,true)).orElse(false);
        });
        return booleans[0];
    }

    @Override
    public int getWeakRedstonePower(Direction facing) {
        if (outputRedstone){
            int[] redstone = new int[1];
            redstone[0] = this.itemHandler.map(i -> {
                for (int slot = 0; slot < i.getHandler(SlotType.STORAGE).getSlots(); slot++){
                    ItemStack stack = i.getHandler(SlotType.STORAGE).getStackInSlot(slot);
                    if (!stack.isEmpty()) return invertRedstone ? 0 : 15;
                }
                return invertRedstone ? 15 : 0;
            }).orElse(0);
            if (redstone[0] > 0){
                return redstone[0];
            }
        }
        return super.getWeakRedstonePower(facing);
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains(Ref.KEY_MACHINE_ITEMS)){
            CompoundTag tag1 = tag.getCompound(Ref.KEY_MACHINE_ITEMS);
            if (tag1.contains("filterable")){
                CompoundTag filterable = tag1.getCompound("filterable");
                tag1.put(SlotType.STORAGE.getId(), filterable);
            }
        }
        super.load(tag);
        blacklist = tag.getBoolean("blacklist");
        nbt = tag.getBoolean("nbt");
        outputRedstone = tag.getBoolean("outputRedstone");
        invertRedstone = tag.getBoolean("invertRedstone");
        emitEnergy = tag.getBoolean("emitEnergy");
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("blacklist", blacklist);
        tag.putBoolean("nbt", nbt);
        tag.putBoolean("outputRedstone", outputRedstone);
        tag.putBoolean("invertRedstone", invertRedstone);
        tag.putBoolean("emitEnergy", emitEnergy);
    }


    public boolean isBlacklist() {
        return blacklist;
    }

    public boolean isEmitEnergy() {
        return emitEnergy;
    }

    public boolean isInvertRedstone() {
        return invertRedstone;
    }

    public boolean isNbt() {
        return nbt;
    }

    public boolean isOutputRedstone() {
        return outputRedstone;
    }
}