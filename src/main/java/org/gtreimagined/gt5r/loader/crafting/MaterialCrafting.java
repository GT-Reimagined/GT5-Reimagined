package org.gtreimagined.gt5r.loader.crafting;

import com.google.common.collect.ImmutableMap;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RMaterialTypes;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.data.GTCoreMaterials;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.data.ForgeTags;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.pipe.PipeSize;
import org.gtreimagined.gtlib.pipe.types.ItemPipe;
import org.gtreimagined.gtlib.util.Utils;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gt5r.data.GT5RMaterialTypes.CHAMBER;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTTools.*;

public class MaterialCrafting {
    public static void loadRecipes(Consumer<FinishedRecipe> output, GTRecipeProvider provider) {
        addShapelessDustRecipe(output, provider, Bronze, ImmutableMap.of(Copper, 3, Tin, 1));
        addShapelessDustRecipe(output, provider, Brass, ImmutableMap.of(Copper, 3, Zinc, 1));
        addShapelessDustRecipe(output, provider, BlackBronze, ImmutableMap.of(Copper, 3, Silver, 1, Gold, 1));
        addShapelessDustRecipe(output, provider, TinAlloy, ImmutableMap.of(Tin, 1, Iron, 1));
        addShapelessDustRecipe(output, provider, BlackSteel, ImmutableMap.of(Steel, 3, BlackBronze, 1, Nickel, 1));
        addShapelessDustRecipe(output, provider, Electrum, ImmutableMap.of(Gold, 1, Silver, 1));
        addShapelessDustRecipe(output, provider, Invar, ImmutableMap.of(Iron, 2, Nickel, 1));
        addShapelessDustRecipe(output, provider, Cupronickel, ImmutableMap.of(Copper, 1, Nickel, 1));
        addShapelessDustRecipe(output, provider, RoseGold, ImmutableMap.of(Gold, 4, Copper, 1));
        addShapelessDustRecipe(output, provider, SterlingSilver, ImmutableMap.of(Silver, 4, Copper, 1));
        addShapelessDustRecipe(output, provider, BismuthBronze, ImmutableMap.of(Copper, 3, Bismuth, 1, Zinc, 1));
        addShapelessDustRecipe(output, provider, RedSteel, ImmutableMap.of(BlackSteel, 4, Steel, 2, Brass, 1, RoseGold, 1));
        addShapelessDustRecipe(output, provider, BlueSteel, ImmutableMap.of(BlackSteel, 4, Steel, 2, BismuthBronze, 1, SterlingSilver, 1));
        addShapelessDustRecipe(output, provider, CobaltBrass, ImmutableMap.of(Brass, 7, Aluminium, 1, Cobalt, 1));
        addShapelessDustRecipe(output, provider, GalliumArsenide, ImmutableMap.of(Gallium, 1, Arsenic, 1));
        addShapelessDustRecipe(output, provider, IndiumGalliumPhosphide, ImmutableMap.of(Indium, 1, Gallium, 1, Phosphor, 1));
        addShapelessDustRecipe(output, provider, GTCoreMaterials.Signalum, ImmutableMap.of(RedAlloy, 5, Silver, 2, Copper, 1));
        provider.shapeless(output, GT5Reimagined.ID, "", "dusts", GTMaterialTypes.DUST_SMALL.get(Clay, 2), MORTAR.getTag(), Items.CLAY_BALL);
        provider.addItemRecipe(output, GT5Reimagined.ID, "copper_ingot", "ingots", GTMaterialTypes.INGOT.get(Copper), ImmutableMap.of('I', GTMaterialTypes.NUGGET.getMaterialTag(Copper)), "III", "III", "III");
        loadAutoRecipes(output, provider);
        loadMixedMetal(output, provider);
    }

