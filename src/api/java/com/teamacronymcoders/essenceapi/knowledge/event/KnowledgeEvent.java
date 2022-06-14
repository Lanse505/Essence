package com.teamacronymcoders.essenceapi.knowledge.event;

import com.teamacronymcoders.essenceapi.advancement.criterion.EssenceAdvancements;
import com.teamacronymcoders.essenceapi.knowledge.Knowledge;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public class KnowledgeEvent extends PlayerEvent {

    private final Knowledge knowledge;

    public KnowledgeEvent(Player player, Knowledge knowledge) {
        super(player);
        this.knowledge = knowledge;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }


    /**
     *
     */
    @Cancelable
    public static class AddPre extends KnowledgeEvent {
        public AddPre(Player player, Knowledge knowledge) {
            super(player, knowledge);
        }
    }

    /**
     *
     */
    public static class AddPost extends KnowledgeEvent {
        public AddPost(ServerPlayer player, Knowledge knowledge) {
            super(player, knowledge);
            EssenceAdvancements.KNOWLEDGE_TRIGGER.trigger(player, knowledge);
        }
    }


    /**
     *
     */
    public static class Remove extends KnowledgeEvent {
        public Remove(Player player, Knowledge knowledge) {
            super(player, knowledge);
        }
    }
}
