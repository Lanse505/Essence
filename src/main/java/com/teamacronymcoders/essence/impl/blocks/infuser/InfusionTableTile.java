package com.teamacronymcoders.essence.impl.blocks.infuser;

import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import com.teamacronymcoders.essence.impl.serializable.recipe.InfusionTableSerializableRecipe;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import com.teamacronymcoders.essence.utils.tags.EssenceTags;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class InfusionTableTile extends ActiveTile<InfusionTableTile> {
    public static Tag<Item>[] VALID_INPUT = new Tag[]{
        EssenceTags.Items.ESSENCE_AXE, EssenceTags.Items.ESSENCE_PICKAXE, EssenceTags.Items.ESSENCE_SHOVEL,
        EssenceTags.Items.ESSENCE_HOE, EssenceTags.Items.ESSENCE_SWORD, EssenceTags.Items.ESSENCE_OMNITOOL
    };

    public static Tag<Item>[] VALID_INFUSION_ITEMS = new Tag[]{
        EssenceTags.Items.ATTACK_DAMAGE_MODIFIER, EssenceTags.Items.EXPANDER_MODIFIER, EssenceTags.Items.FORTUNE_MODIFIER,
        EssenceTags.Items.SILK_TOUCH_MODIFIER
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
    private SidedInventoryComponent<InfusionTableTile> infusion_array;
    @Save
    private ProgressBarComponent<InfusionTableTile> progressBar;

    public InfusionTableTile() {
        super(EssenceObjectHolders.ESSENCE_INFUSION_TABLE);
        addInventory(infusable = (SidedInventoryComponent<InfusionTableTile>) new SidedInventoryComponent<InfusionTableTile>("input", 0, 0, 1, 0)
            .setColor(DyeColor.CYAN)
            .setComponentHarness(this)
            .setInputFilter(this::canInsertInfusable)
            .setOutputFilter(this::canExtractInfusable)
        );
        addInventory(infusion_array = (SidedInventoryComponent<InfusionTableTile>) new SidedInventoryComponent<InfusionTableTile>("infusion_array", 0, 0, 8, 0)
            .setComponentHarness(this)
            .setInputFilter(this::canInsertInfusionArray)
            .setOutputFilter((stack, integer) -> false)
        );
        addProgressBar(progressBar = new ProgressBarComponent<InfusionTableTile>(0, 0, 1)
            .setCanIncrease(iComponentHarness -> isWorking)
        );
    }

    @Override
    public void tick() {
        super.tick();
        if (shouldBeWorking) {
            if (!areStoredRecipesValidForInfusionArray()) {
                List<ItemStack> stacks = new ArrayList<>();
                for (int i = 0; i < infusion_array.getSlots(); i++) {
                    stacks.add(infusion_array.getStackInSlot(i));
                }
                getInfusionRecipes(stacks);
                workDuration = getCollectedDuration(recipes);
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
            if (itemTag.contains(stack.getItem())) {
                return true;
            }
        }
        return false;
    }

    private boolean canExtractInfusable(ItemStack stack, int slot) {
        return !isWorking;
    }

    private boolean canInsertInfusionArray(ItemStack stack, int slot) {
        for (Tag<Item> itemTag : VALID_INFUSION_ITEMS) {
            if (itemTag.contains(stack.getItem())) {
                return true;
            }
        }
        return false;
    }

    private boolean areStoredRecipesValidForInfusionArray() {
        IntList usedSlots = new IntArrayList(8);
        IntList matchedSlots = new IntArrayList(8);
        int counter = 0;
        for (InfusionTableSerializableRecipe recipe : recipes) {
            for (ItemStack stack : recipe.getCollectedStacks()) {
                for (int idx = 0; idx < 8; idx++) {
                    ItemStack s = infusion_array.getStackInSlot(idx);
                    if (!matchedSlots.contains(counter) && stack.isItemEqualIgnoreDurability(s) && !usedSlots.contains(idx)) {
                        usedSlots.add(idx);
                        matchedSlots.add(counter);
                        break;
                    }
                }
                break;
            }
            counter++;
        }
        return matchedSlots.size() == recipes.size();
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

    private int getCollectedDuration(List<InfusionTableSerializableRecipe> serializableRecipes) {
        return serializableRecipes.stream().map(InfusionTableSerializableRecipe::getDuration).reduce(0, Integer::sum);
    }
}