package org.gtreimagined.gt5r.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.item.ItemBasic;

public class ItemGuide extends ItemBasic<ItemGuide> {
    public ItemGuide() {
        super(GT5Reimagined.ID, "guide");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);

        if (level.isClientSide()) {
            openGuide();
        }

        return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
    }

    private static void openGuide() {
        GT5Reimagined.openGuideAtPreviousPage(new ResourceLocation(GT5Reimagined.ID, "index.md"));
    }
}
