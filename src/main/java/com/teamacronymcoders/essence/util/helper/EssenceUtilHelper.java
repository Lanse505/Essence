package com.teamacronymcoders.essence.util.helper;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.tier.EssenceToolTiers;
import java.util.TreeMap;
import net.minecraft.item.IItemTier;
import net.minecraft.util.text.TextFormatting;

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

  public static String toRoman (int number) {
    int l = roman.floorKey(number);
    if (number == l) {
      return roman.get(number);
    }
    return roman.get(l) + toRoman(number - l);
  }

  public static TextFormatting getTextColor (int free_modifiers, int max_modifiers) {
    int val = free_modifiers / max_modifiers;
    if (val <= 0.33) {
      return TextFormatting.RED;
    } else if (val <= 0.66) {
      return TextFormatting.YELLOW;
    } else {
      return TextFormatting.GREEN;
    }
  }


  public static int nextIntInclusive (int min, int max) {
    return Essence.RANDOM.nextInt(max - min + 1) + min;
  }

  public static String getDurationString (int seconds) {

    int hours = seconds / 3600;
    int minutes = (seconds % 3600) / 60;
    seconds = seconds % 60;

    return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
  }

  private static String twoDigitString (int number) {

    if (number == 0) {
      return "00";
    }

    if (number / 10 == 0) {
      return "0" + number;
    }

    return String.valueOf(number);
  }

  private static boolean isEssenceItemTier (IItemTier tier) {
    return tier instanceof EssenceToolTiers;
  }

  public static EssenceToolTiers getEssenceItemTier (IItemTier tier) {
    return isEssenceItemTier(tier) ? (EssenceToolTiers) tier : null;
  }

  public static int getLevelForExperience (int experience) {
    int i = 0;
    while (getExperienceForLevel(i) <= experience) {
      i++;
    }
    return i - 1;
  }

  public static int getExperienceForLevel (int level) {
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

  public static int getExperienceForLevelWithDestination (int currentLevel, int destination) {
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


}
