package muramasa.gregtech.datagen;


import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.providers.AntimatterLanguageProvider;
import muramasa.antimatter.item.ItemBasic;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.util.Utils;
import muramasa.gregtech.GTIRef;
import muramasa.gregtech.block.*;
import muramasa.gregtech.data.GregTechBlocks;
import muramasa.gregtech.data.GregTechItems;
import muramasa.gregtech.items.ItemDepletedRod;
import muramasa.gregtech.items.ItemIntCircuit;
import muramasa.gregtech.items.ItemNuclearFuelRod;

import java.util.Locale;

import static muramasa.antimatter.machine.Tier.*;
import static muramasa.antimatter.util.Utils.*;
import static muramasa.gregtech.data.Machines.*;
import static muramasa.gregtech.data.Machines.LARGE_BOILER;

public class GregTechLocalizations {

    public static class en_US extends AntimatterLanguageProvider {

        public en_US() {
            super(GTIRef.ID, GTIRef.NAME + " en_us Localization", "en_us");
        }

        @Override
        protected void addTranslations() {
            super.addTranslations();
            add(GTIRef.ID + ".advancements.greg", "GregTech Intergalactical");
            add(GTIRef.ID + ".advancements.greg.desc", "Getting familiar with your surroundings");
            add(GTIRef.ID + ".rei.tooltip.ore.byproducts", "Ore Byproducts List");
            add(GTIRef.ID + ".rei.tooltip.material_tree", "Material Tree");
            add("machine.transformer.voltage_info", "%s -> %s (Use Soft Hammer to invert)");
            add("machine.gti.large_boiler.production", "Produces %sL of Steam with 1 Coal at %sL/s");
            add("machine.gti.large_boiler.circuit", "A programmed circuit in the main block throttles the boiler (-1000L/s per config)");
            add(GTIRef.ID + ".rotor.tooltip.efficiency", "Turbine Efficiency: %s");
            add(GTIRef.ID + ".rotor.tooltip.steam_flow", "Optimal Steam flow: %sL/sec");
            add(GTIRef.ID + ".rotor.tooltip.gas_flow", "Optimal Gas flow(EU burnvalue per tick): %sEU/t");
            add("tooltip.gti.redstone_mode.2", "Ignore Redstone");
            add("tooltip.gti.redstone_mode.1", "Invert Conditional");
            add("tooltip.gti.redstone_mode.0", "Conditional");
            add("tooltip.gti.redstone_mode.normal", "Normal");
            add("tooltip.gti.redstone_mode.inverted", "Inverted");
            add("tooltip.gti.export_mode.0", "Export");
            add("tooltip.gti.export_mode.1", "Import");
            add("tooltip.gti.export_mode.2", "Export allow Import");
            add("tooltip.gti.export_mode.3", "Import allow Export");
            add("tooltip.gti.filter_mode.0", "Filter on both Export and Import");
            add("tooltip.gti.filter_mode.1", "Filter on Import only");
            add("tooltip.gti.filter_mode.2", "Filter on Export only");
            add("tooltip.gti.whitelist", "Whitelist");
            add("tooltip.gti.blacklist", "Blacklist");
            add("tooltip.gti.nbt.on", "Don't ignore nbt");
            add("tooltip.gti.nbt.off", "Ignore nbt");
            add("tooltip.gti.data_stick.raw_prospection_data", "Raw Prospection Data");
            add("tooltip.gti.data_stick.analyzed_prospection_data", "Analyzed Prospection Data");
            add("tooltip.gti.data_stick.by", "By X: %s Z: %s Dim: %s");
            add("tooltip.gti.coil.percentage", "Pyrolysis oven processing speed percentage: %s");
            add("tooltip.gti.coil.maxSimultaneousRecipes", "Max simultaneous recipes in Multismelter: %s");
            add("tooltip.gti.depleted_rod.depleted", "Depleted");
            add("tooltip.gti.depleted_rod.0", "This Rod is %s and will not output or accept any Neutrons");
            add("tooltip.gti.depleted_rod.1", "Can be centrifuged to get valuable materials");
            add("tooltip.gti.enriched_rod.0", "Emits half the Heat per Neutron on this Rod");
            add("tooltip.gti.enriched_rod.1", "Breed from %s");
            add("tooltip.gti.breeder_rod.0", "Absorbs Neutrons to breed into an %s");
            add("tooltip.gti.breeder_rod.1", "Emits half the Heat per Neutron on this Rod");
            add("tooltip.gti.breeder_rod.2", "Can't breed with Neutrons from %s Fuel Rods");
            add("tooltip.gti.breeder_rod.3", "The %s value gets subtracted from Neutrons entering this Rod");
            add("tooltip.gti.breeder_rod.4", "This applies to each side where Neutrons enter, not to the total of all sides");
            add("tooltip.gti.breeder_rod.5", "Remaining Neurons on this Rod get added to the breeding process");
            add("tooltip.gti.breeder_rod.6", "Turns into: %s");
            add("tooltip.gti.breeder_rod.7", "Needed: %s %s");
            add("tooltip.gti.breeder_rod.8", "Loss: %s %s");
            add("tooltip.gti.breeder_rod.loss", "Loss");
            add("tooltip.gti.breeder_rod.neutrons", "Neutrons");
            add("tooltip.gti.breeder_rod.enriched", "Enriched Rod");
            add("tooltip.gti.nuclear_rod.emission_1", "Emission");
            add("tooltip.gti.nuclear_rod.self_1", "Self");
            add("tooltip.gti.nuclear_rod.maximum_1", "Maximum");
            add("tooltip.gti.nuclear_rod.factor_1", "Factor");
            add("tooltip.gti.nuclear_rod.emission_info", "The %s describes how many Neutrons are emitted to adjacent Rods");
            add("tooltip.gti.nuclear_rod.self_info", "The %s describes how many Neutrons naturally onto this Rod");
            add("tooltip.gti.nuclear_rod.maximum_info", "The %s describes how many Neutrons can be on this Rod while lasting the advertised duration");
            add("tooltip.gti.nuclear_rod.factor_info", "A greater %s means the Rod emits more extra Neutrons for the amount of Neutrons on it");
            add("tooltip.gti.nuclear_rod.remaining", "Remaining: %s Minutes");
            add("tooltip.gti.nuclear_rod.emission", "Emission: %s %s");
            add("tooltip.gti.nuclear_rod.self", "Self: %s %s");
            add("tooltip.gti.nuclear_rod.maximum", "Maximum: %s %s");
            add("tooltip.gti.nuclear_rod.neutrons", "Neutrons/t");
            add("tooltip.gti.nuclear_rod.factor", "Factor: %s");
            add("tooltip.gti.nuclear_rod.critical.0", "This fuel is %s");
            add("tooltip.gti.nuclear_rod.critical.1", "Critical");
            add("tooltip.gti.nuclear_rod.moderated.0", "Fuel rods will be %s");
            add("tooltip.gti.nuclear_rod.moderated.1", "Moderated");
            add("tooltip.gti.nuclear_rod.heat", "%s the heat per Neutron");
            add("tooltip.gti.nuclear_rod.when_used.1", "When used with %s:");
            add("tooltip.gti.nuclear_rod.when_used.2", "When used with %s or %s:");
            add("tooltip.gti.empty_nuclear_fuel_rod.0", "Empty Reactor Rod, transparent to Neutrons.");
            add("tooltip.gti.neutron_absorber_rod.0", "Absorbs Neutrons and emits twice the Heat per Neutron to Coolant");
            add("tooltip.gti.neutron_reflector_rod.0", "Reflects Neutrons back to their Source, boosting the Reaction");
            add("tooltip.gti.neutron_moderator_rod.0", "Reflects Neutrons back times the number of fuel rods touching when active");
            add("tooltip.gti.neutron_moderator_rod.1", "Touching Fuel Rods become moderated and moderate touching Fuel Rods");
            add("tooltip.gti.neutron_moderator_rod.2", "Moderated Fuel Rods can't be used for Breeding and only last a quarter as long");
            add("tooltip.gti.int_circuit.0", "Right click to cycle mode forward");
            add("tooltip.gti.int_circuit.1", "Shift right click to cycle mode backward");
            add("message.gti.nuclear_reactor.off", "Reactor Block is OFF");
            add("message.gti.nuclear_reactor.on", "Reactor Block is ON");
            add("message.gti.nuclear_reactor.neutron_levels", "Neutron Levels: %sn; %sn; %sn; %sn");
            add("message.gti.mini_portal.connect", "Target at: x: %s y: %s z: %s in %s");
            add("message.gti.redstone_mode.normal", "Redstone Mode: Normal");
            add("message.gti.redstone_mode.inverted", "Redstone Mode: Inverted");
            add("message.gti.needs_maintenance.scaled.normal", "Outputs a scaled signal");
            add("message.gti.needs_maintenance.scaled.inverted", "Outputs a scaled signal (Inverted)");
            add("message.gti.needs_maintenance.unscaled.normal", "Outputs a flat signal");
            add("message.gti.needs_maintenance.unscaled.inverted", "Outputs a flat signal (Inverted)");
            structureTranslations();
        }

