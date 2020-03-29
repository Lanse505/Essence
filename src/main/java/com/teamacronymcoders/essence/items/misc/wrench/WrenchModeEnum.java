package com.teamacronymcoders.essence.items.misc.wrench;

public enum WrenchModeEnum {
    SERIALIZE("mode.wrench.serialize"),
    ROTATE("mode.wrench.rotate");

    public static final WrenchModeEnum[] VALUES = new WrenchModeEnum[]{SERIALIZE, ROTATE};
    private final String localeName;

    WrenchModeEnum(String localeName) {
        this.localeName = localeName;
    }

    public static WrenchModeEnum cycleMode(int id) {
        if (id == 0) {
            return VALUES[1];
        } else {
            return VALUES[0];
        }
    }

    public String getLocaleName() {
        return localeName;
    }

}
