package org.gtreimagined.gt5r.integration.forge.tfc.datagen;

import org.gtreimagined.gtlib.AntimatterAPI;
import org.gtreimagined.gtlib.datagen.providers.AntimatterLanguageProvider;
import org.gtreimagined.gt5r.integration.forge.tfc.ore.GTTFCOreBlock;
import org.gtreimagined.gt5r.integration.forge.tfc.ore.GTTFCOreItem;

import static org.gtreimagined.gtlib.util.Utils.lowerUnderscoreToUpperSpaced;

public class TFCLangProvider extends AntimatterLanguageProvider {
    public TFCLangProvider(String providerDomain, String providerName, String locale) {
        super(providerDomain, providerName, locale);
    }

    @Override
    protected void english(String domain, String locale) {
        AntimatterAPI.all(GTTFCOreItem.class, domain).forEach(i -> {
            add(i, lowerUnderscoreToUpperSpaced(i.getId()));
        });
        AntimatterAPI.all(GTTFCOreBlock.class, domain).forEach(i -> {
            add(i, lowerUnderscoreToUpperSpaced(i.getId()));
        });
    }
}
