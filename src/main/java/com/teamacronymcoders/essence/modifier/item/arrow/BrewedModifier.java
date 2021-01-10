package com.teamacronymcoders.essence.modifier.item.arrow;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.IModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemArrowCoreModifier;
import com.teamacronymcoders.essence.util.helper.EssenceUtilHelper;
import com.teamacronymcoders.essence.util.keybindings.EssenceKeyHandler;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;

public class BrewedModifier extends ItemArrowCoreModifier {

  public static final String TAG_EFFECTS = "Effects";
  private final List<EffectInstance> effects = new ArrayList<>();

  public BrewedModifier () {
  }

  @Override
  public void alterArrowEntity (AbstractArrowEntity abstractArrowEntity, PlayerEntity shooter, float velocity, ModifierInstance<ItemStack> instance) {
    if (abstractArrowEntity instanceof ArrowEntity) {
      ArrowEntity arrowEntity = (ArrowEntity) abstractArrowEntity;
      for (EffectInstance effect : effects) {
        arrowEntity.addEffect(effect);
      }
    }
  }

  @Override
  public void update (CompoundNBT compoundNBT) {
    List<EffectInstance> instances = new ArrayList<>();
    final ListNBT listNBT = compoundNBT.getList(TAG_EFFECTS, Constants.NBT.TAG_COMPOUND);
    for (int i = 0; i < listNBT.size(); i++) {
      final CompoundNBT nbt = listNBT.getCompound(i);
      instances.add(EffectInstance.read(nbt));
    }
    if (!effects.containsAll(instances)) {
      effects.clear();
      effects.addAll(instances);
    }
  }

  @Override
  public boolean canApplyTogether (IModifier modifier) {
    return !(modifier instanceof BrewedModifier);
  }

  @Override
  public ITextComponent getTextComponentName (int level) {
    if (level == -1) {
      return new TranslationTextComponent(getTranslationName() + ".cleaned");
    }
    return super.getTextComponentName(level);
  }

  @Override
  public List<ITextComponent> getRenderedText (ModifierInstance<ItemStack> instance) {
    List<ITextComponent> textComponents = new ArrayList<>();
    if (!EssenceKeyHandler.EXTENDED_INFORMATION.isKeyDown()) {
      textComponents.add(new StringTextComponent("  ").append(new TranslationTextComponent(getTranslationName(), new TranslationTextComponent(EssenceKeyHandler.EXTENDED_INFORMATION.getKey().getTranslationKey())).mergeStyle(TextFormatting.GREEN)));
      return textComponents;
    }
    textComponents.add(new StringTextComponent("    ").append(new TranslationTextComponent(getTranslationName() + ".cleaned").mergeStyle(TextFormatting.GREEN)));
    textComponents.add(new StringTextComponent("      ").append(new TranslationTextComponent("essence.brewed.contents").mergeStyle(TextFormatting.GOLD)));
    for (EffectInstance effect : effects) {
      if (effect.getPotion().isBeneficial()) {
        textComponents.add(new StringTextComponent("        ").append(new TranslationTextComponent(effect.getPotion().getName())).mergeStyle(TextFormatting.BLUE));
        textComponents.add(new StringTextComponent("          ").append(new TranslationTextComponent("essence.brewed.duration", EssenceUtilHelper.getDurationString(effect.getDuration() / 20)).mergeStyle(TextFormatting.BLUE)));
        textComponents.add(new StringTextComponent("          ").append(new TranslationTextComponent("essence.brewed.amplifier", effect.getAmplifier()).mergeStyle(TextFormatting.BLUE)));
      } else {
        textComponents.add(new StringTextComponent("        ").append(new TranslationTextComponent(effect.getPotion().getName())).mergeStyle(TextFormatting.RED));
        textComponents.add(new StringTextComponent("          ").append(new TranslationTextComponent("essence.brewed.duration", EssenceUtilHelper.getDurationString(effect.getDuration() / 20)).mergeStyle(TextFormatting.RED)));
        textComponents.add(new StringTextComponent("          ").append(new TranslationTextComponent("essence.brewed.amplifier", effect.getAmplifier()).mergeStyle(TextFormatting.RED)));
      }
    }
    return textComponents;
  }

}
