package org.gtreimagined.gt5r.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.item.ItemBasic;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.registration.IColorHandler;
import org.gtreimagined.gtlib.texture.Texture;
import org.jetbrains.annotations.Nullable;

public class ItemDopedBoule extends ItemBasic<ItemDopedBoule> implements IColorHandler {
    private final Material dopedMaterial;
    public ItemDopedBoule(Material dopedMaterial) {
        super(GT5Reimagined.ID, dopedMaterial.getId() + "_doped_silicon_boule");
        this.dopedMaterial = dopedMaterial;
    }

    @Override
    public int getItemColor(ItemStack stack, @Nullable Block block, int i) {
        return i == 0 ? Materials.Silicon.getRGB() : dopedMaterial.getRGB();
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{
                new Texture(Ref.ID, "item/material/boule"),
                new Texture(GT5Reimagined.ID, "item/basic/silicon/doped_boule_overlay")
        };
    }
}
