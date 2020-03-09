package com.teamacronymcoders.essence.blocks;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class EssenceCrystalOreBlock extends EssenceOreBlock {

    @Override
    protected int getExperience(Random random) {
        return MathHelper.nextInt(random, 1, 5);
    }
}
