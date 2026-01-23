package org.gtreimagined.gt5r.integration.mekanism;

import net.devtech.arrp.json.tags.JTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.gtreimagined.gt5r.GT5RConfig;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.datagen.GTLibDynamics;
import org.gtreimagined.gtlib.event.GTCraftingEvent;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import org.gtreimagined.gtlib.util.RegistryUtils;

import java.util.List;

public class MekanismRegistrar extends GTMod {
    public MekanismRegistrar() {
        super();
        if (isEnabled()){
            FMLJavaModLoadingContext.get().getModEventBus().register(this);
        }
    }
    @Override
    public void onRegistrationEvent(RegistrationEvent event, Dist side) {
        if (event == RegistrationEvent.DATA_INIT){
            if (GT5RConfig.DISABLE_MEKANISM_OREGEN.get()){
                JTag tag = JTag.tag().replace();
                GTLibDynamics.RUNTIME_DATA_PACK.addTag(new ResourceLocation(getId(), "worldgen/biome/spawn_ores"), tag);
            }
        }
    }

    @SubscribeEvent
    public void registerRecipes(GTCraftingEvent event){
        event.addLoader(MekanismCraftingRecipes::initRecipes);
    }

    @Override
    public String getId() {
        return "mekanism";
    }

    @Override
    public List<String> getDataPackDomains() {
        return List.of("mekanism", "mekanismgenerators", "mekanismadditions");
    }

    @Override
    public boolean isEnabled() {
        return GTAPI.isModLoaded(getId()) && GT5RConfig.ENABLE_MEKANISM_COMPAT.get();
    }

    static Item mekItem(String id){
        return RegistryUtils.getItemFromID("mekanism", id);
    }
    static Item mekGItem(String id){
        return RegistryUtils.getItemFromID("mekanismgenerators", id);
    }
}
