package org.gtreimagined.gt5r.loader.multi;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gt5r.integration.SpaceModRegistrar;
import org.gtreimagined.gtlib.data.VanillaStoneTypes;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.recipe.map.RecipeBuilder;
import org.gtreimagined.gtlib.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.BEDROCK_DRILL;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class BedrockDrillLoader {
    public static void init(){
        addBedrockDrillRecipe(Materials.Adamantine, 105, Adamantium);
        //addBedrockDrillRecipe(GTLibMaterials.NetheriteScrap, 63,);
        addBedrockDrillRecipe(Bastnasite, 63, Monazite, RareEarth, Neodymium/*, Nikolite*/);
        addBedrockDrillRecipe(Bauxite, 53, Ilmenite, Hematite, Alumina);
        ItemStack cobble = new ItemStack(Items.COBBLESTONE);
        cobble.setHoverName(Utils.literal("Various Cobblestone Types"));
        BEDROCK_DRILL.RB().ii(Items.BEDROCK).fi(Lubricant.getLiquid(100)).io(cobble, DUST.get(Bedrock, 1)).outputChances(9990, 10);
        addBedrockDrillRecipe(Cassiterite, 35, Molybdenite, Fluorite, Sperrylite, Apatite);
        addBedrockDrillRecipe(Chalcopyrite, 40, Pyrite, Cobaltite, Cadmium, Gold, Sperrylite, Indium);
        addBedrockDrillRecipe(Coal, 105, Lignite, Sulfur);
        addBedrockDrillRecipe(Sheldonite, 79, Palladium, Nickel, Iridium);
        addBedrockDrillRecipe(Copper, 53, Cobaltite, Gold, Nickel, Malachite, Arsenic);
        if (SpaceModRegistrar.INSTANCE.isEnabled()) {
            addBedrockDrillRecipe(SpaceModRegistrar.Desh, 35, Cobaltite, Monazite, LithiumChloride);
        }
        addBedrockDrillRecipe(Diamond, 105, Graphite);
        addBedrockDrillRecipe(Galena, 45, Sphalerite, Silver, Lead, Selenium, Fluorite, Calcite);
        addBedrockDrillRecipe(Gold, 79, Copper, Nickel, Cinnabar);
        addBedrockDrillRecipe(Graphite, 157, Carbon);
        addBedrockDrillRecipe(Hematite, 63, Ilmenite, Pyrolusite);
        addBedrockDrillRecipe(Monazite, 63, Thorium, Neodymium, RareEarth/*, Nikolite*/);
        addBedrockDrillRecipe(Naquadah, 105);
        addBedrockDrillRecipe(Quartz, 63, Barite, Fluorite);
        addBedrockDrillRecipe(Pentlandite, 53, Hematite, Sulfur, Cobaltite, Sperrylite);
        addBedrockDrillRecipe(Pitchblende, 63, Lead, Radium, RareEarth, Thorium);
        addBedrockDrillRecipe(Powellite, 105, Molybdenite, Scheelite);
        addBedrockDrillRecipe(Redstone, 79, Cinnabar, RareEarth, Glowstone);
        addBedrockDrillRecipe(Scheelite, 79, Pyrolusite, Molybdenite, Calcite);
        addBedrockDrillRecipe(Sphalerite, 45, Cadmium, Gallium, Zinc, Selenium, Indium);
        addBedrockDrillRecipe(Stibnite, 35, Antimony, Cinnabar, Galena, Pyrite, Barite, Calcite);
        addBedrockDrillRecipe(Tungstate, 79, Pyrolusite, Silver, LithiumChloride);
        addBedrockDrillRecipe(Uraninite, 63, Lead, Radium, RareEarth, Thorium);
    }

    public static void addBedrockDrillRecipe(Material main, int byproductChance, Material... byproduct){
        RecipeBuilder rb = BEDROCK_DRILL.RB();
        List<Integer> chances = new ArrayList<>();
        chances.add(9687);
        rb.ii(RecipeIngredient.of(1, ORE.getMaterialTag(main, VanillaStoneTypes.BEDROCK), ORE_SMALL.getMaterialTag(main, VanillaStoneTypes.BEDROCK))).fi(Materials.Lubricant.getLiquid(100)).io(RAW_ORE.get(main));
        for (Material m : byproduct){
            if (m.has(RAW_ORE)) {
                rb.io(RAW_ORE.get(m));
            } else if (m.has(DUST)){
                rb.io(DUST.get(m));
            } else continue;
            chances.add(byproductChance);
        }
        rb.io(DUST.get(Bedrock));
        chances.add(10);
        ItemStack cobble = new ItemStack(Items.COBBLESTONE);
        cobble.setHoverName(Utils.literal("Various Cobblestone Types"));
        rb.io(cobble);
        chances.add(10000);
        rb.outputChances(chances.stream().mapToInt(i -> i).toArray());
        rb.add("bedrock_" + main.getId() + "_ore", 1, 32768);
    }
}
