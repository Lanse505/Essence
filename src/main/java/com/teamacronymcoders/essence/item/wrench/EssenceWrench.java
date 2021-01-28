package com.teamacronymcoders.essence.item.wrench;

import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModifiedTool;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierHolder;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierProvider;
import com.teamacronymcoders.essence.client.render.tesr.itemstack.SerializableMobRenderer;
import com.teamacronymcoders.essence.item.wrench.config.BlockSerializationEnum;
import com.teamacronymcoders.essence.item.wrench.config.EntitySerializationEnum;
import com.teamacronymcoders.essence.modifier.item.enchantment.EfficiencyModifier;
import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.util.EssenceStats;
import com.teamacronymcoders.essence.util.EssenceTags;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceBlockTags;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceEntityTags;
import com.teamacronymcoders.essence.util.config.EssenceGeneralConfig;
import com.teamacronymcoders.essence.util.helper.EssenceInformationHelper;
import com.teamacronymcoders.essence.util.network.base.IItemNetwork;
import com.teamacronymcoders.essence.util.tier.EssenceItemTiers;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.Property;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

public class EssenceWrench extends Item implements IModifiedTool, IItemNetwork {

  private WrenchModeEnum mode;
  private final int baseModifiers = 1;
  private int freeModifiers;
  private final int additionalModifiers = 0;

  public EssenceWrench(Properties properties) {
    super(properties);
    this.mode = WrenchModeEnum.SERIALIZE;
    this.freeModifiers = 1;
  }

  private static <T extends Comparable<T>> String getStatePropertyValue(BlockState state, Property<T> property) {
    T prop = state.get(property);
    return property.getName(prop);
  }

  @Override
  @ParametersAreNonnullByDefault
  @MethodsReturnNonnullByDefault
  public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {
    if (target.getEntityWorld().isRemote) {
      return ActionResultType.FAIL;
    }
    LazyOptional<ItemStackModifierHolder> lazy = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
    return lazy.isPresent() ? lazy.map(holder -> {
      Optional<ModifierInstance> optional = holder.getModifierInstances().stream().filter(instance -> instance.getModifier() instanceof EfficiencyModifier).findAny();
      ItemStack serialized = new ItemStack(EssenceItemRegistrate.SERIALIZED_ENTITY.get());
      boolean successful;
      if (optional.isPresent()) {
        successful = serializeEntity(serialized, target, true);
      } else {
        successful = serializeEntity(serialized, target, false);
      }
      if (successful) {
        player.addItemStackToInventory(serialized);
        stack.damageItem(1, player, playerEntity -> playerEntity.sendBreakAnimation(hand));
      }
      return successful ? ActionResultType.SUCCESS : ActionResultType.FAIL;
    }).orElse(ActionResultType.FAIL) : ActionResultType.FAIL;
  }

  @Override
  public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
    World world = context.getWorld();
    BlockPos pos = context.getPos();
    BlockState state = world.getBlockState(pos);
    PlayerEntity player = context.getPlayer();
    TileEntity te = world.getTileEntity(pos);
    BlockSerializationEnum config = EssenceGeneralConfig.getInstance().getSerializeBlock().get();

    if (state.getBlock().isAir(state, world, pos) || !state.getFluidState().equals(Fluids.EMPTY.getDefaultState()) || player != null && !player.abilities.allowEdit && !stack.canPlaceOn(world.getTags(), new CachedBlockInfo(world, pos, false))) {
      return ActionResultType.PASS;
    }

