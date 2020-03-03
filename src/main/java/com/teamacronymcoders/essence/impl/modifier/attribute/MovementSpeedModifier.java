package com.teamacronymcoders.essence.impl.modifier.attribute;

import com.teamacronymcoders.essence.api.modifier.core.CoreModifier;
import com.teamacronymcoders.essence.api.tool.IModifiedCurio;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class MovementSpeedModifier extends CoreModifier {

    public static final UUID MOVEMENT_SPEED_UUID = UUID.fromString("90742179-0f40-4ab8-8254-70ea451c9afb");

    public MovementSpeedModifier() {
        super(SharedMonsterAttributes.MOVEMENT_SPEED, "movement_speed_modifier", MOVEMENT_SPEED_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean canApplyOnItemStack(ItemStack stack) {
        return stack.getItem() instanceof IModifiedCurio;
    }
}
