package com.teamacronymcoders.essence.impl.modifier.cosmetic;

import com.teamacronymcoders.essence.api.modifier.CosmeticCoreModifier;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class EnchantedModifier extends CosmeticCoreModifier {

    public EnchantedModifier() {
        super(1);
    }


    @Override
    public ITextComponent getRenderedText(int level) {
        return super.getRenderedText(level).applyTextStyle(TextFormatting.LIGHT_PURPLE);
    }
}
