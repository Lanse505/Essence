package com.teamacronymcoders.essence.util;

import com.hrznstudio.titanium.fluid.TitaniumFluidInstance;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.block.worker.EssenceFurnaceWorkerBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.registries.ObjectHolder;

public class EssenceObjectHolders {

  @ObjectHolder("essence:furnace_worker")
  public static EssenceFurnaceWorkerBlock FURNACE_WORKER_BLOCK;

  public static TitaniumFluidInstance ESSENCE_FLUID = new TitaniumFluidInstance(Essence.MOD_ID, "essence", FluidAttributes.builder(new ResourceLocation(Essence.MOD_ID, "blocks/fluids/essence_still"), new ResourceLocation(Essence.MOD_ID, "blocks/fluids/essence_flowing")), true, Essence.CORE_TAB);
  public static TitaniumFluidInstance EXP_FLUID = new TitaniumFluidInstance(Essence.MOD_ID, "experience", FluidAttributes.builder(new ResourceLocation(Essence.MOD_ID, "blocks/fluids/experience_still"), new ResourceLocation(Essence.MOD_ID, "blocks/fluids/experience_flowing")), true, Essence.CORE_TAB);

}
