package com.teamacronymcoders.essence.common.item.wrench;

public enum WrenchModeEnum {
    SERIALIZE("serialize", "essence.mode.wrench.serialize"),
    ROTATE("rotate", "essence.mode.wrench.rotate"),
    TRIGGER("trigger", "essence.mode.wrench.trigger");

    public static final WrenchModeEnum[] VALUES = new WrenchModeEnum[]{SERIALIZE, ROTATE, TRIGGER};
    private final String name;
    private final String localeName;

    WrenchModeEnum(String name, String localeName) {
        this.name = name;
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

    public static WrenchModeEnum byName(String input) {
        WrenchModeEnum mode;
        switch (input) {
            default ->  mode = WrenchModeEnum.SERIALIZE;
            case "rotate" -> mode = WrenchModeEnum.ROTATE;
            case "trigger" ->  mode = WrenchModeEnum.TRIGGER;
        }
        return mode;
    }

    public String getName() {
        return name;
    }

    public String getLocaleName() {
        return localeName;
    }

}
