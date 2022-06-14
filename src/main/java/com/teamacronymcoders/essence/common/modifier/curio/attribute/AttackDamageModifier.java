package com.teamacronymcoders.essence.common.modifier.curio.attribute;

import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class AttackDamageModifier extends ItemCoreModifier {

    public static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("dc3a5e97-4bbb-4b0f-9698-43fe2babb628");

    public AttackDamageModifier() {
        super(Attributes.ATTACK_DAMAGE, "attack_damage_modifier", ATTACK_DAMAGE_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean canApplyOnObject(ItemStack object) {
        return true;
    }

    @Override
    public Component getTextComponentName(int level) {
        if (level == -1) {
            return Component.translatable("com.teamacronymcoders.essenceapi.modifier.essence.attribute", Component.translatable("attribute.essence.attack_damage"));
        }
        return super.getTextComponentName(level);
    }

}
