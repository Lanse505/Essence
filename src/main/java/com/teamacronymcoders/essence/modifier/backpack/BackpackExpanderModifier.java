package com.teamacronymcoders.essence.modifier.backpack;

import com.teamacronymcoders.essence.api.tool.modifierholder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.antlr.v4.runtime.misc.Triple;

import java.util.HashMap;
import java.util.Map;

public class BackpackExpanderModifier extends BackpackCoreModifier {

    private static final Int2ObjectMap<Triple<Integer, Integer, Integer>> levelToSizeMap = new Int2ObjectArrayMap<>();

    static {
        Map<Integer, Triple<Integer, Integer, Integer>> tier1 = new HashMap<>();

    }

    public BackpackExpanderModifier() {
        super(2);
    }

    @Override
    public void onInventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem, ModifierInstance instance) {
        int level = instance.getLevel();
        if (level == 1) {

        } else if (level == 2) {

        }
        super.onInventoryTick(stack, world, entity, inventorySlot, isCurrentItem, instance);
    }

    @Override
    public boolean canApplyOnItemStack(ItemStack stack) {
        return super.canApplyOnItemStack(stack);
    }

    @Override
    public boolean canApplyTogether(Modifier modifier) {
        return super.canApplyTogether(modifier);
    }
}
