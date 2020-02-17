package com.teamacronymcoders.essence.impl.blocks.infuser;

import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import com.teamacronymcoders.essence.impl.serializable.recipe.InfusionTableSerializableRecipe;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import com.teamacronymcoders.essence.utils.module.ModuleObjects;
import com.teamacronymcoders.essence.utils.tags.EssenceTags;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InfusionTableTile extends ActiveTile<InfusionTableTile> {
    public static Tag<Item>[] VALID_INPUT = new Tag[] {
            EssenceTags.Items.ESSENCE_AXE, EssenceTags.Items.ESSENCE_PICKAXE, EssenceTags.Items.ESSENCE_SHOVEL,
            EssenceTags.Items.ESSENCE_HOE, EssenceTags.Items.ESSENCE_SWORD, EssenceTags.Items.ESSENCE_OMNITOOL
    };

    public static Tag<Item>[] VALID_INFUSION_ITEMS = new Tag[] {
            EssenceTags.Items.ATTACK_DAMAGE_MODIFIER, EssenceTags.Items.EXPANDER_MODIFIER
    };

    private List<InfusionTableSerializableRecipe> recipes = new ArrayList<>();

    private boolean isWorking = false;

    @Save
    private SidedInventoryComponent<InfusionTableTile> input;

    @Save
    private SidedInventoryComponent<InfusionTableTile> infusion_array;

    @Save
    private SidedInventoryComponent<InfusionTableTile> output;

    @Save
    private ProgressBarComponent<InfusionTableTile> progressBar;

    public InfusionTableTile() {
        super(ModuleObjects.ESSENCE_INFUSION_TABLE);
        addInventory(input = (SidedInventoryComponent<InfusionTableTile>) new SidedInventoryComponent<InfusionTableTile>("input", 0, 0, 1, 0)
            .setColor(DyeColor.CYAN)
            .setComponentHarness(this)
            .setInputFilter(this::canInsertInput)
            .setOutputFilter((stack, integer) -> false)
        );
        addInventory(infusion_array = (SidedInventoryComponent<InfusionTableTile>) new SidedInventoryComponent<InfusionTableTile>("infusion_array", 0, 0, 8, 0)
            .setComponentHarness(this)
            .setInputFilter(this::canInsertInfusionArray)
            .setOutputFilter((stack, integer) -> false)
        );
        addInventory(output = (SidedInventoryComponent<InfusionTableTile>) new SidedInventoryComponent<InfusionTableTile>("output", 0, 0, 1, 0)
            .setColor(DyeColor.ORANGE)
            .setComponentHarness(this)
            .setInputFilter((stack, integer) -> false)
            .setOutputFilter((stack, integer) -> true)
        );
        addProgressBar(progressBar = new ProgressBarComponent<InfusionTableTile>(0, 0, 0)
                .setCanIncrease(iComponentHarness -> isWorking)
        );
    }

    @Override
    public void tick() {
        super.tick();
    }



    @Nonnull
    @Override
    public InfusionTableTile getSelf() {
        return this;
    }

    private boolean canInsertInput(ItemStack stack, int slot) {
        for (Tag<Item> itemTag : VALID_INPUT) {
            if (itemTag.contains(stack.getItem())) {
                return true;
            }
        }
        return false;
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
            for (ItemStack stack : recipe.getTagList().getStacks()) {
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

    private void getInfusionRecipes(ItemStack... itemStacks) {
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

    private int getCollectedDuration(InfusionTableSerializableRecipe... serializableRecipes) {
        return Arrays.stream(serializableRecipes).map(InfusionTableSerializableRecipe::getDuration).reduce(0, Integer::sum);
    }

}
