package com.teamacronymcoders.essence.common.util.network.message.server;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.common.util.network.PacketHandler;
import com.teamacronymcoders.essence.common.util.network.base.IItemNetwork;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.List;
import java.util.function.Supplier;

/**
 * Credit for most of this code goes to Mekanism.
 */
public class PacketItemStack {

    private List<Object> parameters;
    private FriendlyByteBuf storedBuffer;
    private final InteractionHand hand;

    public PacketItemStack(InteractionHand hand, List<Object> params) {
        this.hand = hand;
        parameters = params;
    }

    private PacketItemStack(InteractionHand hand, FriendlyByteBuf storedBuffer) {
        this.hand = hand;
        this.storedBuffer = storedBuffer;
    }

    public static void handle(PacketItemStack message, Supplier<NetworkEvent.Context> context) {
        Player player = PacketHandler.getPlayer(context);
        if (player == null) {
            return;
        }
        context.get().enqueueWork(() -> {
            ItemStack stack = player.getItemInHand(message.hand);
            if (!stack.isEmpty() && stack.getItem() instanceof IItemNetwork) {
                IItemNetwork network = (IItemNetwork) stack.getItem();
                try {
                    network.handlePacketData(player.level, stack, message.storedBuffer);
                } catch (Exception e) {
                    Essence.LOGGER.error("FIXME: Packet handling error", e);
                }
                message.storedBuffer.release();
            }
        });
        context.get().setPacketHandled(true);
    }

    public static void encode(PacketItemStack pkt, FriendlyByteBuf buf) {
        buf.writeEnum(pkt.hand);
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            PacketHandler.log("Sending ItemStack packet");
        }
        PacketHandler.encode(pkt.parameters.toArray(), buf);
    }

    public static PacketItemStack decode(FriendlyByteBuf buf) {
        return new PacketItemStack(buf.readEnum(InteractionHand.class), new FriendlyByteBuf(buf.copy()));
    }

}
