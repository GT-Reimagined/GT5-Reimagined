package org.gtreimagined.gt5r.loader.machines

import net.ccbluex.fastutil.invoke
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import org.gtreimagined.gt5r.data.GT5RItems
import org.gtreimagined.gt5r.data.GT5RMaterialTags
import org.gtreimagined.gt5r.data.Materials
import org.gtreimagined.gt5r.data.RecipeMaps
import org.gtreimagined.gt5r.data.RecipeMaps.EXTRUDER
import org.gtreimagined.gtcore.block.RedstoneWire
import org.gtreimagined.gtcore.data.GTCoreItems
import org.gtreimagined.gtcore.data.GTCoreMaterials
import org.gtreimagined.gtlib.GTAPI
import org.gtreimagined.gtlib.Ref
import org.gtreimagined.gtlib.data.GTMaterialTypes
import org.gtreimagined.gtlib.data.GTMaterialTypes.DUST
import org.gtreimagined.gtlib.data.GTMaterialTypes.GEM
import org.gtreimagined.gtlib.data.GTMaterialTypes.INGOT
import org.gtreimagined.gtlib.data.GTTools
import org.gtreimagined.gtlib.material.Material
import org.gtreimagined.gtlib.material.MaterialTags
import org.gtreimagined.gtlib.pipe.PipeSize
import org.gtreimagined.gtlib.pipe.types.*
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient
import org.gtreimagined.gtlib.tool.GTToolType
import java.util.function.ToLongFunction
import kotlin.math.max

