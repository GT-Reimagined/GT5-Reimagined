package org.gtreimagined.gt5r.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.client.ModelUtils;
import org.gtreimagined.gtlib.client.model.MachineModel;
import org.gtreimagined.gtlib.client.model.loader.GTModelLoader;
import org.gtreimagined.gtlib.machine.MachineState;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ReactorModelLoader extends GTModelLoader<MachineModel> {
    public ReactorModelLoader(ResourceLocation loc) {
        super(loc);
    }

    @NotNull
    @Override
    public MachineModel read(JsonObject json, JsonDeserializationContext context) {
        ResourceLocation particle = json.has("particle") ? new ResourceLocation(json.get("particle").getAsString()) : MissingTextureAtlasSprite.getLocation();
        Map<MachineState, UnbakedModel[]> m = new HashMap<>();
        GTAPI.all(MachineState.class, t -> {
            if (json.has(t.toString().toLowerCase())) {
                JsonArray arr = json.get(t.toString().toLowerCase()).getAsJsonArray();
                UnbakedModel[] a = new UnbakedModel[6];
                for (int i = 0; i < 6; i++) {
                    a[i] = context.deserialize(arr.get(i), BlockModel.class);
                }
                m.put(t, a);
            }
        });
        return new ReactorModel(m, particle);
    }
}
