package org.gtreimagined.gt5r.client;

import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gt5r.GT5Reimagined;

public class GT5RModelManager {

    public static final ResourceLocation LOADER_REACTOR = new ResourceLocation(GT5Reimagined.ID, "reactor");
    public static final ResourceLocation LOADER_BATHING_POT = new ResourceLocation(GT5Reimagined.ID, "bathing_pot");

    public static void init(){
        new ReactorModelLoader(LOADER_REACTOR);
        BathingPotModelLoader.init();
    }
}
