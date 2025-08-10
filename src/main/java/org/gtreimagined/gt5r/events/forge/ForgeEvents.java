package org.gtreimagined.gt5r.events.forge;

import net.minecraft.util.RandomSource;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.LevelEvent;
import org.gtreimagined.gtlib.data.GTTools;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.gtreimagined.gt5r.blockentity.multi.MiningPipeStructureCache;
import org.gtreimagined.gt5r.worldgen.PlayerPlacedBlockSavedData;
import org.gtreimagined.gtcore.events.GTCommonEvents;

import java.util.UUID;

public class ForgeEvents {
    UUID bearUUID = UUID.fromString("1964e3d1-6500-40e7-9ff2-e6161d41a8c2");

    @SubscribeEvent
    public static void rightClickEntity(PlayerInteractEvent.EntityInteract event){
        /*if (event.getTarget() instanceof Player targetPlayer){

        }*/
        ItemStack handStack = event.getEntity().getItemInHand(event.getHand());
        if(handStack.is(GTTools.WRENCH.getTag()) && event.getTarget() instanceof Player targetPlayer && targetPlayer.getUUID().equals(GTCommonEvents.BEAR_UUID)){
            RandomSource random = event.getEntity().getRandom();
            targetPlayer.moveTo(targetPlayer.getX(), targetPlayer.getY(), targetPlayer.getZ(), random.nextInt(180), targetPlayer.getXRot());
        }
    }

    @SubscribeEvent
    public static void onBlockBreakEvent(BlockEvent.BreakEvent event){
        if (event.getLevel() instanceof ServerLevel serverLevel){
            PlayerPlacedBlockSavedData.getOrCreate(serverLevel).removeBlockPos(event.getPos());
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event){
        if (event.getLevel() instanceof ServerLevel serverLevel && event.getEntity() instanceof Player){
            PlayerPlacedBlockSavedData.getOrCreate(serverLevel).addBlockPos(event.getBlockSnapshot().getPos());
        }
    }

    @SubscribeEvent
    public static void onBlockMultiPlace(BlockEvent.EntityMultiPlaceEvent event){
        if (event.getLevel() instanceof ServerLevel serverLevel && event.getEntity() instanceof Player) {
            var savedData = PlayerPlacedBlockSavedData.getOrCreate(serverLevel);
            for (var snapshot : event.getReplacedBlockSnapshots()){
                savedData.addBlockPos(snapshot.getPos());
            }
        }
    }


    @SubscribeEvent
    public static void onWorldUnload(LevelEvent.Unload event){
        MiningPipeStructureCache.onWorldUnload(event.getLevel());
    }
}
