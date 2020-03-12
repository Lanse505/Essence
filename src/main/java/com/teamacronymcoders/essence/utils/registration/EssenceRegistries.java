package com.teamacronymcoders.essence.utils.registration;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class EssenceRegistries {

    public static ForgeRegistry<Modifier> MODIFIER_REGISTRY = (ForgeRegistry<Modifier>) new RegistryBuilder<Modifier>()
        .setName(new ResourceLocation(Essence.MODID, "modifiers"))
        .setIDRange(1, Integer.MAX_VALUE - 1)
        .setType(Modifier.class)
        .disableSaving()
        .create();

    public static ForgeRegistry<Knowledge> KNOWLEDGE_REGISTRY = (ForgeRegistry<Knowledge>) new RegistryBuilder<Knowledge>()
        .setName(new ResourceLocation(Essence.MODID, "knowledge"))
        .setIDRange(1, Integer.MAX_VALUE - 1)
        .setType(Knowledge.class)
        .disableSaving()
        .create();
}
