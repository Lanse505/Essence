package com.teamacronymcoders.essence.datagen.lang;

import com.teamacronymcoders.essence.block.EssenceBrickBlock;
import com.teamacronymcoders.essence.datagen.advancement.CoreAdvancementProvider;
import com.teamacronymcoders.essence.datagen.advancement.KnowledgeAdvancementProvider;
import com.teamacronymcoders.essence.datagen.advancement.misc.*;
import com.teamacronymcoders.essence.datagen.advancement.modifier.*;
import net.minecraft.data.DataGenerator;

import java.util.Arrays;
import java.util.List;

import static com.teamacronymcoders.essence.util.EssenceObjectHolders.*;
import static com.teamacronymcoders.essence.util.registration.EssenceModifierRegistration.*;

public class EssenceEnglishLangProvider extends EssenceBaseLangProvider {

    public static final List<EssenceBrickBlock> BRICK_BLOCK_LIST = Arrays.asList(
        ESSENCE_BRICKS_WHITE,
        ESSENCE_BRICKS_ORANGE,
        ESSENCE_BRICKS_MAGENTA,
        ESSENCE_BRICKS_LIGHT_BLUE,
        ESSENCE_BRICKS_YELLOW,
        ESSENCE_BRICKS_LIME,
        ESSENCE_BRICKS_PINK,
        ESSENCE_BRICKS_GRAY,
        ESSENCE_BRICKS_LIGHT_GRAY,
        ESSENCE_BRICKS_CYAN,
        ESSENCE_BRICKS_PURPLE,
        ESSENCE_BRICKS_BLUE,
        ESSENCE_BRICKS_BROWN,
        ESSENCE_BRICKS_GREEN,
        ESSENCE_BRICKS_RED,
        ESSENCE_BRICKS_BLACK
    );

    public EssenceEnglishLangProvider(DataGenerator generator) {
        super(generator, "en_us");
    }

    @Override
    protected void addTranslations() {
        addAdvancements();
        addBlocks();
        addCommands();
        addItems();
        addItemMisc();
        addKnowledge();
        addModifiers();
        addModifierMisc();
        addMisc();
        addTooltip();
    }

