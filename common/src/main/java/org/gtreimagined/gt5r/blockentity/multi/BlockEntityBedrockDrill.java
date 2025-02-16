package org.gtreimagined.gt5r.blockentity.multi;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.Getter;
import lombok.Setter;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.blockentity.multi.BlockEntityMultiMachine;
import muramasa.antimatter.capability.machine.MachineRecipeHandler;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.data.AntimatterMaterials;
import muramasa.antimatter.data.AntimatterStoneTypes;
import muramasa.antimatter.machine.MachineState;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.MaterialType;
import muramasa.antimatter.ore.CobbleStoneType;
import muramasa.antimatter.ore.StoneType;
import muramasa.antimatter.recipe.IRecipe;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import muramasa.antimatter.util.RegistryUtils;
import muramasa.antimatter.util.TagUtils;
import muramasa.antimatter.util.int3;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.data.RecipeMaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.gtreimagined.gt5r.data.Materials.Lubricant;

public class BlockEntityBedrockDrill extends BlockEntityMultiMachine<BlockEntityBedrockDrill> {
    @Getter
    @Setter
    int bedrockOresFound = 0;

    IRecipe recipe;
    int oreChance = 0;

    Item cobble = Items.COBBLED_DEEPSLATE;
    ItemStack mainOutput = ItemStack.EMPTY;
    List<Pair<ItemStack, Integer>> byProducts = new ArrayList<>();
    ItemStack cachedOutput = ItemStack.EMPTY;

    public BlockEntityBedrockDrill(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        recipeHandler.set(() -> new MachineRecipeHandler<>(this){
            @Override
            public void onServerUpdate() {

            }
        });
    }

    @Override
    public boolean onStructureFormed() {
        recipe = null;
        oreChance = 0;
        if (level == null) return false;
        BlockPos behind = new int3(this.getBlockPos(), this.getFacing()).back(1).below(2).immutable();
        for (int x = -1; x < 2; x++) {
            for (int z = -1; z < 2; z++) {
                BlockPos orePos = behind.mutable().move(x, 0, z).immutable();
                BlockState oreBlock = level.getBlockState(orePos);
                if (oreBlock.is(TagUtils.getForgelikeBlockTag("bedrock_ores"))){
                    oreChance += 2;
                    if (recipe == null){
                        recipe = RecipeMaps.BEDROCK_DRILL.find(new ItemStack[]{new ItemStack(oreBlock.getBlock())}, new FluidHolder[]{Lubricant.getLiquid(100)}, Tier.LUV, r -> true);
                    }
                } else if (oreBlock.is(TagUtils.getForgelikeBlockTag("bedrock_small_ores"))){
                    oreChance++;
                    if (recipe == null){
                        recipe = RecipeMaps.BEDROCK_DRILL.find(new ItemStack[]{new ItemStack(oreBlock.getBlock())}, new FluidHolder[]{Lubricant.getLiquid(100)}, Tier.LUV, r -> true);
                    }
                }
            }
        }
        if (recipe == null || oreChance <= 0) return false;
        ItemStack[] outputs = recipe.getOutputItems(false);
        if (recipe.getOutputChances() == null) return false;
        mainOutput = outputs[0];
        for (int i = 1; i < outputs.length - 2; i++) {
            ItemStack output = outputs[i];
            byProducts.add(Pair.of(output, recipe.getOutputChances()[i]));
        }
        return super.onStructureFormed();
    }

    @Override
    public boolean checkStructure() {
        bedrockOresFound = 0;
        return super.checkStructure();
    }

    int inActiveTicks = 0;
    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
        if (inActiveTicks > 40){
            if (this.getMachineState() == MachineState.ACTIVE) setMachineState(MachineState.IDLE);
            inActiveTicks = 0;
        }
        if (!cachedOutput.isEmpty()) {
            if (itemHandler.map(i -> i.canOutputsFit(new ItemStack[]{cachedOutput})).orElse(false)){
                itemHandler.ifPresent(i -> i.addOutputs(cachedOutput));
                cachedOutput = ItemStack.EMPTY;
            }
            return;
        }
        if (recipe != null && energyHandler.map(e -> e.getEnergy() >= recipe.getPower()).orElse(false)) {
            boolean consumeFluid = fluidHandler.map(h -> {
                List<FluidHolder> fluidInputs = h.consumeAndReturnInputs(recipe.getInputFluids(), true);
                return !fluidInputs.isEmpty();
            }).orElse(true);
            if (consumeFluid && !mainOutput.isEmpty()) {
                if (level.getRandom().nextInt(1000) == 0) {
                    List<StoneType> types = AntimatterAPI.all(StoneType.class).stream().filter(s -> s instanceof CobbleStoneType || s == AntimatterStoneTypes.BEDROCK || s == AntimatterStoneTypes.STONE).toList();
                    int index = level.getRandom().nextInt(types.size());
                    StoneType type = types.get(index);
                    if (type == AntimatterStoneTypes.DEEPSLATE) {
                        cobble = Items.COBBLED_DEEPSLATE;
                    } else if (type == AntimatterStoneTypes.STONE){
                        cobble = Items.COBBLESTONE;
                    } else if (type instanceof CobbleStoneType cobbleStoneType){
                        cobble = cobbleStoneType.getBlock("cobble").asItem();
                    }
                }
                int selector = level.getRandom().nextInt(128);
                ItemStack output = new ItemStack(cobble);
                if (selector < oreChance) {
                    if (level.getRandom().nextInt(32) == 0 && !byProducts.isEmpty()) output = byProducts.get(level.getRandom().nextInt(byProducts.size())).first();
                    else output = this.mainOutput;
                } else {
                    if (level.getRandom().nextInt(1000) == 0){
                        output = AntimatterMaterialTypes.DUST.get(AntimatterMaterials.Bedrock, 1);
                    } else if (level.dimension().location().equals(Level.NETHER.location())){
                        output = new ItemStack(Items.NETHERRACK);
                    }
                }
                ItemStack finalOutput = output;
                if (itemHandler.map(i -> i.canOutputsFit(new ItemStack[]{finalOutput})).orElse(false)){
                    itemHandler.ifPresent(i -> i.addOutputs(finalOutput));
                } else {
                    cachedOutput = output;
                }
                energyHandler.ifPresent(e -> e.extractEu(recipe.getPower(), false));
                fluidHandler.ifPresent(f -> f.consumeAndReturnInputs(recipe.getInputFluids(), false));
                inActiveTicks = 0;
                if (getMachineState() == MachineState.IDLE) {
                    setMachineState(MachineState.ACTIVE);
                }
            } else setInActive();
        } else setInActive();
    }

    private void setInActive(){
        if (this.getMachineState() == MachineState.ACTIVE) inActiveTicks++;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!cachedOutput.isEmpty()){
            tag.put("cachedOutput", cachedOutput.save(new CompoundTag()));
        }
        if (cobble != null) {
            tag.putString("cobble", RegistryUtils.getIdFromItem(cobble).toString());
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("cachedOutput")) {
            cachedOutput = ItemStack.of(tag.getCompound("cachedOutput"));
        }
        if (tag.contains("cobble")) {
            cobble = RegistryUtils.getItemFromID(new ResourceLocation(tag.getString("cobble")));
        }
    }
}
