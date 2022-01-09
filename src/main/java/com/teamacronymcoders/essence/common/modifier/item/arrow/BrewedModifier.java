package com.teamacronymcoders.essence.common.modifier.item.arrow;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.IModifier;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemArrowCoreModifier;
import com.teamacronymcoders.essence.client.util.keybindings.EssenceKeyHandler;
import com.teamacronymcoders.essence.common.entity.ModifiableArrowEntity;
import com.teamacronymcoders.essence.common.util.helper.EssenceUtilHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

import static com.teamacronymcoders.essence.common.util.helper.EssenceBowHelper.TAG_EFFECTS;

public class BrewedModifier extends ItemArrowCoreModifier {

    private final List<MobEffectInstance> effects = new ArrayList<>();

    public BrewedModifier() {
        super();
    }

    @Override
    public void onCollide(ItemStack bowStack, ModifiableArrowEntity modifiableArrowEntity, Player shooter, BlockHitResult result, ModifierInstance instance) {
    }

    @Override
    public void alterArrowEntity(ModifiableArrowEntity modifiableArrowEntity, Player shooter, float velocity, ModifierInstance instance) {
        for (MobEffectInstance effect : effects) {
            modifiableArrowEntity.addEffect(effect);
        }
    }

    @Override
    public void update(CompoundTag compoundNBT) {
        List<MobEffectInstance> instances = new ArrayList<>();
        final ListTag listNBT = compoundNBT.getList(TAG_EFFECTS, Tag.TAG_COMPOUND);
        for (int i = 0; i < listNBT.size(); i++) {
            final CompoundTag nbt = listNBT.getCompound(i);
            instances.add(MobEffectInstance.load(nbt));
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
    public Component getTextComponentName(int level) {
        if (level == -1) {
            return new TranslatableComponent(getTranslationName() + ".cleaned");
        }
        return super.getTextComponentName(level);
    }

    @Override
    public List<Component> getRenderedText(ModifierInstance instance) {
        List<Component> textComponents = new ArrayList<>();
        if (!EssenceKeyHandler.EXTENDED_INFORMATION.isDown()) {
            textComponents.add(new TextComponent("  ").append(new TranslatableComponent(getTranslationName(), new TranslatableComponent(EssenceKeyHandler.EXTENDED_INFORMATION.getKey().getName())).withStyle(ChatFormatting.GREEN)));
            return textComponents;
        }
        textComponents.add(new TextComponent("    ").append(new TranslatableComponent(getTranslationName() + ".cleaned").withStyle(ChatFormatting.GREEN)));
        textComponents.add(new TextComponent("      ").append(new TranslatableComponent("essence.brewed.contents").withStyle(ChatFormatting.GOLD)));
        for (MobEffectInstance effect : effects) {
            if (effect.getEffect().isBeneficial()) {
                textComponents.add(new TextComponent("        ").append(new TranslatableComponent(effect.getEffect().getDescriptionId())).withStyle(ChatFormatting.BLUE));
                textComponents.add(new TextComponent("          ").append(new TranslatableComponent("essence.brewed.duration", EssenceUtilHelper.getDurationString(effect.getDuration() / 20)).withStyle(ChatFormatting.BLUE)));
                textComponents.add(new TextComponent("          ").append(new TranslatableComponent("essence.brewed.amplifier", effect.getAmplifier()).withStyle(ChatFormatting.BLUE)));
            } else {
                textComponents.add(new TextComponent("        ").append(new TranslatableComponent(effect.getEffect().getDescriptionId())).withStyle(ChatFormatting.RED));
                textComponents.add(new TextComponent("          ").append(new TranslatableComponent("essence.brewed.duration", EssenceUtilHelper.getDurationString(effect.getDuration() / 20)).withStyle(ChatFormatting.RED)));
                textComponents.add(new TextComponent("          ").append(new TranslatableComponent("essence.brewed.amplifier", effect.getAmplifier()).withStyle(ChatFormatting.RED)));
            }
        }
        return textComponents;
    }

    @Override
    public void mergeTags(CompoundTag original, CompoundTag mergeTag) {
        if (original.contains("Effects") && mergeTag.contains("Effects")) {
            ListTag originalEffects = original.getList("Effects", Tag.TAG_COMPOUND);
            ListTag toMergeEffects = mergeTag.getList("Effects", Tag.TAG_COMPOUND);
            for (int i = 0; i < toMergeEffects.size(); i++) {
                CompoundTag effectTag = toMergeEffects.getCompound(i);
                originalEffects.add(effectTag);
            }
        }
    }
}
