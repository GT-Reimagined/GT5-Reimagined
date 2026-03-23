package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gt5r.GT5RConfig;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.recipe.map.RecipeBuilder;
import org.gtreimagined.gtlib.util.RegistryUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.items.ItemDepletedRod;
import org.gtreimagined.gt5r.items.ItemEnrichedRod;
import org.gtreimagined.gtcore.data.GTCoreFluids;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.data.GTCoreMaterials;
import org.gtreimagined.gtcore.data.GTCoreTags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient.of;
import static net.minecraft.world.item.Items.*;
import static org.gtreimagined.gt5r.data.GT5RMaterialTags.*;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.CENTRIFUGE;

public class CentrifugeLoader {
    public static void init() {
        IMPURE_DUST.all().forEach(dust -> {
            Material aOreByProduct = !dust.getByProducts().isEmpty() ? dust.getByProducts().get(0) : MaterialTags.MACERATE_INTO.getMapping(dust);
            if (!aOreByProduct.has(DUST)) return;
            CENTRIFUGE.RB().ii(of(IMPURE_DUST.get(dust),1)).io(new ItemStack(DUST.get(dust), 1), TINY_DUST.get(aOreByProduct, 1)).add("dust_impure_" + dust.getId(), 400, 2);
        });
        PURE_DUST.all().forEach(dust -> {
            Material aOreByProduct = dust.getByProducts().size() > 1 ? dust.getByProducts().get(1) : !dust.getByProducts().isEmpty() ? dust.getByProducts().get(0) : MaterialTags.MACERATE_INTO.getMapping(dust);
            if (!aOreByProduct.has(DUST)) return;
            CENTRIFUGE.RB().ii(of(PURE_DUST.get(dust),1)).io(new ItemStack(DUST.get(dust), 1), TINY_DUST.get(aOreByProduct, 1)).add("dust_pure_" + dust.getId(),dust.getMass(), 2);
        });
        Map<Material, Integer> centMap = new HashMap<>();
        initCentLists(centMap);
        centMap.forEach((t, euPerTick) -> {
            if (!t.has(DUST) && !t.has(LIQUID) && !t.has(GAS)) return;
            FluidStack[] fluids = t.getProcessInto().stream().filter(mat -> ((mat.m.has(GAS) || mat.m.has(LIQUID)) && !mat.m.has(DUST))).map(mat -> mat.m.has(GAS) ? mat.m.getGas(mat.s*1000) : mat.m.getLiquid(mat.s*1000)).toArray(FluidStack[]::new);
            if (fluids.length > 6) return;
            for (FluidStack fluid : fluids) {
                if (fluid.isEmpty())
                    return;
            }
            ItemStack[] items = t.getProcessInto().stream().filter(mat -> mat.m.has(DUST)).map(mat -> DUST.get(mat.m, mat.s)).toArray(ItemStack[]::new);
            int inputAmount = MaterialTags.PROCESS_INTO.get(t).getRight() > 0 ? MaterialTags.PROCESS_INTO.get(t).getRight() : t.getProcessInto().stream().mapToInt(mat -> mat.s).sum();
            RecipeBuilder b = CENTRIFUGE.RB();
            if (t.has(DUST)){
                b.ii(DUST.getMaterialIngredient(t, inputAmount));
            } else {
                b.fi(t.has(LIQUID) ? t.getLiquid(inputAmount * 1000) : t.getGas(inputAmount * 1000));
            }
            b.io(items).fo(fluids).add("dust_" + t.getId(),t.getMass()*4, euPerTick);
        });
        //some stone dust recipe from gtnh without metal mixture
        //CENTRIFUGING.RB().ii(of(DUST.get(Stone, 32))).io(DUST.get(Quartz, 9), DUST.get(PotassiumFeldspar, 9), DUST.get(Marble, 8), DUST.get(Biotite, 4),
        //        DUST.get(Sodalite, 4)).add("stone_dust",7680, 30);

        CENTRIFUGE.RB().fi(Lava.getLiquid(100)).io(NUGGET.get(Copper), NUGGET.get(Tin), NUGGET.get(Gold), NUGGET.get(Silver), NUGGET.get(Tantalum), SMALL_DUST.get(Tungstate)).outputChances(.2, .1, .025, .025, .025, .025).add("lava", 80, 80);
        CENTRIFUGE.RB().fi(new FluidStack(GTCoreFluids.PAHOEHOE_LAVA.getFluid(), 100)).io(NUGGET.get(Copper), NUGGET.get(Tin), NUGGET.get(Gold), NUGGET.get(Silver), NUGGET.get(Tantalum), SMALL_DUST.get(Tungstate)).outputChances(.2, .1, .025, .025, .025, .025).add("pahoehoe_lava", 40, 80);
        CENTRIFUGE.RB().ii(of(GLOWSTONE_DUST, 10)).io(DUST.get(Gold, 5), DUST.get(Redstone, 5)).fo(Helium.getGas(1000)).add("glowstone_to_helium", 2920, 16);
        CENTRIFUGE.RB().ii(of(MAGMA_CREAM, 1)).io(BLAZE_POWDER, SLIME_BALL).add("magma_cream", 500, 5);
        CENTRIFUGE.RB().ii(SOUL_SAND).io(SMALL_DUST.get(Saltpeter), TINY_DUST.get(Coal), SAND).outputChances(.8, .2, 1).fo(Oil.getLiquid(40)).add("oil_from_soulsand", 200, 80);
        CENTRIFUGE.RB().ii(SOUL_SOIL).io(TINY_DUST.get(Coal), DIRT).outputChances( .2, 1).fo(Oil.getLiquid(40)).add("oil_from_soulsoil", 200, 80);
        //CENTRIFUGE.RB().ii(DUST.getMaterialIngredient(RareEarth, 1)).io(DUST_SMALL.get(Neodymium), DUST_SMALL.get(Yttrium), DUST_SMALL.get(Lanthanum), DUST_SMALL.get(Cerium), DUST_SMALL.get(Cadmium), DUST_SMALL.get(Caesium)).outputChances(0.25, 0.25, 0.25, 0.25, 0.25, 0.25).add("rare_earth", 64, 20);
        CENTRIFUGE.RB().ii(DUST.getMaterialIngredient(RareEarth, 1)).io(SMALL_DUST.get(Neodymium), SMALL_DUST.get(Yttrium), SMALL_DUST.get(Cerium), SMALL_DUST.get(Cadmium), SMALL_DUST.get(Caesium)).outputChances(0.25, 0.25, 0.25, 0.25, 0.25).add("rare_earth", 64, 20);
        CENTRIFUGE.RB().fi(RefineryGas.getGas(800)).fo(LPG.getLiquid(400)).add("refinery_gas", 20, 5);
        CENTRIFUGE.RB().ii(DUST.getMaterialIngredient(PlatinumGroupSludge,1)).io(TINY_DUST.get(Platinum), TINY_DUST.get(Palladium), TINY_DUST.get(Iridium), TINY_DUST.get(Osmium)).outputChances(1, .8, .6, .6).add("platinum_group_sludge", 900, 30);
        CENTRIFUGE.RB().fi(UraniumHexafluoride.getGas(1400)).fo(Uranium238Hexafluoride.getGas(1000), Uranium235Hexafluoride.getGas(400)).add("uranium_hexafluoride", 24, 512);
        CENTRIFUGE.RB().fi(Water.getLiquid(100000)).fo(SemiheavyWater.getLiquid(100), HeavyWater.getLiquid(10), TritiatedWater.getLiquid(1)).add("heavy_water", 64, 64);
        CENTRIFUGE.RB().fi(SemiheavyWater.getLiquid(500)).fo(HeavyWater.getLiquid(50), TritiatedWater.getLiquid(5)).add("heavy_water_2", 32, 64);
        CENTRIFUGE.RB().ii(REFINED_ORE.getMaterialIngredient(Cobalt, 1)).io(DUST.get(Cobalt, 1), TINY_DUST.get(Cobalt60, 2)).add("cobalt_60", 2304, 512);
        CENTRIFUGE.RB().ii(DUST.getMaterialIngredient(Endstone, 1)).io(DUST.get(Sand), TINY_DUST.get(Platinum), TINY_DUST.get(TungstenTrioxide)).outputChances(.8, .01, .03)
                .fo(Helium.getGas(120)).add("endstone_dust", 320, 20);
        CENTRIFUGE.RB().ii(DUST.getMaterialIngredient(Netherrack, 1)).io(DUST.get(Stone, 1), TINY_DUST.get(Sulfur, 2), TINY_DUST.get(Redstone, 1), TINY_DUST.get(Coal, 1), TINY_DUST.get(Gold, 1)).outputChances(.8, .05, .05, .05, .01).add("netherrack_dust", 160, 20);
        //Cake Centrifuging
        /*CENTRIFUGING.RB().ii(of(DUST.get(ThoriumCake, 5))).io(DUST.get(ThoriumDioxide, 1), DUST.get(TrithoriumOctoxide, 4)).add("thorium_cake_centrifuging",400, 500);
        CENTRIFUGING.RB().ii(of(DUST.get(UraniumCake, 5))).io(DUST.get(UraniumDioxide, 1), DUST.get(TriuraniumOctoxide, 4)).add("uranium_cake_centrifuging",400, 500);*/
        CENTRIFUGE.RB().ii(of(GTCoreTags.RUBBER_LOGS)).io(new ItemStack(GTCoreItems.StickyResin), new ItemStack(GTCoreItems.Plantball), DUST.get(Carbon, 1), DUST.get(Wood, 1)).fo(Methane.getGas(60)).outputChances(0.5, 0.375, 0.25, 0.25).add("rubber_logs", 200, 20);

        CENTRIFUGE.RB().ii(of(GTCoreItems.StickyResin, 1)).io(DUST.get(RawRubber, 3), new ItemStack(GTCoreItems.Plantball)).fo(Glue.getLiquid(100)).outputChances(1.0, 0.1).add("sticky_resin", 300, 5);
        CENTRIFUGE.RB().ii(HONEYCOMB).io(DUST.get(GTCoreMaterials.Beeswax)).fo(Honey.getLiquid(100)).add("honeycomb", 128, 5);
        //Methane
        addMethaneRecipe(MELON, 9, 72);
        addMethaneRecipe(BREAD, 9, 72);
        addMethaneRecipe(COOKIE, 9, 72);
        addMethaneRecipe(BROWN_MUSHROOM, 18, 144);
        addMethaneRecipe(RED_MUSHROOM, 18, 144);
        addMethaneRecipe(APPLE, 18, 144);
        addMethaneRecipe(NETHER_WART, 18, 144);
        addMethaneRecipe(SPIDER_EYE, 18, 144);
        addMethaneRecipe(BAKED_POTATO, 24, 192);
        addMethaneRecipe(PUMPKIN, 36, 288);
        addMethaneRecipe(COOKED_BEEF, 36, 288);
        addMethaneRecipe(POTATO, 36, 288);
        addMethaneRecipe(COOKED_PORKCHOP, 36, 288);
        addMethaneRecipe(ROTTEN_FLESH, 36, 288);
        addMethaneRecipe(CARROT, 36, 288);
        addMethaneRecipe(BEETROOT, 36, 288);
        addMethaneRecipe(COOKED_SALMON, 36, 288);
        addMethaneRecipe(COOKED_COD, 36, 288);
        addMethaneRecipe(COOKED_CHICKEN, 36, 288);
        addMethaneRecipe(COOKED_MUTTON, 36, 288);
        addMethaneRecipe(COOKED_RABBIT, 36, 288);
        addMethaneRecipe(MUSHROOM_STEW, 36, 288, new ItemStack(BOWL));
        addMethaneRecipe(CHORUS_FRUIT, 36, 288);
        addMethaneRecipe(BEEF, 48, 384);
        addMethaneRecipe(CHICKEN, 48, 384);
        addMethaneRecipe(PORKCHOP, 48, 384);
        addMethaneRecipe(MUTTON, 48, 384);
        addMethaneRecipe(RABBIT, 48, 384);
        addMethaneRecipe(COD, 48, 384);
        addMethaneRecipe(SALMON, 48, 384);
        addMethaneRecipe(PUFFERFISH, 48, 384);
        addMethaneRecipe(TROPICAL_FISH, 48, 384);
        addMethaneRecipe(RED_MUSHROOM_BLOCK, 48, 384);
        addMethaneRecipe(BROWN_MUSHROOM_BLOCK, 48, 384);
        addMethaneRecipe(POISONOUS_POTATO, 48, 384);
        addMethaneRecipe(CAKE, 72, 576);
        //addMethaneRecipe(TerraWart, 36, 288);
        addMethaneRecipe(GOLDEN_APPLE, 576, 9216, INGOT.get(Gold, 7));
        addMethaneRecipe(ENCHANTED_GOLDEN_APPLE, 4608, 9216, INGOT.get(Gold, 64));
        addMethaneRecipe(GOLDEN_CARROT, 576, 9216, new ItemStack(GOLD_NUGGET, 6));
        addMethaneRecipe(GLISTERING_MELON_SLICE, 576, 9216, new ItemStack(GOLD_NUGGET, 6));

        addDepletedRodRecipe(GT5RItems.DepletedThorium232Rod, Uranium);
        addDepletedRodRecipe(GT5RItems.DepletedUranium238Rod, Uranium235);
        addDepletedRodRecipe(GT5RItems.DepletedUranium235Rod, Plutonium);
        addDepletedRodRecipe(GT5RItems.DepletedUranium233Rod, Plutonium243);
        addDepletedRodRecipe(GT5RItems.DepletedPlutonium244Rod, Plutonium241);
        addDepletedRodRecipe(GT5RItems.DepletedPlutonium241Rod, Plutonium243);
        addDepletedRodRecipe(GT5RItems.DepletedPlutonium243Rod, Americium);
        addDepletedRodRecipe(GT5RItems.DepletedPlutonium239Rod, Americium241);
        addDepletedRodRecipe(GT5RItems.DepletedAmericium245Rod, Americium241);
        addDepletedRodRecipe(GT5RItems.DepletedAmericium241Rod, EnrichedNaquadah);
        addDepletedRodRecipe(GT5RItems.DepletedCobalt60Rod, Thorium);
        addDepletedRodRecipe(GT5RItems.DepletedEnrichedNaquadahRod, Naquadria);
        addDepletedRodRecipe(GT5RItems.DepletedNaquadriaRod, Cobalt60);
        addEnrichedRodRecipe(GT5RItems.Uranium233EnrichedRod, Thorium);
        addEnrichedRodRecipe(GT5RItems.Plutonium239EnrichedRod, Uranium);
        addEnrichedRodRecipe(GT5RItems.EnrichedNaquadahEnrichedRod, Naquadah);
    }

