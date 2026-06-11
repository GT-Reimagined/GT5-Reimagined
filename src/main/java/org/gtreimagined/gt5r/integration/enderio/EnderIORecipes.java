package org.gtreimagined.gt5r.integration.enderio;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gt5r.data.RecipeMaps;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.util.TagUtils;

import java.util.function.Consumer;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.DUST;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.INGOT;

public class EnderIORecipes {
    public static void init(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        
    }

    public static void init(){
        RecipeMaps.ALLOY_SMELTER.RB().ii(INGOT.getMaterialIngredient(Materials.Copper, 1), Ingredient.of(TagUtils.getForgelikeItemTag("silicon")))
                .io(getEIOItem("copper_alloy_ingot")).add("eio_copper_alloy_ingot", 100, 12);
        RecipeMaps.ALLOY_SMELTER.RB().ii(DUST.getMaterialIngredient(Redstone, 1), INGOT.getMaterialIngredient(Materials.Gold, 1), DUST.getMaterialIngredient(Glowstone, 1))
                .io(getEIOItem("energetic_alloy_ingot")).add("eio_energetic_alloy_ingot", 100, 12);
        RecipeMaps.ALLOY_SMELTER.RB().ii(getEIOItem("energetic_alloy_ingot"), Items.ENDER_PEARL).io(getEIOItem("vibrant_alloy_ingot")).add("eio_vibrant_alloy_ingot", 100, 12);
        RecipeMaps.ALLOY_SMELTER.RB().ii(DUST.getMaterialIngredient(Redstone, 1), Ingredient.of(TagUtils.getForgelikeItemTag("silicon")))
                .io(getEIOItem("redstone_alloy_ingot")).add("eio_redstone_alloy_ingot", 100, 12);
        RecipeMaps.ALLOY_SMELTER.RB().ii(Ingredient.of(getEIOItem("copper_alloy_ingot")), INGOT.getMaterialIngredient(Iron, 1), DUST.getMaterialIngredient(Redstone, 1))
                .io(getEIOItem("conductive_alloy_ingot")).add("eio_conductive_alloy_ingot", 100, 12);
        RecipeMaps.ALLOY_SMELTER.RB().ii(INGOT.getMaterialIngredient(Iron, 1), Ingredient.of(Items.ENDER_PEARL))
                .io(getEIOItem("pulsating_alloy_ingot")).add("eio_pulsating_alloy_ingot", 100, 12);

    }

    public static Item getEIOItem(String id){
        return RegistryUtils.getItemFromID("enderio", id);
    }
}
