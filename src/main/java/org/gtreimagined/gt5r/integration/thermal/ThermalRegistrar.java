package org.gtreimagined.gt5r.integration.thermal;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.ClimateSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings.Builder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.BiomeModifier.Phase;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.worldgen.GTLibWorldGenerator;
import org.gtreimagined.gtlib.worldgen.IGTWorldgenFunction;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.loader.multi.BlastFurnaceLoader.addBlastAlloyRecipes;

public class ThermalRegistrar extends GTMod implements IGTWorldgenFunction {

    public static void thermalMachineRecipes(){

        addBlastAlloyRecipes(Enderium, 4, 1000, 120, ImmutableMap.of(Tin, 2, Silver, 1, Platinum, 1, EnderPearl, 4));
    }

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
        return "thermal";
    }

    @Override
    public void build(Phase phase, Holder<Biome> biomeHolder, ClimateSettings climate, BiomeSpecialEffects effects, BiomeGenerationSettingsBuilder gen, Builder spawns, Registry<PlacedFeature> placedFeatureRegistry) {
        if (phase == Phase.REMOVE){
            String[] oreTypes = new String[]{"apatite", "cinnabar", "niter", "sulfur", "tin", "lead", "silver", "nickel", "ruby", "sapphire"};
            for (String oreType : oreTypes){
                GTLibWorldGenerator.removeDecoratedFeatureFromAllBiomes(gen, Decoration.UNDERGROUND_ORES, Feature.ORE, tb(oreType + "_ore").defaultBlockState(), tb("deepslate_" + oreType + "_ore").defaultBlockState());
            }
        }
    }

    private Block tb(String id){
        return RegistryUtils.getBlockFromId(getId(), id);
    }
}
