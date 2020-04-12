package com.teamacronymcoders.essence.datagen.advancement.modifier;

import com.teamacronymcoders.essence.datagen.advancement.ExtendableAdvancementProvider;
import com.teamacronymcoders.essence.datagen.advancement.KnowledgeAdvancementProvider;
import com.teamacronymcoders.essence.serializable.advancement.criterion.knowledge.UnlockKnowledgeCriterionInstance;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.registration.EssenceKnowledgeRegistration;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Consumer;

public class EnchantmentKnowledgeAdvancementProvider {

    private static Advancement enchantment;
    private static Advancement efficiency;
    private static Advancement infinity;
    private static Advancement knockback;
    private static Advancement luck;
    private static Advancement mending;
    private static Advancement silkTouch;
    private static Advancement strengthened;
    private static Advancement unbreaking;

    public static void addEnchantmentAdvancements(Consumer<Advancement> consumer) {
        enchantment = Advancement.Builder.builder()
            .withDisplay(
                ExtendableAdvancementProvider.getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.enchantment_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.enchantment_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(KnowledgeAdvancementProvider.getKnowledgeModifierRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/enchantment/enchantment_modifier");
        efficiency = Advancement.Builder.builder()
            .withDisplay(
                ExtendableAdvancementProvider.getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.efficiency_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.efficiency_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.EFFICIENCY_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/enchantment/efficiency_modifier");
        infinity = Advancement.Builder.builder()
            .withDisplay(
                ExtendableAdvancementProvider.getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.infinity_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.infinity_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.INFINITY_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/enchantment/infinity_modifier");
        knockback = Advancement.Builder.builder()
            .withDisplay(
                ExtendableAdvancementProvider.getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.knockback_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.knockback_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.KNOCKBACK_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/enchantment/knockback_modifier");
        luck = Advancement.Builder.builder()
            .withDisplay(
                ExtendableAdvancementProvider.getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.luck_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.luck_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.LUCK_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/enchantment/luck_modifier");
        mending = Advancement.Builder.builder()
            .withDisplay(
                ExtendableAdvancementProvider.getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.mending_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.mending_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.MENDING_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/enchantment/mending_modifier");
        silkTouch = Advancement.Builder.builder()
            .withDisplay(
                ExtendableAdvancementProvider.getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.silk_touch_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.silk_touch_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.SILK_TOUCH_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/enchantment/silk_touch_modifier");
        strengthened = Advancement.Builder.builder()
            .withDisplay(
                ExtendableAdvancementProvider.getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.strengthened_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.strengthened_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.STRENGTHENED_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/enchantment/strengthened_modifier");
        unbreaking = Advancement.Builder.builder()
            .withDisplay(
                ExtendableAdvancementProvider.getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.unbreaking_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.unbreaking_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.UNBREAKING_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/enchantment/unbreaking_modifier");
    }

    public static Advancement getEnchantment() {
        return enchantment;
    }

    public static Advancement getEfficiency() {
        return efficiency;
    }

    public static Advancement getInfinity() {
        return infinity;
    }

    public static Advancement getKnockback() {
        return knockback;
    }

    public static Advancement getLuck() {
        return luck;
    }

    public static Advancement getMending() {
        return mending;
    }

    public static Advancement getSilkTouch() {
        return silkTouch;
    }

    public static Advancement getStrengthened() {
        return strengthened;
    }

    public static Advancement getUnbreaking() {
        return unbreaking;
    }
}
