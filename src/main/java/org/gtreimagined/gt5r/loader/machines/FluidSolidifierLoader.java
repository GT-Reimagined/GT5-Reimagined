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
            if (mat.has(PLATE)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldPlate, 1).setNoConsume())
                .fi(mat.getFluidIngredient(L)).io(PLATE.get(mat,1)).add(mat.getId() + "_plate",32, 8);
            }
            if (mat.has(ITEM_CASING)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldCasing, 1).setNoConsume())
                        .fi(mat.getFluidIngredient(L / 2)).io(ITEM_CASING.get(mat,1)).add(mat.getId() + "_casing",16, 8);
            }
            if (mat.has(INGOT)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldIngot, 1).setNoConsume())
                        .fi(mat.getFluidIngredient(mat == Alumina ? (L * 7) / 2 : L)).io(INGOT.get(mat,1)).add(mat.getId() + "_ingot",32, 8);
            }
            if (mat.has(GEAR)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldGear, 1).setNoConsume())
                .fi(mat.getFluidIngredient(L * 4)).io(GEAR.get(mat,1)).add(mat.getId() + "_gear",128, 8);
            }
            if (mat.has(SMALL_GEAR)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldGearSmall, 1).setNoConsume())
                .fi(mat.getFluidIngredient(L)).io(SMALL_GEAR.get(mat,1)).add(mat.getId() + "_gear_small",16, 8);
            }
            if (mat.has(NUGGET)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldNugget, 1).setNoConsume())
                .fi(mat.getFluidIngredient(mat == Alumina ? (L9 * 7) / 2 : L9)).io(NUGGET.get(mat,1)).add(mat.getId() + "_nugget",16, 4);
            }
            if (mat.has(LONG_ROD)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldLongRod, 1).setNoConsume())
                        .fi(mat.getFluidIngredient(L)).io(LONG_ROD.get(mat,1)).add(mat.getId() + "_long_rod",16, 8);
            }
            if (mat.has(BLOCK)) {
                FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldBlock, 1).setNoConsume())
                .fi(mat.getFluidIngredient(mat == Alumina ? (L * 9 * 7) / 2 : L * 9)).io(BLOCK.get().get(mat).asStack(1)).add(mat.getId() + "_block",288, 8);
            }
        });
        for (DyeColor dye : DyeColor.values()) {
            Material concrete = Material.get(dye.getName() + "_concrete");
            FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldBlock, 1).setNoConsume()).fi(concrete.getLiquid(L)).io(RegistryUtils.getItemFromID(new ResourceLocation(dye.getName() + "_concrete"))).add(dye.getName() + "_concrete",288, 8);
        }
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldLongRod, 1).setNoConsume()).fi(Lava.getLiquid(111)).io(LONG_ROD.get(Obsidian)).add("long_obsidian_rod", 16, 8);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldPlate, 1).setNoConsume()).fi(Lava.getLiquid(111)).io(PLATE.get(Obsidian)).add("obsidian_plate", 16, 8);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldBlock, 1).setNoConsume()).fi(Lava.getLiquid(1000)).io(Items.OBSIDIAN).add("obsidian", 16, 8);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldAnvil, 1).setNoConsume()).fi(Iron.getFluidIngredient(L * 31)).io(Items.ANVIL).add("anvil", 128, 16);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldPlate, 1).setNoConsume()).fi(Glass.getFluidIngredient(L)).io(PLATE.get(Glass)).add("glass_plate",12, 4);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldBlock, 1).setNoConsume()).fi(Glass.getFluidIngredient(L)).io(Items.GLASS).add("glass_block",12, 4);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldBottle, 1).setNoConsume()).fi(Glass.getFluidIngredient(L)).io(Items.GLASS_BOTTLE).add("glass_bottle",12, 4);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldBlock, 1).setNoConsume()).fi(Glowstone.getFluidIngredient(L * 4)).io(Items.GLOWSTONE).add("glowstone_block", 12, 4);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldBlock, 1).setNoConsume()).fi(Water.getLiquid(1000)).io(Items.SNOW_BLOCK).add("snow_block", 512, 4);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldBlock, 1).setNoConsume()).fi(DistilledWater.getFluidIngredient(1000)).io(Items.SNOW_BLOCK).add("snow_block_2", 512, 4);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldBall, 1).setNoConsume()).fi(Water.getLiquid(250)).io(Items.SNOWBALL).add("snow_ball", 128, 4);
        FLUID_SOLIDIFYER.RB().ii(RecipeIngredient.of(GTCoreItems.MoldBall, 1).setNoConsume()).fi(DistilledWater.getFluidIngredient(250)).io(Items.SNOWBALL).add("snow_ball_2", 128, 4);
    }
}
