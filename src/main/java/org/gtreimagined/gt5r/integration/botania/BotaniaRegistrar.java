package org.gtreimagined.gt5r.integration.botania;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.event.GTCraftingEvent;
import org.gtreimagined.gtlib.event.MaterialEvent;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.block.block_entity.PetalApothecaryBlockEntity;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class BotaniaRegistrar extends GTMod {
    public BotaniaRegistrar(){
        if (isEnabled()){
            FMLJavaModLoadingContext.get().getModEventBus().<GTCraftingEvent>addListener(e -> e.addLoader(BotaniaRecipes::init));
            MinecraftForge.EVENT_BUS.addGenericListener(BlockEntity.class, this::onAttachCaps);
        }
    }
    @Override
    public void onRegistrationEvent(RegistrationEvent event, Dist side) {

    }

    @Override
    public void onMaterialEvent(MaterialEvent event) {
        event.setMaterial(Materials.Manasteel).asMetal(2311, LONG_ROD, MaterialTags.MAGIC)
                .tool(Iron).toolQuality(3).toolSpeed(12).toolDurability(300).toolEnchantments(ImmutableMap.of(Enchantments.MOB_LOOTING, 2, Enchantments.BLOCK_FORTUNE, 2))
                .addBehaviour(ManaRegenerationBehaviour.INSTANCE).handleMaterial(Livingwood).build()
                .mats(ImmutableMap.of(Steel, 1, Magic, 1));
        event.setMaterial(Terrasteel).asMetal(2561, MaterialTags.MAGIC)
                .tool().toolDamage(4).toolQuality(4).toolSpeed(16).toolDurability(2048).handleMaterial(Livingwood).build()
                .mats(ImmutableMap.of(Manasteel, 1, Diamond, 1, EnderPearl, 1));
        event.setMaterial(Elementium).asMetal(2811, MaterialTags.MAGIC)
                .tool().toolDamage(3).toolQuality(3).toolSpeed(14).toolDurability(512).handleMaterial(Dreamwood).build();
        event.setMaterial(GaiaSpirit).asMetal(3945, LONG_ROD, MaterialTags.MAGIC, MaterialTags.RAINBOW_RGB)
                .tool().toolDamage(4).toolQuality(4).toolSpeed(20).toolDurability(2048).handleMaterial(Elementium).build();
        ROD.replacement(Livingwood, () -> botItem("livingwood_twig"));
        ROD.replacement(Dreamwood, () -> botItem("dreamwood_twig"));
        INGOT.replacement(Materials.Manasteel, () -> botItem("manasteel_ingot"));
        NUGGET.replacement(Materials.Manasteel, () -> botItem("manasteel_nugget"));
        INGOT.replacement(Terrasteel, () -> botItem("terrasteel_ingot"));
        NUGGET.replacement(Terrasteel, () -> botItem("terrasteel_nugget"));
        INGOT.replacement(Elementium, () -> botItem("elementium_ingot"));
        NUGGET.replacement(Elementium, () -> botItem("elementium_nugget"));
        INGOT.replacement(GaiaSpirit, () -> botItem("gaia_ingot"));
        GTTools.PICKAXE.addReplacement(Materials.Manasteel, () -> botItem("manasteel_pick"));
        GTTools.AXE.addReplacement(Materials.Manasteel, () -> botItem("manasteel_axe"));
        GTTools.SWORD.addReplacement(Materials.Manasteel, () -> botItem("manasteel_sword"));
        GTTools.SHOVEL.addReplacement(Materials.Manasteel, () -> botItem("manasteel_shovel"));
        GTTools.HOE.addReplacement(Materials.Manasteel, () -> botItem("manasteel_hoe"));
        GTTools.PICKAXE.addReplacement(Elementium, () -> botItem("elementium_pickaxe"));
        GTTools.AXE.addReplacement(Elementium, () -> botItem("elementium_axe"));
        GTTools.SWORD.addReplacement(Elementium, () -> botItem("elementium_sword"));
        GTTools.SHOVEL.addReplacement(Elementium, () -> botItem("elementium_shovel"));
        GTTools.HOE.addReplacement(Elementium, () -> botItem("elementium_hoe"));
    }

    @Override
    public boolean isEnabled() {
        return GTAPI.isModLoaded(getId());
    }

    static Item botItem(String id) {
        return RegistryUtils.getItemFromID("botania", id);
    }

    @Override
    public String getId() {
        return "botania";
    }

    @Override
    public int getPriority() {
        return 700;
    }

    private void onAttachCaps(AttachCapabilitiesEvent<BlockEntity> event){
        if (event.getObject() instanceof PetalApothecaryBlockEntity){
            event.addCapability(new ResourceLocation(GT5Reimagined.ID, "petal_apothecary"), new ICapabilityProvider() {
                @Override
                public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
                    if (capability == ForgeCapabilities.FLUID_HANDLER){
                        return LazyOptional.of(() -> new PetalApothecaryWrapper(event.getObject().getBlockState(), event.getObject().getLevel(), event.getObject().getBlockPos())).cast();
                    }
                    return LazyOptional.empty();
                }
            });
        }
    }
}
