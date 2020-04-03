package com.teamacronymcoders.essence.util.helper;

import com.teamacronymcoders.essence.block.infusion.tile.InfusionTableTile;
import com.teamacronymcoders.essence.effect.sound.InfusionSound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EssenceWorldHelper {

    /**
     * @param world
     * @param pos
     * @param hasTileEntity
     * @param entity
     * @param stack
     * @return
     */
    public static boolean breakBlock(World world, BlockPos pos, boolean hasTileEntity, @Nullable Entity entity, ItemStack stack) {
        BlockState blockstate = world.getBlockState(pos);
        if (blockstate.isAir(world, pos)) {
            return false;
        } else {
            IFluidState ifluidstate = world.getFluidState(pos);
            world.playEvent(2001, pos, Block.getStateId(blockstate));
            if (hasTileEntity) {
                TileEntity tileentity = blockstate.hasTileEntity() ? world.getTileEntity(pos) : null;
                Block.spawnDrops(blockstate, world, pos, tileentity, entity, stack);
            }

            return world.setBlockState(pos, ifluidstate.getBlockState(), 3);
        }
    }

    /**
     * Gets a tile entity if the location is loaded
     *
     * @param world - world
     * @param pos   - position
     * @return tile entity if found, null if either not found or not loaded
     */
    @Nullable
    public static TileEntity getTileEntity(@Nullable World world, @Nonnull BlockPos pos) {
        if (world == null || !world.isBlockPresent(pos)) {
            return null;
        }
        return world.getTileEntity(pos);
    }

    public static Set<Biome> getBiomes(BiomeDictionary.Type type, BiomeDictionary.Type... filterTypes) {
        Set<Biome> biomes = BiomeDictionary.getBiomes(type);
        if (filterTypes.length == 0) {
            return biomes;
        }
        Set<Biome> filtering = new HashSet<>(biomes);
        for (BiomeDictionary.Type fType : filterTypes) {
            Set<Biome> filterBiomes = BiomeDictionary.getBiomes(fType);
            filtering.retainAll(filterBiomes);
        }
        return filtering;
    }

    public static Set<Biome> getBiomes(BiomeDictionary.Type type, List<BiomeDictionary.Type> filterTypes) {
        Set<Biome> biomes = BiomeDictionary.getBiomes(type);
        if (filterTypes.size() == 0) {
            return biomes;
        }
        Set<Biome> filtering = new HashSet<>(biomes);
        for (BiomeDictionary.Type fType : filterTypes) {
            Set<Biome> filterBiomes = BiomeDictionary.getBiomes(fType);
            filtering.retainAll(filterBiomes);
        }
        return filtering;
    }

    @OnlyIn(Dist.CLIENT)
    public static void playInfusionSound(@Nonnull InfusionTableTile tableTile, boolean distanceDelay) {
        double sqdt = Minecraft.getInstance().gameRenderer.getActiveRenderInfo()
            .getProjectedView().squareDistanceTo(tableTile.getPos().getX(), tableTile.getPos().getY(), tableTile.getPos().getZ());
        InfusionSound sound = new InfusionSound(tableTile);
        if (distanceDelay && sqdt > 100.0F) {
            double sqrt = Math.sqrt(sqdt) / 40.0D;
            Minecraft.getInstance().getSoundHandler().playDelayed(sound, (int)(sqrt * 20.0D));
        } else {
            Minecraft.getInstance().getSoundHandler().play(sound);
        }
    }

}
