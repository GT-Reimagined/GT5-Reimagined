package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.util.RegistryUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import org.gtreimagined.gtcore.data.GTCoreItems;

import static org.gtreimagined.gtlib.Ref.L;
import static org.gtreimagined.gtlib.Ref.L9;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.FLUID_SOLIDIFYER;

public class FluidSolidifierLoader {
    public static void init() {
        GTAPI.all(Material.class, mat -> {
            if (!mat.has(GTMaterialTypes.LIQUID) || mat == Glass) return;
            Material outMat = mat == Iron && GTAPI.isModLoaded("tfc") ? CastIron : mat;
            if (outMat.has(PLATE)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.PlateMold, 1).setNoConsume())
                .fi(mat.getFluidIngredient(L)).io(PLATE.get(outMat,1)).add(outMat.getId() + "_plate",32, 8);
            }
            if (outMat.has(ITEM_CASING)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.CasingMold, 1).setNoConsume())
                        .fi(mat.getFluidIngredient(L / 2)).io(ITEM_CASING.get(outMat,1)).add(outMat.getId() + "_casing",16, 8);
            }
            if (outMat.has(INGOT)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.IngotMold, 1).setNoConsume())
                        .fi(mat.getFluidIngredient(mat == Alumina ? (L * 7) / 2 : L)).io(INGOT.get(outMat,1)).add(outMat.getId() + "_ingot",32, 8);
            }
            if (outMat.has(GEAR)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.GearMold, 1).setNoConsume())
                .fi(mat.getFluidIngredient(L * 4)).io(GEAR.get(outMat,1)).add(outMat.getId() + "_gear",128, 8);
            }
            if (outMat.has(SMALL_GEAR)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.SmallGearMold, 1).setNoConsume())
                .fi(mat.getFluidIngredient(L)).io(SMALL_GEAR.get(outMat,1)).add(outMat.getId() + "_gear_small",16, 8);
            }
            if (outMat.has(NUGGET)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.NuggetMold, 1).setNoConsume())
                .fi(mat.getFluidIngredient(mat == Alumina ? (L9 * 7) / 2 : L9)).io(NUGGET.get(outMat,1)).add(outMat.getId() + "_nugget",16, 4);
            }
            if (outMat.has(LONG_ROD)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.LongRodMold, 1).setNoConsume())
                        .fi(mat.getFluidIngredient(L)).io(LONG_ROD.get(outMat,1)).add(outMat.getId() + "_long_rod",16, 8);
            }
            if (outMat.has(BLOCK)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.BlockMold, 1).setNoConsume())
                .fi(mat.getFluidIngredient(mat == Alumina ? (L * 9 * 7) / 2 : L * 9)).io(BLOCK.get().get(outMat).asStack(1)).add(outMat.getId() + "_block",288, 8);
            }
        });
        for (DyeColor dye : DyeColor.values()) {
            Material concrete = Material.get(dye.getName() + "_concrete");
            FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.BlockMold, 1).setNoConsume()).fi(concrete.getLiquid(L)).io(RegistryUtils.getItemFromID(new ResourceLocation(dye.getName() + "_concrete"))).add(dye.getName() + "_concrete",288, 8);
        }
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.LongRodMold, 1).setNoConsume()).fi(Lava.getLiquid(111)).io(LONG_ROD.get(Obsidian)).add("long_obsidian_rod", 16, 8);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.PlateMold, 1).setNoConsume()).fi(Lava.getLiquid(111)).io(PLATE.get(Obsidian)).add("obsidian_plate", 16, 8);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.BlockMold, 1).setNoConsume()).fi(Lava.getLiquid(1000)).io(Items.OBSIDIAN).add("obsidian", 16, 8);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.PlateMold, 1).setNoConsume()).fi(Glass.getFluidIngredient(L)).io(PLATE.get(Glass)).add("glass_plate",12, 4);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.BlockMold, 1).setNoConsume()).fi(Glass.getFluidIngredient(L)).io(Items.GLASS).add("glass_block",12, 4);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.BottleMold, 1).setNoConsume()).fi(Glass.getFluidIngredient(L)).io(Items.GLASS_BOTTLE).add("glass_bottle",12, 4);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.BlockMold, 1).setNoConsume()).fi(Glowstone.getFluidIngredient(L * 4)).io(Items.GLOWSTONE).add("glowstone_block", 12, 4);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.BlockMold, 1).setNoConsume()).fi(Water.getLiquid(1000)).io(Items.SNOW_BLOCK).add("snow_block", 512, 4);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.BlockMold, 1).setNoConsume()).fi(DistilledWater.getFluidIngredient(1000)).io(Items.SNOW_BLOCK).add("snow_block_2", 512, 4);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.BallMold, 1).setNoConsume()).fi(Water.getLiquid(250)).io(Items.SNOWBALL).add("snow_ball", 128, 4);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.BallMold, 1).setNoConsume()).fi(DistilledWater.getFluidIngredient(250)).io(Items.SNOWBALL).add("snow_ball_2", 128, 4);
        if (!GTAPI.isModLoaded("tfc")) {
            FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.AnvilMold, 1).setNoConsume()).fi(Iron.getFluidIngredient(L * 31)).io(Items.ANVIL).add("anvil", 128, 16);
        }
    }
}
