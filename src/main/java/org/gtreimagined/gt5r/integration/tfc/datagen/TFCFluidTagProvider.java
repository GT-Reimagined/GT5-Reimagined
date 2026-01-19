package org.gtreimagined.gt5r.integration.tfc.datagen;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.TFCTags.Fluids;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.util.Metal.Default;
import org.gtreimagined.gt5r.integration.tfc.data.TFCGTFluids;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.datagen.providers.GTFluidTagProvider;
import org.gtreimagined.gtlib.fluid.GTFluid;
import org.gtreimagined.gtlib.util.TagUtils;

public class TFCFluidTagProvider extends GTFluidTagProvider {
    public TFCFluidTagProvider() {
        super(Ref.MOD_TFC, "TFC Fluid Tags", false);
    }

    @Override
    protected void processTags(String domain) {
        super.processTags(domain);
        this.tag(TagUtils.getForgelikeFluidTag("salt_water")).add(TFCFluids.SALT_WATER.getSource());
        for (GTFluid fluid : TFCGTFluids.FLUIDS){
            this.tag(Fluids.USABLE_IN_INGOT_MOLD).add(fluid.getFluid());
            this.tag(Fluids.USABLE_IN_TOOL_HEAD_MOLD).add(fluid.getFluid());
        }
        this.tag(Fluids.USABLE_IN_TOOL_HEAD_MOLD).add(TFCFluids.METALS.get(Default.ROSE_GOLD).getSource(),
                TFCFluids.METALS.get(Default.STERLING_SILVER).getSource(),
                TFCFluids.METALS.get(Default.NICKEL).getSource(),
                TFCFluids.METALS.get(Default.GOLD).getSource());
    }
}
