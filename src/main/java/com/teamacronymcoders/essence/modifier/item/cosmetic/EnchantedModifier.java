package com.teamacronymcoders.essence.modifier.item.cosmetic;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemCosmeticCoreModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class EnchantedModifier extends ItemCosmeticCoreModifier {

    public EnchantedModifier() {
        super();
    }

    @Override
    public List<ITextComponent> getRenderedText(ModifierInstance<ItemStack> instance) {
        super.getRenderedText(instance).add(0, super.getRenderedText(instance).get(0).copyRaw().mergeStyle(TextFormatting.LIGHT_PURPLE));
        return super.getRenderedText(instance);
    }

}
