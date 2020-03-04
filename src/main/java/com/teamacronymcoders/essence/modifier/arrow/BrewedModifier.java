package com.teamacronymcoders.essence.modifier.arrow;

import com.teamacronymcoders.essence.api.modifier.ArrowCoreModifier;
import com.teamacronymcoders.essence.api.modifier.core.INBTModifier;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.utils.helpers.EssenceUtilHelper;
import net.minecraft.client.Minecraft;
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
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class BrewedModifier extends ArrowCoreModifier implements INBTModifier {

    public static final String TAG_EFFECTS = "Effects";
    private List<EffectInstance> effects = new ArrayList<>();

    public BrewedModifier() {
        super(1);
    }

    @Override
    public void alterArrowEntity(AbstractArrowEntity abstractArrowEntity, PlayerEntity shooter, float velocity, int level) {
        if (abstractArrowEntity instanceof ArrowEntity) {
            ArrowEntity arrowEntity = (ArrowEntity) abstractArrowEntity;
            for (EffectInstance instance : effects) {
                arrowEntity.addEffect(instance);
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
    public boolean canApplyOnItemStack(ItemStack stack) {
        return super.canApplyOnItemStack(stack);
    }

    @Override
    public boolean canApplyTogether(Modifier modifier) {
        return super.canApplyTogether(modifier);
    }

    @Override
    public String getTranslationName() {
        return super.getTranslationName();
    }

    @Override
    public List<ITextComponent> getRenderedText(Pair<Integer, CompoundNBT> info) {
        long handle = Minecraft.getInstance().getMainWindow().getHandle();
        List<ITextComponent> textComponents = new ArrayList<>();
        if (InputMappings.isKeyDown(handle, GLFW.GLFW_KEY_LEFT_SHIFT) || InputMappings.isKeyDown(handle, GLFW.GLFW_KEY_RIGHT_SHIFT)) {
            textComponents.add(new TranslationTextComponent(getTranslationName() + ".cleaned").applyTextStyle(TextFormatting.GREEN));
            textComponents.add(new TranslationTextComponent("brewed.contents").applyTextStyle(TextFormatting.GOLD));
            for (EffectInstance instance : effects) {
                if (instance.getPotion().isBeneficial()) {
                    textComponents.add(new StringTextComponent("      ").appendSibling(new TranslationTextComponent(instance.getPotion().getName())).applyTextStyle(TextFormatting.BLUE));
                    textComponents.add(new TranslationTextComponent("brewed.duration", EssenceUtilHelper.getDurationString(instance.getDuration() / 20)).applyTextStyle(TextFormatting.BLUE));
                    textComponents.add(new TranslationTextComponent("brewed.amplifier", instance.getAmplifier()).applyTextStyle(TextFormatting.BLUE));
                } else {
                    textComponents.add(new StringTextComponent("      ").appendSibling(new TranslationTextComponent(instance.getPotion().getName())).applyTextStyle(TextFormatting.RED));
                    textComponents.add(new TranslationTextComponent("brewed.duration", EssenceUtilHelper.getDurationString(instance.getDuration() / 20)).applyTextStyle(TextFormatting.RED));
                    textComponents.add(new TranslationTextComponent("brewed.amplifier", instance.getAmplifier()).applyTextStyle(TextFormatting.RED));
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
