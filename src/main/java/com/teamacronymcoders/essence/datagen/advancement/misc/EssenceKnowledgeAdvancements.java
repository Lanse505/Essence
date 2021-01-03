package com.teamacronymcoders.essence.datagen.advancement.misc;

import com.teamacronymcoders.essence.datagen.advancement.*;
import com.teamacronymcoders.essence.serializable.advancement.criterion.knowledge.*;
import com.teamacronymcoders.essence.util.*;
import com.teamacronymcoders.essence.util.registration.*;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.*;
import net.minecraft.advancements.criterion.EntityPredicate.AndPredicate;
import net.minecraft.util.*;
import net.minecraft.util.text.*;

import java.util.function.*;

public class EssenceKnowledgeAdvancements {

    private static Advancement miscKnowledge;
    private static Advancement arborealKnowledge;

    public static void addMiscKnowledge(Consumer<Advancement> consumer) {
        miscKnowledge = Advancement.Builder.builder()
            .withDisplay(
                EssenceObjectHolders.TOME_OF_KNOWLEDGE,
                new TranslationTextComponent("advancements.essence.knowledge.misc.misc_knowledge.title"),
                new TranslationTextComponent("advancements.essence.knowledge.misc.misc_knowledge.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, true
            )
            .withParent(KnowledgeAdvancementProvider.getKnowledgeRoot())
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/misc/misc_knowledge");
        arborealKnowledge = Advancement.Builder.builder()
            .withDisplay(
                EssenceObjectHolders.ESSENCE_WOOD_SAPLING,
                new TranslationTextComponent("advancements.essence.knowledge.misc.arboreal_knowledge.title"),
                new TranslationTextComponent("advancements.essence.knowledge.misc.arboreal_knowledge.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, true
            )
            .withParent(miscKnowledge)
            .withCriterion("arboreal_knowledge", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.ARBOREAL_NOTES.get(), AndPredicate.ANY_AND))
            .withCriterion("essence_sapling", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.ESSENCE_WOOD_SAPLING))
            .register(consumer, "essence:knowledge/misc/arboreal_knowledge");
    }

    public static Advancement getMiscKnowledge() {
        return miscKnowledge;
    }

    public static Advancement getArborealKnowledge() {
        return arborealKnowledge;
    }
}