    public static void loadMixedMetal(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        mixedMetalRecipe(consumer, provider, Iron, Bronze, Tin, 1);
        mixedMetalRecipe(consumer, provider, Iron, Bronze, Zinc, 1);
        mixedMetalRecipe(consumer, provider, Iron, Bronze, Aluminium, 1);
        mixedMetalRecipe(consumer, provider, Iron, Brass, Tin, 1);
        mixedMetalRecipe(consumer, provider, Iron, Brass, Zinc, 1);
        mixedMetalRecipe(consumer, provider, Iron, Brass, Aluminium, 1);
        mixedMetalRecipe(consumer, provider, Nickel, Bronze, Tin, 1);
        mixedMetalRecipe(consumer, provider, Nickel, Bronze, Zinc, 1);
        mixedMetalRecipe(consumer, provider, Nickel, Bronze, Aluminium, 1);
        mixedMetalRecipe(consumer, provider, Nickel, Brass, Tin, 1);
        mixedMetalRecipe(consumer, provider, Nickel, Brass, Zinc, 1);
        mixedMetalRecipe(consumer, provider, Nickel, Brass, Aluminium, 1);
        mixedMetalRecipe(consumer, provider, Invar, Bronze, Tin, 2);
        mixedMetalRecipe(consumer, provider, Invar, Bronze, Zinc, 2);
        mixedMetalRecipe(consumer, provider, Invar, Bronze, Aluminium, 3);
        mixedMetalRecipe(consumer, provider, Invar, Brass, Tin, 2);
        mixedMetalRecipe(consumer, provider, Invar, Brass, Zinc, 2);
        mixedMetalRecipe(consumer, provider, Invar, Brass, Aluminium, 3);
        mixedMetalRecipe(consumer, provider, Steel, Bronze, Tin, 2);
        mixedMetalRecipe(consumer, provider, Steel, Bronze, Zinc, 2);
        mixedMetalRecipe(consumer, provider, Steel, Bronze, Aluminium, 3);
        mixedMetalRecipe(consumer, provider, Steel, Brass, Tin, 2);
        mixedMetalRecipe(consumer, provider, Steel, Brass, Zinc, 2);
        mixedMetalRecipe(consumer, provider, Steel, Brass, Aluminium, 3);
        mixedMetalRecipe(consumer, provider, StainlessSteel, Bronze, Tin, 3);
        mixedMetalRecipe(consumer, provider, StainlessSteel, Bronze, Zinc, 3);
        mixedMetalRecipe(consumer, provider, StainlessSteel, Bronze, Aluminium, 4);
        mixedMetalRecipe(consumer, provider, StainlessSteel, Brass, Tin, 3);
        mixedMetalRecipe(consumer, provider, StainlessSteel, Brass, Zinc, 3);
        mixedMetalRecipe(consumer, provider, StainlessSteel, Brass, Aluminium, 4);
        mixedMetalRecipe(consumer, provider, Titanium, Bronze, Tin, 3);
        mixedMetalRecipe(consumer, provider, Titanium, Bronze, Zinc, 3);
        mixedMetalRecipe(consumer, provider, Titanium, Bronze, Aluminium, 4);
        mixedMetalRecipe(consumer, provider, Titanium, Brass, Tin, 3);
        mixedMetalRecipe(consumer, provider, Titanium, Brass, Zinc, 3);
        mixedMetalRecipe(consumer, provider, Titanium, Brass, Aluminium, 4);
        mixedMetalRecipe(consumer, provider, Tungsten, Bronze, Tin, 3);
        mixedMetalRecipe(consumer, provider, Tungsten, Bronze, Zinc, 3);
        mixedMetalRecipe(consumer, provider, Tungsten, Bronze, Aluminium, 4);
        mixedMetalRecipe(consumer, provider, Tungsten, Brass, Tin, 3);
        mixedMetalRecipe(consumer, provider, Tungsten, Brass, Zinc, 3);
        mixedMetalRecipe(consumer, provider, Tungsten, Brass, Aluminium, 4);
        mixedMetalRecipe(consumer, provider, TungstenSteel, Bronze, Tin, 5);
        mixedMetalRecipe(consumer, provider, TungstenSteel, Bronze, Zinc, 5);
        mixedMetalRecipe(consumer, provider, TungstenSteel, Bronze, Aluminium, 6);
        mixedMetalRecipe(consumer, provider, TungstenSteel, Brass, Tin, 5);
        mixedMetalRecipe(consumer, provider, TungstenSteel, Brass, Zinc, 5);
        mixedMetalRecipe(consumer, provider, TungstenSteel, Brass, Aluminium, 6);
    }

    public static void mixedMetalRecipe(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider, Material top, Material middle, Material bottom, int amount){
        provider.addStackRecipe(consumer, GT5Reimagined.ID, "mixed_metal_from_" + top.getId() + "_" + middle.getId() + "_" + bottom.getId(), "mixed_metal", Utils.ca(amount, GTCoreItems.MixedMetalIngot.getMixedMetalIngot(top, middle, bottom)),
                of('T', PLATE.getMaterialTag(top), 'M', PLATE.getMaterialTag(middle), 'B', PLATE.getMaterialTag(bottom)), "T", "M", "B");
    }

