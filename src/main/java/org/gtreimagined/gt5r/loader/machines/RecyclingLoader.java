package org.gtreimagined.gt5r.loader.machines;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.block.BlockCasing;
import org.gtreimagined.gt5r.block.BlockColoredWall;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gt5r.data.GT5RCovers;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.data.GT5RRecipeTags;
import org.gtreimagined.gt5r.data.RecipeMaps;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.block.RedstoneWire;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.machine.DrumMachine;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.item.ItemBasic;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.material.MaterialType;
import org.gtreimagined.gtlib.material.MaterialTypeItem;
import org.gtreimagined.gtlib.pipe.PipeSize;
import org.gtreimagined.gtlib.pipe.types.Cable;
import org.gtreimagined.gtlib.pipe.types.FluidPipe;
import org.gtreimagined.gtlib.pipe.types.ItemPipe;
import org.gtreimagined.gtlib.pipe.types.Wire;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.recipe.map.RecipeBuilder;
import org.gtreimagined.gtlib.util.RegistryUtils;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gt5r.data.GT5RMachines.*;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.TierMaps.TIER_MATERIALS;
import static org.gtreimagined.gtlib.Ref.U;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.machine.Tier.*;

public class RecyclingLoader {
    public static void initRecyclingRecipes() {
        for (MaterialType<?> t : GTAPI.all(MaterialType.class)) {
            if (t.getUnitValue() <= 0 || t == DUST || t == TINY_DUST || t == SMALL_DUST || t == INGOT || t == NUGGET || t == CHUNK ||
                    t == HOT_INGOT || t == GEM || t == CHIPPED_GEM || t == FLAWED_GEM || t == FLAWLESS_GEM || t == EXQUISITE_GEM || t == ROCK || t == BEARING_ROCK) continue;
            double amount = (double) t.getUnitValue() / U;
            t.all().forEach(m -> {
                if (!m.has(DUST) || m == Bone || m == Carbon || m == Blaze) return;
                if (m.has(MaterialTags.RUBBERTOOLS)) {
                    int i = (int) amount;
                    float leftover = (float) (amount - i);
                    var mac = RecipeMaps.PULVERIZER.RB().ii(t.getMaterialIngredient(m, 1));
                    if (leftover > 0){
                        float mExtraF = leftover * 4;
                        int mExtra = (int) (mExtraF);
                        float mLeftover = mExtraF - mExtra;
                        int aExtra = (int) (leftover * 9);
                        if (mLeftover > 0){
                            mac.io(TINY_DUST.get(m, (i * 9) + aExtra));
                        } else {
                            mac.io(SMALL_DUST.get(m, (i * 4) + mExtra));
                        }
                    } else {
                        mac.io(DUST.get(m, i));
                    }
                    mac.add(m.getId() + "_" + t.getId() + "_recycling", m.getMass() * 2, 4);
                } else {
                    addRecyclingRecipe(t.getMaterialIngredient(m, 1), of(m, (float) amount), m.getId() + "_" + t.getId() + "_recycling");
                }
            });
        }
        GTAPI.all(BlockColoredWall.class).forEach(b -> {
            if (b.getMaterial() == Wood){
                addRecyclingRecipe(b.asItem(), of(Lead, 1f, Wood, 4f));
            } else {
                addRecyclingRecipe(b.asItem(), of(b.getMaterial(), 4f));
            }
        });
        GTAPI.all(FluidPipe.class).stream().filter(t -> t.getMaterial() != Wood && t.getMaterial().has(DUST)).forEach(t -> {
            if (t.getSizes().contains(PipeSize.TINY)){
                addRecyclingRecipe(t.getBlockItem(PipeSize.TINY), of(t.getMaterial(), 0.5f));
            }
            if (t.getSizes().contains(PipeSize.SMALL)){
                addRecyclingRecipe(t.getBlockItem(PipeSize.SMALL), of(t.getMaterial(), 1f));
            }
            if (t.getSizes().contains(PipeSize.NORMAL)){
                addRecyclingRecipe(t.getBlockItem(PipeSize.NORMAL), of(t.getMaterial(), 3f));
            }
            if (t.getSizes().contains(PipeSize.LARGE)){
                addRecyclingRecipe(t.getBlockItem(PipeSize.LARGE), of(t.getMaterial(), 6f));
            }
            if (t.getSizes().contains(PipeSize.HUGE)){
                addRecyclingRecipe(t.getBlockItem(PipeSize.HUGE), of(t.getMaterial(), 12f));
            }
        });
        GTAPI.all(ItemPipe.class).stream().filter(t -> t.getMaterial() != Wood && t.getMaterial().has(DUST)).forEach(t -> {
            if (t.getSizes().contains(PipeSize.TINY)){
                addRecyclingRecipe(t.getBlockItem(PipeSize.TINY), of(t.getMaterial(), 0.5f));
            }
            if (t.getSizes().contains(PipeSize.SMALL)){
                addRecyclingRecipe(t.getBlockItem(PipeSize.SMALL), of(t.getMaterial(), 1f));
            }
            if (t.getSizes().contains(PipeSize.NORMAL)){
                addRecyclingRecipe(t.getBlockItem(PipeSize.NORMAL), of(t.getMaterial(), 3f));
            }
            if (t.getSizes().contains(PipeSize.LARGE)){
                addRecyclingRecipe(t.getBlockItem(PipeSize.LARGE), of(t.getMaterial(), 6f));
            }
            if (t.getSizes().contains(PipeSize.HUGE)){
                addRecyclingRecipe(t.getBlockItem(PipeSize.HUGE), of(t.getMaterial(), 12f));
            }
        });
        GTAPI.all(Wire.class, w -> {
            ImmutableSet<PipeSize> sizes = w.getSizes();
            if (!w.getMaterial().has(DUST)) return;
            sizes.forEach(size -> {
                Item cableItem = w.getBlockItem(size);
                int ct = size.getCableThickness();
                float amount = ct == 1 ? 0.5f : ct == 2 ? 1 : ct == 4 ? 2 : ct == 8 ? 4 : ct == 12 ? 6 : 8;
                addRecyclingRecipe(cableItem, of(w.getMaterial(), amount));
            });
        });
        GTAPI.all(RedstoneWire.class, w -> {
            ImmutableSet<PipeSize> sizes = w.getSizes();
            if (!w.getMaterial().has(DUST)) return;
            sizes.forEach(size -> {
                Item cableItem = w.getBlockItem(size);
                int ct = size.getCableThickness();
                float amount = ct == 1 ? 0.5f : ct == 2 ? 1 : ct == 4 ? 2 : ct == 8 ? 4 : ct == 12 ? 6 : 8;
                addRecyclingRecipe(cableItem, of(w.getMaterial(), amount));
            });
        });
        GTAPI.all(Cable.class, c -> {
            ImmutableSet<PipeSize> sizes = c.getSizes();
            if (!c.getMaterial().has(DUST)) return;
            sizes.forEach(size -> {
                Item cableItem = c.getBlockItem(size);
                int ct = size.getCableThickness();
                float multiplier = ct == 16 ?  5 : ct == 12 ? 4 : ct == 8 ? 3 : ct == 4 ? 2 : 1;
                float amount = ct == 1 ? 0.5f : ct == 2 ? 1 : ct == 4 ? 2 : ct == 8 ? 4 : ct == 12 ? 6 : 8;
                addRecyclingRecipe(cableItem, of(c.getMaterial(), amount, Rubber, multiplier));
            });
        });
        GTAPI.all(ItemBasic.class, GTCore.ID).stream().filter(i -> i.getId().contains("mold") || i.getId().contains("shape")).forEach(i -> {
            addRecyclingRecipe(i, of(Steel, 4f));
        });
        GTAPI.all(DrumMachine.class).forEach(d -> {
            addRecyclingRecipe(d.getItem(NONE), of(d.getMaterial(), 6f));
        });
        addRecyclingRecipe(GTCoreItems.MotorLV, of(Copper, 2f, Tin, 1f, Steel, 1f, Iron, 0.5f));
        addRecyclingRecipe(GTCoreItems.MotorMV, of(Copper, 5f, Aluminium, 1f, Steel, 0.5f));
        addRecyclingRecipe(GTCoreItems.MotorHV, of(Copper, 8f, Gold, 1f, StainlessSteel, 1f, Steel, 0.5f));
        addRecyclingRecipe(GTCoreItems.MotorEV, of(Copper, 16f, Aluminium,1f, Titanium, 1f, Neodymium, 0.5f));
        addRecyclingRecipe(GTCoreItems.MotorIV, of(Copper, 32f, Tungsten,1f, TungstenSteel, 1f, Neodymium, 0.5f));
        addRecyclingRecipe(GT5RCovers.COVER_PUMP.getItem(LV).getItem(), of(Tin, 5.78f, Bronze, 3f, Copper, 2f, Iron, 1.5f));
        addRecyclingRecipe(GT5RCovers.COVER_PUMP.getItem(MV).getItem(), of(Copper, 5.5f, Bronze, 4.34f, Steel, 3.5f, Aluminium, 1f));
        addRecyclingRecipe(GT5RCovers.COVER_PUMP.getItem(HV).getItem(), of(Copper, 8f, Steel, 4.78f, StainlessSteel, 4f, Gold, 1.5f));
        addRecyclingRecipe(GT5RCovers.COVER_PUMP.getItem(EV).getItem(), of(Copper, 16f, StainlessSteel, 4.78f, Titanium, 4f, Aluminium, 1.5f));
        addRecyclingRecipe(GT5RCovers.COVER_PUMP.getItem(IV).getItem(), of(Copper, 32f, TungstenSteel, 3.67f, Tungsten, 1.5f, Neodymium, 0.5f));
        addRecyclingRecipe(GT5RCovers.COVER_CONVEYOR.getItem(LV).getItem(), of(Rubber, 6f, Copper, 4f, Iron, 3f, Tin, 2.5f));
        addRecyclingRecipe(GT5RCovers.COVER_CONVEYOR.getItem(MV).getItem(), of(Rubber, 6f, Copper, 10.5f, Aluminium, 2f, Steel, 1f));
        addRecyclingRecipe(GT5RCovers.COVER_CONVEYOR.getItem(HV).getItem(), of(Copper, 16f, Gold, 2.5f, StainlessSteel, 2f, Steel, 1f));
        addRecyclingRecipe(GT5RCovers.COVER_CONVEYOR.getItem(EV).getItem(), of(Copper, 32f, Aluminium, 2.5f, Titanium, 2f, Neodymium, 1f));
        addRecyclingRecipe(GT5RCovers.COVER_CONVEYOR.getItem(IV).getItem(), of(Copper, 64f, Tungsten, 2.5f, TungstenSteel, 2f, Neodymium, 1f));
        addRecyclingRecipe(GT5RItems.PistonLV, of(Steel, 5f, Tin, 2f, Copper, 2f, Iron, 1.5f));
        addRecyclingRecipe(GT5RItems.PistonMV, of(Aluminium, 6f, Copper, 6f, Steel, 0.5f));
        addRecyclingRecipe(GT5RItems.PistonHV, of(Copper, 8f, StainlessSteel, 6f, Gold, 2f, Steel, 0.5f));
        addRecyclingRecipe(GT5RItems.PistonEV, of(Copper, 16f, Titanium, 6f, Aluminium, 2f, Neodymium, 0.5f));
        addRecyclingRecipe(GT5RItems.PistonIV, of(Copper, 32f, TungstenSteel, 6f, Tungsten, 2f, Neodymium, 0.5f));
        addRecyclingRecipe(SOLID_FUEL_BOILER.getItem(BRONZE), of(Stone, 8f, Bronze, 5f, Brick, 2f));
        addRecyclingRecipe(SOLID_FUEL_BOILER.getItem(STEEL), of(Stone, 8f, Steel, 5f, Brick, 2f));
        addRecyclingRecipe(LAVA_BOILER.getItem(STEEL), of(Steel, 10f, Glass, 3f, Brick, 3f));
        addRecyclingRecipe(SOLAR_BOILER.getItem(BRONZE), of(Bronze, 7f, Glass, 3f, Silver, 3f, Brick, 3f));
        addRecyclingRecipe(STEAM_FURNACE.getItem(BRONZE), of(Bronze, 12f, Stone, 8f, Brick, 3f));
        addRecyclingRecipe(STEAM_FURNACE.getItem(STEEL), of(Steel, 12f, Stone, 8f, Brick, 3f));
        addRecyclingRecipe(STEAM_COMPRESSOR.getItem(BRONZE), of(Bronze, 14f, Stone, 8f, Wood, 6f));
        addRecyclingRecipe(STEAM_COMPRESSOR.getItem(STEEL), of(Steel, 14f, Stone, 8f, Wood, 6f));
        addRecyclingRecipe(STEAM_EXTRACTOR.getItem(BRONZE), of(Bronze, 14f, Stone, 4f, Wood, 3f));
        addRecyclingRecipe(STEAM_EXTRACTOR.getItem(STEEL), of(Steel, 14f, Stone, 4f, Wood, 3f));
        addRecyclingRecipe(STEAM_FORGE_HAMMER.getItem(BRONZE), of(Bronze, 14f, Iron, 10f, Stone, 4f, Wood, 3f));
        addRecyclingRecipe(STEAM_FORGE_HAMMER.getItem(STEEL), of(Steel, 14f, Iron, 10f, Stone, 4f, Wood, 3f));
        addRecyclingRecipe(STEAM_ALLOY_SMELTER.getItem(BRONZE), of(Bronze, 11f, Stone, 16f, Wood, 3f));
        addRecyclingRecipe(STEAM_ALLOY_SMELTER.getItem(STEEL), of(Steel, 11f, Stone, 16f, Wood, 3f));
        addRecyclingRecipe(STEAM_SIFTER.getItem(BRONZE), of(Bronze, 11f, Steel, 3f, Stone, 8f, Wood, 6f));
        addRecyclingRecipe(STEAM_SIFTER.getItem(STEEL), of(Steel, 14f, Stone, 8f, Wood, 6f));
        addRecyclingRecipe(STEAM_MACERATOR.getItem(BRONZE), of(Bronze, 12f, Stone, 8f, Wood, 6f, Diamond, 2f));
        addRecyclingRecipe(STEAM_MACERATOR.getItem(STEEL), of(Steel, 12f, Stone, 8f, Wood, 6f, Diamond, 2f));
        addRecyclingRecipe(STEAM_CUTTER.getItem(BRONZE), of(Bronze, 13f, Steel, 4f, Stone, 8f, Wood, 6f));
        addRecyclingRecipe(STEAM_CUTTER.getItem(STEEL), of(Steel, 17f, Stone, 8f, Wood, 6f));
        for (Tier tier : getAllElectric()) {
            addRecyclingRecipe(GT5Reimagined.get(BlockCasing.class,"casing_" + tier.getId()), of(TIER_MATERIALS.get(tier), 8f));
        }
        addRecyclingRecipe(GT5RBlocks.BRONZE_CASING, of(Bronze, 8f));
        addRecyclingRecipe(GT5RBlocks.BRICKED_BRONZE_CASING, of(Bronze, 5f, Brick, 3f));
        addRecyclingRecipe(GT5RBlocks.STEEL_CASING, of(Steel, 8f));
        addRecyclingRecipe(GT5RBlocks.BRICKED_STEEL_CASING, of(Steel, 5f, Brick, 3f));
        addRecyclingRecipe(GT5RBlocks.SOLID_STEEL_CASING, of(Steel, 8f));
        addRecyclingRecipe(GT5RBlocks.STAINLESS_STEEL_CASING, of(StainlessSteel, 8f));
        addRecyclingRecipe(GT5RBlocks.TITANIUM_CASING, of(Titanium, 8f));
        addRecyclingRecipe(GT5RBlocks.TUNGSTENSTEEL_CASING, of(TungstenSteel, 8f));
        addRecyclingRecipe(GT5RBlocks.TUNGSTEN_CASING, of(Tungsten, 8f));
        addRecyclingRecipe(GT5RBlocks.PLATINUM_CASING, of(Platinum, 8f));
        addRecyclingRecipe(GT5RBlocks.BLACK_BRONZE_CASING, of(BlackBronze, 8f));
        addRecyclingRecipe(GT5RBlocks.HEAT_PROOF_CASING, of(Invar, 8f));
        addRecyclingRecipe(GT5RBlocks.FROST_PROOF_CASING, of(Aluminium, 8f));
        addRecyclingRecipe(GT5RBlocks.RADIATION_PROOF_CASING, of(Lead, 8f));
        addRecyclingRecipe(GT5RBlocks.DENSE_LEAD_CASING, of(Lead, 56f));
        addRecyclingRecipe(GT5RBlocks.BRONZE_FIREBOX_CASING, of(Bronze, 8f));
        addRecyclingRecipe(GT5RBlocks.STEEL_FIREBOX_CASING, of(Steel, 8f));
        addRecyclingRecipe(GT5RBlocks.TITANIUM_FIREBOX_CASING, of(Titanium, 8f));
        addRecyclingRecipe(GT5RBlocks.TUNGSTENSTEEL_FIREBOX_CASING, of(TungstenSteel, 8f));
        addRecyclingRecipe(GT5RBlocks.BRONZE_GEARBOX_CASING, of(Bronze, 14f));
        addRecyclingRecipe(GT5RBlocks.STEEL_GEARBOX_CASING, of(Steel, 14f));
        addRecyclingRecipe(GT5RBlocks.TITANIUM_GEARBOX_CASING, of(Titanium, 14f));
        addRecyclingRecipe(GT5RBlocks.TUNGSTENSTEEL_GEARBOX_CASING, of(TungstenSteel, 14f));
        addRecyclingRecipe(GT5RBlocks.BRONZE_PIPE_CASING, of(Bronze, 18f));
        addRecyclingRecipe(GT5RBlocks.STEEL_PIPE_CASING, of(Steel, 18f));
        addRecyclingRecipe(GT5RBlocks.TITANIUM_PIPE_CASING, of(Titanium, 18f));
        addRecyclingRecipe(GT5RBlocks.TUNGSTENSTEEL_PIPE_CASING, of(TungstenSteel, 18f));
        addRecyclingRecipe(GT5RBlocks.STEEL_TURBINE_CASING, of(Steel, 9f));
        addRecyclingRecipe(GT5RBlocks.STAINLESS_STEEL_TURBINE_CASING, of(StainlessSteel, 9f));
        addRecyclingRecipe(GT5RBlocks.TITANIUM_TURBINE_CASING, of(Titanium, 9f));
        addRecyclingRecipe(GT5RBlocks.TUNGSTENSTEEL_TURBINE_CASING, of(TungstenSteel, 9f));;
        addRecyclingRecipe(GT5RBlocks.CUPRONICKEL_COIL, of(Cupronickel, 8f));
        addRecyclingRecipe(GT5RBlocks.KANTHAL_COIL, of(Kanthal, 8f));
        addRecyclingRecipe(GT5RBlocks.NICHROME_COIL, of(Nichrome, 8f));
        addRecyclingRecipe(GT5RBlocks.TUNGSTENSTEEL_COIL, of(TungstenSteel, 8f));
        addRecyclingRecipe(GT5RBlocks.HSSG_COIL, of(HSSG, 8f));
        addRecyclingRecipe(GT5RBlocks.NAQUADAH_COIL, of(Naquadah, 8f));
        addRecyclingRecipe(GT5RBlocks.NAQUADAH_ALLOY_COIL, of(NaquadahAlloy, 8f));
        addRecyclingRecipe(Items.IRON_DOOR, of(Iron, 2.0f));
        addRecyclingRecipe(GT5RBlocks.MINING_PIPE_THIN, of(Steel, 0.5f));
        addRecyclingRecipe(GT5RBlocks.LONG_DIST_ITEM_PIPE, of(Electrum, 12f, Plastic, 4f));
        addRecyclingRecipe(GT5RBlocks.LONG_DIST_FLUID_PIPE, of(StainlessSteel, 12f, Plastic, 4f));
        addRecyclingRecipe(GT5RBlocks.LONG_DIST_WIRE_EV, of(Tin, 16f, Rubber, 4f, Copper, 2f, Aluminium, 2f));
        addRecyclingRecipe(GT5RBlocks.LONG_DIST_WIRE_IV, of(Copper, 18f, Rubber, 4f, Aluminium, 2f));
        addRecyclingRecipe(GT5RBlocks.LONG_DIST_WIRE_LUV, of(Electrum, 16f, Rubber, 4f, Copper, 2f, Aluminium, 2f));
        addRecyclingRecipe(GT5RBlocks.LONG_DIST_WIRE_ZPM, of(Aluminium, 18f, Rubber, 4f, Copper, 2f));
        addRecyclingRecipe(GT5RBlocks.LONG_DIST_WIRE_UV, of(Platinum, 16f, Rubber, 4f, Copper, 2f, Aluminium, 2f));
        addRecyclingRecipe(Items.IRON_HELMET, of(Iron, 5f));
        addRecyclingRecipe(Items.IRON_CHESTPLATE, of(Iron, 8f));
        addRecyclingRecipe(Items.IRON_LEGGINGS, of(Iron, 7f));
        addRecyclingRecipe(Items.IRON_BOOTS, of(Iron, 4f));
        addRecyclingRecipe(Items.GOLDEN_HELMET, of(Gold, 5f));
        addRecyclingRecipe(Items.GOLDEN_CHESTPLATE, of(Gold, 8f));
        addRecyclingRecipe(Items.GOLDEN_LEGGINGS, of(Gold, 7f));
        addRecyclingRecipe(Items.GOLDEN_BOOTS, of(Gold, 4f));
        addRecyclingRecipe(Items.DIAMOND_HELMET, of(Diamond, 5f));
        addRecyclingRecipe(Items.DIAMOND_CHESTPLATE, of(Diamond, 8f));
        addRecyclingRecipe(Items.DIAMOND_LEGGINGS, of(Diamond, 7f));
        addRecyclingRecipe(Items.DIAMOND_BOOTS, of(Diamond, 4f));
        MaterialTags.ARMOR.all().forEach(m -> {
            addRecyclingRecipe(GTTools.HELMET.getToolStack(m).getItem(), of(m, 5f));
            addRecyclingRecipe(GTTools.CHESTPLATE.getToolStack(m).getItem(), of(m, 8f));
            addRecyclingRecipe(GTTools.LEGGINGS.getToolStack(m).getItem(), of(m, 7f));
            addRecyclingRecipe(GTTools.BOOTS.getToolStack(m).getItem(), of(m, 4f));
        });
        addRecyclingRecipe(Items.CAULDRON, of(Iron, 7f));
    }

