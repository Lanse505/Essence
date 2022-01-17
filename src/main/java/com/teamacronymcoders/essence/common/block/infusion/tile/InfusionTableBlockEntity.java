package com.teamacronymcoders.essence.common.block.infusion.tile;

import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.recipe.infusion.ExtendableInfusionRecipe;
import com.teamacronymcoders.essence.common.block.infusion.InfusionPedestalBlock;
import com.teamacronymcoders.essence.common.item.tome.TomeOfKnowledgeItem;
import com.teamacronymcoders.essence.common.util.helper.EssenceWorldHelper;
import com.teamacronymcoders.essence.compat.registrate.EssenceBlockRegistrate;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public class InfusionTableBlockEntity extends ActiveTile<InfusionTableBlockEntity> {

    private static final BlockPos[] pedestal_positions = new BlockPos[]{
            new BlockPos(-3, 0, 0),
            new BlockPos(+3, 0, 0),
            new BlockPos(0, 0, +3),
            new BlockPos(0, 0, -3),
            new BlockPos(+2, 0, -2),
            new BlockPos(+2, 0, +2),
            new BlockPos(-2, 0, +2),
            new BlockPos(-2, 0, -2)
    };

    @Save
    private String recipe;

    @Save
    private Boolean shouldBeWorking = false;
    @Save
    private Integer shouldBeWorkingTicks = 0;
    @Save
    private Boolean isWorking = false;
    @Save
    private Integer workDuration = 0;
    @Save
    private Integer totalWorkDuration = 0;
    @Save
    private Boolean hasFiredSound = false;
    @Save
    private Integer ticksExisted = 0;

    @Save
    private final InventoryComponent<InfusionTableBlockEntity> infusable;
    @Save
    private final InventoryComponent<InfusionTableBlockEntity> tome;

    // Book Rendering Variables
    public int ticks;
    public float flip;
    public float oldFlip;
    public float flipT;
    public float flipA;
    public float open;
    public float oldOpen;
    public float rot;
    public float oldRot;
    public float tRot;
    public int particleTick = 0;

    @Save
    public Long pageSoundLastPlayed = 0L;

    public InfusionTableBlockEntity(BlockPos pos, BlockState state) {
        super(EssenceBlockRegistrate.INFUSION_TABLE.get(), pos, state);
        addInventory(infusable = new InventoryComponent<InfusionTableBlockEntity>("input", 80, 20, 1)
                .setComponentHarness(this)
                .setOutputFilter(this::canExtractInfusable)
                .setSlotLimit(1)
        );
        addInventory(tome = new InventoryComponent<InfusionTableBlockEntity>("tome", 9, 10, 1)
                .setComponentHarness(this)
                .setOnSlotChanged((stack, integer) -> markComponentForUpdate(false))
                .setInputFilter((stack, integer) -> false)
                .setOutputFilter((stack, integer) -> false)
                .setSlotLimit(1)
        );
    }

    public static void tick(Level level, BlockPos pos, BlockState state, InfusionTableBlockEntity blockEntity) {
        blockEntity.ticksExisted++;
        if (level.isClientSide()) {
            runAnimationTickCycle(pos, blockEntity);
        } else {
            runLogicTickCycle(blockEntity);
        }

    }

    public static void runLogicTickCycle(InfusionTableBlockEntity be) {
        NonNullList<ItemStack> stacks = be.getPedestalStacks();
        if (be.shouldBeWorking && !be.isWorking && be.recipe == null)
            be.getInfusionRecipe(be.getInfusable().getStackInSlot(0), stacks);
        if (be.shouldBeWorking && be.recipe == null) be.shouldBeWorkingTicks++;
        if (be.shouldBeWorkingTicks >= 40) {
            be.shouldBeWorking = false;
            be.shouldBeWorkingTicks = 0;
        }
        be.markComponentForUpdate(false);
        if (be.recipe != null && (be.shouldBeWorking || be.isWorking)) {
            ExtendableInfusionRecipe recipe = (ExtendableInfusionRecipe) be.getLevel().getRecipeManager().byKey(new ResourceLocation(be.recipe)).get();
            be.markComponentDirty();
            if (be.totalWorkDuration == 0) {
                be.totalWorkDuration = recipe.duration;
                be.markComponentForUpdate(true);
            }
            if (!be.isWorking) {
                be.isWorking = true;
                be.markComponentForUpdate(true);
            }
            if (be.workDuration >= be.totalWorkDuration) {
                ItemStack infusable = be.infusable.getStackInSlot(0);
                ItemStack result = recipe.resolveRecipe(be, infusable);
                be.infusable.setStackInSlot(0, result);
                be.shouldBeWorking = false;
                be.isWorking = false;
                be.hasFiredSound = false;
                be.workDuration = 0;
                be.totalWorkDuration = 0;
                be.recipe = null;
                be.markComponentForUpdate(false);
            } else {
                be.workDuration++;
            }
            if (be.isWorking && !be.hasFiredSound) {
                EssenceWorldHelper.playInfusionSound(be, true);
                be.hasFiredSound = true;
            }
        }

    }

    public static void runAnimationTickCycle(BlockPos pos, InfusionTableBlockEntity blockEntity) {
        blockEntity.oldOpen = blockEntity.open;
        blockEntity.oldRot = blockEntity.rot;
        Player player = blockEntity.getLevel().getNearestPlayer((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 3.0D, false);
        if (player != null) {
            double d0 = player.getX() - ((double) pos.getX() + 0.5D);
            double d1 = player.getZ() - ((double) pos.getZ() + 0.5D);
            blockEntity.tRot = (float) Mth.atan2(d1, d0);
            blockEntity.open += 0.1F;
            if (blockEntity.open < 0.5F || Essence.RANDOM.nextInt(40) == 0) {
                float f1 = blockEntity.flipT;
                do {
                    blockEntity.flipT += (float) (Essence.RANDOM.nextInt(4) - Essence.RANDOM.nextInt(4));
                } while (f1 == blockEntity.flipT);
            }
        } else {
            blockEntity.tRot += 0.02F;
            blockEntity.open -= 0.1F;
        }

        while (blockEntity.rot >= (float) Math.PI) {
            blockEntity.rot -= ((float) Math.PI * 2F);
        }

        while (blockEntity.rot < -(float) Math.PI) {
            blockEntity.rot += ((float) Math.PI * 2F);
        }

        while (blockEntity.tRot >= (float) Math.PI) {
            blockEntity.tRot -= ((float) Math.PI * 2F);
        }

        while (blockEntity.tRot < -(float) Math.PI) {
            blockEntity.tRot += ((float) Math.PI * 2F);
        }

        float f2;
        for (f2 = blockEntity.tRot - blockEntity.rot; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F)) {
        }

        while (f2 < -(float) Math.PI) {
            f2 += ((float) Math.PI * 2F);
        }

        blockEntity.rot += f2 * 0.4F;
        blockEntity.open = Mth.clamp(blockEntity.open, 0.0F, 1.0F);
        ++blockEntity.ticks;
        blockEntity.oldFlip = blockEntity.flip;
        float f = (blockEntity.flipT - blockEntity.flip) * 0.4F;
        float f3 = 0.2F;
        f = Mth.clamp(f, -f3, f3);
        blockEntity.flipA += (f - blockEntity.flipA) * 0.9F;
        blockEntity.flip += blockEntity.flipA;

        if (blockEntity.isWorking) {
            if (blockEntity.particleTick >= 3) {
                for (InfusionPedestalBlockEntity pedestal : blockEntity.getPedestals()) {
                    if(pedestal.getStack().isEmpty()){
                        continue;
                    }
                    ClientLevel level = (ClientLevel) blockEntity.getLevel();
                    BlockPos targetPosition = blockEntity.getBlockPos().above(2);
                    BlockPos pedestalPosition = pedestal.getBlockPos();
                    Vec3 targetVec = new Vec3(targetPosition.getX(), targetPosition.getY(), targetPosition.getZ());
                    Vec3 pedestalVec = new Vec3(pedestalPosition.getX(), pedestalPosition.getY(), pedestalPosition.getZ());
                    Vec3 subtractedVec = targetVec.subtract(pedestalVec);
                    float delta = blockEntity.workDuration > 0 && blockEntity.totalWorkDuration > 0 ? Mth.clamp((blockEntity.workDuration / blockEntity.totalWorkDuration), 0, 1) : 0;
                    Vec3 lerpedVec = subtractedVec.lerp(new Vec3(0, 0, 0), delta);
                    level.addParticle(
                            new ItemParticleOption(ParticleTypes.ITEM, pedestal.getStack()),
                            pedestalPosition.getX() + 0.5, pedestalPosition.getY() + 1.25, pedestalPosition.getZ() + 0.5,
                            lerpedVec.x * 0.1, lerpedVec.y * 0.15, lerpedVec.z * 0.1
                    );
                    blockEntity.particleTick = 0;
                }
            } else {
                blockEntity.particleTick++;
            }
        }
    }

    @Nonnull
    @Override
    public InfusionTableBlockEntity getSelf() {
        return this;
    }

    private boolean canExtractInfusable(ItemStack stack, int slot) {
        return !isWorking;
    }

    public NonNullList<InfusionPedestalBlockEntity> getPedestals() {
        BlockPos tablePosition = this.getBlockPos();
        NonNullList<InfusionPedestalBlockEntity> pedestals = NonNullList.create();
        if (getLevel() != null) {
            for (BlockPos pos : pedestal_positions) {
                BlockPos pedestalPosition = tablePosition.offset(pos.getX(), pos.getY(), pos.getZ());
                if (getLevel().getBlockState(pedestalPosition).getBlock() instanceof InfusionPedestalBlock && getLevel().getBlockEntity(pedestalPosition) instanceof InfusionPedestalBlockEntity pedestal) {
                    pedestals.add(pedestal);
                }
            }
        }
        return pedestals;
    }

    public NonNullList<ItemStack> getPedestalStacks() {
        BlockPos tablePosition = this.getBlockPos();
        NonNullList<ItemStack> stacks = NonNullList.create();
        if (getLevel() != null) {
            for (BlockPos pos : pedestal_positions) {
                BlockPos pedestalPosition = tablePosition.offset(pos.getX(), pos.getY(), pos.getZ());
                if (getLevel().getBlockState(pedestalPosition).getBlock() instanceof InfusionPedestalBlock && getLevel().getBlockEntity(pedestalPosition) instanceof InfusionPedestalBlockEntity pedestal) {
                    stacks.add(pedestal.getStack());
                }
            }
        }
        return stacks;
    }

    private void getInfusionRecipe(ItemStack infusable, NonNullList<ItemStack> stacks) {
        if (getLevel() != null) {
            getLevel().getRecipeManager().getRecipes()
                    .stream()
                    .filter(iRecipe -> iRecipe instanceof ExtendableInfusionRecipe)
                    .map(iRecipe -> (ExtendableInfusionRecipe) iRecipe)
                    .filter(recipes -> recipes.isValid(infusable, stacks))
                    .findFirst().ifPresent(recipe -> this.recipe = recipe.getId().toString());
        }
    }

    public Boolean getWorking() {
        return isWorking;
    }

    public InventoryComponent<InfusionTableBlockEntity> getTome() {
        return tome;
    }

    public InventoryComponent<InfusionTableBlockEntity> getInfusable() {
        return infusable;
    }

    public void setShouldBeWorking(Boolean shouldBeWorking) {
        this.shouldBeWorking = shouldBeWorking;
    }

    public boolean hasTome() {
        return !tome.getStackInSlot(0).isEmpty() && tome.getStackInSlot(0).getItem() instanceof TomeOfKnowledgeItem;
    }

    public Integer getTicksExisted() {
        return ticksExisted;
    }

    public Long getPageSoundLastPlayed() {
        return pageSoundLastPlayed;
    }

}