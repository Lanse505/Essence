package lanse505.essence.impl.serializable.recipe;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import lanse505.essence.api.modifier.core.Modifier;
import lanse505.essence.impl.blocks.infuser.InfusionTableTile;
import lanse505.essence.utils.EssenceReferences;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InfusionTableSerializableRecipe extends SerializableRecipe {
    public static GenericSerializer<InfusionTableSerializableRecipe> SERIALIZER = new GenericSerializer<InfusionTableSerializableRecipe>(new ResourceLocation(EssenceReferences.MODID, "modifier_infusion"), InfusionTableSerializableRecipe.class);
    public static List<InfusionTableSerializableRecipe> RECIPES = new ArrayList<>();

    private ResourceLocation id;
    private Modifier modifier;
    private int level;
    private Ingredient.TagList tagList;
    private int duration;

    static {

    }

    public InfusionTableSerializableRecipe(ResourceLocation id) {
        super(id);
    }

    public InfusionTableSerializableRecipe(ResourceLocation id, Ingredient.TagList tagList, Modifier modifier, int level, int duration) {
        super(id);
        this.id = id;
        this.modifier = modifier;
        this.level = level;
        this.tagList = tagList;
        this.duration = duration;
    }

    @Override
    public boolean matches(IInventory p_77569_1_, World p_77569_2_) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory p_77572_1_) {
        return null;
    }

    @Override
    public boolean canFit(int p_194133_1_, int p_194133_2_) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
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
        Optional<Boolean> optional = tagList.getStacks().stream().map(tagStack -> tagStack.isItemEqualIgnoreDurability(stack)).findAny();
        return optional.orElse(false);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public Ingredient.TagList getTagList() {
        return tagList;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public int getLevel() {
        return level;
    }

    public int getDuration() {
        return duration;
    }
}