    private void addAdvancements() {
        add(CoreAdvancementProvider.getCore(), "Essence", "Essence Advancements");

        add(KnowledgeAdvancementProvider.getKnowledgeRoot(), "Essence-tial Knowledge", "Knowledge Advancements");
        add(KnowledgeAdvancementProvider.getKnowledgeModifierRoot(), "Tool-Knowledge", "Knowledge about Essence-Infused Tools");
        add(KnowledgeAdvancementProvider.getKnowledgeToolRoot(), "Modifier-Knowledge", "Knowledge relating to Modifiers");

        add(ArrowKnowledgeAdvancements.getArrow(), "Bow-Specific Modifiers", "Various Infusions with Bow-Specific Properties");
        add(ArrowKnowledgeAdvancements.getBrewed(), "Brewed", "Brewed with 11 Secret Herbs and Spices");
        add(ArrowKnowledgeAdvancements.getKeen(), "Keen", "You used Snipe, It Was Super-Effective!");

        add(AttributeKnowledgeAdvancements.getAttribute(), "Attribute-Modifiers", "Various Infusions with Attribute-Altering Properties");
        add(AttributeKnowledgeAdvancements.getArmor(), "Armor", "My Armor is Impenetrable!");
        add(AttributeKnowledgeAdvancements.getArmorToughness(), "Armor-Toughness", "Tougher Than Your Average Piece of Spaghetti");
        add(AttributeKnowledgeAdvancements.getAttackDamage(), "Attack-Damage", "The Harder You Hit, The Faster They Fall");
        add(AttributeKnowledgeAdvancements.getMaxHealth(), "Max-Health", "I'm Healthy by Default");
        add(AttributeKnowledgeAdvancements.getMovementSpeed(), "Movement-Speed", "We've Got To Hit The 88/mph Mark Marty!");

        add(CosmeticKnowledgeAdvancements.getCosmetic(), "Cosmetic-Modifier", "Various Infusions with Cosmetic Properties");
        add(CosmeticKnowledgeAdvancements.getEnchanted(), "Enchanted", "Sparkle like a Twinkling Star");

        add(EnchantmentKnowledgeAdvancementProvider.getEnchantment(), "Enchantment-Granting Modifiers", "Various Infusions with Enchanting Properties");
        add(EnchantmentKnowledgeAdvancementProvider.getEfficiency(), "Efficiency", "Efficiency is overrated, Just gotta go fast");
        add(EnchantmentKnowledgeAdvancementProvider.getInfinity(), "Infinity", "UNLIMITED POWAH!");
        add(EnchantmentKnowledgeAdvancementProvider.getKnockback(), "Knockback", "And it's going and going and going...");
        add(EnchantmentKnowledgeAdvancementProvider.getLuck(), "Luck", "Luck had nothing to do with it");
        add(EnchantmentKnowledgeAdvancementProvider.getMending(), "Mending", "Experience mends all wounds...");
        add(EnchantmentKnowledgeAdvancementProvider.getSilkTouch(), "Silk-Touch", "Silky Smooth");
        add(EnchantmentKnowledgeAdvancementProvider.getStrengthened(), "Strengthened", "IT'S OVER 9000!!!");
        add(EnchantmentKnowledgeAdvancementProvider.getUnbreaking(), "Unbreaking", "Unbreakable Timmy Schmidt");

        add(InteractionKnowledgeAdvancements.getInteraction(), "Interaction-Changing Modifiers", "Various Infusions with Interaction-Changing Properties");
        add(InteractionKnowledgeAdvancements.getCascading(), "Cascading", "Cascading World Exception");
        add(InteractionKnowledgeAdvancements.getExpander(), "Expander", "Well That's Quite the Reach!");
        add(InteractionKnowledgeAdvancements.getFiery(), "Fiery", "Muy Caliente");
        add(InteractionKnowledgeAdvancements.getRainbow(), "Rainbow", "Catch the rainbow, Taste the rainbow");

        add(EssenceKnowledgeAdvancements.getMiscKnowledge(), "Miscellaneous Knowledge", "Assorted knowledge of the world");
        add(EssenceKnowledgeAdvancements.getArborealKnowledge(), "Arboreal Knowledge", "Knowledge of The Essence of Trees");

        add(EssenceMaterialTierAdvancements.getTiers(), "Material-Tiers", "Materials and Their Properties");
        add(EssenceMaterialTierAdvancements.getBasic(), "Basic-Tier", "Basic-Quality Materials");
        add(EssenceMaterialTierAdvancements.getEmpowered(), "Empowered-Tier", "Empowered-Quality Materials");
        add(EssenceMaterialTierAdvancements.getSupreme(), "Supreme-Tier", "Supreme-Quality Materials");
        add(EssenceMaterialTierAdvancements.getDivine(), "Divine-Tier", "Divine-Quality Materials");

        add(EssenceToolTypeAdvancements.getToolTypes(), "Tool-Types", "The various implementations of Essence-Infused Materials in Tools");
        add(EssenceToolTypeAdvancements.getAxe(), "Essence-Infused Axe", "A Axe holding the Essence of The World");
        add(EssenceToolTypeAdvancements.getBow(), "Essence-Infused Bow", "A Bow holding the Essence of The World");
        add(EssenceToolTypeAdvancements.getHoe(), "Essence-Infused Hoe", "A Hoe holding the Essence of The World");
        add(EssenceToolTypeAdvancements.getOmniTool(), "Essence-Infused Omnitool", "A Omnitool holding the Essence of The World");
        add(EssenceToolTypeAdvancements.getPickaxe(), "Essence-Infused Pickaxe", "A Pickaxe holding the Essence of The World");
        add(EssenceToolTypeAdvancements.getShear(), "Essence-Infused Shears", "A Pair of Shears holding the Essence of The World");
        add(EssenceToolTypeAdvancements.getShovel(), "Essence-Infused Shovel", "A Shovel holding the Essence of The World");
        add(EssenceToolTypeAdvancements.getSword(), "Essence-Infused Sword", "A Sword holding the Essence of The World");
    }

