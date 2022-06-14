package com.teamacronymcoders.essence.common.modifier.item.interaction;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemInteractionModifier;
import com.teamacronymcoders.essence.common.item.tool.EssenceSword;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

// TODO: IMPLEMENT
public class LifestealModifier extends ItemInteractionModifier {

    public LifestealModifier() {
        super(3);
        EventManager.forge(LivingHurtEvent.class)
                .filter(e -> e.getSource() == DamageSource.GENERIC && e.getSource() != null && e.getSource().getEntity() instanceof Player)
                .process(e -> {
                    Player player = (Player) e.getSource().getEntity();
                    boolean hasLifeSteal = EssenceItemstackModifierHelpers.hasModifier(this, player.getMainHandItem());
                    ModifierInstance instance = EssenceItemstackModifierHelpers.getModifierInstance(player.getMainHandItem(), this);
                    int lifeStealLevel = hasLifeSteal && instance != null ? instance.getLevel() : 0;
                    if (hasLifeSteal && lifeStealLevel > 0) {
                        float dmgToHeal = e.getAmount() * (0.25f * lifeStealLevel);
                        player.heal(dmgToHeal);
                    }
                }).subscribe();
    }

    @Override
    public boolean canApplyOnObject(ItemStack stack) {
        return stack.getItem() instanceof EssenceSword;
    }
}
