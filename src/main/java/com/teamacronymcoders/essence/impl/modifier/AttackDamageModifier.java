package com.teamacronymcoders.essence.impl.modifier;

import com.teamacronymcoders.essence.api.modifier.core.CoreModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;

public class AttackDamageModifier extends CoreModifier {

    public AttackDamageModifier() {
        super(SharedMonsterAttributes.ATTACK_DAMAGE, "attack_damage_modifier", "dc3a5e97-4bbb-4b0f-9698-43fe2babb628", 2.5d, 4, AttributeModifier.Operation.ADDITION);
    }

}
