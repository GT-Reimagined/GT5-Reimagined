package org.gtreimagined.gt5r.blockentity.multi;

import com.mojang.blaze3d.vertex.PoseStack;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityMultiMachine;
import org.gtreimagined.gtlib.capability.machine.DefaultHeatHandler;
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;
import org.gtreimagined.gtlib.gui.widget.InfoRenderWidget;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.texture.Texture;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.gtreimagined.gt5r.machine.caps.SecondaryMultiFluidHandler;
import org.gtreimagined.gt5r.machine.recipe.FusionRecipe;

import static org.gtreimagined.gt5r.data.Materials.*;

public class BlockEntityFusionReactor extends BlockEntityMultiMachine<BlockEntityFusionReactor> {

    Display display = Display.REGULAR;

    public BlockEntityFusionReactor(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.heatHandler.set(() -> new DefaultHeatHandler(this, 32768 * 8, 8192, 0));
        this.fluidHandler.set(() -> new SecondaryMultiFluidHandler<>(this));
        this.recipeHandler.set(() -> new MachineRecipeHandler<>(this){
            boolean consumedStartEu = false;

            @Override
            public boolean consumeResourceForRecipe(boolean simulate) {
                if (activeRecipe == null) return false;
                if (!consumedStartEu){
                    boolean tConsumedStartEu = energyHandler.map(e -> e.extractEu(activeRecipe.getSpecialValue(), true) == activeRecipe.getSpecialValue()).orElse(false);
                    if (tConsumedStartEu){
                        if (!simulate) {
                            energyHandler.ifPresent(e -> e.extractEu(activeRecipe.getSpecialValue(), false));
                        }
                        consumedStartEu = true;
                    } else {
                        return false;
                    }
                }
                boolean run = super.consumeResourceForRecipe(simulate) && consumedStartEu;
                if (run) {
                    heatHandler.ifPresent(h -> h.insert(((FusionRecipe)activeRecipe).getHuOutput(), simulate));
                }
                return run;
            }

            @Override
            protected MachineState tickRecipe() {
                IRecipe oldActive = activeRecipe;
                MachineState oldState = tile.getMachineState();
                MachineState superState =  super.tickRecipe();
                if (consumedStartEu && oldActive != null && ((oldActive != activeRecipe) || (oldState == MachineState.ACTIVE && superState != MachineState.ACTIVE))){
                    consumedStartEu = false;
                }
                return superState;
            }

            @Override
            public CompoundTag serialize() {
                CompoundTag nbt = super.serialize();
                nbt.putBoolean("consumedStartEu", consumedStartEu);
                return nbt;
            }

            @Override
            public void deserialize(CompoundTag nbt) {
                super.deserialize(nbt);
                consumedStartEu = nbt.getBoolean("consumedStartEu");
            }
        });
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
        fluidHandler.ifPresent(f -> {
            if (!(f instanceof SecondaryMultiFluidHandler<?> mf)) return;
            if (mf.getSecondaryInputTanks() == null) return;
            heatHandler.ifPresent(h -> {
                if (h.getHeat() >= 30){
                    int heatMultiplier = h.getHeat() / 30;
                    FluidTank coolantTank = mf.getSecondaryInputTanks().getTank(mf.getSecondaryInputTanks().getFirstAvailableTank(Helium.getGas(1), true));
                    if (coolantTank != null) {
                        heatMultiplier = Math.min(heatMultiplier, coolantTank.getFluidAmount());
                        if (coolantTank.drain(Helium.getGas(heatMultiplier), FluidAction.SIMULATE).getAmount() == heatMultiplier ) {
                            if (mf.getSecondaryOutputTanks() != null && mf.getSecondaryOutputTanks().getTanks() >= 1) {
                                long inserted = mf.getSecondaryOutputTanks().fill(HotHelium.getGas(heatMultiplier), FluidAction.SIMULATE);
                                if (inserted >= 1){
                                    heatMultiplier = (int) Math.min(heatMultiplier, (inserted));
                                    coolantTank.drain(Helium.getGas(heatMultiplier), FluidAction.EXECUTE);
                                    mf.getSecondaryOutputTanks().fill(HotHelium.getGas(heatMultiplier), FluidAction.EXECUTE);
                                    h.extract(heatMultiplier * 30, false);
                                }
                            }
                        }
                    }
                }
            });

        });
    }

//    @Override
//    public void onRecipeFound() {
//        consumeEnergy(activeRecipe.getSpecialValue());
//        System.out.println("Consumed Starting Energy");
//    }

    public Display getDisplay() {
        return display;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("display", display.ordinal());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.display = Display.values()[tag.getInt("display")];
    }

    @Override
    public void onGuiEvent(IGuiEvent event, Player playerEntity) {
        super.onGuiEvent(event, playerEntity);
        if (event.getFactory() == GuiEvents.EXTRA_BUTTON){
            GuiEvents.GuiEvent ev =(GuiEvents.GuiEvent) event;
            int[] data = ev.data;
            if (data[1] == 0){
                this.display = Display.REGULAR;
            } else if (data[1] == 1){
                this.display = Display.MIDDLE;
            } else if (data[1] == 2){
                this.display = Display.TOP_BOTTOM;
            }
        }
    }

    public Texture getTextureForHatches(Direction dir, BlockPos hatchPos){
        return new Texture(GT5Reimagined.ID, "block/casing/fusion_1");
    }

    @Override
    public int guiHeight() {
        return 182;
    }

    @Override
    public int drawInfo(InfoRenderWidget.MultiRenderWidget instance, PoseStack stack, Font renderer, int left, int top) {
        return 0;
    }

    public enum Display{
        REGULAR,
        MIDDLE,
        TOP_BOTTOM
    }
}
