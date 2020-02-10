package lanse505.essence.core.items.tools;

import lanse505.essence.utils.EssenceItemTiers;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;

public class EssenceSword extends SwordItem {

    public EssenceSword(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
        super(EssenceItemTiers.ESSENCE, 3, -2.4F, new Item.Properties().group(ItemGroup.COMBAT));
    }

}
