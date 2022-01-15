package com.teamacronymcoders.essence.common.modifier.item.interaction;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.ItemArrowModifier;
import com.teamacronymcoders.essence.api.modifier.item.ItemInteractionModifier;
import com.teamacronymcoders.essence.common.entity.ModifiableArrowEntity;
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

public class RainbowModifier extends ItemArrowModifier {

    @Override
    public void alterFinalEntity(Player shooter, ModifiableArrowEntity arrow) {
        if (shooter.getLookAngle().y >= 0.95 && !shooter.getLevel().isRaining() && shooter.getLevel().canSeeSky(shooter.eyeBlockPosition())) {
            arrow.discard();
            shooter.getLevel().setRainLevel(1.0F);
        }
    }

    @Override
    public List<ItemStack> onSheared(ItemStack stack, Player player, LivingEntity sheared, InteractionHand hand, List<ItemStack> stackList, ModifierInstance instance) {
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
