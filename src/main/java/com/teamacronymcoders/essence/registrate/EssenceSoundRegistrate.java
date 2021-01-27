package com.teamacronymcoders.essence.registrate;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.effect.EssenceEffect;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.util.SoundEvent;

public class EssenceSoundRegistrate {

  public static void init() {}

  public static final RegistryEntry<SoundEvent> INFUSION_SOUND = Essence.ESSENCE_REGISTRATE.simple("crafting_infusion", SoundEvent.class, () -> EssenceEffect.infusion);
  public static final RegistryEntry<SoundEvent> INFUSION_BOOK_SOUND = Essence.ESSENCE_REGISTRATE.simple("book_infusion", SoundEvent.class, () -> EssenceEffect.infusionBook);

}