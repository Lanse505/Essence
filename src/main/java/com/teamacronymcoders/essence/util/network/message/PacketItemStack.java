package com.teamacronymcoders.essence.util.network.message;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.network.PacketHandler;
import com.teamacronymcoders.essence.util.network.base.IItemNetwork;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

/**
 * Credit for most of this code goes to Mekanism.
 */
public class PacketItemStack {

  private List<Object> parameters;
  private PacketBuffer storedBuffer;
  private final Hand currentHand;

  public PacketItemStack(Hand hand, List<Object> params) {
    currentHand = hand;
    parameters = params;
  }

  private PacketItemStack(Hand hand, PacketBuffer storedBuffer) {
    currentHand = hand;
    this.storedBuffer = storedBuffer;
  }

  public static void handle(PacketItemStack message, Supplier<NetworkEvent.Context> context) {
    PlayerEntity player = PacketHandler.getPlayer(context);
    if (player == null) {
      return;
    }
    context.get().enqueueWork(() -> {
      ItemStack stack = player.getHeldItem(message.currentHand);
      if (!stack.isEmpty() && stack.getItem() instanceof IItemNetwork) {
        IItemNetwork network = (IItemNetwork) stack.getItem();
        try {
          network.handlePacketData(player.world, stack, message.storedBuffer);
        } catch (Exception e) {
          Essence.LOGGER.error("FIXME: Packet handling error", e);
        }
        message.storedBuffer.release();
      }
    });
    context.get().setPacketHandled(true);
  }

  public static void encode(PacketItemStack pkt, PacketBuffer buf) {
    buf.writeEnumValue(pkt.currentHand);
    MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
    if (server != null) {
      PacketHandler.log("Sending ItemStack packet");
    }
    PacketHandler.encode(pkt.parameters.toArray(), buf);
  }

  public static PacketItemStack decode(PacketBuffer buf) {
    return new PacketItemStack(buf.readEnumValue(Hand.class), new PacketBuffer(buf.copy()));
  }

}
