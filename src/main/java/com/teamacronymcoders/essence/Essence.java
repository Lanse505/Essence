package com.teamacronymcoders.essence;

import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.network.CompoundSerializableDataHandler;
import com.hrznstudio.titanium.recipe.serializer.JSONSerializableDataHandler;
import com.hrznstudio.titanium.tab.AdvancedTitaniumTab;
import com.teamacronymcoders.essence.api.recipe.infusion.SerializableModifier;
import com.teamacronymcoders.essence.client.util.keybindings.EssenceKeyHandler;
import com.teamacronymcoders.essence.common.item.behaviour.EssenceDispenseBehaviours;
import com.teamacronymcoders.essence.common.util.EssenceEventHandlers;
import com.teamacronymcoders.essence.common.util.EssenceItemProperties;
import com.teamacronymcoders.essence.common.util.EssenceSerializableObjectHandler;
import com.teamacronymcoders.essence.common.util.config.EssenceGeneralConfig;
import com.teamacronymcoders.essence.common.util.config.EssenceModifierConfig;
import com.teamacronymcoders.essence.common.util.config.EssenceWorldGenConfig;
import com.teamacronymcoders.essence.common.util.network.PacketHandler;
import com.teamacronymcoders.essence.common.util.proxy.EssenceCommonProxy;
import com.teamacronymcoders.essence.common.util.proxy.EssenceSafeSuppliers;
import com.teamacronymcoders.essence.common.util.tab.EssenceCoreTab;
import com.teamacronymcoders.essence.common.util.tab.EssenceToolTab;
import com.teamacronymcoders.essence.common.world.generation.ore.EssenceOreGenRegistration;
import com.teamacronymcoders.essence.common.world.generation.tree.EssenceTreeFeatures;
import com.teamacronymcoders.essence.compat.registrate.*;
import com.teamacronymcoders.essence.compat.registrate.datagen.EssenceRecipeProvider;
import com.teamacronymcoders.essence.data.advancement.criterion.EssenceAdvancements;
import com.teamacronymcoders.essence.data.ingredient.TierIngredient;
import com.teamacronymcoders.essence.data.loot.condition.EssenceConditions;
import com.teamacronymcoders.essence.data.loot.condition.MatchModifier;
import com.teamacronymcoders.essence.server.command.argument.EssenceHandArgumentType;
import com.teamacronymcoders.essence.server.command.argument.EssenceKnowledgeArgumentType;
import com.teamacronymcoders.essence.server.command.argument.EssenceModifierArgumentType;
import com.teamacronymcoders.essence.server.command.argument.extendable.EssenceEnumArgumentType;
import com.tterrag.registrate.Registrate;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Random;

// TODO List:
// 1. Make Crafting a thing
// 2. Work on finishing the unfinished things (Tablet of Muffling, Portable Crafter, etc)

@Mod("essence")
public class Essence extends ModuleController {

    public static final String MOD_ID = "essence";
    public static final String versionNumber = "1.0.0-alpha";
    public static final Random RANDOM = new Random();
    public static final Logger LOGGER = LogManager.getLogger("Essence");
    public static AdvancedTitaniumTab CORE_TAB;
    public static AdvancedTitaniumTab TOOL_TAB;

    public static Essence instance;
    public static EssenceCommonProxy proxy = DistExecutor.safeRunForDist(EssenceSafeSuppliers.getClientProxy(), EssenceSafeSuppliers.getServerProxy());
    public static PacketHandler handler = new PacketHandler();

    public static Registrate ESSENCE_REGISTRATE;

