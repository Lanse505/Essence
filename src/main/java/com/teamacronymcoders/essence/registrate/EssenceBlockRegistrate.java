package com.teamacronymcoders.essence.registrate;

import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.block.EssenceBlock;
import com.teamacronymcoders.essence.block.EssenceBrickBlock;
import com.teamacronymcoders.essence.block.EssenceCrystalOreBlock;
import com.teamacronymcoders.essence.block.EssenceOreBlock;
import com.teamacronymcoders.essence.block.base.CustomLeavesBlock;
import com.teamacronymcoders.essence.block.base.CustomRotatedPillarBlock;
import com.teamacronymcoders.essence.block.infusion.InfusionPedestalBlock;
import com.teamacronymcoders.essence.block.infusion.InfusionTableBlock;
import com.teamacronymcoders.essence.block.infusion.tile.InfusionPedestalTile;
import com.teamacronymcoders.essence.block.infusion.tile.InfusionTableTile;
import com.teamacronymcoders.essence.block.wood.EssencePlankBlock;
import com.teamacronymcoders.essence.block.wood.EssenceSaplingBlock;
import com.teamacronymcoders.essence.block.wood.EssenceSlabBlock;
import com.teamacronymcoders.essence.client.render.tesr.InfusionPedestalTESR;
import com.teamacronymcoders.essence.client.render.tesr.InfusionTableTESR;
import com.teamacronymcoders.essence.item.essence.EssenceIngotItem;
import com.teamacronymcoders.essence.item.essence.EssenceNuggetItem;
import com.teamacronymcoders.essence.util.EssenceTags;
import com.teamacronymcoders.essence.util.helper.EssenceUtilHelper;
import com.teamacronymcoders.essence.util.tier.EssenceItemTiers;
import com.teamacronymcoders.essence.util.tier.EssenceToolTiers;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.TileEntityEntry;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.*;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

public class EssenceBlockRegistrate {

  public static void init() {}

