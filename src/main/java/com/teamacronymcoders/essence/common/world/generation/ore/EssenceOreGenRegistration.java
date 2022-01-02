package com.teamacronymcoders.essence.common.world.generation.ore;

public class EssenceOreGenRegistration {

    public static EssenceConfiguredFeatures configured;
    public static EssencePlacedFeatures placed;

    public static void registerWorldGen() {
        configured = new EssenceConfiguredFeatures();
        placed = new EssencePlacedFeatures(configured);
    }
}
