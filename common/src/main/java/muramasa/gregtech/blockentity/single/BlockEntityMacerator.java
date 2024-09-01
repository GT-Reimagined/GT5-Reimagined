package muramasa.gregtech.blockentity.single;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.capability.machine.MachineRecipeHandler;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.recipe.IRecipe;
import muramasa.antimatter.recipe.Recipe;
import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import muramasa.antimatter.recipe.map.IRecipeMap;
import muramasa.antimatter.recipe.map.RecipeBuilder;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import muramasa.antimatter.util.TagUtils;
import muramasa.antimatter.util.Utils;
import muramasa.gregtech.GTIRef;
import muramasa.gregtech.data.RecipeMaps;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import tesseract.TesseractGraphWrappers;
import tesseract.api.item.ExtendedItemContainer;

import java.util.ArrayList;
import java.util.List;

import static muramasa.antimatter.Ref.L;
import static muramasa.antimatter.machine.Tier.*;
import static muramasa.gregtech.data.Materials.Chlorine;

public class BlockEntityMacerator extends BlockEntityMachine<BlockEntityMacerator> {
    public BlockEntityMacerator(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        recipeHandler.set(() -> new MachineRecipeHandler<>(this){
            @Override
            protected IRecipeMap getRecipeMap() {
                if (tile.getMachineTier() == LV || tile.getMachineTier() == MV) return RecipeMaps.MACERATOR;
                return super.getRecipeMap();
            }
        });
    }
}
