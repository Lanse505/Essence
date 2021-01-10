package com.teamacronymcoders.essence.util.registration;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.modifier.curio.attribute.*;
import com.teamacronymcoders.essence.modifier.item.arrow.BrewedModifier;
import com.teamacronymcoders.essence.modifier.item.arrow.KeenModifier;
import com.teamacronymcoders.essence.modifier.item.cosmetic.EnchantedModifier;
import com.teamacronymcoders.essence.modifier.item.enchantment.*;
import com.teamacronymcoders.essence.modifier.item.enchantment.strengthened.StrengthenedModifier;
import com.teamacronymcoders.essence.modifier.item.enchantment.strengthened.StrengthenedType;
import com.teamacronymcoders.essence.modifier.item.interaction.ExpanderModifier;
import com.teamacronymcoders.essence.modifier.item.interaction.FieryModifier;
import com.teamacronymcoders.essence.modifier.item.interaction.RainbowModifier;
import com.teamacronymcoders.essence.modifier.item.interaction.cascading.CascadingModifier;
import com.teamacronymcoders.essence.modifier.item.interaction.cascading.CascadingType;
import com.teamacronymcoders.essence.modifier.item.tank.HoldingModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class EssenceModifierRegistration {
  private static final DeferredRegister<Modifier<?>> MODIFIER_DEFERRED_REGISTER = DeferredRegister.create(EssenceRegistries.MODIFIER, Essence.MOD_ID);

  // Modifier RegistryObjects
  // Arrow Modifiers
  public static RegistryObject<BrewedModifier> BREWED_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("brewed", BrewedModifier::new);
  public static RegistryObject<KeenModifier> KEEN_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("keen", KeenModifier::new);

  // Attribute Modifiers
  public static RegistryObject<ArmorModifier> ARMOR_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("armor", ArmorModifier::new);
  public static RegistryObject<ArmorToughnessModifier> ARMOR_TOUGHNESS_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("armor_toughness", ArmorToughnessModifier::new);
  public static RegistryObject<AttackDamageModifier> ATTACK_DAMAGE_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("attack_damage", AttackDamageModifier::new);
  public static RegistryObject<MaxHealthModifier> MAX_HEALTH_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("max_health", MaxHealthModifier::new);
  public static RegistryObject<MovementSpeedModifier> MOVEMENT_SPEED_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("movement_speed", MovementSpeedModifier::new);

  // Cosmetic Modifiers
  public static RegistryObject<EnchantedModifier> ENCHANTED_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("enchanted", EnchantedModifier::new);

  // Enchantment Modifiers
  public static RegistryObject<EfficiencyModifier> EFFICIENCY_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("efficiency", EfficiencyModifier::new);
  public static RegistryObject<InfinityModifier> INFINITY_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("infinity", InfinityModifier::new);
  public static RegistryObject<KnockbackModifier> KNOCKBACK_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("knockback", KnockbackModifier::new);
  public static RegistryObject<LuckModifier> LUCK_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("luck", LuckModifier::new);
  public static RegistryObject<MendingModifier> MENDING_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("mending", MendingModifier::new);
  public static RegistryObject<SilkTouchModifier> SILK_TOUCH_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("silk_touch", SilkTouchModifier::new);
  public static RegistryObject<UnbreakingModifier> UNBREAKING_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("unbreaking", UnbreakingModifier::new);
  // Strengthened Modifiers
  public static RegistryObject<StrengthenedModifier> STRENGTHENED_ARTHROPOD_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("strengthened_arthropod", () -> new StrengthenedModifier(StrengthenedType.BANE_OF_ARTHROPODS));
  public static RegistryObject<StrengthenedModifier> STRENGTHENED_SHARPNESS_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("strengthened_sharpness", () -> new StrengthenedModifier(StrengthenedType.SHARPNESS));
  public static RegistryObject<StrengthenedModifier> STRENGTHENED_SMITE_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("strengthened_smite", () -> new StrengthenedModifier(StrengthenedType.SMITE));
  public static RegistryObject<StrengthenedModifier> STRENGTHENED_POWER_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("strengthened_power", () -> new StrengthenedModifier(StrengthenedType.POWER));

  // Interaction Modifiers
  public static RegistryObject<ExpanderModifier> EXPANDER_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("expander", ExpanderModifier::new);
  public static RegistryObject<FieryModifier> FIERY_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("fiery", FieryModifier::new);
  public static RegistryObject<RainbowModifier> RAINBOW_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("rainbow", RainbowModifier::new);
  // Cascading Modifiers
  public static RegistryObject<CascadingModifier> CASCADING_NONE_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("cascading_none", () -> new CascadingModifier(CascadingType.NONE));
  public static RegistryObject<CascadingModifier> CASCADING_LUMBER_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("cascading_lumber", () -> new CascadingModifier(CascadingType.LUMBER));
  public static RegistryObject<CascadingModifier> CASCADING_VEIN_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("cascading_vein", () -> new CascadingModifier(CascadingType.VEIN));
  public static RegistryObject<CascadingModifier> CASCADING_EXCAVATION_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("cascading_excavation", () -> new CascadingModifier(CascadingType.EXCAVATION));

  // Tank Modifiers
  public static RegistryObject<HoldingModifier> HOLDING_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("holding_tank", HoldingModifier::new);

  public static void register (IEventBus eventBus) {
    MODIFIER_DEFERRED_REGISTER.register(eventBus);
  }
}
