package com.teamacronymcoders.essence.item.tome.experience;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModifiedTank;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.item.tome.TomeItem;
import com.teamacronymcoders.essence.registrate.EssenceFluidRegistrate;
import com.teamacronymcoders.essence.util.helper.EssenceInformationHelper;
import com.teamacronymcoders.essence.util.helper.EssenceUtilHelper;
import com.teamacronymcoders.essence.util.network.base.IItemNetwork;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
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

public class TomeOfExperienceItem extends TomeItem implements IModifiedTank, IItemNetwork {

  private ExperienceModeEnum mode;
  private final int baseModifiers;
  private int freeModifiers;
  private int additionalModifiers;

  public TomeOfExperienceItem(Item.Properties properties) {
    mode = ExperienceModeEnum.FILL;
    this.baseModifiers = 3;
    this.freeModifiers = 3;
    this.additionalModifiers = 0;
  }


  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) {
    super.appendHoverText(stack, level, tooltip, flagIn);
    if (!EssenceInformationHelper.isSneakKeyDown()) {
      tooltip.add(new TranslatableComponent("tooltip.essence.modifier.free", new TextComponent(String.valueOf(freeModifiers)).withStyle(EssenceUtilHelper.getTextColor(freeModifiers, (baseModifiers + additionalModifiers)))).withStyle(ChatFormatting.GRAY));
      tooltip.add(EssenceInformationHelper.shiftForDetails.withStyle(ChatFormatting.GREEN));
      return;
    }

    //Amount
    stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(handler -> {
      int currentAmount = handler.getFluidInTank(0).getAmount();
      int capacityAmount = handler.getTankCapacity(0);
      tooltip.add(new TranslatableComponent("tome.essence.mode.tooltip").withStyle(ChatFormatting.LIGHT_PURPLE).append(new TextComponent(" ").withStyle(ChatFormatting.WHITE).append(new TranslatableComponent(mode.getLocaleString()))));
      tooltip.add(new TranslatableComponent("tooltip.essence.tome_of_experience.holding").withStyle(ChatFormatting.LIGHT_PURPLE));
      tooltip.add(new TranslatableComponent("tooltip.essence.tome_of_experience.levels").withStyle(ChatFormatting.LIGHT_PURPLE).append(new TextComponent(NumberFormat.getNumberInstance(Locale.ROOT).format(EssenceUtilHelper.getLevelForExperience(currentAmount)) + "/").append(new TextComponent(NumberFormat.getNumberInstance(Locale.ROOT).format(Math.floor(EssenceUtilHelper.getLevelForExperience(capacityAmount))))).withStyle(EssenceUtilHelper.getTextColor(currentAmount, Math.round(EssenceUtilHelper.getLevelForExperience(capacityAmount))))));
      tooltip.add(new TranslatableComponent("tooltip.essence.tome_of_experience.amount").withStyle(ChatFormatting.LIGHT_PURPLE).append(new TextComponent(NumberFormat.getNumberInstance(Locale.ROOT).format(currentAmount)).append("/").append(new TextComponent(NumberFormat.getNumberInstance(Locale.ROOT).format(capacityAmount))).withStyle(EssenceUtilHelper.getTextColor(currentAmount, capacityAmount))));
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
  public boolean recheck(List<ModifierInstance> modifierInstances) {
    int cmc = 0;
    for (ModifierInstance instance : modifierInstances) {
      if (instance.getModifier() instanceof ItemCoreModifier) {
        cmc += instance.getModifier().getModifierCountValue(instance.getLevel());
      }
    }
    return cmc <= baseModifiers + additionalModifiers;
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
}
