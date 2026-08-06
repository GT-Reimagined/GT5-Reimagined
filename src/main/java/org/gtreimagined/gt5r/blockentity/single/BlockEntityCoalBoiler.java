package org.gtreimagined.gt5r.blockentity.single;

import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.blockentity.IFuelMachine;
import org.gtreimagined.gtlib.capability.fluid.FluidTanks;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler;
import org.gtreimagined.gtlib.gui.SlotTypes;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.util.FluidUtils;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.gtreimagined.gtlib.machine.MachineState.ACTIVE;
import static org.gtreimagined.gtlib.machine.MachineState.IDLE;
import static org.gtreimagined.gtlib.machine.Tier.BRONZE;
import static org.gtreimagined.gt5r.data.Materials.DistilledWater;

public class BlockEntityCoalBoiler extends BlockEntityMachine<BlockEntityCoalBoiler> implements IFuelMachine {
    int maxHeat = 500, heat, fuel = 0, maxFuel, lossTimer = 0;
    boolean hadNoWater;

    public BlockEntityCoalBoiler(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        fluidHandler.set(() -> new CoalBoilerFluidHandler(this));
        recipeHandler.set(() -> new CoalBoilerRecipeHandler(this));
    }

    public int getFuel() {
        return recipeHandler.map(r -> (((CoalBoilerRecipeHandler) r).getFuel())).orElse(0);
    }

    public int getHeat() {
        return recipeHandler.map(r -> (((CoalBoilerRecipeHandler) r).getHeat())).orElse(0);
    }

    public int getMaxFuel() {
        return recipeHandler.map(r -> (((CoalBoilerRecipeHandler) r).getMaxFuel())).orElse(0);

    }

    public int getMaxHeat() {
        return recipeHandler.map(r -> (((CoalBoilerRecipeHandler) r).getMaxHeat())).orElse(0);

    }

    public static class CoalBoilerRecipeHandler extends MachineRecipeHandler<BlockEntityCoalBoiler> implements ISteamBoilerHandler {
        int maxHeat = 500, heat, fuel = 0, maxFuel, lossTimer = 0;
        boolean hadNoWater;

