package com.teamacronymcoders.essence.modifier.curio.attribute;

import com.teamacronymcoders.essence.api.modifier.item.extendables.ItemAttributeModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class MovementSpeedModifier extends ItemAttributeModifier {

    public static final UUID MOVEMENT_SPEED_UUID = UUID.fromString("90742179-0f40-4ab8-8254-70ea451c9afb");

    public MovementSpeedModifier() {
        super(SharedMonsterAttributes.MOVEMENT_SPEED, "movement_speed_modifier", MOVEMENT_SPEED_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean canApplyOnObject(ItemStack object) {
        return true;
    }

}
