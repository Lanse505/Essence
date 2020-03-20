package com.teamacronymcoders.essence.modifier.item.enchantment.strengthened;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.IModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendables.ItemEnchantmentCoreModifier;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import com.teamacronymcoders.essence.utils.helpers.EssenceUtilHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class StrengthenedModifier extends ItemEnchantmentCoreModifier {

    private final StrengthenedType type;

    public StrengthenedModifier(StrengthenedType type) {
        super(5);
        this.type = type;
    }

    @Override
    public void onInventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance instance) {
        EssenceEnchantmentHelper.createOrUpdateEnchantment(stack, getLinkedEnchantment(stack), instance, 2);
    }

    @Override
    public Enchantment getLinkedEnchantment(ItemStack stack) {
        return this.type.getEnchantment();
    }

    @Override
    public boolean canApplyTogether(IModifier modifier) {
        return !(modifier instanceof StrengthenedModifier);
    }

    @Override
    public String getTranslationName() {
        return "modifier.essence.strengthened";
    }

    @Override
    public List<ITextComponent> getRenderedText(ModifierInstance instance) {
        List<ITextComponent> textComponents = new ArrayList<>();
        textComponents.add(new TranslationTextComponent(getTranslationName(), EssenceUtilHelper.toRoman(instance.getLevel()), new TranslationTextComponent("strengthened.type." + this.type.getName()).applyTextStyles(this.type.getTextFormatting())).applyTextStyle(TextFormatting.GRAY));
        return textComponents;
    }

    public StrengthenedType getStrengthenedType() {
        return type;
    }
}
