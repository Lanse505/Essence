package com.teamacronymcoders.essence.item.tool.misc;

import com.google.common.collect.Lists;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IScreenAddon;
import com.hrznstudio.titanium.api.client.IScreenAddonProvider;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.teamacronymcoders.essence.Essence;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class PortableCrafterItem extends Item implements IScreenAddonProvider, INamedContainerProvider {

    private final InventoryComponent<?> grid;
    private final InventoryComponent<?> output;

    public PortableCrafterItem() {
        super(new Item.Properties().maxStackSize(1).group(Essence.TOOL_TAB).rarity(Rarity.RARE));
        grid = new InventoryComponent<>("grid", 26, 25, 9).setRange(3, 3);
        output = new InventoryComponent<>("grid", 133, 43, 1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote && stack.getItem() == this) {
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, this, buffer -> buffer.writeString(handIn.name()));
        }
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    @Override
    public List<IFactory<? extends IScreenAddon>> getScreenAddons() {
        return Lists.newArrayList(grid, output).stream().map(IScreenAddonProvider::getScreenAddons).flatMap(List::stream).collect(Collectors.toList());
    }

    public InventoryComponent<?> getGrid() {
        return grid;
    }

    public InventoryComponent<?> getOutput() {
        return output;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.essence.portable.crafter").applyTextStyle(TextFormatting.BLACK);
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return null;
    }

//    @Nullable
//    @Override
//    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
//        return new PortableCrafterContainer(this, playerInventory, id);
//    }
}
