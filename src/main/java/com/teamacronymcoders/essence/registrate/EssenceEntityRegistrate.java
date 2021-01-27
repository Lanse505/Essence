package com.teamacronymcoders.essence.registrate;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.entity.GlueBallEntity;
import com.teamacronymcoders.essence.entity.ModifiableArrowEntity;
import com.teamacronymcoders.essence.entity.render.*;
import com.teamacronymcoders.essence.entity.sheared.*;
import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.EntityClassification;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.Smelt;
import net.minecraft.util.ResourceLocation;

public class EssenceEntityRegistrate {

  private static final EntityPredicate.Builder ON_FIRE = EntityPredicate.Builder.create().flags(EntityFlagsPredicate.Builder.create().onFire(true).build());

  public static void init() {}

  public static EntityEntry<ModifiableArrowEntity> ARROW_ENTITY = Essence.ESSENCE_REGISTRATE.object("modifiable_entity").<ModifiableArrowEntity>entity(ModifiableArrowEntity::new, EntityClassification.MISC).renderer(() -> ModifiableArrowRenderer::new).register();
  public static EntityEntry<GlueBallEntity> GLUE_BALL = Essence.ESSENCE_REGISTRATE.object("glue_ball").<GlueBallEntity>entity(GlueBallEntity::new, EntityClassification.MISC).renderer(() -> GlueBallRenderer::new).register();
  public static EntityEntry<ShearedChickenEntity> SHEARED_CHICKEN = Essence.ESSENCE_REGISTRATE.object("sheared_chicken").entity(ShearedChickenEntity::new, EntityClassification.CREATURE).renderer(() -> ShearedChickenRenderer::new)
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
          .spawnEgg(10592673, 16711680).model((context, provider) -> provider.withExistingParent(context.getId().getPath(), new ResourceLocation("item/template_spawn_egg"))).build()
          .register();
  public static EntityEntry<ShearedCowEntity> SHEARED_COW = Essence.ESSENCE_REGISTRATE.object("sheared_cow").entity(ShearedCowEntity::new, EntityClassification.CREATURE).renderer(() -> ShearedCowRenderer::new)
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
          .spawnEgg(4470310, 10592673).model((context, provider) -> provider.withExistingParent(context.getId().getPath(), new ResourceLocation("item/template_spawn_egg"))).build()
          .register();
  public static EntityEntry<ShearedCreeperEntity> SHEARED_CREEPER = Essence.ESSENCE_REGISTRATE.object("sheared_creeper").entity(ShearedCreeperEntity::new, EntityClassification.MONSTER).renderer(() -> ShearedCreeperRenderer::new)
          .loot((tables, type) -> tables.registerLootTable(type, LootTable.builder()
                  .addLootPool(LootPool.builder()
                          .rolls(ConstantRange.of(1))
                          .addEntry(ItemLootEntry.builder(Items.GUNPOWDER)
                                  .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 0.5F)))
                                  .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                          )
                  )
          ))
          .spawnEgg(894731, 0).model((context, provider) -> provider.withExistingParent(context.getId().getPath(), new ResourceLocation("item/template_spawn_egg"))).build()
          .register();
  public static EntityEntry<ShearedGhastEntity> SHEARED_GHAST = Essence.ESSENCE_REGISTRATE.object("sheared_ghast").entity(ShearedGhastEntity::new, EntityClassification.MONSTER).renderer(() -> ShearedGhastRenderer::new)
          .loot((tables, type) -> tables.registerLootTable(type, LootTable.builder()
                  .addLootPool(LootPool.builder()
                          .rolls(ConstantRange.of(1))
                          .addEntry(ItemLootEntry.builder(Items.GUNPOWDER)
                                  .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 1.0F)))
                                  .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                          )
                  )
          ))
          .spawnEgg(16382457, 12369084).model((context, provider) -> provider.withExistingParent(context.getId().getPath(), new ResourceLocation("item/template_spawn_egg"))).build()
          .register();
  public static EntityEntry<ShearedPigEntity> SHEARED_PIG = Essence.ESSENCE_REGISTRATE.object("sheared_pig").entity(ShearedPigEntity::new, EntityClassification.CREATURE).renderer(() -> ShearedPigRenderer::new)
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
          .spawnEgg(15771042, 14377823).model((context, provider) -> provider.withExistingParent(context.getId().getPath(), new ResourceLocation("item/template_spawn_egg"))).build()
          .register();
}
