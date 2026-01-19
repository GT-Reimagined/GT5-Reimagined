package org.gtreimagined.gt5r.integration.tfc.mixin;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagLoader;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.gtreimagined.gt5r.integration.tfc.TFCOreGen;
import org.gtreimagined.gtlib.GTLib;
import org.gtreimagined.gtlib.util.Utils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mixin(TagLoader.class)
public abstract class TagLoaderMixin {
    @Shadow
    @Final
    private String directory;

    @Inject(method = "build(Ljava/util/Map;)Ljava/util/Map;", at = @At("RETURN"))
    private <T> void gt5r$onCreateLoadResult(Map<ResourceLocation, List<TagLoader.EntryWithSource>> map, CallbackInfoReturnable<Map<ResourceLocation, Collection<T>>> cir) {
        if (directory.equals("tags/worldgen/placed_feature")){
            try {
                Map<ResourceLocation, Collection<Holder<PlacedFeature>>> tags = Utils.cast(cir.getReturnValue());
                ResourceLocation resourceLocation = new ResourceLocation("tfc", "in_biome/veins");
                if (tags.containsKey(resourceLocation)){
                    Collection<Holder<PlacedFeature>> tag = tags.get(resourceLocation);
                    tag = new ArrayList<>(tag.stream().filter(i -> i.unwrapKey().isPresent() && !TFCOreGen.veinsToRemove.contains(i.unwrapKey().get().location())).toList());
                    tags.put(resourceLocation, tag);
                }
            } catch (Exception e) {
                GTLib.LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
