package com.teamacronymcoders.essence.datagen.recipe;

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
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_AXE_SUPREME).setName(new ResourceLocation(Essence.MODID, "essence_axe_supreme"))
            .patternLine("ii ").patternLine("is ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_SUPREME).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_AXE_DIVINE).setName(new ResourceLocation(Essence.MODID, "essence_axe_divine"))
            .patternLine("ii ").patternLine("is ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_DIVINE).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
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
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_BOW_SUPREME).setName(new ResourceLocation(Essence.MODID, "essence_bow_supreme"))
            .patternLine(" ns").patternLine("n s").patternLine(" ns")
            .key('n', EssenceObjectHolders.ESSENCE_INFUSED_METAL_SUPREME).key('s', Tags.Items.STRING)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_BOW_DIVINE).setName(new ResourceLocation(Essence.MODID, "essence_bow_divine"))
            .patternLine(" ns").patternLine("n s").patternLine(" ns")
            .key('n', EssenceObjectHolders.ESSENCE_INFUSED_METAL_DIVINE).key('s', Tags.Items.STRING)
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
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_HOE_SUPREME).setName(new ResourceLocation(Essence.MODID, "essence_hoe_supreme"))
            .patternLine(" ii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_SUPREME).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_HOE_DIVINE).setName(new ResourceLocation(Essence.MODID, "essence_hoe_divine"))
            .patternLine(" ii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_DIVINE).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
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
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_OMNITOOL_SUPREME).setName(new ResourceLocation(Essence.MODID, "essence_omnitool_supreme"))
            .patternLine("asp").patternLine(" t ").patternLine(" t ")
            .key('a', EssenceObjectHolders.ESSENCE_AXE_SUPREME).key('s', EssenceObjectHolders.ESSENCE_SHOVEL_SUPREME).key('p', EssenceObjectHolders.ESSENCE_PICKAXE_SUPREME)
            .key('t', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_OMNITOOL_DIVINE).setName(new ResourceLocation(Essence.MODID, "essence_omnitool_divine"))
            .patternLine("asp").patternLine(" t ").patternLine(" t ")
            .key('a', EssenceObjectHolders.ESSENCE_AXE_DIVINE).key('s', EssenceObjectHolders.ESSENCE_SHOVEL_DIVINE).key('p', EssenceObjectHolders.ESSENCE_PICKAXE_DIVINE)
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
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_PICKAXE_SUPREME).setName(new ResourceLocation(Essence.MODID, "essence_pickaxe_supreme"))
            .patternLine("iii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_SUPREME).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_PICKAXE_DIVINE).setName(new ResourceLocation(Essence.MODID, "essence_pickaxe_divine"))
            .patternLine("iii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_DIVINE).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);

        // Shear Recipes
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SHEAR).setName(new ResourceLocation(Essence.MODID, "essence_shear_basic"))
            .patternLine(" i").patternLine("i ").key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SHEAR_EMPOWERED).setName(new ResourceLocation(Essence.MODID, "essence_shear_empowered"))
            .patternLine("iii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_EMPOWERED).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SHEAR_SUPREME).setName(new ResourceLocation(Essence.MODID, "essence_shear_supreme"))
            .patternLine("iii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_SUPREME).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SHEAR_DIVINE).setName(new ResourceLocation(Essence.MODID, "essence_shear_divine"))
            .patternLine("iii").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_DIVINE).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
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
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SHOVEL_SUPREME).setName(new ResourceLocation(Essence.MODID, "essence_shovel_supreme"))
            .patternLine(" i ").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_SUPREME).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SHOVEL_DIVINE).setName(new ResourceLocation(Essence.MODID, "essence_shovel_divine"))
            .patternLine(" i ").patternLine(" s ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_DIVINE).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
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
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SWORD_SUPREME).setName(new ResourceLocation(Essence.MODID, "essence_sword_supreme"))
            .patternLine(" i ").patternLine(" i ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_SUPREME).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(EssenceObjectHolders.ESSENCE_SWORD_DIVINE).setName(new ResourceLocation(Essence.MODID, "essence_sword_divine"))
            .patternLine(" i ").patternLine(" i ").patternLine(" s ")
            .key('i', EssenceObjectHolders.ESSENCE_INFUSED_METAL_DIVINE).key('s', EssenceObjectHolders.ESSENCE_INFUSED_STICK)
            .build(consumer);
    }

    @Override
    public String getName() {
        return "Essence Recipes - Tools";
    }
}
