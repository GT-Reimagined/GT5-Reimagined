package org.gtreimagined.gt5r.proxy;

import net.dries007.tfc.util.Helpers;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.registries.RegistryObject;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.integration.tfc.client.JavelinModel;
import org.gtreimagined.gt5r.integration.tfc.data.TFCToolTypes;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.client.ModelUtils;
import org.gtreimagined.gtlib.machine.Tier;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.loading.FMLPaths;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.block.BlockBedrockFlower;
import org.gtreimagined.gt5r.block.BlockCasing;
import org.gtreimagined.gt5r.block.BlockColoredWall;
import org.gtreimagined.gt5r.data.GT5RMachines;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.tool.IGTTool;
import org.gtreimagined.gtlib.util.Utils;

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

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(modelIdentifier("javelin_head"), JavelinModel::createHeadLayer);
        event.registerLayerDefinition(modelIdentifier("javelin_handle"), JavelinModel::createHandleLayer);
    }

    public static ModelLayerLocation modelIdentifier(String name, String part) {
        return new ModelLayerLocation(new ResourceLocation(GT5Reimagined.ID, name), part);
    }

    public static ModelLayerLocation modelIdentifier(String name) {
        return modelIdentifier(name, "main");
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
            InputStream in = GT5Reimagined.class.getResourceAsStream("/assets/" + GT5Reimagined.ID + "/" + readName + ".zip");
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

    public static void onTooltipEvent(ItemTooltipEvent event){
        ItemStack stack = event.getItemStack();
        if (stack.getItem() == GT5RItems.DataOrb){
            if (stack.getTag() != null){
                if (stack.getTag().contains("scanned_gt_material")){
                    String material = stack.getTag().getString("scanned_gt_material");
                    Material mat = Material.get(material);
                    if (mat != Material.NULL && mat.getElement() != null){
                        event.getToolTip().add(1, Utils.translatable("tooltip.gt5r.data_orb.elemental_scan"));
                        event.getToolTip().add(2, Utils.literal(mat.getChemicalFormula()));
                    }
                }
            }
        }
    }
}
