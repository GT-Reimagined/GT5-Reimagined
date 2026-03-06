package org.gtreimagined.gt5r.integration.forestry;

import forestry.api.apiculture.ForestryBeeSpecies;
import forestry.api.core.HumidityType;
import forestry.api.genetics.alleles.BeeChromosomes;
import forestry.api.genetics.alleles.ForestryAlleles;
import forestry.api.genetics.alleles.IAllele;
import forestry.api.plugin.IApicultureRegistration;
import forestry.api.plugin.IBeeSpeciesBuilder;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.util.Utils;

public enum GTBees {
    //FUELIS
    CLAY(GTGenera.FUELIS,"clay", 0x19d0ec, 0xe0c113){
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(ForestryRegistrar.getFRItem("bee_comb_honey")), 0.3f);
            speciesBuilder.addProduct(new ItemStack(Items.CLAY_BALL), 0.15f).setHumidity(HumidityType.DAMP);
            speciesBuilder.addMutations(m -> {
                m.add(ForestryBeeSpecies.INDUSTRIOUS, ForestryBeeSpecies.DILIGENT, 0.2f);
            });
        }
    },
    SLIME(GTGenera.FUELIS, "slime", 0x4e9e55, 0x00e012){
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.RESIN_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(ForestryRegistrar.getFRItem("bee_comb_mossy")), 0.3f);
            speciesBuilder.setHumidity(HumidityType.DAMP);
            speciesBuilder.addMutations(m -> {
                m.add(ForestryBeeSpecies.MARSHY, CLAY.getResourceLocation(), 0.15f);
            });
        }
    },
    LIGNITE(GTGenera.FUELIS, "lignite", 0x906237, 0x522d0a) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(ForestryRegistrar.getFRItem("bee_comb_honey")), 0.15f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.LIGNITE_COMB), 0.3f);
            speciesBuilder.addMutations(m -> {
                m.add(ForestryBeeSpecies.RURAL, CLAY.getResourceLocation(), 0.2f);
            });
        }
    },
    RUBBERY(GTGenera.FUELIS, "rubbery", 0x2e8f5b, 0xdcc289) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCoreItems.StickyResin), 0.15f);
            speciesBuilder.addProduct(new ItemStack(ForestryRegistrar.getFRItem("bee_comb_honey")), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(SLIME.getResourceLocation(), LIGNITE.getResourceLocation(), 0.25f);
            });
        }
    },
    COAL(GTGenera.FUELIS, "coal", 0x666666, 0x484848) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.LIGNITE_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.COAL_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(ForestryBeeSpecies.INDUSTRIOUS, LIGNITE.getResourceLocation(), 0.18f);
            });
        }
    },
    OIL(GTGenera.FUELIS, "oil", 0x4c4c4c, 0x2d2d2d) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.OIL_COMB), 0.15f);
            speciesBuilder.addProduct(new ItemStack(ForestryRegistrar.getFRItem("bee_comb_honey")), 0.15f);
            speciesBuilder.setHumidity(HumidityType.DAMP);
            speciesBuilder.setGenome(g -> {
                g.set(BeeChromosomes.ACTIVITY, ForestryAlleles.ACTIVITY_NOCTURNAL);
            });
            speciesBuilder.addMutations(m -> {
                m.add(COAL.getResourceLocation(), RUBBERY.getResourceLocation(), 0.08f);
            });
        }
    },
    //ORNAMENTIS
    REDSTONE(GTGenera.ORNAMENTIS, "redstone", 0x7d0f0f, 0xb81616) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.REDSTONE_COMB), 0.15f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.STONE_COMB), 0.3f);
            speciesBuilder.addMutations(m -> {
                m.add(ForestryBeeSpecies.INDUSTRIOUS, ForestryBeeSpecies.DEMONIC, 0.2f);
            });
        }
    },
    LAPIS(GTGenera.ORNAMENTIS, "lapis", 0x1947d1, 0x3e5fbf) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.LAPIS_COMB), 0.15f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.STONE_COMB), 0.3f);
            speciesBuilder.addMutations(m -> {
                m.add(ForestryBeeSpecies.DEMONIC, ForestryBeeSpecies.IMPERIAL, 0.2f);
            });
        }
    },
    CERTUS(GTGenera.ORNAMENTIS, "certus", 0x57cffb, 0xaedded) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.STONE_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.CERTUS_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(ForestryBeeSpecies.HERMITIC, LAPIS.getResourceLocation(), 0.2f);
            });
        }
    },
    RUBY(GTGenera.ORNAMENTIS, "ruby", 0xe6005c, 0xbe004c) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.STONE_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.RUBY_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(REDSTONE.getResourceLocation(), DIAMOND.getResourceLocation(), 0.1f);
            });
        }
    },
    SAPPHIRE(GTGenera.ORNAMENTIS, "sapphire", 0x0033cc, 0x002185) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.STONE_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.SAPPHIRE_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(CERTUS.getResourceLocation(), LAPIS.getResourceLocation(), 0.1f);
            });
        }
    },
    DIAMOND(GTGenera.ORNAMENTIS, "diamond", 0xccffff, 0xa3cccc) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.STONE_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.DIAMOND_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(CERTUS.getResourceLocation(), COAL.getResourceLocation(), 0.06f);
            });
        }
    },
    OLIVINE(GTGenera.ORNAMENTIS,"olivine", 0x248f24, 0xbeedbe) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.STONE_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.OLIVINE_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(CERTUS.getResourceLocation(), ForestryBeeSpecies.ENDED, 0.1f);
            });
        }
    },
    EMERALD(GTGenera.ORNAMENTIS, "emerald", 0x248f24, 0x2bab2b) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.STONE_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.EMERALD_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(OLIVINE.getResourceLocation(), DIAMOND.getResourceLocation(), 0.08f);
            });
        }
    },
    //METALIFERIS
    COPPER(GTGenera.METALIFERIS, "copper", 0xff6600, 0xca5100) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.COPPER_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(ForestryBeeSpecies.MAJESTIC, CLAY.getResourceLocation(), 0.25f);
            });
        }
    },
    TIN(GTGenera.METALIFERIS,"tin", 0xd4d4d4, 0xcdcdcd) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.TIN_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(ForestryBeeSpecies.DILIGENT, CLAY.getResourceLocation(), 0.25f);
            });
        }
    },
    LEAD(GTGenera.METALIFERIS,"lead", 0x666699, 0x9393b8) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.LEAD_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(COAL.getResourceLocation(), COPPER.getResourceLocation(), 0.25f);
            });
        }
    },
    IRON(GTGenera.METALIFERIS,"iron", 0xda9147, 0xde9c59) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.IRON_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(TIN.getResourceLocation(), COPPER.getResourceLocation(), 0.25f);
            });
        }
    },
    STEEL(GTGenera.METALIFERIS,"steel", 0x808080, 0x8a8a8a) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.STEEL_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(IRON.getResourceLocation(), COAL.getResourceLocation(), 0.20f);
            });
        }
    },
    NICKEL(GTGenera.METALIFERIS,"nickel", 0x8585ad, 0x7c7ca1) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.NICKEL_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(IRON.getResourceLocation(), COPPER.getResourceLocation(), 0.25f);
            });
        }
    },
    ZINC(GTGenera.METALIFERIS,"zinc", 0xf0def0, 0xe1d1e1) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.ZINC_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(IRON.getResourceLocation(), TIN.getResourceLocation(), 0.20f);
            });
        }
    },
    SILVER(GTGenera.METALIFERIS,"silver", 0xc2c2d6, 0xbfbfce) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.SILVER_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(LEAD.getResourceLocation(), TIN.getResourceLocation(), 0.20f);
            });
        }
    },
    GOLD(GTGenera.METALIFERIS,"gold", 0xebc633, 0xd6b840) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.GOLD_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(LEAD.getResourceLocation(), COPPER.getResourceLocation(), 0.20f);
            });
        }
    },
    //MINERALLIS
    ALUMINIUM(GTGenera.MINERALLIS,"aluminium", 0xb8b8ff, 0xc1c1e6) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.ALUMINIUM_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(NICKEL.getResourceLocation(), ZINC.getResourceLocation(), 0.18f);
            });
        }
    },
    TITANIUM(GTGenera.MINERALLIS, "titanium", 0xcc99ff, 0xccabed) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.TITANIUM_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(REDSTONE.getResourceLocation(), ALUMINIUM.getResourceLocation(), 0.05f);
            });
        }
    },
    CHROMIUM(GTGenera.MINERALLIS, "chromium", 0xeba1eb, 0xe1b5e1) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.CHROME_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(TITANIUM.getResourceLocation(), RUBY.getResourceLocation(), 0.05f)
                        .requireResource(GTMaterialTypes.BLOCK.get().get(Materials.Chromium).asState());
            });
        }
    },
    MANGANESE(GTGenera.MINERALLIS, "manganese", 0xd5d5d5, 0x999999) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.MAGANESE_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(TITANIUM.getResourceLocation(), ALUMINIUM.getResourceLocation(), 0.05f)
                        .requireResource(GTMaterialTypes.BLOCK.get().get(Materials.Manganese).asState());
            });
        }
    },
    TUNGSTEN(GTGenera.MINERALLIS, "tungsten", 0x5c5c8a, 0x717191) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.TUNGSTEN_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(ForestryBeeSpecies.HEROIC, MANGANESE.getResourceLocation(), 0.05f)
                        .requireResource(GTMaterialTypes.BLOCK.get().get(Materials.Tungsten).asState());
            });
        }
    },
    PLATINUM(GTGenera.MINERALLIS, "platinum", 0xe6e6e6, 0xededbe) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.PLATINUM_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(DIAMOND.getResourceLocation(), CHROMIUM.getResourceLocation(), 0.05f)
                        .requireResource(GTMaterialTypes.BLOCK.get().get(Materials.Platinum).asState());
            });
        }
    },
    IRIDIUM(GTGenera.MINERALLIS, "iridium", 0xdadada, 0xbcbcca) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.TUNGSTEN_COMB), 0.15f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.PLATINUM_COMB), 0.15f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.IRIDIUM_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(TUNGSTEN.getResourceLocation(), PLATINUM.getResourceLocation(), 0.05f)
                        .requireResource(GTMaterialTypes.BLOCK.get().get(Materials.Iridium).asState());
            });
        }
    },
    //CRITICALIS
    URANIUM(GTGenera.CRITICALIS, "uranium", 0x19af19, 0x149314) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.URANIUM_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(ForestryBeeSpecies.AVENGING, PLATINUM.getResourceLocation(), 0.05f)
                        .requireResource(GTMaterialTypes.BLOCK.get().get(Materials.Uranium).asState());
            });
        }
    },
    PLUTONIUM(GTGenera.CRITICALIS, "plutonium", 0x335c33, 0x688500) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.PLUTONIUM_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(URANIUM.getResourceLocation(), EMERALD.getResourceLocation(), 0.05f)
                        .requireResource(GTMaterialTypes.BLOCK.get().get(Materials.Plutonium).asState());
            });
        }
    },
    NAQUADAH(GTGenera.CRITICALIS, "naquadah", 0x003300, 0x002000) {
        @Override
        protected void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder) {
            speciesBuilder.addProduct(new ItemStack(GTCombs.SLAG_COMB), 0.3f);
            speciesBuilder.addProduct(new ItemStack(GTCombs.NAQUADAH_COMB), 0.15f);
            speciesBuilder.addMutations(m -> {
                m.add(PLUTONIUM.getResourceLocation(), IRIDIUM.getResourceLocation(), 0.03f)
                        .requireResource(GTMaterialTypes.BLOCK.get().get(Materials.Naquadah).asState());
            });
        }
    };

    private final GTGenera genus;
    final String id;
    final int primary, secondary;

    GTBees(GTGenera genus, String id, int primary, int secondary){
        this.genus = genus;
        this.id = "bee_" + id;
        this.primary = primary;
        this.secondary = secondary;
    }

    ResourceLocation getResourceLocation(){
        return id(this.id);
    }

    protected abstract void setSpeciesProperties(IBeeSpeciesBuilder speciesBuilder);

    public static ResourceLocation id(String id) {
        return new ResourceLocation(GT5Reimagined.ID, "bee_" + id);
    }

    public static void defineBees(IApicultureRegistration registration){
        //registration.registerSpecies()
        for(GTBees bee : GTBees.values()){
            IBeeSpeciesBuilder b = registration.registerSpecies(new ResourceLocation(GT5Reimagined.ID, bee.id), bee.genus.scientific, Utils.lowerUnderscoreToUpperSpaced(bee.id), true, TextColor.fromRgb(bee.secondary))
                    .setBody(TextColor.fromRgb(bee.primary)).setStripes(TextColor.fromRgb(bee.secondary)).setOutline(TextColor.fromRgb(bee.secondary));
            bee.setSpeciesProperties(b);
        }
    }
}
