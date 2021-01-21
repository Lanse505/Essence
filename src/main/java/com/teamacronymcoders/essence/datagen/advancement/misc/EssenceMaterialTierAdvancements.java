package com.teamacronymcoders.essence.datagen.advancement.misc;

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

public class EssenceMaterialTierAdvancements {

  private static Advancement tiers;
  private static Advancement basic;
  private static Advancement empowered;
  private static Advancement supreme;
  private static Advancement divine;

  public static void addTierAdvancements (Consumer<Advancement> consumer) {
    tiers = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.material.material_tiers.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.material.material_tiers.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(KnowledgeAdvancementProvider.getKnowledgeRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(consumer, "essence:knowledge/material/material_tiers");

    basic = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_INGOT.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.material.basic.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.material.basic.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(tiers)
            .withCriterion("material_tiers", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.BASIC_TIER_KNOWLEDGE.get(), AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_INGOT.get()))
            .register(consumer, "essence:knowledge/material/basic");

    empowered = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_INGOT_EMPOWERED.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.material.empowered.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.material.empowered.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(basic)
            .withCriterion("material_tiers", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.EMPOWERED_TIER_KNOWLEDGE.get(), AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_INGOT_EMPOWERED.get()))
            .register(consumer, "essence:knowledge/material/empowered");

    supreme = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_INGOT_SUPREME.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.material.supreme.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.material.supreme.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(empowered)
            .withCriterion("material_tiers", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.SUPREME_TIER_KNOWLEDGE.get(), AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_INGOT_SUPREME.get()))
            .register(consumer, "essence:knowledge/material/supreme");

    divine = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_INGOT_DIVINE.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.material.divine.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.material.divine.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(supreme)
            .withCriterion("material_tiers", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.DIVINE_TIER_KNOWLEDGE.get(), AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_INGOT_DIVINE.get()))
            .register(consumer, "essence:knowledge/material/divine");
  }

  public static Advancement getTiers () {
    return tiers;
  }

  public static Advancement getBasic () {
    return basic;
  }

  public static Advancement getEmpowered () {
    return empowered;
  }

  public static Advancement getSupreme () {
    return supreme;
  }

  public static Advancement getDivine () {
    return divine;
  }
}
