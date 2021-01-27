package com.teamacronymcoders.essence.registrate;

import com.teamacronymcoders.essence.serializable.advancement.criterion.knowledge.UnlockKnowledgeCriterionInstance;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class EssenceAdvancementRegistrate {

  // Core
  private static Advancement core;

  // Knowledge
  private static Advancement knowledgeRoot;
  private static Advancement knowledgeModifierRoot;
  private static Advancement knowledgeToolRoot;

  // Modifiers
  // Arrow
  private static Advancement arrow;
  private static Advancement brewed;
  private static Advancement keen;
  // Attributes
  private static Advancement attribute;
  private static Advancement armor;
  private static Advancement armorToughness;
  private static Advancement attackDamage;
  private static Advancement maxHealth;
  private static Advancement movementSpeed;
  // Cosmetic
  private static Advancement cosmetic;
  private static Advancement enchanted;
  // Enchantments
  private static Advancement enchantment;
  private static Advancement efficiency;
  private static Advancement infinity;
  private static Advancement knockback;
  private static Advancement luck;
  private static Advancement mending;
  private static Advancement silkTouch;
  private static Advancement strengthened;
  private static Advancement unbreaking;
  // Interaction
  private static Advancement interaction;
  private static Advancement cascading;
  private static Advancement expander;
  private static Advancement fiery;
  private static Advancement rainbow;

  // Tiers
  private static Advancement tiers;
  private static Advancement basic;
  private static Advancement empowered;
  private static Advancement supreme;
  private static Advancement divine;

  // Tool-Types
  private static Advancement toolTypes;
  private static Advancement axe;
  private static Advancement bow;
  private static Advancement hoe;
  private static Advancement omniTool;
  private static Advancement pickaxe;
  private static Advancement shear;
  private static Advancement shovel;
  private static Advancement sword;

  // Misc
  private static Advancement miscKnowledge;
  private static Advancement arborealKnowledge;

  public static void init(Registrate registrate) {
    registrate.addDataGenerator(ProviderType.ADVANCEMENT, provider -> {
      coreAdvancements(provider);
      knowledgeAdvancements(provider);
      modifierAdvancements(provider);
      miscAdvancements(provider);
    });
  }

  public static void coreAdvancements(RegistrateAdvancementProvider provider) {
    core = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.core.root.title"),
                    new TranslationTextComponent("advancements.essence.core.root.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withCriterion("essence_ore", InventoryChangeTrigger.Instance.forItems(EssenceBlockRegistrate.ESSENCE_ORE.get()))
            .register(provider, "essence:core/root");
  }

  public static void knowledgeAdvancements(RegistrateAdvancementProvider provider) {
    knowledgeRoot = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.root.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.root.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/root");
    knowledgeModifierRoot = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.root_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.root_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(getKnowledgeRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/root_modifier");
    knowledgeToolRoot = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.root_tools.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.root_tools.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(getKnowledgeRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/root_tools");
  }

  public static void modifierAdvancements(RegistrateAdvancementProvider provider) {
    arrow = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.arrow_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.arrow_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(getKnowledgeModifierRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/arrow/arrow_modifier");
    brewed = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.brewed_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.brewed_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(arrow)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.BREWED_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceBlockRegistrate.ESSENCE_ORE.get()))
            .register(provider, "essence:knowledge/arrow/brewed_modifier");
    keen = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.keen_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.keen_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(arrow)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.KEEN_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceBlockRegistrate.ESSENCE_ORE.get()))
            .register(provider, "essence:knowledge/arrow/keen_modifier");
    attribute = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.attribute_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.attribute_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(getKnowledgeModifierRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/attribute/attribute_modifier");
    armor = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.armor_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.armor_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(attribute)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.ARMOR_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/attribute/armor_modifier");
    armorToughness = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.armor_toughness_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.armor_toughness_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(attribute)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.ARMOR_TOUGHNESS_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/attribute/armor_toughness_modifier");
    attackDamage = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.attack_damage_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.attack_damage_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(attribute)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.ATTACK_DAMAGE_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/attribute/attack_damage_modifier");
    maxHealth = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.max_health_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.max_health_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(attribute)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.MAX_HEALTH_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/attribute/max_health_modifier");
    movementSpeed = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.movement_speed_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.movement_speed_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(attribute)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.MOVEMENT_SPEED_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/attribute/movement_speed_modifier");
    cosmetic = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.cosmetic_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.cosmetic_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(getKnowledgeModifierRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/cosmetic/cosmetic_modifier");
    enchanted = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.enchanted_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.enchanted_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(cosmetic)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.ENCHANTED_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/cosmetic/enchanted_modifier");
    enchantment = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.enchantment_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.enchantment_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(getKnowledgeModifierRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/enchantment/enchantment_modifier");
    efficiency = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.efficiency_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.efficiency_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.EFFICIENCY_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/enchantment/efficiency_modifier");
    infinity = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.infinity_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.infinity_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.INFINITY_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/enchantment/infinity_modifier");
    knockback = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.knockback_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.knockback_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.KNOCKBACK_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/enchantment/knockback_modifier");
    luck = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.luck_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.luck_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.LUCK_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/enchantment/luck_modifier");
    mending = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.mending_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.mending_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.MENDING_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/enchantment/mending_modifier");
    silkTouch = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.silk_touch_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.silk_touch_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.SILK_TOUCH_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/enchantment/silk_touch_modifier");
    strengthened = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.strengthened_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.strengthened_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.STRENGTHENED_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/enchantment/strengthened_modifier");
    unbreaking = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.unbreaking_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.unbreaking_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(enchantment)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.UNBREAKING_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/enchantment/unbreaking_modifier");
    interaction = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.interaction_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.interaction_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(getKnowledgeModifierRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/interaction/interaction_modifier");
    cascading = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.cascading_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.cascading_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(interaction)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.CASCADING_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/interaction/cascading_modifier");
    expander = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.expander_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.expander_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(getKnowledgeModifierRoot())
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.EXPANDER_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/interaction/expander_modifier");
    fiery = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.fiery_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.fiery_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(interaction)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.FIERY_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/interaction/fiery_modifier");
    rainbow = Advancement.Builder.builder()
            .withDisplay(
                    getDefaultIcon(),
                    new TranslationTextComponent("advancements.essence.knowledge.rainbow_modifier.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.rainbow_modifier.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, true, false, true
            )
            .withParent(interaction)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.RAINBOW_MODIFIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/interaction/rainbow_modifier");
  }

  public static void miscAdvancements(RegistrateAdvancementProvider provider) {
    tiers = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.material.material_tiers.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.material.material_tiers.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(getKnowledgeRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/material/material_tiers");

    basic = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_INGOT.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.material.basic.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.material.basic.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(tiers)
            .withCriterion("material_tiers", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.BASIC_TIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_INGOT.get()))
            .register(provider, "essence:knowledge/material/basic");

    empowered = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_INGOT_EMPOWERED.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.material.empowered.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.material.empowered.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(basic)
            .withCriterion("material_tiers", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.EMPOWERED_TIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_INGOT_EMPOWERED.get()))
            .register(provider, "essence:knowledge/material/empowered");

    supreme = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_INGOT_SUPREME.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.material.supreme.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.material.supreme.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(empowered)
            .withCriterion("material_tiers", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.SUPREME_TIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_INGOT_SUPREME.get()))
            .register(provider, "essence:knowledge/material/supreme");

    divine = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_INGOT_DIVINE.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.material.divine.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.material.divine.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(supreme)
            .withCriterion("material_tiers", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.DIVINE_TIER_KNOWLEDGE.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_INGOT_DIVINE.get()))
            .register(provider, "essence:knowledge/material/divine");
    toolTypes = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_AXE.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.tool_type.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.tool_type.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(getKnowledgeToolRoot())
            .withCriterion("tool_crafting", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.TOOL_CRAFTING.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/tools/tool_types");

    axe = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_AXE.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.axe.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.axe.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_AXE.get()))
            .register(provider, "essence:knowledge/tools/essence_axe");

    bow = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_BOW.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.bow.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.bow.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_BOW.get()))
            .register(provider, "essence:knowledge/tools/essence_bow");

    hoe = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_HOE.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.hoe.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.hoe.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_HOE.get()))
            .register(provider, "essence:knowledge/tools/essence_hoe");

    omniTool = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_OMNITOOL.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.omnitool.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.omnitool.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_OMNITOOL.get()))
            .register(provider, "essence:knowledge/tools/essence_omnitool");

    pickaxe = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_PICKAXE.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.pickaxe.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.pickaxe.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_PICKAXE.get()))
            .register(provider, "essence:knowledge/tools/essence_pickaxe");

    shear = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_SHEAR.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.shear.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.shear.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_SHEAR.get()))
            .register(provider, "essence:knowledge/tools/essence_shear");

    shovel = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_SHOVEL.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.shovel.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.shovel.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_SHOVEL.get()))
            .register(provider, "essence:knowledge/tools/essence_shovel");

    sword = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_SWORD.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.sword.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.sword.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_SWORD.get()))
            .register(provider, "essence:knowledge/tools/essence_sword");
    miscKnowledge = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.misc.misc_knowledge.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.misc.misc_knowledge.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(getKnowledgeRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(provider, "essence:knowledge/misc/misc_knowledge");
    arborealKnowledge = Advancement.Builder.builder()
            .withDisplay(
                    EssenceBlockRegistrate.ESSENCE_WOOD_SAPLING.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.misc.arboreal_knowledge.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.misc.arboreal_knowledge.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(miscKnowledge)
            .withCriterion("arboreal_knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistrate.ARBOREAL_NOTES.get(), EntityPredicate.AndPredicate.ANY_AND))
            .withCriterion("essence_sapling", InventoryChangeTrigger.Instance.forItems(EssenceBlockRegistrate.ESSENCE_WOOD_SAPLING.get()))
            .register(provider, "essence:knowledge/misc/arboreal_knowledge");
  }

  public static Item getDefaultIcon() {
    return EssenceItemRegistrate.ESSENCE_CRYSTAL.get();
  }

  public static Advancement getCore() {
    return core;
  }

  public static Advancement getKnowledgeRoot() {
    return knowledgeRoot;
  }

  public static Advancement getKnowledgeModifierRoot() {
    return knowledgeModifierRoot;
  }

  public static Advancement getKnowledgeToolRoot() {
    return knowledgeToolRoot;
  }

  public static Advancement getArrow() {
    return arrow;
  }

  public static Advancement getBrewed() {
    return brewed;
  }

  public static Advancement getKeen() {
    return keen;
  }

  public static Advancement getAttribute() {
    return attribute;
  }

  public static Advancement getArmor() {
    return armor;
  }

  public static Advancement getArmorToughness() {
    return armorToughness;
  }

  public static Advancement getAttackDamage() {
    return attackDamage;
  }

  public static Advancement getMaxHealth() {
    return maxHealth;
  }

  public static Advancement getMovementSpeed() {
    return movementSpeed;
  }

  public static Advancement getCosmetic() {
    return cosmetic;
  }

  public static Advancement getEnchanted() {
    return enchanted;
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

  public static Advancement getTiers() {
    return tiers;
  }

  public static Advancement getBasic() {
    return basic;
  }

  public static Advancement getEmpowered() {
    return empowered;
  }

  public static Advancement getSupreme() {
    return supreme;
  }

  public static Advancement getDivine() {
    return divine;
  }

  public static Advancement getToolTypes() {
    return toolTypes;
  }

  public static Advancement getAxe() {
    return axe;
  }

  public static Advancement getBow() {
    return bow;
  }

  public static Advancement getHoe() {
    return hoe;
  }

  public static Advancement getOmniTool() {
    return omniTool;
  }

  public static Advancement getPickaxe() {
    return pickaxe;
  }

  public static Advancement getShear() {
    return shear;
  }

  public static Advancement getShovel() {
    return shovel;
  }

  public static Advancement getSword() {
    return sword;
  }

  public static Advancement getMiscKnowledge() {
    return miscKnowledge;
  }

  public static Advancement getArborealKnowledge() {
    return arborealKnowledge;
  }
}
