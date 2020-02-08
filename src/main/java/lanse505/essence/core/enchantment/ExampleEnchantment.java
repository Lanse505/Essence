package lanse505.essence.core.enchantment;

import net.minecraft.enchantment.EfficiencyEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class ExampleEnchantment extends Enchantment {

    protected ExampleEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

}
