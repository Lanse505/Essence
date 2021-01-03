package com.teamacronymcoders.essence.modifier.item.interaction.cascading;

import com.teamacronymcoders.essence.util.EssenceTags.EssenceModifierTags;
import com.teamacronymcoders.essence.util.config.EssenceModifierConfig;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

import java.util.Arrays;
import java.util.function.Supplier;


public enum CascadingType {
    NONE(0, "none", EssenceModifierTags.NONE_TOOL, EssenceModifierTags.CASCADING_NONE, () -> 0, () -> 0, TextFormatting.GRAY, TextFormatting.ITALIC),
    LUMBER(1, "lumber", EssenceModifierTags.AXE_TOOL, EssenceModifierTags.CASCADING_LUMBER, () -> EssenceModifierConfig.getInstance().getLumber().getBlockLimit().get(), () -> EssenceModifierConfig.getInstance().getLumber().getSearchLimit().get(), TextFormatting.GREEN, TextFormatting.ITALIC),
    VEIN(2, "vein", EssenceModifierTags.PICKAXE_TOOL, EssenceModifierTags.CASCADING_VEIN, () -> EssenceModifierConfig.getInstance().getVein().getBlockLimit().get(), () -> EssenceModifierConfig.getInstance().getVein().getSearchLimit().get(), TextFormatting.BLUE, TextFormatting.ITALIC),
    EXCAVATION(3, "excavation", EssenceModifierTags.SHOVEL_TOOL, EssenceModifierTags.CASCADING_EXCAVATION, () -> EssenceModifierConfig.getInstance().getExcavation().getBlockLimit().get(), () -> EssenceModifierConfig.getInstance().getExcavation().getSearchLimit().get(), TextFormatting.GOLD, TextFormatting.ITALIC);

    private static final CascadingType[] VALUES = new CascadingType[]{VEIN, LUMBER, EXCAVATION};
    private final int id;
    private final String name;
    private final TextFormatting[] formatting;
    private ITag.INamedTag<Item> toolTag;
    private ITag.INamedTag<Block> blockTag;
    private Supplier<Integer> blockLimit;
    private Supplier<Integer> searchLimit;

    CascadingType(int id, String name, ITag.INamedTag<Item> toolTag, ITag.INamedTag<Block> blockTag, Supplier<Integer> blockLimit, Supplier<Integer> searchLimit, TextFormatting... formatting) {
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

    public ITag.INamedTag<Item> getToolTag() {
        return toolTag;
    }

    public ITag.INamedTag<Block> getBlockTag() {
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
