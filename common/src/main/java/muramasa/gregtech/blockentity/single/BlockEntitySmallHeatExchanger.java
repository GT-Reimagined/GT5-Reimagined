package muramasa.gregtech.blockentity.single;

import com.mojang.blaze3d.vertex.PoseStack;
import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.blockentity.multi.BlockEntityMultiMachine;
import muramasa.antimatter.capability.IFilterableHandler;
import muramasa.antimatter.capability.fluid.FluidTank;
import muramasa.antimatter.capability.machine.DefaultHeatHandler;
import muramasa.antimatter.capability.machine.MachineRecipeHandler;
import muramasa.antimatter.gui.GuiInstance;
import muramasa.antimatter.gui.ICanSyncData;
import muramasa.antimatter.gui.IGuiElement;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.gui.widget.InfoRenderWidget;
import muramasa.antimatter.gui.widget.WidgetSupplier;
import muramasa.antimatter.integration.jeirei.renderer.IInfoRenderer;
import muramasa.antimatter.machine.event.IMachineEvent;
import muramasa.antimatter.machine.event.MachineEvent;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.recipe.IRecipe;
import muramasa.antimatter.util.Utils;
import muramasa.gregtech.items.ItemIntCircuit;
import muramasa.gregtech.machine.caps.ParallelRecipeHandler;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import tesseract.TesseractGraphWrappers;
import tesseract.api.heat.IHeatHandler;

import static muramasa.antimatter.data.AntimatterMaterials.Water;
import static muramasa.gregtech.data.Materials.*;

public class BlockEntitySmallHeatExchanger extends BlockEntitySecondaryOutput<BlockEntitySmallHeatExchanger> {
    boolean hadNoWater = false;

    public BlockEntitySmallHeatExchanger(Machine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        heatHandler.set(() -> new DefaultHeatHandler(this, Integer.MAX_VALUE, 80, 0));
        recipeHandler.set(() -> new MachineRecipeHandler<>(this){

            @Override
            protected boolean validateRecipe(IRecipe r) {
                return super.validateRecipe(r) && r.getSpecialValue() != 0;
            }

            @Override
            protected boolean canRecipeContinue() {
                return super.canRecipeContinue() && heatHandler.map(h -> h.getHeat() + (activeRecipe.getTotalPower()) <= h.getHeatCap()).orElse(false);
            }

            @Override
            public boolean consumeResourceForRecipe(boolean simulate) {
                return tile.heatHandler.map(e -> e.insert((int) getPower(), simulate) >= getPower()).orElse(false);
            }
        });

    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
        if (level.getGameTime() % 20 == 0){
            fluidHandler.ifPresent(f -> {
                heatHandler.ifPresent(h -> {
                    if (h.getHeat() >= 80){
                        int heatMultiplier = h.getHeat() / 80;
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
                            waterToExtract = (int) Math.min(heatMultiplier, waterTank.getStoredFluid().getFluidAmount() / TesseractGraphWrappers.dropletMultiplier);
                        }
                        if (waterToExtract > 0){
                            if (hadNoWater){
                                Utils.createExplosion(this.level, worldPosition, 6.0F, Explosion.BlockInteraction.DESTROY);
                                return;
                            }
                            Material steam = Steam;
                            int waterMultiplier = 160;
                            int steamToAdd = waterToExtract  * waterMultiplier;
                            long inserted = f.getOutputTanks().internalInsert(steam.getGas(steamToAdd), true);
                            if (inserted >= TesseractGraphWrappers.dropletMultiplier){
                                waterToExtract = Math.min(waterToExtract, (int) (inserted / TesseractGraphWrappers.dropletMultiplier));
                                waterTank.internalExtract(Utils.ca(waterToExtract, waterTank.getStoredFluid()), false);
                                f.getOutputTanks().internalInsert(steam.getGas(steamToAdd), false);
                                h.extract(waterToExtract * 80, false);
                            }
                            hadNoWater = false;
                        } else {
                            hadNoWater = true;
                        }
                    }
                });

            });
        }

    }
}
