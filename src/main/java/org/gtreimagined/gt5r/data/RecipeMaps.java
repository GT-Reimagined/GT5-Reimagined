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

    public static final IRecipeInfoRenderer BLASTING_RENDERER = r -> {
        if (r.getDuration() == 0 && r.getPower() == 0) return List.of();
        List<Component> list = new ArrayList<>();
        Component additional;
        if (r.getDuration() < 1200) {
            additional = Component.empty();
        } else if (r.getDuration() < 36000) {
            additional = Utils.translatable("recipe_info.gtlib.duration.seconds", (r.getDuration() / 20.0f));
        } else {
            additional = Utils.translatable("recipe_info.gtlib.duration.minutes", (r.getDuration() / 1200.0f));
        }
        list.add(Utils.translatable("recipe_info.gtlib.duration", r.getDuration(), additional));
        Tier tier = Tier.getTier((r.getPower() / r.getAmps()));
        list.add(Utils.translatable("recipe_info.gtlib.eut", r.getPower(), Utils.translatable("recipe_info.gtlib.eut.tier", tier.getId().toUpperCase(Locale.ROOT)).withStyle(tier.getRarityFormatting())));
        list.add(Utils.translatable("recipe_info.gtlib.amps", r.getAmps()));
        list.add(Utils.translatable("recipe_info.gt5r.temperature", r.getSpecialValue()));
        list.add(Utils.translatable("recipe_info.gtlib.total_eu", r.getDuration() * r.getPower() * r.getAmps()));
        return list;
    };

    public static final IRecipeInfoRenderer LARGE_BOILER_RENDERER = r -> {
        List<Component> list = new ArrayList<>();
        Component additional;
        if (r.getDuration() < 1200) {
            additional = Component.empty();
        } else if (r.getDuration() < 36000) {
            additional = Utils.translatable("recipe_info.gtlib.duration.seconds", (r.getDuration() / 20.0f));
        } else {
            additional = Utils.translatable("recipe_info.gtlib.duration.minutes", (r.getDuration() / 1200.0f));
        }
        list.add(Utils.translatable("recipe_info.gtlib.duration", r.getDuration(), additional));
        list.add(Utils.translatable("recipe_info.gt5r.extra_burntime", r.getPower()));
        list.add(Utils.translatable("recipe_info.gt5r.heat_multiplier", Math.max(r.getSpecialValue(), 1)));
        return list;
    };

    public static final IRecipeInfoRenderer HEAT_EXCHANGER_RENDERER = new IRecipeInfoRenderer() {
        @Override
        public void render(GuiGraphics graphics, IRecipe recipe, Font fontRenderer, int guiOffsetX, int guiOffsetY) {
            String additional = recipe.getDuration() < 1200 ? "" : recipe.getDuration() < 36000 ? " (" + (recipe.getDuration() / 20.0f) + " secs)" : " (" + (recipe.getDuration() / 1200.0f) + " mins)";
            String duration = "Duration: " + recipe.getDuration() + " ticks" + additional;
            String extraBurntime = "HU/t: " + recipe.getPower();
            String heatIncreaseMultiplier = "Total HU: " + (recipe.getPower() * recipe.getDuration());
            String heatExchanger = recipe.getTags().contains(GT5RRecipeTags.LARGE_HEAT_EXCHANGED_ONLY) ? "Large Heat Exchanger only" : recipe.getTags().contains(GT5RRecipeTags.SMALL_HEAT_EXCHANGED_ONLY) ? "Small Heat Exchanger only" : "";
            renderString(graphics, duration, fontRenderer, 5, 0, guiOffsetX, guiOffsetY);
            renderString(graphics, extraBurntime, fontRenderer, 5, 10, guiOffsetX, guiOffsetY);
            renderString(graphics, heatIncreaseMultiplier, fontRenderer, 5, 20, guiOffsetX, guiOffsetY);
            renderString(graphics, heatExchanger, fontRenderer, 5, 30, guiOffsetX, guiOffsetY);
        }

        @Override
        public int getRows() {
            return 4;
        }
    };

    public static final IRecipeInfoRenderer FUSION_RENDERER = new IRecipeInfoRenderer() {
        public void render(GuiGraphics graphics, IRecipe recipe, Font fontRenderer, int guiOffsetX, int guiOffsetY) {
            if (!(recipe instanceof FusionRecipe fusionRecipe)) return;
            if (recipe.getDuration() == 0) return;
            String additional = recipe.getDuration() < 1200 ? "" : recipe.getDuration() < 36000 ? " (" + (recipe.getDuration() / 20.0f) + " secs)" : " (" + (recipe.getDuration() / 1200.0f) + " mins)";
            String power = "Duration: " + recipe.getDuration() + " ticks" + additional;
            String euT = "EU/t: " + recipe.getPower();
            String huT = "Creates " + fusionRecipe.getHuOutput() + " HU/t";
            String total = "Total: " + recipe.getPower() * recipe.getDuration() + " EU";
            String start = "Start: " + recipe.getSpecialValue() + " EU";
            Tier tier = Tier.getTier(recipe.getPower() / recipe.getAmps());
            Tier outputTier = Tier.getTier(fusionRecipe.getHuOutput());
            String formattedText = " (" + tier.getId().toUpperCase() + ")";
            String formattedText1 = " (" + outputTier.getId().toUpperCase() + ")";
            renderString(graphics, power, fontRenderer, 5, 0, guiOffsetX, guiOffsetY);
            renderString(graphics, euT, fontRenderer, 5, 10, guiOffsetX, guiOffsetY);
            renderString(graphics, formattedText, fontRenderer, 5 + stringWidth(euT, fontRenderer), 10, Tier.EV.getRarityFormatting().getColor(), guiOffsetX, guiOffsetY);
            renderString(graphics, huT, fontRenderer, 5, 20, guiOffsetX, guiOffsetY);
            renderString(graphics, formattedText1, fontRenderer, 5 + stringWidth(huT, fontRenderer), 20, Tier.ULV.getRarityFormatting().getColor(), guiOffsetX, guiOffsetY, false);
            renderString(graphics, total, fontRenderer, 5, 30, guiOffsetX, guiOffsetY);
            renderString(graphics, start, fontRenderer, 5, 40, guiOffsetX, guiOffsetY);
        }

        @Override
        public int getRows() {
            return 5;
        }
    };

    public static final IRecipeInfoRenderer CHEM_RENDERER = new IRecipeInfoRenderer() {
        @Override
        public void render(GuiGraphics graphics, IRecipe recipe, Font fontRenderer, int guiOffsetX, int guiOffsetY) {
            if (recipe.getDuration() == 0 && recipe.getPower() == 0) return;
            String additional = recipe.getDuration() < 1200 ? "" : recipe.getDuration() < 36000 ? " (" + (recipe.getDuration() / 20.0f) + " secs)" : " (" + (recipe.getDuration() / 1200.0f) + " mins)";
            String power = "Duration: " + recipe.getDuration() + " ticks" + additional;
            String euT = "EU/t: " + recipe.getPower();
            String total = "Total: " + recipe.getPower() * recipe.getDuration() + " EU";
            String complicated = recipe.getSpecialValue() == -1 ? "Complicated Recipe" : null;
            boolean toLarge = false;
            for (FluidIngredient outputFluid : recipe.getInputFluids()) {
                if (outputFluid.getAmount() > 32000){
                    toLarge = true;
                    break;
                }
            }
            Tier tier = Tier.getTier((recipe.getPower() / recipe.getAmps()));
            String formattedText = " (" + tier.getId().toUpperCase() + ")";
            renderString(graphics, power, fontRenderer, 5, 0, guiOffsetX, guiOffsetY);
            renderString(graphics, euT, fontRenderer, 5, 10, guiOffsetX, guiOffsetY);
            renderString(graphics, formattedText, fontRenderer, 5 + stringWidth(euT, fontRenderer), 10, Tier.EV.getRarityFormatting().getColor(), guiOffsetX, guiOffsetY);
            renderString(graphics, total, fontRenderer, 5, 20, guiOffsetX, guiOffsetY);
            if (complicated != null){
                renderString(graphics, complicated, fontRenderer, 5, 30, guiOffsetX, guiOffsetY);
            }
            if (toLarge){
                renderString(graphics, "Large chem reactor only", fontRenderer, 5, complicated != null ? 40 : 30, guiOffsetX, guiOffsetY);
            }
        }

        @Override
        public int getRows() {
            return 5;
        }
    };

    public static final IRecipeInfoRenderer MACERATOR_RENDERER = new IRecipeInfoRenderer() {
        public void render(GuiGraphics graphics, IRecipe recipe, Font fontRenderer, int guiOffsetX, int guiOffsetY) {
            if (recipe.getDuration() == 0 && recipe.getPower() == 0) return;
            String additional = recipe.getDuration() < 1200 ? "" : recipe.getDuration() < 36000 ? " (" + (recipe.getDuration() / 20.0f) + " secs)" : " (" + (recipe.getDuration() / 1200.0f) + " mins)";
            String power = "Duration: " + recipe.getDuration() + " ticks" + additional;
            String euT = "EU/t: " + recipe.getPower();
            String amps = "Amps: " + recipe.getAmps();
            String total = "Total: " + recipe.getPower() * recipe.getDuration() + " EU";
            long steamDuration = recipe.getDuration() * 2L;
            long steamPower = recipe.getPower() * 2L;
            String steamT = "Steam: " + steamPower + " mb/t";
            String steamAdditional = steamDuration < 1200 ? "" : steamDuration < 36000 ? " (" + (steamDuration / 20.0f) + " secs)" : " (" + (steamDuration / 1200.0f) + " mins)";
            String steamLength = "Steam Duration: " + steamDuration + " ticks" + steamAdditional;
            Tier tier = Tier.getTier((recipe.getPower() / recipe.getAmps()));
            String formattedText = " (" + tier.getId().toUpperCase() + ")";
            renderString(graphics, power, fontRenderer, 5, 0, guiOffsetX, guiOffsetY);
            renderString(graphics, euT, fontRenderer, 5, 10, guiOffsetX, guiOffsetY);
            renderString(graphics, formattedText, fontRenderer, 5 + stringWidth(euT, fontRenderer), 10, Tier.EV.getRarityFormatting().getColor(), guiOffsetX, guiOffsetY);
            renderString(graphics, amps, fontRenderer, 5, 20, guiOffsetX, guiOffsetY);
            renderString(graphics, total, fontRenderer, 5, 30, guiOffsetX, guiOffsetY);
            if (steamPower <= Tier.LV.getVoltage()){
                renderString(graphics, steamT, fontRenderer, 5, 40, guiOffsetX, guiOffsetY);
                renderString(graphics, steamLength, fontRenderer, 5, 50, guiOffsetX, guiOffsetY);
            } else {
                renderString(graphics, "Not runnable in Steam machines", fontRenderer, 5, 40, guiOffsetX, guiOffsetY);
            }
            renderString(graphics, "Byproducts in pulverizer only", fontRenderer, 5, steamPower <= Tier.LV.getVoltage() ? 60 : 50, guiOffsetX, guiOffsetY);
        }

        @Override
        public int getRows() {
            return 7;
        }
    };

    public static final IRecipeInfoRenderer ALLOY_SMELTER_RENDERER = new IRecipeInfoRenderer() {
        public void render(GuiGraphics graphics, IRecipe recipe, Font fontRenderer, int guiOffsetX, int guiOffsetY) {
            if (recipe.getDuration() == 0 && recipe.getPower() == 0) return;
            String additional = recipe.getDuration() < 1200 ? "" : recipe.getDuration() < 36000 ? " (" + (recipe.getDuration() / 20.0f) + " secs)" : " (" + (recipe.getDuration() / 1200.0f) + " mins)";
            String power = "Duration: " + recipe.getDuration() + " ticks" + additional;
            String euT = "EU/t: " + recipe.getPower();
            String amps = "Amps: " + recipe.getAmps();
            String total = "Total: " + recipe.getPower() * recipe.getDuration() + " EU";
            long steamDuration = recipe.getDuration() * 2L;
            long steamPower = recipe.getPower() * 2L;
            String steamT = "Steam: " + steamPower + " mb/t";
            String steamAdditional = steamDuration < 1200 ? "" : steamDuration < 36000 ? " (" + (steamDuration / 20.0f) + " secs)" : " (" + (steamDuration / 1200.0f) + " mins)";
            String steamLength = "Steam Duration: " + steamDuration + " ticks" + steamAdditional;
            Tier tier = Tier.getTier((recipe.getPower() / recipe.getAmps()));
            String formattedText = " (" + tier.getId().toUpperCase() + ")";
            renderString(graphics, power, fontRenderer, 5, 0, guiOffsetX, guiOffsetY);
            renderString(graphics, euT, fontRenderer, 5, 10, guiOffsetX, guiOffsetY);
            renderString(graphics, formattedText, fontRenderer, 5 + stringWidth(euT, fontRenderer), 10, Tier.EV.getRarityFormatting().getColor(), guiOffsetX, guiOffsetY);
            renderString(graphics, amps, fontRenderer, 5, 20, guiOffsetX, guiOffsetY);
            renderString(graphics, total, fontRenderer, 5, 30, guiOffsetX, guiOffsetY);
            if (recipe.getInputItems().size() > 2){
                renderString(graphics, "Multi Smelter only", fontRenderer, 5, 40, 0xFF0000, guiOffsetX, guiOffsetY, false);
            } else if (steamPower <= Tier.LV.getVoltage()){
                renderString(graphics, steamT, fontRenderer, 5, 40, guiOffsetX, guiOffsetY);
                renderString(graphics, steamLength, fontRenderer, 5, 50, guiOffsetX, guiOffsetY);
            } else {
                renderString(graphics, "Not runnable in Steam machines", fontRenderer, 5, 40, guiOffsetX, guiOffsetY);
            }
        }

        @Override
        public int getRows() {
            return 6;
        }
    };

    public static final IRecipeInfoRenderer STEAM_RENDERER = new IRecipeInfoRenderer() {
        public void render(GuiGraphics graphics, IRecipe recipe, Font fontRenderer, int guiOffsetX, int guiOffsetY) {
            if (recipe.getDuration() == 0 && recipe.getPower() == 0) return;
            String additional = recipe.getDuration() < 1200 ? "" : recipe.getDuration() < 36000 ? " (" + (recipe.getDuration() / 20.0f) + " secs)" : " (" + (recipe.getDuration() / 1200.0f) + " mins)";
            String power = "Duration: " + recipe.getDuration() + " ticks" + additional;
            String euT = "EU/t: " + recipe.getPower();
            String amps = "Amps: " + recipe.getAmps();
            String total = "Total: " + recipe.getPower() * recipe.getDuration() + " EU";
            long steamDuration = recipe.getDuration() * (recipe.getMapLoc().getPath().equals("plate_cutter") ? 4L : 2L);
            long steamPower = recipe.getMapLoc().getPath().equals("cutter") ? recipe.getPower() : recipe.getPower() * 2L;
            String steamT = "Steam: " + steamPower + " mb/t";
            String steamAdditional = steamDuration < 1200 ? "" : steamDuration < 36000 ? " (" + (steamDuration / 20.0f) + " secs)" : " (" + (steamDuration / 1200.0f) + " mins)";
            String steamLength = "Steam Duration: " + steamDuration + " ticks" + steamAdditional;
            Tier tier = Tier.getTier((recipe.getPower() / recipe.getAmps()));
            String formattedText = " (" + tier.getId().toUpperCase() + ")";
            renderString(graphics, power, fontRenderer, 5, 0, guiOffsetX, guiOffsetY);
            renderString(graphics, euT, fontRenderer, 5, 10, guiOffsetX, guiOffsetY);
            renderString(graphics, formattedText, fontRenderer, 5 + stringWidth(euT, fontRenderer), 10, Tier.EV.getRarityFormatting().getColor(), guiOffsetX, guiOffsetY);
            renderString(graphics, amps, fontRenderer, 5, 20, guiOffsetX, guiOffsetY);
            renderString(graphics, total, fontRenderer, 5, 30, guiOffsetX, guiOffsetY);
            if (steamPower <= Tier.LV.getVoltage()){
                renderString(graphics, steamT, fontRenderer, 5, 40, guiOffsetX, guiOffsetY);
                renderString(graphics, steamLength, fontRenderer, 5, 50, guiOffsetX, guiOffsetY);
            } else {
                renderString(graphics, "Not runnable in Steam machines", fontRenderer, 5, 40, guiOffsetX, guiOffsetY);
            }
        }

        @Override
        public int getRows() {
            return 6;
        }
    };

    public static final IRecipeInfoRenderer MAGIC_FUEL_RENDERER = new IRecipeInfoRenderer() {
        @Override
        public void render(GuiGraphics graphics, IRecipe recipe, Font fontRenderer, int guiOffsetX, int guiOffsetY) {
            if (recipe.hasInputFluids()){
                String fuelPerMb = "EU/L: " + ((double) recipe.getPower() / (double) Objects.requireNonNull(recipe.getInputFluids()).get(0).getAmount());
                String fuelPerB = "Fluid Amount / tick: " + Objects.requireNonNull(recipe.getInputFluids()).get(0).getAmount();
                renderString(graphics, fuelPerMb, fontRenderer, 5, 0, guiOffsetX, guiOffsetY);
                renderString(graphics, fuelPerB, fontRenderer, 5, 10, guiOffsetX, guiOffsetY);
            } else if (recipe.hasInputItems()){
                String fuelValue = "Fuel Value: " + recipe.getTotalPower() + " EU";
                renderString(graphics, fuelValue, fontRenderer, 5, 0, guiOffsetX, guiOffsetY);
            }
        }

        @Override
        public int getRows() {
            return 2;
        }
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
            if (recipe.hasOutputItems() && recipe.getOutputItems().length > 0) {
                recipeBuilder.io(recipe.getOutputItems(false)[0]);
            }
            if (recipe.hasOutputChances() && recipe.getOutputChances().length > 0) {
                recipeBuilder.outputChances(recipe.getOutputChances()[0]);
            }
            recipeBuilder.inputChances(recipe.getInputChances()).add(domain, id, recipe.getDuration(), recipe.getPower(), recipe.getSpecialValue(), recipe.getAmps());
            return recipe;
        }
    }
}
