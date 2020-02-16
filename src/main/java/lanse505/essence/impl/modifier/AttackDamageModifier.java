package lanse505.essence.impl.modifier;

import lanse505.essence.api.modifier.CoreModifier;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.ResourceLocation;

public class AttackDamageModifier extends CoreModifier {
    public AttackDamageModifier() {
        super(new ResourceLocation(EssenceReferences.MODID, "attack_damage"), SharedMonsterAttributes.ATTACK_DAMAGE, 2.5d, AttributeModifier.Operation.ADDITION, "attack_damage", 4);
    }
}
