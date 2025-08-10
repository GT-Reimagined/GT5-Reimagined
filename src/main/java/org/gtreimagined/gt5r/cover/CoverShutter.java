package org.gtreimagined.gt5r.cover;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import org.gtreimagined.gtlib.blockentity.pipe.BlockEntityPipe;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.BaseCover;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CoverShutter extends BaseCover implements ICoverRedstoneSensitive {
    Mode mode = Mode.OPEN_REDSTONE;
    boolean isPowered = false;

    boolean checkConnection = false;
    public CoverShutter(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public boolean canPlace() {
        return handler.getTile() instanceof BlockEntityPipe<?>;
    }

    @Override
    public void onPlace() {
        super.onPlace();
        checkConnection = true;
    }

    /*@Override
    public <T> boolean blocksCapability(Class<T> cap, @Nullable Direction side) {
        if (side == null || this.handler.getTile().getLevel().isClientSide()) return false;
        return (mode == Mode.OPEN_NO_REDSTONE && isPowered) || (mode == Mode.OPEN_REDSTONE && !isPowered);
    }*/

    @Override
    public <T> boolean blocksInput(Class<T> cap, @Nullable Direction side) {
        return mode == Mode.OUTPUT_ONLY;
    }

    @Override
    public <T> boolean blocksOutput(Class<T> cap, @Nullable Direction side) {
        return mode == Mode.INPUT_ONLY;
    }

    @Override
    public InteractionResult onInteract(Player player, InteractionHand hand, Direction side, @Nullable GTToolType type) {
        if (type != null && type.getTag() == GTTools.SCREWDRIVER.getTag()){
            mode = player.isShiftKeyDown() ? mode.cycleBackward() : mode.cycleForward();
            this.handler.getTile().setChanged();
            switch (mode){
                case OPEN_NO_REDSTONE -> {
                    player.displayClientMessage(Utils.literal("Open if work disabled"), false);
                }
                case OUTPUT_ONLY -> {
                    player.displayClientMessage(Utils.literal("Output only"), false);
                }
                case INPUT_ONLY -> {
                    player.displayClientMessage(Utils.literal("Input only"), false);
                }
                case OPEN_REDSTONE -> {
                    player.displayClientMessage(Utils.literal("Open if work enabled"), false);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return super.onInteract(player, hand, side, type);
    }

    @Override
    public void onBlockUpdateAllSides() {
        checkConnection = true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (checkConnection){
            isPowered = isPowered(this.side);
            checkConnection = false;
        }
        checkPipeConnection();
    }

    private void checkPipeConnection(){
        BlockEntityPipe<?> pipe = (BlockEntityPipe<?>) handler.getTile();
        if (mode == Mode.OPEN_NO_REDSTONE || mode == Mode.OPEN_REDSTONE){
            boolean remove = (mode == Mode.OPEN_NO_REDSTONE && isPowered) || (mode == Mode.OPEN_REDSTONE && !isPowered);
            if (remove) pipe.clearConnection(this.side);
            else pipe.setConnection(this.side);
        }
    }

    @Override
    protected String getRenderId() {
        return "shutter";
    }

    @Override
    public String getId() {
        return "shutter";
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        if (type.equals("pipe")) return PIPE_COVER_MODEL;
        return getBasicModel();
    }

    @Override
    public List<String> getInfo(boolean simple) {
       List<String> info = new ArrayList<>();
       info.add("Shutter Mode: " + mode);
       return info;
    }

    enum Mode {
        INPUT_ONLY, OUTPUT_ONLY, OPEN_REDSTONE, OPEN_NO_REDSTONE;

        Mode cycleForward(){
            return switch (this){
                case OPEN_REDSTONE -> Mode.OPEN_NO_REDSTONE;
                case OPEN_NO_REDSTONE -> Mode.OUTPUT_ONLY;
                case OUTPUT_ONLY -> Mode.INPUT_ONLY;
                case INPUT_ONLY -> Mode.OPEN_REDSTONE;
            };
        }

        Mode cycleBackward(){
            return switch (this){
                case OPEN_REDSTONE -> Mode.INPUT_ONLY;
                case OPEN_NO_REDSTONE -> Mode.OPEN_REDSTONE;
                case OUTPUT_ONLY -> Mode.OPEN_NO_REDSTONE;
                case INPUT_ONLY -> Mode.OUTPUT_ONLY;
            };
        }
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag nbt = super.serialize();
        nbt.putInt("mode", mode.ordinal());
        return nbt;
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        super.deserialize(nbt);
        mode = Mode.values()[nbt.getInt("mode")];
    }
}
