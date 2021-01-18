package com.teamacronymcoders.essence.datagen.loottable;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.registrate.EssenceItemRegistrate;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.registrate.EssenceEntityRegistrate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.Smelt;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class EssenceEntityLootTables extends EntityLootTables {

  // Horse -> Zombie Horse
  public static final ResourceLocation SHEARING_HORSE = new ResourceLocation(Essence.MOD_ID, "entities/shearing_horse");
  // Zombie Horse -> Skeleton Horse
  public static final ResourceLocation SHEARING_ZOMBIE_HORSE = new ResourceLocation(Essence.MOD_ID, "entities/shearing_zombie_horse");
  // Zombie -> Skeleton
  public static final ResourceLocation SHEARING_ZOMBIE = new ResourceLocation(Essence.MOD_ID, "entities/shearing_zombie");

  @Override
  protected Iterable<EntityType<?>> getKnownEntities () {
    return new ArrayList<>();
  }

  @Override
  protected void addTables () {
    this.registerLootTable(SHEARING_HORSE, LootTable.builder()
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
    this.registerLootTable(SHEARING_ZOMBIE_HORSE, LootTable.builder()
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
    this.registerLootTable(SHEARING_ZOMBIE, LootTable.builder()
            .addLootPool(LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(Items.ROTTEN_FLESH)
                            .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                            .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                    )
            )
    );
  }

}
