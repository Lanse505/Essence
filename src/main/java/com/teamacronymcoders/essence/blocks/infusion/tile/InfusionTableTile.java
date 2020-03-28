package com.teamacronymcoders.essence.blocks.infusion.tile;

import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.serializable.recipe.infusion.InfusionTableSerializableRecipe;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import com.teamacronymcoders.essence.utils.EssenceTags;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

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

    public static Tag<Item>[] VALID_INPUT = new Tag[]{
        EssenceTags.Items.ESSENCE_AXE, EssenceTags.Items.ESSENCE_BOW, EssenceTags.Items.ESSENCE_HOE,
        EssenceTags.Items.ESSENCE_OMNITOOL, EssenceTags.Items.ESSENCE_PICKAXE, EssenceTags.Items.ESSENCE_SHEAR,
        EssenceTags.Items.ESSENCE_SHOVEL, EssenceTags.Items.ESSENCE_SWORD
    };

    private List<InfusionTableSerializableRecipe> recipes = new ArrayList<>();

    @Save
    private Boolean shouldBeWorking = false;
    @Save
    private Boolean isWorking = false;
    @Save
    private Integer workDuration = 0;

    @Save
    private SidedInventoryComponent<InfusionTableTile> infusable;
    @Save
    private InventoryComponent<InfusionTableTile> tome;
    @Save
    private InventoryComponent<InfusionTableTile> infusion_array;
    @Save
    private ProgressBarComponent<InfusionTableTile> progressBar;

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

    public InfusionTableTile() {
        super(EssenceObjectHolders.INFUSION_TABLE);
        addInventory(infusable = (SidedInventoryComponent<InfusionTableTile>) new SidedInventoryComponent<InfusionTableTile>("input", 80, 20, 1, 0)
            .setColor(DyeColor.CYAN)
            .setComponentHarness(this)
            .setInputFilter(this::canInsertInfusable)
            .setOutputFilter(this::canExtractInfusable)
        );
        addInventory(tome = new InventoryComponent<InfusionTableTile>("tome", 9, 10, 1)
            .setComponentHarness(this)
            .setOnSlotChanged((stack, integer) -> markComponentForUpdate())
        );
        addInventory(infusion_array = new InventoryComponent<InfusionTableTile>("infusion_array", 17, 55, 8)
            .setComponentHarness(this)
            .setInputFilter((stack, integer) -> true)
            .setOutputFilter((stack, integer) -> true)
        );
        addProgressBar(progressBar = new ProgressBarComponent<InfusionTableTile>(175, 1, 1)
            .setCanIncrease(iComponentHarness -> isWorking)
        );
    }

    @Override
    public void tick() {
        super.tick();
        handleBookRender();
        if (shouldBeWorking) {
            // TODO: Implement Cache-Checking
            if (true) {
                List<ItemStack> stacks = new ArrayList<>();
                for (int i = 0; i < infusion_array.getSlots(); i++) {
                    stacks.add(infusion_array.getStackInSlot(i));
                }
                getInfusionRecipes(stacks);
                workDuration = 0; // TODO: Implement getting of Total Work Duration
                progressBar.setMaxProgress(workDuration);
            }
            isWorking = true;
            if (progressBar.getProgress() >= progressBar.getMaxProgress()) {
                ItemStack infused_item = infusable.getStackInSlot(0);
                for (InfusionTableSerializableRecipe recipe : recipes) {
                    recipe.resolveRecipe(infused_item);
                }
                isWorking = false;
                shouldBeWorking = false;
            }
        }
    }

    @Nonnull
    @Override
    public InfusionTableTile getSelf() {
        return this;
    }

    private boolean canInsertInfusable(ItemStack stack, int slot) {
        for (Tag<Item> itemTag : VALID_INPUT) {
            if (stack.getItem().isIn(itemTag)) {
                return true;
            }
        }
        return false;
    }

    private boolean canExtractInfusable(ItemStack stack, int slot) {
        return !isWorking;
    }

    private void getInfusionRecipes(List<ItemStack> itemStacks) {
        recipes.clear();
        for (ItemStack stack : itemStacks) {
            recipes.add(this.world.getRecipeManager().getRecipes()
                .stream()
                .filter(iRecipe -> iRecipe.getType() == InfusionTableSerializableRecipe.SERIALIZER.getRecipeType())
                .map(iRecipe -> (InfusionTableSerializableRecipe) iRecipe)
                .filter(recipes -> recipes.isValid(stack))
                .findFirst().orElse(null));
        }
    }

    private void handleBookRender() {
        this.field_195528_k = this.field_195527_j;
        this.field_195530_m = this.field_195529_l;
        if (world == null) return;
        PlayerEntity lvt_1_1_ = this.world.getClosestPlayer(((float)this.pos.getX() + 0.5F), ((float)this.pos.getY() + 0.5F), ((float)this.pos.getZ() + 0.5F), 3.0D, false);
        if (lvt_1_1_ != null) {
            double lvt_2_1_ = lvt_1_1_.getPosX() - ((double)this.pos.getX() + 0.5D);
            double lvt_4_1_ = lvt_1_1_.getPosZ() - ((double)this.pos.getZ() + 0.5D);
            this.field_195531_n = (float) MathHelper.atan2(lvt_4_1_, lvt_2_1_);
            this.field_195527_j += 0.1F;
            if (this.field_195527_j < 0.5F || Essence.RANDOM.nextInt(40) == 0) {
                float lvt_6_1_ = this.field_195525_h;

                do {
                    this.field_195525_h += (float)(Essence.RANDOM.nextInt(4) - Essence.RANDOM.nextInt(4));
                } while(lvt_6_1_ == this.field_195525_h);
            }
        } else {
            this.field_195531_n += 0.02F;
            this.field_195527_j -= 0.1F;
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

}