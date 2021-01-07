package com.teamacronymcoders.essence.util.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class EssenceServerProxy extends EssenceCommonProxy {

    @Override
    public PlayerEntity getPlayer(Supplier<Context> context) {
        return null;
    }
}
