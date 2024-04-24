package muramasa.gregtech.datagen;

import io.github.gregtechintergalactical.gtcore.data.GTCoreItems;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.Ref;
import muramasa.antimatter.data.ForgeCTags;
import muramasa.antimatter.datagen.providers.AntimatterBlockTagProvider;
import muramasa.antimatter.datagen.providers.AntimatterItemTagProvider;
import muramasa.gregtech.data.GregTechItems;
import muramasa.gregtech.data.GregTechTags;
import net.minecraft.world.item.Items;

import static io.github.gregtechintergalactical.gtcore.data.GTCoreTags.*;
import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.data.AntimatterMaterials.*;
import static muramasa.gregtech.data.Materials.*;

public class GregTechItemTagProvider  extends AntimatterItemTagProvider {
    public GregTechItemTagProvider(String providerDomain, String providerName, boolean replace, AntimatterBlockTagProvider p) {
        super(providerDomain, providerName, replace, p);
    }

    @Override
    protected void processTags(String domain) {
        super.processTags(domain);
        //this.tag(GregTechTags.CIRCUITS_EXTREME).add(GregTechData.CircuitDataStorage);
        this.tag(CIRCUITS_ELITE).add(GregTechItems.NanoProcessor);
        this.tag(CIRCUITS_MASTER).add(GregTechItems.QuantumProcessor);
        this.tag(RESISTORS).add(GTCoreItems.Resistor, GTCoreItems.SMDResistor);
        this.tag(CAPACITORS).add(GTCoreItems.Capacitor, GTCoreItems.SMDCapacitor);
        this.tag(TRANSISTORS).add(GTCoreItems.Transistor, GTCoreItems.SMDTransistor);
        this.tag(DIODES).add(GTCoreItems.Diode, GTCoreItems.SMDDiode);
        this.tag(FIRESTARTER).add(Items.FLINT_AND_STEEL);
        this.tag(GEM.getMaterialTag(Amethyst)).remove(Items.AMETHYST_SHARD);
        this.tag(GEM.getTag()).remove(Items.AMETHYST_SHARD);
        this.tag(BLOCK.getMaterialTag(Amethyst)).remove(Items.AMETHYST_BLOCK);
        this.tag(PLATES_IRON_ALUMINIUM).addTag(PLATE.getMaterialTag(Iron)).addTag(PLATE.getMaterialTag(WroughtIron)).addTag(PLATE.getMaterialTag(Aluminium));
        this.tag(DUST_LAPIS_LAZURITE).addTag(DUST.getMaterialTag(Lapis)).addTag(DUST.getMaterialTag(Lazurite));
        this.tag(GregTechTags.GRIND_HEADS).add(GTCoreItems.DiamondGrindHead, GTCoreItems.TungstenGrindHead);
        this.tag(DUST_COALS).addTag(DUST.getMaterialTag(Coal)).addTag(DUST.getMaterialTag(Charcoal)).addTag(DUST.getMaterialTag(Carbon));
        this.tag(ForgeCTags.GEMS_QUARTZ_ALL).addTag(GEM.getMaterialTag(MilkyQuartz));
        this.tag(GregTechTags.GEM_SAPPHIRES).addTag(GEM.getMaterialTag(Sapphire)).addTag(GEM.getMaterialTag(GreenSapphire));
        this.tag(GregTechTags.DUST_SAPPHIRES).addTag(DUST.getMaterialTag(Sapphire)).addTag(DUST.getMaterialTag(GreenSapphire));
        this.tag(GregTechTags.DUST_SANDS).addTag(DUST.getMaterialTag(Sand)).addTag(DUST.getMaterialTag(RedSand));
        this.tag(GregTechTags.DUST_SIO).addTag(DUST.getMaterialTag(SiliconDioxide))
                .addTag(DUST.getMaterialTag(Quartzite)).addTag(DUST.getMaterialTag(Quartz))
                .addTag(DUST.getMaterialTag(Glass)).addTag(DUST.getMaterialTag(Flint))
                .addTag(DUST.getMaterialTag(MilkyQuartz));
        if (AntimatterAPI.isModLoaded(Ref.MOD_AE)){
            this.tag(GregTechTags.DUST_SIO).addTag(DUST.getMaterialTag(CertusQuartz));
        }
    }
}
