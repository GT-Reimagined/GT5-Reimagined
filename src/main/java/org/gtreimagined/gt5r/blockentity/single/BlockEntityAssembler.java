package org.gtreimagined.gt5r.blockentity.single;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.recipe.serializer.MachineRecipeSerializer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.data.RecipeMaps;

import static org.gtreimagined.gt5r.data.Materials.Glue;

public class BlockEntityAssembler extends BlockEntityMachine<BlockEntityAssembler> {
    public BlockEntityAssembler(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        recipeHandler.set(() -> new MachineRecipeHandler<>(this){
            @Override
            public IRecipe findRecipe() {
                IRecipe recipe = super.findRecipe();
                if (recipe == null){
                    IItemHandler container = itemHandler.get().getInputHandler();
                    ItemStack printedPages = ItemStack.EMPTY;
                    boolean leather = false;
                    for (int i = 0; i < container.getSlots(); i++) {
                        ItemStack stack = container.getStackInSlot(i);
                        if (stack.getItem() == GT5RItems.PrintedPages && printedPages.isEmpty()){
                            printedPages = stack;
                        } else if (stack.getItem() == Items.LEATHER){
                            leather = true;
                        }
                    }
                    if (!printedPages.isEmpty() && leather){
                        FluidStack glue = fluidHandler.map(f -> f.getFluidInTank(0)).orElse(FluidStack.EMPTY);
                        if (!glue.isEmpty() && glue.isFluidEqual(Glue.getLiquid(20)) && glue.getAmount() >= 20){
                            ItemStack output = new ItemStack(Items.WRITTEN_BOOK);
                            output.setTag(printedPages.copy().getTag());
                            return RecipeMaps.ASSEMBLER.RB().recipeMapOnly().ii(RecipeIngredient.of(printedPages.copy()), RecipeIngredient.of(Items.LEATHER)).fi(Glue.getLiquid(20)).io(output).add("written_book", 32, 8);
                        }
                    }
                }
                return recipe;
            }

            @Override
            public boolean accepts(ItemStack stack) {
                return super.accepts(stack) || stack.getItem() == GT5RItems.PrintedPages;
            }

            @Override
            public CompoundTag serialize() {
                CompoundTag nbt = super.serialize();
                if (activeRecipe != null) {
                    nbt.putString("activeRecipe", activeRecipe.toJson().toString());
                }
                if (lastRecipe != null) {
                    nbt.putString("lastRecipe", lastRecipe.toJson().toString());
                }
                return nbt;
            }

            @Override
            public void deserialize(CompoundTag nbt) {
                super.deserialize(nbt);
                if (nbt.contains("activeRecipe")) {
                    activeRecipe = MachineRecipeSerializer.INSTANCE.fromJson(new ResourceLocation(nbt.getString("AR")), (JsonObject) JsonParser.parseString(nbt.getString("activeRecipe")));
                }
                if (nbt.contains("lastRecipe")) {
                    lastRecipe = MachineRecipeSerializer.INSTANCE.fromJson(new ResourceLocation(nbt.getString("LR")), (JsonObject) JsonParser.parseString(nbt.getString("lastRecipe")));
                }
            }
        });
    }
}
