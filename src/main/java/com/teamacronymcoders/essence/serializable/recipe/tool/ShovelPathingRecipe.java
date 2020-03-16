package com.teamacronymcoders.essence.serializable.recipe.tool;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ShovelPathingRecipe extends SerializableRecipe {

    public static GenericSerializer<ShovelPathingRecipe> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MODID, "shovel_pathing"), ShovelPathingRecipe.class);
    public static List<ShovelPathingRecipe> RECIPES = new ArrayList<>();

    static {
        RECIPES.add(new ShovelPathingRecipe(new ResourceLocation(Essence.MODID, "dirt_to_path"), Blocks.DIRT, Blocks.GRASS_PATH.getDefaultState()));
        RECIPES.add(new ShovelPathingRecipe(new ResourceLocation(Essence.MODID, "coarse_dirt_to_path"), Blocks.COARSE_DIRT, Blocks.GRASS_PATH.getDefaultState()));
        RECIPES.add(new ShovelPathingRecipe(new ResourceLocation(Essence.MODID, "grass_to_path"), Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.getDefaultState()));
        RECIPES.add(new ShovelPathingRecipe(new ResourceLocation(Essence.MODID, "podzol_to_path"), Blocks.PODZOL, Blocks.GRASS_PATH.getDefaultState()));
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
        PlayerEntity playerentity = context.getPlayer();
        BlockState blockstate = to;
        if (blockstate != null && world.isAirBlock(blockpos.up())) {
            world.playSound(playerentity, blockpos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        if (blockstate != null) {
            if (!world.isRemote) {
                world.setBlockState(blockpos, blockstate, 11);
                if (playerentity != null) {
                    context.getItem().damageItem(1, playerentity, (p_220041_1_) -> {
                        p_220041_1_.sendBreakAnimation(context.getHand());
                    });
                }
            }
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.PASS;
        }
    }

    public Block getFrom() {
        return from;
    }

    public BlockState getTo() {
        return to;
    }
}
