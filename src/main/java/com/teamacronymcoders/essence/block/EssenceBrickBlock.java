package com.teamacronymcoders.essence.block;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.misc.IColorProvider;
import com.teamacronymcoders.essence.registrate.EssenceBlockRegistrate;
import com.teamacronymcoders.essence.util.helper.EssenceColorHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class EssenceBrickBlock extends Block implements IColorProvider {

  public static final Map<DyeColor, Supplier<EssenceBrickBlock>> dyeToColorMap = new HashMap<>();

  static {
    dyeToColorMap.put(DyeColor.WHITE, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_WHITE.get());
    dyeToColorMap.put(DyeColor.ORANGE, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_ORANGE.get());
    dyeToColorMap.put(DyeColor.MAGENTA, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_MAGENTA.get());
    dyeToColorMap.put(DyeColor.LIGHT_BLUE, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_LIGHT_BLUE.get());
    dyeToColorMap.put(DyeColor.YELLOW, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_YELLOW.get());
    dyeToColorMap.put(DyeColor.LIME, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_LIME.get());
    dyeToColorMap.put(DyeColor.PINK, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_PINK.get());
    dyeToColorMap.put(DyeColor.GRAY, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_GRAY.get());
    dyeToColorMap.put(DyeColor.LIGHT_GRAY, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_LIGHT_GRAY.get());
    dyeToColorMap.put(DyeColor.CYAN, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_CYAN.get());
    dyeToColorMap.put(DyeColor.PURPLE, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_PURPLE.get());
    dyeToColorMap.put(DyeColor.BLUE, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_BLUE.get());
    dyeToColorMap.put(DyeColor.BROWN, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_BROWN.get());
    dyeToColorMap.put(DyeColor.GREEN, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_GREEN.get());
    dyeToColorMap.put(DyeColor.RED, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_RED.get());
    dyeToColorMap.put(DyeColor.BLACK, () -> EssenceBlockRegistrate.ESSENCE_BRICKS_BLACK.get());
  }

  public final DyeColor color;

  public EssenceBrickBlock (Properties properties, DyeColor color) {
    super(properties);
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
  public DyeColor hasColor () {
    return this.color;
  }
}
