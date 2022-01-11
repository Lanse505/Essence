package com.teamacronymcoders.essence.compat.registrate;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.modifier.IModifier;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;

import static com.teamacronymcoders.essence.compat.registrate.EssenceModifierRegistrate.*;

public class EssenceLangRegistrate {

    public static void init(Registrate registrate) {
        registrate.addDataGenerator(ProviderType.LANG, provider -> {
            addAdvancements(provider);
            addCommands(provider);
            addKnowledge(provider);
            addItemMisc(provider);
            addMisc(provider);
            addModifiers(provider);
            addModifierMisc(provider);
            addTooltip(provider);
        });
    }

    /**
     * Helper function to add advancements to the lang generator
     *
     * @param advancement The advancement for localizations to be added
     * @param title       The title of the advancement
     * @param description The description of the advancement
     */
    private static void addAdvancement(RegistrateLangProvider provider, Advancement advancement, String title, String description) {
        final DisplayInfo display = advancement.getDisplay();
        provider.add(display.getTitle().getString(), title);
        provider.add(display.getDescription().getString(), description);
    }

    public static void addModifier(RegistrateLangProvider provider, IModifier modifier, String translation) {
        provider.add(modifier.getTranslationName(), translation);
    }

    private static void addAdvancements(RegistrateLangProvider provider) {
        addAdvancement(provider, EssenceAdvancementRegistrate.getCore(), "Essence", "Essence Advancements");
        addAdvancement(provider, EssenceAdvancementRegistrate.getKnowledgeRoot(), "Essence-tial Knowledge", "Knowledge Advancements");
        addAdvancement(provider, EssenceAdvancementRegistrate.getKnowledgeToolRoot(), "Tool-Knowledge", "Knowledge about Essence-Infused Tools");
        addAdvancement(provider, EssenceAdvancementRegistrate.getKnowledgeModifierRoot(), "Modifier-Knowledge", "Knowledge relating to Modifiers");
        addAdvancement(provider, EssenceAdvancementRegistrate.getArrow(), "Bow-Specific Modifiers", "Various Infusions with Bow-Specific Properties");
        addAdvancement(provider, EssenceAdvancementRegistrate.getBrewed(), "Brewed", "Brewed with 11 Secret Herbs and Spices");
        addAdvancement(provider, EssenceAdvancementRegistrate.getKeen(), "Keen", "You used Snipe, It Was Super-Effective!");
        addAdvancement(provider, EssenceAdvancementRegistrate.getAttribute(), "Attribute-Modifiers", "Various Infusions with Attribute-Altering Properties");
        addAdvancement(provider, EssenceAdvancementRegistrate.getArmor(), "Armor", "My Armor is Impenetrable!");
        addAdvancement(provider, EssenceAdvancementRegistrate.getArmorToughness(), "Armor-Toughness", "Tougher Than Your Average Piece of Spaghetti");
        addAdvancement(provider, EssenceAdvancementRegistrate.getAttackDamage(), "Attack-Damage", "The Harder You Hit, The Faster They Fall");
        addAdvancement(provider, EssenceAdvancementRegistrate.getMaxHealth(), "Max-Health", "I'm Healthy by Default");
        addAdvancement(provider, EssenceAdvancementRegistrate.getMovementSpeed(), "Movement-Speed", "We've Got To Hit The 88/mph Mark Marty!");
        addAdvancement(provider, EssenceAdvancementRegistrate.getCosmetic(), "Cosmetic-Modifier", "Various Infusions with Cosmetic Properties");
        addAdvancement(provider, EssenceAdvancementRegistrate.getEnchanted(), "Enchanted", "Sparkle like a Twinkling Star");
        addAdvancement(provider, EssenceAdvancementRegistrate.getEnchantment(), "Enchantment-Granting Modifiers", "Various Infusions with Enchanting Properties");
        addAdvancement(provider, EssenceAdvancementRegistrate.getEfficiency(), "Efficiency", "Efficiency is overrated, Just gotta go fast");
        addAdvancement(provider, EssenceAdvancementRegistrate.getInfinity(), "Infinity", "UNLIMITED POWAH!");
        addAdvancement(provider, EssenceAdvancementRegistrate.getKnockback(), "Knockback", "And it's going and going and going...");
        addAdvancement(provider, EssenceAdvancementRegistrate.getLuck(), "Luck", "Luck had nothing to do with it");
        addAdvancement(provider, EssenceAdvancementRegistrate.getMending(), "Mending", "Experience mends all wounds...");
        addAdvancement(provider, EssenceAdvancementRegistrate.getSilkTouch(), "Silk-Touch", "Silky Smooth");
        addAdvancement(provider, EssenceAdvancementRegistrate.getStrengthened(), "Strengthened", "IT'S OVER 9000!!!");
        addAdvancement(provider, EssenceAdvancementRegistrate.getUnbreaking(), "Unbreaking", "Unbreakable Timmy Schmidt");
        addAdvancement(provider, EssenceAdvancementRegistrate.getInteraction(), "Interaction-Changing Modifiers", "Various Infusions with Interaction-Changing Properties");
        addAdvancement(provider, EssenceAdvancementRegistrate.getCascading(), "Cascading", "Cascading World Exception");
        addAdvancement(provider, EssenceAdvancementRegistrate.getExpander(), "Expander", "Well That's Quite the Reach!");
        addAdvancement(provider, EssenceAdvancementRegistrate.getFiery(), "Fiery", "Muy Caliente");
        addAdvancement(provider, EssenceAdvancementRegistrate.getRainbow(), "Rainbow", "Catch the rainbow, Taste the rainbow");
        addAdvancement(provider, EssenceAdvancementRegistrate.getMiscKnowledge(), "Miscellaneous Knowledge", "Assorted knowledge of the world");
        addAdvancement(provider, EssenceAdvancementRegistrate.getArborealKnowledge(), "Arboreal Knowledge", "Knowledge of The Essence of Trees");
        addAdvancement(provider, EssenceAdvancementRegistrate.getTiers(), "Material-Tiers", "Materials and Their Properties");
        addAdvancement(provider, EssenceAdvancementRegistrate.getBasic(), "Basic-Tier", "Basic-Quality Materials");
        addAdvancement(provider, EssenceAdvancementRegistrate.getEmpowered(), "Empowered-Tier", "Empowered-Quality Materials");
        addAdvancement(provider, EssenceAdvancementRegistrate.getSupreme(), "Supreme-Tier", "Supreme-Quality Materials");
        addAdvancement(provider, EssenceAdvancementRegistrate.getDivine(), "Divine-Tier", "Divine-Quality Materials");
        addAdvancement(provider, EssenceAdvancementRegistrate.getToolTypes(), "Tool-Types", "The various implementations of Essence-Infused Materials in Tools");
        addAdvancement(provider, EssenceAdvancementRegistrate.getAxe(), "Essence-Infused Axe", "A Axe holding the Essence of The World");
        addAdvancement(provider, EssenceAdvancementRegistrate.getBow(), "Essence-Infused Bow", "A Bow holding the Essence of The World");
        addAdvancement(provider, EssenceAdvancementRegistrate.getHoe(), "Essence-Infused Hoe", "A Hoe holding the Essence of The World");
        addAdvancement(provider, EssenceAdvancementRegistrate.getOmniTool(), "Essence-Infused Omnitool", "A Omnitool holding the Essence of The World");
        addAdvancement(provider, EssenceAdvancementRegistrate.getPickaxe(), "Essence-Infused Pickaxe", "A Pickaxe holding the Essence of The World");
        addAdvancement(provider, EssenceAdvancementRegistrate.getShear(), "Essence-Infused Shears", "A Pair of Shears holding the Essence of The World");
        addAdvancement(provider, EssenceAdvancementRegistrate.getShovel(), "Essence-Infused Shovel", "A Shovel holding the Essence of The World");
        addAdvancement(provider, EssenceAdvancementRegistrate.getSword(), "Essence-Infused Sword", "A Sword holding the Essence of The World");
    }

