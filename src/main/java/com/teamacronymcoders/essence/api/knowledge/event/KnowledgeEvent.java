package com.teamacronymcoders.essence.api.knowledge.event;

import com.teamacronymcoders.essence.api.knowledge.Knowledge;
import com.teamacronymcoders.essence.serializable.advancement.criterion.EssenceAdvancements;
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
  public static class addPre extends KnowledgeEvent {
    public addPre(Player player, Knowledge knowledge) {
      super(player, knowledge);
    }
  }

  /**
   *
   */
  public static class addPost extends KnowledgeEvent {
    public addPost(ServerPlayer player, Knowledge knowledge) {
      super(player, knowledge);
      EssenceAdvancements.KNOWLEDGE_TRIGGER.trigger(player, knowledge);
    }
  }


  /**
   *
   */
  public static class remove extends KnowledgeEvent {
    public remove(Player player, Knowledge knowledge) {
      super(player, knowledge);
    }
  }
}
