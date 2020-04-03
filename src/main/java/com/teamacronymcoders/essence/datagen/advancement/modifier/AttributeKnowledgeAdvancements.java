package com.teamacronymcoders.essence.serializable.provider.advancement.modifier;

import com.teamacronymcoders.essence.serializable.provider.advancement.ExtendableAdvancementProvider;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Consumer;

public class AttributeKnowledgeAdvancements extends ExtendableAdvancementProvider {

    public AttributeKnowledgeAdvancements(DataGenerator generator) {
        super(generator, "/knowledge/attribute");
    }

    @Override
    protected void addAdvancements(Consumer<Advancement> consumer) {
        Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.knowledge.attribute_modifier.title"),
                new TranslationTextComponent("advancements.essence.knowledge.attribute_modifier.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, true, false, true
            )
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:attribute_modifier");
    }

    @Override
    public String getName() {
        return "Essence Advancements: [Knowledge/Modifier/Attributes]";
    }
}
