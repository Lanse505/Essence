package com.teamacronymcoders.essence.compat.registrate;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.client.render.entity.renderer.*;
import com.teamacronymcoders.essence.common.entity.GlueBallEntity;
import com.teamacronymcoders.essence.common.entity.ModifiableArrowEntity;
import com.teamacronymcoders.essence.common.entity.SerializedEntityEntity;
import com.teamacronymcoders.essence.common.entity.sheared.*;
import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class EssenceEntityRegistrate {

    private static final EntityPredicate.Builder ON_FIRE = EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true).build());

    public static void init() {
    }

    public static EntityEntry<ModifiableArrowEntity> ARROW_ENTITY = Essence.ESSENCE_REGISTRATE.object("modifiable_arrow").<ModifiableArrowEntity>entity(ModifiableArrowEntity::new, MobCategory.MISC).renderer(() -> ModifiableArrowRenderer::new).register();
    public static EntityEntry<GlueBallEntity> GLUE_BALL = Essence.ESSENCE_REGISTRATE.object("glue_ball").<GlueBallEntity>entity(GlueBallEntity::new, MobCategory.MISC).renderer(() -> GlueBallRenderer::new).register();
    public static EntityEntry<SerializedEntityEntity> SERIALIZED_ENTITY = Essence.ESSENCE_REGISTRATE.object("serialized_entity").<SerializedEntityEntity>entity(SerializedEntityEntity::new, MobCategory.MISC).renderer(() -> SerializedEntityRenderer::new).register();
    public static EntityEntry<ShearedChickenEntity> SHEARED_CHICKEN = Essence.ESSENCE_REGISTRATE.object("sheared_chicken").entity(ShearedChickenEntity::new, MobCategory.CREATURE).renderer(() -> ShearedChickenRenderer::new)
            .loot((tables, type) -> tables.add(type, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.CHICKEN)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ON_FIRE))
                                    )
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            ))
            .spawnEgg(10592673, 16711680).model((context, provider) -> provider.withExistingParent(context.getId().getPath(), new ResourceLocation("item/template_spawn_egg"))).build()
            .register();
    public static EntityEntry<ShearedCowEntity> SHEARED_COW = Essence.ESSENCE_REGISTRATE.object("sheared_cow").entity(ShearedCowEntity::new, MobCategory.CREATURE).renderer(() -> ShearedCowRenderer::new)
            .loot((tables, type) -> tables.add(type, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.BEEF)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ON_FIRE))
                                    )
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            ))
            .spawnEgg(4470310, 10592673).model((context, provider) -> provider.withExistingParent(context.getId().getPath(), new ResourceLocation("item/template_spawn_egg"))).build()
            .register();
    public static EntityEntry<ShearedCreeperEntity> SHEARED_CREEPER = Essence.ESSENCE_REGISTRATE.object("sheared_creeper").entity(ShearedCreeperEntity::new, MobCategory.MONSTER).renderer(() -> ShearedCreeperRenderer::new)
            .loot((tables, type) -> tables.add(type, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.GUNPOWDER)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 0.5F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            ))
            .spawnEgg(894731, 0).model((context, provider) -> provider.withExistingParent(context.getId().getPath(), new ResourceLocation("item/template_spawn_egg"))).build()
            .register();
    public static EntityEntry<ShearedGhastEntity> SHEARED_GHAST = Essence.ESSENCE_REGISTRATE.object("sheared_ghast").entity(ShearedGhastEntity::new, MobCategory.MONSTER).renderer(() -> ShearedGhastRenderer::new)
            .loot((tables, type) -> tables.add(type, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.GUNPOWDER)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            ))
            .spawnEgg(16382457, 12369084).model((context, provider) -> provider.withExistingParent(context.getId().getPath(), new ResourceLocation("item/template_spawn_egg"))).build()
            .register();
    public static EntityEntry<ShearedPigEntity> SHEARED_PIG = Essence.ESSENCE_REGISTRATE.object("sheared_pig").entity(ShearedPigEntity::new, MobCategory.CREATURE).renderer(() -> ShearedPigRenderer::new)
            .loot((tables, type) -> tables.add(type, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.PORKCHOP)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ON_FIRE))
                                    )
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            ))
            .spawnEgg(15771042, 14377823).model((context, provider) -> provider.withExistingParent(context.getId().getPath(), new ResourceLocation("item/template_spawn_egg"))).build()
            .register();
}
