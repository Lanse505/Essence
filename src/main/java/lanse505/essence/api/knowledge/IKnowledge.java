package lanse505.essence.api.knowledge;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public interface IKnowledge {
    ResourceLocation getIdentifier();
    Item getInfusionItem();

}