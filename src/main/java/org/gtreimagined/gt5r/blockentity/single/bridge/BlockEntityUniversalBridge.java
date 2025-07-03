package org.gtreimagined.gt5r.blockentity.single.bridge;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gtcore.blockentity.BlockEntityRedstoneWire;
import org.gtreimagined.gtlib.blockentity.pipe.BlockEntityCable;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.tesseract.api.eu.EUHolder;

import static org.gtreimagined.gtlib.machine.Tier.IV;

public class BlockEntityUniversalBridge extends BlockEntityInventoryTankBridge {
    @Getter
    @Setter
    long holder;

    public BlockEntityUniversalBridge(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected boolean canBridgeBlockEntity(BlockEntity entity) {
        return super.canBridgeBlockEntity(entity) || entity instanceof BlockEntityRedstoneWire<?>;
    }

    @Override
    public void onLoad() {
        this.holder = EUHolder.create(this, 0);
        super.onLoad();
    }

    @Override
    public void onBlockUpdate(BlockPos neighbor) {
        super.onBlockUpdate(neighbor);
    }

    @Override
    public void onRemove() {
        super.onRemove();
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
        this.setHolder(EUHolder.create(this, 0));
    }


    @Override
    public double getLoss() {
        return 1;
    }

    @Override
    public int getAmps() {
        return 32;
    }

    @Override
    public long getVoltage() {
        return IV.getVoltage();
    }

    @Override
    public boolean insulated() {
        return true;
    }

    @Override
    public boolean connects(Direction direction) {
        return internalConnects(direction) && internalConnects(direction.getOpposite());
    }

    private boolean internalConnects(Direction side){
        return getCachedBlockEntity(side) instanceof BlockEntityCable<?> cable && cable.connects(side.getOpposite());
    }

    @Override
    public boolean validate(Direction dir) {
        return true;
    }

    @Override
    public boolean isActuallyNode() {
        return false;
    }
}
