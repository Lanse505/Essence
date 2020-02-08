package lanse505.essence.utils.module;

import com.hrznstudio.titanium.fluid.TitaniumFluidInstance;
import lanse505.essence.core.blocks.essence.EssenceLeavesBlock;
import lanse505.essence.core.blocks.essence.EssenceLogBlock;
import lanse505.essence.core.blocks.essence.EssencePlankBlock;
import lanse505.essence.core.blocks.essence.EssenceSlabBlock;
import lanse505.essence.core.blocks.infuser.InfuserBlock;
import lanse505.essence.core.items.essence.EssenceSapling;
import lanse505.essence.core.items.essence.EssenceStickItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

import static lanse505.essence.utils.EssenceReferences.MODID;

public class ModuleObjects {

    public static EssenceSapling ESSENCE_SAPLING = new EssenceSapling();
    public static EssenceStickItem ESSENCE_STICK = new EssenceStickItem();

    public static EssenceLeavesBlock ESSENCE_LEAVES = new EssenceLeavesBlock();
    public static EssenceLogBlock ESSENCE_LOG = new EssenceLogBlock();
    public static EssencePlankBlock ESSENCE_PLANKS = new EssencePlankBlock();
    public static EssenceSlabBlock ESSENCE_WOOD_SLAB = new EssenceSlabBlock();

    public static InfuserBlock INFUSION_TABLE = new InfuserBlock();

    public static TitaniumFluidInstance ESSENCE = new TitaniumFluidInstance(MODID, "essence", FluidAttributes.builder(new ResourceLocation(MODID, "blocks/fluids/essence_still"), new ResourceLocation(MODID, "blocks/fluids/essence_flowing")), true, null);

}
