package com.teamacronymcoders.essence.util;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.*;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public class EssenceTags {

    public static class EssenceItemTags {
        public static final IOptionalNamedTag<Item> AMMO_HOLDER = ItemTags.createOptional(new ResourceLocation(Essence.MODID, "ammo_holder"));

        public static final INamedTag<Item> ESSENCE_WOOD_LOG = ItemTags.makeWrapperTag(Essence.MODID + ":" + "essence_logs");
        public static final INamedTag<Item> ESSENCE_WOOD_PLANKS = ItemTags.makeWrapperTag(Essence.MODID + ":" + "essence_planks");
        public static final INamedTag<Item> ESSENCE_CRYSTAL = ItemTags.makeWrapperTag(Essence.MODID + ":" + "infused_crystal");
        public static final INamedTag<Item> ESSENCE_INFUSED_METAL_NUGGET = ItemTags.makeWrapperTag(Essence.MODID + ":" + "infused_nugget");
        public static final INamedTag<Item> ESSENCE_INFUSED_METAL = ItemTags.makeWrapperTag(Essence.MODID + ":" + "infused_ingot");
        public static final INamedTag<Item> ESSENCE_INFUSED_METAL_BLOCK = ItemTags.makeWrapperTag(Essence.MODID + ":" + "infused_block");
        public static final INamedTag<Item> ESSENCE_INFUSED_STICK = ItemTags.makeWrapperTag(Essence.MODID + ":" + "infused_stick");
        public static final INamedTag<Item> ESSENCE_BRICKS = ItemTags.makeWrapperTag(Essence.MODID + ":" + "essence_bricks");

        public static final INamedTag<Item> ESSENCE_AXE = ItemTags.makeWrapperTag(Essence.MODID + ":" + "essence_axe");
        public static final INamedTag<Item> ESSENCE_BOW = ItemTags.makeWrapperTag(Essence.MODID + ":" + "essence_bow");
        public static final INamedTag<Item> ESSENCE_HOE = ItemTags.makeWrapperTag(Essence.MODID + ":" + "essence_hoe");
        public static final INamedTag<Item> ESSENCE_OMNITOOL = ItemTags.makeWrapperTag(Essence.MODID + ":" + "essence_omnitool");
        public static final INamedTag<Item> ESSENCE_PICKAXE = ItemTags.makeWrapperTag(Essence.MODID + ":" + "essence_pickaxe");
        public static final INamedTag<Item> ESSENCE_SHEAR = ItemTags.makeWrapperTag(Essence.MODID + ":" + "essence_shears");
        public static final INamedTag<Item> ESSENCE_SHOVEL = ItemTags.makeWrapperTag(Essence.MODID + ":" + "essence_shovel");
        public static final INamedTag<Item> ESSENCE_SWORD = ItemTags.makeWrapperTag(Essence.MODID + ":" + "essence_sword");
    }

    public static class EssenceBlockTags {
        public static final INamedTag<Block> FORGE_MOVEABLE_BLACKLIST = BlockTags.makeWrapperTag("forge:block/moving/blacklist");
        public static final INamedTag<Block> FORGE_MOVEABLE_WHITELIST = BlockTags.makeWrapperTag("forge:block/moving/whitelist");

        public static final INamedTag<Block> ESSENCE_CRYSTAL_ORE = BlockTags.makeWrapperTag(Essence.MODID + ":" + "essence_crystal_ore");
        public static final INamedTag<Block> ESSENCE_WOOD_LOG = BlockTags.makeWrapperTag(Essence.MODID + ":" + "essence_logs");
        public static final INamedTag<Block> ESSENCE_ORE = BlockTags.makeWrapperTag(Essence.MODID + ":" + "essence_ore");
        public static final INamedTag<Block> ESSENCE_BRICKS = BlockTags.makeWrapperTag(Essence.MODID + ":" + "essence_bricks");
    }

    public static class EssenceEntityTags {
        public static final INamedTag<EntityType<?>> BLACKLIST = EntityTypeTags.getTagById(Essence.MODID + ":" + "entity/blacklist");
        public static final INamedTag<EntityType<?>> WHITELIST = EntityTypeTags.getTagById(Essence.MODID + ":" + "entity/whitelist");
    }

    public static class EssenceFluidTags {
        public static final INamedTag<Fluid> FORGE_ESSENCE = FluidTags.makeWrapperTag("forge:fluid/essence");
        public static final INamedTag<Fluid> MY_ESSENCE = FluidTags.makeWrapperTag(Essence.MODID + ":" + "fluid/essence");
        public static final INamedTag<Fluid> FORGE_EXPERIENCE = FluidTags.makeWrapperTag("forge:fluid/experience");
        public static final INamedTag<Fluid> MY_EXPERIENCE = FluidTags.makeWrapperTag(Essence.MODID + ":" + "fluid/experience");
    }

    public static class EssenceModifierTags {
        public static final INamedTag<Item> NONE_TOOL = ItemTags.makeWrapperTag(Essence.MODID + ":" + "tool/none");
        public static final INamedTag<Item> AXE_TOOL = ItemTags.makeWrapperTag(Essence.MODID + ":" + "tool/axe");
        public static final INamedTag<Item> PICKAXE_TOOL = ItemTags.makeWrapperTag(Essence.MODID + ":" + "tool/pickaxe");
        public static final INamedTag<Item> SHOVEL_TOOL = ItemTags.makeWrapperTag(Essence.MODID + ":" + "tool/shovel");

        public static final INamedTag<Block> CASCADING_NONE = BlockTags.makeWrapperTag(Essence.MODID + ":" + "cascading/none");
        public static final INamedTag<Block> CASCADING_VEIN = BlockTags.makeWrapperTag(Essence.MODID + ":" + "cascading/vein");
        public static final INamedTag<Block> CASCADING_LUMBER = BlockTags.makeWrapperTag(Essence.MODID + ":" + "cascading/lumber");
        public static final INamedTag<Block> CASCADING_EXCAVATION = BlockTags.makeWrapperTag(Essence.MODID + ":" + "cascading/excavation");
    }


}
