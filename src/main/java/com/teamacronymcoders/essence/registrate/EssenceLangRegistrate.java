package com.teamacronymcoders.essence.registrate;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.datagen.advancement.CoreAdvancementProvider;
import com.teamacronymcoders.essence.datagen.advancement.KnowledgeAdvancementProvider;
import com.teamacronymcoders.essence.datagen.advancement.misc.EssenceKnowledgeAdvancements;
import com.teamacronymcoders.essence.datagen.advancement.misc.EssenceMaterialTierAdvancements;
import com.teamacronymcoders.essence.datagen.advancement.misc.EssenceToolTypeAdvancements;
import com.teamacronymcoders.essence.datagen.advancement.modifier.*;
import com.teamacronymcoders.essence.registrate.lang.EssenceBaseLangProviderRegistrate;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import static com.teamacronymcoders.essence.util.registration.EssenceModifierRegistration.*;

public class EssenceLangRegistrate {

  public static ProviderType<EssenceBaseLangProviderRegistrate> EN_US_UD = ProviderType.register("essence_en_us_ud", (type) -> (p, e) -> new EssenceBaseLangProviderRegistrate(p, e.getGenerator(), "en_us"));

  public static void init(Registrate registrate) {
    registrate.addDataGenerator(EN_US_UD, provider -> {
      addAdvancements(provider);
      addCommands(provider);
      addKnowledge(provider);
      addItemMisc(provider);
      addItems(provider);
      addMisc(provider);
      addModifiers(provider);
      addModifierMisc(provider);
      addTooltip(provider);
    });
  }

