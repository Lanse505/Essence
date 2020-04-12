package com.teamacronymcoders.essence.datagen.advancement.misc;

import com.teamacronymcoders.essence.datagen.advancement.*;
import com.teamacronymcoders.essence.serializable.advancement.criterion.knowledge.*;
import com.teamacronymcoders.essence.util.*;
import com.teamacronymcoders.essence.util.registration.*;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;

import java.util.function.*;

public class EssenceToolTypeAdvancements {

    private static Advancement toolTypes;
    private static Advancement axe;
    private static Advancement bow;
    private static Advancement hoe;
    private static Advancement omniTool;
    private static Advancement pickaxe;
    private static Advancement shear;
    private static Advancement shovel;
    private static Advancement sword;

    public static void addToolTypeAdvancements(Consumer<Advancement> consumer) {
        toolTypes = Advancement.Builder.builder()
            .withDisplay(
                EssenceObjectHolders.ESSENCE_AXE,
                new TranslationTextComponent("advancements.essence.knowledge.tools.tool_type.title"),
                new TranslationTextComponent("advancements.essence.knowledge.tools.tool_type.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, true
            )
            .withParent(KnowledgeAdvancementProvider.getKnowledgeToolRoot())
            .withCriterion("tool_crafting", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.TOOL_CRAFTING.get()))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.TOME_OF_KNOWLEDGE))
            .register(consumer, "essence:knowledge/tools/tool_types");

        axe = Advancement.Builder.builder()
            .withDisplay(
                EssenceObjectHolders.ESSENCE_AXE,
                new TranslationTextComponent("advancements.essence.knowledge.tools.axe.title"),
                new TranslationTextComponent("advancements.essence.knowledge.tools.axe.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.ESSENCE_AXE))
            .register(consumer, "essence:knowledge/tools/essence_axe");

        bow = Advancement.Builder.builder()
            .withDisplay(
                EssenceObjectHolders.ESSENCE_BOW,
                new TranslationTextComponent("advancements.essence.knowledge.tools.bow.title"),
                new TranslationTextComponent("advancements.essence.knowledge.tools.bow.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.ESSENCE_BOW))
            .register(consumer, "essence:knowledge/tools/essence_bow");

        hoe = Advancement.Builder.builder()
            .withDisplay(
                EssenceObjectHolders.ESSENCE_HOE,
                new TranslationTextComponent("advancements.essence.knowledge.tools.hoe.title"),
                new TranslationTextComponent("advancements.essence.knowledge.tools.hoe.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.ESSENCE_HOE))
            .register(consumer, "essence:knowledge/tools/essence_hoe");

        omniTool = Advancement.Builder.builder()
            .withDisplay(
                EssenceObjectHolders.ESSENCE_OMNITOOL,
                new TranslationTextComponent("advancements.essence.knowledge.tools.omnitool.title"),
                new TranslationTextComponent("advancements.essence.knowledge.tools.omnitool.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.ESSENCE_OMNITOOL))
            .register(consumer, "essence:knowledge/tools/essence_omnitool");

        pickaxe = Advancement.Builder.builder()
            .withDisplay(
                EssenceObjectHolders.ESSENCE_PICKAXE,
                new TranslationTextComponent("advancements.essence.knowledge.tools.pickaxe.title"),
                new TranslationTextComponent("advancements.essence.knowledge.tools.pickaxe.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.ESSENCE_PICKAXE))
            .register(consumer, "essence:knowledge/tools/essence_pickaxe");

        shear = Advancement.Builder.builder()
            .withDisplay(
                EssenceObjectHolders.ESSENCE_SHEAR,
                new TranslationTextComponent("advancements.essence.knowledge.tools.shear.title"),
                new TranslationTextComponent("advancements.essence.knowledge.tools.shear.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.ESSENCE_SHEAR))
            .register(consumer, "essence:knowledge/tools/essence_shear");

        shovel = Advancement.Builder.builder()
            .withDisplay(
                EssenceObjectHolders.ESSENCE_SHOVEL,
                new TranslationTextComponent("advancements.essence.knowledge.tools.shovel.title"),
                new TranslationTextComponent("advancements.essence.knowledge.tools.shovel.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.ESSENCE_SHOVEL))
            .register(consumer, "essence:knowledge/tools/essence_shovel");

        sword = Advancement.Builder.builder()
            .withDisplay(
                EssenceObjectHolders.ESSENCE_SWORD,
                new TranslationTextComponent("advancements.essence.knowledge.tools.sword.title"),
                new TranslationTextComponent("advancements.essence.knowledge.tools.sword.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceObjectHolders.ESSENCE_SWORD))
            .register(consumer, "essence:knowledge/tools/essence_sword");
    }

    public static Advancement getToolTypes() {
        return toolTypes;
    }

    public static Advancement getAxe() {
        return axe;
    }

    public static Advancement getBow() {
        return bow;
    }

    public static Advancement getHoe() {
        return hoe;
    }

    public static Advancement getOmniTool() {
        return omniTool;
    }

    public static Advancement getPickaxe() {
        return pickaxe;
    }

    public static Advancement getShear() {
        return shear;
    }

    public static Advancement getShovel() {
        return shovel;
    }

    public static Advancement getSword() {
        return sword;
    }
}
