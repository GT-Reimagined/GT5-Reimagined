package org.gtreimagined.gt5r.integration.forestry;

import forestry.api.plugin.IApicultureRegistration;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.util.Utils;

public enum GTBees {
    //FUELIS
    CLAY(GTGenera.FUELIS,"clay", 0x19d0ec, 0xe0c113),
    SLIME(GTGenera.FUELIS, "slime", 0x4e9e55, 0x00e012),
    LIGNITE(GTGenera.FUELIS, "lignite", 0x906237, 0x522d0a),
    RUBBERY(GTGenera.FUELIS, "rubbery", 0x2e8f5b, 0xdcc289),
    COAL(GTGenera.FUELIS, "coal", 0x666666, 0x484848),
    OIL(GTGenera.FUELIS, "oil", 0x4c4c4c, 0x2d2d2d),
    //ORNAMENTIS
    REDSTONE(GTGenera.ORNAMENTIS, "redstone", 0x7d0f0f, 0xb81616),
    LAPIS(GTGenera.ORNAMENTIS, "lapis", 0x1947d1, 0x3e5fbf),
    CERTUS(GTGenera.ORNAMENTIS, "certus", 0x57cffb, 0xaedded),
    RUBY(GTGenera.ORNAMENTIS, "ruby", 0xe6005c, 0xbe004c),
    SAPPHIRE(GTGenera.ORNAMENTIS, "sapphire", 0x0033cc, 0x002185),
    DIAMOND(GTGenera.ORNAMENTIS, "diamond", 0xccffff, 0xa3cccc),
    OLIVINE(GTGenera.ORNAMENTIS,"olivine", 0x248f24, 0xbeedbe),
    EMERALD(GTGenera.ORNAMENTIS, "emerald", 0x248f24, 0x2bab2b),
    //METALIFERIS
    COPPER(GTGenera.METALIFERIS, "copper", 0xff6600, 0xca5100),
    TIN(GTGenera.METALIFERIS,"tin", 0xd4d4d4, 0xcdcdcd),
    LEAD(GTGenera.METALIFERIS,"lead", 0x666699, 0x9393b8),
    IRON(GTGenera.METALIFERIS,"iron", 0xda9147, 0xde9c59),
    STEEL(GTGenera.METALIFERIS,"steel", 0x808080, 0x8a8a8a),
    NICKEL(GTGenera.METALIFERIS,"nickel", 0x8585ad, 0x7c7ca1),
    ZINC(GTGenera.METALIFERIS,"zinc", 0xf0def0, 0xe1d1e1),
    SILVER(GTGenera.METALIFERIS,"silver", 0xc2c2d6, 0xbfbfce),
    GOLD(GTGenera.METALIFERIS,"gold", 0xebc633, 0xd6b840),
    //MINERALLIS
    ALUMINIUM(GTGenera.MINERALLIS,"aluminium", 0xb8b8ff, 0xc1c1e6),
    TITANIUM(GTGenera.MINERALLIS, "titanium", 0xcc99ff, 0xccabed),
    CHROMIUM(GTGenera.MINERALLIS, "chromium", 0xeba1eb, 0xe1b5e1),
    MANGANESE(GTGenera.MINERALLIS, "manganese", 0xd5d5d5, 0x999999),
    TUNGSTEN(GTGenera.MINERALLIS, "tungsten", 0x5c5c8a, 0x717191),
    PLATINUM(GTGenera.MINERALLIS, "platinum", 0xe6e6e6, 0xededbe),
    IRIDIUM(GTGenera.MINERALLIS, "iridium", 0xdadada, 0xbcbcca),
    //CRITICALIS
    URANIUM(GTGenera.CRITICALIS, "uranium", 0x19af19, 0x149314),
    PLUTONIUM(GTGenera.CRITICALIS, "plutonium", 0x335c33, 0x688500),
    NAQUADAH(GTGenera.CRITICALIS, "naquadah", 0x003300, 0x002000);

    private final GTGenera genus;
    final String id;
    final int primary, secondary;

    GTBees(GTGenera genus, String id, int primary, int secondary){
        this.genus = genus;
        this.id = "bee_" + id;
        this.primary = primary;
        this.secondary = secondary;
    }

    public static ResourceLocation id(String id) {
        return new ResourceLocation(GT5Reimagined.ID, "bee_" + id);
    }

    public static void defineBees(IApicultureRegistration registration){
        //registration.registerSpecies()
        for(GTBees bee : GTBees.values()){
            registration.registerSpecies(new ResourceLocation(GT5Reimagined.ID, bee.id), bee.genus.scientific, Utils.lowerUnderscoreToUpperSpaced(bee.id), true, TextColor.fromRgb(bee.secondary))
                    .setBody(TextColor.fromRgb(bee.primary)).setStripes(TextColor.fromRgb(bee.secondary)).setOutline(TextColor.fromRgb(bee.secondary));
        }
    }
}
