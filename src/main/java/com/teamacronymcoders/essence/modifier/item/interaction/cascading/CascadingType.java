package com.teamacronymcoders.essence.modifier.item.rewrite.interaction.cascading;

import com.teamacronymcoders.essence.utils.EssenceTags;
import com.teamacronymcoders.essence.utils.config.EssenceModifierConfig;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.function.Supplier;


public enum CascadingType {
    NONE(0, "none", EssenceTags.Modifier.NONE_TOOL, EssenceTags.Modifier.CASCADING_NONE, () -> 0, () -> 0, TextFormatting.GRAY, TextFormatting.ITALIC),
    LUMBER(1, "lumber", EssenceTags.Modifier.AXE_TOOL, EssenceTags.Modifier.CASCADING_LUMBER, () -> EssenceModifierConfig.getInstance().getLumber().getBlockLimit().get(), () -> EssenceModifierConfig.getInstance().getLumber().getSearchLimit().get(), TextFormatting.GREEN, TextFormatting.ITALIC),
    VEIN(2, "vein", EssenceTags.Modifier.PICKAXE_TOOL, EssenceTags.Modifier.CASCADING_VEIN, () -> EssenceModifierConfig.getInstance().getVein().getBlockLimit().get(), () -> EssenceModifierConfig.getInstance().getVein().getSearchLimit().get(), TextFormatting.BLUE, TextFormatting.ITALIC),
    EXCAVATION(3, "excavation", EssenceTags.Modifier.SHOVEL_TOOL, EssenceTags.Modifier.CASCADING_EXCAVATION, () -> EssenceModifierConfig.getInstance().getExcavation().getBlockLimit().get(), () -> EssenceModifierConfig.getInstance().getExcavation().getSearchLimit().get(), TextFormatting.GOLD, TextFormatting.ITALIC);

    private static final CascadingType[] VALUES = new CascadingType[]{VEIN, LUMBER, EXCAVATION};
    private final int id;
    private final String name;
    private final TextFormatting[] formatting;
    private Tag<Item> toolTag;
    private Tag<Block> blockTag;
    private Supplier<Integer> blockLimit;
    private Supplier<Integer> searchLimit;

    CascadingType(int id, String name, Tag<Item> toolTag, Tag<Block> blockTag, Supplier<Integer> blockLimit, Supplier<Integer> searchLimit, TextFormatting... formatting) {
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

    public Tag<Item> getToolTag() {
        return toolTag;
    }

    public Tag<Block> getBlockTag() {
        return blockTag;
    }

    public Supplier<Integer> getBlockLimit() {
        return blockLimit;
    }

    public Supplier<Integer> getSearchLimit() {
        return searchLimit;
    }

    public TextFormatting[] getFormatting() {
        return formatting;
    }
}
