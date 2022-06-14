package com.teamacronymcoders.essenceapi.helper;

import com.teamacronymcoders.essenceapi.EssenceAPI;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.world.item.DyeColor;

import java.util.Locale;
import java.util.TreeMap;

public class EssenceAPIUtilHelper {

    private final static TreeMap<Integer, String> roman = Util.make(new TreeMap<>(), (map) -> {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    });

    public static String toRoman(int number) {
        int l = roman.floorKey(number);
        if (number == l) {
            return roman.get(number);
        }
        return roman.get(l) + toRoman(number - l);
    }

    public static ChatFormatting getTextColor(int current, int max) {
        int val = current / max;
        if (val <= 0.33) {
            return ChatFormatting.RED;
        } else if (val <= 0.66) {
            return ChatFormatting.YELLOW;
        } else {
            return ChatFormatting.GREEN;
        }
    }

    public static int nextIntInclusive(int min, int max) {
        return EssenceAPI.RANDOM.nextInt(max - min + 1) + min;
    }

    public static String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }

    private static String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    public static int getLevelForExperience(int experience) {
        int i = 0;
        while (getExperienceForLevel(i) <= experience) {
            i++;
        }
        return i - 1;
    }

    public static int getExperienceForLevel(int level) {
        if (level == 0) {
            return 0;
        }

        if (level > 0 && level < 17) {
            return level * level + 6 * level;
        } else if (level > 16 && level < 32) {
            return (int) (2.5 * level * level - 40.5 * level + 360);
        }
        return (int) (4.5 * level * level - 162.5 * level + 2220);
    }

    public static int getExperienceForLevelWithDestination(int currentLevel, int destination) {
        int exp = 0;
        for (; currentLevel > destination; currentLevel--) {
            if (currentLevel <= 16) {
                exp += 2 * currentLevel + 7;
            } else if (currentLevel <= 31) {
                exp += 5 * currentLevel - 38;
            } else {
                exp += 9 * currentLevel - 158;
            }
        }
        return exp;
    }

    public static String getColorFormattedLangString(String input, DyeColor color) {
        String colorName = color.getName();
        String name;
        if (colorName.contains("_")) {
            String[] strings = colorName.split("_");
            StringBuilder builder = new StringBuilder();
            boolean firstString = true;
            for (String string : strings) {
                builder.append(string.substring(0, 1).toUpperCase(Locale.ROOT)).append(string.substring(1));
                if (firstString) {
                    builder.append("-");
                    firstString = false;
                }
            }
            name = builder + " " + input;
            return name;
        }
        name = colorName.substring(0, 1).toUpperCase(Locale.ROOT) + colorName.substring(1) + " " + input;
        return name;
    }


}
