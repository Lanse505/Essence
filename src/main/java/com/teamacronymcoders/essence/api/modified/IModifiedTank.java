package com.teamacronymcoders.essence.api.modified;

import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.modifier.item.tank.HoldingModifier;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.util.helper.EssenceUtilHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface IModifiedTank extends IModified {

  default int getMaxCapacityFromModifiers(int currentCapacity, ItemStack stack) {
    return stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER)
            .map(holder -> holder.getModifierInstances().stream()
                    .filter(instance -> instance.getModifier() instanceof HoldingModifier)
                    .map(instance -> {
                      HoldingModifier modifier = (HoldingModifier) instance.getModifier();
                      return modifier.getTankSizeModifier(instance.getLevel(), currentCapacity);
                    }).reduce(0, Integer::sum)).orElse(currentCapacity);
  }

  default void addInformationFromModifiers(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
    int freeModifiers = stack.getItem() instanceof IModifiedTank ? ((IModifiedTank) stack.getItem()).getFreeModifiers() : 0;
    int maxModifiers = stack.getItem() instanceof IModifiedTank ? ((IModifiedTank) stack.getItem()).getMaxModifiers() : 0;
    list.add(new TranslatableComponent("tooltip.essence.modifier.free", new TextComponent(String.valueOf(freeModifiers)).withStyle(EssenceUtilHelper.getTextColor(freeModifiers, maxModifiers))).withStyle(ChatFormatting.GRAY));
    if (stack.getOrCreateTag().contains(EssenceItemstackModifierHelpers.TAG_MODIFIERS) && !stack.getOrCreateTag().getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Tag.TAG_COMPOUND).isEmpty()) {
      list.add(new TranslatableComponent("tooltip.essence.modifier").withStyle(ChatFormatting.GOLD));
      Map<String, List<Component>> sorting_map = new HashMap<>();
      stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER)
              .ifPresent(holder -> holder.getModifierInstances()
                      .forEach(instance -> sorting_map.put(instance.getModifier().getRenderedText(instance).get(0).getString(), instance.getModifier().getRenderedText(instance))));
      sorting_map.entrySet().stream()
              .sorted(Map.Entry.comparingByKey())
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (string, component) -> component, LinkedHashMap::new))
              .forEach((s, iTextComponents) -> list.addAll(iTextComponents));
      list.add(new TextComponent(""));
    }
  }
}
