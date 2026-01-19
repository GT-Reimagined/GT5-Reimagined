package org.gtreimagined.gt5r.integration.tfc.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.client.render.blockentity.JavelinItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gt5r.proxy.ClientHandler;
import org.gtreimagined.gtlib.registration.IColorHandler;
import org.gtreimagined.gtlib.util.CodeUtils;

public class GTJavelinItemRenderer extends BlockEntityWithoutLevelRenderer {
    private final ResourceLocation textureLocation;
    private final ResourceLocation overlayLocation;
    private final JavelinModel head = new JavelinModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientHandler.modelIdentifier("javelin_head")));
    private final JavelinModel handle = new JavelinModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientHandler.modelIdentifier("javelin_handle")));

    public GTJavelinItemRenderer(ResourceLocation textureLocation, ResourceLocation overlayLocation) {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.textureLocation = textureLocation;
        this.overlayLocation = overlayLocation;
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffers, int packedLight, int packedOverlay) {
        if (!(stack.getItem() instanceof IColorHandler colorHandler)) return;
        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);
        int rgbPrimary = colorHandler.getItemColor(stack, null, 0);
        int rgbSecondary = colorHandler.getItemColor(stack, null, 1);
        int rP = CodeUtils.getR(rgbPrimary), gP = CodeUtils.getG(rgbPrimary), bP = CodeUtils.getB(rgbPrimary);
        int rS = CodeUtils.getR(rgbSecondary), gS = CodeUtils.getG(rgbSecondary), bS = CodeUtils.getB(rgbSecondary);
        VertexConsumer buffer = ItemRenderer.getFoilBufferDirect(buffers, this.head.renderType(this.textureLocation), false, stack.hasFoil());
        this.head.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, rP / 255f, gP / 255f, bP / 255f, 1.0F);
        buffer = ItemRenderer.getFoilBufferDirect(buffers, this.handle.renderType(this.overlayLocation), false, stack.hasFoil());
        this.handle.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, rS / 255f, gS / 255f, bS / 255f, 1.0F);
        poseStack.popPose();
    }
}
