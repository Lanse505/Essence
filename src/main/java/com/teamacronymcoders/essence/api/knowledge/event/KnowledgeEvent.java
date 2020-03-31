package com.teamacronymcoders.essence.api.knowledge.event;

import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import com.teamacronymcoders.essence.serializable.advancement.criterion.EssenceAdvancements;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public class KnowledgeEvent extends PlayerEvent {

    private Knowledge<?> knowledge;

    public KnowledgeEvent(PlayerEntity player, Knowledge<?> knowledge) {
        super(player);
        this.knowledge = knowledge;
    }

    public Knowledge<?> getKnowledge() {
        return knowledge;
    }


    /**
     *
     */
    @Cancelable
    public static class addPre extends KnowledgeEvent {
        public addPre(PlayerEntity player, Knowledge<?> knowledge) {
            super(player, knowledge);
        }
    }

    /**
     *
     */
    public static class addPost extends KnowledgeEvent {
        public addPost(ServerPlayerEntity player, Knowledge<?> knowledge) {
            super(player, knowledge);
            EssenceAdvancements.UNLOCK_KNOWLEDGE.trigger(player, knowledge);
        }
    }


    /**
     *
     */
    public static class remove extends KnowledgeEvent {
        public remove(PlayerEntity player, Knowledge<?> knowledge) {
            super(player, knowledge);
        }
    }
}
