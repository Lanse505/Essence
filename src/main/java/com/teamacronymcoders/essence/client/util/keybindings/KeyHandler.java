package com.teamacronymcoders.essence.client.util.keybindings;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * This is totally not stolen from Mekanism
 * Nope not at all, and if anyone asks this is not the link to the specific code on the specific repo
 * link: https://github.com/mekanism/Mekanism/blob/1.16.x/src/main/java/mekanism/client/MekKeyHandler.java
 * <p>
 * Nope that is totally not what this is :)
 * Also their license is not MIT, and can not be found on their github: https://github.com/mekanism/Mekanism/blob/1.16.x/LICENSE
 */
public abstract class KeyHandler {
    /**
     * KeyBinding instances
     */
    private final KeyMapping[] keyBindings;

    /**
     * Track which keys have been seen as pressed currently
     */
    private final BitSet keyDown;

    /**
     * Whether keys send repeated KeyDown pseudo-messages
     */
    private final BitSet repeatings;

    /**
     * Pass an array of keybindings and a repeat flag for each one
     *
     * @param bindings Bindings to set
     */
    public KeyHandler(Builder bindings) {
        keyBindings = bindings.getBindings();
        repeatings = bindings.getRepeatFlags();
        keyDown = new BitSet();
    }

    public static boolean getIsKeyPressed(KeyMapping keyBinding) {
        if (keyBinding.isDown()) {
            return true;
        }
        if (keyBinding.getKeyConflictContext().isActive() && keyBinding.getKeyModifier().isActive(keyBinding.getKeyConflictContext())) {
            //Manually check in case keyBinding#pressed just never got a chance to be updated
            return isKeyDown(keyBinding);
        }
        //If we failed, due to us being a key com.teamacronymcoders.essenceapi.modifier as our key, check the old way
        return KeyModifier.isKeyCodeModifier(keyBinding.getKey()) && isKeyDown(keyBinding);
    }

    public static boolean isKeyDown(KeyMapping keyBinding) {
        InputConstants.Key key = keyBinding.getKey();
        int keyCode = key.getValue();
        if (keyCode != InputConstants.UNKNOWN.getValue()) {
            long windowHandle = Minecraft.getInstance().getWindow().getWindow();
            try {
                if (key.getType() == InputConstants.Type.KEYSYM) {
                    return InputConstants.isKeyDown(windowHandle, keyCode);
                } else if (key.getType() == InputConstants.Type.MOUSE) {
                    return GLFW.glfwGetMouseButton(windowHandle, keyCode) == GLFW.GLFW_PRESS;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    public void keyTick() {
        for (int i = 0; i < keyBindings.length; i++) {
            KeyMapping keyBinding = keyBindings[i];
            boolean state = keyBinding.isDown();
            boolean lastState = keyDown.get(i);
            if (state != lastState || (state && repeatings.get(i))) {
                if (state) {
                    keyDown(keyBinding, lastState);
                } else {
                    keyUp(keyBinding);
                }
                keyDown.set(i, state);
            }
        }
    }

    public abstract void keyDown(KeyMapping kb, boolean isRepeat);

    public abstract void keyUp(KeyMapping kb);

    public static class Builder {

        private final List<KeyMapping> bindings;
        private final BitSet repeatFlags = new BitSet();

        public Builder(int expectedCapacity) {
            this.bindings = new ArrayList<>(expectedCapacity);
        }

        /**
         * Add a keybinding to the list
         *
         * @param k          the KeyBinding to add
         * @param repeatFlag true if keyDown pseudo-events continue to be sent while key is held
         */
        public Builder addBinding(KeyMapping k, boolean repeatFlag) {
            repeatFlags.set(bindings.size(), repeatFlag);
            bindings.add(k);
            return this;
        }

        protected BitSet getRepeatFlags() {
            return repeatFlags;
        }

        protected KeyMapping[] getBindings() {
            return bindings.toArray(new KeyMapping[0]);
        }
    }
}
