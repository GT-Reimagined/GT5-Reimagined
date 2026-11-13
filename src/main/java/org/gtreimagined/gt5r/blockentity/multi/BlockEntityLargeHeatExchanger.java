package org.gtreimagined.gt5r.blockentity.multi;

import brachy.modularui.screen.viewport.ModularGuiContext;
import brachy.modularui.theme.WidgetThemeEntry;
import brachy.modularui.value.sync.IntSyncValue;
import brachy.modularui.value.sync.PanelSyncManager;
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityMultiMachine;
import org.gtreimagined.gtlib.capability.IFilterableHandler;
import org.gtreimagined.gtlib.capability.machine.DefaultHeatHandler;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.capability.machine.MultiMachineFluidHandler;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.SlotTypes;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.event.IMachineEvent;
import org.gtreimagined.gtlib.machine.event.MachineEvent;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.mui.widgets.GTInfoRenderWidget;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.gtreimagined.gt5r.data.GT5RRecipeTags;
import org.gtreimagined.gt5r.machine.caps.ParallelRecipeHandler;
import org.gtreimagined.gtcore.item.ItemSelectorTag;

import java.util.ArrayList;
import java.util.List;

import static org.gtreimagined.gt5r.data.Materials.*;

public class BlockEntityLargeHeatExchanger extends BlockEntityMultiMachine<BlockEntityLargeHeatExchanger> implements IFilterableHandler {

    int superheatedThreshold = Integer.MAX_VALUE;
    int efficiency = 1000;
    int dryHeatCounter = 0;
    int dryHeatMaximum = 100;
    boolean fullOfSteam = false;

    public BlockEntityLargeHeatExchanger(Machine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        heatHandler.set(() -> new DefaultHeatHandler(this, Integer.MAX_VALUE, 80, 0));
        this.fluidHandler.set(() -> new MultiMachineFluidHandler<>(this){
            @Override
            protected int compareOutputHatches(MachineFluidHandler<?> a, MachineFluidHandler<?> b) {
                return a.getTile().getBlockPos().getY() == this.tile.getBlockPos().getY() ? -1 : 1;
            }

            @Override
            protected int compareInputHatches(MachineFluidHandler<?> a, MachineFluidHandler<?> b) {
                return a.getTile().getBlockPos().getY() == this.tile.getBlockPos().getY() + 3 ? -1 : 1;
            }
        });
        recipeHandler.set(() -> new ParallelRecipeHandler<>(this, 1){

            @Override
            protected boolean validateRecipe(IRecipe r) {
                return super.validateRecipe(r) && !r.getTags().contains(GT5RRecipeTags.SMALL_HEAT_EXCHANGED_ONLY);
            }

            @Override
            protected int maxSimultaneousRecipes() {
                if (activeRecipe != null){
                    int base = 100;
                    long totalHu = activeRecipe.getTotalPower();
                    if (totalHu == 0) return 0;
                    double ratio = 80.0 / totalHu;
                    int totalRecipes = (int) (base * ratio);
                    return Math.max(1, totalRecipes);
                }
                return super.maxSimultaneousRecipes();
            }

            @Override
            protected boolean canRecipeContinue() {
                return super.canRecipeContinue() && heatHandler.map(h -> h.getHeat() + (activeRecipe.getTotalPower()) <= h.getHeatCap()).orElse(false) && !fullOfSteam;
            }

            @Override
            public boolean canOutput() {
                return !tile.fluidHandler.isPresent() || !activeRecipe.hasOutputFluids() || tile.fluidHandler.map(t -> t.getOutputTanks() != null && t.getOutputTanks().getTanks() > 1 && t.getOutputTanks().getTank(0).fill(Utils.ca(activeRecipe.getOutputFluids().get(0).getAmount() * concurrentRecipes, activeRecipe.getOutputFluids().get(0)), FluidAction.SIMULATE) == activeRecipe.getOutputFluids().get(0).getAmount() * concurrentRecipes).orElse(false);
            }

            @Override
            public boolean consumePower(boolean simulate) {
                return true;
            }

            protected void addOutputs() {
                for (int i = 0; i < concurrentRecipes; i++) {
                    if (activeRecipe.hasOutputItems()) {
                        tile.itemHandler.ifPresent(h -> {
                            //Roll the chances here. If they don't fit add flat (no chances).
                            List<ItemStack> out = activeRecipe.getOutputItems(true);
                            if (h.canOutputsFit(out)) {
                                h.addOutputs(out);
                            } else {
                                h.addOutputs(activeRecipe.getFlatOutputItems());
                            }
                        });
                    }
                    if (activeRecipe.hasOutputFluids()) {
                        tile.fluidHandler.ifPresent(h -> {
                            if (h.getOutputTanks() == null) return;
                            h.getOutputTanks().getTank(0).fill(activeRecipe.getOutputFluids().get(0).copy(), FluidAction.EXECUTE);
                        });
                    }
                    heatHandler.ifPresent(h -> h.insert((int) activeRecipe.getPower(), false));
                }
                if (activeRecipe.hasOutputItems()) tile.onMachineEvent(MachineEvent.ITEMS_OUTPUTTED);
                if (activeRecipe.hasOutputFluids()) tile.onMachineEvent(MachineEvent.FLUIDS_OUTPUTTED);
            }

            @Override
            protected boolean consumeSingleInput(boolean simulate) {
                boolean flag = true;
                if (!tile.hadFirstTick()) return true;
                final List<ItemStack>[] itemInputs = new List[]{new ArrayList<>()};
                final List<FluidStack>[] fluidInputs = new List[]{new ArrayList<>()};
                if (activeRecipe.hasInputItems()) {
                    flag &= tile.itemHandler.map(h -> {
                        itemInputs[0] = h.consumeInputs(activeRecipe, simulate);
                        return !itemInputs[0].isEmpty();
                    }).orElse(true);
                }
                if (activeRecipe.hasInputFluids()) {
                    flag &= tile.fluidHandler.map(h -> {
                        fluidInputs[0] = activeRecipe.getInputFluids().get(0).drain(h.getInputTanks().getTank(0), simulate);
                        return !fluidInputs[0].isEmpty();
                    }).orElse(true);
                }
                if (!simulate) {
                    if (flag) {
                        consumedResources = true;
                    }
                    this.itemInputs = itemInputs[0];
                    this.fluidInputs = fluidInputs[0];
                }
                return flag;
            }
        });

    }

