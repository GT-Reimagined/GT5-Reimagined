package org.gtreimagined.gt5r.integration.tfc.ore;

import org.gtreimagined.gtlib.item.ItemBasic;
import org.gtreimagined.gtlib.texture.Texture;

public class GTTFCOreItem extends ItemBasic<GTTFCOreItem> {
    public GTTFCOreItem(String domain, String id) {
        super(domain, id);
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(domain, "item/tfc/ore/" + getId())};
    }
}
