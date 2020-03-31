package com.teamacronymcoders.essence.modifier.item.arrow;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.IModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemArrowCoreModifier;
import com.teamacronymcoders.essence.util.helper.EssenceUtilHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
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

import java.util.ArrayList;
import java.util.List;

public class BrewedModifier extends ItemArrowCoreModifier {

    public static final String TAG_EFFECTS = "Effects";
    private List<EffectInstance> effects = new ArrayList<>();

    public BrewedModifier() {
    }

    @Override
    public void alterArrowEntity(AbstractArrowEntity abstractArrowEntity, PlayerEntity shooter, float velocity, ModifierInstance<ItemStack> instance) {
        if (abstractArrowEntity instanceof ArrowEntity) {
            ArrowEntity arrowEntity = (ArrowEntity) abstractArrowEntity;
            for (EffectInstance effect : effects) {
                arrowEntity.addEffect(effect);
            }
        }
    }

    @Override
    public void update(CompoundNBT compoundNBT) {
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
    public boolean canApplyTogether(IModifier modifier) {
        return !(modifier instanceof BrewedModifier);
    }

    @Override
    public ITextComponent getTextComponentName(int level) {
        if (level == -1) {
            return new TranslationTextComponent(getTranslationName() + ".cleaned");
        }
        return super.getTextComponentName(level);
    }

    @Override
    public List<ITextComponent> getRenderedText(ModifierInstance<ItemStack> instance) {
        final KeyBinding keyBindSneak = Minecraft.getInstance().gameSettings.keyBindSneak;
        final long handle = Minecraft.getInstance().getMainWindow().getHandle();
        List<ITextComponent> textComponents = new ArrayList<>();
        if (InputMappings.isKeyDown(handle, keyBindSneak.getKey().getKeyCode())) {
            textComponents.add(new StringTextComponent("  ").appendSibling(new TranslationTextComponent(getTranslationName() + ".cleaned").applyTextStyle(TextFormatting.GREEN)));
            textComponents.add(new StringTextComponent("    ").appendSibling(new TranslationTextComponent("brewed.contents").applyTextStyle(TextFormatting.GOLD)));
            for (EffectInstance effect : effects) {
                if (effect.getPotion().isBeneficial()) {
                    textComponents.add(new StringTextComponent("      ").appendSibling(new TranslationTextComponent(effect.getPotion().getName())).applyTextStyle(TextFormatting.BLUE));
                    textComponents.add(new StringTextComponent("        ").appendSibling(new TranslationTextComponent("brewed.duration", EssenceUtilHelper.getDurationString(effect.getDuration() / 20)).applyTextStyle(TextFormatting.BLUE)));
                    textComponents.add(new StringTextComponent("        ").appendSibling(new TranslationTextComponent("brewed.amplifier", effect.getAmplifier()).applyTextStyle(TextFormatting.BLUE)));
                } else {
                    textComponents.add(new StringTextComponent("      ").appendSibling(new TranslationTextComponent(effect.getPotion().getName())).applyTextStyle(TextFormatting.RED));
                    textComponents.add(new StringTextComponent("        ").appendSibling(new TranslationTextComponent("brewed.duration", EssenceUtilHelper.getDurationString(effect.getDuration() / 20)).applyTextStyle(TextFormatting.RED)));
                    textComponents.add(new StringTextComponent("        ").appendSibling(new TranslationTextComponent("brewed.amplifier", effect.getAmplifier()).applyTextStyle(TextFormatting.RED)));
                }
            }
        } else {
            textComponents.add(new TranslationTextComponent(getTranslationName()).applyTextStyle(TextFormatting.GREEN));
        }
        return textComponents;
    }

    public List<EffectInstance> getEffects() {
        return effects;
    }

}