        protected final ContainerData GUI_SYNC_DATA2 = new ContainerData() {

            @Override
            public int get(int index) {
                switch (index) {
                    case 0:
                        return CoalBoilerRecipeHandler.this.heat;
                    case 1:
                        return CoalBoilerRecipeHandler.this.maxHeat;
                    case 2:
                        return CoalBoilerRecipeHandler.this.fuel;
                    case 3:
                        return CoalBoilerRecipeHandler.this.maxFuel;
                }
                return 0;
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        CoalBoilerRecipeHandler.this.heat = value;
                        break;
                    case 1:
                        CoalBoilerRecipeHandler.this.maxHeat = value;
                        break;
                    case 2:
                        CoalBoilerRecipeHandler.this.fuel = value;
                        break;
                    case 3:
                        CoalBoilerRecipeHandler.this.maxFuel = value;
                        break;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };

        public CoalBoilerRecipeHandler(BlockEntityCoalBoiler tile) {
            super(tile);
            GUI_SYNC_DATA2.set(0, 0);
            maxHeat = tile.getMachineTier() == BRONZE ? 500 : 1000;
        }

        public int getFuel() {
            return fuel;
        }

        @Override
        public int getProcessDelay() {
            return tile.getMachineTier() == BRONZE ? 25 : 10;
        }

        @Override
        public int getLossTimerMax() {
            return tile.getMachineTier() == BRONZE ? 45 : 40;
        }

        @Override
        public int getHeat() {
            return heat;
        }

        @Override
        public void setHeat(int heat) {
            this.heat = heat;
        }

        @Override
        public int getLossTimer() {
            return lossTimer;
        }

        @Override
        public void setLossTimer(int lossTimer) {
            this.lossTimer = lossTimer;
        }

        @Override
        public boolean hadNoWater() {
            return hadNoWater;
        }

        @Override
        public void setHadNoWater(boolean hadNoWater) {
            this.hadNoWater = hadNoWater;
        }

        @Override
        public BlockEntityMachine<?> getTile() {
            return tile;
        }

        @Override
        public void exportFluid() {
            if (tile.fluidHandler.map(f -> f.getOutputTanks().isEmpty()).orElse(false)) return;
            Arrays.stream(Direction.values()).filter(f -> f != Direction.DOWN).collect(Collectors.toList()).forEach(this::exportFluidFromMachineToSide);
        }

        public int getMaxFuel() {
            return maxFuel;
        }

        public int getMaxHeat() {
            return maxHeat;
        }

        @Override
        public void onServerUpdate() {
            tick();
            super.onServerUpdate();
        }

        public void exportFluidFromMachineToSide(Direction side){
            if (tile.fluidHandler.map(f -> f.getOutputTanks().isEmpty()).orElse(false)) return;
            LazyOptional<IFluidHandler> cap = FluidUtils.getFluidHandler(tile.getLevel(), tile.getBlockPos().relative(side), tile.getCachedBlockEntity(side), side.getOpposite());
            tile.fluidHandler.ifPresent(f -> cap.ifPresent(other -> Utils.transferFluids(f.getOutputTanks(), other, 1000)));
        }

        @Override
        protected MachineState recipeFinish() {
            if (!canRecipeContinue()) {
                this.resetRecipe();
                return IDLE;
            } else {
                return ACTIVE;
            }
        }

        @Override
        protected MachineState tickRecipe() {
            if (this.activeRecipe == null) {
                return tile.getMachineState();
            }
            else {
                tile.onRecipePreTick();
                if (fuel <= 0 && canRecipeContinue()) {
                    if (fuel < 0) {
                        fuel = 0;
                    }
                    this.maxFuel = activeRecipe.getDuration();
                    addOutputs();
                    this.fuel += maxFuel;
                    consumeInputs();
                }
                if ((this.heat < maxHeat) && (this.fuel > 0) && (tile.getLevel().getGameTime() % 12L == 0L)) {
                    int fuelSubtract = tile.getMachineTier() == BRONZE ? 1 : 2;
                    this.fuel -= fuelSubtract;
                    this.heat += 1;
                }
                tile.onRecipePostTick();
                if (fuel == 0){
                    return this.recipeFinish();
                }
                return ACTIVE;
            }
        }

        public void setActive(boolean t){
            if (t && tile.getMachineState() != ACTIVE){
                tile.setMachineState(ACTIVE);
            } else if (!t && tile.getMachineState() == ACTIVE){
                tile.setMachineState(IDLE);
            }
        }

        @Override
        public boolean accepts(FluidStack fluid) {
            return fluid.getFluid() == Fluids.WATER || fluid.getFluid() == DistilledWater.getLiquid();
        }

        @Override
        public boolean canOutput() {
            return true;
        }

        @Override
        public CompoundTag serialize() {
            CompoundTag nbt = super.serialize();
            nbt.putInt("heat", heat);
            nbt.putInt("maxHeat", maxHeat);
            nbt.putInt("fuel", fuel);
            nbt.putInt("maxFuel", maxFuel);
            nbt.putInt("lossTimer", lossTimer);
            nbt.putBoolean("hadNoWater", hadNoWater);
            return nbt;
        }

        @Override
        public void deserialize(CompoundTag nbt) {
            super.deserialize(nbt);
            this.heat = nbt.getInt("heat");
            this.maxHeat = nbt.getInt("maxHeat");
            this.fuel = nbt.getInt("fuel");
            this.maxFuel = nbt.getInt("maxFuel");
            this.lossTimer = nbt.getInt("lossTimer");
            this.hadNoWater = nbt.getBoolean("hadNoWater");
        }
    }

    public static class CoalBoilerFluidHandler extends MachineFluidHandler<BlockEntityCoalBoiler> {

        public CoalBoilerFluidHandler(BlockEntityCoalBoiler tile) {
            super(tile);
            tanks.put(FluidTankType.INPUT, FluidTanks.create(tile, SlotTypes.FL_IN, b -> {
                b.tank(16000);
                return b;
            }));
            tanks.put(FluidTankType.OUTPUT, FluidTanks.create(tile, SlotTypes.FL_OUT, b -> {
                b.tank(16000);
                return b;
            }));
        }

        @Override
        protected FluidTanks getCellAccessibleTanks() {
            return getInputTanks();
        }
    }
}
