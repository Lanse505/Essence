package com.teamacronymcoders.essence.util.registration;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.generation.tree.essence_tree.FancyEssenceTreeFeature;
import com.teamacronymcoders.essence.generation.tree.essence_tree.NormalEssenceTreeFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EssenceFeatureRegistration {

    private static final DeferredRegister<Feature<?>> FEATURE_DEFERRED_REGISTER = new DeferredRegister<>(ForgeRegistries.FEATURES, Essence.MODID);

    // Feature RegistryObjects
    // Essence Tree
    public static RegistryObject<NormalEssenceTreeFeature> NORMAL_ESSENCE_TREE_FEATURE = FEATURE_DEFERRED_REGISTER.register("normal_essence_tree", () -> new NormalEssenceTreeFeature(TreeFeatureConfig::func_227338_a_));
    public static RegistryObject<FancyEssenceTreeFeature> FANCY_ESSENCE_TREE_FEATURE = FEATURE_DEFERRED_REGISTER.register("fancy_essence_tree", () -> new FancyEssenceTreeFeature(TreeFeatureConfig::func_227338_a_));
    // Essence Ore

    public static void register(IEventBus eventBus) {
        FEATURE_DEFERRED_REGISTER.register(eventBus);
    }
}
