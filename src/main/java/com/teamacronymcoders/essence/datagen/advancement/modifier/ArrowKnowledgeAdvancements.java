package com.teamacronymcoders.essence.datagen.advancement.modifier;

import com.teamacronymcoders.essence.datagen.advancement.ExtendableAdvancementProvider;
import com.teamacronymcoders.essence.datagen.advancement.KnowledgeAdvancementProvider;
import com.teamacronymcoders.essence.registrate.EssenceBlockRegistrate;
import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.serializable.advancement.criterion.knowledge.UnlockKnowledgeCriterionInstance;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.registration.EssenceKnowledgeRegistration;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.EntityPredicate.AndPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class ArrowKnowledgeAdvancements {

  private static Advancement arrow;
  private static Advancement brewed;
  private static Advancement keen;

  public static void addArrowAdvancements (Consumer<Advancement> consumer) {
    arrow = Advancement.Builder.builder()
            .withDisplay(
                    ExtendableAdvancementProvider.getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.arrow_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.arrow_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(KnowledgeAdvancementProvider.getKnowledgeModifierRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(consumer, "essence:knowledge/arrow/arrow_modifier");
    brewed = Advancement.Builder.builder()
            .withDisplay(
                    ExtendableAdvancementProvider.getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.brewed_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.brewed_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(arrow)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.BREWED_MODIFIER_KNOWLEDGE.get(), AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceBlockRegistrate.ESSENCE_ORE.get()))
            .register(consumer, "essence:knowledge/arrow/brewed_modifier");
    keen = Advancement.Builder.builder()
            .withDisplay(
                    ExtendableAdvancementProvider.getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.keen_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.keen_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(arrow)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.KEEN_MODIFIER_KNOWLEDGE.get(), AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceBlockRegistrate.ESSENCE_ORE.get()))
            .register(consumer, "essence:knowledge/arrow/keen_modifier");
  }

  public static Advancement getArrow () {
    return arrow;
  }

  public static Advancement getBrewed () {
    return brewed;
  }

  public static Advancement getKeen () {
    return keen;
  }
}
