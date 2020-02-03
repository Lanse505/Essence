package lanse505.essence.utils;

import com.hrznstudio.titanium.tab.AdvancedTitaniumTab;
import com.hrznstudio.titanium.tab.TitaniumTab;
import lanse505.essence.utils.module.ModuleObjects;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public class EssenceReferences {
    public static final String MODID = "essence";

    public static final ItemGroup CORE_TAB = new AdvancedTitaniumTab("essence_core", true) {
        @Override
        public void addIconStacks(Collection<ItemStack> icons) {
            icons.add(new ItemStack(ModuleObjects.ESSENCE.getBucketFluid()));
            icons.add(new ItemStack(ModuleObjects.ESSENCE_SAPLING));
            icons.add(new ItemStack(ModuleObjects.ESSENCE_LEAVES));
            icons.add(new ItemStack(ModuleObjects.ESSENCE_LOG));
            icons.add(new ItemStack(ModuleObjects.ESSENCE_PLANKS));
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }
    }.setBackgroundImageName("item_search.png");
}
