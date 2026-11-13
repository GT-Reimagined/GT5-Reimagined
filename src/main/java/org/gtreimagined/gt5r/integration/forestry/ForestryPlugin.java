package org.gtreimagined.gt5r.integration.forestry;

import forestry.api.farming.ForestryFarmTypes;
import forestry.api.plugin.IApicultureRegistration;
import forestry.api.plugin.IFarmingRegistration;
import forestry.api.plugin.IForestryPlugin;
import forestry.api.plugin.IGeneticRegistration;
import forestry.farming.logic.farmables.FarmableSapling;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.data.GTCoreItems;

import java.util.List;

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
        farming.modifyFarmType(ForestryFarmTypes.ARBOREAL, f -> {
            f.addWindfallFarmable(GTCoreBlocks.RUBBER_SAPLING.asItem(), FarmableSapling::new, b -> b.addWindfall(List.of(Items.STICK, GTCoreItems.StickyResin)));
        });
    }
}
