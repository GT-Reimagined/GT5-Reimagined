package muramasa.gregtech.cover;

import muramasa.antimatter.blockentity.pipe.BlockEntityItemPipe;
import muramasa.antimatter.capability.ICoverHandler;
import muramasa.antimatter.cover.BaseCover;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.util.Utils;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tesseract.TesseractCapUtils;
import tesseract.api.item.PlatformItemHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class CoverItemRetriever extends BaseCover {
    public CoverItemRetriever(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public void onUpdate() {
        if (!source().getTile().getLevel().isClientSide() && source().getTile() instanceof BlockEntityItemPipe<?> pipe){
            if (pipe.getLevel().getGameTime() % 20 == 15 && pipe.pipeCapacityCheck()){
                ArrayList<BlockEntityItemPipe<?>> tUsedPipes = new ArrayList<>();
                BlockEntity adjacent = pipe.getCachedBlockEntity(this.side);
                if (adjacent == null) return;
                PlatformItemHandler to = TesseractCapUtils.INSTANCE.getItemHandler(adjacent, this.side.getOpposite()).orElse(null);
                if (to == null) return;
                for (BlockEntityItemPipe<?> p : BlockEntityItemPipe.scanPipes(pipe, new HashMap<>(), 0, true, false).keySet()){
                    if (tUsedPipes.add(p)){
                        for (Direction dir : Direction.values()){
                            if (p.canAcceptItemsFrom(dir, pipe) && (dir != this.side || p != pipe)){
                                BlockEntity a = p.getCachedBlockEntity(dir);
                                if (!(a instanceof BlockEntityItemPipe) && a != null){
                                    PlatformItemHandler itemHandler = TesseractCapUtils.INSTANCE.getItemHandler(a, dir.getOpposite()).orElse(null);
                                    if (itemHandler != null && Utils.transferItems(itemHandler, to, true)){
                                        for (BlockEntityItemPipe<?> tUsedPipe : tUsedPipes){
                                            tUsedPipe.incrementTransferCounter(1);
                                        }
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
