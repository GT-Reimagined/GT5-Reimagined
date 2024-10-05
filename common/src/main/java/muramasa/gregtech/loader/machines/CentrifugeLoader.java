package muramasa.gregtech.loader.machines;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import io.github.gregtechintergalactical.gtcore.data.GTCoreFluids;
import io.github.gregtechintergalactical.gtcore.data.GTCoreItems;
import io.github.gregtechintergalactical.gtcore.data.GTCoreMaterials;
import io.github.gregtechintergalactical.gtcore.data.GTCoreTags;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.MaterialTags;
import muramasa.antimatter.recipe.map.RecipeBuilder;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import muramasa.gregtech.data.GT5RItems;
import muramasa.gregtech.items.ItemDepletedRod;
import muramasa.gregtech.items.ItemEnrichedRod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tesseract.TesseractGraphWrappers;

import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.data.AntimatterMaterials.*;
import static muramasa.antimatter.recipe.ingredient.RecipeIngredient.of;
import static muramasa.gregtech.data.GT5RMaterialTags.*;
import static muramasa.gregtech.data.Materials.*;
import static muramasa.gregtech.data.RecipeMaps.CENTRIFUGE;
import static net.minecraft.world.item.Items.*;
import static net.minecraft.world.item.Items.GOLD_NUGGET;

