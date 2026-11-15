@file:JvmName("BathLoader")
package org.gtreimagined.gt5r.loader.machines

import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraftforge.fluids.FluidStack
import org.gtreimagined.gt5r.GT5RConfig
import org.gtreimagined.gt5r.GT5Reimagined
import org.gtreimagined.gt5r.data.*
import org.gtreimagined.gtcore.data.GTCoreBlocks
import org.gtreimagined.gtcore.data.GTCoreFluids
import org.gtreimagined.gtcore.data.GTCoreItems
import org.gtreimagined.gtlib.GTAPI.isModLoaded
import org.gtreimagined.gtlib.Ref
import org.gtreimagined.gtlib.data.GTMaterialTypes.CRUSHED_ORE
import org.gtreimagined.gtlib.data.GTMaterialTypes.DUST
import org.gtreimagined.gtlib.data.GTMaterialTypes.FRAME
import org.gtreimagined.gtlib.data.GTMaterialTypes.GEM
import org.gtreimagined.gtlib.data.GTMaterialTypes.LIQUID
import org.gtreimagined.gtlib.data.GTMaterialTypes.PLATE
import org.gtreimagined.gtlib.data.GTMaterialTypes.PURIFIED_ORE
import org.gtreimagined.gtlib.data.GTMaterialTypes.REFINED_ORE
import org.gtreimagined.gtlib.data.GTMaterialTypes.SMALL_DUST
import org.gtreimagined.gtlib.data.GTMaterialTypes.TINY_DUST
import org.gtreimagined.gtlib.material.Material
import org.gtreimagined.gtlib.material.MaterialTypeBlock
import org.gtreimagined.gtlib.material.MaterialTypeItem
import org.gtreimagined.gtlib.pipe.PipeSize
import org.gtreimagined.gtlib.recipe.ingredient.FluidIngredient
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient
import org.gtreimagined.gtlib.util.RegistryUtils
import org.gtreimagined.gtlib.util.TagUtils
import java.util.function.Consumer

