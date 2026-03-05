package org.gtreimagined.gt5r.mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ExperienceOrb.class)
public abstract class ExperienceOrbMixin {
    @Shadow
    protected abstract int repairPlayerItems(Player player, int repairAmount);

    @Shadow
    public int value;

    @Inject(method = "repairPlayerItems", at = @At("HEAD"), cancellable = true)
    private void gt5r$injectRepairPlayerItems(Player player, int repairAmount, CallbackInfoReturnable<Integer> cir){
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(Enchantments.MENDING, player, ItemStack::isDamaged);
        if (entry != null) {
            ItemStack itemstack = entry.getValue();
            int mendingRepairs = itemstack.getTag() != null ? itemstack.getTag().getInt("mendingRepairs") : 0;
            float reductionRatio = 1f;
            if (mendingRepairs > itemstack.getMaxDamage() / 2) reductionRatio = 0.75f;
            if (mendingRepairs > itemstack.getMaxDamage()) reductionRatio = 0.5f;
            if (mendingRepairs > itemstack.getMaxDamage() * 2) reductionRatio = 0.25f;
            if (mendingRepairs > itemstack.getMaxDamage() * 3) reductionRatio = 0f;
            if (reductionRatio == 0f) cir.setReturnValue(repairAmount);
            float xpRepairRatio = itemstack.getXpRepairRatio() * reductionRatio;
            float exactToRepair = this.value * xpRepairRatio;
            int toRepair = Math.min((int)(exactToRepair), itemstack.getDamageValue());
            if (toRepair == 0) cir.setReturnValue(repairAmount);
            itemstack.setDamageValue(itemstack.getDamageValue() - toRepair);
            itemstack.getOrCreateTag().putInt("mendingRepairs", mendingRepairs + toRepair);
            int j = repairAmount - (int)(exactToRepair / xpRepairRatio);
            cir.setReturnValue(j > 0 ? this.repairPlayerItems(player, j) : 0);
        }
    }
}
