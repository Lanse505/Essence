package com.teamacronymcoders.essence.modifier.item.arrow;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.IModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemArrowCoreModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;

public class KeenModifier extends ItemArrowCoreModifier {

    public KeenModifier() {
        super(4);
    }

    @Override
    public void alterArrowEntity(AbstractArrowEntity abstractArrowEntity, PlayerEntity shooter, float velocity, ModifierInstance<ItemStack> instance) {
        if (velocity >= (1f - (0.25f * instance.getLevel()))) {
            abstractArrowEntity.setIsCritical(true);
        }
    }

    @Override
    public boolean isCompatibleWith(IModifier modifier) {
        return !(modifier instanceof KeenModifier);
    }

}
