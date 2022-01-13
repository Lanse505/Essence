package com.teamacronymcoders.essence.common.modifier.item.interaction;

import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemInteractionModifier;
import com.teamacronymcoders.essence.common.item.tool.EssenceSword;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

// TODO: IMPLEMENT
public class LifestealModifier extends ItemInteractionModifier {

    public LifestealModifier() {
        super(5);
    }

    @Override
    public boolean canApplyOnObject(ItemStack stack) {
        return stack.getItem() instanceof EssenceSword;
    }
}
