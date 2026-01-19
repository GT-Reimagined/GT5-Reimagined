package org.gtreimagined.gt5r.integration.tfc.data;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.fluid.GTClientFluidTypeExtension;
import org.gtreimagined.gtlib.fluid.GTFluid;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;

import java.util.ArrayList;
import java.util.List;

public class TFCGTFluids {
    public static final List<GTFluid> FLUIDS = new ArrayList<>();
    public static final GTFluid BERYLLIUM = createFluid(Materials.Beryllium);
    public static final GTFluid ALUMINIUM = createFluid(Materials.Aluminium);
    public static final GTFluid ANTIMONY = createFluid(Materials.Antimony);
    public static final GTFluid LEAD = createFluid(Materials.Lead);
    public static final GTFluid BATTERY_ALLOY = createFluid(Materials.BatteryAlloy);
    public static final GTFluid CUPRONICKEL = createFluid(Materials.Cupronickel);
    public static final GTFluid DAMASCUS_STEEL = createFluid(Materials.DamascusSteel);
    public static final GTFluid ELECTRUM = createFluid(Materials.Electrum);
    public static final GTFluid INVAR = createFluid(Materials.Invar);

    public static void init(){

    }

    private static GTFluid createFluid(Material material) {
        GTFluid fluid = GTAPI.register(GTFluid.class, new GTFluid(GT5Reimagined.ID, material.getId(), prepareFluidProperties(material), prepareProperties(), prepareFluidExtension(material)));
        FLUIDS.add(fluid);
        return fluid;
    }

    private static FluidType.Properties prepareFluidProperties(Material fluid) {
        return FluidType.Properties.create().sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .viscosity(6000).lightLevel(15).temperature(MaterialTags.LIQUID_TEMPERATURE.getInt(fluid));
    }

    private static IClientFluidTypeExtensions prepareFluidExtension(Material fluid) {
        return GTClientFluidTypeExtension.builder().stillTexture(GTFluid.LIQUID_HOT_STILL_TEXTURE).flowingTexture(GTFluid.LIQUID_HOT_FLOW_TEXTURE)
                .overlayTexture(GTFluid.OVERLAY_TEXTURE).tintColor(0xFF000000 | (fluid.getRGB() & 0x00FFFFFF)).build();
    }

    private static BlockBehaviour.Properties prepareProperties() {
        return Properties.of().liquid().mapColor(MapColor.FIRE).replaceable().pushReaction(PushReaction.DESTROY).strength(100.0F).noLootTable();
    }
}
