package org.gtreimagined.gt5r.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.gtreimagined.gt5r.block.BlockAsphalt;
import org.gtreimagined.gt5r.block.BlockAsphaltSlab;
import org.gtreimagined.gt5r.block.BlockAsphaltStair;
import org.gtreimagined.gt5r.block.BlockBedrockFlower;
import org.gtreimagined.gt5r.block.BlockCasing;
import org.gtreimagined.gt5r.block.BlockCoil;
import org.gtreimagined.gt5r.block.BlockColoredWall;
import org.gtreimagined.gt5r.block.BlockFakeCasing;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gt5r.integration.AppliedEnergisticsRegistrar;
import org.gtreimagined.gt5r.integration.SpaceModRegistrar;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.VanillaStoneTypes;
import org.gtreimagined.gtlib.datagen.providers.GTBlockLootProvider;
import org.gtreimagined.gtlib.ore.CobbleStoneType;
import org.gtreimagined.gtlib.util.RegistryUtils;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class GT5RBlockLootProvider extends GTBlockLootProvider {
    public GT5RBlockLootProvider(String providerDomain, String providerName) {
        super(providerDomain, providerName);
    }

    @Override
    protected void loot() {
        super.loot();
        GTAPI.all(BlockCasing.class,providerDomain, this::add);
        GTAPI.all(BlockColoredWall.class,providerDomain, this::add);
        GTAPI.all(BlockCoil.class,providerDomain, this::add);
        GTAPI.all(BlockFakeCasing.class, providerDomain, this::add);
        GTAPI.all(BlockAsphalt.class, providerDomain, this::add);
        GTAPI.all(BlockAsphaltSlab.class, providerDomain, b -> tables.put(b, this::createSlabItemTable));
        GTAPI.all(BlockAsphaltStair.class, providerDomain, this::add);
        GTAPI.all(BlockBedrockFlower.class, providerDomain, this::add);
        this.add(GT5RBlocks.MINING_PIPE_THIN);
        this.add(GT5RBlocks.SOLID_SUPER_FUEL);
        this.add(GT5RBlocks.POWDER_BARREL);
        this.add(GT5RBlocks.BRONZE_CAULDRON);
        tables.put(GT5RBlocks.BRONZE_WATER_CAULDRON, b -> this.build(GT5RBlocks.BRONZE_CAULDRON));
        tables.put(GT5RBlocks.MINING_PIPE, b -> this.build(GT5RBlocks.MINING_PIPE_THIN));
        tables.put(Blocks.IRON_ORE, b -> createOreDropWithHammer(b, RAW_ORE.get(Iron), CRUSHED_ORE.get(Iron), 1));
        tables.put(Blocks.DEEPSLATE_IRON_ORE, b -> createOreDropWithHammer(b, RAW_ORE.get(Iron), CRUSHED_ORE.get(Iron), 1));
        tables.put(Blocks.LAPIS_ORE, b -> createOreDropWithHammer(b, RAW_ORE.get(Lapis), CRUSHED_ORE.get(Lapis), 6));
        tables.put(Blocks.DEEPSLATE_LAPIS_ORE, b -> createOreDropWithHammer(b, RAW_ORE.get(Lapis), CRUSHED_ORE.get(Lapis), 6));
        tables.put(Blocks.REDSTONE_ORE, b -> createOreDropWithHammer(b, RAW_ORE.get(Redstone), CRUSHED_ORE.get(Redstone), 5));
        tables.put(Blocks.DEEPSLATE_REDSTONE_ORE, b -> createOreDropWithHammer(b, RAW_ORE.get(Redstone), CRUSHED_ORE.get(Redstone), 5));
        tables.put(Blocks.DIAMOND_ORE, b -> createOreDropWithHammer(b, RAW_ORE.get(Diamond), CRUSHED_ORE.get(Diamond), 1));
        tables.put(Blocks.DEEPSLATE_DIAMOND_ORE, b -> createOreDropWithHammer(b, RAW_ORE.get(Diamond), CRUSHED_ORE.get(Diamond), 1));
        tables.put(Blocks.EMERALD_ORE, b -> createOreDropWithHammer(b, RAW_ORE.get(Emerald), CRUSHED_ORE.get(Emerald), 1));
        tables.put(Blocks.DEEPSLATE_EMERALD_ORE, b -> createOreDropWithHammer(b, RAW_ORE.get(Emerald), CRUSHED_ORE.get(Emerald), 1));
        tables.put(Blocks.COPPER_ORE, b -> createOreDropWithHammer(b, RAW_ORE.get(Copper), CRUSHED_ORE.get(Copper), 1));
        tables.put(Blocks.DEEPSLATE_COPPER_ORE, b -> createOreDropWithHammer(b, RAW_ORE.get(Copper), CRUSHED_ORE.get(Copper), 1));
        tables.put(Blocks.COAL_ORE, b -> createOreDropWithHammer(b, RAW_ORE.get(Coal), CRUSHED_ORE.get(Coal), 1));
        tables.put(Blocks.DEEPSLATE_COAL_ORE, b -> createOreDropWithHammer(b, RAW_ORE.get(Coal), CRUSHED_ORE.get(Coal), 1));
        tables.put(GT5RBlocks.BRITTLE_CHARCOAL, b -> createSingleItemTable(Items.CHARCOAL, UniformGenerator.between(1.0f, 2.0f)));
        tables.put(Blocks.ANCIENT_DEBRIS, b -> createOreDropWithHammer(b, RAW_ORE.get(NetheriteScrap), CRUSHED_ORE.get(NetheriteScrap), 1));
        tables.put(Blocks.ANDESITE, b -> createSingleItemTableWithSilkTouch(Blocks.ANDESITE, ((CobbleStoneType) VanillaStoneTypes.ANDESITE).getBlock("cobble")));
        tables.put(Blocks.DIORITE, b -> createSingleItemTableWithSilkTouch(Blocks.DIORITE, ((CobbleStoneType) VanillaStoneTypes.DIORITE).getBlock("cobble")));
        tables.put(Blocks.GRANITE, b -> createSingleItemTableWithSilkTouch(Blocks.GRANITE, ((CobbleStoneType) VanillaStoneTypes.GRANITE).getBlock("cobble")));
        if (GTAPI.isModLoaded(Ref.MOD_AE)){
            tables.put(AppliedEnergisticsRegistrar.getAe2Block("quartz_ore"), b -> createOreDrop(b, RAW_ORE.get(Materials.CertusQuartz)));
            tables.put(AppliedEnergisticsRegistrar.getAe2Block("deepslate_quartz_ore"), b -> createOreDrop(b, RAW_ORE.get(Materials.CertusQuartz)));
        }
        if (GTAPI.isModLoaded("ad_astra")){
            tables.put(SpaceModRegistrar.getSpaceBlock("mars_diamond_ore"), b -> createOreDropWithHammer(b, RAW_ORE.get(Diamond), CRUSHED_ORE.get(Diamond), 1));
        }
        tables.put(Blocks.GLASS, b -> createSilkDropWithHammer(b, Items.AIR, DUST.get(Glass), 9));
        tables.put(Blocks.GLASS_PANE, b -> createSilkDropWithHammer(b, Items.AIR, DUST.get(Glass), 1));
        for (DyeColor color : DyeColor.values()) {
            tables.put(RegistryUtils.getBlockFromId(new ResourceLocation(color.getSerializedName() + "_stained_glass")), b -> createSilkDropWithHammer(b, Items.AIR, DUST.get(Glass), 9));
            tables.put(RegistryUtils.getBlockFromId(new ResourceLocation(color.getSerializedName() + "_stained_glass_pane")), b -> createSilkDropWithHammer(b, Items.AIR, DUST.get(Glass), 1));
        }
    }
}
