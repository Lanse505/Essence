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
