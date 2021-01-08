package com.teamacronymcoders.essence.item.misc;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants.NBT;

import javax.annotation.Nullable;

public class CustomSpawnEggItem extends SpawnEggItem {

    private final EntityType entityTypeRO;
    public static final String ENTITY_TAG = "EntityTag";
    public static final String ID = "id";

    public CustomSpawnEggItem(EntityType entityTypeRO, int primaryColor, int secondaryColor) {
        super(null, primaryColor, secondaryColor, new Item.Properties().group(Essence.CORE_TAB));
        this.entityTypeRO = entityTypeRO;
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundNBT nbt) {
        if (nbt != null && nbt.contains(ENTITY_TAG, NBT.TAG_COMPOUND)) {
            CompoundNBT entityTag = nbt.getCompound(ENTITY_TAG);
            if (entityTag.contains(ID, NBT.TAG_STRING)) {
                return EntityType.byKey(entityTag.getString(ID)).orElse(entityTypeRO);
            }
        }
        return super.getType(nbt);
    }

    public void addToEggList() {
        EGGS.put(entityTypeRO, this);
    }
}
