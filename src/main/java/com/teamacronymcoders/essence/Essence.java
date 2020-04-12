package com.teamacronymcoders.essence;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.network.CompoundSerializableDataHandler;
import com.hrznstudio.titanium.recipe.generator.BlockItemModelGeneratorProvider;
import com.hrznstudio.titanium.recipe.serializer.JSONSerializableDataHandler;
import com.hrznstudio.titanium.tab.AdvancedTitaniumTab;
import com.teamacronymcoders.essence.api.capabilities.NBTCapabilityStorage;
import com.teamacronymcoders.essence.api.knowledge.IKnowledgeHolder;
import com.teamacronymcoders.essence.api.knowledge.KnowledgeHolder;
import com.teamacronymcoders.essence.api.recipe.infusion.SerializableModifier;
import com.teamacronymcoders.essence.capability.block.BlockModifierHolder;
import com.teamacronymcoders.essence.capability.itemstack.ItemStackModifierHolder;
import com.teamacronymcoders.essence.client.EssenceClientProxy;
import com.teamacronymcoders.essence.client.render.tesr.InfusionPedestalTESR;
import com.teamacronymcoders.essence.client.render.tesr.InfusionTableTESR;
import com.teamacronymcoders.essence.datagen.*;
import com.teamacronymcoders.essence.datagen.advancement.CoreAdvancementProvider;
import com.teamacronymcoders.essence.datagen.advancement.KnowledgeAdvancementProvider;
import com.teamacronymcoders.essence.entity.render.*;
import com.teamacronymcoders.essence.item.tool.EssenceShear;
import com.teamacronymcoders.essence.item.wrench.EssenceWrench;
import com.teamacronymcoders.essence.item.wrench.WrenchModeEnum;
import com.teamacronymcoders.essence.serializable.advancement.criterion.EssenceAdvancements;
import com.teamacronymcoders.essence.serializable.loot.condition.MatchModifier;
import com.teamacronymcoders.essence.util.*;
import com.teamacronymcoders.essence.command.argument.EssenceHandArgumentType;
import com.teamacronymcoders.essence.command.argument.EssenceKnowledgeArgumentType;
import com.teamacronymcoders.essence.command.argument.EssenceModifierArgumentType;
import com.teamacronymcoders.essence.command.argument.extendable.EssenceEnumArgumentType;
import com.teamacronymcoders.essence.util.config.EssenceGeneralConfig;
import com.teamacronymcoders.essence.util.config.EssenceModifierConfig;
import com.teamacronymcoders.essence.util.config.EssenceWorldGenConfig;
import com.teamacronymcoders.essence.util.helper.EssenceColorHelper;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.util.helper.EssenceUtilHelper;
import com.teamacronymcoders.essence.util.network.PacketHandler;
import com.teamacronymcoders.essence.util.network.message.PacketItemStack;
import com.teamacronymcoders.essence.util.registration.*;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.Collections;
import java.util.Random;
import java.util.function.Supplier;

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

    public static Essence instance;
    public static EssenceCommonProxy proxy = DistExecutor.runForDist(Essence::getClientProxy, () -> EssenceCommonProxy::new);
    public static PacketHandler handler = new PacketHandler();
    public final String versionNumber;

    @OnlyIn(Dist.CLIENT)
    private static Supplier<EssenceCommonProxy> getClientProxy() {
        return EssenceClientProxy::new;
    }

    public Essence() {
        instance = this;
        versionNumber = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();
        handler.init();

        JSONSerializableDataHandler.map(SerializableModifier.class, EssenceSerializableObjectHandler::writeSerializableModifier, EssenceSerializableObjectHandler::readSerializableModifier);
        JSONSerializableDataHandler.map(SerializableModifier[].class, EssenceSerializableObjectHandler::writeSerializableModifierArray, EssenceSerializableObjectHandler::readSerializableModifierArray);
        JSONSerializableDataHandler.map(BlockState.class, EssenceSerializableObjectHandler::writeBlockState, EssenceSerializableObjectHandler::readBlockState);
        CompoundSerializableDataHandler.map(SerializableModifier.class, EssenceSerializableObjectHandler::readSerializableModifier, EssenceSerializableObjectHandler::writeSerializableModifier);
        CompoundSerializableDataHandler.map(SerializableModifier[].class, EssenceSerializableObjectHandler::readSerializableModifierArray, EssenceSerializableObjectHandler::writeSerializableModifierArray);
        CompoundSerializableDataHandler.map(BlockState.class, EssenceSerializableObjectHandler::readBlockState, EssenceSerializableObjectHandler::writeBlockState);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EssenceGeneralConfig.initialize(), "essence/general.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EssenceWorldGenConfig.initialize(), "essence/worldgen.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EssenceModifierConfig.initialize(), "essence/modifiers.toml");

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::setupCuriosIMC);

        EssenceEntityRegistration.register(eventBus);
        EssenceFeatureRegistration.register(eventBus);
        EssenceKnowledgeRegistration.register(eventBus);
        EssenceModifierRegistration.register(eventBus);
        EssenceSoundRegistration.register(eventBus);

        EssenceAdvancements.setup();
        EssenceEventHandlers.setup();

        setupCreativeTabIcons();
    }

    @Override
    protected void initModules() {
        addModule(EssenceModules.CORE);
        addModule(EssenceModules.TOOLS);
    }

    @Override
    public void addDataProvider(GatherDataEvent event) {
        super.addDataProvider(event);
        EssenceTagProvider.addTagProviders(event.getGenerator());
        EssenceRecipeProvider.addRecipeProviders(event.getGenerator());
        event.getGenerator().addProvider(new BlockItemModelGeneratorProvider(event.getGenerator(), MODID));
        event.getGenerator().addProvider(new EssenceLootTableProvider(event.getGenerator()));
        event.getGenerator().addProvider(new EssenceAdvancementProvider(event.getGenerator()));
        event.getGenerator().addProvider(new CoreAdvancementProvider(event.getGenerator()));
        event.getGenerator().addProvider(new KnowledgeAdvancementProvider(event.getGenerator()));
        EssenceLangProvider.registerLangProviders(event.getGenerator());
    }

    private void setupCuriosIMC(final InterModEnqueueEvent event) {
        //InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("backpack").setSize(1).setEnabled(true).setHidden(false));
        //InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_ICON, () -> new Tuple<>("backpack", new ResourceLocation(MODID, "items/curios/empty_backpack_slot")));
    }

    private void setup(final FMLCommonSetupEvent event) {
        ArgumentTypes.register("essence_hand", EssenceEnumArgumentType.class, new ArgumentSerializer<>(EssenceHandArgumentType::new));
        ArgumentTypes.register("essence_modifier", EssenceModifierArgumentType.class, new ArgumentSerializer<>(EssenceModifierArgumentType::new));
        ArgumentTypes.register("essence_knowledge", EssenceKnowledgeArgumentType.class, new ArgumentSerializer<>(EssenceKnowledgeArgumentType::new));
        CapabilityManager.INSTANCE.register(IKnowledgeHolder.class, NBTCapabilityStorage.create(CompoundNBT.class), KnowledgeHolder::new);
        CapabilityManager.INSTANCE.register(ItemStackModifierHolder.class, NBTCapabilityStorage.create(ListNBT.class), ItemStackModifierHolder::new);
        CapabilityManager.INSTANCE.register(BlockModifierHolder.class, NBTCapabilityStorage.create(ListNBT.class), BlockModifierHolder::new);

        LootConditionManager.registerCondition(new MatchModifier.Serializer());

        // TODO: Rework Worldgen Configs...
        //EssenceGeneration.addFeaturesToWorldGen();

        // TODO: Fix Dispenser Behaviour
        //EssenceDispenseBehaviours.init();
    }

    @SuppressWarnings("unchecked")
    private void clientSetup(final FMLClientSetupEvent event) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        RenderTypeLookup.setRenderLayer(EssenceObjectHolders.ESSENCE_WOOD_LEAVES, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(EssenceObjectHolders.ESSENCE_WOOD_SAPLING, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(EssenceObjectHolders.INFUSION_TABLE, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(EssenceObjectHolders.INFUSION_PEDESTAL, RenderType.getTranslucent());
        ClientRegistry.bindTileEntityRenderer(EssenceObjectHolders.INFUSION_TABLE.getTileEntityType(), InfusionTableTESR::new);
        ClientRegistry.bindTileEntityRenderer(EssenceObjectHolders.INFUSION_PEDESTAL.getTileEntityType(), InfusionPedestalTESR::new);
        RenderingRegistry.registerEntityRenderingHandler(EssenceEntityRegistration.GLUE_BALL.get(), manager -> new SpriteRenderer<>(manager, renderer, 0.75F, true));
        RenderingRegistry.registerEntityRenderingHandler(EssenceEntityRegistration.SHEARED_COW.get(), ShearedCowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EssenceEntityRegistration.SHEARED_PIG.get(), ShearedPigRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EssenceEntityRegistration.SHEARED_CHICKEN.get(), ShearedChickenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EssenceEntityRegistration.SHEARED_CREEPER.get(), ShearedCreeperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EssenceEntityRegistration.SHEARED_GHAST.get(), ShearedGhastRenderer::new);
        // TODO: Reimplement once I get this working
        //ScreenManager.registerFactory(PortableCrafterContainer.type, PortableCrafterContainerScreen::new);
    }

    private void setupCreativeTabIcons() {
        CORE_TAB.addIconStacks(
            new ItemStack(EssenceObjectHolders.ESSENCE_FLUID.getBucketFluid()),
            new ItemStack(EssenceObjectHolders.ESSENCE_WOOD_SAPLING),
            new ItemStack(EssenceObjectHolders.ESSENCE_WOOD_LEAVES),
            new ItemStack(EssenceObjectHolders.ESSENCE_WOOD_LOG),
            new ItemStack(EssenceObjectHolders.ESSENCE_WOOD_PLANKS)
        );

        TOOL_TAB.addIconStacks(
            new ItemStack(EssenceObjectHolders.ESSENCE_AXE),
            new ItemStack(EssenceObjectHolders.ESSENCE_PICKAXE),
            new ItemStack(EssenceObjectHolders.ESSENCE_SHOVEL),
            new ItemStack(EssenceObjectHolders.ESSENCE_SWORD),
            new ItemStack(EssenceObjectHolders.ESSENCE_HOE),
            new ItemStack(EssenceObjectHolders.ESSENCE_OMNITOOL));
    }

    @OnlyIn(Dist.CLIENT)
    private void setupClientEventHandlers() {
        // Atlas Texture Handler
        EventManager.mod(TextureStitchEvent.Pre.class)
            .filter(stitch -> stitch.getMap().getTextureLocation().equals(PlayerContainer.LOCATION_BLOCKS_TEXTURE))
            .process(stitch -> {
              stitch.addSprite(InfusionTableTESR.BOOK_TEXTURE);
            }).subscribe();
        // Rainbow Tooltip Handler
        EventManager.forge(RenderTooltipEvent.Color.class)
            .process(color -> {
                boolean isShear = color.getStack().getItem() instanceof EssenceShear;
                boolean hasRainbow = EssenceItemstackModifierHelpers.hasRainbowModifier(color.getStack());
                if (isShear && hasRainbow) {
                    EssenceShear shear = (EssenceShear) color.getStack().getItem();
                    int rainbowVal = shear.getRainbowVal();
                    if (rainbowVal > 599) {
                        rainbowVal = 0;
                    }
                    Color colorVal = EssenceColorHelper.getColor(rainbowVal);
                    Color colorVal3 = EssenceColorHelper.getColor(rainbowVal + 60);
                    color.setBorderStart(colorVal.getRGB());
                    color.setBorderEnd(colorVal3.getRGB());
                    shear.setRainbowVal(rainbowVal + 1);
                }
            }).subscribe();

        // Wrench-Mode Handler
        EventManager.forge(InputEvent.MouseScrollEvent.class)
            .process(scroll -> {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.player != null && minecraft.player.isShiftKeyDown()) {
                    ItemStack stack = minecraft.player.getHeldItemMainhand();
                    if (stack.getItem() instanceof EssenceWrench) {
                        double scrolling = scroll.getScrollDelta();
                        if (scrolling != 0) {
                            EssenceWrench wrench = (EssenceWrench) stack.getItem();
                            WrenchModeEnum mode = wrench.getMode();
                            WrenchModeEnum newMode = WrenchModeEnum.cycleMode(mode.ordinal());
                            wrench.setMode(newMode);
                            minecraft.player.sendStatusMessage(new TranslationTextComponent("wrench.mode.tooltip").appendText(": ").appendSibling(new TranslationTextComponent(newMode.getLocaleName())), true);
                            handler.sendToServer(new PacketItemStack(Hand.MAIN_HAND, Collections.singletonList(newMode)));
                            scroll.setCanceled(true);
                        }
                    }
                }
            }).subscribe();
    }
}
