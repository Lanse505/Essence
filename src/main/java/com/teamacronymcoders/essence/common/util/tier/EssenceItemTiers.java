package com.teamacronymcoders.essence.common.util.tier;

import com.teamacronymcoders.essence.common.block.EssenceBlock;
import com.teamacronymcoders.essence.common.item.essence.EssenceCrystalItem;
import com.teamacronymcoders.essence.common.item.essence.EssenceIngotItem;
import com.teamacronymcoders.essence.common.item.essence.EssenceNuggetItem;
import com.teamacronymcoders.essence.common.util.EssenceTags;
import com.teamacronymcoders.essence.compat.registrate.EssenceBlockRegistrate;
import com.teamacronymcoders.essence.compat.registrate.EssenceItemRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Supplier;

public enum EssenceItemTiers implements IEssenceBaseTier {
    BASIC("tier.essence.basic", Rarity.COMMON,
            () -> Pair.of(EssenceItemRegistrate.ESSENCE_NUGGET, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_NUGGET),
            () -> Pair.of(EssenceItemRegistrate.ESSENCE_INGOT, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL),
            () -> Pair.of(EssenceBlockRegistrate.ESSENCE_INFUSED_METAL_BLOCK, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_BLOCK),
            () -> Pair.of(EssenceItemRegistrate.ESSENCE_CRYSTAL, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_CRYSTAL),
            () -> Pair.of(EssenceBlockRegistrate.ESSENCE_INFUSED_CRYSTAL_BLOCK, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_CRYSTAL_BLOCK),
            3),
    EMPOWERED("tier.essence.empowered", Rarity.UNCOMMON,
            () -> Pair.of(EssenceItemRegistrate.ESSENCE_NUGGET_EMPOWERED, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_NUGGET_EMPOWERED),
            () -> Pair.of(EssenceItemRegistrate.ESSENCE_INGOT_EMPOWERED, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED),
            () -> Pair.of(EssenceBlockRegistrate.ESSENCE_INFUSED_METAL_EMPOWERED_BLOCK, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_BLOCK_EMPOWERED),
            () -> Pair.of(EssenceItemRegistrate.ESSENCE_CRYSTAL_EMPOWERED, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_CRYSTAL_EMPOWERED),
            () -> Pair.of(EssenceBlockRegistrate.ESSENCE_INFUSED_CRYSTAL_EMPOWERED_BLOCK, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_CRYSTAL_EMPOWERED_BLOCK),
            4),
    SUPREME("tier.essence.supreme", Rarity.RARE,
            () -> Pair.of(EssenceItemRegistrate.ESSENCE_NUGGET_SUPREME, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_NUGGET_SUPREME),
            () -> Pair.of(EssenceItemRegistrate.ESSENCE_INGOT_SUPREME, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_SUPREME),
            () -> Pair.of(EssenceBlockRegistrate.ESSENCE_INFUSED_METAL_SUPREME_BLOCK, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_BLOCK_SUPREME),
            () -> Pair.of(EssenceItemRegistrate.ESSENCE_CRYSTAL_SUPREME, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_CRYSTAL_SUPREME),
            () -> Pair.of(EssenceBlockRegistrate.ESSENCE_INFUSED_CRYSTAL_SUPREME_BLOCK, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_CRYSTAL_SUPREME_BLOCK),
            5),
    DIVINE("tier.essence.divine", Rarity.EPIC,
            () -> Pair.of(EssenceItemRegistrate.ESSENCE_NUGGET_DIVINE, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_NUGGET_DIVINE),
            () -> Pair.of(EssenceItemRegistrate.ESSENCE_INGOT_DIVINE, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_DIVINE),
            () -> Pair.of(EssenceBlockRegistrate.ESSENCE_INFUSED_METAL_DIVINE_BLOCK, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_BLOCK_DIVINE),
            () -> Pair.of(EssenceItemRegistrate.ESSENCE_CRYSTAL_DIVINE, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_CRYSTAL_DIVINE),
            () -> Pair.of(EssenceBlockRegistrate.ESSENCE_INFUSED_CRYSTAL_DIVINE_BLOCK, EssenceTags.EssenceItemTags.ESSENCE_INFUSED_CRYSTAL_DIVINE_BLOCK),
            6);

    private final String localString;
    private final Rarity rarity;
    private final Supplier<Pair<ItemEntry<EssenceNuggetItem>, TagKey<Item>>> nugget;
    private final Supplier<Pair<ItemEntry<EssenceIngotItem>, TagKey<Item>>> ingot;
    private final Supplier<Pair<BlockEntry<EssenceBlock>, TagKey<Item>>> block;
    private final Supplier<Pair<ItemEntry<EssenceCrystalItem>, TagKey<Item>>> crystal;
    private final Supplier<Pair<BlockEntry<EssenceBlock>, TagKey<Item>>> crystalBlock;
    private final int basePoints;

    EssenceItemTiers(String localString, Rarity rarity, Supplier<Pair<ItemEntry<EssenceNuggetItem>, TagKey<Item>>> nugget, Supplier<Pair<ItemEntry<EssenceIngotItem>, TagKey<Item>>> ingot, Supplier<Pair<BlockEntry<EssenceBlock>, TagKey<Item>>> block, Supplier<Pair<ItemEntry<EssenceCrystalItem>, TagKey<Item>>> crystal, Supplier<Pair<BlockEntry<EssenceBlock>, TagKey<Item>>> crystalBlock, int basePoints) {
        this.localString = localString;
        this.rarity = rarity;
        this.nugget = nugget;
        this.ingot = ingot;
        this.block = block;
        this.crystal = crystal;
        this.crystalBlock = crystalBlock;
        this.basePoints = basePoints;
    }

    @Override
    public String getLocaleString() {
        return localString;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Supplier<Pair<ItemEntry<EssenceNuggetItem>, TagKey<Item>>> getNugget() {
        return nugget;
    }

    public Supplier<Pair<ItemEntry<EssenceIngotItem>, TagKey<Item>>> getIngot() {
        return ingot;
    }

    public Supplier<Pair<BlockEntry<EssenceBlock>, TagKey<Item>>> getBlock() {
        return block;
    }

    public Supplier<Pair<ItemEntry<EssenceCrystalItem>, TagKey<Item>>> getCrystal() {
        return crystal;
    }

    public Supplier<Pair<BlockEntry<EssenceBlock>, TagKey<Item>>> getCrystalBlock() {
        return crystalBlock;
    }
}
