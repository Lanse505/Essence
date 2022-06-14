package com.teamacronymcoders.essence.common.item.wrench;

import com.teamacronymcoders.essence.api.capabilities.EssenceCapability;
import com.teamacronymcoders.essence.api.modified.rewrite.IModifiedItem;
import com.teamacronymcoders.essence.api.modified.rewrite.itemstack.ItemStackModifierHolder;
import com.teamacronymcoders.essence.api.modified.rewrite.itemstack.ItemStackModifierProvider;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.client.render.tesr.itemstack.SerializableMobRenderer;
import com.teamacronymcoders.essence.common.item.wrench.config.BlockSerializationEnum;
import com.teamacronymcoders.essence.common.item.wrench.config.EntitySerializationEnum;
import com.teamacronymcoders.essence.common.modifier.item.enchantment.EfficiencyModifier;
import com.teamacronymcoders.essence.common.util.EssenceStats;
import com.teamacronymcoders.essence.common.util.EssenceTags;
import com.teamacronymcoders.essence.common.util.EssenceTags.EssenceBlockTags;
import com.teamacronymcoders.essence.common.util.EssenceTags.EssenceEntityTags;
import com.teamacronymcoders.essence.common.util.config.EssenceGeneralConfig;
import com.teamacronymcoders.essence.common.util.network.base.IItemNetwork;
import com.teamacronymcoders.essence.common.util.tier.EssenceToolTiers;
import com.teamacronymcoders.essence.compat.registrate.EssenceItemRegistrate;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EssenceWrench extends Item implements IModifiedItem, IItemNetwork {

    public EssenceWrench(Properties properties) {
        super(properties);
    }

    private static <T extends Comparable<T>> String getStatePropertyValue(BlockState state, Property<T> property) {
        T prop = state.getValue(property);
        return property.getName(prop);
    }

    @Override
    @ParametersAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (target.level.isClientSide()) {
            return InteractionResult.FAIL;
        }
        LazyOptional<ItemStackModifierHolder> lazy = stack.getCapability(EssenceCapability.ITEMSTACK_MODIFIER_HOLDER);
        return lazy.isPresent() ? lazy.map(holder -> {
            Optional<ModifierInstance> optional = holder.getModifierInstances().stream().filter(instance -> instance.getModifier().get() instanceof EfficiencyModifier).findAny();
            ItemStack serialized = new ItemStack(EssenceItemRegistrate.SERIALIZED_ENTITY.get());
            boolean successful;
            if (optional.isPresent()) {
                successful = serializeEntity(serialized, target, true);
            } else {
                successful = serializeEntity(serialized, target, false);
            }
            if (successful) {
                player.addItem(serialized);
                stack.hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(hand));
            }
            return successful ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        }).orElse(InteractionResult.FAIL) : InteractionResult.FAIL;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        Player player = context.getPlayer();
        BlockEntity be = world.getBlockEntity(pos);
        BlockSerializationEnum config = EssenceGeneralConfig.getInstance().getSerializeBlock().get();

        if (state.isAir() || !state.getFluidState().equals(Fluids.EMPTY.defaultFluidState()) || player != null && !player.getAbilities().mayBuild && !stack.hasAdventureModePlaceTagForBlock(world.getTagManager(), new BlockInWorld(world, pos, false))) {
            return InteractionResult.PASS;
        }

        if (player != null && stack.getTag() != null) {
            if (stack.getTag().getString("wrench_mode").equals(WrenchModeEnum.SERIALIZE.getName()) && (state.getProperties().size() > 0 || state.hasBlockEntity()) && ((((config == BlockSerializationEnum.BLACKLIST ? !state.is(EssenceBlockTags.FORGE_MOVEABLE_BLACKLIST) : state.is(EssenceBlockTags.FORGE_MOVEABLE_WHITELIST)) && !state.is(EssenceBlockTags.RELOCATION_NOT_SUPPORTED)) || (be != null && (!be.getType().isIn(EssenceTags.EssenceTileEntityTypeTags.IMMOVABLE) && !be.getType().isIn(EssenceTags.EssenceTileEntityTypeTags.RELOCATION_NOT_SUPPORTED)))) && !state.getPistonPushReaction().equals(PushReaction.BLOCK))) {
                ItemStack drop = new ItemStack(state.getBlock());
                CompoundTag stateNBT = new CompoundTag();
                state.getProperties().forEach(iProperty -> stateNBT.putString(iProperty.getName(), getStatePropertyValue(state, iProperty)));
                // Serializes the BlockState
                drop.addTagElement("BlockStateTag", stateNBT);
                // Serializes the TE
                if (be != null) {
                    drop.addTagElement("BlockEntityTag", be.serializeNBT());
                }
                // Adds the Stats
                player.awardStat(EssenceStats.INSTANCE.SERIALIZED);
                player.awardStat(Stats.ITEM_USED.get(this));
                // Cleans up the World and Drops the Item
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                world.addFreshEntity(Util.make(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), drop), ItemEntity::setDefaultPickUpDelay));
                stack.hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                return InteractionResult.SUCCESS;
            }

            if (stack.getTag().getString("wrench_mode").equals(WrenchModeEnum.ROTATE.getName())) {
                if (EssenceInformationHelper.isSneakKeyDown()) {
                    state.rotate(world, pos, Rotation.CLOCKWISE_180);
                }
                state.rotate(world, pos, Rotation.CLOCKWISE_90);
                player.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        addInformationFromModifiers(stack, level, list, flag);
        if (stack.getTag() != null && stack.getTag().contains("wrench_mode"))
            list.add(Component.translatable("essence.wrench.mode.tooltip").withStyle(ChatFormatting.GRAY, ChatFormatting.BOLD).withStyle(ChatFormatting.WHITE).append(Component.translatable(WrenchModeEnum.byName(stack.getTag().getString("wrench_mode")).getLocaleName())));
        if (stack.getTag() != null && flag == TooltipFlag.Default.ADVANCED && stack.getTag().getString("wrench_mode").equals(WrenchModeEnum.SERIALIZE.getName())) {
            list.add(Component.translatable("essence.wrench.disclaimer").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
            list.add(Component.translatable("essence.wrench.disclaimer_message"));
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag tag) {
        if (tag != null && !tag.isEmpty()) {
            return new ItemStackModifierProvider(stack, tag);
        }
        return new ItemStackModifierProvider(stack);
    }

    public boolean serializeEntity(ItemStack stack, LivingEntity target, boolean checkBoss) {
        if (target.level.isClientSide()) {
            return false;
        }
        if (checkBoss) {
            if (target instanceof Player || !target.canChangeDimensions() || !target.isAlive()) {
                return false;
            }
        } else {
            if (target instanceof Player || !target.isAlive()) {
                return false;
            }
        }
        UUID uuid = UUID.fromString(target.getStringUUID());
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
        target.remove(Entity.RemovalReason.DISCARDED);
        return true;
    }

    public CompoundTag serializeNBT(LivingEntity entity) {
        CompoundTag nbt = new CompoundTag();
        nbt.putUUID("uuid", UUID.fromString(entity.getStringUUID()));
        String entityID = EntityType.getKey(entity.getType()).toString();
        nbt.putString("entity", entityID);
        entity.load(nbt);
        return nbt;
    }

    public WrenchModeEnum getMode(ItemStack stack) {
        return stack.getTag() != null && stack.getTag().contains("wrench_mode") ? WrenchModeEnum.byName(stack.getTag().getString("wrench_mode")) : WrenchModeEnum.SERIALIZE;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(stack, pLevel, pEntity, pSlotId, pIsSelected);
        if (pIsSelected) {
            CompoundTag tag;
            if (stack.getTag() == null) {
                tag = stack.getOrCreateTag();
                tag.putString("wrench_mode", WrenchModeEnum.SERIALIZE.getName());
            }
        }
    }

    public void setMode(ItemStack stack, WrenchModeEnum mode) {
        CompoundTag tag;
        if (stack.getTag() != null) {
            tag = stack.getTag();
        } else {
            tag = stack.getOrCreateTag();
        }
        tag.putString("wrench_mode", mode.getName());
    }

    @Override
    public void handlePacketData(LevelAccessor accessor, ItemStack stack, FriendlyByteBuf packetBuffer) {
        if (!accessor.isClientSide()) {
            setMode(stack, packetBuffer.readEnum(WrenchModeEnum.class));
        }
    }

    @SuppressWarnings("ConstantConditions")
    public boolean isEntityBlacklisted(String entityID) {
        return EssenceEntityTags.BLACKLIST.getValues().stream().anyMatch(type -> type.getRegistryName().toString().equals(entityID));
    }

    @SuppressWarnings("ConstantConditions")
    public boolean isEntityWhitelisted(String entityID) {
        return EssenceEntityTags.WHITELIST.getValues().stream().anyMatch(type -> type.getRegistryName().toString().equals(entityID));
    }

    @Override
    public InteractionResult useOnModified(UseOnContext context, boolean isRecursive) {
        return InteractionResult.PASS;
    }

    @Override
    public EssenceToolTiers getTier() {
        return EssenceToolTiers.ESSENCE;
    }
}
