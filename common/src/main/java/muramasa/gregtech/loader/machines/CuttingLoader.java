package muramasa.gregtech.loader.machines;

import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.data.AntimatterMaterials;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.MaterialTags;
import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import muramasa.gregtech.data.Materials;
import muramasa.gregtech.data.RecipeMaps;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import static muramasa.gregtech.data.RecipeMaps.CUTTING;

public class CuttingLoader {
    public static void init() {
        for (Material mat : AntimatterMaterialTypes.PLATE.all()) {
            if (!mat.has(AntimatterMaterialTypes.BLOCK))
                continue;
            int multiplier = mat.has(AntimatterMaterialTypes.GEM) ? 8 : 3;
            if (mat == AntimatterMaterials.Diamond)
                multiplier = 20;
            int count = mat.has(MaterialTags.QUARTZ_LIKE_BLOCKS) ? 4 : 9;
            CUTTING.RB().ii(RecipeIngredient.of(AntimatterMaterialTypes.BLOCK.getMaterialTag(mat), 1)).io(AntimatterMaterialTypes.PLATE.get(mat, count))
                    .fi(new FluidStack(Fluids.WATER, 1000)).add("plate_" + mat.getId() + "_with_water",mat.getMass() * 2 * multiplier, 24);
            CUTTING.RB().ii(RecipeIngredient.of(AntimatterMaterialTypes.BLOCK.getMaterialTag(mat), 1)).io(AntimatterMaterialTypes.PLATE.get(mat, count))
                    .fi(Materials.Lubricant.getLiquid(250)).add("plate_" + mat.getId() + "_with_lubricant",mat.getMass() * multiplier, 16);

        }
        AntimatterMaterialTypes.BOLT.all().forEach(t -> {
            if (t.has(AntimatterMaterialTypes.ROD)) {
                RecipeMaps.CUTTING.RB().ii(RecipeIngredient.of(AntimatterMaterialTypes.ROD.getMaterialTag(t), 1))
                        .fi(new FluidStack(Fluids.WATER, 1000))
                        .io(AntimatterMaterialTypes.BOLT.get(t, 2)).add("bolt_" + t.getId() + "_with_water",t.getMass()/4, 8);
                RecipeMaps.CUTTING.RB().ii(RecipeIngredient.of(AntimatterMaterialTypes.ROD.getMaterialTag(t), 1))
                        .fi(Materials.Lubricant.getLiquid(250))
                        .io(AntimatterMaterialTypes.BOLT.get(t, 2)).add("bolt_" + t.getId() + "_with_lubricant",t.getMass()/4, 8);
            }
        });
    }
}
