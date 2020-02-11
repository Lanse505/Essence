package lanse505.essence.api.modifier;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lanse505.essence.api.modifier.core.BaseModifier;
import lanse505.essence.utils.EssenceHelpers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AttributeCoreModifier extends BaseModifier {
    private final List<Multimap<String, AttributeModifier>> modifiers = new ArrayList<>();
    private final int maxLevel;

    public AttributeCoreModifier(ResourceLocation id, IAttribute attribute, double amount, AttributeModifier.Operation operation, String uuid, int maxLevel) {
        super(id);
        this.maxLevel = maxLevel;
        final ResourceLocation registryName = getRegistryName();
        for (int i  = 1; i <= maxLevel; i++) {
            final Multimap<String, AttributeModifier> levelModifiers = HashMultimap.create();
            levelModifiers.put(attribute.getName(), new AttributeModifier(UUID.fromString(uuid), registryName.getNamespace() + "_" + registryName.getPath(), amount * i, operation));
            this.modifiers.add(levelModifiers);
        }
    }

    public AttributeCoreModifier(String id, IAttribute attribute, double amount, AttributeModifier.Operation operation, String uuid, int maxLevel) {
        this(EssenceHelpers.getIDForActiveMod(id), attribute, amount, operation, uuid, maxLevel);
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack, LivingEntity wielder, int level) {
        return this.modifiers.get(this.getLevelInRange(level) - 1);
    }
}
