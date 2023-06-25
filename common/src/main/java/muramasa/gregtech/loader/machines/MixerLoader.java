package muramasa.gregtech.loader.machines;

import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.data.AntimatterMaterials;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.MaterialTypeItem;
import muramasa.gregtech.data.Materials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.stream.Collectors;

import static muramasa.antimatter.data.AntimatterMaterials.*;
import static muramasa.antimatter.recipe.ingredient.RecipeIngredient.*;

import static muramasa.gregtech.data.TierMaps.INT_CIRCUITS;
import static muramasa.gregtech.data.Materials.*;
import static muramasa.gregtech.data.RecipeMaps.MIXING;



public class MixerLoader {

    public static void init() {
        addDust(StainlessSteel, 8, 45*20);
        addDust(Nichrome, 8, 25*20);
        addDust(Invar, 8, 15*20);
        addDust(Bronze, 8, 10*20);
        addDust(BlackBronze, 8, 25*20);
        addDust(FerriteMixture, 8, 30*20);
        addDust(IndiumGalliumPhosphide, 8, 20*20);
        addDust(Energium, 8, 10*20);
        addDust(GalliumArsenide, 8, 15*20);
        addDust(VanadiumSteel, 8, 50*20);
        addDust(CobaltBrass, 8, 45*20);
        addDust(BlueSteel, 8, 40*20);
        addDust(RedSteel, 8, 40*20);
        addDust(BlackSteel, 8, 25*20);
        addDust(SterlingSilver, 8, 25*20);
        addDust(RoseGold, 8, 25*20);
        addDust(BismuthBronze, 8, 25*20);
        addDust(VanadiumGallium, 8, 20*20);
        addDust(Ultimet, 8, 45*20);
        addDust(TinAlloy, 8, 10*20);
        addDust(SodiumSulfide, 8, 3*20);
        addDust(Magnalium, 8, 20*20);
        addDust(Kanthal, 8, 15*20);
        addDust(Electrum, 8, 10*20);
        addDust(Brass, 8, 20*20);
        addDust(BatteryAlloy, 8, 25*20);
        addDust(Cupronickel, 8, 10*20);
        addDust(EnderEye, 8, 5*20);
        recipes();
    }

    private static void addDust(Material mat, int eut, int duration) {
        for (MaterialTypeItem<?> type : new MaterialTypeItem[]{AntimatterMaterialTypes.DUST, AntimatterMaterialTypes.DUST_SMALL, AntimatterMaterialTypes.DUST_TINY}) {
            List<Ingredient> ings = mat.getProcessInto().stream().map(t -> type.getMaterialIngredient(t.m, t.s)).collect(Collectors.toList());
            if (ings.size() == 0) return;
            var type2 = type;
            int count = mat.getProcessInto().stream().mapToInt(t -> t.s).sum();
            if (type != AntimatterMaterialTypes.DUST){
                if (type == AntimatterMaterialTypes.DUST_SMALL){
                    if (count % 4 == 0){
                        count /=4;
                        type2 = AntimatterMaterialTypes.DUST;
                    }
                } else {
                    if (count % 9 == 0){
                        count /=9;
                        type2 = AntimatterMaterialTypes.DUST;
                    }
                }
            }


            MIXING.RB().ii(ings).io(type2.get(mat,count)).add(type.getId() + "_" + mat.getId(),duration,eut);
        }
    }

