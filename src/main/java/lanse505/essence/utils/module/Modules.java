package lanse505.essence.utils.module;

import com.hrznstudio.titanium.fluid.TitaniumFluidInstance;
import com.hrznstudio.titanium.module.Feature;
import com.hrznstudio.titanium.module.Module;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class Modules {
    public static final Module.Builder CORE = Module.builder("core")
            .force()
            .description("Core-Content")
            .feature(
                    Feature.builder("items")
                            .force()
                            .description("Core-Items")
                            .content(Item.class, ModuleObjects.ESSENCE_INFUSED_CRYSTAL)
                            .content(Item.class, ModuleObjects.ESSENCE_INFUSED_METAL_NUGGET)
                            .content(Item.class, ModuleObjects.ESSENCE_INFUSED_METAL)
                            .content(Item.class, ModuleObjects.ESSENCE_INFUSED_STICK)
            )
            .feature(
                    Feature.builder("blocks")
                            .force()
                            .description("Core-Blocks")
                            .content(Block.class, ModuleObjects.ESSENCE_INFUSED_BLOCK)
                            .content(Block.class, ModuleObjects.ESSENCE_CRYSTAL_ORE)
                            .content(Block.class, ModuleObjects.ESSENCE_WOOD_LEAVES)
                            .content(Block.class, ModuleObjects.ESSENCE_WOOD_LOG)
                            .content(Block.class, ModuleObjects.ESSENCE_ORE)
                            .content(Block.class, ModuleObjects.ESSENCE_WOOD_PLANKS)
                            .content(Block.class, ModuleObjects.ESSENCE_WOOD_SAPLING)
                            .content(Block.class, ModuleObjects.ESSENCE_WOOD_SLAB)
            )
            .feature(
                    Feature.builder("misc")
                            .force()
                            .description("Core-Misc")
                            .content(TitaniumFluidInstance.class, ModuleObjects.ESSENCE_FLUID)
            );

    public static final Module.Builder TOOLS = Module.builder("tools")
            .force()
            .description("Tool-Content")
            .feature(
                    Feature.builder("tools")
                            .force()
                            .description("Tools")
                            .content(Item.class, ModuleObjects.ESSENCE_AXE)
                            .content(Item.class, ModuleObjects.ESSENCE_PICKAXE)
                            .content(Item.class, ModuleObjects.ESSENCE_SHOVEL)
                            .content(Item.class, ModuleObjects.ESSENCE_OMNITOOL)
            );
}
