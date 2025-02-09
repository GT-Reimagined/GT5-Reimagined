package org.gtreimagined.gt5r.blockentity;

import net.minecraft.world.item.crafting.CraftingRecipe;

public interface IAutocrafter {
    CraftingRecipe getRecipe();

    CraftingRecipe getOldRecipe();
}
