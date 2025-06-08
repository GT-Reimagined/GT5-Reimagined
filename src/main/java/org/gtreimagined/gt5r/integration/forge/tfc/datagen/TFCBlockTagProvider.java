package org.gtreimagined.gt5r.integration.forge.tfc.datagen;

import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.datagen.providers.GTBlockTagProvider;
import org.gtreimagined.gtlib.ore.StoneType;
import net.dries007.tfc.common.TFCTags;
import net.minecraft.world.level.block.Block;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.ORE;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.SMALL_ORE;
import static org.gtreimagined.gtlib.data.VanillaStoneTypes.BEDROCK;

public class TFCBlockTagProvider extends GTBlockTagProvider {
    public TFCBlockTagProvider(String providerDomain, String providerName, boolean replace) {
        super(providerDomain, providerName, replace);
    }

    @Override
    protected void processTags(String domain) {
        super.processTags(domain);
        ORE.all().forEach(m -> {
            GTAPI.all(StoneType.class).stream().filter(s -> s.doesGenerateOre() && s != BEDROCK).forEach(s -> {
                Block ore = ORE.get().get(m, s).asBlock();
                Block smallOre = SMALL_ORE.get().get(m, s).asBlock();
                this.tag(TFCTags.Blocks.CAN_COLLAPSE).add(ore, smallOre);
                this.tag(TFCTags.Blocks.CAN_TRIGGER_COLLAPSE).add(ore, smallOre);
                this.tag(TFCTags.Blocks.MONSTER_SPAWNS_ON).add(ore, smallOre);
                this.tag(TFCTags.Blocks.PROSPECTABLE).add(ore, smallOre);
                this.tag(TFCTags.Blocks.CAN_START_COLLAPSE).add(ore, smallOre);
            });
        });
        /*for (Material material : TFCRegistrar.array) {
            Helpers.mapOfKeys(Ore.Grade.class, (grade) -> {
                return Helpers.mapOfKeys(Rock.class, (rock) -> {
                    Block ore = GT5Reimagined.get(Block.class, grade.name().toLowerCase() + "_" + rock.name().toLowerCase() + "_" + material.getId());
                    this.tag(TFCTags.Blocks.CAN_COLLAPSE).add(ore);
                    this.tag(TFCTags.Blocks.CAN_TRIGGER_COLLAPSE).add(ore);
                    this.tag(TFCTags.Blocks.MONSTER_SPAWNS_ON).add(ore);
                    this.tag(TFCTags.Blocks.PROSPECTABLE).add(ore);
                    this.tag(TFCTags.Blocks.CAN_START_COLLAPSE).add(ore);
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ore);
                    int oreMiningLevel = material.has(MaterialTags.MINING_LEVEL) ? MaterialTags.MINING_LEVEL.getInt(material) : 0;
                    if (oreMiningLevel > 0){
                        this.tag(fromMiningLevel(oreMiningLevel)).add(ore);
                    }
                    return true;
                });
            });
        }*/
    }
}
