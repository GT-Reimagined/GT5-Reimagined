package org.gtreimagined.gt5r.integration.tfc.data;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gtlib.behaviour.IBlockDestroyed;
import org.gtreimagined.gtlib.tool.IBasicGTTool;
import org.gtreimagined.gtlib.tool.IGTTool;

public class ScythHarvestBehaviour implements IBlockDestroyed<IBasicGTTool> {
    public static final ScythHarvestBehaviour INSTANCE = new ScythHarvestBehaviour();
    @Override
    public boolean onBlockDestroyed(IBasicGTTool instance, ItemStack stack, Level level, BlockState state, BlockPos origin, LivingEntity entity) {
        if (entity instanceof ServerPlayer player) {
            for(BlockPos pos : BlockPos.betweenClosed(origin.offset(-1, -1, -1), origin.offset(1, 1, 1))) {
                BlockState stateAt = level.getBlockState(pos);
                if (!pos.equals(origin) && instance.getItem().isCorrectToolForDrops(stack, stateAt)) {
                    Block.dropResources(stateAt, level, pos, stateAt.hasBlockEntity() ? level.getBlockEntity(pos) : null, player, player.getMainHandItem());
                    level.destroyBlock(pos, false, player);
                }
            }
        }
        return true;
    }
}
