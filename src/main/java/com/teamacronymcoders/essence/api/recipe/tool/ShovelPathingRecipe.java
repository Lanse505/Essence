package com.teamacronymcoders.essence.api.recipe.tool;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.BlockFlags;

public class ShovelPathingRecipe extends SerializableRecipe {

  public static GenericSerializer<ShovelPathingRecipe> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MOD_ID, "shovel_pathing"), ShovelPathingRecipe.class);
  public static List<ShovelPathingRecipe> RECIPES = new ArrayList<>();

  static {
    RECIPES.add(new ShovelPathingRecipe(new ResourceLocation(Essence.MOD_ID, "dirt_to_path"), Blocks.DIRT, Blocks.GRASS_PATH.getDefaultState()));
    RECIPES.add(new ShovelPathingRecipe(new ResourceLocation(Essence.MOD_ID, "coarse_dirt_to_path"), Blocks.COARSE_DIRT, Blocks.GRASS_PATH.getDefaultState()));
    RECIPES.add(new ShovelPathingRecipe(new ResourceLocation(Essence.MOD_ID, "grass_to_path"), Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.getDefaultState()));
    RECIPES.add(new ShovelPathingRecipe(new ResourceLocation(Essence.MOD_ID, "podzol_to_path"), Blocks.PODZOL, Blocks.GRASS_PATH.getDefaultState()));
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
  public boolean matches(IInventory inv, World worldIn) {
    return false;
  }

  @Override
  public ItemStack getCraftingResult(IInventory inv) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean canFit(int width, int height) {
    return false;
  }

  @Override
  public ItemStack getRecipeOutput() {
    return ItemStack.EMPTY;
  }

  @Override
  public GenericSerializer<? extends SerializableRecipe> getSerializer() {
    return SERIALIZER;
  }

  @Override
  public IRecipeType<?> getType() {
    return SERIALIZER.getRecipeType();
  }

  public boolean matches(Block block) {
    return block == from;
  }

  public ActionResultType resolveRecipe(ItemUseContext context) {
    World world = context.getWorld();
    BlockPos blockpos = context.getPos();
    PlayerEntity player = context.getPlayer();
    ItemStack stack = context.getItem();
    if (context.getFace() != Direction.DOWN && world.isAirBlock(blockpos.up())) {
      if (to != null) {
        world.playSound(player, blockpos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
        if (!world.isRemote()) {
          // If the state is unchanged after firing the forge hook then use the recipe provided state
          world.setBlockState(blockpos, to, BlockFlags.DEFAULT_AND_RERENDER);
          if (player != null) {
            stack.damageItem(1, player, (playerIn) -> {
              player.sendBreakAnimation(context.getHand());
            });
          }
          return ActionResultType.SUCCESS;
        }
      }
    }
    return ActionResultType.PASS;
  }

  public Block getFrom() {
    return from;
  }

  public BlockState getTo() {
    return to;
  }
}
