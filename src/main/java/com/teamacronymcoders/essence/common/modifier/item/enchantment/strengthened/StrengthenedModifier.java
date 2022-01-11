package com.teamacronymcoders.essence.common.modifier.item.enchantment.strengthened;

import com.teamacronymcoders.essence.api.modifier.IModifier;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemInteractionModifier;
import com.teamacronymcoders.essence.common.util.helper.EssenceEnchantmentHelper;
import com.teamacronymcoders.essence.common.util.helper.EssenceUtilHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class StrengthenedModifier extends ItemInteractionModifier {

    private final StrengthenedType type;

    public StrengthenedModifier(StrengthenedType type) {
        super(1, 5);
        this.type = type;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance instance) {
        EssenceEnchantmentHelper.createOrUpdateEnchantment(stack, getLinkedEnchantment(stack), instance, 2);
    }

    @Override
    public Enchantment getLinkedEnchantment(ItemStack stack) {
        return this.type.getEnchantment();
    }

    @Override
    public boolean canApplyTogether(ItemStack stack, IModifier<ItemStack> modifier) {
        return !(modifier instanceof StrengthenedModifier);
    }

    @Override
    public String getTranslationName() {
        return "modifier.essence.strengthened";
    }

    @Override
    public Component getTextComponentName(int level) {
        if (level == -1) {
            return new TranslatableComponent(getTranslationName() + ".cleaned", new TranslatableComponent("essence.strengthened.type." + this.type.getName()));
        }
        return super.getTextComponentName(level);
    }

    @Override
    public List<Component> getRenderedText(ModifierInstance instance) {
        List<Component> textComponents = new ArrayList<>();
        textComponents.add(new TextComponent("  ").append(new TranslatableComponent(getTranslationName(), EssenceUtilHelper.toRoman(instance.getLevel()), new TranslatableComponent("essence.strengthened.type." + this.type.getName()).withStyle(this.type.getTextFormatting())).withStyle(ChatFormatting.GRAY)));
        return textComponents;
    }

    public StrengthenedType getStrengthenedType() {
        return type;
    }
}