  private static void addAdvancements(EssenceBaseLangProviderRegistrate provider) {
    provider.addAdvancement(CoreAdvancementProvider.getCore(), "Essence", "Essence Advancements");
    provider.addAdvancement(KnowledgeAdvancementProvider.getKnowledgeRoot(), "Essence-tial Knowledge", "Knowledge Advancements");
    provider.addAdvancement(KnowledgeAdvancementProvider.getKnowledgeModifierRoot(), "Tool-Knowledge", "Knowledge about Essence-Infused Tools");
    provider.addAdvancement(KnowledgeAdvancementProvider.getKnowledgeToolRoot(), "Modifier-Knowledge", "Knowledge relating to Modifiers");
    provider.addAdvancement(ArrowKnowledgeAdvancements.getArrow(), "Bow-Specific Modifiers", "Various Infusions with Bow-Specific Properties");
    provider.addAdvancement(ArrowKnowledgeAdvancements.getBrewed(), "Brewed", "Brewed with 11 Secret Herbs and Spices");
    provider.addAdvancement(ArrowKnowledgeAdvancements.getKeen(), "Keen", "You used Snipe, It Was Super-Effective!");
    provider.addAdvancement(AttributeKnowledgeAdvancements.getAttribute(), "Attribute-Modifiers", "Various Infusions with Attribute-Altering Properties");
    provider.addAdvancement(AttributeKnowledgeAdvancements.getArmor(), "Armor", "My Armor is Impenetrable!");
    provider.addAdvancement(AttributeKnowledgeAdvancements.getArmorToughness(), "Armor-Toughness", "Tougher Than Your Average Piece of Spaghetti");
    provider.addAdvancement(AttributeKnowledgeAdvancements.getAttackDamage(), "Attack-Damage", "The Harder You Hit, The Faster They Fall");
    provider.addAdvancement(AttributeKnowledgeAdvancements.getMaxHealth(), "Max-Health", "I'm Healthy by Default");
    provider.addAdvancement(AttributeKnowledgeAdvancements.getMovementSpeed(), "Movement-Speed", "We've Got To Hit The 88/mph Mark Marty!");
    provider.addAdvancement(CosmeticKnowledgeAdvancements.getCosmetic(), "Cosmetic-Modifier", "Various Infusions with Cosmetic Properties");
    provider.addAdvancement(CosmeticKnowledgeAdvancements.getEnchanted(), "Enchanted", "Sparkle like a Twinkling Star");
    provider.addAdvancement(EnchantmentKnowledgeAdvancementProvider.getEnchantment(), "Enchantment-Granting Modifiers", "Various Infusions with Enchanting Properties");
    provider.addAdvancement(EnchantmentKnowledgeAdvancementProvider.getEfficiency(), "Efficiency", "Efficiency is overrated, Just gotta go fast");
    provider.addAdvancement(EnchantmentKnowledgeAdvancementProvider.getInfinity(), "Infinity", "UNLIMITED POWAH!");
    provider.addAdvancement(EnchantmentKnowledgeAdvancementProvider.getKnockback(), "Knockback", "And it's going and going and going...");
    provider.addAdvancement(EnchantmentKnowledgeAdvancementProvider.getLuck(), "Luck", "Luck had nothing to do with it");
    provider.addAdvancement(EnchantmentKnowledgeAdvancementProvider.getMending(), "Mending", "Experience mends all wounds...");
    provider.addAdvancement(EnchantmentKnowledgeAdvancementProvider.getSilkTouch(), "Silk-Touch", "Silky Smooth");
    provider.addAdvancement(EnchantmentKnowledgeAdvancementProvider.getStrengthened(), "Strengthened", "IT'S OVER 9000!!!");
    provider.addAdvancement(EnchantmentKnowledgeAdvancementProvider.getUnbreaking(), "Unbreaking", "Unbreakable Timmy Schmidt");
    provider.addAdvancement(InteractionKnowledgeAdvancements.getInteraction(), "Interaction-Changing Modifiers", "Various Infusions with Interaction-Changing Properties");
    provider.addAdvancement(InteractionKnowledgeAdvancements.getCascading(), "Cascading", "Cascading World Exception");
    provider.addAdvancement(InteractionKnowledgeAdvancements.getExpander(), "Expander", "Well That's Quite the Reach!");
    provider.addAdvancement(InteractionKnowledgeAdvancements.getFiery(), "Fiery", "Muy Caliente");
    provider.addAdvancement(InteractionKnowledgeAdvancements.getRainbow(), "Rainbow", "Catch the rainbow, Taste the rainbow");
    provider.addAdvancement(EssenceKnowledgeAdvancements.getMiscKnowledge(), "Miscellaneous Knowledge", "Assorted knowledge of the world");
    provider.addAdvancement(EssenceKnowledgeAdvancements.getArborealKnowledge(), "Arboreal Knowledge", "Knowledge of The Essence of Trees");
    provider.addAdvancement(EssenceMaterialTierAdvancements.getTiers(), "Material-Tiers", "Materials and Their Properties");
    provider.addAdvancement(EssenceMaterialTierAdvancements.getBasic(), "Basic-Tier", "Basic-Quality Materials");
    provider.addAdvancement(EssenceMaterialTierAdvancements.getEmpowered(), "Empowered-Tier", "Empowered-Quality Materials");
    provider.addAdvancement(EssenceMaterialTierAdvancements.getSupreme(), "Supreme-Tier", "Supreme-Quality Materials");
    provider.addAdvancement(EssenceMaterialTierAdvancements.getDivine(), "Divine-Tier", "Divine-Quality Materials");
    provider.addAdvancement(EssenceToolTypeAdvancements.getToolTypes(), "Tool-Types", "The various implementations of Essence-Infused Materials in Tools");
    provider.addAdvancement(EssenceToolTypeAdvancements.getAxe(), "Essence-Infused Axe", "A Axe holding the Essence of The World");
    provider.addAdvancement(EssenceToolTypeAdvancements.getBow(), "Essence-Infused Bow", "A Bow holding the Essence of The World");
    provider.addAdvancement(EssenceToolTypeAdvancements.getHoe(), "Essence-Infused Hoe", "A Hoe holding the Essence of The World");
    provider.addAdvancement(EssenceToolTypeAdvancements.getOmniTool(), "Essence-Infused Omnitool", "A Omnitool holding the Essence of The World");
    provider.addAdvancement(EssenceToolTypeAdvancements.getPickaxe(), "Essence-Infused Pickaxe", "A Pickaxe holding the Essence of The World");
    provider.addAdvancement(EssenceToolTypeAdvancements.getShear(), "Essence-Infused Shears", "A Pair of Shears holding the Essence of The World");
    provider.addAdvancement(EssenceToolTypeAdvancements.getShovel(), "Essence-Infused Shovel", "A Shovel holding the Essence of The World");
    provider.addAdvancement(EssenceToolTypeAdvancements.getSword(), "Essence-Infused Sword", "A Sword holding the Essence of The World");
  }

