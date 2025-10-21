package org.gtreimagined.gt5r.integration.tfc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Ore.Grade;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal.Default;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.AlloyingFinishedRecipe;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.AlloyingFinishedRecipe.Alloy;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.HeatingFinishedRecipe;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.QuernFinishedRecipe;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.RockKnappingFinishedRecipe;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.datagen.DynamicDataPack;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gt5r.data.Materials.Flint;
import static org.gtreimagined.gt5r.integration.tfc.data.TFCToolTypes.JAVELIN;
import static org.gtreimagined.gt5r.integration.tfc.data.TFCToolTypes.JAVELIN_HEAD;
import static org.gtreimagined.gtlib.data.GTLibMaterials.Wood;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTTools.KNIFE;
import static org.gtreimagined.gtlib.material.MaterialTags.TOOLS;

public class TFCRecipes {
    public static void initRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        consumer.accept(new QuernFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "quern/raw_redstone"), Ingredient.of(GTMaterialTypes.RAW_ORE.getMaterialTag(Materials.Redstone)), new ItemStack(Items.REDSTONE, 8)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_axe_head"),
                new String[]{
                        " X   ",
                        "XXXX ",
                        "XXXXX",
                        "XXXX ",
                        " X   "
                },
                Ingredient.of(Items.FLINT), AXE_HEAD.get(Flint, 1)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_hoe_head_1"),
                new String[]{
                        "XXXXX",
                        "XX   ",
                        "     ",
                        "XXXXX",
                        "XX   "
                },
                Ingredient.of(Items.FLINT), HOE_HEAD.get(Flint, 2)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_hoe_head_2"),
                new String[]{
                        "XXXXX",
                        "XX   ",
                        "     ",
                        "XXXXX",
                        "   XX"
                },
                Ingredient.of(Items.FLINT), HOE_HEAD.get(Flint, 2)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_hoe_head"),
                new String[]{
                        "XXXXX",
                        "   XX"
                },
                Ingredient.of(Items.FLINT), HOE_HEAD.get(Flint, 1)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_knife_head_1"),
                new String[]{
                        "X  X ",
                        "XX XX",
                        "XX XX",
                        "XX XX",
                        "XX XX"
                },
                Ingredient.of(Items.FLINT), KNIFE_HEAD.get(Flint, 2)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_knife_head_2"),
                new String[]{
                        "X   X",
                        "XX XX",
                        "XX XX",
                        "XX XX",
                        "XX XX"
                },
                Ingredient.of(Items.FLINT), KNIFE_HEAD.get(Flint, 2)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_knife_head_3"),
                new String[]{
                        " X X ",
                        "XX XX",
                        "XX XX",
                        "XX XX",
                        "XX XX"
                },
                Ingredient.of(Items.FLINT), KNIFE_HEAD.get(Flint, 2)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_knife_head"),
                new String[]{
                        "X ",
                        "XX",
                        "XX",
                        "XX",
                        "XX"
                },
                Ingredient.of(Items.FLINT), KNIFE_HEAD.get(Flint, 1)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_shovel_head"),
                new String[]{
                        "XXX",
                        "XXX",
                        "XXX",
                        "XXX",
                        " X "
                },
                Ingredient.of(Items.FLINT), SHOVEL_HEAD.get(Flint, 1)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_javelin_head"),
                new String[]{
                        "XXX  ",
                        "XXXX ",
                        "XXXXX",
                        " XXX ",
                        "  X  "
                },
                Ingredient.of(Items.FLINT), JAVELIN_HEAD.get(Flint, 1)));
        TOOLS.getAll().forEach((m, t) -> {
            if (t.toolTypes().contains(JAVELIN)){
                TagKey<Item> rod = t.handleMaterial().has(ROD) ? ROD.getMaterialTag(t.handleMaterial()) : ROD.getMaterialTag(Wood);
                provider.addStackRecipe(consumer, GT5Reimagined.ID, m.getId() + "_javelin_from_javelin_head", "gt_tools_from_tool_parts", JAVELIN.getToolStack(m), of('T', JAVELIN_HEAD.getMaterialTag(m), 'R', rod), "T", "R");
            }
            if (t.toolTypes().contains(KNIFE)){
                TagKey<Item> rod = t.handleMaterial().has(ROD) ? ROD.getMaterialTag(t.handleMaterial()) : ROD.getMaterialTag(Wood);
                provider.addStackRecipe(consumer, GT5Reimagined.ID, m.getId() + "_knife_from_knife_head", "gt_tools_from_tool_parts", KNIFE.getToolStack(m), of('T', KNIFE_HEAD.getMaterialTag(m), 'R', rod), "T", "R");
            }
        });
        for (Ore ore : Ore.values()){
            if (ore.isGraded()){
                TFCMetal tfcMetal = metalFromOre(ore);
                Fluid fluid = TFCFluids.METALS.get(tfcMetal.metal).getSource();
                for (Grade grade : Grade.values()){
                    consumer.accept(new HeatingFinishedRecipe(new ResourceLocation(Ref.MOD_TFC, "heating/ore/" + grade.name().toLowerCase() + "_" + ore.name().toLowerCase()),
                            Ingredient.of(TFCItems.GRADED_ORES.get(ore).get(grade).get()), new FluidStack(fluid, grade == Grade.POOR ? 25 : grade == Grade.NORMAL ? 50 : 100), tfcMetal.temperature));
                }
            }
        }
        consumer.accept(new AlloyingFinishedRecipe(new ResourceLocation(Ref.MOD_TFC, "alloy/sterling_silver"), "tfc:sterling_silver", new Alloy("tfc:copper", 0.2, 0.25), new Alloy("tfc:silver", 0.75, 0.8)));
        consumer.accept(new AlloyingFinishedRecipe(new ResourceLocation(Ref.MOD_TFC, "alloy/rose_gold"), "tfc:rose_gold", new Alloy("tfc:copper", 0.2, 0.25), new Alloy("tfc:gold", 0.75, 0.8)));
        consumer.accept(new AlloyingFinishedRecipe(new ResourceLocation(Ref.MOD_TFC, "alloy/bismuth_bronze"), "tfc:bismuth_bronze", new Alloy("tfc:zinc", 0.25, 0.3), new Alloy("tfc:copper", 0.5, 0.6), new Alloy("tfc:bismuth", 0.15, 0.2)));
        consumer.accept(new AlloyingFinishedRecipe(new ResourceLocation(Ref.MOD_TFC, "alloy/black_bronze"), "tfc:black_bronze", new Alloy("tfc:copper", 0.6, 0.7), new Alloy("tfc:gold", 0.15, 0.2), new Alloy("tfc:silver", 0.15, 0.2)));
        JsonObject page = JsonParser.parseString("{\n" +
                "  \"__comment__\": \"This file was automatically created by mcresources\",\n" +
                "  \"name\": \"Primitive Alloys\",\n" +
                "  \"category\": \"tfc:getting_started\",\n" +
                "  \"icon\": \"tfc:ceramic/ingot_mold{tank:{\\\"Amount\\\":100,\\\"FluidName\\\":\\\"tfc:metal/bronze\\\"}}\",\n" +
                "  \"pages\": [\n" +
                "    {\n" +
                "      \"type\": \"patchouli:text\",\n" +
                "      \"text\": \"$(thing)Alloys$() are a method of mixing two or more metals together, to create a new, stronger metal. During the early game, while copper is a useful metal for creating tools, the next tier of metal is one of three types of $(thing)Bronze$(). An alloy is made up of component $(thing)metals$() which must each satisfy a specific percentage of the overall whole.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"patchouli:text\",\n" +
                "      \"text\": \"One method through which alloys can be made during the early game is through the usage of a $(thing)Small Vessel$(). The process is very similar to $(l:getting_started/finding_ores#casting)Casting$(). However, instead of using just a single metal, place enough ore pieces inside the vessel in the correct ratio to form a known alloy mix.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"patchouli:text\",\n" +
                "      \"text\": \"For example, to create 1000 mB of $(thing)Bronze$() (shown to the right), you would need between 880 and 920 mB of $(thing)Copper$(), and between 80 and 120 mB of $(thing)Tin$().$(br2)The next three pages show the recipes of the three bronzes. Each type of bronze can be used to make tools, $(l:mechanics/armor)Armor$(), and other metal items. They are slightly different so resulting tools will have different durability, efficiency, and attack damage.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"patchouli:spotlight\",\n" +
                "      \"item\": \"tfc:metal/ingot/bronze\",\n" +
                "      \"title\": \"Bronze\",\n" +
                "      \"link_recipes\": false,\n" +
                "      \"text\": \"$(br)$(bold)Requirements:$()$(br)$(li)88 - 92 % : $(thing)Copper$()$(li)8 - 12 % : $(thing)Tin$()$(br2)\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"patchouli:spotlight\",\n" +
                "      \"item\": \"tfc:metal/ingot/bismuth_bronze\",\n" +
                "      \"title\": \"Bismuth Bronze\",\n" +
                "      \"link_recipes\": false,\n" +
                "      \"text\": \"$(br)$(bold)Requirements:$()$(br)$(li)25 - 30 % : $(thing)Zinc$()$(li)50 - 60 % : $(thing)Copper$()$(li)15 - 20 % : $(thing)Bismuth$()$(br2)\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"patchouli:spotlight\",\n" +
                "      \"item\": \"tfc:metal/ingot/black_bronze\",\n" +
                "      \"title\": \"Black Bronze\",\n" +
                "      \"link_recipes\": false,\n" +
                "      \"text\": \"$(br)$(bold)Requirements:$()$(br)$(li)60 - 70 % : $(thing)Copper$()$(li)15 - 20 % : $(thing)Silver$()$(li)15 - 20 % : $(thing)Gold$()$(br2)\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"read_by_default\": true,\n" +
                "  \"sortnum\": 5,\n" +
                "  \"extra_recipe_mappings\": {\n" +
                "    \"tfc:metal/ingot/bronze\": 3,\n" +
                "    \"tfc:metal/ingot/bismuth_bronze\": 4,\n" +
                "    \"tfc:metal/ingot/black_bronze\": 5\n" +
                "  }\n" +
                "}\n").getAsJsonObject();
        DynamicDataPack.addData(new ResourceLocation("tfc", "patchouli_books/field_guide/en_us/entries/getting_started/primitive_alloys.json"), page);
    }

    private static TFCMetal metalFromOre(Ore ore){
        return switch (ore) {
            case BISMUTHINITE -> TFCMetal.BISMUTH;
            case HEMATITE, LIMONITE, MAGNETITE -> TFCMetal.CAST_IRON;
            case NATIVE_COPPER, MALACHITE, TETRAHEDRITE -> TFCMetal.COPPER;
            case GARNIERITE -> TFCMetal.NICKEL;
            case SPHALERITE -> TFCMetal.ZINC;
            case CASSITERITE -> TFCMetal.TIN;
            case NATIVE_GOLD -> TFCMetal.GOLD;
            case NATIVE_SILVER -> TFCMetal.SILVER;
            default -> TFCMetal.UNKNOWN;
        };
    }

    private enum TFCMetal{
        BISMUTH(Default.BISMUTH, 270),
        CAST_IRON(Default.CAST_IRON, 1535),
        COPPER(Default.COPPER, 1080),
        NICKEL(Default.NICKEL, 1453),
        ZINC(Default.ZINC, 420),
        TIN(Default.TIN, 230),
        GOLD(Default.GOLD, 1060),
        SILVER(Default.SILVER, 961),
        UNKNOWN(Default.UNKNOWN, -1),;


        private final Default metal;
        private final int temperature;

        private TFCMetal(Default metal, int temperature){
            this.metal = metal;
            this.temperature = temperature;
        }
    }

}
