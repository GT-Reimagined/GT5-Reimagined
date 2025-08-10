package org.gtreimagined.gt5r.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.cover.CoverAirVent;
import org.gtreimagined.gt5r.cover.CoverConveyor;
import org.gtreimagined.gt5r.cover.CoverDrain;
import org.gtreimagined.gt5r.cover.CoverDynamoColored;
import org.gtreimagined.gt5r.cover.CoverEnergyColored;
import org.gtreimagined.gt5r.cover.CoverEnergyDetector;
import org.gtreimagined.gt5r.cover.CoverExtenderOutput;
import org.gtreimagined.gt5r.cover.CoverFluidDetector;
import org.gtreimagined.gt5r.cover.CoverFluidFilter;
import org.gtreimagined.gt5r.cover.CoverFluidRegulator;
import org.gtreimagined.gt5r.cover.CoverItemDetector;
import org.gtreimagined.gt5r.cover.CoverItemFilter;
import org.gtreimagined.gt5r.cover.CoverItemRegulator;
import org.gtreimagined.gt5r.cover.CoverItemRetriever;
import org.gtreimagined.gt5r.cover.CoverMuffler;
import org.gtreimagined.gt5r.cover.CoverPump;
import org.gtreimagined.gt5r.cover.CoverReactorOutput;
import org.gtreimagined.gt5r.cover.CoverReactorOutputSecondary;
import org.gtreimagined.gt5r.cover.CoverRobotArm;
import org.gtreimagined.gt5r.cover.CoverSecondaryOutput;
import org.gtreimagined.gt5r.cover.CoverShutter;
import org.gtreimagined.gt5r.cover.redstone.CoverActivityDetectorPossible;
import org.gtreimagined.gt5r.cover.redstone.CoverActivityDetectorProcessing;
import org.gtreimagined.gt5r.cover.redstone.CoverActivityDetectorSuccessful;
import org.gtreimagined.gt5r.cover.redstone.CoverNeedsMaintenance;
import org.gtreimagined.gt5r.cover.redstone.CoverProgressSensor;
import org.gtreimagined.gt5r.cover.redstone.CoverRedstoneConductorAccept;
import org.gtreimagined.gt5r.cover.redstone.CoverRedstoneConductorEmit;
import org.gtreimagined.gt5r.cover.redstone.CoverRedstoneMachineController;
import org.gtreimagined.gt5r.items.ItemCoverCustomTooltip;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.blockentity.pipe.BlockEntityItemPipe;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.item.ItemCover;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.texture.Texture;
import org.gtreimagined.gtlib.util.FluidUtils;

