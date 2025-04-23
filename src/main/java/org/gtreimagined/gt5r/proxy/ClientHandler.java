package org.gtreimagined.gt5r.proxy;

import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.client.ModelUtils;
import org.gtreimagined.gtlib.machine.Tier;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.loading.FMLPaths;
import org.gtreimagined.gt5r.GT5RRef;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.block.BlockBedrockFlower;
import org.gtreimagined.gt5r.block.BlockCasing;
import org.gtreimagined.gt5r.block.BlockColoredWall;
import org.gtreimagined.gt5r.data.GT5RMachines;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClientHandler {

    public static void setup() {
        GTAPI.all(BlockCasing.class, t -> ModelUtils.setRenderLayer(t, RenderType.cutout()));
        GTAPI.all(BlockColoredWall.class, t -> ModelUtils.setRenderLayer(t, RenderType.cutout()));
        GTAPI.all(BlockBedrockFlower.class, t -> ModelUtils.setRenderLayer(t, RenderType.cutout()));
        ModelUtils.setRenderLayer(GT5RMachines.NUCLEAR_REACTOR_CORE.getBlockState(Tier.NONE), RenderType.cutout());
        copyProgrammerArtIfMissing();
    }

    private static void copyProgrammerArtIfMissing() {
        writeResourcePack("Classic-GT5-Machine-Textures", "classic-gt5-machine-textures");
        writeResourcePack("Old-GTI-Machine-Textures", "old-gti-machine-textures");
    }

    private static void writeResourcePack(String writeName, String readName){
        File dir = new File(FMLPaths.CONFIGDIR.get().getParent().toFile(), "resourcepacks");
        File target = new File(dir, writeName + ".zip");


        //if(!target.exists())
        try {
            dir.mkdirs();
            InputStream in = GT5Reimagined.class.getResourceAsStream("/assets/" + GT5RRef.ID + "/" + readName + ".zip");
            FileOutputStream out = new FileOutputStream(target);

            byte[] buf = new byte[16384];
            int len = 0;
            while((len = in.read(buf)) > 0)
                out.write(buf, 0, len);

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
