package muramasa.gregtech.items;

import muramasa.antimatter.Ref;
import muramasa.antimatter.blockentity.pipe.BlockEntityPipe;
import muramasa.antimatter.item.ICustomDurability;
import muramasa.antimatter.item.ItemBasic;
import muramasa.antimatter.pipe.types.FluidPipe;
import muramasa.antimatter.pipe.types.ItemPipe;
import muramasa.antimatter.util.Utils;
import muramasa.gregtech.GTIRef;
import muramasa.gregtech.data.GregTechData;
import muramasa.gregtech.data.GregTechItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.gregtechintergalactical.gtcore.data.GTCoreItems.LighterEmpty;
import static muramasa.gregtech.data.GregTechItems.EmptySprayCan;

public class ItemSprayCan extends ItemBasic<ItemSprayCan> implements ICustomDurability {
    private final DyeColor color;

    public ItemSprayCan(DyeColor color) {
        super(GTIRef.ID, color.getName() + "_spray_can", "spray_cans/", new Properties().tab(Ref.TAB_ITEMS).defaultDurability(512));
        this.color = color;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockEntity be = context.getLevel().getBlockEntity(context.getClickedPos());
        if (!context.getLevel().isClientSide() && be instanceof BlockEntityPipe<?> pipe && (pipe.getPipeType() instanceof FluidPipe || pipe.getPipeType() instanceof ItemPipe)){
            int rgb = GregTechData.getColorFromDyeColor(color);
            if (pipe.getPipeColor() != rgb){
                pipe.setPipeColor(rgb);
                pipe.checkConnections();
                pipe.sidedSync(true);
                Utils.damageStack(1, context.getItemInHand(), context.getPlayer());
                context.getItemInHand().hurtAndBreak(1, context.getPlayer(), p -> {
                    p.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                    if (!p.addItem(new ItemStack(EmptySprayCan))) p.drop(new ItemStack(EmptySprayCan), true);
                });
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        tooltipComponents.add(Utils.translatable("tooltip." + getDomain() + "." + "spray_can" + (stack.isDamaged() ? ".used" : ".full")));
        tooltipComponents.add(Utils.translatable("tooltip." + getDomain() + "." + "spray_can.0", Utils.literal(Utils.lowerUnderscoreToUpperSpaced(color.getName()))));
        tooltipComponents.add(Utils.translatable("tooltip." + getDomain() + "." + "spray_can.1", stack.getMaxDamage() - stack.getDamageValue()));
    }
}
