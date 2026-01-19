package org.gtreimagined.gt5r.integration.tfc.datagen;

import org.gtreimagined.gt5r.integration.tfc.Metals;
import org.gtreimagined.gt5r.integration.tfc.ore.GTTFCOreBlock;
import org.gtreimagined.gt5r.integration.tfc.ore.GTTFCOreItem;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.datagen.providers.GTLanguageProvider;
import org.gtreimagined.gtlib.ore.BlockOre;

import static org.gtreimagined.gtlib.util.Utils.*;
import static org.gtreimagined.gtlib.util.Utils.getLocalizeStoneType;
import static org.gtreimagined.gtlib.util.Utils.getLocalizedType;

public class TFCLangProvider extends GTLanguageProvider {
    public TFCLangProvider(String providerDomain, String providerName, String locale) {
        super(providerDomain, providerName, locale);
    }

    @Override
    protected void english(String domain, String locale) {
        GTAPI.all(GTTFCOreItem.class, domain).forEach(i -> {
            add(i, lowerUnderscoreToUpperSpaced(i.getId()));
        });
        GTAPI.all(GTTFCOreBlock.class, domain).forEach(i -> {
            add(i, lowerUnderscoreToUpperSpaced(i.getId()));
        });
        GTAPI.all(BlockOre.class, o -> {
            String nativeSuffix = o.getMaterial().getElement() != null ? "Native " : "";
            add("block.antimatter_shared." + o.getDescriptionId() + ".prospected", String.join("", nativeSuffix, getLocalizedType(o.getMaterial())));
        });
        Metals.METALS.forEach((m, i) -> {
            add("metal.gt5r." + m.getId(), m.getDisplayNameString());
        });
    }
}
