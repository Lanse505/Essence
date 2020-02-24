package com.teamacronymcoders.essence.impl.modifier.enchantment;

import com.teamacronymcoders.essence.api.modifier.EnchantmentCoreModifier;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
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
        return type.getEnchantment();
    }

    @Override
    public boolean canApplyTogether(Modifier modifier) {
        return !(modifier instanceof StrengthenedModifier && !(((StrengthenedModifier) modifier).getType().equals(type)));
    }

    @Override
    public String getTranslationName() {
        return "modifier.essence.strengthened";
    }

    @Override
    public ITextComponent getRenderedText(int level) {
        return new TranslationTextComponent(getTranslationName(), new TranslationTextComponent("strengthened.type." + type.name).applyTextStyles(type.getTextFormatting())).applyTextStyle(TextFormatting.GRAY);
    }

    public StrengthenedType getType() {
        return type;
    }

    public StrengthenedModifier setType(StrengthenedType type) {
        this.type = type;
        return this;
    }

    public enum StrengthenedType {
        BANE_OF_ARTHROPODS(0, "arthropod", Enchantments.BANE_OF_ARTHROPODS, TextFormatting.DARK_PURPLE),
        SHARPNESS(1, "sharpness", Enchantments.SHARPNESS, TextFormatting.WHITE),
        SMITE(2, "smite", Enchantments.SMITE, TextFormatting.YELLOW),
        POWER(3, "power", Enchantments.POWER, TextFormatting.GOLD);

        private static final StrengthenedType[] VALUES = new StrengthenedType[]{BANE_OF_ARTHROPODS, SHARPNESS, SMITE, POWER};
        private final int id;
        private final String name;
        private final Enchantment enchantment;
        private final TextFormatting[] textFormatting;

        StrengthenedType(int id, String name, Enchantment enchantment, TextFormatting... textFormatting) {
            this.id = id;
            this.name = name;
            this.enchantment = enchantment;
            this.textFormatting = textFormatting;
        }

        public static StrengthenedType byID(int id) {
            if (id >= 0 && id < VALUES.length) {
                return VALUES[id];
            } else {
                throw new IllegalArgumentException("No type with value " + id);
            }
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Enchantment getEnchantment() {
            return enchantment;
        }

        public TextFormatting[] getTextFormatting() {
            return textFormatting;
        }
    }
}
