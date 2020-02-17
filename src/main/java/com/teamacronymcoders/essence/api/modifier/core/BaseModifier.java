package lanse505.essence.api.modifier.core;

import lanse505.essence.utils.EssenceHelpers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BaseModifier extends Modifier {

    /**
     * Returns a baseline dummy Modifier with a ResourceLocation id.
     * THIS IS A COMPLETELY DEAD CLASS, IT DOES NOTHING AND IS ONLY FOR BASELINE PURPOSES.
     */
    public BaseModifier(ResourceLocation id) {
        this.setRegistryName(id);
    }

    /**
     * Returns a baseline dummy Modifier with a ResourceLocation id.
     * THIS IS A COMPLETELY DEAD CLASS, IT DOES NOTHING AND IS ONLY FOR BASELINE PURPOSES.
     */
    public BaseModifier(String id) {
        this.setRegistryName(EssenceHelpers.getIDForActiveMod(id));
    }

    /**
     * @return Gets the ITextComponent that should be rendered in it's Information-Box on the ItemStack.
     */
    @OnlyIn(Dist.CLIENT)
    public ITextComponent getRenderedText() {
        return new TranslationTextComponent(this.getRegistryName().toString());
    }
}
