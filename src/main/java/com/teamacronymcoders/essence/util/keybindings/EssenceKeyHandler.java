package com.teamacronymcoders.essence.util.keybindings;

import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.glfw.GLFW;

/**
 * This is very much not inspired heavily and using the systems that Mekanism has.
 * If anyone asks you totally can't see their implementation in the link provided in this javadoc.
 * That would be silly wouldn't it: https://github.com/mekanism/Mekanism/blob/1.16.x/src/main/java/mekanism/client/MekanismKeyHandler.java
 * Also their license is not MIT, and can not be found on their github: https://github.com/mekanism/Mekanism/blob/1.16.x/LICENSE
 */
public class EssenceKeyHandler extends KeyHandler {

  public static final KeyMapping EXTENDED_INFORMATION = new KeyMapping(
          KBTranslationKeys.EXTENDED_INFORMATION,
          KeyConflictContext.GUI,
          Type.KEYSYM,
          GLFW.GLFW_KEY_LEFT_SHIFT,
          KBTranslationKeys.MODID
  );

  public static final KeyMapping CYCLING = new KeyMapping(
          KBTranslationKeys.CYCLING,
          KeyConflictContext.IN_GAME,
          Type.KEYSYM,
          GLFW.GLFW_KEY_LEFT_SHIFT,
          KBTranslationKeys.MODID
  );

  private static final Builder BINDINGS = new Builder(2)
          .addBinding(EXTENDED_INFORMATION, false)
          .addBinding(CYCLING, false);

  public EssenceKeyHandler() {
    super(BINDINGS);
    ClientRegistry.registerKeyBinding(EXTENDED_INFORMATION);
    ClientRegistry.registerKeyBinding(CYCLING);
    MinecraftForge.EVENT_BUS.addListener(this::onTick);
  }

  private void onTick(InputEvent.KeyInputEvent event) {
    keyTick();
  }

  @Override
  public void keyDown(KeyMapping kb, boolean isRepeat) {
  }

  @Override
  public void keyUp(KeyMapping kb) {
  }
}
