package muramasa.gregtech.block;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.builder.AntimatterBlockModelBuilder;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.dynamic.BlockDynamic;
import muramasa.antimatter.texture.Texture;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class BlockCasing extends BlockDynamic {

    public BlockCasing(String domain, String id, Block.Properties properties) {
        super(domain, id, properties.isValidSpawn((blockState, blockGetter, blockPos, object) -> false));
        if (this.getClass() != BlockCasing.class) AntimatterAPI.register(BlockCasing.class, this);
    }

    public BlockCasing(String domain, String id) {
        this(domain, id, Block.Properties.of(Material.METAL).strength(1.0f, 10.0f).sound(SoundType.METAL).requiresCorrectToolForDrops());
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(getDomain(), "block/casing/" + getId().replaceAll("casing_", "").replaceAll("_casing", ""))};
    }

    @Override
    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        AntimatterBlockModelBuilder builder = buildBlock(block,prov);
        if (builder != null) {
            prov.state(block, builder);
        } else {
            super.onBlockModelBuild(block, prov);
        }
    }
    //Hierarchial block builder.
    protected AntimatterBlockModelBuilder buildBlock(Block block, AntimatterBlockStateProvider prov) {
        return null;
    }
}