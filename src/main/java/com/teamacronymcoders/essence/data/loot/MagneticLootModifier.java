package com.teamacronymcoders.essence.data.loot;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MagneticLootModifier extends LootModifier {

  /**
   * Constructs a LootModifier.
   *
   * @param conditionsIn the ILootConditions that need to be matched before the loot is com.teamacronymcoders.essenceapi.modified.
   */
  protected MagneticLootModifier(LootItemCondition[] conditionsIn) {
    super(conditionsIn);
  }

  private static List<ItemStack> magneticPickUp(List<ItemStack> generatedLoot, LootContext context) {
    if (context.hasParam(LootContextParams.THIS_ENTITY) && context.hasParam(LootContextParams.BLOCK_STATE)) {
      if (context.getParam(LootContextParams.THIS_ENTITY) instanceof Player player) {
        for (ItemStack stack : generatedLoot) {
          if (!player.addItem(stack)) {
            break;
          }
          generatedLoot.remove(stack);
        }
      }
    }
    return generatedLoot;
  }

  @NotNull
  @Override
  protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
    return magneticPickUp(generatedLoot, context);
  }

  public static class Serializer extends GlobalLootModifierSerializer<MagneticLootModifier> {
    @Override
    public MagneticLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
      return new MagneticLootModifier(ailootcondition);
    }

    @Override
    public JsonObject write(MagneticLootModifier instance) {
      return new JsonObject();
    }
  }
}