    private static void recipes() {
        MIXING.RB().fi(NitricAcid.getLiquid(1000),SulfuricAcid.getLiquid(1000)).fo(NitrationMixture.getLiquid(2000)).add("nitration_mixture",25 * 20, 2);
        MIXING.RB().fi(PolyvinylAcetate.getLiquid(1000),Acetone.getLiquid(1500)).fo(Glue.getLiquid(2500)).add("glue",25 * 2, 8);
        MIXING.RB().fi(PolyvinylAcetate.getLiquid(1000),MethylAcetate.getLiquid(1500)).fo(Glue.getLiquid(2500)).add("glue_1",25 * 2, 8);
        MIXING.RB().fi(BioDiesel.getLiquid(1000),Tetranitromethane.getLiquid(40)).fo(CetaneBoostedDiesel.getLiquid(750)).add("nitro_fuel", 20, 480);
        MIXING.RB().fi(Diesel.getLiquid(1000),Tetranitromethane.getLiquid(20)).fo(CetaneBoostedDiesel.getLiquid(1000)).add("nitro_fuel_1", 20, 480);
        MIXING.RB().fi(Oxygen.getGas(1000),Dimethylhydrazine.getLiquid(1000)).fo(RocketFuel.getLiquid(3000)).add("rocket_fuel",3 * 20, 16);
        MIXING.RB().fi(DinitrogenTetroxide.getGas(1000),Dimethylhydrazine.getLiquid(1000)).fo(RocketFuel.getLiquid(6000)).add("rocket_fuel_1",3 * 20, 16);
        MIXING.RB().fi(LightFuel.getLiquid(5000),HeavyFuel.getLiquid(1000)).fo(Diesel.getLiquid(6000)).add("diesel",8 * 2, 120);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(Wood,4))).fi(SulfuricAcid.getLiquid(1000)).io(Items.CHARCOAL.getDefaultInstance()).fo(DilutedSulfuricAcid.getLiquid(1000)).add("diluted_sulfuric_acid",60*20, 2);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(AntimatterMaterials.Sugar,4))).fi(SulfuricAcid.getLiquid(1000)).io(Items.CHARCOAL.getDefaultInstance()).fo(DilutedSulfuricAcid.getLiquid(1000)).add("diluted_sulfuric_acid_1",60*20, 2);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(Salt,2))).fi(AntimatterMaterials.Water.getLiquid(1000)).fo(SaltWater.getLiquid(1000)).add("salt_water",2*20, 8);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(AntimatterMaterials.Copper,3)),of(AntimatterMaterialTypes.DUST.get(Barium,2)),of(AntimatterMaterialTypes.DUST.get(Yttrium,1))).fi(Oxygen.getGas(7000)).io(AntimatterMaterialTypes.DUST.get(YttriumBariumCuprate,13)).add("yttrium_barium_cuprate",40*20, 8);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(Talc,1))).fi(Oil.getLiquid(750)).fo(Lubricant.getLiquid(750)).add("lubricant",64*2, 4);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(Talc,1))).fi(Creosote.getLiquid(750)).fo(Lubricant.getLiquid(750)).add("lubricant_1",64*2, 4);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(Talc,1))).fi(SeedOil.getLiquid(750)).fo(Lubricant.getLiquid(750)).add("lubricant_2",64*2, 4);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(Soapstone,1))).fi(Oil.getLiquid(750)).fo(Lubricant.getLiquid(750)).add("lubricant_3",64*2, 4);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(Soapstone,1))).fi(Creosote.getLiquid(750)).fo(Lubricant.getLiquid(750)).add("lubricant_4",64*2, 4);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(Soapstone,1))).fi(SeedOil.getLiquid(750)).fo(Lubricant.getLiquid(750)).add("lubricant_5",64*2, 4);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(AntimatterMaterials.Redstone,1))).fi(Oil.getLiquid(750)).fo(Lubricant.getLiquid(750)).add("lubricant_6",64*2, 4);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(AntimatterMaterials.Redstone,1))).fi(Creosote.getLiquid(750)).fo(Lubricant.getLiquid(750)).add("lubricant_7",64*2, 4);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(AntimatterMaterials.Redstone,1))).fi(SeedOil.getLiquid(750)).fo(Lubricant.getLiquid(750)).add("lubricant_8",64*2, 4);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(Glass,7)),of(AntimatterMaterialTypes.DUST.get(Boron,1))).io(AntimatterMaterialTypes.DUST.get(BorosilicateGlass,8)).add("borosilicate_glass",40*20, 8);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(Saltpeter,2)),of(AntimatterMaterialTypes.DUST.get(Sulfur,1)),of(AntimatterMaterialTypes.DUST.get(AntimatterMaterials.Coal,1))).io(Items.GUNPOWDER).add("gunpowder",20*20, 8);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(Saltpeter,2)),of(AntimatterMaterialTypes.DUST.get(Sulfur,1)),of(AntimatterMaterialTypes.DUST.get(AntimatterMaterials.Charcoal,1))).io(new ItemStack(Items.GUNPOWDER,2)).add("gunpowder_1",15*20, 8);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(AntimatterMaterials.Stone,1))).fi(Lubricant.getLiquid(20), AntimatterMaterials.Water.getLiquid(4980)).fo(DrillingFluid.getLiquid(5000)).add("drilling_fluid",32*2, 16);
        MIXING.RB().ii(of(AntimatterMaterialTypes.DUST.get(AntimatterMaterials.Stone,3)),of(AntimatterMaterialTypes.DUST.get(Clay,1))).fi(AntimatterMaterials.Water.getLiquid(500)).fo(Concrete.getLiquid(576)).add("concrete", 20, 16);
        MIXING.RB().fi(SodiumCarbonateSolution.getLiquid(1000),SodiumBicarbonateSolution.getLiquid(1000)).fo(LeachingSolution.getLiquid(2000)).add("leaching_solution", 60, 32);
    }

}
