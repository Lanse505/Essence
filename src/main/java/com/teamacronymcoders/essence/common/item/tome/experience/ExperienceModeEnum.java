package com.teamacronymcoders.essence.common.item.tome.experience;

public enum ExperienceModeEnum {
    FILL("tome.essence.experience.mode.fill"),
    DRAIN("tome.essence.experience.mode.drain"),
    ABSORB("tome.essence.experience.mode.absorb");

    public static final ExperienceModeEnum[] VALUES = new ExperienceModeEnum[]{FILL, DRAIN, ABSORB};
    private final String localeString;

    ExperienceModeEnum(String localeString) {
        this.localeString = localeString;
    }

    public static ExperienceModeEnum cycleMode(int id) {
        if (id == 0) {
            return VALUES[1];
        } else if (id == 1) {
            return VALUES[2];
        } else {
            return VALUES[0];
        }
    }

    public String getLocaleString() {
        return localeString;
    }
}
