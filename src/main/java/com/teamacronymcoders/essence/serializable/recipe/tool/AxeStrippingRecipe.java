package com.teamacronymcoders.essence.serializable.recipe.tool;

import com.google.common.collect.ImmutableMap;
import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
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

public class AxeStrippingRecipe extends SerializableRecipe {

    public static GenericSerializer<AxeStrippingRecipe> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MODID, "axe_stripping"), AxeStrippingRecipe.class);
    public static List<AxeStrippingRecipe> RECIPES = new ArrayList<>();

    static {
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "oak_wood_to_stripped"), Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "oak_log_to_stripped"), Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "dark_oak_wood_to_stripped"), Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "dark_oak_log_to_stripped"), Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "acacia_wood_to_stripped"), Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "acacia_log_to_stripped"), Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "birch_wood_to_stripped"), Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "birch_log_to_stripped"), Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "jungle_wood_to_stripped"), Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "jungle_log_to_stripped"), Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "spruce_wood_to_stripped"), Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "spruce_log_to_stripped"), Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG));
    }

    public Block from;
    public Block to;

    public AxeStrippingRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    public AxeStrippingRecipe(ResourceLocation resourceLocation, Block from, Block to) {
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
        BlockState blockstate = world.getBlockState(blockpos);
        if (blockstate.getBlock() == from) {
            PlayerEntity playerentity = context.getPlayer();
            world.playSound(playerentity, blockpos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isRemote) {
                if (from instanceof RotatedPillarBlock && to instanceof RotatedPillarBlock) {
                    world.setBlockState(blockpos, to.getDefaultState().with(RotatedPillarBlock.AXIS, blockstate.get(RotatedPillarBlock.AXIS)), 11);
                } else {
                    world.setBlockState(blockpos, to.getDefaultState(), 11);
                }
                if (playerentity != null) {
                    context.getItem().damageItem(1, playerentity, (p_220040_1_) -> {
                        p_220040_1_.sendBreakAnimation(context.getHand());
                    });
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    public Block getFrom() {
        return from;
    }

    public Block getTo() {
        return to;
    }
}
