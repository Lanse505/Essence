package com.teamacronymcoders.essence.util.proxy;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EssenceServerProxy extends EssenceCommonProxy {

  @Override
  public Player getPlayer(Supplier<NetworkEvent.Context> context) {
    return null;
  }
}
