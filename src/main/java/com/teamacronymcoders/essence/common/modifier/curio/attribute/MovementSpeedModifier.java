package com.teamacronymcoders.essence.common.modifier.curio.attribute;

import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class MovementSpeedModifier extends ItemCoreModifier {

    public static final UUID MOVEMENT_SPEED_UUID = UUID.fromString("90742179-0f40-4ab8-8254-70ea451c9afb");

    public MovementSpeedModifier() {
        super(Attributes.MOVEMENT_SPEED, "movement_speed_modifier", MOVEMENT_SPEED_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean canApplyOnObject(ItemStack object) {
        return true;
    }

    @Override
    public Component getTextComponentName(int level) {
        if (level == -1) {
            return new TranslatableComponent("modifier.essence.attribute", new TranslatableComponent("attribute.essence.movement_speed"));
        }
        return super.getTextComponentName(level);
    }

}
