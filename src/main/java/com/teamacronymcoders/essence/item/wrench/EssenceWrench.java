package com.teamacronymcoders.essence.item.wrench;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.holder.ModifierInstance;
import com.teamacronymcoders.essence.api.modified.IModifiedTool;
import com.teamacronymcoders.essence.api.modifier.item.ItemCoreModifier;
import com.teamacronymcoders.essence.capability.EssenceCoreCapability;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierHolder;
import com.teamacronymcoders.essence.capability.itemstack.modifier.ItemStackModifierProvider;
import com.teamacronymcoders.essence.item.wrench.config.BlockSerializationEnum;
import com.teamacronymcoders.essence.item.wrench.config.EntitySerializationEnum;
import com.teamacronymcoders.essence.modifier.item.enchantment.EfficiencyModifier;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.EssenceStats;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceBlockTags;
import com.teamacronymcoders.essence.util.EssenceTags.EssenceEntityTags;
import com.teamacronymcoders.essence.util.config.EssenceGeneralConfig;
import com.teamacronymcoders.essence.util.keybindings.EssenceKeyHandler;
import com.teamacronymcoders.essence.util.network.base.IItemNetwork;
import com.teamacronymcoders.essence.util.tier.EssenceItemTiers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
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
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.Property;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import javax.swing.*;
import java.util.List;
import java.util.Optional;

public class EssenceWrench extends Item implements IModifiedTool, IItemNetwork {

    private WrenchModeEnum mode;
    private final int baseModifiers = 1;
    private int freeModifiers;
    private final int additionalModifiers = 0;

    public EssenceWrench() {
        super(new Item.Properties().group(Essence.TOOL_TAB).maxStackSize(1).maxDamage(2048).rarity(Rarity.RARE));
        this.mode = WrenchModeEnum.SERIALIZE;
        this.freeModifiers = 1;
    }

