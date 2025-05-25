package org.gtreimagined.gt5r.data;

import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.function.TriFunction;
import org.gtreimagined.gt5r.GT5RConfig;
import org.gtreimagined.gtcore.data.GTCoreTags;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.item.ItemBasic;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.SubTag;
import org.gtreimagined.gtlib.pipe.PipeItemBlock;
import org.gtreimagined.gtlib.pipe.PipeSize;
import org.gtreimagined.gtlib.util.TagUtils;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.GEM;
import static org.gtreimagined.gtlib.machine.Tier.*;

public class TierMaps {

    public static final ImmutableMap<Tier, Material> TIER_MATERIALS;
    public static final ImmutableMap<Tier, Material> TIER_PIPE_MATERIAL;
    public static ImmutableMap<Tier, PipeItemBlock> TIER_WIRES;
    //public static ImmutableMap<Tier, Item> TIER_CABLES;
    public static Function<Tier, TagKey<Item>> TIER_CIRCUITS;
    public static ImmutableMap<Tier, ItemBasic<?>> TIER_BOARDS;
    public static ImmutableMap<Tier, Material> EMITTER_RODS;
    public static ImmutableMap<Tier, Object> EMITTER_GEMS;
    public static ImmutableMap<Tier, Material> TIER_ROTORS;

    public static final BiFunction<PipeSize, Tier, Object> PIPE_GETTER;
    public static final BiFunction<PipeSize, Tier, Object> HATCH_PIPE_GETTER;
    public static final BiFunction<PipeSize, Tier, Object> WIRE_GETTER;
    public static final TriFunction<PipeSize, Tier, Boolean, Object> CABLE_GETTER;

