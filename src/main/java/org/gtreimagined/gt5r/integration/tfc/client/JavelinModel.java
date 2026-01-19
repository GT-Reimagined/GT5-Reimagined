package org.gtreimagined.gt5r.integration.tfc.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class JavelinModel extends Model {
    private final ModelPart root;

    public JavelinModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.root = root;
    }

    public static LayerDefinition createHeadLayer()
    {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 4).addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -20.0F, 0.0F, 1.5708F, -0.7854F, -3.1416F));

        PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 4).addBox(0.5F, -1.0F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.0F, -1.5F, 0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -23.5F, 1.0F, 0.0F, 1.5708F, 1.5708F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public static LayerDefinition createHandleLayer()
    {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition stick_r1 = partdefinition.addOrReplaceChild("stick_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, 8.0F, 1.0F, 1.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, 1.5708F, 1.5708F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
