package org.gtreimagined.gt5r;

import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.event.MaterialEvent;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import net.minecraftforge.api.distmarker.Dist;
import org.gtreimagined.gt5r.data.GT5RMaterialEvents;
import org.gtreimagined.gt5r.material.GregTechMaterialEvent;

public class GT5RPostRegistrar extends GTMod {
    @Override
    public String getId() {
        return GT5RRef.ID + "_post";
    }

    @Override
    public void onRegistrationEvent(RegistrationEvent event, Dist side) {

    }

    @Override
    public void onMaterialEvent(MaterialEvent event) {
        event = new GregTechMaterialEvent();
        GT5RMaterialEvents.onMaterialEvent((GregTechMaterialEvent) event);
    }

    @Override
    public int getPriority() {
        return 600;
    }
}
