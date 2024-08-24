package muramasa.gregtech.items;

import muramasa.antimatter.blockentity.pipe.BlockEntityPipe;
import muramasa.antimatter.item.ItemBasic;
import muramasa.antimatter.pipe.types.FluidPipe;
import muramasa.antimatter.pipe.types.ItemPipe;
import muramasa.gregtech.GTIRef;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ItemSprayCan extends ItemBasic<ItemSprayCan> {
    private final DyeColor color;

    public ItemSprayCan(DyeColor color) {
        super(GTIRef.ID, color.getName() + "_spray_can", "spray_cans/");
        this.color = color;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockEntity be = context.getLevel().getBlockEntity(context.getClickedPos());
        if (!context.getLevel().isClientSide() && be instanceof BlockEntityPipe<?> pipe && (pipe.getPipeType() instanceof FluidPipe || pipe.getPipeType() instanceof ItemPipe)){
            pipe.setPipeColor(color.getTextColor());
            pipe.sidedSync(true);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }
}
