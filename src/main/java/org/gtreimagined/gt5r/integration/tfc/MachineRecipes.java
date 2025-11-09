package org.gtreimagined.gt5r.integration.tfc;


import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;
import org.gtreimagined.gt5r.data.GT5RRecipeTags;
import org.gtreimagined.gt5r.data.RecipeMaps;
import org.gtreimagined.gt5r.loader.machines.CutterLoader;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.data.GTCoreTags;
import org.gtreimagined.gtcore.integration.tfc.TFCRubberData;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.util.TagUtils;
import org.gtreimagined.gtlib.util.Utils;

import java.util.function.ToLongFunction;

import static net.dries007.tfc.common.blocks.rock.Ore.*;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.BENDER;
import static org.gtreimagined.gt5r.data.RecipeMaps.FLUID_PRESS;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.DUST;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.INGOT;
import static org.gtreimagined.gtlib.material.MaterialTags.MACERATE_INTO;
import static org.gtreimagined.gtlib.material.MaterialTags.ORE_MULTI;

public class MachineRecipes {
    public static void init(){
        initMaceratorRecipes();
    }

    public static void initMaceratorRecipes(){
        addMaceratorRecipe(NATIVE_COPPER, Copper);
        addMaceratorRecipe(NATIVE_GOLD, Gold);
        addMaceratorRecipe(NATIVE_SILVER, Silver);
        addMaceratorRecipe(HEMATITE, Hematite);
        addMaceratorRecipe(CASSITERITE, Cassiterite);
        addMaceratorRecipe(BISMUTHINITE, Bismuth);
        addMaceratorRecipe(GARNIERITE, Garnierite);
        addMaceratorRecipe(MALACHITE, Malachite);
        addMaceratorRecipe(MAGNETITE, Magnetite);
        addMaceratorRecipe(LIMONITE, YellowLimonite);
        addMaceratorRecipe(SPHALERITE, Sphalerite);
        addMaceratorRecipe(TETRAHEDRITE, Tetrahedrite);
        addMaceratorRecipe(BITUMINOUS_COAL, Coal);
        addMaceratorRecipe(LIGNITE, Lignite);
        //addMaceratorRecipe(KAOLINITE);
        //addMaceratorRecipe(GYPSUM,);
        addMaceratorRecipe(GRAPHITE, Graphite);
        addMaceratorRecipe(SULFUR, Sulfur);
        addMaceratorRecipe(CINNABAR, Cinnabar);
        addMaceratorRecipe(CRYOLITE, Redstone);
        addMaceratorRecipe(SALTPETER, Saltpeter);
        addMaceratorRecipe(HALITE, Salt);
        addMaceratorRecipe(SYLVITE, Sylvite);
        addMaceratorRecipe(LAPIS_LAZULI, Lapis);
        addMaceratorRecipe(EMERALD, Emerald);
        addMaceratorRecipe(DIAMOND, Diamond);
        addMaceratorRecipe(PYRITE, Pyrite);
        addMaceratorRecipe(RUBY, Ruby);
        addMaceratorRecipe(SAPPHIRE, Sapphire);
        addMaceratorRecipe(TOPAZ, Topaz);
        addMaceratorRecipe(OPAL, Opal);
        addMaceratorRecipe(AMETHYST, Amethyst);
        /*for (Material material : TFCRegistrar.array) {
            addMaceratorRecipe(material);
        }*/
        Helpers.mapOfKeys(net.dries007.tfc.common.blocks.wood.Wood.class, w -> {
            CutterLoader.addWoodRecipe(TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, w.name().toLowerCase() + "_logs")), RegistryUtils.getItemFromID(Ref.MOD_TFC, "wood/lumber/" + w.name().toLowerCase()), 2, w.name().toLowerCase() + "_lumber", 200, 8);
            return true;
        });
        ToLongFunction<Material> baseDuration = m -> {
            if (m.has(GT5RMaterialTags.RECIPE_MASS)) return GT5RMaterialTags.RECIPE_MASS.get(m);
            return m.getMass();
        };
        Helpers.mapOfKeys(Metal.Default.class, d -> {
            Material material = Material.get(d.getSerializedName());
            if (material != Material.NULL && material.has(INGOT) && d.hasParts()){
                BENDER.RB().ii(INGOT.getMaterialIngredient(material, 2), GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(2)).io(RegistryUtils.getItemFromID(Ref.MOD_TFC, "metal/sheet/" + material.getId())).add(material.getId() + "_sheet", baseDuration.applyAsLong(material) * 2, 24);
                BENDER.RB().ii(INGOT.getMaterialIngredient(material, 4), GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(4)).io(RegistryUtils.getItemFromID(Ref.MOD_TFC, "metal/double_sheet/" + material.getId())).add(material.getId() + "_double_sheet", baseDuration.applyAsLong(material) * 2, 24);
            }
            return true;
        });
        CutterLoader.addWoodRecipe(GTCoreTags.RUBBER_LOGS, TFCRubberData.RUBBER_LUMBER, 2, "rubber_lumber", 200, 8);
        FLUID_PRESS.RB().ii(RecipeIngredient.of(TagUtils.getItemTag(new ResourceLocation("tfc:seeds")))).fo(SeedOil.getLiquid(10)).add("seed_oil_tfc", 32, 2);
        FLUID_PRESS.RB().ii(RegistryUtils.getItemFromID(Ref.MOD_TFC, "food/cod")).fo(FishOil.getLiquid(40)).add("fish_oil_cod_tfc", 16, 4);
        FLUID_PRESS.RB().ii(RegistryUtils.getItemFromID(Ref.MOD_TFC, "food/salmon")).fo(FishOil.getLiquid(60)).add("fish_oil_salmon_tfc", 16, 4);
        FLUID_PRESS.RB().ii(RegistryUtils.getItemFromID(Ref.MOD_TFC, "food/tropical_fish")).fo(FishOil.getLiquid(70)).add("fish_oil_tropical_fish_tfc", 16, 4);
    }

    private static void addMaceratorRecipe(Ore input, Material material){
        int multiplier = ORE_MULTI.getInt(material);
        ItemStack crushedStack = GTMaterialTypes.CRUSHED_ORE.get(MACERATE_INTO.getMapping(material), multiplier);
        Material oreByProduct1 = !material.getByProducts().isEmpty() ? material.getByProducts().get(0) : MACERATE_INTO.getMapping(material);
        if (input.isGraded()){
            RecipeMaps.PULVERIZER.RB().ii(TFCItems.GRADED_ORES.get(input).get(Grade.POOR).get()).io(crushedStack, DUST.get(oreByProduct1, 1)).outputChances(1.0, 0.05 * multiplier).tags(GT5RRecipeTags.MACERATOR_ORE_PROCESING).add("poor_" + material.getId() + "_tfc", 400, 2);
            RecipeMaps.PULVERIZER.RB().ii(TFCItems.GRADED_ORES.get(input).get(Grade.NORMAL).get()).io(Utils.ca(multiplier * 2, crushedStack), DUST.get(oreByProduct1, 1)).outputChances(1.0, 0.1 * multiplier).tags(GT5RRecipeTags.MACERATOR_ORE_PROCESING).add("normal_" + material.getId() + "_tfc", 400, 2);
            RecipeMaps.PULVERIZER.RB().ii(TFCItems.GRADED_ORES.get(input).get(Grade.RICH).get()).io(Utils.ca(multiplier * 4, crushedStack), DUST.get(oreByProduct1, 1)).outputChances(1.0, 0.15 * multiplier).tags(GT5RRecipeTags.MACERATOR_ORE_PROCESING).add("rich_" + material.getId() + "_tfc", 400, 2);
        } else {
            RecipeMaps.PULVERIZER.RB().ii(TFCItems.ORES.get(input).get()).io(Utils.ca(multiplier * 2, crushedStack), DUST.get(oreByProduct1, 1)).outputChances(1.0, 0.1 * multiplier).tags(GT5RRecipeTags.MACERATOR_ORE_PROCESING).add("normal_" + material.getId() + "_tfc", 400, 2);
        }


    }

    private static void addMaceratorRecipe(Material material){
        int multiplier = ORE_MULTI.getInt(material);
        ItemStack crushedStack = GTMaterialTypes.CRUSHED_ORE.get(MACERATE_INTO.getMapping(material), multiplier);
        Material oreByProduct1 = !material.getByProducts().isEmpty() ? material.getByProducts().get(0) : MACERATE_INTO.getMapping(material);
        RecipeMaps.PULVERIZER.RB().ii(GT5Reimagined.get(Item.class, "poor_" + material.getId())).io(crushedStack, DUST.get(oreByProduct1, 1)).outputChances(1.0, 0.05 * multiplier).add("poor_" + material.getId() + "_tfc", 400, 2);
        RecipeMaps.PULVERIZER.RB().ii(GT5Reimagined.get(Item.class, "normal_" + material.getId())).io(Utils.ca(multiplier * 2, crushedStack), DUST.get(oreByProduct1, 1)).outputChances(1.0, 0.1 * multiplier).add("normal_" + material.getId() + "_tfc", 400, 2);
        RecipeMaps.PULVERIZER.RB().ii(GT5Reimagined.get(Item.class, "rich_" + material.getId())).io(Utils.ca(multiplier * 3, crushedStack), DUST.get(oreByProduct1, 1)).outputChances(1.0, 0.15 * multiplier).add("rich_" + material.getId() + "_tfc", 400, 2);
    }
}
