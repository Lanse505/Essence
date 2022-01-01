package com.teamacronymcoders.essence.common.block.infusion;

import com.hrznstudio.titanium.block.BasicTileBlock;
import com.teamacronymcoders.essence.common.block.infusion.tile.InfusionTableBlockEntity;
import com.teamacronymcoders.essence.common.item.tome.TomeOfKnowledgeItem;
import com.teamacronymcoders.essence.common.item.wrench.EssenceWrench;
import com.teamacronymcoders.essence.common.item.wrench.WrenchModeEnum;
import com.teamacronymcoders.essence.common.util.EssenceBlockModels;
import com.teamacronymcoders.essence.compat.registrate.EssenceBlockRegistrate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class InfusionTableBlock extends BasicTileBlock<InfusionTableBlockEntity> {

    public InfusionTableBlock(BlockBehaviour.Properties properties) {
        super("infusion_table", properties, InfusionTableBlockEntity.class);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        InfusionTableBlockEntity te = level.getBlockEntity(pos) instanceof InfusionTableBlockEntity ? (InfusionTableBlockEntity) level.getBlockEntity(pos) : null;
        if (te != null) {
            ItemStack stack = player.getItemInHand(hand);

            if (!stack.isEmpty() && stack.getItem() instanceof EssenceWrench wrench) {
                if (wrench.getMode() == WrenchModeEnum.TRIGGER) {
                    te.setShouldBeWorking(true);
                }
                return InteractionResult.SUCCESS;
            }

            // Handle Player Inventory -> Block Inventory
            if (!stack.isEmpty() && stack.getItem() instanceof TomeOfKnowledgeItem && !te.hasTome()) {
                ItemStack copy = stack.copy();
                te.getTome().setStackInSlot(0, copy);
                stack.shrink(1);
                te.markComponentForUpdate(false);
                return InteractionResult.SUCCESS;
            } else if (!stack.isEmpty() && te.getInfusable().getStackInSlot(0).isEmpty()) {
                ItemStack copy = stack.copy();
                te.getInfusable().setStackInSlot(0, copy);
                stack.shrink(1);
                te.markComponentForUpdate(false);
                return InteractionResult.SUCCESS;
            }

            // Handle Block Inventory -> Player Inventory
            if (stack.isEmpty() && te.hasTome() && player.isDiscrete()) {
                ItemStack copy = te.getTome().getStackInSlot(0);
                player.addItem(copy);
                te.getTome().setStackInSlot(0, ItemStack.EMPTY);
                te.markComponentForUpdate(false);
                return InteractionResult.SUCCESS;
            } else if (stack.isEmpty() && !te.getInfusable().getStackInSlot(0).isEmpty()) {
                ItemStack copy = te.getInfusable().getStackInSlot(0).copy();
                player.addItem(copy);
                te.getInfusable().setStackInSlot(0, ItemStack.EMPTY);
                te.markComponentForUpdate(false);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return InfusionTableBlockEntity::new;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        super.animateTick(state, level, pos, random);
        InfusionTableBlockEntity te = level.getBlockEntity(pos) instanceof InfusionTableBlockEntity ? (InfusionTableBlockEntity) level.getBlockEntity(pos) : null;
        if (te != null && te.getWorking()) {
            for (int i = -2; i <= 2; ++i) {
                for (int j = -2; j <= 2; ++j) {
                    if (i > -2 && i < 2 && j == -1) {
                        j = 2;
                    }
                    if (random.nextInt(16) == 0) {
                        for (int k = 0; k <= 1; ++k) {
                            if (!level.isEmptyBlock(pos.offset(i / 2, 0, j / 2))) {
                                break;
                            }
                            level.addParticle(ParticleTypes.ENCHANT, (double) pos.getX() + 0.5D, (double) pos.getY() + 2.0D, (double) pos.getZ() + 0.5D, (double) ((float) i + random.nextFloat()) - 0.5D, (float) k - random.nextFloat() - 1.0F, (double) ((float) j + random.nextFloat()) - 0.5D);
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    @ParametersAreNonnullByDefault
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
        return EssenceBlockModels.INFUSION_TABLE[0];
    }

    @Nonnull
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
        return EssenceBlockModels.INFUSION_TABLE[0];
    }

    @Override
    public List<VoxelShape> getBoundingBoxes(BlockState state, BlockGetter getter, BlockPos pos) {
        return Collections.singletonList(EssenceBlockModels.INFUSION_TABLE[0]);
    }

    @Override
    public BlockEntityType getTileEntityType() {
        return EssenceBlockRegistrate.INFUSION_TABLE_TILE.get();
    }

    @Nullable
    @Override
    public <R extends BlockEntity> BlockEntityTicker<R> getTicker(Level level, BlockState state, BlockEntityType<R> type) {
        return createTickerHelper(type, EssenceBlockRegistrate.INFUSION_TABLE_TILE.get(), InfusionTableBlockEntity::tick);
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> typeA, BlockEntityType<E> typeB, BlockEntityTicker<? super E> ticker) {
        return typeB == typeA ? (BlockEntityTicker<A>)ticker : null;
    }
}
