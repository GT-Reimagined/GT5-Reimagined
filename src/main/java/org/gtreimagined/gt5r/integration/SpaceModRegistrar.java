package org.gtreimagined.gt5r.integration;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.api.distmarker.Dist;
import org.gtreimagined.gt5r.GT5RRef;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.event.MaterialEvent;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.TextureSet;
import org.gtreimagined.gtlib.ore.StoneType;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import org.gtreimagined.gtlib.texture.Texture;
import org.gtreimagined.gtlib.util.RegistryUtils;

import static org.gtreimagined.gt5r.data.Materials.Diamond;
import static org.gtreimagined.gt5r.data.Materials.Iron;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class SpaceModRegistrar extends GTMod {
    public static SpaceModRegistrar INSTANCE;
    public static Material Desh;
    public SpaceModRegistrar(){
        INSTANCE = this;
    }

    @Override
    public String getId() {
        return "gt_space";
    }

    @Override
    public void onRegistrationEvent(RegistrationEvent event, Dist side) {
        if (event == RegistrationEvent.DATA_INIT){
            Desh = GTAPI.register(Material.class, new Material(GT5RRef.ID, "desh", 0x282828, TextureSet.DULL));
            String block = GTAPI.isModLoaded("ad_astra") ? "block" : "blocks";
            GTAPI.register(StoneType.class, new StoneType(GT5RRef.ID, "moon_sand", Material.NULL, new Texture(getMod(), block + "/moon_sand"), SoundType.SAND, false).setState(getSpaceBlock("moon_sand")).setSandLike(true));
            var moonStone = GTAPI.register(StoneType.class, new StoneType(GT5RRef.ID, "moon_stone", Material.NULL, new Texture(getMod(), block + "/moon_stone"), SoundType.STONE, false).setState(getSpaceBlock("moon_stone")));
            GTAPI.register(StoneType.class, new StoneType(GT5RRef.ID, "mars_sand", Material.NULL, new Texture(getMod(), block + "/mars_sand"), SoundType.SAND, false).setState(getSpaceBlock("mars_sand")).setSandLike(true));
            var marsStone = GTAPI.register(StoneType.class, new StoneType(GT5RRef.ID, "mars_stone", Material.NULL, new Texture(getMod(), block + "/mars_stone"), SoundType.STONE, false).setState(getSpaceBlock("mars_stone")));
            ORE.replacement(Iron, moonStone, () -> getSpaceBlock("moon_iron_ore").asItem());
            ORE.replacement(Iron, marsStone, () -> getSpaceBlock("mars_iron_ore").asItem());
            ORE.replacement(Diamond, marsStone, () -> getSpaceBlock("mars_diamond_ore").asItem());
            INGOT.replacement(Desh, () -> getSpaceItem("desh_ingot"));
            PLATE.replacement(Desh, () -> getSpaceItem("desh_plate"));
            NUGGET.replacement(Desh, () -> getSpaceItem("desh_nugget"));
            RAW_ORE.replacement(Desh, () -> getSpaceItem("raw_desh"));
            BLOCK.replacement(Desh, () -> getSpaceBlock("desh_block").asItem());
            RAW_ORE_BLOCK.replacement(Desh, () -> getSpaceBlock("raw_desh_block").asItem());
            ORE.replacement(Desh, moonStone, () -> getSpaceBlock("moon_desh_ore").asItem());
        }
    }

    @Override
    public void onMaterialEvent(MaterialEvent event) {
        super.onMaterialEvent(event);
        event.setMaterial(Desh).asOre().asMetal().tool().toolQuality(3).toolSpeed(4.0f).toolDurability(1280).toolDamage(2.5f).handleMaterial(Desh).build();
    }

    @Override
    public boolean isEnabled() {
        return (GTAPI.isModLoaded("ad_astra") || GTAPI.isModLoaded("beyond_earth")) && !GTAPI.isModLoaded(Ref.MOD_GC);
    }

    private static String getMod(){
        return GTAPI.isModLoaded("ad_astra") ? "ad_astra" : "beyond_earth";
    }

    public static Block getSpaceBlock(String id){
        return RegistryUtils.getBlockFromId(getMod(), id);
    }
    public static Item getSpaceItem(String id){
        return RegistryUtils.getItemFromID(getMod(), id);
    }
}
