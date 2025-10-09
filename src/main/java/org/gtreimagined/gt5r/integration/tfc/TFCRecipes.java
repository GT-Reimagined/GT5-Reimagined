package org.gtreimagined.gt5r.integration.tfc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.recipes.QuernRecipe;
import net.dries007.tfc.common.recipes.SimpleItemRecipe;
import net.dries007.tfc.common.recipes.TFCRecipeSerializers;
import net.dries007.tfc.compat.jei.category.QuernRecipeCategory;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.datagen.GTLibDynamics;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.jetbrains.annotations.Nullable;

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
    }

    private record QuernFinishedRecipe(ResourceLocation id, Ingredient input, ItemStack output) implements FinishedRecipe {

        @Override
        public void serializeRecipeData(JsonObject jsonObject) {
            jsonObject.add("ingredient", this.input.toJson());
            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("item", RegistryUtils.getIdFromItem(this.output.getItem()).toString());
            if (this.output.getCount() > 1) {
                resultObj.addProperty("count", this.output.getCount());
            }
            jsonObject.add("result", resultObj);
            if (this.output.hasTag()) {
                resultObj.addProperty("nbt", this.output.getTag().toString());
            }
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return TFCRecipeSerializers.QUERN.get();
        }

        @Override
        public @Nullable JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        public @Nullable ResourceLocation getAdvancementId() {
            return null;
        }
    }

    private record RockKnappingFinishedRecipe(ResourceLocation id, String[] pattern, Ingredient input, ItemStack output) implements FinishedRecipe {

        @Override
        public void serializeRecipeData(JsonObject jsonObject) {
            jsonObject.add("ingredient", this.input.toJson());
            JsonArray array = new JsonArray();
            for (String s : pattern) {
                array.add(new JsonPrimitive(s));
            }
            jsonObject.add("pattern", array);
            jsonObject.addProperty("outside_slot_required", false);
            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("item", RegistryUtils.getIdFromItem(this.output.getItem()).toString());
            if (this.output.getCount() > 1) {
                resultObj.addProperty("count", this.output.getCount());
            }
            jsonObject.add("result", resultObj);
            if (this.output.hasTag()) {
                resultObj.addProperty("nbt", this.output.getTag().toString());
            }
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return TFCRecipeSerializers.ROCK_KNAPPING.get();
        }

        @Override
        public @Nullable JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        public @Nullable ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