    public static void loadAutoRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        DUST.all().forEach(m -> {
            provider.addStackRecipe(consumer, GT5Reimagined.ID, m.getId() + "_small_dust", "gt_materials", DUST_SMALL.get(m, 4),
                    of('D', DUST.getMaterialTag(m)), " D");
            /*provider.addStackRecipe(consumer, GT5RRef.ID, m.getId() + "_tiny_dust", "gt_materials", "has_wrench", in, DUST_TINY.get(m, 9),
                    of('D', DUST.getMaterialTag(m)), "D ");*/
        });
        GT5RMaterialTypes.TURBINE_BLADE.all().forEach(m -> {
            provider.addStackRecipe(consumer, GT5Reimagined.ID, "", "gt_materials",
                    GT5RMaterialTypes.TURBINE_BLADE.get(m, 1), ImmutableMap.<Character, Object>builder()
                            .put('S', SCREWDRIVER.getTag())
                            .put('F', FILE.getTag())
                            .put('P', PLATE.getMaterialTag(m))
                            .put('s', SCREW.getMaterialTag(m)).build(), "FPS", "sPs", " P ");
        });
        CHAMBER.all().forEach(m -> {
            if (!m.has(GEM) && !m.has(PLATE)) return;
            TagKey<Item> input = m == Quartz ? ForgeTags.GEMS_QUARTZ_ALL : m.has(GEM) ? GEM.getMaterialTag(m) :  PLATE.getMaterialTag(m);
            provider.addItemRecipe(consumer, "chambers", CHAMBER.get(m),
                    of('I', input, 'H', HAMMER.getTag() ,'W', WRENCH.getTag()), "IHI", "IWI", "III");
        });
        GTAPI.all(ItemPipe.class, i -> {
            if (i.getSizes().contains(PipeSize.NORMAL)){
                provider.addStackRecipe(consumer, GT5Reimagined.ID, "", "gt_pipes", new ItemStack(i.getRestrictedBlock(PipeSize.NORMAL), 1), of('H', HAMMER.getTag(), 'R', RING.getMaterialTag(Steel), 'P', i.getBlock(PipeSize.NORMAL)), " H ", "RPR", " R ");
            }
            if (i.getSizes().contains(PipeSize.LARGE)){
                provider.addStackRecipe(consumer, GT5Reimagined.ID, "", "gt_pipes", new ItemStack(i.getRestrictedBlock(PipeSize.LARGE), 1), of('H', HAMMER.getTag(), 'R', RING.getMaterialTag(Steel), 'P', i.getBlock(PipeSize.LARGE)), "HR ", "RPR", " R ");
            }
            if (i.getSizes().contains(PipeSize.HUGE)) {
                provider.addStackRecipe(consumer, GT5Reimagined.ID, "", "gt_pipes", new ItemStack(i.getRestrictedBlock(PipeSize.HUGE), 1), of('H', HAMMER.getTag(), 'R', RING.getMaterialTag(Steel), 'P', i.getBlock(PipeSize.HUGE)), " H ", "RPR", "RRR");
            }
        });
        RAW_ORE_BLOCK.all().forEach(m -> {
            if (m.has(RAW_ORE)){
                if (RAW_ORE.hasReplacement(m) && RAW_ORE_BLOCK.hasReplacement(m)) return; //assumes mods it uses for replacement already adds a recipe
                provider.addItemRecipe(consumer, "blocks", RAW_ORE_BLOCK.get().get(m).asItem(), of('I', RAW_ORE.getMaterialTag(m)), "III", "III", "III");
                provider.shapeless(consumer, "", "blocks", RAW_ORE.get(m, 9), RAW_ORE_BLOCK.getMaterialTag(m));
            }
        });
    }

    private static void addShapelessDustRecipe(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider, Material output, ImmutableMap<Material, Integer> inputs){
        int sum = inputs.values().stream().mapToInt(i -> i).sum();
        if (sum > 9) return;
        Object[] inputArray = new Object[sum];
        int index = 0;
        for (var entry : inputs.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                inputArray[index] = DUST.getMaterialTag(entry.getKey());
                index++;
            }
        }
        provider.shapeless(consumer, GT5Reimagined.ID, output.getId() + "_dust", "dusts", DUST.get(output, sum), inputArray);
    }
}