    private static <T extends Comparable<T>> String getStatePropertyValue(BlockState state, Property<T> property) {
        T prop = state.get(property);
        return property.getName(prop);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {
        if (target.getEntityWorld().isRemote) return ActionResultType.FAIL;
        LazyOptional<ItemStackModifierHolder> lazy = stack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER);
        return lazy.isPresent() ? lazy.map(holder -> {
            Optional<ModifierInstance<ItemStack>> optional = holder.getModifierInstances().stream().filter(instance -> instance.getModifier() instanceof EfficiencyModifier).findAny();
            ItemStack serialized = new ItemStack(EssenceObjectHolders.ENTITY_ITEM);
            boolean successful;
            if (optional.isPresent()) {
                successful = serializeEntity(serialized, player, target, hand, true);
            } else {
                successful = serializeEntity(serialized, player, target, hand, false);
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

        if (state.isAir(world, pos) || !state.getFluidState().equals(Fluids.EMPTY.getDefaultState()) || player != null && !player.abilities.allowEdit && !stack.canPlaceOn(world.getTags(), new CachedBlockInfo(world, pos, false))) {
            return ActionResultType.PASS;
        }

        if (player != null) {
            BlockSerializationEnum config = EssenceGeneralConfig.getInstance().getSerializeBlock().get();
            if (config == BlockSerializationEnum.BLACKLIST) {
                if (mode == WrenchModeEnum.SERIALIZE && state.getProperties().size() > 0 && state.isIn(EssenceBlockTags.FORGE_MOVEABLE_BLACKLIST)) {
                    TileEntity te = world.getTileEntity(pos);
                    ItemStack drop = new ItemStack(state.getBlock());
                    CompoundNBT stateNBT = new CompoundNBT();
                    state.getProperties().forEach(iProperty -> stateNBT.putString(iProperty.getName(), getStatePropertyValue(state, iProperty)));
                    drop.setTagInfo("BlockStateTag", stateNBT);

                    if (te != null) {
                        drop.setTagInfo("BlockEntityTag", te.serializeNBT());
                    }

                    player.addStat(EssenceStats.INSTANCE.SERIALIZED);
                    player.addStat(Stats.ITEM_USED.get(this));

                    world.removeTileEntity(pos);
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), Constants.BlockFlags.DEFAULT_AND_RERENDER);

                    ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), drop);
                    itemEntity.setDefaultPickupDelay();
                    world.addEntity(itemEntity);

                    stack.damageItem(1, player, playerEntity -> playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));

                    return ActionResultType.SUCCESS;
                }
            } else {
                if (mode == WrenchModeEnum.SERIALIZE && state.getProperties().size() > 0 && state.isIn(EssenceBlockTags.FORGE_MOVEABLE_WHITELIST)) {
                    TileEntity te = world.getTileEntity(pos);
                    ItemStack drop = new ItemStack(state.getBlock());
                    CompoundNBT stateNBT = new CompoundNBT();
                    state.getProperties().forEach(iProperty -> stateNBT.putString(iProperty.getName(), getStatePropertyValue(state, iProperty)));
                    drop.setTagInfo("BlockStateTag", stateNBT);

                    if (te != null) {
                        drop.setTagInfo("BlockEntityTag", te.serializeNBT());
                    }

                    player.addStat(EssenceStats.INSTANCE.SERIALIZED);
                    player.addStat(Stats.ITEM_USED.get(this));

                    world.removeTileEntity(pos);
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), Constants.BlockFlags.DEFAULT_AND_RERENDER);

                    ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), drop);
                    itemEntity.setDefaultPickupDelay();
                    world.addEntity(itemEntity);

                    stack.damageItem(1, player, playerEntity -> playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));

                    return ActionResultType.SUCCESS;
                }
            }
        }

        if (player != null && mode == WrenchModeEnum.ROTATE) {
            if (Minecraft.getInstance().gameSettings.keyBindSneak.isKeyDown()) {
                state.rotate(Rotation.CLOCKWISE_180);
            }
            state.rotate(Rotation.CLOCKWISE_90);
            player.addStat(Stats.ITEM_USED.get(this));
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        list.add(new TranslationTextComponent("essence.wrench.mode.tooltip").appendString(" ").append(new TranslationTextComponent(mode.getLocaleName())));
        if (flag == ITooltipFlag.TooltipFlags.ADVANCED && mode == WrenchModeEnum.SERIALIZE) {
            list.add(new StringTextComponent(" "));
            list.add(new TranslationTextComponent("essence.wrench.disclaimer"));
        }
        addInformationFromModifiers(stack, world, list, flag, EssenceItemTiers.ESSENCE);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (nbt != null && !nbt.isEmpty()) {
            return new ItemStackModifierProvider(stack, nbt);
        }
        return new ItemStackModifierProvider(stack);
    }

    public boolean serializeEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand, boolean checkBoss) {
        if (target.getEntityWorld().isRemote) return false;
        if (checkBoss) {
            if (target instanceof PlayerEntity || !target.isNonBoss() || !target.isAlive()) return false;
        } else {
            if (target instanceof PlayerEntity || !target.isAlive()) return false;
        }
        return stack.getCapability(EssenceCoreCapability.ENTITY_STORAGE).map(storage -> {
            String entityID = EntityType.getKey(target.getType()).toString();
            if (EssenceGeneralConfig.getInstance().getSerializeEntity().get() == EntitySerializationEnum.BLACKLIST) {
                if (isEntityBlacklisted(entityID)) return false;
            } else {
                if (!isEntityWhitelisted(entityID)) return false;
            }
            storage.setEntity(target);
            playerIn.swingArm(hand);
            target.remove(true);
            return true;
        }).orElse(false);
    }

    public WrenchModeEnum getMode() {
        return mode;
    }

    public void setMode(WrenchModeEnum mode) {
        this.mode = mode;
    }

    @Override
    public void handlePacketData(IWorld world, ItemStack stack, PacketBuffer dataStream) {
        if (!world.isRemote()) {
            setMode(dataStream.readEnumValue(WrenchModeEnum.class));
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
    public void addModifierWithoutIncreasingAdditional(int increase){}

    @Override
    public void increaseFreeModifiers(int increase) {}

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
}
