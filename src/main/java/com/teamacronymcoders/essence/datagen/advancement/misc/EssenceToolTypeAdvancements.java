package com.teamacronymcoders.essence.datagen.advancement.misc;

import com.teamacronymcoders.essence.datagen.advancement.KnowledgeAdvancementProvider;
import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.serializable.advancement.criterion.knowledge.UnlockKnowledgeCriterionInstance;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.registration.EssenceKnowledgeRegistration;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.EntityPredicate.AndPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

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

  public static void addToolTypeAdvancements (Consumer<Advancement> consumer) {
    toolTypes = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_AXE.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.tool_type.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.tool_type.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(KnowledgeAdvancementProvider.getKnowledgeToolRoot())
            .withCriterion("tool_crafting", new UnlockKnowledgeCriterionInstance(EssenceKnowledgeRegistration.TOOL_CRAFTING.get(), AndPredicate.ANY_AND))
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.TOME_OF_KNOWLEDGE.get()))
            .register(consumer, "essence:knowledge/tools/tool_types");

    axe = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_AXE.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.axe.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.axe.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_AXE.get()))
            .register(consumer, "essence:knowledge/tools/essence_axe");

    bow = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_BOW.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.bow.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.bow.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_BOW.get()))
            .register(consumer, "essence:knowledge/tools/essence_bow");

    hoe = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_HOE.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.hoe.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.hoe.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_HOE.get()))
            .register(consumer, "essence:knowledge/tools/essence_hoe");

    omniTool = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_OMNITOOL.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.omnitool.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.omnitool.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_OMNITOOL.get()))
            .register(consumer, "essence:knowledge/tools/essence_omnitool");

    pickaxe = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_PICKAXE.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.pickaxe.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.pickaxe.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_PICKAXE.get()))
            .register(consumer, "essence:knowledge/tools/essence_pickaxe");

    shear = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_SHEAR.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.shear.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.shear.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_SHEAR.get()))
            .register(consumer, "essence:knowledge/tools/essence_shear");

    shovel = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_SHOVEL.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.shovel.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.shovel.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_SHOVEL.get()))
            .register(consumer, "essence:knowledge/tools/essence_shovel");

    sword = Advancement.Builder.builder()
            .withDisplay(
                    EssenceItemRegistrate.ESSENCE_SWORD.get(),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.sword.title"),
                    new TranslationTextComponent("advancements.essence.knowledge.tools.sword.description"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.CHALLENGE, false, false, true
            )
            .withParent(toolTypes)
            .withCriterion("tome_of_knowledge", InventoryChangeTrigger.Instance.forItems(EssenceItemRegistrate.ESSENCE_SWORD.get()))
            .register(consumer, "essence:knowledge/tools/essence_sword");
  }

  public static Advancement getToolTypes () {
    return toolTypes;
  }

  public static Advancement getAxe () {
    return axe;
  }

  public static Advancement getBow () {
    return bow;
  }

  public static Advancement getHoe () {
    return hoe;
  }

  public static Advancement getOmniTool () {
    return omniTool;
  }

  public static Advancement getPickaxe () {
    return pickaxe;
  }

  public static Advancement getShear () {
    return shear;
  }

  public static Advancement getShovel () {
    return shovel;
  }

  public static Advancement getSword () {
    return sword;
  }
}
