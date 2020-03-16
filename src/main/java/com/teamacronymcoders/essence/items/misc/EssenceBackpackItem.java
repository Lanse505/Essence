package com.teamacronymcoders.essence.items.misc;

import com.hrznstudio.titanium.component.IComponentHarness;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.tool.ModifierInstance;
import com.teamacronymcoders.essence.capabilities.BackpackCapability;
import com.teamacronymcoders.essence.utils.helpers.EssenceModifierHelpers;
import com.teamacronymcoders.essence.utils.tiers.EssenceBackpackTiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.List;

public class EssenceBackpackItem extends Item implements IComponentHarness {

    private final EssenceBackpackTiers tier;

    public EssenceBackpackItem(ResourceLocation resourceLocation, EssenceBackpackTiers tier) {
        super(new Item.Properties().group(Essence.CORE_TAB).maxStackSize(1));
        setRegistryName(resourceLocation);
        this.tier = tier;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new BackpackCapability(new InventoryComponent<EssenceBackpackItem>("backpackInventory", 0, 0, tier.getBackpackSlots())
            .setRange(tier.getBackpackX(), tier.getBackpackY()).setComponentHarness(this).setInputFilter((input, integer) -> !(input.getItem() instanceof EssenceBackpackItem)));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (isSelected) {
            InventoryComponent<EssenceBackpackItem> inventory = getComponent(stack);
            Triple<Integer, Integer, Integer> values = Triple.of(tier.getBackpackSlots(), tier.getBackpackX(), tier.getBackpackY());
            final List<ModifierInstance> instances = EssenceModifierHelpers.getModifiers(stack);
        }
    }


    @Override
    public World getComponentWorld() {
        return null;
    }

    @Override
    public void markComponentForUpdate() {
    }

    @Override
    public void markComponentDirty() {
    }

    public InventoryComponent<EssenceBackpackItem> getComponent(ItemStack stack) {
        InventoryComponent<EssenceBackpackItem> essenceBackpackItemInventoryComponent = (InventoryComponent<EssenceBackpackItem>) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        return essenceBackpackItemInventoryComponent;
    }
}
