package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gtlib.material.Material;

import static org.gtreimagined.gtlib.Ref.L;
import static org.gtreimagined.gtlib.Ref.L9;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.DUST;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.DUST_TINY;
import static org.gtreimagined.gtlib.data.GTLibMaterials.Iron;
import static org.gtreimagined.gt5r.data.GT5RMaterialTypes.BOULE;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.CRYSTALLIZATION_CHAMBER;

public class CrystallizationChamberLoader {
    public static void init(){
        addRecipe(Silicon);
        addSapphireRecipe(Chromium, Ruby);
        addSapphireRecipe(Iron, Sapphire);
        addSapphireRecipe(Magnesium, GreenSapphire);
    }
    private static void addSapphireRecipe(Material input, Material output){
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST_TINY.getMaterialIngredient(input, 6))
                .fi(Alumina.getLiquid(L9 * 105), Krypton.getGas(1000))
                .io(BOULE.get(output)).add(output.getId() + "_boule_with_krypton_tiny", 4500, 256, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST_TINY.getMaterialIngredient(input, 6))
                .fi(Alumina.getLiquid(L9 * 105), Helium.getGas(1000))
                .io(BOULE.get(output)).add(output.getId() + "_boule_with_helium_tiny", 4500, 256, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST_TINY.getMaterialIngredient(input, 6))
                .fi(Alumina.getLiquid(L9 * 105), Argon.getGas(1000))
                .io(BOULE.get(output)).add(output.getId() + "_boule_with_argon_tiny", 4500, 256, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST_TINY.getMaterialIngredient(input, 6))
                .fi(Alumina.getLiquid(L9 * 105), Neon.getGas(1000))
                .io(BOULE.get(output)).add(output.getId() + "_boule_with_neon_tiny", 4500, 256, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST_TINY.getMaterialIngredient(input, 6))
                .fi(Alumina.getLiquid(L9 * 105), Radon.getGas(1000))
                .io(BOULE.get(output)).add(output.getId() + "_boule_with_radon_tiny", 4500, 256, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST_TINY.getMaterialIngredient(input, 6))
                .fi(Alumina.getLiquid(L9 * 105), Xenon.getGas(1000))
                .io(BOULE.get(output)).add(output.getId() + "_boule_with_xenon_tiny", 4500, 256, 0, 4);

        CRYSTALLIZATION_CHAMBER.RB().ii(DUST.getMaterialIngredient(input, 2))
                .fi(Alumina.getLiquid(L * 35), Krypton.getGas(3000))
                .io(BOULE.get(output, 3)).add(output.getId() + "_boule_with_krypton", (10 * 60 * 20) + (45 * 20), 256, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST.getMaterialIngredient(input, 2))
                .fi(Alumina.getLiquid(L * 35), Helium.getGas(3000))
                .io(BOULE.get(output, 3)).add(output.getId() + "_boule_with_helium", (10 * 60 * 20) + (45 * 20), 256, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST.getMaterialIngredient(input, 2))
                .fi(Alumina.getLiquid(L * 35), Argon.getGas(3000))
                .io(BOULE.get(output, 3)).add(output.getId() + "_boule_with_argon", (10 * 60 * 20) + (45 * 20), 256, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST.getMaterialIngredient(input, 2))
                .fi(Alumina.getLiquid(L * 35), Neon.getGas(3000))
                .io(BOULE.get(output, 3)).add(output.getId() + "_boule_with_neon", (10 * 60 * 20) + (45 * 20), 256, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST.getMaterialIngredient(input, 2))
                .fi(Alumina.getLiquid(L * 35), Radon.getGas(3000))
                .io(BOULE.get(output, 3)).add(output.getId() + "_boule_with_radon", (10 * 60 * 20) + (45 * 20), 256, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST.getMaterialIngredient(input, 2))
                .fi(Alumina.getLiquid(L * 35), Xenon.getGas(3000))
                .io(BOULE.get(output, 3)).add(output.getId() + "_boule_with_xenon", (10 * 60 * 20) + (45 * 20), 256, 0, 4);
    }

    private static void addRecipe(Material material){
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST_TINY.getMaterialIngredient(material, 1))
                .fi(material.getLiquid((L * 3) + (L9 * 8)), Krypton.getGas(1000))
                .io(BOULE.get(material)).add(material.getId() + "_boule_with_krypton_tiny", 15 * 60 * 20, 16, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST_TINY.getMaterialIngredient(material, 1))
                .fi(material.getLiquid((L * 3) + (L9 * 8)), Helium.getGas(1000))
                .io(BOULE.get(material)).add(material.getId() + "_boule_with_helium_tiny", 15 * 60 * 20, 16, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST_TINY.getMaterialIngredient(material, 1))
                .fi(material.getLiquid((L * 3) + (L9 * 8)), Argon.getGas(1000))
                .io(BOULE.get(material)).add(material.getId() + "_boule_with_argon_tiny", 15 * 60 * 20, 16, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST_TINY.getMaterialIngredient(material, 1))
                .fi(material.getLiquid((L * 3) + (L9 * 8)), Neon.getGas(1000))
                .io(BOULE.get(material)).add(material.getId() + "_boule_with_neon_tiny", 15 * 60 * 20, 16, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST_TINY.getMaterialIngredient(material, 1))
                .fi(material.getLiquid((L * 3) + (L9 * 8)), Radon.getGas(1000))
                .io(BOULE.get(material)).add(material.getId() + "_boule_with_radon_tiny", 15 * 60 * 20, 16, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST_TINY.getMaterialIngredient(material, 1))
                .fi(material.getLiquid((L * 3) + (L9 * 8)), Xenon.getGas(1000))
                .io(BOULE.get(material)).add(material.getId() + "_boule_with_xenon_tiny", 15 * 60 * 20, 16, 0, 4);

        CRYSTALLIZATION_CHAMBER.RB().ii(DUST.getMaterialIngredient(material, 1))
                .fi(material.getLiquid(L * 35), Krypton.getGas(9000))
                .io(BOULE.get(material, 9)).add(material.getId() + "_boule_with_krypton", 135 * 60 * 20, 16, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST.getMaterialIngredient(material, 1))
                .fi(material.getLiquid(L * 35), Helium.getGas(9000))
                .io(BOULE.get(material, 9)).add(material.getId() + "_boule_with_helium", 135 * 60 * 20, 16, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST.getMaterialIngredient(material, 1))
                .fi(material.getLiquid(L * 35), Argon.getGas(9000))
                .io(BOULE.get(material, 9)).add(material.getId() + "_boule_with_argon", 135 * 60 * 20, 16, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST.getMaterialIngredient(material, 1))
                .fi(material.getLiquid(L * 35), Neon.getGas(9000))
                .io(BOULE.get(material, 9)).add(material.getId() + "_boule_with_neon", 135 * 60 * 20, 16, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST.getMaterialIngredient(material, 1))
                .fi(material.getLiquid(L * 35), Radon.getGas(9000))
                .io(BOULE.get(material, 9)).add(material.getId() + "_boule_with_radon", 135 * 60 * 20, 16, 0, 4);
        CRYSTALLIZATION_CHAMBER.RB().ii(DUST.getMaterialIngredient(material, 1))
                .fi(material.getLiquid(L * 35), Xenon.getGas(9000))
                .io(BOULE.get(material, 9)).add(material.getId() + "_boule_with_xenon", 135 * 60 * 20, 16, 0, 4);
    }
}
