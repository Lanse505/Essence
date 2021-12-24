package com.teamacronymcoders.essence.api.recipe.tool;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ShovelPathingRecipe extends SerializableRecipe {

  public static GenericSerializer<ShovelPathingRecipe> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MOD_ID, "shovel_pathing"), ShovelPathingRecipe.class);
  public static List<ShovelPathingRecipe> RECIPES = new ArrayList<>();

  static {
    RECIPES.add(new ShovelPathingRecipe(new ResourceLocation(Essence.MOD_ID, "dirt_to_path"), Blocks.DIRT, Blocks.DIRT_PATH.defaultBlockState()));
    RECIPES.add(new ShovelPathingRecipe(new ResourceLocation(Essence.MOD_ID, "coarse_dirt_to_path"), Blocks.COARSE_DIRT, Blocks.DIRT_PATH.defaultBlockState()));
    RECIPES.add(new ShovelPathingRecipe(new ResourceLocation(Essence.MOD_ID, "grass_to_path"), Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.defaultBlockState()));
    RECIPES.add(new ShovelPathingRecipe(new ResourceLocation(Essence.MOD_ID, "podzol_to_path"), Blocks.PODZOL, Blocks.DIRT_PATH.defaultBlockState()));
  }

  public Block from;
  public BlockState to;

  public ShovelPathingRecipe(ResourceLocation resourceLocation) {
    super(resourceLocation);
  }

  public ShovelPathingRecipe(ResourceLocation resourceLocation, Block from, BlockState to) {
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

  public InteractionResult resolveRecipe(UseOnContext context) {
    Level world = context.getLevel();
    BlockPos blockpos = context.getClickedPos();
    Player player = context.getPlayer();
    ItemStack stack = context.getItemInHand();
    if (context.getClickedFace() != Direction.DOWN && world.isEmptyBlock(blockpos.above())) {
      if (to != null) {
        world.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (!world.isClientSide()) {
          // If the state is unchanged after firing the forge hook then use the recipe provided state
          world.setBlock(blockpos, to, Block.UPDATE_ALL_IMMEDIATE);
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
