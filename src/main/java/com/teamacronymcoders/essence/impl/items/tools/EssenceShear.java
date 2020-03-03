package com.teamacronymcoders.essence.impl.items.tools;

import com.google.common.collect.Multimap;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.modifier.InteractionCoreModifier;
import com.teamacronymcoders.essence.api.modifier.core.CoreModifier;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import com.teamacronymcoders.essence.api.tool.IModifiedTool;
import com.teamacronymcoders.essence.utils.EssenceRegistration;
import com.teamacronymcoders.essence.utils.helpers.EssenceEnchantmentHelper;
import com.teamacronymcoders.essence.utils.helpers.EssenceModifierHelpers;
import com.teamacronymcoders.essence.utils.helpers.EssenceUtilHelper;
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
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.ToolType;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

import static com.teamacronymcoders.essence.utils.EssenceItemTiers.ESSENCE;

public class EssenceShear extends ShearsItem implements IModifiedTool {

    private int freeModifiers;

    public EssenceShear(ResourceLocation resourceLocation) {
        super(new Item.Properties().maxDamage(238).group(Essence.TOOL_TAB));
        setRegistryName(resourceLocation);
        freeModifiers = 5;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> list) {
        if (this.isInGroup(group)) {
            ItemStack stack;
            for (int i = 1; i < 6; i++) {
                stack = new ItemStack(this);;
                EssenceModifierHelpers.addModifier(stack, EssenceRegistration.RAINBOW_MODIFIER.get(), 1);
                EssenceModifierHelpers.addModifier(stack, EssenceRegistration.LUCK_MODIFIER.get(), i);
                if (!list.contains(stack)) {
                    list.add(stack);
                }
            }
        }
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
        return EssenceModifierHelpers.getModifiers(stack).containsKey(EssenceRegistration.ENCHANTED_MODIFIER.get());
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return super.getMaxDamage(stack) + EssenceModifierHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof CoreModifier)
            .map(modifierEntry -> Pair.of(((CoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
            .map(modifierPair -> modifierPair.getLeft().getModifiedDurability(stack, modifierPair.getRight(), ESSENCE.getMaxUses())).reduce(0, Integer::sum);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return super.getDestroySpeed(stack, state) + EssenceModifierHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof CoreModifier)
            .map(modifierEntry -> Pair.of(((CoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
            .map(modifierPair -> modifierPair.getLeft().getModifiedEfficiency(stack, modifierPair.getRight(), super.getDestroySpeed(stack, state))).reduce(0f, Float::sum);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        int harvestLevel = super.getHarvestLevel(stack, tool, player, blockState);
        return harvestLevel + EssenceModifierHelpers.getModifiers(stack).entrySet().stream().filter(modifierEntry -> modifierEntry.getKey() instanceof CoreModifier)
            .map(modifierEntry -> Pair.of(((CoreModifier) modifierEntry.getKey()), modifierEntry.getValue()))
            .map(modifierPair -> modifierPair.getLeft().getModifiedHarvestLevel(stack, modifierPair.getRight(), harvestLevel)).reduce(0, Integer::sum);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        if (slot == EquipmentSlotType.MAINHAND) {
            Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot);
            EssenceModifierHelpers.getModifiers(stack).entrySet()
                .stream()
                .map(entry -> entry.getKey().getAttributeModifiers(stack, null, entry.getValue()))
                .forEach(modifierMultimap -> modifierMultimap.entries().forEach(entry -> multimap.put(entry.getKey(), entry.getValue())));
            return multimap;
        }
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ActionResultType superResult = super.onItemUse(context);
        Optional<ActionResultType> modifierResult = EssenceModifierHelpers.getModifiers(context.getItem())
            .entrySet()
            .stream()
            .filter(modifierEntry -> modifierEntry.getKey() instanceof InteractionCoreModifier)
            .map(modifierEntry -> ((InteractionCoreModifier) modifierEntry.getKey()).onItemUse(context, modifierEntry.getValue()))
            .filter(actionResultType -> actionResultType == ActionResultType.SUCCESS)
            .findFirst();
        return superResult == ActionResultType.SUCCESS ? superResult : modifierResult.orElse(superResult);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity entity, LivingEntity player) {
        EssenceModifierHelpers.getModifiers(stack)
            .entrySet()
            .stream()
            .filter(modifierEntry -> modifierEntry.getKey() instanceof InteractionCoreModifier)
            .forEach(modifierEntry -> ((InteractionCoreModifier) modifierEntry.getKey()).onHitEntity(stack, entity, player, modifierEntry.getValue()));
        return super.hitEntity(stack, entity, player);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        EssenceModifierHelpers.getModifiers(stack)
            .entrySet()
            .stream()
            .filter(modifierEntry -> modifierEntry.getKey() instanceof InteractionCoreModifier)
            .forEach(modifierEntry -> ((InteractionCoreModifier) modifierEntry.getKey()).onBlockDestroyed(stack, world, state, pos, miner, modifierEntry.getValue()));
        return super.onBlockDestroyed(stack, world, state, pos, miner);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int inventorySlot, boolean isCurrentItem) {
        EssenceEnchantmentHelper.checkEnchantmentsForRemoval(stack);
        EssenceModifierHelpers.getModifiers(stack)
            .entrySet()
            .stream()
            .filter(modifierEntry -> modifierEntry.getKey() instanceof InteractionCoreModifier)
            .forEach(modifierEntry -> ((InteractionCoreModifier) modifierEntry.getKey()).onInventoryTick(stack, world, entity, inventorySlot, isCurrentItem, modifierEntry.getValue()));
        super.inventoryTick(stack, world, entity, inventorySlot, isCurrentItem);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity player, LivingEntity sheared, Hand hand) {
        if (sheared.world.isRemote) return false;
        if (sheared instanceof IShearable) {
            IShearable target = (IShearable) sheared;
            BlockPos pos = new BlockPos(sheared.getPosX(), sheared.getPosY(), sheared.getPosZ());
            if (target.isShearable(stack, sheared.world, pos)) {
                final List<ItemStack> baseDropList = sheared instanceof SheepEntity ? handleShearingSheep((SheepEntity) sheared, stack, sheared.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack)) : target.onSheared(stack, sheared.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));
                List<ItemStack> dropList = baseDropList;

                // Gathers a list of Entries with InteractionCoreModifiers that also return a different value than the default
                List<Pair<Modifier, Integer>> matchingEntries = EssenceModifierHelpers.getModifiers(stack)
                    .entrySet()
                    .stream()
                    .filter(modifierEntry -> modifierEntry.getKey() instanceof InteractionCoreModifier)
                    .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());

                // Loops over and Gathers the final modified list
                for (Pair<Modifier, Integer> pair : matchingEntries) {
                    InteractionCoreModifier interactionCoreModifier = (InteractionCoreModifier) pair.getKey();
                    dropList = interactionCoreModifier.onSheared(stack, player, sheared, hand, dropList, pair.getValue());
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
        list.add(new TranslationTextComponent("tooltip.essence.modifier.free", new StringTextComponent(String.valueOf(freeModifiers)).applyTextStyle(EssenceUtilHelper.getTextColor(freeModifiers))).applyTextStyle(TextFormatting.GRAY));
        if (stack.getOrCreateTag().contains(EssenceModifierHelpers.TAG_MODIFIERS)) {
            list.add(new TranslationTextComponent("tooltip.essence.modifier").applyTextStyle(TextFormatting.GOLD));
            Map<String, ITextComponent> sorting_map = new HashMap<>();
            EssenceModifierHelpers.getModifiers(stack).forEach((key, value) -> sorting_map.put(key.getRenderedText(value).getString(), key.getRenderedText(value)));
            sorting_map
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (string, component) -> component, LinkedHashMap::new))
                .forEach((s, iTextComponent) -> list.add(iTextComponent));
            list.add(new StringTextComponent(""));
        }
    }

    private static List<ItemStack> handleShearingSheep(SheepEntity sheep, ItemStack item, IWorld world, BlockPos pos, int fortune) {
        List<ItemStack> ret = new ArrayList<>();
        if (!sheep.world.isRemote) {
            sheep.setSheared(true);
            int i = EssenceUtilHelper.nextIntInclusive(1 + fortune, 4 + fortune);

            for(int j = 0; j < i; ++j) {
                ret.add(new ItemStack(SheepEntity.WOOL_BY_COLOR.get(sheep.getFleeceColor())));
            }
        }
        sheep.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
    }

    @Override
    public ActionResultType onItemUseModified(ItemUseContext context, boolean isRecursive) {
        return ActionResultType.PASS;
    }
}
