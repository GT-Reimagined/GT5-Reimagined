package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gt5r.data.RecipeMaps;

import static org.gtreimagined.gt5r.data.GT5RItems.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gt5r.data.GT5RMaterialTypes.BOULE;
import static org.gtreimagined.gt5r.data.Materials.*;

public class LaserEngraverLoader {
    public static void init(){
        RecipeMaps.LASER_ENGRAVER.RB().ii(RecipeIngredient.of(LapotronCrystal, 1), LENS.getMaterialIngredient(Sapphire, 1).setNoConsume().setNoConsume()).io(new ItemStack(EngravedLapotronChip, 3)).add("engraved_lapotron_chip_sapphire", 256, 480);
        RecipeMaps.LASER_ENGRAVER.RB().ii(RecipeIngredient.of(LapotronCrystal, 1), LENS.getMaterialIngredient(BlueTopaz, 1).setNoConsume().setNoConsume()).io(new ItemStack(EngravedLapotronChip, 3)).add("engraved_lapotron_chip_blue_topaz", 256, 480);
        RecipeMaps.LASER_ENGRAVER.RB().ii(RecipeIngredient.of(LapotronCrystal, 1), LENS.getMaterialIngredient(Opal, 1).setNoConsume().setNoConsume()).io(new ItemStack(EngravedLapotronChip, 3)).add("engraved_lapotron_chip_opal", 256, 480);
        RecipeMaps.LASER_ENGRAVER.RB().ii(PLATE.getMaterialIngredient(Emerald, 1), LENS.getMaterialIngredient(Emerald, 1).setNoConsume()).io(new ItemStack(EngravedCrystalChip)).add("engraved_crystal_chip_emerald", 256, 480);
        RecipeMaps.LASER_ENGRAVER.RB().ii(PLATE.getMaterialIngredient(Olivine, 1), LENS.getMaterialIngredient(Emerald, 1).setNoConsume()).io(new ItemStack(EngravedCrystalChip)).add("engraved_crystal_chip_olivine", 256, 480);
        RecipeMaps.LASER_ENGRAVER.RB().ii(FOIL.getMaterialIngredient(Copper, 1), LENS.getMaterialIngredient(Ruby, 1).setNoConsume()).io(new ItemStack(CopperEtchedWiring)).add("copper_etched_wiring_1", 64, 30);
        RecipeMaps.LASER_ENGRAVER.RB().ii(FOIL.getMaterialIngredient(AnnealedCopper, 1), LENS.getMaterialIngredient(Ruby, 1).setNoConsume()).io(new ItemStack(CopperEtchedWiring)).add("copper_etched_wiring_2", 64, 30);
        RecipeMaps.LASER_ENGRAVER.RB().ii(FOIL.getMaterialIngredient(Copper, 1), LENS.getMaterialIngredient(RedGarnet, 1).setNoConsume()).io(new ItemStack(CopperEtchedWiring)).add("copper_etched_wiring_3", 64, 30);
        RecipeMaps.LASER_ENGRAVER.RB().ii(FOIL.getMaterialIngredient(AnnealedCopper, 1), LENS.getMaterialIngredient(RedGarnet, 1).setNoConsume()).io(new ItemStack(CopperEtchedWiring)).add("copper_etched_wiring_4", 64, 30);
        RecipeMaps.LASER_ENGRAVER.RB().ii(FOIL.getMaterialIngredient(Gold, 1), LENS.getMaterialIngredient(Ruby, 1).setNoConsume()).io(new ItemStack(GoldEtchedWiring)).add("gold_etched_wiring_1", 64, 120);
        RecipeMaps.LASER_ENGRAVER.RB().ii(FOIL.getMaterialIngredient(Electrum, 1), LENS.getMaterialIngredient(Ruby, 1).setNoConsume()).io(new ItemStack(GoldEtchedWiring)).add("gold_etched_wiring_2", 64, 120);
        RecipeMaps.LASER_ENGRAVER.RB().ii(FOIL.getMaterialIngredient(Gold, 1), LENS.getMaterialIngredient(RedGarnet, 1).setNoConsume()).io(new ItemStack(GoldEtchedWiring)).add("gold_etched_wiring_3", 64, 120);
        RecipeMaps.LASER_ENGRAVER.RB().ii(FOIL.getMaterialIngredient(Electrum, 1), LENS.getMaterialIngredient(RedGarnet, 1).setNoConsume()).io(new ItemStack(GoldEtchedWiring)).add("gold_etched_wiring_4", 64, 120);
        RecipeMaps.LASER_ENGRAVER.RB().ii(FOIL.getMaterialIngredient(Platinum, 1), LENS.getMaterialIngredient(Ruby, 1).setNoConsume()).io(new ItemStack(PlatinumEtchedWiring)).add("platinum_etched_wiring_1", 64, 480);
        RecipeMaps.LASER_ENGRAVER.RB().ii(FOIL.getMaterialIngredient(Platinum, 1), LENS.getMaterialIngredient(RedGarnet, 1).setNoConsume()).io(new ItemStack(PlatinumEtchedWiring)).add("platinum_etched_wiring_2", 64, 480);
        BOULE.all().stream().filter(m -> m.has(GEM_EXQUISITE)).forEach(m -> {
            RecipeMaps.LASER_ENGRAVER.RB().ii(BOULE.getMaterialIngredient(m, 1), LENS.getMaterialIngredient(Diamond, 1).setNoConsume()).io(GEM_EXQUISITE.get(m)).add("gem_exquisite_" + m.getId(), 64, 256);
        });
    }
}