    if (player != null) {
      if (mode == WrenchModeEnum.SERIALIZE && (state.getProperties().size() > 0 || state.hasTileEntity()) && ((((config == BlockSerializationEnum.BLACKLIST ? !state.isIn(EssenceBlockTags.FORGE_MOVEABLE_BLACKLIST) : state.isIn(EssenceBlockTags.FORGE_MOVEABLE_WHITELIST)) && !state.isIn(EssenceBlockTags.RELOCATION_NOT_SUPPORTED)) || (te != null && (!te.getType().isIn(EssenceTags.EssenceTileEntityTypeTags.IMMOVABLE) && !te.getType().isIn(EssenceTags.EssenceTileEntityTypeTags.RELOCATION_NOT_SUPPORTED)))) && !state.getPushReaction().equals(PushReaction.BLOCK))) {
        ItemStack drop = new ItemStack(state.getBlock());
        CompoundNBT stateNBT = new CompoundNBT();
        state.getProperties().forEach(iProperty -> stateNBT.putString(iProperty.getName(), getStatePropertyValue(state, iProperty)));
        // Serializes the BlockState
        drop.setTagInfo("BlockStateTag", stateNBT);
        // Serializes the TE
        if (te != null) {
          drop.setTagInfo("BlockEntityTag", te.serializeNBT());
        }
        // Adds the Stats
        player.addStat(EssenceStats.INSTANCE.SERIALIZED);
        player.addStat(Stats.ITEM_USED.get(this));
        // Cleans up the World and Drops the Item
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), Constants.BlockFlags.DEFAULT_AND_RERENDER);
        world.addEntity(Util.make(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), drop), ItemEntity::setDefaultPickupDelay));
        stack.damageItem(1, player, playerEntity -> playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return ActionResultType.SUCCESS;
      }

      if (mode == WrenchModeEnum.ROTATE) {
        if (EssenceInformationHelper.isSneakKeyDown()) {
          state.rotate(world, pos, Rotation.CLOCKWISE_180);
        }
        state.rotate(world, pos, Rotation.CLOCKWISE_90);
        player.addStat(Stats.ITEM_USED.get(this));
        return ActionResultType.SUCCESS;
      }
    }
    return ActionResultType.PASS;
  }

  @Override
  @ParametersAreNonnullByDefault
  public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
    addInformationFromModifiers(stack, world, list, flag, EssenceItemTiers.ESSENCE);
    list.add(new TranslationTextComponent("essence.wrench.mode.tooltip").mergeStyle(TextFormatting.GRAY, TextFormatting.BOLD).appendString(": ").mergeStyle(TextFormatting.WHITE).append(new TranslationTextComponent(mode.getLocaleName())));
    if (flag == ITooltipFlag.TooltipFlags.ADVANCED && mode == WrenchModeEnum.SERIALIZE) {
      list.add(new TranslationTextComponent("essence.wrench.disclaimer").mergeStyle(TextFormatting.RED, TextFormatting.BOLD));
      list.add(new TranslationTextComponent("essence.wrench.disclaimer_message"));
    }
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
    if (nbt != null && !nbt.isEmpty()) {
      return new ItemStackModifierProvider(stack, nbt);
    }
    return new ItemStackModifierProvider(stack);
  }

  public boolean serializeEntity(ItemStack stack, LivingEntity target, boolean checkBoss) {
    if (target.getEntityWorld().isRemote) {
      return false;
    }
    if (checkBoss) {
      if (target instanceof PlayerEntity || !target.isNonBoss() || !target.isAlive()) {
        return false;
      }
    } else {
      if (target instanceof PlayerEntity || !target.isAlive()) {
        return false;
      }
    }
    UUID uuid = target.getUniqueID();
    String entityID = EntityType.getKey(target.getType()).toString();
    if (EssenceGeneralConfig.getInstance().getSerializeEntity().get() == EntitySerializationEnum.BLACKLIST) {
      if (isEntityBlacklisted(entityID)) {
        return false;
      }
    } else {
      if (!isEntityWhitelisted(entityID)) {
        return false;
      }
    }
    SerializableMobRenderer.entityCache.put(uuid, target);
    stack.setTag(serializeNBT(target));
    target.remove();
    return true;
  }

  public CompoundNBT serializeNBT(LivingEntity entity) {
    CompoundNBT nbt = new CompoundNBT();
    nbt.putUniqueId("uuid", entity.getUniqueID());
    String entityID = EntityType.getKey(entity.getType()).toString();
    nbt.putString("entity", entityID);
    entity.writeWithoutTypeId(nbt);
    return nbt;
  }

  public WrenchModeEnum getMode() {
    return mode;
  }

  public void setMode(WrenchModeEnum mode) {
    this.mode = mode;
  }

  @Override
  public void handlePacketData(IWorld world, ItemStack stack, PacketBuffer packetBuffer) {
    if (!world.isRemote()) {
      setMode(packetBuffer.readEnumValue(WrenchModeEnum.class));
    }
  }

  @SuppressWarnings("ConstantConditions")
  public boolean isEntityBlacklisted(String entityID) {
    return EssenceEntityTags.BLACKLIST.getAllElements().stream().anyMatch(type -> type.getRegistryName().toString().equals(entityID));
  }

  @SuppressWarnings("ConstantConditions")
  public boolean isEntityWhitelisted(String entityID) {
    return EssenceEntityTags.WHITELIST.getAllElements().stream().anyMatch(type -> type.getRegistryName().toString().equals(entityID));
  }

  @Override
  public ActionResultType onItemUseModified(ItemUseContext context, boolean isRecursive) {
    return ActionResultType.PASS;
  }

  @Override
  public void addModifierWithoutIncreasingAdditional(int increase) {
  }

  @Override
  public void increaseFreeModifiers(int increase) {
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
}
