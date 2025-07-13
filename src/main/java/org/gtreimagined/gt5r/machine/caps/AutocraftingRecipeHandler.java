package org.gtreimagined.gt5r.machine.caps;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.machine.MachineFlag;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.recipe.Recipe;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.recipe.map.IRecipeMap;
import org.gtreimagined.gtlib.recipe.serializer.MachineRecipeSerializer;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.gtreimagined.gt5r.blockentity.IAutocrafter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.gtreimagined.gtlib.machine.MachineState.ACTIVE;
import static org.gtreimagined.gtlib.machine.MachineState.INVALID_TIER;

public class AutocraftingRecipeHandler<T extends BlockEntityMachine<T> & IAutocrafter> extends ParallelRecipeHandler<T>{
    public AutocraftingRecipeHandler(T tile, int maxSimultaneousRecipes) {
        super(tile, maxSimultaneousRecipes);
    }

    @Override
    protected boolean canRecipeContinue() {
        return super.canRecipeContinue() && (!activeRecipe.getMapId().isEmpty() || (tile.getRecipe() != null && tile.getRecipe().getId().equals(activeRecipe.getId())));
    }

    @Override
    public IRecipe findRecipe() {
        if (lastRecipe != null) {
            activeRecipe = lastRecipe;
            if (canRecipeContinue()) {
                activeRecipe = null;
                return lastRecipe;
            }
            activeRecipe = null;
        }
        IRecipe recipe = null;
        if (tile.getRecipe() != null) {
            List<Ingredient> condensed = new ArrayList<>();
            Map<Ingredient, Integer> condensedMap = new HashMap<>();
            for (Ingredient i : tile.getRecipe().getIngredients()) {
                if (i.isEmpty()) continue;
                if (!condensedMap.containsKey(i)) {
                    condensedMap.put(i, Math.max(1, RecipeIngredient.count(i)));
                } else {
                    condensedMap.compute(i, (k, currentCount) -> currentCount + Math.max(1, RecipeIngredient.count(i)));
                }
            }
            condensedMap.forEach((k, v) -> {
                condensed.add(RecipeIngredient.of(k, v));
            });
            recipe = new Recipe(condensed, new ItemStack[]{tile.getRecipe().getResultItem()}, List.of(), null, 1024, 16, 0, 1);
            recipe.setId(tile.getRecipe().getId());
            recipe.setMapId("");
            boolean valid = validateRecipe(recipe);
            if (!valid) {
                recipe = null;
            }
        }
        if (recipe == null) {
            IRecipeMap map = getRecipeMap();
            recipe = map != null ? map.find(tile.itemHandler, tile.fluidHandler, tile.getMachineTier(), this::validateRecipe) : null;
        }
        return recipe;
    }

    public void checkRecipe() {
        if (activeRecipe != null) {
            return;
        }
        //First lookup.
        if (!this.tile.hadFirstTick() && hasLoadedInput()) {
            if (!tile.getMachineState().allowRecipeCheck()) return;
            activeRecipe = findRecipe();
            if (activeRecipe == null) return;
            calculateDurations();
            lastRecipe = activeRecipe;
            return;
        }
        if (tile.getMachineState().allowRecipeCheck()) {
            if ((activeRecipe = cachedRecipe()) != null || (activeRecipe = findRecipe()) != null) {
                if (!validateRecipe(activeRecipe)) {
                    tile.setMachineState(INVALID_TIER);
                    activeRecipe = null;
                    return;
                }
                calculateDurations();
                if (!consumePower(true) || !canRecipeContinue()) {
                    activeRecipe = null;
                    tile.setMachineState(tile.getDefaultMachineState());
                    //wait half a second after trying again.
                    tickTimer += 10;
                    return;
                }
                activateRecipe(true);
                tile.setMachineState(ACTIVE);
            }
        }
    }

    public int getOverclock() {
        if (maxSimultaneousRecipes() > 1) return 0;
        if (activeRecipe == null) return 0;
        int oc = 0;
        if (activeRecipe.getPower() > 0 && this.tile.getPowerLevel().getVoltage() > activeRecipe.getPower()) {
            long voltage = this.activeRecipe.getPower();
            int tier = Utils.getVoltageTier(voltage);
            long tempoverclock = (this.tile.getPowerLevel().getVoltage() / Ref.V[tier]);
            while (tempoverclock > 1) {
                tempoverclock >>= 2;
                oc++;
            }
        }
        return oc;
    }

    public long getPower() {
        if (maxSimultaneousRecipes() > 1) return super.getPower();
        if (activeRecipe == null) return 0;
        if (overclock == 0 || tile.has(MachineFlag.FE)) return activeRecipe.getPower();
        //half the duration => overclock ^ 2.
        //so if overclock is 2 tiers, we have 1/4 the duration(200 -> 50) but for e.g. 8eu/t this would be
        //8*4*4 = 128eu/t.
        return (activeRecipe.getPower() * (1L << overclock) * (1L << overclock));
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
}
