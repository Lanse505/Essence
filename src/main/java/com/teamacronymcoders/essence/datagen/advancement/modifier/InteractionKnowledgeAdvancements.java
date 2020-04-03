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

public class InteractionKnowledgeAdvancements extends ExtendableAdvancementProvider {

    private static Advancement interaction;
    private static Advancement cascading;
    private static Advancement expander;
    private static Advancement fiery;
    private static Advancement rainbow;

    public InteractionKnowledgeAdvancements(DataGenerator generator) {
        super(generator, "/knowledge/interaction");
    }

    @Override
    protected void addAdvancements(Consumer<Advancement> consumer) {
        interaction = Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.interaction_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.interaction_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(interaction)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:interaction_modifier");
        cascading = Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.cascading_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.cascading_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(interaction)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.CASCADING_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:cascading_modifier");
        expander = Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.expander_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.expander_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(KnowledgeAdvancementProvider.getKnowledgeModifierRoot())
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.EXPANDER_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:expander_modifier");
        fiery = Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.fiery_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.fiery_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(interaction)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.FIERY_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:fiery_modifier");
        rainbow = Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.rainbow_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.rainbow_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(interaction)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.RAINBOW_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:rainbow_modifier");
    }

    @Override
    public String getName() {
        return "Essence Advancements: [Knowledge/Modifier/Interaction]";
    }

    public static Advancement getInteraction() {
        return interaction;
    }

    public static Advancement getCascading() {
        return cascading;
    }

    public static Advancement getExpander() {
        return expander;
    }

    public static Advancement getFiery() {
        return fiery;
    }

    public static Advancement getRainbow() {
        return rainbow;
    }
}
