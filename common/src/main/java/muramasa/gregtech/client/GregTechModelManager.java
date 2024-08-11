package muramasa.gregtech.client;

import muramasa.antimatter.Ref;
import muramasa.gregtech.GTIRef;
import net.minecraft.resources.ResourceLocation;

public class GregTechModelManager {

    public static final ResourceLocation LOADER_REACTOR = new ResourceLocation(GTIRef.ID, "reactor");

    public static void init(){
        new ReactorModelLoader(LOADER_REACTOR);
    }
}
