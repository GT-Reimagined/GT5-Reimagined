package org.gtreimagined.gt5r.blockentity.multi;

import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityMultiMachine;
import org.gtreimagined.gtlib.capability.item.ITrackedHandler;
import org.gtreimagined.gtlib.capability.item.MultiTrackedItemHandler;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.capability.machine.MultiMachineItemHandler;
import org.gtreimagined.gtlib.machine.types.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.gtreimagined.gt5r.machine.caps.ParallelRecipeHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BlockEntityLargeSifter extends BlockEntityMultiMachine<BlockEntityLargeSifter> {
    public Set<Integer> HATCH_LAYERS = new HashSet<>();
    public BlockEntityLargeSifter(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.recipeHandler.set(() -> new ParallelRecipeHandler<>(this, 64){
            @Override
            protected boolean addSingleItemOutput() {
                List<ITrackedHandler> outputs = itemHandler.map(i -> ((LargeSifterMultiMachineItemHandler)i).outputList).orElse(List.of());
                if (outputs.isEmpty()) return false;
                List<ItemStack> stacks = activeRecipe.getOutputItems(false);
                if (itemHandler.map(i -> !i.canOutputsFit(stacks)).orElse(false)) return false;
                int sucessful = 0;
                boolean chance = activeRecipe.hasOutputChances();
                for (int i = 0; i < outputs.size(); i++) {
                    if (stacks.size() == i) break;
                    ItemStack stack = stacks.get(i);
                    if (chance && Ref.RNG.nextInt(10000) >= activeRecipe.getOutputChances()[i]){
                        sucessful++;
                        continue;
                    }
                    insertItem(outputs.get(i), stack, false);
                    sucessful++;
                }
                return sucessful == stacks.size();
            }
        });
        this.itemHandler.set(() -> new LargeSifterMultiMachineItemHandler(this));
    }

    @Override
    public boolean checkStructure() {
        HATCH_LAYERS.clear();
        return super.checkStructure();
    }

    public static ItemStack insertItem(ITrackedHandler to, ItemStack stack, boolean simulate){
        if (to == null || stack.isEmpty())
            return stack;

        for (int i = 0; i < to.getSlots(); i++)
        {
            stack = to.insertOutputItem(i, stack, simulate);
            if (stack.isEmpty())
            {
                return ItemStack.EMPTY;
            }
        }

        return stack;
    }


    private static class LargeSifterMultiMachineItemHandler extends MultiMachineItemHandler<BlockEntityLargeSifter> {
        List<ITrackedHandler> outputList = new ArrayList<>();

        public LargeSifterMultiMachineItemHandler(BlockEntityLargeSifter sifter) {
            super(sifter);
        }

        @Override
        public void invalidate() {
            super.invalidate();
            outputList.clear();
        }

        @Override
        protected int compareOutputBuses(MachineItemHandler<?> a, MachineItemHandler<?> b) {
            return a.getTile().getBlockPos().getY() < b.getTile().getBlockPos().getY() ? 1 : -1;
        }

        @Override
        protected ITrackedHandler calculateOutputs() {
            outputList = tile.getComponentsByHandlerId(outputComponentString()).stream().filter(t -> t.getItemHandler().isPresent()).map(t -> t.getItemHandler().get()).sorted(this::compareOutputBuses).map(MachineItemHandler::getOutputHandler).collect(Collectors.toList());
            return new MultiTrackedItemHandler(outputList.toArray(new IItemHandlerModifiable[0]));
        }
    }
}
