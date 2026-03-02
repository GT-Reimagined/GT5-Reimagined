package org.gtreimagined.gt5r.integration;

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
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.material.Material;
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
        /*
        //TODO move comb stuff to dedicated class
        ItemStack comb;

        //Organic Line
        comb = Data.CombLignite.get(1);
        addSpecialCent(comb, new int[]{90}, Lignite.getGem(1));
        addProcessMain(comb, Lignite);
        comb = Data.CombCoal.get(1);
        addSpecialCent(comb, new int[]{40}, Coal.getGem(1));
        addProcessMain(comb, Coal);
        comb = Data.CombResin.get(1);
        addSpecialCent(comb, new int[]{70}, Data.StickyResin.get(1));
        comb = Data.CombOil.get(1);
        addSpecialCent(comb, new int[]{70}, Data.DropOil.get(2));
        addProcessMain(comb, Oilsands);

        //Gem Line
        //TODO duplicate recipes
        comb = Data.CombStone.get(1);
        addSpecialCent(comb, new int[]{70, 20, 20}, Stone.getDust(1), Salt.getDust(1), RockSalt.getDust(1));
        //addProcessMain(comb, Soapstone);
        addProcess(comb, Talc);
        addProcess(comb, ForestryRegistrar.Apatite);
        addProcess(comb, Phosphate);
        addProcess(comb, Phosphorus);
        comb = Data.CombCertus.get(1);
        //addProcessMain(comb, CertusQuartz);
        addProcess(comb, Quartzite);
        addProcess(comb, Barite);
        //TODO duplicate recipes
        comb = Data.CombRedstone.get(1);
        addProcessMain(comb, Redstone);
        addProcess(comb, Cinnabar);
        comb = Data.CombLapis.get(1);
        addProcessMain(comb, Lapis);
        //addProcess(comb, Sodalite);
        //addProcess(comb, Lazurite);
        addProcess(comb, Calcite);
        comb = Data.CombRuby.get(1);
        addProcessMain(comb, Ruby);
        addProcess(comb, Redstone);
        comb = Data.CombSapphire.get(1);
        addProcessMain(comb, BlueSapphire);
        //addProcess(comb, GreenSapphire);
        addProcess(comb, Almandine);
        addProcess(comb, Pyrope);
        comb = Data.CombDiamond.get(1);
        addProcessMain(comb, Diamond);
        addProcess(comb, Graphite);
        comb = Data.CombOlivine.get(1);
        addProcessMain(comb, Olivine);
        addProcess(comb, Bentonite);
        addProcess(comb, Magnesite);
        addProcess(comb, Glauconite);
        comb = Data.CombEmerald.get(1);
        addProcessMain(comb, Emerald);
        addProcess(comb, Beryllium);
        addProcess(comb, Thorium);

        //Metals Line
        comb = Data.CombSlag.get(1);
        addSpecialCent(comb, new int[]{50, 20, 20}, Stone.getDust(1), GraniteBlack.getDust(1), GraniteRed.getDust(1));
        addProcessMain(comb, Salt);
        addProcess(comb, RockSalt);
        //addProcess(comb, Lepidolite);
        addProcess(comb, Spodumene);
        //addProcess(comb, Monazite);
        comb = Data.CombCopper.get(1);
        addSpecialCent(comb, new int[]{70}, Copper.getDustTiny(1));
        addProcessMain(comb, Copper);
        addProcess(comb, Tetrahedrite);
        addProcess(comb, Chalcopyrite);
        addProcess(comb, Malachite);
        addProcess(comb, Pyrite);
        addProcess(comb, Stibnite);
        comb = Data.CombTin.get(1);
        addSpecialCent(comb, new int[]{60}, Tin.getDustTiny(1));
        addProcessMain(comb, Tin);
        addProcess(comb, Cassiterite);
        //TODO has ironcomb recipes?
        comb = Data.CombLead.get(1);
        addSpecialCent(comb, new int[]{45}, Lead.getDustTiny(1));
        addProcessMain(comb, Lead);
        addProcess(comb, Galena);
        comb = Data.CombIron.get(1);
//        addProcess(comb, Iron);
        addProcessMain(comb, Magnetite);
        addProcess(comb, BrownLimonite);
        addProcess(comb, YellowLimonite);
        addProcess(comb, VanadiumMagnetite);
        addProcess(comb, BandedIron);
        addProcess(comb, Pyrite);
        //TODO GC Compat if (ProcessingModSupport.aEnableGCMarsMats) addProcess(comb, MeteoricIron);
        comb = Data.CombSteel.get(1);
//        addProcess(comb, Iron, Steel);
        addProcess(comb, Magnetite*//*, Steel*//*);
        addProcess(comb, BrownLimonite*//*, Steel*//*);
        addProcess(comb, YellowLimonite*//*, Steel*//*);
//        addProcess(comb, VanadiumMagnetite, VanadiumSteel);
        addProcess(comb, BandedIron*//*, Steel*//*);
        addProcess(comb, Pyrite*//*, Steel*//*);
        //TODO GC Compat if (ProcessingModSupport.aEnableGCMarsMats) addProcess(comb, MeteoricIron, MeteoricSteel);
        addProcessMain(comb, Molybdenite);
        addProcess(comb, Molybdenum);
        comb = Data.CombNickel.get(1);
        addProcessMain(comb, Nickel);
        addProcess(comb, Garnierite);
        addProcess(comb, Pentlandite);
        addProcess(comb, Cobaltite);
        addProcess(comb, Wulfenite);
        //addProcess(comb, Powellite);
        comb = Data.CombZinc.get(1);
        addProcessMain(comb, Zinc);
        addProcess(comb, Sphalerite);
        addProcess(comb, Sulfur);
        comb = Data.CombSilver.get(1);
        addSpecialCent(comb, new int[]{30}, Silver.getDustTiny(1));
        addProcessMain(comb, Silver);
        addProcess(comb, Galena);
        comb = Data.CombGold.get(1);
        addProcessMain(comb, Gold);
        addProcess(comb, Magnetite, Gold);

        //Rare Metals Line
        comb = Data.CombAluminium.get(1);
        addProcessMain(comb, 60, Aluminium);
        addProcess(comb, Bauxite);
        comb = Data.CombManganese.get(1);
        addProcessMain(comb, 30, Manganese);
        addProcess(comb, Grossular);
        addProcess(comb, Spessartine);
        addProcess(comb, Pyrolusite);
        addProcess(comb, Tantalite);
        comb = Data.CombTitanium.get(1);
//        addProcessMain(comb, Titanium);
        addProcessMain(comb, Ilmenite);
        addProcess(comb, Bauxite);
        comb = Data.CombChrome.get(1);
        addProcessMain(comb, 50, Chrome);
        addProcess(comb, Ruby);
        //TODO ? addProcess(comb, Chromite, 50);
        addProcess(comb, Redstone);
        addProcess(comb, Neodymium);
        addProcess(comb, Bastnasite);
        comb = Data.CombTungsten.get(1);
        addProcessMain(comb, Tungstate);
        addProcess(comb, Scheelite);
        addProcess(comb, Lithium);
        comb = Data.CombPlatinum.get(1);
        addProcessMain(comb, 40, Platinum);
        addProcess(comb, 40, Cooperite);
        addProcess(comb, 40, Palladium);
        comb = Data.CombIridium.get(1);
        addProcessMain(comb, 20, Iridium);
        addProcess(comb, 20, Osmium);

        //Radioactive Line
        comb = Data.CombUranium.get(1);
        addProcessMain(comb, 50, Uranium);
        addProcess(comb, 50, Pitchblende);
        addProcess(comb, 50, Uraninite);
        addProcess(comb, 50, Uranium235);
        comb = Data.CombPlutonium.get(1);
        addProcessMain(comb, 10, Plutonium);
        addProcess(comb, 5, Uranium235, Plutonium);
        comb = Data.CombNaquadah.get(1);
        addProcessMain(comb, 10, Naquadah);
        addProcess(comb, 10, NaquadahEnriched);
        //addProcess(comb, 10, Naquadria);
        */

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
//            FluidStack output =  ? materials[0].getByProducts().get(0).getLiquid(144) : new FluidStack[0];
            RecipeBuilder rb = CHEMICAL_REACTOR.RB().ii(RecipeIngredient.of(9, stack), CRUSHED_ORE.getMaterialIngredient(materials[0], 1)).fi(Water.getLiquid(1000)).io(PURIFIED_ORE.get(materials.length == 2 ? materials[1] : materials[0], 4));
            if (!materials[0].getByProducts().isEmpty() && materials[0].getByProducts().get(0).has(LIQUID)) {
                rb.fo(materials[0].getByProducts().get(0).getLiquid(144)).add(id, 96, 24);
            }
            AUTOCLAVE.RB().ii(Ingredient.of(Utils.ca(16, stack))).fi(UUMatter.getLiquid((int)Math.max(1, ((materials[0].getMass()+9)/10)))).io(CRUSHED_ORE.get(materials[0])).add(id, materials[0].getMass() * 128, 384);
        } else {
            CENTRIFUGE.RB().ii(Ingredient.of(stack)).io(TINY_DUST.get(materials[0]), getFRItem("beeswax")).outputChances(chance, 30).add(id, 128, 5);
            //TODO RecipeManagers.centrifugeManager.addRecipe(40, stack, ImmutableMap.of(materials[0].getDustTiny(1), /* TODO chance will be wrong */chance * 0.01f, FR_WAX, 0.3f));
        }
    }

    public static void addSpecialCent(String id, ItemStack stack, int[] chances, ItemStack... outputs) {
        int[] chancesCopy = Arrays.copyOf(chances, chances.length + 1);
        chancesCopy[chances.length] = 30;
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
