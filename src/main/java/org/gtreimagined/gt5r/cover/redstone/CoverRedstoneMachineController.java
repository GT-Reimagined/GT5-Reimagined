package org.gtreimagined.gt5r.cover.redstone;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import org.gtreimagined.gt5r.cover.CoverShutter;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.cover.ICover;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.Tier;
import net.minecraft.core.Direction;
import org.gtreimagined.gt5r.cover.base.CoverBasicRedstoneInput;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.jetbrains.annotations.Nullable;


public class CoverRedstoneMachineController extends CoverBasicRedstoneInput {

    public CoverRedstoneMachineController(ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public void onRemove() {
        if (handler.getTile() instanceof BlockEntityMachine<?> machine && machine.isServerSide()){
            if (machine.getLevel().isLoaded(machine.getBlockPos())) {
                if (machine.getMachineState() == MachineState.DISABLED){
                    machine.toggleMachine();
                }
            }
        }
    }

    @Override
    public InteractionResult onInteract(Player player, InteractionHand hand, Direction side, @Nullable GTToolType type) {
        InteractionResult interactionResult = super.onInteract(player, hand, side, type);
        if (interactionResult == InteractionResult.SUCCESS){
            for (ICover iCover : handler.getAll()) {
                if (iCover instanceof CoverShutter shutter){
                    shutter.onBlockUpdateAllSides();
                }
            }
        }
        return interactionResult;
    }

    @Override
    public void onTickPost() {
        if (handler.getTile() instanceof BlockEntityMachine<?> machine){
            if (machine.getMachineState() != MachineState.DISABLED){
                if (!isPowered()){
                    machine.toggleMachine();
                }
            } else {
                if (isPowered()){
                    machine.toggleMachine();
                }
            }

        }
    }
}
