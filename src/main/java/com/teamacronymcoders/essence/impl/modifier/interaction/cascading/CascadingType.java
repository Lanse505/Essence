package com.teamacronymcoders.essence.impl.modifier.interaction.cascading;

import com.teamacronymcoders.essence.utils.tags.EssenceTags;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;

import static com.teamacronymcoders.essence.utils.config.EssenceModifierConfig.Cascading.*;

public enum CascadingType {
    NONE(0, "none", EssenceTags.Modifier.NONE_TOOL, EssenceTags.Modifier.CASCADING_NONE, 0, 0, TextFormatting.GRAY, TextFormatting.ITALIC),
    LUMBER(1, "lumber", EssenceTags.Modifier.AXE_TOOL, EssenceTags.Modifier.CASCADING_LUMBER, Lumber.blockLimit, Lumber.searchLimit, TextFormatting.GREEN, TextFormatting.ITALIC),
    VEIN(2, "vein", EssenceTags.Modifier.PICKAXE_TOOL, EssenceTags.Modifier.CASCADING_VEIN, Vein.blockLimit, Vein.searchLimit, TextFormatting.BLUE, TextFormatting.ITALIC),
    EXCAVATION(3, "excavation", EssenceTags.Modifier.SHOVEL_TOOL, EssenceTags.Modifier.CASCADING_EXCAVATION, Excavation.blockLimit, Excavation.searchLimit, TextFormatting.GOLD, TextFormatting.ITALIC);

    private final int id;
    private final String name;
    private Tag<Item> toolTag;
    private Tag<Block> blockTag;
    private int blockLimit;
    private int searchLimit;
    private final TextFormatting[] formatting;

    private static final CascadingType[] VALUES = new CascadingType[] {VEIN, LUMBER, EXCAVATION};

    CascadingType(int id, String name, Tag<Item> toolTag, Tag<Block> blockTag, int blockLimit, int searchLimit, TextFormatting... formatting) {
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

    public int getBlockLimit() {
        return blockLimit;
    }

    public int getSearchLimit() {
        return searchLimit;
    }

    public TextFormatting[] getFormatting() {
        return formatting;
    }
}
