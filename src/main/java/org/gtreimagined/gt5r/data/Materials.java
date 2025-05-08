package org.gtreimagined.gt5r.data;

import net.minecraft.world.item.DyeColor;
import org.gtreimagined.gt5r.GT5RRef;
import org.gtreimagined.gtcore.data.GTCoreMaterials;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTLibMaterials;
import org.gtreimagined.gtlib.material.Material;

import static org.gtreimagined.gtlib.material.Element.*;
import static org.gtreimagined.gtlib.material.TextureSet.*;

public class Materials {
    //TODO add Zincite, chromium dioxide(mass multi=3), niobium nitride, nitro carbon, wollastonite, kyanite, chromite, pyrochlore, gypsum,
    // dymethylamine, mirabilite, dolomite, borax, kaolinite, asbestos, glycerol, chlorobenzene, trona, Pollucite, Fullers Earth, alunite, mica, vermiculate, zeolite


    /**
     *** PSE (No Isotopes)
     **/

    public static Material Hydrogen = GTCoreMaterials.Hydrogen;
    public static Material Helium = GTCoreMaterials.Helium;
    public static Material Lithium = GTCoreMaterials.Lithium;
    public static Material Beryllium = GTCoreMaterials.Beryllium;
    public static Material Boron = GTCoreMaterials.Boron;
    public static Material Carbon = GTCoreMaterials.Carbon;
    public static Material Nitrogen = GTCoreMaterials.Nitrogen;
    public static Material Oxygen = GTCoreMaterials.Oxygen;
    public static Material Fluorine = GTCoreMaterials.Fluorine;
    public static Material Neon = GTCoreMaterials.Neon;
    public static Material Sodium = GTCoreMaterials.Sodium;
    public static Material Magnesium = GTCoreMaterials.Magnesium;
    public static Material Aluminium = GTCoreMaterials.Aluminium;
    public static Material Silicon = GTCoreMaterials.Silicon;
    public static Material Phosphor = GTCoreMaterials.Phosphor;
    public static Material Sulfur = GTCoreMaterials.Sulfur;
    public static Material Chlorine = GTCoreMaterials.Chlorine;
    public static Material Argon = GTCoreMaterials.Argon;
    public static Material Potassium = GTCoreMaterials.Potassium;
    public static Material Calcium = GTCoreMaterials.Calcium;
    public static Material Titanium = GTCoreMaterials.Titanium;
    public static Material Vanadium = GTCoreMaterials.Vanadium;
    public static Material Chromium = GTCoreMaterials.Chromium;
    public static Material Manganese = GTCoreMaterials.Manganese;
    public static Material Iron = GTLibMaterials.Iron;
    public static Material Cobalt = GTCoreMaterials.Cobalt;
    public static Material Nickel = GTCoreMaterials.Nickel;
    public static Material Copper = GTLibMaterials.Copper;
    public static Material Zinc = GTCoreMaterials.Zinc;
    public static Material Gallium = GTCoreMaterials.Gallium;
    public static Material Germanium = GTCoreMaterials.Germanium;
    public static Material Arsenic = GTCoreMaterials.Arsenic;
    public static Material Selenium = GTCoreMaterials.Selenium;
    public static Material Krypton = GTCoreMaterials.Krypton;
    public static Material Rubidium = GTCoreMaterials.Rubidium;
    public static Material Strontium = GTCoreMaterials.Strontium;
    public static Material Yttrium = GTCoreMaterials.Yttrium;
    public static Material Zirconium = GTCoreMaterials.Zirconium;
    public static Material Niobium = GTCoreMaterials.Niobium;
    public static Material Molybdenum = GTCoreMaterials.Molybdenum;
    public static Material Technetium = GTCoreMaterials.Technetium;
    public static Material Ruthenium = GTCoreMaterials.Ruthenium;
    public static Material Rhodium = GTCoreMaterials.Rhodium;
    public static Material Palladium = GTCoreMaterials.Palladium;
    public static Material Silver = GTCoreMaterials.Silver;
    public static Material Cadmium = GTCoreMaterials.Cadmium;
    public static Material Indium = GTCoreMaterials.Indium;
    public static Material Tin = GTCoreMaterials.Tin;
    public static Material Antimony = GTCoreMaterials.Antimony;
    public static Material Tellurium = GTCoreMaterials.Tellurium;
    public static Material Iodine = GTCoreMaterials.Iodine;
    public static Material Xenon = GTCoreMaterials.Xenon;
    public static Material Caesium = GTCoreMaterials.Caesium;
    public static Material Barium = GTCoreMaterials.Barium;
    public static Material Lanthanum = GTCoreMaterials.Lanthanum;
    public static Material Cerium = GTCoreMaterials.Cerium;
    public static Material Praseodymium = GTCoreMaterials.Praseodymium;
    public static Material Neodymium = GTCoreMaterials.Neodymium;
    public static Material Promethium = GTCoreMaterials.Promethium;
    public static Material Samarium = GTCoreMaterials.Samarium;
    public static Material Europium = GTCoreMaterials.Europium;
    public static Material Gadolinium = GTCoreMaterials.Gadolinium;
    public static Material Terbium = GTCoreMaterials.Terbium;
    public static Material Dysprosium = GTCoreMaterials.Dysprosium;
    public static Material Holmium = GTCoreMaterials.Holmium;
    public static Material Erbium = GTCoreMaterials.Erbium;
    public static Material Thulium = GTCoreMaterials.Thulium;
    public static Material Ytterbium = GTCoreMaterials.Ytterbium;
    public static Material Lutetium = GTCoreMaterials.Lutetium;
    public static Material Hafnium = GTCoreMaterials.Hafnium;
    public static Material Tantalum = GTCoreMaterials.Tantalum;
    public static Material Tungsten = GTCoreMaterials.Tungsten;
    public static Material Rhenium = GTCoreMaterials.Rhenium;
    public static Material Osmium = GTCoreMaterials.Osmium;
    public static Material Iridium = GTCoreMaterials.Iridium;
    public static Material Platinum = GTCoreMaterials.Platinum;
    public static Material Gold = GTLibMaterials.Gold;
    public static Material Mercury = GTCoreMaterials.Mercury;
    public static Material Thallium = GTCoreMaterials.Thallium;
    public static Material Lead = GTCoreMaterials.Lead;
    public static Material Bismuth = GTCoreMaterials.Bismuth;
    public static Material Polonium = GTCoreMaterials.Polonium;
    public static Material Astatine = GTCoreMaterials.Astatine;
    public static Material Radon = GTCoreMaterials.Radon;
    public static Material Francium = GTCoreMaterials.Francium;
    public static Material Radium = GTCoreMaterials.Radium;
    public static Material Actinium = GTCoreMaterials.Actinium;
    public static Material Thorium = GTCoreMaterials.Thorium.setDisplayNameString("Thorium 232");
    public static Material Protactinium = GTCoreMaterials.Protactinium;
    public static Material Uranium = GTCoreMaterials.Uranium.setDisplayNameString("Uranium 238");
    public static Material Neptunium = GTCoreMaterials.Neptunium;
    public static Material Plutonium = GTCoreMaterials.Plutonium;
    public static Material Americium = GTCoreMaterials.Americium;
    public static Material Curium = GTCoreMaterials.Curium;
    public static Material Berkelium = GTCoreMaterials.Berkelium;
    public static Material Californium = GTCoreMaterials.Californium;
    public static Material Einsteinium = GTCoreMaterials.Einsteinium;
    public static Material Fermium = GTCoreMaterials.Fermium;
    public static Material Mendelevium = GTCoreMaterials.Mendelevium;

    /**
     * Fantasy/SciFi elements
     */

    public static Material Naquadah = GTAPI.register(Material.class, new Material(GT5RRef.ID, "naquadah", 0x323232, METALLIC, Elements.Nq));
    public static Material EnrichedNaquadah = GTAPI.register(Material.class, new Material(GT5RRef.ID, "enriched_naquadah", 0x323232, SHINY, Elements.Nq528));
    public static Material Naquadria = GTAPI.register(Material.class, new Material(GT5RRef.ID, "naquadria", 0x1e1e1e, SHINY, Elements.Nq522));
    public static Material Neutronium = GTAPI.register(Material.class, new Material(GT5RRef.ID, "neutronium", 0xfafafa, DULL, Elements.Nt));
    public static Material Adamantium = GTAPI.register(Material.class, new Material(GT5RRef.ID, "adamantium", 0xffffff, SHINY, Elements.Ad));
    /**
     *** Isotopes
     **/

