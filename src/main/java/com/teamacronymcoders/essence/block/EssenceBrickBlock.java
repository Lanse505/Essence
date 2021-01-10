package com.teamacronymcoders.essence.block;

import com.hrznstudio.titanium.block.BasicBlock;
import com.hrznstudio.titanium.datagenerator.loot.block.BasicBlockLootTables;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.misc.IColorProvider;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceItemTags;
import com.teamacronymcoders.essence.util.helper.EssenceColorHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.Constants;

public class EssenceBrickBlock extends BasicBlock implements IColorProvider {

  private static final Map<DyeColor, Supplier<EssenceBrickBlock>> dyeToColorMap = new HashMap<>();

  static {
    dyeToColorMap.put(DyeColor.WHITE, () -> EssenceObjectHolders.ESSENCE_BRICKS_WHITE);
    dyeToColorMap.put(DyeColor.ORANGE, () -> EssenceObjectHolders.ESSENCE_BRICKS_ORANGE);
    dyeToColorMap.put(DyeColor.MAGENTA, () -> EssenceObjectHolders.ESSENCE_BRICKS_MAGENTA);
    dyeToColorMap.put(DyeColor.LIGHT_BLUE, () -> EssenceObjectHolders.ESSENCE_BRICKS_LIGHT_BLUE);
    dyeToColorMap.put(DyeColor.YELLOW, () -> EssenceObjectHolders.ESSENCE_BRICKS_YELLOW);
    dyeToColorMap.put(DyeColor.LIME, () -> EssenceObjectHolders.ESSENCE_BRICKS_LIME);
    dyeToColorMap.put(DyeColor.PINK, () -> EssenceObjectHolders.ESSENCE_BRICKS_PINK);
    dyeToColorMap.put(DyeColor.GRAY, () -> EssenceObjectHolders.ESSENCE_BRICKS_GRAY);
    dyeToColorMap.put(DyeColor.LIGHT_GRAY, () -> EssenceObjectHolders.ESSENCE_BRICKS_LIGHT_GRAY);
    dyeToColorMap.put(DyeColor.CYAN, () -> EssenceObjectHolders.ESSENCE_BRICKS_CYAN);
    dyeToColorMap.put(DyeColor.PURPLE, () -> EssenceObjectHolders.ESSENCE_BRICKS_PURPLE);
    dyeToColorMap.put(DyeColor.BLUE, () -> EssenceObjectHolders.ESSENCE_BRICKS_BLUE);
    dyeToColorMap.put(DyeColor.BROWN, () -> EssenceObjectHolders.ESSENCE_BRICKS_BROWN);
    dyeToColorMap.put(DyeColor.GREEN, () -> EssenceObjectHolders.ESSENCE_BRICKS_GREEN);
    dyeToColorMap.put(DyeColor.RED, () -> EssenceObjectHolders.ESSENCE_BRICKS_RED);
    dyeToColorMap.put(DyeColor.BLACK, () -> EssenceObjectHolders.ESSENCE_BRICKS_BLACK);
  }

  public final DyeColor color;

  public EssenceBrickBlock (DyeColor color) {
    super(Block.Properties.create(Material.ROCK).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 1200F));
    this.color = color;
  }

  @SuppressWarnings("deprecation")
  @Override
  public ActionResultType onBlockActivated (BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
    if (worldIn.isRemote) {
      return ActionResultType.PASS;
    }
    ItemStack stack = player.getHeldItem(handIn);
    List<DyeColor> colors = EssenceColorHelper.tagToDye.keySet().stream()
            .filter(tagToDye -> stack.getItem().getTags().size() > 0)
            .filter(stack.getItem()::isIn)
            .map(EssenceColorHelper.tagToDye::get)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    return colors != null && colors.size() > 0 ? colors.stream()
            .skip(Essence.RANDOM.nextInt(colors.size()))
            .findFirst()
            .map(dyeToColorMap::get)
            .map(Supplier::get)
            .map(essenceBrickBlock -> {
              if (!state.equals(essenceBrickBlock.getDefaultState())) {
                worldIn.setBlockState(pos, essenceBrickBlock.getDefaultState());
                worldIn.notifyBlockUpdate(pos, state, essenceBrickBlock.getDefaultState(), Constants.BlockFlags.NOTIFY_NEIGHBORS);
              }
              return ActionResultType.SUCCESS;
            })
            .orElse(ActionResultType.PASS) : ActionResultType.PASS;
  }

  @Override
  public MaterialColor getMaterialColor () {
    return super.getMaterialColor();
  }

  @Override
  public Builder getLootTable (@Nonnull BasicBlockLootTables blockLootTables) {
    return blockLootTables.droppingSelf(this);
  }

  @Override
  public void registerRecipe (Consumer<IFinishedRecipe> consumer) {
    TitaniumShapedRecipeBuilder.shapedRecipe(this, 8)
            .patternLine("bbb").patternLine("bdb").patternLine("bbb")
            .key('b', EssenceItemTags.ESSENCE_BRICKS).key('d', color.getTag())
            .build(consumer, new ResourceLocation(getRegistryName().getNamespace(), getRegistryName().getPath() + "_recolor"));
  }

  @Override
  public DyeColor hasColor () {
    return this.color;
  }
}
