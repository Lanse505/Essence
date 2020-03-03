package com.teamacronymcoders.essence.impl.modifier.attribute;

import com.teamacronymcoders.essence.api.modifier.core.CoreModifier;
import com.teamacronymcoders.essence.api.tool.IModifiedCurio;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class MaxHealthModifier extends CoreModifier {

    public static final UUID MAX_HEALTH_UUID = UUID.fromString("baa36be4-749d-42f0-8e4f-a89959a36edf");

    public MaxHealthModifier() {
        super(SharedMonsterAttributes.MAX_HEALTH, "max_health_modifier", MAX_HEALTH_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean canApplyOnItemStack(ItemStack stack) {
        return stack.getItem() instanceof IModifiedCurio;
    }
}
