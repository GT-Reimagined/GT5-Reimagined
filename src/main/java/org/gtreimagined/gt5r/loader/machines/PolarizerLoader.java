package org.gtreimagined.gt5r.loader.machines;

import net.minecraft.world.item.Item;
import org.gtreimagined.gt5r.data.RecipeMaps;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTypeBlock;
import org.gtreimagined.gtlib.material.MaterialTypeItem;

import static org.gtreimagined.gt5r.data.Materials.*;

public class PolarizerLoader {
    public static void init(){
        Material[] materials = new Material[]{IronMagnetic, SteelMagnetic, NeodymiumMagnetic};
        Material[] materials2 = new Material[]{Iron, Steel, Neodymium};
        for (int i = 0; i < materials.length; i++) {
            Material out = materials[i];
            Material in = materials2[i];
            if (GTAPI.isModLoaded("tfc") && in == Iron) in = WroughtIron;
            Material finalIn = in;
            out.getTypes().forEach(t -> {
                if (t.getUnitValue() <= 0) return;
                if (!finalIn.has(t)) return;
                Item output = t instanceof MaterialTypeItem<?> typeItem ? typeItem.get(out) : t instanceof MaterialTypeBlock<?> typeBlock && typeBlock.get() instanceof MaterialTypeBlock.IBlockGetter getter ? getter.get(out).asItem() : null;
                if (output != null){
                    RecipeMaps.POLARIZER.RB().ii(t.getMaterialIngredient(finalIn, 1)).io(output).add(finalIn.getId() + "_" + t.getId() + "_to_" + out.getId(), Math.max(1, (128 * t.getUnitValue()) / Ref.U), (finalIn == Neodymium ? 64 : 16));
                }
            });
        }
    }
}
