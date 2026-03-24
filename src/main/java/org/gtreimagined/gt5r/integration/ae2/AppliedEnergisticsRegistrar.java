package org.gtreimagined.gt5r.integration.ae2;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.RecipeMaps;
import org.gtreimagined.gt5r.integration.tfc.TFCRegistrar;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.ForgeTags;
import org.gtreimagined.gtlib.data.VanillaStoneTypes;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.event.MaterialEvent;
import org.gtreimagined.gtlib.recipe.ingredient.FluidIngredient;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.util.TagUtils;

import java.util.function.Consumer;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.*;
import static org.gtreimagined.gtlib.Ref.L;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient.of;

public class AppliedEnergisticsRegistrar extends GTMod {

    @Override
    public String getId() {
        return Ref.MOD_AE;
    }

    @Override
    public void onRegistrationEvent(RegistrationEvent event, Dist side) {
        if (event == RegistrationEvent.DATA_INIT){
            GEM.replacement(CertusQuartz, () -> getAe2Item("certus_quartz_crystal"));
            GEM.replacement(ChargedCertusQuartz, () -> getAe2Item("charged_certus_quartz_crystal"));
            GEM.replacement(Fluix, () -> getAe2Item("fluix_crystal"));
            DUST.replacement(CertusQuartz, () -> getAe2Item("certus_quartz_dust"));
            DUST.replacement(Fluix, () -> getAe2Item("fluix_dust"));
            BLOCK.replacement(CertusQuartz, () -> getAe2Item("quartz_block"));
            BLOCK.replacement(Fluix, () -> getAe2Item("fluix_block"));
            ORE.replacement(CertusQuartz, VanillaStoneTypes.STONE, () -> getAe2Item("quartz_ore"));
            ORE.replacement(CertusQuartz, VanillaStoneTypes.DEEPSLATE, () -> getAe2Item("deepslate_quartz_ore"));
        }
    }

    @Override
    public boolean isEnabled() {
        return GTAPI.isModLoaded(getId());
    }

    @Override
    public int getPriority() {
        return 0;
    }

