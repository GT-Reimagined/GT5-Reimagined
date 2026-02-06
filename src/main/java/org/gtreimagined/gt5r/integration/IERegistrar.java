package org.gtreimagined.gt5r.integration;

import blusunrize.immersiveengineering.common.world.IEOreFeature.IEOreFeatureConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.ClimateSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings.Builder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.BiomeModifier.Phase;
import net.minecraftforge.registries.ForgeRegistries;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.worldgen.GTLibWorldGenerator;
import org.gtreimagined.gtlib.worldgen.IGTWorldgenFunction;

import java.util.ArrayList;
import java.util.List;

public class IERegistrar extends GTMod implements IGTWorldgenFunction {
    @Override
    public void onRegistrationEvent(RegistrationEvent event, Dist side) {
        if (event == RegistrationEvent.DATA_INIT){
            GTAPI.register(IGTWorldgenFunction.class, this);
        }
    }

    @Override
    public boolean isEnabled() {
        return GTAPI.isModLoaded(getId());
    }

    @Override
    public String getId() {
        return "immersiveengineering";
    }

    @Override
    public void build(Phase phase, Holder<Biome> biomeHolder, ClimateSettings climate, BiomeSpecialEffects effects, BiomeGenerationSettingsBuilder gen, Builder spawns, Registry<PlacedFeature> placedFeatureRegistry) {
        if (phase == Phase.REMOVE){
            String[] oreTypes = new String[]{"aluminum", "lead", "silver", "nickel", "uranium"};
            for (String oreType : oreTypes){
                List<BlockState> blockStates = List.of(ib("ore_" + oreType).defaultBlockState(), ib("deepslate_ore_" + oreType).defaultBlockState());
                gen.getFeatures(Decoration.UNDERGROUND_ORES).removeIf(f -> {
                    if (f.get().feature().get().config() instanceof IEOreFeatureConfig oreFeatureConfig){
                        return oreFeatureConfig.targetList().stream().anyMatch(t -> blockStates.contains(t.state));
                    }
                    return false;
                });
            }
        }
    }

    private Block ib(String id){
        return RegistryUtils.getBlockFromId(getId(), id);
    }

}