        private void structureTranslations(){
            add("tooltip.electric_blast_furnace.0", "Controller for the Electrical Blast Furnace");
            add("tooltip.electric_blast_furnace.1", "Size(WxHxD): 3x4x3");
            add("tooltip.electric_blast_furnace.2", "Controller (bottom Middle front centered)");
            add("tooltip.electric_blast_furnace.3", "1x Item Input Hatch (Minimum 1) Bottom Layer outside ring");
            add("tooltip.electric_blast_furnace.4", "1x Item Output Hatch Bottom Layer outside ring");
            add("tooltip.electric_blast_furnace.5", "1x Energy Hatch Bottom Layer outside ring");
            add("tooltip.electric_blast_furnace.6", "16x Coils Blocks, 2 layers of rings of x8  Coil Blocks, each middle layers (The center of both layers should be empty)");
            add("tooltip.electric_blast_furnace.7", "8x Heat Proof Casings around a Muffler Hatch (facing up) on top Layer");
            add("tooltip.electric_blast_furnace.8", "2 -4 Amps of power supply");

            add("tooltip.combustion_engine.0", "Controller for the Large Combustion Engine");
            add("tooltip.combustion_engine.1", "Size(WxHxD): 3x3x4");
            add("tooltip.combustion_engine.2", "Controller (front centered)");
            add("tooltip.combustion_engine.3", "3x3x4 of Stable Titanium Casing (hollow, Min 24!)");
            add("tooltip.combustion_engine.4", "2x Titanium Gear Box Casing inside the Hollow Casing ");
            add("tooltip.combustion_engine.5", "8x Engine Intake Casings (around controller Min 8!)");
            add("tooltip.combustion_engine.6", "1x Input Hatch (one of the Casings)");
            add("tooltip.combustion_engine.7", "1x Muffler Hatch (Top middle back)");
            add("tooltip.combustion_engine.8", "1x Dynamo Hatch (back centered) ");
            add("tooltip.combustion_engine.9", "Engine Intake Casings not obstructed (only air blocks)");

            add("tooltip.cracking_unit.0", "Controller for the Oil Cracking Unit");
            add("tooltip.cracking_unit.1", "Size(WxHxD): 5x3x3");
            add("tooltip.cracking_unit.2", "Controller (middle front centered)");
            add("tooltip.cracking_unit.3", "3x3x4 Titanium Casing (hollow, Min 22!)");
            add("tooltip.cracking_unit.4", "16x Cupronickel Coils Blocks, two hollow vertical rings touching each side of the controller");
            add("tooltip.cracking_unit.5", "1x Fluid Input Hatch on the Right Side Middle Center of Vertical Ring  the oil product");
            add("tooltip.cracking_unit.6", "1x Fluid Input Hatch  on any of the center Casing Ring  For Steam input");
            add("tooltip.cracking_unit.7", "1x Fluid Output Hatch on the Left Side Middle  Center of Vertical Ring  for Cracked Product");
            add("tooltip.cracking_unit.8", "1x Energy Hatch (Any of the center Casing Ring)");
            add("tooltip.cracking_unit.9", "18-21x Stainless Steel Casing Elsewhere");
            add("tooltip.cracking_unit.10", "Optional 1x Item Hatch Input or 1x Item Hatch Output  on the middle Ring Casings");

            add("tooltip.distillation_tower.0", "Controller for the Distillation Tower");
            add("tooltip.distillation_tower.1", "Size(WxHxD): 3xNx3(where N is between three and twelve)");
            add("tooltip.distillation_tower.2", "Controller (middle bottom front centered)");
            add("tooltip.distillation_tower.3", "1x Energy Hatch any bottom layer casing");
            add("tooltip.distillation_tower.4", "1x+ Item Output Hatch any bottom layer casing");
            add("tooltip.distillation_tower.5", "1x+ Fluid Input Hatch any bottom layer casing");
            add("tooltip.distillation_tower.6", "1-11x Fluid Output Hatch exactly one per layer (except the bottom layer)");
            add("tooltip.distillation_tower.7", "19-82x Stainless Steel  Casings, everywhere else.");

            add("tooltip.cryo_distillation_tower.0", "Controller for the Cryogenic Distillation Tower");
            add("tooltip.cryo_distillation_tower.1", "Size(WxHxD): 3xNx3(where N is between three and twelve)");
            add("tooltip.cryo_distillation_tower.2", "Controller (middle bottom front centered)");
            add("tooltip.cryo_distillation_tower.3", "1x Energy Hatch any bottom layer casing");
            add("tooltip.cryo_distillation_tower.4", "1x+ Item Output Hatch any bottom layer casing");
            add("tooltip.cryo_distillation_tower.5", "1x+ Fluid Input Hatch any bottom layer casing");
            add("tooltip.cryo_distillation_tower.6", "1-11x Fluid Output Hatch exactly one per layer (except the bottom layer)");
            add("tooltip.cryo_distillation_tower.7", "19-82x Frostproof Casings, everywhere else.");

            add("tooltip.heat_exchanger.0", "Controller for the Heat Exchanger");
            add("tooltip.heat_exchanger.1", "Size(WxHxD): 3x4x3");
            add("tooltip.heat_exchanger.2", "Controller (middle bottom front centered)");
            add("tooltip.heat_exchanger.3", "2x Fluid Input Hatch, one center of bottom layer (facing down for Hot Fluid input), the other any exterior casing of mid layers");
            add("tooltip.heat_exchanger.4", "2x Fluid Output Hatch, one center of top layer (facing up), the other any exterior casing of mid layers");
            add("tooltip.heat_exchanger.5", "2x Titanium Pipe Casings (center two blocks of multi, not visible from outside)");
            add("tooltip.heat_exchanger.6", "21-30x Titanium Casings, everywhere else.");

            add("tooltip.implosion_compressor.0", "Controller for the Implosion Compressor");
            add("tooltip.implosion_compressor.1", "Size(WxHxD): 3x3x3");
            add("tooltip.implosion_compressor.2", "Controller (middle front centered)");
            add("tooltip.implosion_compressor.3", "17-20x  Solid Steel Casings (Hollow Cube)");
            add("tooltip.implosion_compressor.4", "None or 1+ Item Output hatch on any Sides");
            add("tooltip.implosion_compressor.5", "None or 1+ Item Input Hatch on any Sides");
            add("tooltip.implosion_compressor.6", "1x+ Energy Hatch on any Sides");
            add("tooltip.implosion_compressor.7", "1x Muffler Hatch on any Sides");
        }

