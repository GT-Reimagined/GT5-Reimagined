package muramasa.gti.tree;

import muramasa.antimatter.block.BlockBasic;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.texture.Texture;
import muramasa.antimatter.util.Utils;
import muramasa.gti.Ref;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Containers;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.Random;

import static muramasa.antimatter.Data.DUST;
import static muramasa.antimatter.Data.HAMMER;
import static muramasa.gti.data.Materials.RawRubber;

public class BlockRubberLog extends BlockBasic {

    final static EnumProperty<ResinState> RESIN_STATE = EnumProperty.create("resin_state", ResinState.class);
    final static DirectionProperty RESIN_FACING = BlockStateProperties.HORIZONTAL_FACING;
    final static EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    final static double CHANCE_FILL = 0.3;

    public BlockRubberLog(String domain, String id) {
        super(domain, id, Block.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD).randomTicks());
        registerDefaultState(defaultBlockState().setValue(RESIN_STATE, ResinState.NONE).setValue(RESIN_FACING, Direction.NORTH).setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(RESIN_STATE, RESIN_FACING, AXIS);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);

        if (state.getValue(RESIN_STATE) != ResinState.EMPTY) {
            return;
        }
        if (random.nextDouble() < CHANCE_FILL) {
            worldIn.setBlock(pos, state.setValue(RESIN_STATE, ResinState.FILLED), 3);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn.isClientSide || state.getValue(RESIN_STATE) != ResinState.FILLED) {
            return InteractionResult.PASS;
        }
        if (Utils.isPlayerHolding(player, handIn, HAMMER)) {
            worldIn.setBlock(pos, state.setValue(RESIN_STATE, ResinState.EMPTY), 3);
            Direction dir = state.getValue(RESIN_FACING);
            BlockPos spawnPos = pos.offset(dir.getStepX(), dir.getStepY(), dir.getStepZ());
            Containers.dropItemStack(worldIn, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), DUST.get(RawRubber, 1));
            if (worldIn.random.nextDouble() > 0.5) {
                Containers.dropItemStack(worldIn, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), DUST.get(RawRubber, 1));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()).setValue(RESIN_FACING, context.getHorizontalDirection().getOpposite()).setValue(RESIN_STATE, ResinState.NONE);
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(getRegistryName().getNamespace(), "block/tree/rubber_log")};
    }

    @Override
    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        ModelFile rubberLog = prov.existing(Ref.ID, "block/rubber_log");
        ModelFile rubberLogEmpty = prov.existing(Ref.ID, "block/rubber_log_empty");
        ModelFile rubberLogFilled = prov.existing(Ref.ID, "block/rubber_log_filled");
        prov.getVariantBuilder(block).forAllStates(s ->
                ConfiguredModel.builder().modelFile(s.getValue(RESIN_STATE) == ResinState.NONE ? rubberLog : s.getValue(RESIN_STATE) == ResinState.EMPTY ? rubberLogEmpty : rubberLogFilled)
                        .rotationY((int) s.getValue(RESIN_FACING).getOpposite().toYRot())
                        .rotationX(s.getValue(AXIS) == Direction.Axis.Y ? 0 : 90).build()
        );
    }
}
