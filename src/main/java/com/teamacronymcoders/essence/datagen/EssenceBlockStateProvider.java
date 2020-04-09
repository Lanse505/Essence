package com.teamacronymcoders.essence.datagen;


import com.teamacronymcoders.essence.Essence;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;

import java.util.Map;
import java.util.function.Supplier;

public class EssenceBlockStateProvider extends BlockStateProvider {

    private final ExistingFileHelper helper;

    public EssenceBlockStateProvider(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, Essence.MODID, helper);
        this.helper = helper;
    }

    @Override
    protected void registerStatesAndModels() {

    }

    public ExistingFileHelper getHelper() {
        return helper;
    }

    public void getBlockStateJsonForAllVariants(Block block, ConfiguredModel model) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.forAllStates(blockState -> new ConfiguredModel[]{model});
    }

    private ResourceLocation customTexture(ResourceLocation customTextureLocation) {
        return modLoc(customTextureLocation.toString());
    }

    private ResourceLocation blockTexture(Supplier<? extends Block> block) {
        ResourceLocation base = block.get().getRegistryName();
        return modLoc("block/" + base.getPath());
    }
}
