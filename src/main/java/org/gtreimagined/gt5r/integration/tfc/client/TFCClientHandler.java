package org.gtreimagined.gt5r.integration.tfc.client;

import net.dries007.tfc.util.Helpers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.tfc.data.TFCToolTypes;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.tool.IGTTool;

public class TFCClientHandler {
    public static void setup(){
        GTAPI.all(IGTTool.class, GT5Reimagined.ID).stream().filter(t -> t.getGTToolType() == TFCToolTypes.JAVELIN).forEach(j -> {
            Item javelin =j.getItem();
            ItemProperties.register(javelin, Helpers.identifier("throwing"), (stack, level, entity, unused) ->
                    entity != null && ((entity.isUsingItem() && entity.getUseItem() == stack) || (entity instanceof Monster monster && monster.isAggressive())) ? 1.0F : 0.0F
            );
        });
    }
}
