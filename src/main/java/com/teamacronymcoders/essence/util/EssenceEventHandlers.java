package com.teamacronymcoders.essence.util;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.capabilities.EssenceCapability;
import com.teamacronymcoders.essence.api.knowledge.KnowledgeProvider;
import com.teamacronymcoders.essence.api.modified.IModifiedBlock;
import com.teamacronymcoders.essence.api.modified.IModifiedTool;
import com.teamacronymcoders.essence.api.recipe.tool.AxeStrippingRecipe;
import com.teamacronymcoders.essence.api.recipe.tool.HoeTillingRecipe;
import com.teamacronymcoders.essence.api.recipe.tool.ShovelPathingRecipe;
import com.teamacronymcoders.essence.capability.block.BlockModifierProvider;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierProvider;
import com.teamacronymcoders.essence.client.render.tesr.InfusionTableTESR;
import com.teamacronymcoders.essence.item.tome.experience.ExperienceModeEnum;
import com.teamacronymcoders.essence.item.tome.experience.TomeOfExperienceItem;
import com.teamacronymcoders.essence.item.tool.EssenceShear;
import com.teamacronymcoders.essence.item.wrench.EssenceWrench;
import com.teamacronymcoders.essence.item.wrench.WrenchModeEnum;
import com.teamacronymcoders.essence.serializable.loot.FieryLootModifier;
import com.teamacronymcoders.essence.serializable.recipe.infusion.InfusionTableSerializableRecipe;
import com.teamacronymcoders.essence.command.EssenceCommands;
import com.teamacronymcoders.essence.util.helper.EssenceColorHelper;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.util.network.message.PacketItemStack;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import java.awt.*;
import java.util.Collections;

import static com.teamacronymcoders.essence.Essence.MODID;

public class EssenceEventHandlers {

    public static EssenceEventHandlers handlers = new EssenceEventHandlers();

    public static void setup() {
        setupRegistries();
        setupModifierCapabilities();
        setupKnowledgeCapabilities();
        setupServer();
        DistExecutor.runWhenOn(Dist.CLIENT, () -> EssenceEventHandlers::setupClientEventHandlers);
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
//        EventManager.mod(RegistryEvent.Register.class)
//            .filter(register -> register.getGenericType().equals(ContainerType.class))
//            .process(register -> {
//                register.getRegistry().registerAll(
//                    IForgeContainerType.create(PortableCrafterContainer::new).setRegistryName(new ResourceLocation(MODID, "portable_crafter"))
//                );
//            }).subscribe();
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

    @OnlyIn(Dist.CLIENT)
    private static void setupClientEventHandlers() {
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
                            minecraft.player.sendStatusMessage(new TranslationTextComponent("wrench.essence.mode.tooltip").appendText(": ").appendSibling(new TranslationTextComponent(newMode.getLocaleName())), true);
                            Essence.handler.sendToServer(new PacketItemStack(Hand.MAIN_HAND, Collections.singletonList(newMode)));
                            scroll.setCanceled(true);
                        }
                    }
                }
            }).subscribe();

        // Wrench-Mode Handler
        EventManager.forge(InputEvent.MouseScrollEvent.class)
            .process(scroll -> {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.player != null && minecraft.player.isShiftKeyDown()) {
                    ItemStack stack = minecraft.player.getHeldItemMainhand();
                    if (stack.getItem() instanceof TomeOfExperienceItem) {
                        double scrolling = scroll.getScrollDelta();
                        if (scrolling != 0) {
                            TomeOfExperienceItem tome = (TomeOfExperienceItem) stack.getItem();
                            ExperienceModeEnum mode = tome.getMode();
                            ExperienceModeEnum newMode = ExperienceModeEnum.cycleMode(mode.ordinal());
                            tome.setMode(newMode);
                            minecraft.player.sendStatusMessage(new TranslationTextComponent("tome.essence.mode.tooltip").appendText(": ").appendSibling(new TranslationTextComponent(newMode.getLocaleString())), true);
                            Essence.handler.sendToServer(new PacketItemStack(Hand.MAIN_HAND, Collections.singletonList(newMode)));
                            scroll.setCanceled(true);
                        }
                    }
                }
            }).subscribe();
    }
}
