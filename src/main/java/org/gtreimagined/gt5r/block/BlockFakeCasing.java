package org.gtreimagined.gt5r.block;

import org.gtreimagined.gtlib.block.BlockFakeTile;
import org.gtreimagined.gtlib.texture.Texture;

public class BlockFakeCasing extends BlockFakeTile {
    public BlockFakeCasing(String domain, String id, Properties properties) {
        super(domain, id, properties);
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(getDomain(), "block/casing/" + getRegistryName().getPath().replaceAll("casing_", ""))};
    }
}