    public static Material HotHelium = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hot_helium", 0xffff00, NONE, He));
    public static Material Deuterium = GTAPI.register(Material.class, new Material(GT5RRef.ID, "deuterium", 0xffff00, NONE, D));
    public static Material Tritium = GTAPI.register(Material.class, new Material(GT5RRef.ID, "tritium", 0xff0000, METALLIC, T));
    public static Material Helium3 = GTAPI.register(Material.class, new Material(GT5RRef.ID, "helium_3", 0xffffff, NONE, He3));
    public static Material Cobalt60 = GTAPI.register(Material.class, new Material(GT5RRef.ID, "cobalt_60", 0x5a5afa, RAD, Co60));
    public static Material Thorium230 = GTAPI.register(Material.class, new Material(GT5RRef.ID, "thorium_230", 0x001400, RAD, Th230));
    public static Material Uranium233 = GTAPI.register(Material.class, new Material(GT5RRef.ID, "uranium_233", 0x46fa32, RAD, U233));
    public static Material Uranium235 = GTAPI.register(Material.class, new Material(GT5RRef.ID, "uranium_235", 0x46fa46, RAD, U235));
    public static Material Plutonium239 = GTAPI.register(Material.class, new Material(GT5RRef.ID, "plutonium_239", 0xeb3232, RAD, Pu239));
    public static Material Plutonium241 = GTAPI.register(Material.class, new Material(GT5RRef.ID, "plutonium_241", 0xf54646, RAD, Pu241));
    public static Material Plutonium243 = GTAPI.register(Material.class, new Material(GT5RRef.ID, "plutonium_243", 0xfa4646, RAD, Pu243));
    public static Material Americium241 = GTAPI.register(Material.class, new Material(GT5RRef.ID, "americium_241", 0xd2d2d2, RAD, Am241));
    public static Material Americium242 = GTAPI.register(Material.class, new Material(GT5RRef.ID, "americium_242", 0xd2d2d2, RAD, Am242));

    /**
     *** Solids
     **/

    /**
     ** Metals
     **/

