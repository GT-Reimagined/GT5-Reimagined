package org.gtreimagined.gt5r.proxy;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.Pack.Position;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModFileInfo;
import org.gtreimagined.gt5r.GT5RConfig;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.integration.tfc.client.JavelinModel;
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
import org.gtreimagined.gtlib.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public class ClientHandler {

    public static void setup() {
        GTAPI.all(BlockCasing.class, t -> ModelUtils.setRenderLayer(t, RenderType.cutout()));
        GTAPI.all(BlockColoredWall.class, t -> ModelUtils.setRenderLayer(t, RenderType.cutout()));
        GTAPI.all(BlockBedrockFlower.class, t -> ModelUtils.setRenderLayer(t, RenderType.cutout()));
        ModelUtils.setRenderLayer(GT5RBlocks.BRONZE_CAULDRON, RenderType.cutout());
        ModelUtils.setRenderLayer(GT5RBlocks.BRONZE_WATER_CAULDRON, RenderType.cutout());
        ModelUtils.setRenderLayer(GT5RMachines.NUCLEAR_REACTOR_CORE.getBlockState(Tier.NONE), RenderType.cutout());
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

    public static void onPackEvent(AddPackFindersEvent event){
        if (event.getPackType() == PackType.CLIENT_RESOURCES) event.addRepositorySource(new ResourcePackFinder());
    }

    private static class ResourcePackFinder implements RepositorySource {

        @Override
        public void loadPacks(Consumer<Pack> consumer) {
            IModFileInfo modFile = ModList.get().getModContainerById(GT5Reimagined.ID).get().getModInfo().getOwningFile();

            var classicTextures = Pack.readMetaAndCreate(
                    GT5Reimagined.ID + ":classic_textures",
                    Utils.translatable("resourcePack." + GT5Reimagined.ID + ".classic_textures"),
                    false,
                    n -> new PathPackResources(GT5Reimagined.ID + ":classic_textures", modFile.getFile().findResource("texture_packs/classic-gt5-machine-textures/"), true),
                    PackType.CLIENT_RESOURCES,
                    Position.TOP,
                    PackSource.BUILT_IN);
            consumer.accept(classicTextures);
            var gtiTextures = Pack.readMetaAndCreate(
                    GT5Reimagined.ID + ":gti_textures",
                    Utils.translatable("resourcePack." + GT5Reimagined.ID + ".gti_textures"),
                    false,
                    n -> new PathPackResources(GT5Reimagined.ID + ":gti_textures", modFile.getFile().findResource("texture_packs/old-gti-machine-textures/"), true),
                    PackType.CLIENT_RESOURCES,
                    Position.TOP,
                    PackSource.BUILT_IN);
            consumer.accept(gtiTextures);
            if (GTAPI.isModLoaded("computercraft") && GT5RConfig.REPLACE_COMPUTER_TEXTURES.get() && GT5RConfig.GREGIFY_CC_RECIPES.get()){
                var ccTextures = Pack.readMetaAndCreate(
                        GT5Reimagined.ID + ":cc_textures",
                        Utils.translatable("resourcePack." + GT5Reimagined.ID + ".cc_textures"),
                        true,
                        n -> new PathPackResources(GT5Reimagined.ID + ":cc_textures", modFile.getFile().findResource("texture_packs/cc-tweaked-overrides/"), true),
                        PackType.CLIENT_RESOURCES,
                        Position.TOP,
                        PackSource.BUILT_IN);
                consumer.accept(ccTextures);
            }

        }
    }
}