    public Essence() {
        instance = this;
        handler.init();

        // Titanium Serializers
        JSONSerializableDataHandler.map(SerializableModifier.class, EssenceSerializableObjectHandler::writeSerializableModifier, EssenceSerializableObjectHandler::readSerializableModifier);
        JSONSerializableDataHandler.map(SerializableModifier[].class, EssenceSerializableObjectHandler::writeSerializableModifierArray, EssenceSerializableObjectHandler::readSerializableModifierArray);
        JSONSerializableDataHandler.map(BlockState.class, EssenceSerializableObjectHandler::writeBlockState, EssenceSerializableObjectHandler::readBlockState);
        JSONSerializableDataHandler.map(EntityType.class, EssenceSerializableObjectHandler::writeEntityType, EssenceSerializableObjectHandler::readEntityType);
        JSONSerializableDataHandler.map(ResourceLocation.class, EssenceSerializableObjectHandler::writeResourceLocation, EssenceSerializableObjectHandler::readResourceLocation);
        CompoundSerializableDataHandler.map(SerializableModifier.class, EssenceSerializableObjectHandler::readSerializableModifier, EssenceSerializableObjectHandler::writeSerializableModifier);
        CompoundSerializableDataHandler.map(SerializableModifier[].class, EssenceSerializableObjectHandler::readSerializableModifierArray, EssenceSerializableObjectHandler::writeSerializableModifierArray);
        CompoundSerializableDataHandler.map(BlockState.class, EssenceSerializableObjectHandler::readBlockState, EssenceSerializableObjectHandler::writeBlockState);
        CompoundSerializableDataHandler.map(EntityType.class, EssenceSerializableObjectHandler::readEntityType, EssenceSerializableObjectHandler::writeEntityType);
        CompoundSerializableDataHandler.map(ResourceLocation.class, EssenceSerializableObjectHandler::readResourceLocation, EssenceSerializableObjectHandler::writeResourceLocation);

        // Configs

        Path path = FMLPaths.CONFIGDIR.get();
        Path.of(path.toString(), "titanium").toFile().mkdir();
        if (!(Path.of(path.toString(), "essence").toFile().exists()))
            Path.of(path.toString(), "essence").toFile().mkdir();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EssenceGeneralConfig.initialize(), "essence/general.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EssenceWorldGenConfig.initialize(), "essence/worldgen.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EssenceModifierConfig.initialize(), "essence/modifiers.toml");

        // Registrates
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CORE_TAB = new EssenceCoreTab();
        TOOL_TAB = new EssenceToolTab();
        ESSENCE_REGISTRATE = Registrate.create("essence");
        EssenceAdvancements.setup();
        EssenceEventHandlers.setup();

        // Registrates
        EssenceItemRegistrate.init();
        EssenceBlockRegistrate.init();
        EssenceFluidRegistrate.init();
        EssenceEntityRegistrate.init();
        EssenceModifierRegistrate.init();
        EssenceKnowledgeRegistrate.init();
        EssenceParticleRegistrate.init();
        EssenceSoundRegistrate.init();
        EssenceGUIRegistrate.init();

        // Data-Generators
        EssenceAdvancementRegistrate.init(ESSENCE_REGISTRATE);
        EssenceLootTableRegistrate.init(ESSENCE_REGISTRATE);
        EssenceTagRegistrate.init(ESSENCE_REGISTRATE);
        EssenceLangRegistrate.init(ESSENCE_REGISTRATE);

