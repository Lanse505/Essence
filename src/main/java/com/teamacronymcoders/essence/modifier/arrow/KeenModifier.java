package com.teamacronymcoders.essence.modifier.arrow;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;

public class KeenModifier extends ArrowCoreModifier {

    public KeenModifier() {
        super(4);
    }

    @Override
    public void alterArrowEntity(AbstractArrowEntity abstractArrowEntity, PlayerEntity shooter, float velocity, int level) {
        if (velocity >= (1f - (0.25f * level))) {
            abstractArrowEntity.setIsCritical(true);
        }
    }
}