    private void addBlocks() {
        add("Essence-Infused Metal Block", ESSENCE_INFUSED_METAL_BLOCK, ESSENCE_INFUSED_METAL_EMPOWERED_BLOCK, ESSENCE_INFUSED_METAL_SUPREME_BLOCK, ESSENCE_INFUSED_METAL_DIVINE_BLOCK);

        add(ESSENCE_WOOD_LEAVES, "Essence-Wood Leaves");
        add(ESSENCE_WOOD_LOG, "Essence-Wood Log");
        add(ESSENCE_WOOD_SAPLING, "Essence-Wood Sapling");
        add(ESSENCE_WOOD_PLANKS, "Essence-Wood Planks");
        add(ESSENCE_WOOD_SLAB, "Essence-Wood Slab");

        add(ESSENCE_ORE, "Essence-Infused Ore");
        add(ESSENCE_CRYSTAL_ORE, "Essence-Infused Crystal Ore");

        add(INFUSION_TABLE, "Infusion Table");
        add(INFUSION_PEDESTAL, "Infusion Pedestal");

        addColored(BRICK_BLOCK_LIST, EssenceBrickBlock::hasColor, this::add, " Essence-Infused Bricks");
    }

    private void addModifierMisc() {
        add("essence.brewed.contents", "Contents:");
        add("essence.brewed.duration", "Duration %s");
        add("essence.brewed.amplifier", "Amplifier %s");

        add("essence.cascading.type.none", "???");
        add("essence.cascading.type.excavation", "Excavation");
        add("essence.cascading.type.lumber", "Lumber");
        add("essence.cascading.type.vein", "Vein");

        add("essence.strengthened.type.arthropod", "Arthropods");
        add("essence.strengthened.type.sharpness", "Melee");
        add("essence.strengthened.type.smite", "Holy");
        add("essence.strengthened.type.power", "Ranged");
    }

    private void addItemMisc() {
        add("essence.wrench.disclaimer", "Please note: The Wrench makes every attempt fully set a block into Stasis, unfortunately due to situations out of our control. Not all parts of a block's aspects may be put into stasis.");
        add("essence.wrench.mode.tooltip", "Wrench Mode");
        add("essence.mode.wrench.serialize", "Stasis");
        add("essence.mode.wrench.rotate", "Rotate");
        add("essence.tome.mode.tooltip", "Mode:");
        add("tome.essence.experience.mode.fill", "Fill");
        add("tome.essence.experience.mode.drain", "Drain");
        add("tome.essence.experience.mode.absorb", "Absorb");
    }

    private void addCommands() {
        add("command.essence.generic.dump.knowledge", "Knowledge [%s] of Type: [%s]");
        add("command.essence.generic.dump.modifier", "Modifier [%s] of Type: [%s]");

        add("command.essence.knowledge.add", "Added Knowledge: [%s] to Player/UUID [%s]");
        add("command.essence.knowledge.remove", "Removed Knowledge: [%s] to Player/UUID [%s]");
        add("command.essence.knowledge.clear", "Cleared All Knowledge from Player/UUID [%s]");
        add("command.essence.knowledge.dump", "Dumped Knowledge for Player/UUID [%s]");

        add("command.essence.modifier.itemstack.add", "Added Modifier: %s to Tool in Hand: %s");
        add("command.essence.modifier.itemstack.remove", "Removed Modifier: %s from Tool in Hand: %s");
        add("command.essence.modifier.itemstack.merge", "Merged Modifier Tag into Modifier: %s");
        add("command.essence.modifier.itemstack.level_up", "Leveled-Up Modifier: %s");
        add("command.essence.modifier.itemstack.level_down", "Leveled-Down Modifier: %s");

        add("command.essence.registry.dump.knowledge", "Now dumping contents of Registry 'Knowledge' to Logs");
        add("command.essence.registry.dump.modifier", "Now dumping contents of Registry 'Modifier' to Logs");
        add("command.essence.registry.dump.knowledge.type", "Knowledge of Type: [%s] with ID of: [%s]");
        add("command.essence.registry.dump.modifier.type", "Modifier of Type: [%s] with ID of: [%s]");

        add("command.essence.setup_dev", "Dev-World Successfully Setup!");
    }


