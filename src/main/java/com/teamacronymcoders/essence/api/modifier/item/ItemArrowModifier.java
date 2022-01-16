package com.teamacronymcoders.essence.api.modifier.item;

import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.common.entity.ModifiableArrowEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.fml.common.Mod;

public abstract class ItemArrowModifier extends ItemInteractionModifier {

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
     * Used to modify "onHitBlock" method behaviour.
     *
     * @param bowStack              The bow as an ItemStack.
     * @param modifiableArrowEntity The ModifiableArrowEntity being shot.
     * @param shooter               The shooting Player.
     * @param result                The BlockHitResult of the entity on collision.
     * @param instance              The instance of the modifier.
     */
    public void onHitBlock(ItemStack bowStack, ModifiableArrowEntity modifiableArrowEntity, Player shooter, BlockHitResult result, ModifierInstance instance) {}

    /**
     * Used to modify "onHitEntity" method behaviour.
     *
     * @param bowStack              The bow as an ItemStack.
     * @param modifiableArrowEntity The ModifiableArrowEntity being shot.
     * @param shooter               The shooting Player.
     * @param result                The EntityHitResult of the entity on collision.
     * @param instance              The instance of the modifier.
     */
    public void onHitEntity(ItemStack bowStack, ModifiableArrowEntity modifiableArrowEntity, Player shooter, EntityHitResult result, ModifierInstance instance) {}

    /**
     * Used to modify "alterArrowEntity" method behaviour.
     *
     * @param abstractArrowEntity The ModifiableArrowEntity being shot.
     * @param shooter             The shooting Player.
     * @param velocity            The current velocity of the arrow entity when shot.
     * @param instance            The instance of the modifier.
     */
    public void alterArrowEntity(ModifiableArrowEntity abstractArrowEntity, Player shooter, float velocity, ModifierInstance instance) {};

    /**
     * Used to do final changes to the arrow before it gets added to the world.
     *
     * @param shooter The shooting Player
     * @param arrow The ModifiableArrowEntity being shot
     */
    public void alterFinalEntity(Player shooter, ModifiableArrowEntity arrow) {}
}