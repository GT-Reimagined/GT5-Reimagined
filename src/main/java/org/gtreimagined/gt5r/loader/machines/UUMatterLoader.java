package org.gtreimagined.gt5r.loader.machines;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gt5r.data.GT5RFluids;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.recipe.map.RecipeBuilder;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gt5r.data.RecipeMaps;
import org.gtreimagined.gtcore.data.GTCoreItems;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gt5r.data.Materials.UUAmplifier;

public class UUMatterLoader {
    public static void init(){
        //RecipeMaps.MASS_FABRICATOR.RB().fi(UUAmplifier.getLiquid(1)).fo(Materials.UUMatter.getLiquid(1)).add("uu_matter", 803, 256);
        //RecipeMaps.MASS_FABRICATOR.RB().ii(GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(0)).fo(Materials.UUMatter.getLiquid(1)).add("uu_matter_expensive", 3215, 256);
        GTAPI.all(Material.class).stream().filter(m -> m.getElement() != null && (m.has(DUST) || m.has(LIQUID) || m.has(GAS))).forEach(m -> {
            RecipeBuilder b = RecipeMaps.MASS_FABRICATOR.RB();
            RecipeBuilder sb = RecipeMaps.SCANNER.RB();
            if (m.has(GTMaterialTypes.DUST)){
                b.ii(DUST.getMaterialIngredient(m, 1)); sb.ii(DUST.getMaterialIngredient(m, 1));
            } else if (m.has(LIQUID)){
                b.fi(m.getLiquid(1000)); sb.ii(GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(0)).fi(m.getLiquid(1000));
            } else if (m.has(GAS)){
                b.fi(m.getGas(1000)); sb.ii(GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(0)).fi(m.getGas(1000));
            }
            ItemStack dataOrb = new ItemStack(GTCoreItems.DataOrb);
            dataOrb.getOrCreateTag().putString("scanned_gt_material", m.getId());
            sb.ii(GTCoreItems.DataOrb).io(dataOrb.copy()).add(m.getId() + "_scanning", m.getMass() * 8192, 32);
            if (m.getProtons() > 0){
                b.fo(new FluidStack(GT5RFluids.CHARGED_MATTER.getFluid(), (int)m.getProtons()));
            }
            if (m.getNeutrons() > 0){
                b.fo(new FluidStack(GT5RFluids.NEUTRAL_MATTER.getFluid(), (int)m.getNeutrons()));
            }
            b.add("matter_from_" + m.getId(), 100, 32);
        });
    }
}
