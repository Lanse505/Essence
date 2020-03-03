package com.teamacronymcoders.essence.api.modifier;

import com.teamacronymcoders.essence.api.modifier.core.CoreModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;

public class ArrowCoreModifier extends CoreModifier {

    public ArrowCoreModifier(int maxLevel) {
        super(maxLevel);
    }

    public void alterArrowEntity(AbstractArrowEntity abstractArrowEntity, PlayerEntity shooter, float velocity, int level) {}
}
