package com.teamacronymcoders.essence;

import com.hrznstudio.titanium.client.screen.container.BasicTileContainerScreen;
import com.hrznstudio.titanium.container.impl.BasicInventoryContainer;
import com.hrznstudio.titanium.event.handler.EventManager;
import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.network.CompoundSerializableDataHandler;
import com.hrznstudio.titanium.recipe.generator.BlockItemModelGeneratorProvider;
import com.hrznstudio.titanium.recipe.serializer.JSONSerializableDataHandler;
import com.hrznstudio.titanium.tab.AdvancedTitaniumTab;
import com.teamacronymcoders.essence.client.gui.PortableCrafterContainerScreen;
import com.teamacronymcoders.essence.client.render.PedestalTESR;
import com.teamacronymcoders.essence.container.PortableCrafterContainer;
import com.teamacronymcoders.essence.items.tools.EssenceShear;
import com.teamacronymcoders.essence.items.tools.misc.EssenceDispenseBehaviours;
import com.teamacronymcoders.essence.serializable.providers.EssenceRecipeProvider;
import com.teamacronymcoders.essence.serializable.providers.EssenceSerializableProvider;
import com.teamacronymcoders.essence.serializable.providers.EssenceTagProvider;
import com.teamacronymcoders.essence.serializable.providers.EssenceToolRecipeProvider;
import com.teamacronymcoders.essence.serializable.loot.FieryLootModifier;
import com.teamacronymcoders.essence.serializable.loot.condition.MatchModifier;
import com.teamacronymcoders.essence.serializable.recipe.InfusionTableSerializableRecipe;
import com.teamacronymcoders.essence.serializable.recipe.SerializableModifier;
import com.teamacronymcoders.essence.utils.EssenceModules;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import com.teamacronymcoders.essence.utils.EssenceRegistration;
import com.teamacronymcoders.essence.utils.EssenceSerializableObjectHandler;
import com.teamacronymcoders.essence.utils.config.EssenceGeneralConfig;
import com.teamacronymcoders.essence.utils.helpers.EssenceColorHelper;
import com.teamacronymcoders.essence.utils.helpers.EssenceModifierHelpers;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

@Mod("essence")
public class Essence extends ModuleController {

    public static final String MODID = "essence";
    public static final Random RANDOM = new Random();
    public static final Logger LOGGER = LogManager.getLogger("Essence");

    public static final AdvancedTitaniumTab CORE_TAB = new AdvancedTitaniumTab("essence_core", true) {
        @Override
        public boolean hasScrollbar() {
            return true;
        }
    };
    public static final AdvancedTitaniumTab TOOL_TAB = new AdvancedTitaniumTab("essence_tools", true) {
        @Override
        public boolean hasScrollbar() {
            return true;
        }
    };

