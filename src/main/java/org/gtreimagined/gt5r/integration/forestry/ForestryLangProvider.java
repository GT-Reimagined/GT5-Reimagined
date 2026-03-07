package org.gtreimagined.gt5r.integration.forestry;

import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.datagen.providers.GTLanguageProvider;

public class ForestryLangProvider extends GTLanguageProvider {
    public ForestryLangProvider() {
        super(GT5Reimagined.ID, GT5Reimagined.NAME + "en_us Localization for Forestry", "en_us");
    }

    @Override
    protected void addTranslations() {
        for (GTBees bee : GTBees.values()) {
            add("allele." + Ref.MOD_FR + ".bee_species." + GT5Reimagined.ID + "." + bee.id, bee.name);
        }
    }
}
