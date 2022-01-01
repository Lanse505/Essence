package com.teamacronymcoders.essence.common.util;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.common.Tags.IOptionalNamedTag;
import net.minecraftforge.registries.ForgeRegistries;

public class EssenceTags {

    public static class EssenceItemTags {
        public static final IOptionalNamedTag<Item> AMMO_HOLDER = essenceItemTag("ammo_holder");

        public static final IOptionalNamedTag<Item> ESSENCE_WOOD_LOG = essenceItemTag("essence_logs");
        public static final IOptionalNamedTag<Item> ESSENCE_WOOD_PLANKS = essenceItemTag("essence_planks");
        public static final IOptionalNamedTag<Item> ESSENCE_CRYSTAL = essenceItemTag("infused_crystal");
        public static final IOptionalNamedTag<Item> ESSENCE_INFUSED_METAL_NUGGET = essenceItemTag("infused_nugget");
        public static final IOptionalNamedTag<Item> ESSENCE_INFUSED_METAL = essenceItemTag("infused_ingot");
        public static final IOptionalNamedTag<Item> ESSENCE_INFUSED_METAL_BLOCK = essenceItemTag("infused_block");
        public static final IOptionalNamedTag<Item> ESSENCE_INFUSED_METAL_NUGGET_EMPOWERED = essenceItemTag("infused_nugget_empowered");
        public static final IOptionalNamedTag<Item> ESSENCE_INFUSED_METAL_EMPOWERED = essenceItemTag("infused_ingot_empowered");
        public static final IOptionalNamedTag<Item> ESSENCE_INFUSED_METAL_BLOCK_EMPOWERED = essenceItemTag("infused_block_empowered");
        public static final IOptionalNamedTag<Item> ESSENCE_INFUSED_METAL_NUGGET_SUPREME = essenceItemTag("infused_nugget_supreme");
        public static final IOptionalNamedTag<Item> ESSENCE_INFUSED_METAL_SUPREME = essenceItemTag("infused_ingot_supreme");
        public static final IOptionalNamedTag<Item> ESSENCE_INFUSED_METAL_BLOCK_SUPREME = essenceItemTag("infused_block_supreme");
        public static final IOptionalNamedTag<Item> ESSENCE_INFUSED_METAL_NUGGET_DIVINE = essenceItemTag("infused_nugget_divine");
        public static final IOptionalNamedTag<Item> ESSENCE_INFUSED_METAL_DIVINE = essenceItemTag("infused_ingot_divine");
        public static final IOptionalNamedTag<Item> ESSENCE_INFUSED_METAL_BLOCK_DIVINE = essenceItemTag("infused_block_divine");
        public static final IOptionalNamedTag<Item> ESSENCE_INFUSED_STICK = essenceItemTag("infused_stick");
        public static final IOptionalNamedTag<Item> ESSENCE_BRICKS = essenceItemTag("essence_bricks");

        public static final IOptionalNamedTag<Item> ESSENCE_AXE = essenceItemTag("essence_axe");
        public static final IOptionalNamedTag<Item> ESSENCE_BOW = essenceItemTag("essence_bow");
        public static final IOptionalNamedTag<Item> ESSENCE_HOE = essenceItemTag("essence_hoe");
        public static final IOptionalNamedTag<Item> ESSENCE_OMNITOOL = essenceItemTag("essence_omnitool");
        public static final IOptionalNamedTag<Item> ESSENCE_PICKAXE = essenceItemTag("essence_pickaxe");
        public static final IOptionalNamedTag<Item> ESSENCE_SHEAR = essenceItemTag("essence_shears");
        public static final IOptionalNamedTag<Item> ESSENCE_SHOVEL = essenceItemTag("essence_shovel");
        public static final IOptionalNamedTag<Item> ESSENCE_SWORD = essenceItemTag("essence_sword");

        public static final IOptionalNamedTag<Item> ESSENCE_AXE_EMPOWERED = essenceItemTag("essence_axe_empowered");
        public static final IOptionalNamedTag<Item> ESSENCE_BOW_EMPOWERED = essenceItemTag("essence_bow_empowered");
        public static final IOptionalNamedTag<Item> ESSENCE_HOE_EMPOWERED = essenceItemTag("essence_hoe_empowered");
        public static final IOptionalNamedTag<Item> ESSENCE_OMNITOOL_EMPOWERED = essenceItemTag("essence_omnitool_empowered");
        public static final IOptionalNamedTag<Item> ESSENCE_PICKAXE_EMPOWERED = essenceItemTag("essence_pickaxe_empowered");
        public static final IOptionalNamedTag<Item> ESSENCE_SHEAR_EMPOWERED = essenceItemTag("essence_shears_empowered");
        public static final IOptionalNamedTag<Item> ESSENCE_SHOVEL_EMPOWERED = essenceItemTag("essence_shovel_empowered");
        public static final IOptionalNamedTag<Item> ESSENCE_SWORD_EMPOWERED = essenceItemTag("essence_sword_empowered");

