package lanse505.essence.api.modifier;

import lanse505.essence.api.modifier.core.BaseModifier;
import lanse505.essence.utils.EssenceHelpers;
import net.minecraft.util.ResourceLocation;

public class InteractionCoreModifier extends CoreModifier {

    public InteractionCoreModifier(ResourceLocation id, int maxLevel) {
        super(id, maxLevel);
    }

    public InteractionCoreModifier(String id, int maxLevel) {
        this(EssenceHelpers.getIDForActiveMod(id), maxLevel);
    }

}
