package org.gtreimagined.gt5r.data;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.integration.recipeviewer.renderer.IRecipeInfoRenderer;
import org.gtreimagined.gtlib.integration.recipeviewer.renderer.InfoRenderers;
import org.gtreimagined.gtlib.machine.BlockMachine;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.recipe.RecipeProxies;
import org.gtreimagined.gtlib.recipe.ingredient.FluidIngredient;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.recipe.map.Proxy;
import org.gtreimagined.gtlib.recipe.map.RecipeBuilder;
import org.gtreimagined.gtlib.recipe.map.RecipeMap;
import org.gtreimagined.gtlib.recipe.map.SubCategory;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.gtreimagined.gt5r.machine.recipe.FusionRecipe;
import org.gtreimagined.gt5r.machine.recipe.FusionRecipeBuilder;
import org.gtreimagined.gt5r.machine.recipe.FusionRecipeSerializer;
import org.gtreimagined.gtcore.data.RecipeBuilders;
import org.gtreimagined.gtlib.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;

@SuppressWarnings("unchecked")
public class RecipeMaps {

    public static BiFunction<Integer, Integer, Proxy> DISSASSEMBLER_PROXY = (power, duration) -> new Proxy(RecipeType.CRAFTING, getDefaultCrafting(power, duration));
    public static RecipeMap<RecipeBuilder> STEAM_FUELS =
            new RecipeMap<>(GT5Reimagined.ID, "steam_fuels", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> HP_STEAM_FUELS =
            new RecipeMap<>(GT5Reimagined.ID, "hp_steam_fuels", new RecipeBuilder());

    public static RecipeMap<RecipeBuilder> ALLOY_SMELTER =
            new RecipeMap<>(GT5Reimagined.ID, "alloy_smelter", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> ARC_FURNACE =
            new RecipeMap<>(GT5Reimagined.ID, "arc_furnace", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> ASSEMBLER =
            new RecipeMap<>(GT5Reimagined.ID, "assembler", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> AUTOCLAVE =
            new RecipeMap<>(GT5Reimagined.ID, "autoclave", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> PRIMITIVE_BLAST_FURNACE =
            new RecipeMap<>(GT5Reimagined.ID, "primitive_blast_furnace", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> BEDROCK_DRILL =
            new RecipeMap<>(GT5Reimagined.ID, "bedrock_drill", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> BENDER =
            new RecipeMap<>(GT5Reimagined.ID, "bender", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> CANNER =
            new RecipeMap<>(GT5Reimagined.ID, "canner", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> CENTRIFUGE =
            new RecipeMap<>(GT5Reimagined.ID, "centrifuge", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> BATH =
            new RecipeMap<>(GT5Reimagined.ID, "bath", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> CHEMICAL_REACTOR =
            new RecipeMap<>(GT5Reimagined.ID, "chemical_reactor", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> CIRCUIT_ASSEMBLER =
            new RecipeMap<>(GT5Reimagined.ID, "circuit_assembler", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> SOLID_FUEL_BOILERS =
            new RecipeMap<>(GT5Reimagined.ID, "solid_fuel_boilers", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> COKE_OVEN =
            new RecipeMap<>(GT5Reimagined.ID, "coke_oven", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> COMBUSTION_FUELS =
            new RecipeMap<>(GT5Reimagined.ID, "combustion_fuels", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> COMPRESSOR =
            new RecipeMap<>(GT5Reimagined.ID, "compressor", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> CRYSTALLIZATION_CHAMBER =
            new RecipeMap<>(GT5Reimagined.ID, "crystallization_chamber", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> CUTTER =
            new RecipeMap<>(GT5Reimagined.ID, "cutter", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> CRACKING =
            new RecipeMap<>(GT5Reimagined.ID, "cracking", new RecipeBuilder()).setGuiData(Guis.MULTI_DISPLAY);
    public static RecipeMap<RecipeBuilder> ASSEMBLY_LINE =
            new RecipeMap<>(GT5Reimagined.ID, "assembly_line", new RecipeBuilder()).setGuiData(Guis.MULTI_DISPLAY_FLUID);
    public static RecipeMap<RecipeBuilder> DEHYDRATOR =
            new RecipeMap<>(GT5Reimagined.ID, "dehydrator", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> DISASSEMBLER =
            new RecipeMap<>(GT5Reimagined.ID, "disassembler", new RecipeBuilder()).setProxy(DISSASSEMBLER_PROXY.apply(8, 200));
    public static RecipeMap<RecipeBuilder> DISTILLATION =
            new RecipeMap<>(GT5Reimagined.ID, "distillation", new RecipeBuilder()).setGuiData(Guis.MULTI_DISPLAY_FLUID);
    public static RecipeMap<RecipeBuilder> CRYO_DISTILLATION =
            new RecipeMap<>(GT5Reimagined.ID, "cryo_distillation", new RecipeBuilder()).setGuiData(Guis.MULTI_DISPLAY_FLUID);
    public static RecipeMap<RecipeBuilder> DISTILLERY =
            new RecipeMap<>(GT5Reimagined.ID, "distillery", new RecipeBuilder());
    public static RecipeMap<RecipeBuilders.BlastingBuilder> E_BLAST_FURNACE =
            new RecipeMap<>(GT5Reimagined.ID, "electric_blast_furnace", new RecipeBuilders.BlastingBuilder()).setGuiData(Guis.MULTI_DISPLAY);
    public static RecipeMap<RecipeBuilder> ELECTRIC_FURNACE =
            new RecipeMap<>(GT5Reimagined.ID, "electric_furnace", new RecipeBuilder())
                    .setProxy(RecipeProxies.FURNACE_PROXY.apply(8, 80));
    public static RecipeMap<RecipeBuilder> ELECTRIC_OVEN =
            new RecipeMap<>(GT5Reimagined.ID, "electric_oven", new RecipeBuilder())
                    .setProxy(RecipeProxies.SMOKING_PROXY.apply(8, 40));
    public static RecipeMap<RecipeBuilder> ELECTROLYZER =
            new RecipeMap<>(GT5Reimagined.ID, "electrolyzer", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> ELECTROMAGNETIC_SEPARATOR =
            new RecipeMap<>(GT5Reimagined.ID, "electromagnetic_separator", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> EXTRACTOR =
            new RecipeMap<>(GT5Reimagined.ID, "extractor", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> EXTRUDER =
            new RecipeMap<>(GT5Reimagined.ID, "extruder", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> FERMENTER =
            new RecipeMap<>(GT5Reimagined.ID, "fermenter", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> FLUID_CANNER =
            new RecipeMap<>(GT5Reimagined.ID, "fluid_canner", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> FLUID_PRESS =
            new RecipeMap<>(GT5Reimagined.ID, "fluid_press", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> FLUID_HEATER =
            new RecipeMap<>(GT5Reimagined.ID, "fluid_heater", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> FLUID_SOLIDIFYER =
            new RecipeMap<>(GT5Reimagined.ID, "fluid_solidifyer", new RecipeBuilder());
    public static RecipeMap<FusionRecipeBuilder> FUSION =
            new RecipeMap<>(GT5Reimagined.ID, "fusion", new FusionRecipeBuilder()).setRecipeSerializer(FusionRecipeSerializer.INSTANCE).setGuiData(Guis.MULTI_DISPLAY);
    public static RecipeMap<RecipeBuilder> GAS_FUELS =
            new RecipeMap<>(GT5Reimagined.ID, "gas_fuels", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> FORGE_HAMMER =
            new RecipeMap<>(GT5Reimagined.ID, "forge_hammer", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> HEAT_EXCHANGER =
            new RecipeMap<>(GT5Reimagined.ID, "heat_exchanger", new RecipeBuilder()).setGuiData(Guis.MULTI_DISPLAY);
    public static RecipeMap<RecipeBuilder> IMPLOSION_COMPRESSOR =
            new RecipeMap<>(GT5Reimagined.ID, "implosion_compressor", new RecipeBuilder()).setGuiData(Guis.MULTI_DISPLAY);
    public static RecipeMap<RecipeBuilder> LARGE_BOILERS =
            new RecipeMap<>(GT5Reimagined.ID, "large_boilers", new RecipeBuilder()).setGuiData(Guis.MULTI_DISPLAY);
    public static RecipeMap<RecipeBuilder> LASER_CUTTER =
            new RecipeMap<>(GT5Reimagined.ID, "laser_cutter", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> LASER_ENGRAVER =
            new RecipeMap<>(GT5Reimagined.ID, "laser_engraver", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> LATHE =
            new RecipeMap<>(GT5Reimagined.ID, "lathe", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> MACERATOR =
            new RecipeMap<>(GT5Reimagined.ID, "macerator", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> MAGIC_FUELS =
            new RecipeMap<>(GT5Reimagined.ID, "magic_fuels", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> MASS_FABRICATOR =
            new RecipeMap<>(GT5Reimagined.ID, "mass_fabricator", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> MIXER =
            new RecipeMap<>(GT5Reimagined.ID, "mixer", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> ORE_BYPRODUCTS =
            new RecipeMap<>(GT5Reimagined.ID, "ore_byproducts", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> ORE_WASHER =
            new RecipeMap<>(GT5Reimagined.ID, "ore_washer", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> PACKAGER =
            new RecipeMap<>(GT5Reimagined.ID, "packager", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> POLARIZER =
            new RecipeMap<>(GT5Reimagined.ID, "polarizer", new RecipeBuilder());
    public static RecipeMap<PulverizerBuilder> PULVERIZER =
            new RecipeMap<>(GT5Reimagined.ID, "pulverizer", new PulverizerBuilder()).setGuiTier(Tier.HV);
    public static RecipeMap<RecipeBuilder> FORMING_PRESS =
            new RecipeMap<>(GT5Reimagined.ID, "forming_press", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> PRINTING =
            new RecipeMap<>(GT5Reimagined.ID, "printing", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> PYROLYSE_OVEN =
            new RecipeMap<>(GT5Reimagined.ID, "pyrolyse_oven", new RecipeBuilder()).setGuiData(Guis.MULTI_DISPLAY);

    public static RecipeMap<RecipeBuilder> ROASTER =
            new RecipeMap<>(GT5Reimagined.ID, "roaster", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> RECYCLER =
            new RecipeMap<>(GT5Reimagined.ID, "recycler", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> REPLICATOR =
            new RecipeMap<>(GT5Reimagined.ID, "replicating", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> ROCK_BREAKER =
            new RecipeMap<>(GT5Reimagined.ID, "rock_breaker", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> SCANNER =
            new RecipeMap<>(GT5Reimagined.ID, "scanning", new RecipeBuilder());

    public static RecipeMap<RecipeBuilder> SEMI_FUELS =
            new RecipeMap<>(GT5Reimagined.ID, "semi_fuels", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> SIFTER =
            new RecipeMap<>(GT5Reimagined.ID, "sifter", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> SMELTER =
            new RecipeMap<>(GT5Reimagined.ID, "smelter", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> SMALL_BOILERS =
            new RecipeMap<>(GT5Reimagined.ID, "small_boilers", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> THERMAL_CENTRIFUGE =
            new RecipeMap<>(GT5Reimagined.ID, "thermal_centrifuge", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> TREE_GROWTH_SIMULATOR =
            new RecipeMap<>(GT5Reimagined.ID, "tree_growth_simulator", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> UNPACKAGER =
            new RecipeMap<>(GT5Reimagined.ID, "unpackager", new RecipeBuilder());
    public static RecipeMap<RecipeBuilder> VACUUM_FREEZER =
            new RecipeMap<>(GT5Reimagined.ID, "vacuum_freezer", new RecipeBuilder()).setGuiData(Guis.MULTI_DISPLAY);
    public static RecipeMap<RecipeBuilder> WIRE_MILL =
            new RecipeMap<>(GT5Reimagined.ID, "wire_mill", new RecipeBuilder());

    private static BiFunction<Recipe<?>, RecipeBuilder, IRecipe> getDefaultCrafting(int power, int duration) {
        return (t, b) -> {
            if (!(t instanceof ShapedRecipe shapedRecipe)) return null;
            List<Ingredient> ingredients = t.getIngredients();
            if (!(t.getResultItem(null).getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof BlockMachine machine)) return null;
            if (machine.getType() == GT5RMachines.HULL) return null;
            List<ItemStack> list = new ObjectArrayList<>();
            for (Ingredient i : ingredients){
                for (ItemStack stack : i.getItems()){
                    if (!stack.isEmpty() && !stack.isDamageableItem()){
                        list.add(stack);
                        break;
                    }
                }
            }
            ItemStack craftingOut = shapedRecipe.getResultItem(null);
            if (list.isEmpty()) return null;
            RecipeIngredient ing = RecipeIngredient.of(craftingOut);
            IRecipe recipe = b.recipeMapOnly().ii(ing)
                    .io(list.toArray(new ItemStack[0])).hide().add(t.getId().getPath(), duration, power, 0, 1);
            recipe.setMapId(b.getMap().getLoc().toString());
            return recipe;
        };
    }

    public static final IRecipeInfoRenderer STEAM_RENDERER = r -> {
        var list = InfoRenderers.DEFAULT_RENDERER.getLines(r);
        if (!list.isEmpty()){
            long steamDuration = r.getDuration() * (r.getMapLoc().getPath().equals("plate_cutter") ? 4L : 2L);
            long steamPower = r.getMapLoc().getPath().equals("cutter") ? r.getPower() : r.getPower() * 2L;
            if (steamPower <= Tier.LV.getVoltage()){
                list.add(Utils.translatable("recipe_info.gt5r.steam_per_tick", steamPower));
                Component additional;
                if (steamDuration < 1200) {
                    additional = Component.empty();
                } else if (steamDuration < 36000) {
                    additional = Utils.translatable("recipe_info.gtlib.duration.seconds", (steamDuration / 20.0f));
                } else {
                    additional = Utils.translatable("recipe_info.gtlib.duration.minutes", (steamDuration / 1200.0f));
                }
                list.add(Utils.translatable("recipe_info.gt5r.steam_duration", steamDuration, additional));
            } else {
                list.add(Utils.translatable("recipe_info.gt5r.not_steam_runnable"));
            }

        }
        return list;
    };
    public static final IRecipeInfoRenderer BLASTING_RENDERER = r -> {
        List<Component> list = InfoRenderers.DEFAULT_RENDERER.getLines(r);
        if (list.isEmpty()) return list;
        list.add(3, Utils.translatable("recipe_info.gt5r.temperature", r.getSpecialValue()));
        return list;
    };

    public static final IRecipeInfoRenderer LARGE_BOILER_RENDERER = r -> {
        List<Component> list = InfoRenderers.BASIC_RENDERER.getLines(r);
        if (list.isEmpty()) return list;
        list.add(Utils.translatable("recipe_info.gt5r.extra_burntime", r.getPower()));
        list.add(Utils.translatable("recipe_info.gt5r.heat_multiplier", Math.max(r.getSpecialValue(), 1)));
        return list;
    };

    public static final IRecipeInfoRenderer HEAT_EXCHANGER_RENDERER = r -> {
        List<Component> list = InfoRenderers.BASIC_RENDERER.getLines(r);
        if (list.isEmpty()) return list;
        list.add(Utils.translatable("recipe_info.gt5r.hut", r.getPower()));
        list.add(Utils.translatable("recipe_info.gt5r.total_hu", r.getPower() * r.getDuration()));

        var tags = r.getTags();
        if (tags.contains(GT5RRecipeTags.LARGE_HEAT_EXCHANGED_ONLY) || tags.contains(GT5RRecipeTags.SMALL_HEAT_EXCHANGED_ONLY)){
            list.add(Utils.translatable("recipe_info.gt5r." + (tags.contains(GT5RRecipeTags.LARGE_HEAT_EXCHANGED_ONLY) ? "large" : "small") + "_heat_exchanger_only"));
        }
        return list;
    };

    public static final IRecipeInfoRenderer FUSION_RENDERER = r -> {
        if (!(r instanceof FusionRecipe fr)) return List.of();
        List<Component> list = InfoRenderers.BASIC_RENDERER.getLines(r);
        if (list.isEmpty()) return list;
        Tier tier = Tier.getTier((r.getPower() / r.getAmps()));
        Tier outputTier = Tier.getTier(fr.getHuOutput());
        list.add(Utils.translatable("recipe_info.gtlib.eut", r.getPower(), Utils.translatable("recipe_info.gtlib.eut.tier", tier.getId().toUpperCase(Locale.ROOT)).withStyle(Tier.EV.getRarityFormatting())));
        list.add(Utils.translatable("recipe_info.gt5r.fusion_hut", fr.getHuOutput(), Utils.translatable("recipe_info.gtlib.eut.tier", outputTier.getId().toUpperCase(Locale.ROOT)).withStyle(Tier.ULV.getRarityFormatting())));
        list.add(Utils.translatable("recipe_info.gtlib.total_eu", r.getDuration() * r.getPower() * r.getAmps()));
        list.add(Utils.translatable("recipe_info.gt5r.start_eu", r.getSpecialValue()));
        return list;
    };

    public static final IRecipeInfoRenderer CHEM_RENDERER = r -> {
        List<Component> list = InfoRenderers.DEFAULT_RENDERER.getLines(r);
        if (list.isEmpty()) return list;
        boolean toLarge = false;
        for (FluidIngredient outputFluid : r.getInputFluids()) {
            if (outputFluid.getAmount() > 32000){
                toLarge = true;
                break;
            }
        }
        if (r.getTags().contains(GT5RRecipeTags.COMPLICATED_RECIPE)){
             list.add(Utils.translatable("recipe_info.gt5r.complicated_recipe"));
        }
        if (toLarge){
            list.add(Utils.translatable("recipe_info.gt5r.large_chem_reactor"));
        }
        return list;
    };

    public static final IRecipeInfoRenderer MACERATOR_RENDERER = r -> {
        List<Component> list = STEAM_RENDERER.getLines(r);
        if (!list.isEmpty()){
            list.add(Utils.translatable("recipe_info.gt5r.pulverizer_only"));
        }
        return list;
    };

    public static final IRecipeInfoRenderer ALLOY_SMELTER_RENDERER = r -> {
        var list = STEAM_RENDERER.getLines(r);
        if (!list.isEmpty()){
            long steamPower = r.getPower() * 2L;
            if (r.getInputItems().size() > 2){
                list.remove(list.size() - 1);
                if (steamPower <= Tier.LV.getVoltage()){
                    list.remove(list.size() - 1);
                }
                list.add(Utils.translatable("recipe_info.gt5r.multismelter_only"));
            }
        }
        return list;
    };


    public static final IRecipeInfoRenderer MAGIC_FUEL_RENDERER = r -> {
        if (r.hasInputFluids()){
            return InfoRenderers.FUEL_RENDERER.getLines(r);
        } else if (r.hasInputItems()){
            return List.of(Utils.translatable("recipe_info.gt5r.fuel_value", r.getTotalPower()));
        }
        return List.of();
    };

    static {
        PULVERIZER.addSubCategory("macerator_recycling", new SubCategory("jei.category.macerator_recycling", () -> new ResourceLocation(GT5Reimagined.ID, "textures/gui/icon/macerator_recycling.png"), r -> r.getTags().contains(GT5RRecipeTags.RECYCLING)));
        PULVERIZER.addSubCategory("macerator_ore_processing", new SubCategory("jei.category.macerator_ore_processing", () -> GT5RMachines.MACERATOR.getItem(Tier.LV), r -> r.getTags().contains(GT5RRecipeTags.MACERATOR_ORE_PROCESING)));
        ARC_FURNACE.addSubCategory("arc_furnace_recycling", new SubCategory("jei.category.arc_furnace_recycling", () -> new ResourceLocation(GT5Reimagined.ID, "textures/gui/icon/arc_furnace_recycling.png"), r -> r.getTags().contains(GT5RRecipeTags.RECYCLING)));
        ALLOY_SMELTER.addSubCategory("alloy_smelter_molds", new SubCategory("jei.category.alloy_smelter_molds", () -> new ResourceLocation(GT5Reimagined.ID, "textures/gui/icon/alloy_smelter_molding.png"), r -> r.getTags().contains(GT5RRecipeTags.ALLOY_SMELTER_MOLDING)));
        COKE_OVEN.setGuiData(Guis.COKE_OVEN_RECIPE_GUI);
        PRIMITIVE_BLAST_FURNACE.setGuiData(Guis.PBF_RECIPE_GUI);
        SOLID_FUEL_BOILERS.setGuiData(Guis.SIMPLE_DISPLAY);
        COMBUSTION_FUELS.setGuiData(Guis.MULTI_DISPLAY);
        GAS_FUELS.setGuiData(Guis.MULTI_DISPLAY);
        SEMI_FUELS.setGuiData(Guis.MULTI_DISPLAY);
        MAGIC_FUELS.setGuiData(Guis.MULTI_DISPLAY);
        ORE_BYPRODUCTS.setGuiData(Guis.ORE_BYPRODUCTS);
        STEAM_FUELS.setGuiData(Guis.MULTI_DISPLAY);
        HP_STEAM_FUELS.setGuiData(Guis.MULTI_DISPLAY);
        TREE_GROWTH_SIMULATOR.setGuiData(Guis.MULTI_DISPLAY);
        DISTILLATION.setGuiData(Guis.MULTI_DISPLAY_DISTILLATION);
        CRYO_DISTILLATION.setGuiData(Guis.MULTI_DISPLAY_DISTILLATION);
        ALLOY_SMELTER.setGuiData(Guis.ALLOY_SMELTER_DISPLAY);
        BEDROCK_DRILL.setGuiData(Guis.BEDROCK_DRILL_DISPLAY);
    }

    public static void clientMaps() {

        E_BLAST_FURNACE.setInfoRenderer(BLASTING_RENDERER);
        PRIMITIVE_BLAST_FURNACE.setInfoRenderer(InfoRenderers.BASIC_RENDERER);
        COKE_OVEN.setInfoRenderer(InfoRenderers.BASIC_RENDERER);
        SOLID_FUEL_BOILERS.setInfoRenderer(InfoRenderers.BASIC_RENDERER);
        ALLOY_SMELTER.setInfoRenderer(ALLOY_SMELTER_RENDERER);
        BATH.setInfoRenderer(InfoRenderers.BASIC_RENDERER);
        COMBUSTION_FUELS.setInfoRenderer(InfoRenderers.FUEL_RENDERER);
        GAS_FUELS.setInfoRenderer(InfoRenderers.FUEL_RENDERER);
        SEMI_FUELS.setInfoRenderer(InfoRenderers.FUEL_RENDERER);
        MAGIC_FUELS.setInfoRenderer(MAGIC_FUEL_RENDERER);
        ORE_BYPRODUCTS.setInfoRenderer(InfoRenderers.EMPTY_RENDERER);
        STEAM_FUELS.setInfoRenderer(InfoRenderers.FUEL_RENDERER);
        HP_STEAM_FUELS.setInfoRenderer(InfoRenderers.FUEL_RENDERER);
        LARGE_BOILERS.setInfoRenderer(LARGE_BOILER_RENDERER);
        HEAT_EXCHANGER.setInfoRenderer(HEAT_EXCHANGER_RENDERER);
        FUSION.setInfoRenderer(FUSION_RENDERER);
        CHEMICAL_REACTOR.setInfoRenderer(CHEM_RENDERER);
        PULVERIZER.setInfoRenderer(MACERATOR_RENDERER);
        ELECTRIC_FURNACE.setInfoRenderer(STEAM_RENDERER);
        COMPRESSOR.setInfoRenderer(STEAM_RENDERER);
        EXTRACTOR.setInfoRenderer(STEAM_RENDERER);
        CUTTER.setInfoRenderer(STEAM_RENDERER);
        SIFTER.setInfoRenderer(STEAM_RENDERER);
        FORGE_HAMMER.setInfoRenderer(STEAM_RENDERER);
    }

    public static class PulverizerBuilder extends RecipeBuilder{
        @Override
        public IRecipe add(String domain, String id) {
            IRecipe recipe = super.add(domain, id);
            var  recipeBuilder = MACERATOR.RB().hide().ii(recipe.getInputItems());
            if (recipe.hasOutputItems() && !recipe.getOutputItems().isEmpty()) {
                recipeBuilder.io(recipe.getOutputItems(false).get(0));
            }
            if (recipe.hasOutputChances() && recipe.getOutputChances().length > 0) {
                recipeBuilder.outputChances(recipe.getOutputChances()[0]);
            }
            recipeBuilder.inputChances(recipe.getInputChances()).add(domain, id, recipe.getDuration(), recipe.getPower(), recipe.getSpecialValue(), recipe.getAmps());
            return recipe;
        }
    }
}
