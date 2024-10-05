package muramasa.gregtech.client.fabric;

import muramasa.antimatter.client.fabric.IAntimatterClientInitializer;
import muramasa.gregtech.proxy.ClientHandler;

public class GT5RFabricClient implements IAntimatterClientInitializer {
    @Override
    public void onInitializeClient() {
        ClientHandler.setup();
    }
}
