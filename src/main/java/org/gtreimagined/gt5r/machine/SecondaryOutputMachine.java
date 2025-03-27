package org.gtreimagined.gt5r.machine;

import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.cover.ICover;
import org.gtreimagined.gtlib.machine.types.BasicMachine;
import org.gtreimagined.gt5r.blockentity.single.BlockEntitySecondaryOutput;

public class SecondaryOutputMachine extends BasicMachine implements ISecondaryOutputMachine {
    protected CoverFactory secondaryOutputCover = ICover.emptyFactory;
    public SecondaryOutputMachine(String domain, String id) {
        super(domain, id);
        this.setTile(BlockEntitySecondaryOutput::new);
    }

    @Override
    public CoverFactory getSecondaryOutputCover() {
        return secondaryOutputCover;
    }

    public SecondaryOutputMachine setSecondaryOutputCover(CoverFactory cover) {
        this.secondaryOutputCover = cover;
        return this;
    }
}
