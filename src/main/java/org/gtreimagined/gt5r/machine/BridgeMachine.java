package org.gtreimagined.gt5r.machine;

import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.Textures;
import org.gtreimagined.gtlib.machine.types.BasicMachine;
import org.gtreimagined.gtlib.texture.Texture;

import static org.gtreimagined.gtlib.machine.Tier.NONE;

public class BridgeMachine extends BasicMachine {
    public BridgeMachine(String domain, String id) {
        super(domain, id);
        this.setTiers(NONE).noOutputCover().setNoFacing(true);
        this.overlayTexture(Textures.BRIDGE).baseTexture(new Texture(GT5Reimagined.ID, "block/machine/base/bridge"));
    }
}
