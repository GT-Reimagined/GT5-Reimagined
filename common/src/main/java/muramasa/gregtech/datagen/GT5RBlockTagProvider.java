package muramasa.gregtech.datagen;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.datagen.providers.AntimatterBlockTagProvider;
import muramasa.antimatter.machine.Tier;
import muramasa.gregtech.GT5RRef;
import muramasa.gregtech.block.*;
import muramasa.gregtech.data.GT5RBlocks;
import muramasa.gregtech.data.GT5RTags;
import muramasa.gregtech.data.Machines;
import net.minecraft.tags.BlockTags;

import static muramasa.antimatter.data.AntimatterMaterials.Wood;

public class GT5RBlockTagProvider extends AntimatterBlockTagProvider {

    public GT5RBlockTagProvider(String providerDomain, String providerName, boolean replace) {
        super(providerDomain, providerName, replace);
    }

    @Override
    public void processTags(String domain){
        super.processTags(domain);
        AntimatterAPI.all(BlockCasing.class, GT5RRef.ID, cas -> {
            if (cas.getId().contains("long_distance_wire")){
                this.tag(AntimatterDefaultTools.WIRE_CUTTER.getToolType()).add(cas);
                return;
            }
            this.tag(AntimatterDefaultTools.WRENCH.getToolType()).add(cas);
        });
        AntimatterAPI.all(BlockColoredWall.class, GT5RRef.ID, cas -> {
            if (cas.getMaterial() == Wood){
                this.tag(AntimatterDefaultTools.AXE.getToolType()).add(cas);
            } else {
                this.tag(AntimatterDefaultTools.WRENCH.getToolType()).add(cas);
            }
        });
        AntimatterAPI.all(BlockFakeCasing.class, GT5RRef.ID, cas -> {
            this.tag(AntimatterDefaultTools.PICKAXE.getToolType()).add(cas);
        });
        AntimatterAPI.all(BlockAsphalt.class, GT5RRef.ID, cas -> {
            this.tag(GT5RTags.ASPHALT).add(cas);
            this.tag(AntimatterDefaultTools.PICKAXE.getToolType()).add(cas);
        });
        AntimatterAPI.all(BlockAsphaltSlab.class, GT5RRef.ID, cas -> {
            this.tag(GT5RTags.ASPHALT).add(cas);
            this.tag(AntimatterDefaultTools.PICKAXE.getToolType()).add(cas);
        });
        AntimatterAPI.all(BlockAsphaltStair.class, GT5RRef.ID, cas -> {
            this.tag(GT5RTags.ASPHALT).add(cas);
            this.tag(AntimatterDefaultTools.PICKAXE.getToolType()).add(cas);
        });
        AntimatterAPI.all(BlockCoil.class, GT5RRef.ID, cas -> {
            this.tag(AntimatterDefaultTools.WRENCH.getToolType()).add(cas);
        });
        this.tag(AntimatterDefaultTools.AXE.getToolType()).add(GT5RBlocks.BRITTLE_CHARCOAL, GT5RBlocks.POWDER_BARREL);
        this.tag(AntimatterDefaultTools.PICKAXE.getToolType()).add(GT5RBlocks.MINING_PIPE, GT5RBlocks.MINING_PIPE_THIN, GT5RBlocks.SOLID_SUPER_FUEL);
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(Machines.MINIATURE_NETHER_PORTAL.getBlockState(Tier.NONE));
    }
}
