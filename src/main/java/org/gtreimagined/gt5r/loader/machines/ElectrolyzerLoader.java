package org.gtreimagined.gt5r.loader.machines;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gt5r.GT5RConfig;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;
import org.gtreimagined.gt5r.data.GT5RTags;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.recipe.map.RecipeBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.gtreimagined.gt5r.data.GT5RMaterialTags.*;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.ELECTROLYZER;
import static org.gtreimagined.gtcore.data.GTCoreItems.SELECTOR_TAG_INGREDIENTS;
import static org.gtreimagined.gtlib.Ref.L;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class ElectrolyzerLoader {
    public static void init() {
        Map<Material, Integer> materials = new HashMap<>();
        initElecLists(materials);
        materials.forEach((m, euPerTick) -> {
            if (!m.has(DUST) && !m.has(LIQUID) && !m.has(GAS)) return;
            FluidStack[] fluids = m.getProcessInto().stream().filter(mat -> ((mat.m.has(GAS) || mat.m.has(GTMaterialTypes.LIQUID)) && !mat.m.has(GTMaterialTypes.DUST))).map(mat -> mat.m.has(GAS) ? mat.m.getGas(mat.s*1000) : mat.m.getLiquid(mat.s*1000)).toArray(FluidStack[]::new);
            for (FluidStack fluid : fluids) {
                if (fluid.isEmpty())
                    return;
            }
            if (fluids.length > 6) return;
            ItemStack[] items = m.getProcessInto().stream().filter(mat -> mat.m.has(GTMaterialTypes.DUST)).map(mat -> GTMaterialTypes.DUST.get(mat.m, mat.s)).toArray(ItemStack[]::new);
            int inputAmount = MaterialTags.PROCESS_INTO.get(m).getRight() > 0 ? MaterialTags.PROCESS_INTO.get(m).getRight() : m.getProcessInto().stream().mapToInt(mat -> mat.s).sum();
            RecipeBuilder b = ELECTROLYZER.RB();
            String prefix = "dust";
            if (m.has(DUST)){
                b.ii(DUST.getMaterialIngredient(m, inputAmount));
            } else {
                prefix = "fluid";
                b.fi(m.getFluidIngredient(inputAmount * 1000));
            }
            if (m.has(ELEC_CIRCUIT)){
                b.ii(SELECTOR_TAG_INGREDIENTS.get(1).get());
            }
            long duration = m.has(ELEC_TICKS) ? ELEC_TICKS.getInt(m) : m.getMass() * 20;
            b.io(items).fo(fluids).add(prefix + "_" + m.getId(),duration, euPerTick);
        });
        ELECTROLYZER.RB().ii(DUST.getMaterialIngredient(SodiumBisulfate, 2), FINE_WIRE.getMaterialIngredient(Platinum, 1).setNoConsume()).io(DUST.get(SodiumPersulfate)).fo(Hydrogen.getGas(1000)).add("sodium_persulfate_creation", 600, 30);
        ELECTROLYZER.RB().ii(RecipeIngredient.of(ItemTags.SAND, 8)).io(DUST.get(Materials.SiliconDioxide)).add("sand_to_silicon_dioxide", 500, 25);
        ELECTROLYZER.RB().ii(RecipeIngredient.of(GT5RTags.DUST_SANDS, 32)).io(DUST.get(Materials.SiliconDioxide)).add("sand_dusts_to_silicon_dioxide", 500, 25);
        ELECTROLYZER.RB().ii(RecipeIngredient.of(Items.BONE_MEAL, 3)).io(DUST.get(Materials.Calcium)).add("bone_meal", 98, 26);
        ELECTROLYZER.RB().ii(DUST.getMaterialIngredient(Bentonite, 33)).io(SMALL_DUST.get(Sodium, 2), DUST.get(Magnesium, 3), DUST.get(Silicon, 6))
                .fo(Hydrogen.getGas(3000), Water.getLiquid(2500), Oxygen.getGas(18000)).add("dust_bentonite", 240, 120);
        if (GT5RConfig.HARDER_ALUMINIUM_PROCESSING.get()) {
            ELECTROLYZER.RB().ii(DUST.getMaterialIngredient(Carbon, 3), DUST.getMaterialIngredient(Alumina, 10))
                    .fi(AluminiumFluoride.getLiquid(L / 36), Cryolite.getLiquid(L / 72)).io(DUST.get(Aluminium, 4))
                    .fo(CarbonDioxide.getGas(9000), Fluorine.getGas(29)).add("alumina_carbon", 2040, 16);
            ELECTROLYZER.RB().ii(DUST.getMaterialIngredient(Charcoal, 3), DUST.getMaterialIngredient(Alumina, 10))
                    .fi(AluminiumFluoride.getLiquid(L / 36), Cryolite.getLiquid(L / 72)).io(DUST.get(Aluminium, 4))
                    .fo(CarbonDioxide.getGas(9000), Fluorine.getGas(29)).add("alumina_charcoal", 2040, 16);
            ELECTROLYZER.RB().ii(DUST.getMaterialIngredient(Coal, 3), DUST.getMaterialIngredient(Alumina, 10))
                    .fi(AluminiumFluoride.getLiquid(L / 36), Cryolite.getLiquid(L / 72)).io(DUST.get(Aluminium, 4))
                    .fo(CarbonDioxide.getGas(9000), Fluorine.getGas(29)).add("alumina_coal", 2040, 16);
            ELECTROLYZER.RB().ii(DUST.getMaterialIngredient(CoalCoke, 3), DUST.getMaterialIngredient(Alumina, 10))
                    .fi(AluminiumFluoride.getLiquid(L / 36), Cryolite.getLiquid(L / 72)).io(DUST.get(Aluminium, 4))
                    .fo(CarbonDioxide.getGas(9000), Fluorine.getGas(29)).add("alumina_coke", 2040, 16);
            ELECTROLYZER.RB().ii(DUST.getMaterialIngredient(Graphite, 3), DUST.getMaterialIngredient(Alumina, 10))
                    .fi(AluminiumFluoride.getLiquid(L / 36), Cryolite.getLiquid(L / 72)).io(DUST.get(Aluminium, 4))
                    .fo(CarbonDioxide.getGas(9000), Fluorine.getGas(29)).add("alumina_graphite", 2040, 16);
        }
        addVitriolRecipe(BlueVitriol, Copper);
        addVitriolRecipe(GreenVitriol, Iron);
        addVitriolRecipe(RedVitriol, Cobalt);
        addVitriolRecipe(PinkVitriol, Magnesium);
        addVitriolRecipe(CyanVitriol, Nickel);
        addVitriolRecipe(WhiteVitriol, Zinc);
        addVitriolRecipe(GrayVitriol, Manganese);
        ELECTROLYZER.RB().fi(Water.getLiquid(900), VitriolOfClay.getLiquid(1700)).ii(SELECTOR_TAG_INGREDIENTS.get(1).get()).io(SMALL_DUST.get(Alumina, 2)).fo(SulfuricAcid.getLiquid(2100)).add("vitriol_of_clay_to_alumina", 19, 64);
        ELECTROLYZER.RB().fi(DistilledWater.getLiquid(900), VitriolOfClay.getLiquid(1700)).ii(SELECTOR_TAG_INGREDIENTS.get(1).get()).io(SMALL_DUST.get(Alumina, 2)).fo(SulfuricAcid.getLiquid(2100)).add("vitriol_of_clay_to_alumina_distilled", 19, 64);
        ELECTROLYZER.RB().fi(Water.getLiquid(6000), ChloroplatinicAcid.getLiquid(9000)).ii(SELECTOR_TAG_INGREDIENTS.get(2).get()).io(DUST.get(Platinum)).fo(HydrochloricAcid.getLiquid(12000), Oxygen.getGas(2000)).add("chloroplatinic_acid", 96, 64);
        ELECTROLYZER.RB().fi(DistilledWater.getLiquid(6000), ChloroplatinicAcid.getLiquid(9000)).ii(SELECTOR_TAG_INGREDIENTS.get(2).get()).io(DUST.get(Platinum)).fo(HydrochloricAcid.getLiquid(12000), Oxygen.getGas(2000)).add("chloroplatinic_acid_distilled", 96, 64);
    }

    private static void addVitriolRecipe(Material vitriol, Material dust){
        ELECTROLYZER.RB().fi(vitriol.getLiquid(6000), Water.getLiquid(3000)).ii(SELECTOR_TAG_INGREDIENTS.get(2).get()).io(DUST.get(dust)).fo(SulfuricAcid.getLiquid(7000), Oxygen.getGas(1000)).add(vitriol.getId() + "_to_" + dust.getId(), 64, 64);
        ELECTROLYZER.RB().fi(vitriol.getLiquid(6000), DistilledWater.getLiquid(3000)).ii(SELECTOR_TAG_INGREDIENTS.get(2).get()).io(DUST.get(dust)).fo(SulfuricAcid.getLiquid(7000), Oxygen.getGas(1000)).add(vitriol.getId() + "_to_" + dust.getId() + "_distilled", 64, 64);
    }

    private static void initElecLists(Map<Material, Integer> elecMap){
        List<Material> elec30 = new ArrayList<>(List.of(Charcoal, Materials.Opal, Coal, Materials.CoalCoke, Materials.Lignite, Materials.SteelMagnetic, Materials.IronMagnetic, Materials.Quicklime, Materials.Quartzite,
                Materials.SiliconDioxide, Materials.Wollastonite, Materials.CobaltOxide, Materials.Garnierite, Materials.CupricOxide, Materials.Sylvite, /*Zincite,*/Materials.Pyrolusite, /*ChromiumDioxide,*/
                Materials.Phosphate, /*NiobiumNitride,*/ Materials.GreenSapphire, Materials.Sapphire, Materials.NeodymiumMagnetic, Materials.Cassiterite,
                Materials.PhosphorousPentoxide, Materials.Hematite, Materials.Massicot, Materials.ArsenicTrioxide, Sugar, Materials.Magnetite, Materials.AntimonyTrioxide,
                Materials.Salt, Materials.SodiumBisulfate, Materials.PotassiumBisulfate, Materials.HydrochloricAcid, Materials.SaltWater, Materials.HydrochloricAcid, Diamond,
                Water, Materials.DistilledWater, Materials.HeavyWater, Materials.SemiheavyWater, Materials.TritiatedWater, Materials.MilkyQuartz, Materials.FerricChloride, Materials.Sperrylite, Materials.LithiumChloride));
        List<Material> elec60 = new ArrayList<>(List.of(Materials.CalciumChloride, Materials.SodiumHydroxide, Materials.Propene, Materials.Ethylene, Materials.Butene, Materials.Benzene, Materials.Styrene, Materials.Ethane, Materials.Ammonia, Materials.SodiumSulfide, Materials.Methane,
                Materials.Magnesite, Materials.HydrofluoricAcid, Materials.HydrogenFluoride, /*NitroCarbon,*/ Materials.SodaAsh, Materials.Calcite, Materials.Saltpeter, Materials.Monazite,
                /*Wollastonite,*/ Materials.NitrogenMonoxide, Materials.Butane, Materials.CarbonMonoxide, Materials.RedGranite, Materials.Ferrosilite, Materials.Butadiene, Materials.Amethyst,
                Materials.Ruby, /*Kyanite,*/ Materials.NitrogenDioxide, Materials.DinitrogenTetroxide, Materials.Propane, Materials.Barite, Materials.Isoprene,
                Materials.Chromite, EnderPearl, Materials.SiliconDioxide, Materials.Apatite, Materials.SulfurTrioxide, /*Pyrochlore, */ Materials.Toluene, Materials.Phosphate,
                Materials.Tantalite, Materials.PhosphorousPentoxide, Materials.Osmiridium, Materials.Steel, Materials.Graphite, Materials.MagnesiumChloride, IodineSalt));
        List<Material> elec90 = new ArrayList<>(List.of(Materials.Polydimethylsiloxane, Materials.AceticAcid, Materials.Olivine, Materials.Ethanol, Materials.Methanol, Materials.VinylAcetate, /*Gypsum,*/
                /*Dymethylamine, Mirabilite,*/ Materials.Spodumene, /*Dolomite,*/ Materials.HypochlorousAcid, Materials.Chloramine, Materials.Bastnasite,
                Materials.Chloromethane, Materials.Malachite, /*Borax, */ /*Kaolinite,*/ Materials.Obsidian, Materials.NitricAcid, Materials.VinylChloride, Materials.Acetone, /*Asbestos,*/ Materials.PotassiumFeldspar,
                Materials.MethylAcetate, Materials.Sodalite, Materials.AllylChloride, Materials.Phenol, Materials.Glycerol, Materials.Talc, Materials.Soapstone, Materials.PhosphoricAcid,
                Materials.Chlorobenzene, Materials.SulfuricAcid, Materials.Pyrope, Materials.SodiumPersulfate, Materials.Chloroform, Materials.Grossular, Materials.Spessartine, Adamantine, Materials.Almandine, Materials.Uvarovite, Materials.Andradite,
                Emerald, Materials.Zircon));
        List<Material> elec120 = new ArrayList<>(List.of(Materials.Clay, /*Trona,*/ Materials.BlueTopaz, Materials.Topaz, /*Pollucite,*/ Materials.CarbonDioxide, Materials.SulfurDioxide, Materials.Epichlorohydrin, Materials.Lepidolite, /*FullersEarth, Alunite,*/ Materials.Glauconite,
                /*Mica,*/ Materials.Lazurite, Materials.Tanzanite, Materials.Biotite, Materials.StainlessSteel, Materials.Ultimet, Materials.CalciumAcetateSolution, Materials.Dimethyldichlorosilane, /*Vermiculate, Zeolite,*/ Materials.GlycerylTrinitrate));
        if (!GT5RConfig.FORCE_ROASTER.get()){
            elec60.addAll(List.of(Pyrite, Pentlandite, Sphalerite, Molybdenite));
            elec90.addAll(List.of(Cobaltite, Galena, Chalcopyrite));
        }
        if (!GT5RConfig.HARDER_ALUMINIUM_PROCESSING.get()){
            elec90.add(Bauxite);
            elec60.add(Alumina);
        }
        elec30.forEach(m -> elecMap.put(m, 30));
        elec60.forEach(m -> elecMap.put(m, 60));
        elec90.forEach(m -> elecMap.put(m, 90));
        elec120.forEach(m -> elecMap.put(m, 120));
    }

}
