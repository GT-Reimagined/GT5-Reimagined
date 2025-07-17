package org.gtreimagined.gt5r.loader.crafting;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gtcore.GTCoreConfig;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.util.TagUtils;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTTools.HAMMER;

public class VanillaExtensions {
    public static void loadRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider) {
        provider.addStackRecipe(consumer, GT5Reimagined.ID, "sulfur_torch", "torches", new ItemStack(Items.TORCH, 2),
                of('D', DUST.getMaterialTag(Sulfur), 'R', Tags.Items.RODS_WOODEN), "D", "R");

        provider.addItemRecipe(consumer, GT5Reimagined.ID, "chainmail_helmet", "chainmail_armor",
                Items.CHAINMAIL_HELMET, of('R', GTMaterialTypes.RING.getMaterialTag(Steel), 'H', HAMMER.getTag()), "RRR", "RHR");
        provider.addItemRecipe(consumer, GT5Reimagined.ID, "chainmail_chestplate", "chainmail_armor",
                Items.CHAINMAIL_CHESTPLATE, of('R', GTMaterialTypes.RING.getMaterialTag(Steel), 'H', HAMMER.getTag()), "RHR", "RRR", "RRR");
        provider.addItemRecipe(consumer, GT5Reimagined.ID, "chainmail_leggings", "chainmail_armor",
                Items.CHAINMAIL_LEGGINGS, of('R', GTMaterialTypes.RING.getMaterialTag(Steel), 'H', HAMMER.getTag()), "RRR", "RHR", "R R");
        provider.addItemRecipe(consumer, GT5Reimagined.ID, "chainmail_boots", "chainmail_armor",
                Items.CHAINMAIL_BOOTS, of('R', GTMaterialTypes.RING.getMaterialTag(Steel), 'H', HAMMER.getTag()), "R R", "RHR");
        provider.addItemRecipe(consumer, GT5Reimagined.ID, "saddle", "", Items.SADDLE,
                of('L', Items.LEATHER, 'R', GTMaterialTypes.RING.getMaterialTag(Steel), 'S', GTMaterialTypes.SCREW.getMaterialTag(Steel)), "LLL", "LSL", "R R");

        provider.addItemRecipe(consumer, "magnetic_rods_iron", ROD.get(IronMagnetic),
                of('R', Tags.Items.DUSTS_REDSTONE, 'S', TagUtils.getForgelikeItemTag("rods/iron")), " R ", "RSR", " R ");

        provider.addItemRecipe(consumer, "magnetic_rods_neodymium", ROD.get(Neodymium),
                of('R', Tags.Items.DUSTS_REDSTONE, 'S', TagUtils.getForgelikeItemTag("rods/neodymium")), " R ", "RSR", " R ");
        provider.addStackRecipe(consumer, GT5Reimagined.ID, "torch_from_coal", "torches", new ItemStack(Items.TORCH, 4),
                of('C', RecipeIngredient.ofIngredient(1, RAW_ORE.getMaterialTag(Coal), DUST.getMaterialTag(Coal), IMPURE_DUST.getMaterialTag(Coal), PURE_DUST.getMaterialTag(Coal), CRUSHED_ORE.getMaterialTag(Coal), PURIFIED_ORE.getMaterialTag(Coal), REFINED_ORE.getMaterialTag(Coal), ROCK.getMaterialTag(Coal)), 'S', Items.STICK), "C", "S");
        provider.addStackRecipe(consumer, GT5Reimagined.ID, "torch_from_lignite", "torches", new ItemStack(Items.TORCH, 2),
                of('C', RecipeIngredient.ofIngredient(1, GEM.getMaterialTag(Lignite), RAW_ORE.getMaterialTag(Lignite), DUST.getMaterialTag(Lignite), IMPURE_DUST.getMaterialTag(Lignite), PURE_DUST.getMaterialTag(Lignite), CRUSHED_ORE.getMaterialTag(Lignite), PURIFIED_ORE.getMaterialTag(Lignite), REFINED_ORE.getMaterialTag(Lignite), ROCK.getMaterialTag(Lignite)), 'S', Items.STICK), "C", "S");
        provider.addStackRecipe(consumer, GT5Reimagined.ID, "torch_from_creosote", "torches", new ItemStack(Items.TORCH, 6),
                of('W', ItemTags.WOOL, 'C', Creosote.getLiquid().getBucket(), 'S', Items.STICK), "C", "W", "S");
        provider.shapeless(consumer, GT5Reimagined.ID, "green_dye_from_malachite", "dyes", Items.GREEN_DYE.getDefaultInstance(), DUST.getMaterialTag(Malachite));
        provider.shapeless(consumer, GT5Reimagined.ID, "blue_dye_from_sodalite_dust", "dyes", Items.BLUE_DYE.getDefaultInstance(), DUST.getMaterialTag(Sodalite));
        provider.shapeless(consumer, GT5Reimagined.ID, "blue_dye_from_sodalite_gem", "dyes", Items.BLUE_DYE.getDefaultInstance(), GEM.getMaterialTag(Sodalite));
        provider.shapeless(consumer, GT5Reimagined.ID, "cyan_dye_from_lazurite_dust", "dyes", Items.CYAN_DYE.getDefaultInstance(), DUST.getMaterialTag(Lazurite));
        provider.shapeless(consumer, GT5Reimagined.ID, "cyan_dye_from_lazurite_gem", "dyes", Items.CYAN_DYE.getDefaultInstance(), GEM.getMaterialTag(Lazurite));
        provider.shapeless(consumer, GT5Reimagined.ID, "green_dye_from_blue_and_yellow", "dyes", new ItemStack(Items.GREEN_DYE, 2), Items.YELLOW_DYE, Items.BLUE_DYE);
        provider.shapeless(consumer, GT5Reimagined.ID, "brown_dye_from_primaries", "dyes", new ItemStack(Items.BROWN_DYE, 3), Items.YELLOW_DYE, Items.BLUE_DYE, Items.RED_DYE);
        if (GTCoreConfig.VANILLA_OVERRIDES.get()){
            for (DyeColor dye : DyeColor.values()){
                provider.removeRecipe(new ResourceLocation(dye.getName() + "_concrete_powder"));
            }
            provider.removeRecipe(new ResourceLocation("netherite_ingot"));
        }
        provider.shapeless(consumer, GT5Reimagined.ID, "yellow_dye_from_bedrock_flower", "dyes", Items.YELLOW_DYE.getDefaultInstance(), GT5RBlocks.DESERT_TRUMPET);
        provider.shapeless(consumer, GT5Reimagined.ID, "pink_dye_from_bedrock_flower", "dyes", Items.PINK_DYE.getDefaultInstance(), GT5RBlocks.BECIUM_HOMBLEI);
        provider.shapeless(consumer, GT5Reimagined.ID, "yellow_dye_from_bedrock_flower_2", "dyes", Items.YELLOW_DYE.getDefaultInstance(), GT5RBlocks.PRINCES_PLUME);
        provider.shapeless(consumer, GT5Reimagined.ID, "purple_dye_from_bedrock_flower", "dyes", Items.PURPLE_DYE.getDefaultInstance(), GT5RBlocks.THOMPSONS_LOCOWEED);
        provider.shapeless(consumer, GT5Reimagined.ID, "green_dye_from_bedrock_flower", "dyes", Items.GREEN_DYE.getDefaultInstance(), GT5RBlocks.TUNGSTUS); //smelt?
        provider.shapeless(consumer, GT5Reimagined.ID, "yellow_dye_from_bedrock_flower_3", "dyes", Items.YELLOW_DYE.getDefaultInstance(), GT5RBlocks.ALTERED_ANDESITE_BUCKWHEAT);
        provider.shapeless(consumer, GT5Reimagined.ID, "yellow_dye_from_bedrock_flower_4", "dyes", Items.YELLOW_DYE.getDefaultInstance(), GT5RBlocks.CROSBY_BUCKWHEAT);
        provider.shapeless(consumer, GT5Reimagined.ID, "magenta_dye_from_bedrock_flower", "dyes", Items.MAGENTA_DYE.getDefaultInstance(), GT5RBlocks.ALPINE_CATCHFLY);
        provider.shapeless(consumer, GT5Reimagined.ID, "yellow_dye_from_bedrock_flower_5", "dyes", Items.YELLOW_DYE.getDefaultInstance(), GT5RBlocks.VIOLA_CALAMINARIA);
        provider.shapeless(consumer, GT5Reimagined.ID, "pink_dye_from_bedrock_flower_2", "dyes", Items.PINK_DYE.getDefaultInstance(), GT5RBlocks.THLASPI_LERESCHIANUM);
        provider.shapeless(consumer, GT5Reimagined.ID, "white_dye_from_bedrock_flower_", "dyes", Items.WHITE_DYE.getDefaultInstance(), GT5RBlocks.TUFTED_EVENING_PRIMROSE);
        provider.shapeless(consumer, GT5Reimagined.ID, "light_blue_dye_from_bedrock_flower", "dyes", Items.LIGHT_BLUE_DYE.getDefaultInstance(), GT5RBlocks.NARCISSUS_SHELDONIA);
        provider.shapeless(consumer, GT5Reimagined.ID, "brown_dye_from_bedrock_flower", "dyes", Items.BROWN_DYE.getDefaultInstance(), GT5RBlocks.ORECHID);

        provider.addItemRecipe(consumer, GT5Reimagined.ID, Items.PISTON,
                of('P', ItemTags.PLANKS, 'C', Tags.Items.COBBLESTONE, 'R', DUST.getMaterialTag(Redstone),
                        'I', RecipeIngredient.ofIngredient(1, INGOT.getMaterialTag(Iron), INGOT.getMaterialTag(Bronze), INGOT.getMaterialTag(WroughtIron), INGOT.getMaterialTag(Aluminium), INGOT.getMaterialTag(Steel), INGOT.getMaterialTag(Titanium))), "PPP", "CIC", "CRC");
    }
}
