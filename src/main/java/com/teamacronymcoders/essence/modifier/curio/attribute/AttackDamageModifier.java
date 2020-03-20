package com.teamacronymcoders.essence.modifier.curio.attribute;

import com.teamacronymcoders.essence.api.modifier.item.extendables.ItemAttributeModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class AttackDamageModifier extends ItemAttributeModifier {

    public static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("dc3a5e97-4bbb-4b0f-9698-43fe2babb628");

    public AttackDamageModifier() {
        super(SharedMonsterAttributes.ATTACK_DAMAGE, "attack_damage_modifier", ATTACK_DAMAGE_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean canApplyOnObject(ItemStack object) {
        return true;
    }

}
