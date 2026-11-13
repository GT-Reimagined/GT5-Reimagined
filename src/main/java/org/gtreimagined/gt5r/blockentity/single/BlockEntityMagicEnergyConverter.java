package org.gtreimagined.gt5r.blockentity.single;

import net.minecraft.world.item.Items;
import org.gtreimagined.gt5r.data.RecipeMaps;
import org.gtreimagined.gtlib.blockentity.single.BlockEntityGenerator;
import org.gtreimagined.gtlib.capability.item.ITrackedHandler;
import org.gtreimagined.gtlib.capability.machine.MachineEnergyHandler;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.SlotTypes;
import org.gtreimagined.gtlib.item.ItemFluidCell;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

import java.util.Map;

public class BlockEntityMagicEnergyConverter extends BlockEntityGenerator<BlockEntityMagicEnergyConverter> {
    long leftoverToInsert;
    public BlockEntityMagicEnergyConverter(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.recipeHandler.set(() -> new MachineRecipeHandler<>(this){
            @Override
            public IRecipe findRecipe() {
                IRecipe recipe = super.findRecipe();
                if (recipe == null) {
                    recipe = itemHandler.map(i -> i).map(i -> {
                        ItemStack input = i.getInputHandler().getStackInSlot(0);
                        long eu = BlockEntityMagicEnergyConverter.this.euFromItem(input);
                        if (eu > 0){
                            ItemStack output = input.getItem() == Items.ENCHANTED_BOOK ? new ItemStack(Items.BOOK) : input.copy();
                            EnchantmentHelper.setEnchantments(Map.of(), output);
                            return RecipeMaps.MAGIC_FUELS.RB().recipeMapOnly().ii(RecipeIngredient.of(input.copy())).io(output).add("enchanted_item", 1, eu);
                        }
                        return null;
                    }).orElse(null);
                }
                return recipe;
            }

            @Override
            public boolean accepts(ItemStack stack) {
                return super.accepts(stack) || !EnchantmentHelper.getEnchantments(stack).isEmpty();
            }
        });
        this.itemHandler.set(() -> new MachineItemHandler<>(this){
            @Override
            public ITrackedHandler getCellInputHandler() {
                return super.getInputHandler();
            }

            @Override
            public ITrackedHandler getCellOutputHandler() {
                return super.getOutputHandler();
            }

            @Override
            public ITrackedHandler getHandler(SlotType<?> type) {
                if (type == SlotTypes.CELL_IN) type = SlotTypes.IT_IN;
                if (type == SlotTypes.CELL_OUT) type = SlotTypes.IT_OUT;
                return super.getHandler(type);
            }
        });
    }


    private long euFromItem(ItemStack tStack) {
        if (tStack.isEmpty()) return 0;
        long tEU = 0;
        // Convert enchantments to their EU Value
        Map<Enchantment, Integer> tMap = EnchantmentHelper.getEnchantments(tStack);
        for (Map.Entry<Enchantment, Integer> e : tMap.entrySet()) {
            Enchantment ench = e.getKey();
            Integer tLevel = e.getValue();
            tEU += 1000000L * tLevel / ench.getMaxLevel() / ench.getRarity().getWeight();
        }
        return tEU;
    }
}
