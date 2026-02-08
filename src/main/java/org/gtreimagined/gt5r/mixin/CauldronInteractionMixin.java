package org.gtreimagined.gt5r.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(CauldronInteraction.class)
public interface CauldronInteractionMixin {
    @Inject(method = {"lambda$bootStrap$8", "m_175731_"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"), cancellable = true)
    private static void gt5r$injectEmptyBottle(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack, CallbackInfoReturnable<InteractionResult> cir){
        if (state.is(GT5RBlocks.BRONZE_CAULDRON)){
            Item item = stack.getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
            player.awardStat(Stats.FILL_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(item));
            level.setBlockAndUpdate(pos, GT5RBlocks.BRONZE_WATER_CAULDRON.defaultBlockState());
            level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
            cir.setReturnValue(InteractionResult.SUCCESS);
        }

    }

    @Inject(method = "fillBucket", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"), cancellable = true)
    private static void gt5r$injectFillBucket(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack emptyStack, ItemStack filledStack, Predicate<BlockState> statePredicate, SoundEvent fillSound, CallbackInfoReturnable<InteractionResult> cir){
        if (blockState.is(GT5RBlocks.BRONZE_WATER_CAULDRON)){
            Item item = emptyStack.getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(emptyStack, player, filledStack));
            player.awardStat(Stats.USE_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(item));
            level.setBlockAndUpdate(pos, GT5RBlocks.BRONZE_CAULDRON.defaultBlockState());
            level.playSound(null, pos, fillSound, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
            cir.setReturnValue(InteractionResult.SUCCESS);
        }

    }

    @Inject(method = "emptyBucket", at = @At("HEAD"), cancellable = true)
    private static void gt5r$injectEmptyBucket(Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack filledStack, BlockState state, SoundEvent emptySound, CallbackInfoReturnable<InteractionResult> cir){
        if (!level.isClientSide) {
            BlockState cauldron = level.getBlockState(pos);
            if (cauldron.is(GT5RBlocks.BRONZE_CAULDRON)){
                if (state.is(Blocks.WATER_CAULDRON)){
                    state = GT5RBlocks.BRONZE_WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, LayeredCauldronBlock.MAX_FILL_LEVEL);
                    Item item = filledStack.getItem();
                    player.setItemInHand(hand, ItemUtils.createFilledResult(filledStack, player, new ItemStack(Items.BUCKET)));
                    player.awardStat(Stats.FILL_CAULDRON);
                    player.awardStat(Stats.ITEM_USED.get(item));
                    level.setBlockAndUpdate(pos, state);
                    level.playSound(null, pos, emptySound, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                } else {
                    cir.setReturnValue(InteractionResult.PASS);
                }
            }
        }
    }
}
