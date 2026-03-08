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

    @Override
    protected void overrides() {
        override(GT5Reimagined.ID, "item.gt5r.resin_comb", "Stickyresin Comb");
        override(GT5Reimagined.ID, "item.gt5r.copper_comb", "Coppon Comb");
        override(GT5Reimagined.ID, "item.gt5r.tin_comb", "Tine Comb");
        override(GT5Reimagined.ID, "item.gt5r.lead_comb", "Plumbilia Comb");
        override(GT5Reimagined.ID, "item.gt5r.iron_comb", "Ferru Comb");
        override(GT5Reimagined.ID, "item.gt5r.zinc_comb", "Galvania Comb");
        override(GT5Reimagined.ID, "item.gt5r.silver_comb", "Argentia Comb");
        override(GT5Reimagined.ID, "item.gt5r.gold_comb", "Aurelia Comb");
        override(GT5Reimagined.ID, "item.gt5r.aluminium_comb", "Bauxia Comb");
        override(GT5Reimagined.ID, "item.gt5r.manganese_comb", "Pyrolusium Comb");
        override(GT5Reimagined.ID, "item.gt5r.tungsten_comb", "Scheelinium Comb");
        override(GT5Reimagined.ID, "item.gt5r.platinum_comb", "Platina Comb");
        override(GT5Reimagined.ID, "item.gt5r.iridium_comb", "Quantaria Comb");
        override(GT5Reimagined.ID, "item.gt5r.uranium_comb", "Urania Comb");
    }
}
