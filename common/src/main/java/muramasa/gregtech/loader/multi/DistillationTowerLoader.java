package muramasa.gregtech.loader.multi;

import io.github.gregtechintergalactical.gtcore.data.GTCoreFluids;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.recipe.map.RecipeBuilder;
import muramasa.gregtech.GregTechConfig;
import muramasa.gregtech.material.FluidProduct;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import tesseract.FluidPlatformUtils;
import tesseract.TesseractGraphWrappers;

import static muramasa.antimatter.data.AntimatterMaterialTypes.DUST_SMALL;
import static muramasa.antimatter.data.AntimatterMaterials.*;
import static muramasa.gregtech.data.Materials.*;
import static muramasa.gregtech.data.RecipeMaps.*;
import static muramasa.gregtech.data.TierMaps.INT_CIRCUITS;

public class DistillationTowerLoader {

    public static void init() {
        /*if (GregTechConfig.GT5U_OIL.get()){
            init5U();
        } else {
            initBasic();
        }*/
        if (GregTechConfig.COMPLICATED_CHEMICAL_PROCESSING.get()){
            initComplicated();
        }
        initBasic();
        initCracked();
        addDistillingRecipe(Creosote, 24, 16, 96, ItemStack.EMPTY, new FluidProduct(Lubricant, 12));
        addDistillingRecipe(FishOil, 24, 16, 96, ItemStack.EMPTY, new FluidProduct(Lubricant, 12));
        addDistillingRecipe(SeedOil, 32, 16, 96, ItemStack.EMPTY, new FluidProduct(Lubricant, 12));
        addDistillingRecipe(Water, 576, 16, 120, ItemStack.EMPTY, new FluidProduct(DistilledWater,520));
        DISTILLERY.RB()
                .ii(INT_CIRCUITS.get(0).setNoConsume())
                .fi(FluidPlatformUtils.createFluidStack(GTCoreFluids.BEET_JUICE.getFluid(), 100 * TesseractGraphWrappers.dropletMultiplier))
                .fo(DistilledWater.getLiquid(50)).io(Items.SUGAR).add("beet_juice", 80, 16);
        DISTILLERY.RB()
                .ii(INT_CIRCUITS.get(0).setNoConsume())
                .fi(Honey.getLiquid(100))
                .fo(DistilledWater.getLiquid(10)).io(Items.SUGAR).add("honey", 16, 16);
        addDistillingRecipe(DilutedHydrochloricAcid, 2000, 300, 64, ItemStack.EMPTY,
                new FluidProduct(HydrochloricAcid, 1000),
                new FluidProduct(Water, 1000));
        addDistillingRecipe(DilutedSulfuricAcid, 2000, 600, 120, ItemStack.EMPTY,
                new FluidProduct(SulfuricAcid, 1000),
                new FluidProduct(Water, 1000));
        addDistillationDistillingRecipe(Biomass, 80, 16, 64, 24, 16, DUST_SMALL.get(Wood, 1),
                new FluidProduct(Ethanol, 20),
                new FluidProduct(Glycerol, 20),
                new FluidProduct(Methane, 4),
                new FluidProduct(DistilledWater,50));
        Material ethane = GregTechConfig.COMPLICATED_CHEMICAL_PROCESSING.get() ? Ethane : Ethylene;
        addDistillationDistillingRecipe(RefineryGas, 1000, 240, 120,
                new FluidProduct(Butane, 60),
                new FluidProduct(Propane, 70),
                new FluidProduct(ethane, 100),
                new FluidProduct(Methane, 750),
                new FluidProduct(Helium, 20));
        CRYO_DISTILLATION.RB().fi(Air.getGas(200)).fo(Helium.getGas(1), Neon.getGas(1), Argon.getGas(1), Nitrogen.getGas(143), Oxygen.getGas(50), CarbonDioxide.getGas(10)).add("air_distillation", 64, 64);
    }

