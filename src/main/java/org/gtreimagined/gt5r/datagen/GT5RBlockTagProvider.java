package org.gtreimagined.gt5r.datagen;

import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.datagen.providers.GTBlockTagProvider;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.util.RegistryUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import org.gtreimagined.gt5r.block.BlockAsphalt;
import org.gtreimagined.gt5r.block.BlockAsphaltSlab;
import org.gtreimagined.gt5r.block.BlockAsphaltStair;
import org.gtreimagined.gt5r.block.BlockCasing;
import org.gtreimagined.gt5r.block.BlockCoil;
import org.gtreimagined.gt5r.block.BlockColoredWall;
import org.gtreimagined.gt5r.block.BlockFakeCasing;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gt5r.data.GT5RMachines;
import org.gtreimagined.gt5r.data.GT5RTags;
import org.gtreimagined.gt5r.integration.AppliedEnergisticsRegistrar;

import static org.gtreimagined.gt5r.data.Materials.Wood;


public class GT5RBlockTagProvider extends GTBlockTagProvider {

    public GT5RBlockTagProvider(String providerDomain, String providerName, boolean replace) {
        super(providerDomain, providerName, replace);
    }

    @Override
    public void processTags(String domain){
        super.processTags(domain);
        GTAPI.all(BlockCasing.class, GT5Reimagined.ID, cas -> {
            if (cas.getId().contains("long_distance_wire")){
                this.tag(GTTools.WIRE_CUTTER.getToolType()).add(cas);
                return;
            }
            this.tag(GTTools.WRENCH.getToolType()).add(cas);
        });
        GTAPI.all(BlockColoredWall.class, GT5Reimagined.ID, cas -> {
            if (cas.getMaterial() == Wood){
                this.tag(GTTools.AXE.getToolType()).add(cas);
            } else {
                this.tag(GTTools.WRENCH.getToolType()).add(cas);
            }
        });
        GTAPI.all(BlockFakeCasing.class, GT5Reimagined.ID, cas -> {
            this.tag(GTTools.PICKAXE.getToolType()).add(cas);
        });
        for (DyeColor color : DyeColor.values()) {
            this.tag(GT5RTags.ASPHALT).add(RegistryUtils.getBlockFromId(new ResourceLocation(color.getName() + "_concrete")));
        }
        GTAPI.all(BlockAsphalt.class, GT5Reimagined.ID, cas -> {
            this.tag(GT5RTags.ASPHALT).add(cas);
            this.tag(GTTools.PICKAXE.getToolType()).add(cas);
        });
        GTAPI.all(BlockAsphaltSlab.class, GT5Reimagined.ID, cas -> {
            this.tag(GT5RTags.ASPHALT).add(cas);
            this.tag(GTTools.PICKAXE.getToolType()).add(cas);
        });
        GTAPI.all(BlockAsphaltStair.class, GT5Reimagined.ID, cas -> {
            this.tag(GT5RTags.ASPHALT).add(cas);
            this.tag(GTTools.PICKAXE.getToolType()).add(cas);
        });
        GTAPI.all(BlockCoil.class, GT5Reimagined.ID, cas -> {
            this.tag(GTTools.WRENCH.getToolType()).add(cas);
        });
        this.tag(GTTools.AXE.getToolType()).add(GT5RBlocks.BRITTLE_CHARCOAL, GT5RBlocks.POWDER_BARREL);
        this.tag(GTTools.PICKAXE.getToolType()).add(GT5RBlocks.MINING_PIPE, GT5RBlocks.MINING_PIPE_THIN, GT5RBlocks.SOLID_SUPER_FUEL, GT5RBlocks.BRONZE_CAULDRON, GT5RBlocks.BRONZE_WATER_CAULDRON);
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(GT5RMachines.MINIATURE_NETHER_PORTAL.getBlockState(Tier.NONE));
        if (GTAPI.isModLoaded(Ref.MOD_AE)){
            //TODO config for this
            this.tag(GTTools.WRENCH.getToolType()).add(AppliedEnergisticsRegistrar.getAe2Block("cable_bus"));
            this.tag(GTTools.PICKAXE.getToolType()).remove(AppliedEnergisticsRegistrar.getAe2Block("cable_bus"));
        }
    }
}
