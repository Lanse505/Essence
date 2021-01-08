package com.teamacronymcoders.essence.api.recipe.tool;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.helper.EssenceBlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.state.Property;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.Constants.BlockFlags;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

public class AxeStrippingRecipe extends SerializableRecipe {

    public static GenericSerializer<AxeStrippingRecipe> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MODID, "axe_stripping"), AxeStrippingRecipe.class);
    public static List<AxeStrippingRecipe> RECIPES = new ArrayList<>();

    static {
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "oak_wood_to_stripped"), Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD.getDefaultState()));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "oak_log_to_stripped"), Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG.getDefaultState()));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "dark_oak_wood_to_stripped"), Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD.getDefaultState()));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "dark_oak_log_to_stripped"), Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG.getDefaultState()));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "acacia_wood_to_stripped"), Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD.getDefaultState()));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "acacia_log_to_stripped"), Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG.getDefaultState()));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "birch_wood_to_stripped"), Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD.getDefaultState()));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "birch_log_to_stripped"), Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG.getDefaultState()));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "jungle_wood_to_stripped"), Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD.getDefaultState()));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "jungle_log_to_stripped"), Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG.getDefaultState()));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "spruce_wood_to_stripped"), Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD.getDefaultState()));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "spruce_log_to_stripped"), Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG.getDefaultState()));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "crimson_stem_to_stripped"), Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM.getDefaultState()));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "crimson_hyphae_to_stripped"), Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE.getDefaultState()));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "warped_stem_to_stripped"), Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM.getDefaultState()));
        RECIPES.add(new AxeStrippingRecipe(new ResourceLocation(Essence.MODID, "warped_hyphae_to_stripped"), Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE.getDefaultState()));
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
        BlockState targetedState = world.getBlockState(blockpos);
        if (context.getFace() != Direction.DOWN && world.isAirBlock(blockpos.up())) {
            if (to != null) {
                world.playSound(player, blockpos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!world.isRemote()) {
                    // If the state is unchanged after firing the forge hook then use the recipe provided state
                    BlockState state = to;
                    for (Entry<Property<?>, Comparable<?>> entry : EssenceBlockHelper.getCommonProperties(targetedState, to).entrySet()) {
                        Property property = state.getBlock().getStateContainer().getProperty(entry.getKey().getName());
                        final Optional<Comparable> propValue = property.parseValue(entry.getValue().toString());
                        if (propValue.isPresent()) to = to.with(property, propValue.get());
                    }
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
