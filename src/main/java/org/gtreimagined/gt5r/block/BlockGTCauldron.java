package org.gtreimagined.gt5r.block;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;

public class BlockGTCauldron extends CauldronBlock implements IGTObject, IModelProvider {
    public BlockGTCauldron() {
        super(Properties.copy(Blocks.CAULDRON));
        GTAPI.register(BlockGTCauldron.class, this);
    }

    public void handlePrecipitation(BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation) {
        if (shouldHandlePrecipitation(level, precipitation)) {
            if (precipitation == Precipitation.RAIN) {
                level.setBlockAndUpdate(pos, GT5RBlocks.BRONZE_WATER_CAULDRON.defaultBlockState());
                level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
        }
    }

    protected boolean canReceiveStalactiteDrip(Fluid fluid) {
        return fluid == Fluids.WATER;
    }

    protected void receiveStalactiteDrip(BlockState state, Level level, BlockPos pos, Fluid fluid) {
        if (fluid == Fluids.WATER) {
            BlockState blockstate = GT5RBlocks.BRONZE_WATER_CAULDRON.defaultBlockState();
            level.setBlockAndUpdate(pos, blockstate);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(blockstate));
            level.levelEvent(1047, pos, 0);
        }
    }

    @Override
    public String getId() {
        return "bronze_cauldron";
    }

    @Override
    public String getDomain() {
        return GT5Reimagined.ID;
    }

    @Override
    public void onItemModelBuild(ItemLike item, GTItemModelProvider prov) {
        prov.tex(item, new ResourceLocation(GT5Reimagined.ID, "item/basic/bronze_cauldron"));
    }

    @Override
    public void onBlockModelBuild(Block block, GTBlockStateProvider prov) {
        prov.simpleBlock(block, () -> new ResourceLocation(GT5Reimagined.ID, "block/cauldron"));
    }
}

