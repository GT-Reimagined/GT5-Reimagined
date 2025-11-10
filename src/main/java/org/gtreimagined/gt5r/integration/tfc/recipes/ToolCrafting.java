package org.gtreimagined.gt5r.integration.tfc.recipes;

import com.google.common.collect.ImmutableMap;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.tfc.data.TFCToolTypes;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.AdvancedShapedCraftingFinishedRecipe;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.GTCoreConfig;
import org.gtreimagined.gtcore.data.GTCoreTools;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.recipe.ingredient.PropertyIngredient;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.gtreimagined.gtlib.util.TagUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.Materials.Diamond;
import static org.gtreimagined.gt5r.data.Materials.Gold;
import static org.gtreimagined.gt5r.data.Materials.Iron;
import static org.gtreimagined.gt5r.data.Materials.WroughtIron;
import static org.gtreimagined.gtlib.data.GTLibMaterials.*;
import static org.gtreimagined.gtlib.data.GTLibMaterials.Wood;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.GEM;
import static org.gtreimagined.gtlib.data.GTTools.*;
import static org.gtreimagined.gtlib.data.GTTools.KNIFE;
import static org.gtreimagined.gtlib.material.MaterialTags.*;
import static org.gtreimagined.gtlib.material.MaterialTags.FLINT;
import static org.gtreimagined.gtlib.recipe.RecipeBuilders.CROWBAR_BUILDER;

