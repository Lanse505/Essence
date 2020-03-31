package com.teamacronymcoders.essence.util.registration;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.effect.EssenceEffect;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EssenceSoundRegistration {

    private static final DeferredRegister<SoundEvent> SOUND_EVENT_DEFERRED_REGISTER = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, Essence.MODID);

    public static RegistryObject<SoundEvent> INFUSION_SOUND = SOUND_EVENT_DEFERRED_REGISTER.register("crafting_infusion", () -> EssenceEffect.infusion);
    public static RegistryObject<SoundEvent> INFUSION_BOOK_SOUND = SOUND_EVENT_DEFERRED_REGISTER.register("ambient_infusion_book", () -> EssenceEffect.infusionBook);

    public static void register(IEventBus bus) {
        SOUND_EVENT_DEFERRED_REGISTER.register(bus);
    }

}
