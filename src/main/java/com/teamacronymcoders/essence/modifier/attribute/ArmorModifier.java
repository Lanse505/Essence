package com.teamacronymcoders.essence.modifier.attribute;

import com.teamacronymcoders.essence.api.modifier.core.CoreModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class ArmorModifier extends CoreModifier {

    public static final UUID ARMOR_UUID = UUID.fromString("fbe6f3d9-80bc-4160-b54c-5020b5a914bc");

    public ArmorModifier() {
        super(SharedMonsterAttributes.ARMOR, "armor_modifier", ARMOR_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean canApplyOnItemStack(ItemStack stack) {
        return true;
    }

}
