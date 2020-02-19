package com.teamacronymcoders.essence.utils;

import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.impl.generation.FancyEssenceTreeFeature;
import com.teamacronymcoders.essence.impl.generation.NormalEssenceTreeFeature;
import com.teamacronymcoders.essence.impl.modifier.AttackDamageModifier;
import com.teamacronymcoders.essence.impl.modifier.ExpanderModifier;
import com.teamacronymcoders.essence.impl.modifier.LuckModifier;
import com.teamacronymcoders.essence.impl.modifier.SilkTouchModifier;
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
    private static final DeferredRegister<Modifier> MODIFIER_DEFERRED_REGISTER = new DeferredRegister<>(MODIFIER_REGISTRY, EssenceReferences.MODID);
    public static RegistryObject<NormalEssenceTreeFeature> NORMAL_ESSENCE_TREE_FEATURE = FEATURE_DEFERRED_REGISTER.register("normal_essence_tree", () -> new NormalEssenceTreeFeature(TreeFeatureConfig::deserialize));
    public static RegistryObject<FancyEssenceTreeFeature> FANCY_ESSENCE_TREE_FEATURE = FEATURE_DEFERRED_REGISTER.register("fancy_essence_tree", () -> new FancyEssenceTreeFeature(TreeFeatureConfig::deserialize));
    public static RegistryObject<AttackDamageModifier> ATTACK_DAMAGE_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("attack_damage_modifier", AttackDamageModifier::new);
    public static RegistryObject<ExpanderModifier> EXPANDER_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("expander_modifier", ExpanderModifier::new);
    public static RegistryObject<LuckModifier> LUCK_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("luck_modifier", LuckModifier::new);
    public static RegistryObject<SilkTouchModifier> SILK_TOUCH_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("silk_touch_modifier", SilkTouchModifier::new);

    public static void register(IEventBus eventBus) {
        FEATURE_DEFERRED_REGISTER.register(eventBus);
        MODIFIER_DEFERRED_REGISTER.register(eventBus);
    }
}
