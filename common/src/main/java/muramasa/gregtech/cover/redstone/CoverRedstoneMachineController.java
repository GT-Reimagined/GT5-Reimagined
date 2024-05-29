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
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;


public class CoverRedstoneMachineController extends BaseCover {
    protected int redstonePower;
    boolean inverted = false;

    public CoverRedstoneMachineController(ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public String getId() {
        return "redstone_machine_controller";
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

    public boolean isPowered(){
        return inverted ? redstonePower == 0 : redstonePower > 0;
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

    @Override
    public void onBlockUpdate() {
        redstonePower = handler.getTile().getLevel().getSignal(handler.getTile().getBlockPos().relative(side), side);
    }

    @Override
    public void onFirstTick() {
        onBlockUpdate();
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        if (type.equals("pipe")) return PIPE_COVER_MODEL;
        return getBasicModel();
    }

    @Override
    public InteractionResult onInteract(Player player, InteractionHand hand, Direction side, @Nullable AntimatterToolType type) {
        if (type != null && type.getTag() == AntimatterDefaultTools.SCREWDRIVER.getTag()){
            inverted = !inverted;
            player.sendMessage(Utils.translatable("message.gti.redstone_mode." + (inverted ? "inverted" : "normal")), player.getUUID());
            for (Direction dir : Direction.values()) {
                if (dir == this.side) continue;
                source().get(dir).onBlockUpdate();
            }
            return InteractionResult.SUCCESS;
        }
        return super.onInteract(player, hand, side, type);
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag nbt =  super.serialize();
        nbt.putBoolean("inverted", inverted);
        return nbt;
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        super.deserialize(nbt);
        inverted = nbt.getBoolean("inverted");
    }
}