public class ToolCrafting {
    public static void init(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider) {
        List<Material> tfcTools = List.of(BismuthBronze, BlackBronze, BlackSteel,
                BlueSteel, Bronze, RedSteel, Steel, WroughtIron);
        TOOLS.getAll().forEach((m, t) -> {
            TagKey<Item> rod = t.handleMaterial().has(ROD) ? ROD.getMaterialTag(t.handleMaterial()) : ROD.getMaterialTag(Wood);
            GTToolType[] toolHeadTypes = new GTToolType[]{PICKAXE, AXE, SWORD, SHOVEL, HOE, FILE, SAW, HAMMER, SCREWDRIVER, SCYTHE, KNIFE, TFCToolTypes.JAVELIN, TFCToolTypes.PROPICK};
            Arrays.stream(toolHeadTypes).forEach(type -> {
                if (t.toolTypes().contains(type)){
                    if (type.getMaterialTypeItem() == null) return;
                    if (type != FILE && type != SCREWDRIVER && tfcTools.contains(m)) return;
                    if (m.has(type.getMaterialTypeItem())){
                        consumer.accept(new AdvancedShapedCraftingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, m.getId() + "_" + type.getId()), type.getToolStack(m), 0, 0, new String[]{"tfc:copy_forging_bonus"},
                                of('T', type.getMaterialTypeItem().getMaterialTag(m), 'R', rod), "T", "R"));
                    }
                }
            });
            if (m.has(INGOT) || m.has(GEM) || m == Wood){
                TagKey<Item> plateGem = m.has(GEM) ? GEM.getMaterialTag(m) : m.has(PLATE) ? PLATE.getMaterialTag(m) : INGOT.getMaterialTag(m);
                TagKey<Item> ingotGem = m.has(GEM) ? GEM.getMaterialTag(m) : INGOT.getMaterialTag(m);

                if (t.toolTypes().contains(GTCoreTools.POCKET_MULTITOOL) && m != Wood){
                    if (m.has(RING) && m.has(FILE_HEAD) && m.has(SAW_BLADE) && m.has(SCREWDRIVER_TIP) && m.has(SWORD_BLADE)){
                        provider.addStackRecipe(consumer, GT5Reimagined.ID, "", "tools", GTCoreTools.POCKET_MULTITOOL.getToolStack(m),
                                ImmutableMap.<Character, Object>builder()
                                        .put('F', FILE_HEAD.getMaterialTag(m))
                                        .put('S', SCREWDRIVER_TIP.getMaterialTag(m))
                                        .put('s', SAW_BLADE.getMaterialTag(m))
                                        .put('P', m.has(GEM) ? GEM.getMaterialTag(m) : m.has(PLATE) ? PLATE.getMaterialTag(m) : INGOT.getMaterialTag(m))
                                        .put('R', RING.getMaterialTag(m))
                                        .put('W', SWORD_BLADE.getMaterialTag(m)).build(), "SsR", "FPW", "RW ");
                    }
                }
                if (t.toolTypes().contains(WRENCH)){
                    provider.addStackRecipe(consumer, GT5Reimagined.ID, "", "", WRENCH.getToolStack(m),
                            of('H', HAMMER.getTag(), 'P', plateGem), "PHP", "PPP", " P ");
                }
                if (t.toolTypes().contains(SOFT_HAMMER) && m.has(RUBBERTOOLS)){
                    TagKey<Item> ingotGem1 = m == Wood ? ItemTags.PLANKS : m.has(GEM) ? GEM.getMaterialTag(m) : INGOT.getMaterialTag(m);
                    provider.addStackRecipe(consumer, GT5Reimagined.ID, "", "", SOFT_HAMMER.getToolStack(m),
                            of('R', rod, 'P', ingotGem1), "PP ", "PPR", "PP ");
                }
                if (t.toolTypes().contains(FILE)){
                    provider.addStackRecipe(consumer, GT5Reimagined.ID, "", "", FILE.getToolStack(m),
                            of('R', rod, 'P', plateGem), "P", "P", "R");
                }
                if (t.toolTypes().contains(SCREWDRIVER) && m.has(ROD)){
                    provider.addStackRecipe(consumer, GT5Reimagined.ID, "", "", SCREWDRIVER.getToolStack(m),
                            of('R', rod, 'P', ROD.getMaterialTag(m),'F', GTTools.FILE.getTag(), 'H', GTTools.HAMMER.getTag()), " FP", " PH", "R  ");
                }
                if (t.toolTypes().contains(PLUNGER) && m.has(ROD)){
                    RUBBERTOOLS.all().stream().filter(r -> r.has(PLATE) && r != Wood).forEach(r -> {
                        provider.addStackRecipe(consumer, GT5Reimagined.ID, m.getId() + "_plunger_with_" + r.getId(), "", PLUNGER.getToolStack(m),
                                of('P', ROD.getMaterialTag(m), 'R', PLATE.getMaterialTag(r),'F', GTTools.FILE.getTag(), 'W', WIRE_CUTTER.getTag()), "WRR", " PR", "P F");
                    });

                }
                if (t.toolTypes().contains(WIRE_CUTTER)){
                    ImmutableMap.Builder<Character, Object> builder = ImmutableMap.builder();
                    builder.put('R', rod).put('P', plateGem).put('F', GTTools.FILE.getTag()).put('H', GTTools.HAMMER.getTag()).put('S', SCREWDRIVER.getTag());
                    if (m.has(SCREW)) builder.put('W', SCREW.getMaterialTag(m));
                    String last = m.has(SCREW) ? "RWR" : "R R";
                    provider.addStackRecipe(consumer, GT5Reimagined.ID, "", "", WIRE_CUTTER.getToolStack(m),
                            builder.build(), "PFP", "HPS", last);
                }
                if (t.toolTypes().contains(BRANCH_CUTTER)){
                    ImmutableMap.Builder<Character, Object> builder = ImmutableMap.builder();
                    builder.put('R', rod).put('P', plateGem).put('F', GTTools.FILE.getTag()).put('S', SCREWDRIVER.getTag());
                    if (m.has(SCREW)) builder.put('W', SCREW.getMaterialTag(m));
                    String last = m.has(SCREW) ? "RWR" : "R R";
                    provider.addStackRecipe(consumer, GT5Reimagined.ID, "", "", BRANCH_CUTTER.getToolStack(m),
                            builder.build(), "PFP", "PSP", last);
                }
                if (t.toolTypes().contains(CROWBAR) && m.has(ROD)){
                    provider.addToolRecipe(CROWBAR_BUILDER.get(m.getId() + "_" + CROWBAR.getId()), consumer, GT5Reimagined.ID, "", "gt_crowbars", CROWBAR.getToolStack(m), of('H', GTTools.HAMMER.getTag(), 'C', PropertyIngredient.builder("secondary").itemTags(TagUtils.getForgelikeItemTag("dyes")).build(), 'R', ROD.getMaterialTag(m), 'F', GTTools.FILE.getTag()), "HCR", "CRC", "RCF");
                }
            }
        });
    }
}
