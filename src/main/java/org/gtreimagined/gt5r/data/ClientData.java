package org.gtreimagined.gt5r.data;

import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.gui.container.ContainerMachine;
import org.gtreimagined.gtlib.gui.container.ContainerMultiMachine;
import net.minecraft.client.gui.screens.MenuScreens;
import org.gtreimagined.gt5r.gui.ScreenFusionReactor;

public class ClientData {
    public final static MenuScreens.ScreenConstructor SCREEN_FUSION_REACTOR = GTAPI.register(MenuScreens.ScreenConstructor.class, "fusion_reactor", GT5Reimagined.ID,(MenuScreens.ScreenConstructor)(a, b, c) -> new ScreenFusionReactor<>((ContainerMultiMachine) a,b,c));

    public static void init() {
    }
}