    public Essence() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::setupCuriosIMC);
        EssenceRegistration.register(eventBus);
        setupEventManagers();
        if (EssenceGeneralConfig.enableDebugLogging) {
            LOGGER.info("Printing 10 new UUIDs to Log for Modifier-Use");
            for (int i = 0; i < 10; i++) {
                LOGGER.info(UUID.randomUUID());
            }
        }

    }

    @Override
    protected void initModules() {
        addModule(EssenceModules.CORE);
        addModule(EssenceModules.TOOLS);
    }

    @Override
    public void addDataProvider(GatherDataEvent event) {
        super.addDataProvider(event);
        event.getGenerator().addProvider(new EssenceTagProvider.Items(event.getGenerator()));
        event.getGenerator().addProvider(new EssenceTagProvider.Blocks(event.getGenerator()));
        event.getGenerator().addProvider(new EssenceRecipeProvider(event.getGenerator()));
        event.getGenerator().addProvider(new EssenceToolRecipeProvider(event.getGenerator()));
        event.getGenerator().addProvider(new EssenceSerializableProvider(event.getGenerator()));
        event.getGenerator().addProvider(new BlockItemModelGeneratorProvider(event.getGenerator(), MODID));
    }

    private void setupCuriosIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("head").setSize(1).setEnabled(true).setHidden(false));
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("necklace").setSize(1).setEnabled(true).setHidden(false));
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("hands").setSize(1).setEnabled(true).setHidden(false));
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("ring").setSize(2).setEnabled(true).setHidden(false));
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("backpack").setSize(1).setEnabled(true).setHidden(false));
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_ICON, () -> new Tuple<>("head", new ResourceLocation(CuriosAPI.MODID, "item/empty_head_slot")));
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_ICON, () -> new Tuple<>("necklace", new ResourceLocation(CuriosAPI.MODID, "item/empty_necklace_slot")));
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_ICON, () -> new Tuple<>("hands", new ResourceLocation(CuriosAPI.MODID, "item/empty_hands_slot")));
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_ICON, () -> new Tuple<>("ring", new ResourceLocation(CuriosAPI.MODID, "item/empty_ring_slot")));
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_ICON, () -> new Tuple<>("backpack", new ResourceLocation(MODID, "items/curios/empty_backpack_slot")));
    }

    private void setup(final FMLCommonSetupEvent event) {
        CORE_TAB.addIconStacks(new ItemStack(EssenceObjectHolders.ESSENCE_FLUID.getBucketFluid()), new ItemStack(EssenceObjectHolders.ESSENCE_WOOD_SAPLING), new ItemStack(EssenceObjectHolders.ESSENCE_WOOD_LEAVES), new ItemStack(EssenceObjectHolders.ESSENCE_WOOD_LOG), new ItemStack(EssenceObjectHolders.ESSENCE_WOOD_PLANKS));
        TOOL_TAB.addIconStacks(new ItemStack(EssenceObjectHolders.ESSENCE_AXE), new ItemStack(EssenceObjectHolders.ESSENCE_PICKAXE), new ItemStack(EssenceObjectHolders.ESSENCE_SHOVEL), new ItemStack(EssenceObjectHolders.ESSENCE_SWORD), new ItemStack(EssenceObjectHolders.ESSENCE_HOE), new ItemStack(EssenceObjectHolders.ESSENCE_OMNITOOL));
        JSONSerializableDataHandler.map(SerializableModifier.class, EssenceSerializableObjectHandler::writeSerializableModifier, EssenceSerializableObjectHandler::readSerializableModifier);
        JSONSerializableDataHandler.map(SerializableModifier[].class, EssenceSerializableObjectHandler::writeSerializableModifierArray, EssenceSerializableObjectHandler::readSerializableModifierArray);
        CompoundSerializableDataHandler.map(SerializableModifier.class, EssenceSerializableObjectHandler::readSerializableModifier, EssenceSerializableObjectHandler::writeSerializableModifier);
        CompoundSerializableDataHandler.map(SerializableModifier[].class, EssenceSerializableObjectHandler::readSerializableModifierArray, EssenceSerializableObjectHandler::writeSerializableModifierArray);
        LootConditionManager.registerCondition(new MatchModifier.Serializer());
        EssenceDispenseBehaviours.init();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(EssenceObjectHolders.ESSENCE_WOOD_LEAVES, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(EssenceObjectHolders.ESSENCE_WOOD_SAPLING, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(EssenceObjectHolders.ESSENCE_INFUSION_PEDESTAL, RenderType.getCutout());
        ClientRegistry.bindTileEntityRenderer(EssenceObjectHolders.ESSENCE_INFUSION_PEDESTAL.getTileEntityType(), PedestalTESR::new);
        ScreenManager.registerFactory(PortableCrafterContainer.type, PortableCrafterContainerScreen::new);
    }

    private void setupEventManagers() {
        EventManager.mod(RegistryEvent.Register.class)
            .filter(register -> register.getGenericType().equals(IRecipeSerializer.class))
            .process(register -> {
                register.getRegistry().registerAll(
                    InfusionTableSerializableRecipe.SERIALIZER
                );
            }).subscribe();
        EventManager.mod(RegistryEvent.Register.class)
            .filter(register -> register.getGenericType().equals(GlobalLootModifierSerializer.class))
            .process(register -> {
                register.getRegistry().registerAll(
                    new FieryLootModifier.Serializer().setRegistryName(new ResourceLocation(MODID, "fiery_modifier"))
                );
            }).subscribe();
        EventManager.mod(RegistryEvent.Register.class)
            .filter(register -> register.getGenericType().equals(ContainerType.class))
            .process(register -> {
                register.getRegistry().registerAll(
                    IForgeContainerType.create(PortableCrafterContainer::new).setRegistryName(new ResourceLocation(MODID, "portable_crafter"))
                );
            }).subscribe();
        EventManager.forge(RenderTooltipEvent.Color.class)
            .process(color -> {
                boolean isShear = color.getStack().getItem() instanceof EssenceShear;
                boolean hasRainbow = EssenceModifierHelpers.getModifiers(color.getStack()).containsKey(EssenceRegistration.RAINBOW_MODIFIER.get());
                if (isShear && hasRainbow) {
                    EssenceShear shear = (EssenceShear) color.getStack().getItem();
                    int rainbowVal = shear.getRainbowVal();
                    if (rainbowVal > 599) rainbowVal = 0;
                    Color colorVal = EssenceColorHelper.getColor(rainbowVal);
                    Color colorVal3 = EssenceColorHelper.getColor(rainbowVal + 60);
                    color.setBorderStart(colorVal.getRGB());
                    color.setBorderEnd(colorVal3.getRGB());
                    shear.setRainbowVal(rainbowVal + 1);
                }
            }).subscribe();
    }

}
