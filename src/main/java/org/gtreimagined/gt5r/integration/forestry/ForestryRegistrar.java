package org.gtreimagined.gt5r.integration.forestry;

import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.capability.IIndividualHandlerItem;
import forestry.core.utils.GeneticsUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import org.gtreimagined.gt5r.blockentity.single.BlockEntityScanner;
import org.gtreimagined.gt5r.datagen.GT5RLocalizations;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.datagen.GTLibDynamics;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTypeItem;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.recipe.map.RecipeBuilder;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.util.Utils;

import java.util.Arrays;

import static org.gtreimagined.gt5r.GT5Reimagined.ID;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.*;
import static org.gtreimagined.gtlib.Ref.L;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class ForestryRegistrar extends GTMod {

    public static boolean EASY_COMB_RECIPES = false;

    public ForestryRegistrar() {
        if (isEnabled()){
            GTLibDynamics.clientProvider(ID, ForestryLangProvider::new);
        }
    }

    @Override
    public void onRegistrationEvent(RegistrationEvent event, Dist side) {
        if (event == RegistrationEvent.DATA_INIT){
            GTCombs.init();
        }
        if (event == RegistrationEvent.DATA_READY){
            BlockEntityScanner.addScannerFunction((specimen, data, player) -> {
                if (IIndividualHandlerItem.isIndividual(specimen)){
                    ItemStack out = specimen.copy();
                    ItemStack convertedSpecimen = GeneticsUtil.convertToGeneticEquivalent(specimen);
                    if (!ItemStack.matches(specimen, convertedSpecimen)) {
                        out = convertedSpecimen.copy();
                    }
                    IIndividual individual = IIndividualHandlerItem.getIndividual(out);

                    // Analyze if necessary
                    if (individual != null) {
                        if (!individual.isAnalyzed()) {
                            if (individual.analyze()) {
                                if (player != null){
                                    IBreedingTracker breedingTracker = individual.getType().getBreedingTracker(player.level(), player.getGameProfile());
                                    breedingTracker.registerSpecies(individual.getSpecies());
                                    breedingTracker.registerSpecies(individual.getInactiveSpecies());
                                }

                                individual.saveToStack(out);
                                return SCANNER.RB().recipeMapOnly().ii(RecipeIngredient.of(specimen)).fi(Honey.getFluidIngredient(100)).io(out).add("foresty_bee_"+ individual.getType().id().getPath(), 500, 2);
                            }
                        }
                    }
                }
                return null;
            });
            BlockEntityScanner.addScannerFilter(IIndividualHandlerItem::isIndividual);
        }
    }

    @Override
    public String getId() {
        return Ref.MOD_FR;
    }




    public static void init() {

        ItemStack comb;

        //Organic Line
        comb = new ItemStack(GTCombs.LIGNITE_COMB);
        addSpecialCent("lignite_comb", comb, new int[]{90}, GEM.get(Lignite, 1));
        addProcessMain("lignite_comb", comb, Lignite);
        comb = new ItemStack(GTCombs.COAL_COMB);
        addSpecialCent("coal_comb", comb, new int[]{40}, GEM.get(Coal, 1));
        addProcessMain("coal_comb", comb, Coal);
        comb = new ItemStack(GTCombs.RESIN_COMB);
        addSpecialCent("resin_comb", comb, new int[]{70}, new ItemStack(GTCoreItems.StickyResin));
        comb = new ItemStack(GTCombs.OIL_COMB);
        //addSpecialCent(comb, new int[]{70}, Data.DropOil.get(2));
        addProcessMain("oil_comb", comb, OilShale);

        //Gem Line
        //TODO duplicate recipes
        comb = new ItemStack(GTCombs.STONE_COMB);
        addSpecialCent("stone_comb", comb, new int[]{70, 20, 20}, DUST.get(Stone, 1), DUST.get(Salt, 1), DUST.get(Sylvite, 1));
        addProcessMain("stone_comb", comb, Soapstone);
        addProcess("stone_comb_to_talc", comb, Talc);
        addProcess("stone_comb_to_apatite", comb, Apatite);
        addProcess("stone_comb_to_phosphate", comb, Phosphate);
        addProcess("stone_comb_to_phosphor", comb, Phosphor);
        comb = new ItemStack(GTCombs.CERTUS_COMB);
        Material quartz = GTAPI.isModLoaded(Ref.MOD_AE) ? CertusQuartz : Quartz;
        addProcessMain("certus_comb", comb, quartz);
        addProcess("certus_comb_to_milky_quartz", comb, MilkyQuartz);
        addProcess("certus_comb_to_barite", comb, Barite);
        //TODO duplicate recipes
        comb = new ItemStack(GTCombs.REDSTONE_COMB);
        addProcessMain("redstone_comb", comb, Redstone);
        addProcess("redstone_comb_to_cinnabar", comb, Cinnabar);
        comb = new ItemStack(GTCombs.LAPIS_COMB);
        addProcessMain("lapis_comb", comb, Lapis);
        addProcess("lapis_comb_to_sodalite", comb, Sodalite);
        addProcess("lapis_comb_to_lazurite", comb, Lazurite);
        addProcess("lapis_comb_to_calcite", comb, Calcite);
        comb = new ItemStack(GTCombs.RUBY_COMB);
        addProcessMain("ruby_comb", comb, Ruby);
        addProcess("ruby_comb_to_redstone", comb, Redstone);
        comb = new ItemStack(GTCombs.SAPPHIRE_COMB);
        addProcessMain("sapphire_comb", comb, Sapphire);
        addProcess("sapphire_comb_to_green_sapphire", comb, GreenSapphire);
        addProcess("sapphire_comb_to_almandine", comb, Almandine);
        addProcess("sapphire_comb_to_pyrope", comb, Pyrope);
        comb = new ItemStack(GTCombs.DIAMOND_COMB);
        addProcessMain("diamond_comb", comb, Diamond);
        addProcess("diamond_comb_t-_graphite", comb, Graphite);
        comb = new ItemStack(GTCombs.OLIVINE_COMB);
        addProcessMain("olivine_comb", comb, Olivine);
        addProcess("olivine_comb_to_bentonite", comb, Bentonite);
        addProcess("olivine_comb_to_magnesite", comb, Magnesite);
        addProcess("olivine_comb_to_glauconite", comb, Glauconite);
        comb = new ItemStack(GTCombs.EMERALD_COMB);
        addProcessMain("emerald_comb", comb, Emerald);
        addProcess("emerald_comb_to_beryllium", comb, Beryllium);
        addProcess("emerald_comb_to_thorium", comb, Thorium);


        //Metals Line
        comb = new ItemStack(GTCombs.SLAG_COMB);
        addSpecialCent("slag_comb", comb, new int[]{50, 20, 20}, DUST.get(Stone, 1), DUST.get(BlackGranite, 1), DUST.get(RedGranite, 1));
        addProcessMain("slag_comb", comb, Salt);
        addProcess("slag_comb_to_sylvite", comb, Sylvite);
        addProcess("slag_comb_to_lepidolite", comb, Lepidolite);
        addProcess("slag_comb_to_spodumene", comb, Spodumene);
        addProcess("slag_comb_to_monazite", comb, Monazite);
        comb = new ItemStack(GTCombs.COPPER_COMB);
        addSpecialCent("copper_comb", comb, new int[]{70}, TINY_DUST.get(Copper, 1));
        addProcessMain("copper_comb", comb, Copper);
        addProcess("copper_comb_to_tetrahedrite", comb, Tetrahedrite);
        addProcess("copper_comb_to_chalcopyrite", comb, Chalcopyrite);
        addProcess("copper_comb_to_malachite", comb, Malachite);
        addProcess("copper_comb_to_pyrite", comb, Pyrite);
        addProcess("copper_comb_to_stibnite", comb, Stibnite);
        comb = new ItemStack(GTCombs.TIN_COMB);
        addSpecialCent("tin_comb", comb, new int[]{60}, TINY_DUST.get(Tin, 1));
        addProcessMain("tin_comb", comb, Tin);
        addProcess("tin_comb_to_cassiterite", comb, Cassiterite);
        comb = new ItemStack(GTCombs.LEAD_COMB);
        addSpecialCent("lead_comb", comb, new int[]{45}, TINY_DUST.get(Lead, 1));
        addProcessMain("lead_comb", comb, Lead);
        addProcess("lead_comb_to_galena", comb, Galena);
        comb = new ItemStack(GTCombs.IRON_COMB);
//        addProcess(comb, Iron);
        addProcessMain("iron_comb", comb, Magnetite);
        addProcess("iron_comb_to_brown_limonite", comb, BrownLimonite);
        addProcess("iron_comb_to_yellow_limonite", comb, YellowLimonite);
        addProcess("iron_comb_to_vanadium_magnetite", comb, VanadiumMagnetite);
        addProcess("iron_comb_to_hematite", comb, Hematite);
        addProcess("iron_comb_to_pyrite", comb, Pyrite);
        //TODO GC Compat if (ProcessingModSupport.aEnableGCMarsMats) addProcess(comb, MeteoricIron);
        comb = new ItemStack(GTCombs.STEEL_COMB);
        addProcessMain("steel_comb", comb, Iron);
        addProcess("steel_comb_to_magnetite", comb, Magnetite);
        addProcess("steel_comb_to_brown_limonite", comb, BrownLimonite);
        addProcess("steel_comb_to_yellow_limonite", comb, YellowLimonite);
        addProcess("steel_comb_to_vanadium_magnetite", comb, VanadiumMagnetite);
        addProcess("steel_comb_to_hematite", comb, Hematite);
        addProcess("steel_comb_to_pyrite", comb, Pyrite);
        //TODO GC Compat if (ProcessingModSupport.aEnableGCMarsMats) addProcess(comb, MeteoricIron, MeteoricSteel);
        addProcess("steel_comb_to_molybdenite", comb, Molybdenite);
        addProcess("steel_comb_to_molybdenum", comb, Molybdenum);
        comb = new ItemStack(GTCombs.NICKEL_COMB);
        addProcessMain("nickel_comb", comb, Nickel);
        addProcess("nickel_comb_to_garnierite", comb, Garnierite);
        addProcess("nickel_comb_to_pentlandite", comb, Pentlandite);
        addProcess("nickel_comb_to_cobaltite", comb, Cobaltite);
        addProcess("nickel_comb_to_wulfenite", comb, Wulfenite);
        addProcess("nickel_comb_to_powellite", comb, Powellite);
        comb = new ItemStack(GTCombs.ZINC_COMB);
        addProcessMain("zinc_comb", comb, Zinc);
        addProcess("zinc_comb_to_sphalerite", comb, Sphalerite);
        addProcess("zinc_comb_to_sulfur", comb, Sulfur);
        comb = new ItemStack(GTCombs.SILVER_COMB);
        addSpecialCent("silver_comb", comb, new int[]{30}, TINY_DUST.get(Silver, 1));
        addProcessMain("silver_comb", comb, Silver);
        addProcess("silver_comb_to_galena", comb, Galena);
        comb = new ItemStack(GTCombs.GOLD_COMB);
        addProcessMain("gold_comb", comb, Gold);
        addProcess("gold_comb_to_magnetite", comb, Magnetite, Gold);

        //Rare Metals Line
        comb = new ItemStack(GTCombs.ALUMINIUM_COMB);
        addProcessMain("aluminium_comb", comb, 60, Alumina);
        addProcess("aluminium_comb_to_bauxite", comb, Bauxite);
        comb = new ItemStack(GTCombs.MAGANESE_COMB);
        addProcessMain("manganese_comb", comb, 30, Manganese);
        addProcess("manganese_comb_to_grossular", comb, Grossular);
        addProcess("manganese_comb_to_spessartine", comb, Spessartine);
        addProcess("manganese_comb_to_pyrolusite", comb, Pyrolusite);
        addProcess("manganese_comb_to_tantalite", comb, Tantalite);
        comb = new ItemStack(GTCombs.TITANIUM_COMB);
        addProcessMain("titanium_comb", comb, Titanium);
        addProcess("titanium_comb_to_ilmenite", comb, Ilmenite);
        addProcess("titanium_comb_to_bauxite", comb, Bauxite);
        comb = new ItemStack(GTCombs.CHROME_COMB);
        addProcessMain("chromium_comb", comb, 50, Chromite);
        addProcess("chromium_comb_to_ruby", comb, Ruby);
        addProcess("chromium_comb_to_chromite", comb, 50, Chromite);
        addProcess("chromium_comb_to_redstone", comb, Redstone);
        addProcess("chromium_comb_to_neodymium", comb, Neodymium);
        addProcess("chromium_comb_to_bastnasite", comb, Bastnasite);
        comb = new ItemStack(GTCombs.TUNGSTEN_COMB);
        addProcessMain("tungsten_comb", comb, Tungstate);
        addProcess("tungsten_comb_to_scheelite", comb, Scheelite);
        addProcess("tungsten_comb_to_lithium", comb, Lithium);
        comb = new ItemStack(GTCombs.PLATINUM_COMB);
        addProcessMain("platinum_comb", comb, 40, Platinum);
        addProcess("platinum_comb_to_sheldonite", comb, 40, Sheldonite);
        addProcess("platinum_comb_to_palladium", comb, 40, Palladium);
        comb = new ItemStack(GTCombs.IRIDIUM_COMB);
        addProcessMain("iridium_comb", comb, 20, Iridium);
        addProcess("iridium_comb_to_osmium", comb, 20, Osmium);

        //Radioactive Line
        comb = new ItemStack(GTCombs.URANIUM_COMB);
        addProcessMain("uranium_comb", comb, 50, Uraninite);
        addProcess("uranium_comb_to_putchblende", comb, 50, Pitchblende);
        addProcess("uranium_comb_to_u235", comb, 50, Uranium235);
        comb = new ItemStack(GTCombs.PLUTONIUM_COMB);
        addProcessMain("plutonium_comb", comb, 10, Plutonium);
        addProcess("plutonium_comb_to_u235", comb, 5, Uranium235, Plutonium);
        comb = new ItemStack(GTCombs.NAQUADAH_COMB);
        addProcessMain("naquadah_comb", comb, 10, Naquadah);
        addProcess("naquadah_comb_to_enriched_naquadah", comb, 10, EnrichedNaquadah);
        addProcess("naquadah_comb_to_naquadria", comb, 10, Naquadria);

        ASSEMBLER.RB().ii(DUST.getMaterialIngredient(Redstone, 2), INGOT.getMaterialIngredient(Copper, 5)).fi(Glass.getLiquid(L / 2 )).io(new ItemStack(getFRItem("electron_tube_copper"), 4)).add("electron_tube_copper", 64, 32);
        ASSEMBLER.RB().ii(DUST.getMaterialIngredient(Redstone, 2), INGOT.getMaterialIngredient(AnnealedCopper, 5)).fi(Glass.getLiquid(L / 2 )).io(new ItemStack(getFRItem("electron_tube_copper"), 4)).add("electron_tube_annealed_copper", 64, 32);
        ASSEMBLER.RB().ii(DUST.getMaterialIngredient(Redstone, 2), INGOT.getMaterialIngredient(Tin, 5)).fi(Glass.getLiquid(L / 2 )).io(new ItemStack(getFRItem("electron_tube_tin"), 4)).add("electron_tube_tin", 64, 32);
        ASSEMBLER.RB().ii(DUST.getMaterialIngredient(Redstone, 2), INGOT.getMaterialIngredient(Bronze, 5)).fi(Glass.getLiquid(L / 2 )).io(new ItemStack(getFRItem("electron_tube_bronze"), 4)).add("electron_tube_bronze", 64, 32);
        if (!GTAPI.isModLoaded("tfc")) ASSEMBLER.RB().ii(DUST.getMaterialIngredient(Redstone, 2), INGOT.getMaterialIngredient(Iron, 5)).fi(Glass.getLiquid(L / 2 )).io(new ItemStack(getFRItem("electron_tube_iron"), 4)).add("electron_tube_iron", 64, 32);
        ASSEMBLER.RB().ii(DUST.getMaterialIngredient(Redstone, 2), INGOT.getMaterialIngredient(WroughtIron, 5)).fi(Glass.getLiquid(L / 2 )).io(new ItemStack(getFRItem("electron_tube_iron"), 4)).add("electron_tube_wrought_iron", 64, 32);
        ASSEMBLER.RB().ii(DUST.getMaterialIngredient(Redstone, 2), INGOT.getMaterialIngredient(Gold, 5)).fi(Glass.getLiquid(L / 2 )).io(new ItemStack(getFRItem("electron_tube_gold"), 4)).add("electron_tube_gold", 64, 32);
        ASSEMBLER.RB().ii(DUST.getMaterialIngredient(Redstone, 2), GEM.getMaterialIngredient(Diamond, 5)).fi(Glass.getLiquid(L / 2 )).io(new ItemStack(getFRItem("electron_tube_diamond"), 4)).add("electron_tube_diamantine", 64, 32);
        ASSEMBLER.RB().ii(DUST.getMaterialIngredient(Redstone, 2), RecipeIngredient.of(Items.OBSIDIAN, 5)).fi(Glass.getLiquid(L / 2 )).io(new ItemStack(getFRItem("electron_tube_obsidian"), 4)).add("electron_tube_obsidian", 64, 32);
        ASSEMBLER.RB().ii(DUST.getMaterialIngredient(Redstone, 2), DUST.getMaterialIngredient(Blaze, 5)).fi(Glass.getLiquid(L / 2 )).io(new ItemStack(getFRItem("electron_tube_blaze"), 4)).add("electron_tube_blaze", 64, 32);
        ASSEMBLER.RB().ii(DUST.getMaterialIngredient(Redstone, 2), GEM.getMaterialIngredient(Emerald, 5)).fi(Glass.getLiquid(L / 2 )).io(new ItemStack(getFRItem("electron_tube_emerald"), 4)).add("electron_tube_emerald", 64, 32);
        ASSEMBLER.RB().ii(DUST.getMaterialIngredient(Redstone, 2), GEM.getMaterialIngredient(Apatite, 5)).fi(Glass.getLiquid(L / 2 )).io(new ItemStack(getFRItem("electron_tube_apatite"), 4)).add("electron_tube_apatite", 64, 32);
        ASSEMBLER.RB().ii(DUST.getMaterialIngredient(Redstone, 2), GEM.getMaterialIngredient(Lapis, 5)).fi(Glass.getLiquid(L / 2 )).io(new ItemStack(getFRItem("electron_tube_lapis"), 4)).add("electron_tube_lapis", 64, 32);
        ASSEMBLER.RB().ii(DUST.getMaterialIngredient(EnderEye, 2), RecipeIngredient.of(Items.END_STONE, 5)).fi(Glass.getLiquid(L / 2 )).io(new ItemStack(getFRItem("electron_tube_ender"), 4)).add("electron_tube_ender", 64, 32);
        FLUID_PRESS.RB().ii(getFRItem("honey_drop")).io(getFRItem("propolis_normal")).outputChances(0.05).fo(Honey.getLiquid(100)).add("honey_drop", 400, 2);
        FLUID_PRESS.RB().ii(getFRItem("honeydew")).fo(Honey.getLiquid(100)).add("honeydew", 400, 2);
    }

    public static void addProcessMain(String id, ItemStack stack, Material... materials) {
        addProcessMain(id, stack, 100, materials);
    }

    public static void addProcessMain(String id, ItemStack stack, int chance, Material... materials) {
        if (!EASY_COMB_RECIPES) {
            if (materials.length == 0) return;
            MaterialTypeItem<?> crushed = materials[0].has(CRUSHED_ORE) ? CRUSHED_ORE : DUST;
            MaterialTypeItem<?> purified = (materials.length == 2 ? materials[1] : materials[0]).has(PURIFIED_ORE) ? PURIFIED_ORE : DUST;
            RecipeBuilder rb = CHEMICAL_REACTOR.RB().ii(RecipeIngredient.of(9, stack), crushed.getMaterialIngredient(materials[0], 1)).fi(Water.getLiquid(1000)).io(purified.get(materials.length == 2 ? materials[1] : materials[0], 4));
            if (!materials[0].getByProducts().isEmpty() && materials[0].getByProducts().get(0).has(LIQUID)) {
                rb.fo(materials[0].getByProducts().get(0).getLiquid(144));
            }
            rb.add(id, 96, 24);
            AUTOCLAVE.RB().ii(Ingredient.of(Utils.ca(16, stack))).fi(UUMatter.getLiquid((int)Math.max(1, ((materials[0].getMass()+9)/10)))).io(crushed.get(materials[0])).add(id, materials[0].getMass() * 128, 384);
        } else {
            CENTRIFUGE.RB().ii(Ingredient.of(stack)).io(TINY_DUST.get(materials[0]), getFRItem("beeswax")).outputChances(chance * 100, 3000).add(id, 128, 5);
        }
    }

    public static void addSpecialCent(String id, ItemStack stack, int[] chances, ItemStack... outputs) {
        for (int i = 0; i < chances.length; i++) {
            chances[i] *= 100;
        }
        int[] chancesCopy = Arrays.copyOf(chances, chances.length + 1);
        chancesCopy[chances.length] = 3000;
        ItemStack[] outputsCopy = Arrays.copyOf(outputs, outputs.length + 1);
        outputsCopy[outputs.length] = new ItemStack(getFRItem("beeswax"));
        CENTRIFUGE.RB().ii(Ingredient.of(stack)).io(outputsCopy).outputChances(chancesCopy).add(id, 128, 5);
    }

    public static void addProcess(String id, ItemStack stack, Material... materials) {
        addProcess(id, stack, 10000, materials);
    }

    public static void addProcess(String id, ItemStack stack, int chance, Material... materials) {
        if (!EASY_COMB_RECIPES) {
            if (materials.length == 0) return;
            MaterialTypeItem<?> crushed = materials[0].has(CRUSHED_ORE) ? CRUSHED_ORE : DUST;
            MaterialTypeItem<?> purified = (materials.length == 2 ? materials[1] : materials[0]).has(PURIFIED_ORE) ? PURIFIED_ORE : DUST;
            RecipeBuilder rb = CHEMICAL_REACTOR.RB().ii(RecipeIngredient.of(Utils.ca(9, stack)), crushed.getMaterialIngredient(materials[0], 1)).fi(Water.getLiquid(1000)).io(purified.get(materials.length == 2 ? materials[1] : materials[0], 4));
            if (!materials[0].getByProducts().isEmpty() && materials[0].getByProducts().get(0).has(LIQUID)) {
                rb.fo(materials[0].getByProducts().get(0).getLiquid(144));
            }
            rb.add(id, 96, 24);
        } else {
            CENTRIFUGE.RB().ii(RecipeIngredient.of(stack)).io(TINY_DUST.get(materials[0]), getFRItem("beeswax")).outputChances(chance * 100, 3000).add(id, 128, 5);
        }
    }

    static Item getFRItem(String id){
        return RegistryUtils.getItemFromID(Ref.MOD_FR, id);
    }

}
