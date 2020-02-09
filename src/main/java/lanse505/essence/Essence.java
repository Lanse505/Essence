package lanse505.essence;

import com.hrznstudio.titanium.module.ModuleController;
import lanse505.essence.utils.EssenceRegistration;
import lanse505.essence.utils.module.ModuleObjects;
import lanse505.essence.utils.module.Modules;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("essence")
public class Essence extends ModuleController {

    public static final Logger LOGGER = LogManager.getLogger("Essence");

    public Essence() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::loadComplete);
        EssenceRegistration.register(eventBus);
    }

    @Override
    protected void initModules() {
        addModule(Modules.CORE);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(ModuleObjects.ESSENCE_LEAVES, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModuleObjects.ESSENCE_SAPLING, RenderType.getCutout());
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
    }
}
