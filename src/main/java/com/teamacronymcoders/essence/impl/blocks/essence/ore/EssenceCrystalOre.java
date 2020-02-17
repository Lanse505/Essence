package com.teamacronymcoders.essence.impl.blocks.essence.ore;

import com.teamacronymcoders.essence.utils.EssenceReferences;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class EssenceCrystalOre extends EssenceOre {

    public EssenceCrystalOre() {
        super(new ResourceLocation(EssenceReferences.MODID, "essence_crystal_ore"));
    }

    @Override
    protected int getExperience(Random random) {
        return MathHelper.nextInt(random, 1, 5);
    }
}
