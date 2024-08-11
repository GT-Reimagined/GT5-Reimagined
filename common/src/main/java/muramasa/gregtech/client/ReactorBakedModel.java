package muramasa.gregtech.client;

import com.google.common.collect.ImmutableMap;
import muramasa.antimatter.Ref;
import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.client.ModelUtils;
import muramasa.antimatter.client.baked.MachineBakedModel;
import muramasa.antimatter.machine.MachineState;
import muramasa.gregtech.blockentity.single.BlockEntityNuclearReactorCore;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ReactorBakedModel extends MachineBakedModel {
    private final BakedModel[] rodModels;
    public ReactorBakedModel(TextureAtlasSprite particle, ImmutableMap<MachineState, BakedModel[]> sides, BakedModel[] rodModels) {
        super(particle, sides);
        this.rodModels = rodModels;
    }

    @Override
    public List<BakedQuad> getBlockQuads(BlockState state, Direction side, Random rand, BlockAndTintGetter level, @NotNull BlockPos pos) {
        List<BakedQuad> superBlockQuads = super.getBlockQuads(state, side, rand, level, pos);
        if (side == Direction.UP){
            List<BakedQuad> list = new ArrayList<>();
            BlockEntity tile = level.getBlockEntity(pos);
            if (!(tile instanceof BlockEntityNuclearReactorCore core)) return Collections.emptyList();
            for (int i = 0; i < 4; i++) {
                ItemStack rod = core.getRod(i);
                if (rod.isEmpty()) continue;
                BakedModel model = rodModels[i];
                for (Direction dir : Ref.DIRS) {
                    list.addAll(ModelUtils.getQuadsFromBaked(model, state, dir, rand, level, pos));
                }
                list.addAll(ModelUtils.getQuadsFromBaked(model, state, null, rand, level, pos));
            }
            list.addAll(superBlockQuads);
            return list;
        }
        return superBlockQuads;
    }
}
