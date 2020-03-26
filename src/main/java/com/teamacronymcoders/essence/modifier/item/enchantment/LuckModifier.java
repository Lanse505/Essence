package com.teamacronymcoders.essence.modifier.item.enchantment;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.IModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendables.ItemEnchantmentCoreModifier;
import com.teamacronymcoders.essence.items.tools.EssenceSword;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class LuckModifier extends ItemEnchantmentCoreModifier {

    public LuckModifier() {
        super(5);
    }

    @Override
    public void onInventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance<ItemStack> instance) {
        EssenceEnchantmentHelper.createOrUpdateEnchantment(stack, getLinkedEnchantment(stack), instance);
    }

    @Override
    public Enchantment getLinkedEnchantment(ItemStack stack) {
        return stack.getItem() instanceof EssenceSword ? Enchantments.LOOTING : Enchantments.FORTUNE;
    }

    @Override
    public boolean canApplyTogether(IModifier modifier) {
        return !(modifier instanceof SilkTouchModifier);
    }

    @Override
    public List<ITextComponent> getRenderedText(ModifierInstance<ItemStack> instance) {
        super.getRenderedText(instance).add(0, super.getRenderedText(instance).get(0).applyTextStyle(TextFormatting.BLUE));
        return super.getRenderedText(instance);
    }
}