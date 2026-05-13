package org.gtreimagined.gt5r;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTRemapping;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.cover.CoverFactory;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.material.MaterialType;
import org.gtreimagined.gtlib.ore.StoneType;

import java.util.Map;

import static org.gtreimagined.gt5r.data.GT5RMachines.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class GT5RRemapping {
    private static final Map<String, String> REMAPPING_MAP = new Object2ObjectArrayMap<>();

    public static void init(){
        GTAPI.all(StoneType.class).stream().filter(StoneType::doesGenerateOre).forEach(s -> {
            GTRemapping.remap(Ref.SHARED_ID, s.getId() + "_manganese_ore", s.getId() + "_pyrolusite_ore");
            GTRemapping.remap(Ref.SHARED_ID, "small_" + s.getId() + "_manganese_ore", "small_" + s.getId() + "_pyrolusite_ore");
            GTRemapping.remap(Ref.SHARED_ID, s.getId() + "_manganese_surface_rock", s.getId() + "_pyrolusite_surface_rock");

        });
        MaterialType<?>[] types = {RAW_ORE, RAW_ORE_BLOCK, CRUSHED_ORE, PURIFIED_ORE, REFINED_ORE, IMPURE_DUST, PURE_DUST, BEARING_ROCK};
        for (MaterialType<?> type : types){
            String oldID = type.getIdGetter().apply(Materials.Manganese);
            String newID = type.getIdGetter().apply(Materials.Pyrolusite);
            GTRemapping.remap(Ref.SHARED_ID, oldID, newID);
        }
    }

    private static void remap(String oldId, String newId){
        GTRemapping.remap(GT5Reimagined.ID, oldId, newId);
    }

    private static void remapGTCore(String oldId, String newId){
        GTRemapping.remap(new ResourceLocation(GT5Reimagined.ID, oldId), new ResourceLocation(GTCore.ID, newId));
    }

    private static void remapFromGTCore(String oldId, String newId){
        GTRemapping.remap(new ResourceLocation(GTCore.ID, oldId), new ResourceLocation(GT5Reimagined.ID, newId));
    }

    private static void remapFromGTCore(String id){
        GTRemapping.remap(new ResourceLocation(GTCore.ID, id), new ResourceLocation(GT5Reimagined.ID, id));
    }

    public static Map<String, String> getRemappingMap() {
        return REMAPPING_MAP;
    }
}
