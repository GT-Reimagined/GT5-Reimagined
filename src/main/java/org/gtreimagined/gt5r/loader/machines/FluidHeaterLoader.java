package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gt5r.GT5RConfig;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.FLUID_HEATER;

public class FluidHeaterLoader {
    public static void init(){
        if (GT5RConfig.COMPLICATED_CHEMICAL_PROCESSING.get()) {
            FLUID_HEATER.RB().fi(CalciumAcetateSolution.getLiquid(180)).fo(Acetone.getLiquid(100)).add("acetone", 16, 30);
        }
        FLUID_HEATER.RB().fi(DistilledWater.getLiquid(6)).fo(Steam.getGas(960)).add("steam", 30, 32);
        FLUID_HEATER.RB().fi(Water.getLiquid(6)).fo(Steam.getGas(960)).add("steam_2", 30, 32);
    }
}
