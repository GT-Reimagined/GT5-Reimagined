package org.gtreimagined.gt5r.blockentity.multi;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.block.BlockCasing;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gt5r.data.GT5RMachines;
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityBasicMultiMachine;
import org.gtreimagined.gtlib.capability.machine.MachineEnergyHandler;
import org.gtreimagined.gtlib.client.scene.TrackedDummyWorld;
import org.gtreimagined.gtlib.machine.MachineFlag;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.structure.StructureCache;
import org.gtreimagined.tesseract.api.eu.IEnergyHandler;
import org.gtreimagined.tesseract.api.forge.TesseractCaps;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockEntityLongDistancePipeEndpoint extends BlockEntityBasicMultiMachine<BlockEntityLongDistancePipeEndpoint> {
    BlockEntityLongDistancePipeEndpoint target = null, sender = null;
    public BlockEntityLongDistancePipeEndpoint(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        if (type.has(MachineFlag.EU)){
            energyHandler.set(() -> new MachineEnergyHandler<>(this, false){

                @Override
                public long insertEu(long voltage, boolean simulate) {
                    if (tile.target == null) return 0;
                    if (!checkVoltage(voltage)) return 0;
                    if (tile.target.getNeighbor() == null) return 0;
                    IEnergyHandler handler = tile.target.getNeighbor().getCapability(TesseractCaps.ENERGY_HANDLER_CAPABILITY, tile.target.getFacing().getOpposite()).resolve().orElse(null);
                    if (handler == null) return 0;
                    if (tile.successfulPositions == null) return 0;
                    int loss = Math.round(tile.successfulPositions.size() * 0.125f);
                    if (loss >= voltage) return 0;
                    return handler.insertEu(Math.max(0, voltage - loss), simulate) + loss;
                }

                @Override
                public long availableAmpsInput(long voltage) {
                    if (tile.target == null) return 0;
                    if (tile.target.getNeighbor() == null) return 0;
                    IEnergyHandler handler = tile.target.getNeighbor().getCapability(TesseractCaps.ENERGY_HANDLER_CAPABILITY, tile.target.getFacing().getOpposite()).resolve().orElse(null);
                    if (handler == null) return 0;
                    if (tile.successfulPositions == null) return 0;
                    int loss = Math.round(tile.successfulPositions.size() * 0.125f);
                    return handler.availableAmpsInput(Math.max(0, voltage - loss));
                }

                @Override
                public long getCapacity() {
                    return 0;
                }

                @Override
                protected boolean checkVoltage(long voltage) {
                    return voltage <= this.getInputVoltage();
                }
            });
        }
    }

    protected Block getPipeline(){
        if (type == GT5RMachines.LONG_DISTANCE_FLUID_ENDPOINT) return GT5RBlocks.LONG_DIST_FLUID_PIPE;
        if (type == GT5RMachines.LONG_DISTANCE_ITEM_ENDPOINT) return GT5RBlocks.LONG_DIST_ITEM_PIPE;
        if (type == GT5RMachines.LONG_DISTANCE_TRANSFORMER_ENDPOINT){
            Block block = GT5Reimagined.get(BlockCasing.class, "long_distance_wire_" + this.tier.getId());
            if (block != null) return block;
        }
        return Blocks.DIAMOND_BLOCK;
    }

    LongList successfulPositions;
    @Override
    public boolean checkStructure() {
        checkingStructure++;

        boolean fail = false;
        boolean succeed = false;
        Direction to = this.getFacing();
        BlockPos.MutableBlockPos mut = this.getBlockPos().mutable();
        successfulPositions = new LongArrayList();
        int pipelinesFound = 0;
        boolean switchDirection = false;
        while (true){
            mut.move(to);
            BlockState state =  this.getLevel().getBlockState(mut);
            if (state.getBlock() == this.getBlockState().getBlock() && pipelinesFound > 0){
                Direction compare = switchDirection ? to.getOpposite() : to;
                if (this.getLevel().getBlockEntity(mut) instanceof BlockEntityLongDistancePipeEndpoint endpoint && endpoint.getFacing() == compare){
                    endpoint.validStructure = true;
                    if (switchDirection){
                        this.sender = endpoint;
                        this.sender.target = this;
                    } else {
                        this.target = endpoint;
                        this.target.sender = this;
                    }
                    successfulPositions.add(mut.asLong());
                    succeed = true;
                }
                break;
            }
            if (state.getBlock() == getPipeline()){
                successfulPositions.add(mut.asLong());
                pipelinesFound++;
                continue;
            }
            mut.move(to.getOpposite());
            if (mut.equals(this.getBlockPos())){
                if (!switchDirection){
                    switchDirection = true;
                    mut = this.getBlockPos().mutable();
                    to = this.getFacing().getOpposite();
                    continue;
                }
                break;
            }
            int failed = 0;
            int succeeded = 0;
            Direction oldTo = to;
            for (Direction dir : Direction.values()){
                if (dir == oldTo || dir == oldTo.getOpposite()) continue;
                BlockState state2 =  this.getLevel().getBlockState(mut.immutable().relative(dir));
                if (state2.getBlock() == getPipeline()){
                    if (succeeded == 0) to = dir;
                    succeeded++;
                } else {
                    failed++;
                }
            }
            if (failed == 4 || succeeded > 1){
                if (!switchDirection){
                    switchDirection = true;
                    mut = this.getBlockPos().mutable();
                    to = this.getFacing().getOpposite();
                    successfulPositions = new LongArrayList();
                    continue;
                }
                break;
            }
        }
        checkingStructure--;
        validStructure = succeed && pipelinesFound > 0;
        if (validStructure){
            if (level instanceof TrackedDummyWorld) {
                StructureCache.add(level, worldPosition, successfulPositions);
                StructureCache.validate(level, worldPosition, successfulPositions, maxShares());
                checkingStructure--;
                return true;
            } else if (onStructureFormed() && StructureCache.validate(this.getLevel(), this.getBlockPos(), successfulPositions, maxShares())){
                if (isServerSide()){
                    afterStructureFormed();
                    if (machineState != MachineState.ACTIVE && machineState != MachineState.DISABLED) {
                        setMachineState(MachineState.IDLE);
                    }
                }
                sidedSync(true);
                StructureCache.add(level, getBlockPos(), successfulPositions);
            } else {
                validStructure = false;
            }

        }
        if (!validStructure) successfulPositions.clear();
        return validStructure;
    }

    public void onBlockUpdate(BlockPos pos) {
        if (checkingStructure > 0)
            return;
        if (validStructure) {
            BlockState state = this.getLevel().getBlockState(pos);
            if (successfulPositions != null && successfulPositions.contains(pos.asLong()) && state.getBlock() != getPipeline()){
                invalidateStructure();
            }
        } else {
            checkStructure();
        }
    }

    public BlockEntity getNeighbor() {
        return this.getCachedBlockEntity(this.getFacing());
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        if (successfulPositions != null) {
            successfulPositions.clear();
        }
        if (target != null){
            target.sender = null;
            target.invalidateStructure();
            target = null;
        }
        if (sender != null){
            sender.target = null;
            sender.invalidateStructure();
            sender = null;
        }
    }

    @Override
    public @NotNull <U> LazyOptional<U> getCapability(@NotNull Capability<U> cap, @Nullable Direction side) {
        if (side == getFacing().getOpposite()) {
            if ((cap == ForgeCapabilities.FLUID_HANDLER && has(MachineFlag.FLUID)) ||
                    (cap == ForgeCapabilities.ITEM_HANDLER && has(MachineFlag.ITEM))) {
                if (this.target != null){
                    BlockEntity sideBE = this.target.getCachedBlockEntity(target.getFacing());
                    if (sideBE != null){
                        return sideBE.getCapability(cap, target.getFacing().getOpposite());
                    }
                }
                return LazyOptional.empty();
            }
        }
        return super.getCapability(cap, side);
    }
}
