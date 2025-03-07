package org.gtreimagined.gt5r.blockentity.multi;

import muramasa.antimatter.blockentity.multi.BlockEntityMultiMachine;
import muramasa.antimatter.capability.IFilterableHandler;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.machine.event.IMachineEvent;
import muramasa.antimatter.machine.types.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.blockentity.IAutocrafter;
import org.gtreimagined.gt5r.machine.caps.AssemblyLineMultiItemHandler;
import org.gtreimagined.gt5r.machine.caps.AutocraftingRecipeHandler;
import org.gtreimagined.gtcore.data.GTCoreItems;

import java.util.Optional;

public class BlockEntityBasicAssemblyLine extends BlockEntityMultiMachine<BlockEntityBasicAssemblyLine> implements IAutocrafter, IFilterableHandler {
    CraftingRecipe recipe;
    CraftingRecipe oldRecipe;
    public BlockEntityBasicAssemblyLine(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemHandler.set(() -> new AssemblyLineMultiItemHandler<>(this));
        this.recipeHandler.set(() -> new AutocraftingRecipeHandler<>(this, 16));
    }

    @Override
    public void onFirstTickServer(Level level, BlockPos pos, BlockState state) {
        super.onFirstTickServer(level, pos, state);
        onMachineEvent(SlotType.STORAGE);
    }

    @Override
    public CraftingRecipe getRecipe() {
        return recipe;
    }

    @Override
    public void setRecipe(CraftingRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onMachineEvent(IMachineEvent event, Object... data) {
        if (event == SlotType.STORAGE){
            initRecipe(this);
        }
        super.onMachineEvent(event, data);
    }

    @Override
    public boolean test(SlotType<?> slotType, int slot, ItemStack stack) {
        if (slotType == SlotType.STORAGE) return stack.getItem() == GTCoreItems.Blueprint;
        return true;
    }
}
