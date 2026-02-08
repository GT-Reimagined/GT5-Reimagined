package org.gtreimagined.gt5r.block;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.datagen.builder.VariantBlockStateBuilder.VariantBuilder;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.registration.IColorHandler;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IItemBlockProvider;
import org.gtreimagined.gtlib.registration.IModelProvider;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Predicate;

public class BlockGTWaterCauldron extends LayeredCauldronBlock implements IGTObject, IItemBlockProvider, IModelProvider, IColorHandler {
    public BlockGTWaterCauldron() {
        super(Properties.copy(Blocks.WATER_CAULDRON), LayeredCauldronBlock.RAIN, CauldronInteraction.WATER);
        GTAPI.register(BlockGTWaterCauldron.class, this);
    }

    @Override
    public String getDomain() {
        return GT5Reimagined.ID;
    }

    @Override
    public String getId() {
        return "bronze_water_cauldron";
    }

    @Override
    public boolean generateItemBlock() {
        return false;
    }

    @Override
    public void onBlockModelBuild(Block block, GTBlockStateProvider prov) {
        prov.getVariantBuilder(block).forAllStates(state -> {
            int level = state.getValue(BlockGTWaterCauldron.LEVEL);
            String suffix = level == 3 ? "full" : "level" + (level);
            return new VariantBuilder().modelFile(new ResourceLocation(GT5Reimagined.ID, "block/water_cauldron_" + suffix));
        });
    }

    @Override
    public int getBlockColor(BlockState state, @Nullable BlockGetter world, @Nullable BlockPos pos, int i) {
        if (world instanceof BlockAndTintGetter level && pos != null && i == 0){
            return BiomeColors.getAverageWaterColor(level, pos);
        }
        return IColorHandler.super.getBlockColor(state, world, pos, i);
    }
}
