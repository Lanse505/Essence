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

public class HoeTillingRecipe extends SerializableRecipe {

  public static GenericSerializer<HoeTillingRecipe> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MOD_ID, "hoe_tilling"), HoeTillingRecipe.class);
  public static List<HoeTillingRecipe> RECIPES = new ArrayList<>();

  static {
    RECIPES.add(new HoeTillingRecipe(new ResourceLocation(Essence.MOD_ID, "grass_to_farmland"), Blocks.GRASS_BLOCK, Blocks.FARMLAND.getDefaultState()));
    RECIPES.add(new HoeTillingRecipe(new ResourceLocation(Essence.MOD_ID, "path_to_farmland"), Blocks.GRASS_PATH, Blocks.FARMLAND.getDefaultState()));
    RECIPES.add(new HoeTillingRecipe(new ResourceLocation(Essence.MOD_ID, "dirt_to_farmland"), Blocks.DIRT, Blocks.FARMLAND.getDefaultState()));
    RECIPES.add(new HoeTillingRecipe(new ResourceLocation(Essence.MOD_ID, "coarse_dirt_to_farmland"), Blocks.COARSE_DIRT, Blocks.FARMLAND.getDefaultState()));
  }

  public Block from;
  public BlockState to;

  public HoeTillingRecipe (ResourceLocation resourceLocation) {
    super(resourceLocation);
  }

  public HoeTillingRecipe (ResourceLocation resourceLocation, Block from, BlockState to) {
    super(resourceLocation);
    this.from = from;
    this.to = to;
  }

  @Override
  public boolean matches (IInventory inv, World worldIn) {
    return false;
  }

  @Override
  public ItemStack getCraftingResult (IInventory inv) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean canFit (int width, int height) {
    return false;
  }

  @Override
  public ItemStack getRecipeOutput () {
    return ItemStack.EMPTY;
  }

  @Override
  public GenericSerializer<? extends SerializableRecipe> getSerializer () {
    return SERIALIZER;
  }

  @Override
  public IRecipeType<?> getType () {
    return SERIALIZER.getRecipeType();
  }

  public boolean matches (Block block) {
    return block == from;
  }

  public ActionResultType resolveRecipe (ItemUseContext context) {
    World world = context.getWorld();
    BlockPos blockpos = context.getPos();
    PlayerEntity playerentity = context.getPlayer();
    ItemStack stack = context.getItem();
    if (context.getFace() != Direction.DOWN && world.isAirBlock(blockpos.up())) {
      if (to != null) {
        world.playSound(playerentity, blockpos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
        if (!world.isRemote) {
          // If the state is unchanged after firing the forge hook then use the recipe provided state
          world.setBlockState(blockpos, to, BlockFlags.DEFAULT_AND_RERENDER);
          if (playerentity != null) {
            stack.damageItem(1, playerentity, (player) -> {
              player.sendBreakAnimation(context.getHand());
            });
          }
          return ActionResultType.SUCCESS;
        }
      }
    }
    return ActionResultType.PASS;
  }

  public Block getFrom () {
    return from;
  }

  public BlockState getTo () {
    return to;
  }
}
