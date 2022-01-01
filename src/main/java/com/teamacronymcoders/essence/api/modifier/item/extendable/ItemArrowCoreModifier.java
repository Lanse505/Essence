package com.teamacronymcoders.essence.api.modifier.item.extendable;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.common.entity.ModifiableArrowEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

public abstract class ItemArrowCoreModifier extends ItemCoreModifier {

    public ItemArrowCoreModifier() {
        this(1);
    }

    public ItemArrowCoreModifier(int maxLevel) {
        this(maxLevel, 0);
    }

    public ItemArrowCoreModifier(int minLevel, int maxLevel) {
        super(minLevel, maxLevel);
    }

    public abstract void onCollide(ItemStack bowStack, ModifiableArrowEntity abstractArrowEntity, Player shooter, BlockHitResult result, ModifierInstance instance);

    public abstract void alterArrowEntity(ModifiableArrowEntity abstractArrowEntity, Player shooter, float velocity, ModifierInstance instance);
}
