package com.teamacronymcoders.essence.api.misc;

public interface IOreGenConfig {
    boolean getShouldGenerate();

    int getChanceToGenerate();

    int getMaxVeinSize();

    int getMaxHeight();

    int getTopOffset();

    int getBottomOffset();
}
