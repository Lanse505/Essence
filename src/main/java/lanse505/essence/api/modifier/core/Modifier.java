package lanse505.essence.api.modifier.core;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Modifier extends ForgeRegistryEntry<Modifier> {

    private ResourceLocation resourceLocation;
    private static final Multimap<String, AttributeModifier> EMPTY_ATTRIBUTE_MAP = HashMultimap.create();

    public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack, LivingEntity wielder, int level) {
        return EMPTY_ATTRIBUTE_MAP;
    }

    public int getMaxLevel() {
        return 1;
    }

    public boolean countsTowardsLimit(ItemStack stack, int level) {
        return true;
    }

    public int getLevelInRange(int level) {
        return Math.max(Math.min(level, this.getMaxLevel()), 0);
    }

    @OnlyIn(Dist.CLIENT)
    public String getTranslationName() {
        final ResourceLocation id = this.getRegistryName();
        return "essence.modifier." + id.getNamespace() + "." + id.getPath();
    }
}
