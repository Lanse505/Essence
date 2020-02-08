package lanse505.essence;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.hrznstudio.titanium.module.ModuleController;
import lanse505.essence.core.blocks.essence.EssenceLeavesBlock;
import lanse505.essence.core.generation.WorldGenHandler;
import lanse505.essence.core.generation.trees.feature.EssenceTreeFeature;
import lanse505.essence.utils.module.ModuleObjects;
import lanse505.essence.utils.module.Modules;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod("essence")
public class Essence extends ModuleController {
    public static final Logger LOGGER = LogManager.getLogger();

    public Essence() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
        MinecraftForge.EVENT_BUS.register(this);

        EventManager.mod(RegistryEvent.Register.class).filter(register -> register.getGenericType().equals(Feature.class))
                .process(register -> {
                    register.getRegistry().register(new EssenceTreeFeature());
                }).subscribe();
    }

    @Override
    protected void initModules() {
        addModule(Modules.CORE);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void clientSetup(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(ModuleObjects.ESSENCE_LEAVES, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModuleObjects.ESSENCE_SAPLING, RenderType.cutout());
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        WorldGenHandler.loadComplete();
    }
}
