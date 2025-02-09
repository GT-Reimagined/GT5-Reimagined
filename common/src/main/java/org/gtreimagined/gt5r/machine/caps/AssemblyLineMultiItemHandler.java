package org.gtreimagined.gt5r.machine.caps;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import muramasa.antimatter.Data;
import muramasa.antimatter.Ref;
import muramasa.antimatter.blockentity.multi.BlockEntityMultiMachine;
import muramasa.antimatter.capability.item.ITrackedHandler;
import muramasa.antimatter.capability.item.MultiTrackedItemHandler;
import muramasa.antimatter.capability.machine.MachineItemHandler;
import muramasa.antimatter.capability.machine.MultiMachineItemHandler;
import muramasa.antimatter.recipe.IRecipe;
import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import tesseract.api.item.ExtendedItemContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AssemblyLineMultiItemHandler<T extends BlockEntityMultiMachine<T>> extends MultiMachineItemHandler<T> {
    List<ITrackedHandler> inputList = new ArrayList<>();
    public AssemblyLineMultiItemHandler(T tile) {
        super(tile);
    }

    /**
     * Consumes the inputs from the active recipe.
     *
     * @param recipe   active recipe.
     * @param simulate whether to execute or just return items.
     * @return a list of consumed items, or an empty list if it failed during simulation.
     */
    @Override
    public List<ItemStack> consumeInputs(IRecipe recipe, boolean simulate) {
        if (recipe.getInputItems().size() > inputList.size()) return Collections.emptyList();
        boolean chance = !simulate && recipe.hasInputChances();
        int[] chances = recipe.getInputChances();
        List<ItemStack> consumed = new ArrayList<>();
        for (int i = 0; i < recipe.getInputItems().size(); i++) {
            if (!chance || Ref.RNG.nextInt(10000) < chances[i]){
                consumed.addAll(consumeInput(recipe.getInputItems().get(i), inputList.get(i), !chance && simulate));
            } else {
                consumed.add(Data.DEBUG_SCANNER.get(1)); //so the consumeInputs returns true
            }
        }
        return consumed;
    }

    public List<ItemStack> consumeInput(Ingredient input, ITrackedHandler container, boolean simulate) {
        if (input == null) return Collections.emptyList();
        IntSet skipSlots = new IntOpenHashSet(getInputHandler().getContainerSize());
        List<ItemStack> consumedItems = new ObjectArrayList<>();

        int failed = 0;
        int countToReach = RecipeIngredient.count(input);
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack item = container.getItem(i);
            if (input.test(item) && !skipSlots.contains(i)) {
                int toConsume = Math.min(item.getCount(), Math.max(countToReach - item.getCount(), countToReach));
                countToReach -= toConsume;
                skipSlots.add(i);
                ItemStack copy = item.copy();
                copy.setCount(toConsume);
                consumedItems.add(copy);
                if (!RecipeIngredient.ignoreConsume(input) && !simulate) container.extractFromInput(i, toConsume, simulate);
                if (countToReach == 0) {
                    break;
                }
            }
            if (i == container.getContainerSize() - 1) {
                failed++;
            }
        }
        boolean success = failed == 0;
        //onSlotChanged should call dirty though, not sure if needed.
        if (!simulate && success) tile.setChanged();
        if (simulate) return success ? consumedItems : Collections.emptyList();
        return consumedItems;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        inputList.clear();
    }

    @Override
    protected ITrackedHandler calculateInputs() {
        inputList = tile.getComponentsByHandlerId(inputComponentString()).stream().filter(t -> t.getItemHandler().isPresent()).map(t -> t.getItemHandler().get()).sorted(this::compareInputBuses).map(MachineItemHandler::getInputHandler).collect(Collectors.toList());
        return new MultiTrackedItemHandler(inputList.toArray(new ExtendedItemContainer[0]));
    }
}
