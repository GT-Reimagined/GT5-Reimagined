package muramasa.gregtech.integration.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryStacks;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.pipe.PipeSize;
import muramasa.antimatter.pipe.types.Wire;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.List;

import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.integration.rei.REIUtils.toREIFLuidStack;
import static muramasa.antimatter.material.MaterialTags.CABLE;
import static muramasa.antimatter.material.MaterialTags.WIRE;

public class MaterialTreeDisplay implements Display {
    Material mat;
    private List<EntryIngredient> input, output;

    public MaterialTreeDisplay(Material material){
        this.mat = material;
        this.input = createInputEntries(List.of(AntimatterMaterialTypes.DUST.getMaterialIngredient(material, 1)));
        this.output = List.of(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.FLUID, toREIFLuidStack((mat.getLiquid(1000))))));
        if(mat.has(DUST_SMALL)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack(DUST_SMALL.get(mat),1))));
        }
        if(mat.has(DUST_TINY)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack(DUST_TINY.get(mat),1))));
        }
        if(mat.has(INGOT)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack(INGOT.get(mat),1))));
        }
        if(mat.has(BLOCK)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,AntimatterMaterialTypes.BLOCK.get().get(mat).asStack(1))));
        }
        if(mat.has(NUGGET)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack(NUGGET.get(mat),1))));
        }
        if(mat.has(PLATE)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack(PLATE.get(mat),1))));
        }
        if(mat.has(PLATE_DENSE)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack(PLATE_DENSE.get(mat),1))));
        }
        if(mat.has(GEAR)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack(GEAR.get(mat),1))));
        }
        if(mat.has(GEAR_SMALL)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack(GEAR_SMALL.get(mat),1))));
        }
        if(mat.has(ROD)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack(ROD.get(mat),1))));
        }
        if(mat.has(ROD_LONG)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack(ROD_LONG.get(mat),1))));
        }
        if(mat.has(RING)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack(RING.get(mat),1))));
        }
        if(mat.has(BOLT)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack(BOLT.get(mat),1))));
        }
        if(mat.has(SCREW)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack(SCREW.get(mat),1))));
        }
        if(mat.has(WIRE)){
            Item wireItem = AntimatterAPI.get(WIRE.getClass(),"wire_"+mat.getId()).getBlockItem(PipeSize.VTINY);
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack(wireItem))));
        }
        if(mat.has(CABLE)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack((ItemLike) AntimatterAPI.get(CABLE.getClass(),"cable_"+mat.getId())))));
        }
        if(mat.has(WIRE_FINE)){
            this.output.add(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM,new ItemStack(WIRE_FINE.get(mat),1))));
        }
    }

    public Material getMat() {
        return mat;
    }

    public static List<EntryIngredient> createInputEntries(List<Ingredient> input) {
        return input.stream().map(i -> Arrays.stream(i.getItems()).map(EntryStacks::of).toList()).map(EntryIngredient::of).toList();
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return input;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return output;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return MaterialTreeCategory.id;
    }
}
