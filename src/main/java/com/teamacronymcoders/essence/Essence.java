package com.teamacronymcoders.essence;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.network.CompoundSerializableDataHandler;
import com.hrznstudio.titanium.recipe.generator.titanium.DefaultLootTableProvider;
import com.hrznstudio.titanium.recipe.serializer.JSONSerializableDataHandler;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.impl.serializable.EssenceRecipeProvider;
import com.teamacronymcoders.essence.impl.serializable.EssenceSerializableProvider;
import com.teamacronymcoders.essence.impl.serializable.EssenceTagProvider;
import com.teamacronymcoders.essence.impl.serializable.recipe.InfusionTableSerializableRecipe;
import com.teamacronymcoders.essence.impl.serializable.recipe.SerializableModifier;
import com.teamacronymcoders.essence.utils.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
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
        EssenceRegistration.register(eventBus);
        EventManager.mod(RegistryEvent.Register.class)
            .filter(register -> register.getGenericType().equals(IRecipeSerializer.class))
            .process(register -> {
                register.getRegistry().register(InfusionTableSerializableRecipe.SERIALIZER);
            }).subscribe();
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
        event.getGenerator().addProvider(new EssenceSerializableProvider(event.getGenerator()));
    }

    private void setup(final FMLCommonSetupEvent event) {
        EssenceReferences.CORE_TAB.addIconStacks(new ItemStack(EssenceObjectHolders.ESSENCE_FLUID.getBucketFluid()), new ItemStack(EssenceObjectHolders.ESSENCE_WOOD_SAPLING), new ItemStack(EssenceObjectHolders.ESSENCE_WOOD_LEAVES), new ItemStack(EssenceObjectHolders.ESSENCE_WOOD_LOG), new ItemStack(EssenceObjectHolders.ESSENCE_WOOD_PLANKS));
        EssenceReferences.TOOL_TAB.addIconStacks(new ItemStack(EssenceObjectHolders.ESSENCE_AXE), new ItemStack(EssenceObjectHolders.ESSENCE_PICKAXE), new ItemStack(EssenceObjectHolders.ESSENCE_SHOVEL), new ItemStack(EssenceObjectHolders.ESSENCE_SWORD), new ItemStack(EssenceObjectHolders.ESSENCE_HOE), new ItemStack(EssenceObjectHolders.ESSENCE_OMNITOOL));
        JSONSerializableDataHandler.map(SerializableModifier.class, EssenceSerializableObjectHandler::writeSerializableModifier, EssenceSerializableObjectHandler::readSerializableModifier);
        JSONSerializableDataHandler.map(SerializableModifier[].class, EssenceSerializableObjectHandler::writeSerializableModifierArray, EssenceSerializableObjectHandler::readSerializableModifierArray);
        CompoundSerializableDataHandler.map(SerializableModifier.class, EssenceSerializableObjectHandler::readSerializableModifier, EssenceSerializableObjectHandler::writeSerializableModifier);
        CompoundSerializableDataHandler.map(SerializableModifier[].class, EssenceSerializableObjectHandler::readSerializableModifierArray, EssenceSerializableObjectHandler::writeSerializableModifierArray);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(EssenceObjectHolders.ESSENCE_WOOD_LEAVES, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(EssenceObjectHolders.ESSENCE_WOOD_SAPLING, RenderType.getCutout());
    }
}
