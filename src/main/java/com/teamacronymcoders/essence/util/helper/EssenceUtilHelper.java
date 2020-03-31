package com.teamacronymcoders.essence.util.helper;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.tier.EssenceToolTiers;
import net.minecraft.item.IItemTier;
import net.minecraft.util.text.TextFormatting;

import java.util.TreeMap;

public class EssenceUtilHelper {

    private final static TreeMap<Integer, String> roman = new TreeMap<>();

    static {
        roman.put(1000, "M");
        roman.put(900, "CM");
        roman.put(500, "D");
        roman.put(400, "CD");
        roman.put(100, "C");
        roman.put(90, "XC");
        roman.put(50, "L");
        roman.put(40, "XL");
        roman.put(10, "X");
        roman.put(9, "IX");
        roman.put(5, "V");
        roman.put(4, "IV");
        roman.put(1, "I");
    }

    public static String toRoman(int number) {
        int l = roman.floorKey(number);
        if (number == l) {
            return roman.get(number);
        }
        return roman.get(l) + toRoman(number - l);
    }

    public static TextFormatting getTextColor(int free_modifiers) {
        if (free_modifiers <= 1) {
            return TextFormatting.RED;
        }
        if (free_modifiers <= 3) {
            return TextFormatting.YELLOW;
        }
        return TextFormatting.GREEN;
    }


    public static int nextIntInclusive(int min, int max) {
        return Essence.RANDOM.nextInt(max - min + 1) + min;
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

    private static boolean isEssenceItemTier(IItemTier tier) {
        return tier instanceof EssenceToolTiers;
    }

    public static EssenceToolTiers getEssenceItemTier(IItemTier tier) {
        return isEssenceItemTier(tier) ? (EssenceToolTiers) tier : null;
    }
}
