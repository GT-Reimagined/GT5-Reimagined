package org.gtreimagined.gt5r.integration.tfc.data;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.fluids.FluidAttributes;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.fluid.GTFluid;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;

import java.util.ArrayList;
import java.util.List;

public class TFCGTFluids {
    public static final List<GTFluid> FLUIDS = new ArrayList<>();
    public static final GTFluid INVAR = createFluid(Materials.Invar);

    public static void init(){

    }

    private static GTFluid createFluid(Material material) {
        GTFluid fluid = GTAPI.register(GTFluid.class, new GTFluid(GT5Reimagined.ID, material.getId(), prepareAttributes(material), prepareProperties()));
        FLUIDS.add(fluid);
        return fluid;
    }

    private static FluidAttributes.Builder prepareAttributes(Material fluid) {
        return FluidAttributes.builder(GTFluid.LIQUID_HOT_STILL_TEXTURE, GTFluid.LIQUID_HOT_FLOW_TEXTURE).overlay(GTFluid.OVERLAY_TEXTURE)
                .sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA).viscosity(6000).luminosity(15)
                .color(0xFF000000 | (fluid.getRGB() & 0x00FFFFFF))
                .temperature(MaterialTags.LIQUID_TEMPERATURE.getInt(fluid));
    }

    private static BlockBehaviour.Properties prepareProperties() {
        return Properties.of(net.minecraft.world.level.material.Material.WATER).strength(100.0F).noDrops();
    }
}
