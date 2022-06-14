package com.teamacronymcoders.essence.common.util.helper;

import com.teamacronymcoders.essence.client.sound.InfusionBookSound;
import com.teamacronymcoders.essence.client.sound.InfusionSound;
import com.teamacronymcoders.essence.common.block.infusion.tile.InfusionTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EssenceWorldHelper {

    /**
     * @param level
     * @param pos
     * @param hasBlockEntity
     * @param entity
     * @param stack
     * @return
     */
    public static boolean breakBlock(Level level, BlockPos pos, boolean hasBlockEntity, @Nullable Entity entity, ItemStack stack) {
        BlockState blockstate = level.getBlockState(pos);
        if (blockstate.isAir()) {
            return false;
        } else {
            FluidState fluidState = level.getFluidState(pos);
            level.levelEvent(2001, pos, Block.getId(blockstate));
            if (hasBlockEntity) {
                BlockEntity blockEntity = blockstate.hasBlockEntity() ? level.getBlockEntity(pos) : null;
                Block.dropResources(blockstate, level, pos, blockEntity, entity, stack);
            }

            return level.setBlock(pos, fluidState.createLegacyBlock(), 3);
        }
    }

    /**
     * Gets a tile entity if the location is loaded
     *
     * @param level - level
     * @param pos   - position
     * @return tile entity if found, null if either not found or not loaded
     */
    @Nullable
    public static BlockEntity getBlockEntity(@Nullable Level level, @Nonnull BlockPos pos) {
        if (level == null || !level.isLoaded(pos)) {
            return null;
        }
        return level.getBlockEntity(pos);
    }

    @OnlyIn(Dist.CLIENT)
    public static void playInfusionSound(@Nonnull InfusionTableBlockEntity tableTile, boolean distanceDelay) {
        double sqdt = Minecraft.getInstance().gameRenderer.getMainCamera()
                .getPosition().distanceToSqr(tableTile.getBlockPos().getX(), tableTile.getBlockPos().getY(), tableTile.getBlockPos().getZ());
        InfusionSound sound = new InfusionSound(tableTile);
        if (distanceDelay && sqdt > 100.0F) {
            double sqrt = Math.sqrt(sqdt) / 40.0D;
            Minecraft.getInstance().getSoundManager().playDelayed(sound, (int) (sqrt * 20.0D));
        } else {
            Minecraft.getInstance().getSoundManager().play(sound);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void playBookSound(@Nonnull InfusionTableBlockEntity tableTile, boolean distanceDelay) {
        double sqdt = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition().distanceToSqr(tableTile.getBlockPos().getX(), tableTile.getBlockPos().getY(), tableTile.getBlockPos().getZ());
        InfusionBookSound sound = new InfusionBookSound(tableTile);
        if (distanceDelay && sqdt > 100.0F) {
            double sqrt = Math.sqrt(sqdt) / 40.0D;
            Minecraft.getInstance().getSoundManager().playDelayed(sound, (int) (sqrt * 20.0D));
        } else {
            Minecraft.getInstance().getSoundManager().play(sound);
        }
    }


}