public class GT5RCovers {
    public static final CoverFactory COVER_CONVEYOR = CoverFactory.builder(CoverConveyor::new).gui().item((a, b) ->
            new ItemCover(a.getDomain(), a.getId(), b).tip(String.format("1 Stack every %dt(%ss) (as Cover)", CoverConveyor.speeds.get(b), (float)CoverConveyor.speeds.get(b) / 20))
    ).addTextures(new Texture(GT5Reimagined.ID, "block/cover/conveyor")).setTiers(Tier.getStandardWithIV()).build(GT5Reimagined.ID, "conveyor");
    public static final CoverFactory COVER_ITEM_REGULATOR = CoverFactory.builder(CoverItemRegulator::new).gui().item((a, b) ->
            new ItemCover(a.getDomain(), a.getId(), b).tip(String.format("1 Stack every %dt(%ss), with configurable stack size limits (as Cover)", CoverConveyor.speeds.get(b), (float)CoverConveyor.speeds.get(b) / 20))
    ).addTextures(new Texture(GT5Reimagined.ID, "block/cover/conveyor")).setTiers(Tier.getStandardWithIV()).build(GT5Reimagined.ID, "item_regulator");
    public static final CoverFactory COVER_ITEM_RETRIEVER = CoverFactory.builder(CoverItemRetriever::new).item((a, b) -> {
        return new ItemCoverCustomTooltip(GT5Reimagined.ID, "item_retriever", (stack, world, tooltip, flag) -> {
            CompoundTag nbt = stack.getTag();
            if (nbt != null && nbt.contains("coverInventories")){
                CompoundTag coverInventories = nbt.getCompound("coverInventories");
                if (coverInventories.contains("display_settable")){
                    CompoundTag displayManager = coverInventories.getCompound("display_settable");
                    if (displayManager.contains("Items")){
                        ListTag items = displayManager.getList("Items", Tag.TAG_COMPOUND);
                        if (!items.isEmpty()){
                            ItemStack contained = ItemStack.of(items.getCompound(0));
                            if (!contained.isEmpty()){
                                tooltip.add(contained.getHoverName());
                            }
                        }
                    }
                }
            }
        }).tip("Can be placed as cover");
    }).addTextures(new Texture(GT5Reimagined.ID, "block/cover/item_retriever")).setIsValid(b -> b instanceof BlockEntityItemPipe<?>).gui().build(GT5Reimagined.ID, "item_retriever");
    public static final CoverFactory COVER_PUMP = CoverFactory.builder(CoverPump::new).gui().item((a, b) ->
            new ItemCover(a.getDomain(), a.getId(), b).tip(String.format("%d L/t (as Cover)", CoverPump.speeds.get(b))))
            .addTextures(new Texture(GT5Reimagined.ID, "block/cover/pump")).setTiers(Tier.getStandardWithIV()).build(GT5Reimagined.ID, "pump");
    public static final CoverFactory COVER_FLUID_REGULATOR = CoverFactory.builder(CoverFluidRegulator::new).gui().item((a, b) ->
                    new ItemCover(a.getDomain(), a.getId(), b).tip(String.format("Configurable up to %d L/t (as Cover)", CoverPump.speeds.get(b))))
            .addTextures(new Texture(GT5Reimagined.ID, "block/cover/pump")).setTiers(Tier.getStandardWithIV()).build(GT5Reimagined.ID, "fluid_regulator");
    public static final CoverFactory COVER_ROBOT_ARM = CoverFactory.builder(CoverRobotArm::new).gui().item((a, b) ->
                    new ItemCover(a.getDomain(), a.getId(), b).tip(String.format("1 Stack every %dt(%ss) (as Cover)", CoverConveyor.speeds.get(b), (float)CoverConveyor.speeds.get(b) / 20)))
            .addTextures(new Texture(GT5Reimagined.ID, "block/cover/conveyor")).setTiers(Tier.getStandardWithIV()).build(GT5Reimagined.ID, "robot_arm");
    public static final CoverFactory COVER_DRAIN = CoverFactory.builder(CoverDrain::new).item((a, b) ->
            new ItemCover(GT5Reimagined.ID, "drain").tip("Can be placed on machines/pipes as a cover")).addTextures(new Texture(GT5Reimagined.ID, "block/cover/drain")).build(GT5Reimagined.ID, "drain");
    public static final CoverFactory COVER_AIR_VENT = CoverFactory.builder(CoverAirVent::new).item((a, b) ->
            new ItemCover(GT5Reimagined.ID, "air_vent").tip("Can be placed on machines/pipes as a cover")).addTextures(new Texture(GT5Reimagined.ID, "block/cover/air_vent")).build(GT5Reimagined.ID, "air_vent");
    public static final CoverFactory COVER_ITEM_FILTER = CoverFactory.builder(CoverItemFilter::new).item((a, b) ->
            new ItemCoverCustomTooltip(GT5Reimagined.ID, "item_filter", (stack, world, tooltip, flag) -> {
                CompoundTag nbt = stack.getTag();
                if (nbt != null && nbt.contains("coverInventories")){
                    CompoundTag coverInventories = nbt.getCompound("coverInventories");
                    if (coverInventories.contains("display_settable")){
                        CompoundTag displayManager = coverInventories.getCompound("display_settable");
                        if (displayManager.contains("Items")){
                            ListTag items = displayManager.getList("Items", Tag.TAG_COMPOUND);
                            if (!items.isEmpty()){
                                ItemStack contained = ItemStack.of(items.getCompound(0));
                                if (!contained.isEmpty()){
                                    tooltip.add(contained.getHoverName());
                                }
                            }
                        }
                    }
                }
            }).tip("Can be placed as cover")).addTextures(new Texture(GT5Reimagined.ID, "block/cover/item_filter"), new Texture(GT5Reimagined.ID, "block/cover/item_filter_inverted")).gui().build(GT5Reimagined.ID, "item_filter");
    public static final CoverFactory COVER_FLUID_FILTER = CoverFactory.builder(CoverFluidFilter::new).item((a, b) ->
            new ItemCoverCustomTooltip(GT5Reimagined.ID, "fluid_filter", (stack, world, tooltip, flag) -> {
                CompoundTag nbt = stack.getTag();
                if (nbt != null && nbt.contains("coverInventories")){
                    CompoundTag coverInventories = nbt.getCompound("coverInventories");
                    if (coverInventories.contains("fluid_display_settable")){
                        CompoundTag displayManager = coverInventories.getCompound("fluid_display_settable");
                        if (displayManager.contains("Items")){
                            ListTag items = displayManager.getList("Items", Tag.TAG_COMPOUND);
                            if (!items.isEmpty()){
                                ItemStack contained = ItemStack.of(items.getCompound(0));
                                IFluidHandlerItem fluidItemHandler = contained.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElse(null);
                                if (fluidItemHandler != null && !fluidItemHandler.getFluidInTank(0).isEmpty()){
                                    tooltip.add(FluidUtils.getFluidDisplayName(fluidItemHandler.getFluidInTank(0)));
                                }
                            }
                        }
                    }
                }
            }).tip("Can be placed as cover")).addTextures(new Texture(GT5Reimagined.ID, "block/cover/fluid_filter"),new Texture(GT5Reimagined.ID, "block/cover/fluid_filter_inverted")).gui().build(GT5Reimagined.ID, "fluid_filter");
    public static final CoverFactory COVER_REDSTONE_MACHINE_CONTROLLER = CoverFactory.builder(CoverRedstoneMachineController::new).item((a, b) -> {
        return new ItemCover(GT5Reimagined.ID, "redstone_machine_controller");
    }).addTextures(new Texture(GT5Reimagined.ID, "block/cover/redstone_machine_controller")).build(GT5Reimagined.ID, "redstone_machine_controller");
    public static final CoverFactory COVER_REDSTONE_CONDUCTOR_ACCEPT = CoverFactory.builder(CoverRedstoneConductorAccept::new).item((a, b) -> {
        return new ItemCover(GT5Reimagined.ID, "redstone_conductor_accept");
    }).addTextures(new Texture(GT5Reimagined.ID, "block/cover/redstone_conductor_accept")).build(GT5Reimagined.ID, "redstone_conductor_accept");
    public static final CoverFactory COVER_REDSTONE_CONDUCTOR_EMIT = CoverFactory.builder(CoverRedstoneConductorEmit::new).item((a, b) -> {
        return new ItemCover(GT5Reimagined.ID, "redstone_conductor_emit");
    }).addTextures(new Texture(GT5Reimagined.ID, "block/cover/redstone_conductor_emit")).build(GT5Reimagined.ID, "redstone_conductor_emit");
    public static final CoverFactory COVER_NEEDS_MAINTENANCE_COVER = CoverFactory.builder(CoverNeedsMaintenance::new).item((a, b) -> {
        return new ItemCover(GT5Reimagined.ID, "needs_maintenance_cover");
    }).addTextures(new Texture(GT5Reimagined.ID, "block/cover/needs_maintenance_cover")).build(GT5Reimagined.ID, "needs_maintenance_cover");
    public static final CoverFactory COVER_PROGRESS_SENSOR = CoverFactory.builder(CoverProgressSensor::new).item((a, b) -> {
        return new ItemCover(GT5Reimagined.ID, "progress_sensor");
    }).addTextures(new Texture(GT5Reimagined.ID, "block/cover/progress_sensor")).build(GT5Reimagined.ID, "progress_sensor");
    public static final CoverFactory COVER_ACTIVITY_DETECTOR_PROCESSING = CoverFactory.builder(CoverActivityDetectorProcessing::new).item((a, b) -> {
        return new ItemCover(GT5Reimagined.ID, "activity_detector_processing");
    }).addTextures(new Texture(GT5Reimagined.ID, "block/cover/activity_detector_processing")).build(GT5Reimagined.ID, "activity_detector_processing");
    public static final CoverFactory COVER_ACTIVITY_DETECTOR_SUCCESSFUL = CoverFactory.builder(CoverActivityDetectorSuccessful::new).item((a, b) -> {
        return new ItemCover(GT5Reimagined.ID, "activity_detector_successful");
    }).addTextures(new Texture(GT5Reimagined.ID, "block/cover/activity_detector_successful")).build(GT5Reimagined.ID, "activity_detector_successful");
    public static final CoverFactory COVER_ACTIVITY_DETECTOR_POSSIBLE = CoverFactory.builder(CoverActivityDetectorPossible::new).item((a, b) -> {
        return new ItemCover(GT5Reimagined.ID, "activity_detector_possible");
    }).addTextures(new Texture(GT5Reimagined.ID, "block/cover/activity_detector_possible")).build(GT5Reimagined.ID, "activity_detector_possible");
    public static final CoverFactory COVER_ENERGY_DETECTOR = CoverFactory.builder(CoverEnergyDetector::new).item((a, b) -> {
        return new ItemCover(GT5Reimagined.ID, "energy_detector");
    }).addTextures(new Texture(GT5Reimagined.ID, "block/cover/energy_detector")).build(GT5Reimagined.ID, "energy_detector");
    public static final CoverFactory COVER_FLUID_DETECTOR = CoverFactory.builder(CoverFluidDetector::new).item((a, b) -> {
        return new ItemCover(GT5Reimagined.ID, "fluid_detector");
    }).addTextures(new Texture(GT5Reimagined.ID, "block/cover/fluid_detector")).build(GT5Reimagined.ID, "fluid_detector");
    public static final CoverFactory COVER_ITEM_DETECTOR = CoverFactory.builder(CoverItemDetector::new).gui().item((a, b) -> {
        return new ItemCover(GT5Reimagined.ID, "item_detector");
    }).addTextures(new Texture(GT5Reimagined.ID, "block/cover/item_detector")).build(GT5Reimagined.ID, "item_detector");
    public static final CoverFactory COVER_REACTOR_OUTPUT = CoverFactory.builder(CoverReactorOutput::new)
            .addTextures(new Texture(GT5Reimagined.ID, "block/cover/reactor_output")).build(GT5Reimagined.ID, "reactor_output");
    public static final CoverFactory COVER_REACTOR_OUTPUT_SECONDARY = CoverFactory.builder(CoverReactorOutputSecondary::new)
            .addTextures(new Texture(GT5Reimagined.ID, "block/cover/reactor_output_secondary")).build(GT5Reimagined.ID, "reactor_output_secondary");
    public static final CoverFactory COVER_OUTPUT_SECONDARY = CoverFactory.builder(CoverSecondaryOutput::new)
            .addTextures(new Texture(GT5Reimagined.ID, "block/cover/output_secondary")).build(GT5Reimagined.ID, "output_secondary");
    public static final CoverFactory COVER_OUTPUT_EXTENDER = CoverFactory.builder(CoverExtenderOutput::new)
            .addTextures(new Texture(GT5Reimagined.ID, "block/cover/extender_output")).build(GT5Reimagined.ID, "extender_output");
    public static CoverFactory COVER_DYNAMO_COLORED = CoverFactory.builder(CoverDynamoColored::new).addTextures(new Texture(Ref.ID, "block/cover/dynamo")).build(GT5Reimagined.ID, "dynamo");
    public static CoverFactory COVER_ENERGY_COLORED = CoverFactory.builder(CoverEnergyColored::new).addTextures(new Texture(Ref.ID, "block/cover/energy")).build(GT5Reimagined.ID, "energy");
    public static CoverFactory COVER_SHUTTER = CoverFactory.builder(CoverShutter::new).item((a, b) ->
            new ItemCover(GT5Reimagined.ID, "shutter").tip("can be placed as a pipe cover")).addTextures(new Texture(GT5Reimagined.ID, "block/cover/shutter")).build(GT5Reimagined.ID, "shutter");
    public static CoverFactory COVER_MUFFLER = CoverFactory.builder(CoverMuffler::new).item((a, b) ->
            new ItemCover(GT5Reimagined.ID, "muffler").tip("Can be placed as machine cover")).addTextures(new Texture(GT5Reimagined.ID, "block/cover/muffler")).build(GT5Reimagined.ID, "muffler");

    public static void init(){

    }
}
