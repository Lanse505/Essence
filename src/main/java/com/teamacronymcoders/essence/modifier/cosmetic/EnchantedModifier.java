package com.teamacronymcoders.essence.modifier.cosmetic;

import com.teamacronymcoders.essence.api.modifier.CosmeticCoreModifier;
import com.teamacronymcoders.essence.api.tool.ModifierInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class EnchantedModifier extends CosmeticCoreModifier {

    public EnchantedModifier() {
        super(1);
    }

    @Override
    public List<ITextComponent> getRenderedText(ModifierInstance instance) {
        super.getRenderedText(instance).add(0, super.getRenderedText(instance).get(0).applyTextStyle(TextFormatting.LIGHT_PURPLE));
        return super.getRenderedText(instance);
    }
}
