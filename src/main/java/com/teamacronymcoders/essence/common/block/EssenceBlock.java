package com.teamacronymcoders.essence.common.block;

import com.hrznstudio.titanium.api.IFactory;
import com.teamacronymcoders.essence.common.util.tier.EssenceItemTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class EssenceBlock extends Block {
    private final EssenceItemTiers tier;

    public EssenceBlock(Block.Properties properties, EssenceItemTiers tier) {
        super(properties);
        this.tier = tier;
    }

    public IFactory<BlockItem> getBlockItem(Item.Properties properties) {
        return () -> (BlockItem) new BlockItem(this, properties.rarity(tier.getRarity())) {
            @Override
            public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> list, @Nonnull TooltipFlag flagIn) {
                list.add(Component.translatable("tooltip.essence.tool.tier").withStyle(ChatFormatting.GRAY).append(Component.translatable(tier.getLocaleString()).withStyle(tier.getRarity().color)));
            }
        };
    }
}
