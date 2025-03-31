package org.gtreimagined.gt5r.data;

import org.gtreimagined.gtlib.GTAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.gtreimagined.gt5r.GT5RRef;

public class GT5RSounds {

    public static void init() {

    }

    public static final SoundEvent EXTRACTOR = GTAPI.register(SoundEvent.class, GT5RRef.ID, "extractor", new SoundEvent(new ResourceLocation(GT5RRef.ID, "extractor")));
    public static final SoundEvent MACERATOR = GTAPI.register(SoundEvent.class, GT5RRef.ID, "macerator", new SoundEvent(new ResourceLocation(GT5RRef.ID, "macerator")));
    public static final SoundEvent MAGNETIZER = GTAPI.register(SoundEvent.class, GT5RRef.ID, "magnetizer", new SoundEvent(new ResourceLocation(GT5RRef.ID, "magnetizer")));
    public static final SoundEvent FURNACE = GTAPI.register(SoundEvent.class, GT5RRef.ID, "furnace", new SoundEvent(new ResourceLocation(GT5RRef.ID, "furnace")));
    public static final SoundEvent TREETAP = GTAPI.register(SoundEvent.class, GT5RRef.ID, "treetap", new SoundEvent(new ResourceLocation(GT5RRef.ID, "treetap")));
}
