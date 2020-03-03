package com.teamacronymcoders.essence.utils.config;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;
import com.teamacronymcoders.essence.utils.config.modifier.CascadingConfig;

@ConfigFile(value = "modifiers")
public class EssenceModifierConfig {

    @ConfigVal
    public static CascadingConfig cascading = new CascadingConfig();


}
