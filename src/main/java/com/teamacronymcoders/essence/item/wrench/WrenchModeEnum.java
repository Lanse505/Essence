package com.teamacronymcoders.essence.item.wrench;

public enum WrenchModeEnum {
  SERIALIZE("essence.mode.wrench.serialize"),
  ROTATE("essence.mode.wrench.rotate"),
  TRIGGER("essence.mode.wrench.trigger");

  public static final WrenchModeEnum[] VALUES = new WrenchModeEnum[] {SERIALIZE, ROTATE, TRIGGER};
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
