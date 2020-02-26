package com.teamacronymcoders.essence.utils.config;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;

@ConfigFile("general")
public class EssenceGeneralConfig {
    @ConfigVal
    public static boolean enableDebugLogging = false;
}
