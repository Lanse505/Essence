package com.teamacronymcoders.essence.utils;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class EssenceTags {

    public static class Items {
        public static Tag<Item> AMMO_HOLDER = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "ammo_holder"));

        public static Tag<Item> ESSENCE_WOOD_LOG = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "essence_logs"));
        public static Tag<Item> ESSENCE_WOOD_PLANKS = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "essence_planks"));
        public static Tag<Item> ESSENCE_CRYSTAL = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "infused_crystal"));
        public static Tag<Item> ESSENCE_INFUSED_METAL_NUGGET = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "infused_nugget"));
        public static Tag<Item> ESSENCE_INFUSED_METAL = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "infused_ingot"));
        public static Tag<Item> ESSENCE_INFUSED_METAL_BLOCK = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "infused_block"));
        public static Tag<Item> ESSENCE_INFUSED_STICK = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "infused_stick"));
        public static Tag<Item> ESSENCE_BRICKS = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "essence_bricks"));

        public static Tag<Item> ESSENCE_AXE = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "essence_axe"));
        public static Tag<Item> ESSENCE_PICKAXE = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "essence_pickaxe"));
        public static Tag<Item> ESSENCE_SHOVEL = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "essence_shovel"));
        public static Tag<Item> ESSENCE_HOE = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "essence_hoe"));
        public static Tag<Item> ESSENCE_SWORD = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "essence_sword"));
        public static Tag<Item> ESSENCE_OMNITOOL = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "essence_omnitool"));

        public static Tag<Item> ATTACK_DAMAGE_MODIFIER = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "modifier_attack_damage"));
        public static Tag<Item> EXPANDER_MODIFIER = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "modifier_expander"));
        public static Tag<Item> FORTUNE_MODIFIER = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "fortune_modifier"));
        public static Tag<Item> SILK_TOUCH_MODIFIER = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "silk_touch_modifier"));
    }

    public static class Blocks {
        public static Tag<Block> FORGE_MOVEABLE = new BlockTags.Wrapper(new ResourceLocation("forge:moveable"));

        public static Tag<Block> ESSENCE_CRYSTAL_ORE = new BlockTags.Wrapper(new ResourceLocation(Essence.MODID, "essence_crystal_ore"));
        public static Tag<Block> ESSENCE_WOOD_LOG = new BlockTags.Wrapper(new ResourceLocation(Essence.MODID, "essence_logs"));
        public static Tag<Block> ESSENCE_ORE = new BlockTags.Wrapper(new ResourceLocation(Essence.MODID, "essence_ore"));
        public static Tag<Block> ESSENCE_BRICKS = new BlockTags.Wrapper(new ResourceLocation(Essence.MODID, "essence_bricks"));
    }

    public static class Modifier {
        public static Tag<Item> NONE_TOOL = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "tool/none"));
        public static Tag<Item> AXE_TOOL = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "tool/axe"));
        public static Tag<Item> PICKAXE_TOOL = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "tool/pickaxe"));
        public static Tag<Item> SHOVEL_TOOL = new ItemTags.Wrapper(new ResourceLocation(Essence.MODID, "tool/shovel"));

        public static Tag<Block> CASCADING_NONE = new BlockTags.Wrapper(new ResourceLocation(Essence.MODID, "cascading/none"));
        public static Tag<Block> CASCADING_VEIN = new BlockTags.Wrapper(new ResourceLocation(Essence.MODID, "cascading/vein"));
        public static Tag<Block> CASCADING_LUMBER = new BlockTags.Wrapper(new ResourceLocation(Essence.MODID, "cascading/lumber"));
        public static Tag<Block> CASCADING_EXCAVATION = new BlockTags.Wrapper(new ResourceLocation(Essence.MODID, "cascading/excavation"));
    }


}
