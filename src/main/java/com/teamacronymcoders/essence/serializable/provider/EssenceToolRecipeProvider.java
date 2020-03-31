package com.teamacronymcoders.essence.serializable.provider;

import com.hrznstudio.titanium.recipe.generator.TitaniumRecipeProvider;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class EssenceToolRecipeProvider extends TitaniumRecipeProvider {

    public EssenceToolRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public void register(Consumer<IFinishedRecipe> consumer) {
        // Axe Recipes
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_AXE).setName(new ResourceLocation(Essence.MODID, "essence_axe_basic"))
            .patternLine("ii ").patternLine("is ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_AXE_EMPOWERED).setName(new ResourceLocation(Essence.MODID, "essence_axe_empowered"))
            .patternLine("ii ").patternLine("is ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_EMPOWERED).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_AXE_EXALTED).setName(new ResourceLocation(Essence.MODID, "essence_axe_exalted"))
            .patternLine("ii ").patternLine("is ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_EXALTED).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_AXE_GODLY).setName(new ResourceLocation(Essence.MODID, "essence_axe_godly"))
            .patternLine("ii ").patternLine("is ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_GODLY).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);

        // Bow Recipes
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_BOW).setName(new ResourceLocation(Essence.MODID, "essence_bow_basic"))
            .patternLine(" ns").patternLine("n s").patternLine(" ns")
            .key('n', EssenceObjectHolders.ESSENCE_INFUSED_METAL).key('s', Tags.Items.STRING)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_BOW_EMPOWERED).setName(new ResourceLocation(Essence.MODID, "essence_bow_empowered"))
            .patternLine(" ns").patternLine("n s").patternLine(" ns")
            .key('n', EssenceObjectHolders.ESSENCE_INFUSED_METAL_EMPOWERED).key('s', Tags.Items.STRING)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_BOW_EXALTED).setName(new ResourceLocation(Essence.MODID, "essence_bow_exalted"))
            .patternLine(" ns").patternLine("n s").patternLine(" ns")
            .key('n', EssenceObjectHolders.ESSENCE_INFUSED_METAL_EXALTED).key('s', Tags.Items.STRING)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_BOW_GODLY).setName(new ResourceLocation(Essence.MODID, "essence_bow_godly"))
            .patternLine(" ns").patternLine("n s").patternLine(" ns")
            .key('n', EssenceObjectHolders.ESSENCE_INFUSED_METAL_GODLY).key('s', Tags.Items.STRING)
            .build(consumer);

        // Hoe Recipes
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_HOE).setName(new ResourceLocation(Essence.MODID, "essence_hoe_basic"))
            .patternLine(" ii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_HOE_EMPOWERED).setName(new ResourceLocation(Essence.MODID, "essence_hoe_empowered"))
            .patternLine(" ii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_EMPOWERED).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_HOE_EXALTED).setName(new ResourceLocation(Essence.MODID, "essence_hoe_exalted"))
            .patternLine(" ii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_EXALTED).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_HOE_GODLY).setName(new ResourceLocation(Essence.MODID, "essence_hoe_godly"))
            .patternLine(" ii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_GODLY).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);

        // Omnitool Recipes
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_OMNITOOL).setName(new ResourceLocation(Essence.MODID, "essence_omnitool_basic"))
            .patternLine("asp").patternLine(" t ").patternLine(" t ")
            .key('a', EssenceObjectHolders.ESSENCE_AXE).key('s', EssenceObjectHolders.ESSENCE_SHOVEL).key('p', EssenceObjectHolders.ESSENCE_PICKAXE)
            .key('t', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_OMNITOOL_EMPOWERED).setName(new ResourceLocation(Essence.MODID, "essence_omnitool_empowered"))
            .patternLine("asp").patternLine(" t ").patternLine(" t ")
            .key('a', EssenceObjectHolders.ESSENCE_AXE_EMPOWERED).key('s', EssenceObjectHolders.ESSENCE_SHOVEL_EMPOWERED).key('p', EssenceObjectHolders.ESSENCE_PICKAXE_EMPOWERED)
            .key('t', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_OMNITOOL_EXALTED).setName(new ResourceLocation(Essence.MODID, "essence_omnitool_exalted"))
            .patternLine("asp").patternLine(" t ").patternLine(" t ")
            .key('a', EssenceObjectHolders.ESSENCE_AXE_EXALTED).key('s', EssenceObjectHolders.ESSENCE_SHOVEL_EXALTED).key('p', EssenceObjectHolders.ESSENCE_PICKAXE_EXALTED)
            .key('t', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_OMNITOOL_GODLY).setName(new ResourceLocation(Essence.MODID, "essence_omnitool_godly"))
            .patternLine("asp").patternLine(" t ").patternLine(" t ")
            .key('a', EssenceObjectHolders.ESSENCE_AXE_GODLY).key('s', EssenceObjectHolders.ESSENCE_SHOVEL_GODLY).key('p', EssenceObjectHolders.ESSENCE_PICKAXE_GODLY)
            .key('t', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);

        // Pickaxe Recipes
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_PICKAXE).setName(new ResourceLocation(Essence.MODID, "essence_pickaxe_basic"))
            .patternLine("iii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_PICKAXE_EMPOWERED).setName(new ResourceLocation(Essence.MODID, "essence_pickaxe_empowered"))
            .patternLine("iii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_EMPOWERED).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_PICKAXE_EXALTED).setName(new ResourceLocation(Essence.MODID, "essence_pickaxe_exalted"))
            .patternLine("iii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_EXALTED).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_PICKAXE_GODLY).setName(new ResourceLocation(Essence.MODID, "essence_pickaxe_godly"))
            .patternLine("iii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_GODLY).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);

        // Shear Recipes
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SHEAR).setName(new ResourceLocation(Essence.MODID, "essence_shear_basic"))
            .patternLine(" i").patternLine("i ").key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SHEAR_EMPOWERED).setName(new ResourceLocation(Essence.MODID, "essence_shear_empowered"))
            .patternLine("iii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_EMPOWERED).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SHEAR_EXALTED).setName(new ResourceLocation(Essence.MODID, "essence_shear_exalted"))
            .patternLine("iii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_EXALTED).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SHEAR_GODLY).setName(new ResourceLocation(Essence.MODID, "essence_shear_godly"))
            .patternLine("iii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_GODLY).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);

        // Shovel Recipes
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SHOVEL).setName(new ResourceLocation(Essence.MODID, "essence_shovel_basic"))
            .patternLine(" i ").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SHOVEL_EMPOWERED).setName(new ResourceLocation(Essence.MODID, "essence_shovel_empowered"))
            .patternLine(" i ").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_EMPOWERED).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SHOVEL_EXALTED).setName(new ResourceLocation(Essence.MODID, "essence_shovel_exalted"))
            .patternLine(" i ").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_EXALTED).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SHOVEL_GODLY).setName(new ResourceLocation(Essence.MODID, "essence_shovel_godly"))
            .patternLine(" i ").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_GODLY).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);

        // Sword Recipes
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SWORD).setName(new ResourceLocation(Essence.MODID, "essence_sword_basic"))
            .patternLine(" i ").patternLine(" i ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SWORD_EMPOWERED).setName(new ResourceLocation(Essence.MODID, "essence_sword_empowered"))
            .patternLine(" i ").patternLine(" i ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_EMPOWERED).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SWORD_EXALTED).setName(new ResourceLocation(Essence.MODID, "essence_sword_exalted"))
            .patternLine(" i ").patternLine(" i ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_EXALTED).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SWORD_GODLY).setName(new ResourceLocation(Essence.MODID, "essence_sword_godly"))
            .patternLine(" i ").patternLine(" i ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_GODLY).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
    }
}
