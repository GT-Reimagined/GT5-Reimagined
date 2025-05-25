package org.gtreimagined.gt5r.datagen;

import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.ForgeTags;
import org.gtreimagined.gtlib.datagen.providers.GTBlockTagProvider;
import org.gtreimagined.gtlib.datagen.providers.GTItemTagProvider;
import net.minecraft.world.item.Items;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.data.GT5RTags;
import org.gtreimagined.gtcore.data.GTCoreItems;

import static org.gtreimagined.gt5r.data.GT5RItems.*;
import static org.gtreimagined.gt5r.data.GT5RItems.DataOrb;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtcore.data.GTCoreTags.*;

public class GT5RItemTagProvider extends GTItemTagProvider {
    public GT5RItemTagProvider(String providerDomain, String providerName, boolean replace, GTBlockTagProvider p) {
        super(providerDomain, providerName, replace, p);
    }

    @Override
    protected void processTags(String domain) {
        super.processTags(domain);
        //this.tag(GT5RTags.CIRCUITS_EXTREME).add(GT5RData.CircuitDataStorage);
        this.tag(CIRCUITS_ELITE).add(GT5RItems.NanoProcessor);
        this.tag(CIRCUITS_MASTER).add(GT5RItems.QuantumProcessor);
        this.tag(GT5RTags.RESISTORS).add(GT5RItems.Resistor, GT5RItems.SMDResistor);
        this.tag(GT5RTags.CAPACITORS).add(GT5RItems.Capacitor, GT5RItems.SMDCapacitor);
        this.tag(GT5RTags.TRANSISTORS).add(GT5RItems.Transistor, GT5RItems.SMDTransistor);
        this.tag(GT5RTags.DIODES).add(GT5RItems.Diode, GT5RItems.SMDDiode);
        this.tag(FIRESTARTER).add(Items.FLINT_AND_STEEL);
        this.tag(BATTERIES_LV).add(GT5RItems.BatterySmallSodium, GT5RItems.BatterySmallCadmium, GT5RItems.BatterySmallLithium);
        this.tag(BATTERIES_MV).add(GT5RItems.BatteryMediumSodium, GT5RItems.BatteryMediumCadmium, GT5RItems.BatteryMediumLithium);
        this.tag(BATTERIES_HV).add(GT5RItems.BatteryLargeSodium, GT5RItems.BatteryLargeCadmium, GT5RItems.BatteryLargeLithium, GT5RItems.EnergyCrystal);
        this.tag(GEM.getMaterialTag(Amethyst)).remove(Items.AMETHYST_SHARD);
        this.tag(GEM.getTag()).remove(Items.AMETHYST_SHARD);
        this.tag(BLOCK.getMaterialTag(Amethyst)).remove(Items.AMETHYST_BLOCK);
        this.tag(PLATES_IRON_ALUMINIUM).addTag(PLATE.getMaterialTag(Iron)).addTag(PLATE.getMaterialTag(WroughtIron)).addTag(PLATE.getMaterialTag(Aluminium));
        this.tag(DUST_LAPIS_LAZURITE).addTag(DUST.getMaterialTag(Lapis)).addTag(DUST.getMaterialTag(Lazurite));
        this.tag(GT5RTags.GRIND_HEADS).add(GTCoreItems.DiamondGrindHead, GTCoreItems.TungstenGrindHead);
        this.tag(DUST_COALS).addTag(DUST.getMaterialTag(Coal)).addTag(DUST.getMaterialTag(Charcoal)).addTag(DUST.getMaterialTag(Carbon));
        this.tag(ForgeTags.GEMS_QUARTZ_ALL).addTag(GEM.getMaterialTag(MilkyQuartz));
        this.tag(GT5RTags.GEM_SAPPHIRES).addTag(GEM.getMaterialTag(Sapphire)).addTag(GEM.getMaterialTag(GreenSapphire));
        this.tag(GT5RTags.DUST_SAPPHIRES).addTag(DUST.getMaterialTag(Sapphire)).addTag(DUST.getMaterialTag(GreenSapphire));
        this.tag(GT5RTags.DUST_SANDS).addTag(DUST.getMaterialTag(Sand)).addTag(DUST.getMaterialTag(RedSand));
        this.tag(GT5RTags.DUST_SIO).addTag(DUST.getMaterialTag(SiliconDioxide))
                .addTag(DUST.getMaterialTag(Quartzite)).addTag(DUST.getMaterialTag(Quartz))
                .addTag(DUST.getMaterialTag(Glass)).addTag(DUST.getMaterialTag(Flint))
                .addTag(DUST.getMaterialTag(MilkyQuartz));
        if (GTAPI.isModLoaded(Ref.MOD_AE)){
            this.tag(GT5RTags.DUST_SIO).addTag(DUST.getMaterialTag(CertusQuartz));
        }
        this.tag(CIRCUITS_BASIC).add(BasicCircuit);
        this.tag(CIRCUITS_GOOD).add(GoodCircuit);
        this.tag(CIRCUITS_ADVANCED).add(AdvancedCircuit);
        this.tag(CIRCUITS_COMPLEX).add(ComplexCircuit);
        this.tag(CIRCUITS_DATA).add(DataStorageCircuit);
        this.tag(CIRCUITS_ELITE).add(DataControlCircuit);
        this.tag(CIRCUITS_MASTER).add(EnergyFlowCircuit);
        this.tag(CIRCUITS_DATA_ORB).add(DataOrb);
        this.tag(ForgeTags.DYES_BLACK).add(Items.INK_SAC);
        this.tag(ForgeTags.DYES_BLUE).addTag(GEM.getMaterialTag(Lapis)).addTag(GEM.getMaterialTag(Sodalite));
        this.tag(ForgeTags.DYES_CYAN).addTag(GEM.getMaterialTag(Lazurite));
        this.tag(ForgeTags.DYES_GREEN).addTag(DUST.getMaterialTag(Malachite));
        this.tag(ForgeTags.DYES_WHITE).add(Items.BONE_MEAL);
    }
}
