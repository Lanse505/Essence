package com.teamacronymcoders.essence.modifier.arrow;

import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;

public class KeenModifier extends ArrowCoreModifier {

    public KeenModifier() {
        super(4);
    }

    @Override
    public void alterArrowEntity(AbstractArrowEntity abstractArrowEntity, PlayerEntity shooter, float velocity, ModifierInstance instance) {
        if (velocity >= (1f - (0.25f * instance.getLevel()))) {
            abstractArrowEntity.setIsCritical(true);
        }
    }
}