    private static void addDepletedRodRecipe(ItemDepletedRod rod, Material secondary){
        CENTRIFUGE.RB().ii(rod).io(DUST.get(Zirconium), TINY_DUST.get(rod.getMaterial()), TINY_DUST.get(secondary)).outputChances(1.0, 1.0, .75).add(rod.getId(), 256, 64);
    }

    private static void addEnrichedRodRecipe(ItemEnrichedRod rod, Material secondary){
        CENTRIFUGE.RB().ii(rod).io(DUST.get(Zirconium, 1), TINY_DUST.get(rod.getMaterial(), 4), TINY_DUST.get(secondary, 1)).outputChances(1.0, 1.0, .5).add(rod.getId(), 256, 64);
    }

    private static void addMethaneRecipe(Item input, int methane, int ticks){
        CENTRIFUGE.RB().ii(of(input, 1)).fo(Methane.getGas(methane)).add(RegistryUtils.getIdFromItem(input).getPath() + "_into_methane", ticks, 5);
    }

    private static void addMethaneRecipe(Item input, int methane, int ticks, ItemStack extra){
        CENTRIFUGE.RB().ii(of(input, 1)).io(extra).fo(Methane.getGas(methane)).add(RegistryUtils.getIdFromItem(input).getPath() + "_into_methane", ticks, 5);
    }

