package muramasa.gregtech.datagen;

import muramasa.antimatter.datagen.providers.AntimatterFluidTagProvider;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.util.TagUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class GregTechFluidTagProvider extends AntimatterFluidTagProvider {
    public GregTechFluidTagProvider(String providerDomain, String providerName, boolean replace) {
        super(providerDomain, providerName, replace);
    }

    @Override
    protected void processTags(String domain) {
        super.processTags(domain);
        for (DyeColor color : DyeColor.values()) {
            String dyeName = color.getName() + "_dye";
            Material chemDye = Material.get("chemical_" + dyeName);
            Material waterMixedDye = Material.get("water_mixed_" + dyeName);
            this.tag(TagUtils.getFluidTag(new ResourceLocation(domain, dyeName))).add(chemDye.getLiquid(), waterMixedDye.getLiquid());
        }
    }
}
