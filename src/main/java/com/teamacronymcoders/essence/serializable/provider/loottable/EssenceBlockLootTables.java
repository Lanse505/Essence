package com.teamacronymcoders.essence.serializable.provider.loottable;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.block.EssenceBlock;
import com.teamacronymcoders.essence.block.EssenceBrickBlock;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.state.properties.SlabType;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.*;
import net.minecraft.world.storage.loot.functions.ApplyBonus;
import net.minecraft.world.storage.loot.functions.ExplosionDecay;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class EssenceBlockLootTables extends BlockLootTables {



    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
            .filter(entity -> Optional.ofNullable(entity.getRegistryName())
                .filter(registryName -> registryName.getNamespace().equals(Essence.MODID)).isPresent()
            ).collect(Collectors.toList());
    }

    @Override
    protected void addTables() {
        this.registerLootTable(EssenceObjectHolders.ESSENCE_WOOD_LEAVES, LootTable.builder()
            .addLootPool(LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(AlternativesLootEntry.builder(
                    ItemLootEntry.builder(EssenceObjectHolders.ESSENCE_WOOD_LEAVES)
                        .acceptCondition(
                            MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS))
                                .alternative(MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))
                            )
                        ),
                    ItemLootEntry.builder(EssenceObjectHolders.ESSENCE_WOOD_SAPLING)
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
        );
        this.registerLootTable(EssenceObjectHolders.ESSENCE_WOOD_LOG, LootTable.builder()
            .addLootPool(LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(EssenceObjectHolders.ESSENCE_WOOD_LOG))
                .acceptCondition(SurvivesExplosion.builder())
            )
        );
        this.registerLootTable(EssenceObjectHolders.ESSENCE_WOOD_PLANKS, LootTable.builder()
            .addLootPool(LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(EssenceObjectHolders.ESSENCE_WOOD_PLANKS))
                .acceptCondition(SurvivesExplosion.builder())
            )
        );
        this.registerLootTable(EssenceObjectHolders.ESSENCE_WOOD_SAPLING, LootTable.builder()
            .addLootPool(LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(EssenceObjectHolders.ESSENCE_WOOD_SAPLING))
                .acceptCondition(SurvivesExplosion.builder())
            )
        );
        this.registerLootTable(EssenceObjectHolders.ESSENCE_WOOD_SLAB, LootTable.builder()
            .addLootPool(LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(EssenceObjectHolders.ESSENCE_WOOD_LOG)
                    .acceptFunction(SetCount.builder(ConstantRange.of(2))
                        .acceptCondition(BlockStateProperty.builder(EssenceObjectHolders.ESSENCE_WOOD_SLAB)
                            .fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(SlabBlock.TYPE, SlabType.DOUBLE))
                        )
                    )
                    .acceptFunction(ExplosionDecay.builder())
                )
                .acceptCondition(SurvivesExplosion.builder())
            )
        );
        this.registerLootTable(EssenceObjectHolders.ESSENCE_ORE, LootTable.builder()
            .addLootPool(LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(EssenceObjectHolders.ESSENCE_ORE))
                .acceptCondition(SurvivesExplosion.builder())
            )
        );
        this.registerLootTable(EssenceObjectHolders.ESSENCE_CRYSTAL_ORE, LootTable.builder()
            .addLootPool(LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(AlternativesLootEntry.builder(
                    ItemLootEntry.builder(EssenceObjectHolders.ESSENCE_CRYSTAL_ORE)
                        .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create()
                            .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))
                        )),
                    ItemLootEntry.builder(EssenceObjectHolders.ESSENCE_INFUSED_CRYSTAL)
                        .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE))
                        .acceptFunction(ExplosionDecay.builder())
                ))
            )
        );
        this.registerLootTable(EssenceObjectHolders.INFUSION_PEDESTAL, LootTable.builder()
            .addLootPool(LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(EssenceObjectHolders.INFUSION_PEDESTAL))
                .acceptCondition(SurvivesExplosion.builder())
            )
        );
        this.registerLootTable(EssenceObjectHolders.INFUSION_TABLE, LootTable.builder()
            .addLootPool(LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(EssenceObjectHolders.INFUSION_TABLE))
                .acceptCondition(SurvivesExplosion.builder())
            )
        );

        for (Supplier<EssenceBlock> metal_block : EssenceObjectHolders.ESSENCE_BLOCKS) {
            this.registerLootTable(metal_block.get(), LootTable.builder()
                .addLootPool(LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(metal_block.get()))
                    .acceptCondition(SurvivesExplosion.builder())
                )
            );
        }

        for (Supplier<EssenceBrickBlock> brick : EssenceObjectHolders.BRICK_BLOCK_LIST) {
            this.registerLootTable(brick.get(), LootTable.builder()
                .addLootPool(LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(brick.get()))
                )
            );
        }
    }
}
