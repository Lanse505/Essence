package com.teamacronymcoders.essence.impl.modifier.attribute;

import com.teamacronymcoders.essence.api.modifier.core.CoreModifier;
import com.teamacronymcoders.essence.api.tool.IModifiedCurio;
import com.teamacronymcoders.essence.utils.EssenceReferences;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

public class MaxHealthModifier extends CoreModifier {

    public MaxHealthModifier() {
        super(SharedMonsterAttributes.MAX_HEALTH, "max_health_modifier", EssenceReferences.MAX_HEALTH_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean canApplyOnItemStack(ItemStack stack) {
        return stack.getItem() instanceof IModifiedCurio;
    }
}
