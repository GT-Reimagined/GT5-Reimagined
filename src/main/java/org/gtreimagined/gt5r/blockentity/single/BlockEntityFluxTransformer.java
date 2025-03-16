package org.gtreimagined.gt5r.blockentity.single;

import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.capability.machine.MachineEnergyHandler;
import muramasa.antimatter.capability.machine.MachineFEHandler;
import muramasa.antimatter.machine.MachineFlag;
import muramasa.antimatter.machine.event.MachineEvent;
import muramasa.antimatter.machine.types.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityFluxTransformer extends BlockEntityMachine<BlockEntityFluxTransformer> {

    public BlockEntityFluxTransformer(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.feHandler.set(() -> new MachineFEHandler<>(this, (int)this.getMachineTier().getVoltage() * 66 * 4, true){
            @Override
            public boolean canExtract(Direction direction) {
               return super.canExtract(direction) && direction != tile.getFacing();
            }
        });
        this.energyHandler.set(() -> new MachineEnergyHandler<>(this, false){
            @Override
            public boolean canInput(Direction direction) {
                return super.canInput(direction) && direction == this.tile.getFacing();
            }

            @Override
            public long insertEu(long voltage, boolean simulate) {
                if (voltage < 0) return 0;
                if (inputAmperageCheck()) return 0;
                int loss = 1;
                voltage -= loss;
                if (!this.tile.getMachineType().has(MachineFlag.PARTIAL_AMPS) && cachedItems.isEmpty() && this.getEnergy() + voltage > this.getCapacity()) return 0;
                int toAdd = (int) Math.min(voltage, this.getCapacity() - this.getEnergy());
                int inserted = this.tile.feHandler.map(fe -> fe.receiveEnergy(toAdd * 4, simulate)).orElse(0);
                return inserted > 0 ? inserted + loss : 0;
            }

            @Override
            public long getEnergy() {
                return feHandler.map(fe -> fe.getEnergyStored() / 4).orElse(0);
            }
        });
    }
}
