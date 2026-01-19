package org.gtreimagined.gt5r.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.block.BlockBasicStair;
import org.gtreimagined.gtlib.registration.IColorHandler;
import org.gtreimagined.gtlib.texture.Texture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockAsphaltStair extends BlockBasicStair implements IColorHandler {
    final int color;
    public BlockAsphaltStair(String domain, String id, Block base, int color) {
        super(domain, id, base, Properties.of().mapColor(DyeColor.byName(id.replace("_asphalt_stairs", ""), DyeColor.BLACK)).instrument(NoteBlockInstrument.BASEDRUM).strength(1.0f, 1.0f).sound(SoundType.STONE));
        this.color = color;
    }

    @Override
    public int getItemColor(ItemStack stack, @Nullable Block block, int i) {
        return color;
    }

    @Override
    public int getBlockColor(BlockState state, @Nullable BlockGetter world, @Nullable BlockPos pos, int i) {
        return color;
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(GT5Reimagined.ID, "block/stone/asphalt")};
    }
}
