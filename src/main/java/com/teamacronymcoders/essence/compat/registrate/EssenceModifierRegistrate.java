package com.teamacronymcoders.essence.compat.registrate;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.modifier.IModifier;
import com.teamacronymcoders.essence.common.modifier.curio.attribute.*;
import com.teamacronymcoders.essence.common.modifier.item.arrow.BrewedModifier;
import com.teamacronymcoders.essence.common.modifier.item.arrow.EndericModifier;
import com.teamacronymcoders.essence.common.modifier.item.arrow.KeenModifier;
import com.teamacronymcoders.essence.common.modifier.item.arrow.SoakedModifier;
import com.teamacronymcoders.essence.common.modifier.item.cosmetic.EnchantedModifier;
import com.teamacronymcoders.essence.common.modifier.item.enchantment.*;
import com.teamacronymcoders.essence.common.modifier.item.enchantment.strengthened.StrengthenedModifier;
import com.teamacronymcoders.essence.common.modifier.item.enchantment.strengthened.StrengthenedType;
import com.teamacronymcoders.essence.common.modifier.item.interaction.ExpanderModifier;
import com.teamacronymcoders.essence.common.modifier.item.interaction.RainbowModifier;
import com.teamacronymcoders.essence.common.modifier.item.interaction.cascading.CascadingModifier;
import com.teamacronymcoders.essence.common.modifier.item.interaction.cascading.CascadingType;
import com.teamacronymcoders.essence.common.modifier.item.tank.HoldingModifier;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class EssenceModifierRegistrate {

    public static void init() {
    }

    public static final Supplier<IForgeRegistry<IModifier<?>>> REGISTRY = Essence.ESSENCE_REGISTRATE.makeRegistry("modifier", IModifier.class, () -> new RegistryBuilder<IModifier<?>>()
            .setName(new ResourceLocation(Essence.MOD_ID, "modifier"))
            .setIDRange(1, Integer.MAX_VALUE - 1)
            .disableSaving()
    );

    public static final RegistryEntry<BrewedModifier> BREWED_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("brewed", IModifier.class, BrewedModifier::new);
    public static final RegistryEntry<KeenModifier> KEEN_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("keen", IModifier.class, KeenModifier::new);
    public static final RegistryEntry<SoakedModifier> SOAKED_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("soaked", IModifier.class, SoakedModifier::new);
    public static final RegistryEntry<EndericModifier> ENDERIC_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("enderic", IModifier.class, EndericModifier::new);

    public static final RegistryEntry<ArmorModifier> ARMOR_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("armor", IModifier.class, ArmorModifier::new);
    public static final RegistryEntry<ArmorToughnessModifier> ARMOR_TOUGHNESS_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("armor_toughness", IModifier.class, ArmorToughnessModifier::new);
    public static final RegistryEntry<AttackDamageModifier> ATTACK_DAMAGE_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("attack_damage", IModifier.class, AttackDamageModifier::new);
    public static final RegistryEntry<MaxHealthModifier> MAX_HEALTH_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("max_health", IModifier.class, MaxHealthModifier::new);
    public static final RegistryEntry<MovementSpeedModifier> MOVEMENT_SPEED_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("movement_speed", IModifier.class, MovementSpeedModifier::new);

    public static final RegistryEntry<EnchantedModifier> ENCHANTED_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("enchanted", IModifier.class, EnchantedModifier::new);

    public static final RegistryEntry<EfficiencyModifier> EFFICIENCY_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("efficiency", IModifier.class, EfficiencyModifier::new);
    public static final RegistryEntry<InfinityModifier> INFINITY_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("infinity", IModifier.class, InfinityModifier::new);
    public static final RegistryEntry<KnockbackModifier> KNOCKBACK_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("knockback", IModifier.class, KnockbackModifier::new);
    public static final RegistryEntry<LuckModifier> LUCK_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("luck", IModifier.class, LuckModifier::new);
    public static final RegistryEntry<MendingModifier> MENDING_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("mending", IModifier.class, MendingModifier::new);
    public static final RegistryEntry<SilkTouchModifier> SILK_TOUCH_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("silk_touch", IModifier.class, SilkTouchModifier::new);
    public static final RegistryEntry<UnbreakingModifier> UNBREAKING_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("unbreaking", IModifier.class, UnbreakingModifier::new);

    public static final RegistryEntry<StrengthenedModifier> STRENGTHENED_ARTHROPOD_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("strengthened_arthropod", IModifier.class, () -> new StrengthenedModifier(StrengthenedType.BANE_OF_ARTHROPODS));
    public static final RegistryEntry<StrengthenedModifier> STRENGTHENED_SHARPNESS_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("strengthened_sharpness", IModifier.class, () -> new StrengthenedModifier(StrengthenedType.SHARPNESS));
    public static final RegistryEntry<StrengthenedModifier> STRENGTHENED_SMITE_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("strengthened_smite", IModifier.class, () -> new StrengthenedModifier(StrengthenedType.SMITE));
    public static final RegistryEntry<StrengthenedModifier> STRENGTHENED_POWER_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("strengthened_power", IModifier.class, () -> new StrengthenedModifier(StrengthenedType.POWER));

    public static final RegistryEntry<ExpanderModifier> EXPANDER_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("expander", IModifier.class, ExpanderModifier::new);
    public static final RegistryEntry<FieryModifier> FIERY_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("fiery", IModifier.class, FieryModifier::new);
    public static final RegistryEntry<RainbowModifier> RAINBOW_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("rainbow", IModifier.class, RainbowModifier::new);

    public static final RegistryEntry<CascadingModifier> CASCADING_NONE_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("cascading_none", IModifier.class, () -> new CascadingModifier(CascadingType.NONE));
    public static final RegistryEntry<CascadingModifier> CASCADING_LUMBER_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("cascading_lumber", IModifier.class, () -> new CascadingModifier(CascadingType.LUMBER));
    public static final RegistryEntry<CascadingModifier> CASCADING_VEIN_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("cascading_vein", IModifier.class, () -> new CascadingModifier(CascadingType.VEIN));
    public static final RegistryEntry<CascadingModifier> CASCADING_EXCAVATION_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("cascading_excavation", IModifier.class, () -> new CascadingModifier(CascadingType.EXCAVATION));

    public static final RegistryEntry<HoldingModifier> HOLDING_MODIFIER = Essence.ESSENCE_REGISTRATE.simple("holding_tank", IModifier.class, HoldingModifier::new);


}
