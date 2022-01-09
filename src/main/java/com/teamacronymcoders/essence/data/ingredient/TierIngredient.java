package com.teamacronymcoders.essence.data.ingredient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.teamacronymcoders.essence.api.modified.IModifiedTool;
import com.teamacronymcoders.essence.common.util.tier.EssenceToolTiers;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TierIngredient extends Ingredient {

    private static final Predicate<EssenceToolTiers> essencePredicate = tier -> tier == EssenceToolTiers.ESSENCE || tier == EssenceToolTiers.EMPOWERED || tier == EssenceToolTiers.SUPREME || tier == EssenceToolTiers.DIVINE;
    private static final Predicate<EssenceToolTiers> empoweredPredicate = tier -> tier == EssenceToolTiers.EMPOWERED || tier == EssenceToolTiers.SUPREME || tier == EssenceToolTiers.DIVINE;
    private static final Predicate<EssenceToolTiers> supremePredicate = tier -> tier == EssenceToolTiers.SUPREME || tier == EssenceToolTiers.DIVINE;
    private static final Predicate<EssenceToolTiers> divinePredicate = tier -> tier == EssenceToolTiers.DIVINE;

    private final Tag<Item> tag;
    private final EssenceToolTiers tier;
    private final Optional<CompoundTag> tagOptional;

    protected TierIngredient(Tag<Item> tag, EssenceToolTiers tier, Optional<CompoundTag> tagOptional) {
        super(Stream.of(new Ingredient.TagValue(tag)));
        this.tag = tag;
        this.tier = tier;
        this.tagOptional = tagOptional;
    }

    @Override
    public boolean test(@Nullable ItemStack input) {
        if (input == null) return false;
        boolean tagTest = super.test(input);
        boolean tierTest = input.getItem() instanceof IModifiedTool && isValid(this.tier, ((IModifiedTool) input.getItem()).getTier());
        if (tagOptional.isPresent()) {
            CompoundTag ingredientTag = tagOptional.get();
            CompoundTag inputTag = input.getShareTag();
            return tagTest && tierTest && ingredientTag.equals(inputTag);
        }
        return tagTest && tierTest;
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public JsonElement toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", CraftingHelper.getID(TierIngredient.Serializer.INSTANCE).toString());
        json.addProperty("tag", SerializationTags.getInstance().getIdOrThrow(Registry.ITEM_REGISTRY, this.tag, () -> new IllegalStateException("Unknown item tag")).toString());
        json.addProperty("tier", this.tier.toString());
        tagOptional.ifPresent(compoundTag -> json.addProperty("compoundTag", compoundTag.toString()));
        return json;
    }

    public boolean isValid(EssenceToolTiers ingredientTier, EssenceToolTiers itemTier) {
        return switch (ingredientTier) {
            case ESSENCE -> essencePredicate.test(itemTier);
            case EMPOWERED -> empoweredPredicate.test(itemTier);
            case SUPREME -> supremePredicate.test(itemTier);
            case DIVINE -> divinePredicate.test(itemTier);
        };
    }

    public static TierIngredient of(Tag<Item> tag, EssenceToolTiers tier) {
        return of(tag, tier, Optional.empty());
    }

    public static TierIngredient of(Tag<Item> tag, EssenceToolTiers tier, Optional<CompoundTag> optionalNBT) {
        return new TierIngredient(tag, tier, optionalNBT);
    }

    public static class Serializer implements IIngredientSerializer<TierIngredient>
    {
        public static final TierIngredient.Serializer INSTANCE = new TierIngredient.Serializer();

        @Override
        public TierIngredient parse(FriendlyByteBuf buffer) {
            ResourceLocation resourcelocation = new ResourceLocation(buffer.readUtf());
            Tag<Item> tag = SerializationTags.getInstance().getTagOrThrow(Registry.ITEM_REGISTRY, resourcelocation, (rl) -> new JsonSyntaxException("Unknown item tag '" + rl + "'"));
            CompoundTag nbt = buffer.readNbt();
            return nbt != null ? new TierIngredient(tag, EssenceToolTiers.valueOf(buffer.readUtf()), Optional.of(nbt)) : new TierIngredient(tag, EssenceToolTiers.valueOf(buffer.readUtf()), Optional.empty());
        }

        @Override
        public TierIngredient parse(JsonObject json) {
            if (json.has("tag") && json.has("tier") && json.has("nbt")) {
                ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(json, "tag"));
                Tag<Item> tag = SerializationTags.getInstance().getTagOrThrow(Registry.ITEM_REGISTRY, resourcelocation, (p_151262_) -> new JsonSyntaxException("Unknown item tag '" + p_151262_ + "'"));
                try {
                    return new TierIngredient(tag, EssenceToolTiers.valueOf(json.get("tier").getAsString()), Optional.of(TagParser.parseTag(json.get("nbt").getAsString())));
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                }
            } else if (json.has("tag") && json.has("tier")) {
                ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(json, "tag"));
                Tag<Item> tag = SerializationTags.getInstance().getTagOrThrow(Registry.ITEM_REGISTRY, resourcelocation, (p_151262_) -> new JsonSyntaxException("Unknown item tag '" + p_151262_ + "'"));
                return new TierIngredient(tag, EssenceToolTiers.valueOf(json.get("tier").getAsString()), Optional.empty());
            }
            throw new IllegalStateException("Unable to parse TierIngredient due to lack of 'tag' and/or 'tier' json entries");
        }

        @Override
        public void write(FriendlyByteBuf buffer, TierIngredient ingredient) {
            buffer.writeUtf(SerializationTags.getInstance().getIdOrThrow(Registry.ITEM_REGISTRY, ingredient.tag, () -> new IllegalStateException("Unknown item tag")).toString());
            buffer.writeUtf(ingredient.tier.toString());
            ingredient.tagOptional.ifPresent(buffer::writeNbt);
        }
    }
}
