package org.gtreimagined.gt5r.machine;

import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.gui.SlotTypes;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.types.BasicMachine;
import org.gtreimagined.gtlib.texture.Texture;
import org.gtreimagined.gtlib.util.Dir;
import org.gtreimagined.gtlib.util.Utils;
import org.gtreimagined.gt5r.blockentity.single.BlockEntitySmallHeatExchanger;
import org.gtreimagined.gt5r.data.GT5RCovers;
import org.gtreimagined.gt5r.data.RecipeMaps;

import static org.gtreimagined.gtlib.machine.MachineFlag.*;
import static org.gtreimagined.gtlib.machine.Tier.NONE;

public class HeatExchangerMachine extends BasicMachine {
    int rate;
    int efficiency = 10000;
    public HeatExchangerMachine(String domain, String id, int rate) {
        super(domain, id);
        this.rate = rate;
        this.setTile((m, p, s) -> new BlockEntitySmallHeatExchanger(this, p, s, this.rate, this.efficiency));
        this.setOverlayTextures((type, state, tier, i) -> {
            state = state.getTextureState();
            String stateDir = state == MachineState.IDLE ? "" : state.getId() + "/";
            return new Texture[]{
                    new Texture(domain, "block/machine/overlay/small_heat_exchanger/" + stateDir + "bottom"),
                    new Texture(domain, "block/machine/overlay/small_heat_exchanger/" + stateDir + "top"),
                    new Texture(domain, "block/machine/overlay/small_heat_exchanger/" + stateDir + "back"),
                    new Texture(domain, "block/machine/overlay/small_heat_exchanger/" + stateDir + "front"),
                    new Texture(domain, "block/machine/overlay/small_heat_exchanger/" + stateDir + "side"),
                    new Texture(domain, "block/machine/overlay/small_heat_exchanger/" + stateDir + "side"),
            };
        });
        removeFlags(EU);
        setSecondaryOutputCover(GT5RCovers.COVER_OUTPUT_SECONDARY);
        setSecondaryOutputDir(Dir.RIGHT);
        setAllowsOutputCoversOnFacing(true);
        setTiers(NONE);
        setBaseTexture(new Texture(GT5Reimagined.ID, "block/machine/base/" + id));
        setMap(RecipeMaps.HEAT_EXCHANGER).addFlags(GUI, ITEM, FLUID).setAllowsFrontCovers().setAllowsFrontIO();
        addTooltipInfo((machine, stack, world, tooltip, flag) -> {
            tooltip.add(Utils.translatable("tooltip.gt5r.small_heat_exchanger.heat_rate", rate));
            tooltip.add(Utils.translatable("tooltip.gt5r.small_heat_exchanger.efficiency", ((double)efficiency / 100.0) + "%"));
        });
        add(SlotTypes.IT_IN, 53, 25).add(SlotTypes.IT_OUT, 107, 25).add(SlotTypes.FL_IN, 44, 63).add(SlotTypes.FL_IN, 62, 63)
                .add(SlotTypes.FL_OUT,107,63).add(SlotTypes.FL_OUT,125,63);
    }

    public HeatExchangerMachine setEfficiency(int efficiency) {
        if (efficiency > 10000) efficiency = 10000;
        if (efficiency < 0) efficiency = 0;
        this.efficiency = efficiency;
        return this;
    }

    public int getRate() {
        return rate;
    }

    public int getEfficiency() {
        return efficiency;
    }
}
