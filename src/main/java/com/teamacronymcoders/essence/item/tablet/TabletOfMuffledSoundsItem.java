package com.teamacronymcoders.essence.item.tablet;

import com.teamacronymcoders.essence.util.helper.EssenceInformationHelper;
import com.teamacronymcoders.essence.util.keybindings.EssenceKeyHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;

public class TabletOfMuffledSoundsItem extends TabletItem {

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(EssenceKeyHandler.CYCLING.isKeyDown()) {
            toggle(player, stack);
        } else {
//            playerIn.openGui(SuperSoundMuffler.instance, GuiHandler.SOUND_MUFFLER_BAUBLE_GUI_ID, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
        }
        return ActionResult.resultSuccess(stack);
    }

    public boolean shouldMuffleSound(ItemStack stack, ResourceLocation sound) {
        if(!stack.hasTag()) {
            return false;
        }

        CompoundNBT compound = stack.getTag();
        if (compound == null) return false;
        if(compound.contains("disabled") && !compound.getBoolean("disabled")) {
            return false;
        }

        boolean isWhiteList = compound.contains("whiteList") && compound.getBoolean("whiteList");
        if(compound.contains("sounds")) {
            ListNBT tags = compound.getList("sounds", Constants.NBT.TAG_COMPOUND);
            if(hasSound(tags, sound)) {
                return !isWhiteList;
            }
        }

        return isWhiteList;
    }

    public void toggleWhiteList(ItemStack stack) {
        boolean isWhiteList = false;
        if(stack.hasTag()) {
            CompoundNBT compound = stack.getTag();
            if (compound == null) {
                return;
            }
            if(compound.contains("whiteList")) {
                isWhiteList = compound.getBoolean("whiteList");
            }

            compound.putBoolean("whiteList", !isWhiteList);
            stack.setTag(compound);
        } else {
            CompoundNBT compound = new CompoundNBT();
            compound.putBoolean("whiteList", true);
            stack.setTag(compound);
        }
    }

    public void mute(ItemStack stack, ResourceLocation sound) {
        CompoundNBT compound = stack.hasTag() ? stack.getTag() : new CompoundNBT();
        if (compound == null) {
            return;
        }

        ListNBT tags = compound.contains("sounds") ? compound.getList("sounds", Constants.NBT.TAG_COMPOUND) : new ListNBT();
        if(hasSound(tags, sound)) {
            return;
        }

        CompoundNBT tag = new CompoundNBT();
        tag.putString("sound", sound.toString());
        tags.add(tag);
        compound.put("sounds", tags);
        stack.setTag(compound);
    }

    public void unmute(ItemStack stack, ResourceLocation sound) {
        if(stack.hasTag()) {
            CompoundNBT compound = stack.getTag();
            if (compound != null && compound.contains("sounds")) {
                ListNBT tags = compound.getList("sounds", Constants.NBT.TAG_COMPOUND);
                ListNBT newTags = new ListNBT();
                for(int i = 0; i < tags.size(); ++i) {
                    CompoundNBT s = tags.getCompound(i);
                    String soundLocation = s.getString("sound");
                    if(!soundLocation.equals(sound.toString())) {
                        newTags.add(s);
                    }
                }
                compound.put("sounds", newTags);
                stack.setTag(compound);
            }
        }
    }

    private boolean hasSound(ListNBT tags, ResourceLocation check) {
        for(int i = 0; i < tags.size(); ++i) {
            CompoundNBT s = tags.getCompound(i);
            String recorded = s.getString("sound");
            if(recorded.equals(check.toString())) {
                return true;
            }
        }
        return false;
    }

    private boolean isDisabled(ItemStack stack) {
        if(stack.hasTag()) {
            CompoundNBT compound = stack.getTag();
            return compound.getBoolean("toggled");
        }
        return false;
    }

    private void toggle(PlayerEntity playerIn, ItemStack stack) {
        if(stack.hasTag()) {
            CompoundNBT compound = stack.getTag();
            if(compound != null && (compound.contains("toggled") || compound.contains("toggled") && compound.getBoolean("toggled"))) {
                compound.putBoolean("disabled", false);
                stack.setTag(compound);
                playerIn.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1F, 1F);
            } else if (compound != null) {
                compound.putBoolean("disabled", true);
                stack.setTag(compound);
                playerIn.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1F, 0.8F);
            }
        } else {
            CompoundNBT compound = new CompoundNBT();
            compound.putBoolean("disabled", true);
            stack.setTag(compound);
            playerIn.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1F, 0.8F);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.sound_muffler_bauble.tooltip.header"));
        if(stack.hasTag()) {
            CompoundNBT compound = stack.getTag();
            boolean showWhiteListTooltip = compound != null && (!compound.contains("whiteList") || compound.getBoolean("whiteList"));
            String key = showWhiteListTooltip ? "item.sound_muffler.tooltip.mode.white_list" : "item.sound_muffler.tooltip.mode.black_list";
            tooltip.add(new TranslationTextComponent(key));
            if(compound != null && compound.contains("sounds")) {
                ListNBT tagList = compound.getList("sounds", 10);
                int count = tagList.size();
                tooltip.add(new TranslationTextComponent("item.sound_muffler.tooltip.sounds.count", count));
                if(EssenceInformationHelper.isSneakKeyDown()) {
                    for(int i = 0; i < tagList.size(); ++i) {
                        CompoundNBT sound = tagList.getCompound(i);
                        tooltip.add(new TranslationTextComponent("item.sound_muffler.tooltip.sound", sound.getString("sound")));
                    }
                }
            } else {
                tooltip.add(new TranslationTextComponent("item.sound_muffler.tooltip.sounds.count", 0));
            }
        } else {
            tooltip.add(new TranslationTextComponent("item.sound_muffler.tooltip.mode.black_list"));
            tooltip.add(new TranslationTextComponent("item.sound_muffler.tooltip.sounds.count", 0));
        }
    }

}
