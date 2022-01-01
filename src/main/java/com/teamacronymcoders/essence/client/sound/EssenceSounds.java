package com.teamacronymcoders.essence.client.sound;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class EssenceSounds {

    private static final ResourceLocation infusionSoundRL = new ResourceLocation(Essence.MOD_ID, "crafting_infusion");
    public static final SoundEvent infusion = new SoundEvent(infusionSoundRL);

    private static final ResourceLocation infusionBookRL = new ResourceLocation(Essence.MOD_ID, "ambient_infusion_book");
    public static final SoundEvent infusionBook = new SoundEvent(infusionBookRL);

}
