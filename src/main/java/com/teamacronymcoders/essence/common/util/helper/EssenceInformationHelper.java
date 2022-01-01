package com.teamacronymcoders.essence.common.util.helper;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EssenceInformationHelper {

    public static final TranslatableComponent shiftForDetails = new TranslatableComponent("tooltip.essence.generic.shiftForInformation", new TranslatableComponent(Minecraft.getInstance().options.keyShift.getKey().getName()));

    @OnlyIn(Dist.CLIENT)
    public static boolean isSneakKeyDown() {
        final KeyMapping keyBindSneak = Minecraft.getInstance().options.keyShift;
        final long handle = Minecraft.getInstance().getWindow().getWindow();
        return InputConstants.isKeyDown(handle, keyBindSneak.getKey().getValue());
    }

}
