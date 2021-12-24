package com.teamacronymcoders.essence.util.proxy;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EssenceCommonProxy {
  public Player getPlayer(Supplier<NetworkEvent.Context> context) {
    return context.get().getSender();
  }
}
