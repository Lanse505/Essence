package com.teamacronymcoders.essence.compat.registrate;

import com.hrznstudio.titanium.nbthandler.NBTManager;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.common.block.EssenceBlock;
import com.teamacronymcoders.essence.common.block.EssenceBrickBlock;
import com.teamacronymcoders.essence.common.block.EssenceCrystalOreBlock;
import com.teamacronymcoders.essence.common.block.infusion.InfusionPedestalBlock;
import com.teamacronymcoders.essence.common.block.infusion.InfusionTableBlock;
import com.teamacronymcoders.essence.common.block.infusion.tile.InfusionPedestalBlockEntity;
import com.teamacronymcoders.essence.common.block.infusion.tile.InfusionTableBlockEntity;
import com.teamacronymcoders.essence.common.block.wood.EssencePlankBlock;
import com.teamacronymcoders.essence.common.block.wood.EssenceSaplingBlock;
import com.teamacronymcoders.essence.common.block.wood.EssenceSlabBlock;
import com.teamacronymcoders.essence.client.render.tesr.InfusionPedestalTESR;
import com.teamacronymcoders.essence.client.render.tesr.InfusionTableTESR;
import com.teamacronymcoders.essence.common.item.essence.EssenceCrystalItem;
import com.teamacronymcoders.essence.common.item.essence.EssenceIngotItem;
import com.teamacronymcoders.essence.common.item.essence.EssenceNuggetItem;
import com.teamacronymcoders.essence.common.util.EssenceTags;
import com.teamacronymcoders.essence.common.util.helper.EssenceUtilHelper;
import com.teamacronymcoders.essence.common.util.tier.EssenceItemTiers;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EssenceBlockRegistrate {

    public static void init() {
    }

    // Tiered Blocks
    public static BlockEntry<EssenceBlock> ESSENCE_INFUSED_METAL_BLOCK = essenceBlock(EssenceItemTiers.BASIC);
    public static BlockEntry<EssenceBlock> ESSENCE_INFUSED_METAL_EMPOWERED_BLOCK = essenceBlock(EssenceItemTiers.EMPOWERED);
    public static BlockEntry<EssenceBlock> ESSENCE_INFUSED_METAL_SUPREME_BLOCK = essenceBlock(EssenceItemTiers.SUPREME);
    public static BlockEntry<EssenceBlock> ESSENCE_INFUSED_METAL_DIVINE_BLOCK = essenceBlock(EssenceItemTiers.DIVINE);

    public static BlockEntry<EssenceBlock> ESSENCE_INFUSED_CRYSTAL_BLOCK = essenceCrystalBlock(EssenceItemTiers.BASIC);
    public static BlockEntry<EssenceBlock> ESSENCE_INFUSED_CRYSTAL_EMPOWERED_BLOCK = essenceCrystalBlock(EssenceItemTiers.EMPOWERED);
    public static BlockEntry<EssenceBlock> ESSENCE_INFUSED_CRYSTAL_SUPREME_BLOCK = essenceCrystalBlock(EssenceItemTiers.SUPREME);
    public static BlockEntry<EssenceBlock> ESSENCE_INFUSED_CRYSTAL_DIVINE_BLOCK = essenceCrystalBlock(EssenceItemTiers.DIVINE);

    // Bricks
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_WHITE = essenceBrickBlock(DyeColor.WHITE);
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_ORANGE = essenceBrickBlock(DyeColor.ORANGE);
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_MAGENTA = essenceBrickBlock(DyeColor.MAGENTA);
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_LIGHT_BLUE = essenceBrickBlock(DyeColor.LIGHT_BLUE);
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_YELLOW = essenceBrickBlock(DyeColor.YELLOW);
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_LIME = essenceBrickBlock(DyeColor.LIME);
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_PINK = essenceBrickBlock(DyeColor.PINK);
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_GRAY = essenceBrickBlock(DyeColor.GRAY);
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_LIGHT_GRAY = essenceBrickBlock(DyeColor.LIGHT_GRAY);
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_CYAN = essenceBrickBlock(DyeColor.CYAN);
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_PURPLE = essenceBrickBlock(DyeColor.PURPLE);
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_BLUE = essenceBrickBlock(DyeColor.BLUE);
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_BROWN = essenceBrickBlock(DyeColor.BROWN);
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_GREEN = essenceBrickBlock(DyeColor.GREEN);
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_RED = essenceBrickBlock(DyeColor.RED);
    public static BlockEntry<EssenceBrickBlock> ESSENCE_BRICKS_BLACK = essenceBrickBlock(DyeColor.BLACK);

    // MISC
    public static BlockEntry<EssenceCrystalOreBlock> ESSENCE_CRYSTAL_ORE = Essence.ESSENCE_REGISTRATE.object("essence_crystal_ore")
            .block(Material.STONE, EssenceCrystalOreBlock::new)
            .properties(properties -> properties.strength(3.0F, 3.0F).sound(SoundType.STONE))
            .lang("Essence-Infused Crystal Ore")
            .tag(EssenceTags.EssenceBlockTags.ESSENCE_CRYSTAL_ORE)
            .blockstate((context, provider) -> provider.simpleBlock(context.get()))
            .loot(getCrystalLootTable())
            .item()
            .tab(() -> Essence.CORE_TAB)
            .model((context, provider) -> provider.blockItem(context)).build()
            .register();

    public static BlockEntry<EssenceCrystalOreBlock> ESSENCE_CRYSTAL_ORE_DEEP_SLATE = Essence.ESSENCE_REGISTRATE.object("essence_crystal_ore_deepslate")
            .block(Material.STONE, EssenceCrystalOreBlock::new)
            .properties(properties -> properties.strength(3.0F, 3.0F).sound(SoundType.STONE))
            .lang("Deepslate Essence-Infused Crystal Ore")
            .tag(EssenceTags.EssenceBlockTags.ESSENCE_CRYSTAL_ORE)
            .blockstate((context, provider) -> provider.simpleBlock(context.get()))
            .loot(getCrystalLootTable())
            .item()
            .tab(() -> Essence.CORE_TAB)
            .model((context, provider) -> provider.blockItem(context)).build()
            .register();

    public static BlockEntry<Block> CrystalBlock = Essence.ESSENCE_REGISTRATE.object("raw_essence_block")
            .block(Material.STONE, Block::new)
            .initialProperties(Material.STONE, MaterialColor.COLOR_CYAN)
            .properties(properties -> properties.requiresCorrectToolForDrops().strength(5.0F, 6.0F))
            .blockstate((context, provider) -> provider.simpleBlock(context.get()))
            .loot(BlockLoot::dropSelf)
            .lang("Raw Essence Block")
            .item()
            .tab(() -> Essence.CORE_TAB)
            .model((context, provider) -> provider.blockItem(context)).build()
            .register();

    public static BlockEntry<OreBlock> ESSENCE_ORE = Essence.ESSENCE_REGISTRATE.object("essence_ore")
            .block(Material.STONE, OreBlock::new)
            .properties(properties -> properties.strength(3.0F, 3.0F).sound(SoundType.STONE))
            .lang("Essence-Infused Ore")
            .tag(EssenceTags.EssenceBlockTags.ESSENCE_ORE)
            .blockstate((context, provider) -> provider.simpleBlock(context.get()))
            .loot(getOreLootTable())
            .item()
            .tab(() -> Essence.CORE_TAB)
            .model((context, provider) -> provider.blockItem(context)).build()
            .register();

    public static BlockEntry<OreBlock> ESSENCE_ORE_DEEP_SLATE = Essence.ESSENCE_REGISTRATE.object("essence_ore_deepslate")
            .block(Material.STONE, OreBlock::new)
            .properties(properties -> properties.strength(3.0F, 3.0F).sound(SoundType.STONE))
            .lang("Deepslate Essence-Infused Ore")
            .tag(EssenceTags.EssenceBlockTags.ESSENCE_ORE)
            .blockstate((context, provider) -> provider.simpleBlock(context.get()))
            .loot(getOreLootTable())
            .item()
            .tab(() -> Essence.CORE_TAB)
            .model((context, provider) -> provider.blockItem(context)).build()
            .register();

    public static BlockEntry<Block> RAW_ESSENCE_BLOCK = Essence.ESSENCE_REGISTRATE.object("raw_essence_block")
            .block(Material.STONE, Block::new)
            .initialProperties(Material.STONE, MaterialColor.COLOR_CYAN)
            .properties(properties -> properties.requiresCorrectToolForDrops().strength(5.0F, 6.0F))
            .blockstate((context, provider) -> provider.simpleBlock(context.get()))
            .loot(BlockLoot::dropSelf)
            .lang("Raw Essence Block")
            .item()
            .tab(() -> Essence.CORE_TAB)
            .model((context, provider) -> provider.blockItem(context)).build()
            .register();

    public static BlockEntry<Block> ANCIENT_ENDERITE = Essence.ESSENCE_REGISTRATE.object("ancient_enderite")
            .block(Material.METAL, Block::new).initialProperties(Material.METAL, MaterialColor.COLOR_BLACK).properties(properties -> properties.requiresCorrectToolForDrops().strength(50.0F, 1200.0F).sound(SoundType.ANCIENT_DEBRIS))
            .blockstate((context, provider) -> provider.models().cubeColumn("ancient_enderite", new ResourceLocation(Essence.MOD_ID, "block/ancient_enderite_side"), new ResourceLocation(Essence.MOD_ID, "block/ancient_enderite_top")))
            .loot(BlockLoot::dropSelf)
            .lang("Ancient Enderite")
            .item()
            .tab(() -> Essence.CORE_TAB)
            .model((context, provider) -> provider.blockItem(context)).build()
            .register();

    // Wood Blocks
    public static BlockEntry<LeavesBlock> ESSENCE_WOOD_LEAVES = Essence.ESSENCE_REGISTRATE.object("essence_wood_leaves")
            .block(LeavesBlock::new).initialProperties(Material.WOOD, MaterialColor.COLOR_CYAN).properties(properties -> properties.strength(0.2F).randomTicks().sound(SoundType.GRASS).noCollission())
            .lang("Essence-Wood Leaves").tag(BlockTags.LEAVES)
            .blockstate((context, provider) -> provider.simpleBlock(context.get()))
            .loot(getEssenceLeavesLootTable())
            .item().tab(() -> Essence.CORE_TAB)
            .model((context, provider) -> provider.blockItem(context)).build()
            .register();
    public static BlockEntry<RotatedPillarBlock> ESSENCE_WOOD_LOG = Essence.ESSENCE_REGISTRATE.object("essence_wood_log")
            .block(RotatedPillarBlock::new)
            .initialProperties(Material.WOOD, MaterialColor.COLOR_CYAN)
            .properties(properties -> properties.strength(2.0F).sound(SoundType.WOOD))
            .lang("Essence-Wood Log")
            .tag(EssenceTags.EssenceBlockTags.ESSENCE_WOOD_LOG, BlockTags.LOGS, BlockTags.LOGS_THAT_BURN)
            .loot(BlockLoot::dropSelf)
            .addLayer(() -> RenderType::cutout)
            .blockstate((context, provider) -> provider.axisBlock(context.get()))
            .item()
            .tab(() -> Essence.CORE_TAB)
            .tag(EssenceTags.EssenceItemTags.ESSENCE_WOOD_LOG)
            .model((context, provider) -> provider.blockItem(context))
            .build()
            .register();
    public static BlockEntry<RotatedPillarBlock> STRIPPED_ESSENCE_WOOD_LOG = Essence.ESSENCE_REGISTRATE.object("stripped_essence_wood_log")
            .block(RotatedPillarBlock::new).initialProperties(Material.WOOD, MaterialColor.COLOR_CYAN)
            .properties(properties -> properties.strength(2.0F).sound(SoundType.WOOD))
            .lang("Stripped Essence-Wood Log")
            .tag(EssenceTags.EssenceBlockTags.ESSENCE_WOOD_LOG, BlockTags.LOGS, BlockTags.LOGS_THAT_BURN)
            .loot(BlockLoot::dropSelf)
            .addLayer(() -> RenderType::cutout)
            .blockstate((context, provider) -> provider.axisBlock(context.get()))
            .item()
            .tab(() -> Essence.CORE_TAB)
            .tag(EssenceTags.EssenceItemTags.ESSENCE_WOOD_LOG)
            .model((context, provider) -> provider.blockItem(context))
            .build()
            .register();
    public static BlockEntry<EssenceSaplingBlock> ESSENCE_WOOD_SAPLING = Essence.ESSENCE_REGISTRATE.object("essence_wood_sapling")
            .block(EssenceSaplingBlock::new)
            .initialProperties(Material.WOOD, MaterialColor.COLOR_CYAN)
            .properties(properties -> properties.noCollission().randomTicks().strength(0F).sound(SoundType.GRASS))
            .lang("Essence-Wood Sapling")
            .loot(BlockLoot::dropSelf)
            .tag(BlockTags.SAPLINGS)
            .addLayer(() -> RenderType::cutout)
            .blockstate((context, provider) -> provider.simpleBlock(context.get(), provider.models().cross(context.getId().getPath(), new ResourceLocation("essence:item/essence_wood_sapling"))))
            .item()
            .tab(() -> Essence.CORE_TAB)
            .model((context, provider) -> provider.blockItem(context))
            .build()
            .register();
    public static BlockEntry<EssencePlankBlock> ESSENCE_WOOD_PLANKS = Essence.ESSENCE_REGISTRATE.object("essence_wood_plank")
            .block(EssencePlankBlock::new)
            .initialProperties(Material.WOOD, MaterialColor.COLOR_CYAN)
            .properties(properties -> properties.strength(2.0F, 3.0F).sound(SoundType.WOOD))
            .lang("Essence-Wood Planks")
            .loot(BlockLoot::dropSelf)
            .tag(BlockTags.PLANKS)
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapeless(context.get(), 4).requires(EssenceTags.EssenceItemTags.ESSENCE_WOOD_LOG).unlockedBy("hasLog", DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_WOOD_LOG).getCritereon(provider)).save(provider))
            .blockstate((context, provider) -> provider.simpleBlock(context.get()))
            .item()
            .tab(() -> Essence.CORE_TAB)
            .tag(EssenceTags.EssenceItemTags.ESSENCE_WOOD_PLANKS)
            .model((context, provider) -> provider.blockItem(context))
            .build()
            .register();
    public static BlockEntry<EssenceSlabBlock> ESSENCE_WOOD_SLAB = Essence.ESSENCE_REGISTRATE.object("essence_wood_slab")
            .block(EssenceSlabBlock::new)
            .initialProperties(Material.WOOD, MaterialColor.COLOR_CYAN)
            .properties(properties -> properties.strength(2.0F, 3.0F).sound(SoundType.WOOD))
            .lang("Essence-Wood Slab")
            .tag(BlockTags.SLABS, BlockTags.WOODEN_SLABS)
            .blockstate((context, provider) -> provider.slabBlock(context.get(), new ResourceLocation(Essence.MOD_ID, "block/essence_wood_plank"), new ResourceLocation(Essence.MOD_ID, "block/essence_wood_plank")))
            .loot(getEssenceWoodSlabLootTable())
            .recipe((context, provider) -> TitaniumShapedRecipeBuilder.shapedRecipe(context.get(), 4).setName(new ResourceLocation(Essence.MOD_ID, "essence_wood_slab_mid")).pattern("   ").pattern("ppp").pattern("   ").define('p', EssenceTags.EssenceItemTags.ESSENCE_WOOD_PLANKS).unlockedBy("hasPlanks", DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_WOOD_PLANKS).getCritereon(provider)).save(provider))
            .item()
            .tab(() -> Essence.CORE_TAB)
            .model((context, provider) -> provider.blockItem(context))
            .build()
            .register();

    // Infusion Blocks
    public static BlockEntry<InfusionTableBlock> INFUSION_TABLE = Essence.ESSENCE_REGISTRATE.object("essence_infusion_table")
            .block(Material.STONE, InfusionTableBlock::new)
            .properties(properties -> properties.sound(SoundType.STONE).strength(3.5F).noCollission().dynamicShape())
            .lang("Infusion Table")
            .loot(BlockLoot::dropSelf)
            .addLayer(() -> RenderType::translucent)
            .blockstate((context, provider) -> provider.models().getExistingFile(new ResourceLocation(Essence.MOD_ID, "essence_infusion_table")))
            .item((table, properties) -> {
                table.setItem(RegistryObject.of(new ResourceLocation(Essence.MOD_ID, "essence_infusion_table"), ForgeRegistries.ITEMS));
                return new BlockItem(table, properties.tab(Essence.CORE_TAB));
            })
            .model((context, provider) -> provider.blockItem(context))
            .build()
            .register();
    public static BlockEntityEntry<InfusionTableBlockEntity> INFUSION_TABLE_TILE = Essence.ESSENCE_REGISTRATE.<InfusionTableBlockEntity>blockEntity("essence_infusion_table", (type, pos, state) -> new InfusionTableBlockEntity(pos, state))
            .onRegister(tile -> NBTManager.getInstance().scanTileClassForAnnotations(InfusionTableBlockEntity.class))
            .renderer(() -> InfusionTableTESR::new)
            .validBlock(INFUSION_TABLE)
            .register();

    public static BlockEntry<InfusionPedestalBlock> INFUSION_PEDESTAL = Essence.ESSENCE_REGISTRATE.object("essence_infusion_pedestal")
            .block(Material.STONE, InfusionPedestalBlock::new)
            .properties(properties -> properties.strength(3).sound(SoundType.STONE).noCollission())
            .lang("Infusion Pedestal")
            .loot(BlockLoot::dropSelf)
            .addLayer(() -> RenderType::translucent)
            .blockstate((context, provider) -> provider.models().getExistingFile(new ResourceLocation(Essence.MOD_ID, "essence_infusion_pedestal")))
            .item((pedestal, properties) -> {
                pedestal.setItem(RegistryObject.of(new ResourceLocation(Essence.MOD_ID, "essence_infusion_pedestal"), ForgeRegistries.ITEMS));
                return new BlockItem(pedestal, properties.tab(Essence.CORE_TAB));
            })
            .model((context, provider) -> provider.blockItem(context))
            .build()
            .register();
    public static BlockEntityEntry<InfusionPedestalBlockEntity> INFUSION_PEDESTAL_TILE = Essence.ESSENCE_REGISTRATE.<InfusionPedestalBlockEntity>blockEntity("essence_infusion_pedestal", (type, pos, state) -> new InfusionPedestalBlockEntity(pos, state))
            .onRegister(tile -> NBTManager.getInstance().scanTileClassForAnnotations(InfusionPedestalBlockEntity.class))
            .renderer(() -> InfusionPedestalTESR::new)
            .validBlock(INFUSION_PEDESTAL)
            .register();

    // Creation Methods
    public static BlockEntry<EssenceBlock> essenceBlock(EssenceItemTiers tier) {
        String name = "_essence_infused_block";
        String tierType = tier == EssenceItemTiers.BASIC ? "" : tier.toString().toLowerCase();
        String entryName = tierType.equals("") ? name.substring(1) : tierType + name;
        return Essence.ESSENCE_REGISTRATE.object(entryName)
                .block(Material.METAL, properties -> new EssenceBlock(properties, tier))
                .properties(properties -> properties.sound(SoundType.METAL).speedFactor(1.25f))
                .loot(BlockLoot::dropSelf)
                .lang("Essence-Infused Block")
                .blockstate((context, provider) -> provider.simpleBlock(context.get(), provider.models().cubeAll(context.getId().getPath(), tier != EssenceItemTiers.BASIC ? new ResourceLocation(Essence.MOD_ID, ModelProvider.BLOCK_FOLDER + "/" + tierType + "_essence_infused_block") : new ResourceLocation(Essence.MOD_ID, ModelProvider.BLOCK_FOLDER + "/" + "essence_infused_block"))))
                .recipe((context, provider) -> {
                    EssenceNuggetItem nugget = tier.getNugget().get().getKey().get();
                    EssenceIngotItem ingot = tier.getIngot().get().getKey().get();
                    EssenceBlock block = context.get();
                    DataIngredient nuggetIngredient = DataIngredient.tag(tier.getNugget().get().getValue());
                    DataIngredient ingotIngredient = DataIngredient.tag(tier.getIngot().get().getValue());
                    DataIngredient blockIngredient = DataIngredient.tag(tier.getBlock().get().getValue());
                    ShapedRecipeBuilder.shaped(ingot)
                            .pattern("nnn").pattern("nnn").pattern("nnn")
                            .define('n', nuggetIngredient)
                            .unlockedBy("has_" + provider.safeName(nuggetIngredient), nuggetIngredient.getCritereon(provider))
                            .save(provider, new ResourceLocation(Essence.MOD_ID, entryName + "_nugget_to_ingot"));
                    ShapelessRecipeBuilder.shapeless(nugget, 9)
                            .requires(ingotIngredient)
                            .unlockedBy("has_" + provider.safeName(ingotIngredient), ingotIngredient.getCritereon(provider))
                            .save(provider, new ResourceLocation(Essence.MOD_ID, entryName + "_ingot_to_nuggets"));
                    ShapedRecipeBuilder.shaped(block)
                            .pattern("iii").pattern("iii").pattern("iii")
                            .define('i', ingotIngredient)
                            .unlockedBy("has_" + provider.safeName(ingotIngredient), ingotIngredient.getCritereon(provider))
                            .save(provider, new ResourceLocation(Essence.MOD_ID, entryName + "_ingot_to_block"));
                    ShapelessRecipeBuilder.shapeless(ingot, 9)
                            .requires(blockIngredient)
                            .unlockedBy("has_" + provider.safeName(blockIngredient), blockIngredient.getCritereon(provider))
                            .save(provider, new ResourceLocation(Essence.MOD_ID, entryName + "_block_to_ingots"));
                })
                .item((essenceBlock, properties) -> essenceBlock.getBlockItem(properties.tab(Essence.CORE_TAB)).create())
                .model((context, provider) -> provider.blockItem(context)).tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_BLOCK)
                .build()
                .register();
    }

    public static BlockEntry<EssenceBlock> essenceCrystalBlock(EssenceItemTiers tier) {
        String name = "_essence_infused_crystal_block";
        String tierType = tier == EssenceItemTiers.BASIC ? "" : tier.toString().toLowerCase();
        String entryName = tierType.equals("") ? name.substring(1) : tierType + name;
        return Essence.ESSENCE_REGISTRATE.object(entryName)
                .block(Material.METAL, properties -> new EssenceBlock(properties, tier))
                .properties(properties -> properties.sound(SoundType.METAL).speedFactor(1.25f))
                .loot(BlockLoot::dropSelf)
                .lang("Essence-Infused Crystal Block")
                .blockstate((context, provider) -> provider.simpleBlock(context.get(), provider.models().cubeAll(context.getId().getPath(), tier != EssenceItemTiers.BASIC ? new ResourceLocation(Essence.MOD_ID, ModelProvider.BLOCK_FOLDER + "/" + tierType +"_essence_infused_crystal_block") : new ResourceLocation(Essence.MOD_ID, ModelProvider.BLOCK_FOLDER + "/" + "essence_infused_crystal_block"))))
                .recipe((context, provider) -> {
                    EssenceCrystalItem crystal = tier.getCrystal().get().getKey().get();
                    EssenceBlock crystalBlock = tier.getCrystalBlock().get().getKey().get();
                    DataIngredient crystalIngredient = DataIngredient.tag(tier.getCrystal().get().getValue());
                    DataIngredient blockIngredient = DataIngredient.tag(tier.getCrystalBlock().get().getValue());
                    ShapedRecipeBuilder.shaped(crystalBlock)
                            .pattern("CCC").pattern("CCC").pattern("CCC")
                            .define('C', crystalIngredient)
                            .unlockedBy("has_" + provider.safeName(crystalIngredient), crystalIngredient.getCritereon(provider))
                            .save(provider, new ResourceLocation(Essence.MOD_ID, entryName + "_crystal_to_block"));
                    ShapelessRecipeBuilder.shapeless(crystal, 9)
                            .requires(blockIngredient)
                            .unlockedBy("has_" + provider.safeName(blockIngredient), blockIngredient.getCritereon(provider))
                            .save(provider, new ResourceLocation(Essence.MOD_ID, entryName + "_block_to_crystal"));
                })
                .item((essenceBlock, properties) -> essenceBlock.getBlockItem(properties.tab(Essence.CORE_TAB)).create())
                .model((context, provider) -> provider.blockItem(context))
                .tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_BLOCK)
                .build()
                .register();
    }

    private static boolean baseRecipe = false;
    private static boolean essenceToBrickRecipe = false;

    public static BlockEntry<EssenceBrickBlock> essenceBrickBlock(DyeColor color) {
        return Essence.ESSENCE_REGISTRATE.object("essence_bricks_" + color.getName())
                .block(Material.METAL, properties -> new EssenceBrickBlock(properties, color)).properties(properties -> properties.sound(SoundType.STONE).strength(1.5F, 1200F))
                .loot(BlockLoot::dropSelf).lang(EssenceUtilHelper.getColorFormattedLangString("Essence-Infused Bricks", color)).tag(BlockTags.STONE_BRICKS, BlockTags.WITHER_IMMUNE, EssenceTags.EssenceBlockTags.ESSENCE_BRICKS)
                .blockstate((context, provider) -> {
                    provider.simpleBlock(context.get());
                })
                // Item Portion
                .item().tab(() -> Essence.CORE_TAB)
                .model((context, provider) -> {
                    provider.blockItem(context);
                }).tag(EssenceTags.EssenceItemTags.ESSENCE_BRICKS)
                .recipe((context, provider) -> {
                    EssenceBrickBlock brickBlock = EssenceBrickBlock.dyeToColorMap.get(color).get();
                    // Recolor
                    DataIngredient brickIngredient = DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_BRICKS);
                    DataIngredient nuggetIngredient = DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_NUGGET);
                    new ShapedRecipeBuilder(brickBlock, 8)
                            .pattern("bbb").pattern("bdb").pattern("bbb")
                            .define('b', brickIngredient).define('d', color.getTag())
                            .unlockedBy("has_" + provider.safeName(brickIngredient), brickIngredient.getCritereon(provider))
                            .save(provider, new ResourceLocation(brickBlock.getRegistryName().getNamespace(), brickBlock.getRegistryName().getPath() + "_recolor"));
                    // Base Recipe
                    if (!baseRecipe) {
                        new ShapedRecipeBuilder(ESSENCE_BRICKS_CYAN.get(), 8)
                                .pattern("bbb").pattern("bnb").pattern("bbb")
                                .define('b', ItemTags.STONE_BRICKS).define('n', nuggetIngredient)
                                .unlockedBy("has_" + provider.safeName(nuggetIngredient), nuggetIngredient.getCritereon(provider))
                                .save(provider, new ResourceLocation(Essence.MOD_ID, "essence_bricks"));
                        baseRecipe = true;
                    }
                    if (!essenceToBrickRecipe) {
                        ShapelessRecipeBuilder.shapeless(Items.STONE_BRICKS).requires(EssenceTags.EssenceItemTags.ESSENCE_BRICKS).unlockedBy("has_" + provider.safeName(brickIngredient), brickIngredient.getCritereon(provider)).save(provider, new ResourceLocation(Essence.MOD_ID, "essence_brick_to_stone_brick"));
                        essenceToBrickRecipe = true;
                    }
                }).build()
                .register();
    }

    public static NonNullBiConsumer<RegistrateBlockLootTables, OreBlock> getOreLootTable() {
        return  (blockLootTables, essenceOreBlock) -> blockLootTables.add(essenceOreBlock, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .setBonusRolls(ConstantValue.exactly(0))
                        .add(AlternativesEntry.alternatives(
                                LootItem.lootTableItem(essenceOreBlock)
                                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item()
                                                .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))
                                        )),
                                LootItem.lootTableItem(EssenceItemRegistrate.RAW_ESSENCE.get())
                                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                                        .apply(ApplyExplosionDecay.explosionDecay())
                        ))
                )
        );
    }

    public static NonNullBiConsumer<RegistrateBlockLootTables, EssenceCrystalOreBlock> getCrystalLootTable() {
       return  (blockLootTables, essenceCrystalOreBlock) -> blockLootTables.add(essenceCrystalOreBlock, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(AlternativesEntry.alternatives(
                                LootItem.lootTableItem(essenceCrystalOreBlock)
                                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item()
                                                .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))
                                        )),
                                LootItem.lootTableItem(EssenceItemRegistrate.ESSENCE_CRYSTAL.get())
                                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                                        .apply(ApplyExplosionDecay.explosionDecay())
                        ))
                )
        );
    }

    public static NonNullBiConsumer<RegistrateBlockLootTables, LeavesBlock> getEssenceLeavesLootTable() {
        return (registrateBlockLootTables, customLeavesBlock) -> registrateBlockLootTables.add(customLeavesBlock, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(AlternativesEntry.alternatives(
                                LootItem.lootTableItem(customLeavesBlock)
                                        .when(
                                                MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS))
                                                        .or(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))))
                                                        )),
                                LootItem.lootTableItem(EssenceBlockRegistrate.ESSENCE_WOOD_SAPLING.get().asItem())
                                        .when(ExplosionCondition.survivesExplosion())
                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.0625F, 0.083333336F, 0.1F))
                        ))
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.STICK)
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
                                .apply(ApplyExplosionDecay.explosionDecay())
                        )
                        .when(
                                InvertedLootItemCondition.invert(
                                        AlternativeLootItemCondition.alternative(
                                                MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)),
                                                MatchTool.toolMatches(ItemPredicate.Builder.item()
                                                        .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static NonNullBiConsumer<RegistrateBlockLootTables, EssenceSlabBlock> getEssenceWoodSlabLootTable() {
        return (registrateBlockLootTables, essenceSlabBlock) -> registrateBlockLootTables.add(essenceSlabBlock, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ESSENCE_WOOD_LOG.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(essenceSlabBlock)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))
                                        )
                                )
                                .apply(ApplyExplosionDecay.explosionDecay())
                        )
                        .when(ExplosionCondition.survivesExplosion())
                )
        );
    }


}
