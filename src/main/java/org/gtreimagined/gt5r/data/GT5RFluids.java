package org.gtreimagined.gt5r.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidAttributes;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtcore.data.GTCoreFluids;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.fluid.GTFluid;

public class GT5RFluids {

    public static final GTFluid CHARGED_MATTER = GTAPI.register(GTFluid.class, new GTFluid(GT5Reimagined.ID, "charged_matter", prepareAttributes("charged_matter"), prepareProperties()));
    public static final GTFluid NEUTRAL_MATTER = GTAPI.register(GTFluid.class, new GTFluid(GT5Reimagined.ID, "neutral_matter", prepareAttributes("neutral_matter"), prepareProperties()));

    private static FluidAttributes.Builder prepareAttributes(String fluid) {
        return FluidAttributes.builder(new ResourceLocation(GT5Reimagined.ID, "block/fluid/" + fluid), new ResourceLocation(GT5Reimagined.ID, "block/fluid/" + fluid)).overlay(GTFluid.OVERLAY_TEXTURE).sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY);
    }

    private static BlockBehaviour.Properties prepareProperties() {
        return Properties.of(Material.WATER).strength(100.0F).noDrops();
    }

    public static void init(){
    }
}
