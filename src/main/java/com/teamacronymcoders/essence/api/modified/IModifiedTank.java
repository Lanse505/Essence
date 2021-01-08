package com.teamacronymcoders.essence.api.modified;

import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.modifier.item.tank.HoldingModifier;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.util.helper.EssenceUtilHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface IModifiedTank extends IModified<ItemStack> {

    default int getMaxCapacityFromModifiers(int currentCapacity, ItemStack stack) {
        return stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER)
            .map(holder -> holder.getModifierInstances().stream()
                .filter(instance -> instance.getModifier() instanceof HoldingModifier)
                .map(instance -> {
                    HoldingModifier modifier = (HoldingModifier) instance.getModifier();
                    return modifier.getTankSizeModifier(instance.getLevel(), currentCapacity);
                }).reduce(0, Integer::sum)).map(integer -> Math.min(integer, Integer.MAX_VALUE)).orElse(currentCapacity);
    }

    default void addInformationFromModifiers(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        int freeModifiers = stack.getItem() instanceof IModifiedTank ? ((IModifiedTank) stack.getItem()).getFreeModifiers() : 0;
        int maxModifiers = stack.getItem() instanceof IModifiedTank ? ((IModifiedTank) stack.getItem()).getMaxModifiers() : 0;
        list.add(new TranslationTextComponent("tooltip.essence.modifier.free", new StringTextComponent(String.valueOf(freeModifiers)).mergeStyle(EssenceUtilHelper.getTextColor(freeModifiers, maxModifiers))).mergeStyle(TextFormatting.GRAY));
        if ((stack.getOrCreateTag().contains(EssenceItemstackModifierHelpers.TAG_MODIFIERS) && stack.getOrCreateTag().getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND).size() > 0) || flag.isAdvanced()) {
            list.add(new TranslationTextComponent("tooltip.essence.modifier").mergeStyle(TextFormatting.GOLD));
            Map<String, List<ITextComponent>> sorting_map = new HashMap<>();
            stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER)
                .ifPresent(holder -> holder.getModifierInstances()
                    .forEach(instance -> sorting_map.put(instance.getModifier().getRenderedText(instance).get(0).getString(), instance.getModifier().getRenderedText(instance))));
            sorting_map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (string, component) -> component, LinkedHashMap::new))
                .forEach((s, iTextComponents) -> list.addAll(iTextComponents));
            list.add(new StringTextComponent(""));
        }
    }
}
