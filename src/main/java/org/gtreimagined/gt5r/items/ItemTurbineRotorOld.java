package org.gtreimagined.gt5r.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.tool.GTItemTier;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.gtreimagined.gtlib.tool.MaterialTool;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gt5r.data.ToolTypes;

import java.util.List;

public class ItemTurbineRotorOld extends MaterialTool {
    public ItemTurbineRotorOld(String domain, GTToolType type, GTItemTier tier, Properties properties) {
        super(domain, type, tier, properties);
    }

    public float getEfficiency(){
        return 60.0F + (10.0F * (type.getBaseAttackDamage() + itemTier.getAttackDamageBonus()));
    }

    public int speedMultiplier(){
        return 1;
    }

    public float getSpeed(){
        return itemTier.getSpeed() * speedMultiplier();
    }

    public Material getRodMaterial(){
        return Materials.Magnalium;
    }

    @Override
    public void onGenericAddInformation(ItemStack stack, List<Component> tooltip, TooltipFlag flag) {
        super.onGenericAddInformation(stack, tooltip, flag);
        tooltip.add(Utils.translatable("gt5r.rotor.tooltip.efficiency", Utils.literal("" + getEfficiency()).withStyle(ChatFormatting.BLUE)));
        tooltip.add(Utils.translatable("gt5r.rotor.tooltip.steam_flow", Utils.literal("" + Math.max(Float.MIN_NORMAL, getSpeed() * 1000)).withStyle(ChatFormatting.LIGHT_PURPLE)));
        tooltip.add(Utils.translatable("gt5r.rotor.tooltip.gas_flow", Utils.literal("" + Math.max(Float.MIN_NORMAL, getSpeed() * 50)).withStyle(ChatFormatting.LIGHT_PURPLE)));
    }

    @Override
    public ItemStack resolveStack(Material primary, Material secondary, long startingEnergy, long maxEnergy) {
        return new ItemStack(this);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slotType, ItemStack stack) {
        return HashMultimap.create();
    }
}
