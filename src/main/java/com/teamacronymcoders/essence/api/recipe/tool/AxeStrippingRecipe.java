package com.teamacronymcoders.essence.api.recipe.tool;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.helper.EssenceBlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

public class AxeStrippingRecipe extends SerializableRecipe {

  public static final GenericSerializer<AxeStrippingRecipe> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MOD_ID, "axe_stripping"), AxeStrippingRecipe.class);
  public static final List<AxeStrippingRecipe> RECIPES = new ArrayList<>();

  static {
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "oak_wood_to_stripped"), Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD.defaultBlockState()));
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "oak_log_to_stripped"), Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG.defaultBlockState()));
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "dark_oak_wood_to_stripped"), Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD.defaultBlockState()));
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "dark_oak_log_to_stripped"), Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG.defaultBlockState()));
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "acacia_wood_to_stripped"), Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD.defaultBlockState()));
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "acacia_log_to_stripped"), Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG.defaultBlockState()));
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "birch_wood_to_stripped"), Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD.defaultBlockState()));
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "birch_log_to_stripped"), Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG.defaultBlockState()));
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "jungle_wood_to_stripped"), Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD.defaultBlockState()));
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "jungle_log_to_stripped"), Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG.defaultBlockState()));
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "spruce_wood_to_stripped"), Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD.defaultBlockState()));
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "spruce_log_to_stripped"), Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG.defaultBlockState()));
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "crimson_stem_to_stripped"), Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM.defaultBlockState()));
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "crimson_hyphae_to_stripped"), Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE.defaultBlockState()));
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "warped_stem_to_stripped"), Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM.defaultBlockState()));
    RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MOD_ID, "warped_hyphae_to_stripped"), Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE.defaultBlockState()));
  }

  public Block from;
  public BlockState to;

  public AxeStrippingRecipe(ResourceLocation resourceLocation) {
    super(resourceLocation);
  }

  public AxeStrippingRecipe(ResourceLocation resourceLocation, Block from, BlockState to) {
    super(resourceLocation);
    this.from = from;
    this.to = to;
  }

  @Override
  public boolean matches(Container container, Level level) {
    return false;
  }

  @Override
  public ItemStack assemble(Container container) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean canCraftInDimensions(int width, int height) {
    return false;
  }

  @Override
  public ItemStack getResultItem() {
    return ItemStack.EMPTY;
  }

  @Override
  public GenericSerializer<? extends SerializableRecipe> getSerializer() {
    return SERIALIZER;
  }

  @Override
  public RecipeType<?> getType() {
    return SERIALIZER.getRecipeType();
  }

  public boolean matches(Block block) {
    return block == from;
  }

  @SuppressWarnings("unchecked")
  public InteractionResult resolveRecipe(UseOnContext context) {
    Level level = context.getLevel();
    BlockPos blockpos = context.getClickedPos();
    Player player = context.getPlayer();
    ItemStack stack = context.getItemInHand();
    BlockState targetedState = level.getBlockState(blockpos);
    if (context.getClickedFace() != Direction.DOWN && level.isEmptyBlock(blockpos.above())) {
      if (to != null) {
        level.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (!level.isClientSide()) {
          BlockState state = to;
          for (Entry<Property<?>, Comparable<?>> entry : EssenceBlockHelper.getCommonProperties(targetedState, to).entrySet()) {
            Property property = state.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
            final Optional<Comparable> propValue;
            if (property != null) {
              propValue = property.getValue(entry.getValue().toString());
              propValue.ifPresent(comparable -> to = to.setValue(property, comparable));
            }
          }
          level.setBlock(blockpos, to, Block.UPDATE_ALL_IMMEDIATE);
          if (player != null) {
            stack.hurtAndBreak(1, player, (playerIn) -> {
              playerIn.broadcastBreakEvent(context.getHand());
            });
          }
          return InteractionResult.SUCCESS;
        }
      }
    }
    return InteractionResult.PASS;
  }

  public Block getFrom() {
    return from;
  }

  public BlockState getTo() {
    return to;
  }
}