fun initBath() {
    RecipeMaps.BATH.RB()
        .ii(RecipeIngredient.of(DUST.get(Materials.Wood), 1))
        .fi(Materials.Water.getLiquid(125))
        .io(ItemStack(Items.PAPER))
        .add("paper", 100)
    RecipeMaps.BATH.RB()
        .ii(RecipeIngredient.of(Items.SUGAR_CANE, 1))
        .fi(Materials.Water.getLiquid(125))
        .io(ItemStack(Items.PAPER))
        .add("paper_from_sugarcane", 50)
    RecipeMaps.BATH.RB()
        .ii(DUST.getMaterialIngredient(Materials.Chad, 1))
        .fi(Materials.Water.getLiquid(125))
        .io(ItemStack(Items.PAPER))
        .add("paper_from_chad", 50)
    RecipeMaps.BATH.RB()
        .ii(RecipeIngredient.of(Items.NETHER_STAR, 1))
        .fi(Materials.Radon.getGas(1250))
        .io(ItemStack(GT5RItems.QuantumStar))
        .add("quantum_star", (96 * 20).toLong())
    RecipeMaps.BATH.RB()
        .ii(GEM.getMaterialIngredient(Materials.Diamond, 1))
        .fi(Materials.Netherite.getLiquid(Ref.L / 4))
        .io(GEM.get(Materials.NetherizedDiamond))
        .add("netherized_diamond_recipe", 144)
    RecipeMaps.BATH.RB()
        .ii(RecipeIngredient.of(ItemTags.PLANKS))
        .fi(Materials.Creosote.getFluidIngredient(100))
        .io(GTCoreBlocks.TREATED_PLANKS.asItem())
        .add("treated_planks", 144)
    RecipeMaps.BATH.RB()
        .ii(RecipeIngredient.of(ItemTags.WOODEN_STAIRS))
        .fi(Materials.Creosote.getFluidIngredient(75))
        .io(GTCoreBlocks.TREATED_STAIRS.asItem())
        .add("treated_stairs", 102)
    RecipeMaps.BATH.RB()
        .ii(RecipeIngredient.of(ItemTags.WOODEN_SLABS))
        .fi(Materials.Creosote.getFluidIngredient(50))
        .io(GTCoreBlocks.TREATED_STAIRS.asItem())
        .add("treated_slabs", 72)
    PipeSize.entries.forEach { size ->
        if (size == PipeSize.VTINY) return@forEach
        val amount = when(size){
            PipeSize.VTINY -> 0
            PipeSize.TINY -> 50
            PipeSize.SMALL -> 100
            PipeSize.NORMAL -> 300
            PipeSize.LARGE -> 600
            PipeSize.HUGE -> 1200
            PipeSize.QUADRUPLE -> 1200
            PipeSize.NONUPLE -> 900
        }
        RecipeMaps.BATH.RB()
            .ii(GT5RBlocks.FLUID_PIPE_WOOD.getBlock(size))
            .fi(Materials.Creosote.getFluidIngredient(amount))
            .io(ItemStack(GT5RBlocks.FLUID_PIPE_TREATED_WOOD.getBlock(size)))
            .add("${size.id}_treated_wood_fluid_pipe", 72 * (amount / 50L))
    }
    RecipeMaps.BATH.RB().fi(FluidIngredient.of(GT5RTags.BLUE_DYE, Ref.L))
        .ii(Items.PAPER).io(GTCoreItems.EmptyBlueprint).add("empty_blueprint", 16)
    RecipeMaps.BATH.RB().ii(DUST.getMaterialIngredient(Materials.Tungstate, 7))
        .fi(Materials.HydrochloricAcid.getLiquid(4000))
        .io(DUST.get(Materials.LithiumChloride, 4), DUST.get(Materials.TungsticAcid, 7)).add("tungstate", 512)
    RecipeMaps.BATH.RB().ii(DUST.getMaterialIngredient(Materials.Scheelite, 6))
        .fi(Materials.HydrochloricAcid.getLiquid(4000))
        .io(DUST.get(Materials.CalciumChloride, 3), DUST.get(Materials.TungsticAcid, 7)).add("scheelite", 512)
    RecipeMaps.BATH.RB().ii(RecipeIngredient.of(FRAME.getMaterialTag(Materials.Steel)))
        .fi(Materials.Concrete.getLiquid(Ref.L)).io(GTCoreBlocks.REINFORCED_STONE.asItem())
        .add("reinforced_stone", 200, 4)
    if (GT5RConfig.HARDER_CIRCUITS.get()) {
        RecipeMaps.BATH.RB().ii(GTCoreItems.CarbonFibre).fi(Materials.EpoxyResin.getLiquid(Ref.L))
            .io(PLATE.get(Materials.FiberReinforcedEpoxyResin)).add("fiber_reinforced_epoxy_resin", 240)
    }
    if (GT5RConfig.HARDER_ALUMINIUM_PROCESSING.get()) {
        RecipeMaps.BATH.RB().ii(DUST.getMaterialIngredient(Materials.SodiumAluminate, 4))
            .fi(Materials.Water.getLiquid(6000))
            .io(DUST.get(Materials.AluminiumHydroxide, 7), DUST.get(Materials.SodiumHydroxide, 3))
            .add("aluminium_hydroxide", (102 * 20).toLong())
        RecipeMaps.BATH.RB().ii(DUST.getMaterialIngredient(Materials.SodiumAluminate, 4))
            .fi(Materials.DistilledWater.getLiquid(6000))
            .io(DUST.get(Materials.AluminiumHydroxide, 7), DUST.get(Materials.SodiumHydroxide, 3))
            .add("aluminium_hydroxide_distilled_water", (102 * 20).toLong())
    }
    RecipeMaps.BATH.RB().ii(DUST.getMaterialIngredient(Materials.Magnesium, 2))
        .fi(Materials.TitaniumTetrachloride.getLiquid(5000))
        .io(DUST.get(Materials.Titanium, 1), DUST.get(Materials.MagnesiumChloride, 6)).add("titanium", 512)
    if (isModLoaded(Ref.MOD_TWILIGHT)) {
        Materials.FierySteel.getTypes().forEach { t ->
            if (Materials.Steel.has(t)) {
                val getter = t.get()
                if (t is MaterialTypeItem<*>) {
                    addFieryBathing(
                        t.getMaterialTag(Materials.Steel),
                        t.get(Materials.FierySteel),
                        t.unitValue
                    )
                } else if (getter is MaterialTypeBlock.IBlockGetter) {
                    addFieryBathing(
                        t.getMaterialTag(Materials.Steel),
                        getter.get(Materials.FierySteel).asItem(),
                        t.unitValue
                    )
                }
            }
        }
    }
    for (dye in DyeColor.entries) {
        val dyeName = "${dye.getName()}_dye"
        val dyeLiquid = TagUtils.getFluidTag(ResourceLocation(GT5Reimagined.ID, dyeName))
        RecipeMaps.BATH.RB().fi(FluidIngredient.of(dyeLiquid, Ref.L / 8)).ii(Items.GLASS)
            .io(RegistryUtils.getItemFromID(ResourceLocation("${dye.getName()}_stained_glass")))
            .add("${dye.getName()}_stained_glass", 64)
        RecipeMaps.BATH.RB().fi(FluidIngredient.of(dyeLiquid, Ref.L / 8)).ii(Items.GLASS_PANE)
            .io(RegistryUtils.getItemFromID(ResourceLocation("${dye.getName()}_stained_glass_pane")))
            .add("${dye.getName()}_stained_glass_pane", 64)
        RecipeMaps.BATH.RB().fi(FluidIngredient.of(dyeLiquid, Ref.L / 8)).ii(Items.TERRACOTTA)
            .io(RegistryUtils.getItemFromID(ResourceLocation("${dye.getName()}_terracotta")))
            .add("${dye.getName()}_terracotta", 64)
        RecipeMaps.BATH.RB().fi(FluidIngredient.of(dyeLiquid, Ref.L / 2)).ii(Items.CANDLE)
            .io(RegistryUtils.getItemFromID(ResourceLocation("${dye.getName()}_candle")))
            .add("${dye.getName()}_candle", 64)
        RecipeMaps.BATH.RB().fake().ii(Items.SHULKER_BOX).fi(FluidIngredient.of(dyeLiquid, Ref.L / 2))
            .io(RegistryUtils.getItemFromID(ResourceLocation("${dye.getName()}_shulker_box")))
            .add("${dye.getName()}_shulker_box", 64)
        RecipeMaps.BATH.RB().fake()
            .ii(RegistryUtils.getItemFromID(ResourceLocation("${dye.getName()}_shulker_box")))
            .fi(Materials.Chlorine.getGas(50)).io(Items.SHULKER_BOX).add("shulker_box_from_" + dye.getName(), 64)

        if (dye != DyeColor.WHITE) {
            RecipeMaps.BATH.RB().fi(FluidIngredient.of(dyeLiquid, Ref.L / 2)).ii(Items.WHITE_WOOL)
                .io(RegistryUtils.getItemFromID(ResourceLocation(dye.getName() + "_wool")))
                .add("${dye.getName()}_wool", 64)
            RecipeMaps.BATH.RB().fi(Materials.Chlorine.getGas(50))
                .ii(RegistryUtils.getItemFromID(ResourceLocation(dye.getName() + "_wool"))).io(Items.WHITE_WOOL)
                .add("white_wool_from_$dyeName", 400)
            RecipeMaps.BATH.RB().fi(Materials.Chlorine.getGas(25))
                .ii(RegistryUtils.getItemFromID(ResourceLocation(dye.getName() + "_carpet"))).io(Items.WHITE_CARPET)
                .add("white_carpet_from_$dyeName", 400)
            RecipeMaps.BATH.RB().fi(FluidIngredient.of(dyeLiquid, Ref.L / 2)).ii(Items.WHITE_BED)
                .io(RegistryUtils.getItemFromID(ResourceLocation(dye.getName() + "_bed")))
                .add(dye.getName() + "_bed", 64)
            RecipeMaps.BATH.RB().fi(Materials.Chlorine.getGas(50))
                .ii(RegistryUtils.getItemFromID(ResourceLocation(dye.getName() + "_bed"))).io(Items.WHITE_BED)
                .add("white_bed_from_$dyeName", 400)
        }
    }
    mercurybathing()
    persulfatebathing()
    RecipeMaps.BATH.RB().ii(CRUSHED_ORE.getMaterialIngredient(Materials.Cobaltite, 1))
        .fi(Materials.SodiumPersulfateSolution.getLiquid(1000))
        .io(PURIFIED_ORE.get(Materials.Cobaltite), PURIFIED_ORE.get(Materials.Cobalt), DUST.get(Materials.Stone))
        .outputChances(1.0, 0.25, 1.0).add("persulfate_cobaltite", 144)
    GT5RMaterialTags.VITRIOL.all.forEach { (ore: Material?, vitriol: Material?) ->
        if (vitriol!!.has(LIQUID) && ore!!.has(PURIFIED_ORE)) {
            addVitriolRecipe(ore, vitriol)
        }
    }
    RecipeMaps.BATH.RB().ii(DUST.getMaterialIngredient(Materials.Ilmenite, 5))
        .fi(Materials.SulfuricAcid.getLiquid(7000)).io(DUST.get(Materials.Rutile))
        .fo(Materials.GreenVitriol.getLiquid(6000), Materials.Water.getLiquid(3000)).add("ilmenite_to_rutile", 512)
    GT5RMaterialTags.PLATINUM_GROUP_SLUDGE.all().forEach(Consumer { m: Material? ->
        if (m!!.has(PURIFIED_ORE)) {
            addPSGRecipe(m)
        }
    })
}

