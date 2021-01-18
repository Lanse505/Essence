package com.teamacronymcoders.essence.util;

import com.hrznstudio.titanium.fluid.TitaniumFluidInstance;
import com.hrznstudio.titanium.module.Feature;
import com.hrznstudio.titanium.module.Module;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.block.EssenceBlock;
import com.teamacronymcoders.essence.block.EssenceBrickBlock;
import com.teamacronymcoders.essence.block.EssenceCrystalOreBlock;
import com.teamacronymcoders.essence.block.EssenceOreBlock;
import com.teamacronymcoders.essence.block.infusion.InfusionPedestalBlock;
import com.teamacronymcoders.essence.block.infusion.InfusionTableBlock;
import com.teamacronymcoders.essence.block.wood.*;
import com.teamacronymcoders.essence.item.misc.GlueBallItem;
import com.teamacronymcoders.essence.item.tome.TomeOfKnowledgeItem;
import com.teamacronymcoders.essence.item.tome.experience.TomeOfExperienceItem;
import com.teamacronymcoders.essence.item.tool.*;
import com.teamacronymcoders.essence.item.tool.misc.PortableCrafterItem;
import com.teamacronymcoders.essence.item.wrench.EssenceWrench;
import com.teamacronymcoders.essence.item.wrench.SerializedEntityItem;
import com.teamacronymcoders.essence.util.tier.EssenceItemTiers;
import com.teamacronymcoders.essence.util.tier.EssenceToolTiers;
import net.minecraft.block.Block;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class EssenceModules {
  public static final Module.Builder CORE = Module.builder("core")
          .force()
          .description("Core-Content")
          .feature(
                  Feature.builder("misc")
                          .force()
                          .description("Core-Misc")
                          .content(TitaniumFluidInstance.class, EssenceObjectHolders.ESSENCE_FLUID)
                          .content(TitaniumFluidInstance.class, EssenceObjectHolders.EXP_FLUID)
          );
}
