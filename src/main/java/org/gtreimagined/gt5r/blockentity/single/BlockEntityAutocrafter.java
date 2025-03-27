package org.gtreimagined.gt5r.blockentity.single;

import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.IFilterableHandler;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.machine.event.IMachineEvent;
import org.gtreimagined.gtlib.machine.types.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.blockentity.IAutocrafter;
import org.gtreimagined.gt5r.machine.caps.AutocraftingRecipeHandler;
import org.gtreimagined.gtcore.data.GTCoreItems;

public class BlockEntityAutocrafter extends BlockEntityMachine<BlockEntityAutocrafter> implements IAutocrafter, IFilterableHandler {
    CraftingRecipe recipe;
    CraftingRecipe oldRecipe;
    public BlockEntityAutocrafter(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.recipeHandler.set(() -> new AutocraftingRecipeHandler<>(this, 1));
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
        return stack.getItem() != GTCoreItems.Blueprint;
    }
}
