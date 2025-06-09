package org.gtreimagined.gt5r.machine;

import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.machine.MachineFlag;
import org.gtreimagined.gtlib.machine.types.BasicMachine;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gt5r.data.Textures;

import static org.gtreimagined.gtlib.machine.MachineFlag.UNCULLED;
import static org.gtreimagined.gtlib.machine.Tier.NONE;

public class MiniPortalMachine extends BasicMachine {
    public MiniPortalMachine(String domain, String id) {
        super(domain, id);
        this.setTiers(NONE);
        this.addFlags(UNCULLED);
        this.setNoOutputCover().setAllowsFrontIO();
        this.setItemModelParent(new ResourceLocation(GT5Reimagined.ID, "block/mini_portal"));
        this.setCustomModel(Textures.MINI_PORTAL);
        this.removeFlags(MachineFlag.COVERABLE);
        this.setToolTag(GTTools.PICKAXE.getToolType());
    }
}
