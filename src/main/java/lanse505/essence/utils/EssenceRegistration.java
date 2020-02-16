package lanse505.essence.utils;

import lanse505.essence.api.modifier.core.Modifier;
import lanse505.essence.impl.generation.FancyEssenceTreeFeature;
import lanse505.essence.impl.generation.NormalEssenceTreeFeature;
import lanse505.essence.impl.modifier.AttackDamageModifier;
import lanse505.essence.impl.modifier.ExpanderModifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EssenceRegistration {
    private static ForgeRegistry<Modifier> MODIFIER_REGISTRY = null;
    private static final DeferredRegister<Modifier> MODIFIER_DEFERRED_REGISTER = new DeferredRegister<>(MODIFIER_REGISTRY, EssenceReferences.MODID);
    private static final DeferredRegister<Feature<?>> FEATURE_DEFERRED_REGISTER = new DeferredRegister<>(ForgeRegistries.FEATURES, EssenceReferences.MODID);

    public static RegistryObject<NormalEssenceTreeFeature> NORMAL_ESSENCE_TREE_FEATURE = FEATURE_DEFERRED_REGISTER.register("normal_essence_tree",
            () -> new NormalEssenceTreeFeature(TreeFeatureConfig::deserialize));
    public static RegistryObject<FancyEssenceTreeFeature> FANCY_ESSENCE_TREE_FEATURE = FEATURE_DEFERRED_REGISTER.register("fancy_essence_tree",
            () -> new FancyEssenceTreeFeature(TreeFeatureConfig::deserialize));
    public static RegistryObject<AttackDamageModifier> ATTACK_DAMAGE_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("attack_damage_modifier",
            AttackDamageModifier::new);
    public static RegistryObject<ExpanderModifier> EXPANDER_MODIFIER = MODIFIER_DEFERRED_REGISTER.register("expander_modifier",
            ExpanderModifier::new);


    public static void register(IEventBus eventBus) {
        FEATURE_DEFERRED_REGISTER.register(eventBus);
    }

    public static void setModifierRegistry(ForgeRegistry<Modifier> modifierRegistry) {
        MODIFIER_REGISTRY = modifierRegistry;
    }
}
