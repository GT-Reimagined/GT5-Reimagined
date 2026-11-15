package org.gtreimagined.gt5r.integration.railcraft;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.event.GTCraftingEvent;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import thedarkcolour.kotlinforforge.forge.ForgeKt;

public class RailcraftRegistrar extends GTMod {
    public RailcraftRegistrar(){
        if (isEnabled()){
            ForgeKt.getMOD_BUS().<GTCraftingEvent>addListener(e -> e.addLoader(RailcraftRecipes::initRecipes));
        }
    }
    @Override
    public void onRegistrationEvent(RegistrationEvent event, Dist side) {

    }

    @Override
    public boolean isEnabled() {
        return GTAPI.isModLoaded(getId());
    }

    @Override
    public String getId() {
        return "railcraft";
    }
}
