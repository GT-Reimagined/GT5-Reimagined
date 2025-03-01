package org.gtreimagined.gt5r.material;

import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.material.Material;
import net.minecraftforge.fluids.FluidStack;

public record FluidProduct(Material mat, int amount){
    public FluidStack convert(){
        return mat.has(AntimatterMaterialTypes.LIQUID) ? mat.getLiquid(amount) : mat.getGas(amount);
    }
}
