package com.teamacronymcoders.essence.impl.serializable.recipe;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.utils.EssenceReferences;
import com.teamacronymcoders.essence.utils.EssenceRegistration;
import com.teamacronymcoders.essence.utils.helpers.EssenceModifierHelpers;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.*;

public class InfusionTableSerializableRecipe extends SerializableRecipe {
    public static GenericSerializer<InfusionTableSerializableRecipe> SERIALIZER = new GenericSerializer<>(new ResourceLocation(EssenceReferences.MODID, "modifier_infusion"), InfusionTableSerializableRecipe.class);
    public static List<InfusionTableSerializableRecipe> RECIPES = new ArrayList<>();

    static {
        RECIPES.add(new InfusionTableSerializableRecipe(
            new ResourceLocation(EssenceReferences.MODID, "attack_damage_test"),
            new Ingredient.IItemList[]{
                new Ingredient.SingleItemList(new ItemStack(Items.QUARTZ)),
            },
            SerializableModifier.getNewArray(new SerializableModifier(EssenceRegistration.ATTACK_DAMAGE_MODIFIER.get(), 1, SerializableModifier.Operation.ADD)),
            300
        ));
        RECIPES.add(new InfusionTableSerializableRecipe(
            new ResourceLocation(EssenceReferences.MODID, "attack_damage_test_2"),
            new Ingredient.IItemList[]{
                new Ingredient.SingleItemList(new ItemStack(Items.QUARTZ_BLOCK)),
                new Ingredient.SingleItemList(new ItemStack(Items.QUARTZ_PILLAR)),
                new Ingredient.SingleItemList(new ItemStack(Items.CHISELED_QUARTZ_BLOCK)),
                new Ingredient.SingleItemList(new ItemStack(Items.SMOOTH_QUARTZ))
            },
            SerializableModifier.getNewArray(new SerializableModifier(EssenceRegistration.ATTACK_DAMAGE_MODIFIER.get(), 1, SerializableModifier.Operation.INCREMENT)),
            600
        ));
        RECIPES.add(new InfusionTableSerializableRecipe(
            new ResourceLocation(EssenceReferences.MODID, "fortune_test"),
            new Ingredient.IItemList[]{new Ingredient.TagList(Tags.Items.GEMS_LAPIS)},
            SerializableModifier.getNewArray(new SerializableModifier(EssenceRegistration.LUCK_MODIFIER.get(), 1, SerializableModifier.Operation.ADD)),
            300
        ));
        RECIPES.add(new InfusionTableSerializableRecipe(
            new ResourceLocation(EssenceReferences.MODID, "fortune_test_2"),
            new Ingredient.IItemList[]{new Ingredient.TagList(Tags.Items.STORAGE_BLOCKS_LAPIS)},
            SerializableModifier.getNewArray(new SerializableModifier(EssenceRegistration.LUCK_MODIFIER.get(), 1, SerializableModifier.Operation.INCREMENT)),
            300
        ));
        RECIPES.add(new InfusionTableSerializableRecipe(
            new ResourceLocation(EssenceReferences.MODID, "expander_test"),
            new Ingredient.IItemList[]{new Ingredient.SingleItemList(new ItemStack(Items.PISTON))},
            SerializableModifier.getNewArray(new SerializableModifier(EssenceRegistration.EXPANDER_MODIFIER.get(), 1, SerializableModifier.Operation.ADD)),
            300
        ));
        RECIPES.add(new InfusionTableSerializableRecipe(
            new ResourceLocation(EssenceReferences.MODID, "expander_test_2"),
            new Ingredient.IItemList[]{new Ingredient.SingleItemList(new ItemStack(Items.STICKY_PISTON))},
            SerializableModifier.getNewArray(new SerializableModifier(EssenceRegistration.EXPANDER_MODIFIER.get(), 1, SerializableModifier.Operation.INCREMENT)),
            600
        ));
        RECIPES.add(new InfusionTableSerializableRecipe(
            new ResourceLocation(EssenceReferences.MODID, "silk_touch_test"),
            new Ingredient.IItemList[]{new Ingredient.SingleItemList(new ItemStack(Items.STICKY_PISTON))},
            SerializableModifier.getNewArray(new SerializableModifier(EssenceRegistration.SILK_TOUCH_MODIFIER.get(), 1, SerializableModifier.Operation.ADD)),
            600
        ));
    }

    public ResourceLocation id;
    public Ingredient.IItemList[] inputList;
    public SerializableModifier[] modifiers;
    public int duration;

    public InfusionTableSerializableRecipe(ResourceLocation id) {
        super(id);
    }

    public InfusionTableSerializableRecipe(ResourceLocation id, Ingredient.IItemList[] inputList, SerializableModifier[] modifiers, int duration) {
        super(id);
        this.id = id;
        this.inputList = inputList;
        this.modifiers = modifiers;
        this.duration = duration;
    }

    @Override
    public boolean matches(IInventory p_77569_1_, World p_77569_2_) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory p_77572_1_) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int p_194133_1_, int p_194133_2_) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public GenericSerializer<? extends SerializableRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return SERIALIZER.getRecipeType();
    }

    public boolean isValid(ItemStack stack) {
        return Arrays.stream(getInputList()).map(list -> list.getStacks().stream().map(tagStack -> tagStack.isItemEqual(stack))).anyMatch(booleanStream -> booleanStream.findAny().orElse(false));
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    private Ingredient.IItemList[] getInputList() {
        return this.inputList;
    }

    public Set<ItemStack> getCollectedStacks() {
        Set<ItemStack> stacks = new HashSet<>();
        for (Ingredient.IItemList itemList : getInputList()) {
            stacks.addAll(itemList.getStacks());
        }
        return stacks;
    }

    public SerializableModifier[] getModifiers() {
        return this.modifiers;
    }

    public void resolveRecipe(ItemStack stack) {
        for (SerializableModifier modifier : getModifiers()) {
            resolveOperationBehaviour(stack, modifier);
        }
    }

    private void resolveOperationBehaviour(ItemStack stack, SerializableModifier modifier) {
        switch (modifier.getOperation()) {
            case ADD:
                EssenceModifierHelpers.addModifier(stack, modifier.getModifier(), modifier.getLevel());
            case REMOVE:
                EssenceModifierHelpers.removeModifiers(stack, modifier.getModifier());
            case REPLACE:
                EssenceModifierHelpers.replaceModifierValue(stack, modifier.getModifier(), modifier.getLevel());
            case INCREMENT:
                EssenceModifierHelpers.increaseModifierLevel(stack, modifier.getModifier(), modifier.getLevel());
            case DECREMENT:
                EssenceModifierHelpers.decreaseModifierLevel(stack, modifier.getModifier(), modifier.getLevel());
        }
    }

    public int getDuration() {
        return duration;
    }
}
