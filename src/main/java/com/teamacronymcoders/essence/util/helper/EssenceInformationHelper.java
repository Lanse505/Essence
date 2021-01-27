package com.teamacronymcoders.essence.util.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EssenceInformationHelper {

  public static final TranslationTextComponent shiftForDetails = new TranslationTextComponent("tooltip.essence.generic.shiftForInformation", new TranslationTextComponent(Minecraft.getInstance().gameSettings.keyBindSneak.getKey().getTranslationKey()));

  @OnlyIn(Dist.CLIENT)
  public static boolean isSneakKeyDown() {
    final KeyBinding keyBindSneak = Minecraft.getInstance().gameSettings.keyBindSneak;
    final long handle = Minecraft.getInstance().getMainWindow().getHandle();
    return InputMappings.isKeyDown(handle, keyBindSneak.getKey().getKeyCode());
  }

}
