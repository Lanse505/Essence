package com.teamacronymcoders.essence.datagen.advancement.misc;

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

public class EssenceKnowledgeAdvancements {

  private static Advancement miscKnowledge;
  private static Advancement arborealKnowledge;

  public static void addMiscKnowledge (Consumer<Advancement> consumer) {
    miscKnowledge = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.misc.misc_knowledge.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.misc.misc_knowledge.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(KnowledgeAdvancementProvider.getKnowledgeRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(consumer, "essence:knowledge/misc/misc_knowledge");
    arborealKnowledge = Advancement.Builder.builder()
            .withDisplay(
                    EssenceBlockRegistrate.ESSENCE_WOOD_SAPLING.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.misc.arboreal_knowledge.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.misc.arboreal_knowledge.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(miscKnowledge)
            .withCriterion("arboreal_knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.ARBOREAL_NOTES.get(), AndPredicate.ANY_AND))
            .withCriterion("essence_sapling", InventoryChangeTrigger.Instance.forItems(EssenceBlockRegistrate.ESSENCE_WOOD_SAPLING.get()))
            .register(consumer, "essence:knowledge/misc/arboreal_knowledge");
  }

  public static Advancement getMiscKnowledge () {
    return miscKnowledge;
  }

  public static Advancement getArborealKnowledge () {
    return arborealKnowledge;
  }
}
