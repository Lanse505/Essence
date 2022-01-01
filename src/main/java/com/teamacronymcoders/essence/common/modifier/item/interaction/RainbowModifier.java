package com.teamacronymcoders.essence.common.modifier.item.interaction;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemInteractionCoreModifier;
import com.teamacronymcoders.essence.common.item.tool.EssenceShear;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RainbowModifier extends ItemInteractionCoreModifier {

    @Override
    public List<ItemStack> onShearedAltered(ItemStack stack, Player player, LivingEntity sheared, InteractionHand hand, List<ItemStack> stackList, ModifierInstance instance) {
        if (sheared instanceof Sheep) {
            List<ItemStack> modifiedList = new ArrayList<>();
            for (ItemStack s : stackList) {
                if (s.is(ItemTags.WOOL)) {
                    modifiedList.add(new ItemStack(Sheep.ITEM_BY_DYE.get(DyeColor.values()[Essence.RANDOM.nextInt(DyeColor.values().length)])));
                } else {
                    modifiedList.add(s);
                }
            }
            return modifiedList;
        }
        return stackList;
    }

    @Override
    public boolean canApplyOnObject(ItemStack object) {
        return object.getItem() instanceof EssenceShear;
    }

}
