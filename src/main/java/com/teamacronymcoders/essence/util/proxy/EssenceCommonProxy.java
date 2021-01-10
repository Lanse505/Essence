package com.teamacronymcoders.essence.util.proxy;

import java.util.function.Supplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

public class EssenceCommonProxy {
  public PlayerEntity getPlayer (Supplier<NetworkEvent.Context> context) {
    return context.get().getSender();
  }
}
