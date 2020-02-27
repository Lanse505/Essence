package com.teamacronymcoders.essence;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.network.CompoundSerializableDataHandler;
import com.hrznstudio.titanium.recipe.serializer.JSONSerializableDataHandler;
import com.teamacronymcoders.essence.impl.blocks.infuser.InfusionPedestalTile;
import com.teamacronymcoders.essence.impl.client.PedestalTESR;
import com.teamacronymcoders.essence.impl.serializable.EssenceRecipeProvider;
import com.teamacronymcoders.essence.impl.serializable.EssenceSerializableProvider;
import com.teamacronymcoders.essence.impl.serializable.EssenceTagProvider;
import com.teamacronymcoders.essence.impl.serializable.loot_modifier.FieryLootModifier;
import com.teamacronymcoders.essence.impl.serializable.recipe.InfusionTableSerializableRecipe;
import com.teamacronymcoders.essence.impl.serializable.recipe.SerializableModifier;
import com.teamacronymcoders.essence.utils.*;
import com.teamacronymcoders.essence.utils.config.EssenceGeneralConfig;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.storage.loot.LootTable;
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

import java.util.UUID;

@Mod("essence")
public class Essence extends ModuleController {

    public static final Logger LOGGER = LogManager.getLogger("Essence");

    public Essence() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::setupCuriosIMC);
        EssenceRegistration.register(eventBus);
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
                    new FieryLootModifier.Serializer().setRegistryName(new ResourceLocation(EssenceReferences.MODID, "fiery_modifier"))
                );
            }).subscribe();
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
        event.getGenerator().addProvider(new EssenceSerializableProvider(event.getGenerator()));
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
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_ICON, () -> new Tuple<>("backpack", new ResourceLocation(EssenceReferences.MODID, "items/curios/empty_backpack_slot")));
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
        RenderTypeLookup.setRenderLayer(EssenceObjectHolders.ESSENCE_INFUSION_PEDESTAL, RenderType.getCutout());

        ClientRegistry.bindTileEntityRenderer((TileEntityType<InfusionPedestalTile>) EssenceObjectHolders.ESSENCE_INFUSION_PEDESTAL.getTileEntityType(), PedestalTESR::new);
    }
}
