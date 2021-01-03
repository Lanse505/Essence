package com.teamacronymcoders.essence.datagen.advancement.modifier;

import com.teamacronymcoders.essence.datagen.advancement.ExtendableAdvancementProvider;
import com.teamacronymcoders.essence.datagen.advancement.KnowledgeAdvancementProvider;
import com.teamacronymcoders.essence.serializable.advancement.criterion.knowledge.UnlockKnowledgeCriterionInstance;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.registration.EssenceKnowledgeRegistration;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.EntityPredicate.AndPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Consumer;

public class AttributeKnowledgeAdvancements {

    private static Advancement attribute;
    private static Advancement armor;
    private static Advancement armor_toughness;
    private static Advancement attack_damage;
    private static Advancement max_health;
    private static Advancement movement_speed;

    public static void addAttributeAdvancements(Consumer<Advancement> consumer) {
        attribute = Advancement.Builder.builder()
            .withDisplay(
                ExtendableAdvancementProvider.getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.attribute_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.attribute_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(KnowledgeAdvancementProvider.getKnowledgeModifierRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/attribute/attribute_modifier");
        armor = Advancement.Builder.builder()
            .withDisplay(
                ExtendableAdvancementProvider.getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.armor_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.armor_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(attribute)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.ARMOR_MODIFIER_KNOWLEDGE.get(), AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/attribute/armor_modifier");
        armor_toughness = Advancement.Builder.builder()
            .withDisplay(
                ExtendableAdvancementProvider.getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.armor_toughness_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.armor_toughness_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(attribute)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.ARMOR_TOUGHNESS_MODIFIER_KNOWLEDGE.get(), AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/attribute/armor_toughness_modifier");
        attack_damage = Advancement.Builder.builder()
            .withDisplay(
                ExtendableAdvancementProvider.getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.attack_damage_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.attack_damage_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(attribute)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.ATTACK_DAMAGE_MODIFIER_KNOWLEDGE.get(), AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/attribute/attack_damage_modifier");
        max_health = Advancement.Builder.builder()
            .withDisplay(
                ExtendableAdvancementProvider.getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.max_health_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.max_health_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(attribute)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.MAX_HEALTH_MODIFIER_KNOWLEDGE.get(), AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/attribute/max_health_modifier");
        movement_speed = Advancement.Builder.builder()
            .withDisplay(
                ExtendableAdvancementProvider.getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.movement_speed_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.movement_speed_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withParent(attribute)
            .withCriterion("knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.MOVEMENT_SPEED_MODIFIER_KNOWLEDGE.get(), AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/attribute/movement_speed_modifier");
    }

    public static Advancement getAttribute() {
        return attribute;
    }

    public static Advancement getArmor() {
        return armor;
    }

    public static Advancement getArmorToughness() {
        return armor_toughness;
    }

    public static Advancement getAttackDamage() {
        return attack_damage;
    }

    public static Advancement getMaxHealth() {
        return max_health;
    }

    public static Advancement getMovementSpeed() {
        return movement_speed;
    }
}
