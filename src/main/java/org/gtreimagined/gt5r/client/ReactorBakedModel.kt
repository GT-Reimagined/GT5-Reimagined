package org.gtreimagined.gt5r.client;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import org.gtreimagined.gtlib.GTLibProperties;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.client.ModelUtils;
import org.gtreimagined.gtlib.client.baked.MachineBakedModel;
import org.gtreimagined.gtlib.client.quad.ITextureReferenceBakedQuad;
import org.gtreimagined.gtlib.client.quad.RetexturedBakedQuad;
import org.gtreimagined.gtlib.cover.ICover;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.texture.Texture;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.blockentity.single.BlockEntityNuclearReactorCore;
import org.gtreimagined.gt5r.data.GT5RCovers;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class ReactorBakedModel extends MachineBakedModel {
    private final BakedModel[] rodModels;
    public ReactorBakedModel(TextureAtlasSprite particle, ImmutableMap<MachineState, BakedModel[]> sides, BakedModel[] rodModels) {
        super(particle, sides);
        this.rodModels = rodModels;
    }

    @Override
    public List<BakedQuad> getBlockQuads(BlockState state, Direction side, RandomSource rand, BlockAndTintGetter level, @NotNull BlockPos pos) {
        if (side == null) {
            return Collections.emptyList();
        }
        BlockEntity tile = level.getBlockEntity(pos);
        if (!(tile instanceof BlockEntityNuclearReactorCore core)) return Collections.emptyList();
        GTLibProperties.MachineProperties props = getMachineProperty(core);
        if (props == null) return Collections.emptyList();
        List<BakedQuad> superBlockQuads = new ObjectArrayList<>(20);
        List<BakedQuad> coverQuads = getCoverQuads(state, side, rand, props, core, level, pos);
        ICover cover = props.covers[side.get3DDataValue()];
        boolean isOutputCover = cover.getFactory() == GT5RCovers.COVER_REACTOR_OUTPUT || cover.getFactory() == GT5RCovers.COVER_REACTOR_OUTPUT_SECONDARY;
        BakedModel model = getModel(state, side, props.state, props.type);
        for (Direction dir : Ref.DIRS) {
            superBlockQuads.addAll(ModelUtils.getQuadsFromBaked(model, state, dir, rand, level, pos));
        }
        superBlockQuads.addAll(ModelUtils.getQuadsFromBaked(model, state, null, rand, level, pos));
        if (!coverQuads.isEmpty()) {
            if (isOutputCover) {
                return coverQuads;
            }
            Function<Direction, Texture> ft = core.getMultiTexture();
            for (int i = 0; i < superBlockQuads.size(); i++) {
                BakedQuad quad = superBlockQuads.get(i);
                if (((ITextureReferenceBakedQuad)quad).gtLib$getTextureId().equals("#base") && quad.getDirection() == side.getOpposite())
                    coverQuads.add(new RetexturedBakedQuad(quad, ModelUtils.getSprite(ft.apply(side))));
            }
            return coverQuads;
        }

        if (side == Direction.UP){
            List<BakedQuad> list = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                ItemStack rod = core.getRod(i);
                if (rod.isEmpty()) continue;
                BakedModel rodModel = rodModels[i];
                for (Direction dir : Ref.DIRS) {
                    list.addAll(ModelUtils.getQuadsFromBaked(rodModel, state, dir, rand, level, pos));
                }
                list.addAll(ModelUtils.getQuadsFromBaked(rodModel, state, null, rand, level, pos));
            }
            list.addAll(superBlockQuads);
            return list;
        }
        return superBlockQuads;
    }
}
