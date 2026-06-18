package org.gtreimagined.gt5r.items;

import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;
import org.gtreimagined.gt5r.data.GT5RMaterialTags.TurbineRotorData;
import org.gtreimagined.gt5r.data.GT5RMaterialTypes;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtlib.item.ICustomDurability;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialItem;
import org.gtreimagined.gtlib.material.MaterialType;
import org.gtreimagined.gtlib.material.MaterialTypeItem;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemTurbineRotor extends MaterialItem implements ICustomDurability {
    @Getter
    final float efficiency;
    final float speed;
    public ItemTurbineRotor(String domain, MaterialType<?> type, Material material) {
        super(domain, type, material, new Properties().durability(material.has(GT5RMaterialTags.TURBINE_DATA) ? GT5RMaterialTags.TURBINE_DATA.get(material).durability() : 1).setNoRepair());
        if (material.has(GT5RMaterialTags.TURBINE_DATA)){
            TurbineRotorData data = GT5RMaterialTags.TURBINE_DATA.get(material);
            this.efficiency = data.efficiency();
            this.speed = data.speed();
        } else {
            this.efficiency = 0;
            this.speed = 0;
        }
    }

    public int speedMultiplier(){
        if (type == GT5RMaterialTypes.TURBINE_ROTOR) return 2;
        if (type == GT5RMaterialTypes.LARGE_TURBINE_ROTOR) return 3;
        if (type == GT5RMaterialTypes.HUGE_TURBINE_ROTOR) return 4;
        return 1;
    }

    public float getSpeed() {
        return speed * speedMultiplier();
    }

    public Material getRodMaterial(){
        if (type == GT5RMaterialTypes.TURBINE_ROTOR) return Materials.Titanium;
        if (type == GT5RMaterialTypes.LARGE_TURBINE_ROTOR) return Materials.TungstenSteel;
        if (type == GT5RMaterialTypes.HUGE_TURBINE_ROTOR) return Materials.Adamantium;
        return Materials.Magnalium;
    }

    public MaterialTypeItem<?> getBrokenRotor(){
        if (type == GT5RMaterialTypes.TURBINE_ROTOR) return GT5RMaterialTypes.BROKEN_TURBINE_ROTOR;
        if (type == GT5RMaterialTypes.LARGE_TURBINE_ROTOR) return GT5RMaterialTypes.LARGE_BROKEN_TURBINE_ROTOR;
        if (type == GT5RMaterialTypes.HUGE_TURBINE_ROTOR) return GT5RMaterialTypes.HUGE_BROKEN_TURBINE_ROTOR;
        return GT5RMaterialTypes.SMALL_BROKEN_TURBINE_ROTOR;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(Utils.translatable("gtlib.tooltip.durability", Utils.literal((stack.getMaxDamage() - stack.getDamageValue()) + "/" + stack.getMaxDamage()).withStyle(ChatFormatting.GREEN)));
        tooltip.add(Utils.translatable("gt5r.rotor.tooltip.efficiency", Utils.literal("" + getEfficiency()).withStyle(ChatFormatting.BLUE)));
        tooltip.add(Utils.translatable("gt5r.rotor.tooltip.steam_flow", Utils.literal("" + Math.max(Float.MIN_NORMAL, getSpeed() * 1000)).withStyle(ChatFormatting.LIGHT_PURPLE)));
        tooltip.add(Utils.translatable("gt5r.rotor.tooltip.gas_flow", Utils.literal("" + Math.max(Float.MIN_NORMAL, getSpeed() * 50)).withStyle(ChatFormatting.LIGHT_PURPLE)));
    }
}
