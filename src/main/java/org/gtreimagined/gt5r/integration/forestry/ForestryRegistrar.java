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
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTypeItem;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.recipe.map.RecipeBuilder;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.util.Utils;

import java.util.Arrays;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.*;
import static org.gtreimagined.gtlib.Ref.L;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class ForestryRegistrar extends GTMod {

    public static boolean EASY_COMB_RECIPES = false;


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
        addSpecialCent("lignite_comb", comb, new int[]{9000}, GEM.get(Lignite, 1));
        addProcessMain("lignite_comb", comb, Lignite);
        comb = new ItemStack(GTCombs.COAL_COMB);
        addSpecialCent("coal_comb", comb, new int[]{4000}, GEM.get(Coal, 1));
        addProcessMain("coal_comb", comb, Coal);
        comb = new ItemStack(GTCombs.RESIN_COMB);
        addSpecialCent("resin_comb", comb, new int[]{7000}, new ItemStack(GTCoreItems.StickyResin));
        comb = new ItemStack(GTCombs.OIL_COMB);
        //addSpecialCent(comb, new int[]{70}, Data.DropOil.get(2));
        addProcessMain("oil_comb", comb, OilShale);

        //Gem Line
        //TODO duplicate recipes
        comb = new ItemStack(GTCombs.STONE_COMB);
        addSpecialCent("stone_comb", comb, new int[]{7000, 2000, 2000}, DUST.get(Stone, 1), DUST.get(Salt, 1), DUST.get(Sylvite, 1));
        //addProcessMain(comb, Soapstone);
        addProcess(comb, Talc);
        addProcess(comb, Apatite);
        addProcess(comb, Phosphate);
        addProcess(comb, Phosphor);
        if (GTAPI.isModLoaded(Ref.MOD_AE)){
            comb = new ItemStack(GTCombs.CERTUS_COMB);
            addProcessMain("certus_comb", comb, CertusQuartz);
            addProcess(comb, Quartzite);
            addProcess(comb, Barite);
        }
        //TODO duplicate recipes
        comb = new ItemStack(GTCombs.REDSTONE_COMB);
        addProcessMain("redstone_comb", comb, Redstone);
        addProcess(comb, Cinnabar);
        comb = new ItemStack(GTCombs.LAPIS_COMB);
        addProcessMain("lapis_comb", comb, Lapis);
        //addProcess(comb, Sodalite);
        //addProcess(comb, Lazurite);
        addProcess(comb, Calcite);
        comb = new ItemStack(GTCombs.RUBY_COMB);
        addProcessMain("ruby_comb", comb, Ruby);
        addProcess(comb, Redstone);
        comb = new ItemStack(GTCombs.SAPPHIRE_COMB);
        addProcessMain("sapphire_comb", comb, Sapphire);
        //addProcess(comb, GreenSapphire);
        addProcess(comb, Almandine);
        addProcess(comb, Pyrope);
        comb = new ItemStack(GTCombs.DIAMOND_COMB);
        addProcessMain("diamond_comb", comb, Diamond);
        addProcess(comb, Graphite);
        comb = new ItemStack(GTCombs.OLIVINE_COMB);
        addProcessMain("olivine_comb", comb, Olivine);
        addProcess(comb, Bentonite);
        addProcess(comb, Magnesite);
        addProcess(comb, Glauconite);
        comb = new ItemStack(GTCombs.EMERALD_COMB);
        addProcessMain("emerald_comb", comb, Emerald);
        addProcess(comb, Beryllium);
        addProcess(comb, Thorium);


        //Metals Line
        comb = new ItemStack(GTCombs.SLAG_COMB);
        addSpecialCent("slag_comb", comb, new int[]{5000, 2000, 2000}, DUST.get(Stone, 1), DUST.get(BlackGranite, 1), DUST.get(RedGranite, 1));
        addProcessMain("slag_comb", comb, Salt);
        addProcess(comb, Sylvite);
        //addProcess(comb, Lepidolite);
        addProcess(comb, Spodumene);
        //addProcess(comb, Monazite);
        comb = new ItemStack(GTCombs.COPPER_COMB);
        addSpecialCent("copper_comb", comb, new int[]{7000}, TINY_DUST.get(Copper, 1));
        addProcessMain("copper_comb", comb, Copper);
        addProcess(comb, Tetrahedrite);
        addProcess(comb, Chalcopyrite);
        addProcess(comb, Malachite);
        addProcess(comb, Pyrite);
        addProcess(comb, Stibnite);
        comb = new ItemStack(GTCombs.TIN_COMB);
        addSpecialCent("tin_comb", comb, new int[]{60}, TINY_DUST.get(Tin, 1));
        addProcessMain("tin_comb", comb, Tin);
        addProcess(comb, Cassiterite);
        //TODO has ironcomb recipes?
        comb = new ItemStack(GTCombs.LEAD_COMB);
        addSpecialCent("lead_comb", comb, new int[]{4500}, TINY_DUST.get(Lead, 1));
        addProcessMain("lead_comb", comb, Lead);
        addProcess(comb, Galena);
        comb = new ItemStack(GTCombs.IRON_COMB);
//        addProcess(comb, Iron);
        addProcessMain("iron_comb", comb, Magnetite);
        addProcess(comb, BrownLimonite);
        addProcess(comb, YellowLimonite);
        addProcess(comb, VanadiumMagnetite);
        addProcess(comb, Hematite);
        addProcess(comb, Pyrite);
        //TODO GC Compat if (ProcessingModSupport.aEnableGCMarsMats) addProcess(comb, MeteoricIron);
        comb = new ItemStack(GTCombs.STEEL_COMB);
//        addProcess(comb, Iron, Steel);
        addProcess(comb, Magnetite, Steel);
        addProcess(comb, BrownLimonite, Steel);
        addProcess(comb, YellowLimonite, Steel);
//        addProcess(comb, VanadiumMagnetite, VanadiumSteel);
        addProcess(comb, Hematite, Steel);
        addProcess(comb, Pyrite, Steel);
        //TODO GC Compat if (ProcessingModSupport.aEnableGCMarsMats) addProcess(comb, MeteoricIron, MeteoricSteel);
        addProcessMain("steel_comb", comb, Molybdenite);
        addProcess(comb, Molybdenum);
        comb = new ItemStack(GTCombs.NICKEL_COMB);
        addProcessMain("nickel_comb", comb, Nickel);
        addProcess(comb, Garnierite);
        addProcess(comb, Pentlandite);
        addProcess(comb, Cobaltite);
        addProcess(comb, Wulfenite);
        //addProcess(comb, Powellite);
        comb = new ItemStack(GTCombs.ZINC_COMB);
        addProcessMain("zinc_comb", comb, Zinc);
        addProcess(comb, Sphalerite);
        addProcess(comb, Sulfur);
        comb = new ItemStack(GTCombs.SILVER_COMB);
        addSpecialCent("silver_comb", comb, new int[]{3000}, TINY_DUST.get(Silver, 1));
        addProcessMain("silver_comb", comb, Silver);
        addProcess(comb, Galena);
        comb = new ItemStack(GTCombs.GOLD_COMB);
        addProcessMain("gold_comb", comb, Gold);
        addProcess(comb, Magnetite, Gold);

        //Rare Metals Line
        comb = new ItemStack(GTCombs.ALUMINIUM_COMB);
        addProcessMain("aluminium_comb", comb, 6000, Alumina);
        addProcess(comb, Bauxite);
        comb = new ItemStack(GTCombs.MAGANESE_COMB);
        addProcessMain("manganese_comb", comb, 3000, Manganese);
        addProcess(comb, Grossular);
        addProcess(comb, Spessartine);
        addProcess(comb, Pyrolusite);
        addProcess(comb, Tantalite);
        comb = new ItemStack(GTCombs.TITANIUM_COMB);
//        addProcessMain(comb, Titanium);
        addProcessMain("titanium_comb", comb, Ilmenite);
        addProcess(comb, Bauxite);
        comb = new ItemStack(GTCombs.CHROME_COMB);
        addProcessMain("chromium_comb", comb, 5000, Chromite);
        addProcess(comb, Ruby);
        //TODO ? addProcess(comb, Chromite, 50);
        addProcess(comb, Redstone);
        addProcess(comb, Neodymium);
        addProcess(comb, Bastnasite);
        comb = new ItemStack(GTCombs.TUNGSTEN_COMB);
        addProcessMain("tungsten_comb", comb, Tungstate);
        addProcess(comb, Scheelite);
        addProcess(comb, Lithium);
        comb = new ItemStack(GTCombs.PLATINUM_COMB);
        addProcessMain("platinum_comb", comb, 4000, Platinum);
        addProcess(comb, 4000, Sheldonite);
        addProcess(comb, 4000, Palladium);
        comb = new ItemStack(GTCombs.IRIDIUM_COMB);
        addProcessMain("iridium_comb", comb, 2000, Iridium);
        addProcess(comb, 2000, Osmium);

        //Radioactive Line
        comb = new ItemStack(GTCombs.URANIUM_COMB);
        addProcessMain("uranium_comb", comb, 5000, Uraninite);
        addProcess(comb, 5000, Pitchblende);
        addProcess(comb, 5000, Uranium235);
        comb = new ItemStack(GTCombs.PLUTONIUM_COMB);
        addProcessMain("plutonium_comb", comb, 1000, Plutonium);
        addProcess(comb, 5, Uranium235, Plutonium);
        comb = new ItemStack(GTCombs.NAQUADAH_COMB);
        addProcessMain("naquadah_comb", comb, 10, Naquadah);
        addProcess(comb, 10, EnrichedNaquadah);
        //addProcess(comb, 10, Naquadria);

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
            MaterialTypeItem<?> crushed =materials[0].has(CRUSHED_ORE) ? CRUSHED_ORE : DUST;
            MaterialTypeItem<?> purified =materials[0].has(PURIFIED_ORE) ? PURIFIED_ORE : DUST;
            RecipeBuilder rb = CHEMICAL_REACTOR.RB().ii(RecipeIngredient.of(9, stack), crushed.getMaterialIngredient(materials[0], 1)).fi(Water.getLiquid(1000)).io(purified.get(materials.length == 2 ? materials[1] : materials[0], 4));
            if (!materials[0].getByProducts().isEmpty() && materials[0].getByProducts().get(0).has(LIQUID)) {
                rb.fo(materials[0].getByProducts().get(0).getLiquid(144));
            }
            rb.add(id, 96, 24);
            AUTOCLAVE.RB().ii(Ingredient.of(Utils.ca(16, stack))).fi(UUMatter.getLiquid((int)Math.max(1, ((materials[0].getMass()+9)/10)))).io(crushed.get(materials[0])).add(id, materials[0].getMass() * 128, 384);
        } else {
            CENTRIFUGE.RB().ii(Ingredient.of(stack)).io(TINY_DUST.get(materials[0]), getFRItem("beeswax")).outputChances(chance, 30).add(id, 128, 5);
            //TODO RecipeManagers.centrifugeManager.addRecipe(40, stack, ImmutableMap.of(materials[0].getDustTiny(1), /* TODO chance will be wrong */chance * 0.01f, FR_WAX, 0.3f));
        }
    }

    public static void addSpecialCent(String id, ItemStack stack, int[] chances, ItemStack... outputs) {
        int[] chancesCopy = Arrays.copyOf(chances, chances.length + 1);
        chancesCopy[chances.length] = 3000;
        ItemStack[] outputsCopy = Arrays.copyOf(outputs, outputs.length + 1);
        outputsCopy[outputs.length] = new ItemStack(getFRItem("beeswax"));
        CENTRIFUGE.RB().ii(Ingredient.of(stack)).io(outputsCopy).outputChances(chancesCopy).add(id, 128, 5);
        //TODO RecipeManagers.centrifugeManager.addRecipe(40, stack, ImmutableMap.of(aOutput, chance * 0.01f, ItemList.FR_Wax.get(1, new Object[0]), 0.3f,aOutput2,chance2 * 0.01f,aOutput3,chance3*0.01f));
    }

    public static void addProcess(ItemStack stack, Material... materials) {
        addProcess(stack, 100, materials);
    }

    public static void addProcess(ItemStack stack, int chance, Material... materials) {
        return;
//        if (!EASY_COMB_RECIPES) {
//            if (materials.length == 0) return;
////            FluidStack output =  ? materials[0].getByProducts().get(0).getLiquid(144) : new FluidStack[0];
//            RB.get(Machines.CHEMICAL_REACTOR).ii(Utils.ca(9, stack), materials[0].getCrushed(1)).fi(Water.getLiquid(1000)).io(materials.length == 2 ? materials[1].getCrushedPurified(4) : materials[0].getCrushedPurified(4));
//            if (!materials[0].getByProducts().isEmpty() && materials[0].getByProducts().get(0).has(LIQUID)) {
////                RB.fo(materials[0].getByProducts().get(0).getLiquid(144)).add(96, 24);
//            }
//            RB.add(96, 24);
////            RB.get(Machines.AUTOCLAVE).ii(Utils.ca(16, stack)).fi(UUMatter.getLiquid(Math.max(1, ((materials[0].getMass()+9)/10)))).io(materials[0].getCrushedPurified(1)).add(materials[0].getMass() * 128, 384);
//        } else {
//            RB.get(Machines.CENTRIFUGE).ii(stack).io(materials[0].getDustTiny(1), FR_WAX).chances(chance, 30).add(128, 5);
//            //TODO RecipeManagers.centrifugeManager.addRecipe(40, stack, ImmutableMap.of(materials[0].getDustTiny(1), /* TODO chance will be wrong */chance * 0.01f, FR_WAX, 0.3f));
//        }
    }

    private static Item getFRItem(String id){
        return RegistryUtils.getItemFromID(Ref.MOD_FR, id);
    }

}