    @Override
    public void onFirstTickServer(Level level, BlockPos pos, BlockState state) {
        super.onFirstTickServer(level, pos, state);
        onMachineEvent(SlotTypes.STORAGE);
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
        if (level.getGameTime() % 20 == 0 && this.getMachineState() != MachineState.DISABLED){
            fluidHandler.ifPresent(f -> {
                heatHandler.ifPresent(h -> {
                    if (h.getHeat() >= 80){
                        int heatMultiplier = h.getHeat() / 80;
                        if (f.getInputTanks() == null) return;
                        FluidTank waterTank = f.getInputTanks().getTank(1);
                        if (waterTank != null && waterTank.getFluidInTank(0).isFluidEqual(DistilledWater.getLiquid(1))) {
                            heatMultiplier = Math.min(heatMultiplier, waterTank.getFluidAmount());
                            if (waterTank.drain(DistilledWater.getLiquid(heatMultiplier), FluidAction.SIMULATE).getAmount() == heatMultiplier) {
                                if (f.getOutputTanks() != null && f.getOutputTanks().getTanks() >= 2){
                                    Material steam = Steam;
                                    if (h.getHeat() >= superheatedThreshold){
                                        steam = SuperheatedSteam;
                                    }
                                    float tEfficiency = steam == Steam ? 1 : efficiency / 1000.0f;
                                    int waterMultiplier = steam == Steam ? 160 : 80;
                                    int steamToAdd = (int) (heatMultiplier  * waterMultiplier *  tEfficiency);
                                    long inserted = f.getOutputTanks().getTank(1).fill(steam.getGas(steamToAdd), FluidAction.SIMULATE);
                                    if (inserted >= 1){
                                        heatMultiplier = Math.min(heatMultiplier, (int)(inserted / tEfficiency));
                                        f.drainInput(DistilledWater.getLiquid(heatMultiplier), FluidAction.EXECUTE);
                                        f.getOutputTanks().getTank(1).fill(steam.getGas(steamToAdd), FluidAction.EXECUTE);
                                        h.extractInternal(heatMultiplier * 80, false);
                                        fullOfSteam = false;
                                    } else {
                                        fullOfSteam = true;
                                    }
                                }
                                dryHeatCounter = 0;
                            } else {
                                dryHeatCounter++;
                            }
                        } else {
                            dryHeatCounter++;
                        }
                    }
                });

            });
            if (dryHeatCounter >= dryHeatMaximum){
                explodeMultiblock();
            }
        }

    }

    @Override
    public boolean test(SlotType<?> slotType, int slot, ItemStack stack) {
        if (slotType == SlotTypes.STORAGE) return stack.getItem() instanceof ItemSelectorTag;
        return true;
    }

    @Override
    public void onMachineEvent(IMachineEvent event, Object... data) {
        if (event == SlotTypes.STORAGE){
            ItemStack circuit = itemHandler.map(i -> i.getHandler(SlotTypes.STORAGE).getStackInSlot(0)).orElse(ItemStack.EMPTY);
            if (circuit.getItem() instanceof ItemSelectorTag circuit1){
                superheatedThreshold = 80000 - (circuit1.circuitId * 3000);
                efficiency = 1000 - (circuit1.circuitId * 15);
            } else {
                superheatedThreshold = Integer.MAX_VALUE;
                efficiency = 1000;
            }
        }
        super.onMachineEvent(event, data);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("efficiency", efficiency);
        tag.putBoolean("fullOfSteam", fullOfSteam);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        efficiency = tag.getInt("efficiency");
        fullOfSteam = tag.getBoolean("fullOfSteam");
    }

    @Override
    public void drawInfo(GTInfoRenderWidget widget, ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {
        super.drawInfo(widget, context, widgetTheme);
        int y = getMachineState() == MachineState.ACTIVE ? 32 : 16;
        widget.drawText(context, widgetTheme, 0, y, Utils.literal("Heat: " +
                widget.getSyncedValue("heat", Integer.class).orElse(0)), 0xFAFAFF);
    }

    @Override
    public void registerSyncHandlers(PanelSyncManager manager) {
        super.registerSyncHandlers(manager);
        manager.syncValue("heat", new IntSyncValue(() -> this.heatHandler.map(DefaultHeatHandler::getHeat).orElse(0)));
    }
}
