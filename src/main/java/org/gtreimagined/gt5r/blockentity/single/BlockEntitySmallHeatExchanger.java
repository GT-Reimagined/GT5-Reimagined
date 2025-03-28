package org.gtreimagined.gt5r.blockentity.single;

import org.gtreimagined.gtlib.capability.fluid.FluidTanks;
import org.gtreimagined.gtlib.capability.machine.DefaultHeatHandler;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.recipe.map.IRecipeMap;
import org.gtreimagined.gtlib.util.CodeUtils;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.gtreimagined.gt5r.data.GT5RRecipeTags;
import org.gtreimagined.gt5r.machine.HeatExchangerMachine;
import org.gtreimagined.gt5r.machine.caps.ParallelRecipeHandler;
import org.gtreimagined.gtcore.data.GTCoreTags;

import static org.gtreimagined.gtlib.data.GTLibMaterials.Water;
import static org.gtreimagined.gt5r.data.Materials.DistilledWater;
import static org.gtreimagined.gt5r.data.Materials.Steam;

public class BlockEntitySmallHeatExchanger extends BlockEntitySecondaryOutput<BlockEntitySmallHeatExchanger> {
    boolean hadNoWater = false;
    int rate;
    int efficiency;

    public BlockEntitySmallHeatExchanger(HeatExchangerMachine type, BlockPos pos, BlockState state, int rate, int efficiency) {
        super(type, pos, state);
        this.rate = rate;
        this.efficiency = efficiency;
        heatHandler.set(() -> new DefaultHeatHandler(this, Integer.MAX_VALUE, 80, 0));
        recipeHandler.set(() -> new ParallelRecipeHandler<>(this, 1){

            @Override
            protected boolean validateRecipe(IRecipe r) {
                return super.validateRecipe(r) && !r.getTags().contains(GT5RRecipeTags.LARGE_HEAT_EXCHANGED_ONLY);
            }

            @Override
            protected boolean canRecipeContinue() {
                return super.canRecipeContinue() && heatHandler.map(h -> h.getHeat() < rate * 2).orElse(false);
            }

            @Override
            protected int maxSimultaneousRecipes() {
                if (activeRecipe != null){
                    return (int) Math.max(1L, rate / activeRecipe.getTotalPower());
                }
                return super.maxSimultaneousRecipes();
            }

            @Override
            public boolean consumeResourceForRecipe(boolean simulate) {
                if (activeRecipe == null) return false;
                if (currentProgress > 0 || simulate) return true;
                if (!consumedResources && shouldConsumeResources()) {
                    this.consumeInputs();
                }
                long totalPower = CodeUtils.units(activeRecipe.getTotalPower(), 10000, efficiency, false) * concurrentRecipes;
                return tile.heatHandler.map(e -> e.insertInternal((int) totalPower, simulate) >= totalPower).orElse(false);
            }

            @Override
            public boolean accepts(FluidStack stack) {
                return super.accepts(stack) || stack.getFluid() == Water.getLiquid() || stack.getFluid() == DistilledWater.getLiquid();
            }
        });
        fluidHandler.set(() -> new SmallHeatExchangerFluidHandler(this));
    }

    int steamHeat = 0;

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
        heatHandler.ifPresent(h -> {
            if (h.getHeat() > 0){
                int tempRate = Math.min(h.getHeat(), rate);
                steamHeat += tempRate;
                h.extractInternal(tempRate, false);
            }
        });
        if (level.getGameTime() % 20 == 0){
            fluidHandler.ifPresent(f -> {
                /*if (steamHeat >= rate * 80){
                    Utils.createExplosion(this.level, worldPosition, 6.0F, Explosion.BlockInteraction.DESTROY);
                    return;
                }*/
                if (steamHeat >= 80){
                    int max = rate * 20 / 80;
                    int heatMultiplier = Math.min(max, steamHeat / 80);
                    int waterToExtract = 0;
                    int waterTankId = f.getInputTanks().getFirstAvailableTank(DistilledWater.getLiquid(1), true);
                    if (waterTankId < 0){
                        waterTankId = f.getInputTanks().getFirstAvailableTank(Water.getLiquid(1), true);
                    }
                    FluidTank waterTank;
                    if (waterTankId < 0){
                        waterTank = null;
                    } else {
                        waterTank = f.getInputTanks().getTank(waterTankId);
                    }
                    if (waterTank != null) {
                        waterToExtract = (int) Math.min(heatMultiplier, waterTank.getFluid().getAmount());
                    }
                    if (waterToExtract > 0){
                        if (hadNoWater){
                            Utils.createExplosion(this.level, worldPosition, 6.0F, Explosion.BlockInteraction.DESTROY);
                            return;
                        }
                        Material steam = Steam;
                        int waterMultiplier = 160;
                        int steamToAdd = waterToExtract  * waterMultiplier;
                        long inserted = f.getOutputTanks().fill(steam.getGas(steamToAdd), FluidAction.SIMULATE);
                        int successfulSteam = (int) (inserted / 160);
                        if (successfulSteam >= 1){
                            waterToExtract = Math.min(waterToExtract, successfulSteam);
                            waterTank.drain(Utils.ca(waterToExtract, waterTank.getFluid()), FluidAction.EXECUTE);
                            f.getOutputTanks().fill(steam.getGas(waterToExtract * waterMultiplier), FluidAction.EXECUTE);
                            steamHeat -= waterToExtract * 80;
                        }
                        hadNoWater = false;
                    } else {
                        hadNoWater = true;
                    }
                }
            });
        }

    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        steamHeat = tag.getInt("steamHeat");
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("steamHeat", steamHeat);
    }

    public static class SmallHeatExchangerFluidHandler extends MachineFluidHandler<BlockEntitySmallHeatExchanger> {
        public SmallHeatExchangerFluidHandler(BlockEntitySmallHeatExchanger tile) {
            super(tile);
            tanks.put(FluidDirection.INPUT, FluidTanks.create(tile, SlotType.FL_IN, b -> {
                b.tank(this::acceptsRecipe, 1000);
                b.tank(this::acceptWater, 4000);
                return b;
            }));
            tanks.put(FluidDirection.OUTPUT, FluidTanks.create(tile, SlotType.FL_OUT, b -> {
                b.tank(f -> !f.getFluid().is(GTCoreTags.STEAM), 1000);
                b.tank(f -> f.getFluid().is(GTCoreTags.STEAM), 16000);
                return b;
            }));
        }

        public boolean acceptsRecipe(FluidStack stack) {
            return tile.recipeHandler.map(t -> {
                IRecipeMap map = t.getRecipeMap();
                return map == null || map.acceptsFluid(stack);
            }).orElse(true);
        }

        public boolean acceptWater(FluidStack stack) {
            return stack.getFluid() == Water.getLiquid() || stack.getFluid() == DistilledWater.getLiquid();
        }

        @Override
        public boolean canFluidBeAutoOutput(FluidStack fluid) {
            return fluid.getFluid() != Steam.getGas();
        }
    }
}
