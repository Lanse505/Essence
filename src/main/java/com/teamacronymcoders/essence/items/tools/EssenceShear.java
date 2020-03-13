package com.teamacronymcoders.essence.items.tools;

import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.api.modifier.core.CoreModifier;
import com.teamacronymcoders.essence.api.tool.IModifiedTool;
import com.teamacronymcoders.essence.utils.EssenceObjectHolders;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import com.teamacronymcoders.essence.utils.helpers.EssenceModifierHelpers;
import com.teamacronymcoders.essence.utils.helpers.EssenceUtilHelper;
import com.teamacronymcoders.essence.utils.registration.EssenceModifierRegistration;
import com.teamacronymcoders.essence.utils.tiers.EssenceToolTiers;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class EssenceShear extends ShearsItem implements IModifiedTool {

    private int freeModifiers;
    private EssenceToolTiers tier;
    private int rainbowVal = 0;

    public EssenceShear(EssenceToolTiers tier) {
        super(new Item.Properties().maxDamage(tier.getMaxUses()).group(Essence.TOOL_TAB).rarity(tier.getRarity()));
        this.freeModifiers = tier.getFreeModifiers();
        this.tier = tier;
    }

    private static List<ItemStack> handleShearingSheep(SheepEntity sheep, ItemStack item, IWorld world, BlockPos pos, int fortune) {
        List<ItemStack> ret = new ArrayList<>();
        if (!sheep.world.isRemote) {
            sheep.setSheared(true);
            int i = EssenceUtilHelper.nextIntInclusive(1 + fortune, 4 + fortune);
            for (int j = 0; j < i; ++j) {
                ret.add(new ItemStack(SheepEntity.WOOL_BY_COLOR.get(sheep.getFleeceColor())));
            }
        }
        sheep.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
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
            ItemStack stack = new ItemStack(EssenceObjectHolders.ESSENCE_SHEAR_EMPOWERED);
            EssenceModifierHelpers.addModifier(stack, EssenceModifierRegistration.RAINBOW_MODIFIER.get(), 1, null);
            if (!items.contains(stack)) {
                items.add(stack);
            }
        }
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return super.getMaxDamage(stack) + EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof CoreModifier)
            .map(instance -> {
                CoreModifier modifier = (CoreModifier) instance.getModifier();
                return modifier.getModifiedDurability(stack, instance, tier.getMaxUses());
            }).reduce(0, Integer::sum);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return super.getDestroySpeed(stack, state) + EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof CoreModifier)
            .map(instance -> {
                CoreModifier modifier = (CoreModifier) instance.getModifier();
                return modifier.getModifiedEfficiency(stack, instance, super.getDestroySpeed(stack, state));
            }).reduce(0f, Float::sum);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        int harvestLevel = super.getHarvestLevel(stack, tool, player, blockState);
        return harvestLevel + EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof CoreModifier)
            .map(instance -> {
                CoreModifier modifier = (CoreModifier) instance.getModifier();
                return modifier.getModifiedHarvestLevel(stack, instance, harvestLevel);
            }).reduce(0, Integer::sum);
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
    public ActionResultType onItemUse(ItemUseContext context) {
        ActionResultType superResult = super.onItemUse(context);
        Optional<ActionResultType> modifierResult = EssenceModifierHelpers.getModifiers(context.getItem()).stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .map(instance -> ((InteractionCoreModifier) instance.getModifier()).onItemUse(context, instance))
            .filter(actionResultType -> actionResultType == ActionResultType.SUCCESS)
            .findFirst();
        return superResult == ActionResultType.SUCCESS ? superResult : modifierResult.orElse(superResult);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity entity, LivingEntity player) {
        EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .forEach(instance -> ((InteractionCoreModifier) instance.getModifier()).onHitEntity(stack, entity, player, instance));
        return super.hitEntity(stack, entity, player);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .forEach(instance -> ((InteractionCoreModifier) instance.getModifier()).onBlockDestroyed(stack, world, state, pos, miner, instance));
        return super.onBlockDestroyed(stack, world, state, pos, miner);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem) {
        EssenceEnchantmentHelper.checkEnchantmentsForRemoval(stack);
        EssenceModifierHelpers.getModifiers(stack).stream()
            .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
            .forEach(instance -> ((InteractionCoreModifier) instance.getModifier()).onInventoryTick(stack, world, entity, inventorySlot, isCurrentItem, instance));
        super.inventoryTick(stack, world, entity, inventorySlot, isCurrentItem);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity player, LivingEntity sheared, Hand hand) {
        if (sheared.world.isRemote) {
            return false;
        }
        if (sheared instanceof IShearable) {
            IShearable target = (IShearable) sheared;
            BlockPos pos = new BlockPos(sheared.getPosX(), sheared.getPosY(), sheared.getPosZ());
            if (target.isShearable(stack, sheared.world, pos)) {
                List<ItemStack> dropList = sheared instanceof SheepEntity ? handleShearingSheep((SheepEntity) sheared, stack, sheared.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack)) : target.onSheared(stack, sheared.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));

                // Gathers a list of Entries with InteractionCoreModifiers that also return a different value than the default
                List<ModifierInstance> matchingEntries = EssenceModifierHelpers.getModifiers(stack).stream()
                    .filter(instance -> instance.getModifier() instanceof InteractionCoreModifier)
                    .collect(Collectors.toList());

                // Loops over and Gathers the final modified list
                for (ModifierInstance instance : matchingEntries) {
                    InteractionCoreModifier interactionCoreModifier = (InteractionCoreModifier) instance.getModifier();
                    dropList = interactionCoreModifier.onSheared(stack, player, sheared, hand, dropList, instance);
                }

                // Handle dropping the final list of ItemStacks
                dropList.forEach(s -> {
                    ItemEntity entity = sheared.entityDropItem(s);
                    entity.setMotion(entity.getMotion().add(
                        (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F,
                        Essence.RANDOM.nextFloat() * 0.05F,
                        (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F));
                });

                // Damage the ItemStack
                stack.damageItem(1, sheared, entity -> entity.sendBreakAnimation(hand));
                return true;
            }
        }
        return false;
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

    public int getRainbowVal() {
        return rainbowVal;
    }

    public void setRainbowVal(int rainbowVal) {
        this.rainbowVal = rainbowVal;
    }
}
