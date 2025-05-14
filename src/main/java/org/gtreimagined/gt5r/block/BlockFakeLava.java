package org.gtreimagined.gt5r.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.registration.IItemBlockProvider;

public class BlockFakeLava extends LiquidBlock implements IItemBlockProvider {
    public BlockFakeLava() {
        super(() -> Fluids.LAVA, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.LAVA).noCollission().randomTicks().strength(100.0F).lightLevel((blockStatex) -> 15).noDrops());
        GTAPI.register(Block.class, "lava", GT5Reimagined.ID, this);
    }

    @Override
    public boolean generateItemBlock() {
       return false;
    }

    @Override
    public ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }
}
