package org.gtreimagined.gt5r.integration.mekanism;

import com.google.common.collect.ImmutableMap;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import org.gtreimagined.gt5r.GT5RConfig;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gt5r.integration.tfc.TFCRegistrar;
import org.gtreimagined.gtcore.data.GTCoreTags;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.util.TagUtils;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gt5r.data.Materials.Copper;
import static org.gtreimagined.gt5r.integration.mekanism.MekanismRegistrar.mekGItem;
import static org.gtreimagined.gt5r.integration.mekanism.MekanismRegistrar.mekItem;

public class MekanismCraftingRecipes {
    public static void initRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        var inputType = GT5RConfig.GREGIFY_MEK_RECIPES.get() ? GTMaterialTypes.PLATE : GTMaterialTypes.INGOT;
        Material osmium = GT5RConfig.REPLACE_MEK_OSMIUM_WITH_GERMANIUM.get() ? Materials.Germanium : Materials.Osmium;
        String[] factories = {"smelting", "enriching", "crushing", "compressing", "combining", "purifying", "injecting", "infusing", "sawing"};
        for (String factory : factories) {
            provider.addItemRecipe(consumer, "mekanism", "factory/advanced/" + factory, "machines", mekItem("advanced_" + factory + "_factory"),
                    of('O', inputType.getMaterialTag(osmium), 'I', mekItem("alloy_infused"), 'C', GTCoreTags.CIRCUITS_ADVANCED, 'B', mekItem("basic_" + factory + "_factory")), "ICI", "OBO", "ICI");
        }
        provider.addItemRecipe(consumer, "machines", mekItem("metallurgic_infuser"),
                of('I', inputType.getMaterialTag(TFCRegistrar.getIron()), 'F', Items.FURNACE, 'R', Items.REDSTONE, 'O', inputType.getMaterialTag(osmium)), "IFI", "ROR", "IFI");
        provider.addItemRecipe(consumer, "machines", mekItem("purification_chamber"),
                of('O', inputType.getMaterialTag(osmium), 'I', mekItem("alloy_infused"), 'C', GTCoreTags.CIRCUITS_ADVANCED, 'B', mekItem("enrichment_chamber")), "ICI", "OBO", "ICI");
        provider.addItemRecipe(consumer, "machines", mekItem("electric_pump"),
                of('O', inputType.getMaterialTag(osmium), 'I', mekItem("alloy_infused"), 'B', Items.BUCKET, 'C', mekItem("steel_casing")), " B ", "ICI", "OOO");
        provider.addItemRecipe(consumer, "mekanism", "energy_cube/advanced", "machines", mekItem("advanced_energy_cube"),
                of('O', inputType.getMaterialTag(osmium), 'I', mekItem("alloy_infused"), 'C', mekItem("energy_tablet"), 'B', mekItem("basic_energy_cube")), "ICI", "OBO", "ICI");
        provider.addItemRecipe(consumer, "mekanism", "tier_installer/advanced", "machines", mekItem("advanced_tier_installer"),
                of('O', inputType.getMaterialTag(osmium), 'C', GTCoreTags.CIRCUITS_ADVANCED, 'I', mekItem("alloy_infused"), 'P', ItemTags.PLANKS), "ICI", "OPO", "ICI");
        provider.addItemRecipe(consumer, "mekanism", "chemical_tank/basic", "machines", mekItem("basic_chemical_tank"),
                of('O', inputType.getMaterialTag(osmium), 'R', Items.REDSTONE), "ROR", "O O", "ROR");
        provider.addItemRecipe(consumer, "mekanism", "chemical_tank/advanced", "machines", mekItem("advanced_chemical_tank"),
                of('O', inputType.getMaterialTag(osmium), 'R', mekItem("alloy_infused"), 'T', mekItem("basic_chemical_tank")), "ROR", "OTO", "ROR");
        provider.addItemRecipe(consumer, "mekanism", "chemical_tank/elite", "machines", mekItem("elite_chemical_tank"),
                of('O', inputType.getMaterialTag(osmium), 'R', mekItem("alloy_reinforced"), 'T', mekItem("advanced_chemical_tank")), "ROR", "OTO", "ROR");
        provider.addItemRecipe(consumer, "mekanism", "chemical_tank/ultimate", "machines", mekItem("ultimate_chemical_tank"),
                of('O', inputType.getMaterialTag(osmium), 'R', mekItem("alloy_atomic"), 'T', mekItem("elite_chemical_tank")), "ROR", "OTO", "ROR");
        provider.addItemRecipe(consumer, "machines", mekItem("steel_casing"),
                of('S', inputType.getMaterialTag(Materials.Steel), 'G', TagUtils.getForgelikeItemTag("glass/silica"), 'O', inputType.getMaterialTag(osmium)), "SGS", "GOG", "SGS");
        provider.addItemRecipe(consumer, "misc", mekItem("gauge_dropper"),
                of('O', inputType.getMaterialTag(osmium), 'G', Tags.Items.GLASS_PANES), " O ", "G G", "GGG");
        if (GTAPI.isModLoaded("mekanismgenerators")){
            provider.addStackRecipe(consumer, "mekanismgenerators", "turbine/casing", "generators", new ItemStack(mekGItem("turbine_casing"), 4),
                    of('S', inputType.getMaterialTag(Materials.Steel), 'O', inputType.getMaterialTag(osmium)), " S ", "SOS", " S ");
            provider.addItemRecipe(consumer, "generators", mekGItem("solar_panel"),
                    of('G', Tags.Items.GLASS_PANES, 'R', Items.REDSTONE, 'I', mekItem("alloy_infused"), 'O', inputType.getMaterialTag(osmium)), "GGG", "RIR", "OOO");
            provider.addItemRecipe(consumer, "mekanismgenerators", "generator/solar", "generators", mekGItem("solar_generator"),
                    of('S', mekGItem("solar_panel"), 'A', mekItem("alloy_infused"), 'I', inputType.getMaterialTag(TFCRegistrar.getIron()), 'O', inputType.getMaterialTag(osmium), 'E', mekItem("energy_tablet")), "SSS","AIA", "OEO");
            provider.addItemRecipe(consumer, "mekanismgenerators", "generator/gas_burning", "generators", mekGItem("gas_burning_generator"),
                    of('O', inputType.getMaterialTag(osmium), 'A', mekItem("alloy_infused"), 'C', mekItem("steel_casing"), 'E', mekItem("electrolytic_core")), "OAO", "CEC", "OAO");
            provider.addItemRecipe(consumer, "mekanismgenerators", "generator/wind", "generators", mekGItem("wind_generator"),
                    of('O', inputType.getMaterialTag(osmium), 'A', mekItem("alloy_infused"), 'E', mekItem("energy_tablet"), 'C', GTCoreTags.CIRCUITS_BASIC), " O ", "OAO", "ECE");
            provider.addItemRecipe(consumer, "mekanismgenerators", "generator/heat", "generators", mekGItem("heat_generator"),
                    of('O', inputType.getMaterialTag(osmium), 'I', inputType.getMaterialTag(TFCRegistrar.getIron()), 'C', inputType.getMaterialTag(Copper), 'F', Items.FURNACE, 'P', ItemTags.PLANKS), "III", "POP", "CFC");
        }
        if (GTAPI.isModLoaded("mekanismadditions")){
            provider.addItemRecipe(consumer, "misc", RegistryUtils.getItemFromID("mekanismadditions", "walkie_talkie"),
                    of('S', inputType.getMaterialTag(Materials.Steel), 'O', inputType.getMaterialTag(osmium), 'C', GTCoreTags.CIRCUITS_BASIC), "  O", "SCS", " S ");
        }



    }


}
