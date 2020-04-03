package com.teamacronymcoders.essence.serializable.provider.advancement.modifier;

import com.teamacronymcoders.essence.serializable.advancement.criterion.knowledge.UnlockKnowledgeCriterionInstance;
import com.teamacronymcoders.essence.serializable.provider.advancement.ExtendableAdvancementProvider;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.registration.EssenceKnowledgeRegistration;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Consumer;

public class EnchantmentKnowledgeAdvancementProvider extends ExtendableAdvancementProvider {

    public EnchantmentKnowledgeAdvancementProvider(DataGenerator generator) {
        super(generator, "/knowledge/enchantment");
    }

    @Override
    protected void addAdvancements(Consumer<Advancement> consumer) {
        Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.efficiency_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.efficiency_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.EFFICIENCY_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:efficiency_modifier");
        Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.enchantment_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.enchantment_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:enchantment_modifier");
        Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.infinity_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.infinity_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.INFINITY_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:infinity_modifier");
        Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.knockback_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.knockback_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.KNOCKBACK_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knockback_modifier");
        Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.luck_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.luck_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.LUCK_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:luck_modifier");
        Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.mending_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.mending_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.MENDING_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:mending_modifier");
        Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.silk_touch_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.silk_touch_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.SILK_TOUCH_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:silk_touch_modifier");
        Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.strengthened_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.strengthened_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.STRENGTHENED_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:strengthened_modifier");
        Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.unbreaking_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.unbreaking_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.UNBREAKING_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:unbreaking_modifier");
    }

    @Override
    public String getName() {
        return "Essence Advancements: [Knowledge/Modifier/Enchantment]";
    }
}
