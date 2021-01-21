package com.teamacronymcoders.essence.datagen.advancement.modifier;

import com.teamacronymcoders.essence.datagen.advancement.ExtendableAdvancementProvider;
import com.teamacronymcoders.essence.datagen.advancement.KnowledgeAdvancementProvider;
import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.serializable.advancement.criterion.knowledge.UnlockKnowledgeCriterionInstance;
import com.teamacronymcoders.essence.util.registration.EssenceKnowledgeRegistration;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.EntityPredicate.AndPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class CosmeticKnowledgeAdvancements {

  private static Advancement cosmetic;
  private static Advancement enchanted;

  public static void addCosmeticAdvancements (Consumer<Advancement> consumer) {
    cosmetic = Advancement.Builder.builder()
            .withDisplay(
                    ExtendableAdvancementProvider.getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.cosmetic_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.cosmetic_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(KnowledgeAdvancementProvider.getKnowledgeModifierRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(consumer, "essence:knowledge/cosmetic/cosmetic_modifier");
    enchanted = Advancement.Builder.builder()
            .withDisplay(
                    ExtendableAdvancementProvider.getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.enchanted_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.enchanted_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(cosmetic)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.ENCHANTED_MODIFIER_KNOWLEDGE.get(), AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(consumer, "essence:knowledge/cosmetic/enchanted_modifier");
  }

  public static Advancement getCosmetic () {
    return cosmetic;
  }

  public static Advancement getEnchanted () {
    return enchanted;
  }
}