        public static final IOptionalNamedTag<Item> ESSENCE_AXE_SUPREME = essenceItemTag("essence_axe_supreme");
        public static final IOptionalNamedTag<Item> ESSENCE_BOW_SUPREME = essenceItemTag("essence_bow_supreme");
        public static final IOptionalNamedTag<Item> ESSENCE_HOE_SUPREME = essenceItemTag("essence_hoe_supreme");
        public static final IOptionalNamedTag<Item> ESSENCE_OMNITOOL_SUPREME = essenceItemTag("essence_omnitool_supreme");
        public static final IOptionalNamedTag<Item> ESSENCE_PICKAXE_SUPREME = essenceItemTag("essence_pickaxe_supreme");
        public static final IOptionalNamedTag<Item> ESSENCE_SHEAR_SUPREME = essenceItemTag("essence_shears_supreme");
        public static final IOptionalNamedTag<Item> ESSENCE_SHOVEL_SUPREME = essenceItemTag("essence_shovel_supreme");
        public static final IOptionalNamedTag<Item> ESSENCE_SWORD_SUPREME = essenceItemTag("essence_sword_supreme");

        public static final IOptionalNamedTag<Item> ESSENCE_AXE_DIVINE = essenceItemTag("essence_axe_divine");
        public static final IOptionalNamedTag<Item> ESSENCE_BOW_DIVINE = essenceItemTag("essence_bow_divine");
        public static final IOptionalNamedTag<Item> ESSENCE_HOE_DIVINE = essenceItemTag("essence_hoe_divine");
        public static final IOptionalNamedTag<Item> ESSENCE_OMNITOOL_DIVINE = essenceItemTag("essence_omnitool_divine");
        public static final IOptionalNamedTag<Item> ESSENCE_PICKAXE_DIVINE = essenceItemTag("essence_pickaxe_divine");
        public static final IOptionalNamedTag<Item> ESSENCE_SHEAR_DIVINE = essenceItemTag("essence_shears_divine");
        public static final IOptionalNamedTag<Item> ESSENCE_SHOVEL_DIVINE = essenceItemTag("essence_shovel_divine");
        public static final IOptionalNamedTag<Item> ESSENCE_SWORD_DIVINE = essenceItemTag("essence_sword_divine");

        public static final IOptionalNamedTag<Item> MINEABLE_AXE = minecraftItemTag("mineable/axe");
        public static final IOptionalNamedTag<Item> MINEABLE_PICKAXE = minecraftItemTag("mineable/pickaxe");
        public static final IOptionalNamedTag<Item> MINEABLE_SHOVEL = minecraftItemTag("mineable/shovel");
        public static final IOptionalNamedTag<Item> MINEABLE_HOE = minecraftItemTag("mineable/hoe");

        public static final IOptionalNamedTag<Item> WOOD_TIER = forgeItemTag("needs_wood_tool");
        public static final IOptionalNamedTag<Item> GOLD_TIER = forgeItemTag("needs_gold_tool");
        public static final IOptionalNamedTag<Item> STONE_TIER = minecraftItemTag("needs_stone_tool");
        public static final IOptionalNamedTag<Item> IRON_TIER = minecraftItemTag("needs_iron_tool");
        public static final IOptionalNamedTag<Item> DIAMOND_TIER = minecraftItemTag("needs_diamond_tool");
        public static final IOptionalNamedTag<Item> NETHERITE_TIER = forgeItemTag("needs_netherite_tool");
    }

    public static class EssenceBlockTags {
        public static final IOptionalNamedTag<Block> FORGE_MOVEABLE_BLACKLIST = forgeBlockTag("moving/blacklist");
        public static final IOptionalNamedTag<Block> FORGE_MOVEABLE_WHITELIST = forgeBlockTag("moving/whitelist");
        public static final IOptionalNamedTag<Block> RELOCATION_NOT_SUPPORTED = forgeBlockTag("relocation_not_supported");

        public static final IOptionalNamedTag<Block> ESSENCE_CRYSTAL_ORE = essenceBlockTag("essence_crystal_ore");
        public static final IOptionalNamedTag<Block> ESSENCE_WOOD_LOG = essenceBlockTag("essence_logs");
        public static final IOptionalNamedTag<Block> ESSENCE_ORE = essenceBlockTag("essence_ore");
        public static final IOptionalNamedTag<Block> ESSENCE_BRICKS = essenceBlockTag("essence_bricks");
    }

