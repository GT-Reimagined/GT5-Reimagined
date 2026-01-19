package org.gtreimagined.gt5r.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtcore.data.GTCoreFluids;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.fluid.GTClientFluidTypeExtension;
import org.gtreimagined.gtlib.fluid.GTFluid;

public class GT5RFluids {

    public static final GTFluid CHARGED_MATTER = GTAPI.register(GTFluid.class, new GTFluid(GT5Reimagined.ID, "charged_matter", prepareFluidProperties(), prepareProperties().mapColor(DyeColor.MAGENTA), prepareFluidExtension("charged_matter")));
    public static final GTFluid NEUTRAL_MATTER = GTAPI.register(GTFluid.class, new GTFluid(GT5Reimagined.ID, "neutral_matter", prepareFluidProperties(), prepareProperties().mapColor(DyeColor.YELLOW), prepareFluidExtension("neutral_matter")));

    private static FluidType.Properties prepareFluidProperties(){
        return FluidType.Properties.create().sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL);
    }

    private static IClientFluidTypeExtensions prepareFluidExtension(String fluid){
        return GTClientFluidTypeExtension.builder().stillTexture(new ResourceLocation(GT5Reimagined.ID, "block/fluid/" + fluid)).flowingTexture(new ResourceLocation(GT5Reimagined.ID, "block/fluid/" + fluid)).flowingTexture(new ResourceLocation(GT5Reimagined.ID, "block/fluid/" + fluid)).overlayTexture(GTFluid.OVERLAY_TEXTURE).build();
    }

    private static BlockBehaviour.Properties prepareProperties() {
        return Properties.of().liquid().replaceable().pushReaction(PushReaction.DESTROY).strength(100.0F).noLootTable();
    }

    public static void init(){
    }
}
