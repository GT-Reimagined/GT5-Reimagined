package org.gtreimagined.gt5r.loader.machines;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

import static org.gtreimagined.gt5r.data.RecipeMaps.ORE_BYPRODUCTS;

public class OreByproducts {
    public static void init() {
        GTMaterialTypes.CRUSHED.all().forEach(m -> {
            if (!m.has(GTMaterialTypes.ORE)) return;
            if (!m.has(GTMaterialTypes.INGOT)) return;
            RecipeIngredient ore = GTMaterialTypes.ORE.getMaterialIngredient(m, 1), crushed = GTMaterialTypes.CRUSHED.getIngredient(m, 1);
            if (m.hasByProducts()) {
                List<Material> byProducts = m.getByProducts();
                int byProductsCount = byProducts.size();

                List<Ingredient> ores = new ObjectArrayList<>();
                if (m.has(GTMaterialTypes.ORE)) ores.add(ore);
                if (m.has(GTMaterialTypes.BEARING_ROCK)) ores.add(GTMaterialTypes.BEARING_ROCK.getIngredient(m, 1));
                if (m.has(GTMaterialTypes.CRUSHED)) ores.add(crushed);
                if (m.has(GTMaterialTypes.CRUSHED_PURIFIED)) ores.add(GTMaterialTypes.CRUSHED_PURIFIED.getIngredient(m, 1));
                if (m.has(GTMaterialTypes.CRUSHED_REFINED)) ores.add(GTMaterialTypes.CRUSHED_REFINED.getIngredient(m, 1));


                List<ItemStack> dusts = new ObjectArrayList<>(byProductsCount);
                byProducts.forEach(p -> dusts.add(GTMaterialTypes.DUST.get(p, 1)));
                ORE_BYPRODUCTS.RB().ii(ores).io(dusts.toArray(new ItemStack[byProductsCount])).add(m.getId() + "_byproducts");
            }
        });
    }
}
