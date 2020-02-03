package lanse505.essence;

import com.hrznstudio.titanium.module.ModuleController;
import lanse505.essence.core.generation.WorldGenHandler;
import lanse505.essence.utils.module.Modules;
import net.minecraftforge.common.MinecraftForge;
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
    private static final Logger LOGGER = LogManager.getLogger();

    public Essence() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    protected void initModules() {
        addModule(Modules.CORE);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void clientSetup(final FMLClientSetupEvent event) {

    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        WorldGenHandler.loadComplete();
    }
}
