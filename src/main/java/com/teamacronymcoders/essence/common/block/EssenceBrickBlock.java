package com.teamacronymcoders.essence.common.block;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.misc.IColorProvider;
import com.teamacronymcoders.essence.compat.registrate.EssenceBlockRegistrate;
import com.teamacronymcoders.essence.common.util.helper.EssenceColorHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

    public EssenceBrickBlock(Properties properties, DyeColor color) {
        super(properties);
        this.color = color;
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult result) {
        if (level.isClientSide()) {
            return InteractionResult.PASS;
        }
        ItemStack stack = player.getItemInHand(handIn);
        List<DyeColor> colors = EssenceColorHelper.tagToDye.keySet().stream()
                .filter(tagToDye -> stack.getItem().getTags().size() > 0)
                .filter(stack::is)
                .map(EssenceColorHelper.tagToDye::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return colors.size() > 0 ? colors.stream()
                .skip(Essence.RANDOM.nextInt(colors.size()))
                .findFirst()
                .map(dyeToColorMap::get)
                .map(Supplier::get)
                .map(essenceBrickBlock -> {
                    if (!state.equals(essenceBrickBlock.defaultBlockState())) {
                        level.setBlock(pos, essenceBrickBlock.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                    }
                    return InteractionResult.SUCCESS;
                })
                .orElse(InteractionResult.PASS) : InteractionResult.PASS;
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }
}
