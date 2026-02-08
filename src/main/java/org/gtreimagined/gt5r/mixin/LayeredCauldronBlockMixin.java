package org.gtreimagined.gt5r.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import org.gtreimagined.gt5r.block.BlockGTWaterCauldron;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.world.level.block.LayeredCauldronBlock.LEVEL;

@Mixin(LayeredCauldronBlock.class)
public class LayeredCauldronBlockMixin {
    @Inject(method = "lowerFillLevel", at = @At("HEAD"), cancellable = true)
    private static void gt5r$injectLowerFillLevel(BlockState state, Level level, BlockPos pos, CallbackInfo ci){
        if (state.getBlock() instanceof BlockGTWaterCauldron){
            int i = state.getValue(LEVEL) - 1;
            BlockState blockstate = i == 0 ? GT5RBlocks.BRONZE_CAULDRON.defaultBlockState() : state.setValue(LEVEL, i);
            level.setBlockAndUpdate(pos, blockstate);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(blockstate));
            ci.cancel();
        }
    }
}
