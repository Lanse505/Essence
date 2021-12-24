package com.teamacronymcoders.essence.registrate;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class EssenceKnowledgeRegistrate {

  public static void init() {}

  public static final Supplier<IForgeRegistry<Knowledge>> REGISTRY = Essence.ESSENCE_REGISTRATE.makeRegistry("knowledge", Knowledge.class, () -> new RegistryBuilder<Knowledge>()
          .setName(new ResourceLocation(Essence.MOD_ID, "modifier"))
          .setIDRange(1, Integer.MAX_VALUE - 1)
          .disableSaving()
          .setType(Knowledge.class));

  public static final RegistryEntry<Knowledge> BREWED_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("brewed", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.BREWED_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> KEEN_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("keen", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.KEEN_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> ENCHANTED_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("enchanted", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.ENCHANTED_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> ARMOR_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("armor", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.ARMOR_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> ARMOR_TOUGHNESS_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("armor_toughness", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.ARMOR_TOUGHNESS_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> ATTACK_DAMAGE_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("attack_damage", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.ATTACK_DAMAGE_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> MAX_HEALTH_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("max_health", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.MAX_HEALTH_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> MOVEMENT_SPEED_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("movement_speed", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.MOVEMENT_SPEED_MODIFIER::get, 1, null)));

  public static final RegistryEntry<Knowledge> EFFICIENCY_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("efficiency", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.EFFICIENCY_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> INFINITY_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("infinity", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.INFINITY_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> KNOCKBACK_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("knockback", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.KNOCKBACK_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> LUCK_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("luck", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.LUCK_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> SILK_TOUCH_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("silk_touch", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.SILK_TOUCH_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> UNBREAKING_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("unbreaking", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.UNBREAKING_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> MENDING_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("mending", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.MENDING_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> STRENGTHENED_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("strengthened", Knowledge.class, () -> new Knowledge(
          new ModifierInstance(EssenceModifierRegistrate.STRENGTHENED_ARTHROPOD_MODIFIER::get, 1, null),
          new ModifierInstance(EssenceModifierRegistrate.STRENGTHENED_SHARPNESS_MODIFIER::get, 1, null),
          new ModifierInstance(EssenceModifierRegistrate.STRENGTHENED_SMITE_MODIFIER::get, 1, null),
          new ModifierInstance(EssenceModifierRegistrate.STRENGTHENED_POWER_MODIFIER::get, 1, null)));

  public static final RegistryEntry<Knowledge> EXPANDER_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("expander", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.EXPANDER_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> FIERY_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("fiery_modifier", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.FIERY_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> RAINBOW_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("rainbow", Knowledge.class, () -> new Knowledge(new ModifierInstance(EssenceModifierRegistrate.RAINBOW_MODIFIER::get, 1, null)));
  public static final RegistryEntry<Knowledge> CASCADING_MODIFIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("cascading", Knowledge.class, () -> new Knowledge(
          new ModifierInstance(EssenceModifierRegistrate.CASCADING_NONE_MODIFIER::get, 1, null),
          new ModifierInstance(EssenceModifierRegistrate.CASCADING_EXCAVATION_MODIFIER::get, 1, null),
          new ModifierInstance(EssenceModifierRegistrate.CASCADING_LUMBER_MODIFIER::get, 1, null),
          new ModifierInstance(EssenceModifierRegistrate.CASCADING_VEIN_MODIFIER::get, 1, null)));

  public static final RegistryEntry<Knowledge> TOOL_CRAFTING = Essence.ESSENCE_REGISTRATE.simple("tool_crafting", Knowledge.class, Knowledge::new);
  public static final RegistryEntry<Knowledge> ARBOREAL_NOTES = Essence.ESSENCE_REGISTRATE.simple("arboreal_notes", Knowledge.class, Knowledge::new);
  public static final RegistryEntry<Knowledge> BASIC_TIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("basic_tier", Knowledge.class, Knowledge::new);
  public static final RegistryEntry<Knowledge> EMPOWERED_TIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("empowered_tier", Knowledge.class, Knowledge::new);
  public static final RegistryEntry<Knowledge> SUPREME_TIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("supreme_tier", Knowledge.class, Knowledge::new);
  public static final RegistryEntry<Knowledge> DIVINE_TIER_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.simple("divine_tier", Knowledge.class, Knowledge::new);

}
