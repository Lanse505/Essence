package com.teamacronymcoders.essence;

import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.network.CompoundSerializableDataHandler;
import com.hrznstudio.titanium.recipe.serializer.JSONSerializableDataHandler;
import com.hrznstudio.titanium.tab.AdvancedTitaniumTab;
import com.teamacronymcoders.essence.api.capabilities.NBTCapabilityStorage;
import com.teamacronymcoders.essence.api.knowledge.IKnowledgeHolder;
import com.teamacronymcoders.essence.api.knowledge.KnowledgeHolder;
import com.teamacronymcoders.essence.api.recipe.infusion.SerializableModifier;
import com.teamacronymcoders.essence.capability.block.BlockModifierHolder;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierHolder;
import com.teamacronymcoders.essence.capability.itemstack.wrench.EntityStorageCapability;
import com.teamacronymcoders.essence.command.argument.EssenceHandArgumentType;
import com.teamacronymcoders.essence.command.argument.EssenceKnowledgeArgumentType;
import com.teamacronymcoders.essence.command.argument.EssenceModifierArgumentType;
import com.teamacronymcoders.essence.command.argument.extendable.EssenceEnumArgumentType;
import com.teamacronymcoders.essence.datagen.EssenceAdvancementProvider;
import com.teamacronymcoders.essence.datagen.EssenceRecipeProvider;
import com.teamacronymcoders.essence.datagen.advancement.CoreAdvancementProvider;
import com.teamacronymcoders.essence.datagen.advancement.KnowledgeAdvancementProvider;
import com.teamacronymcoders.essence.item.tool.misc.behaviour.EssenceDispenseBehaviours;
import com.teamacronymcoders.essence.registrate.*;
import com.teamacronymcoders.essence.serializable.advancement.criterion.EssenceAdvancements;
import com.teamacronymcoders.essence.serializable.loot.condition.EssenceConditions;
import com.teamacronymcoders.essence.serializable.loot.condition.MatchModifier;
import com.teamacronymcoders.essence.util.EssenceEventHandlers;
import com.teamacronymcoders.essence.util.EssenceItemProperties;
import com.teamacronymcoders.essence.util.EssenceSerializableObjectHandler;
import com.teamacronymcoders.essence.util.config.EssenceGeneralConfig;
import com.teamacronymcoders.essence.util.config.EssenceModifierConfig;
import com.teamacronymcoders.essence.util.config.EssenceWorldGenConfig;
import com.teamacronymcoders.essence.util.keybindings.EssenceKeyHandler;
import com.teamacronymcoders.essence.util.network.PacketHandler;
import com.teamacronymcoders.essence.util.proxy.EssenceCommonProxy;
import com.teamacronymcoders.essence.util.proxy.EssenceSafeSuppliers;
import com.teamacronymcoders.essence.util.registration.EssenceKnowledgeRegistration;
import com.teamacronymcoders.essence.util.registration.EssenceModifierRegistration;
import com.teamacronymcoders.essence.util.registration.EssenceParticleTypeRegistration;
import com.teamacronymcoders.essence.util.registration.EssenceSoundRegistration;
import com.teamacronymcoders.essence.util.tab.EssenceCoreTab;
import com.teamacronymcoders.essence.util.tab.EssenceToolTab;
import com.tterrag.registrate.Registrate;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("essence")
public class Essence extends ModuleController {

  public static final String MOD_ID = "essence";
  public static final String versionNumber = "1.0.0-alpha";
  public static final Random RANDOM = new Random();
  public static final Logger LOGGER = LogManager.getLogger("Essence");
  public static final AdvancedTitaniumTab CORE_TAB = new EssenceCoreTab();
  public static final AdvancedTitaniumTab TOOL_TAB = new EssenceToolTab();

  public static Essence instance;
  public static EssenceCommonProxy proxy = DistExecutor.safeRunForDist(EssenceSafeSuppliers.getClientProxy(), EssenceSafeSuppliers.getServerProxy());
  public static PacketHandler handler = new PacketHandler();

