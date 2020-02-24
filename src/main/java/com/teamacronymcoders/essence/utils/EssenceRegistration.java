package com.teamacronymcoders.essence.utils;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.impl.generation.FancyEssenceTreeFeature;
import com.teamacronymcoders.essence.impl.generation.NormalEssenceTreeFeature;
import com.teamacronymcoders.essence.impl.modifier.attribute.AttackDamageModifier;
import com.teamacronymcoders.essence.impl.modifier.cosmetic.EnchantedModifier;
import com.teamacronymcoders.essence.impl.modifier.enchantment.*;
import com.teamacronymcoders.essence.impl.modifier.interaction.ExpanderModifier;
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
    private static final DeferredRegister<Feature<?>> FEATURE_DEFERRED_REGISTER = new DeferredRegister<>(ForgeRegistries.FEATURES, EssenceReferences.MODID);
    public static ForgeRegistry<Modifier> MODIFIER_REGISTRY = (ForgeRegistry<Modifier>) new RegistryBuilder<Modifier>()
        .setName(new ResourceLocation(EssenceReferences.MODID, "modifiers"))
        .setIDRange(1, Integer.MAX_VALUE - 1)
        .setType(Modifier.class)
        .disableSaving()
        .create();

    // DeferredRegistry
    private static final DeferredRegister<Modifier> MODIFIER_DEFERRED_REGISTER = new DeferredRegister<>(MODIFIER_REGISTRY, EssenceReferences.MODID);

    // Feature RegistryObjects
    public static RegistryObject<NormalEssenceTreeFeature> NORMAL_ESSENCE_TREE_FEATURE = FEATURE_DEFERRED_REGISTER.register("normal_essence_tree", () -> new NormalEssenceTreeFeature(TreeFeatureConfig::deserialize));
    public static RegistryObject<FancyEssenceTreeFeature> FANCY_ESSENCE_TREE_FEATURE = FEATURE_DEFERRED_REGISTER.register("fancy_essence_tree", () -> new FancyEssenceTreeFeature(TreeFeatureConfig::deserialize));

    // Modifier RegistryObjects
    // Attribute Modifiers
    public static RegistryObject<AttackDamageModifier> ATTACK_DAMAGE_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("attack_damage", AttackDamageModifier::new);
    // Cosmetic Modifiers
    public static RegistryObject<EnchantedModifier> ENCHANTED_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("enchanted", EnchantedModifier::new);
    // Enchantment Modifiers
    public static RegistryObject<StrengthenedModifier> STRENGTHENED_ARTHROPOD_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("strengthened_arthropod", () -> new StrengthenedModifier(StrengthenedModifier.StrengthenedType.BANE_OF_ARTHROPODS));
    public static RegistryObject<StrengthenedModifier> STRENGTHENED_SHARPNESS_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("strengthened_sharpness", () -> new StrengthenedModifier(StrengthenedModifier.StrengthenedType.SHARPNESS));
    public static RegistryObject<StrengthenedModifier> STRENGTHENED_SMITE_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("strengthened_smite", () -> new StrengthenedModifier(StrengthenedModifier.StrengthenedType.SMITE));
    public static RegistryObject<StrengthenedModifier> STRENGTHENED_POWER_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("strengthened_power", () -> new StrengthenedModifier(StrengthenedModifier.StrengthenedType.POWER));
    public static RegistryObject<EfficiencyModifier> EFFICIENCY_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("efficiency", EfficiencyModifier::new);
    public static RegistryObject<KnockbackModifier> KNOCKBACK_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("knockback", KnockbackModifier::new);
    public static RegistryObject<LuckModifier> LUCK_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("luck", LuckModifier::new);
    public static RegistryObject<SilkTouchModifier> SILK_TOUCH_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("silk_touch", SilkTouchModifier::new);
    // Interaction Modifiers
    public static RegistryObject<ExpanderModifier> EXPANDER_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("expander", ExpanderModifier::new);


    public static void register(IEventBus eventBus) {
        FEATURE_DEFERRED_REGISTER.register(eventBus);
        MODIFIER_DEFERRED_REGISTER.register(eventBus);
    }
}
