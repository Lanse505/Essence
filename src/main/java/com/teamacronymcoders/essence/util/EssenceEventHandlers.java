package com.teamacronymcoders.essence.util;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.capabilities.EssenceCapability;
import com.teamacronymcoders.essence.api.knowledge.KnowledgeProvider;
import com.teamacronymcoders.essence.api.modified.IModifiedTool;
import com.teamacronymcoders.essence.api.recipe.infusion.InfusionRecipeConversion;
import com.teamacronymcoders.essence.api.recipe.infusion.InfusionRecipeModifier;
import com.teamacronymcoders.essence.api.recipe.tool.AxeStrippingRecipe;
import com.teamacronymcoders.essence.api.recipe.tool.HoeTillingRecipe;
import com.teamacronymcoders.essence.api.recipe.tool.ShovelPathingRecipe;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierProvider;
import com.teamacronymcoders.essence.client.render.tesr.InfusionTableTESR;
import com.teamacronymcoders.essence.command.EssenceCommands;
import com.teamacronymcoders.essence.item.tome.experience.ExperienceModeEnum;
import com.teamacronymcoders.essence.item.tome.experience.TomeOfExperienceItem;
import com.teamacronymcoders.essence.item.tool.EssenceShear;
import com.teamacronymcoders.essence.item.wrench.EssenceWrench;
import com.teamacronymcoders.essence.item.wrench.WrenchModeEnum;
import com.teamacronymcoders.essence.registrate.EssenceBlockRegistrate;
import com.teamacronymcoders.essence.registrate.EssenceFluidRegistrate;
import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.serializable.loot.FieryLootModifier;
import com.teamacronymcoders.essence.util.config.EssenceWorldGenConfig;
import com.teamacronymcoders.essence.util.config.subconfigs.EssenceOreGenConfig;
import com.teamacronymcoders.essence.util.config.subconfigs.EssenceTreeGenConfig;
import com.teamacronymcoders.essence.util.helper.EssenceColorHelper;
import com.teamacronymcoders.essence.util.helper.EssenceInformationHelper;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.util.network.message.server.PacketItemStack;
import com.teamacronymcoders.essence.world.generation.ore.EssenceOreFeatures;
import com.teamacronymcoders.essence.world.generation.tree.EssenceTreeFeatures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static com.teamacronymcoders.essence.Essence.*;

public class EssenceEventHandlers {

  public static void setup() {
    setupRegistries();
    setupModifierCapabilities();
    setupKnowledgeCapabilities();
    setupServer();
    setupWorldgen();
  }

  // Registration Handlers
  private static void setupRegistries() {
    EventManager.modGeneric(RegistryEvent.Register.class, RecipeSerializer.class)
            .process(register -> {
              ((RegistryEvent.Register) register).getRegistry().registerAll(
                      AxeStrippingRecipe.SERIALIZER,
                      HoeTillingRecipe.SERIALIZER,
                      ShovelPathingRecipe.SERIALIZER,
                      InfusionRecipeConversion.SERIALIZER,
                      InfusionRecipeModifier.SERIALIZER
              );
            }).subscribe();
    EventManager.modGeneric(RegistryEvent.Register.class, GlobalLootModifierSerializer.class)
            .process(register -> {
              ((RegistryEvent.Register) register).getRegistry().registerAll(
                      new FieryLootModifier.Serializer().setRegistryName(new ResourceLocation(MOD_ID, "fiery_modifier"))
              );
            }).subscribe();
  }

  private static void setupModifierCapabilities() {
    EventManager.forgeGeneric(AttachCapabilitiesEvent.class, ItemStack.class)
            .process(attach -> {
              if (((AttachCapabilitiesEvent) attach).getObject() instanceof IModifiedTool) {
                ((AttachCapabilitiesEvent) attach).addCapability(new ResourceLocation(MOD_ID, "item_modifier_holder"), new ItemStackModifierProvider((ItemStack) ((AttachCapabilitiesEvent) attach).getObject()));
              }
            }).subscribe();
  }