fun mercurybathing() {
    GT5RMaterialTags.BATH_MERCURY.all.forEach { (main, side) ->
        RecipeMaps.BATH.RB()
            .ii(RecipeIngredient.of(CRUSHED_ORE.get(main), 1))
            .fi(Materials.Mercury.getLiquid(1000))
            .io(
                ItemStack(PURIFIED_ORE.get(main)),
                ItemStack(DUST.get(side)),
                ItemStack(DUST.get(Materials.Stone))
            )
            .outputChances(1.0, 0.7, 1.0)
            .add("mercury_" + main.id, (40 * 20).toLong())
    }
}

fun persulfatebathing() {
    GT5RMaterialTags.BATH_PERSULFATE.all.forEach { (main, side) ->
        RecipeMaps.BATH.RB()
            .ii(RecipeIngredient.of(CRUSHED_ORE.get(main), 1))
            .fi(Materials.SodiumPersulfateSolution.getLiquid(1000))
            .io(
                ItemStack(PURIFIED_ORE.get(main)),
                ItemStack(DUST.get(side)),
                ItemStack(DUST.get(Materials.Stone))
            )
            .outputChances(1.0, 0.7, 1.0)
            .add("persulfate_${main.id}", (40 * 20).toLong())
    }
}

private fun addFieryBathing(input: TagKey<Item>, out: Item, materialAmount: Long) {
    val amount = ((Ref.L * materialAmount) / Ref.U).toInt()
    RecipeMaps.BATH.RB().ii(RecipeIngredient.of(input)).io(out)
        .fi(FluidStack(GTCoreFluids.FIERY_TEARS.fluid, amount)).add(
            "${input.location().path}_to_${RegistryUtils.getIdFromItem(out).path}_tears",
            amount.toLong()
        )
    RecipeMaps.BATH.RB().ii(RecipeIngredient.of(input)).io(out)
        .fi(FluidStack(GTCoreFluids.FIERY_BLOOD.fluid, amount)).add(
            "${input.location().path}_to_${RegistryUtils.getIdFromItem(out).path}_blood",
            amount.toLong()
        )
}

