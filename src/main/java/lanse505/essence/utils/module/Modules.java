package lanse505.essence.utils.module;

import com.hrznstudio.titanium.fluid.TitaniumFluidInstance;
import com.hrznstudio.titanium.module.Feature;
import com.hrznstudio.titanium.module.Module;
import net.minecraft.block.Block;

public class Modules {
    public static final Module.Builder CORE = Module.builder("core")
            .force()
            .description("Core-Content")
            .feature(
                    Feature.builder("items")
                            .force()
                            .description("Core-Items")
            )
            .feature(
                    Feature.builder("blocks")
                            .force()
                            .description("Core-Blocks")
                            .content(Block.class, ModuleObjects.ESSENCE_LEAVES)
                            .content(Block.class, ModuleObjects.ESSENCE_LOG)
                            .content(Block.class, ModuleObjects.ESSENCE_PLANKS)
                            .content(Block.class, ModuleObjects.ESSENCE_WOOD_SLAB)
            )
            .feature(
                    Feature.builder("misc")
                            .force()
                            .description("Core-Misc")
                            .content(TitaniumFluidInstance.class, ModuleObjects.ESSENCE)
            );
}
