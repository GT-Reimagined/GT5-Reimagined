package org.gtreimagined.gt5r.integration;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.event.GTCraftingEvent;
import org.gtreimagined.gtlib.registration.RegistrationEvent;

import java.util.function.Consumer;

public class BluepowerRegistrar extends GTMod {
    public BluepowerRegistrar(){
        if (isEnabled()){
            FMLJavaModLoadingContext.get().getModEventBus().<GTCraftingEvent>addListener(e -> e.addLoader(this::initRecipes));
        }
    }
    @Override
    public void onRegistrationEvent(RegistrationEvent event, Dist side) {

    }

    public void initRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider) {
        provider.removeRecipe(new ResourceLocation(getId(), "alloy_furnace/zincplate"));
        provider.removeRecipe(new ResourceLocation(getId(), "alloy_furnace/red_alloy_ingot"));
        provider.removeRecipe(new ResourceLocation(getId(), "alloy_furnace/red_alloy_ingot_copper"));
        provider.removeRecipe(new ResourceLocation(getId(), "alloy_furnace/blue_alloy_ingot"));
        provider.removeRecipe(new ResourceLocation(getId(), "alloy_furnace/tungsten_carbide"));
    }


    @Override
    public boolean isEnabled() {
        return GTAPI.isModLoaded(getId());
    }

    @Override
    public String getId() {
        return "bluepower";
    }
}
