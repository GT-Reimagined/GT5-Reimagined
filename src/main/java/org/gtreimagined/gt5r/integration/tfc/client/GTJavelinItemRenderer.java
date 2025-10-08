package org.gtreimagined.gt5r.integration.tfc.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.client.render.blockentity.JavelinItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class GTJavelinItemRenderer extends JavelinItemRenderer {
    private final ResourceLocation textureLocation;
    private final ResourceLocation overlayLocation;
    private final TridentModel model = new TridentModel(Minecraft.getInstance().getEntityModels().bakeLayer(RenderHelpers.modelIdentifier("javelin")));

    public GTJavelinItemRenderer(ResourceLocation textureLocation, ResourceLocation overlayLocation) {
        super(textureLocation);
        this.textureLocation = textureLocation;
        this.overlayLocation = overlayLocation;
    }

    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transforms, PoseStack poseStack, MultiBufferSource buffers, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);
        VertexConsumer buffer = ItemRenderer.getFoilBufferDirect(buffers, this.model.renderType(this.textureLocation), false, stack.hasFoil());
        this.model.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, 0.5F, 1.0F, 1.0F, 1.0F);
        buffer = ItemRenderer.getFoilBufferDirect(buffers, this.model.renderType(this.overlayLocation), false, stack.hasFoil());
        this.model.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
