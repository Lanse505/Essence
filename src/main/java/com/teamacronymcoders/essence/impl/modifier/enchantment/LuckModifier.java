package com.teamacronymcoders.essence.impl.modifier.enchantment;

import com.teamacronymcoders.essence.api.modifier.EnchantmentCoreModifier;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.impl.items.tools.EssencePickaxe;
import com.teamacronymcoders.essence.impl.items.tools.EssenceSword;
import com.teamacronymcoders.essence.impl.modifier.interaction.FieryModifier;
import com.teamacronymcoders.essence.utils.config.EssenceModifierConfig;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class LuckModifier extends EnchantmentCoreModifier {

    public LuckModifier() {
        super(5);
    }

    @Override
    public void onInventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem, int level) {
        EssenceEnchantmentHelper.createOrUpdateEnchantment(stack, getLinkedEnchantment(stack), level);
    }

    @Override
    public Enchantment getLinkedEnchantment(ItemStack stack) {
        return stack.getItem() instanceof EssenceSword ? Enchantments.LOOTING : Enchantments.FORTUNE;
    }

    @Override
    public boolean canApplyTogether(Modifier modifier) {
        return !(modifier instanceof SilkTouchModifier);
    }

    @Override
    public ITextComponent getRenderedText(int level) {
        return super.getRenderedText(level).applyTextStyle(TextFormatting.BLUE);
    }
}
