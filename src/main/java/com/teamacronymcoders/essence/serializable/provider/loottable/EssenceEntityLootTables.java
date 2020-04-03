package com.teamacronymcoders.essence.serializable.provider.loottable;

import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapelessRecipeBuilder;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.util.EssenceObjectHolders;
import com.teamacronymcoders.essence.util.registration.EssenceEntityRegistration;
import com.teamacronymcoders.essence.util.registration.EssenceRegistries;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.EntityHasProperty;
import net.minecraft.world.storage.loot.functions.LootingEnchantBonus;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.Smelt;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.stream.Collectors;

public class EssenceEntityLootTables extends EntityLootTables {

    public static final ResourceLocation SHEARED_COW = new ResourceLocation(Essence.MODID, "entities/sheared_cow");
    public static final ResourceLocation SHEARED_PIG = new ResourceLocation(Essence.MODID, "entities/sheared_pig");
    public static final ResourceLocation SHEARED_CHICKEN = new ResourceLocation(Essence.MODID, "entities/sheared_chicken");
    public static final ResourceLocation SHEARED_CREEPER = new ResourceLocation(Essence.MODID, "entities/sheared_creeper");
    public static final ResourceLocation SHEARED_GHAST = new ResourceLocation(Essence.MODID, "entities/sheared_ghast");

    // Horse -> Zombie Horse
    public static final ResourceLocation SHEARING_HORSE = new ResourceLocation(Essence.MODID, "entities/sheared_horse");
    // Zombie Horse -> Skeleton Horse
    public static final ResourceLocation SHEARING_ZOMBIE_HORSE = new ResourceLocation(Essence.MODID, "entities/sheared_zombie_horse");
    // Zombie -> Skeleton
    public static final ResourceLocation SHEARING_ZOMBIE = new ResourceLocation(Essence.MODID, "entities/sheared_zombie");

    private static final EntityPredicate.Builder ON_FIRE = EntityPredicate.Builder.create().flags(EntityFlagsPredicate.Builder.create().onFire(true).build());

    @Override
    protected Iterable<EntityType<?>> getKnownEntities() {
        return ForgeRegistries.ENTITIES.getValues().stream()
            .filter(entity -> Optional.ofNullable(entity.getRegistryName())
                .filter(registryName -> registryName.getNamespace().equals(Essence.MODID)).isPresent()
            ).collect(Collectors.toList());
    }

    @Override
    protected void addTables() {
        this.registerLootTable(EssenceEntityRegistration.SHEARED_CHICKEN.get(), LootTable.builder()
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
        );
        this.registerLootTable(EssenceEntityRegistration.SHEARED_COW.get(), LootTable.builder()
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
        );
        this.registerLootTable(EssenceEntityRegistration.SHEARED_CREEPER.get(), LootTable.builder()
            .addLootPool(LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(Items.GUNPOWDER)
                    .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 0.5F)))
                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                )
            )
        );
        this.registerLootTable(EssenceEntityRegistration.SHEARED_GHAST.get(), LootTable.builder()
            .addLootPool(LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(Items.GUNPOWDER)
                    .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 1.0F)))
                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                )
            )
        );
        this.registerLootTable(EssenceEntityRegistration.SHEARED_PIG.get(), LootTable.builder()
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
        );
        this.registerLootTable(SHEARING_HORSE, LootTable.builder()
            .addLootPool(LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(Items.LEATHER)
                    .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                )
                .addEntry(ItemLootEntry.builder(EssenceObjectHolders.GLUE_BALL)
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
                .addEntry(ItemLootEntry.builder(EssenceObjectHolders.GLUE_BALL)
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
