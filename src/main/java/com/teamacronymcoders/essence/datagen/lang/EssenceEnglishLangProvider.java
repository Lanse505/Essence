package com.teamacronymcoders.essence.datagen.lang;

import com.teamacronymcoders.essence.api.misc.IColorProvider;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.block.EssenceBrickBlock;
import com.teamacronymcoders.essence.datagen.advancement.CoreAdvancementProvider;
import com.teamacronymcoders.essence.datagen.advancement.KnowledgeAdvancementProvider;
import com.teamacronymcoders.essence.datagen.advancement.modifier.*;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.registration.EssenceModifierRegistration;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.teamacronymcoders.essence.util.EssenceObjectHolders.*;

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
    }

    private void addAdvancements() {
        add(CoreAdvancementProvider.getCore(), "Essence", "Essence Advancements");

        add(KnowledgeAdvancementProvider.getKnowledgeRoot(), "Essence-tial Knowledge", "Knowledge Advancements");
        add(KnowledgeAdvancementProvider.getKnowledgeModifierRoot(), "Tool-Knowledge", "Knowledge about Essence-Infused Tools");
        add(KnowledgeAdvancementProvider.getKnowledgeToolRoot(), "Modifier-Knowledge", "Knowledge relating to Modifiers");

        add(ArrowKnowledgeAdvancements.getArrow(), "Bow-Specific Modifiers", "");
        add(ArrowKnowledgeAdvancements.getBrewed(), "Brewed", "");
        add(ArrowKnowledgeAdvancements.getKeen(), "Keen", "");

        add(AttributeKnowledgeAdvancements.getAttribute(), "Attribute-Modifiers", "");
        add(AttributeKnowledgeAdvancements.getArmor(), "Armor", "");
        add(AttributeKnowledgeAdvancements.getArmorToughness(), "Armor-Toughness", "");
        add(AttributeKnowledgeAdvancements.getAttackDamage(), "Attack-Damage", "");
        add(AttributeKnowledgeAdvancements.getMaxHealth(), "Max-Health", "");
        add(AttributeKnowledgeAdvancements.getMovementSpeed(), "Movement-Speed", "");

        add(CosmeticKnowledgeAdvancements.getCosmetic(), "Cosmetic-Modifier", "");
        add(CosmeticKnowledgeAdvancements.getEnchanted(), "Enchanted", "");

        add(EnchantmentKnowledgeAdvancementProvider.getEnchantment(), "Enchantment-Granting Modifiers", "");
        add(EnchantmentKnowledgeAdvancementProvider.getEfficiency(), "Efficiency", "");
        add(EnchantmentKnowledgeAdvancementProvider.getInfinity(), "Infinity", "");
        add(EnchantmentKnowledgeAdvancementProvider.getKnockback(), "Knockback", "");
        add(EnchantmentKnowledgeAdvancementProvider.getLuck(), "Luck", "");
        add(EnchantmentKnowledgeAdvancementProvider.getMending(), "Mending", "");
        add(EnchantmentKnowledgeAdvancementProvider.getSilkTouch(), "Silk-Touch", "");
        add(EnchantmentKnowledgeAdvancementProvider.getStrengthened(), "Strengthened", "");
        add(EnchantmentKnowledgeAdvancementProvider.getUnbreaking(), "Unbreaking", "");

        add(InteractionKnowledgeAdvancements.getInteraction(), "Interaction-Changing Modifiers", "");
        add(InteractionKnowledgeAdvancements.getCascading(), "Cascading", "");
        add(InteractionKnowledgeAdvancements.getExpander(), "Expander", "");
        add(InteractionKnowledgeAdvancements.getFiery(), "Fiery", "");
        add(InteractionKnowledgeAdvancements.getRainbow(), "Rainbow", "");
    }

    private void addBlocks() {
        add("Essence-Infused Metal Block", ESSENCE_INFUSED_METAL_BLOCK, ESSENCE_INFUSED_METAL_EMPOWERED_BLOCK, ESSENCE_INFUSED_METAL_SUPREME_BLOCK, ESSENCE_INFUSED_METAL_GODLY_BLOCK);

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
        add(ESSENCE_INFUSED_STICK, "Essence-Wood Sticks");
        add(PORTABLE_CRAFTER, "Portable Crafter");
        add(WRENCH, "Essence-Infused Wrench");
        add(ESSENCE_INFUSED_CRYSTAL, "Essence-Infused Crystal");
        add(TOME_OF_KNOWLEDGE, "Tome of Bound-Knowledge");
        add(GLUE_BALL, "Ball of Glue");
        add("Essence-Infused Nugget", ESSENCE_INFUSED_METAL_NUGGET, ESSENCE_INFUSED_METAL_EMPOWERED_NUGGET, ESSENCE_INFUSED_METAL_SUPREME_NUGGET, ESSENCE_INFUSED_METAL_GODLY_NUGGET);
        add("Essence-Infused Ingot", ESSENCE_INFUSED_METAL, ESSENCE_INFUSED_METAL_EMPOWERED, ESSENCE_INFUSED_METAL_SUPREME, ESSENCE_INFUSED_METAL_GODLY);
        add("Essence-Infused Axe", ESSENCE_AXE, ESSENCE_AXE_EMPOWERED, ESSENCE_AXE_SUPREME, ESSENCE_AXE_GODLY);
        add("Essence-Infused Bow", ESSENCE_BOW, ESSENCE_BOW_EMPOWERED, ESSENCE_BOW_SUPREME, ESSENCE_BOW_GODLY);
        add("Essence-Infused Hoe", ESSENCE_HOE, ESSENCE_HOE_EMPOWERED, ESSENCE_HOE_SUPREME, ESSENCE_HOE_GODLY);
        add("Essence-Infused Omni-Tool", ESSENCE_OMNITOOL, ESSENCE_OMNITOOL_EMPOWERED, ESSENCE_OMNITOOL_SUPREME, ESSENCE_OMNITOOL_GODLY);
        add("Essence-Infused Pickaxe", ESSENCE_PICKAXE, ESSENCE_PICKAXE_EMPOWERED, ESSENCE_PICKAXE_SUPREME, ESSENCE_PICKAXE_GODLY);
        add("Essence-Infused Shear", ESSENCE_SHEAR, ESSENCE_SHEAR_EMPOWERED, ESSENCE_SHEAR_SUPREME, ESSENCE_SHEAR_GODLY);
        add("Essence-Infused Shovel", ESSENCE_SHOVEL, ESSENCE_SHOVEL_EMPOWERED, ESSENCE_SHOVEL_SUPREME, ESSENCE_SHOVEL_GODLY);
        add("Essence-Infused Sword", ESSENCE_SWORD, ESSENCE_SWORD_EMPOWERED, ESSENCE_SWORD_SUPREME, ESSENCE_SWORD_GODLY);
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
        add("knowledge.essence.godly_tier", "Godly-Tier");
    }

    private void addModifiers() {
        //Irregulars
        add("modifier.essence.attribute", "Attribute [%s]");
        add("modifier.brewed.cleaned", "Brewed");
        add("modifier.essence.cascading", "Cascading [%s]");
        add("modifier.essence.strengthened", "Strengthened %s [%s]");
        add("modifier.essence.strengthened.cleaned", "Strengthened [%s]");

        //Regulars
        add(EssenceModifierRegistration.EFFICIENCY_MODIFIER.get(), "Efficiency");
        add(EssenceModifierRegistration.ENCHANTED_MODIFIER.get(), "Enchanted");
        add(EssenceModifierRegistration.EXPANDER_MODIFIER.get(), "Expander");
        add(EssenceModifierRegistration.FIERY_MODIFIER.get(), "Fiery");
        add(EssenceModifierRegistration.KEEN_MODIFIER.get(), "Keen");
        add(EssenceModifierRegistration.KNOCKBACK_MODIFIER.get(), "Knockback");
        add(EssenceModifierRegistration.LUCK_MODIFIER.get(), "Luck");
        add(EssenceModifierRegistration.RAINBOW_MODIFIER.get(), "Rainbow");
        add(EssenceModifierRegistration.SILK_TOUCH_MODIFIER.get(), "Silk Touch");
        add(EssenceModifierRegistration.UNBREAKING_MODIFIER.get(), "Unbreaking");
    }

    private void addMisc() {
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
        add("tier.essence.godly", "Godly");

        add("tooltip.essence.modifier", "Modifiers:");
        add("tooltip.essence.modifier.free", "Free Modifiers: %s");
        add("tooltip.essence.tool.tier", "Material Tier: ");
        add("tooltip.titanium.facing_handler.input", "Infusable");

        add("patchouli.book.essence.knowledge.tome.name", "Tome of Knowledge");
        add("patchouli.book.essence.knowledge.tome.landingtext", "Welcome to the Libria-Essentium as documented by former Explorer 'C.S. Ironbeak'");
    }
}
