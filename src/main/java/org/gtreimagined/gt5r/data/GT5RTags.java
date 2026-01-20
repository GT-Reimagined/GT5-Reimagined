package org.gtreimagined.gt5r.data;

import net.minecraft.core.registries.Registries;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.util.TagUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class GT5RTags {
    public static final TagKey<Fluid> STEAM =  TagKey.create(Registries.FLUID, new ResourceLocation("forge", "steam"));
    public static final TagKey<Item> GRIND_HEADS = getTag("grind_heads");
    public static final TagKey<Item> GEM_SAPPHIRES = getTag("gems/sapphires");
    public static final TagKey<Item> DUST_SAPPHIRES = getTag("dusts/sapphires");
    public static final TagKey<Item> DUST_SANDS = getTag("dusts/sands");
    public static final TagKey<Item> DUST_SIO = getTag("dusts/silicon_dioxides");
    public static TagKey<Item> RESISTORS = TagUtils.getItemTag(new ResourceLocation(GT5Reimagined.ID, "resistors"));
    public static TagKey<Item> CAPACITORS = TagUtils.getItemTag(new ResourceLocation(GT5Reimagined.ID, "capacitors"));
    public static TagKey<Item> TRANSISTORS = TagUtils.getItemTag(new ResourceLocation(GT5Reimagined.ID, "transistors"));
    public static TagKey<Item> DIODES = TagUtils.getItemTag(new ResourceLocation(GT5Reimagined.ID, "diodes"));
    public static final TagKey<Block> ASPHALT = TagUtils.getBlockTag(new ResourceLocation(GT5Reimagined.ID, "asphalt"));

    public static final TagKey<Fluid> BLUE_DYE = TagUtils.getFluidTag(new ResourceLocation(GT5Reimagined.ID, "blue_dye"));
    public static TagKey<Item> getTag(String id){
        return TagUtils.getForgelikeItemTag(id);
    }
}
