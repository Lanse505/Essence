package com.teamacronymcoders.essence.utils.helpers;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.TreeMap;

public class EssenceUtilHelper {

    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {
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
    }

    public static String toRoman(int number) {
        int l = map.floorKey(number);
        if (number == l) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number - l);
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

    /**
     * Gets a tile entity if the location is loaded
     * @param world - world
     * @param pos   - position
     * @return tile entity if found, null if either not found or not loaded
     */
    @Nullable
    public static TileEntity getTileEntity(@Nullable World world, @Nonnull BlockPos pos) {
        if (world == null || !world.isBlockPresent(pos)) {
            return null;
        }
        return world.getTileEntity(pos);
    }

}