        @Override
        protected void english(String domain, String locale) {
            super.english(domain, locale);
            AntimatterAPI.all(BlockCasing.class, domain).forEach(i -> {
                if (i.getId().startsWith("casing_") || i.getId().startsWith("hull_")){
                    add(i, lowerUnderscoreToUpperSpacedRotated(i.getId()));
                    return;
                }
                if (i.getId().contains("long_distance_cable")){
                    String tier = i.getId().replace("long_distance_cable_", "");
                    add(i, "Long Distance Cable (" + tier.toUpperCase() + ")");
                }
                add(i, lowerUnderscoreToUpperSpaced(i.getId()));
            });

            add(GregTechBlocks.MINING_PIPE, "Mining Pipe");
            add(GregTechBlocks.MINING_PIPE_THIN, "Mining Pipe");
            add(GregTechBlocks.BRITTLE_CHARCOAL, "Brittle Charcoal");
            add(GregTechBlocks.SOLID_SUPER_FUEL, "Solid Super Fuel");
            AntimatterAPI.all(BlockFakeCasing.class, domain).forEach(i -> add(i, lowerUnderscoreToUpperSpaced(i.getId())));
            AntimatterAPI.all(BlockColoredWall.class, domain).forEach(i -> add(i, lowerUnderscoreToUpperSpaced(i.getId())));
            AntimatterAPI.all(BlockAsphalt.class, domain).forEach(i -> add(i, lowerUnderscoreToUpperSpaced(i.getId())));
            AntimatterAPI.all(BlockAsphaltSlab.class, domain).forEach(i -> add(i, lowerUnderscoreToUpperSpaced(i.getId())));
            AntimatterAPI.all(BlockAsphaltStair.class, domain).forEach(i -> add(i, lowerUnderscoreToUpperSpaced(i.getId())));
            AntimatterAPI.all(BlockCoil.class, domain).forEach(i -> add(i, lowerUnderscoreToUpperSpaced(i.getId())));
            AntimatterAPI.all(ItemBasic.class, domain).forEach(i -> override(i.getDescriptionId(), lowerUnderscoreToUpperSpaced(i.getId())
                    .replace("Lv", "(LV)")
                    .replace("Mv", "(MV)")
                    .replace("Hv", "(HV)")
                    .replace("Ev", "(EV)")
                    .replace("Iv", "(IV)")));
            AntimatterAPI.all(ItemIntCircuit.class, domain).forEach(i -> override(i.getDescriptionId(), "Integrated Circuit (" + i.circuitId + ")"));
            AntimatterAPI.all(ItemNuclearFuelRod.class, domain).forEach(i -> override(i.getDescriptionId(), Utils.getLocalizedType(i.getMaterial()) + " Fuel Rod"));
            AntimatterAPI.all(ItemDepletedRod.class, domain).forEach(i -> override(i.getDescriptionId(), "Depleted " + Utils.getLocalizedType(i.getMaterial()) + " Fuel Rod"));
            String[] fluids = new String[]{"hot_molten_lithium_chloride", "hot_molten_tin", "hot_molten_sodium"};
            for (String s : fluids) {
                override("fluid_type.antimatter_shared.liquid_" + s, Utils.lowerUnderscoreToUpperSpaced(s));
                override("item.antimatter_shared.liquid_" + s + "_bucket", Utils.lowerUnderscoreToUpperSpaced(s) + " Bucket");
            }
            override("fluid_type.antimatter_shared.liquid_nitrogen", Utils.lowerUnderscoreToUpperSpaced("liquid_nitrogen"));
            override("item.antimatter_shared.liquid_nitrogen_bucket", Utils.lowerUnderscoreToUpperSpaced("liquid_nitrogen") + " Bucket");
//            AntimatterAPI.all(ItemPowerUnit.class, domain).stream().filter(i -> i.getId().startsWith("power_unit") || i.getId().startsWith("small_power_unit")).forEach(i -> override(i.getDescriptionId(), lowerUnderscoreToUpperSpaced(i.getId())));
            override(LARGE_TURBINE, HV, "Large Steam Turbine");
            override(LARGE_TURBINE, EV, "Large Gas Turbine");
            override(LARGE_TURBINE, IV, "Large HP Steam Turbine");
            override(LARGE_BOILER, LV, "Large Bronze Boiler");
            override(LARGE_BOILER, MV, "Large Steel Boiler");
            override(LARGE_BOILER, HV, "Large HP Titanium Boiler");
            override(LARGE_BOILER, EV, "Large HP Tungstensteel Boiler");
            override(GregTechItems.EmptyGeigerCounter.getDescriptionId(), "Geiger Counter (Empty)");
            add(GregTechBlocks.POWDER_BARREL, "Powder Barrel");
            override("machine.hull", "%s " + HULL.getLang(locale));
            HULL.getTiers().forEach(tier -> {
                override(HULL, tier, tier.getId().toUpperCase() + " " + HULL.getLang(locale));
            });
        }
    }

}
