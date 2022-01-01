package com.teamacronymcoders.essence.common.util.helper;

import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EssenceColorHelper {
    public static Map<IOptionalNamedTag<Item>, DyeColor> tagToDye = new HashMap<>();
    private static final List<Color> colors = new ArrayList<>();

    static {
        for (int r = 0; r < 100; r++) {
            colors.add(new Color(r * 255 / 100, 255, 0));
        }
        for (int g = 100; g > 0; g--) {
            colors.add(new Color(255, g * 255 / 100, 0));
        }
        for (int b = 0; b < 100; b++) {
            colors.add(new Color(255, 0, b * 255 / 100));
        }
        for (int r = 100; r > 0; r--) {
            colors.add(new Color(r * 255 / 100, 0, 255));
        }
        for (int g = 0; g < 100; g++) {
            colors.add(new Color(0, g * 255 / 100, 255));
        }
        for (int b = 100; b > 0; b--) {
            colors.add(new Color(0, 255, b * 255 / 100));
        }
        colors.add(new Color(0, 255, 0));

        tagToDye.put(Tags.Items.DYES_WHITE, DyeColor.WHITE);
        tagToDye.put(Tags.Items.DYES_ORANGE, DyeColor.ORANGE);
        tagToDye.put(Tags.Items.DYES_MAGENTA, DyeColor.MAGENTA);
        tagToDye.put(Tags.Items.DYES_LIGHT_BLUE, DyeColor.LIGHT_BLUE);
        tagToDye.put(Tags.Items.DYES_YELLOW, DyeColor.YELLOW);
        tagToDye.put(Tags.Items.DYES_LIME, DyeColor.LIME);
        tagToDye.put(Tags.Items.DYES_PINK, DyeColor.PINK);
        tagToDye.put(Tags.Items.DYES_GRAY, DyeColor.GRAY);
        tagToDye.put(Tags.Items.DYES_LIGHT_GRAY, DyeColor.LIGHT_GRAY);
        tagToDye.put(Tags.Items.DYES_CYAN, DyeColor.CYAN);
        tagToDye.put(Tags.Items.DYES_PURPLE, DyeColor.PURPLE);
        tagToDye.put(Tags.Items.DYES_BLUE, DyeColor.BLUE);
        tagToDye.put(Tags.Items.DYES_BROWN, DyeColor.BROWN);
        tagToDye.put(Tags.Items.DYES_GREEN, DyeColor.GREEN);
        tagToDye.put(Tags.Items.DYES_RED, DyeColor.RED);
        tagToDye.put(Tags.Items.DYES_BLACK, DyeColor.BLACK);
    }

    public static Color getColor(int rainbowValue) {
        return colors.get(Mth.clamp(rainbowValue, 0, colors.size() - 1));
    }

}