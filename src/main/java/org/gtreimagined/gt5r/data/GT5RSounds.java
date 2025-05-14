package org.gtreimagined.gt5r.data;

import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.GTAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class GT5RSounds {

    public static void init() {

    }

    public static final SoundEvent EXTRACTOR = GTAPI.register(SoundEvent.class, GT5Reimagined.ID, "extractor", new SoundEvent(new ResourceLocation(GT5Reimagined.ID, "extractor")));
    public static final SoundEvent MACERATOR = GTAPI.register(SoundEvent.class, GT5Reimagined.ID, "macerator", new SoundEvent(new ResourceLocation(GT5Reimagined.ID, "macerator")));
    public static final SoundEvent MAGNETIZER = GTAPI.register(SoundEvent.class, GT5Reimagined.ID, "magnetizer", new SoundEvent(new ResourceLocation(GT5Reimagined.ID, "magnetizer")));
    public static final SoundEvent FURNACE = GTAPI.register(SoundEvent.class, GT5Reimagined.ID, "furnace", new SoundEvent(new ResourceLocation(GT5Reimagined.ID, "furnace")));
    public static final SoundEvent TREETAP = GTAPI.register(SoundEvent.class, GT5Reimagined.ID, "treetap", new SoundEvent(new ResourceLocation(GT5Reimagined.ID, "treetap")));
}
