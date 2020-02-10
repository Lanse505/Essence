package lanse505.essence.utils.tags;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import static lanse505.essence.utils.EssenceReferences.MODID;

public class EssenceTags {

    public static class Items {
        public static Tag<Item> ESSENCE_WOOD_LOG = new ItemTags.Wrapper(new ResourceLocation(MODID, "essence_logs"));
        public static Tag<Item> ESSENCE_WOOD_PLANKS = new ItemTags.Wrapper(new ResourceLocation(MODID, "essence_planks"));
        public static Tag<Item> ESSENCE_CRYSTAL = new ItemTags.Wrapper(new ResourceLocation(MODID, "infused_crystal"));
        public static Tag<Item> ESSENCE_INFUSED_METAL = new ItemTags.Wrapper(new ResourceLocation(MODID, "infused_ingot"));
        public static Tag<Item> ESSENCE_INFUSED_METAL_NUGGET = new ItemTags.Wrapper(new ResourceLocation(MODID, "infused_nugget"));
        public static Tag<Item> ESSENCE_INFUSED_STICK = new ItemTags.Wrapper(new ResourceLocation(MODID, "infused_stick"));
    }

    public static class Blocks {
        public static Tag<Block> ESSENCE_CRYSTAL_ORE = new BlockTags.Wrapper(new ResourceLocation(MODID, "essence_crystal_ore"));
        public static Tag<Block> ESSENCE_WOOD_LOG = new BlockTags.Wrapper(new ResourceLocation(MODID, "essence_logs"));
        public static Tag<Block> ESSENCE_ORE = new BlockTags.Wrapper(new ResourceLocation(MODID, "essence_ore"));
    }
}
