package muramasa.gregtech.loader.machines;

import muramasa.antimatter.AntimatterAPI;
import muramasa.gregtech.GT5RRef;
import muramasa.gregtech.data.GT5RItems;
import muramasa.gregtech.items.ItemBreederRod;
import muramasa.gregtech.items.ItemNuclearFuelRod;
import org.gtreimagined.gtcore.data.GTCoreItems;

import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.recipe.ingredient.RecipeIngredient.of;
import static muramasa.gregtech.data.Materials.*;
import static muramasa.gregtech.data.RecipeMaps.CANNER;

public class CannerLoader {

    static int MULTIPLE_SMALL = 2;
    static int MULTIPLE_MEDIUM = 8;
    static int MULTIPLE_LARGE = 32;

    public static void init() {
        CANNER.RB().ii(of(GTCoreItems.BatteryHullSmall.getDefaultInstance()), of(DUST.get(Sodium, MULTIPLE_SMALL))).io(GTCoreItems.BatterySmallSodium.getDefaultInstance()).add("battery_small_sodium",100, 2);
        CANNER.RB().ii(of(GTCoreItems.BatteryHullSmall.getDefaultInstance()), of(DUST.get(Cadmium, MULTIPLE_SMALL))).io(GTCoreItems.BatterySmallCadmium.getDefaultInstance()).add("battery_small_cadmium",100, 2);
        CANNER.RB().ii(of(GTCoreItems.BatteryHullSmall.getDefaultInstance()), of(DUST.get(Lithium, MULTIPLE_SMALL))).io(GTCoreItems.BatterySmallLithium.getDefaultInstance()).add("battery_small_lithium",100, 2);

        CANNER.RB().ii(of(GTCoreItems.BatteryHullMedium.getDefaultInstance()), of(DUST.get(Sodium, MULTIPLE_MEDIUM))).io(GTCoreItems.BatteryMediumSodium.getDefaultInstance()).add("battery_medium_sodium",400, 2);
        CANNER.RB().ii(of(GTCoreItems.BatteryHullMedium.getDefaultInstance()), of(DUST.get(Cadmium, MULTIPLE_MEDIUM))).io(GTCoreItems.BatteryMediumCadmium.getDefaultInstance()).add("battery_medium_cadmium",400, 2);
        CANNER.RB().ii(of(GTCoreItems.BatteryHullMedium.getDefaultInstance()), of(DUST.get(Lithium, MULTIPLE_MEDIUM))).io(GTCoreItems.BatteryMediumLithium.getDefaultInstance()).add("battery_medium_lithium",400, 2);

        CANNER.RB().ii(of(GTCoreItems.BatteryHullLarge.getDefaultInstance()), of(DUST.get(Sodium, MULTIPLE_LARGE))).io(GTCoreItems.BatteryLargeSodium.getDefaultInstance()).add("battery_large_sodium",1600, 2);
        CANNER.RB().ii(of(GTCoreItems.BatteryHullLarge.getDefaultInstance()), of(DUST.get(Cadmium, MULTIPLE_LARGE))).io(GTCoreItems.BatteryLargeCadmium.getDefaultInstance()).add("battery_large_cadmium",1600, 2);
        CANNER.RB().ii(of(GTCoreItems.BatteryHullLarge.getDefaultInstance()), of(DUST.get(Lithium, MULTIPLE_LARGE))).io(GTCoreItems.BatteryLargeLithium.getDefaultInstance()).add("battery_large_lithium",1600, 2);
        CANNER.RB().ii(ROD.getMaterialIngredient(CdInAGAlloy, 1), of(GT5RItems.EmptyNuclearFuelRod)).io(GT5RItems.NeutronAbsorberRod).add("neutron_absorber_rod", 16, 16);
        CANNER.RB().ii(ROD.getMaterialIngredient(Beryllium, 1), of(GT5RItems.EmptyNuclearFuelRod)).io(GT5RItems.NeutronReflectorRod).add("neutron_reflector_rod", 16, 16);
        CANNER.RB().ii(ROD.getMaterialIngredient(Graphite, 1), of(GT5RItems.EmptyNuclearFuelRod)).io(GT5RItems.NeutronModeratorRod).add("neutron_moderator_rod", 16, 16);
        CANNER.RB().ii(DUST.getMaterialIngredient(Iodine, 1), of(GTCoreItems.EmptyWaxPill)).io(GTCoreItems.Radaway).add("radaway", 16, 16);
        AntimatterAPI.all(ItemNuclearFuelRod.class, GT5RRef.ID).forEach(r -> {
            if (r.getMaterial().has(ROD)){
                CANNER.RB().ii(ROD.getMaterialIngredient(r.getMaterial(), 1), of(GT5RItems.EmptyNuclearFuelRod)).io(r).add(r.getId(), 16, 16);
            }
        });
        AntimatterAPI.all(ItemBreederRod.class, GT5RRef.ID).forEach(r -> {
            if (r.getMaterial().has(BOLT)){
                CANNER.RB().ii(BOLT.getMaterialIngredient(r.getMaterial(), 4), of(GT5RItems.EmptyNuclearFuelRod)).io(r).add(r.getId(), 16, 16);
            }
        });
    }
}
