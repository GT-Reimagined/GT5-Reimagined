package org.gtreimagined.gt5r.integration.xei;

import muramasa.antimatter.recipe.IRecipe;
import org.jetbrains.annotations.Nullable;

public record OreByProduct(IRecipe maceratorOre, IRecipe oreWasher, IRecipe maceratorCrushed, IRecipe centrifugeImpure, IRecipe maceratorRefined,
                           IRecipe thermalCentrifuge, IRecipe maceratorPurified, IRecipe centrifugePure,
                           @Nullable IRecipe bath, @Nullable IRecipe furnace, @Nullable IRecipe sifter, @Nullable IRecipe seperator) {
}