fun initExtruder() {
    val energyPerTick= ToLongFunction<Material> { if (it.has(MaterialTags.RUBBERTOOLS)) 16 else 128 }
    val baseDuration = ToLongFunction<Material> {
        if (it.has(GT5RMaterialTags.RECIPE_MASS)) return@ToLongFunction GT5RMaterialTags.RECIPE_MASS.get(it).toLong()
        it.getMass()
    }
    EXTRUDER.RB().ii(INGOT.getMaterialIngredient(Materials.Zirconium, 1), RecipeIngredient.of(GTCoreItems.CellShape, 1).setNoConsume()).io(GT5RItems.EmptyNuclearFuelRod).add("empty_nuclear_rod", 216, 96)
    EXTRUDER.RB().ii(DUST.getMaterialIngredient(GTCoreMaterials.Beeswax, 1), RecipeIngredient.of(GTCoreItems.BottleShape, 1).setNoConsume()).io(GTCoreItems.EmptyWaxPill).add("empty_wax_pill", 64, 16)
    EXTRUDER.RB().ii(DUST.getMaterialIngredient(Materials.Glass, 1), RecipeIngredient.of(GTCoreItems.BottleShape, 1).setNoConsume()).io(Items.GLASS_BOTTLE).add("glass_bottle", 64, 16)
    EXTRUDER.RB().ii(DUST.getMaterialIngredient(Materials.Stone, 9), RecipeIngredient.of(GTCoreItems.BlockShape, 1).setNoConsume())
        .io(Items.STONE).add("stone_from_stone_dust", 576, 128)
    GTMaterialTypes.RING.all().forEach { r ->
        if (r.has(INGOT)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(r), 1), RecipeIngredient.of(GTCoreItems.RingShape, 1).setNoConsume())
                .io(GTMaterialTypes.RING.get(r, 4))
                .add("ring_${r.id}", baseDuration(r), energyPerTick(r))
        }
        if (r.has(DUST) && r.has(MaterialTags.RUBBERTOOLS) && !r.has(MaterialTags.WOOD)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(r), 1), RecipeIngredient.of(GTCoreItems.RingShape, 1).setNoConsume())
                .io(GTMaterialTypes.RING.get(r, 4))
                .add("ring_${r.id}_from_dust", baseDuration(r), energyPerTick(r))
        }
    }
    GTMaterialTypes.FOIL.all().forEach { r ->
        if (r.has(INGOT)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(r), 1), RecipeIngredient.of(GTCoreItems.FoilShape, 1).setNoConsume())
                .io(GTMaterialTypes.FOIL.get(r, 4))
                .add("foil_${r.id}", baseDuration(r), energyPerTick(r))
        }
        if (r.has(DUST) && r.has(MaterialTags.RUBBERTOOLS) && !r.has(MaterialTags.WOOD)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(r), 1), RecipeIngredient.of(GTCoreItems.FoilShape, 1).setNoConsume())
                .io(GTMaterialTypes.FOIL.get(r, 4))
                .add("foil_${r.id}_from_dust", baseDuration(r), energyPerTick(r))
        }
    }
    GTMaterialTypes.FINE_WIRE.all().forEach { r ->
        if (r.has(INGOT)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(r), 1), RecipeIngredient.of(GTCoreItems.FineWireShape, 1).setNoConsume())
                .io(GTMaterialTypes.FINE_WIRE.get(r, 8))
                .add("fine_wire_${r.id}", baseDuration(r), energyPerTick(r))
        }
        if (r.has(DUST) && r.has(MaterialTags.RUBBERTOOLS) && !r.has(MaterialTags.WOOD)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(r), 1), RecipeIngredient.of(GTCoreItems.FineWireShape, 1).setNoConsume())
                .io(GTMaterialTypes.FINE_WIRE.get(r, 8))
                .add("fine_wire_${r.id}_from_dust", baseDuration(r), energyPerTick(r))
        }
    }
    GTMaterialTypes.TINY_PLATE.all().forEach { r ->
        if (r.has(INGOT)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(r), 1), RecipeIngredient.of(GTCoreItems.TinyPlateShape, 1).setNoConsume())
                .io(GTMaterialTypes.TINY_PLATE.get(r, 9))
                .add("tiny_plate_${r.id}", baseDuration(r), energyPerTick(r))
        }
        if (r.has(DUST) && r.has(MaterialTags.RUBBERTOOLS) && !r.has(MaterialTags.WOOD)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(r), 1), RecipeIngredient.of(GTCoreItems.TinyPlateShape, 1).setNoConsume())
                .io(GTMaterialTypes.TINY_PLATE.get(r, 9))
                .add("tiny_plate_${r.id}_from_dust", baseDuration(r), energyPerTick(r))
        }
    }

    GTMaterialTypes.GEAR.all().forEach { g ->
        if (g.has(INGOT)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(g), 4), RecipeIngredient.of(GTCoreItems.GearShape, 1).setNoConsume())
                .io(GTMaterialTypes.GEAR.get(g, 1))
                .add("gear_${g.id}", baseDuration(g) * 4, energyPerTick(g))
        }
        if (g.has(DUST) && g.has(MaterialTags.RUBBERTOOLS) && !g.has(MaterialTags.WOOD)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(g), 4), RecipeIngredient.of(GTCoreItems.GearShape, 1).setNoConsume())
                .io(GTMaterialTypes.GEAR.get(g, 1))
                .add("gear_${g.id}_from_dust", baseDuration(g) * 4, energyPerTick(g))
        }
    }

    GTMaterialTypes.BOLT.all().forEach { b ->
        if (b.has(INGOT)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(b), 1), RecipeIngredient.of(GTCoreItems.BoltShape, 1).setNoConsume())
                .io(GTMaterialTypes.BOLT.get(b, 8))
                .add("bolt_${b.id}", baseDuration(b), energyPerTick(b))
        }
        if (b.has(DUST) && b.has(MaterialTags.RUBBERTOOLS) && !b.has(MaterialTags.WOOD)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(b), 1), RecipeIngredient.of(GTCoreItems.BoltShape, 1).setNoConsume())
                .io(GTMaterialTypes.BOLT.get(b, 8))
                .add("bolt_${b.id}_from_dust", baseDuration(b), energyPerTick(b))
        }
    }

    GTMaterialTypes.SMALL_GEAR.all().forEach { g ->
        if (g.has(INGOT)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(g), 1), RecipeIngredient.of(GTCoreItems.SmallGearShape, 1).setNoConsume())
                .io(GTMaterialTypes.SMALL_GEAR.get(g, 1))
                .add("gear_small_${g.id}", baseDuration(g), energyPerTick(g))
        }
        if (g.has(DUST) && g.has(MaterialTags.RUBBERTOOLS) && !g.has(MaterialTags.WOOD)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(g), 1), RecipeIngredient.of(GTCoreItems.SmallGearShape, 1).setNoConsume())
                .io(GTMaterialTypes.SMALL_GEAR.get(g, 1))
                .add("gear_small_${g.id}_from_dust", baseDuration(g), energyPerTick(g))
        }
    }

    GTMaterialTypes.PLATE.all().forEach { p ->
        if (p.has(INGOT)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(p), 1), RecipeIngredient.of(GTCoreItems.PlateShape, 1).setNoConsume())
                .io(GTMaterialTypes.PLATE.get(p, 1))
                .add("plate_${p.id}", baseDuration(p), energyPerTick(p))
        }
        if (p.has(DUST) && p.has(MaterialTags.RUBBERTOOLS) && !p.has(MaterialTags.WOOD)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(p), 1), RecipeIngredient.of(GTCoreItems.PlateShape, 1).setNoConsume())
                .io(GTMaterialTypes.PLATE.get(p, 1))
                .add("plate_${p.id}_from_dust", baseDuration(p), energyPerTick(p))
        }
    }

    GTMaterialTypes.ITEM_CASING.all().forEach { r ->
        if (r.has(INGOT)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(r), 1), RecipeIngredient.of(GTCoreItems.CasingShape, 1).setNoConsume())
                .io(GTMaterialTypes.ITEM_CASING.get(r, 2))
                .add("item_casing_${r.id}", baseDuration(r), energyPerTick(r))
        }
        if (r.has(DUST) && r.has(MaterialTags.RUBBERTOOLS) && !r.has(MaterialTags.WOOD)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(r), 1), RecipeIngredient.of(GTCoreItems.CasingShape, 1).setNoConsume())
                .io(GTMaterialTypes.ITEM_CASING.get(r, 2))
                .add("item_casing_${r.id}_from_dust", baseDuration(r), energyPerTick(r))
        }
    }

    GTMaterialTypes.ROD.all().forEach { r ->
        if (r.has(INGOT)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(r), 1), RecipeIngredient.of(GTCoreItems.RodShape, 1).setNoConsume())
                .io(GTMaterialTypes.ROD.get(r, 2))
                .add("rod_${r.id}", baseDuration(r), energyPerTick(r))
        }
        if (r.has(DUST) && !r.has(MaterialTags.WOOD) && (r.has(MaterialTags.RUBBERTOOLS) || (!r.has(INGOT) && !r.has(GEM) && r !== Materials.Obsidian))) {
            EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(r), 1), RecipeIngredient.of(GTCoreItems.RodShape, 1).setNoConsume())
                .io(GTMaterialTypes.ROD.get(r, 2))
                .add("rod_${r.id}_from_dust", baseDuration(r), energyPerTick(r))
        }
    }

    GTMaterialTypes.LONG_ROD.all().forEach { r ->
        if (r.has(INGOT)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(r), 1), RecipeIngredient.of(GTCoreItems.LongRodShape, 1).setNoConsume())
                .io(GTMaterialTypes.LONG_ROD.get(r, 1))
                .add("long_rod_${r.id}", baseDuration(r), energyPerTick(r))
        }
        if (r.has(DUST) && !r.has(MaterialTags.WOOD) && (r.has(MaterialTags.RUBBERTOOLS) || r === Materials.Endstone)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(r), 1), RecipeIngredient.of(GTCoreItems.LongRodShape, 1).setNoConsume())
                .io(GTMaterialTypes.LONG_ROD.get(r, 1))
                .add("long_rod_${r.id}_from_dust", baseDuration(r), energyPerTick(r))
        }
    }
    MaterialTags.RUBBERTOOLS.all().forEach { p ->
        if (p.has(DUST) && p.has(INGOT)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(p), 1), RecipeIngredient.of(GTCoreItems.IngotShape, 1).setNoConsume())
                .io(INGOT.get(p, 1))
                .add("ingot_${p.id}_from_dust", baseDuration(p), energyPerTick(p))
        }
    }

    GTMaterialTypes.BLOCK.all().forEach { p ->
        if (p.has(INGOT)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(p), 9), RecipeIngredient.of(GTCoreItems.BlockShape, 1).setNoConsume())
                .io(GTMaterialTypes.BLOCK.get()!!.get(p).asStack())
                .add("block_${p.id}", baseDuration(p) * 9, energyPerTick(p))
        }
        if (p.has(DUST) && p.has(MaterialTags.RUBBERTOOLS) && !p.has(MaterialTags.WOOD)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(p), 9), RecipeIngredient.of(GTCoreItems.BlockShape, 1).setNoConsume())
                .io(GTMaterialTypes.BLOCK.get()!!.get(p).asStack())
                .add("block_${p.id}_from_dust", baseDuration(p) * 9, energyPerTick(p))
        }
    }

    MaterialTags.TOOLS.getAll().forEach { (m, t) ->
        val toolHeadTypes = arrayOf<GTToolType>(GTTools.PICKAXE, GTTools.AXE, GTTools.SWORD, GTTools.SHOVEL,
            GTTools.HOE, GTTools.FILE, GTTools.SAW, GTTools.HAMMER)
        val toolHeadShapes = arrayOf<Item?>(GTCoreItems.PickaxeHeadShape, GTCoreItems.AxeHeadShape, GTCoreItems.SwordBladeShape,
            GTCoreItems.ShovelHeadShape, GTCoreItems.HoeHeadShape, GTCoreItems.FileHeadShape, GTCoreItems.SawBladeShape, GTCoreItems.HammerHeadShape)
        var i = 0
        for (type in toolHeadTypes) {
            if (t!!.toolTypes.contains(type)) {
                val itemType = type.materialTypeItem ?: continue
                if (m!!.has(itemType)) {
                    val amountIn = Math.toIntExact(itemType.unitValue / Ref.U)
                    if (m.has(INGOT)) {
                        EXTRUDER.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(m), amountIn), RecipeIngredient.of(toolHeadShapes[i], 1).setNoConsume())
                            .io(itemType.get(m, 1))
                            .add("${itemType.getId()}_${m.id}", baseDuration(m) * amountIn, energyPerTick(m))
                    }
                    if (m.has(DUST) && m.has(MaterialTags.RUBBERTOOLS) && !m.has(MaterialTags.WOOD)) {
                        EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(m), amountIn), RecipeIngredient.of(toolHeadShapes[i], 1).setNoConsume())
                            .io(itemType.get(m, 1))
                            .add("${itemType.getId()}_${m.id}_from_dust", baseDuration(m) * amountIn, energyPerTick(m))
                    }
                }
            }
            i++
        }
    }

    GTAPI.all<Wire<*>>().forEach { t ->
        val wireItem: Item = t.getBlockItem(PipeSize.VTINY)
        val stack = ItemStack(wireItem, 2)
        if (t.getMaterial().has(INGOT)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(t.getMaterial()), 1), RecipeIngredient.of(GTCoreItems.WireShape, 1).setNoConsume())
                .io(stack)
                .add("wire_${t.getMaterial().id}", baseDuration(t.getMaterial()), energyPerTick(t.getMaterial()))
        }
        if (t.getMaterial().has(DUST) && t.getMaterial().has(MaterialTags.RUBBERTOOLS)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(t.getMaterial()), 1), RecipeIngredient.of(GTCoreItems.WireShape, 1).setNoConsume())
                .io(stack)
                .add("wire_${t.getMaterial().id}_from_dust", baseDuration(t.getMaterial()), energyPerTick(t.getMaterial()))
        }
    }
    GTAPI.all<RedstoneWire<*>>().forEach { t ->
        val wireItem: Item = t.getBlockItem(PipeSize.VTINY)
        val stack = ItemStack(wireItem, 2)
        if (t.getMaterial().has(INGOT)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(t.getMaterial()), 1), RecipeIngredient.of(GTCoreItems.WireShape, 1).setNoConsume())
                .io(stack)
                .add("wire_${t.getMaterial().id}", baseDuration(t.getMaterial()), energyPerTick(t.getMaterial()))
        }
        if (t.getMaterial().has(DUST) && t.getMaterial().has(MaterialTags.RUBBERTOOLS) && !t.getMaterial().has(MaterialTags.WOOD)) {
            EXTRUDER.RB().ii(RecipeIngredient.of(DUST.getMaterialTag(t.getMaterial()), 1), RecipeIngredient.of(GTCoreItems.WireShape, 1).setNoConsume())
                .io(stack)
                .add("wire_${t.getMaterial().id}_from_dust", baseDuration(t.getMaterial()), energyPerTick(t.getMaterial()))
        }
    }

    GTAPI.all<FluidPipe<*>>().stream()
        .filter { t -> t.getMaterial().has(INGOT) }.forEach { t ->
            addPipeRecipe(t.getMaterial(), 1, 2, PipeSize.TINY, t, 2)
            addPipeRecipe(t.getMaterial(), 1, 1, PipeSize.SMALL, t, 1)
            addPipeRecipe(t.getMaterial(), 3, 1, PipeSize.NORMAL, t, 3)
            addPipeRecipe(t.getMaterial(), 6, 1, PipeSize.LARGE, t, 6)
            addPipeRecipe(t.getMaterial(), 12, 1, PipeSize.HUGE, t, 12)
        }

    GTAPI.all<ItemPipe<*>>().forEach { t ->
        if (!t.getMaterial().has(INGOT)) return@forEach
        addPipeRecipe(t.getMaterial(), 1, 2, PipeSize.TINY, t, 2)
        addPipeRecipe(t.getMaterial(), 1, 1, PipeSize.SMALL, t, 1)
        addPipeRecipe(t.getMaterial(), 3, 1, PipeSize.NORMAL, t, 3)
        addPipeRecipe(t.getMaterial(), 6, 1, PipeSize.LARGE, t, 6)
        addPipeRecipe(t.getMaterial(), 12, 1, PipeSize.HUGE, t, 12)
    }

    GTAPI.all<HeatPipe<*>>().forEach { t ->
        if (!t.getMaterial().has(INGOT)) return@forEach
        addPipeRecipe(t.getMaterial(), 1, 2, PipeSize.TINY, t, 10)
        addPipeRecipe(t.getMaterial(), 1, 1, PipeSize.SMALL, t, 20)
        addPipeRecipe(t.getMaterial(), 3, 1, PipeSize.NORMAL, t, 30)
        addPipeRecipe(t.getMaterial(), 6, 1, PipeSize.LARGE, t, 40)
        addPipeRecipe(t.getMaterial(), 12, 1, PipeSize.HUGE, t, 50)
    }
}