  public static Registrate ESSENCE_REGISTRATE;

  public Essence () {
    instance = this;
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
    ESSENCE_REGISTRATE = Registrate.create("essence");
    EssenceKnowledgeRegistration.register(eventBus);
    EssenceModifierRegistration.register(eventBus);
    EssenceSoundRegistration.register(eventBus);
    EssenceParticleTypeRegistration.register(eventBus);
    EssenceAdvancements.setup();
    EssenceEventHandlers.setup();

    EssenceItemRegistrate.init();
    EssenceBlockRegistrate.init();
    EssenceFluidRegistrate.init();
    EssenceEntityRegistrate.init();
    EssenceTagRegistrate.init(ESSENCE_REGISTRATE);
    EssenceLangRegistrate.init(ESSENCE_REGISTRATE);

    DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> EssenceEventHandlers::setupClientEventHandlers);
    setupCreativeTabIcons();
    eventBus.addListener(this::setup);
    eventBus.addListener(this::clientSetup);
    eventBus.addListener(this::setupCuriosIMC);
  }

  @Override
  protected void initModules () {}

  @Override
  public void addDataProvider (GatherDataEvent event) {
    super.addDataProvider(event);
    EssenceRecipeProvider.addRecipeProviders(event.getGenerator());
    // TODO: Port Advancements to Registrate
    event.getGenerator().addProvider(new EssenceAdvancementProvider(event.getGenerator()));
    event.getGenerator().addProvider(new CoreAdvancementProvider(event.getGenerator()));
    event.getGenerator().addProvider(new KnowledgeAdvancementProvider(event.getGenerator()));
  }

  private void setupCuriosIMC (final InterModEnqueueEvent event) {
    //InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("backpack").setSize(1).setEnabled(true).setHidden(false));
    //InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_ICON, () -> new Tuple<>("backpack", new ResourceLocation(MOD_ID, "items/curios/empty_backpack_slot")));
  }

  private void setup (final FMLCommonSetupEvent event) {
    ArgumentTypes.register("essence_hand", EssenceEnumArgumentType.class, new ArgumentSerializer<>(EssenceHandArgumentType::new));
    ArgumentTypes.register("essence_modifier", EssenceModifierArgumentType.class, new ArgumentSerializer<>(EssenceModifierArgumentType::new));
    ArgumentTypes.register("essence_knowledge", EssenceKnowledgeArgumentType.class, new ArgumentSerializer<>(EssenceKnowledgeArgumentType::new));
    CapabilityManager.INSTANCE.register(BlockModifierHolder.class, NBTCapabilityStorage.create(ListNBT.class), BlockModifierHolder::new);
    CapabilityManager.INSTANCE.register(EntityStorageCapability.class, NBTCapabilityStorage.create(CompoundNBT.class), EntityStorageCapability::new);
    CapabilityManager.INSTANCE.register(IKnowledgeHolder.class, NBTCapabilityStorage.create(CompoundNBT.class), KnowledgeHolder::new);
    CapabilityManager.INSTANCE.register(ItemStackModifierHolder.class, NBTCapabilityStorage.create(ListNBT.class), ItemStackModifierHolder::new);
    EssenceConditions.MATCH_MODIFIER = LootConditionManager.register("essence:match_modifier", new MatchModifier.Serializer());

    GlobalEntityTypeAttributes.put(EssenceEntityRegistrate.SHEARED_CHICKEN.get(), ChickenEntity.func_234187_eI_().create());
    GlobalEntityTypeAttributes.put(EssenceEntityRegistrate.SHEARED_COW.get(), CowEntity.func_234188_eI_().create());
    GlobalEntityTypeAttributes.put(EssenceEntityRegistrate.SHEARED_CREEPER.get(), CreeperEntity.registerAttributes().create());
    GlobalEntityTypeAttributes.put(EssenceEntityRegistrate.SHEARED_GHAST.get(), GhastEntity.func_234290_eH_().create());
    GlobalEntityTypeAttributes.put(EssenceEntityRegistrate.SHEARED_PIG.get(), PigEntity.func_234215_eI_().create());

    // TODO: Fix Dispenser Behaviour
    EssenceDispenseBehaviours.init();
  }

  @SuppressWarnings("unchecked")
  private void clientSetup (final FMLClientSetupEvent event) {
    new EssenceKeyHandler();
    // TODO: Reimplement once I get this working
    //ScreenManager.registerFactory(PortableCrafterContainer.type, PortableCrafterContainerScreen::new);

    // Pull
    ItemModelsProperties.registerProperty(EssenceItemRegistrate.ESSENCE_BOW.get(), new ResourceLocation(Essence.MOD_ID, "pull"), EssenceItemProperties.PULL);
    ItemModelsProperties.registerProperty(EssenceItemRegistrate.ESSENCE_BOW_EMPOWERED.get(), new ResourceLocation(Essence.MOD_ID, "pull"), EssenceItemProperties.PULL);
    ItemModelsProperties.registerProperty(EssenceItemRegistrate.ESSENCE_BOW_SUPREME.get(), new ResourceLocation(Essence.MOD_ID, "pull"), EssenceItemProperties.PULL);
    ItemModelsProperties.registerProperty(EssenceItemRegistrate.ESSENCE_BOW_DIVINE.get(), new ResourceLocation(Essence.MOD_ID, "pull"), EssenceItemProperties.PULL);

    // Pulling
    ItemModelsProperties.registerProperty(EssenceItemRegistrate.ESSENCE_BOW.get(), new ResourceLocation(Essence.MOD_ID, "pulling"), EssenceItemProperties.PULLING);
    ItemModelsProperties.registerProperty(EssenceItemRegistrate.ESSENCE_BOW_EMPOWERED.get(), new ResourceLocation(Essence.MOD_ID, "pulling"), EssenceItemProperties.PULLING);
    ItemModelsProperties.registerProperty(EssenceItemRegistrate.ESSENCE_BOW_SUPREME.get(), new ResourceLocation(Essence.MOD_ID, "pulling"), EssenceItemProperties.PULLING);
    ItemModelsProperties.registerProperty(EssenceItemRegistrate.ESSENCE_BOW_DIVINE.get(), new ResourceLocation(Essence.MOD_ID, "pulling"), EssenceItemProperties.PULLING);

    // Toggled
    // TODO: Implement for Tablet of Muffling
    //ItemModelsProperties.registerProperty(EssenceObjectHolders.ESSENCE_BOW, new ResourceLocation(Essence.MOD_ID, "toggling"), EssenceItemProperties.TOGGLED);
  }

  private void setupCreativeTabIcons () {
    CORE_TAB.addIconStacks(
            new ItemStack(EssenceFluidRegistrate.ESSENCE.get().getFilledBucket()),
            new ItemStack(EssenceBlockRegistrate.ESSENCE_WOOD_SAPLING.get()),
            new ItemStack(EssenceBlockRegistrate.ESSENCE_WOOD_LEAVES.get()),
            new ItemStack(EssenceBlockRegistrate.ESSENCE_WOOD_LOG.get()),
            new ItemStack(EssenceBlockRegistrate.ESSENCE_WOOD_PLANKS.get())
    );

    TOOL_TAB.addIconStacks(
            new ItemStack(EssenceItemRegistrate.ESSENCE_AXE.get()),
            new ItemStack(EssenceItemRegistrate.ESSENCE_PICKAXE.get()),
            new ItemStack(EssenceItemRegistrate.ESSENCE_SHOVEL.get()),
            new ItemStack(EssenceItemRegistrate.ESSENCE_SWORD.get()),
            new ItemStack(EssenceItemRegistrate.ESSENCE_HOE.get()),
            new ItemStack(EssenceItemRegistrate.ESSENCE_OMNITOOL.get()),
            new ItemStack(EssenceItemRegistrate.ESSENCE_SHEAR.get()),
            new ItemStack(EssenceItemRegistrate.ESSENCE_BOW.get())
    );
  }
}
