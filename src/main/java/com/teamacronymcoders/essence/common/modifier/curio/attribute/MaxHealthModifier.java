package com.teamacronymcoders.essence.common.modifier.curio.attribute;

import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class MaxHealthModifier extends ItemCoreModifier {

    public static final UUID MAX_HEALTH_UUID = UUID.fromString("baa36be4-749d-42f0-8e4f-a89959a36edf");

    public MaxHealthModifier() {
        super(Attributes.MAX_HEALTH, "max_health_modifier", MAX_HEALTH_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean canApplyOnObject(ItemStack object) {
        return true;
    }

    @Override
    public Component getTextComponentName(int level) {
        if (level == -1) {
            return new TranslatableComponent("modifier.essence.attribute", new TranslatableComponent("attribute.essence.max_health"));
        }
        return super.getTextComponentName(level);
    }

}
