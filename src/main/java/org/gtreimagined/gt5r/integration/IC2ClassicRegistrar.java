package org.gtreimagined.gt5r.integration;

import org.gtreimagined.gtlib.AntimatterMod;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import net.minecraftforge.api.distmarker.Dist;

public class IC2ClassicRegistrar extends AntimatterMod {

    @Override
    public String getId() {
        return Ref.MOD_IC2C;
    }

    @Override
    public void onRegistrationEvent(RegistrationEvent event, Dist side) {
        //TODO
    }
}
