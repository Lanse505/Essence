package com.teamacronymcoders.essence.util.proxy;

import java.util.function.Supplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class EssenceServerProxy extends EssenceCommonProxy {

  @Override
  public PlayerEntity getPlayer (Supplier<Context> context) {
    return null;
  }
}
