package com.teamacronymcoders.essence.impl.modifier.enchantment.strengthened;

import com.teamacronymcoders.essence.api.modifier.EnchantmentCoreModifier;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import com.teamacronymcoders.essence.utils.helpers.EssenceUtilHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class StrengthenedModifier extends EnchantmentCoreModifier {

    private StrengthenedType type;

    public StrengthenedModifier() {
        super(5);
    }

    public StrengthenedModifier(StrengthenedType type) {
        super(5);
        this.type = type;
    }

    @Override
    public void onInventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem, int level) {
        EssenceEnchantmentHelper.createOrUpdateEnchantment(stack, getLinkedEnchantment(stack), level * 2);
    }

    @Override
    public Enchantment getLinkedEnchantment(ItemStack stack) {
        return this.type.getEnchantment();
    }

    @Override
    public boolean canApplyTogether(Modifier modifier) {
        return !(modifier instanceof StrengthenedModifier && !(((StrengthenedModifier) modifier).getType().equals(this.type)));
    }

    @Override
    public String getTranslationName() {
        return "modifier.essence.strengthened";
    }

    @Override
    public ITextComponent getRenderedText(int level) {
        return new TranslationTextComponent(getTranslationName(), EssenceUtilHelper.toRoman(level), new TranslationTextComponent("strengthened.type." + this.type.getName()).applyTextStyles(this.type.getTextFormatting())).applyTextStyle(TextFormatting.GRAY);
    }

    public StrengthenedType getType() {
        return this.type;
    }

}
