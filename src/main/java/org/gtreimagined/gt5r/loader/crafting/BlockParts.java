package org.gtreimagined.gt5r.loader.crafting;

import com.google.common.collect.ImmutableMap;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.gtreimagined.gt5r.GT5RConfig;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.block.BlockAsphalt;
import org.gtreimagined.gt5r.block.BlockAsphaltSlab;
import org.gtreimagined.gt5r.block.BlockAsphaltStair;
import org.gtreimagined.gt5r.block.BlockCasing;
import org.gtreimagined.gt5r.block.BlockCoil;
import org.gtreimagined.gt5r.block.BlockColoredWall;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gt5r.data.GT5RCovers;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.data.GT5RTags;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.pipe.PipeItemBlock;
import org.gtreimagined.gtlib.pipe.PipeSize;
import org.gtreimagined.gtlib.pipe.types.PipeType;
import org.gtreimagined.gtlib.util.RegistryUtils;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gt5r.data.GT5RMachines.HULL;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.TierMaps.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTTools.*;
import static org.gtreimagined.gtlib.machine.Tier.*;

public class BlockParts {
    public static void loadRecipes(Consumer<FinishedRecipe> output, GTRecipeProvider provider) {
        GTMaterialTypes.FRAME.all().forEach(frame -> {
            if (!frame.has(GTMaterialTypes.ROD)) return;
            provider.addStackRecipe(output, GT5Reimagined.ID, "", "gtblockparts", GTMaterialTypes.FRAME.get().get(frame).asStack(2),
                    of('R', GTMaterialTypes.ROD.get(frame), 'W', WRENCH.getTag())
            , "RRR","RWR", "RRR");
        });

        GTAPI.all(BlockColoredWall.class, b -> {
            if (b.getMaterial() == Wood){
                provider.addItemRecipe(output, "walls", b.asItem(),
                        of('P', PLATE.getMaterialTag(Lead), 'H', HAMMER.getTag(), 'S', SAW.getTag(), 'W', ItemTags.PLANKS), "W W", "SPH", "W W");
            } else {
                provider.addItemRecipe(output, "walls", b.asItem(),
                        of('P', PLATE.getMaterialTag(b.getMaterial()), 'H', HAMMER.getTag(), 'W', WRENCH.getTag()), "WPP", "HPP");
            }
        });

        addBrickedCasing(output, provider, Bronze, GT5RBlocks.BRICKED_BRONZE_CASING);
        provider.addStackRecipe(output, GT5Reimagined.ID, "firebricks", "blocks",
                new ItemStack(GT5RBlocks.FIRE_BRICKS), of('F', GTCoreItems.FireBrick), "FF", "FF");
        addBrickedCasing(output, provider, Steel, GT5RBlocks.BRICKED_STEEL_CASING);

        addFirebox(output, provider, Bronze, GT5RBlocks.BRONZE_FIREBOX_CASING);
        addFirebox(output, provider, Steel, GT5RBlocks.STEEL_FIREBOX_CASING);
        addFirebox(output, provider, Titanium, GT5RBlocks.TITANIUM_FIREBOX_CASING);
        addFirebox(output, provider, TungstenSteel, GT5RBlocks.TUNGSTENSTEEL_FIREBOX_CASING);

        addGearbox(output, provider, Bronze, GT5RBlocks.BRONZE_GEARBOX_CASING);
        addGearbox(output, provider, Steel, GT5RBlocks.STEEL_GEARBOX_CASING);
        addGearbox(output, provider, Titanium, GT5RBlocks.TITANIUM_GEARBOX_CASING);
        addGearbox(output, provider, TungstenSteel, GT5RBlocks.TUNGSTENSTEEL_GEARBOX_CASING);

        addPipeCasing(output, provider, Bronze, GT5RBlocks.FLUID_PIPE_BRONZE, GT5RBlocks.BRONZE_PIPE_CASING);
        addPipeCasing(output, provider, Steel, GT5RBlocks.FLUID_PIPE_STEEL, GT5RBlocks.STEEL_PIPE_CASING);
        addPipeCasing(output, provider, Titanium, GT5RBlocks.FLUID_PIPE_TITANIUM, GT5RBlocks.TITANIUM_PIPE_CASING);
        addPipeCasing(output, provider, TungstenSteel, GT5RBlocks.FLUID_PIPE_TUNGSTEN_STEEL, GT5RBlocks.TUNGSTENSTEEL_PIPE_CASING);

        addTurbine(output, provider, Steel, GT5RBlocks.STEEL_TURBINE_CASING);
        addTurbine(output, provider, StainlessSteel, GT5RBlocks.STAINLESS_STEEL_TURBINE_CASING);
        addTurbine(output, provider, Titanium, GT5RBlocks.TITANIUM_TURBINE_CASING);
        addTurbine(output, provider, TungstenSteel, GT5RBlocks.TUNGSTENSTEEL_TURBINE_CASING);

        addCasing(output, provider, Invar, GT5RBlocks.HEAT_PROOF_CASING);
        addCasing(output, provider, Aluminium, GT5RBlocks.FROST_PROOF_CASING);
        addCasing(output, provider, Steel, GT5RBlocks.SOLID_STEEL_CASING);
        addCasing(output, provider, StainlessSteel, GT5RBlocks.STAINLESS_STEEL_CASING);
        addCasing(output, provider, Titanium, GT5RBlocks.TITANIUM_CASING);
        addCasing(output, provider, Lead, GT5RBlocks.RADIATION_PROOF_CASING);
        addCasing(output, provider, TungstenSteel, GT5RBlocks.TUNGSTENSTEEL_CASING);
        addCasing(output, provider, Tungsten, GT5RBlocks.TUNGSTEN_CASING);
        addCasing(output, provider, Platinum, GT5RBlocks.PLATINUM_CASING);
        addCasing(output, provider, BlackBronze, GT5RBlocks.BLACK_BRONZE_CASING);
        addCasing(output, provider, Plastic, GT5RBlocks.PLASTIC_CASING);
        provider.addItemRecipe(output, "casings", GT5RBlocks.DENSE_LEAD_CASING,
                of('L', PLATE_DENSE.getMaterialTag(Lead), 'R', ROD_LONG.getMaterialTag(Lead), 'W', WRENCH.getTag()), "RLL", "LWL", "LLR");

        provider.addItemRecipe(output, "casings", GT5RBlocks.ELECTROLYTIC_CELL,
                of('W', GT5RBlocks.WIRE_PLATINUM.getBlockItem(PipeSize.VTINY), 'M', GT5RBlocks.STAINLESS_STEEL_CASING, 'C', TIER_CIRCUITS.apply(EV)), "WWW", "WMW", "CCC");
        provider.addItemRecipe(output, "casings", GT5RBlocks.GRINDING_WHEELS,
                of('G', GEAR.getMaterialTag(TungstenSteel), 'M', GT5RBlocks.TUNGSTENSTEEL_CASING, 'D', GT5RTags.GRIND_HEADS), "GDG", "GMG");
        provider.addItemRecipe(output, "casings", GT5RBlocks.ORE_WASHING_PARTS,
                of('G', GEAR.getMaterialTag(Titanium), 'P', GT5RBlocks.FLUID_PIPE_TITANIUM.getBlock(PipeSize.NORMAL),'C', GT5RBlocks.TITANIUM_CASING, 'W', WRENCH.getTag()), "GGG", "PCP", "PWP");
        provider.addItemRecipe(output, "casings", GT5RBlocks.FILTER_CASING, of('C', GT5RBlocks.TITANIUM_CASING, 'I', GT5RCovers.COVER_ITEM_FILTER.getItem()), " I ", "ICI", " I ");
        provider.addItemRecipe(output, "casings", GT5RBlocks.BEDROCK_DRILL_HEAD,
                of('D', GT5RTags.GRIND_HEADS, 'd', DRILLBIT.getMaterialTag(TungstenSteel), 'G', GEAR.getMaterialTag(TungstenSteel), 'C', GT5RBlocks.TUNGSTENSTEEL_CASING), "GdG", "DCD", "GdG");
        addCoil(output, provider, GT5RBlocks.WIRE_CUPRONICKEL.getBlockItem(PipeSize.TINY), GT5RBlocks.CUPRONICKEL_COIL);
        addCoil(output, provider, GT5RBlocks.WIRE_KANTHAL.getBlockItem(PipeSize.TINY), GT5RBlocks.KANTHAL_COIL);
        addCoil(output, provider, GT5RBlocks.WIRE_NICHROME.getBlockItem(PipeSize.TINY), GT5RBlocks.NICHROME_COIL);
        addCoil(output, provider, GT5RBlocks.WIRE_TUNGSTEN_STEEL.getBlockItem(PipeSize.TINY), GT5RBlocks.TUNGSTENSTEEL_COIL);
        addCoil(output, provider, GT5RBlocks.WIRE_HSSG.getBlockItem(PipeSize.TINY), GT5RBlocks.HSSG_COIL);
        addCoil(output, provider, GT5RBlocks.WIRE_NAQUADAH.getBlockItem(PipeSize.TINY), GT5RBlocks.NAQUADAH_COIL);
        addCoil(output, provider, GT5RBlocks.WIRE_NAQUADAH_ALLOY.getBlockItem(PipeSize.TINY), GT5RBlocks.NAQUADAH_ALLOY_COIL);
        addCoil(output, provider, GT5RBlocks.WIRE_SUPERCONDUCTOR.getBlockItem(PipeSize.TINY), GT5RBlocks.SUPERCONDUCTOR_COIL);
        provider.addItemRecipe(output, "coils", GT5RBlocks.FUSION_COIL,
                of('C', TIER_CIRCUITS.apply(LUV), 'F', GT5RItems.FieldGenMV, 'c', GT5RBlocks.SUPERCONDUCTOR_COIL, 'I', GTCoreItems.IridiumNeutronReflector), "CIC", "FcF", "CIC");

        addTierCasing(output, provider, Tier.ULV);
        addTierCasing(output, provider, LV);
        addTierCasing(output, provider, MV);
        addTierCasing(output, provider, Tier.HV);
        addTierCasing(output, provider, Tier.EV);
        addTierCasing(output, provider, Tier.IV);
        addTierCasing(output, provider, Tier.LUV);
        addTierCasing(output, provider, Tier.ZPM);
        addTierCasing(output, provider, Tier.UV);
        addTierCasing(output, provider, Tier.UHV);

        addTierHull(output, provider, Wood,Tier.ULV);
        addTierHull(output, provider, WroughtIron, LV);
        addTierHull(output, provider, WroughtIron, MV);
        addTierHull(output, provider, Plastic,Tier.HV);
        addTierHull(output, provider, Plastic,Tier.EV);
        addTierHull(output, provider, Plastic,Tier.IV);
        addTierHull(output, provider, Plastic,Tier.LUV);
        addTierHull(output, provider, Polytetrafluoroethylene,Tier.ZPM);
        addTierHull(output, provider, Polytetrafluoroethylene,Tier.UV);
        addTierHull(output, provider, Polytetrafluoroethylene,Tier.UHV);

        provider.addStackRecipe(output, GT5Reimagined.ID, "", "gtblockparts", new ItemStack(GT5RBlocks.ENGINE_INTAKE_CASING, 1),
                of('P', ROTOR.getMaterialTag(Titanium), 'W', WRENCH.getTag(), 'H', GTTools.HAMMER.getTag(), 'F', GT5RBlocks.TITANIUM_CASING, 'G', GT5RBlocks.FLUID_PIPE_TITANIUM.getBlockItem(PipeSize.NORMAL))
                ,
                "PHP", "GFG", "PWP");
        if (GT5RConfig.HARD_SETTINGS){
            provider.addItemRecipe(output, "gtblockparts", GT5RBlocks.ASSEMBLY_LINE_CASING,
                    of('S', PLATE.getMaterialTag(TungstenSteel), 'F', FRAME.getMaterialTag(TungstenSteel), 'R', GT5RCovers.COVER_ROBOT_ARM.getItem(IV), 'H', HAMMER.getTag(), 'W', WRENCH.getTag()), "SHS" ,"RFR", "SWS");
            provider.addItemRecipe(output, "gtblockparts", GT5RBlocks.ADVANCED_ASSEMBLER_CASING,
                    of('C', TIER_CIRCUITS.apply(EV), 'c', TIER_CIRCUITS.apply(IV), 'F', FRAME.getMaterialTag(TungstenSteel), 'M', GTCoreItems.MotorIV), "CcC", "CFC", "CMC");
        }
        provider.addItemRecipe(output, "gtblockparts", GT5RBlocks.AUTOCRAFTER_ASSEMBLY_LINE_CASING,
                of('S', PLATE.getMaterialTag(Steel), 'F', FRAME.getMaterialTag(Steel), 'R', GT5RCovers.COVER_ROBOT_ARM.getItem(HV), 'H', HAMMER.getTag(), 'W', WRENCH.getTag()), "SHS" ,"RFR", "SWS");
        provider.addItemRecipe(output, "gtblockparts", GT5RBlocks.ASSEMBLER_CASING,
                of('C', TIER_CIRCUITS.apply(MV), 'c', TIER_CIRCUITS.apply(HV), 'I', GT5RCovers.COVER_CONVEYOR.getItem(HV), 'F', FRAME.getMaterialTag(Steel), 'M', GTCoreItems.MotorHV), "IcI", "CFC", "CMC");
        provider.addItemRecipe(output, "gtblockparts", GT5RBlocks.GRATE_CASING,
                of('B', Items.IRON_BARS, 'R', ROTOR.getMaterialTag(Steel), 'F', FRAME.getMaterialTag(Steel), 'M', GTCoreItems.MotorMV), "BRB", "BFB", "BMB");

        provider.addStackRecipe(output, GT5Reimagined.ID, "", "gtblockparts", new ItemStack(GT5RBlocks.BRONZE_PLATED_BRICK_CASING, 1),
                ImmutableMap.<Character, Object>builder()
                        .put('P', PLATE.get(Bronze))
                        .put('B', Blocks.BRICKS)
                        .put('W', WRENCH.getTag()).put('H', HAMMER.getTag()).build(), "PHP", "PBP", "PWP");

        provider.addItemRecipe(output, "gtblockparts", GT5RBlocks.STEEL_CASING,
                ImmutableMap.<Character, Object>builder()
                        .put('P', PLATE.get(Steel))
                        .put('W', HAMMER.getTag()).build(), "PPP", "PWP", "PPP");
        provider.addStackRecipe(output, GT5Reimagined.ID, "", "gtblockparts", new ItemStack(GT5RBlocks.FUSION_CASING, 1),
                of('P', PLATE.getMaterialTag(TungstenSteel), 'W', WRENCH.getTag(), 'H', GTTools.HAMMER.getTag(), 'F', GT5RBlocks.CASING_LUV)
                ,
                "PHP", "PFP", "PWP");
        /*provider.addStackRecipe(output, GT5RRef.ID, "", "gtblockparts", "has_wrench", provider.hasSafeItem(WRENCH.getTag()), new ItemStack(CASING_FUSION_2, 1),
                of('P', PLATE.getMaterialTag(Americium), 'W', WRENCH.getTag(), 'H', GTTools.HAMMER.getTag(), 'F', CASING_FUSION_1)
                ,
                "PHP", "PFP", "PWP");*/

        //TODO make these also use annealed copper
        provider.addItemRecipe(output, "long_distance_cables", GT5RBlocks.LONG_DIST_WIRE_EV,
                of('C', GT5RBlocks.WIRE_TIN.getBlockItem(PipeSize.HUGE), 'c', PLATE.getMaterialTag(Copper), 'A', PLATE.getMaterialTag(Aluminium), 'R', PLATE.getMaterialTag(Rubber)), "RAR", "cCc", "RAR");
        provider.addItemRecipe(output, "long_distance_cables", GT5RBlocks.LONG_DIST_WIRE_IV,
                of('C', WIRE_GETTER.apply(PipeSize.HUGE, LV), 'c', PLATE.getMaterialTag(Copper), 'A', PLATE.getMaterialTag(Aluminium), 'R', PLATE.getMaterialTag(Rubber)), "RAR", "cCc", "RAR");
        provider.addItemRecipe(output, "long_distance_cables", GT5RBlocks.LONG_DIST_WIRE_LUV,
                of('C', GT5RBlocks.WIRE_ELECTRUM.getBlockItem(PipeSize.HUGE), 'c', PLATE.getMaterialTag(Copper), 'A', PLATE.getMaterialTag(Aluminium), 'R', PLATE.getMaterialTag(Rubber)), "RAR", "cCc", "RAR");
        provider.addItemRecipe(output, "long_distance_cables", GT5RBlocks.LONG_DIST_WIRE_ZPM,
                of('C', GT5RBlocks.WIRE_ALUMINIUM.getBlockItem(PipeSize.HUGE), 'c', PLATE.getMaterialTag(Copper), 'A', PLATE.getMaterialTag(Aluminium), 'R', PLATE.getMaterialTag(Rubber)), "RAR", "cCc", "RAR");
        provider.addItemRecipe(output, "long_distance_cables", GT5RBlocks.LONG_DIST_WIRE_UV,
                of('C', GT5RBlocks.WIRE_PLATINUM.getBlockItem(PipeSize.HUGE), 'c', PLATE.getMaterialTag(Copper), 'A', PLATE.getMaterialTag(Aluminium), 'R', PLATE.getMaterialTag(Rubber)), "RAR", "cCc", "RAR");
        provider.addItemRecipe(output, "long_distance_pipes", GT5RBlocks.LONG_DIST_ITEM_PIPE,
                of('E', GT5RBlocks.ITEM_PIPE_ELECTRUM.getBlockItem(PipeSize.NORMAL), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(Plastic)), "PEP", "EWE", "PEP");
        provider.addItemRecipe(output, "long_distance_pipes", GT5RBlocks.LONG_DIST_FLUID_PIPE,
                of('E', GT5RBlocks.FLUID_PIPE_STAINLESS_STEEL.getBlockItem(PipeSize.NORMAL), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(Plastic)), "PEP", "EWE", "PEP");

        GTAPI.all(BlockAsphalt.class, GT5Reimagined.ID).forEach(b -> {
            Block slab = GTAPI.get(BlockAsphaltSlab.class, b.getId() + "_slab", GT5Reimagined.ID);
            Block stairs = GTAPI.get(BlockAsphaltStair.class, b.getId() + "_stairs", GT5Reimagined.ID);
            if (slab != null){
                addSlabRecipe(output, provider, b, slab);
            }
            if (stairs != null){
                addStairRecipe(output, provider, b, stairs);
            }
        });

        /*provider.addStackRecipe(output, GT5RRef.ID, "blastbrickcasing", "gtblockparts", "has_wrench", provider.hasSafeItem(GTTools.WRENCH.getTag()), new ItemStack(CASING_BLAST_BRICK,4),
                of('C', CASING_FIRE_BRICK,
                'P', PLATE.get(GTLibMaterials.Iron),
                        'B', GTAPI.get(Item.class, "liquid_creosote_bucket", Ref.SHARED_ID)
        ), "PCP", "CBC", "PCP");*/
        provider.removeRecipe(new ResourceLocation("tnt"));
        provider.addItemRecipe(output, "misc", GT5RBlocks.POWDER_BARREL,
                of('W', ItemTags.PLANKS, 'G', Items.GUNPOWDER, 'S', Items.STRING), "WSW" ,"GGG", "WGW");
    }

