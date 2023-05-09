package muramasa.gregtech.loader.crafting;

import com.github.gregtechintergalactical.gtrubber.GTRubberData;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.data.AntimatterMaterials;
import muramasa.antimatter.data.ForgeCTags;
import muramasa.antimatter.datagen.providers.AntimatterRecipeProvider;
import muramasa.antimatter.util.TagUtils;
import muramasa.gregtech.GTIRef;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.data.AntimatterMaterials.Coal;
import static muramasa.gregtech.data.GregTechData.ItemFilter;
import static muramasa.gregtech.data.Materials.*;

public class VanillaExtensions {
    public static void loadRecipes(Consumer<FinishedRecipe> consumer, AntimatterRecipeProvider provider) {
        provider.addConditionalRecipe(consumer, provider.getStackRecipe("", "has_sulfur_dust", provider.hasSafeItem(TagUtils.getForgelikeItemTag("dusts/sulfur")),
                new ItemStack(Blocks.TORCH, 2), of('D', TagUtils.getForgelikeItemTag("dusts/sulfur"), 'R', ForgeCTags.RODS_WOODEN), "D", "R"), GTIRef.class, "sulfurTorch", GTIRef.ID, "sulfur_torch");

        provider.addItemRecipe(consumer, GTIRef.ID, "hopper", "", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()),
                Blocks.HOPPER, of('C', Blocks.CHEST, 'I', TagUtils.getForgelikeItemTag("plates/iron"), 'W', AntimatterDefaultTools.WRENCH.getTag()), "IWI", "ICI", " I ");

        provider.addItemRecipe(consumer, GTIRef.ID,"filter", "", "has_iron_plate", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()),
                ItemFilter, of('Z', TagUtils.getForgelikeItemTag("foils/zinc"), 'I', TagUtils.getForgelikeItemTag("plates/iron")), "ZZZ", "ZIZ", "ZZZ");

        provider.addItemRecipe(consumer, GTIRef.ID,AntimatterMaterialTypes.GEAR.get(AntimatterMaterials.Wood).getRegistryName().getPath() , "gears", "has_wooden_rod", provider.hasSafeItem(ForgeCTags.RODS_WOODEN),
                AntimatterMaterialTypes.GEAR.get(AntimatterMaterials.Wood), of('P', ItemTags.PLANKS, 'W', AntimatterDefaultTools.WRENCH.getTag(), 'R', ForgeCTags.RODS_WOODEN), "RPR", "PWP", "RPR");

        provider.addItemRecipe(consumer, GTIRef.ID, AntimatterMaterialTypes.GEAR.get(AntimatterMaterials.Wood).getRegistryName().getPath() + "_alt", "gears", "has_wooden_rod", provider.hasSafeItem(ForgeCTags.RODS_WOODEN),
                AntimatterMaterialTypes.GEAR.get(AntimatterMaterials.Wood), of('P', ItemTags.PLANKS, 'W', AntimatterDefaultTools.WRENCH.getTag(), 'R', ForgeCTags.RODS_WOODEN), "RPR", "PWP", "RPR");

        provider.addItemRecipe(consumer, GTIRef.ID, AntimatterMaterialTypes.GEAR.get(AntimatterMaterials.Stone).getRegistryName().getPath() , "gears", "has_stone", provider.hasSafeItem(ForgeCTags.STONE),
                AntimatterMaterialTypes.GEAR.get(AntimatterMaterials.Stone), of('S', ForgeCTags.STONE, 'W', AntimatterDefaultTools.WRENCH.getTag(), 'C', ForgeCTags.COBBLESTONE), "SCS", "CWC", "SCS");

        provider.addItemRecipe(consumer, GTIRef.ID, AntimatterMaterialTypes.GEAR.get(AntimatterMaterials.Stone).getRegistryName().getPath() + "_alt", "gears", "has_stone", provider.hasSafeItem(ForgeCTags.STONE),
                AntimatterMaterialTypes.GEAR.get(AntimatterMaterials.Stone), of('S', ForgeCTags.STONE, 'W', AntimatterDefaultTools.WRENCH.getTag(), 'C', ForgeCTags.COBBLESTONE), "CSC", "SWS", "CSC");

        provider.addItemRecipe(consumer, GTIRef.ID, "piston_sticky","gears", "has_stone", provider.hasSafeItem(Blocks.PISTON), Blocks.STICKY_PISTON, of('S', GTRubberData.StickyResin, 'P', Blocks.PISTON), "S", "P");

        provider.addItemRecipe(consumer, "magnetic_rods_iron", "has_redstone", provider.hasSafeItem(ForgeCTags.DUSTS_REDSTONE), ROD.get(IronMagnetic),
                of('R', ForgeCTags.DUSTS_REDSTONE, 'S', TagUtils.getForgelikeItemTag("rods/iron")), " R ", "RSR", " R ");

        provider.addItemRecipe(consumer, "magnetic_rods_neodymium", "has_redstone", provider.hasSafeItem(ForgeCTags.DUSTS_REDSTONE), ROD.get(Neodymium),
                of('R', ForgeCTags.DUSTS_REDSTONE, 'S', TagUtils.getForgelikeItemTag("rods/neodymium")), " R ", "RSR", " R ");
        provider.addStackRecipe(consumer, GTIRef.ID, "torch_from_coal", "torches", "has_coal_dust", provider.hasSafeItem(DUST_IMPURE.get(Coal)), new ItemStack(Items.TORCH, 4),
                of('C', Ingredient.of(RAW_ORE.get(Coal), DUST.get(Coal), DUST_IMPURE.get(Coal), DUST_PURE.get(Coal), CRUSHED.get(Coal),CRUSHED_PURIFIED.get(Coal), CRUSHED_REFINED.get(Coal)), 'S', Items.STICK), "C", "S");
        provider.addStackRecipe(consumer, GTIRef.ID, "torch_from_lignite", "torches", "has_lignite_dust", provider.hasSafeItem(DUST_IMPURE.get(Lignite)), new ItemStack(Items.TORCH, 2),
                of('C', Ingredient.of(GEM.get(Lignite), RAW_ORE.get(Lignite), DUST.get(Lignite), DUST_IMPURE.get(Lignite), DUST_PURE.get(Lignite), CRUSHED.get(Coal),CRUSHED_PURIFIED.get(Lignite), CRUSHED_REFINED.get(Lignite)), 'S', Items.STICK), "C", "S");
    }
}
