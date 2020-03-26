package com.teamacronymcoders.essence.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class EssenceCommonProxy {
    public PlayerEntity getPlayer(Supplier<NetworkEvent.Context> context) {
        return context.get().getSender();
    }
}