private fun addPipeRecipe(material: Material, countIn: Int, countOut: Int, size: PipeSize, pipe: PipeType<*>, durationMultiplier: Int) {
    if (pipe.getSizes().contains(size)) {
        val energyPerTick = ToLongFunction<Material> { m -> if (m.has(MaterialTags.RUBBERTOOLS)) 16 else 128 }
        val duration = if (material.has(GT5RMaterialTags.RECIPE_MASS)) GT5RMaterialTags.RECIPE_MASS.get(material)
            .toLong() else max(material.getMass(), 1)
        EXTRUDER.RB().ii(INGOT.getMaterialIngredient(material, countIn), RecipeIngredient.of(getPipeMold(size), 1).setNoConsume())
            .io(ItemStack(pipe.getBlockItem(size), countOut))
            .add(size.id + "_" + pipe.getType() + "_" + material.id, duration * durationMultiplier, energyPerTick(material))
    }
}

private fun getPipeMold(size: PipeSize): Item? {
    return when (size) {
        PipeSize.HUGE -> GTCoreItems.HugePipeShape
        PipeSize.LARGE -> GTCoreItems.LargePipeShape
        PipeSize.NORMAL -> GTCoreItems.NormalPipeShape
        PipeSize.SMALL -> GTCoreItems.SmallPipeShape
        PipeSize.TINY, PipeSize.VTINY -> GTCoreItems.TinyPipeShape
        else -> Items.AIR
    }
}