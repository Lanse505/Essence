package com.teamacronymcoders.essence.api.modifier.item;

import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.common.entity.ModifiableArrowEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

public abstract class ItemArrowModifier extends ItemCoreModifier {

    public ItemArrowModifier() {
        this(0, 1);
    }

    public ItemArrowModifier(int minLevel) {
        this(minLevel, 1);
    }

    public ItemArrowModifier(int minLevel, int maxLevel) {
        super(minLevel, maxLevel);
    }

    /**
     * Used to modify "onCollide" method behaviour.
     *
     * @param bowStack            The bow as an ItemStack.
     * @param abstractArrowEntity The ModifiableArrowEntity being shot.
     * @param shooter             The shooting Player.
     * @param result              The BlockHitResult of the entity on collision.
     * @param instance            The instance of the modifier.
     */
    public abstract void onCollide(ItemStack bowStack, ModifiableArrowEntity abstractArrowEntity, Player shooter, BlockHitResult result, ModifierInstance instance);

    /**
     * Used to modify "alterArrowEntity" method behaviour.
     *
     * @param abstractArrowEntity The ModifiableArrowEntity being shot.
     * @param shooter             The shooting Player.
     * @param velocity            The current velocity of the arrow entity when shot.
     * @param instance            The instance of the modifier.
     */
    public abstract void alterArrowEntity(ModifiableArrowEntity abstractArrowEntity, Player shooter, float velocity, ModifierInstance instance);
}