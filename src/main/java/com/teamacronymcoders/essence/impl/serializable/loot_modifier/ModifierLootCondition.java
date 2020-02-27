package com.teamacronymcoders.essence.impl.serializable.loot_modifier;

import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

public class ModifierLootCondition implements ILootCondition {
    @Override
    public boolean test(LootContext context) {
        return false;
    }
}
