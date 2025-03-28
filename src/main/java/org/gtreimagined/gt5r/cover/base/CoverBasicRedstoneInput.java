package org.gtreimagined.gt5r.cover.base;

import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.BaseCover;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.tool.AntimatterToolType;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class CoverBasicRedstoneInput extends BaseCover {
    protected int redstonePower;
    boolean inverted = false;

    public CoverBasicRedstoneInput(ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    public boolean isPowered(){
        return inverted ? redstonePower == 0 : redstonePower > 0;
    }

    public int getRedstonePower() {
        return redstonePower;
    }

    @Override
    public void onBlockUpdate() {
        redstonePower = handler.getTile().getLevel().getSignal(handler.getTile().getBlockPos().relative(side), side);
    }

    @Override
    public void onFirstTick() {
        onBlockUpdate();
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        if (type.equals("pipe")) return PIPE_COVER_MODEL;
        return getBasicModel();
    }

    @Override
    public InteractionResult onInteract(Player player, InteractionHand hand, Direction side, @Nullable AntimatterToolType type) {
        if (type != null && type.getTag() == GTTools.SCREWDRIVER.getTag()){
            inverted = !inverted;
            player.sendMessage(Utils.translatable("message.gt5r.redstone_mode." + (inverted ? "inverted" : "normal")), player.getUUID());
            for (Direction dir : Direction.values()) {
                if (dir == this.side) continue;
                source().get(dir).onBlockUpdate();
            }
            return InteractionResult.SUCCESS;
        }
        return super.onInteract(player, hand, side, type);
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag nbt =  super.serialize();
        nbt.putBoolean("inverted", inverted);
        return nbt;
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        super.deserialize(nbt);
        inverted = nbt.getBoolean("inverted");
    }

    @Override
    public List<String> getInfo(boolean simple) {
        List<String> info = new ArrayList<>();
        info.add("Inverted: " + inverted);
        return info;
    }
}
