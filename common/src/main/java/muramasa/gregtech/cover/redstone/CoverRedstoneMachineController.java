package muramasa.gregtech.cover.redstone;

import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.blockentity.pipe.BlockEntityPipe;
import muramasa.antimatter.capability.ICoverHandler;
import muramasa.antimatter.cover.BaseCover;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.gui.ButtonOverlay;
import muramasa.antimatter.gui.event.GuiEvents;
import muramasa.antimatter.gui.event.IGuiEvent;
import muramasa.antimatter.machine.MachineState;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.tool.AntimatterToolType;
import muramasa.antimatter.util.Utils;
import muramasa.gregtech.cover.base.CoverBasicRedstoneInput;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;


public class CoverRedstoneMachineController extends CoverBasicRedstoneInput {

    public CoverRedstoneMachineController(ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public void onRemove() {
        if (handler.getTile() instanceof BlockEntityMachine<?> machine){
            if (machine.getLevel().isLoaded(machine.getBlockPos())) {
                if (machine.getMachineState() == MachineState.DISABLED){
                    machine.toggleMachine();
                }
            }
        }
    }

    @Override
    public void onUpdate() {
        if (handler.getTile() instanceof BlockEntityMachine<?> machine && machine.isServerSide()){
            if (machine.getMachineState() != MachineState.DISABLED){
                if (!isPowered()){
                    machine.toggleMachine();
                }
            } else {
                if (isPowered()){
                    machine.toggleMachine();
                }
            }

        }
    }
}
