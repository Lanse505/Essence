package com.teamacronymcoders.essence.util.registration;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.entity.impl.GlueBallEntity;
import com.teamacronymcoders.essence.entity.impl.sheared.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EssenceEntityRegistration {

  private static final DeferredRegister<EntityType<?>> ENTITY_TYPE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, Essence.MOD_ID);

  public static RegistryObject<EntityType<GlueBallEntity>> GLUE_BALL = ENTITY_TYPE_DEFERRED_REGISTER.register("glue_ball", () -> EntityType.Builder.<GlueBallEntity>create(GlueBallEntity::new, EntityClassification.MISC).build("glue_ball"));

  public static RegistryObject<EntityType<ShearedChickenEntity>> SHEARED_CHICKEN = ENTITY_TYPE_DEFERRED_REGISTER.register("sheared_chicken", () -> EntityType.Builder.create(ShearedChickenEntity::new, EntityClassification.CREATURE).build("sheared_chicken"));
  public static RegistryObject<EntityType<ShearedCowEntity>> SHEARED_COW = ENTITY_TYPE_DEFERRED_REGISTER.register("sheared_cow", () -> EntityType.Builder.create(ShearedCowEntity::new, EntityClassification.CREATURE).build("sheared_cow"));
  public static RegistryObject<EntityType<ShearedCreeperEntity>> SHEARED_CREEPER = ENTITY_TYPE_DEFERRED_REGISTER.register("sheared_creeper", () -> EntityType.Builder.create(ShearedCreeperEntity::new, EntityClassification.MONSTER).build("sheared_creeper"));
  public static RegistryObject<EntityType<ShearedGhastEntity>> SHEARED_GHAST = ENTITY_TYPE_DEFERRED_REGISTER.register("sheared_ghast", () -> EntityType.Builder.create(ShearedGhastEntity::new, EntityClassification.MONSTER).build("sheared_ghast"));
  public static RegistryObject<EntityType<ShearedPigEntity>> SHEARED_PIG = ENTITY_TYPE_DEFERRED_REGISTER.register("sheared_pig", () -> EntityType.Builder.create(ShearedPigEntity::new, EntityClassification.CREATURE).build("sheared_pig"));

  public static void register (IEventBus bus) {
    ENTITY_TYPE_DEFERRED_REGISTER.register(bus);
  }
}
