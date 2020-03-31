package com.teamacronymcoders.essence.block.infusion.tile;

import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.block.infusion.InfusionPedestalBlock;
import com.teamacronymcoders.essence.item.misc.TomeOfKnowledgeItem;
import com.teamacronymcoders.essence.serializable.recipe.infusion.ExtendableInfusionRecipe;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.helper.EssenceWorldHelper;
import com.teamacronymcoders.essence.util.registration.EssenceSoundRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class InfusionTableTile extends ActiveTile<InfusionTableTile> {

    private static final BlockPos[] pedestal_positions = new BlockPos[]{
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
    private InventoryComponent<InfusionTableTile> infusable;
    @Save
    private InventoryComponent<InfusionTableTile> tome;

    // Book Rendering Variables
    public int field_195522_a;
    public float field_195523_f;
    public float field_195524_g;
    public float field_195525_h;
    public float field_195526_i;
    public float field_195527_j;
    public float field_195528_k;
    public float field_195529_l;
    public float field_195530_m;
    public float field_195531_n;
    @Save
    public Integer pageSoundLastPlayed = 0;

    public InfusionTableTile() {
        super(EssenceObjectHolders.INFUSION_TABLE);
        addInventory(infusable = new InventoryComponent<InfusionTableTile>("input", 80, 20, 1)
            .setComponentHarness(this)
            .setOutputFilter(this::canExtractInfusable)
            .setSlotLimit(1)
        );
        addInventory(tome = new InventoryComponent<InfusionTableTile>("tome", 9, 10, 1)
            .setComponentHarness(this)
            .setOnSlotChanged((stack, integer) -> markComponentForUpdate())
            .setInputFilter((stack, integer) -> false)
            .setOutputFilter((stack, integer) -> false)
            .setSlotLimit(1)
        );
    }

    @Override
    public void tick() {
        super.tick();
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
    public InfusionTableTile getSelf() {
        return this;
    }

    private boolean canExtractInfusable(ItemStack stack, int slot) {
        return !isWorking;
    }

    private NonNullList<ItemStack> getPedestalStacks() {
        BlockPos tablePosition = getPos();
        NonNullList<ItemStack> stacks = NonNullList.create();
        if (getWorld() != null) {
            for (BlockPos pos : pedestal_positions) {
                BlockPos pedestalPosition = tablePosition.add(pos.getX(), pos.getY(), pos.getZ());
                if (getWorld().getBlockState(pedestalPosition).getBlock() instanceof InfusionPedestalBlock && getWorld().getTileEntity(pedestalPosition) instanceof InfusionPedestalTile) {
                    InfusionPedestalTile pedestal = (InfusionPedestalTile) getWorld().getTileEntity(pos);
                    if (pedestal != null) {
                        stacks.add(pedestal.getStack());
                    }
                }
            }
        }
        return stacks;
    }

    private void getInfusionRecipe(NonNullList<ItemStack> stacks) {
        if (getWorld() != null) {
            recipe = getWorld().getRecipeManager().getRecipes()
                .stream()
                .filter(iRecipe -> iRecipe instanceof ExtendableInfusionRecipe)
                .map(iRecipe -> (ExtendableInfusionRecipe) iRecipe)
                .filter(recipes -> recipes.isValid(stacks))
                .findFirst().orElse(null);
        }
    }

    private PlayerEntity player;

    private void handleBookRender() {
        this.field_195528_k = this.field_195527_j;
        this.field_195530_m = this.field_195529_l;
        if (world == null) return;
        PlayerEntity player = this.world.getClosestPlayer(((float)this.pos.getX() + 0.5F), ((float)this.pos.getY() + 0.5F), ((float)this.pos.getZ() + 0.5F), 3.0D, false);
        if (player != null) {
            this.player = player;
            double lvt_2_1_ = player.getPosX() - ((double)this.pos.getX() + 0.5D);
            double lvt_4_1_ = player.getPosZ() - ((double)this.pos.getZ() + 0.5D);
            this.field_195531_n = (float) MathHelper.atan2(lvt_4_1_, lvt_2_1_);
            this.field_195527_j += 0.1F;
            if (this.field_195527_j < 0.5F || Essence.RANDOM.nextInt(40) == 0) {
                float lvt_6_1_ = this.field_195525_h;
                do {
                    this.field_195525_h += (float)(Essence.RANDOM.nextInt(4) - Essence.RANDOM.nextInt(4));
                } while(lvt_6_1_ == this.field_195525_h);
            }
        } else {
            this.player = null;
            this.field_195531_n += 0.02F;
            this.field_195527_j -= 0.1F;
        }

        if (player != null && !world.isRemote()) {
            world.playSound(player, pos, EssenceSoundRegistration.INFUSION_BOOK_SOUND.get(), SoundCategory.BLOCKS, 1f, 1f);
        }

        while(this.field_195529_l >= 3.1415927F) {
            this.field_195529_l -= 6.2831855F;
        }

        while(this.field_195529_l < -3.1415927F) {
            this.field_195529_l += 6.2831855F;
        }

        while(this.field_195531_n >= 3.1415927F) {
            this.field_195531_n -= 6.2831855F;
        }

        while(this.field_195531_n < -3.1415927F) {
            this.field_195531_n += 6.2831855F;
        }

        float lvt_2_2_;
        lvt_2_2_ = this.field_195531_n - this.field_195529_l;
        while (lvt_2_2_ >= 3.1415927F) {
            lvt_2_2_ -= 6.2831855F;
        }

        while(lvt_2_2_ < -3.1415927F) {
            lvt_2_2_ += 6.2831855F;
        }

        this.field_195529_l += lvt_2_2_ * 0.4F;
        this.field_195527_j = MathHelper.clamp(this.field_195527_j, 0.0F, 1.0F);
        ++this.field_195522_a;
        this.field_195524_g = this.field_195523_f;
        float lvt_3_1_ = (this.field_195525_h - this.field_195523_f) * 0.4F;
        lvt_3_1_ = MathHelper.clamp(lvt_3_1_, -0.2F, 0.2F);
        this.field_195526_i += (lvt_3_1_ - this.field_195526_i) * 0.9F;
        this.field_195523_f += this.field_195526_i;
    }

    public Boolean getWorking() {
        return isWorking;
    }

    public InventoryComponent<InfusionTableTile> getTome() {
        return tome;
    }

    public InventoryComponent<InfusionTableTile> getInfusable() {
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
}