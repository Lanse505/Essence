package com.teamacronymcoders.essence.api.recipe.infusion;

public enum InfusionOperation {
  ADD(0, "add"),
  REMOVE(1, "remove"),
  INCREMENT(2, "increment"),
  DECREMENT(3, "decrement"),
  MERGE_TAGS(4, "merge_tags");

  private static final InfusionOperation[] VALUES = new InfusionOperation[] {ADD, REMOVE, INCREMENT, DECREMENT, MERGE_TAGS};
  private final int id;
  private final String name;

  InfusionOperation (int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static InfusionOperation byID (int id) {
    if (id >= 0 && id < VALUES.length) {
      return VALUES[id];
    } else {
      throw new IllegalArgumentException("No operation with value " + id);
    }
  }

  public int getID () {
    return this.id;
  }

  public String getName () {
    return name;
  }
}