public class CentrifugeLoader {
    public static void init() {
        DUST_IMPURE.all().forEach(dust -> {
            Material aOreByProduct = !dust.getByProducts().isEmpty() ? dust.getByProducts().get(0) : MaterialTags.MACERATE_INTO.getMapping(dust);
            if (!aOreByProduct.has(DUST)) return;
            CENTRIFUGE.RB().ii(of(DUST_IMPURE.get(dust),1)).io(new ItemStack(DUST.get(dust), 1), DUST_TINY.get(aOreByProduct, 1)).add("dust_impure_" + dust.getId(), 400, 2);
        });
        DUST_PURE.all().forEach(dust -> {
            Material aOreByProduct = dust.getByProducts().size() > 1 ? dust.getByProducts().get(1) : !dust.getByProducts().isEmpty() ? dust.getByProducts().get(0) : MaterialTags.MACERATE_INTO.getMapping(dust);
            if (!aOreByProduct.has(DUST)) return;
            CENTRIFUGE.RB().ii(of(DUST_PURE.get(dust),1)).io(new ItemStack(DUST.get(dust), 1), DUST_TINY.get(aOreByProduct, 1)).add("dust_pure_" + dust.getId(),dust.getMass(), 2);
        });
        CENT.all().forEach(t -> {
            if (!t.has(DUST) && !t.has(LIQUID) && !t.has(GAS)) return;
            FluidHolder[] fluids = t.getProcessInto().stream().filter(mat -> ((mat.m.has(GAS) || mat.m.has(LIQUID)) && !mat.m.has(DUST))).map(mat -> mat.m.has(GAS) ? mat.m.getGas(mat.s*1000) : mat.m.getLiquid(mat.s*1000)).toArray(FluidHolder[]::new);
            if (fluids.length > 6) return;
            for (FluidHolder fluid : fluids) {
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
            int euPerTick = t.has(CENT5) ? 5 : t.has(CENT10) ? 10 : t.has(CENT15) ? 15 : t.has(CENT20) ? 20 : 16;
            b.io(items).fo(fluids).add("dust_" + t.getId(),t.getMass()*4, euPerTick);
        });
        //some stone dust recipe from gtnh without metal mixture
        //CENTRIFUGING.RB().ii(of(DUST.get(Stone, 32))).io(DUST.get(Quartz, 9), DUST.get(PotassiumFeldspar, 9), DUST.get(Marble, 8), DUST.get(Biotite, 4),
        //        DUST.get(Sodalite, 4)).add("stone_dust",7680, 30);

        CENTRIFUGE.RB().fi(Lava.getLiquid(100)).io(NUGGET.get(Copper), NUGGET.get(Tin), NUGGET.get(Gold), NUGGET.get(Silver), NUGGET.get(Tantalum), DUST_SMALL.get(Tungstate)).outputChances(.2, .1, .025, .025, .025, .025).add("lava", 80, 80);
        CENTRIFUGE.RB().fi(FluidHooks.newFluidHolder(GTCoreFluids.PAHOEHOE_LAVA.getFluid(), 100 * TesseractGraphWrappers.dropletMultiplier, null)).io(NUGGET.get(Copper), NUGGET.get(Tin), NUGGET.get(Gold), NUGGET.get(Silver), NUGGET.get(Tantalum), DUST_SMALL.get(Tungstate)).outputChances(.2, .1, .025, .025, .025, .025).add("pahoehoe_lava", 40, 80);
        CENTRIFUGE.RB().ii(of(GLOWSTONE_DUST, 10)).io(DUST.get(Gold, 5), DUST.get(Redstone, 5)).fo(Helium.getGas(1000)).add("glowstone_to_helium", 2920, 16);
        CENTRIFUGE.RB().ii(of(MAGMA_CREAM, 1)).io(BLAZE_POWDER, SLIME_BALL).add("magma_cream", 500, 5);
        CENTRIFUGE.RB().ii(SOUL_SAND).io(DUST_SMALL.get(Saltpeter), DUST_TINY.get(Coal), SAND).outputChances(.8, .2, 1).fo(Oil.getLiquid(40)).add("oil_from_soulsand", 200, 80);
        CENTRIFUGE.RB().ii(SOUL_SOIL).io(DUST_TINY.get(Coal), DIRT).outputChances( .2, 1).fo(Oil.getLiquid(40)).add("oil_from_soulsoil", 200, 80);
        //CENTRIFUGE.RB().ii(DUST.getMaterialIngredient(RareEarth, 1)).io(DUST_SMALL.get(Neodymium), DUST_SMALL.get(Yttrium), DUST_SMALL.get(Lanthanum), DUST_SMALL.get(Cerium), DUST_SMALL.get(Cadmium), DUST_SMALL.get(Caesium)).outputChances(0.25, 0.25, 0.25, 0.25, 0.25, 0.25).add("rare_earth", 64, 20);
        CENTRIFUGE.RB().ii(DUST.getMaterialIngredient(RareEarth, 1)).io(DUST_SMALL.get(Neodymium), DUST_SMALL.get(Yttrium), DUST_SMALL.get(Cerium), DUST_SMALL.get(Cadmium), DUST_SMALL.get(Caesium)).outputChances(0.25, 0.25, 0.25, 0.25, 0.25).add("rare_earth", 64, 20);
        CENTRIFUGE.RB().fi(RefineryGas.getGas(800)).fo(LPG.getLiquid(400)).add("refinery_gas", 20, 5);
        CENTRIFUGE.RB().ii(DUST.getMaterialIngredient(PlatinumGroupSludge,1)).io(DUST_TINY.get(Platinum), DUST_TINY.get(Palladium), DUST_TINY.get(Iridium), DUST_TINY.get(Osmium)).outputChances(1, .8, .6, .6).add("platinum_group_sludge", 900, 30);
        CENTRIFUGE.RB().fi(UraniumHexafluoride.getGas(1400)).fo(Uranium238Hexafluoride.getGas(1000), Uranium235Hexafluoride.getGas(400)).add("uranium_hexafluoride", 24, 512);
        CENTRIFUGE.RB().fi(Water.getLiquid(100000)).fo(SemiheavyWater.getLiquid(100), HeavyWater.getLiquid(10), TritiatedWater.getLiquid(1)).add("heavy_water", 64, 64);
        CENTRIFUGE.RB().fi(SemiheavyWater.getLiquid(500)).fo(HeavyWater.getLiquid(50), TritiatedWater.getLiquid(5)).add("heavy_water_2", 32, 64);
        CENTRIFUGE.RB().ii(CRUSHED_REFINED.getMaterialIngredient(Cobalt, 1)).io(DUST.get(Cobalt, 1), DUST_TINY.get(Cobalt60, 2)).add("cobalt_60", 2304, 512);
        CENTRIFUGE.RB().ii(DUST.getMaterialIngredient(Endstone, 1)).io(DUST.get(Sand), DUST_TINY.get(Platinum), DUST_TINY.get(TungstenTrioxide)).outputChances(.8, .01, .03)
                .fo(Helium.getGas(120)).add("endstone_dust", 320, 20);
        CENTRIFUGE.RB().ii(DUST.getMaterialIngredient(Netherrack, 1)).io(DUST.get(Stone, 1), DUST_TINY.get(Sulfur, 2), DUST_TINY.get(Redstone, 1), DUST_TINY.get(Coal, 1), DUST_TINY.get(Gold, 1)).outputChances(.8, .05, .05, .05, .01).add("netherrack_dust", 160, 20);
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
        addMethaneRecipe(GOLDEN_APPLE, 576, 9216, new ItemStack(GOLD_INGOT, 7));
        addMethaneRecipe(ENCHANTED_GOLDEN_APPLE, 4608, 9216, new ItemStack(GOLD_INGOT, 64));
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
        CENTRIFUGE.RB().ii(rod).io(DUST.get(Zirconium), DUST_TINY.get(rod.getMaterial()), DUST_TINY.get(secondary)).outputChances(1.0, 1.0, .75).add(rod.getId(), 256, 64);
    }

    private static void addEnrichedRodRecipe(ItemEnrichedRod rod, Material secondary){
        CENTRIFUGE.RB().ii(rod).io(DUST.get(Zirconium, 1), DUST_TINY.get(rod.getMaterial(), 4), DUST_TINY.get(secondary, 1)).outputChances(1.0, 1.0, .5).add(rod.getId(), 256, 64);
    }

    private static void addMethaneRecipe(Item input, int methane, int ticks){
        CENTRIFUGE.RB().ii(of(input, 1)).fo(Methane.getGas(methane)).add(AntimatterPlatformUtils.getIdFromItem(input).getPath() + "_into_methane", ticks, 5);
    }

    private static void addMethaneRecipe(Item input, int methane, int ticks, ItemStack extra){
        CENTRIFUGE.RB().ii(of(input, 1)).io(extra).fo(Methane.getGas(methane)).add(AntimatterPlatformUtils.getIdFromItem(input).getPath() + "_into_methane", ticks, 5);
    }
}

