package org.gtreimagined.gt5r.integration.forestry;

import forestry.api.genetics.ForestryTaxa;
import forestry.api.genetics.alleles.BeeChromosomes;
import forestry.api.genetics.alleles.ForestryAlleles;
import forestry.api.plugin.IGeneticRegistration;
import forestry.api.plugin.ITaxonBuilder;

public enum GTGenera {
    FUELIS("Fuelis"){
        @Override
        protected void setBranchProperties(ITaxonBuilder taxonBuilder) {
            taxonBuilder.setDefaultChromosome(BeeChromosomes.TEMPERATURE_TOLERANCE, ForestryAlleles.TOLERANCE_UP_2);
            taxonBuilder.setDefaultChromosome(BeeChromosomes.POLLINATION, ForestryAlleles.POLLINATION_SLOW);
            taxonBuilder.setDefaultChromosome(BeeChromosomes.FLOWER_TYPE, ForestryAlleles.FLOWER_TYPE_MUSHROOMS);
        }
    },
    ORNAMENTIS("Ornamentis"){
        @Override
        protected void setBranchProperties(ITaxonBuilder taxonBuilder) {
            taxonBuilder.setDefaultChromosome(BeeChromosomes.POLLINATION, ForestryAlleles.POLLINATION_AVERAGE);
        }
    },
    METALIFERIS("Metaliferis"){
        @Override
        protected void setBranchProperties(ITaxonBuilder taxonBuilder) {
            taxonBuilder.setDefaultChromosome(BeeChromosomes.TEMPERATURE_TOLERANCE, ForestryAlleles.TOLERANCE_DOWN_2);
            taxonBuilder.setDefaultChromosome(BeeChromosomes.FLOWER_TYPE, ForestryAlleles.FLOWER_TYPE_JUNGLE);
            taxonBuilder.setDefaultChromosome(BeeChromosomes.POLLINATION, ForestryAlleles.POLLINATION_SLOWER);
        }
    },
    MINERALLIS("Minerallis"){
        @Override
        protected void setBranchProperties(ITaxonBuilder taxonBuilder) {
            taxonBuilder.setDefaultChromosome(BeeChromosomes.TEMPERATURE_TOLERANCE, ForestryAlleles.TOLERANCE_DOWN_1);
            taxonBuilder.setDefaultChromosome(BeeChromosomes.FLOWER_TYPE, ForestryAlleles.FLOWER_TYPE_CACTI);
            taxonBuilder.setDefaultChromosome(BeeChromosomes.POLLINATION, ForestryAlleles.POLLINATION_FAST);
        }
    },
    CRITICALIS("Criticalis"){
        @Override
        protected void setBranchProperties(ITaxonBuilder taxonBuilder) {
            taxonBuilder.setDefaultChromosome(BeeChromosomes.POLLINATION, ForestryAlleles.POLLINATION_AVERAGE);
            taxonBuilder.setDefaultChromosome(BeeChromosomes.ACTIVITY, ForestryAlleles.ACTIVITY_NOCTURNAL);
            taxonBuilder.setDefaultChromosome(BeeChromosomes.FLOWER_TYPE, ForestryAlleles.FLOWER_TYPE_END);
        }
    };


    final String scientific;

    GTGenera(String scientific){
        this.scientific = scientific;
    }

    protected abstract void setBranchProperties(ITaxonBuilder taxonBuilder);

    public static void defineTaxa(IGeneticRegistration genetics){
        genetics.defineTaxon(ForestryTaxa.CLASS_INSECTS, ForestryTaxa.ORDER_HYMNOPTERA, order -> {
            order.defineSubTaxon(ForestryTaxa.FAMILY_BEES, family -> {
                for (GTGenera genus : GTGenera.values()){
                    family.defineSubTaxon(genus.scientific, genus::setBranchProperties);
                }
            });
        });
    }
}
