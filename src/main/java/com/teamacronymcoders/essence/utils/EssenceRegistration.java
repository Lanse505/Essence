package com.teamacronymcoders.essence.utils;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.generation.FancyEssenceTreeFeature;
import com.teamacronymcoders.essence.generation.NormalEssenceTreeFeature;
import com.teamacronymcoders.essence.modifier.arrow.BrewedModifier;
import com.teamacronymcoders.essence.modifier.arrow.KeenModifier;
import com.teamacronymcoders.essence.modifier.attribute.*;
import com.teamacronymcoders.essence.modifier.cosmetic.EnchantedModifier;
import com.teamacronymcoders.essence.modifier.enchantment.EfficiencyModifier;
import com.teamacronymcoders.essence.modifier.enchantment.KnockbackModifier;
import com.teamacronymcoders.essence.modifier.enchantment.LuckModifier;
import com.teamacronymcoders.essence.modifier.enchantment.SilkTouchModifier;
import com.teamacronymcoders.essence.modifier.enchantment.strengthened.StrengthenedModifier;
import com.teamacronymcoders.essence.modifier.enchantment.strengthened.StrengthenedType;
import com.teamacronymcoders.essence.modifier.interaction.ExpanderModifier;
import com.teamacronymcoders.essence.modifier.interaction.FieryModifier;
import com.teamacronymcoders.essence.modifier.interaction.RainbowModifier;
import com.teamacronymcoders.essence.modifier.interaction.cascading.CascadingModifier;
import com.teamacronymcoders.essence.modifier.interaction.cascading.CascadingType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EssenceRegistration {
    private static final DeferredRegister<Feature<?>> FEATURE_DEFERRED_REGISTER = new DeferredRegister<>(ForgeRegistries.FEATURES, Essence.MODID);
    // Registry
    public static ForgeRegistry<Modifier> MODIFIER_REGISTRY = (ForgeRegistry<Modifier>) new RegistryBuilder<Modifier>()
        .setName(new ResourceLocation(Essence.MODID, "modifiers"))
        .setIDRange(1, Integer.MAX_VALUE - 1)
        .setType(Modifier.class)
        .disableSaving()
        .create();
    // DeferredRegistry
    private static final DeferredRegister<Modifier> MODIFIER_DEFERRED_REGISTER = new DeferredRegister<>(MODIFIER_REGISTRY, Essence.MODID);
    // Feature RegistryObjects
    // Essence Tree
    public static RegistryObject<NormalEssenceTreeFeature> NORMAL_ESSENCE_TREE_FEATURE = FEATURE_DEFERRED_REGISTER.register("normal_essence_tree", () -> new NormalEssenceTreeFeature(TreeFeatureConfig::func_227338_a_));
    public static RegistryObject<FancyEssenceTreeFeature> FANCY_ESSENCE_TREE_FEATURE = FEATURE_DEFERRED_REGISTER.register("fancy_essence_tree", () -> new FancyEssenceTreeFeature(TreeFeatureConfig::func_227338_a_));
    // Essence Ore


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
    public static RegistryObject<FieryModifier> FIERY_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("fiery", FieryModifier::new);
    public static RegistryObject<KnockbackModifier> KNOCKBACK_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("knockback", KnockbackModifier::new);
    public static RegistryObject<LuckModifier> LUCK_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("luck", LuckModifier::new);
    public static RegistryObject<SilkTouchModifier> SILK_TOUCH_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("silk_touch", SilkTouchModifier::new);
    // Strengthened Modifiers
    public static RegistryObject<StrengthenedModifier> STRENGTHENED_ARTHROPOD_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("strengthened_arthropod", () -> new StrengthenedModifier(StrengthenedType.BANE_OF_ARTHROPODS));
    public static RegistryObject<StrengthenedModifier> STRENGTHENED_SHARPNESS_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("strengthened_sharpness", () -> new StrengthenedModifier(StrengthenedType.SHARPNESS));
    public static RegistryObject<StrengthenedModifier> STRENGTHENED_SMITE_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("strengthened_smite", () -> new StrengthenedModifier(StrengthenedType.SMITE));
    public static RegistryObject<StrengthenedModifier> STRENGTHENED_POWER_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("strengthened_power", () -> new StrengthenedModifier(StrengthenedType.POWER));

    // Interaction Modifiers
    public static RegistryObject<ExpanderModifier> EXPANDER_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("expander", ExpanderModifier::new);
    public static RegistryObject<RainbowModifier> RAINBOW_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("rainbow", RainbowModifier::new);
    // Cascading Modifiers
    public static RegistryObject<CascadingModifier> CASCADING_NONE_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("cascading_none", () -> new CascadingModifier(CascadingType.NONE));
    public static RegistryObject<CascadingModifier> CASCADING_LUMBER_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("cascading_lumber", () -> new CascadingModifier(CascadingType.LUMBER));
    public static RegistryObject<CascadingModifier> CASCADING_VEIN_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("cascading_vein", () -> new CascadingModifier(CascadingType.VEIN));
    public static RegistryObject<CascadingModifier> CASCADING_EXCAVATION_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("cascading_excavation", () -> new CascadingModifier(CascadingType.EXCAVATION));

    // Deferred Registration
    public static void register(IEventBus eventBus) {
        FEATURE_DEFERRED_REGISTER.register(eventBus);
        MODIFIER_DEFERRED_REGISTER.register(eventBus);
    }

}