    private static void initCracked(){
        addDistillationRecipe(SteamCrackedButane, 1000, 120, 120, DUST_SMALL.get(Carbon, 1),
                new FluidProduct(Butadiene,500), new FluidProduct(Propene,1000),
                new FluidProduct(Ethylene,1000),new FluidProduct(Methane,500));
        addDistillationRecipe(SteamCrackedNaphtha, 1000, 120, 120, DUST_SMALL.get(Carbon, 1),
                new FluidProduct(Toluene,200),new FluidProduct(Benzene,400),
                new FluidProduct(Butadiene,400),new FluidProduct(Propene,600),
                new FluidProduct(Ethylene,600), new FluidProduct(Methane,600));
        /*addDistillationRecipe(SteamCrackedRefineryGas, 1000, 120, 120, DUST_SMALL.get(Carbon, 1),
                new FluidProduct(Butadiene,60),new FluidProduct(Propene,70),
                new FluidProduct(Ethylene,100), new FluidProduct(Methane,750),
                new FluidProduct(Helium, 20));*/
        addDistillationRecipe(SteamCrackedEthane, 1000, 120, 120, DUST_SMALL.get(Carbon, 1),
                new FluidProduct(Ethylene,1000), new FluidProduct(Methane,500));
        addDistillationRecipe(SteamCrackedPropane, 1000, 120, 120, DUST_SMALL.get(Carbon, 1),
                new FluidProduct(Propene,500), new FluidProduct(Ethylene,1000),
                new FluidProduct(Methane,500));
        addDistillationRecipe(HydroCrackedEthane, 1000, 120, 120, new FluidProduct(Methane,2000));
        addDistillationRecipe(HydroCrackedRefineryGas, 1000, 120, 120,
                new FluidProduct(Methane,1500), new FluidProduct(Helium, 20));
        addDistillationRecipe(HydroCrackedPropane, 1000, 120, 120,
                new FluidProduct(Ethane, 1000), new FluidProduct(Methane, 1000));
        addDistillationRecipe(HydroCrackedButane, 1000, 120, 120,
                new FluidProduct(Propane, 1000), new FluidProduct(Ethane, 1000),
                new FluidProduct(Methane, 1000));
        addDistillationRecipe(HydroCrackedNaphtha, 1000, 120, 120,
                new FluidProduct(Butane, 750), new FluidProduct(Propane, 750),
                new FluidProduct(Ethane, 750), new FluidProduct(Methane, 750));
    }

    private static void initBasic(){
        addDistillationDistillingRecipe(OilLight, 25, 64, 64,
                new FluidProduct(FuelOil,15),
                new FluidProduct(Diesel,10),
                new FluidProduct(Naphtha, 10),
                new FluidProduct(Kerosene, 10),
                new FluidProduct(RefineryGas, 25),
                new FluidProduct(Lubricant, 15),
                new FluidProduct(Tar, 15));
        addDistillationDistillingRecipe(Oil, 25, 64, 64,
                new FluidProduct(FuelOil,25),
                new FluidProduct(Diesel,15),
                new FluidProduct(Naphtha, 15),
                new FluidProduct(Kerosene, 15),
                new FluidProduct(RefineryGas, 15),
                new FluidProduct(Lubricant, 25),
                new FluidProduct(Tar, 15));
        addDistillationDistillingRecipe(OilHeavy, 25, 64, 64,
                new FluidProduct(FuelOil,30),
                new FluidProduct(Diesel,20),
                new FluidProduct(Naphtha, 20),
                new FluidProduct(Kerosene, 20),
                new FluidProduct(RefineryGas, 10),
                new FluidProduct(Lubricant, 40),
                new FluidProduct(Tar, 15));
    }

