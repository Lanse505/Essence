package com.teamacronymcoders.essence.common.modifier.item.interaction;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemInteractionCoreModifier;
import com.teamacronymcoders.essence.common.item.tool.EssenceSword;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class LifestealModifier extends ItemInteractionCoreModifier {

    public LifestealModifier() {
        super(5);
    }

    @Override
    public boolean onHitEntity(ItemStack stack, LivingEntity entity, LivingEntity player, ModifierInstance instance) {
        
        return super.onHitEntity(stack, entity, player, instance);
    }

    @Override
    public boolean canApplyOnObject(ItemStack stack) {
        return stack.getItem() instanceof EssenceSword;
    }
}
