package org.gtreimagined.gt5r.cover.redstone;

import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.machine.MachineFlag;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.Tier;
import net.minecraft.core.Direction;
import org.gtreimagined.gt5r.cover.base.CoverBasicRedstoneOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoverActivityDetectorProcessing extends CoverBasicRedstoneOutput {
    public CoverActivityDetectorProcessing(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public void onTickPost() {
        if (source().getTile() instanceof BlockEntityMachine<?> machine && machine.has(MachineFlag.RECIPE)){
            if (machine.getMachineState() == MachineState.ACTIVE){
                setOutputRedstone(inverted ? 0 : 15);
            } else {
                setOutputRedstone(inverted ? 15 : 0);
            }
        }
    }
}
