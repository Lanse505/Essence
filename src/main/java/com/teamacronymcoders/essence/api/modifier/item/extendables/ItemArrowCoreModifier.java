package com.teamacronymcoders.essence.api.modifier_new.item.extendables;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier_new.item.ItemCoreModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;

public abstract class ItemArrowCoreModifier extends ItemCoreModifier {

    public ItemArrowCoreModifier() {
        this(1);
    }

    public ItemArrowCoreModifier(int maxLevel) {
        this(maxLevel, 0);
    }

    public ItemArrowCoreModifier(int maxLevel, int minLevel) {
        super(maxLevel, minLevel);
    }

    public abstract void alterArrowEntity(AbstractArrowEntity abstractArrowEntity, PlayerEntity shooter, float velocity, ModifierInstance instance);
}