  // Tiered Blocks
  public static BlockEntry<EssenceBlock> ESSENCE_INFUSED_METAL_BLOCK = essenceBlock(EssenceItemTiers.ESSENCE);
  public static BlockEntry<EssenceBlock> ESSENCE_INFUSED_METAL_EMPOWERED_BLOCK = essenceBlock(EssenceItemTiers.EMPOWERED_ESSENCE);
  public static BlockEntry<EssenceBlock> ESSENCE_INFUSED_METAL_SUPREME_BLOCK = essenceBlock(EssenceItemTiers.SUPREME_ESSENCE);
  public static BlockEntry<EssenceBlock> ESSENCE_INFUSED_METAL_DIVINE_BLOCK = essenceBlock(EssenceItemTiers.DIVINE_ESSENCE);

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
          .block(Material.ROCK, EssenceCrystalOreBlock::new).properties(properties -> properties.hardnessAndResistance(3.0F, 3.0F).sound(SoundType.STONE)).loot(BlockLootTables::registerDropSelfLootTable).lang("Essence-Infused Crystal Ore").tag(EssenceTags.EssenceBlockTags.ESSENCE_CRYSTAL_ORE)
          .loot((registrateBlockLootTables, essenceCrystalOreBlock) -> registrateBlockLootTables.registerLootTable(essenceCrystalOreBlock, LootTable.builder()
                  .addLootPool(LootPool.builder()
                          .rolls(ConstantRange.of(1))
                          .addEntry(AlternativesLootEntry.builder(
                                  ItemLootEntry.builder(essenceCrystalOreBlock)
                                          .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create()
                                                  .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))
                                          )),
                                  ItemLootEntry.builder(EssenceItemRegistrate.ESSENCE_CRYSTAL.get())
                                          .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE))
                                          .acceptFunction(ExplosionDecay.builder())
                          ))
                  )
          ))
          .blockstate((context, provider) -> {})
          .item().group(() -> Essence.CORE_TAB).model((context, provider) -> {}).build()
          .register();
  public static BlockEntry<EssenceOreBlock> ESSENCE_ORE = Essence.ESSENCE_REGISTRATE.object("essence_ore")
          .block(Material.ROCK, EssenceOreBlock::new).properties(properties -> properties.hardnessAndResistance(3.0F, 3.0F).sound(SoundType.STONE)).loot(BlockLootTables::registerDropSelfLootTable).lang("Essence-Infused Ore").tag(EssenceTags.EssenceBlockTags.ESSENCE_CRYSTAL_ORE).loot(BlockLootTables::registerDropSelfLootTable)
          .blockstate((context, provider) -> {})
          .item().group(() -> Essence.CORE_TAB).model((context, provider) -> {}).build()
          .register();

  // Wood Blocks
  public static BlockEntry<LeavesBlock> ESSENCE_WOOD_LEAVES = Essence.ESSENCE_REGISTRATE.object("essence_wood_leaves")
          .block(LeavesBlock::new).initialProperties(Material.WOOD, MaterialColor.CYAN).properties(properties -> properties.hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()).loot(BlockLootTables::registerDropSelfLootTable).lang("Essence-Wood Leaves").tag(BlockTags.LEAVES)
          .loot((registrateBlockLootTables, customLeavesBlock) -> registrateBlockLootTables.registerLootTable(customLeavesBlock, LootTable.builder()
                  .addLootPool(LootPool.builder()
                          .rolls(ConstantRange.of(1))
                          .addEntry(AlternativesLootEntry.builder(
                                  ItemLootEntry.builder(customLeavesBlock)
                                          .acceptCondition(
                                                  MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS))
                                                          .alternative(MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))
                                                          )),
                                  ItemLootEntry.builder(EssenceBlockRegistrate.ESSENCE_WOOD_SAPLING.get().asItem())
                                          .acceptCondition(SurvivesExplosion.builder())
                                          .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.05F, 0.0625F, 0.083333336F, 0.1F))
                          ))
                  )
                  .addLootPool(LootPool.builder()
                          .rolls(ConstantRange.of(1))
                          .addEntry(ItemLootEntry.builder(Items.STICK)
                                  .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))
                                  .acceptFunction(SetCount.builder(RandomValueRange.of(1.0f, 2.0f)))
                                  .acceptFunction(ExplosionDecay.builder())
                          )
                          .acceptCondition(
                                  Inverted.builder(
                                          Alternative.builder(
                                                  MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS)),
                                                  MatchTool.builder(ItemPredicate.Builder.create()
                                                          .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))
                                                  )
                                          )
                                  )
                          )
                  )
          ))
          .blockstate((context, provider) -> {})
          .item().group(() -> Essence.CORE_TAB).model((context, provider) -> {}).build()
          .register();
  public static BlockEntry<RotatedPillarBlock> ESSENCE_WOOD_LOG = Essence.ESSENCE_REGISTRATE.object("essence_wood_log")
          .block(RotatedPillarBlock::new).initialProperties(Material.WOOD, MaterialColor.CYAN).properties(properties -> properties.hardnessAndResistance(2.0F).sound(SoundType.WOOD)).loot(BlockLootTables::registerDropSelfLootTable).lang("Essence-Wood Log").tag(EssenceTags.EssenceBlockTags.ESSENCE_WOOD_LOG, BlockTags.LOGS, BlockTags.LOGS_THAT_BURN).loot(BlockLootTables::registerDropSelfLootTable).addLayer(() -> RenderType::getCutout)
          .blockstate((context, provider) -> {})
          .item().group(() -> Essence.CORE_TAB).model((context, provider) -> {}).tag(EssenceTags.EssenceItemTags.ESSENCE_WOOD_LOG).build()
          .register();
  public static BlockEntry<EssenceSaplingBlock> ESSENCE_WOOD_SAPLING = Essence.ESSENCE_REGISTRATE.object("essence_wood_sapling")
          .block(EssenceSaplingBlock::new).initialProperties(Material.WOOD, MaterialColor.CYAN).properties(properties -> properties.doesNotBlockMovement().tickRandomly().hardnessAndResistance(0F).sound(SoundType.PLANT)).loot(BlockLootTables::registerDropSelfLootTable).lang("Essence-Wood Sapling").loot(BlockLootTables::registerDropSelfLootTable).tag(BlockTags.SAPLINGS).addLayer(() -> RenderType::getCutout)
          .blockstate((context, provider) -> {})
          .item().group(() -> Essence.CORE_TAB).model((context, provider) -> {}).build()
          .register();
  public static BlockEntry<EssencePlankBlock> ESSENCE_WOOD_PLANKS = Essence.ESSENCE_REGISTRATE.object("essence_wood_plank")
          .block(EssencePlankBlock::new).initialProperties(Material.WOOD, MaterialColor.CYAN).properties(properties -> properties.hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)).loot(BlockLootTables::registerDropSelfLootTable).lang("Essence-Wood Planks").loot(BlockLootTables::registerDropSelfLootTable).tag(BlockTags.PLANKS)
          .recipe((context, provider) -> ShapelessRecipeBuilder.shapelessRecipe(context.get(), 4).addIngredient(EssenceTags.EssenceItemTags.ESSENCE_WOOD_LOG).addCriterion("hasLog", DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_WOOD_LOG).getCritereon(provider)).build(provider))
          .blockstate((context, provider) -> {})
          .item().group(() -> Essence.CORE_TAB).model((context, provider) -> {}).tag(EssenceTags.EssenceItemTags.ESSENCE_WOOD_PLANKS).build()
          .register();
  public static BlockEntry<EssenceSlabBlock> ESSENCE_WOOD_SLAB = Essence.ESSENCE_REGISTRATE.object("essence_wood_slab")
          .block(EssenceSlabBlock::new).initialProperties(Material.WOOD, MaterialColor.CYAN).properties(properties -> properties.hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)).loot(BlockLootTables::registerDropSelfLootTable).lang("Essence-Wood Slab").tag(BlockTags.SLABS, BlockTags.WOODEN_SLABS)
          .loot((registrateBlockLootTables, essenceSlabBlock) -> registrateBlockLootTables.registerLootTable(essenceSlabBlock, LootTable.builder()
                  .addLootPool(LootPool.builder()
                          .rolls(ConstantRange.of(1))
                          .addEntry(ItemLootEntry.builder(ESSENCE_WOOD_LOG.get())
                                  .acceptFunction(SetCount.builder(ConstantRange.of(2))
                                          .acceptCondition(BlockStateProperty.builder(essenceSlabBlock)
                                                  .fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(SlabBlock.TYPE, SlabType.DOUBLE))
                                          )
                                  )
                                  .acceptFunction(ExplosionDecay.builder())
                          )
                          .acceptCondition(SurvivesExplosion.builder())
                  )
          ))
          .recipe((context, provider) -> TitaniumShapedRecipeBuilder.shapedRecipe(context.get(), 4).setName(new ResourceLocation(Essence.MOD_ID, "essence_wood_slab_mid")).patternLine("   ").patternLine("ppp").patternLine("   ").key('p', EssenceTags.EssenceItemTags.ESSENCE_WOOD_PLANKS).addCriterion("hasPlanks", DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_WOOD_PLANKS).getCritereon(provider)).build(provider))
          .blockstate((context, provider) -> {})
          .item().group(() -> Essence.CORE_TAB).model((context, provider) -> {}).build()
          .register();

  // Infusion Blocks
  public static BlockEntry<InfusionTableBlock> INFUSION_TABLE = Essence.ESSENCE_REGISTRATE.object("essence_infusion_table")
          .block(Material.ROCK, InfusionTableBlock::new).properties(properties -> properties.sound(SoundType.STONE).hardnessAndResistance(3.5F).harvestTool(ToolType.PICKAXE).harvestLevel(2).notSolid().variableOpacity()).lang("Infusion Table").loot(BlockLootTables::registerDropSelfLootTable).addLayer(() -> RenderType::getTranslucent)
          .blockstate((context, provider) -> {})
          .item().group(() -> Essence.CORE_TAB).model((context, provider) -> {}).build()
          .register();
  public static TileEntityEntry<InfusionTableTile> INFUSION_TABLE_TILE = Essence.ESSENCE_REGISTRATE.tileEntity("essence_infusion_table", InfusionTableTile::new)
          .renderer(() -> InfusionTableTESR::new).validBlock(INFUSION_TABLE)
          .register();

  public static BlockEntry<InfusionPedestalBlock> INFUSION_PEDESTAL = Essence.ESSENCE_REGISTRATE.object("essence_infusion_pedestal")
          .block(Material.ROCK, InfusionPedestalBlock::new).properties(properties -> properties.hardnessAndResistance(3).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(2).notSolid()).lang("Infusion Pedestal").loot(BlockLootTables::registerDropSelfLootTable).addLayer(() -> RenderType::getTranslucent)
          .blockstate((context, provider) -> {})
          .item().group(() -> Essence.CORE_TAB).model((context, provider) -> {}).build()
          .register();
  public static TileEntityEntry<InfusionPedestalTile> INFUSION_PEDESTAL_TILE = Essence.ESSENCE_REGISTRATE.tileEntity("essence_infusion_pedestal", InfusionPedestalTile::new)
          .renderer(() -> InfusionPedestalTESR::new).validBlock(INFUSION_PEDESTAL)
          .register();

  // Creation Methods
  public static BlockEntry<EssenceBlock> essenceBlock(EssenceItemTiers tier) {
    String name = "essence_infused_block_";
    String tierType = tier == EssenceItemTiers.ESSENCE ? "" : tier.toString().toLowerCase();
    String entryName = tierType.equals("") ? name.substring(0, name.length() - 1): name + tierType.split("_")[0];
    return Essence.ESSENCE_REGISTRATE.object(entryName)
            .block(Material.IRON, properties -> new EssenceBlock(properties, tier))
            .properties(properties -> properties.sound(SoundType.METAL).speedFactor(1.25f))
            .loot(BlockLootTables::registerDropSelfLootTable)
            .lang("Essence-Infused Block")
            .blockstate((context, provider) -> {})
            .recipe((context, provider) -> {
              EssenceNuggetItem nugget = tier.getNugget().get().getKey().get();
              EssenceIngotItem ingot = tier.getIngot().get().getKey().get();
              EssenceBlock block = context.get();
              DataIngredient nuggetIngredient = DataIngredient.tag(tier.getNugget().get().getValue());
              DataIngredient ingotIngredient = DataIngredient.tag(tier.getIngot().get().getValue());
              DataIngredient blockIngredient = DataIngredient.tag(tier.getBlock().get().getValue());
              ShapedRecipeBuilder.shapedRecipe(ingot)
                      .patternLine("nnn").patternLine("nnn").patternLine("nnn")
                      .key('n', nuggetIngredient)
                      .addCriterion("has_" + provider.safeName(nuggetIngredient), nuggetIngredient.getCritereon(provider))
                      .build(provider, new ResourceLocation(Essence.MOD_ID, entryName + "_nugget_to_ingot"));
              ShapelessRecipeBuilder.shapelessRecipe(nugget, 9)
                      .addIngredient(ingotIngredient)
                      .addCriterion("has_" + provider.safeName(ingotIngredient), ingotIngredient.getCritereon(provider))
                      .build(provider, new ResourceLocation(Essence.MOD_ID, entryName + "_ingot_to_nuggets"));
              ShapedRecipeBuilder.shapedRecipe(block)
                      .patternLine("iii").patternLine("iii").patternLine("iii")
                      .key('i', ingotIngredient)
                      .addCriterion("has_" + provider.safeName(ingotIngredient), ingotIngredient.getCritereon(provider))
                      .build(provider, new ResourceLocation(Essence.MOD_ID, entryName + "_ingot_to_block"));
              ShapelessRecipeBuilder.shapelessRecipe(ingot, 9)
                      .addIngredient(blockIngredient)
                      .addCriterion("has_" + provider.safeName(blockIngredient), blockIngredient.getCritereon(provider))
                      .build(provider, new ResourceLocation(Essence.MOD_ID, entryName + "_block_to_ingots"));
            })
            .item((essenceBlock, properties) -> essenceBlock.getBlockItem(properties.group(Essence.CORE_TAB)).create())
            .model((context, provider) -> {})
            .tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_BLOCK)
            .build()
            .register();
  }

  private static boolean baseRecipe = false;
  private static boolean essenceToBrickRecipe = false;

  public static BlockEntry<EssenceBrickBlock> essenceBrickBlock(DyeColor color) {
    return Essence.ESSENCE_REGISTRATE.object("essence_bricks_" + color.getString())
            .block(Material.IRON, properties -> new EssenceBrickBlock(properties, color)).properties(properties -> properties.sound(SoundType.STONE).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 1200F)).loot(BlockLootTables::registerDropSelfLootTable).lang(blockItem -> EssenceUtilHelper.getColorFormattedLangString("Essence-Infused Bricks", color)).tag(BlockTags.STONE_BRICKS, BlockTags.WITHER_IMMUNE, EssenceTags.EssenceBlockTags.ESSENCE_BRICKS)
            .blockstate((context, provider) -> {})
            .item().group(() -> Essence.CORE_TAB).model((context, provider) -> {}).tag(EssenceTags.EssenceItemTags.ESSENCE_BRICKS)
            .recipe((context, provider) -> {
              EssenceBrickBlock brickBlock = EssenceBrickBlock.dyeToColorMap.get(color).get();
              // Recolor
              DataIngredient brickIngredient = DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_BRICKS);
              DataIngredient nuggetIngredient = DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_NUGGET);
              new ShapedRecipeBuilder(brickBlock, 8)
                      .patternLine("bbb").patternLine("bdb").patternLine("bbb")
                      .key('b', brickIngredient).key('d', color.getTag())
                      .addCriterion("has_" + provider.safeName(brickIngredient), brickIngredient.getCritereon(provider))
                      .build(provider, new ResourceLocation(brickBlock.getRegistryName().getNamespace(), brickBlock.getRegistryName().getPath() + "_recolor"));
              // Base Recipe
              if (!baseRecipe) {
                new ShapedRecipeBuilder(ESSENCE_BRICKS_CYAN.get(), 8)
                        .patternLine("bbb").patternLine("bnb").patternLine("bbb")
                        .key('b', ItemTags.STONE_BRICKS).key('n', nuggetIngredient)
                        .addCriterion("has_" + provider.safeName(nuggetIngredient), nuggetIngredient.getCritereon(provider))
                        .build(provider, new ResourceLocation(Essence.MOD_ID, context.getId().getPath()));
                baseRecipe = true;
              }
              if (!essenceToBrickRecipe) {
                ShapelessRecipeBuilder.shapelessRecipe(Items.STONE_BRICKS).addIngredient(EssenceTags.EssenceItemTags.ESSENCE_BRICKS).addCriterion("has_" + provider.safeName(brickIngredient), brickIngredient.getCritereon(provider)).build(provider, new ResourceLocation(Essence.MOD_ID, "essence_brick_to_stone_brick"));
                essenceToBrickRecipe = true;
              }
            })
            .build()
            .register();
  }

}
