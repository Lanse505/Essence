package com.teamacronymcoders.essence.util.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EssenceClientProxy extends EssenceCommonProxy {
  @Override
  public Player getPlayer(Supplier<NetworkEvent.Context> context) {
    if (context.get().getDirection() == NetworkDirection.PLAY_TO_SERVER || context.get().getDirection() == NetworkDirection.LOGIN_TO_SERVER) {
      return context.get().getSender();
    }
    return Minecraft.getInstance().player;
  }
}
