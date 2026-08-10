package org.gtreimagined.gt5r.data;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.Tags.Blocks;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.item.ItemBattery;
import org.gtreimagined.gtlib.machine.BlockMachine;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.pipe.BlockPipe;
import org.gtreimagined.gtlib.recipe.ingredient.PropertyIngredient;
import org.gtreimagined.gtlib.recipe.material.MaterialRecipe;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.gtreimagined.gtlib.tool.IGTTool;
import org.gtreimagined.gtlib.tool.behaviour.BehaviourExtendedHighlight;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.gtreimagined.gt5r.items.ItemPortableScanner;
import org.gtreimagined.gt5r.items.ItemTurbineRotorOld;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreTags;
import org.gtreimagined.gtcore.data.GTCoreTools;
import org.gtreimagined.gtcore.item.ItemPowerUnit;
import org.jetbrains.annotations.NotNull;
import org.gtreimagined.tesseract.api.forge.TesseractCaps;
import org.gtreimagined.tesseract.api.eu.IEnergyHandler;

import java.util.Map;

import static org.gtreimagined.gtlib.material.Material.NULL;
import static org.gtreimagined.gt5r.data.GT5RItems.BatteryMediumLithium;

public class ToolTypes {

    public static final GTToolType SMALL_TURBINE_ROTOR = GTAPI.register(GTToolType.class, new GTToolType(GT5Reimagined.ID, "small_turbine_rotor", 1, 1, 1, -1.0F, 0.0f, false)).setHasSecondary(false).setMaterialTypeItem(GT5RMaterialTypes.SMALL_BROKEN_TURBINE_ROTOR).setTag(new ResourceLocation(Ref.ID, "turbine_rotor")).setToolSupplier(ItemTurbineRotorOld::new);
    public static final GTToolType PINCERS = GTAPI.register(GTToolType.class, new GTToolType(GT5Reimagined.ID, "pincers", 1, 2, 10, 5.0f, 0.0f, false)).setRepairable(false);

    public static final MaterialRecipe.Provider SCANNER_BUILDER = MaterialRecipe.registerProvider("portable-scanner", GT5Reimagined.ID, id -> new MaterialRecipe.ItemBuilder() {

        @Override
        public ItemStack build(CraftingContainer inv, MaterialRecipe.Result mats) {
            Tuple<Long, Long> battery = (Tuple<Long, Long>) mats.mats.get("battery");
            ItemStack scanner = new ItemStack(GT5RItems.PortableScanner);
            scanner.getCapability(TesseractCaps.ENERGY_HANDLER_CAPABILITY_ITEM).ifPresent(i -> i.setEnergy(battery.getA()));
            return scanner;
        }

        @Override
        public Map<String, Object> getFromResult(@NotNull ItemStack stack) {
            return ImmutableMap.of("energy", getEnergy(stack).getA(), "maxEnergy", getEnergy(stack).getB());
        }
    });
    public static final MaterialRecipe.Provider POWERED_TOOL_BUILDER = MaterialRecipe.registerProvider("powered-tool", GT5Reimagined.ID, id -> new MaterialRecipe.ItemBuilder() {

        @Override
        public ItemStack build(CraftingContainer inv, MaterialRecipe.Result mats) {
            Material m = (Material) mats.mats.get("secondary");
            Tuple<Long, Long> battery = (Tuple<Long, Long>) mats.mats.get("battery");
            IGTTool type = GTAPI.get(IGTTool.class, id.replace('-', '_'), GTCore.ID);
            if (type == null) return ItemStack.EMPTY;
            return type.resolveStack((Material) mats.mats.get("primary"), m == null ? NULL : m, battery.getA(), battery.getB());
        }

        @Override
        public Map<String, Object> getFromResult(@NotNull ItemStack stack) {
            CompoundTag nbt = stack.getOrCreateTagElement(Ref.TAG_TOOL_DATA);
            Material primary = Material.get(nbt.getString(Ref.KEY_TOOL_DATA_PRIMARY_MATERIAL));
            Material secondary = Material.get(nbt.getString(Ref.KEY_TOOL_DATA_SECONDARY_MATERIAL));
            return ImmutableMap.of("primary", primary, "secondary", secondary, "energy", getEnergy(stack).getA(), "maxEnergy", getEnergy(stack).getB());
        }
    });

