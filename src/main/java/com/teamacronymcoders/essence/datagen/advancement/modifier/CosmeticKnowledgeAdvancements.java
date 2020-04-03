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

public class CosmeticKnowledgeAdvancements extends ExtendableAdvancementProvider {

    private static Advancement cosmetic;
    private static Advancement enchanted;

    public CosmeticKnowledgeAdvancements(DataGenerator generator) {
        super(generator, "/knowledge/cosmetic");
    }

    @Override
    protected void addAdvancements(Consumer<Advancement> consumer) {
        cosmetic = Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.cosmetic_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.cosmetic_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(KnowledgeAdvancementProvider.getKnowledgeModifierRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:cosmetic_modifier");
        enchanted = Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.enchanted_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.enchanted_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(cosmetic)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.ENCHANTED_MODIFIER_KNOWLEDGE.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:enchanted_modifier");
    }

    @Override
    public String getName() {
        return "Essence Advancements: [Knowledge/Modifier/Cosmetic]";
    }

    public static Advancement getCosmetic() {
        return cosmetic;
    }

    public static Advancement getEnchanted() {
        return enchanted;
    }
}
