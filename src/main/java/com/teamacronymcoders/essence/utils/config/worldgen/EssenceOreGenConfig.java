package com.teamacronymcoders.essence.utils.config.worldgen;

import com.hrznstudio.titanium.annotation.config.ConfigVal;
import com.teamacronymcoders.essence.api.misc.IOreGenConfig;

public class EssenceOreGenConfig {

    public static EssenceOre essenceOre = new EssenceOre();

    public static class EssenceOre implements IOreGenConfig {

        @ConfigVal(comment = "Should 'Essence-Infused Ore' generate in-world?")
        public static boolean shouldGenerate = true;

        @ConfigVal(comment = "Chance that 'Essence-Infused Ore' generates in a chunk.")
        @ConfigVal.InRangeInt(min = 1, max = 65536)
        public static int chancePerChunk = 12;

        @ConfigVal(comment = "Max number of blocks in a vein of 'Essence-Infused Ore'")
        @ConfigVal.InRangeInt(min = 1, max = 65536)
        public static int maxVeinSize = 8;

        @ConfigVal(comment = "Base maximum height of 'Essence-Infused Ore' can spawn.")
        @ConfigVal.InRangeInt(min = 1, max = 256)
        public static int maxHeight;

        @ConfigVal(comment = "Top offset for calculating height that veins of can 'Essence-Infused Ore' spawn.")
        @ConfigVal.InRangeInt(min = 1, max = 128)
        public static int topOffset;

        @ConfigVal(comment = "Bottom offset for calculating height that veins of can 'Essence-Infused Ore' spawn.")
        @ConfigVal.InRangeInt(min = 1, max = 128)
        public static int bottomOffset;


        @Override
        public boolean getShouldGenerate() {
            return shouldGenerate;
        }

        @Override
        public int getChanceToGenerate() {
            return chancePerChunk;
        }

        @Override
        public int getMaxVeinSize() {
            return maxVeinSize;
        }

        @Override
        public int getMaxHeight() {
            return maxHeight;
        }

        @Override
        public int getTopOffset() {
            return topOffset;
        }

        @Override
        public int getBottomOffset() {
            return bottomOffset;
        }
    }

}
