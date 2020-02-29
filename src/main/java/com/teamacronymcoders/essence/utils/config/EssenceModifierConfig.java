package com.teamacronymcoders.essence.utils.config;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;

@ConfigFile(value = "modifiers")
public class EssenceModifierConfig {

    @ConfigVal
    public static Cascading cascading = new Cascading();

    public static class Cascading {
        @ConfigVal
        public static Lumber lumber = new Lumber();
        @ConfigVal
        public static Vein vein = new Vein();
        @ConfigVal
        public static Excavation excavation = new Excavation();

        public static class Lumber {
            @ConfigVal(comment = "Block-Break Limit")
            public static int blockLimit = 625;
            @ConfigVal(comment = "Max-Search Limit")
            public static int searchLimit = 75;
        }

        public static class Vein {
            @ConfigVal(comment = "Block-Break Limit")
            public static int blockLimit = 625;
            @ConfigVal(comment = "Max-Search Limit")
            public static int searchLimit = 25;
        }

        public static class Excavation {
            @ConfigVal(comment = "Block-Break Limit")
            public static int blockLimit = 625;
            @ConfigVal(comment = "Max-Search Limit")
            public static int searchLimit = 25;
        }
    }
}
