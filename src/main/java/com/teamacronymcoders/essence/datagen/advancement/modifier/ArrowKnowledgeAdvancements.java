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

public class ArrowKnowledgeAdvancements extends ExtendableAdvancementProvider {

    public ArrowKnowledgeAdvancements(DataGenerator generator) {
        super(generator, "/knowledge/arrow");
    }

    @Override
    protected void addAdvancements(Consumer<Advancement> consumer) {
        Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.arrow_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.arrow_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withCriterion("essence_ore", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:arrow_modifier");
        Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.brewed_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.brewed_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.BREWED_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("essence_ore", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.ESSENCE_ORE))
            .register(consumer, "essence:brewed_modifier");
        Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.keen_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.keen_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.KEEN_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("essence_ore", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.ESSENCE_ORE))
            .register(consumer, "essence:keen_modifier");
    }

    @Override
    public String getName() {
        return "Essence Advancements: [Knowledge/Modifier/Arrow]";
    }
}
