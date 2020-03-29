package com.teamacronymcoders.essence.utils.registration;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.effects.EssenceEffects;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EssenceSoundRegistration {

    private static final DeferredRegister<SoundEvent> SOUND_EVENT_DEFERRED_REGISTER = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, Essence.MODID);

    public static RegistryObject<SoundEvent> INFUSION_SOUND = SOUND_EVENT_DEFERRED_REGISTER.register("crafting_infusion", () -> EssenceEffects.infusion);
    public static RegistryObject<SoundEvent> INFUSION_BOOK_SOUND = SOUND_EVENT_DEFERRED_REGISTER.register("ambient_infusion_book", () -> EssenceEffects.infusionBook);

    public static void register(IEventBus bus) {
        SOUND_EVENT_DEFERRED_REGISTER.register(bus);
    }

}
