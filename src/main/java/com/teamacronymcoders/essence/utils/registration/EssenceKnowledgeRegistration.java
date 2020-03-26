package com.teamacronymcoders.essence.utils.registration;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

@SuppressWarnings("unchecked")
public class EssenceKnowledgeRegistration {

    private static final DeferredRegister<Knowledge<?>> KNOWLEDGE_DEFERRED_REGISTER = new DeferredRegister<>(EssenceRegistries.KNOWLEDGE, Essence.MODID);

    // Modifier Knowledge
    public static RegistryObject<Knowledge<ItemStack>> BREWED_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("brewed", () -> new Knowledge<ItemStack>(ItemStack.class, new ResourceLocation(Essence.MODID, "brewed"), new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.BREWED_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge<ItemStack>> KEEN_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("keen", () -> new Knowledge<ItemStack>(ItemStack.class, new ResourceLocation(Essence.MODID, "keen"), new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.KEEN_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge<ItemStack>> ENCHANTED_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("enchanted", () -> new Knowledge<ItemStack>(ItemStack.class, new ResourceLocation(Essence.MODID, "enchanted"), new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.ENCHANTED_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge<ItemStack>> EFFICIENCY_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("efficiency", () -> new Knowledge<ItemStack>(ItemStack.class, new ResourceLocation(Essence.MODID, "efficiency"), new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.EFFICIENCY_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge<ItemStack>> INFINITY_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("infinity", () -> new Knowledge<ItemStack>(ItemStack.class, new ResourceLocation(Essence.MODID, "infinity"), new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.INFINITY_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge<ItemStack>> KNOCKBACK_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("knockback", () -> new Knowledge<ItemStack>(ItemStack.class, new ResourceLocation(Essence.MODID, "knockback"), new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.KNOCKBACK_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge<ItemStack>> LUCK_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("luck", () -> new Knowledge<ItemStack>(ItemStack.class, new ResourceLocation(Essence.MODID, "luck"), new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.LUCK_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge<ItemStack>> SILK_TOUCH_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("silk_touch", () -> new Knowledge<ItemStack>(ItemStack.class, new ResourceLocation(Essence.MODID, "silk_touch"), new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.SILK_TOUCH_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge<ItemStack>> UNBREAKING_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("unbreaking", () -> new Knowledge<ItemStack>(ItemStack.class, new ResourceLocation(Essence.MODID, "unbreaking"), new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.UNBREAKING_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge<ItemStack>> MENDING_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("mending", () -> new Knowledge<ItemStack>(ItemStack.class, new ResourceLocation(Essence.MODID, "mending"), new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.MENDING_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge<ItemStack>> STRENGTHENED_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("strengthened", () -> new Knowledge<ItemStack>(
        ItemStack.class,
        new ResourceLocation(Essence.MODID, "strengthened"),
        new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.STRENGTHENED_ARTHROPOD_MODIFIER.get(), 1, null),
        new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.STRENGTHENED_SHARPNESS_MODIFIER.get(), 1, null),
        new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.STRENGTHENED_SMITE_MODIFIER.get(), 1, null),
        new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.STRENGTHENED_POWER_MODIFIER.get(), 1, null))
    );
    public static RegistryObject<Knowledge<ItemStack>> EXPANDER_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("expander", () -> new Knowledge<>(ItemStack.class, new ResourceLocation(Essence.MODID, "expander"), new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.EXPANDER_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge<ItemStack>> FIERY_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("fiery", () -> new Knowledge<>(ItemStack.class, new ResourceLocation(Essence.MODID, "fiery"), new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.FIERY_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge<ItemStack>> RAINBOW_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("rainbow", () -> new Knowledge<>(ItemStack.class, new ResourceLocation(Essence.MODID, "rainbow"), new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.RAINBOW_MODIFIER.get(), 1, null)));
    public static RegistryObject<Knowledge<ItemStack>> CASCADING_MODIFIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("cascading", () -> new Knowledge<>(
        ItemStack.class,
        new ResourceLocation(Essence.MODID, "cascading"),
        new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.CASCADING_NONE_MODIFIER.get(), 1, null),
        new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.CASCADING_EXCAVATION_MODIFIER.get(), 1, null),
        new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.CASCADING_LUMBER_MODIFIER.get(), 1, null),
        new ModifierInstance<>(ItemStack.class, () -> EssenceModifierRegistration.CASCADING_VEIN_MODIFIER.get(), 1, null))
    );

    // General Knowledge
    public static RegistryObject<Knowledge<Block>> TOOL_CRAFTING = KNOWLEDGE_DEFERRED_REGISTER.register("tool_crafting", () -> new Knowledge<Block>(Block.class, new ResourceLocation(Essence.MODID, "tool_crafting")));
    public static RegistryObject<Knowledge<Block>> ARBOREAL_NOTES = KNOWLEDGE_DEFERRED_REGISTER.register("arboreal_notes", () -> new Knowledge<Block>(Block.class, new ResourceLocation(Essence.MODID, "arboreal_notes")));

    // Material-Tier Knowledge
    public static RegistryObject<Knowledge<ItemStack>> BASIC_TIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("basic_tier", () -> new Knowledge<ItemStack>(ItemStack.class, new ResourceLocation(Essence.MODID, "basic_tier")));
    public static RegistryObject<Knowledge<ItemStack>> EMPOWERED_TIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("empowered_tier", () -> new Knowledge<ItemStack>(ItemStack.class, new ResourceLocation(Essence.MODID, "empowered_tier")));
    public static RegistryObject<Knowledge<ItemStack>> EXALTED_TIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("exalted_tier", () -> new Knowledge<ItemStack>(ItemStack.class, new ResourceLocation(Essence.MODID, "exalted_tier")));
    public static RegistryObject<Knowledge<ItemStack>> GODLY_TIER_KNOWLEDGE = KNOWLEDGE_DEFERRED_REGISTER.register("godly_tier", () -> new Knowledge<ItemStack>(ItemStack.class, new ResourceLocation(Essence.MODID, "godly_tier")));

    public static void register(IEventBus eventBus) {
        KNOWLEDGE_DEFERRED_REGISTER.register(eventBus);
    }
}