private fun addVitriolRecipe(input: Material, vitriol: Material) {
    RecipeMaps.BATH.RB().ii(PURIFIED_ORE.getMaterialIngredient(input, 1))
        .fi(Materials.SulfuricAcid.getLiquid(if (vitriol === Materials.VitriolOfClay) 10500 else 3500))
        .fo(
            vitriol.getLiquid(if (vitriol === Materials.VitriolOfClay) 8500 else 3000),
            Materials.Hydrogen.getGas(if (vitriol === Materials.VitriolOfClay) 3000 else 1000)
        )
        .io(REFINED_ORE.get(input, 1), SMALL_DUST.get(input, 2))
        .add("${vitriol.id}_from_${input.id}", 256)
}

private fun addPSGRecipe(input: Material) {
    RecipeMaps.BATH.RB().ii(PURIFIED_ORE.getMaterialIngredient(input, 1))
        .fi(Materials.AquaRegia.getLiquid(9750))
        .fo(
            Materials.ChloroplatinicAcid.getLiquid(4500),
            Materials.NitrogenMonoxide.getGas(1500),
            Materials.Water.getLiquid(4125)
        )
        .io(REFINED_ORE.get(input, 1), TINY_DUST.get(Materials.PlatinumGroupSludge, 1))
        .add("psg_from_${input.id}", 256)
}
