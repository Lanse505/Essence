package com.teamacronymcoders.essence.common.modifier.item.interaction.cascading;

import com.teamacronymcoders.essence.common.util.EssenceTags.EssenceModifierTags;
import com.teamacronymcoders.essence.common.util.config.EssenceModifierMasterConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.function.Supplier;


public enum CascadingType {
    NONE(0, "none", EssenceModifierTags.NONE_TOOL, EssenceModifierTags.CASCADING_NONE, () -> 0, () -> 0, ChatFormatting.GRAY, ChatFormatting.ITALIC),
    LUMBER(1, "lumber", EssenceModifierTags.AXE_TOOL, EssenceModifierTags.CASCADING_LUMBER, () -> EssenceModifierMasterConfig.getInstance().getLumber().getBlockLimit().get(), () -> EssenceModifierMasterConfig.getInstance().getLumber().getSearchLimit().get(), ChatFormatting.GREEN, ChatFormatting.ITALIC),
    VEIN(2, "vein", EssenceModifierTags.PICKAXE_TOOL, EssenceModifierTags.CASCADING_VEIN, () -> EssenceModifierMasterConfig.getInstance().getVein().getBlockLimit().get(), () -> EssenceModifierMasterConfig.getInstance().getVein().getSearchLimit().get(), ChatFormatting.BLUE, ChatFormatting.ITALIC),
    EXCAVATION(3, "excavation", EssenceModifierTags.SHOVEL_TOOL, EssenceModifierTags.CASCADING_EXCAVATION, () -> EssenceModifierMasterConfig.getInstance().getExcavation().getBlockLimit().get(), () -> EssenceModifierMasterConfig.getInstance().getExcavation().getSearchLimit().get(), ChatFormatting.GOLD, ChatFormatting.ITALIC);

    private static final CascadingType[] VALUES = new CascadingType[]{VEIN, LUMBER, EXCAVATION};
    private final int id;
    private final String name;
    private final ChatFormatting[] formatting;
    private final TagKey<Item> toolTag;
    private final TagKey<Block> blockTag;
    private final Supplier<Integer> blockLimit;
    private final Supplier<Integer> searchLimit;

    CascadingType(int id, String name, TagKey<Item> toolTag, TagKey<Block> blockTag, Supplier<Integer> blockLimit, Supplier<Integer> searchLimit, ChatFormatting... formatting) {
        this.id = id;
        this.name = name;
        this.toolTag = toolTag;
        this.blockTag = blockTag;
        this.blockLimit = blockLimit;
        this.searchLimit = searchLimit;
        this.formatting = formatting;
    }

    public static CascadingType byID(int id) {
        if (id >= 0 && id < VALUES.length) {
            return VALUES[id];
        } else {
            throw new IllegalArgumentException("No CascadingEnum with value " + id);
        }
    }

    public static CascadingType byName(String name) {
        return Arrays.stream(VALUES).filter(enumToCheck -> enumToCheck.name.equals(name)).findAny().orElse(NONE);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TagKey<Item> getToolTag() {
        return toolTag;
    }

    public TagKey<Block> getBlockTag() {
        return blockTag;
    }

    public Supplier<Integer> getBlockLimit() {
        return blockLimit;
    }

    public Supplier<Integer> getSearchLimit() {
        return searchLimit;
    }

    public ChatFormatting[] getFormatting() {
        return formatting;
    }
}