    private static void initComplicated(){
        addDistillationDistillingRecipe(CharcoalByproducts, 1000, 40, 256, 64, 80, DUST_SMALL.get(Charcoal,1),
                new FluidProduct(WoodTar, 250),
                new FluidProduct(WoodVinegar, 500),
                new FluidProduct(WoodGas, 250));
        addDistillationDistillingRecipe(WoodGas, 1000, 40, 256, 64, 16, ItemStack.EMPTY,
                new FluidProduct(CarbonDioxide, 490),
                new FluidProduct(Ethylene, 20),
                new FluidProduct(Methane, 130),
                new FluidProduct(CarbonMonoxide, 340),
                new FluidProduct(Hydrogen, 20));
        addDistillationDistillingRecipe(FermentedBiomass, 1000, 75, 180, 8, 1500, ItemStack.EMPTY,
                new FluidProduct(AceticAcid, 25),
                new FluidProduct(Water, 375),
                new FluidProduct(Ethanol, 150),
                new FluidProduct(Methanol, 150),
                new FluidProduct(Ammonia, 100),
                new FluidProduct(CarbonDioxide, 400),
                new FluidProduct(Methane, 600));
        addDistillationDistillingRecipe(WoodVinegar, 1000, 40, 256,
                new FluidProduct(AceticAcid, 100),
                new FluidProduct(Water, 500),
                new FluidProduct(Ethanol, 10),
                new FluidProduct(Methanol, 300),
                new FluidProduct(Acetone, 50),
                new FluidProduct(MethylAcetate, 10));
        addDistillationDistillingRecipe(WoodTar, 1000, 40, 256,
                new FluidProduct(Creosote, 500),
                new FluidProduct(Phenol, 75),
                new FluidProduct(Benzene, 350),
                new FluidProduct(Toluene, 75));
        /*addDistillationDistillingRecipe(Acetone, 1000, 50, 640,
                new FluidProduct(Ethenone, 500),
                new FluidProduct(Methane, 500));*/
    }

    private static void addDistillationDistillingRecipe(Material input, int amount, int ticks, int euPerTick, int distilleryPerTick, int distilleryTicks, ItemStack itemStack, FluidProduct... outputs){
        addDistillationRecipe(input, amount, ticks, euPerTick, itemStack, outputs);
        for (int i = 0; i < outputs.length; i++){
            RecipeBuilder b = DISTILLERY.RB()
                    .ii(INT_CIRCUITS.get(i + 1).setNoConsume())
                    .fi(input.has(AntimatterMaterialTypes.LIQUID) ? input.getLiquid(amount) : input.getGas(amount))
                    .fo(outputs[i].convert());
            if (!itemStack.isEmpty()) b.io(itemStack);
            b.add(input.getId() + "_" + outputs[i].mat().getId(), distilleryTicks, distilleryPerTick);
        }
    }

    private static void addDistillationDistillingRecipe(Material input, int amount, int ticks, int euPerTick, FluidProduct... outputs){
        addDistillationDistillingRecipe(input, amount, ticks, euPerTick, euPerTick / 4, ticks, ItemStack.EMPTY, outputs);
    }

    private static void addDistillingRecipe(Material input, int amount, int ticks, int euPerTick, ItemStack itemStack, FluidProduct... outputs){
        for (int i = 0; i < outputs.length; i++){
            RecipeBuilder b = DISTILLERY.RB()
                    .ii(INT_CIRCUITS.get(i + 1).setNoConsume())
                    .fi(input.has(AntimatterMaterialTypes.LIQUID) ? input.getLiquid(amount) : input.getGas(amount))
                    .fo(outputs[i].convert());
            if (!itemStack.isEmpty()) b.io(itemStack);
            b.add(input.getId() + "_" + outputs[i].mat().getId(), ticks, euPerTick);
        }
    }

    private static void addDistillationRecipe(Material input, int amount, int ticks, int euPerTick, ItemStack itemOutput, FluidProduct... outputs){
        RecipeBuilder builder = DISTILLATION.RB().fi(input.has(AntimatterMaterialTypes.LIQUID) ? input.getLiquid(amount) : input.getGas(amount));
        for(int i=0;i<outputs.length;i++){
            Material fo = outputs[i].mat();
            if (fo.has(AntimatterMaterialTypes.LIQUID)){
                builder.fo(outputs[i].mat().getLiquid(outputs[i].amount()));
            } else if (fo.has(AntimatterMaterialTypes.GAS)){
                builder.fo(outputs[i].mat().getGas(outputs[i].amount()));
            }

        }

        if (!itemOutput.isEmpty()) builder.io(itemOutput);
        builder.add(input.getId(), ticks, euPerTick);
    }

    private static void addDistillationRecipe(Material input, int amount, int ticks, int euPerTick, FluidProduct... outputs){
        addDistillationRecipe(input, amount, ticks, euPerTick, ItemStack.EMPTY, outputs);
    }
}