    public static Material AnnealedCopper = GTAPI.register(Material.class, new Material(GT5RRef.ID, "annealed_copper", 0xff7814, SHINY));
    public static Material BatteryAlloy = GTAPI.register(Material.class, new Material(GT5RRef.ID, "battery_alloy", 0x9c7ca0, DULL));
    public static Material BismuthBronze = GTAPI.register(Material.class, new Material(GT5RRef.ID, "bismuth_bronze", 0x647d7d, DULL));
    public static Material BlackBronze = GTAPI.register(Material.class, new Material(GT5RRef.ID, "black_bronze", 0x64327d, DULL));
    public static Material BlackSteel = GTAPI.register(Material.class, new Material(GT5RRef.ID, "black_steel", 0x646464, METALLIC));
    public static Material BlueSteel = GTAPI.register(Material.class, new Material(GT5RRef.ID, "blue_steel", 0x64648c, METALLIC));
    public static Material Brass = GTCoreMaterials.Brass;
    public static Material Bronze = GTCoreMaterials.Bronze;
    public static Material CdInAGAlloy = GTAPI.register(Material.class, new Material(GT5RRef.ID, "cd_in_ag_alloy", 0x646480, METALLIC)).setDisplayNameString("Cd-In-Ag-Alloy");
    public static Material CobaltBrass = GTAPI.register(Material.class, new Material(GT5RRef.ID, "cobalt_brass", 0xb4b4a0, METALLIC));
    public static Material Cupronickel = GTCoreMaterials.Cupronickel;
    public static Material DamascusSteel = GTAPI.register(Material.class, new Material(GT5RRef.ID, "damascus_steel", 0x6e6e6e, METALLIC));
    public static Material Duranium = GTAPI.register(Material.class, new Material(GT5RRef.ID, "duranium", 0xffffff, METALLIC));
    public static Material Electrum = GTCoreMaterials.Electrum;
    public static Material HSSE = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hsse", 0x336600, METALLIC)).setDisplayNameString("HSS-E");
    public static Material HSSG = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hssg", 0x999900, METALLIC)).setDisplayNameString("HSS-G");
    public static Material HSSS = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hsss", 0x660033, METALLIC)).setDisplayNameString("HSS-S");
    public static Material Invar = GTCoreMaterials.Invar;
    public static Material IronMagnetic = GTAPI.register(Material.class, new Material(GT5RRef.ID, "magnetic_iron", 0xc8c8c8, MAGNETIC)).setMassMultiplierAndDivider(51, 50);
    public static Material Kanthal = GTCoreMaterials.Kanthal;
    public static Material LeadedRedstone = GTCoreMaterials.LeadedRedstone;
    public static Material Magnalium = GTAPI.register(Material.class, new Material(GT5RRef.ID, "magnalium", 0xc8beff, DULL));
    public static Material NaquadahAlloy = GTAPI.register(Material.class, new Material(GT5RRef.ID, "naquadah_alloy", 0x282828, METALLIC));
    public static Material NeodymiumMagnetic = GTAPI.register(Material.class, new Material(GT5RRef.ID, "magnetic_neodymium", 0x646464, MAGNETIC)).setMassMultiplierAndDivider(51, 50);
    public static Material Nichrome = GTCoreMaterials.Nichrome;
    public static Material NickelZincFerrite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "nickel_zinc_ferrite", 0x3c3c3c, ROUGH));
    public static Material NiobiumTitanium = GTAPI.register(Material.class, new Material(GT5RRef.ID, "niobium_titanium", 0x1d1d29, DULL));
    public static Material Osmiridium = GTCoreMaterials.Osmiridium;
    public static Material RedAlloy = GTCoreMaterials.RedAlloy;
    public static Material RedSteel = GTAPI.register(Material.class, new Material(GT5RRef.ID, "red_steel", 0x8c6464, METALLIC));
    public static Material RoseGold = GTAPI.register(Material.class, new Material(GT5RRef.ID, "rose_gold", 0xffe61e, SHINY));
    public static Material SolderingAlloy = GTCoreMaterials.SolderingAlloy;
    public static Material Steel = GTCoreMaterials.Steel;
    public static Material SteelMagnetic = GTCoreMaterials.SteelMagnetic;
    public static Material SterlingSilver = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sterling_silver", 0xfadce1, SHINY));
    public static Material StainlessSteel = GTCoreMaterials.StainlessSteel;
    public static Material TinAlloy = GTAPI.register(Material.class, new Material(GT5RRef.ID, "tin_alloy", 0x9fadbb, NONE));
    public static Material Trinitanium = GTAPI.register(Material.class, new Material(GT5RRef.ID, "trinitanium", 0xEBAFFF, METALLIC));
    public static Material TritaniumAlloy = GTAPI.register(Material.class, new Material(GT5RRef.ID, "tritanium_alloy", 0x379B9B, SHINY));
    public static Material TungstenCarbide = GTCoreMaterials.TungstenCarbide;
    public static Material TungstenSteel = GTCoreMaterials.TungstenSteel;
    public static Material Ultimet = GTAPI.register(Material.class, new Material(GT5RRef.ID, "ultimet", 0xb4b4e6, SHINY));
    public static Material VanadiumGallium = GTAPI.register(Material.class, new Material(GT5RRef.ID, "vanadium_gallium", 0x80808c, SHINY));
    public static Material VanadiumSteel = GTAPI.register(Material.class, new Material(GT5RRef.ID, "vanadium_steel", 0xc0c0c0, METALLIC));
    public static Material Vibranium = GTAPI.register(Material.class, new Material(GT5RRef.ID, "vibranium", 0x00ffff, SHINY));
    public static Material WroughtIron = GTCoreMaterials.WroughtIron;
    public static Material YttriumBariumCuprate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "yttrium_barium_cuprate", 0x504046, METALLIC));

    public static Material Signalum = GTCoreMaterials.Signalum;
    public static Material Lumium = GTCoreMaterials.Lumium;
    public static Material Enderium = GTCoreMaterials.Enderium;

    public static Material Ironwood = GTCoreMaterials.Ironwood;
    public static Material Steeleaf = GTCoreMaterials.Steeleaf;
    public static Material Knightmetal = GTCoreMaterials.Knightmetal;
    public static Material FierySteel = GTCoreMaterials.FierySteel;

    /**
     ** Dusts
     **/

    public static Material AluminiumHydroxide = GTAPI.register(Material.class, new Material(GT5RRef.ID,"aluminium_hydroxide", 0xbebec8, DULL));
    public static Material AluminiumTrichloride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "aluminium_trichloride", 0xf0d77d, FINE));
    public static Material Aluminosilicate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "aluminosilicate", 0xbfbdb0, FINE));
    public static Material AmmoniumChloride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "ammonium_chloride", 0xffffff, DULL));
    public static Material AntimonyTrioxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "antimony_trioxide", 0xe6e6f0, DULL));
    public static Material ArsenicTrioxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "arsenic_trioxide", 0xffffff, SHINY));
    public static Material Ash = GTAPI.register(Material.class, new Material(GT5RRef.ID, "ash", 0x969696, DULL));
    public static Material BariumCarbonate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "barium_carbonate", 0xffffff, NONE));
    public static Material BenzoylChloride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "benzoyl_chloride", 0xf7f5eb, NONE));
    public static Material Biotite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "biotite", 0x141e14, METALLIC));
    public static Material Bitumen = GTAPI.register(Material.class, new Material(GT5RRef.ID, "bitumen", 0x585863, ROUGH));
    public static Material BorosilicateGlass = GTAPI.register(Material.class, new Material(GT5RRef.ID, "borosilicate_glass", 0xfafafa, NONE));
    public static Material Brick = GTCoreMaterials.Brick;
    public static Material Calcite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "calcite", 0xfae6dc, DULL));
    public static Material CalciumChloride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "calcium_chloride", 0xebebfa, DULL));
    public static Material CalciumSulfate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "calcium_sulfate", 0xf0dcd2, DULL));
    public static Material Clay = GTCoreMaterials.Clay;
    public static Material CobaltOxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "cobalt_oxide", 0x668000, DULL));
    public static Material Concrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "concrete", 0x646464, ROUGH));
    public static Material CupricOxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "cupric_oxide", 0x0f0f0f, DULL));
    public static Material DarkAsh = GTAPI.register(Material.class, new Material(GT5RRef.ID, "dark_ash", 0x323232, DULL)).setMassMultiplierAndDivider(2, 1);
    public static Material DialuminiumTrioxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "dialuminium_trioxide", 0xfaf6e6, FINE));
    public static Material Dibenzene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "dibenzene", 0xfaf0c8, FINE));
    public static Material DibenzoylPeroxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "dibenzoyl_peroxide", 0xf7f5eb, FINE));
    public static Material Dichloroethane = GTAPI.register(Material.class, new Material(GT5RRef.ID, "dichloroethane", 0xf8f6fc, NONE));
    public static Material Energium = GTCoreMaterials.Energium;
    public static Material FerricChloride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "ferric_chloride", 0xb4b478, METALLIC));
    public static Material FerriteMixture = GTAPI.register(Material.class, new Material(GT5RRef.ID, "ferrite_mixture", 0xb4b4b4, METALLIC));
    public static Material Ferrosilite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "ferrosilite", 0x97632a, DULL));
    public static Material Fireclay = GTCoreMaterials.Fireclay;
    public static Material Fluorite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "fluorite", 0xFFB98C, NONE));
    public static Material GelledToluene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "gelled_toluene", 0xeeeeee, NONE));
    public static Material Graphene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "graphene", 0x808080, DULL));
    public static Material IndiumGalliumPhosphide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "indium_gallium_phosphide", 0x570b79, NONE));
    public static Material IridiumSodiumOxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "iridium_sodium_oxide", 0xffffff, NONE));
    public static Material Lapotronium = GTCoreMaterials.Lapotronium;
    public static Material LithiumChloride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "lithium_chloride", 0xdedefa, DULL));
    public static Material MagnesiumCarbonate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "magnesium_carbonate", 0xF0E6E6, DULL));
    public static Material MagnesiumChloride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "magnesium_chloride", 0xd40d5c, DULL));
    public static Material Massicot = GTAPI.register(Material.class, new Material(GT5RRef.ID, "massicot", 0xffdd55, DULL));
    public static Material Obsidian = GTAPI.register(Material.class, new Material(GT5RRef.ID, "obsidian", 0x503264, DULL));
    public static Material PhosphorousPentoxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "phosphorous_pentoxide", 0xdcdc00, NONE));
    public static Material PlatinumGroupSludge = GTAPI.register(Material.class, new Material(GT5RRef.ID, "platinum_group_sludge", 0x001e00, NONE));
    public static Material Polydimethylsiloxane = GTAPI.register(Material.class, new Material(GT5RRef.ID, "polydimethylsiloxane", 0xf5f5f5, NONE));
    public static Material PotassiumBisulfate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "potassium_bisulfate", 0xf0f0ff, NONE));
    public static Material PotassiumFeldspar = GTAPI.register(Material.class, new Material(GT5RRef.ID, "potassium_feldspar", 0x782828, FINE));
    public static Material Quicklime = GTAPI.register(Material.class, new Material(GT5RRef.ID, "quicklime", 0xf0f0f0, DULL));
    public static Material RareEarth = GTAPI.register(Material.class, new Material(GT5RRef.ID, "rare_earth", 0x808064, FINE));
    public static Material RawRubber = GTAPI.register(Material.class, new Material(GT5RRef.ID, "raw_rubber", 0xccc789, DULL));
    public static Material RawStyreneButadieneRubber = GTAPI.register(Material.class, new Material(GT5RRef.ID, "raw_styrene_butadiene_rubber", 0x54403d, SHINY));
    public static Material ReactionCatalyst = GTAPI.register(Material.class, new Material(GT5RRef.ID, "reaction_catalyst", 0x43ab43, NONE));
    public static Material SiliconDioxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "silicon_dioxide", 0xc8c8c8, QUARTZ));
    public static Material SodaAsh = GTAPI.register(Material.class, new Material(GT5RRef.ID, "soda_ash", 0xdcdcff, DULL));
    public static Material SodiumAluminate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sodium_aluminate", 0xE6E6FA, NONE));
    public static Material SodiumBisulfate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sodium_bisulfate", 0x004455, NONE));
    public static Material SodiumPersulfate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sodium_persulfate", 0x82b4fa, NONE));
    public static Material SodiumHydroxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sodium_hydroxide", 0x003380, DULL));
    public static Material SodiumSulfate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sodium_sulfate", 0x004455, NONE));
    public static Material SodiumSulfide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sodium_sulfide", 0xffe680, NONE));
    public static Material TricalciumPhosphate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "tricalcium_phosphate", 0xffff00, DULL));
    public static Material TungsticAcid = GTAPI.register(Material.class, new Material(GT5RRef.ID, "tungstic_acid", 0xb4c800, SHINY));
    public static Material TungstenTrioxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "tungsten_trioxide", 0xc7d300, DULL));
    public static Material Wollastonite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "wollastonite", 0xf0f0f0, DULL));
    public static Material YttriumOxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "yttrium_oxide", 0xffffff, NONE));

    /**
     ** Ores
     **/

    public static Material Adamantine = GTAPI.register(Material.class, new Material(GT5RRef.ID, "adamantine", 0xff0040, METALLIC));
    public static Material Almandine = GTAPI.register(Material.class, new Material(GT5RRef.ID, "almandine", 0xff0000, ROUGH));
    public static Material Alumina = GTAPI.register(Material.class, new Material(GT5RRef.ID, "alumina", 0x78C3EB, METALLIC));
    public static Material Andradite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "andradite", 0x967800, ROUGH));
    public static Material Hematite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hematite", 0x915a5a, DULL));
    public static Material Barite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "barite", 0xe6ebff, DULL));
    public static Material Bastnasite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "bastnasite", 0xc86e2d, FINE));
    public static Material Bentonite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "bentonite", 0xf5d7d2, ROUGH)); // TODO: Ore Gen
    public static Material BrownLimonite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "brown_limonite", 0xc86400, METALLIC));
    public static Material Cassiterite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "cassiterite", 0xdcdcdc, METALLIC)).setMassMultiplierAndDivider(3, 1);
    public static Material Chalcopyrite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chalcopyrite", 0xa07828, DULL));
    public static Material Chromite = GTAPI.register(Material.class, new Material(Ref.ID, "chromite", 0x23140F, DULL));
    public static Material Cobaltite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "cobaltite", 0x5050fa, METALLIC));
    public static Material Galena = GTAPI.register(Material.class, new Material(GT5RRef.ID, "galena", 0x643c64, DULL));
    public static Material Garnierite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "garnierite", 0x32c846, METALLIC));
    public static Material Glauconite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "glauconite", 0x82b43c, DULL)); // TODO: Ore Gen;
    public static Material Graphite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "graphite", 0x808080, DULL));
    public static Material Grossular = GTAPI.register(Material.class, new Material(GT5RRef.ID, "grossular", 0xc86400, ROUGH));
    public static Material Ilmenite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "ilmenite", 0x463732, METALLIC)).setMassMultiplierAndDivider(2, 1);
    public static Material Lepidolite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "lepidolite", 0xf0328c, FINE)); // TODO: Ore Gen;
    public static Material Magnesite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "magnesite", 0xfafab4, METALLIC));
    public static Material Magnetite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "magnetite", 0x1e1e1e, METALLIC));
    public static Material Malachite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "malachite", 0x055f05, DULL));
    public static Material Molybdenite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "molybdenite", 0x91919, METALLIC));
    public static Material Pentlandite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "pentlandite", 0xa59605, DULL));
    public static Material Phosphate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "phosphate", 0xffff00, DULL));
    public static Material Pitchblende = GTAPI.register(Material.class, new Material(GT5RRef.ID, "pitchblende", 0xc8d200, DULL));
    public static Material Powellite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "powellite", 0xffff00, DULL));
    public static Material Pyrite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "pyrite", 0x967828, ROUGH));
    // public static Material Pyrochlore = GTAPI.register(Material.class, new Material(GT5RRef.ID, "pyrochlore", 0x2b1100,METALLIC));
    public static Material Pyrolusite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "pyrolusite", 0x9696aa, DULL));
    public static Material Pyrope = GTAPI.register(Material.class, new Material(GT5RRef.ID, "pyrope", 0x783264, METALLIC));
    public static Material Rutile = GTAPI.register(Material.class, new Material(GT5RRef.ID, "rutile", 0xd40d5c, GEM_H)).setMassMultiplierAndDivider(2, 1);
    public static Material Saltpeter = GTAPI.register(Material.class, new Material(GT5RRef.ID, "saltpeter", 0xe6e6e6, FINE));
    public static Material Scheelite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "scheelite", 0xc88c14, DULL));
    public static Material Sheldonite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sheldonite", 0xffffc8, METALLIC));
    public static Material Soapstone = GTAPI.register(Material.class, new Material(GT5RRef.ID, "soapstone", 0x5f915f, DULL)); // TODO: Ore Gen;
    public static Material Sperrylite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sperrylite", 0x696969, SHINY));
    public static Material Spessartine = GTAPI.register(Material.class, new Material(GT5RRef.ID, "spessartine", 0xff6464, DULL));
    public static Material Sphalerite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sphalerite", 0xdede00, DULL));
    public static Material Spodumene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "spodumene", 0xbeaaaa, DULL));
    public static Material Stibnite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "stibnite", 0x464646, METALLIC));
    public static Material Talc = GTAPI.register(Material.class, new Material(GT5RRef.ID, "talc", 0x5ab45a, DULL));
    public static Material Tantalite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "tantalite", 0x915028, METALLIC));
    public static Material Tetrahedrite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "tetrahedrite", 0xc82000, DULL));
    public static Material Wulfenite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "wulfenite", 0xff8000, DULL));
    public static Material VanadiumMagnetite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "vanadium_magnetite", 0x23233c, METALLIC));
    public static Material Tungstate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "tungstate", 0x373223, DULL));
    public static Material Uraninite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "uraninite", 0x232323, METALLIC));
    public static Material Uvarovite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "uvarovite", 0xb4ffb4, DIAMOND));
    public static Material YellowLimonite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "yellow_limonite", 0xc8c800, METALLIC));
    public static Material Zircon = GTAPI.register(Material.class, new Material(GT5RRef.ID, "zircon", 0x63181d, DIAMOND));

    /**
     ** Ore Stones
     **/

    public static Material Bauxite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "bauxite", 0xc86400, DULL));
    public static Material Lignite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "lignite_coal", 0x644646, LIGNITE));
    public static Material OilShale = GTAPI.register(Material.class, new Material(GT5RRef.ID, "oil_shale", 0x32323c, NONE));
    public static Material Salt = GTAPI.register(Material.class, new Material(GT5RRef.ID, "salt", 0xfafafa, CUBE));
    public static Material IodineSalt = GTAPI.register(Material.class, new Material(GT5RRef.ID, "iodine_salt", 0xf0c8f0, CUBE));
    public static Material Sylvite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sylvite", 0xf0c8c8, CUBE));

    /**
     ** Gems
     **/

    public static Material Apatite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "apatite", 0x78B4FA, DIAMOND));
    public static Material Amber = GTAPI.register(Material.class, new Material(GT5RRef.ID, "amber", 0xFFB400, RUBY));
    public static Material Amethyst = GTAPI.register(Material.class, new Material(GT5RRef.ID, "amethyst", 0xd232d2, RUBY));
    public static Material Sapphire = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sapphire", 0x6464c8, GEM_V));
    public static Material BlueTopaz = GTAPI.register(Material.class, new Material(GT5RRef.ID, "blue_topaz", 0x0000ff, GEM_H));
    public static Material MilkyQuartz = GTAPI.register(Material.class, new Material(GT5RRef.ID, "milky_quartz", 0xd2d2d2, QUARTZ));
    public static Material CertusQuartz = GTAPI.register(Material.class,new Material(GT5RRef.ID, "certus_quartz", 0xd2d2e6, QUARTZ, Ref.MOD_AE));
    public static Material ChargedCertusQuartz = GTAPI.register(Material.class,new Material(GT5RRef.ID, "charged_certus_quartz", 0xd2d2e6, QUARTZ, Ref.MOD_AE));
    public static Material Fluix = GTAPI.register(Material.class, new Material(GT5RRef.ID, "fluix", 0x78468C, QUARTZ, Ref.MOD_AE));
    public static Material CoalCoke = GTAPI.register(Material.class, new Material(GT5RRef.ID, "coal_coke", 0x8c8caa, LIGNITE));
    public static Material Glass = GTCoreMaterials.Glass;
    public static Material GreenSapphire = GTAPI.register(Material.class, new Material(GT5RRef.ID, "green_sapphire", 0x64c882, GEM_H));
    public static Material Jade = GTAPI.register(Material.class, new Material(GT5RRef.ID, "jade", 0x64ff7d, LAPIS));
    public static Material Lazurite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "lazurite", 0x6478ff, LAPIS));
    public static Material LigniteCoke = GTAPI.register(Material.class, new Material(GT5RRef.ID, "lignite_coke", 0x8c6464, LIGNITE));
    public static Material Monazite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "monazite", 0x324632, DIAMOND));
    public static Material Olivine = GTAPI.register(Material.class, new Material(GT5RRef.ID, "olivine", 0x96ff96, RUBY));
    public static Material Opal = GTAPI.register(Material.class, new Material(GT5RRef.ID, "opal", 0x0000ff, RUBY));
    public static Material RedGarnet = GTAPI.register(Material.class, new Material(GT5RRef.ID, "red_garnet", 0xc85050, GARNET));
    public static Material Ruby = GTAPI.register(Material.class, new Material(GT5RRef.ID, "ruby", 0xff6464, RUBY));
    public static Material Sodalite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sodalite", 0x1414ff, LAPIS));
    public static Material Tanzanite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "tanzanite", 0x4000c8, GEM_V));
    public static Material Topaz = GTAPI.register(Material.class, new Material(GT5RRef.ID, "topaz", 0xff8000, GEM_H));
    public static Material YellowGarnet = GTAPI.register(Material.class, new Material(GT5RRef.ID, "yellow_garnet", 0xc8c850, GARNET));

    public static Material PetroleumCoke = GTAPI.register(Material.class, new Material(GT5RRef.ID, "petroleum_coke", 0x9696b4, LIGNITE));

    /**
     ** Plastic / Rubber
     **/

    public static Material EpoxyResin = GTAPI.register(Material.class, new Material(GT5RRef.ID, "epoxy_resin", 0xc88c14, DULL));
    public static Material FiberReinforcedEpoxyResin = GTAPI.register(Material.class, new Material(GT5RRef.ID, "fiber_reinforced_epoxy_resin", 0xa07010, DULL));
    public static Material Plastic = GTCoreMaterials.Plastic.setDisplayNameString("Plastic (Polyethylene)");
    public static Material Polystyrene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "polystyrene", 0xbeb4aa, DULL)).setDisplayNameString("Plastic (Polystyrene)");
    public static Material Polytetrafluoroethylene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "polytetrafluoroethylene", 0x646464, DULL)).setDisplayNameString("Teflon (Polytetrafluoroethylene)");
    public static Material PolyvinylChloride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "polyvinyl_chloride", 0xd7e6e6, NONE)).setDisplayNameString("Plastic (Polyvinyl Chloride)");
    public static Material Rubber = GTCoreMaterials.Rubber;
    public static Material SiliconeRubber = GTAPI.register(Material.class, new Material(GT5RRef.ID, "silicone_rubber", 0x9fadbb, NONE));
    public static Material StyreneButadieneRubber = GTAPI.register(Material.class, new Material(GT5RRef.ID, "styrene_butadiene_rubber", 0x211a18, SHINY));

    /**
     ** Stones
     **/
    //Vanilla Stone Materials
    public static Material Stone = GTLibMaterials.Stone;
    public static Material Granite = GTLibMaterials.Granite;
    public static Material Diorite = GTLibMaterials.Diorite;
    public static Material Andesite = GTLibMaterials.Andesite;
    public static Material Deepslate = GTLibMaterials.Deepslate;
    public static Material Tuff = GTLibMaterials.Tuff;
    public static Material Dirt = GTLibMaterials.Dirt;
    public static Material Sand = GTLibMaterials.Sand;
    public static Material RedSand = GTLibMaterials.RedSand;
    public static Material Blackstone = GTLibMaterials.Blackstone;
    public static Material Endstone = GTLibMaterials.Endstone;
    public static Material Netherrack = GTLibMaterials.Netherrack;
    public static Material Bedrock = GTLibMaterials.Bedrock;
    public static Material Prismarine = GTLibMaterials.Prismarine;
    public static Material DarkPrismarine = GTLibMaterials.DarkPrismarine;
    public static Material Basalt = GTCoreMaterials.Basalt;
    public static Material BlackGranite = GTCoreMaterials.BlackGranite;
    public static Material BlueSchist = GTCoreMaterials.BlueSchist;
    public static Material GreenSchist = GTCoreMaterials.GreenSchist;
    public static Material Kimberlite = GTCoreMaterials.Kimberlite;
    public static Material Komatiite = GTCoreMaterials.Komatiite;
    public static Material Limestone = GTCoreMaterials.Limestone;
    public static Material Marble = GTCoreMaterials.Marble;
    public static Material Quartzite = GTCoreMaterials.Quartzite;
    public static Material RedGranite = GTCoreMaterials.RedGranite;
    public static Material Shale = GTCoreMaterials.Shale;
    public static Material Slate = GTCoreMaterials.Slate;

    /**
     ** Misc
     **/

    public static Material Cinnabar = GTAPI.register(Material.class, new Material(GT5RRef.ID, "cinnabar", 0x960000, ROUGH));
    public static Material GalliumArsenide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "gallium_arsenide", 0xa0a0a0, DULL));
    public static Material Superconductor = GTAPI.register(Material.class, new Material(GT5RRef.ID, "superconductor", 0xffffff, NONE));

    /**
     *** Fluids
     **/

    /**
     * Dye Fluids
     */
    public static Material WaterMixedWhiteDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_white_dye", GT5RData.getColorFromDyeColor(DyeColor.WHITE), NONE));
    public static Material WaterMixedOrangeDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_orange_dye", GT5RData.getColorFromDyeColor(DyeColor.ORANGE), NONE));
    public static Material WaterMixedMagentaDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_magenta_dye", GT5RData.getColorFromDyeColor(DyeColor.MAGENTA), NONE));
    public static Material WaterMixedLightBlueDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_light_blue_dye", GT5RData.getColorFromDyeColor(DyeColor.LIGHT_BLUE), NONE));
    public static Material WaterMixedYellowDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_yellow_dye", GT5RData.getColorFromDyeColor(DyeColor.YELLOW), NONE));
    public static Material WaterMixedLimeDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_lime_dye", GT5RData.getColorFromDyeColor(DyeColor.LIME), NONE));
    public static Material WaterMixedPinkDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_pink_dye", GT5RData.getColorFromDyeColor(DyeColor.PINK), NONE));
    public static Material WaterMixedGrayDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_gray_dye", GT5RData.getColorFromDyeColor(DyeColor.GRAY), NONE));
    public static Material WaterMixedLightGrayDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_light_gray_dye", GT5RData.getColorFromDyeColor(DyeColor.LIGHT_GRAY), NONE));
    public static Material WaterMixedCyanDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_cyan_dye", GT5RData.getColorFromDyeColor(DyeColor.CYAN), NONE));
    public static Material WaterMixedPurpleDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_purple_dye", GT5RData.getColorFromDyeColor(DyeColor.PURPLE), NONE));
    public static Material WaterMixedBlueDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_blue_dye", GT5RData.getColorFromDyeColor(DyeColor.BLUE), NONE));
    public static Material WaterMixedBrownDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_brown_dye", GT5RData.getColorFromDyeColor(DyeColor.BROWN), NONE));
    public static Material WaterMixedGreenDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_green_dye", GT5RData.getColorFromDyeColor(DyeColor.GREEN), NONE));
    public static Material WaterMixedRedDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_red_dye", GT5RData.getColorFromDyeColor(DyeColor.RED), NONE));
    public static Material WaterMixedBlackDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "water_mixed_black_dye", GT5RData.getColorFromDyeColor(DyeColor.BLACK), NONE));

    public static Material ChemicalWhiteDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_white_dye", GT5RData.getColorFromDyeColor(DyeColor.WHITE), NONE));
    public static Material ChemicalOrangeDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_orange_dye", GT5RData.getColorFromDyeColor(DyeColor.ORANGE), NONE));
    public static Material ChemicalMagentaDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_magenta_dye", GT5RData.getColorFromDyeColor(DyeColor.MAGENTA), NONE));
    public static Material ChemicalLightBlueDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_light_blue_dye", GT5RData.getColorFromDyeColor(DyeColor.LIGHT_BLUE), NONE));
    public static Material ChemicalYellowDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_yellow_dye", GT5RData.getColorFromDyeColor(DyeColor.YELLOW), NONE));
    public static Material ChemicalLimeDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_lime_dye", GT5RData.getColorFromDyeColor(DyeColor.LIME), NONE));
    public static Material ChemicalPinkDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_pink_dye", GT5RData.getColorFromDyeColor(DyeColor.PINK), NONE));
    public static Material ChemicalGrayDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_gray_dye", GT5RData.getColorFromDyeColor(DyeColor.GRAY), NONE));
    public static Material ChemicalLightGrayDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_light_gray_dye", GT5RData.getColorFromDyeColor(DyeColor.LIGHT_GRAY), NONE));
    public static Material ChemicalCyanDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_cyan_dye", GT5RData.getColorFromDyeColor(DyeColor.CYAN), NONE));
    public static Material ChemicalPurpleDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_purple_dye", GT5RData.getColorFromDyeColor(DyeColor.PURPLE), NONE));
    public static Material ChemicalBlueDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_blue_dye", GT5RData.getColorFromDyeColor(DyeColor.BLUE), NONE));
    public static Material ChemicalBrownDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_brown_dye", GT5RData.getColorFromDyeColor(DyeColor.BROWN), NONE));
    public static Material ChemicalGreenDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_green_dye", GT5RData.getColorFromDyeColor(DyeColor.GREEN), NONE));
    public static Material ChemicalRedDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_red_dye", GT5RData.getColorFromDyeColor(DyeColor.RED), NONE));
    public static Material ChemicalBlackDye = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chemical_black_dye", GT5RData.getColorFromDyeColor(DyeColor.BLACK), NONE));

    public static Material WhiteConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "white_concrete", GT5RData.getColorFromDyeColor(DyeColor.WHITE), NONE));
    public static Material OrangeConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "orange_concrete", GT5RData.getColorFromDyeColor(DyeColor.ORANGE), NONE));
    public static Material MagentaConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "magenta_concrete", GT5RData.getColorFromDyeColor(DyeColor.MAGENTA), NONE));
    public static Material LightBlueConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "light_blue_concrete", GT5RData.getColorFromDyeColor(DyeColor.LIGHT_BLUE), NONE));
    public static Material YellowConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "yellow_concrete", GT5RData.getColorFromDyeColor(DyeColor.YELLOW), NONE));
    public static Material LimeConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "lime_concrete", GT5RData.getColorFromDyeColor(DyeColor.LIME), NONE));
    public static Material PinkConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "pink_concrete", GT5RData.getColorFromDyeColor(DyeColor.PINK), NONE));
    public static Material GrayConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "gray_concrete", GT5RData.getColorFromDyeColor(DyeColor.GRAY), NONE));
    public static Material LightGrayConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "light_gray_concrete", GT5RData.getColorFromDyeColor(DyeColor.LIGHT_GRAY), NONE));
    public static Material CyanConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "cyan_concrete", GT5RData.getColorFromDyeColor(DyeColor.CYAN), NONE));
    public static Material PurpleConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "purple_concrete", GT5RData.getColorFromDyeColor(DyeColor.PURPLE), NONE));
    public static Material BlueConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "blue_concrete", GT5RData.getColorFromDyeColor(DyeColor.BLUE), NONE));
    public static Material BrownConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "brown_concrete", GT5RData.getColorFromDyeColor(DyeColor.BROWN), NONE));
    public static Material GreenConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "green_concrete", GT5RData.getColorFromDyeColor(DyeColor.GREEN), NONE));
    public static Material RedConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "red_concrete", GT5RData.getColorFromDyeColor(DyeColor.RED), NONE));
    public static Material BlackConcrete = GTAPI.register(Material.class, new Material(GT5RRef.ID, "black_concrete", GT5RData.getColorFromDyeColor(DyeColor.BLACK), NONE));

    /**
     ** Organic
     **/

    public static Material AceticAcid = GTAPI.register(Material.class, new Material(GT5RRef.ID, "acetic_acid", 0xc8b4a0, NONE));
    public static Material Acetone = GTAPI.register(Material.class, new Material(GT5RRef.ID, "acetone", 0xafafaf, NONE));
    public static Material AllylChloride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "allyl_chloride", 0x87deaa, NONE));
    public static Material Benzaldehyde = GTAPI.register(Material.class, new Material(GT5RRef.ID, "benzaldehyde", 0xf7dea3, NONE));
    public static Material Benzene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "benzene", 0x1a1a1a, NONE));
    public static Material Biomass = GTAPI.register(Material.class, new Material(GT5RRef.ID, "biomass", 0x00ff00, NONE));
    public static Material BisphenolA = GTAPI.register(Material.class, new Material(GT5RRef.ID, "bisphenol_a", 0xd4b300, NONE));
    public static Material Butanediol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "butanediol", 0xff8000, NONE));
    public static Material Butanol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "butanol", 0xff8000, NONE));
    public static Material Butenol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "butenol", 0xff8000, NONE));
    public static Material CharcoalByproducts = GTAPI.register(Material.class, new Material(GT5RRef.ID, "charcoal_byproducts", 0x784421, NONE));
    public static Material Chloramine = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chloramine", 0x3f9f80, NONE));
    public static Material Chloroform = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chloroform", 0x892ca0, NONE));
    public static Material Cumene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "cumene", 0x552200, NONE));
    public static Material Chlorobenzene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chlorobenzene", 0x004455, NONE));
    public static Material Dimethyldichlorosilane = GTAPI.register(Material.class, new Material(GT5RRef.ID, "dimethyldichlorosilane", 0x441650, NONE));
    public static Material Dimethylhydrazine = GTAPI.register(Material.class, new Material(GT5RRef.ID, "dimethylhydrazine", 0x000055, NONE));
    public static Material Epichlorohydrin = GTAPI.register(Material.class, new Material(GT5RRef.ID, "epichlorohydrin", 0x501d05, NONE));
    public static Material Ethanediol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "ethanediol", 0xff8000, NONE));
    public static Material Ethanol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "ethanol", 0xff8000, NONE));
    public static Material Ethenol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "ethenol", 0xff8000, NONE));
    public static Material FermentedBiomass = GTAPI.register(Material.class, new Material(GT5RRef.ID, "fermented_biomass", 0x09964a, NONE));
    public static Material Glue = GTAPI.register(Material.class, new Material(GT5RRef.ID, "glue", 0xc8c400, NONE));
    public static Material Heptanol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "heptanol", 0xff8000, NONE));
    public static Material Honey = GTAPI.register(Material.class, new Material(GT5RRef.ID, "honey", 0xd2c800, NONE)); // TODO: Only when Forestry's present;
    public static Material Isoprene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "isoprene", 0x141414, NONE));
    public static Material Methanol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "methanol", 0xaa8800, NONE));
    public static Material MethylAcetate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "methyl_acetate", 0xeec6af, NONE));
    public static Material Naphtha = GTAPI.register(Material.class, new Material(GT5RRef.ID, "naphtha", 0xffff00, NONE));
    public static Material Phenol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "phenol", 0x784421, NONE));
    public static Material PhosphoricAcid = GTAPI.register(Material.class, new Material(GT5RRef.ID, "phosphoric_acid", 0xdcdc00, NONE));
    public static Material PolyvinylAcetate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "polyvinyl_acetate", 0xff9955, NONE));
    public static Material Propanediol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "propanediol", 0xff8000, NONE));
    public static Material Propenol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "propenol", 0xff8000, NONE));
    public static Material Propanol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "propanol", 0xff8000, NONE));
    public static Material SquidInk = GTAPI.register(Material.class, new Material(GT5RRef.ID, "squid_ink", 0x000000, NONE));
    public static Material Styrene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "styrene", 0xd2c8be, NONE));
    public static Material SulfuricNaphtha = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sulfuric_naphtha", 0xffff00, NONE));
    public static Material Toluene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "toluene", 0x501d05, NONE));
    public static Material VinylAcetate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "vinyl_acetate", 0xffb380, NONE));
    public static Material WoodTar = GTAPI.register(Material.class, new Material(GT5RRef.ID, "wood_tar", 0x28170b, NONE));
    public static Material WoodVinegar = GTAPI.register(Material.class, new Material(GT5RRef.ID, "wood_vinegar", 0xd45500, NONE));

    /**
     ** Inorganic
     **/

    public static Material AluminiumFluoride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "aluminium_fluoride", 0xc8bebe, NONE));
    public static Material Antimatter = GTAPI.register(Material.class, new Material(GT5RRef.ID, "anti_matter", 0x8000c4, NONE));
    public static Material BlueVitriol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "blue_vitriol", 0x4242DE, NONE));
    public static Material GreenVitriol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "green_vitriol", 0x42de42, NONE));
    public static Material RedVitriol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "red_vitriol", 0xde4242, NONE));
    public static Material PinkVitriol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "pink_vitriol", 0xde6f6f, NONE));
    public static Material CyanVitriol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "cyan_vitriol", 0x6fdede, NONE));
    public static Material WhiteVitriol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "white_vitriol", 0xdedede, NONE));
    public static Material GrayVitriol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "gray_vitriol", 0x6f6f6f, NONE));
    public static Material MartianVitriol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "martian_vitriol", 0xde42de, NONE));
    public static Material VitriolOfClay = GTAPI.register(Material.class, new Material(GT5RRef.ID, "vitriol_of_clay", 0x42dede, NONE));


    public static Material AquaRegia = GTAPI.register(Material.class, new Material(GT5RRef.ID, "aqua_regia", 0x40ff40, NONE));
    public static Material CalciumAcetateSolution = GTAPI.register(Material.class, new Material(GT5RRef.ID, "calcium_acetate_solution", 0xDCC8B4, NONE));
    public static Material ChloroplatinicAcid = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chloroplatinic_acid", 0xff4646, NONE));
    public static Material Coolant = GTAPI.register(Material.class, new Material(GT5RRef.ID, "coolant", 0x0506be, NONE));
    public static Material Cryolite = GTAPI.register(Material.class, new Material(GT5RRef.ID, "cryolite", 0xc8bebe, NONE));
    public static Material DilutedHydrochloricAcid = GTAPI.register(Material.class, new Material(GT5RRef.ID, "diluted_hydrochloric_acid", 0x99a7a3, NONE));
    public static Material DilutedSulfuricAcid = GTAPI.register(Material.class, new Material(GT5RRef.ID, "diluted_sulfuric_acid", 0xc07820, NONE));
    public static Material DistilledWater = GTAPI.register(Material.class, new Material(GT5RRef.ID, "distilled_water", 0x5C5CFF, NONE));
    public static Material DrillingFluid = GTAPI.register(Material.class, new Material(GT5RRef.ID, "drilling_fluid", 0xffffff, NONE)); // TODO: Perhaps for a bedrock drill;
    public static Material HexafluorosilicicAcid = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hexafluorosilicic_acid", 0xbec8be, NONE));
    public static Material HotCoolant = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hot_coolant", 0x7a111a, NONE));
    public static Material HeavyWater = GTAPI.register(Material.class, new Material(GT5RRef.ID, "heavy_water", 0xffff64, NONE));
    public static Material SemiheavyWater = GTAPI.register(Material.class, new Material(GT5RRef.ID, "semiheavy_water", 0xc8c89b, NONE));
    public static Material TritiatedWater = GTAPI.register(Material.class, new Material(GT5RRef.ID, "tritiated_water", 0xff6464, NONE));
    public static Material HotHeavyWater = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hot_heavy_water", 0xffff64, NONE));
    public static Material HotSemiheavyWater = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hot_semiheavy_water", 0xc8c89b, NONE));
    public static Material HotTritiatedWater = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hot_tritiated_water", 0xff6464, NONE));
    public static Material HotMoltenSodium = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hot_molten_sodium", 0x000096, NONE, S));
    public static Material HotMoltenTin = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hot_molten_tin", 0xdcdcdc, NONE, Sn));
    public static Material HotMoltenLithiumChloride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hot_molten_lithium_chloride", 0xdedefa, NONE));
    public static Material HydrochloricAcid = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hydrochloric_acid", 0x6f8a91, NONE));
    public static Material HydrofluoricAcid = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hydrofluoric_acid", 0x0088aa, NONE));
    public static Material HydrogenFluoride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hydrogen_fluoride", 0x00f0f0, NONE));
    public static Material HydrogenPeroxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hydrogen_peroxide", 0xf8efb4, NONE));
    public static Material HypochlorousAcid = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hypochlorous_acid", 0x6f8a91, NONE));
    public static Material Lubricant = GTAPI.register(Material.class, new Material(GT5RRef.ID, "lubricant", 0xffc400, NONE));
    public static Material NickelSulfate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "nickel_sulfate", 0xffffff, NONE));
    public static Material NitricAcid = GTAPI.register(Material.class, new Material(GT5RRef.ID, "nitric_acid", 0xe6e2ab, NONE));
    public static Material PeroxydisulfuricAcid = GTAPI.register(Material.class, new Material(GT5RRef.ID, "peroxydisulfuricacid", 0xff9000, NONE));
    public static Material SaltWater = GTAPI.register(Material.class, new Material(GT5RRef.ID, "salt_water", 0x0760b9, NONE));
    public static Material SodiumPersulfateSolution = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sodium_perfulate_solution", 0x006646, NONE));
    public static Material SulfuricAcid = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sulfuric_acid", 0xff8000, NONE));
    public static Material Tar = GTAPI.register(Material.class, new Material(GT5RRef.ID, "tar", 0x0a0a0a, NONE));
    public static Material ThoriumSalt = GTAPI.register(Material.class, new Material(GT5RRef.ID, "thorium_salt", 0x001e00, NONE));
    public static Material TitaniumTetrachloride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "titanium_tetrachloride", 0xd40d5c, NONE));
    public static Material UUAmplifier = GTAPI.register(Material.class, new Material(GT5RRef.ID, "uu_amplifier", 0x600080, NONE));
    public static Material UUMatter = GTAPI.register(Material.class, new Material(GT5RRef.ID, "uu_matter", 0x8000c4, NONE));

    /**
     ** Fuels
     **/

    public static Material BioDiesel = GTAPI.register(Material.class, new Material(GT5RRef.ID, "bio_diesel", 0xff8000, NONE));
    public static Material Creosote = GTAPI.register(Material.class, new Material(GT5RRef.ID, "creosote", 0x804000, NONE));
    public static Material Diesel = GTAPI.register(Material.class, new Material(GT5RRef.ID, "diesel", 0xffff00, NONE));
    public static Material FishOil = GTAPI.register(Material.class, new Material(GT5RRef.ID, "fish_oil", 0xffc400, NONE));
    public static Material NitroDiesel = GTAPI.register(Material.class, new Material(GT5RRef.ID, "nitro_diesel", 0xc8ff00, NONE));
    public static Material Kerosene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "kerosene", 0x0000FF, NONE));
    public static Material FuelOil = GTAPI.register(Material.class, new Material(GT5RRef.ID, "fuel", 0xffff00, NONE)).setDisplayNameString("Fuel Oil");
    public static Material Gasoline = GTAPI.register(Material.class, new Material(GT5RRef.ID, "gasoline", 0xFFA500, NONE));
    public static Material Oil = GTAPI.register(Material.class, new Material(GT5RRef.ID, "oil", 0x0a0a0a, NONE));
    public static Material OilHeavy = GTAPI.register(Material.class, new Material(GT5RRef.ID, "heavy_oil", 0x0a0a0a, NONE));
    public static Material OilLight = GTAPI.register(Material.class, new Material(GT5RRef.ID, "light_oil", 0x0a0a0a, NONE));
    public static Material SeedOil = GTAPI.register(Material.class, new Material(GT5RRef.ID, "seed_oil", 0xc4ff00, NONE));
    public static Material Glycerol = GTAPI.register(Material.class, new Material(GT5RRef.ID, "glycerol", 0x87de87, NONE));
    public static Material GlycerylTrinitrate = GTAPI.register(Material.class, new Material(GT5RRef.ID, "glyceryl_trinitrate", 0x87de87, NONE)).setDisplayNameString("Nitroglycerin (Glyceryl Trinitrate)");

    /**
     *** Gases/Plasmas
     **/

    /**
     ** Organic
     **/

    public static Material Butane = GTAPI.register(Material.class, new Material(GT5RRef.ID, "butane", 0xb6371e, NONE));
    public static Material Butadiene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "butadiene", 0xe86900, NONE));
    public static Material Butene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "butene", 0xcf5005, NONE));
    public static Material HotCarbonDioxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hot_carbon_dioxide", 0xa9d0f5, NONE));
    public static Material CarbonDioxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "carbon_dioxide", 0xa9d0f5, NONE));
    public static Material CarbonMonoxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "carbon_monoxide", 0x0e4880, NONE));
    public static Material Chloromethane = GTAPI.register(Material.class, new Material(GT5RRef.ID, "chloromethane", 0xc82ca0, NONE));
    public static Material Dimethylamine = GTAPI.register(Material.class, new Material(GT5RRef.ID, "dimethylamine", 0x554469, NONE));
    public static Material Ethane = GTAPI.register(Material.class, new Material(GT5RRef.ID, "ethane", 0xc8c8ff, NONE));
    public static Material Ethylene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "ethylene", 0xe1e1e1, NONE));
    public static Material LPG = GTAPI.register(Material.class, new Material(GT5RRef.ID, "lpg", 0xffff00, NONE)).setDisplayNameString("LPG");
    public static Material Methane = GTAPI.register(Material.class, new Material(GT5RRef.ID, "methane", 0xffffff, NONE));
    public static Material NaturalGas = GTAPI.register(Material.class, new Material(GT5RRef.ID, "natural_gas", 0xffffff, NONE));
    public static Material Propane = GTAPI.register(Material.class, new Material(GT5RRef.ID, "propane", 0xfae250, NONE));
    public static Material Propene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "propene", 0xffdd55, NONE));
    public static Material RefineryGas = GTAPI.register(Material.class, new Material(GT5RRef.ID, "refinery_gas", 0xffffff, NONE));
    public static Material SulfuricGas = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sulfuric_gas", 0xffffff, NONE));
    public static Material Tetrafluoroethylene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "tetrafluoroethylene", 0x7d7d7d, NONE));
    public static Material VinylChloride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "vinyl_chloride", 0xfff0f0, NONE));
    public static Material WoodGas = GTAPI.register(Material.class, new Material(GT5RRef.ID, "wood_gas", 0xdecd87, NONE));

    /**
     ** Inorganic
     **/

    public static Material Air = GTAPI.register(Material.class, new Material(GT5RRef.ID, "air", 0xc9e3fc, NONE));
    public static Material Ammonia = GTAPI.register(Material.class, new Material(GT5RRef.ID, "ammonia", 0x3f3480, NONE));
    public static Material DinitrogenTetroxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "dinitrogen_tetroxide", 0x004184, NONE));
    public static Material EnderAir = GTAPI.register(Material.class, new Material(GT5RRef.ID, "ender_air", 0x556fbc, NONE));
    public static Material HydrogenSulfide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hydrogen_sulfide", 0xffffff, NONE));
    public static Material NetherAir = GTAPI.register(Material.class, new Material(GT5RRef.ID, "nether_air", 0xad7070, NONE));
    public static Material NitrogenMonoxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "nitrogen_monoxide", 0x7dc8f0, NONE));
    public static Material NitrogenDioxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "nitrogen_dioxide", 0x64afff, NONE));
    public static Material NobleGases = GTAPI.register(Material.class, new Material(GT5RRef.ID, "noble_gases", 0xc9e3fc, NONE));
    public static Material Steam = GTAPI.register(Material.class, new Material(GT5RRef.ID, "steam", 0xa0a0a0, NONE));
    public static Material SuperheatedSteam = GTAPI.register(Material.class, new Material(GT5RRef.ID, "superheated_steam", 0xa0a0a0, NONE));
    public static Material SulfurDioxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sulfur_dioxide", 0xc8c819, NONE));
    public static Material SulfurTrioxide = GTAPI.register(Material.class, new Material(GT5RRef.ID, "sulfur_trioxide", 0xa0a014, NONE));

    /**
     ** Cracked
     **/

    public static Material HydroCrackedEthane = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hydro_cracked_ethane", 0xc8c8ff, NONE));
    //public static Material HydroCrackedEthylene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hydro_cracked_ethylene", 0xe1e1e1, NONE));
    public static Material HydroCrackedPropane = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hydro_cracked_propane", 0xfae250, NONE));
    //public static Material HydroCrackedPropene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hydro_cracked_propene", 0xffdd55, NONE));
    public static Material HydroCrackedButane = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hydro_cracked_butane", 0xb6371e, NONE));
    //public static Material HydroCrackedButene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hydro_cracked_butene", 0xcf5005, NONE));
    //public static Material HydroCrackedButadiene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hydro_cracked_butadiene", 0xe86900, NONE));
    public static Material HydroCrackedNaphtha = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hydro_cracked_naphtha", 0xffff00, NONE));
    public static Material HydroCrackedRefineryGas = GTAPI.register(Material.class, new Material(GT5RRef.ID, "hydro_cracked_refinery_gas", 0xffffff, NONE));
    public static Material SteamCrackedEthane = GTAPI.register(Material.class, new Material(GT5RRef.ID, "steam_cracked_ethane", 0xc8c8ff, NONE));
    //public static Material SteamCrackedEthylene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "steam_cracked_ethylene", 0xe1e1e1, NONE));
    public static Material SteamCrackedPropane = GTAPI.register(Material.class, new Material(GT5RRef.ID, "steam_cracked_propane", 0xfae250, NONE));
    //public static Material SteamCrackedPropene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "steam_cracked_propene", 0xffdd55, NONE));
    public static Material SteamCrackedButane = GTAPI.register(Material.class, new Material(GT5RRef.ID, "steam_cracked_butane", 0xb6371e, NONE));
    //public static Material SteamCrackedButene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "steam_cracked_butene", 0xcf5005, NONE));
    //public static Material SteamCrackedButadiene = GTAPI.register(Material.class, new Material(GT5RRef.ID, "steam_cracked_butadiene", 0xe86900, NONE));
    public static Material SteamCrackedNaphtha = GTAPI.register(Material.class, new Material(GT5RRef.ID, "steam_cracked_naphtha", 0xffff00, NONE));
    public static Material SteamCrackedRefineryGas = GTAPI.register(Material.class, new Material(GT5RRef.ID, "steam_cracked_refinery_gas", 0xffffff, NONE));

    /**
     *  Tetrafluorides
     **/
    public static Material UraniumTetrafluoride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "uranium_tetrafluoride", 0x21d921, NONE));
    public static Material Uranium238Tetrafluoride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "uranium_238_tetrafluoride", 0x21d921, NONE));
    public static Material Uranium235Tetrafluoride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "uranium_235_tetrafluoride", 0x21d921, NONE));

    /**
     *  Hexafluorides
     **/
    public static Material UraniumHexafluoride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "uranium_hexafluoride", 0x10c810, NONE));
    public static Material Uranium235Hexafluoride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "uranium_235_hexafluoride", 0x10c810, NONE));
    public static Material Uranium238Hexafluoride = GTAPI.register(Material.class, new Material(GT5RRef.ID, "uranium_238_hexafluoride", 0x10c810, NONE));

    public static Material Glowstone = GTLibMaterials.Glowstone;
    public static Material Sugar = GTLibMaterials.Sugar;
    public static Material Bone = GTLibMaterials.Bone;
    public static Material Wood = GTLibMaterials.Wood;
    public static Material Blaze = GTLibMaterials.Blaze;
    public static Material Flint = GTLibMaterials.Flint;
    public static Material Charcoal = GTLibMaterials.Charcoal;
    public static Material Coal = GTLibMaterials.Coal;
    public static Material Diamond = GTLibMaterials.Diamond;
    public static Material Emerald = GTLibMaterials.Emerald;
    public static Material EnderPearl = GTLibMaterials.EnderPearl;
    public static Material EnderEye = GTLibMaterials.EnderEye;
    public static Material Lapis = GTLibMaterials.Lapis;
    public static Material Redstone = GTLibMaterials.Redstone;
    public static Material Quartz = GTLibMaterials.Quartz;
    public static Material Netherite = GTLibMaterials.Netherite;
    public static Material NetherizedDiamond = GTLibMaterials.NetherizedDiamond;
    public static Material NetheriteScrap = GTLibMaterials.NetheriteScrap;
    public static Material Lava = GTLibMaterials.Lava;
    public static Material Water = GTLibMaterials.Water;

    // TODO go through the GT_Loader_Item_Block_And_Fluid and make sure all explicitly added fluids have the LIQUID tag
    public static void init() {
        // TODO assign correct handle materials
        // for (Material material : generated) {
        // if (material == Blaze) {
        // material.handleMaterial = "blaze";
        // } /*else if (aMaterial.contains(SubTag.MAGICAL) &&
        // aMaterial.contains(SubTag.CRYSTAL) && Utils.isModLoaded(MOD_ID_TC)) {
        // aMaterial.mHandleMaterial = Thaumium;
        // }*/ else if (material.getMass() > Element.Tc.getMass() * 2) {
        // material.handleMaterial = Tungstensteel.;
        // } else if (material.getMass() > Element.Tc.getMass()) {
        // material.handleMaterial = Steel;
        // } else {
        // material.handleMaterial = Wood;
        // }
        // }
        // If using small ore markers, every normal ore needs a small version. This
        // greatly increases block usage
    }
}
