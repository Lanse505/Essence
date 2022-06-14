package com.teamacronymcoders.essence.common.item.tome.experience;

import com.teamacronymcoders.essence.api.modified.rewrite.IModifiedItem;
import com.teamacronymcoders.essence.common.util.network.base.IItemNetwork;
import com.teamacronymcoders.essence.common.util.tier.EssenceToolTiers;
import com.teamacronymcoders.essence.compat.registrate.EssenceFluidRegistrate;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TomeOfExperienceItem extends Item implements IModifiedItem, IItemNetwork {

    private ExperienceModeEnum mode;

    public TomeOfExperienceItem(Item.Properties properties) {
        super(properties);
        mode = ExperienceModeEnum.FILL;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, level, tooltip, flagIn);
        if (!EssenceInformationHelper.isSneakKeyDown()) {
            tooltip.add(EssenceInformationHelper.shiftForDetails.withStyle(ChatFormatting.GREEN));
            return;
        }

        //Amount
        stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(handler -> {
            int currentAmount = handler.getFluidInTank(0).getAmount();
            int capacityAmount = handler.getTankCapacity(0);
            tooltip.add(Component.translatable("tooltip.tome.essence.mode.tooltip").withStyle(ChatFormatting.LIGHT_PURPLE).append(Component.literal(" ").withStyle(ChatFormatting.WHITE).append(Component.translatable(mode.getLocaleString()))));
            tooltip.add(Component.translatable("tooltip.essence.tome_of_experience.holding").withStyle(ChatFormatting.LIGHT_PURPLE));
            tooltip.add(Component.literal("  ").append(Component.translatable("tooltip.essence.tome_of_experience.levels").withStyle(ChatFormatting.LIGHT_PURPLE).append(Component.literal(NumberFormat.getNumberInstance(Locale.ROOT).format(EssenceUtilHelper.getLevelForExperience(currentAmount)) + "/").append(Component.literal(NumberFormat.getNumberInstance(Locale.ROOT).format(Math.floor(EssenceUtilHelper.getLevelForExperience(capacityAmount))))).withStyle(EssenceUtilHelper.getTextColor(currentAmount, Math.round(EssenceUtilHelper.getLevelForExperience(capacityAmount)))))));
            tooltip.add(Component.literal("  ").append(Component.translatable("tooltip.essence.tome_of_experience.amount").withStyle(ChatFormatting.LIGHT_PURPLE).append(Component.literal(NumberFormat.getNumberInstance(Locale.ROOT).format(currentAmount)).append("/").append(Component.literal(NumberFormat.getNumberInstance(Locale.ROOT).format(capacityAmount))).withStyle(EssenceUtilHelper.getTextColor(currentAmount, capacityAmount)))));
        });

        addInformationFromModifiers(stack, level, tooltip, flagIn);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();

        if (!state.isAir()) {
            BlockEntity te = level.getBlockEntity(pos);
            if (te != null) {
                LazyOptional<IFluidHandler> lazy = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
                return lazy.isPresent() ? lazy.map(handler -> {
                    if (getMode() == ExperienceModeEnum.FILL) {
                        FluidUtil.tryFillContainer(stack, handler, Integer.MAX_VALUE, player, true);
                        return InteractionResult.SUCCESS;
                    }
                    if (getMode() == ExperienceModeEnum.DRAIN) {
                        FluidUtil.tryEmptyContainer(stack, handler, Integer.MAX_VALUE, player, true);
                        return InteractionResult.SUCCESS;
                    }
                    return InteractionResult.PASS;
                }).orElse(InteractionResult.PASS) : InteractionResult.PASS;
            }
            return InteractionResult.PASS;
        }

        if (player != null) {
            LazyOptional<IFluidHandlerItem> lazy = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
            int experience = player.totalExperience;
            if (lazy.isPresent()) {
                return lazy.map(handler -> {
                    if (getMode() == ExperienceModeEnum.FILL) {
                        if (Minecraft.getInstance().options.keyShift.isDown()) {
                            int amount = handler.fill(new FluidStack(EssenceFluidRegistrate.EXPERIENCE.get().getSource(), experience), IFluidHandler.FluidAction.EXECUTE);
                            player.giveExperiencePoints(-amount);
                        } else {
                            int amount = handler.fill(new FluidStack(EssenceFluidRegistrate.EXPERIENCE.get().getSource(), EssenceUtilHelper.getExperienceForLevelWithDestination(player.experienceLevel, player.experienceLevel - 1)), IFluidHandler.FluidAction.EXECUTE);
                            player.giveExperiencePoints(-amount);
                        }
                        return InteractionResult.SUCCESS;
                    } else if (getMode() == ExperienceModeEnum.DRAIN) {
                        if (Minecraft.getInstance().options.keyShift.isDown()) {
                            FluidStack amount = handler.drain(experience, IFluidHandler.FluidAction.EXECUTE);
                            player.giveExperiencePoints(amount.getAmount());
                        } else {
                            FluidStack amount = handler.drain(EssenceUtilHelper.getExperienceForLevelWithDestination(player.experienceLevel, player.experienceLevel + 1), IFluidHandler.FluidAction.EXECUTE);
                            player.giveExperiencePoints(amount.getAmount());
                        }
                        return InteractionResult.SUCCESS;
                    }
                    return InteractionResult.PASS;
                }).orElse(InteractionResult.PASS);
            }
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        if (stack != null && !stack.isEmpty() && nbt != null) {
            return new ExperienceTomeProvider(stack, EssenceFluidRegistrate.EXPERIENCE.get().getSource(), nbt);
        }
        return new ExperienceTomeProvider(stack, EssenceFluidRegistrate.EXPERIENCE.get().getSource());
    }

    public ExperienceModeEnum getMode() {
        return mode;
    }

    public void setMode(ExperienceModeEnum mode) {
        this.mode = mode;
    }

    @Override
    public void handlePacketData(LevelAccessor accessor, ItemStack stack, FriendlyByteBuf dataStream) {
        if (!accessor.isClientSide()) {
            setMode(dataStream.readEnum(ExperienceModeEnum.class));
        }
    }

    @Override
    public EssenceToolTiers getTier() {
        return getTier();
    }

    @Override
    public InteractionResult useOnModified(UseOnContext context, boolean isRecursive) {
        return InteractionResult.FAIL;
    }
}
