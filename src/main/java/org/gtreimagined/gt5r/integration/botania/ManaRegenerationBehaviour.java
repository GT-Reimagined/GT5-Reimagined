package org.gtreimagined.gt5r.integration.botania;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.gtreimagined.gtlib.behaviour.IInventoryTick;
import org.gtreimagined.gtlib.tool.IBasicGTTool;
import vazkii.botania.api.mana.ManaItemHandler;

public class ManaRegenerationBehaviour implements IInventoryTick<IBasicGTTool> {
    public static final ManaRegenerationBehaviour INSTANCE = new ManaRegenerationBehaviour();
    @Override
    public String getId() {
        return "mana_regeneration";
    }

    @Override
    public void inventoryTick(IBasicGTTool instance, ItemStack stack, Level level, Entity entity, int slotID, boolean isSelected) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (stack.getDamageValue() > 0 && ManaItemHandler.instance().requestManaExactForTool(stack, player, 60 * 2, true)) {
                stack.setDamageValue(stack.getDamageValue() - 1);
            }
        }
    }
}
