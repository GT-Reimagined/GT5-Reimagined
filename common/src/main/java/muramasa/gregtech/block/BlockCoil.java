package muramasa.gregtech.block;

import muramasa.antimatter.block.BlockBasic;
import muramasa.antimatter.texture.Texture;
import muramasa.antimatter.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockCoil extends BlockBasic {

    protected CoilData coilData;

    public BlockCoil(String domain, String id, CoilData coilData, Block.Properties properties) {
        super(domain, id, properties);
        this.coilData = coilData;
    }

    public BlockCoil(String domain, String id, CoilData coilData) {
        this(domain, id, coilData, Block.Properties.of(Material.METAL).strength(1.0f, 10.0f).sound(SoundType.METAL).isValidSpawn((blockState, blockGetter, blockPos, object) -> false));
    }

    public CoilData getCoilData() {
        return coilData;
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(getDomain(), "block/coil/" + getId().replaceAll("_coil", ""))};
    }

    @Override
    public void appendHoverText(ItemStack p_49816_, @Nullable BlockGetter p_49817_, List<Component> tooltip, TooltipFlag p_49819_) {
        super.appendHoverText(p_49816_, p_49817_, tooltip, p_49819_);
        tooltip.add(Utils.translatable("antimatter.tooltip.heat_capacity").append(": ").append(String.valueOf(this.coilData.heat)));
        tooltip.add(Utils.translatable("tooltip.gti.coil.percentage", (int)(this.coilData.percentage * 100) + "%"));
        tooltip.add(Utils.translatable("tooltip.gti.coil.maxSimultaneousRecipes", this.coilData.maxSimultaneousRecipes));
        tooltip.add(Utils.translatable("tooltip.gti.coil.autoclaveBoosts", ((this.coilData.autoclaveBoosts * 10) + 100) + "%"));
    }

    public record CoilData(int heat, float percentage, int maxSimultaneousRecipes, int autoclaveBoosts){}
}