  private static void addModifierMisc (EssenceBaseLangProviderRegistrate provider) {
    provider.add("essence.brewed.contents", "Contents:");
    provider.add("essence.brewed.duration", "Duration %s");
    provider.add("essence.brewed.amplifier", "Amplifier %s");
    provider.add("essence.cascading.type.none", "???");
    provider.add("essence.cascading.type.excavation", "Excavation");
    provider.add("essence.cascading.type.lumber", "Lumber");
    provider.add("essence.cascading.type.vein", "Vein");
    provider.add("essence.strengthened.type.arthropod", "Arthropods");
    provider.add("essence.strengthened.type.sharpness", "Melee");
    provider.add("essence.strengthened.type.smite", "Holy");
    provider.add("essence.strengthened.type.power", "Ranged");
  }

  private static void addItemMisc (EssenceBaseLangProviderRegistrate provider) {
    provider.add("essence.wrench.disclaimer", "Please note: ");
    provider.add("essence.wrench.disclaimer_message", "The Wrench makes every attempt fully set a block into Stasis, unfortunately due to situations out of our control. Not all parts of a block's aspects may be put into stasis.");
    provider.add("essence.wrench.mode.tooltip", "Wrench Mode");
    provider.add("essence.mode.wrench.serialize", "Stasis");
    provider.add("essence.mode.wrench.rotate", "Rotate");
    provider.add("essence.mode.wrench.trigger", "Trigger");
    provider.add("tome.essence.mode.tooltip", "Mode:");
    provider.add("tome.essence.experience.mode.fill", "Fill");
    provider.add("tome.essence.experience.mode.drain", "Drain");
    provider.add("tome.essence.experience.mode.absorb", "Absorb");
  }

  private static void addCommands (EssenceBaseLangProviderRegistrate provider) {
    provider.add("command.essence.generic.dump.knowledge", "Knowledge [%s] of Type: [%s]");
    provider.add("command.essence.generic.dump.modifier", "Modifier [%s] of Type: [%s]");
    provider.add("command.essence.knowledge.add", "Added Knowledge: [%s] to Player/UUID [%s]");
    provider.add("command.essence.knowledge.remove", "Removed Knowledge: [%s] to Player/UUID [%s]");
    provider.add("command.essence.knowledge.clear", "Cleared All Knowledge from Player/UUID [%s]");
    provider.add("command.essence.knowledge.dump", "Dumped Knowledge for Player/UUID [%s]");
    provider.add("command.essence.modifier.itemstack.add", "Added Modifier: %s to Tool in Hand: %s");
    provider.add("command.essence.modifier.itemstack.remove", "Removed Modifier: %s from Tool in Hand: %s");
    provider.add("command.essence.modifier.itemstack.merge", "Merged Modifier Tag into Modifier: %s");
    provider.add("command.essence.modifier.itemstack.level_up", "Leveled-Up Modifier: %s");
    provider.add("command.essence.modifier.itemstack.level_down", "Leveled-Down Modifier: %s");
    provider.add("command.essence.registry.dump.knowledge", "Now dumping contents of Registry 'Knowledge' to Logs");
    provider.add("command.essence.registry.dump.modifier", "Now dumping contents of Registry 'Modifier' to Logs");
    provider.add("command.essence.registry.dump.knowledge.type", "Knowledge of Type: [%s] with ID of: [%s]");
    provider.add("command.essence.registry.dump.modifier.type", "Modifier of Type: [%s] with ID of: [%s]");
    provider.add("command.essence.setup_dev", "Dev-World Successfully Setup!");
  }


  private static void addItems(EssenceBaseLangProviderRegistrate provider) {
    provider.add("item.essence.essence_bucket", "Bucket of Essence");
    provider.add("item.essence.experience_bucket", "Bucket of Experience");
  }

  private static void addKnowledge(EssenceBaseLangProviderRegistrate provider) {
    provider.add("knowledge.essence.brewed", "Brewed");
    provider.add("knowledge.essence.keen", "Keen");
    provider.add("knowledge.essence.enchanted", "Enchanted");
    provider.add("knowledge.essence.armor", "Armor");
    provider.add("knowledge.essence.armor_toughness", "Armor-Toughness");
    provider.add("knowledge.essence.attack_damage", "Attack-Damage");
    provider.add("knowledge.essence.max_health", "Max-Health");
    provider.add("knowledge.essence.movement_speed", "Movement-Speed");
    provider.add("knowledge.essence.infinity", "Infinity");
    provider.add("knowledge.essence.knockback", "Knockback");
    provider.add("knowledge.essence.luck", "Luck");
    provider.add("knowledge.essence.silk_touch", "Silk Touch");
    provider.add("knowledge.essence.unbreaking", "Unbreaking");
    provider.add("knowledge.essence.mending", "Mending");
    provider.add("knowledge.essence.strengthened", "Strengthened");
    provider.add("knowledge.essence.expander", "Expander");
    provider.add("knowledge.essence.fiery", "Fiery");
    provider.add("knowledge.essence.rainbow", "Rainbow");
    provider.add("knowledge.essence.cascading", "Cascading");
    provider.add("knowledge.essence.tool_crafting", "Tool-Crafting");
    provider.add("knowledge.essence.arboreal_notes", "Arboreal Notes");
    provider.add("knowledge.essence.basic_tier", "Basic-Tier");
    provider.add("knowledge.essence.empowered_tier", "Empowered-Tier");
    provider.add("knowledge.essence.supreme_tier", "Supreme-Tier");
    provider.add("knowledge.essence.divine_tier", "Divine-Tier");
  }

