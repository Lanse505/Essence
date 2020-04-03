package com.teamacronymcoders.essence.serializable.provider.advancement;

import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Consumer;

public class CoreAdvancementProvider extends ExtendableAdvancementProvider {

    public CoreAdvancementProvider(DataGenerator generator) {
        super(generator, "/core");
    }

    @Override
    protected void addAdvancements(Consumer<Advancement> consumer) {
        Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.essence.core.root.title"),
                new TranslationTextComponent("advancements.essence.core.root.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, true
            )
            .withCriterion("essence_ore", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.ESSENCE_ORE))
            .register(consumer, "essence:root");
    }

    @Override
    public String getName() {
        return "Essence Advancements: [Core]";
    }
}
