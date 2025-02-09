package org.gtreimagined.gt5r.blockentity.single;

import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.capability.IFilterableHandler;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.machine.event.IMachineEvent;
import muramasa.antimatter.machine.types.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.blockentity.IAutocrafter;
import org.gtreimagined.gt5r.machine.caps.AutocraftingRecipeHandler;
import org.gtreimagined.gtcore.data.GTCoreItems;

import java.util.Optional;

public class BlockEntityAutocrafter extends BlockEntityMachine<BlockEntityAutocrafter> implements IAutocrafter, IFilterableHandler {
    CraftingRecipe recipe;
    CraftingRecipe oldRecipe;
    public BlockEntityAutocrafter(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.recipeHandler.set(() -> new AutocraftingRecipeHandler<>(this, 1));
    }

    @Override
    public CraftingRecipe getRecipe() {
        return recipe;
    }

    @Override
    public CraftingRecipe getOldRecipe() {
        return oldRecipe;
    }

    @Override
    public void onMachineEvent(IMachineEvent event, Object... data) {
        if (event == SlotType.STORAGE){
            ItemStack blueprint = itemHandler.map(i -> i.getHandler(SlotType.STORAGE).getItem(0)).orElse(ItemStack.EMPTY);
            if (blueprint.isEmpty() && recipe != null){
                oldRecipe = recipe;
                recipe = null;
            } else if (!blueprint.isEmpty()){
                if (blueprint.getTag() != null && blueprint.getTag().contains("recipeId") && level != null){
                    ResourceLocation recipeId = new ResourceLocation(blueprint.getTag().getString("recipeId"));
                    Optional<? extends Recipe<?>> recipeOptional = level.getRecipeManager().byKey(recipeId);
                    if (recipeOptional.map(r -> r instanceof CraftingRecipe).orElse(false)){
                        CraftingRecipe recipe = (CraftingRecipe) recipeOptional.get();
                        if (oldRecipe == null || this.recipe == null) oldRecipe = recipe;
                        else oldRecipe = this.recipe;
                        this.recipe = recipe;
                    }
                } else if (recipe != null){
                    oldRecipe = recipe;
                    recipe = null;
                }
            }
        }
        super.onMachineEvent(event, data);
    }

    @Override
    public boolean test(SlotType<?> slotType, int slot, ItemStack stack) {
        if (slotType == SlotType.STORAGE) return stack.getItem() == GTCoreItems.Blueprint;
        return true;
    }
}
