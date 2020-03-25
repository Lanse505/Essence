package com.teamacronymcoders.essence.serializable.loot;

import com.google.gson.JsonObject;
import com.teamacronymcoders.essence.utils.config.EssenceModifierConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GrinderLootModifier extends LootModifier {

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected GrinderLootModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    private static ItemStack grind(ItemStack stack, LootContext context) {
        if (!stack.getItem().getTags().isEmpty()) {
            Set<ResourceLocation> tags = stack.getItem().getTags();
            List<ResourceLocation> subTypes = tags.stream().filter(tag -> tag.toString().contains("forge:ores/")).collect(Collectors.toList());
            List<String> subStrings = new ArrayList<>();
            subTypes.forEach(tag -> subStrings.add(tag.toString().split("/")[tag.toString().split("/").length - 1]));
            return subStrings.stream().map(str -> {
                if (ItemTags.getCollection().get(new ResourceLocation("forge:dusts/" + str)) != null) {
                    Tag<Item> dustTag = ItemTags.getCollection().get(new ResourceLocation("forge:dusts/" + str));
                    if (dustTag != null && !dustTag.getAllElements().isEmpty()) {
                        Collection<Item> tagItems = dustTag.getAllElements();
                        List<Item> secondaryList = new ArrayList<>();
                        for (Item item : tagItems) {
                            if (item.getRegistryName() != null) {
                                String namespace = item.getRegistryName().getNamespace();
                                String[] preferredValues = EssenceModifierConfig.getInstance().getGrinder().getPreferedMods().get().split(",");
                                for (String namespaceFromConfig : preferredValues) {
                                    String clear = namespaceFromConfig.replace(",", "");
                                    if (namespace.equals(clear)) {
                                        return new ItemStack(item, 2);
                                    }
                                    secondaryList.add(item);
                                }
                            }
                        }
                        return new ItemStack(secondaryList.get(0), 2);
                    }
                }
                return stack;
            }).findFirst().orElse(stack);
        }
        return stack;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        List<ItemStack> list = new ArrayList<>();
        generatedLoot.forEach(stack -> list.add(grind(stack, context)));
        return list;
    }

    public static class Serializer extends GlobalLootModifierSerializer<GrinderLootModifier> {
        @Override
        public GrinderLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
            return new GrinderLootModifier(ailootcondition);
        }
    }
}
