package com.teamacronymcoders.essence.registrate;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.entity.impl.GlueBallEntity;
import com.teamacronymcoders.essence.entity.impl.sheared.*;
import com.teamacronymcoders.essence.entity.render.*;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.Smelt;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EssenceEntityRegistrate {

  private static final EntityPredicate.Builder ON_FIRE = EntityPredicate.Builder.create().flags(EntityFlagsPredicate.Builder.create().onFire(true).build());

  public static void init() {}

  public static EntityEntry<GlueBallEntity> GLUE_BALL = Essence.ESSENCE_REGISTRATE.object("glue_ball").<GlueBallEntity>entity(GlueBallEntity::new, EntityClassification.MISC).renderer(() -> GlueBallRenderer::new).register();
  public static EntityEntry<ShearedChickenEntity> SHEARED_CHICKEN = Essence.ESSENCE_REGISTRATE.object("sheared_chicken").entity(ShearedChickenEntity::new, EntityClassification.CREATURE).renderer(() -> ShearedChickenRenderer::new).spawnEgg(10592673, 16711680).build()
          .loot((tables, type) -> tables.registerLootTable(type, LootTable.builder()
                  .addLootPool(LootPool.builder()
                          .rolls(ConstantRange.of(1))
                          .addEntry(ItemLootEntry.builder(Items.CHICKEN)
                                  .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                  .acceptFunction(Smelt.func_215953_b()
                                          .acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, ON_FIRE))
                                  )
                                  .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                          )
                  )
          ))
          .register();
  public static EntityEntry<ShearedCowEntity> SHEARED_COW = Essence.ESSENCE_REGISTRATE.object("sheared_cow").entity(ShearedCowEntity::new, EntityClassification.CREATURE).renderer(() -> ShearedCowRenderer::new).spawnEgg(4470310, 10592673).build()
          .loot((tables, type) -> tables.registerLootTable(type, LootTable.builder()
                  .addLootPool(LootPool.builder()
                          .rolls(ConstantRange.of(1))
                          .addEntry(ItemLootEntry.builder(Items.BEEF)
                                  .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                  .acceptFunction(Smelt.func_215953_b()
                                          .acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, ON_FIRE))
                                  )
                                  .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                          )
                  )
          ))
          .register();
  public static EntityEntry<ShearedCreeperEntity> SHEARED_CREEPER = Essence.ESSENCE_REGISTRATE.object("sheared_creeper").entity(ShearedCreeperEntity::new, EntityClassification.MONSTER).renderer(() -> ShearedCreeperRenderer::new).spawnEgg(894731, 0).build()
          .loot((tables, type) -> tables.registerLootTable(type, LootTable.builder()
                  .addLootPool(LootPool.builder()
                          .rolls(ConstantRange.of(1))
                          .addEntry(ItemLootEntry.builder(Items.GUNPOWDER)
                                  .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 0.5F)))
                                  .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                          )
                  )
          ))
          .register();
  public static EntityEntry<ShearedGhastEntity> SHEARED_GHAST = Essence.ESSENCE_REGISTRATE.object("sheared_ghast").entity(ShearedGhastEntity::new, EntityClassification.MONSTER).renderer(() -> ShearedGhastRenderer::new).spawnEgg(16382457, 12369084).build()
          .loot((tables, type) -> tables.registerLootTable(type, LootTable.builder()
                  .addLootPool(LootPool.builder()
                          .rolls(ConstantRange.of(1))
                          .addEntry(ItemLootEntry.builder(Items.GUNPOWDER)
                                  .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 1.0F)))
                                  .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                          )
                  )
          ))
          .register();
  public static EntityEntry<ShearedPigEntity> SHEARED_PIG = Essence.ESSENCE_REGISTRATE.object("sheared_pig").entity(ShearedPigEntity::new, EntityClassification.CREATURE).renderer(() -> ShearedPigRenderer::new).spawnEgg(15771042, 14377823).build()
          .loot((tables, type) -> tables.registerLootTable(type, LootTable.builder()
                  .addLootPool(LootPool.builder()
                          .rolls(ConstantRange.of(1))
                          .addEntry(ItemLootEntry.builder(Items.PORKCHOP)
                                  .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                  .acceptFunction(Smelt.func_215953_b()
                                          .acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, ON_FIRE))
                                  )
                                  .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                          )
                  )
          ))
          .register();
}
