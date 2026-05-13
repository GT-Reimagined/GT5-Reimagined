package org.gtreimagined.gt5r.events.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.event.entity.player.PlayerXpEvent.PickupXp;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.registries.MissingMappingsEvent;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTRemapping;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTTools;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.gtreimagined.gt5r.blockentity.multi.MiningPipeStructureCache;
import org.gtreimagined.gt5r.worldgen.PlayerPlacedBlockSavedData;
import org.gtreimagined.gtcore.events.GTCommonEvents;
import org.gtreimagined.gtlib.util.RegistryUtils;

import java.util.Map;
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

    /*@SubscribeEvent
    public static void onPickupXpEvent(PickupXp event){
        Player player = event.getEntity();
        int original = event.getOrb().getValue();
        player.takeXpDelay = 2;
        player.take(event.getOrb(), 1);
        int repaired = repairPlayerItems(player, original, original);
        if (repaired > 0){
            player.giveExperiencePoints(repaired);
        }
        event.getOrb().discard();
        event.setCanceled(true);
    }*/

    private static int repairPlayerItems(Player player, int repairAmount, int originalXp) {
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(Enchantments.MENDING, player, ItemStack::isDamaged);
        GT5Reimagined.LOGGER.info(entry != null);
        if (entry != null) {
            ItemStack itemstack = entry.getValue();
            int mendingRepairs = itemstack.getTag() != null ? itemstack.getTag().getInt("mendingRepairs") : 0;
            float reductionRatio = 1f;
            if (mendingRepairs > itemstack.getMaxDamage() / 2) reductionRatio = 0.75f;
            if (mendingRepairs > itemstack.getMaxDamage()) reductionRatio = 0.5f;
            if (mendingRepairs > itemstack.getMaxDamage() * 2) reductionRatio = 0.25f;
            if (mendingRepairs > itemstack.getMaxDamage() * 3) reductionRatio = 0f;
            if (reductionRatio == 0f) return repairAmount;
            float xpRepairRatio = itemstack.getXpRepairRatio() * reductionRatio;
            float exactToRepair = originalXp * xpRepairRatio;
            int toRepair = Math.min((int)(exactToRepair), itemstack.getDamageValue());
            GT5Reimagined.LOGGER.info("repair amount: " + toRepair);
            itemstack.setDamageValue(itemstack.getDamageValue() - toRepair);
            itemstack.getOrCreateTag().putInt("mendingRepairs", mendingRepairs + toRepair);
            int j = repairAmount - (int)(exactToRepair / xpRepairRatio);
            return j > 0 ? repairPlayerItems(player, j, originalXp) : 0;
        } else return repairAmount;
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

    @SubscribeEvent
    public static void onMissingMappings(MissingMappingsEvent event){
        for (MissingMappingsEvent.Mapping<Fluid> mapping : event.getMappings(Keys.FLUIDS, Ref.SHARED_ID)) {
            if (GTAPI.isModLoaded(Ref.MOD_FR) && mapping.getKey().getPath().equals("liquid_honey")){
                mapping.remap(RegistryUtils.getFluidFromID(new ResourceLocation(Ref.MOD_FR, "honey")));
            }
        }
    }
}
