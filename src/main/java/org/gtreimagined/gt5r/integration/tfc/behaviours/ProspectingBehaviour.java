package org.gtreimagined.gt5r.integration.tfc.behaviours;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.dries007.tfc.common.TFCTags.Blocks;
import net.dries007.tfc.common.items.ProspectResult;
import net.dries007.tfc.network.PacketHandler;
import net.dries007.tfc.network.ProspectedPacket;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.events.ProspectedEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.PacketDistributor;
import org.gtreimagined.gtlib.behaviour.IItemUse;
import org.gtreimagined.gtlib.tool.IBasicGTTool;

import java.util.ArrayList;
import java.util.Random;

import static net.dries007.tfc.common.items.PropickItem.scanAreaFor;

public class ProspectingBehaviour implements IItemUse<IBasicGTTool> {
    public static final ProspectingBehaviour INSTANCE = new ProspectingBehaviour();

    private static final Random RANDOM = new Random();
    @Override
    public InteractionResult onItemUse(IBasicGTTool instance, UseOnContext c) {
        Level level = c.getLevel();
        Player player = c.getPlayer();
        BlockPos pos = c.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (player instanceof ServerPlayer serverPlayer) {
            SoundType sound = state.getSoundType(level, pos, player);
            level.playSound(player, pos, sound.getHitSound(), SoundSource.PLAYERS, sound.getVolume(), sound.getPitch());
            c.getItemInHand().hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(c.getHand()));
            player.getCooldowns().addCooldown(instance.getItem(), 10);
            BlockState found = state;
            RANDOM.setSeed((long) Helpers.hash(19827384739241223L, pos));
            ProspectResult result;
            if (Helpers.isBlock(state, Blocks.PROSPECTABLE)) {
                result = ProspectResult.FOUND;
            } else if (RANDOM.nextFloat() < getFalseNegativeChance(instance, c.getItemInHand())) {
                result = ProspectResult.NOTHING;
            } else {
                Object2IntMap<BlockState> states = scanAreaFor(level, pos, 12, Blocks.PROSPECTABLE);
                if (states.isEmpty()) {
                    result = ProspectResult.NOTHING;
                } else {
                    ArrayList<BlockState> stateKeys = new ArrayList(states.keySet());
                    found = (BlockState)stateKeys.get(RANDOM.nextInt(stateKeys.size()));
                    int amount = states.getOrDefault(found, 1);
                    if (amount < 10) {
                        result = ProspectResult.TRACES;
                    } else if (amount < 20) {
                        result = ProspectResult.SMALL;
                    } else if (amount < 40) {
                        result = ProspectResult.MEDIUM;
                    } else if (amount < 80) {
                        result = ProspectResult.LARGE;
                    } else {
                        result = ProspectResult.VERY_LARGE;
                    }
                }
            }

            MinecraftForge.EVENT_BUS.post(new ProspectedEvent(player, result, found.getBlock()));
            PacketHandler.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new ProspectedPacket(found.getBlock(), result));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public String getId() {
        return "prospecting";
    }

    private static float getFalseNegativeChance(IBasicGTTool instance, ItemStack stack) {
        return 0.3F - (float) Mth.clamp(instance.getTier(stack).getLevel(), 0, 5) * 0.060000002F;
    }
}
