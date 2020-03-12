package com.teamacronymcoders.essence.utils.registration;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class EssenceKnowledgeRegistration {

    private static final DeferredRegister<Knowledge> KNOWLEDGE_DEFERRED_REGISTER = new DeferredRegister<>(EssenceRegistries.KNOWLEDGE_REGISTRY, Essence.MODID);

    public static void register(IEventBus eventBus) {
        KNOWLEDGE_DEFERRED_REGISTER.register(eventBus);
    }
}