    public static void machineRecipes(){
        RecipeMaps.FORMING_PRESS.RB().ii(GEM.getMaterialIngredient(CertusQuartz, 1), of(getAe2Item("calculation_processor_press"), 1).setNoConsume()).io(new ItemStack(getAe2Item("printed_calculation_processor"))).add("printed_calculation_processor", 200, 16);
        RecipeMaps.FORMING_PRESS.RB().ii(GEM.getMaterialIngredient(Diamond, 1), of(getAe2Item("engineering_processor_press"), 1).setNoConsume()).io(new ItemStack(getAe2Item("printed_engineering_processor"))).add("printed_engineering_processor", 200, 16);
        RecipeMaps.FORMING_PRESS.RB().ii(PLATE.getMaterialIngredient(Gold, 1), of(getAe2Item("logic_processor_press"), 1).setNoConsume()).io(new ItemStack(getAe2Item("printed_logic_processor"))).add("printed_logic_processor", 200, 16);
        RecipeMaps.FORMING_PRESS.RB().ii(of(TagUtils.getForgelikeItemTag("silicon")), of(getAe2Item("silicon_press"), 1).setNoConsume()).io(new ItemStack(getAe2Item("printed_silicon"))).add("printed_silicon", 200, 16);
        RecipeMaps.CENTRIFUGE.RB().ii(of(getAe2Item("sky_dust")))
                .io(/*DUST_SMALL.get(BasalticMineralSand, 1), */SMALL_DUST.get(Olivine, 1), SMALL_DUST.get(Obsidian, 1), SMALL_DUST.get(Basalt, 1), SMALL_DUST.get(Flint, 1), SMALL_DUST.get(RareEarth, 1))
                .outputChances(0.2,0.2,0.2,0.2,0.2)
                .add("sky_dust", 64, 20);
        //RecipeMaps.AUTOCLAVE.RB().ii(of(getAe2Item("certus_crystal_seed"), 1).setIgnoreNbt()).fi(Water.getLiquid(200)).io(GEM.get(CertusQuartz, 2)).add("certus_quartz_from_seed", 2000, 24);
        //RecipeMaps.AUTOCLAVE.RB().ii(of(getAe2Item("fluix_crystal_seed"), 1).setIgnoreNbt()).fi(Water.getLiquid(200)).io(GEM.get(Fluix, 2)).add("fluix_from_seed", 2000, 24);
        //RecipeMaps.AUTOCLAVE.RB().ii(of(getAe2Item("certus_crystal_seed"), 1).setIgnoreNbt()).fi(DistilledWater.getLiquid(200)).io(GEM.get(CertusQuartz, 2)).add("certus_quartz_from_seed_2", 1000, 24);
        //RecipeMaps.AUTOCLAVE.RB().ii(of(getAe2Item("fluix_crystal_seed"), 1).setIgnoreNbt()).fi(DistilledWater.getLiquid(200)).io(GEM.get(Fluix, 1)).add("fluix_from_seed_2", 1000, 24);
        RecipeMaps.MIXER.RB().ii(GEM.getIngredient(ChargedCertusQuartz, 1), DUST.getMaterialIngredient(Redstone, 1), of(ForgeTags.GEMS_QUARTZ_ALL)).fi(Water.getLiquid(500)).io(DUST.get(Fluix, 2)).add("fluix_crystal", 20, 16);
        RecipeMaps.MIXER.RB().ii(GEM.getIngredient(ChargedCertusQuartz, 1), DUST.getMaterialIngredient(Redstone, 1), of(ForgeTags.GEMS_QUARTZ_ALL)).fi(DistilledWater.getLiquid(500)).io(DUST.get(Fluix, 2)).add("fluix_crystal_2", 20, 16);
        RecipeMaps.ASSEMBLER.RB().ii(of(getAe2Item("printed_logic_processor")), of(getAe2Item("printed_silicon"))).fi(Redstone.getLiquid(144)).io(new ItemStack(getAe2Item("logic_processor"))).add("logic_processor", 64, 32);
        RecipeMaps.ASSEMBLER.RB().ii(of(getAe2Item("printed_engineering_processor")), of(getAe2Item("printed_silicon"))).fi(Redstone.getLiquid(144)).io(new ItemStack(getAe2Item("engineering_processor"))).add("engineering_processor", 64, 32);
        RecipeMaps.ASSEMBLER.RB().ii(of(getAe2Item("printed_calculation_processor")), of(getAe2Item("printed_silicon"))).fi(Redstone.getLiquid(144)).io(new ItemStack(getAe2Item("calculation_processor"))).add("calculation_processor", 64, 32);
        //RecipeMaps.ASSEMBLER.RB().ii(DUST.getMaterialIngredient(CertusQuartz, 1), of(ItemTags.SAND)).io(new ItemStack(getAe2Item("certus_crystal_seed"), 2)).add("certus_crystal_seed", 64, 8);
        //RecipeMaps.ASSEMBLER.RB().ii(DUST.getMaterialIngredient(Fluix, 1), of(ItemTags.SAND)).io(new ItemStack(getAe2Item("fluix_crystal_seed"), 2)).add("fluix_crystal_seed", 64, 8);
        PULVERIZER.RB().ii(of(getAe2Item("sky_stone_chest"))).io(new ItemStack(getAe2Item("sky_dust"), 8)).add("sky_dust_from_chest", 400, 2);
        PULVERIZER.RB().ii(of(getAe2Item("sky_stone_block"))).io(new ItemStack(getAe2Item("sky_dust"))).add("sky_dust", 400, 2);
        RecipeMaps.ELECTROLYZER.RB().ii(GEM.getMaterialIngredient(CertusQuartz, 1)).io(new ItemStack(getAe2Item("charged_certus_quartz_crystal"))).add("charged_certus_quartz", 2000, 30);
        E_BLAST_FURNACE.RB().ii(RecipeIngredient.of(TagUtils.getForgelikeItemTag("silicon")), GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(1).get()).io(INGOT.get(Silicon)).add("silicon_ingot_from_silicon", 1683, 120, 1683);
        LASER_ENGRAVER.RB().ii(BLOCK.getMaterialIngredient(TFCRegistrar.getIron(), 1), LENS.getMaterialIngredient(GreenSapphire, 1).setNoConsume()).io(new ItemStack(getAe2Item("logic_processor_press"))).add("inscriber_logic_press", 2000, 1920);
        LASER_ENGRAVER.RB().ii(BLOCK.getMaterialIngredient(TFCRegistrar.getIron(), 1), LENS.getMaterialIngredient(Opal, 1).setNoConsume()).io(new ItemStack(getAe2Item("calculation_processor_press"))).add("inscriber_calculation_press", 2000, 1920);
        LASER_ENGRAVER.RB().ii(BLOCK.getMaterialIngredient(TFCRegistrar.getIron(), 1), LENS.getMaterialIngredient(BlueTopaz, 1).setNoConsume()).io(new ItemStack(getAe2Item("calculation_processor_press"))).add("inscriber_calculation_press_2", 2000, 1920);
        LASER_ENGRAVER.RB().ii(BLOCK.getMaterialIngredient(TFCRegistrar.getIron(), 1), LENS.getMaterialIngredient(Sapphire, 1).setNoConsume()).io(new ItemStack(getAe2Item("calculation_processor_press"))).add("inscriber_calculation_press_3", 2000, 1920);
        LASER_ENGRAVER.RB().ii(BLOCK.getMaterialIngredient(TFCRegistrar.getIron(), 1), LENS.getMaterialIngredient(YellowGarnet, 1).setNoConsume()).io(new ItemStack(getAe2Item("engineering_processor_press"))).add("inscriber_engineering_press", 2000, 1920);
        LASER_ENGRAVER.RB().ii(BLOCK.getMaterialIngredient(TFCRegistrar.getIron(), 1), LENS.getMaterialIngredient(Diamond, 1).setNoConsume()).io(new ItemStack(getAe2Item("silicon_press"))).add("inscriber_silicon_press", 2000, 1920);
        LASER_ENGRAVER.RB().ii(BLOCK.getMaterialIngredient(TFCRegistrar.getIron(), 1), LENS.getMaterialIngredient(Glass, 1).setNoConsume()).io(new ItemStack(getAe2Item("silicon_press"))).add("inscriber_silicon_press_2", 2000, 1920);
        for (DyeColor dye : DyeColor.values()){
            String dyeName = dye.getName() + "_dye";
            TagKey<Fluid> dyeLiquid = TagUtils.getFluidTag(new ResourceLocation(GT5Reimagined.ID, dyeName));
            BATH.RB().fi(FluidIngredient.of(dyeLiquid, L / 8)).ii(getAe2Item("fluix_smart_cable")).io(getAe2Item(dye.getName() + "_smart_cable")).add(dye.getName() + "_smart_cable", 64);
            BATH.RB().fi(FluidIngredient.of(dyeLiquid, L / 8)).ii(getAe2Item("fluix_covered_cable")).io(getAe2Item(dye.getName() + "_covered_cable")).add(dye.getName() + "_covered_cable", 64);
            BATH.RB().fi(FluidIngredient.of(dyeLiquid, L / 8)).ii(getAe2Item("fluix_glass_cable")).io(getAe2Item(dye.getName() + "_glass_cable")).add(dye.getName() + "_glass_cable", 64);
            BATH.RB().fi(FluidIngredient.of(dyeLiquid, L / 8)).ii(getAe2Item("fluix_smart_dense_cable")).io(getAe2Item(dye.getName() + "_smart_dense_cable")).add(dye.getName() + "_smart_dense_cable", 64);
            BATH.RB().fi(FluidIngredient.of(dyeLiquid, L / 8)).ii(getAe2Item("fluix_covered_dense_cable")).io(getAe2Item(dye.getName() + "_covered_dense_cable")).add(dye.getName() + "_covered_dense_cable", 64);
        }
    }
    
