package com.teamacronymcoders.essence.item.tome.experience;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModifiedTank;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.item.tome.TomeItem;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.helper.EssenceInformationHelper;
import com.teamacronymcoders.essence.util.helper.EssenceUtilHelper;
import com.teamacronymcoders.essence.util.network.base.IItemNetwork;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class TomeOfExperienceItem extends TomeItem implements IModifiedTank, IItemNetwork {

  private ExperienceModeEnum mode;
  private final int baseModifiers;
  private int freeModifiers;
  private int additionalModifiers;

  public TomeOfExperienceItem (Properties properties) {
    mode = ExperienceModeEnum.FILL;
    this.baseModifiers = 3;
    this.freeModifiers = 3;
    this.additionalModifiers = 0;
  }


  @Override
  public void addInformation (ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    if (!EssenceInformationHelper.isSneakKeyDown()) {
      tooltip.add(new TranslationTextComponent("tooltip.essence.modifier.free", new StringTextComponent(String.valueOf(freeModifiers)).mergeStyle(EssenceUtilHelper.getTextColor(freeModifiers, (baseModifiers + additionalModifiers)))).mergeStyle(TextFormatting.GRAY));
      tooltip.add(EssenceInformationHelper.shiftForDetails.mergeStyle(TextFormatting.GREEN));
      return;
    }

    //Amount
    stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(handler -> {
      int currentAmount = handler.getFluidInTank(0).getAmount();
      int capacityAmount = handler.getTankCapacity(0);
      tooltip.add(new TranslationTextComponent("tome.essence.mode.tooltip").mergeStyle(TextFormatting.LIGHT_PURPLE).append(new StringTextComponent(" ").mergeStyle(TextFormatting.WHITE).append(new TranslationTextComponent(mode.getLocaleString()))));
      tooltip.add(new TranslationTextComponent("tooltip.essence.tome_of_experience.holding").mergeStyle(TextFormatting.LIGHT_PURPLE));
      tooltip.add(new TranslationTextComponent("tooltip.essence.tome_of_experience.levels").mergeStyle(TextFormatting.LIGHT_PURPLE).append(new StringTextComponent(NumberFormat.getNumberInstance(Locale.ROOT).format(EssenceUtilHelper.getLevelForExperience(currentAmount)) + "/").append(new StringTextComponent(NumberFormat.getNumberInstance(Locale.ROOT).format(Math.floor(EssenceUtilHelper.getLevelForExperience(capacityAmount))))).mergeStyle(EssenceUtilHelper.getTextColor(currentAmount, Math.round(EssenceUtilHelper.getLevelForExperience(capacityAmount))))));
      tooltip.add(new TranslationTextComponent("tooltip.essence.tome_of_experience.amount").mergeStyle(TextFormatting.LIGHT_PURPLE).append(new StringTextComponent(NumberFormat.getNumberInstance(Locale.ROOT).format(currentAmount)).appendString("/").append(new StringTextComponent(NumberFormat.getNumberInstance(Locale.ROOT).format(capacityAmount))).mergeStyle(EssenceUtilHelper.getTextColor(currentAmount, capacityAmount))));
    });

    addInformationFromModifiers(stack, worldIn, tooltip, flagIn);
  }

  @Override
  public ActionResultType onItemUse (ItemUseContext context) {
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
            if (Minecraft.getInstance().gameSettings.keyBindSneak.isKeyDown()) {
              int amount = handler.fill(new FluidStack(EssenceObjectHolders.EXP_FLUID.getSourceFluid(), experience), IFluidHandler.FluidAction.EXECUTE);
              player.giveExperiencePoints(-amount);
            } else {
              int amount = handler.fill(new FluidStack(EssenceObjectHolders.EXP_FLUID.getSourceFluid(), EssenceUtilHelper.getExperienceForLevelWithDestination(player.experienceLevel, player.experienceLevel - 1)), IFluidHandler.FluidAction.EXECUTE);
              player.giveExperiencePoints(-amount);
            }
            return ActionResultType.SUCCESS;
          } else if (getMode() == ExperienceModeEnum.DRAIN) {
            if (Minecraft.getInstance().gameSettings.keyBindSneak.isKeyDown()) {
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

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities (ItemStack stack, @Nullable CompoundNBT nbt) {
    if (stack != null && !stack.isEmpty() && nbt != null) {
      return new ExperienceTomeProvider(stack, EssenceObjectHolders.EXP_FLUID.getSourceFluid(), nbt);
    }
    return new ExperienceTomeProvider(stack, EssenceObjectHolders.EXP_FLUID.getSourceFluid());
  }

  public ExperienceModeEnum getMode () {
    return mode;
  }

  @Override
  public void addModifierWithoutIncreasingAdditional (int increase) {
    freeModifiers += increase;
  }

  @Override
  public void increaseFreeModifiers (int increase) {
    freeModifiers += increase;
    additionalModifiers += increase;
  }

  @Override
  public boolean decreaseFreeModifiers (int decrease) {
    if (freeModifiers - decrease < 0) {
      return false;
    }
    freeModifiers = freeModifiers - decrease;
    return true;
  }

  @Override
  public int getFreeModifiers () {
    return freeModifiers;
  }

  @Override
  public int getMaxModifiers () {
    return baseModifiers + additionalModifiers;
  }

  @Override
  public boolean recheck (ItemStack object, List<ModifierInstance<ItemStack>> modifierInstances) {
    int cmc = 0;
    for (ModifierInstance<ItemStack> instance : modifierInstances) {
      if (instance.getModifier() instanceof ItemCoreModifier) {
        cmc += instance.getModifier().getModifierCountValue(instance.getLevel(), object);
      }
    }
    return cmc <= baseModifiers + additionalModifiers;
  }

  @Override
  public Class<ItemStack> getType () {
    return ItemStack.class;
  }

  public void setMode (ExperienceModeEnum mode) {
    this.mode = mode;
  }

  @Override
  public void handlePacketData (IWorld world, ItemStack stack, PacketBuffer dataStream) {
    if (!world.isRemote()) {
      setMode(dataStream.readEnumValue(ExperienceModeEnum.class));
    }
  }
}