    private void addItems() {
        add("item.essence.essence_bucket", "Bucket of Essence");
        add("item.essence.experience_bucket", "Bucket of Essence");
        add(ESSENCE_INFUSED_STICK, "Essence-Wood Sticks");
        add(PORTABLE_CRAFTER, "Portable Crafter");
        add(WRENCH, "Essence-Infused Wrench");
        add(ESSENCE_INFUSED_CRYSTAL, "Essence-Infused Crystal");
        add(TOME_OF_KNOWLEDGE, "Tome of Bound-Knowledge");
        add(TOME_OF_EXPERIENCE, "Tome of Experience");
        add(GLUE_BALL, "Ball of Glue");
        add("Essence-Infused Nugget", ESSENCE_INFUSED_METAL_NUGGET, ESSENCE_INFUSED_METAL_EMPOWERED_NUGGET, ESSENCE_INFUSED_METAL_SUPREME_NUGGET, ESSENCE_INFUSED_METAL_DIVINE_NUGGET);
        add("Essence-Infused Ingot", ESSENCE_INFUSED_METAL, ESSENCE_INFUSED_METAL_EMPOWERED, ESSENCE_INFUSED_METAL_SUPREME, ESSENCE_INFUSED_METAL_DIVINE);
        add("Essence-Infused Axe", ESSENCE_AXE, ESSENCE_AXE_EMPOWERED, ESSENCE_AXE_SUPREME, ESSENCE_AXE_DIVINE);
        add("Essence-Infused Bow", ESSENCE_BOW, ESSENCE_BOW_EMPOWERED, ESSENCE_BOW_SUPREME, ESSENCE_BOW_DIVINE);
        add("Essence-Infused Hoe", ESSENCE_HOE, ESSENCE_HOE_EMPOWERED, ESSENCE_HOE_SUPREME, ESSENCE_HOE_DIVINE);
        add("Essence-Infused Omni-Tool", ESSENCE_OMNITOOL, ESSENCE_OMNITOOL_EMPOWERED, ESSENCE_OMNITOOL_SUPREME, ESSENCE_OMNITOOL_DIVINE);
        add("Essence-Infused Pickaxe", ESSENCE_PICKAXE, ESSENCE_PICKAXE_EMPOWERED, ESSENCE_PICKAXE_SUPREME, ESSENCE_PICKAXE_DIVINE);
        add("Essence-Infused Shear", ESSENCE_SHEAR, ESSENCE_SHEAR_EMPOWERED, ESSENCE_SHEAR_SUPREME, ESSENCE_SHEAR_DIVINE);
        add("Essence-Infused Shovel", ESSENCE_SHOVEL, ESSENCE_SHOVEL_EMPOWERED, ESSENCE_SHOVEL_SUPREME, ESSENCE_SHOVEL_DIVINE);
        add("Essence-Infused Sword", ESSENCE_SWORD, ESSENCE_SWORD_EMPOWERED, ESSENCE_SWORD_SUPREME, ESSENCE_SWORD_DIVINE);
    }

