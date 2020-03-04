package com.teamacronymcoders.essence.modifier.attribute;

import com.teamacronymcoders.essence.api.modifier.core.CoreModifier;
import com.teamacronymcoders.essence.api.tool.IModifiedCurio;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class AttackDamageModifier extends CoreModifier {

    public static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("dc3a5e97-4bbb-4b0f-9698-43fe2babb628");

    public AttackDamageModifier() {
        super(SharedMonsterAttributes.ATTACK_DAMAGE, "attack_damage_modifier", ATTACK_DAMAGE_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean canApplyOnItemStack(ItemStack stack) {
        return stack.getItem() instanceof IModifiedCurio;
    }
}