    private static void addRecyclingRecipe(ItemLike input, ImmutableMap<Material, Float> outputs){
        addRecyclingRecipe(RecipeIngredient.of(input), outputs, RegistryUtils.getIdFromItem(input.asItem()).getPath());
    }

    private static void addRecyclingRecipe(Ingredient input, ImmutableMap<Material, Float> outputs, String id){
        RecipeBuilder arc = RecipeMaps.ARC_FURNACE.RB();
        RecipeBuilder mac = RecipeMaps.PULVERIZER.RB();
        arc.ii(input);
        mac.ii(input);
        long[] totalMassArc = new long[]{0};
        long[] totalMassMac = new long[]{0};
        outputs.forEach((material, floatAmount) -> {
            Material arcOutput = material.has(MaterialTags.RUBBERTOOLS) || material == Wood ? Ash : MaterialTags.ARC_SMELT_INTO.get(material);
            Material macOutput = MaterialTags.MACERATE_INTO.get(material);
            int roundedAmount = floatAmount.intValue();
            float arcFloatAmount = floatAmount;
            if (material.has(MaterialTags.RUBBERTOOLS) || material == Wood) arcFloatAmount = floatAmount / 9;
            int roundedArcAmount = (int)arcFloatAmount;
            float leftover = floatAmount - roundedAmount;
            float arcLeftover = arcFloatAmount - roundedArcAmount;
            totalMassMac[0] += (long) (material.getMass() * floatAmount);
            if (leftover > 0){
                float smallLeftover = leftover * 4;
                int smallExtra = (int) (smallLeftover);
                float tinyLeftover = smallLeftover - smallExtra;
                int tinyExtra = (int) (leftover * 9);
                if (tinyLeftover > 0){
                    mac.io(TINY_DUST.get(macOutput, (roundedAmount * 9) + tinyExtra));
                } else {
                    mac.io(SMALL_DUST.get(macOutput, (roundedAmount * 4) + smallExtra));
                }
            } else {
                mac.io(DUST.get(macOutput, roundedAmount));
            }
            if (arcOutput == Ash || arcOutput.has(INGOT)){
                int amount = 0;
                MaterialTypeItem<?> arcType;
                if (arcLeftover > 0){
                    float smallLeftover = arcLeftover * 4;
                    int smallExtra = (int) (smallLeftover);
                    float tinyLeftover = smallLeftover - smallExtra;
                    int tinyExtra = (int) (arcLeftover * 9);
                    if (tinyLeftover > 0){
                        amount = (roundedArcAmount * 9) + tinyExtra;
                        arcType = arcOutput == Ash ? TINY_DUST : NUGGET;
                    } else {
                        arcType = arcOutput == Ash ? SMALL_DUST : CHUNK;
                        amount = (roundedArcAmount * 4) + smallExtra;
                    }
                } else {
                    arcType = arcOutput == Ash ? DUST : INGOT;
                    amount = roundedArcAmount;
                }
                if (amount > 0){
                    totalMassArc[0] += (long) (arcOutput.getMass() * arcFloatAmount);
                    arc.io(arcType.get(arcOutput, amount));
                }
            }

        });
        if (totalMassArc[0] > 0){
            arc.fi(Oxygen.getGas((int) totalMassArc[0])).tags(GT5RRecipeTags.RECYCLING).add(id, totalMassArc[0], 32);
        }
        if (totalMassMac[0] > 0) {
            mac.tags(GT5RRecipeTags.RECYCLING).add(id, totalMassMac[0] * 2, 4);
        }

    }

    static int fromTier(Tier tier){
        if (tier == LV) return 1;
        if (tier == MV) return 2;
        if (tier == HV) return 4;
        if (tier == IV) return 16;
        return 8;
    }
}
