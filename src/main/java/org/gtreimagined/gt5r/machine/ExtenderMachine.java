package org.gtreimagined.gt5r.machine;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.Property;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RCovers;
import org.gtreimagined.gt5r.data.Textures;
import org.gtreimagined.gtlib.machine.types.BasicMachine;
import org.gtreimagined.gtlib.texture.Texture;

import static org.gtreimagined.gtlib.machine.Tier.NONE;

public class ExtenderMachine extends BasicMachine {
    public ExtenderMachine(String domain, String id) {
        super(domain, id);
        this.setTiers(NONE).covers(GT5RCovers.COVER_OUTPUT_EXTENDER).setVerticalFacingAllowed(true).frontCovers().outputCoversOnFacing(false);
        this.overlayTexture(Textures.EXTENDER).baseTexture(new Texture(GT5Reimagined.ID, "block/machine/base/bridge"));
    }

    @Override
    public Direction handlePlacementFacing(BlockPlaceContext ctxt, Property<?> which, Direction dir) {
       return dir.getOpposite();
    }
}
