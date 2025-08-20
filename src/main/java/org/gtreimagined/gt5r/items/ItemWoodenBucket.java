package org.gtreimagined.gt5r.items;

import lombok.Getter;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;
import org.gtreimagined.gtlib.registration.ITextureProvider;
import org.gtreimagined.gtlib.texture.Texture;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ItemWoodenBucket extends BucketItem implements IGTObject, ITextureProvider, IModelProvider {
    @Getter
    private final String id;

    public ItemWoodenBucket(Supplier<? extends Fluid> supplier, String id) {
        super(supplier, new Item.Properties().tab(Ref.TAB_ITEMS).stacksTo(1));
        this.id = id;
        GTAPI.register(ItemWoodenBucket.class, this);
    }

    @Override
    public String getDomain() {
        return GT5Reimagined.ID;
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(getDomain(), "item/basic/" + getId())};
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
       return getFluid() == Fluids.EMPTY ? 16 : 1;
    }

    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidBucketWrapper(stack){
            @Override
            protected void setFluid(@NotNull FluidStack fluidStack) {
                if (fluidStack.isEmpty()) {
                    this.container = new ItemStack(GT5RItems.WOODEN_BUCKET);
                } else {
                    Item found = findFilledBucket(fluidStack.getFluid());
                    if (found != Items.AIR) {
                        this.container = new ItemStack(found);
                    }
                }
            }

            @Override
            public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
               return findFilledBucket(stack.getFluid()) != Items.AIR;
            }

            @Override
            public int fill(FluidStack resource, FluidAction action) {
                if (!isFluidValid(0, resource)) return 0;
                return super.fill(resource, action);
            }
        };

    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, this.getFluid() == Fluids.EMPTY ? net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY : net.minecraft.world.level.ClipContext.Fluid.NONE);
        InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onBucketUse(player, level, itemstack, blockhitresult);
        if (ret != null) {
            return ret;
        } else if (blockhitresult.getType() == Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else if (blockhitresult.getType() != Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            BlockPos blockpos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (level.mayInteract(player, blockpos) && player.mayUseItemAt(blockpos1, direction, itemstack)) {
                if (this.getFluid() == Fluids.EMPTY) {
                    BlockState blockstate1 = level.getBlockState(blockpos);
                    if (blockstate1.getBlock() instanceof BucketPickup bucketpickup) {
                        ItemStack forgeBucket = bucketpickup.pickupBlock(level, blockpos, blockstate1);
                        if (!forgeBucket.isEmpty() && forgeBucket.getItem() instanceof BucketItem bucketItem) {
                            Item bucket = findFilledBucket(bucketItem.getFluid());
                            if (bucket != Items.AIR) {
                                ItemStack bucketStack = new ItemStack(bucket);
                                player.awardStat(Stats.ITEM_USED.get(this));
                                bucketpickup.getPickupSound(blockstate1).ifPresent((p_150709_) -> player.playSound(p_150709_, 1.0F, 1.0F));
                                level.gameEvent(player, GameEvent.FLUID_PICKUP, blockpos);
                                ItemStack itemstack2 = ItemUtils.createFilledResult(itemstack, player, bucketStack);
                                if (!level.isClientSide) {
                                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, bucketStack);
                                }

                                return InteractionResultHolder.sidedSuccess(itemstack2, level.isClientSide());
                            }
                        }
                    }

                    return InteractionResultHolder.fail(itemstack);
                } else {
                    BlockState blockstate = level.getBlockState(blockpos);
                    BlockPos blockpos2 = this.canBlockContainFluid(level, blockpos, blockstate) ? blockpos : blockpos1;
                    if (this.emptyContents(player, level, blockpos2, blockhitresult)) {
                        this.checkExtraContent(player, level, itemstack, blockpos2);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos2, itemstack);
                        }

                        player.awardStat(Stats.ITEM_USED.get(this));
                        return InteractionResultHolder.sidedSuccess(getEmptyBucket(itemstack, player), level.isClientSide());
                    } else {
                        return InteractionResultHolder.fail(itemstack);
                    }
                }
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
    }

    private boolean canBlockContainFluid(Level worldIn, BlockPos posIn, BlockState blockstate) {
        return blockstate.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer)blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, this.getFluid());
    }


    private static ItemStack getEmptyBucket(ItemStack bucketStack, Player player) {
        return !player.getAbilities().instabuild ? new ItemStack(GT5RItems.WOODEN_BUCKET) : bucketStack;
    }

    private static Item findFilledBucket(Fluid fluid){
        return GTAPI.all(ItemWoodenBucket.class).stream().filter(w -> w.getFluid() == fluid).findFirst().map(Item::asItem).orElse(Items.AIR);
    }

    @Override
    public void onItemModelBuild(ItemLike item, GTItemModelProvider prov) {
        prov.getGTBuilder(item).bucketProperties(this.getFluid(), true, false).parent(new ResourceLocation(Ref.ID + ":item/bucket")).tex((map) -> {
            String id = "wooden_bucket";
            map.put("base", getDomain() + ":item/basic/" + id);
            map.put("cover", getDomain() + ":item/other/" + id + "_cover");
            map.put("fluid", getDomain() + ":item/other/" + id + "_fluid");
        });
    }
}
