package lanse505.essence.impl.blocks.infuser;

import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import lanse505.essence.utils.module.ModuleObjects;
import lanse505.essence.utils.tags.EssenceTags;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;

import javax.annotation.Nonnull;

public class InfusionTableTile extends ActiveTile<InfusionTableTile> {
    public static Tag<Item>[] VALID_INPUT = new Tag[] {
            EssenceTags.Items.ESSENCE_AXE, EssenceTags.Items.ESSENCE_PICKAXE, EssenceTags.Items.ESSENCE_SHOVEL,
            EssenceTags.Items.ESSENCE_HOE, EssenceTags.Items.ESSENCE_SWORD, EssenceTags.Items.ESSENCE_OMNITOOL
    };

    public static Tag<Item>[] VALID_INFUSION_ITEMS = new Tag[] {
            EssenceTags.Items.ATTACK_DAMAGE_MODIFIER, EssenceTags.Items.EXPANDER_MODIFIER
    };

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

}
