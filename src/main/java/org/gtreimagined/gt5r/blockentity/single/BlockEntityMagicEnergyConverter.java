package org.gtreimagined.gt5r.blockentity.single;

import muramasa.antimatter.blockentity.single.BlockEntityGenerator;
import muramasa.antimatter.capability.item.ITrackedHandler;
import muramasa.antimatter.capability.machine.MachineEnergyHandler;
import muramasa.antimatter.capability.machine.MachineItemHandler;
import muramasa.antimatter.capability.machine.MachineRecipeHandler;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.item.ItemFluidCell;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.recipe.IRecipe;
import muramasa.antimatter.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import tesseract.TesseractGraphWrappers;

import java.util.Map;

public class BlockEntityMagicEnergyConverter extends BlockEntityGenerator<BlockEntityMagicEnergyConverter> {
    long leftoverToInsert;
    public BlockEntityMagicEnergyConverter(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.recipeHandler.set(() -> new MachineRecipeHandler<>(this){
            @Override
            protected boolean consumeGeneratorResources(boolean simulate) {
                MachineEnergyHandler<?> handler = tile.energyHandler.orElse(null);
                if (handler == null) return false;
                if (leftoverToInsert > 0) {
                    long inserted = handler.insertInternal(leftoverToInsert, simulate);
                    if (inserted > 0) {
                        if (!simulate){
                            leftoverToInsert -= inserted;
                        }
                        return true;
                    }
                    return false;
                }
                int toConsume = consumedFluidPerOperation(activeRecipe);
                long toInsert = calculateGeneratorProduction(activeRecipe);
                if (activeRecipe.hasInputItems()){

                }
                FluidStack mFluid = tile.fluidHandler.map(f -> f.getInputTanks().getTank(0).getFluid()).orElse(FluidStack.EMPTY);
                if (mFluid.isEmpty()) return false;
                int fluidAmount = mFluid.getAmount();
                if (toInsert > 0 && toConsume > 0 && fluidAmount > toConsume) {
                    int tFluidAmountToUse = (int) Math.min(fluidAmount / toConsume, (handler.getCapacity() - handler.getEnergy()) / toInsert);
                    if (tFluidAmountToUse > 0 && handler.insertInternal(tFluidAmountToUse * toInsert, true) == tFluidAmountToUse * toInsert) {
                        if (tile.getLevel().getGameTime() % 10 == 0 && !simulate){
                            handler.insertInternal(tFluidAmountToUse * toInsert, false);
                            tile.fluidHandler.ifPresent(f -> f.drainInput(Utils.ca(tFluidAmountToUse * toConsume, mFluid), FluidAction.EXECUTE));
                        }
                        return true;
                    }
                }
                return false;
            }

            @Override
            public int consumedFluidPerOperation(IRecipe r) {
                if (r.hasInputItems()) return 1;
                return super.consumedFluidPerOperation(r);
            }

            @Override
            public boolean accepts(ItemStack stack) {
                return super.accepts(stack) || stack.getItem() instanceof ItemFluidCell;
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
                if (type == SlotType.CELL_IN) type = SlotType.IT_IN;
                if (type == SlotType.CELL_OUT) type = SlotType.IT_OUT;
                return super.getHandler(type);
            }
        });
    }


    private long euFromItem(ItemStack tStack) {
        if (tStack.isEmpty()) return 0;
        if (!tStack.isEnchanted()) return 0;
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
