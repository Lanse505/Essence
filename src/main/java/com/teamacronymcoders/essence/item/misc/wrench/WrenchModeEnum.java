package com.teamacronymcoders.essence.item.misc.wrench;

public enum WrenchModeEnum {
    SERIALIZE("mode.wrench.serialize"),
    ROTATE("mode.wrench.rotate"),
    TRIGGER("mode.wrench.trigger");

    public static final WrenchModeEnum[] VALUES = new WrenchModeEnum[]{SERIALIZE, ROTATE, TRIGGER};
    private final String localeName;

    WrenchModeEnum(String localeName) {
        this.localeName = localeName;
    }

    public static WrenchModeEnum cycleMode(int id) {
        if (id == 0) {
            return VALUES[1];
        } else if (id == 1) {
            return VALUES[2];
        } else {
            return VALUES[0];
        }
    }

    public String getLocaleName() {
        return localeName;
    }

}
