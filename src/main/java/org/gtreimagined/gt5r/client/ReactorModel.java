package org.gtreimagined.gt5r.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.client.ModelUtils;
import org.gtreimagined.gtlib.client.model.MachineModel;
import org.gtreimagined.gtlib.machine.MachineState;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtlib.util.Utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class ReactorModel extends MachineModel {
    private UnbakedModel[] rodModels;
    public ReactorModel(Map<MachineState, UnbakedModel[]> models, ResourceLocation particle) {
        super(models, particle);
    }

    @Override
    public Collection<Material> getMaterials(IGeometryBakingContext configuration, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        Collection<Material> materials = super.getMaterials(configuration, modelGetter, missingTextureErrors);
        //materials.addAll(Arrays.stream(rodModels).flatMap(i -> i.getMaterials(modelGetter, missingTextureErrors).stream()).toList());
        String[] strings = {
                "gt5r:block/machine/base/reactor_rods/sides",
                "gt5r:block/machine/base/reactor_rods/top",
                "gt5r:block/machine/overlay/nuclear_reactor_core/rod_top",
                "gt5r:block/machine/overlay/nuclear_reactor_core/rod_sides"
        };
        Arrays.stream(strings).forEach(s -> materials.add(ModelUtils.getBlockMaterial(new ResourceLocation(s))));
        return materials;
    }

    @Override
    public BakedModel bakeModel(IGeometryBakingContext configuration, ModelBakery bakery,
                                Function<Material, TextureAtlasSprite> getter, ModelState transform, ItemOverrides overrides,
                                ResourceLocation loc) {
        ImmutableMap.Builder<MachineState, BakedModel[]> builder = ImmutableMap.builder();

        if (rodModels == null){
            UnbakedModel[] rods = new UnbakedModel[4];
            String[] array = new String[]{"north-west", "south-west", "north-east", "south-east"};
            //northwest is 0, southwest is 1, northeast is 2, southeast is 3
            for (int i = 0; i < 4; i++) {
                ResourceLocation modelLocation = new ResourceLocation(GT5Reimagined.ID, "block/machine/overlay/nuclear_reactor_core/"+ array[i] + "-rod");
                try {
                    rods[i] = ModelUtils.getModel(modelLocation);
                } catch (Exception ignored){
                    ignored.printStackTrace();
                    //rods[i] = ModelUtils.getModel(modelLocation);
                }

            }
            rodModels = rods;
        }
        for (Map.Entry<MachineState, UnbakedModel[]> pair : this.models.entrySet()) {
            BakedModel[] mod = new BakedModel[6];
            for (int i = 0; i < 6; i++) {
                mod[i] = pair.getValue()[i].bake(bakery, getter, transform, loc);
            }
            builder.put(pair.getKey(),mod);
        }
        BakedModel[] rodModelsBaked = new BakedModel[rodModels.length];
        for (int i = 0; i < rodModels.length; i++) {
            rodModelsBaked[i] = rodModels[i].bake(bakery, getter, transform, loc);
        }
        return new ReactorBakedModel(getter.apply(new Material(TextureAtlas.LOCATION_BLOCKS, particle)), builder.build(), rodModelsBaked);
    }
}
