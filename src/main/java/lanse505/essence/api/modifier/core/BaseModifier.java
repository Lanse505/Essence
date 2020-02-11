package lanse505.essence.api.modifier.core;

import lanse505.essence.utils.EssenceHelpers;
import net.minecraft.util.ResourceLocation;

public class BaseModifier extends Modifier {

    public BaseModifier(ResourceLocation id) {
        this.setRegistryName(id);
    }

    public BaseModifier(String id) {
        this.setRegistryName(EssenceHelpers.getIDForActiveMod(id));
    }

}
