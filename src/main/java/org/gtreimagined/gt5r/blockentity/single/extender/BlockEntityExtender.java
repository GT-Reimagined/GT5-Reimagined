package org.gtreimagined.gt5r.blockentity.single.extender;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.gtreimagined.gt5r.blockentity.single.bridge.BlockEntityBridge;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BlockEntityExtender extends BlockEntityMachine<BlockEntityExtender> {
    public BlockEntityExtender(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public boolean wrenchMachine(Player player, BlockHitResult res, boolean crouch) {
        return false;
    }

    @Override
    public InteractionResult onInteractServer(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, @Nullable GTToolType type) {
        if (type == GTTools.WRENCH_ALT) {
            if (setOutputFacing(player, Utils.getInteractSide(hit))) {
                Utils.damageStack(player.getItemInHand(hand), hand, player);
                return InteractionResult.SUCCESS;
            }
        }
        if (type == GTTools.WRENCH){
            if (setFacing(player, Utils.getInteractSide(hit))) {
                Utils.damageStack(player.getItemInHand(hand), hand, player);
                return InteractionResult.SUCCESS;
            }
        }
        return super.onInteractServer(state, world, pos, player, hand, hit, type);
    }

    private boolean isNotExtendingBlockEntity(BlockEntity entity) {
        return !(entity instanceof BlockEntityBridge) && !(entity instanceof BlockEntityExtender);
    }

    protected abstract <U> boolean canExtendCapability(Capability<U> capability);

    @Override
    public @NotNull <U> LazyOptional<U> getCapability(@NotNull Capability<U> cap, @Nullable Direction side) {
        if (canExtendCapability(cap) && side != null) {
            Direction output;
            if (side == this.getFacing()){
                output = this.getOutputFacing();
            } else output = this.getFacing();
            BlockEntity neighbor = this.getCachedBlockEntity(output);
            if (neighbor != null && isNotExtendingBlockEntity(neighbor)) {
                return neighbor.getCapability(cap, output.getOpposite());
            }
        }
        return super.getCapability(cap, side);
    }
}
