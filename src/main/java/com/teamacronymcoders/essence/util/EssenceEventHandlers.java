package com.teamacronymcoders.essence.util;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.essence.api.capabilities.EssenceCapability;
import com.teamacronymcoders.essence.api.knowledge.KnowledgeProvider;
import com.teamacronymcoders.essence.api.modified.IModifiedBlock;
import com.teamacronymcoders.essence.api.modified.IModifiedTool;
import com.teamacronymcoders.essence.api.recipe.tool.AxeStrippingRecipe;
import com.teamacronymcoders.essence.api.recipe.tool.HoeTillingRecipe;
import com.teamacronymcoders.essence.api.recipe.tool.ShovelPathingRecipe;
import com.teamacronymcoders.essence.capability.block.BlockModifierProvider;
import com.teamacronymcoders.essence.capability.itemstack.ItemStackModifierProvider;
import com.teamacronymcoders.essence.container.PortableCrafterContainer;
import com.teamacronymcoders.essence.serializable.loot.FieryLootModifier;
import com.teamacronymcoders.essence.serializable.recipe.infusion.InfusionTableSerializableRecipe;
import com.teamacronymcoders.essence.util.command.EssenceCommands;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import static com.teamacronymcoders.essence.Essence.MODID;

public class EssenceEventHandlers {

    public static void setup() {
        setupRegistries();
        setupModifierCapabilities();
        setupKnowledgeCapabilities();
        setupServer();
    }

    // Registration Handlers
    private static void setupRegistries() {
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
    }

    private static void setupModifierCapabilities() {
        EventManager.forge(AttachCapabilitiesEvent.class)
            .filter(attach -> attach.getObject() instanceof ItemStack)
            .process(attach -> {
                if (attach.getObject() instanceof IModifiedTool) {
                    attach.addCapability(new ResourceLocation(MODID, "item_modifier_holder"), new ItemStackModifierProvider((ItemStack) attach.getObject()));
                }
            }).subscribe();
        EventManager.forge(AttachCapabilitiesEvent.class)
            .filter(attach -> attach.getObject() instanceof Block)
            .process(attach -> {
                if (attach.getObject() instanceof IModifiedBlock) {
                    attach.addCapability(new ResourceLocation(MODID, "block_modifier_holder"), new BlockModifierProvider());
                }
            }).subscribe();
    }

    private static void setupKnowledgeCapabilities() {
        EventManager.forge(AttachCapabilitiesEvent.class)
            .filter(attach -> attach.getObject() instanceof World && ((World) attach.getObject()).getDimension() instanceof OverworldDimension)
            .process(attach -> {
                attach.addCapability(new ResourceLocation(MODID, "knowledge"), new KnowledgeProvider());
            }).subscribe();
        EventManager.forge(EntityJoinWorldEvent.class)
            .filter(join -> join.getEntity() instanceof PlayerEntity && join.getWorld().getDimension() instanceof OverworldDimension)
            .process(join -> {
                join.getWorld().getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
                    holder.addPlayerUUID((PlayerEntity) join.getEntity());
                });
            }).subscribe();
    }

    // Server Handlers
    public static void setupServer() {
        EventManager.forge(FMLServerStartingEvent.class)
            .process(starting -> EssenceCommands.registerCommands(starting.getCommandDispatcher()))
            .subscribe();
    }
}
