package org.gtreimagined.gt5r.integration.tfc.datagen;

import org.gtreimagined.gt5r.integration.tfc.ore.GTTFCOreBlock;
import org.gtreimagined.gt5r.integration.tfc.ore.GTTFCOreItem;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.datagen.providers.GTLanguageProvider;

import static org.gtreimagined.gtlib.util.Utils.lowerUnderscoreToUpperSpaced;

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
    }
}
