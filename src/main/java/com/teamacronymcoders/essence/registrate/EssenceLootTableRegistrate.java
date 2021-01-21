package com.teamacronymcoders.essence.registrate;

import com.teamacronymcoders.essence.Essence;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.loot.RegistrateLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;

public class EssenceLootTableRegistrate {

  // Horse -> Zombie Horse
  public static final ResourceLocation SHEARING_HORSE = new ResourceLocation(Essence.MOD_ID, "entities/shearing_horse");
  // Zombie Horse -> Skeleton Horse
  public static final ResourceLocation SHEARING_ZOMBIE_HORSE = new ResourceLocation(Essence.MOD_ID, "entities/shearing_zombie_horse");
  // Zombie -> Skeleton
  public static final ResourceLocation SHEARING_ZOMBIE = new ResourceLocation(Essence.MOD_ID, "entities/shearing_zombie");

  public static void init(Registrate registrate) {
    registrate.addDataGenerator(ProviderType.LOOT, provider -> {
      provider.addLootAction(RegistrateLootTableProvider.LootType.ENTITY, tables -> {
        tables.registerLootTable(SHEARING_HORSE, LootTable.builder()
                .addLootPool(LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(ItemLootEntry.builder(Items.LEATHER)
                                .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                        )
                        .addEntry(ItemLootEntry.builder(EssenceItemRegistrate.GLUE_BALL_ITEM.get())
                                .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                        )
                )
        );
        tables.registerLootTable(SHEARING_ZOMBIE_HORSE, LootTable.builder()
                .addLootPool(LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(ItemLootEntry.builder(Items.ROTTEN_FLESH)
                                .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                        )
                        .addEntry(ItemLootEntry.builder(EssenceItemRegistrate.GLUE_BALL_ITEM.get())
                                .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                        )
                )
        );
        tables.registerLootTable(SHEARING_ZOMBIE, LootTable.builder()
                .addLootPool(LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(ItemLootEntry.builder(Items.ROTTEN_FLESH)
                                .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                        )
                )
        );
      });
    });
  }
}
