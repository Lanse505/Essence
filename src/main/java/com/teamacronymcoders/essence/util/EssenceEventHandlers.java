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
import com.teamacronymcoders.essence.command.EssenceCommands;
import com.teamacronymcoders.essence.generation.EssenceFeatureConfig;
import com.teamacronymcoders.essence.item.tome.experience.ExperienceModeEnum;
import com.teamacronymcoders.essence.item.tome.experience.TomeOfExperienceItem;
import com.teamacronymcoders.essence.item.tool.EssenceShear;
import com.teamacronymcoders.essence.item.wrench.EssenceWrench;
import com.teamacronymcoders.essence.item.wrench.WrenchModeEnum;
import com.teamacronymcoders.essence.serializable.loot.FieryLootModifier;
import com.teamacronymcoders.essence.serializable.recipe.infusion.InfusionTableSerializableRecipe;
import com.teamacronymcoders.essence.util.config.EssenceWorldGenConfig;
import com.teamacronymcoders.essence.util.config.subconfigs.EssenceOreGenConfig;
import com.teamacronymcoders.essence.util.config.subconfigs.EssenceTreeGenConfig;
import com.teamacronymcoders.essence.util.helper.EssenceColorHelper;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.util.keybindings.EssenceKeyHandler;
import com.teamacronymcoders.essence.util.network.message.PacketItemStack;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
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
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static com.teamacronymcoders.essence.Essence.MODID;

public class EssenceEventHandlers {

    public static EssenceEventHandlers handlers = new EssenceEventHandlers();

    public static void setup() {
        setupRegistries();
        setupModifierCapabilities();
        setupKnowledgeCapabilities();
        setupServer();
        setupWorldgen();
    }