    private static void addModifierMisc(RegistrateLangProvider provider) {
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

    private static void addItemMisc(RegistrateLangProvider provider) {
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

    private static void addCommands(RegistrateLangProvider provider) {
        provider.add("command.essence.generic.dump.knowledge", "Knowledge [%s]");
        provider.add("command.essence.generic.dump.modifier", "Modifier [%s]");
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
        provider.add("command.essence.registry.dump.knowledge.type", "Knowledge with ID of: [%s]");
        provider.add("command.essence.registry.dump.modifier.type", "Modifier with ID of: [%s]");
        provider.add("command.essence.setup_dev", "Dev-World Successfully Setup!");
    }

    private static void addKnowledge(RegistrateLangProvider provider) {
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
        provider.add("knowledge.essence.fiery_modifier", "Fiery");
        provider.add("knowledge.essence.rainbow", "Rainbow");
        provider.add("knowledge.essence.cascading", "Cascading");
        provider.add("knowledge.essence.tool_crafting", "Tool-Crafting");
        provider.add("knowledge.essence.arboreal_notes", "Arboreal Notes");
        provider.add("knowledge.essence.basic_tier", "Basic-Tier");
        provider.add("knowledge.essence.empowered_tier", "Empowered-Tier");
        provider.add("knowledge.essence.supreme_tier", "Supreme-Tier");
        provider.add("knowledge.essence.divine_tier", "Divine-Tier");
    }

    private static void addModifiers(RegistrateLangProvider provider) {
        //Irregulars
        provider.add("modifier.essence.attribute", "Attribute [%s]");
        provider.add("modifier.essence.brewed", "Brewed [Hold %s for Info]");
        provider.add("modifier.essence.brewed.cleaned", "Brewed");
        provider.add("modifier.essence.cascading", "Cascading [%s]");
        provider.add("modifier.essence.strengthened", "Strengthened %s [%s]");
        provider.add("modifier.essence.strengthened.cleaned", "Strengthened [%s]");

        //Regulars
        addModifier(provider, EFFICIENCY_MODIFIER.get(), "Efficiency");
        addModifier(provider, ENCHANTED_MODIFIER.get(), "Enchanted");
        addModifier(provider, EXPANDER_MODIFIER.get(), "Expander");
        addModifier(provider, FIERY_MODIFIER.get(), "Fiery");
        addModifier(provider, KEEN_MODIFIER.get(), "Keen");
        addModifier(provider, KNOCKBACK_MODIFIER.get(), "Knockback");
        addModifier(provider, LUCK_MODIFIER.get(), "Luck");
        addModifier(provider, RAINBOW_MODIFIER.get(), "Rainbow");
        addModifier(provider, SILK_TOUCH_MODIFIER.get(), "Silk Touch");
        addModifier(provider, UNBREAKING_MODIFIER.get(), "Unbreaking");
        addModifier(provider, SOAKED_MODIFIER.get(), "Soaked");
        addModifier(provider, ENDERIC_MODIFIER.get(), "Enderic");
    }

    private static void addMisc(RegistrateLangProvider provider) {
        provider.add("attribute.essence.armor", "Armor");
        provider.add("attribute.essence.armor_toughness", "Armor-Toughness");
        provider.add("attribute.essence.attack_damage", "Attack-Damage");
        provider.add("attribute.essence.max_health", "Max-Health");
        provider.add("attribute.essence.movement_speed", "Movement Speed");
        provider.add("container.essence.portable.crafter", "Crafting Cracker");
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

    private static void addTooltip(RegistrateLangProvider provider) {
        provider.add("tooltip.essence.modifier", "Modifiers:");
        provider.add("tooltip.essence.modifier.free", "Free Modifiers: %s");
        provider.add("tooltip.essence.tool.tier", "Material Tier: ");
        provider.add("tooltip.essence.generic.shiftForInformation", "Hold %s For More Info");
        provider.add("tooltip.essence.tome_of_experience.holding", "Currently Holding");
        provider.add("tooltip.essence.tome_of_experience.levels", "Levels Held %s/%s");
        provider.add("tooltip.essence.tome_of_experience.amount", "Stored Experience: ");
    }
}
