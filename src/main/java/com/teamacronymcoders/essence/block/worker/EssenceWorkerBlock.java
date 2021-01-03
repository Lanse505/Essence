package com.teamacronymcoders.essence.block.worker;

import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModifiedBlock;
import com.teamacronymcoders.essence.api.modifier.block.BlockCoreModifier;
import com.teamacronymcoders.essence.util.tier.IEssenceBaseTier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;


public abstract class EssenceWorkerBlock<T extends ActiveTile<T>> extends BasicTileBlock<T> implements IModifiedBlock {

    private final IEssenceBaseTier tier;
    private final int baseModifiers;
    private int freeModifiers;
    private int additionalModifiers;

    public EssenceWorkerBlock(IEssenceBaseTier tier, Class<T> tileClass) {
        super(Properties.create(Material.ROCK), tileClass);
        this.tier = tier;
        this.baseModifiers = tier.getFreeModifiers();
        this.freeModifiers = tier.getFreeModifiers();
        this.additionalModifiers = 0;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray) {
        ActionResultType result = ActionResultType.PASS;
        Optional<T> tile = getTile(worldIn, pos);
        if (worldIn.isRemote() || !tile.isPresent()) {
            return result;
        }
        result = tile.map(T -> {
            T.openGui(player);
            return ActionResultType.SUCCESS;
        }).orElse(ActionResultType.PASS);
        return result;
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        return state.getBlockHardness(worldIn, pos) + getTile(worldIn, pos).map(tile -> getBlockHardnessFromModifiers(state, worldIn, pos, tile, state.getBlockHardness(worldIn, pos))).orElse(0f);
    }

    @Override
    public float getExplosionResistance(BlockState state, IBlockReader reader, BlockPos pos, Explosion explosion) {
        return state.getExplosionResistance(reader, pos, explosion) + getTile(reader, pos).map(tile -> getExplosionResistanceFromModifiers(state, reader, pos, explosion.getExploder(), explosion, tile, state.getExplosionResistance(reader, pos, explosion))).orElse(0f);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getLightValue(world, pos) + getTile(world, pos).map(tile -> getLightValueFromModifiers(state, world, pos, tile, state.getLightValue(world, pos))).orElse(0);
    }

    @Override
    public boolean canHarvestBlock(BlockState state, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return true;
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 0;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> list, ITooltipFlag flag) {
        addInformation(stack, reader, list, flag, tier);
    }

    @Override
    public void addModifierWithoutIncreasingAdditional(int increase) {
        freeModifiers += increase;
    }

    @Override
    public void increaseFreeModifiers(int increase) {
        freeModifiers += increase;
        additionalModifiers += increase;
    }

    @Override
    public boolean decreaseFreeModifiers(int decrease) {
        if (freeModifiers - decrease < 0) {
            return false;
        }
        freeModifiers -= decrease;
        return true;
    }

    @Override
    public int getFreeModifiers() {
        return freeModifiers;
    }

    @Override
    public int getMaxModifiers() {
        return baseModifiers + additionalModifiers;
    }

    @Override
    public boolean recheck(Block object, List<ModifierInstance<Block>> modifierInstances) {
        int cmc = 0;
        for (ModifierInstance<Block> instance : modifierInstances) {
            if (instance.getModifier() instanceof BlockCoreModifier) {
                cmc += instance.getModifier().getModifierCountValue(instance.getLevel(), object);
            }
        }
        return cmc <= baseModifiers + additionalModifiers;
    }

    @Override
    public Class<Block> getType() {
        return Block.class;
    }
}
