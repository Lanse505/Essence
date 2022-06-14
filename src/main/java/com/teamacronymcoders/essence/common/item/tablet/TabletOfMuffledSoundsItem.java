package com.teamacronymcoders.essence.common.item.tablet;

import com.teamacronymcoders.essence.client.util.keybindings.EssenceKeyHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class TabletOfMuffledSoundsItem extends TabletItem {

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (EssenceKeyHandler.CYCLING.isDown()) {
            toggle(player, stack);
        } else {
//            playerIn.openGui(SuperSoundMuffler.instance, GuiHandler.SOUND_MUFFLER_BAUBLE_GUI_ID, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
        }
        return InteractionResultHolder.success(stack);
    }

    public boolean shouldMuffleSound(ItemStack stack, ResourceLocation sound) {
        if (!stack.hasTag()) {
            return false;
        }

        CompoundTag compound = stack.getTag();
        if (compound == null) {
            return false;
        }
        if (compound.contains("disabled") && !compound.getBoolean("disabled")) {
            return false;
        }

        boolean isWhiteList = compound.contains("whiteList") && compound.getBoolean("whiteList");
        if (compound.contains("sounds")) {
            ListTag tags = compound.getList("sounds", Tag.TAG_COMPOUND);
            if (hasSound(tags, sound)) {
                return !isWhiteList;
            }
        }

        return isWhiteList;
    }

    public void toggleWhiteList(ItemStack stack) {
        boolean isWhiteList = false;
        if (stack.hasTag()) {
            CompoundTag compound = stack.getTag();
            if (compound == null) {
                return;
            }
            if (compound.contains("whiteList")) {
                isWhiteList = compound.getBoolean("whiteList");
            }

            compound.putBoolean("whiteList", !isWhiteList);
            stack.setTag(compound);
        } else {
            CompoundTag compound = new CompoundTag();
            compound.putBoolean("whiteList", true);
            stack.setTag(compound);
        }
    }

    public void mute(ItemStack stack, ResourceLocation sound) {
        CompoundTag compound = stack.hasTag() ? stack.getTag() : new CompoundTag();
        if (compound == null) {
            return;
        }

        ListTag tags = compound.contains("sounds") ? compound.getList("sounds", Tag.TAG_COMPOUND) : new ListTag();
        if (hasSound(tags, sound)) {
            return;
        }

        CompoundTag tag = new CompoundTag();
        tag.putString("sound", sound.toString());
        tags.add(tag);
        compound.put("sounds", tags);
        stack.setTag(compound);
    }

    public void unmute(ItemStack stack, ResourceLocation sound) {
        if (stack.hasTag()) {
            CompoundTag compound = stack.getTag();
            if (compound != null && compound.contains("sounds")) {
                ListTag tags = compound.getList("sounds", Tag.TAG_COMPOUND);
                ListTag newTags = new ListTag();
                for (int i = 0; i < tags.size(); ++i) {
                    CompoundTag s = tags.getCompound(i);
                    String soundLocation = s.getString("sound");
                    if (!soundLocation.equals(sound.toString())) {
                        newTags.add(s);
                    }
                }
                compound.put("sounds", newTags);
                stack.setTag(compound);
            }
        }
    }

    private boolean hasSound(ListTag tags, ResourceLocation check) {
        for (int i = 0; i < tags.size(); ++i) {
            CompoundTag s = tags.getCompound(i);
            String recorded = s.getString("sound");
            if (recorded.equals(check.toString())) {
                return true;
            }
        }
        return false;
    }

    private boolean isDisabled(ItemStack stack) {
        if (stack.hasTag()) {
            CompoundTag compound = stack.getTag();
            return compound.getBoolean("toggled");
        }
        return false;
    }

    private void toggle(Player playerIn, ItemStack stack) {
        if (stack.hasTag()) {
            CompoundTag compound = stack.getTag();
            if (compound != null && (compound.contains("toggled") || compound.contains("toggled") && compound.getBoolean("toggled"))) {
                compound.putBoolean("disabled", false);
                stack.setTag(compound);
                playerIn.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.1F, 1F);
            } else if (compound != null) {
                compound.putBoolean("disabled", true);
                stack.setTag(compound);
                playerIn.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.1F, 0.8F);
            }
        } else {
            CompoundTag compound = new CompoundTag();
            compound.putBoolean("disabled", true);
            stack.setTag(compound);
            playerIn.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.1F, 0.8F);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.sound_muffler_bauble.tooltip.header"));
        if (stack.hasTag()) {
            CompoundTag compound = stack.getTag();
            boolean showWhiteListTooltip = compound != null && (!compound.contains("whiteList") || compound.getBoolean("whiteList"));
            String key = showWhiteListTooltip ? "item.sound_muffler.tooltip.mode.white_list" : "item.sound_muffler.tooltip.mode.black_list";
            tooltip.add(Component.translatable(key));
            if (compound != null && compound.contains("sounds")) {
                ListTag tagList = compound.getList("sounds", Tag.TAG_COMPOUND);
                int count = tagList.size();
                tooltip.add(Component.translatable("item.sound_muffler.tooltip.sounds.count", count));
                if (EssenceInformationHelper.isSneakKeyDown()) {
                    for (int i = 0; i < tagList.size(); ++i) {
                        CompoundTag sound = tagList.getCompound(i);
                        tooltip.add(Component.translatable("item.sound_muffler.tooltip.sound", sound.getString("sound")));
                    }
                }
            } else {
                tooltip.add(Component.translatable("item.sound_muffler.tooltip.sounds.count", 0));
            }
        } else {
            tooltip.add(Component.translatable("item.sound_muffler.tooltip.mode.black_list"));
            tooltip.add(Component.translatable("item.sound_muffler.tooltip.sounds.count", 0));
        }
    }

}
