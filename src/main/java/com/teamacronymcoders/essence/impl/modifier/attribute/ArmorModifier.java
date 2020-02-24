package com.teamacronymcoders.essence.impl.modifier.attribute;

import com.teamacronymcoders.essence.api.modifier.core.CoreModifier;
import com.teamacronymcoders.essence.api.tool.IModifiedCurio;
import com.teamacronymcoders.essence.utils.EssenceReferences;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

public class ArmorModifier extends CoreModifier {

    public ArmorModifier() {
        super(SharedMonsterAttributes.ARMOR, "armor_modifier", EssenceReferences.ARMOR_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean canApplyOnItemStack(ItemStack stack) {
        return stack.getItem() instanceof IModifiedCurio;
    }
}
