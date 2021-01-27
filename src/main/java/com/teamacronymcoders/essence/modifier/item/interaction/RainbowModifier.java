package com.teamacronymcoders.essence.modifier.item.interaction;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemInteractionCoreModifier;
import com.teamacronymcoders.essence.item.tool.EssenceShear;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;

public class RainbowModifier extends ItemInteractionCoreModifier {

  @Override
  public List<ItemStack> onShearedAltered(ItemStack stack, PlayerEntity player, LivingEntity sheared, Hand hand, List<ItemStack> stackList, ModifierInstance instance) {
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
  public boolean canApplyOnObject(ItemStack object) {
    return object.getItem() instanceof EssenceShear;
  }

}
