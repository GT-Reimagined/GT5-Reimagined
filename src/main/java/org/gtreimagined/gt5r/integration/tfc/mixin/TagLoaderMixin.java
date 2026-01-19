package org.gtreimagined.gt5r.integration.tfc.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagLoader;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.Fluid;
import org.gtreimagined.gt5r.integration.tfc.TFCOreGen;
import org.gtreimagined.gtlib.GTLib;
import org.gtreimagined.gtlib.datagen.providers.GTTagProvider;
import org.gtreimagined.gtlib.util.Utils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(TagLoader.class)
public abstract class TagLoaderMixin {
    @Shadow
    @Final
    private String directory;

    @Shadow
    public abstract Map<ResourceLocation, Tag.Builder> load(ResourceManager resourceManager);

    @Inject(method = "build(Ljava/util/Map;)Ljava/util/Map;", at = @At("RETURN"))
    private <T> void onCreateLoadResult(Map<ResourceLocation, Tag.Builder> map, CallbackInfoReturnable<Map<ResourceLocation, Tag<T>>> cir) {
        if (directory.equals("tags/worldgen/placed_feature")){
            try {
                Map<ResourceLocation, Tag<Holder<PlacedFeature>>> tags = Utils.cast(cir.getReturnValue());
                ResourceLocation resourceLocation = new ResourceLocation("tfc", "in_biome/veins");
                if (tags.containsKey(resourceLocation)){
                    Tag<Holder<PlacedFeature>> tag = tags.get(resourceLocation);
                    tag = new Tag<>(tag.getValues().stream().filter(i -> i.unwrapKey().isPresent() && !TFCOreGen.veinsToRemove.contains(i.unwrapKey().get().location())).toList());
                    tags.put(resourceLocation, tag);
                }
            } catch (Exception e) {
                GTLib.LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