    public static class EssenceTileEntityTypeTags {
        public static final IOptionalNamedTag<BlockEntityType<?>> IMMOVABLE = forgeTileEntityTag("relocation_not_supported");
        public static final IOptionalNamedTag<BlockEntityType<?>> RELOCATION_NOT_SUPPORTED = forgeTileEntityTag("immovable");
    }

    public static class EssenceEntityTags {
        public static final IOptionalNamedTag<EntityType<?>> BLACKLIST = essenceEntityTag("entity/blacklist");
        public static final IOptionalNamedTag<EntityType<?>> WHITELIST = essenceEntityTag("entity/whitelist");
    }

    public static class EssenceFluidTags {
        public static final IOptionalNamedTag<Fluid> FORGE_ESSENCE = forgeFluidTag("fluid/essence");
        public static final IOptionalNamedTag<Fluid> MY_ESSENCE = essenceFluidTag("fluid/essence");
        public static final IOptionalNamedTag<Fluid> FORGE_EXPERIENCE = forgeFluidTag("fluid/experience");
        public static final IOptionalNamedTag<Fluid> MY_EXPERIENCE = essenceFluidTag("fluid/experience");
    }

    public static class EssenceModifierTags {
        public static final IOptionalNamedTag<Item> NONE_TOOL = essenceItemTag("tool/none");
        public static final IOptionalNamedTag<Item> AXE_TOOL = essenceItemTag("tool/axe");
        public static final IOptionalNamedTag<Item> PICKAXE_TOOL = essenceItemTag("tool/pickaxe");
        public static final IOptionalNamedTag<Item> SHOVEL_TOOL = essenceItemTag("tool/shovel");

        public static final IOptionalNamedTag<Block> CASCADING_NONE = essenceBlockTag("cascading/none");
        public static final IOptionalNamedTag<Block> CASCADING_VEIN = essenceBlockTag("cascading/vein");
        public static final IOptionalNamedTag<Block> CASCADING_LUMBER = essenceBlockTag("cascading/lumber");
        public static final IOptionalNamedTag<Block> CASCADING_EXCAVATION = essenceBlockTag("cascading/excavation");
    }

    public static IOptionalNamedTag<Item> essenceItemTag(String path) {
        return ItemTags.createOptional(new ResourceLocation(Essence.MOD_ID, path));
    }

    public static IOptionalNamedTag<Block> essenceBlockTag(String path) {
        return BlockTags.createOptional(new ResourceLocation(Essence.MOD_ID, path));
    }

    public static IOptionalNamedTag<Fluid> essenceFluidTag(String path) {
        return FluidTags.createOptional(new ResourceLocation(Essence.MOD_ID, path));
    }

    public static IOptionalNamedTag<EntityType<?>> essenceEntityTag(String path) {
        return EntityTypeTags.createOptional(new ResourceLocation(Essence.MOD_ID, path));
    }

    public static IOptionalNamedTag<BlockEntityType<?>> essenceTileEntityTag(String path) {
        return ForgeTagHandler.createOptionalTag(ForgeRegistries.BLOCK_ENTITIES, new ResourceLocation(Essence.MOD_ID, path));
    }

    public static IOptionalNamedTag<Item> forgeItemTag(String path) {
        return ItemTags.createOptional(new ResourceLocation("forge", path));
    }

    public static IOptionalNamedTag<Item> minecraftItemTag(String path) {
        return ItemTags.createOptional(new ResourceLocation("minecraft", path));
    }

    public static IOptionalNamedTag<Block> forgeBlockTag(String path) {
        return BlockTags.createOptional(new ResourceLocation("forge", path));
    }

    public static IOptionalNamedTag<Block> forgeBlockOptionalTag(String path) {
        return BlockTags.createOptional(new ResourceLocation("forge", path));
    }

    public static IOptionalNamedTag<Fluid> forgeFluidTag(String path) {
        return FluidTags.createOptional(new ResourceLocation("forge", path));
    }

    public static IOptionalNamedTag<BlockEntityType<?>> forgeTileEntityTag(String path) {
        return ForgeTagHandler.createOptionalTag(ForgeRegistries.BLOCK_ENTITIES, new ResourceLocation("forge", path));
    }

    public static IOptionalNamedTag<BlockEntityType<?>> forgeTileEntityOptionalTag(String path) {
        return ForgeTagHandler.createOptionalTag(ForgeRegistries.BLOCK_ENTITIES, new ResourceLocation("forge", path));
    }
}
