package com.teamacronymcoders.essence.block;

import java.util.Random;
import net.minecraft.util.math.MathHelper;

public class EssenceCrystalOreBlock extends EssenceOreBlock {

  public EssenceCrystalOreBlock (Properties properties) {
    super(properties);
  }

  @Override
  protected int getExperience (Random random) {
    return MathHelper.nextInt(random, 1, 5);
  }
}
