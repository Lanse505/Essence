package com.teamacronymcoders.essence.common.modifier.item.cosmetic;

import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class EnchantedModifier extends ItemCoreModifier {

    public EnchantedModifier() {
        super();
    }

    @Override
    public List<Component> getRenderedText(ModifierInstance instance) {
        super.getRenderedText(instance).add(0, super.getRenderedText(instance).get(0).copy().withStyle(ChatFormatting.LIGHT_PURPLE));
        return super.getRenderedText(instance);
    }

    @Override
    public boolean countsTowardsLimit(ItemStack target, int level) {
        return false;
    }
}
