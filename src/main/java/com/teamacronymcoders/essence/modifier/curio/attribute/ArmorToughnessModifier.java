package com.teamacronymcoders.essence.modifier.curio.attribute;

import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemAttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.UUID;

public class ArmorToughnessModifier extends ItemAttributeModifier {

    public static final UUID ARMOR_TOUGHNESS_UUID = UUID.fromString("56a1c8d0-f074-4c7c-bab1-381d35939bbf");

    public ArmorToughnessModifier() {
        super(Attributes.ARMOR_TOUGHNESS, "armor_toughness_modifier", ARMOR_TOUGHNESS_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean canApplyOnObject(ItemStack object) {
        return true;
    }

    @Override
    public ITextComponent getTextComponentName(int level) {
        if (level == -1) {
            return new TranslationTextComponent("modifier.essence.attribute", new TranslationTextComponent("attribute.essence.armor_toughness"));
        }
        return super.getTextComponentName(level);
    }

}
