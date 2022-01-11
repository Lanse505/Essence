package com.teamacronymcoders.essence.common.modifier.item.arrow;

import com.teamacronymcoders.essence.api.modifier.IModifier;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemArrowModifier;
import com.teamacronymcoders.essence.common.entity.ModifiableArrowEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

public class KeenModifier extends ItemArrowModifier {

    public KeenModifier() {
        super(4);
    }

    @Override
    public void onCollide(ItemStack bowStack, ModifiableArrowEntity modifiableArrowEntity, Player shooter, BlockHitResult result, ModifierInstance instance) {
    }

    @Override
    public void alterArrowEntity(ModifiableArrowEntity modifiableArrowEntity, Player shooter, float velocity, ModifierInstance instance) {
        if (velocity >= (1f - (0.25f * instance.getLevel()))) {
            modifiableArrowEntity.setCritArrow(true);
        }
    }

    @Override
    public boolean isCompatibleWith(ItemStack target, IModifier<?> modifier) {
        return !(modifier instanceof KeenModifier);
    }
}