        // Misc Setup

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::setupCuriosIMC);
        eventBus.addListener(this::addAttributes);

        CraftingHelper.register(new ResourceLocation(MOD_ID, "tier"), TierIngredient.Serializer.INSTANCE);
    }

    @Override
    protected void initModules() {
    }

    @Override
    public void addDataProvider(GatherDataEvent event) {
        super.addDataProvider(event);
        EssenceRecipeProvider.addRecipeProviders(event.getGenerator());
    }

    private void setupCuriosIMC(final InterModEnqueueEvent event) {
        //InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("backpack").setSize(1).setEnabled(true).setHidden(false));
        //InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_ICON, () -> new Tuple<>("backpack", new ResourceLocation(MOD_ID, "items/curios/empty_backpack_slot")));
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ArgumentTypes.register("essence_hand", EssenceEnumArgumentType.class, new EmptyArgumentSerializer<>(EssenceHandArgumentType::new));
            ArgumentTypes.register("essence_modifier", EssenceModifierArgumentType.class, new EmptyArgumentSerializer<>(EssenceModifierArgumentType::new));
            ArgumentTypes.register("essence_knowledge", EssenceKnowledgeArgumentType.class, new EmptyArgumentSerializer<>(EssenceKnowledgeArgumentType::new));
            EssenceConditions.MATCH_MODIFIER = LootItemConditions.register("essence:match_modifier", new MatchModifier.ModifierSerializer());
            EssenceDispenseBehaviours.init();
            EssenceOreGenRegistration.registerWorldGen();
            new EssenceTreeFeatures();
        });
    }

    private void addAttributes(final EntityAttributeCreationEvent event) {
        event.put(EssenceEntityRegistrate.SHEARED_CHICKEN.get(), Chicken.createAttributes().build());
        event.put(EssenceEntityRegistrate.SHEARED_COW.get(), Cow.createAttributes().build());
        event.put(EssenceEntityRegistrate.SHEARED_CREEPER.get(), Creeper.createAttributes().build());
        event.put(EssenceEntityRegistrate.SHEARED_GHAST.get(), Ghast.createAttributes().build());
        event.put(EssenceEntityRegistrate.SHEARED_PIG.get(), Pig.createAttributes().build());
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        new EssenceKeyHandler();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> EssenceEventHandlers::setupClientEventHandlers);

        // Pull
        ItemProperties.register(EssenceItemRegistrate.DECODER_SLINGSHOT.get(), new ResourceLocation(Essence.MOD_ID, "pull"), EssenceItemProperties.getPull());
        ItemProperties.register(EssenceItemRegistrate.ESSENCE_BOW.get(), new ResourceLocation(Essence.MOD_ID, "pull"), EssenceItemProperties.getPull());
        ItemProperties.register(EssenceItemRegistrate.ESSENCE_BOW_EMPOWERED.get(), new ResourceLocation(Essence.MOD_ID, "pull"), EssenceItemProperties.getPull());
        ItemProperties.register(EssenceItemRegistrate.ESSENCE_BOW_SUPREME.get(), new ResourceLocation(Essence.MOD_ID, "pull"), EssenceItemProperties.getPull());
        ItemProperties.register(EssenceItemRegistrate.ESSENCE_BOW_DIVINE.get(), new ResourceLocation(Essence.MOD_ID, "pull"), EssenceItemProperties.getPull());

        // Pulling
        ItemProperties.register(EssenceItemRegistrate.DECODER_SLINGSHOT.get(), new ResourceLocation(Essence.MOD_ID, "pulling"), EssenceItemProperties.getPulling());
        ItemProperties.register(EssenceItemRegistrate.ESSENCE_BOW.get(), new ResourceLocation(Essence.MOD_ID, "pulling"), EssenceItemProperties.getPulling());
        ItemProperties.register(EssenceItemRegistrate.ESSENCE_BOW_EMPOWERED.get(), new ResourceLocation(Essence.MOD_ID, "pulling"), EssenceItemProperties.getPulling());
        ItemProperties.register(EssenceItemRegistrate.ESSENCE_BOW_SUPREME.get(), new ResourceLocation(Essence.MOD_ID, "pulling"), EssenceItemProperties.getPulling());
        ItemProperties.register(EssenceItemRegistrate.ESSENCE_BOW_DIVINE.get(), new ResourceLocation(Essence.MOD_ID, "pulling"), EssenceItemProperties.getPulling());

        // Toggled
        // TODO: Implement for Tablet of Muffling
        //ItemProperties.register(EssenceObjectHolders.ESSENCE_BOW, new ResourceLocation(Essence.MOD_ID, "toggling"), EssenceItemProperties.TOGGLED);
    }
}
