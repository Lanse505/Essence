package lanse505.essence.utils;

import com.hrznstudio.titanium.module.ModuleController;
import com.mojang.datafixers.types.templates.CompoundList;
import lanse505.essence.api.modifier.core.Modifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;
import java.util.Map;

public class EssenceHelpers {
    public static final IForgeRegistry<Modifier> MODIFIERS = GameRegistry.findRegistry(Modifier.class);

    private static final String TAG_MODIFIERS = "Modifiers";
    private static final String TAG_MODIFIER = "Modifier";
    private static final String TAG_LEVEL = "ModifierLevel";

    public static ResourceLocation getIDForActiveMod(String id) {
        if (!id.contains(":")) {
            final ModContainer container = ModLoadingContext.get().getActiveContainer();
            if (container != null) {
                return new ResourceLocation(container.getModId(), id);
            }
        }
        return new ResourceLocation(id);
    }

    public static Modifier getModifierByName(String name) {
        return MODIFIERS.getValue(new ResourceLocation(name));
    }

    public static Map<Modifier, Integer> getModifiers(ItemStack stack) {
        final Map<Modifier, Integer> modifiers = new HashMap<>();
        if (stack.hasTag() && stack.getTag().contains(TAG_MODIFIERS)) {
            final ListNBT list = stack.getTag().getList(TAG_MODIFIERS, Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                final CompoundNBT compoundNBT = list.getCompound(i);
                final Modifier modifier = getModifierByName(compoundNBT.getString(TAG_MODIFIER));
                if (modifier != null) {
                    modifiers.put(modifier, compoundNBT.getInt(TAG_LEVEL));
                }
            }
        }
        return modifiers;
    }
}
