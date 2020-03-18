package com.teamacronymcoders.essence.modifier.interaction;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
import com.teamacronymcoders.essence.api.tool.modifierholder.ModifierInstance;
import com.teamacronymcoders.essence.items.tools.EssenceShear;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.List;

public class RainbowModifier extends InteractionCoreModifier {

    public RainbowModifier() {
        super(1);
    }

    @Override
    public List<ItemStack> onSheared(ItemStack stack, PlayerEntity player, LivingEntity sheared, Hand hand, List<ItemStack> stackList, ModifierInstance instance) {
        if (sheared instanceof SheepEntity) {
            List<ItemStack> modifiedList = new ArrayList<>();
            for (ItemStack s : stackList) {
                if (s.getItem().isIn(ItemTags.WOOL)) {
                    modifiedList.add(new ItemStack(SheepEntity.WOOL_BY_COLOR.get(DyeColor.values()[Essence.RANDOM.nextInt(DyeColor.values().length)])));
                } else {
                    modifiedList.add(s);
                }
            }
            return modifiedList;
        }
        return stackList;
    }

    @Override
    public boolean canApplyOnItemStack(ItemStack stack) {
        return stack.getItem() instanceof EssenceShear;
    }

}
