package org.gtreimagined.gt5r.integration.forge.tfc.datagen;

import org.gtreimagined.gtlib.AntimatterAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.datagen.providers.GTBlockTagProvider;
import org.gtreimagined.gtlib.datagen.providers.GTItemTagProvider;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.material.data.ToolData;
import org.gtreimagined.gtlib.tool.AntimatterToolType;
import org.gtreimagined.gtlib.util.TagUtils;
import net.minecraft.resources.ResourceLocation;

import static org.gtreimagined.gtlib.data.GTTools.*;
import static org.gtreimagined.gtlib.data.GTLibMaterials.Copper;
import static org.gtreimagined.gt5r.data.Materials.*;

public class TFCItemTagProvider extends GTItemTagProvider {
    public TFCItemTagProvider(String providerDomain, String providerName, boolean replace, GTBlockTagProvider p) {
        super(providerDomain, providerName, replace, p);
    }

    @Override
    protected void processTags(String domain) {
        super.processTags(domain);
        this.tag(TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, "swords"))).addTag(SWORD.getTag());
        this.tag(TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, "pickaxes"))).addTag(PICKAXE.getTag());
        this.tag(TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, "shovels"))).addTag(SHOVEL.getTag());
        this.tag(TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, "axes"))).addTag(AXE.getTag());
        this.tag(TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, "hoes"))).addTag(HOE.getTag());
        this.tag(TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, "saws"))).addTag(SAW.getTag());
        this.tag(TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, "knives"))).addTag(KNIFE.getTag());
        Material[] tfcTools = new Material[]{BismuthBronze, BlackBronze, Bronze, Copper, WroughtIron, Steel, BlackSteel, BlueSteel, RedSteel};
        AntimatterAPI.all(AntimatterToolType.class).forEach(t -> {
            if (t.hasOriginalTag()) {
                tag(TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, "usable_on_tool_rack"))).addTag(t.getTag());
            }
            if (!t.isSimple()) return;
            for (Material tfcTool : tfcTools) {
                ToolData data = MaterialTags.TOOLS.get(tfcTool);
                if (data != null && data.toolTypes().contains(t)) {
                    tag(TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, "metal_item/" + tfcTool.getId() + "_tools"))).add(t.getToolItem(tfcTool));
                    tag(TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, "metal_item/" + tfcTool.getId()))).add(t.getToolItem(tfcTool));
                    if (t == AXE || t == SWORD){
                        tag(TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, "mob_mainhand_weapons"))).add(t.getToolItem(tfcTool));
                    }
                }
            }

        });
        /*for (Material material : TFCRegistrar.array) {
            this.tag(TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, "ore_pieces"))).add(GT5Reimagined.get(Item.class, "poor_" + material.getId()), GT5Reimagined.get(Item.class, "normal_" + material.getId()), GT5Reimagined.get(Item.class, "rich_" + material.getId()));
        }*/
    }
}
