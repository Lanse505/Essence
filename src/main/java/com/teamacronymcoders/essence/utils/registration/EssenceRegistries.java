package com.teamacronymcoders.essence.utils.registration;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.*;

public class EssenceRegistries {

    static {
        init();
    }

    public static IForgeRegistry<Modifier<?>> MODIFIER = RegistryManager.ACTIVE.getRegistry(Modifier.class);
    public static IForgeRegistry<Knowledge<?>> KNOWLEDGE = RegistryManager.ACTIVE.getRegistry(Knowledge.class);

    @SuppressWarnings("unchecked")
    private static void init() {
        makeRegistry("modifier", Modifier.class).create();
        makeRegistry("knowledge", Knowledge.class).create();
    }

    public static <T extends IForgeRegistryEntry<T>> RegistryBuilder<T> makeRegistry(String name, Class<T> type) {
        return new RegistryBuilder<T>()
            .setName(new ResourceLocation(Essence.MODID, name))
            .setIDRange(1, Integer.MAX_VALUE - 1)
            .disableSaving()
            .setType(type);
    }
}
