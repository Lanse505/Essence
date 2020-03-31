package com.teamacronymcoders.essence.effect;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class EssenceEffect {

    private static final ResourceLocation infusionSoundRL = new ResourceLocation(Essence.MODID, "crafting_infusion");
    public static final SoundEvent infusion = new SoundEvent(infusionSoundRL);

    private static final ResourceLocation infusionBookRL = new ResourceLocation(Essence.MODID, "ambient_infusion_book");
    public static final SoundEvent infusionBook = new SoundEvent(infusionBookRL);

}
