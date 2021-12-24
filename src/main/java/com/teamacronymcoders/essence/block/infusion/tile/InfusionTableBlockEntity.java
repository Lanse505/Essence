package com.teamacronymcoders.essence.block.infusion.tile;

import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.recipe.infusion.ExtendableInfusionRecipe;
import com.teamacronymcoders.essence.block.infusion.InfusionPedestalBlock;
import com.teamacronymcoders.essence.item.tome.TomeOfKnowledgeItem;
import com.teamacronymcoders.essence.registrate.EssenceBlockRegistrate;
import com.teamacronymcoders.essence.util.helper.EssenceWorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class InfusionTableBlockEntity extends ActiveTile<InfusionTableBlockEntity> {

  private static final BlockPos[] pedestal_positions = new BlockPos[] {
          new BlockPos(-4, 0, 0),
          new BlockPos(+4, 0, 0),
          new BlockPos(0, 0, +4),
          new BlockPos(0, 0, -4),
          new BlockPos(+3, 0, -3),
          new BlockPos(+3, 0, +3),
          new BlockPos(-3, 0, +3),
          new BlockPos(-3, 0, -3)
  };

  private ExtendableInfusionRecipe recipe;

  @Save
  private Boolean shouldBeWorking = false;
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
  public float field_195523_f;
  public float field_195524_g;
  public float rawSpeedIncrement;
  public float clampedSpeedIncrement;
  public float nextPageTurningSpeed;
  public float pageTurningSpeed;
  public float nextPageAngle;
  public float pageAngle;
  public float playerTableAngle;
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

  @Override
  public void serverTick(Level level, BlockPos pos, BlockState state, InfusionTableBlockEntity blockEntity) {
    super.serverTick(level, pos, state, blockEntity);
    ticksExisted++;
    handleBookRender();
    if (shouldBeWorking || isWorking) {
      if (!recipe.isValid(getPedestalStacks())) {
        NonNullList<ItemStack> stacks = getPedestalStacks();
        getInfusionRecipe(stacks);
        totalWorkDuration = recipe.duration;
      }
      isWorking = true;
      if (workDuration >= totalWorkDuration) {
        ItemStack infusable = this.infusable.getStackInSlot(0);
        recipe.resolveRecipe(infusable);
        shouldBeWorking = false;
        isWorking = false;
        hasFiredSound = false;
      }
      if (isWorking && !hasFiredSound) {
        EssenceWorldHelper.playInfusionSound(this, true);
        hasFiredSound = true;
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

  private NonNullList<ItemStack> getPedestalStacks() {
    BlockPos tablePosition = getBlockPos();
    NonNullList<ItemStack> stacks = NonNullList.create();
    if (getLevel() != null) {
      for (BlockPos pos : pedestal_positions) {
        BlockPos pedestalPosition = tablePosition.offset(pos.getX(), pos.getY(), pos.getZ());
        if (getLevel().getBlockState(pedestalPosition).getBlock() instanceof InfusionPedestalBlock && getLevel().getBlockEntity(pedestalPosition) instanceof InfusionPedestalBlockEntity) {
          InfusionPedestalBlockEntity pedestal = (InfusionPedestalBlockEntity) getLevel().getBlockEntity(pos);
          if (pedestal != null) {
            stacks.add(pedestal.getStack());
          }
        }
      }
    }
    return stacks;
  }

  private void getInfusionRecipe(NonNullList<ItemStack> stacks) {
    if (getLevel() != null) {
      recipe = getLevel().getRecipeManager().getRecipes()
              .stream()
              .filter(iRecipe -> iRecipe instanceof ExtendableInfusionRecipe)
              .map(iRecipe -> (ExtendableInfusionRecipe) iRecipe)
              .filter(recipes -> recipes.isValid(stacks))
              .findFirst().orElse(null);
    }
  }

  private void handleBookRender() {
    this.pageTurningSpeed = this.nextPageTurningSpeed;
    this.pageAngle = this.nextPageAngle;
    if (getLevel() == null) {
      return;
    }
    Player player = this.level.getNearestPlayer(((float) this.worldPosition.getX() + 0.5F), ((float) this.worldPosition.getY() + 0.5F), ((float) this.worldPosition.getZ() + 0.5F), 3.0D, false);
    if (player != null) {
      double rangeX = player.getX() - ((double) this.worldPosition.getX() + 0.5D);
      double rangeZ = player.getZ() - ((double) this.worldPosition.getZ() + 0.5D);
      this.playerTableAngle = (float) Mth.atan2(rangeZ, rangeX);
      this.nextPageTurningSpeed += 0.1F;
      if (this.nextPageTurningSpeed < 0.5F || Essence.RANDOM.nextInt(40) == 0) {
        float lvt_6_1_ = this.rawSpeedIncrement;
        do {
          this.rawSpeedIncrement += (float) (Essence.RANDOM.nextInt(4) - Essence.RANDOM.nextInt(4));
        } while (lvt_6_1_ == this.rawSpeedIncrement);
      }
    } else {
      this.playerTableAngle += 0.02F;
      this.nextPageTurningSpeed -= 0.1F;
    }

    if (player != null && level.isClientSide() && player.getLevel().getGameTime() - pageSoundLastPlayed > 160) {
      EssenceWorldHelper.playBookSound(this, true);
      pageSoundLastPlayed = player.getLevel().getGameTime();
    }

    while (this.nextPageAngle >= 3.1415927F) {
      this.nextPageAngle -= 6.2831855F;
    }

    while (this.nextPageAngle < -3.1415927F) {
      this.nextPageAngle += 6.2831855F;
    }

    while (this.playerTableAngle >= 3.1415927F) {
      this.playerTableAngle -= 6.2831855F;
    }

    while (this.playerTableAngle < -3.1415927F) {
      this.playerTableAngle += 6.2831855F;
    }

    float angleDelta;
    angleDelta = this.playerTableAngle - this.nextPageAngle;
    while (angleDelta >= 3.1415927F) {
      angleDelta -= 6.2831855F;
    }

    while (angleDelta < -3.1415927F) {
      angleDelta += 6.2831855F;
    }

    this.nextPageAngle += angleDelta * 0.4F;
    this.nextPageTurningSpeed = Mth.clamp(this.nextPageTurningSpeed, 0.0F, 1.0F);
    ++this.ticks;
    this.field_195524_g = this.field_195523_f;
    float speedDelta = (this.rawSpeedIncrement - this.field_195523_f) * 0.4F;
    speedDelta = Mth.clamp(speedDelta, -0.2F, 0.2F);
    this.clampedSpeedIncrement += (speedDelta - this.clampedSpeedIncrement) * 0.9F;
    this.field_195523_f += this.clampedSpeedIncrement;
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