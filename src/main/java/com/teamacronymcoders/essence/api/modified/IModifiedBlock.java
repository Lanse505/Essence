package com.teamacronymcoders.essence.api.modified;

import com.teamacronymcoders.essence.api.modifier.block.BlockCoreModifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.util.helper.EssenceUtilHelper;
import com.teamacronymcoders.essence.util.tier.IEssenceBaseTier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;

public interface IModifiedBlock extends IModified<Block> {

  default float getBlockHardnessFromModifiers (BlockState state, IBlockReader world, BlockPos pos, TileEntity te, float defaultHardness) {
    return te.getCapability(EssenceCoreCapability.BLOCK_MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream()
            .filter(instance -> instance.getModifier() instanceof BlockCoreModifier)
            .map(instance -> {
              BlockCoreModifier modifier = (BlockCoreModifier) instance.getModifier();
              return modifier.getModifiedBlockHardness(state, world, pos);
            }).reduce(0f, Float::sum)).orElse(defaultHardness);
  }

  default float getExplosionResistanceFromModifiers (BlockState state, IBlockReader reader, BlockPos pos, @Nullable Entity exploder, Explosion explosion, TileEntity te, float defaultExplosionResistance) {
    return te.getCapability(EssenceCoreCapability.BLOCK_MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream()
            .filter(instance -> instance.getModifier() instanceof BlockCoreModifier)
            .map(instance -> {
              BlockCoreModifier modifier = (BlockCoreModifier) instance.getModifier();
              return modifier.getModifiedExplosionResistance(state, reader, pos, exploder, explosion);
            }).reduce(0f, Float::sum)).orElse(defaultExplosionResistance);
  }

  default int getLightValueFromModifiers (BlockState state, IBlockReader world, BlockPos pos, TileEntity te, int defaultLightValue) {
    return te.getCapability(EssenceCoreCapability.BLOCK_MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream()
            .filter(instance -> instance.getModifier() instanceof BlockCoreModifier)
            .map(instance -> {
              BlockCoreModifier modifier = (BlockCoreModifier) instance.getModifier();
              return modifier.getModifiedLightValue(state, world, pos);
            }).reduce(0, Integer::sum)).orElse(defaultLightValue);
  }

  default void addInformation (ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> list, ITooltipFlag flag, IEssenceBaseTier tier) {
    int freeModifiers = stack.getItem() instanceof IModifiedTool ? ((IModifiedTool) stack.getItem()).getFreeModifiers() : 0;
    int maxModifiers = stack.getItem() instanceof IModifiedTool ? ((IModifiedTool) stack.getItem()).getMaxModifiers() : 0;
    list.add(new TranslationTextComponent("tooltip.essence.tool.tier").mergeStyle(TextFormatting.GRAY).append(new TranslationTextComponent(tier.getLocaleString()).mergeStyle(tier.getRarity().color)));
    list.add(new TranslationTextComponent("tooltip.essence.modifier.free", new StringTextComponent(String.valueOf(freeModifiers)).mergeStyle(EssenceUtilHelper.getTextColor(freeModifiers, maxModifiers))).mergeStyle(TextFormatting.GRAY));
    if (stack.getOrCreateTag().contains(EssenceItemstackModifierHelpers.TAG_MODIFIERS)) {
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
