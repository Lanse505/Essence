package com.teamacronymcoders.essence.api.recipe.infusion.special;

import com.teamacronymcoders.essence.api.modifier.ModifierInstance;
import com.teamacronymcoders.essence.common.util.helper.EssenceItemstackModifierHelpers;
import com.teamacronymcoders.essence.compat.registrate.EssenceModifierRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.List;

public class ExplosiveChargeRecipe extends CustomRecipe {

  public static final SimpleRecipeSerializer<ExplosiveChargeRecipe> SERIALIZER = new SimpleRecipeSerializer<>(ExplosiveChargeRecipe::new);

  public ExplosiveChargeRecipe(ResourceLocation pId) {
    super(pId);
  }

  @Override
  public boolean matches(CraftingContainer container, Level level) {
    boolean hasExplosive = false;
    int chargers = 0;
    for(int i = 0; i < container.getContainerSize(); i++) {
      ItemStack contStack = container.getItem(i);
      if(contStack.isEmpty()) {
        continue;
      }
      if(EssenceItemstackModifierHelpers.hasModifier(EssenceModifierRegistrate.EXPLOSIVE.get(), contStack)) {
        if(hasExplosive) {
          return false;
        }
        hasExplosive = true;
      } else if(contStack.is(Tags.Items.GUNPOWDER)) {
        chargers++;
      } else {
        return false;
      }

    }
    return hasExplosive && chargers > 0;
  }

  @Override
  public ItemStack assemble(CraftingContainer container) {
    ItemStack holder = ItemStack.EMPTY;
    int chargers = 0;
    for(int i = 0; i < container.getContainerSize(); i++) {
      ItemStack contStack = container.getItem(i);
      if(contStack.isEmpty()) {
        continue;
      }
      if(EssenceItemstackModifierHelpers.hasModifier(EssenceModifierRegistrate.EXPLOSIVE.get(), contStack)) {
        holder = contStack.copy();
      } else if(contStack.is(Tags.Items.GUNPOWDER)) {
        chargers++;
      } else {
        return ItemStack.EMPTY;
      }
    }
    if(!holder.isEmpty() && chargers > 0) {
      ModifierInstance instance = EssenceItemstackModifierHelpers.getModifierInstance(holder, EssenceModifierRegistrate.EXPLOSIVE.get());
      if(instance != null && instance.getModifierData() != null) {
        float charge = instance.getModifierData().getFloat("charge");
        if(charge <= 5000f) {
          instance.getModifierData().putFloat("charge", charge + (25.0f * chargers));
        }
      }
    }
    return holder;
  }

  @Override
  public boolean canCraftInDimensions(int pWidth, int pHeight) {
    return pWidth * pHeight >= 2;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return SERIALIZER;
  }
}
