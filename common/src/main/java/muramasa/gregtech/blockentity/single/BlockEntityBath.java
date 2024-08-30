package muramasa.gregtech.blockentity.single;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.capability.machine.MachineRecipeHandler;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.recipe.IRecipe;
import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import muramasa.antimatter.util.TagUtils;
import muramasa.antimatter.util.Utils;
import muramasa.gregtech.GTIRef;
import muramasa.gregtech.data.GregTechItems;
import muramasa.gregtech.data.RecipeMaps;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import tesseract.TesseractGraphWrappers;
import tesseract.api.item.ExtendedItemContainer;

import static muramasa.antimatter.Ref.L;
import static muramasa.gregtech.data.Materials.Chlorine;
import static muramasa.gregtech.data.Materials.Glue;

public class BlockEntityBath extends BlockEntityMachine<BlockEntityBath> {
    public BlockEntityBath(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        recipeHandler.set(() -> new MachineRecipeHandler<>(this){
            @Override
            public IRecipe findRecipe() {
                IRecipe recipe = super.findRecipe();
                if (recipe == null){
                    ExtendedItemContainer container = itemHandler.get().getInputHandler();
                    ItemStack input = container != null ? container.getItem(0) : ItemStack.EMPTY;
                    ResourceLocation id = AntimatterPlatformUtils.getIdFromItem(input.getItem());
                    FluidHolder fluidInput = fluidHandler.map(f -> f.getFluidInTank(0)).orElse(FluidHooks.emptyFluid());
                    if (!fluidInput.isEmpty()) {
                        if (input.getItem() == Items.SHULKER_BOX){
                            DyeColor color = fromFluid(fluidInput);
                            if (color != null && fluidInput.getFluidAmount() >= L / 2) {
                                ItemStack output = new ItemStack(AntimatterPlatformUtils.getItemFromID(new ResourceLocation(color.getName() + "_shulker_box")));
                                output.setTag(input.getTag());
                                return RecipeMaps.BATH.RB().recipeMapOnly().ii(RecipeIngredient.of(input.copy())).fi(Utils.ca(L / 2, fluidInput)).io(output).add(color.getName() + "_shulker_box", 64);
                            }
                        } else if (id.getPath().contains("_shulker_box") && id.getNamespace().equals("minecraft")){
                            if (fluidInput.matches(Chlorine.getGas(50)) && fluidInput.getFluidAmount() >= 50 * TesseractGraphWrappers.dropletMultiplier) {
                                ItemStack output = new ItemStack(Items.SHULKER_BOX);
                                output.setTag(input.getTag());
                                return RecipeMaps.BATH.RB().recipeMapOnly().ii(RecipeIngredient.of(input.copy())).fi(Chlorine.getGas(50)).io(output).add("shulker_box", 64);
                            }
                        }
                    }
                }
                return recipe;
            }

            @Override
            public boolean accepts(ItemStack stack) {
                ResourceLocation id = AntimatterPlatformUtils.getIdFromItem(stack.getItem());
                return super.accepts(stack) || (id.getPath().contains("shulker_box") && id.getNamespace().equals("minecraft"));
            }
        });
    }

    private DyeColor fromFluid(FluidHolder f) {
        for (DyeColor color : DyeColor.values()) {
            if (f.getFluid().is(TagUtils.getFluidTag(new ResourceLocation(GTIRef.ID, color.getName() + "_dye")))){
                return color;
            }
        }
       return null;
    }
}