    // Registration Handlers
    private static void setupRegistries() {
        EventManager.modGeneric(RegistryEvent.Register.class, IRecipeSerializer.class)
            .process(register -> {
                ((RegistryEvent.Register) register).getRegistry().registerAll(
                    AxeStrippingRecipe.SERIALIZER,
                    HoeTillingRecipe.SERIALIZER,
                    InfusionTableSerializableRecipe.SERIALIZER,
                    ShovelPathingRecipe.SERIALIZER
                );
            }).subscribe();
        EventManager.modGeneric(RegistryEvent.Register.class, GlobalLootModifierSerializer.class)
            .process(register -> {
                ((RegistryEvent.Register) register).getRegistry().registerAll(
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
        EventManager.forgeGeneric(AttachCapabilitiesEvent.class, ItemStack.class)
            .process(attach -> {
                if (((AttachCapabilitiesEvent) attach).getObject() instanceof IModifiedTool) {
                    ((AttachCapabilitiesEvent) attach).addCapability(new ResourceLocation(MODID, "item_modifier_holder"), new ItemStackModifierProvider((ItemStack) ((AttachCapabilitiesEvent) attach).getObject()));
                }
            }).subscribe();
        EventManager.forgeGeneric(AttachCapabilitiesEvent.class, Block.class)
            .process(attach -> {
                if (((AttachCapabilitiesEvent) attach).getObject() instanceof IModifiedBlock) {
                    ((AttachCapabilitiesEvent) attach).addCapability(new ResourceLocation(MODID, "block_modifier_holder"), new BlockModifierProvider());
                }
            }).subscribe();
    }

    private static void setupKnowledgeCapabilities() {
        EventManager.forgeGeneric(AttachCapabilitiesEvent.class, World.class)
            .filter(attach -> ((AttachCapabilitiesEvent) attach).getObject() instanceof World && ((World) ((AttachCapabilitiesEvent) attach).getObject()).getDimensionKey().getRegistryName().compareTo(DimensionType.OVERWORLD_ID) > 0)
            .process(attach -> {
                ((AttachCapabilitiesEvent) attach).addCapability(new ResourceLocation(MODID, "knowledge"), new KnowledgeProvider());
            }).subscribe();
        EventManager.forge(EntityJoinWorldEvent.class)
            .filter(join -> join.getEntity() instanceof PlayerEntity && join.getWorld().getDimensionKey().getRegistryName().compareTo(DimensionType.OVERWORLD_ID) > 0)
            .process(join -> {
                join.getWorld().getCapability(EssenceCapability.KNOWLEDGE).ifPresent(holder -> {
                    holder.addPlayerUUID((PlayerEntity) join.getEntity());
                });
            }).subscribe();
    }

    // Server Handlers
    public static void setupServer() {
        EventManager.forge(RegisterCommandsEvent.class)
            .process(register -> EssenceCommands.registerCommands(register.getDispatcher()))
            .subscribe();
    }

    public static void setupWorldgen() {
        // Add Ores to Overworld
        EventManager.forge(BiomeLoadingEvent.class)
            .filter(biome -> {
                Set<RegistryKey<Biome>> biomes = BiomeDictionary.getBiomes(Type.OVERWORLD);
                return biomes.stream().anyMatch(key -> key.getRegistryName().compareTo(biome.getName()) > 0);
            })
            .process(biome -> {
                EssenceOreGenConfig oreGenConfig = EssenceWorldGenConfig.getOreGenConfig();
                EssenceTreeGenConfig treeGenConfig = EssenceWorldGenConfig.getTreeGenConfig();
                List<Supplier<ConfiguredFeature<?, ?>>> oregen = biome.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES);
                List<Supplier<ConfiguredFeature<?, ?>>> vegetation = biome.getGeneration().getFeatures(Decoration.VEGETAL_DECORATION);
                if (oreGenConfig.getEssenceOre().getShouldGenerate().get()) {
                    oregen.add(() ->
                        Feature.ORE
                            .withConfiguration(new OreFeatureConfig(FillerBlockType.BASE_STONE_OVERWORLD, EssenceObjectHolders.ESSENCE_ORE.getDefaultState(), oreGenConfig.getEssenceOre().getMaxVeinSize().get()))
                            .withPlacement(Placement.RANGE_BIASED.configure(new TopSolidRangeConfig(
                                oreGenConfig.getEssenceOre().getBottomOffset().get(),
                                oreGenConfig.getEssenceOre().getTopOffset().get(),
                                oreGenConfig.getEssenceOre().getMaxHeight().get())))
                    );
                }
                if (oreGenConfig.getEssenceCrystalOre().getShouldGenerate().get()) {
                    oregen.add(() ->
                        Feature.ORE
                            .withConfiguration(new OreFeatureConfig(FillerBlockType.BASE_STONE_OVERWORLD, EssenceObjectHolders.ESSENCE_CRYSTAL_ORE.getDefaultState(), oreGenConfig.getEssenceCrystalOre().getMaxVeinSize().get()))
                            .withPlacement(Placement.RANGE_BIASED.configure(new TopSolidRangeConfig(
                                oreGenConfig.getEssenceCrystalOre().getBottomOffset().get(),
                                oreGenConfig.getEssenceCrystalOre().getTopOffset().get(),
                                oreGenConfig.getEssenceCrystalOre().getMaxHeight().get())))
                    );
                }
                if (treeGenConfig.getNormalVariant().getShouldGenerate().get()) {
                    vegetation.add(() ->
                        Feature.TREE
                            .withConfiguration(EssenceFeatureConfig.NORMAL_WORLD_ESSENCE_TREE_CONFIG)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(
                                0,
                                treeGenConfig.getNormalVariant().getExtraChance().get().floatValue(),
                                treeGenConfig.getNormalVariant().getExtraCount().get())))
                    );
                }
                if (treeGenConfig.getFancyVariant().getShouldGenerate().get()) {
                    vegetation.add(() ->
                        Feature.TREE
                            .withConfiguration(EssenceFeatureConfig.FANCY_WORLD_ESSENCE_TREE_CONFIG)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(
                                0,
                                treeGenConfig.getFancyVariant().getExtraChance().get().floatValue(),
                                treeGenConfig.getFancyVariant().getExtraCount().get())))
                    );
                }
            }).subscribe();
    }

    @OnlyIn(Dist.CLIENT)
    public static void setupClientEventHandlers() {
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
                if (minecraft.player != null && EssenceKeyHandler.CYCLING.isKeyDown()) {
                    ItemStack stack = minecraft.player.getHeldItemMainhand();
                    if (stack.getItem() instanceof EssenceWrench) {
                        double scrolling = scroll.getScrollDelta();
                        if (scrolling != 0) {
                            EssenceWrench wrench = (EssenceWrench) stack.getItem();
                            WrenchModeEnum mode = wrench.getMode();
                            WrenchModeEnum newMode = WrenchModeEnum.cycleMode(mode.ordinal());
                            wrench.setMode(newMode);
                            minecraft.player.sendStatusMessage(new TranslationTextComponent("wrench.essence.mode.tooltip").appendString(": ").append(new TranslationTextComponent(newMode.getLocaleName())), true);
                            Essence.handler.sendToServer(new PacketItemStack(Hand.MAIN_HAND, Collections.singletonList(newMode)));
                            scroll.setCanceled(true);
                        }
                    }
                }
            }).subscribe();

        // Essence-Tome Handler
        EventManager.forge(InputEvent.MouseScrollEvent.class)
            .process(scroll -> {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.player != null && EssenceKeyHandler.CYCLING.isKeyDown()) {
                    ItemStack stack = minecraft.player.getHeldItemMainhand();
                    if (stack.getItem() instanceof TomeOfExperienceItem) {
                        double scrolling = scroll.getScrollDelta();
                        if (scrolling != 0) {
                            TomeOfExperienceItem tome = (TomeOfExperienceItem) stack.getItem();
                            ExperienceModeEnum mode = tome.getMode();
                            ExperienceModeEnum newMode = ExperienceModeEnum.cycleMode(mode.ordinal());
                            tome.setMode(newMode);
                            minecraft.player.sendStatusMessage(new TranslationTextComponent("tome.essence.mode.tooltip").appendString(": ").append(new TranslationTextComponent(newMode.getLocaleString())), true);
                            Essence.handler.sendToServer(new PacketItemStack(Hand.MAIN_HAND, Collections.singletonList(newMode)));
                            scroll.setCanceled(true);
                        }
                    }
                }
            }).subscribe();
    }
}
