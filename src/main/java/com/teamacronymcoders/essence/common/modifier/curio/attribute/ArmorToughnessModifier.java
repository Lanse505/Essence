package com.teamacronymcoders.essence.common.modifier.curio.attribute;

import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class ArmorToughnessModifier extends ItemCoreModifier {

    public static final UUID ARMOR_TOUGHNESS_UUID = UUID.fromString("56a1c8d0-f074-4c7c-bab1-381d35939bbf");

    public ArmorToughnessModifier() {
        super(Attributes.ARMOR_TOUGHNESS, "armor_toughness_modifier", ARMOR_TOUGHNESS_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean canApplyOnObject(ItemStack object) {
        return true;
    }

    @Override
    public Component getTextComponentName(int level) {
        if (level == -1) {
            return Component.translatable("modifier.essence.attribute", Component.translatable("attribute.essence.armor_toughness"));
        }
        return super.getTextComponentName(level);
    }

}
