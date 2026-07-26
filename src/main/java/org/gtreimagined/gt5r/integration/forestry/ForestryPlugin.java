package org.gtreimagined.gt5r.integration.forestry;

import forestry.api.plugin.IApicultureRegistration;
import forestry.api.plugin.IFarmingRegistration;
import forestry.api.plugin.IForestryPlugin;
import forestry.api.plugin.IGeneticRegistration;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gtcore.data.GTCoreItems;

public class ForestryPlugin implements IForestryPlugin {
    @Override
    public ResourceLocation id() {
        return new ResourceLocation(GT5Reimagined.ID, "forestry_plugin");
    }

    @Override
    public void registerGenetics(IGeneticRegistration genetics) {
        GTGenera.defineTaxa(genetics);
    }

    @Override
    public void registerApiculture(IApicultureRegistration apiculture) {
        GTBees.defineBees(apiculture);
    }

    @Override
    public void registerFarming(IFarmingRegistration farming) {
        farming.registerFertilizer(GTCoreItems.Fertilizer, 500);
    }
}