    private static void initCentLists(Map<Material, Integer> map){
        List<Material> cent5 = new ArrayList<>(List.of(/*Chrysolite*/ Flint, /*Niter*/ Materials.Glass, /*Perlite*/ Materials.WroughtIron, Materials.DarkAsh, Materials.AnnealedCopper,
                Materials.Cinnabar, DamascusSteel));
        List<Material> cent10 = new ArrayList<>(List.of(Materials.Magnalium, Materials.VanadiumMagnetite, Materials.BrownLimonite, Materials.YellowLimonite, Materials.BlackGranite, Materials.Cupronickel, Materials.NiobiumTitanium, Materials.BorosilicateGlass,
                Materials.GalliumArsenide, Materials.Marble, Materials.Limestone, Materials.Invar, Materials.TinAlloy, Materials.TungstenCarbide, TitaniumGold, TritaniumAlloy, Trinitanium, EnderEye, Materials.Powellite, Materials.VanadiumGallium, Blaze,
                Materials.TungstenSteel, Materials.Brass, Materials.Nichrome, Materials.Electrum, Materials.Bronze, Materials.Wulfenite, Materials.RedAlloy, Materials.SterlingSilver, Materials.RoseGold, Materials.BatteryAlloy, Materials.SolderingAlloy, Materials.TricalciumPhosphate));
        List<Material> cent15 = new ArrayList<>(List.of(Materials.Kanthal, Materials.IndiumGalliumPhosphide, Materials.BlackSteel, Materials.RedGarnet, Materials.YellowGarnet, Materials.BismuthBronze, Materials.BlackBronze, Materials.VanadiumSteel, Materials.CdInAGAlloy, Materials.CobaltBrass,
                Materials.Pitchblende, Redstone, Materials.HSSS));
        List<Material> cent20 = new ArrayList<>(List.of(Lapis, Materials.RedSteel, Materials.BlueSteel, Basalt, Materials.HSSE, Materials.Sheldonite, Materials.HSSG, Materials.Komatiite));
        if (!GT5RConfig.FORCE_ROASTER.get()){
            cent20.add(Tetrahedrite);
            cent10.add(Stibnite);
        }
        cent5.forEach(m -> map.put(m, 5));
        cent10.forEach(m -> map.put(m, 10));
        cent15.forEach(m -> map.put(m, 15));
        cent20.forEach(m -> map.put(m, 20));    }
}

