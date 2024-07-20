package muramasa.gregtech.datagen;

import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.util.Utils;
import muramasa.gregtech.GTIRef;
import muramasa.gregtech.data.Machines;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

import static muramasa.antimatter.data.AntimatterDefaultTools.*;
import static muramasa.antimatter.data.AntimatterMaterialTypes.DUST;
import static muramasa.antimatter.data.AntimatterMaterialTypes.RAW_ORE;
import static muramasa.antimatter.data.AntimatterMaterials.*;
import static muramasa.antimatter.datagen.providers.AntimatterAdvancementProvider.*;
import static muramasa.antimatter.machine.Tier.BRONZE;
import static muramasa.antimatter.machine.Tier.STEEL;
import static muramasa.antimatter.util.TagUtils.getForgelikeItemTag;
import static muramasa.antimatter.util.Utils.hasItem;
import static muramasa.gregtech.data.Materials.Bronze;
import static muramasa.gregtech.data.Materials.Tin;

public class ProgressionAdvancements implements Consumer<Consumer<Advancement>> {

    public static Advancement progressionRoot;

    @Override
    public void accept(Consumer<Advancement> consumer) {
        progressionRoot = buildRootAdvancement(AntimatterMaterialTypes.ROCK.get(Stone), new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"),
                GTIRef.ID + ".advancements.rock.title", GTIRef.ID + ".advancements.rock.desc", FrameType.TASK, true, true, false)
                .addCriterion("has_rocks", hasItem(getForgelikeItemTag("rocks"))).save(consumer, getLoc(GTIRef.ID, "progression/root"));
        Item pickaxe = PICKAXE.getToolItem(Flint);
        Advancement flintPick = buildBasicAdvancement(progressionRoot, pickaxe, "flint_pickaxe", FrameType.TASK)
                .addCriterion("has_flint_pick", hasItem(pickaxe)).save(consumer, getLoc(GTIRef.ID, "progression/flint_pickaxe"));
        Advancement rawCopper = buildBasicAdvancement(flintPick, RAW_ORE.get(Copper), "raw_copper", FrameType.TASK)
                .addCriterion("has_raw_copper", hasItem(RAW_ORE.getMaterialTag(Copper))).save(consumer, getLoc(GTIRef.ID, "progression/raw_copper"));
        Advancement rawTin = buildBasicAdvancement(rawCopper, RAW_ORE.get(Tin), "raw_tin", FrameType.TASK)
                .addCriterion("has_raw_tin", hasItem(RAW_ORE.getMaterialTag(Tin))).save(consumer, getLoc(GTIRef.ID, "progression/raw_tin"));
        Advancement mortar = buildBasicAdvancement(rawTin, MORTAR.getToolItem(Flint), "mortar", FrameType.TASK)
                .addCriterion("has_mortar", hasItem(MORTAR.getTag())).save(consumer, getLoc(GTIRef.ID, "progression/mortar"));
        Advancement bronzeDust = buildBasicAdvancement(mortar, DUST.get(Bronze), "bronze_dust", FrameType.TASK)
                .addCriterion("has_raw_copper", hasItem(DUST.getMaterialTag(Bronze))).save(consumer, getLoc(GTIRef.ID, "progression/bronze_dust"));
        Advancement hammer = buildBasicAdvancement(bronzeDust, HAMMER.getToolItem(Bronze), "hammer", FrameType.TASK)
                .addCriterion("has_hammer", hasItem(HAMMER.getTag())).save(consumer, getLoc(GTIRef.ID, "progression/hammer"));
        Advancement bronzeSmallBoiler = buildBasicAdvancement(hammer, Machines.SOLID_FUEL_BOILER.getItem(BRONZE), "solid_fuel_boiler", FrameType.TASK)
                .addCriterion("has_solid_fuel_boilers", hasItem(ItemPredicate.Builder.item().of(Machines.SOLID_FUEL_BOILER.getItem(BRONZE), Machines.SOLID_FUEL_BOILER.getItem(STEEL)).build()))
                .save(consumer, getLoc(GTIRef.ID, "progression/bronze_small_boiler"));

    }

    public static Advancement.Builder buildBasicAdvancement(Advancement parent, ItemLike provider, String langComponent, FrameType type) {
        return buildAdvancement(parent, provider, GTIRef.ID + ".advancements." + langComponent + ".title", GTIRef.ID + ".advancements." + langComponent + ".desc", type, true, true, false);
    }

}
