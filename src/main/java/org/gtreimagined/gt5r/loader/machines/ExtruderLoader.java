package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gtlib.AntimatterAPI;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.pipe.PipeSize;
import org.gtreimagined.gtlib.pipe.types.FluidPipe;
import org.gtreimagined.gtlib.pipe.types.HeatPipe;
import org.gtreimagined.gtlib.pipe.types.ItemPipe;
import org.gtreimagined.gtlib.pipe.types.PipeType;
import org.gtreimagined.gtlib.pipe.types.Wire;
import org.gtreimagined.gtlib.tool.GTToolType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtcore.block.RedstoneWire;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.data.GTCoreMaterials;

import java.util.function.ToLongFunction;

import static org.gtreimagined.gtlib.data.GTTools.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTLibMaterials.Endstone;
import static org.gtreimagined.gtlib.material.MaterialTags.RUBBERTOOLS;
import static org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient.of;
import static org.gtreimagined.gt5r.data.Materials.Glass;
import static org.gtreimagined.gt5r.data.Materials.Obsidian;
import static org.gtreimagined.gt5r.data.RecipeMaps.EXTRUDER;

public class ExtruderLoader {
    public static void init() {
        ToLongFunction<Material> energyPerTick = m -> m.has(RUBBERTOOLS) ? 16 : 128;
        ToLongFunction<Material> baseDuration = m -> {
            if (m.has(GT5RMaterialTags.RECIPE_MASS)) return GT5RMaterialTags.RECIPE_MASS.get(m);
            return m.getMass();
        };
        EXTRUDER.RB().ii(INGOT.getMaterialIngredient(Materials.Zirconium, 1), of(GTCoreItems.ShapeCell, 1).setNoConsume()).io(GT5RItems.EmptyNuclearFuelRod).add("empty_nuclear_rod", 216, 96);
        EXTRUDER.RB().ii(DUST.getMaterialIngredient(GTCoreMaterials.Beeswax, 1), of(GTCoreItems.ShapeBottle, 1).setNoConsume()).io(GTCoreItems.EmptyWaxPill).add("empty_wax_pill", 64, 16);
        EXTRUDER.RB().ii(DUST.getMaterialIngredient(Glass, 1), of(GTCoreItems.ShapeBottle, 1).setNoConsume()).io(Items.GLASS_BOTTLE).add("glass_bottle", 64, 16);
        GTMaterialTypes.RING.all().forEach(r -> {
            if (r.has(INGOT)) {
                EXTRUDER.RB().ii(of(INGOT.getMaterialTag(r), 1), of(GTCoreItems.ShapeRing, 1).setNoConsume()).io(GTMaterialTypes.RING.get(r, 4)).add("ring_" + r.getId(), baseDuration.applyAsLong(r), energyPerTick.applyAsLong(r));
            }
            if (r.has(GTMaterialTypes.DUST) && r.has(RUBBERTOOLS)) {
                EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(r), 1), of(GTCoreItems.ShapeRing, 1).setNoConsume()).io(GTMaterialTypes.RING.get(r, 4)).add("ring_" + r.getId() + "_from_dust", baseDuration.applyAsLong(r), energyPerTick.applyAsLong(r));
            }
        });
        GTMaterialTypes.FOIL.all().forEach(r -> {
            if (r.has(INGOT)) {
                EXTRUDER.RB().ii(of(INGOT.getMaterialTag(r), 1), of(GTCoreItems.ShapeFoil, 1).setNoConsume()).io(GTMaterialTypes.FOIL.get(r, 4)).add("foil_" + r.getId(), baseDuration.applyAsLong(r), energyPerTick.applyAsLong(r));
            }
            if (r.has(GTMaterialTypes.DUST) && r.has(RUBBERTOOLS)) {
                EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(r), 1), of(GTCoreItems.ShapeFoil, 1).setNoConsume()).io(GTMaterialTypes.FOIL.get(r, 4)).add("foil_" + r.getId() + "_from_dust", baseDuration.applyAsLong(r), energyPerTick.applyAsLong(r));
            }
        });
        GTMaterialTypes.WIRE_FINE.all().forEach(r -> {
            if (r.has(INGOT)) {
                EXTRUDER.RB().ii(of(INGOT.getMaterialTag(r), 1), of(GTCoreItems.ShapeFineWire, 1).setNoConsume()).io(GTMaterialTypes.WIRE_FINE.get(r, 8)).add("fine_wire_" + r.getId(), baseDuration.applyAsLong(r), energyPerTick.applyAsLong(r));
            }
            if (r.has(GTMaterialTypes.DUST) && r.has(RUBBERTOOLS)) {
                EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(r), 1), of(GTCoreItems.ShapeFineWire, 1).setNoConsume()).io(GTMaterialTypes.WIRE_FINE.get(r, 8)).add("fine_wire_" + r.getId() + "_from_dust", baseDuration.applyAsLong(r), energyPerTick.applyAsLong(r));
            }
        });
        GTMaterialTypes.PLATE_TINY.all().forEach(r -> {
            if (r.has(INGOT)) {
                EXTRUDER.RB().ii(of(INGOT.getMaterialTag(r), 1), of(GTCoreItems.ShapeTinyPlate, 1).setNoConsume()).io(GTMaterialTypes.PLATE_TINY.get(r, 9)).add("tiny_plate_" + r.getId(), baseDuration.applyAsLong(r), energyPerTick.applyAsLong(r));
            }
            if (r.has(GTMaterialTypes.DUST) && r.has(RUBBERTOOLS)) {
                EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(r), 1), of(GTCoreItems.ShapeTinyPlate, 1).setNoConsume()).io(GTMaterialTypes.PLATE_TINY.get(r, 9)).add("tiny_plate_" + r.getId() + "_from_dust", baseDuration.applyAsLong(r), energyPerTick.applyAsLong(r));
            }
        });

        GTMaterialTypes.GEAR.all().forEach(g -> {
            if (g.has(INGOT)) {
                EXTRUDER.RB().ii(of(INGOT.getMaterialTag(g), 4), of(GTCoreItems.ShapeGear, 1).setNoConsume()).io(GTMaterialTypes.GEAR.get(g, 1)).add("gear_" + g.getId(), baseDuration.applyAsLong(g) * 4, energyPerTick.applyAsLong(g));
            }
            if (g.has(GTMaterialTypes.DUST) && g.has(RUBBERTOOLS)) {
                EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(g), 4), of(GTCoreItems.ShapeGear, 1).setNoConsume()).io(GTMaterialTypes.GEAR.get(g, 1)).add("gear_" + g.getId() + "_from_dust", baseDuration.applyAsLong(g) * 4, energyPerTick.applyAsLong(g));
            }
        });

        GTMaterialTypes.BOLT.all().forEach(b -> {
            if (b.has(INGOT)) {
                EXTRUDER.RB().ii(of(INGOT.getMaterialTag(b), 1), of(GTCoreItems.ShapeBolt, 1).setNoConsume()).io(GTMaterialTypes.BOLT.get(b, 8)).add("bolt_" + b.getId(), baseDuration.applyAsLong(b), energyPerTick.applyAsLong(b));
            }
            if (b.has(GTMaterialTypes.DUST) && b.has(RUBBERTOOLS)) {
                EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(b), 1), of(GTCoreItems.ShapeBolt, 1).setNoConsume()).io(GTMaterialTypes.BOLT.get(b, 8)).add("bolt_" + b.getId() + "_from_dust", baseDuration.applyAsLong(b), energyPerTick.applyAsLong(b));
            }
        });

        GTMaterialTypes.GEAR_SMALL.all().forEach(g -> {
            if (g.has(INGOT)) {
                EXTRUDER.RB().ii(of(INGOT.getMaterialTag(g), 1), of(GTCoreItems.ShapeGearSmall, 1).setNoConsume()).io(GTMaterialTypes.GEAR_SMALL.get(g, 1)).add("gear_small_" + g.getId(), baseDuration.applyAsLong(g), energyPerTick.applyAsLong(g));
            }
            if (g.has(GTMaterialTypes.DUST) && g.has(RUBBERTOOLS)) {
                EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(g), 1), of(GTCoreItems.ShapeGearSmall, 1).setNoConsume()).io(GTMaterialTypes.GEAR_SMALL.get(g, 1)).add("gear_small_" + g.getId() + "_from_dust", baseDuration.applyAsLong(g), energyPerTick.applyAsLong(g));
            }
        });

        GTMaterialTypes.PLATE.all().forEach(p -> {
            if (p.has(INGOT)) {
                EXTRUDER.RB().ii(of(INGOT.getMaterialTag(p), 1), of(GTCoreItems.ShapePlate, 1).setNoConsume()).io(GTMaterialTypes.PLATE.get(p, 1)).add("plate_" + p.getId(), baseDuration.applyAsLong(p), energyPerTick.applyAsLong(p));
            }
            if (p.has(GTMaterialTypes.DUST) && p.has(RUBBERTOOLS)) {
                EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(p), 1), of(GTCoreItems.ShapePlate, 1).setNoConsume()).io(GTMaterialTypes.PLATE.get(p, 1)).add("plate_" + p.getId() + "_from_dust", baseDuration.applyAsLong(p), energyPerTick.applyAsLong(p));
            }
        });

        GTMaterialTypes.ITEM_CASING.all().forEach(r -> {
            if (r.has(INGOT)) {
                EXTRUDER.RB().ii(of(INGOT.getMaterialTag(r), 1), of(GTCoreItems.ShapeCasing, 1).setNoConsume()).io(GTMaterialTypes.ITEM_CASING.get(r, 2)).add("item_casing_" + r.getId(), baseDuration.applyAsLong(r), energyPerTick.applyAsLong(r));
            }
            if (r.has(GTMaterialTypes.DUST) && r.has(RUBBERTOOLS)) {
                EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(r), 1), of(GTCoreItems.ShapeCasing, 1).setNoConsume()).io(GTMaterialTypes.ITEM_CASING.get(r, 2)).add("item_casing_" + r.getId() + "_from_dust", baseDuration.applyAsLong(r), energyPerTick.applyAsLong(r));
            }
        });

        GTMaterialTypes.ROD.all().forEach(r -> {
            if (r.has(INGOT)) {
                EXTRUDER.RB().ii(of(INGOT.getMaterialTag(r), 1), of(GTCoreItems.ShapeRod, 1).setNoConsume()).io(GTMaterialTypes.ROD.get(r, 2)).add("rod_" + r.getId(), baseDuration.applyAsLong(r), energyPerTick.applyAsLong(r));
            }
            if (r.has(GTMaterialTypes.DUST) && (r.has(RUBBERTOOLS) || (!r.has(INGOT) && !r.has(GEM) && r != Obsidian))) {
                EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(r), 1), of(GTCoreItems.ShapeRod, 1).setNoConsume()).io(GTMaterialTypes.ROD.get(r, 2)).add("rod_" + r.getId() + "_from_dust", baseDuration.applyAsLong(r), energyPerTick.applyAsLong(r));
            }
        });

        GTMaterialTypes.ROD_LONG.all().forEach(r -> {
            if (r.has(INGOT)) {
                EXTRUDER.RB().ii(of(INGOT.getMaterialTag(r), 1), of(GTCoreItems.ShapeLongRod, 1).setNoConsume()).io(GTMaterialTypes.ROD_LONG.get(r, 1)).add("long_rod_" + r.getId(), baseDuration.applyAsLong(r), energyPerTick.applyAsLong(r));
            }
            if (r.has(GTMaterialTypes.DUST) && (r.has(RUBBERTOOLS) || r == Endstone)) {
                EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(r), 1), of(GTCoreItems.ShapeLongRod, 1).setNoConsume()).io(GTMaterialTypes.ROD_LONG.get(r, 1)).add("long_rod_" + r.getId() + "_from_dust", baseDuration.applyAsLong(r), energyPerTick.applyAsLong(r));
            }
        });
        RUBBERTOOLS.all().forEach(p -> {
            if (p.has(GTMaterialTypes.DUST) && p.has(INGOT)) {
                EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(p), 1), of(GTCoreItems.ShapeIngot, 1).setNoConsume()).io(INGOT.get(p, 1)).add("ingot_" + p.getId() + "_from_dust", baseDuration.applyAsLong(p), energyPerTick.applyAsLong(p));
            }
        });

        GTMaterialTypes.BLOCK.all().forEach(p -> {
            if (p.has(INGOT)) {
                EXTRUDER.RB().ii(of(INGOT.getMaterialTag(p), 9), of(GTCoreItems.ShapeBlock, 1).setNoConsume()).io(GTMaterialTypes.BLOCK.get().get(p).asStack()).add("block_" + p.getId(), baseDuration.applyAsLong(p) * 9, energyPerTick.applyAsLong(p));
            }
            if (p.has(GTMaterialTypes.DUST) && p.has(RUBBERTOOLS)) {
                EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(p), 9), of(GTCoreItems.ShapeBlock, 1).setNoConsume()).io(GTMaterialTypes.BLOCK.get().get(p).asStack()).add("block_" + p.getId() + "_from_dust", baseDuration.applyAsLong(p) * 9, energyPerTick.applyAsLong(p));
            }
        });

        MaterialTags.TOOLS.getAll().forEach((m, t) -> {
            GTToolType[] toolHeadTypes = new GTToolType[]{PICKAXE, AXE, SWORD, SHOVEL, HOE, FILE, SAW, HAMMER};
            Item[] toolHeadShapes = new Item[]{GTCoreItems.ShapeHeadPickaxe, GTCoreItems.ShapeHeadAxe, GTCoreItems.ShapeBladeSword, GTCoreItems.ShapeHeadShovel, GTCoreItems.ShapeHeadHoe, GTCoreItems.ShapeHeadFile, GTCoreItems.ShapeBladeSaw, GTCoreItems.ShapeHeadHammer};
            int i = 0;
            for (GTToolType type : toolHeadTypes) {
                if (t.toolTypes().contains(type)) {
                    var itemType = type.getMaterialTypeItem();
                    if (itemType == null) continue;
                    if (m.has(itemType)) {
                        int amountIn = type == PICKAXE || type == AXE ? 3 : type == HAMMER ? 6 : type == SHOVEL ? 1 : 2;
                        if (m.has(INGOT)) {
                            EXTRUDER.RB().ii(of(INGOT.getMaterialTag(m), amountIn), of(toolHeadShapes[i], 1).setNoConsume()).io(itemType.get(m, 1)).add(itemType.getId() + "_" + m.getId(), baseDuration.applyAsLong(m) * amountIn, energyPerTick.applyAsLong(m));
                        }
                        if (m.has(GTMaterialTypes.DUST) && m.has(RUBBERTOOLS)) {
                            EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(m), amountIn), of(toolHeadShapes[i], 1).setNoConsume()).io(itemType.get(m, 1)).add(itemType.getId() + "_" + m.getId() + "_from_dust", baseDuration.applyAsLong(m) * amountIn, energyPerTick.applyAsLong(m));
                        }
                    }
                }
                i++;
            }
        });

        AntimatterAPI.all(Wire.class).forEach(t -> {
            Item wireItem = t.getBlockItem(PipeSize.VTINY);
            ItemStack stack = new ItemStack(wireItem,2);
            if (t.getMaterial().has(INGOT)) {
                EXTRUDER.RB().ii(of(INGOT.getMaterialTag(t.getMaterial()),1),of(GTCoreItems.ShapeWire,1).setNoConsume()).io(stack).add("wire_" + t.getMaterial().getId(),baseDuration.applyAsLong(t.getMaterial()),energyPerTick.applyAsLong(t.getMaterial()));
            }
            if (t.getMaterial().has(GTMaterialTypes.DUST) && t.getMaterial().has(RUBBERTOOLS)) {
                EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(t.getMaterial()),1),of(GTCoreItems.ShapeWire,1).setNoConsume()).io(stack).add("wire_" + t.getMaterial().getId() + "_from_dust", baseDuration.applyAsLong(t.getMaterial()), energyPerTick.applyAsLong(t.getMaterial()));
            }
        });
        AntimatterAPI.all(RedstoneWire.class).forEach(t -> {
            Item wireItem = t.getBlockItem(PipeSize.VTINY);
            ItemStack stack = new ItemStack(wireItem,2);
            if (t.getMaterial().has(INGOT)) {
                EXTRUDER.RB().ii(of(INGOT.getMaterialTag(t.getMaterial()),1),of(GTCoreItems.ShapeWire,1).setNoConsume()).io(stack).add("wire_" + t.getMaterial().getId(),baseDuration.applyAsLong(t.getMaterial()),energyPerTick.applyAsLong(t.getMaterial()));
            }
            if (t.getMaterial().has(GTMaterialTypes.DUST) && t.getMaterial().has(RUBBERTOOLS)) {
                EXTRUDER.RB().ii(of(GTMaterialTypes.DUST.getMaterialTag(t.getMaterial()),1),of(GTCoreItems.ShapeWire,1).setNoConsume()).io(stack).add("wire_" + t.getMaterial().getId() + "_from_dust", baseDuration.applyAsLong(t.getMaterial()), energyPerTick.applyAsLong(t.getMaterial()));
            }
        });

        AntimatterAPI.all(FluidPipe.class).stream().filter(t -> t.getMaterial().has(INGOT)).forEach(t -> {
            addPipeRecipe(t.getMaterial(), 1, 2, PipeSize.TINY, t, 2);
            addPipeRecipe(t.getMaterial(), 1, 1, PipeSize.SMALL, t, 1);
            addPipeRecipe(t.getMaterial(), 3, 1, PipeSize.NORMAL, t, 3);
            addPipeRecipe(t.getMaterial(), 6, 1, PipeSize.LARGE, t, 6);
            addPipeRecipe(t.getMaterial(), 12, 1, PipeSize.HUGE, t, 12);
        });

        AntimatterAPI.all(ItemPipe.class).forEach(t -> {
            if (!t.getMaterial().has(INGOT)) return;
            addPipeRecipe(t.getMaterial(), 1, 2, PipeSize.TINY, t, 2);
            addPipeRecipe(t.getMaterial(), 1, 1, PipeSize.SMALL, t, 1);
            addPipeRecipe(t.getMaterial(), 3, 1, PipeSize.NORMAL, t, 3);
            addPipeRecipe(t.getMaterial(), 6, 1, PipeSize.LARGE, t, 6);
            addPipeRecipe(t.getMaterial(), 12, 1, PipeSize.HUGE, t, 12);
        });

        AntimatterAPI.all(HeatPipe.class).forEach(t -> {
            if (!t.getMaterial().has(INGOT)) return;
            addPipeRecipe(t.getMaterial(), 1, 2, PipeSize.TINY, t, 10);
            addPipeRecipe(t.getMaterial(), 1, 1, PipeSize.SMALL, t, 20);
            addPipeRecipe(t.getMaterial(), 3, 1, PipeSize.NORMAL, t, 30);
            addPipeRecipe(t.getMaterial(), 6, 1, PipeSize.LARGE, t, 40);
            addPipeRecipe(t.getMaterial(), 12, 1, PipeSize.HUGE, t, 50);
        });
    }

    private static void addPipeRecipe(Material material, int countIn, int countOut, PipeSize size, PipeType<?> pipe, int durationMultiplier){
        if (pipe.getSizes().contains(size)) {
            long duration = material.has(GT5RMaterialTags.RECIPE_MASS) ? GT5RMaterialTags.RECIPE_MASS.get(material) : Math.max(material.getMass(), 1);
            EXTRUDER.RB().ii(INGOT.getMaterialIngredient(material, countIn), of(getPipeMold(size), 1).setNoConsume()).io(new ItemStack(pipe.getBlockItem(size), countOut)).add(size.getId() + "_" + pipe.getType() + "_" + material.getId(), duration * durationMultiplier, 30);
        }
    }

    private static Item getPipeMold(PipeSize size){
        return switch (size) {
            case HUGE -> GTCoreItems.ShapePipeHuge;
            case LARGE -> GTCoreItems.ShapePipeLarge;
            case NORMAL -> GTCoreItems.ShapePipeNormal;
            case SMALL -> GTCoreItems.ShapePipeSmall;
            case TINY, VTINY -> GTCoreItems.ShapePipeTiny;
            default -> Items.AIR;
        };
    }
}