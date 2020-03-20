package com.teamacronymcoders.essence.modifier.curio.attribute;

import com.teamacronymcoders.essence.api.modifier.item.extendables.ItemAttributeModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class ArmorModifier extends ItemAttributeModifier {

    public static final UUID ARMOR_UUID = UUID.fromString("fbe6f3d9-80bc-4160-b54c-5020b5a914bc");

    public ArmorModifier() {
        super(SharedMonsterAttributes.ARMOR, "armor_modifier", ARMOR_UUID, 2.5d, 4, AttributeModifier.Operation.ADDITION);
    }

}
