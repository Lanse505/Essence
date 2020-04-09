package com.teamacronymcoders.essence.item.tome.knowledge;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModifiedTank;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.capability.itemstack.ItemStackModifierHolder;
import com.teamacronymcoders.essence.capability.tank.ModifiableTankProvider;
import com.teamacronymcoders.essence.item.tome.TomeItem;
import com.teamacronymcoders.essence.item.wrench.WrenchModeEnum;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.helper.EssenceInformationHelper;
import com.teamacronymcoders.essence.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.util.helper.EssenceUtilHelper;
import com.teamacronymcoders.essence.util.network.base.IItemNetwork;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TomeOfExperienceItem extends TomeItem implements IModifiedTank, IItemNetwork {

    private ExperienceModeEnum mode;
    private final int baseModifiers;
    private int freeModifiers;
    private int additionalModifiers;

    public TomeOfExperienceItem() {
        mode = ExperienceModeEnum.FILL;
        this.baseModifiers = 3;
        this.freeModifiers = 3;
        this.additionalModifiers = 0;
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!EssenceInformationHelper.isSneakKeyDown()) {
            tooltip.add(EssenceInformationHelper.shiftForDetails.applyTextStyle(TextFormatting.GREEN));
            return;
        }

        //Amount
        stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(handler -> {
            int currentAmount = handler.getFluidInTank(0).getAmount();
            int capacityAmount = handler.getTankCapacity(0);
            tooltip.add(new TranslationTextComponent("essence.tome.mode.tooltip").appendText(" ").appendSibling(new TranslationTextComponent(mode.getLocaleString())));
            tooltip.add(new TranslationTextComponent("tooltip.essence.tome_of_experience.holding").applyTextStyle(TextFormatting.GREEN));
            tooltip.add(new TranslationTextComponent("tooltip.essence.tome_of_experience.levels", NumberFormat.getNumberInstance(Locale.ROOT).format(EssenceUtilHelper.getLevelForExperience(currentAmount))).applyTextStyle(TextFormatting.LIGHT_PURPLE));
            tooltip.add(new TranslationTextComponent("tooltip.essence.tome_of_experience.amount", NumberFormat.getNumberInstance(Locale.ROOT).format(currentAmount), NumberFormat.getNumberInstance(Locale.ROOT).format(capacityAmount)).applyTextStyle(TextFormatting.LIGHT_PURPLE));
        });

        addInformationFromModifiers(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getWorld().isRemote()) {
            return ActionResultType.SUCCESS;
        }

        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = context.getItem();
        PlayerEntity player = context.getPlayer();

        if (!state.getBlock().isAir(state, world, pos)) {
            TileEntity te = world.getTileEntity(pos);
            if (te != null) {
                LazyOptional<IFluidHandler> lazy = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
                return lazy.isPresent() ? lazy.map(handler -> {
                    if (getMode() == ExperienceModeEnum.FILL) {
                        FluidUtil.tryFillContainer(stack, handler, Integer.MAX_VALUE, player, true);
                        return ActionResultType.SUCCESS;
                    }
                    if (getMode() == ExperienceModeEnum.DRAIN) {
                        FluidUtil.tryEmptyContainer(stack, handler, Integer.MAX_VALUE, player, true);
                        return ActionResultType.SUCCESS;
                    }
                    return ActionResultType.PASS;
                }).orElse(ActionResultType.PASS) : ActionResultType.PASS;
            }
            return ActionResultType.PASS;
        }

        if (player != null) {
            LazyOptional<IFluidHandlerItem> lazy = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
            int experience = player.experienceTotal;
            if (lazy.isPresent()) {
                return lazy.map(handler -> {
                    if (getMode() == ExperienceModeEnum.FILL) {
                        if (player.isShiftKeyDown()) {
                            int amount = handler.fill(new FluidStack(EssenceObjectHolders.EXP_FLUID.getSourceFluid(), experience), IFluidHandler.FluidAction.EXECUTE);
                            player.giveExperiencePoints(-amount);
                        } else {
                            int amount = handler.fill(new FluidStack(EssenceObjectHolders.EXP_FLUID.getSourceFluid(), EssenceUtilHelper.getExperienceForLevelWithDestination(player.experienceLevel, player.experienceLevel - 1)), IFluidHandler.FluidAction.EXECUTE);
                            player.giveExperiencePoints(-amount);
                        }
                        return ActionResultType.SUCCESS;
                    } else if (getMode() == ExperienceModeEnum.DRAIN) {
                        if (player.isShiftKeyDown()) {
                            FluidStack amount = handler.drain(experience, IFluidHandler.FluidAction.EXECUTE);
                            player.giveExperiencePoints(amount.getAmount());
                        } else {
                            FluidStack amount = handler.drain(EssenceUtilHelper.getExperienceForLevelWithDestination(player.experienceLevel, player.experienceLevel + 1), IFluidHandler.FluidAction.EXECUTE);
                            player.giveExperiencePoints(amount.getAmount());
                        }
                        return ActionResultType.SUCCESS;
                    }
                    return ActionResultType.PASS;
                }).orElse(ActionResultType.PASS);
            }
            return ActionResultType.PASS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT tag = super.getShareTag(stack);
        if (tag == null) {
            tag = new CompoundNBT();
        }
        CompoundNBT tankCapTag = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).map(cap -> {
            if (cap instanceof FluidTank) {
                FluidTank tank = (FluidTank) cap;
                return tank.writeToNBT(new CompoundNBT());
            }
            return new CompoundNBT();
        }).orElse(new CompoundNBT());
        ListNBT modifierCapTag = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).map(ItemStackModifierHolder::serializeNBT).orElse(new ListNBT());
        tag.put(ExperienceTomeProvider.MODIFIABLE_TANK, tankCapTag);
        tag.put(EssenceItemstackModifierHelpers.TAG_MODIFIERS, modifierCapTag);
        return tag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (nbt != null) {
            if (nbt.contains(EssenceItemstackModifierHelpers.TAG_MODIFIERS)) {
                ListNBT capTag = nbt.getList(EssenceItemstackModifierHelpers.TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND);
                stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).ifPresent(cap -> cap.deserializeNBT(capTag));
            }
            if (nbt.contains(ExperienceTomeProvider.MODIFIABLE_TANK)) {
                stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(cap -> {
                    if (cap instanceof FluidTank) {
                        FluidTank tank = (FluidTank) cap;
                        tank.readFromNBT(nbt);
                    }
                });
            }
        }
        super.readShareTag(stack, nbt);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (stack != null && !stack.isEmpty() && nbt != null) {
            return new ExperienceTomeProvider(stack, EssenceObjectHolders.EXP_FLUID.getSourceFluid(), nbt);
        }
        return new ExperienceTomeProvider(stack, EssenceObjectHolders.EXP_FLUID.getSourceFluid());
    }

    public ExperienceModeEnum getMode() {
        return mode;
    }

    @Override
    public void addModifierWithoutIncreasingAdditional(int increase) {
        freeModifiers += increase;
    }

    @Override
    public void increaseFreeModifiers(int increase) {
        freeModifiers += increase;
        additionalModifiers += increase;
    }

    @Override
    public boolean decreaseFreeModifiers(int decrease) {
        if (freeModifiers - decrease < 0) {
            return false;
        }
        freeModifiers = freeModifiers - decrease;
        return true;
    }

    @Override
    public int getFreeModifiers() {
        return freeModifiers;
    }

    @Override
    public int getMaxModifiers() {
        return baseModifiers + additionalModifiers;
    }

    @Override
    public boolean recheck(ItemStack object, List<ModifierInstance<ItemStack>> modifierInstances) {
        int cmc = 0;
        for (ModifierInstance<ItemStack> instance : modifierInstances) {
            if (instance.getModifier() instanceof ItemCoreModifier) {
                cmc += instance.getModifier().getModifierCountValue(instance.getLevel(), object);
            }
        }
        return cmc <= baseModifiers + additionalModifiers;
    }

    @Override
    public Class<ItemStack> getType() {
        return ItemStack.class;
    }

    public void setMode(ExperienceModeEnum mode) {
        this.mode = mode;
    }

    @Override
    public void handlePacketData(IWorld world, ItemStack stack, PacketBuffer dataStream) {
        if (!world.isRemote()) {
            setMode(dataStream.readEnumValue(ExperienceModeEnum.class));
        }
    }
}
