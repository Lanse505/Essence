package com.teamacronymcoders.essence.datagen.tags;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceEntityTags;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceFluidTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EssenceFluidTagProvider extends FluidTagsProvider {

    public EssenceFluidTagProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Essence.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        // Essence
        getOrCreateBuilder(EssenceFluidTags.FORGE_ESSENCE).add(EssenceObjectHolders.ESSENCE_FLUID.getSourceFluid());
        getOrCreateBuilder(EssenceFluidTags.MY_ESSENCE).add(EssenceObjectHolders.ESSENCE_FLUID.getSourceFluid());

        // Experience
        getOrCreateBuilder(EssenceFluidTags.FORGE_EXPERIENCE).add(EssenceObjectHolders.EXP_FLUID.getSourceFluid());
        getOrCreateBuilder(EssenceFluidTags.MY_EXPERIENCE).add(EssenceObjectHolders.EXP_FLUID.getSourceFluid());
    }

    @Override
    public String getName() {
        return "Essence Tags<Fluid>";
    }
}
