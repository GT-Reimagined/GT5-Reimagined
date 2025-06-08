package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gt5r.data.RecipeMaps;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient.of;

public class RoasterLoader {
    public static void init(){
        RecipeMaps.ROASTER.RB().fi(Oxygen.getGas(500)).ii(DUST.getMaterialIngredient(Galena, 1)).io(SMALL_DUST.get(Silver), SMALL_DUST.get(Lead)).fo(SulfurDioxide.getGas(500)).add("galena", 512, 5, 0, 3);
        RecipeMaps.ROASTER.RB().fi(Oxygen.getGas(1375)).ii(DUST.getMaterialIngredient(Chalcopyrite, 1)).io(SMALL_DUST.get(Copper), SMALL_DUST.get(Iron)).fo(SulfurDioxide.getGas(1000)).add("chalcopyrite", 512, 5, 0, 3);
        RecipeMaps.ROASTER.RB().fi(Oxygen.getGas(1834)).ii(DUST.getMaterialIngredient(Pyrite, 1)).io(SMALL_DUST.get(Iron)).fo(SulfurDioxide.getGas(1000)).add("pyrite", 512, 5, 0, 3);
        RecipeMaps.ROASTER.RB().fi(Oxygen.getGas(938)).ii(DUST.getMaterialIngredient(Tetrahedrite, 1)).io(SMALL_DUST.get(Copper), SMALL_DUST.get(Antimony), TINY_DUST.get(Iron)).fo(SulfurDioxide.getGas(500)).add("tetrahedrite", 512, 5, 0, 3);
        RecipeMaps.ROASTER.RB().fi(Oxygen.getGas(1334)).ii(DUST.getMaterialIngredient(Molybdenite, 1)).io(SMALL_DUST.get(Molybdenum)).fo(SulfurDioxide.getGas(200)).add("molybdenite", 512, 5, 0, 3);
        RecipeMaps.ROASTER.RB().fi(Oxygen.getGas(942)).ii(DUST.getMaterialIngredient(Pentlandite, 1)).io(SMALL_DUST.get(Nickel, 2)).fo(SulfurDioxide.getGas(1000)).add("pentlandite", 512, 5, 0, 3);
        RecipeMaps.ROASTER.RB().fi(Oxygen.getGas(1200)).ii(DUST.getMaterialIngredient(Stibnite, 1)).io(SMALL_DUST.get(Antimony)).fo(SulfurDioxide.getGas(1000)).add("stibnite", 512, 5, 0, 3);
        RecipeMaps.ROASTER.RB().fi(Oxygen.getGas(1000)).ii(DUST.getMaterialIngredient(Sphalerite, 1)).io(SMALL_DUST.get(Zinc, 2)).fo(SulfurDioxide.getGas(1000)).add("sphalerite", 512, 5, 0, 3);
        RecipeMaps.ROASTER.RB().fi(Oxygen.getGas(667)).ii(DUST.getMaterialIngredient(Cobaltite, 1)).io(SMALL_DUST.get(Cobalt), SMALL_DUST.get(Arsenic)).fo(SulfurDioxide.getGas(1000)).add("cobaltite", 512, 5, 0, 3);
        RecipeMaps.ROASTER.RB().fi(Air.getGas(8000)).ii(DUST.getMaterialIngredient(Sulfur, 1)).fo(SulfurDioxide.getGas(3000)).add("sulfur_air", 512, 5, 0, 3);
        RecipeMaps.ROASTER.RB().fi(Oxygen.getGas(2000)).ii(DUST.getMaterialIngredient(Sulfur, 1)).fo(SulfurDioxide.getGas(3000)).add("sulfur", 512, 5, 0, 3);
        RecipeMaps.ROASTER.RB().ii(of(DUST.getMaterialTag(Calcite), 5)).fo(CarbonDioxide.getGas(3000)).io(DUST.get(Quicklime, 2)).add("quicklime",240, 30);
    }
}
