package muramasa.gregtech.loader.crafting;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.datagen.providers.AntimatterRecipeProvider;
import muramasa.antimatter.pipe.PipeSize;
import muramasa.antimatter.pipe.types.Wire;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static muramasa.antimatter.pipe.PipeSize.VTINY;
import static muramasa.antimatter.pipe.PipeSize.values;

public class WireCablesPlates {
    @SuppressWarnings("unchecked")
    public static void loadRecipes(Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider) {
        AntimatterAPI.all(Wire.class, wire -> {
            ImmutableSet<PipeSize> sizes = wire.getSizes();
            Map<PipeSize, Item> wires = sizes.stream().map(s -> new Pair<>(s, wire.getBlockItem(s))).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
            PipeSize[] val = values();
            for (int i = 1; i < val.length; i += 1) {
                oneToTwo(wires, val[i], val[i-1], output, provider);
                twoToOne(wires, val[i-1], val[i], output,provider);
                if (i > 1) {
                    fourToOne(wires, val[i-2], val[i], output, provider);
                }
            }
            if (wire.getMaterial().has(AntimatterMaterialTypes.PLATE)) {
                provider.shapeless(output,  wire.getMaterial().getId() + "_plate_to_wire","wire","has_cutter", provider.hasSafeItem(AntimatterDefaultTools.WIRE_CUTTER.getTag()),
                        new ItemStack(wires.get(VTINY)),
                        AntimatterDefaultTools.WIRE_CUTTER.getTag(), AntimatterMaterialTypes.PLATE.get(wire.getMaterial()));
            }
        });

    }

    private static void twoToOne(Map<PipeSize, Item> wires, PipeSize from, PipeSize to, Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider) {
        provider.shapeless(output,"two_to_one_" + AntimatterPlatformUtils.getIdFromItem(wires.get(to)).getPath(),"wire","has_cutter",provider.hasSafeItem(AntimatterDefaultTools.WIRE_CUTTER.getTag()),
                new ItemStack(wires.get(to),1),wires.get(from),wires.get(from));
    }

    private static void oneToTwo(Map<PipeSize, Item> wires, PipeSize from, PipeSize to, Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider) {
        provider.shapeless(output,"one_to_two_" + AntimatterPlatformUtils.getIdFromItem(wires.get(to)).getPath(),"wire","has_cutter",provider.hasSafeItem(AntimatterDefaultTools.WIRE_CUTTER.getTag()),
                new ItemStack(wires.get(to),2),wires.get(from));
    }

    private static void fourToOne(Map<PipeSize, Item> wires, PipeSize from, PipeSize to, Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider) {
        provider.shapeless(output,"four_to_one_" + AntimatterPlatformUtils.getIdFromItem(wires.get(to)).getPath(),"wire","has_cutter",provider.hasSafeItem(AntimatterDefaultTools.WIRE_CUTTER.getTag()),
                new ItemStack(wires.get(to),1),wires.get(from),wires.get(from),wires.get(from),wires.get(from));
    }
}
