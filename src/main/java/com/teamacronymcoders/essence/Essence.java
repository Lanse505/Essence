package com.teamacronymcoders.essence;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.network.CompoundSerializableDataHandler;
import com.hrznstudio.titanium.recipe.generator.BlockItemModelGeneratorProvider;
import com.hrznstudio.titanium.recipe.serializer.JSONSerializableDataHandler;
import com.hrznstudio.titanium.tab.AdvancedTitaniumTab;
import com.teamacronymcoders.essence.api.capabilities.EssenceCapabilities;
import com.teamacronymcoders.essence.api.capabilities.NBTCapabilityStorage;
import com.teamacronymcoders.essence.api.knowledge.IKnowledgeHolder;
import com.teamacronymcoders.essence.api.knowledge.KnowledgeHolder;
import com.teamacronymcoders.essence.api.knowledge.KnowledgeProvider;
import com.teamacronymcoders.essence.api.modified.IModified;
import com.teamacronymcoders.essence.blocks.infusion.tile.InfusionPedestalTile;
import com.teamacronymcoders.essence.capabilities.block.BlockModifierHolder;
import com.teamacronymcoders.essence.capabilities.block.BlockModifierProvider;
import com.teamacronymcoders.essence.capabilities.itemstack.ItemStackModifierHolder;
import com.teamacronymcoders.essence.capabilities.itemstack.ItemStackModifierProvider;
import com.teamacronymcoders.essence.client.EssenceClientProxy;
import com.teamacronymcoders.essence.client.render.PedestalTESR;
import com.teamacronymcoders.essence.container.PortableCrafterContainer;
import com.teamacronymcoders.essence.items.misc.wrench.EssenceWrench;
import com.teamacronymcoders.essence.items.misc.wrench.WrenchModeEnum;
import com.teamacronymcoders.essence.items.tools.EssenceShear;
import com.teamacronymcoders.essence.serializable.criterion.EssenceAdvancements;
import com.teamacronymcoders.essence.serializable.loot.FieryLootModifier;
import com.teamacronymcoders.essence.serializable.loot.condition.MatchModifier;
import com.teamacronymcoders.essence.serializable.providers.EssenceRecipeProvider;
import com.teamacronymcoders.essence.serializable.providers.EssenceSerializableProvider;
import com.teamacronymcoders.essence.serializable.providers.EssenceTagProvider;
import com.teamacronymcoders.essence.serializable.providers.EssenceToolRecipeProvider;
import com.teamacronymcoders.essence.serializable.recipe.infusion.InfusionTableSerializableRecipe;
import com.teamacronymcoders.essence.serializable.recipe.infusion.SerializableModifier;
import com.teamacronymcoders.essence.serializable.recipe.tool.AxeStrippingRecipe;
import com.teamacronymcoders.essence.serializable.recipe.tool.HoeTillingRecipe;
import com.teamacronymcoders.essence.serializable.recipe.tool.ShovelPathingRecipe;
import com.teamacronymcoders.essence.utils.EssenceCommonProxy;
import com.teamacronymcoders.essence.utils.EssenceModules;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import com.teamacronymcoders.essence.utils.EssenceSerializableObjectHandler;
import com.teamacronymcoders.essence.utils.config.EssenceGeneralConfig;
import com.teamacronymcoders.essence.utils.config.EssenceModifierConfig;
import com.teamacronymcoders.essence.utils.config.EssenceWorldGenConfig;
import com.teamacronymcoders.essence.utils.helpers.EssenceColorHelper;
import com.teamacronymcoders.essence.utils.helpers.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.utils.network.PacketHandler;
import com.teamacronymcoders.essence.utils.network.message.PacketItemStack;
import com.teamacronymcoders.essence.utils.registration.EssenceFeatureRegistration;
import com.teamacronymcoders.essence.utils.registration.EssenceKnowledgeRegistration;
import com.teamacronymcoders.essence.utils.registration.EssenceModifierRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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
    public static Minecraft minecraft = Minecraft.getInstance();
    public static Essence instance;
    public static EssenceCommonProxy proxy = DistExecutor.runForDist(Essence::getClientProxy, () -> EssenceCommonProxy::new);
    public static PacketHandler handler = new PacketHandler();
    public final String versionNumber;
    public Essence() {
        instance = this;
        versionNumber = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();
        handler.init();
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        JSONSerializableDataHandler.map(SerializableModifier.class, EssenceSerializableObjectHandler::writeSerializableModifier, EssenceSerializableObjectHandler::readSerializableModifier);
        JSONSerializableDataHandler.map(SerializableModifier[].class, EssenceSerializableObjectHandler::writeSerializableModifierArray, EssenceSerializableObjectHandler::readSerializableModifierArray);
        JSONSerializableDataHandler.map(BlockState.class, EssenceSerializableObjectHandler::writeBlockState, EssenceSerializableObjectHandler::readBlockState);
        CompoundSerializableDataHandler.map(SerializableModifier.class, EssenceSerializableObjectHandler::readSerializableModifier, EssenceSerializableObjectHandler::writeSerializableModifier);
        CompoundSerializableDataHandler.map(SerializableModifier[].class, EssenceSerializableObjectHandler::readSerializableModifierArray, EssenceSerializableObjectHandler::writeSerializableModifierArray);
        CompoundSerializableDataHandler.map(BlockState.class, EssenceSerializableObjectHandler::readBlockState, EssenceSerializableObjectHandler::writeBlockState);
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::setupCuriosIMC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EssenceGeneralConfig.initialize(), "essence/general.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EssenceWorldGenConfig.initialize(), "essence/worldgen.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EssenceModifierConfig.initialize(), "essence/modifiers.toml");

        EssenceFeatureRegistration.register(eventBus);
        EssenceModifierRegistration.register(eventBus);
        EssenceKnowledgeRegistration.register(eventBus);

        setupAdvancementCriterion();
        setupCreativeTabIcons();
        setupEventManagers();
    }

    @OnlyIn(Dist.CLIENT)
    private static Supplier<EssenceCommonProxy> getClientProxy() {
        return EssenceClientProxy::new;
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
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("backpack").setSize(1).setEnabled(true).setHidden(false));
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_ICON, () -> new Tuple<>("backpack", new ResourceLocation(MODID, "items/curios/empty_backpack_slot")));
    }

    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IKnowledgeHolder.class, NBTCapabilityStorage.create(ListNBT.class), KnowledgeHolder::new);
        CapabilityManager.INSTANCE.register(ItemStackModifierHolder.class, NBTCapabilityStorage.create(ListNBT.class), ItemStackModifierHolder::new);
        CapabilityManager.INSTANCE.register(BlockModifierHolder.class, NBTCapabilityStorage.create(ListNBT.class), BlockModifierHolder::new);

        LootConditionManager.registerCondition(new MatchModifier.Serializer());

        // TODO: Rework Worldgen Configs...
        //EssenceGeneration.addFeaturesToWorldGen();

        // TODO: Fix Dispenser Behaviour
        //EssenceDispenseBehaviours.init();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(EssenceObjectHolders.ESSENCE_WOOD_LEAVES, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(EssenceObjectHolders.ESSENCE_WOOD_SAPLING, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(EssenceObjectHolders.INFUSION_PEDESTAL, RenderType.getCutout());
        ClientRegistry.bindTileEntityRenderer((TileEntityType<InfusionPedestalTile>) EssenceObjectHolders.INFUSION_PEDESTAL.getTileEntityType(), PedestalTESR::new);
        // TODO: Reimplement once I get this working
        //ScreenManager.registerFactory(PortableCrafterContainer.type, PortableCrafterContainerScreen::new);
    }

    private void setupAdvancementCriterion() {
        EssenceAdvancements.setup();
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

    private void setupEventManagers() {
        // Registration Handlers
        EventManager.mod(RegistryEvent.Register.class)
            .filter(register -> register.getGenericType().equals(IRecipeSerializer.class))
            .process(register -> {
                register.getRegistry().registerAll(
                    AxeStrippingRecipe.SERIALIZER,
                    HoeTillingRecipe.SERIALIZER,
                    InfusionTableSerializableRecipe.SERIALIZER,
                    ShovelPathingRecipe.SERIALIZER
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

        // Capability Handlers
        EventManager.forge(AttachCapabilitiesEvent.class)
            .filter(attach -> attach.getObject() instanceof ItemStack)
            .process(attach -> {
                if (attach.getObject() instanceof IModified) {
                    attach.addCapability(new ResourceLocation(MODID, "item_modifier_holder"), new ItemStackModifierProvider());
                }
            }).subscribe();
        EventManager.forge(AttachCapabilitiesEvent.class)
            .filter(attach -> attach.getObject() instanceof Block)
            .process(attach -> {
                if (attach.getObject() instanceof IModified) {
                    attach.addCapability(new ResourceLocation(MODID, "block_modifier_holder"), new BlockModifierProvider());
                }
            }).subscribe();

        EventManager.forge(AttachCapabilitiesEvent.class)
            .filter(attach -> attach.getObject() instanceof Entity)
            .process(attach -> {
                if (attach.getObject() instanceof PlayerEntity) {
                    attach.addCapability(new ResourceLocation(MODID, "knowledge"), new KnowledgeProvider());
                }
            }).subscribe();
        EventManager.forge(PlayerEvent.Clone.class)
            .filter(PlayerEvent.Clone::isWasDeath)
            .process(clone -> {
                clone.getOriginal().getCapability(EssenceCapabilities.KNOWLEDGE).ifPresent(oldHolder -> {
                    clone.getPlayer().getCapability(EssenceCapabilities.KNOWLEDGE).ifPresent(newHolder -> {
                        newHolder.addKnowledge(clone.getPlayer(), oldHolder.getKnowledge());
                    });
                });
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
                if (minecraft.player != null && minecraft.player.isShiftKeyDown()) {
                    ItemStack stack = minecraft.player.getHeldItemMainhand();
                    if (stack.getItem() instanceof EssenceWrench) {
                        double scrolling = scroll.getScrollDelta();
                        if (scrolling != 0) {
                            EssenceWrench wrench = (EssenceWrench) stack.getItem();
                            WrenchModeEnum mode = wrench.getMode();
                            int newVal = mode.getId() + ((int) scrolling) % WrenchModeEnum.VALUES.length;
                            if (newVal > 0) {
                                newVal = newVal % WrenchModeEnum.VALUES.length;
                            } else if (newVal < 0) {
                                newVal = WrenchModeEnum.VALUES.length;
                            }
                            WrenchModeEnum newMode = WrenchModeEnum.cycleMode(newVal);
                            wrench.setMode(newMode);
                            handler.sendToServer(new PacketItemStack(Hand.MAIN_HAND, Collections.singletonList(newMode)));
                            scroll.setCanceled(true);
                        }
                    }
                }
            }).subscribe();
    }
}
