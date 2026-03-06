package org.gtreimagined.gt5r.integration.forestry;

import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.item.ItemBasic;
import org.gtreimagined.gtlib.texture.Texture;

public class ItemGTComb extends ItemBasic<ItemGTComb> {
    public ItemGTComb(String id) {
        super(GT5Reimagined.ID, id + "_comb", "combs/");
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(domain, "item/basic/" + subDir  + getId().replace("_comb", ""))};
    }
}