  private static void addModifiers (EssenceBaseLangProviderRegistrate provider) {
    //Irregulars
    provider.add("modifier.essence.attribute", "Attribute [%s]");
    provider.add("modifier.essence.brewed", "Brewed [Hold %s for Info]");
    provider.add("modifier.essence.brewed.cleaned", "Brewed");
    provider.add("modifier.essence.cascading", "Cascading [%s]");
    provider.add("modifier.essence.strengthened", "Strengthened %s [%s]");
    provider.add("modifier.essence.strengthened.cleaned", "Strengthened [%s]");

    //Regulars
    provider.addModifier(EFFICIENCY_MODIFIER.get(), "Efficiency");
    provider.addModifier(ENCHANTED_MODIFIER.get(), "Enchanted");
    provider.addModifier(EXPANDER_MODIFIER.get(), "Expander");
    provider.addModifier(FIERY_MODIFIER.get(), "Fiery");
    provider.addModifier(KEEN_MODIFIER.get(), "Keen");
    provider.addModifier(KNOCKBACK_MODIFIER.get(), "Knockback");
    provider.addModifier(LUCK_MODIFIER.get(), "Luck");
    provider.addModifier(RAINBOW_MODIFIER.get(), "Rainbow");
    provider.addModifier(SILK_TOUCH_MODIFIER.get(), "Silk Touch");
    provider.addModifier(UNBREAKING_MODIFIER.get(), "Unbreaking");
  }

  private static void addMisc (EssenceBaseLangProviderRegistrate provider) {
    provider.add("attribute.essence.armor", "Armor");
    provider.add("attribute.essence.armor_toughness", "Armor-Toughness");
    provider.add("attribute.essence.attack_damage", "Attack-Damage");
    provider.add("attribute.essence.max_health", "Max-Health");
    provider.add("attribute.essence.movement_speed", "Movement Speed");
    provider.add("container.essence.portable_crafter", "Portable-Crafter");
    provider.add("curios.identifier.backpack", "Backpack");
    provider.add("fluid.essence.essence", "Essence");
    provider.add("itemGroup.resources", "Titanium Resources");
    provider.add(Essence.CORE_TAB, "Essence Core");
    provider.add(Essence.TOOL_TAB, "Essence Tools");
    provider.add("sound.essence.subtitle.crafting.infusion", "Magically Infusing");
    provider.add("sound.essence.subtitle.ambient.infusion_book", "Pages Turning");
    provider.add("tier.essence.basic", "Basic");
    provider.add("tier.essence.empowered", "Empowered");
    provider.add("tier.essence.supreme", "Supreme");
    provider.add("tier.essence.divine", "Divine");
    provider.add("patchouli.book.essence.knowledge.tome.name", "Tome of Knowledge");
    provider.add("patchouli.book.essence.knowledge.tome.landingtext", "Welcome to the Libria-Essentium as documented by former Explorer 'C.S. Ironbeak'");
  }

  private static void addTooltip (EssenceBaseLangProviderRegistrate provider) {
    provider.add("tooltip.essence.modifier", "Modifiers:");
    provider.add("tooltip.essence.modifier.free", "Free Modifiers: %s");
    provider.add("tooltip.essence.tool.tier", "Material Tier: ");
    provider.add("tooltip.essence.generic.shiftForInformation", "Hold %s For More Info");
    provider.add("tooltip.essence.tome_of_experience.holding", "Currently Holding");
    provider.add("tooltip.essence.tome_of_experience.levels", "Levels Held %s/%s");
    provider.add("tooltip.essence.tome_of_experience.amount", "Stored Experience: ");
  }
}
