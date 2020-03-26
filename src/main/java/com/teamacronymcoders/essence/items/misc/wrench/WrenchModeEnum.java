package com.teamacronymcoders.essence.items.misc.wrench;

public enum WrenchModeEnum {
    SERIALIZE("mode.wrench.serialize", 0),
    ROTATE("mode.wrench.rotate", 1);

    private final String localeName;
    private final int id;

    public static final WrenchModeEnum[] VALUES = new WrenchModeEnum[]{ROTATE, SERIALIZE};

    WrenchModeEnum(String localeName, int id) {
        this.localeName = localeName;
        this.id = id;
    }

    public static WrenchModeEnum cycleMode(int id) {
        if (id == VALUES.length) {
            return VALUES[0];
        }
        return VALUES[id + 1];
    }

    public String getLocaleName() {
        return localeName;
    }

    public int getId() {
        return id;
    }
}
