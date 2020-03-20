package com.teamacronymcoders.essence.serializable.recipe.tool;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
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

import java.util.ArrayList;
import java.util.List;

public class HoeTillingRecipe extends SerializableRecipe {

    public static GenericSerializer<HoeTillingRecipe> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MODID, "hoe_tilling"), HoeTillingRecipe.class);
    public static List<HoeTillingRecipe> RECIPES = new ArrayList<>();

    static {
        RECIPES.add(new HoeTillingRecipe(new ResourceLocation(Essence.MODID, "grass_to_farmland"), Blocks.GRASS_BLOCK, Blocks.FARMLAND.getDefaultState()));
        RECIPES.add(new HoeTillingRecipe(new ResourceLocation(Essence.MODID, "path_to_farmland"), Blocks.GRASS_PATH, Blocks.FARMLAND.getDefaultState()));
        RECIPES.add(new HoeTillingRecipe(new ResourceLocation(Essence.MODID, "dirt_to_farmland"), Blocks.DIRT, Blocks.FARMLAND.getDefaultState()));
        RECIPES.add(new HoeTillingRecipe(new ResourceLocation(Essence.MODID, "coarse_dirt_to_farmland"), Blocks.COARSE_DIRT, Blocks.FARMLAND.getDefaultState()));
    }

    public Block from;
    public BlockState to;

    public HoeTillingRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    public HoeTillingRecipe(ResourceLocation resourceLocation, Block from, BlockState to) {
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
        int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
        if (hook != 0) {
            return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
        }
        if (context.getFace() != Direction.DOWN && world.isAirBlock(blockpos.up())) {
            if (to != null) {
                PlayerEntity playerentity = context.getPlayer();
                world.playSound(playerentity, blockpos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!world.isRemote) {
                    world.setBlockState(blockpos, to, 11);
                    if (playerentity != null) {
                        context.getItem().damageItem(1, playerentity, (p_220043_1_) -> {
                            p_220043_1_.sendBreakAnimation(context.getHand());
                        });
                    }
                }
                return ActionResultType.SUCCESS;
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