  private static void setupKnowledgeCapabilities() {
    EventManager.forgeGeneric(AttachCapabilitiesEvent.class, Level.class)
            .filter(attach -> ((AttachCapabilitiesEvent) attach).getObject() instanceof Level && ((Level) ((AttachCapabilitiesEvent) attach).getObject()).dimension().getRegistryName().compareTo(DimensionType.OVERWORLD_EFFECTS) > 0)
            .process(attach -> {
              ((AttachCapabilitiesEvent) attach).addCapability(new ResourceLocation(MOD_ID, "knowledge"), new KnowledgeProvider());
            }).subscribe();
    EventManager.forge(EntityJoinWorldEvent.class)
            .filter(join -> join.getEntity() instanceof Player && join.getWorld().dimension().getRegistryName().compareTo(DimensionType.OVERWORLD_EFFECTS) > 0)
            .process(join -> {
              join.getWorld().getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
                holder.addPlayerUUID((Player) join.getEntity());
              });
            }).subscribe();
  }

  // Server Handlers
  public static void setupServer() {
    EventManager.forge(RegisterCommandsEvent.class)
            .process(register -> EssenceCommands.registerCommands(register.getDispatcher()))
            .subscribe();
    EventManager.forge(ServerStartingEvent.class)
            .process(event -> setupCreativeTabIcons())
            .subscribe();
  }

  public static void setupCreativeTabIcons() {
    CORE_TAB.addIconStacks(
            () -> new ItemStack(EssenceFluidRegistrate.ESSENCE.get().getBucket()),
            () -> new ItemStack(EssenceBlockRegistrate.ESSENCE_WOOD_SAPLING.get()),
            () -> new ItemStack(EssenceBlockRegistrate.ESSENCE_WOOD_LEAVES.get()),
            () -> new ItemStack(EssenceBlockRegistrate.ESSENCE_WOOD_LOG.get()),
            () -> new ItemStack(EssenceBlockRegistrate.ESSENCE_WOOD_PLANKS.get())
    );

    TOOL_TAB.addIconStacks(
            () -> new ItemStack(EssenceItemRegistrate.ESSENCE_AXE.get()),
            () -> new ItemStack(EssenceItemRegistrate.ESSENCE_PICKAXE.get()),
            () -> new ItemStack(EssenceItemRegistrate.ESSENCE_SHOVEL.get()),
            () -> new ItemStack(EssenceItemRegistrate.ESSENCE_SWORD.get()),
            () -> new ItemStack(EssenceItemRegistrate.ESSENCE_HOE.get()),
            () -> new ItemStack(EssenceItemRegistrate.ESSENCE_OMNITOOL.get()),
            () -> new ItemStack(EssenceItemRegistrate.ESSENCE_SHEAR.get()),
            () -> new ItemStack(EssenceItemRegistrate.ESSENCE_BOW.get())
    );
  }

  public static void setupWorldgen() {
    // Add Ores to Overworld
    EventManager.forge(BiomeLoadingEvent.class)
            .filter(biome -> {
              Set<ResourceKey<Biome>> biomes = BiomeDictionary.getBiomes(Type.OVERWORLD);
              return biomes.stream().anyMatch(key -> key.getRegistryName().compareTo(biome.getName()) > 0);
            })
            .process(biome -> {
              EssenceOreGenConfig oreGenConfig = EssenceWorldGenConfig.getOreGenConfig();
              EssenceTreeGenConfig treeGenConfig = EssenceWorldGenConfig.getTreeGenConfig();
              List<Supplier<PlacedFeature>> oregen = biome.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);
              List<Supplier<PlacedFeature>> vegetation = biome.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION);
              if (oreGenConfig.getEssenceOre().getShouldGenerate().get()) {
                oregen.add(() -> EssenceOreFeatures.ORE_ESSENCE_SMALL);
                oregen.add(() -> EssenceOreFeatures.ORE_ESSENCE_MIDDLE);
                oregen.add(() -> EssenceOreFeatures.ORE_ESSENCE_UPPER);
              }
              if (oreGenConfig.getEssenceCrystalOre().getShouldGenerate().get()) {
                  oregen.add(() -> EssenceOreFeatures.ORE_ESSENCE_CRYSTAL_SMALL);
                  oregen.add(() -> EssenceOreFeatures.ORE_ESSENCE_CRYSTAL_MIDDLE);
                  oregen.add(() -> EssenceOreFeatures.ORE_ESSENCE_CRYSTAL_UPPER);
              }
              if (treeGenConfig.getNormalVariant().getShouldGenerate().get()) {
                vegetation.add(() -> EssenceTreeFeatures.NORMAL_ESSENCE_TREE_FEATURE_PLACED);
              }
              if (treeGenConfig.getFancyVariant().getShouldGenerate().get()) {
                vegetation.add(() -> EssenceTreeFeatures.FANCY_ESSENCE_TREE_FEATURE_PLACED);
              }
            }).subscribe();
  }

  @OnlyIn(Dist.CLIENT)
  public static void setupClientEventHandlers() {
    // Atlas Texture Handler
    EventManager.mod(TextureStitchEvent.Pre.class)
            .filter(stitch -> TextureAtlas.LOCATION_BLOCKS.equals(stitch.getAtlas().location()))
            .process(stitch -> {
              stitch.addSprite(InfusionTableTESR.BOOK_TEXTURE);
            }).subscribe();

    // Rainbow Tooltip Handler
    EventManager.forge(RenderTooltipEvent.Color.class)
            .process(color -> {
              boolean isShear = color.getItemStack().getItem() instanceof EssenceShear;
              boolean hasRainbow = EssenceItemstackModifierHelpers.hasRainbowModifier(color.getItemStack());
              if (isShear && hasRainbow) {
                EssenceShear shear = (EssenceShear) color.getItemStack().getItem();
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
              if (minecraft.player != null && EssenceInformationHelper.isSneakKeyDown()) {
                ItemStack stack = minecraft.player.getMainHandItem();
                if (stack.getItem() instanceof EssenceWrench) {
                  double scrolling = scroll.getScrollDelta();
                  if (scrolling != 0) {
                    EssenceWrench wrench = (EssenceWrench) stack.getItem();
                    WrenchModeEnum mode = wrench.getMode();
                    WrenchModeEnum newMode = WrenchModeEnum.cycleMode(mode.ordinal());
                    wrench.setMode(newMode);
                    minecraft.player.displayClientMessage(new TranslatableComponent("wrench.essence.mode.tooltip").append(": ").append(new TranslatableComponent(newMode.getLocaleName())), true);
                    Essence.handler.sendToServer(new PacketItemStack(InteractionHand.MAIN_HAND, Collections.singletonList(newMode)));
                    scroll.setCanceled(true);
                  }
                }
              }
            }).subscribe();

    // Experience-Tome Handler
    EventManager.forge(InputEvent.MouseScrollEvent.class)
            .process(scroll -> {
              Minecraft minecraft = Minecraft.getInstance();
              if (minecraft.player != null && EssenceInformationHelper.isSneakKeyDown()) {
                ItemStack stack = minecraft.player.getMainHandItem();
                if (stack.getItem() instanceof TomeOfExperienceItem) {
                  double scrolling = scroll.getScrollDelta();
                  if (scrolling != 0) {
                    TomeOfExperienceItem tome = (TomeOfExperienceItem) stack.getItem();
                    ExperienceModeEnum mode = tome.getMode();
                    ExperienceModeEnum newMode = ExperienceModeEnum.cycleMode(mode.ordinal());
                    tome.setMode(newMode);
                    minecraft.player.displayClientMessage(new TranslatableComponent("tome.essence.mode.tooltip").append(": ").append(new TranslatableComponent(newMode.getLocaleString())), true);
                    Essence.handler.sendToServer(new PacketItemStack(InteractionHand.MAIN_HAND, Collections.singletonList(newMode)));
                    scroll.setCanceled(true);
                  }
                }
              }
            }).subscribe();
  }
}