    public static void craftingRecipes(Consumer<FinishedRecipe> output, GTRecipeProvider provider){
        SimpleCookingRecipeBuilder.smelting(DUST.getMaterialIngredient(Silicon, 1), RecipeCategory.MISC, getAe2Item("silicon"), 0.5F, 200).unlockedBy("has_silicon_dust", provider.hasSafeItem(DUST.getMaterialTag(Silicon))).save(output, GT5Reimagined.ID + ":silicon");
        SimpleCookingRecipeBuilder.blasting(DUST.getMaterialIngredient(Silicon, 1), RecipeCategory.MISC, getAe2Item("silicon"), 0.5F, 200).unlockedBy("has_silicon_dust", provider.hasSafeItem(DUST.getMaterialTag(Silicon))).save(output, GT5Reimagined.ID + ":silicon_blasting");
    }

    @Override
    public void onMaterialEvent(MaterialEvent event) {
        event.setMaterial(MilkyQuartz).addByProduct(CertusQuartz, Barite);
        event.setMaterial(CertusQuartz).addByProduct(MilkyQuartz, Barite);
    }

    public static Item getAe2Item(String id){
        return RegistryUtils.getItemFromID(Ref.MOD_AE, id);
    }

    public static Block getAe2Block(String id){
        return RegistryUtils.getBlockFromId(Ref.MOD_AE, id);
    }
}
