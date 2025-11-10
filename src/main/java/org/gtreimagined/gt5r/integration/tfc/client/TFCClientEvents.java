package org.gtreimagined.gt5r.integration.tfc.client;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.tfc.Metals;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = GT5Reimagined.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TFCClientEvents {
    @SubscribeEvent
    public static void onTextureStichPre(TextureStitchEvent.Pre event){
        if (event.getAtlas().location() != TextureAtlas.LOCATION_BLOCKS) return;
        Metals.METALS.forEach((m, i) -> {
            event.addSprite(new ResourceLocation(GT5Reimagined.ID, "block/metal/full/" + m.getId()));
        });
    }
}
