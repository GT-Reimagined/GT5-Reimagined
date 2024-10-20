package muramasa.gregtech.proxy;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.client.ModelUtils;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import muramasa.gregtech.GT5RRef;
import muramasa.gregtech.GT5Reimagined;
import muramasa.gregtech.block.BlockCasing;
import muramasa.gregtech.block.BlockColoredWall;
import muramasa.gregtech.data.Machines;
import net.minecraft.client.renderer.RenderType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClientHandler {

    public static void setup() {
        AntimatterAPI.all(BlockCasing.class, t -> ModelUtils.INSTANCE.setRenderLayer(t, RenderType.cutout()));
        AntimatterAPI.all(BlockColoredWall.class, t -> ModelUtils.INSTANCE.setRenderLayer(t, RenderType.cutout()));
        ModelUtils.INSTANCE.setRenderLayer(Machines.NUCLEAR_REACTOR_CORE.getBlockState(Tier.NONE), RenderType.cutout());
        copyProgrammerArtIfMissing();
    }

    private static void copyProgrammerArtIfMissing() {
        writeResourcePack("GT5Reimagined-Old-Machine-Textures", "gt5u-machine-base");
        writeResourcePack("GT5Reimagined-New-Machine-Textures", "new-machine-base");
        writeResourcePack("New-Stone-Textures", "new-stone-textures");
        writeResourcePack("Former-Gui-Textures", "new-gui-textures");
    }

    private static void writeResourcePack(String writeName, String readName){
        File dir = new File(AntimatterPlatformUtils.INSTANCE.getConfigDir().getParent().toFile(), "resourcepacks");
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
