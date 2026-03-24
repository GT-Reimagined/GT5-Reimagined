package org.gtreimagined.gt5r.integration.ie;

import blusunrize.immersiveengineering.common.world.IEOreFeature.IEOreFeatureConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.ClimateSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings.Builder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.BiomeModifier.Phase;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.event.GTCraftingEvent;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.util.TagUtils;
import org.gtreimagined.gtlib.worldgen.IGTWorldgenFunction;

import java.util.List;
import java.util.function.Consumer;

import static org.gtreimagined.gt5r.data.Materials.*;

public class IERegistrar extends GTMod implements IGTWorldgenFunction {
    public IERegistrar(){
        if (isEnabled()){
            FMLJavaModLoadingContext.get().getModEventBus().<GTCraftingEvent>addListener(e -> e.addLoader(this::init));
        }
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
        return RegistryUtils.getBlockFromId(ieId(id));
    }
    private ResourceLocation ieId(String id){
        return new ResourceLocation(getId(), id);
    }

    public void init(Consumer<FinishedRecipe> output, GTRecipeProvider provider){
        Material[] plates = new Material[]{Iron, Gold, Copper, Aluminium, Nickel, Silver, Lead, Cupronickel, Electrum, Steel, Uranium};
        for (Material plate : plates){
            String plateId = plate == Aluminium ? "aluminum" : plate == Cupronickel ? "constantan" : plate.getId();
            provider.removeRecipe(ieId("crafting/plate_" + plateId + "_hammering"));
            provider.removeRecipe(ieId("crafting/stick_" + plateId));
        }
        Material[] blocks = new Material[]{Aluminium, Nickel, Silver, Lead, Cupronickel, Electrum, Steel, Uranium};
        for (Material block : blocks){
            String blockId = block == Aluminium ? "aluminum" : block == Cupronickel ? "constantan" : block.getId();
            provider.removeRecipe(ieId("crafting/ingot_" + blockId + "_to_storage_" + blockId));
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(TagUtils.getForgelikeItemTag("storage_blocks/" + blockId)), RecipeCategory.BUILDING_BLOCKS, ib("storage_" + blockId))
                    .unlockedBy("has_block", provider.hasSafeItem(TagUtils.getForgelikeItemTag("storage_blocks/" + blockId))).save(output, new ResourceLocation(GT5Reimagined.ID, "stonecutting/ie_block_of_" + blockId));
        }
    }

}
