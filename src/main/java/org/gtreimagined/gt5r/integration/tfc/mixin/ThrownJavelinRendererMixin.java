package org.gtreimagined.gt5r.integration.tfc.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.client.render.entity.ThrownJavelinRenderer;
import net.dries007.tfc.common.entities.misc.ThrownJavelin;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.gtreimagined.gt5r.integration.tfc.client.JavelinModel;
import org.gtreimagined.gt5r.integration.tfc.item.MaterialJavelin;
import org.gtreimagined.gt5r.proxy.ClientHandler;
import org.gtreimagined.gtlib.util.CodeUtils;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(print = true)
@OnlyIn(Dist.CLIENT)
@Mixin(ThrownJavelinRenderer.class)
public abstract class ThrownJavelinRendererMixin extends EntityRenderer<ThrownJavelin> {
    @Unique
    @Final
    @Mutable
    private JavelinModel head;
    @Unique
    @Final
    @Mutable
    private JavelinModel handle;

    protected ThrownJavelinRendererMixin(Context p_174008_) {
        super(p_174008_);
    }


    @Inject(method = "<init>", at = @At("TAIL"))
    private void gt5r$injectInit(Context context, CallbackInfo ci){
        head = new JavelinModel(context.bakeLayer(ClientHandler.modelIdentifier("javelin_head")));
        handle = new JavelinModel(context.bakeLayer(ClientHandler.modelIdentifier("javelin_handle")));
    }

    @Inject(method = "render(Lnet/dries007/tfc/common/entities/misc/ThrownJavelin;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true, remap = false)
    private void gt5r$injectRender(ThrownJavelin javelin, float ageInTicks, float pitch, PoseStack poseStack, MultiBufferSource buffers, int light, CallbackInfo ci){
        if (javelin.getItem().getItem() instanceof MaterialJavelin materialJavelin){
            poseStack.pushPose();
            int rgbPrimary = materialJavelin.getItemColor(javelin.getItem(), null, 0);
            int rgbSecondary = materialJavelin.getItemColor(javelin.getItem(), null, 1);
            int rP = CodeUtils.getR(rgbPrimary), gP = CodeUtils.getG(rgbPrimary), bP = CodeUtils.getB(rgbPrimary);
            int rS = CodeUtils.getR(rgbSecondary), gS = CodeUtils.getG(rgbSecondary), bS = CodeUtils.getB(rgbSecondary);
            float degrees = Mth.lerp(pitch, javelin.yRotO, javelin.getYRot()) - 90.0F;
            poseStack.mulPose(Axis.YP.rotationDegrees(degrees));
            float degrees1 = Mth.lerp(pitch, javelin.xRotO, javelin.getXRot()) + 90.0F;
            poseStack.mulPose(Axis.ZP.rotationDegrees(degrees1));
            VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(buffers, this.head.renderType(this.getTextureLocation(javelin)), false, javelin.isEnchantGlowing());
            this.head.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, rP / 255F, gP / 255F, bP / 255F, 1.0F);
            vertexconsumer = ItemRenderer.getFoilBufferDirect(buffers, this.handle.renderType(MaterialJavelin.OVERLAY_LOCATION), false, javelin.isEnchantGlowing());
            this.handle.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, rS / 255F, gS / 255F, bS / 255F, 1.0F);
            poseStack.popPose();
            super.render(javelin, ageInTicks, pitch, poseStack, buffers, light);
            ci.cancel();
        }
    }
}
