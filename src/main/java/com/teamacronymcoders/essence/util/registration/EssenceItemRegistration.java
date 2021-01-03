package com.teamacronymcoders.essence.util.registration;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EssenceItemRegistration {

    private static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Essence.MODID);

    public static void register(IEventBus bus) {
        ITEM_DEFERRED_REGISTER.register(bus);
    }
}