    static {
        {
            ImmutableMap.Builder<Tier, Material> builder = ImmutableMap.builder();
            builder.put(Tier.ULV, Materials.WroughtIron);
            builder.put(Tier.LV, Steel);
            builder.put(Tier.MV, Materials.Aluminium);
            builder.put(Tier.HV, StainlessSteel);
            builder.put(Tier.EV, Titanium);
            builder.put(Tier.IV, TungstenSteel);
            builder.put(LUV, Materials.Chromium);
            builder.put(ZPM, Materials.Iridium);
            builder.put(UV, Materials.Osmium);
            builder.put(UHV, Materials.Neutronium);
            TIER_MATERIALS = builder.build();
        }

        {
            ImmutableMap.Builder<Tier, Material> builder = ImmutableMap.builder();
            builder.put(Tier.ULV, Copper);
            builder.put(Tier.LV, Bronze);
            builder.put(Tier.MV, Steel);
            builder.put(Tier.HV, StainlessSteel);
            builder.put(Tier.EV, Titanium);
            builder.put(Tier.IV, TungstenSteel);
            TIER_PIPE_MATERIAL = builder.build();
        }

        WIRE_GETTER = (size, tier) -> {
            if (tier == LV) {
                return TagUtils.getItemTag(new ResourceLocation(Ref.ID, SubTag.COPPER_WIRE.getId()+"_"+ size.getId()));
            }
            if (tier == MV) {
                return GT5RBlocks.WIRE_CUPRONICKEL.getBlockItem(size);
            }
            if (tier == HV) {
                return GT5RBlocks.WIRE_KANTHAL.getBlockItem(size);
            }
            if (tier == EV) {
                return GT5RBlocks.WIRE_NICHROME.getBlockItem(size);
            }
            if (tier == IV) {
                return GT5RBlocks.WIRE_TUNGSTEN_STEEL.getBlockItem(size);
            }
            throw new IllegalArgumentException("Too high tier in WIRE_GETTER");
        };
        CABLE_GETTER = (size, tier, machine) -> {
            if (tier == ULV) return GT5RBlocks.CABLE_SOLDERING_ALLOY.getBlockItem(size);
            if (tier == LV) return GT5RBlocks.CABLE_TIN.getBlockItem(size);
            if (tier == MV){
                return TagUtils.getItemTag(new ResourceLocation(Ref.ID, SubTag.COPPER_CABLE.getId()+"_"+ size.getId()));
            }
            if (tier == HV) return GT5RBlocks.CABLE_GOLD.getBlockItem(size);
            if (tier == EV) return GT5RBlocks.CABLE_ALUMINIUM.getBlockItem(size);
            if (tier == IV) return machine ? GT5RBlocks.CABLE_PLATINUM.getBlockItem(size) : GT5RBlocks.CABLE_TUNGSTEN.getBlockItem(size);
            if(tier == LUV) return GT5RBlocks.CABLE_VANADIUM_GALLIUM.getBlockItem(size);
            if(tier == ZPM) return GT5RBlocks.CABLE_NAQUADAH.getBlockItem(size);
            if(tier == UV) return GT5RBlocks.CABLE_NAQUADAH_ALLOY.getBlockItem(size);
            if(tier == UHV) return GT5RBlocks.WIRE_SUPERCONDUCTOR.getBlockItem(size);
            throw new IllegalArgumentException("Invalid tier in CABLE_GETTER");
        };
        PIPE_GETTER = (size, tier) -> {
            if (tier == ULV) return GT5RBlocks.FLUID_PIPE_COPPER.getBlockItem(size);
            if (tier == LV) return GT5RBlocks.FLUID_PIPE_BRONZE.getBlockItem(size);
            if (tier == MV) return GT5RBlocks.FLUID_PIPE_STEEL.getBlockItem(size);
            if (tier == HV) return GT5RBlocks.FLUID_PIPE_STAINLESS_STEEL.getBlockItem(size);
            if (tier == EV) return GT5RBlocks.FLUID_PIPE_TITANIUM.getBlockItem(size);
            if (tier == IV) return GT5RBlocks.FLUID_PIPE_TUNGSTEN_STEEL.getBlockItem(size);
            if (tier == LUV || tier == ZPM || tier == UV || tier == UHV) return GT5RBlocks.FLUID_PIPE_CHROMIUM.getBlockItem(size);
            throw new IllegalArgumentException("Invalid tier in PIPE_GETTER");
        };
        HATCH_PIPE_GETTER = (size, tier) -> {
            if (tier == ULV) return GT5RBlocks.FLUID_PIPE_PLASTIC.getBlockItem(size);
            if (tier == LV) return GT5RBlocks.FLUID_PIPE_PVC.getBlockItem(size);
            if (tier == MV) return GT5RBlocks.FLUID_PIPE_INVAR.getBlock(size);
            if (tier == HV) return GT5RBlocks.FLUID_PIPE_STEEL.getBlock(size);
            if (tier == EV) return GT5RBlocks.FLUID_PIPE_STAINLESS_STEEL.getBlockItem(size);
            if (tier == IV) return GT5RBlocks.FLUID_PIPE_CHROMIUM.getBlockItem(size);
            if (tier == LUV) return GT5RBlocks.FLUID_PIPE_TUNGSTEN_STEEL.getBlockItem(size);
            if (tier == ZPM) return GT5RBlocks.FLUID_PIPE_TUNGSTEN.getBlockItem(size);
            if (tier == UV) return GT5RBlocks.FLUID_PIPE_TUNGSTEN_CARBIDE.getBlockItem(size);
            if (tier == UHV) return GT5RBlocks.FLUID_PIPE_ADAMANTIUM.getBlockItem(size);
            throw new IllegalArgumentException("Invalid tier in HATCH_PIPE_GETTER");
        };
    }
    //Called to init the INT CIRCUITS and tier materials early on.
    public static void init() {

    }
    //ProviderInit is called by the RecipeProvider during construction.
    private static boolean doneMaps = false;
    public static void providerInit() {
        if (doneMaps) return;
        doneMaps = true;
        {
            ImmutableMap.Builder<Tier, PipeItemBlock> builder = ImmutableMap.builder();
            builder.put(Tier.ULV, GT5RBlocks.WIRE_SOLDERING_ALLOY.getBlockItem(PipeSize.VTINY));
            builder.put(Tier.LV, GT5RBlocks.WIRE_TIN.getBlockItem(PipeSize.VTINY));
            builder.put(Tier.MV, GT5RBlocks.WIRE_COPPER.getBlockItem(PipeSize.VTINY));
            builder.put(Tier.HV, GT5RBlocks.WIRE_GOLD.getBlockItem(PipeSize.VTINY));
            builder.put(Tier.EV, GT5RBlocks.WIRE_ALUMINIUM.getBlockItem(PipeSize.VTINY));
            builder.put(Tier.IV, GT5RBlocks.WIRE_TUNGSTEN.getBlockItem(PipeSize.VTINY));
            builder.put(LUV, GT5RBlocks.WIRE_VANADIUM_GALLIUM.getBlockItem(PipeSize.VTINY));
            builder.put(ZPM, GT5RBlocks.WIRE_NAQUADAH.getBlockItem(PipeSize.VTINY));
            builder.put(UV, GT5RBlocks.WIRE_NAQUADAH_ALLOY.getBlockItem(PipeSize.SMALL));
            builder.put(UHV, GT5RBlocks.WIRE_SUPERCONDUCTOR.getBlockItem(PipeSize.VTINY));
            TIER_WIRES = builder.build();
        }
        /*{
            ImmutableMap.Builder<Tier, Item> builder = ImmutableMap.builder();
            builder.put(Tier.ULV, CABLE_RED_ALLOY.getBlockItem(PipeSize.VTINY));
            builder.put(Tier.LV, CABLE_TIN.getBlockItem(PipeSize.VTINY));
            builder.put(Tier.MV, CABLE_COPPER.getBlockItem(PipeSize.VTINY));
            builder.put(Tier.HV, CABLE_GOLD.getBlockItem(PipeSize.VTINY));
            builder.put(Tier.EV, CABLE_ALUMINIUM.getBlockItem(PipeSize.VTINY));
            builder.put(Tier.IV, CABLE_TUNGSTEN.getBlockItem(PipeSize.VTINY));
            builder.put(LUV, CABLE_VANADIUM_GALLIUM.getBlockItem(PipeSize.VTINY));
            builder.put(ZPM, CABLE_NAQUADAH.getBlockItem(PipeSize.VTINY));
            builder.put(UV, CABLE_NAQUADAH_ALLOY.getBlockItem(PipeSize.SMALL));
            builder.put(MAX, WIRE_SUPERCONDUCTOR.getBlockItem(PipeSize.VTINY));
            TIER_CABLES = builder.build();
        }*/
        {
            ImmutableMap.Builder<Tier, Material> builder = ImmutableMap.builder();
            builder.put(Tier.ULV, Bronze);
            builder.put(Tier.LV, Materials.Tin);
            builder.put(Tier.MV, Bronze);
            builder.put(Tier.HV, Steel);
            builder.put(Tier.EV, StainlessSteel);
            builder.put(Tier.IV, TungstenSteel);
            TIER_ROTORS = builder.build();
        }
        {
            ImmutableMap.Builder<Tier, Material> builder = ImmutableMap.builder();
            builder.put(Tier.LV, Materials.Brass);
            builder.put(Tier.MV, Materials.Electrum);
            builder.put(Tier.HV, Materials.Chromium);
            builder.put(Tier.EV, Materials.Platinum);
            builder.put(Tier.IV, Materials.Osmium);
            EMITTER_RODS = builder.build();
        }
        {
            ImmutableMap.Builder<Tier, Object> builder = ImmutableMap.builder();
            builder.put(Tier.LV, GEM.getMaterialTag(Materials.MilkyQuartz));
            builder.put(Tier.MV, Items.QUARTZ);
            builder.put(Tier.HV, Items.EMERALD);
            builder.put(Tier.EV, Items.ENDER_PEARL);
            builder.put(Tier.IV, Items.ENDER_EYE);
            EMITTER_GEMS = builder.build();
        }
        {
            ImmutableMap.Builder<Tier, TagKey<Item>> builder = ImmutableMap.builder();
            builder.put(Tier.LV, GTCoreTags.CIRCUITS_BASIC);
            builder.put(Tier.MV, GTCoreTags.CIRCUITS_GOOD);
            builder.put(Tier.HV, GTCoreTags.CIRCUITS_ADVANCED);
            builder.put(Tier.EV, GTCoreTags.CIRCUITS_ELITE);
            builder.put(Tier.IV, GTCoreTags.CIRCUITS_MASTER);
            builder.put(Tier.LUV, GTCoreTags.CIRCUITS_DATA_ORB);
            builder.put(Tier.ZPM, GTCoreTags.CIRCUITS_DATA_ORB);
            TIER_CIRCUITS = t ->{
                boolean hardMode = GT5RConfig.HARDER_CIRCUITS;
                if (t == LV){
                    return GTCoreTags.CIRCUITS_BASIC;
                }
                if (t == MV){
                    return GTCoreTags.CIRCUITS_GOOD;
                }
                if (t == HV){
                    return GTCoreTags.CIRCUITS_ADVANCED;
                }
                if (t == EV){
                    return GTCoreTags.CIRCUITS_COMPLEX;
                }
                if (t == IV){
                    return GTCoreTags.CIRCUITS_ELITE;
                }
                if (t == LUV){
                    return GTCoreTags.CIRCUITS_MASTER;
                }
                if (t == ZPM){
                    return GTCoreTags.CIRCUITS_ULTIMATE;
                }
                if (t == UV){
                    return hardMode ? GTCoreTags.CIRCUITS_ULTIMATE : GTCoreTags.CIRCUITS_DATA_ORB;
                }
                if (t == UHV){
                    return hardMode ? GTCoreTags.CIRCUITS_ULTIMATE : GTCoreTags.CIRCUITS_DATA_ORB;
                }
                return GTCoreTags.CIRCUITS_BASIC;
            };
        }
        {
            ImmutableMap.Builder<Tier, ItemBasic<?>> builder = ImmutableMap.builder();
            builder.put(Tier.LV, GT5RItems.CircuitBoardCoated);
            builder.put(Tier.MV, GT5RItems.CircuitBoardPhenolic);
            builder.put(Tier.HV, GT5RItems.CircuitBoardPlastic);
            builder.put(Tier.EV, GT5RItems.CircuitBoardEpoxy);
            builder.put(Tier.IV, GT5RItems.CircuitBoardFiber);
            builder.put(Tier.LUV, GT5RItems.CircuitBoardMultiFiber);
            builder.put(Tier.ZPM, GT5RItems.CircuitBoardWetware);
            TIER_BOARDS = builder.build();
        }
    }
}
