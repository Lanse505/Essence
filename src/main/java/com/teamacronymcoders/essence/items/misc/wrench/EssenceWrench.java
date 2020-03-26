package com.teamacronymcoders.essence.items.misc.wrench;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.utils.EssenceStats;
import com.teamacronymcoders.essence.utils.EssenceTags;
import com.teamacronymcoders.essence.utils.network.base.IItemNetwork;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.IProperty;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;

public class EssenceWrench extends Item implements IItemNetwork {

    private WrenchModeEnum mode;

    public EssenceWrench() {
        super(new Item.Properties().group(Essence.TOOL_TAB).maxStackSize(1).maxDamage(0).rarity(Rarity.RARE));
        this.mode = WrenchModeEnum.SERIALIZE;
    }

    private static <T extends Comparable<T>> String getStatePropertyValue(BlockState state, IProperty<T> property) {
        T prop = state.get(property);
        return property.getName(prop);
    }

    // TODO: Implement proper standardized tag handling once we figure out what we want to do with it.
    // BLACKLIST vs WHITELIST?
    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = context.getPlayer();

        if (player != null && !player.abilities.allowEdit && !stack.canPlaceOn(world.getTags(), new CachedBlockInfo(world, pos, false))) {
            return ActionResultType.PASS;
        }

        if (player != null && state.isIn(EssenceTags.Blocks.FORGE_MOVEABLE)) {
            if (mode == WrenchModeEnum.SERIALIZE && state.getProperties().size() > 0) {
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

                return ActionResultType.SUCCESS;
            }
        }

        if (player != null && mode == WrenchModeEnum.ROTATE) {
            if (player.isShiftKeyDown()) {
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
        list.add(new TranslationTextComponent("wrench.mode.tooltip").appendText(": ").appendSibling(new TranslationTextComponent(mode.getLocaleName())));
        if (flag == ITooltipFlag.TooltipFlags.ADVANCED) {
            list.add(new StringTextComponent(" "));
            list.add(new TranslationTextComponent("wrench.disclaimer"));
        }
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
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
}
