package org.gtreimagined.gt5r.blockentity.miniportals;

import muramasa.antimatter.machine.MachineState;
import muramasa.antimatter.machine.types.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.loader.WorldGenLoader;

import java.util.ArrayList;
import java.util.List;

public class BlockEntityMiniJAMDPortal extends BlockEntityMiniPortal{
    public static List<BlockEntityMiniPortal> sListJAMDSide = new ArrayList<>();
    public BlockEntityMiniJAMDPortal(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected boolean isPortalSetter(ItemStack stack) {
        return stack.getItem() == Items.FLINT_AND_STEEL;
    }

    @Override
    protected void playActivationSound(Player player) {

    }

    @Override
    public List<BlockEntityMiniPortal> getPortalListA() {
        return sListWorldSide;
    }

    @Override
    public List<BlockEntityMiniPortal> getPortalListB() {
        return sListJAMDSide;
    }

    @Override
    public void addThisPortalToLists() {
        if (level.dimension() == Level.OVERWORLD) {
            if (!sListWorldSide.contains(this)) sListWorldSide.add(this);
        } else if (level.dimension() == WorldGenLoader.JAMD_MINING) {
            if (!sListJAMDSide.contains(this)) sListJAMDSide.add(this);
        }
    }

    @Override
    protected void findTargetPortal() {
        otherSide = null;
        if (level != null && isServerSide()) {
            if (level.dimension() == Level.OVERWORLD) {
                long tShortestDistance = 128*128;
                for (BlockEntityMiniPortal tTarget : sListJAMDSide) if (tTarget != this && !tTarget.isRemoved() && tTarget.isSame(this)) {
                    long tXDifference = getBlockPos().getX()-tTarget.getBlockPos().getX(), tZDifference = getBlockPos().getZ()-tTarget.getBlockPos().getZ();
                    long tTempDist = tXDifference * tXDifference + tZDifference * tZDifference;
                    if (tTempDist < tShortestDistance) {
                        tShortestDistance = tTempDist;
                        otherSide = tTarget;
                    } else if (tTempDist == tShortestDistance && (otherSide == null || Math.abs(tTarget.getBlockPos().getY()-getBlockPos().getY()) < Math.abs(otherSide.getBlockPos().getY()-getBlockPos().getY()))) {
                        otherSide = tTarget;
                    }
                }
            } else if (level.dimension() == WorldGenLoader.JAMD_MINING) {
                long tShortestDistance = 128*128;
                for (BlockEntityMiniPortal tTarget : sListWorldSide) if (tTarget != this && !tTarget.isRemoved() && tTarget.isSame(this)) {
                    long tXDifference = tTarget.getBlockPos().getX()-getBlockPos().getX(), tZDifference = tTarget.getBlockPos().getZ()-getBlockPos().getZ();
                    long tTempDist = tXDifference * tXDifference + tZDifference * tZDifference;
                    if (tTempDist < tShortestDistance) {
                        tShortestDistance = tTempDist;
                        otherSide = tTarget;
                    } else if (tTempDist == tShortestDistance && (otherSide == null || Math.abs(tTarget.getBlockPos().getY()-getBlockPos().getY()) < Math.abs(otherSide.getBlockPos().getY()-getBlockPos().getY()))) {
                        otherSide = tTarget;
                    }
                }
            }
            if (otherSide != null){
                otherSide.setOtherSide(this);
                if (otherSide.getMachineState() != MachineState.ACTIVE){
                    otherSide.setMachineState(MachineState.ACTIVE);
                }
            }
        }
    }
}
