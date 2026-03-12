package org.gtreimagined.gt5r.blockentity.single;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.IFilterableHandler;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler;
import org.gtreimagined.gtlib.gui.SlotType;
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
import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.data.RecipeMaps;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static org.gtreimagined.gt5r.data.GT5RItems.DataOrb;

public class BlockEntityScanner extends BlockEntityMachine<BlockEntityScanner> implements IFilterableHandler {
    private static final List<ScannerFunction> SCANNER_FUNCTIONS = new ArrayList<>();
    private static final List<Predicate<ItemStack>> SCANNER_FILTERS = new ArrayList<>();
    private UUID placedBy = null;

    public BlockEntityScanner(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        recipeHandler.set(() -> new MachineRecipeHandler<>(this){
            @Override
            public IRecipe findRecipe() {
                IRecipe recipe = super.findRecipe();
                if (recipe == null){
                    MachineItemHandler<?> ih = itemHandler.orElse(null);
                    IItemHandler inputHandler = ih.getInputHandler();
                    ItemStack input = inputHandler.getStackInSlot(0);
                    ItemStack data = inputHandler.getStackInSlot(1);
                    if (!input.isEmpty()) {
                        for (ScannerFunction scannerFunction : SCANNER_FUNCTIONS) {
                            IRecipe r = scannerFunction.findRecipe(input, data, placedBy != null ? level.getPlayerByUUID(placedBy) : null);
                            if (r != null){
                                return r;
                            }
                        }
                    }
                }
                return recipe;
            }

            @Override
            public boolean accepts(ItemStack stack) {
                return super.accepts(stack) || SCANNER_FILTERS.stream().anyMatch(scannerFunction -> scannerFunction.test(stack));
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

    @Override
    public boolean test(SlotType<?> slotType, int slot, ItemStack stack) {
        if (slotType == SlotType.IT_IN && slot == 1){
            return stack.getItem() == GT5RItems.DataStick || stack.getItem() == DataOrb;
        }
        return true;
    }

    public static void initDefaultScannerFunctions(){
        addScannerFunction((input, data, player) ->{
            if (input.getItem() == GT5RItems.DataStick) {
                CompoundTag prospect = input.getTagElement("prospectData");
                if (prospect != null) {
                    ItemStack output = input.copy();
                    output.getTagElement("prospectData").putBoolean("analyzed", true);
                    return RecipeMaps.SCANNER.RB().recipeMapOnly().ii(RecipeIngredient.of(input.copy())).io(output).add("data_stick_prospection", 1000, 32);
                } else if (data.getItem() == GT5RItems.DataStick && data.getTag() != null) {
                    ItemStack output = data.copy();
                    return RecipeMaps.SCANNER.RB().recipeMapOnly().ii(RecipeIngredient.of(input.copy()), RecipeIngredient.of(data.copy()).setNoConsume()).io(output).add("data_stick_copying", 128, 32);
                }
            } else if (input.getItem() == Items.WRITTEN_BOOK && input.getTag() != null && data.getItem() == GT5RItems.DataStick && data.getTag() == null){
                ItemStack output = new ItemStack(GT5RItems.DataStick);
                output.getOrCreateTag().put("bookData", input.getTag().copy());
                return RecipeMaps.SCANNER.RB().recipeMapOnly().ii(RecipeIngredient.of(input.copy()), RecipeIngredient.of(data.copy())).io(output).add("book_copying", 128, 32);
            } else if (input.getItem() == GTCoreItems.Blueprint && input.getTag() != null && data.getItem() == GT5RItems.DataStick && data.getTag() == null){
                ItemStack output = new ItemStack(GT5RItems.DataStick);
                output.getOrCreateTag().put("blueprintData", input.getTag().copy());
                return RecipeMaps.SCANNER.RB().recipeMapOnly().ii(RecipeIngredient.of(input.copy()), RecipeIngredient.of(data.copy())).io(output).add("blueprint_copying", 128, 32);
            } else if (input.getItem() == Items.FILLED_MAP && input.getTag() != null && data.getItem() == GT5RItems.DataStick && data.getTag() == null){
                ItemStack output = new ItemStack(GT5RItems.DataStick);
                output.getOrCreateTag().put("filledMapData", input.getTag().copy());
                return RecipeMaps.SCANNER.RB().recipeMapOnly().ii(RecipeIngredient.of(input.copy())).io(output).add("filled_map_copying", 128, 32);
            }
            return null;
        });
        SCANNER_FILTERS.add(stack -> {
            return stack.getItem() == GT5RItems.DataStick || stack.getItem() == Items.WRITTEN_BOOK || stack.getItem() == Items.FILLED_MAP || stack.getItem() == GTCoreItems.Blueprint;
        });
    }

    @Override
    public void onPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (placer != null){
            placedBy = placer.getUUID();
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("placedBy")){
            placedBy = tag.getUUID("placedBy");
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag updateTag = super.getUpdateTag();
        if (placedBy != null){
            updateTag.putUUID("placedBy", placedBy);
        }
        return updateTag;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (placedBy != null) {
            tag.putUUID("placedBy", placedBy);
        }
    }

    public static void addScannerFunction(ScannerFunction function) {
        SCANNER_FUNCTIONS.add(function);
    }

    public static void addScannerFilter(Predicate<ItemStack> filter) {
        SCANNER_FILTERS.add(filter);
    }

    @FunctionalInterface
    public interface ScannerFunction {
        IRecipe findRecipe(ItemStack input, ItemStack data, @Nullable Player player);
    }
}
