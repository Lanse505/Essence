package com.teamacronymcoders.essence.items.tools;

import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
import com.teamacronymcoders.essence.api.tool.IModifiedTool;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import com.teamacronymcoders.essence.utils.helpers.EssenceBowHelper;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import com.teamacronymcoders.essence.utils.helpers.EssenceModifierHelpers;
import com.teamacronymcoders.essence.utils.helpers.EssenceUtilHelper;
import com.teamacronymcoders.essence.utils.registration.EssenceModifierRegistration;
import com.teamacronymcoders.essence.utils.tiers.EssenceToolTiers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EssenceBow extends BowItem implements IModifiedTool {

    private int freeModifiers;
    private EssenceToolTiers tier;

    public EssenceBow(EssenceToolTiers tier) {
        super(new Item.Properties().maxDamage(tier.getMaxUsesBow()).group(Essence.TOOL_TAB).rarity(tier.getRarity()));
        this.freeModifiers = tier.getFreeModifiers();
        this.tier = tier;
        this.addPropertyOverride(new ResourceLocation("pull"), (stack, world, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return !(livingEntity.getActiveItemStack().getItem() instanceof BowItem) ? 0.0F : (float) (stack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), (stack, world, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == stack ? 1.0F : 0.0F);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return EssenceModifierHelpers.hasEnchantedModifier(stack);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            ItemStack stack = new ItemStack(EssenceObjectHolders.ESSENCE_BOW_EMPOWERED);
            EssenceModifierHelpers.addModifier(stack, EssenceModifierRegistration.BREWED_MODIFIER.get(), 1, EssenceBowHelper.createEffectInstanceNBT(new EffectInstance(Effects.POISON, 1200, 3)));
            EssenceModifierHelpers.addModifier(stack, EssenceModifierRegistration.KEEN_MODIFIER.get(), 4, null);
            if (!items.contains(stack)) {
                items.add(stack);
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        if (slot == EquipmentSlotType.MAINHAND) {
            Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot);
            EssenceModifierHelpers.getModifiers(stack).stream()
                .map(instance -> instance.getModifier().getAttributeModifiers(stack, null, instance))
                .forEach(modifierMultimap -> modifierMultimap.entries().forEach(entry -> multimap.put(entry.getKey(), entry.getValue())));
            return multimap;
        }
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity entity, LivingEntity player) {
        EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .forEach(instance -> ((InteractionCoreModifier) instance.getModifier()).onHitEntity(stack, entity, player, instance));
        return super.hitEntity(stack, entity, player);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem) {
        //Essence.LOGGER.info(stack.getTag().toString());
        EssenceEnchantmentHelper.checkEnchantmentsForRemoval(stack);
        EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .forEach(instance -> ((InteractionCoreModifier) instance.getModifier()).onInventoryTick(stack, world, entity, inventorySlot, isCurrentItem, instance));
        super.inventoryTick(stack, world, entity, inventorySlot, isCurrentItem);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack bow, World world, LivingEntity livingEntity, int timeLeft) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) livingEntity;
            boolean flag = player.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow) > 0;
            ItemStack arrow = player.findAmmo(bow);

            int i = this.getUseDuration(bow) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(bow, world, player, i, !arrow.isEmpty() || flag);
            if (i < 0) {
                return;
            }

            if (!arrow.isEmpty() || flag) {
                if (arrow.isEmpty()) {
                    arrow = new ItemStack(Items.ARROW);
                }

                float f = getArrowVelocity(i);
                if (!((double) f < 0.1D)) {
                    boolean flag1 = player.abilities.isCreativeMode || (arrow.getItem() instanceof ArrowItem && ((ArrowItem) arrow.getItem()).isInfinite(arrow, bow, player));
                    if (!world.isRemote) {
                        AbstractArrowEntity abstractarrowentity = EssenceBowHelper.getArrowEntity(world, bow, arrow, player, f);
                        EssenceBowHelper.modifyArrowEntityWithEnchantments(abstractarrowentity, bow);
                        bow.damageItem(1, player, (p_220009_1_) -> {
                            p_220009_1_.sendBreakAnimation(player.getActiveHand());
                        });
                        world.addEntity(abstractarrowentity);
                    }
                    world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !player.abilities.isCreativeMode) {
                        arrow.shrink(1);
                        if (arrow.isEmpty()) {
                            player.inventory.deleteStack(arrow);
                        }
                    }
                    player.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        list.add(new TranslationTextComponent("tooltip.essence.tool.tier").applyTextStyle(TextFormatting.GRAY).appendSibling(new TranslationTextComponent(tier.getLocalName()).applyTextStyle(tier.getRarity().color)));
        list.add(new TranslationTextComponent("tooltip.essence.modifier.free", new StringTextComponent(String.valueOf(freeModifiers)).applyTextStyle(EssenceUtilHelper.getTextColor(freeModifiers))).applyTextStyle(TextFormatting.GRAY));
        if (stack.getOrCreateTag().contains(EssenceModifierHelpers.TAG_MODIFIERS)) {
            list.add(new TranslationTextComponent("tooltip.essence.modifier").applyTextStyle(TextFormatting.GOLD));
            Map<String, List<ITextComponent>> sorting_map = new HashMap<>();
            EssenceModifierHelpers.getModifiers(stack).forEach(instance -> sorting_map.put(instance.getModifier().getRenderedText(instance).get(0).getString(), instance.getModifier().getRenderedText(instance)));
            sorting_map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (string, component) -> component, LinkedHashMap::new))
                .forEach((s, iTextComponents) -> list.addAll(iTextComponents));
            list.add(new StringTextComponent(""));
        }
    }

    @Override
    public ActionResultType onItemUseModified(ItemUseContext context, boolean isRecursive) {
        return ActionResultType.PASS;
    }
}
