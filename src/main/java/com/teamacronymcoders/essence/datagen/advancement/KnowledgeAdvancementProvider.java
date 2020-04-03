package com.teamacronymcoders.essence.datagen.advancement;

import com.teamacronymcoders.essence.datagen.advancement.modifier.*;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Consumer;

public class KnowledgeAdvancementProvider extends ExtendableAdvancementProvider{

    private static Advancement knowledge_root;
    private static Advancement knowledge_modifier_root;
    private static Advancement knowledge_tool_root;

    public KnowledgeAdvancementProvider(DataGenerator generatorIn) {
        super(generatorIn, "/knowledge");
        generatorIn.addProvider(new ArrowKnowledgeAdvancements(generatorIn));
        generatorIn.addProvider(new AttributeKnowledgeAdvancements(generatorIn));
        generatorIn.addProvider(new CosmeticKnowledgeAdvancements(generatorIn));
        generatorIn.addProvider(new EnchantmentKnowledgeAdvancementProvider(generatorIn));
        generatorIn.addProvider(new InteractionKnowledgeAdvancements(generatorIn));
    }

    @Override
    protected void addAdvancements(Consumer<Advancement> consumer) {
        knowledge_root = Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.root.title"),
                new TranslationTextComponent("advancements.essence.knowledge.root.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, true
            )
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:root");
        knowledge_modifier_root = Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.root_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.root_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, true
            )
            .withParent(knowledge_root)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:root_modifier");
        knowledge_tool_root = Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.root_tools.title"),
                new TranslationTextComponent("advancements.essence.knowledge.root_tools.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, true
            )
            .withParent(knowledge_root)
            .withCriterion("essence_ore", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:root_tools");
    }

    @Override
    public String getName() {
        return "Essence Advancements: [Knowledge]";
    }

    public static Advancement getKnowledgeRoot() {
        return knowledge_root;
    }

    public static Advancement getKnowledgeModifierRoot() {
        return knowledge_modifier_root;
    }

    public static Advancement getKnowledgeToolRoot() {
        return knowledge_tool_root;
    }
}
