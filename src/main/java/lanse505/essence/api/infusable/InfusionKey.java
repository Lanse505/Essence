package lanse505.essence.api.infusable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.Tag;

public class InfusionKey {
    private Item infusionItem;
    private Tag<Item> infusionItemTag;
    private CompoundNBT infusionItemNBT;

    public InfusionKey(Item infusionItem) {
        this.infusionItem = infusionItem;
    }

    public InfusionKey(Item infusionItem, CompoundNBT infusionItemNBT) {
        this.infusionItem = infusionItem;
        this.infusionItemNBT = infusionItemNBT;
    }

    public InfusionKey(Tag<Item> infusionItemTag) {
        this.infusionItemTag = infusionItemTag;
    }

    public InfusionKey(Tag<Item> infusionItemTag, CompoundNBT infusionItemNBT) {
        this.infusionItemTag = infusionItemTag;
        this.infusionItemNBT = infusionItemNBT;
    }

    public Item getInfusionItem() {
        return infusionItem;
    }

    public Tag<Item> getInfusionItemTag() {
        return infusionItemTag;
    }

    public CompoundNBT getInfusionItemNBT() {
        return infusionItemNBT;
    }

    public boolean compare(ItemStack stack) {
        if (infusionItem != null) {
            if (infusionItemNBT != null) {
                return infusionItem.equals(stack.getItem()) && infusionItemNBT.equals(stack.getTag());
            }
            return infusionItem.equals(stack.getItem());
        } else if (infusionItemTag != null) {
            if (infusionItemNBT != null) {
                return infusionItemTag.contains(stack.getItem()) && infusionItemNBT.equals(stack.getTag());
            }
            return infusionItemTag.contains(stack.getItem());
        }
        return false;
    }
}
