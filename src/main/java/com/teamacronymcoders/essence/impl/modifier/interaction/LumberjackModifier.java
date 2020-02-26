package com.teamacronymcoders.essence.impl.modifier.interaction;

import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LumberjackModifier extends InteractionCoreModifier {
    private static Tag<Block>[] TARGET_TAGS = new Tag[] {
        BlockTags.LOGS, new BlockTags.Wrapper(new ResourceLocation("forge:logs"))
    };

    public LumberjackModifier() {
        super(1);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, int level) {
        
        return super.onBlockDestroyed(stack, world, state, pos, miner, level);
    }


}
