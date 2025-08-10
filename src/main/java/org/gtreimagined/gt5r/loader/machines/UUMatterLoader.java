package org.gtreimagined.gt5r.loader.machines;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gt5r.data.GT5RFluids;
import org.gtreimagined.gt5r.data.RecipeMaps;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.recipe.map.RecipeBuilder;

import static org.gtreimagined.gt5r.data.GT5RItems.DataOrb;
import static org.gtreimagined.gt5r.data.Materials.Nitrogen;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class UUMatterLoader {
    public static void init(){
        GTAPI.all(Material.class).stream().filter(m -> m.getElement() != null && (m.has(DUST) || m.has(LIQUID) || m.has(GAS))).forEach(m -> {
            RecipeBuilder mb = RecipeMaps.MASS_FABRICATOR.RB();
            RecipeBuilder sb = RecipeMaps.SCANNER.RB();
            RecipeBuilder rb = RecipeMaps.REPLICATOR.RB();
            if (m.has(GTMaterialTypes.DUST)){
                mb.ii(DUST.getMaterialIngredient(m, 1)); sb.ii(DUST.getMaterialIngredient(m, 1));
                rb.io(DUST.get(m, 1));
            } else if (m.has(LIQUID) && m != Nitrogen){
                mb.fi(m.getLiquid(1000)); sb.ii(GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(0).get()).fi(m.getLiquid(1000));
                rb.fo(m.getLiquid(1000));
            } else if (m.has(GAS)){
                mb.fi(m.getGas(1000)); sb.ii(GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(0).get()).fi(m.getGas(1000));
                rb.fo(m.getGas(1000));
            }
            ItemStack dataOrb = new ItemStack(DataOrb);
            dataOrb.getOrCreateTag().putString("scanned_gt_material", m.getId());
            sb.ii(DataOrb).io(dataOrb.copy()).add(m.getId() + "_scanning", m.getMass() * 8192, 32);
            if (m.getProtons() > 0){
                mb.fo(new FluidStack(GT5RFluids.CHARGED_MATTER.getFluid(), (int)m.getProtons()));
                rb.fi(new FluidStack(GT5RFluids.CHARGED_MATTER.getFluid(), (int)m.getProtons()));
            }
            if (m.getNeutrons() > 0){
                mb.fo(new FluidStack(GT5RFluids.NEUTRAL_MATTER.getFluid(), (int)m.getNeutrons()));
                rb.fi(new FluidStack(GT5RFluids.NEUTRAL_MATTER.getFluid(), (int)m.getNeutrons()));
            }
            rb.ii(RecipeIngredient.of(dataOrb).setNoConsume()).add(m.getId(), m.getMass() * 32, 8);
            mb.add("matter_from_" + m.getId(), m.getMass() * 16384, 8);
        });
    }
}