    private void addKnowledge() {
        add("knowledge.essence.brewed", "Brewed");
        add("knowledge.essence.keen", "Keen");
        add("knowledge.essence.enchanted", "Enchanted");
        add("knowledge.essence.armor", "Armor");
        add("knowledge.essence.armor_toughness", "Armor-Toughness");
        add("knowledge.essence.attack_damage", "Attack-Damage");
        add("knowledge.essence.max_health", "Max-Health");
        add("knowledge.essence.movement_speed", "Movement-Speed");
        add("knowledge.essence.infinity", "Infinity");
        add("knowledge.essence.knockback", "Knockback");
        add("knowledge.essence.luck", "Luck");
        add("knowledge.essence.silk_touch", "Silk Touch");
        add("knowledge.essence.unbreaking", "Unbreaking");
        add("knowledge.essence.mending", "Mending");
        add("knowledge.essence.strengthened", "Strengthened");
        add("knowledge.essence.expander", "Expander");
        add("knowledge.essence.fiery", "Fiery");
        add("knowledge.essence.rainbow", "Rainbow");
        add("knowledge.essence.cascading", "Cascading");
        add("knowledge.essence.tool_crafting", "Tool-Crafting");
        add("knowledge.essence.arboreal_notes", "Arboreal Notes");
        add("knowledge.essence.basic_tier", "Basic-Tier");
        add("knowledge.essence.empowered_tier", "Empowered-Tier");
        add("knowledge.essence.supreme_tier", "Supreme-Tier");
        add("knowledge.essence.divine_tier", "Divine-Tier");
    }

    private void addModifiers() {
        //Irregulars
        add("modifier.essence.attribute", "Attribute [%s]");
        add("modifier.essence.brewed", "Brewed [Hold %s for Info]");
        add("modifier.essence.brewed.cleaned", "Brewed");
        add("modifier.essence.cascading", "Cascading [%s]");
        add("modifier.essence.strengthened", "Strengthened %s [%s]");
        add("modifier.essence.strengthened.cleaned", "Strengthened [%s]");

        //Regulars
        add(EFFICIENCY_MODIFIER.get(), "Efficiency");
        add(ENCHANTED_MODIFIER.get(), "Enchanted");
        add(EXPANDER_MODIFIER.get(), "Expander");
        add(FIERY_MODIFIER.get(), "Fiery");
        add(KEEN_MODIFIER.get(), "Keen");
        add(KNOCKBACK_MODIFIER.get(), "Knockback");
        add(LUCK_MODIFIER.get(), "Luck");
        add(RAINBOW_MODIFIER.get(), "Rainbow");
        add(SILK_TOUCH_MODIFIER.get(), "Silk Touch");
        add(UNBREAKING_MODIFIER.get(), "Unbreaking");
    }

    private void addMisc() {
        add("attribute.essence.armor", "Armor");
        add("attribute.essence.armor_toughness", "Armor-Toughness");
        add("attribute.essence.attack_damage", "Attack-Damage");
        add("attribute.essence.max_health", "Max-Health");
        add("attribute.essence.movement_speed", "Movement Speed");

        add("container.essence.portable_crafter", "Portable-Crafter");
        add("curios.identifier.backpack", "Backpack");
        add("fluid.essence.essence", "Essence");

        add("itemGroup.resources", "Titanium Resources");
        add("itemGroup.essence_core", "Essence Core");
        add("itemGroup.essence_tools", "Essence Tools");

        add("sound.essence.subtitle.crafting.infusion", "Magically Infusing");
        add("sound.essence.subtitle.ambient.infusion_book", "Pages Turning");

        add("tier.essence.basic", "Basic");
        add("tier.essence.empowered", "Empowered");
        add("tier.essence.supreme", "Supreme");
        add("tier.essence.divine", "Divine");

        add("patchouli.book.essence.knowledge.tome.name", "Tome of Knowledge");
        add("patchouli.book.essence.knowledge.tome.landingtext", "Welcome to the Libria-Essentium as documented by former Explorer 'C.S. Ironbeak'");
    }

    private void addTooltip() {
        add("tooltip.essence.modifier", "Modifiers:");
        add("tooltip.essence.modifier.free", "Free Modifiers: %s");
        add("tooltip.essence.tool.tier", "Material Tier: ");
        add("tooltip.essence.generic.shiftForInformation", "");

        add("tooltip.essence.tome_of_experience.holding", "Currently Holding");
        add("tooltip.essence.tome_of_experience.levels", "%s Levels");
        add("tooltip.essence.tome_of_experience.amount", "Stored Experience: %s/%s");
    }
}
