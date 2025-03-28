package org.gtreimagined.gt5r.material;

import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.material.Material;
import net.minecraftforge.fluids.FluidStack;

public record FluidProduct(Material mat, int amount){
    public FluidStack convert(){
        return mat.has(GTMaterialTypes.LIQUID) ? mat.getLiquid(amount) : mat.getGas(amount);
    }
}