    private static void addSlabRecipe(Consumer<FinishedRecipe> output, GTRecipeProvider provider, Block full, Block slab){
        provider.addStackRecipe(output, "slabs", new ItemStack(slab, 6), ImmutableMap.of('F', full), "FFF");
        provider.addItemRecipe(output, "slabs", full, ImmutableMap.of('S', slab), "S", "S");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(full), slab, 2).group("slabs").unlockedBy("has_full", provider.hasSafeItem(full)).save(output, new ResourceLocation(GT5Reimagined.ID, "stonecutting/" + RegistryUtils.getIdFromItem(slab.asItem()).getPath()));
    }

    private static void addStairRecipe(Consumer<FinishedRecipe> output, GTRecipeProvider provider, Block full, Block stair){
        provider.addStackRecipe(output, "stairs", new ItemStack(stair, 4), ImmutableMap.of('F', full), "F  ", "FF ", "FFF");
        provider.addStackRecipe(output, GT5Reimagined.ID, RegistryUtils.getIdFromItem(stair.asItem()).getPath() + "_mirrored", "stairs", new ItemStack(stair, 4), ImmutableMap.of('F', full), "  F", " FF", "FFF");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(full), stair, 1).group("stairs").unlockedBy("has_full", provider.hasSafeItem(full)).save(output, new ResourceLocation(GT5Reimagined.ID, "stonecutting/" + RegistryUtils.getIdFromItem(stair.asItem()).getPath()));
    }

    private static void addCasing(Consumer<FinishedRecipe> output, GTRecipeProvider provider, Material mat, Block casing) {
        provider.addItemRecipe(output, GT5Reimagined.ID, "", "gtblockparts", casing,
                of('P', PLATE.getMaterialTag(mat), 'W', WRENCH.getTag(), 'H', GTTools.HAMMER.getTag(), 'F', GTMaterialTypes.FRAME.get().get(mat).asItem())
                ,
                "PHP", "PFP", "PWP");
    }

    private static void addBrickedCasing(Consumer<FinishedRecipe> output, GTRecipeProvider provider, Material mat, Block casing) {
        provider.addItemRecipe(output, "gtblockparts", casing,
                of('B', Items.BRICK, 'P', PLATE.getMaterialTag(mat), 'H', GTTools.HAMMER.getTag())
                , "PPP", "PHP", "BBB");
    }

    private static void addFirebox(Consumer<FinishedRecipe> output, GTRecipeProvider provider, Material mat, Block casing) {
        provider.addItemRecipe(output, GT5Reimagined.ID, "", "gtblockparts", casing,
                of('P', PLATE.getMaterialTag(mat), 'R', ROD.getMaterialTag(mat), 'F', GTMaterialTypes.FRAME.get().get(mat).asItem())
                , "PRP", "RFR", "PRP");
    }

    private static void addGearbox(Consumer<FinishedRecipe> output, GTRecipeProvider provider, Material mat, Block casing) {
        provider.addItemRecipe(output, GT5Reimagined.ID, "", "gtblockparts", casing,
                of('P', PLATE.getMaterialTag(mat), 'W', WRENCH.getTag(), 'H', GTTools.HAMMER.getTag(), 'F', GTMaterialTypes.FRAME.get().get(mat).asItem(), 'G', GEAR.getMaterialTag(mat))
                ,
                "PHP", "GFG", "PWP");
    }

    private static void addPipeCasing(Consumer<FinishedRecipe> output, GTRecipeProvider provider, Material mat, PipeType<?> pipe, Block casing) {
        provider.addItemRecipe(output, GT5Reimagined.ID, "", "gtblockparts", casing,
                of('P', PLATE.getMaterialTag(mat), 'R', pipe.getBlockItem(PipeSize.NORMAL), 'F', GTMaterialTypes.FRAME.get().get(mat).asItem())
                , "PRP", "RFR", "PRP");
    }

    private static void addTurbine(Consumer<FinishedRecipe> output, GTRecipeProvider provider, Material mat, Block casing) {
        provider.addItemRecipe(output, "gtblockparts", casing,
                of('P', PLATE.getMaterialTag(mat), 'R', GTMaterialTypes.ROD.getMaterialTag(mat), 'F', GTMaterialTypes.FRAME.get().get(mat).asItem())
                , "PRP", "PFP", "PRP");
    }

    private static void addCoil(Consumer<FinishedRecipe> output, GTRecipeProvider provider, PipeItemBlock wire, BlockCoil coil) {
        provider.addItemRecipe(output, "casings", coil,
                of('P', wire, 'W', WRENCH.getTag())
                , "PPP", "PWP", "PPP");
    }

    private static void addTierCasing(Consumer<FinishedRecipe> output, GTRecipeProvider provider, Tier tier) {
        provider.addItemRecipe(output, "casings", GTAPI.get(BlockCasing.class, "casing_" + tier.getId(), GT5Reimagined.ID),
                of('P', PLATE.getMaterialTag(TIER_MATERIALS.get(tier)), 'W', WRENCH.getTag())
                , "PPP", "PWP", "PPP");
    }

    private static void addTierHull(Consumer<FinishedRecipe> output, GTRecipeProvider provider, Material mat, Tier tier) {
        provider.addItemRecipe(output, "hulls", HULL.getItem(tier),
                of('P', PLATE.getMaterialTag(mat), 'R', PLATE.getMaterialTag(TIER_MATERIALS.get(tier)), 'W', CABLE_GETTER.apply(tier == Tier.UV ? PipeSize.SMALL : PipeSize.VTINY, tier, false), 'K', GTAPI.get(BlockCasing.class, "casing_" + tier.getId(), GT5Reimagined.ID))
                , "PRP", "WKW");
    }
}
