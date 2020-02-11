package lanse505.essence.api.modifier;

import lanse505.essence.api.modifier.core.BaseModifier;
import lanse505.essence.utils.EssenceHelpers;
import net.minecraft.util.ResourceLocation;

public class InteractionCoreModifier extends BaseModifier {
    private final int maxLevel;

    public InteractionCoreModifier(ResourceLocation id, int maxLevel) {
        super(id);
        this.maxLevel = maxLevel;
    }

    public InteractionCoreModifier(String id, int maxLevel) {
        this(EssenceHelpers.getIDForActiveMod(id), maxLevel);
    }



    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }
}
