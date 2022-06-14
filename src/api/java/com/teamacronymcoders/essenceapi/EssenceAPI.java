package com.teamacronymcoders.essenceapi;

import net.minecraft.util.RandomSource;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("essence_api")
public class EssenceAPI {
  public static final String MOD_ID = "essence";
  public static final Logger LOGGER = LoggerFactory.getLogger("Essence-API");
  public static final RandomSource RANDOM = RandomSource.create();
}
