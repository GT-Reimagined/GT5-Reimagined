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
    protected abstract int durabilityToXp(int durability);

    @Shadow
    public int value;

    @Inject(method = "repairPlayerItems", at = @At("HEAD"), cancellable = true)
    private void gt5r$injectRepairPlayerItems(Player player, int repairAmount, CallbackInfoReturnable<Integer> cir){
        /*Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(Enchantments.MENDING, player, ItemStack::isDamaged);
        if (entry != null) {
            ItemStack itemstack = entry.getValue();
            int mendingRepairs = itemstack.getTag() != null ? itemstack.getTag().getInt("mendingRepairs") : 0;
            float reductionRatio = 1f;
            if (mendingRepairs > 50) reductionRatio = 0.75f;
            if (mendingRepairs > 100) reductionRatio = 0.5f;
            if (mendingRepairs > 150) reductionRatio = 0.25f;
            if (mendingRepairs > 200) reductionRatio = 0f;
            float xpRepairRatio = itemstack.getXpRepairRatio() * reductionRatio;
            int toRepair = Math.min((int)((float)this.value * xpRepairRatio), itemstack.getDamageValue());
            GT5Reimagined.LOGGER.info("repair amount: " + toRepair);
            itemstack.setDamageValue(itemstack.getDamageValue() - toRepair);
            itemstack.getOrCreateTag().putInt("mendingRepairs", mendingRepairs + toRepair);
            int j = repairAmount - this.durabilityToXp(toRepair);
            cir.setReturnValue(j > 0 ? this.repairPlayerItems(player, j) : 0);
        }*/
    }
}
