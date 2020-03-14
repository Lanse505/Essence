package com.teamacronymcoders.essence.utils.registration;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.modifier.arrow.BrewedModifier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class EssenceKnowledgeRegistration {

    private static final DeferredRegister<Knowledge> KNOWLEDGE_DEFERRED_REGISTER = new DeferredRegister<>(EssenceRegistries.KNOWLEDGE_REGISTRY, Essence.MODID);

    // Modifier Knowledge
    public static RegistryObject<Knowledge> BREWED_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("brewed", () -> new Knowledge(new ResourceLocation(Essence.MODID, "brewed"), new ModifierInstance(EssenceModifierRegistration.BREWED_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge> KEEN_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("keen", () -> new Knowledge(new ResourceLocation(Essence.MODID, "keen"), new ModifierInstance(EssenceModifierRegistration.KEEN_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge> ENCHANTED_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("enchanted", () -> new Knowledge(new ResourceLocation(Essence.MODID, "enchanted"), new ModifierInstance(EssenceModifierRegistration.ENCHANTED_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge> EFFICIENCY_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("efficiency", () -> new Knowledge(new ResourceLocation(Essence.MODID, "efficiency"), new ModifierInstance(EssenceModifierRegistration.EFFICIENCY_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge> INFINITY_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("infinity", () -> new Knowledge(new ResourceLocation(Essence.MODID, "infinity"), new ModifierInstance(EssenceModifierRegistration.INFINITY_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge> KNOCKBACK_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("knockback", () -> new Knowledge(new ResourceLocation(Essence.MODID, "knockback"), new ModifierInstance(EssenceModifierRegistration.KNOCKBACK_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge> LUCK_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("luck", () -> new Knowledge(new ResourceLocation(Essence.MODID, "luck"), new ModifierInstance(EssenceModifierRegistration.LUCK_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge> SILK_TOUCH_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("silk_touch", () -> new Knowledge(new ResourceLocation(Essence.MODID, "silk_touch"), new ModifierInstance(EssenceModifierRegistration.SILK_TOUCH_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge> UNBREAKING_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("unbreaking", () -> new Knowledge(new ResourceLocation(Essence.MODID, "unbreaking"), new ModifierInstance(EssenceModifierRegistration.UNBREAKING_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge> MENDING_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("mending", () -> new Knowledge(new ResourceLocation(Essence.MODID, "mending"), new ModifierInstance(EssenceModifierRegistration.MENDING_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge> STRENGTHENED_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("strengthened", () -> new Knowledge(
        new ResourceLocation(Essence.MODID, "strengthened"),
        new ModifierInstance(EssenceModifierRegistration.STRENGTHENED_ARTHROPOD_MODIFIER.get(), 1, null),
        new ModifierInstance(EssenceModifierRegistration.STRENGTHENED_SHARPNESS_MODIFIER.get(), 1, null),
        new ModifierInstance(EssenceModifierRegistration.STRENGTHENED_SMITE_MODIFIER.get(), 1, null),
        new ModifierInstance(EssenceModifierRegistration.STRENGTHENED_POWER_MODIFIER.get(), 1, null))
    );
    public static RegistryObject<Knowledge> EXPANDER_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("expander", () -> new Knowledge(new ResourceLocation(Essence.MODID, "expander"), new ModifierInstance(EssenceModifierRegistration.EXPANDER_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge> FIERY_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("fiery", () -> new Knowledge(new ResourceLocation(Essence.MODID, "fiery"), new ModifierInstance(EssenceModifierRegistration.FIERY_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge> RAINBOW_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("rainbow", () -> new Knowledge(new ResourceLocation(Essence.MODID, "rainbow"), new ModifierInstance(EssenceModifierRegistration.RAINBOW_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge> CASCADING_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("cascading", () -> new Knowledge(
                new ResourceLocation(Essence.MODID, "cascading"),
                new ModifierInstance(EssenceModifierRegistration.CASCADING_NONE_MODIFIER.get(), 1, null),
                new ModifierInstance(EssenceModifierRegistration.CASCADING_EXCAVATION_MODIFIER.get(), 1, null),
                new ModifierInstance(EssenceModifierRegistration.CASCADING_LUMBER_MODIFIER.get(), 1, null),
                new ModifierInstance(EssenceModifierRegistration.CASCADING_VEIN_MODIFIER.get(), 1, null))
    );

    // General Knowledge
    public static RegistryObject<Knowledge> TOOL_CRAFTING = KNOWLEDGE_DEFERRED_REGISTER.register("tool_crafting", () -> new Knowledge(new ResourceLocation(Essence.MODID, "tool_crafting")));
    public static RegistryObject<Knowledge> ARBOREAL_NOTES = KNOWLEDGE_DEFERRED_REGISTER.register("arboreal_notes", () -> new Knowledge(new ResourceLocation(Essence.MODID, "arboreal_notes")));

    // Material-Tier Knowledge
    public static RegistryObject<Knowledge> BASIC_TIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("basic_tier", () -> new Knowledge(new ResourceLocation(Essence.MODID, "basic_tier")));
    public static RegistryObject<Knowledge> EMPOWERED_TIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("empowered_tier", () -> new Knowledge(new ResourceLocation(Essence.MODID, "empowered_tier")));
    public static RegistryObject<Knowledge> EXALTED_TIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("exalted_tier", () -> new Knowledge(new ResourceLocation(Essence.MODID, "exalted_tier")));
    public static RegistryObject<Knowledge> GODLY_TIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("godly_tier", () -> new Knowledge(new ResourceLocation(Essence.MODID, "godly_tier")));

    public static void register(IEventBus eventBus) {
        KNOWLEDGE_DEFERRED_REGISTER.register(eventBus);
    }
}
