package muramasa.gregtech.loader.machines.generator;

import muramasa.antimatter.data.ForgeCTags;
import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.data.AntimatterMaterials.*;
import static muramasa.gregtech.data.Materials.*;
import static muramasa.gregtech.data.RecipeMaps.SOLID_FUEL_BOILERS;

public class SolidFuelBoilerLoader {
    public static void init(){
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(GEM.getMaterialTag(Coal), 1)).io(DUST.get(DarkAsh, 1)).add("coal",160);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(GEM.getMaterialTag(Lignite), 1)).io(DUST.get(DarkAsh, 1)).add("lignite",80);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(GEM.getMaterialTag(Charcoal), 1)).io(DUST.get(Ash, 1)).add("charcoal",160);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(Coal), 1)).io(DUST.get(DarkAsh, 1)).add("coal_dust",160);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(Lignite), 1)).io(DUST.get(DarkAsh, 1)).add("lignite_dust",80);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(Charcoal), 1)).io(DUST.get(Ash, 1)).add("charcoal_dust",160);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(DUST_IMPURE.getMaterialTag(Coal), 1)).io(DUST.get(DarkAsh, 1)).add("coal_dust_impure",160);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(DUST_IMPURE.getMaterialTag(Lignite), 1)).io(DUST.get(DarkAsh, 1)).add("lignite_dust_impure",80);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(CRUSHED.getMaterialTag(Coal), 1)).io(DUST.get(DarkAsh, 1)).add("coal_crushed",180);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(CRUSHED.getMaterialTag(Lignite), 1)).io(DUST.get(DarkAsh, 1)).add("lignite_crushed",90);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(CRUSHED_PURIFIED.getMaterialTag(Coal), 1)).io(DUST.get(DarkAsh, 1)).add("coal_crushed_purified",200);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(CRUSHED_PURIFIED.getMaterialTag(Lignite), 1)).io(DUST.get(DarkAsh, 1)).add("lignite_crushed_purified",100);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(RAW_ORE.getMaterialTag(Coal), 1)).io(DUST.get(DarkAsh, 1)).add("coal_raw_ore",160);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(RAW_ORE.getMaterialTag(Lignite), 1)).io(DUST.get(DarkAsh, 1)).add("lignite_raw_ore",80);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(OilShale), 1)).io(DUST.get(Stone, 1)).add("oilshale_dust",40);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(RAW_ORE.getMaterialTag(OilShale), 1)).io(DUST.get(Stone, 1)).add("oilshale_raw",40);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(ForgeCTags.STORAGE_BLOCKS_COAL, 1)).io(DUST.get(DarkAsh, 9)).add("coal_block", 1600);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(BLOCK.getMaterialTag(Lignite), 1)).io(DUST.get(DarkAsh, 9)).add("lignite_block", 800);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(BLOCK.getMaterialTag(Charcoal), 1)).io(DUST.get(Ash, 9)).add("charcoal_block",1600);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(GEM.getMaterialTag(CoalCoke), 1)).io(DUST.get(DarkAsh, 1)).add("coal_coke",320);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(BLOCK.getMaterialTag(CoalCoke), 1)).io(DUST.get(DarkAsh, 9)).add("coal_coke_block",3200);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(GEM.getMaterialTag(LigniteCoke), 1)).io(DUST.get(DarkAsh, 1)).add("lignite_coke",160);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(BLOCK.getMaterialTag(LigniteCoke), 1)).io(DUST.get(DarkAsh, 9)).add("lignite_coke_block",1600);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(ItemTags.PLANKS, 1)).io(DUST.get(Ash, 1)).add("planks",30);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(ItemTags.LOGS_THAT_BURN, 1)).io(DUST.get(Ash, 1)).add("logs",30);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(ItemTags.WOOL, 1)).io(DUST.get(Ash, 1)).add("wool",10);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(ItemTags.WOODEN_SLABS, 1)).io(DUST.get(Ash, 1)).add("wooden_slabs",15);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(ForgeCTags.BOOKSHELVES, 1)).io(DUST.get(Ash, 1)).add("bookshelves",30);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(ItemTags.WOODEN_STAIRS, 1)).io(DUST.get(Ash, 1)).add("wooden_stairs",30);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(Items.DRIED_KELP_BLOCK, 1)).add("dried_kelp_block",400);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(ItemTags.SAPLINGS, 1)).add("saplings",10);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(Items.DEAD_BUSH, 1)).add("dead_bush",10);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(Items.BAMBOO, 1)).add("bamboo",5);
        SOLID_FUEL_BOILERS.RB().ii(RecipeIngredient.of(ForgeCTags.RODS_WOODEN, 1)).add("sticks",10);
    }
}
