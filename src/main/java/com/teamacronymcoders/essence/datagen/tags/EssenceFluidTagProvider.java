package com.teamacronymcoders.essence.datagen.tags;

import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceEntityTags;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceFluidTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;

public class EssenceFluidTagProvider extends FluidTagsProvider {

    public EssenceFluidTagProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerTags() {
        // Essence
        getBuilder(EssenceFluidTags.FORGE_ESSENCE).add(EssenceObjectHolders.ESSENCE_FLUID.getSourceFluid());
        getBuilder(EssenceFluidTags.MY_ESSENCE).add(EssenceObjectHolders.ESSENCE_FLUID.getSourceFluid());

        // Experience
        getBuilder(EssenceFluidTags.FORGE_EXPERIENCE).add(EssenceObjectHolders.EXP_FLUID.getSourceFluid());
        getBuilder(EssenceFluidTags.MY_EXPERIENCE).add(EssenceObjectHolders.EXP_FLUID.getSourceFluid());
    }

    @Override
    public String getName() {
        return "Essence Tags<Fluid>";
    }
}
