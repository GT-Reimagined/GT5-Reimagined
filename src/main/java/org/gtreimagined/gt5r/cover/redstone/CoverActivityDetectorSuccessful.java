package org.gtreimagined.gt5r.cover.redstone;

import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.capability.IGuiHandler;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.machine.event.IMachineEvent;
import org.gtreimagined.gtlib.machine.event.MachineEvent;
import org.gtreimagined.gtlib.tool.GTToolType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import org.gtreimagined.gt5r.cover.base.CoverBasicRedstoneOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoverActivityDetectorSuccessful extends CoverBasicRedstoneOutput {
    boolean redstoneNextTick = false;

    public CoverActivityDetectorSuccessful(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        if (type.equals("pipe")) return PIPE_COVER_MODEL;
        return getBasicModel();
    }

    @Override
    public InteractionResult onInteract(Player player, InteractionHand hand, Direction side, @Nullable GTToolType type) {
        return InteractionResult.PASS;
    }

    @Override
    public void onUpdate() {
        if (this.redstoneNextTick) {
            this.redstoneNextTick = false;
            setOutputRedstone(15);
            return;
        }
        if (this.getStrongPower() > 0 && !redstoneNextTick){
            setOutputRedstone(0);
        }
    }

    @Override
    public void onMachineEvent(IGuiHandler tile, IMachineEvent event, int... data) {
        if (event == MachineEvent.ITEMS_OUTPUTTED || event == MachineEvent.FLUIDS_OUTPUTTED){
            redstoneNextTick = true;
        }
    }
}
