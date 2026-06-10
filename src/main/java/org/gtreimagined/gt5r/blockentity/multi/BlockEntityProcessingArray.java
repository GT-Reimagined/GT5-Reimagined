package org.gtreimagined.gt5r.blockentity.multi;

import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.capability.IFilterableHandler;
import org.gtreimagined.gtlib.capability.item.TrackedItemHandler;
import org.gtreimagined.gtlib.capability.machine.MultiMachineItemHandler;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.machine.BlockMachine;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.machine.event.IMachineEvent;
import org.gtreimagined.gtlib.machine.types.BasicMachine;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.recipe.map.IRecipeMap;
import org.gtreimagined.gtlib.recipe.map.RecipeMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.machine.caps.ParallelRecipeHandler;

public class BlockEntityProcessingArray extends BlockEntityParallelMultiblock<BlockEntityProcessingArray> implements IFilterableHandler {

    public BlockEntityProcessingArray(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemHandler.set(() -> new MultiMachineItemHandler<>(this){
            @Override
            protected TrackedItemHandler<BlockEntityProcessingArray> createTrackedHandler(SlotType<?> type, BlockEntityProcessingArray tile) {
                if (type == SlotType.STORAGE){
                    return new TrackedItemHandler<>(tile, type, 1, type.isOutput(), type.isInput(), type.getTester(), 16);
                }
                return super.createTrackedHandler(type, tile);
            }
        });
        this.recipeHandler.set(() -> new ParallelRecipeHandler<>(this, 1){
            IRecipeMap recipeMap = null;
            Tier tier = null;



            @Override
            protected int maxSimultaneousRecipes(){
                return itemHandler.map(i -> i.getHandler(SlotType.STORAGE).getStackInSlot(0).getCount()).orElse(0);
            }

            @Override
            public void onMachineEvent(IMachineEvent event, Object... data) {
                if (event == SlotType.STORAGE){
                    IRecipeMap oldRecipeMap = recipeMap;
                    this.tier = null;
                    ItemStack stack = itemHandler.map(i -> i.getHandler(SlotType.STORAGE).getStackInSlot(0)).orElse(ItemStack.EMPTY);
                    if (stack.getItem() instanceof BlockItem blockItem){
                        if (blockItem.getBlock() instanceof BlockMachine machine && machine.getType() instanceof BasicMachine){
                            if (machine.getType().getRecipeMap(machine.getTier()) != null){
                                this.recipeMap = machine.getType().getRecipeMap(machine.getTier());
                                this.tier = machine.getTier();
                            }
                        }
                    }
                    if (oldRecipeMap != recipeMap){
                        checkRecipe();
                    }
                }
                super.onMachineEvent(event, data);
            }

            @Override
            public void init() {
                ItemStack stack = itemHandler.map(i -> i.getHandler(SlotType.STORAGE).getStackInSlot(0)).orElse(ItemStack.EMPTY);
                if (stack.getItem() instanceof BlockItem blockItem){
                    if (blockItem.getBlock() instanceof BlockMachine machine && machine.getType() instanceof BasicMachine){
                        if (machine.getType().getRecipeMap(machine.getTier()) != null){
                            this.recipeMap = machine.getType().getRecipeMap(machine.getTier());
                            this.tier = machine.getTier();
                        }
                    }
                }
                super.init();
            }

            @Override
            public void checkRecipe() {
                if (getRecipeMap() == null){
                    return;
                }
                super.checkRecipe();
            }

            @Override
            public IRecipeMap getRecipeMap() {
                return recipeMap;
            }

            @Override
            protected IRecipe cachedRecipe() {
                if (recipeMap == null) return null;
                return super.cachedRecipe();
            }

            @Override
            public int getOverclock() {
                if (activeRecipe == null || tier == null) return 0;
                int oc = 0;
                /*if (activeRecipe.getPower() > 0 && tier.getVoltage() > activeRecipe.getPower()) {
                    long voltage = this.activeRecipe.getPower();
                    int tier = Utils.getVoltageTier(voltage);
                    long tempoverclock = (this.tile.getPowerLevel().getVoltage() / Ref.V[tier]);
                    while (tempoverclock > 1) {
                        tempoverclock >>= 2;
                        oc++;
                    }
                }*/
                return oc;
            }

            @Override
            public CompoundTag serialize() {
                CompoundTag nbt = super.serialize();
                nbt.putString("recipeMap", recipeMap.getId());
                return nbt;
            }

            @Override
            public void deserialize(CompoundTag nbt) {
                super.deserialize(nbt);
                this.recipeMap = GTAPI.get(RecipeMap.class, new ResourceLocation(nbt.getString("recipeMap")));
            }
        });
    }

    @Override
    public boolean test(SlotType<?> slotType, int slot, ItemStack stack) {
        if (slotType == SlotType.STORAGE){
            if (stack.getItem() instanceof BlockItem blockItem){
                if (blockItem.getBlock() instanceof BlockMachine machine && machine.getType() instanceof BasicMachine){
                    if (machine.getType().getRecipeMap(machine.getTier()) != null){
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }
}