    public static final MaterialRecipe.Provider UNIT_POWERED_TOOL_BUILDER = MaterialRecipe.registerProvider("powered-tool-from-unit", GT5Reimagined.ID, id -> new MaterialRecipe.ItemBuilder() {

        @Override
        public ItemStack build(CraftingContainer inv, MaterialRecipe.Result mats) {
            Tuple<Long, Tuple<Long, Material>> t = (Tuple<Long, Tuple<Long, Material>>) mats.mats.get("secondary");
            IGTTool type = GTAPI.get(IGTTool.class, id.replace('-', '_'), GTCore.ID);
            t.getB().getB();
            return type.resolveStack((Material) mats.mats.get("primary"), t.getB().getB(), t.getA(), t.getB().getA());
        }

        @Override
        public Map<String, Object> getFromResult(@NotNull ItemStack stack) {
            return ImmutableMap.of();
        }
    });
    static {
        PropertyIngredient.addGetter(GTCoreTags.BATTERIES_LV.location(), ToolTypes::getEnergy);
        PropertyIngredient.addGetter(GTCoreTags.BATTERIES_MV.location(), ToolTypes::getEnergy);
        PropertyIngredient.addGetter(GTCoreTags.BATTERIES_HV.location(), ToolTypes::getEnergy);
        PropertyIngredient.addGetter(BatteryMediumLithium.getLoc(), ToolTypes::getEnergy);
        PropertyIngredient.addGetter(GTCoreTags.POWER_UNIT_LV.location(), ToolTypes::getEnergyAndMat);
        PropertyIngredient.addGetter(GTCoreTags.POWER_UNIT_MV.location(), ToolTypes::getEnergyAndMat);
        PropertyIngredient.addGetter(GTCoreTags.POWER_UNIT_HV.location(), ToolTypes::getEnergyAndMat);
        PropertyIngredient.addGetter(GTCoreTags.POWER_UNIT_SMALL.location(), ToolTypes::getEnergyAndMat);
        PropertyIngredient.addGetter(GTCoreTags.POWER_UNIT_JACKHAMMER.location(), ToolTypes::getEnergyAndMat);
    }

    public static void init(){
        if (FMLEnvironment.dist.isClient()){
            GTCoreTools.ELECTRIC_WRENCH_ALT.addBehaviour(new BehaviourExtendedHighlight(b -> b instanceof BlockMachine || (b instanceof BlockPipe && b.builtInRegistryHolder().is(GTTools.WRENCH.getToolType())) || b.defaultBlockState().hasProperty(BlockStateProperties.FACING_HOPPER) || b.defaultBlockState().hasProperty(BlockStateProperties.HORIZONTAL_FACING), BehaviourExtendedHighlight.PIPE_FUNCTION));
        }
        GTTools.HAMMER.addEffectiveBlockTags(Blocks.GLASS, Tags.Blocks.GLASS_PANES);
    }

    public static Tuple<Long, Long> getEnergy(ItemStack stack){
        if (stack.getItem() instanceof ItemBattery battery){
            long energy = stack.getCapability(TesseractCaps.ENERGY_HANDLER_CAPABILITY_ITEM).map(IEnergyHandler::getEnergy).orElse((long)0);
            long maxEnergy = stack.getCapability(TesseractCaps.ENERGY_HANDLER_CAPABILITY_ITEM).map(IEnergyHandler::getCapacity).orElse(battery.getCapacity());
            return new Tuple<>(energy, maxEnergy);
        }
        if (stack.getItem() instanceof ItemPortableScanner){
            long energy = stack.getCapability(TesseractCaps.ENERGY_HANDLER_CAPABILITY_ITEM).map(IEnergyHandler::getEnergy).orElse((long)0);
            long maxEnergy = stack.getCapability(TesseractCaps.ENERGY_HANDLER_CAPABILITY_ITEM).map(IEnergyHandler::getCapacity).orElse(400000L);
            return new Tuple<>(energy, maxEnergy);
        }
        if (stack.getItem() instanceof IGTTool tool){
            if (tool.getGTToolType().isPowered()){
                long currentEnergy = tool.getCurrentEnergy(stack);
                long maxEnergy = tool.getMaxEnergy(stack);
                return new Tuple<>(currentEnergy, maxEnergy);
            }
        }
        return new Tuple<>(0L, 0L);
    }

    public static Tuple<Long, Tuple<Long, Material>> getEnergyAndMat(ItemStack stack){
        if (stack.getItem() instanceof ItemPowerUnit tool){
            long currentEnergy = tool.getCurrentEnergy(stack);
            long maxEnergy = tool.getMaxEnergy(stack);
            return new Tuple<>(currentEnergy, new Tuple<>(maxEnergy, tool.getMaterial(stack)));
        }
        return new Tuple<>(0L, new Tuple<>(0L, NULL));
    }
}
