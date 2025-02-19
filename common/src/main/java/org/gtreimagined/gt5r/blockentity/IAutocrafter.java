package org.gtreimagined.gt5r.blockentity;

import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.gui.SlotType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;

public interface IAutocrafter {
    CraftingRecipe getRecipe();

    void setRecipe(CraftingRecipe recipe);

    default void initRecipe(BlockEntityMachine<?> machine) {
        ItemStack blueprint = machine.itemHandler.map(i -> i.getHandler(SlotType.STORAGE).getStackInSlot(0)).orElse(ItemStack.EMPTY);
        if (blueprint.isEmpty() && getRecipe() != null){
            setRecipe(null);
        } else if (!blueprint.isEmpty()){
            if (blueprint.getTag() != null && blueprint.getTag().contains("recipeId") && machine.getLevel() != null){
                ResourceLocation recipeId = new ResourceLocation(blueprint.getTag().getString("recipeId"));
                Optional<? extends Recipe<?>> recipeOptional = machine.getLevel().getRecipeManager().byKey(recipeId);
                if (recipeOptional.map(r -> r instanceof CraftingRecipe).orElse(false)){
                    setRecipe((CraftingRecipe) recipeOptional.get());
                }
            } else if (getRecipe() != null){
                setRecipe(null);
            }
        }
    }
}
