package muramasa.gregtech.loader.crafting;

import com.google.common.collect.ImmutableMap;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.data.ForgeCTags;
import muramasa.antimatter.datagen.providers.AntimatterRecipeProvider;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.recipe.ingredient.PropertyIngredient;
import muramasa.antimatter.tool.IAntimatterTool;
import muramasa.gregtech.GT5RRef;
import muramasa.gregtech.data.GT5RItems;
import muramasa.gregtech.data.ToolTypes;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreItems;

import java.util.Map;
import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static muramasa.antimatter.data.AntimatterDefaultTools.*;
import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.data.AntimatterMaterials.Diamond;
import static muramasa.antimatter.data.AntimatterMaterials.Wood;
import static muramasa.antimatter.material.MaterialTags.TOOLS;
import static muramasa.gregtech.data.Materials.*;
import static org.gtreimagined.gtcore.data.GTCoreTags.*;
import static org.gtreimagined.gtcore.data.GTCoreTools.*;

public class ElectricToolRecipes {

    public static void loadRecipes(Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider){
        TOOLS.getAll().forEach((m, d) -> {
            if (d.toolTypes().contains(ToolTypes.PINCERS) && (m.has(GEM) || m.has(PLATE))){
                TagKey<Item> plateGem = m.has(GEM) ? GEM.getMaterialTag(m) : m.has(PLATE) ? PLATE.getMaterialTag(m) : INGOT.getMaterialTag(m);
                TagKey<Item> rod = d.handleMaterial().has(ROD) ? ROD.getMaterialTag(d.handleMaterial()) : ROD.getMaterialTag(Wood);
                ImmutableMap.Builder<Character, Object> builder = ImmutableMap.builder();
                builder.put('R', rod).put('P', plateGem).put('H', AntimatterDefaultTools.HAMMER.getTag()).put('S', SCREWDRIVER.getTag());
                if (m.has(SCREW)) builder.put('W', SCREW.getMaterialTag(m));
                String last = m.has(SCREW) ? " W " : " R ";
                provider.addStackRecipe(output, GTCore.ID, "", "", ToolTypes.PINCERS.getToolStack(m),
                        builder.build(), "PHP", last, "RSR");
            }
        });
        provider.addItemRecipe(output, "tools", GTCoreItems.GlassMagnifyingGlass, of('G', LENS.getMaterialTag(Glass), 'S', ForgeCTags.RODS_WOODEN), "G", "S");
        provider.addItemRecipe(output, "tools", GTCoreItems.DiamondMagnifyingGlass, of('G', LENS.getMaterialTag(Diamond), 'S', ForgeCTags.RODS_WOODEN), "G", "S");
        PLATE.all().forEach(m -> {
            if (m.has(DRILLBIT)){
                provider.addItemRecipe(output, "bits", DRILLBIT.get(m),
                        ImmutableMap.of('P', PLATE.getMaterialTag(m), 'S', PLATE.getMaterialTag(Steel), 'H', HAMMER.getTag()), "PSP", "PSP", "SHS");
            }
            if (m.has(WRENCHBIT)){
                provider.addItemRecipe(output, "bits", WRENCHBIT.get(m),
                        ImmutableMap.of('P', PLATE.getMaterialTag(m),
                                'R', RING.getMaterialTag(Steel),
                                'S', SCREW.getMaterialTag(Steel),
                                'H', HAMMER.getTag(),
                                's', SCREWDRIVER.getTag()), "HPS", "PRP", "SPs");
            }

            if (m.has(CHAINSAWBIT)){
                provider.addItemRecipe(output, "bits", CHAINSAWBIT.get(m),
                        ImmutableMap.of('P', PLATE.getMaterialTag(m),
                                'R', RING.getMaterialTag(Steel),
                                'S', PLATE.getMaterialTag(Steel),
                                'H', HAMMER.getTag()), "SRS", "PHP", "SRS");
            }

            if (m.has(BUZZSAW_BLADE)){
                provider.addItemRecipe(output, "bits", BUZZSAW_BLADE.get(m),
                        ImmutableMap.of('P', PLATE.getMaterialTag(m),
                                'W', WRENCH.getTag(),
                                'C', WIRE_CUTTER.getTag(),
                                'H', HAMMER.getTag(),
                                'F', FILE.getTag()), "WPH", "P P", "FPC");
            }

        });
        CriterionTriggerInstance in = provider.hasSafeItem(AntimatterDefaultTools.SCREWDRIVER.getTag());

        IAntimatterTool drill_lv = AntimatterAPI.get(IAntimatterTool.class, "drill_lv");
        IAntimatterTool drill_mv = AntimatterAPI.get(IAntimatterTool.class, "drill_mv");
        IAntimatterTool drill_hv = AntimatterAPI.get(IAntimatterTool.class, "drill_hv");
        provider.addToolRecipe(ToolTypes.POWERED_TOOL_BUILDER.get(DRILL.getId() + "-lv"),output, GT5RRef.ID, DRILL.getId() + "_lv_" + "recipe", "antimatter_drills",
                resolveStack(drill_lv, Material.NULL, Aluminium, 0, 100000),
                ImmutableMap.<Character, Object>builder()
                        .put('W', PropertyIngredient.of(DRILLBIT, "primary"))
                        .put('S', AntimatterDefaultTools.SCREWDRIVER.getTag())
                        .put('b', PropertyIngredient.builder("battery").itemTags(BATTERIES_SMALL).build())
                        .put('P', PropertyIngredient.builder("secondary").mats(Aluminium).types(PLATE).build())
                        .put('s', SCREW.getMaterialTag(Steel))
                        .put('G', GEAR_SMALL.getMaterialTag(Aluminium))
                        .put('M', GTCoreItems.MotorLV).build(), "sWS", "GMG", "PbP");
        provider.addToolRecipe(ToolTypes.POWERED_TOOL_BUILDER.get(DRILL.getId() + "-mv"),output, GT5RRef.ID, DRILL.getId() + "_mv_" + "recipe", "antimatter_drills",
                resolveStack(drill_mv, Material.NULL, StainlessSteel, 0, 200000),
                ImmutableMap.<Character, Object>builder()
                        .put('W', PropertyIngredient.of(DRILLBIT, "primary"))
                        .put('S', AntimatterDefaultTools.SCREWDRIVER.getTag())
                        .put('b', PropertyIngredient.builder("battery").itemTags(BATTERIES_MEDIUM).build())
                        .put('P', PropertyIngredient.builder("secondary").mats(StainlessSteel).types(PLATE).build())
                        .put('s', SCREW.getMaterialTag(Steel))
                        .put('G', GEAR_SMALL.getMaterialTag(StainlessSteel))
                        .put('M', GTCoreItems.MotorMV).build(), "sWS", "GMG", "PbP");
        provider.addToolRecipe(ToolTypes.POWERED_TOOL_BUILDER.get(DRILL.getId() + "-hv"),output, GT5RRef.ID, DRILL.getId() + "_hv_" + "recipe", "antimatter_drills",
                resolveStack(drill_hv, Material.NULL, Titanium, 0, 800000),
                ImmutableMap.<Character, Object>builder()
                        .put('W', PropertyIngredient.of(DRILLBIT, "primary"))
                        .put('S', AntimatterDefaultTools.SCREWDRIVER.getTag())
                        .put('b', PropertyIngredient.builder("battery").itemTags(BATTERIES_LARGE).build())
                        .put('P', PropertyIngredient.builder("secondary").mats(Titanium).types(PLATE).build())
                        .put('s', SCREW.getMaterialTag(Steel))
                        .put('G', GEAR_SMALL.getMaterialTag(Titanium))
                        .put('M', GTCoreItems.MotorHV).build(), "sWS", "GMG", "PbP");
        provider.addToolRecipe(ToolTypes.UNIT_POWERED_TOOL_BUILDER.get(DRILL.getId() + "-lv"),output, GT5RRef.ID, DRILL.getId() + "_lv_power_unit_" + "recipe", "antimatter_drills",
                resolveStack(drill_lv, Material.NULL, Aluminium, 0, 100000), of('B', PropertyIngredient.of(AntimatterMaterialTypes.DRILLBIT, "primary"), 'S', AntimatterDefaultTools.SCREWDRIVER.getTag(), 'P', PropertyIngredient.builder("secondary").itemTags(POWER_UNIT_LV).build()), "BS", "P ");
        provider.addToolRecipe(ToolTypes.UNIT_POWERED_TOOL_BUILDER.get(DRILL.getId() + "-mv"),output, GT5RRef.ID, DRILL.getId() + "_mv_power_unit_" + "recipe", "antimatter_drills",
                resolveStack(drill_mv, Material.NULL, StainlessSteel, 0, 100000), of('B', PropertyIngredient.of(AntimatterMaterialTypes.DRILLBIT, "primary"), 'S', AntimatterDefaultTools.SCREWDRIVER.getTag(), 'P', PropertyIngredient.builder("secondary").itemTags(POWER_UNIT_MV).build()), "BS", "P ");
        provider.addToolRecipe(ToolTypes.UNIT_POWERED_TOOL_BUILDER.get(DRILL.getId() + "-hv"),output, GT5RRef.ID, DRILL.getId() + "_hv_power_unit_" + "recipe", "antimatter_drills",
                resolveStack(drill_hv, Material.NULL, Titanium, 0, 100000), of('B', PropertyIngredient.of(AntimatterMaterialTypes.DRILLBIT, "primary"), 'S', AntimatterDefaultTools.SCREWDRIVER.getTag(), 'P', PropertyIngredient.builder("secondary").itemTags(POWER_UNIT_HV).build()), "BS", "P ");


        IAntimatterTool chainsaw_lv = AntimatterAPI.get(IAntimatterTool.class, "chainsaw_lv");
        IAntimatterTool chainsaw_mv = AntimatterAPI.get(IAntimatterTool.class, "chainsaw_mv");
        IAntimatterTool chainsaw_hv = AntimatterAPI.get(IAntimatterTool.class, "chainsaw_hv");
        provider.addToolRecipe(ToolTypes.POWERED_TOOL_BUILDER.get(CHAINSAW.getId() + "-lv"),output, GT5RRef.ID, CHAINSAW.getId() + "_lv_" + "recipe", "antimatter_chainsaws",
                resolveStack(chainsaw_lv, Material.NULL, Aluminium, 0, 100000),
                ImmutableMap.<Character, Object>builder()
                        .put('W', PropertyIngredient.of(CHAINSAWBIT, "primary"))
                        .put('S', AntimatterDefaultTools.SCREWDRIVER.getTag())
                        .put('b', PropertyIngredient.builder("battery").itemTags(BATTERIES_SMALL).build())
                        .put('P', PropertyIngredient.builder("secondary").mats(Aluminium).types(PLATE).build())
                        .put('s', SCREW.getMaterialTag(Steel))
                        .put('G', GEAR_SMALL.getMaterialTag(Aluminium))
                        .put('M', GTCoreItems.MotorLV).build(), "sWS", "GMG", "PbP");
        provider.addToolRecipe(ToolTypes.POWERED_TOOL_BUILDER.get(CHAINSAW.getId() + "-mv"),output, GT5RRef.ID, CHAINSAW.getId() + "_mv_" + "recipe", "antimatter_chainsaws",
                resolveStack(chainsaw_mv, Material.NULL, StainlessSteel, 0, 200000),
                ImmutableMap.<Character, Object>builder()
                        .put('W', PropertyIngredient.of(CHAINSAWBIT, "primary"))
                        .put('S', AntimatterDefaultTools.SCREWDRIVER.getTag())
                        .put('b', PropertyIngredient.builder("battery").itemTags(BATTERIES_MEDIUM).build())
                        .put('P', PropertyIngredient.builder("secondary").mats(StainlessSteel).types(PLATE).build())
                        .put('s', SCREW.getMaterialTag(Steel))
                        .put('G', GEAR_SMALL.getMaterialTag(StainlessSteel))
                        .put('M', GTCoreItems.MotorMV).build(), "sWS", "GMG", "PbP");
        provider.addToolRecipe(ToolTypes.POWERED_TOOL_BUILDER.get(CHAINSAW.getId() + "-hv"),output, GT5RRef.ID, CHAINSAW.getId() + "_hv_" + "recipe", "antimatter_chainsaws",
                resolveStack(chainsaw_hv, Material.NULL, Titanium, 0, 800000),
                ImmutableMap.<Character, Object>builder()
                        .put('W', PropertyIngredient.of(CHAINSAWBIT, "primary"))
                        .put('S', AntimatterDefaultTools.SCREWDRIVER.getTag())
                        .put('b', PropertyIngredient.builder("battery").itemTags(BATTERIES_LARGE).build())
                        .put('P', PropertyIngredient.builder("secondary").mats(Titanium).types(PLATE).build())
                        .put('s', SCREW.getMaterialTag(Steel))
                        .put('G', GEAR_SMALL.getMaterialTag(Titanium))
                        .put('M', GTCoreItems.MotorHV).build(), "sWS", "GMG", "PbP");
        provider.addToolRecipe(ToolTypes.UNIT_POWERED_TOOL_BUILDER.get(CHAINSAW.getId() + "-lv"),output, GT5RRef.ID, CHAINSAW.getId() + "_lv_power_unit_" + "recipe", "antimatter_chainsaws",
                resolveStack(chainsaw_lv, Material.NULL, Aluminium, 0, 100000), of('B', PropertyIngredient.of(AntimatterMaterialTypes.CHAINSAWBIT, "primary"), 'S', AntimatterDefaultTools.SCREWDRIVER.getTag(), 'P', PropertyIngredient.builder("secondary").itemTags(POWER_UNIT_LV).build()), "BS", "P ");
        provider.addToolRecipe(ToolTypes.UNIT_POWERED_TOOL_BUILDER.get(CHAINSAW.getId() + "-mv"),output, GT5RRef.ID, CHAINSAW.getId() + "_mv_power_unit_" + "recipe", "antimatter_chainsaws",
                resolveStack(chainsaw_mv, Material.NULL, StainlessSteel, 0, 100000), of('B', PropertyIngredient.of(AntimatterMaterialTypes.CHAINSAWBIT, "primary"), 'S', AntimatterDefaultTools.SCREWDRIVER.getTag(), 'P', PropertyIngredient.builder("secondary").itemTags(POWER_UNIT_MV).build()), "BS", "P ");
        provider.addToolRecipe(ToolTypes.UNIT_POWERED_TOOL_BUILDER.get(CHAINSAW.getId() + "-hv"),output, GT5RRef.ID, CHAINSAW.getId() + "_hv_power_unit_" + "recipe", "antimatter_chainsaws",
                resolveStack(chainsaw_hv, Material.NULL, Titanium, 0, 100000), of('B', PropertyIngredient.of(AntimatterMaterialTypes.CHAINSAWBIT, "primary"), 'S', AntimatterDefaultTools.SCREWDRIVER.getTag(), 'P', PropertyIngredient.builder("secondary").itemTags(POWER_UNIT_HV).build()), "BS", "P ");

        IAntimatterTool electric_wrench_lv = AntimatterAPI.get(IAntimatterTool.class, "electric_wrench_lv");
        IAntimatterTool electric_wrench_mv = AntimatterAPI.get(IAntimatterTool.class, "electric_wrench_mv");
        IAntimatterTool electric_wrench_hv = AntimatterAPI.get(IAntimatterTool.class, "electric_wrench_hv");
        provider.addToolRecipe(ToolTypes.POWERED_TOOL_BUILDER.get(ELECTRIC_WRENCH.getId() + "-lv"),output, GT5RRef.ID, ELECTRIC_WRENCH.getId() + "_lv_" + "recipe", "antimatter_electric_wrenches",
                resolveStack(electric_wrench_lv, Material.NULL, Aluminium, 0, 100000),
                ImmutableMap.<Character, Object>builder()
                        .put('W', PropertyIngredient.of(WRENCHBIT, "primary"))
                        .put('S', AntimatterDefaultTools.SCREWDRIVER.getTag())
                        .put('b', PropertyIngredient.builder("battery").itemTags(BATTERIES_SMALL).build())
                        .put('P', PropertyIngredient.builder("secondary").mats(Aluminium).types(PLATE).build())
                        .put('s', SCREW.getMaterialTag(Steel))
                        .put('G', GEAR_SMALL.getMaterialTag(Aluminium))
                        .put('M', GTCoreItems.MotorLV).build(), "sWS", "GMG", "PbP");
        provider.addToolRecipe(ToolTypes.POWERED_TOOL_BUILDER.get(ELECTRIC_WRENCH.getId() + "-mv"),output, GT5RRef.ID, ELECTRIC_WRENCH.getId() + "_mv_" + "recipe", "antimatter_electric_wrenches",
                resolveStack(electric_wrench_mv, Material.NULL, StainlessSteel, 0, 200000),
                ImmutableMap.<Character, Object>builder()
                        .put('W', PropertyIngredient.of(WRENCHBIT, "primary"))
                        .put('S', AntimatterDefaultTools.SCREWDRIVER.getTag())
                        .put('b', PropertyIngredient.builder("battery").itemTags(BATTERIES_MEDIUM).build())
                        .put('P', PropertyIngredient.builder("secondary").mats(StainlessSteel).types(PLATE).build())
                        .put('s', SCREW.getMaterialTag(Steel))
                        .put('G', GEAR_SMALL.getMaterialTag(StainlessSteel))
                        .put('M', GTCoreItems.MotorMV).build(), "sWS", "GMG", "PbP");
        provider.addToolRecipe(ToolTypes.POWERED_TOOL_BUILDER.get(ELECTRIC_WRENCH.getId() + "-hv"),output, GT5RRef.ID, ELECTRIC_WRENCH.getId() + "_hv_" + "recipe", "antimatter_electric_wrenches",
                resolveStack(electric_wrench_hv, Material.NULL, Titanium, 0, 800000),
                ImmutableMap.<Character, Object>builder()
                        .put('W', PropertyIngredient.of(WRENCHBIT, "primary"))
                        .put('S', AntimatterDefaultTools.SCREWDRIVER.getTag())
                        .put('b', PropertyIngredient.builder("battery").itemTags(BATTERIES_LARGE).build())
                        .put('P', PropertyIngredient.builder("secondary").mats(Titanium).types(PLATE).build())
                        .put('s', SCREW.getMaterialTag(Steel))
                        .put('G', GEAR_SMALL.getMaterialTag(Titanium))
                        .put('M', GTCoreItems.MotorHV).build(), "sWS", "GMG", "PbP");
        provider.addToolRecipe(ToolTypes.UNIT_POWERED_TOOL_BUILDER.get(ELECTRIC_WRENCH.getId() + "-lv"),output, GT5RRef.ID, ELECTRIC_WRENCH.getId() + "_lv_power_unit_" + "recipe", "electric_wrenches",
                resolveStack(electric_wrench_lv, Material.NULL, Aluminium, 0, 100000), of('B', PropertyIngredient.of(AntimatterMaterialTypes.WRENCHBIT, "primary"), 'S', AntimatterDefaultTools.SCREWDRIVER.getTag(), 'P', PropertyIngredient.builder("secondary").itemTags(POWER_UNIT_LV).build()), "BS", "P ");
        provider.addToolRecipe(ToolTypes.UNIT_POWERED_TOOL_BUILDER.get(ELECTRIC_WRENCH.getId() + "-mv"),output, GT5RRef.ID, ELECTRIC_WRENCH.getId() + "_mv_power_unit_" + "recipe", "electric_wrenches",
                resolveStack(electric_wrench_mv, Material.NULL, StainlessSteel, 0, 100000), of('B', PropertyIngredient.of(AntimatterMaterialTypes.WRENCHBIT, "primary"), 'S', AntimatterDefaultTools.SCREWDRIVER.getTag(), 'P', PropertyIngredient.builder("secondary").itemTags(POWER_UNIT_MV).build()), "BS", "P ");
        provider.addToolRecipe(ToolTypes.UNIT_POWERED_TOOL_BUILDER.get(ELECTRIC_WRENCH.getId() + "-hv"),output, GT5RRef.ID, ELECTRIC_WRENCH.getId() + "_hv_power_unit_" + "recipe", "electric_wrenches",
                resolveStack(electric_wrench_hv, Material.NULL, Titanium, 0, 100000), of('B', PropertyIngredient.of(AntimatterMaterialTypes.WRENCHBIT, "primary"), 'S', AntimatterDefaultTools.SCREWDRIVER.getTag(), 'P', PropertyIngredient.builder("secondary").itemTags(POWER_UNIT_HV).build()), "BS", "P ");


        IAntimatterTool buzzsaw_lv = AntimatterAPI.get(IAntimatterTool.class, "buzzsaw_lv");
        IAntimatterTool buzzsaw_mv = AntimatterAPI.get(IAntimatterTool.class, "buzzsaw_mv");
        IAntimatterTool buzzsaw_hv = AntimatterAPI.get(IAntimatterTool.class, "buzzsaw_hv");
        provider.addToolRecipe(ToolTypes.POWERED_TOOL_BUILDER.get(BUZZSAW.getId() + "-lv"),output, GT5RRef.ID, "", "antimatter_buzzsaws",
                resolveStack(buzzsaw_lv, Material.NULL, Aluminium, 0, 100000),
                ImmutableMap.<Character, Object>builder()
                        .put('B', PropertyIngredient.of(BUZZSAW_BLADE, "primary"))
                        .put('S', AntimatterDefaultTools.SCREWDRIVER.getTag())
                        .put('b', PropertyIngredient.builder("battery").itemTags(BATTERIES_SMALL).build())
                        .put('P', PropertyIngredient.builder("secondary").mats(Aluminium).types(PLATE).build())
                        .put('s', SCREW.getMaterialTag(Steel))
                        .put('G', GEAR_SMALL.getMaterialTag(Aluminium))
                        .put('M', GTCoreItems.MotorLV).build(), "PbM", "SBG", "sGP");
        provider.addToolRecipe(ToolTypes.POWERED_TOOL_BUILDER.get(BUZZSAW.getId() + "-mv"),output, GT5RRef.ID, "", "antimatter_buzzsaws",
                resolveStack(buzzsaw_mv, Material.NULL, StainlessSteel, 0, 200000),
                ImmutableMap.<Character, Object>builder()
                        .put('B', PropertyIngredient.of(BUZZSAW_BLADE, "primary"))
                        .put('S', AntimatterDefaultTools.SCREWDRIVER.getTag())
                        .put('b', PropertyIngredient.builder("battery").itemTags(BATTERIES_MEDIUM).build())
                        .put('P', PropertyIngredient.builder("secondary").mats(StainlessSteel).types(PLATE).build())
                        .put('s', SCREW.getMaterialTag(Steel))
                        .put('G', GEAR_SMALL.getMaterialTag(StainlessSteel))
                        .put('M', GTCoreItems.MotorMV).build(), "PbM", "SBG", "sGP");
        provider.addToolRecipe(ToolTypes.POWERED_TOOL_BUILDER.get(BUZZSAW.getId() + "-hv"),output, GT5RRef.ID, "", "antimatter_buzzsaws",
                resolveStack(buzzsaw_hv, Material.NULL, Titanium, 0, 800000),
                ImmutableMap.<Character, Object>builder()
                        .put('B', PropertyIngredient.of(BUZZSAW_BLADE, "primary"))
                        .put('S', AntimatterDefaultTools.SCREWDRIVER.getTag())
                        .put('b', PropertyIngredient.builder("battery").itemTags(BATTERIES_LARGE).build())
                        .put('P', PropertyIngredient.builder("secondary").mats(Titanium).types(PLATE).build())
                        .put('s', SCREW.getMaterialTag(Steel))
                        .put('G', GEAR_SMALL.getMaterialTag(Titanium))
                        .put('M', GTCoreItems.MotorHV).build(), "PbM", "SBG", "sGP");
        provider.addToolRecipe(ToolTypes.UNIT_POWERED_TOOL_BUILDER.get(BUZZSAW.getId() + "-lv"),output, GT5RRef.ID, BUZZSAW.getId() + "_lv_power_unit_" + "recipe", "antimatter_buzzsaws",
                resolveStack(buzzsaw_lv, Material.NULL, Aluminium, 0, 100000), of('B', PropertyIngredient.of(BUZZSAW_BLADE, "primary"), 'S', AntimatterDefaultTools.SCREWDRIVER.getTag(), 'P', PropertyIngredient.builder("secondary").itemTags(POWER_UNIT_LV).build()), "PS", "B ");
        provider.addToolRecipe(ToolTypes.UNIT_POWERED_TOOL_BUILDER.get(BUZZSAW.getId() + "-mv"),output, GT5RRef.ID, BUZZSAW.getId() + "_mv_power_unit_" + "recipe", "antimatter_buzzsaws",
                resolveStack(buzzsaw_mv, Material.NULL, StainlessSteel, 0, 100000), of('B', PropertyIngredient.of(BUZZSAW_BLADE, "primary"), 'S', AntimatterDefaultTools.SCREWDRIVER.getTag(), 'P', PropertyIngredient.builder("secondary").itemTags(POWER_UNIT_MV).build()), "PS", "B ");
        provider.addToolRecipe(ToolTypes.UNIT_POWERED_TOOL_BUILDER.get(BUZZSAW.getId() + "-hv"),output, GT5RRef.ID, BUZZSAW.getId() + "_hv_power_unit_" + "recipe", "antimatter_buzzsaws",
                resolveStack(buzzsaw_hv, Material.NULL, Titanium, 0, 100000), of('B', PropertyIngredient.of(BUZZSAW_BLADE, "primary"), 'S', AntimatterDefaultTools.SCREWDRIVER.getTag(), 'P', PropertyIngredient.builder("secondary").itemTags(POWER_UNIT_HV).build()), "PS", "B ");

        IAntimatterTool electric_screwdriver_lv = AntimatterAPI.get(IAntimatterTool.class, "electric_screwdriver_lv");

        provider.addToolRecipe(ToolTypes.POWERED_TOOL_BUILDER.get(ELECTRIC_SCREWDRIVER.getId() + "-lv"), output, GT5RRef.ID, "electric_screwdriver_lv", "antimatter_electric_screwdrivers",
                electric_screwdriver_lv.resolveStack(Material.NULL, Aluminium, 0, 100000),
                ImmutableMap.<Character, Object>builder()
                        .put('R', PropertyIngredient.builder("primary").types(ROD_LONG).tool(ELECTRIC_SCREWDRIVER, true).build())
                        .put('b', PropertyIngredient.builder("battery").itemTags(BATTERIES_SMALL).build())
                        .put('M', GTCoreItems.MotorLV)
                        .put('P', PropertyIngredient.builder("secondary").mats(Aluminium).types(PLATE).build())
                        .put('S', SCREW.getMaterialTag(Steel))
                        .put('G', GEAR_SMALL.getMaterialTag(Aluminium))
                        .put('s', SCREWDRIVER.getTag()).build(), "PsR", "MGS", "GbP");
        provider.addToolRecipe(ToolTypes.UNIT_POWERED_TOOL_BUILDER.get(ELECTRIC_SCREWDRIVER.getId() + "-lv"), output, GT5RRef.ID, ELECTRIC_SCREWDRIVER.getId() + "_power_unit_lv", "antimatter_electric_screwdrivers",
                electric_screwdriver_lv.resolveStack(Material.NULL, Aluminium, 0, 100000), of('R', PropertyIngredient.builder("primary").types(ROD_LONG).tool(ELECTRIC_SCREWDRIVER, true).build(),'S', PropertyIngredient.builder("secondary").itemTags(POWER_UNIT_SMALL).build(), 'W', WRENCH.getTag()), "R ", "WS");

        IAntimatterTool jackhammer_lv = AntimatterAPI.get(IAntimatterTool.class, "jackhammer_hv");
        provider.addToolRecipe(ToolTypes.POWERED_TOOL_BUILDER.get(JACKHAMMER.getId() + "-hv"), output, GT5RRef.ID, "jackhammer_hv", "antimatter_jackhammers",
                jackhammer_lv.resolveStack(Material.NULL, Titanium, 0, 100000),
                ImmutableMap.<Character, Object>builder()
                        .put('R', PropertyIngredient.builder("primary").types(ROD_LONG).tool(JACKHAMMER, true).build())
                        .put('b', PropertyIngredient.builder("battery").itemTags(BATTERIES_LARGE).build())
                        .put('E', GT5RItems.PistonHV)
                        .put('S', SCREW.getMaterialTag(Steel))
                        .put('P', PropertyIngredient.builder("secondary").mats(Titanium).types(PLATE).build())
                        .put('T', SPRING.getMaterialTag(Titanium))
                        .put('s', SCREWDRIVER.getTag()).build(), "SRs", "PTP", "EPb");
        provider.addToolRecipe(ToolTypes.UNIT_POWERED_TOOL_BUILDER.get(JACKHAMMER.getId() + "-hv"), output, GT5RRef.ID, JACKHAMMER.getId() + "_hv_from_pu", "antimatter_jackhammers",
                jackhammer_lv.resolveStack(Material.NULL, Titanium, 0, 100000), of('R', PropertyIngredient.builder("primary").types(ROD_LONG).tool(JACKHAMMER, true).build(), 'b', PropertyIngredient.builder("secondary").itemTags(POWER_UNIT_JACKHAMMER).build(), 'S', SCREWDRIVER.getTag()), "RS", "b ");
    }

    public static ItemStack resolveStack(IAntimatterTool tool, Material primary, Material secondary, long startingEnergy, long maxEnergy) {
        ItemStack stack = new ItemStack(tool.getItem());
        tool.validateTag(stack, primary, secondary, startingEnergy, maxEnergy);
        if (!primary.has(TOOLS)) return stack;
        Map<Enchantment, Integer> mainEnchants = TOOLS.get(primary).toolEnchantment();
        if (!mainEnchants.isEmpty()) {
            mainEnchants.entrySet().stream().filter(e -> e.getKey().canEnchant(stack)).forEach(e -> stack.enchant(e.getKey(), e.getValue()));
        }
        return stack;
    }
}
