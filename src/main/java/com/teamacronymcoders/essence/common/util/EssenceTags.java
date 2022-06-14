package com.teamacronymcoders.essence.common.util;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class EssenceTags {

    public static class EssenceItemTags {
        public static final TagKey<Item> AMMO_HOLDER = essenceItemTag("ammo_holder");

        public static final TagKey<Item> ESSENCE_WOOD_LOG = essenceItemTag("essence_logs");
        public static final TagKey<Item> ESSENCE_WOOD_PLANKS = essenceItemTag("essence_planks");
        public static final TagKey<Item> ESSENCE_INFUSED_METAL_NUGGET = essenceItemTag("infused_nugget");
        public static final TagKey<Item> ESSENCE_INFUSED_METAL = essenceItemTag("infused_ingot");
        public static final TagKey<Item> ESSENCE_INFUSED_METAL_BLOCK = essenceItemTag("infused_block");
        public static final TagKey<Item> ESSENCE_INFUSED_CRYSTAL = essenceItemTag("infused_crystal");
        public static final TagKey<Item> ESSENCE_INFUSED_CRYSTAL_BLOCK = essenceItemTag("infused_crystal_block");
        public static final TagKey<Item> ESSENCE_INFUSED_METAL_NUGGET_EMPOWERED = essenceItemTag("infused_nugget_empowered");
        public static final TagKey<Item> ESSENCE_INFUSED_METAL_EMPOWERED = essenceItemTag("infused_ingot_empowered");
        public static final TagKey<Item> ESSENCE_INFUSED_METAL_BLOCK_EMPOWERED = essenceItemTag("infused_block_empowered");
        public static final TagKey<Item> ESSENCE_INFUSED_CRYSTAL_EMPOWERED = essenceItemTag("infused_crystal_empowered");
        public static final TagKey<Item> ESSENCE_INFUSED_CRYSTAL_EMPOWERED_BLOCK = essenceItemTag("infused_crystal_block_empowered");
        public static final TagKey<Item> ESSENCE_INFUSED_METAL_NUGGET_SUPREME = essenceItemTag("infused_nugget_supreme");
        public static final TagKey<Item> ESSENCE_INFUSED_METAL_SUPREME = essenceItemTag("infused_ingot_supreme");
        public static final TagKey<Item> ESSENCE_INFUSED_METAL_BLOCK_SUPREME = essenceItemTag("infused_block_supreme");
        public static final TagKey<Item> ESSENCE_INFUSED_CRYSTAL_SUPREME = essenceItemTag("infused_crystal_supreme");
        public static final TagKey<Item> ESSENCE_INFUSED_CRYSTAL_SUPREME_BLOCK = essenceItemTag("infused_crystal_block_supreme");
        public static final TagKey<Item> ESSENCE_INFUSED_METAL_NUGGET_DIVINE = essenceItemTag("infused_nugget_divine");
        public static final TagKey<Item> ESSENCE_INFUSED_METAL_DIVINE = essenceItemTag("infused_ingot_divine");
        public static final TagKey<Item> ESSENCE_INFUSED_METAL_BLOCK_DIVINE = essenceItemTag("infused_block_divine");
        public static final TagKey<Item> ESSENCE_INFUSED_CRYSTAL_DIVINE = essenceItemTag("infused_crystal_divine");
        public static final TagKey<Item> ESSENCE_INFUSED_CRYSTAL_DIVINE_BLOCK = essenceItemTag("infused_crystal_block_divine");
        public static final TagKey<Item> ESSENCE_INFUSED_STICK = essenceItemTag("infused_stick");
        public static final TagKey<Item> ESSENCE_BRICKS = essenceItemTag("essence_bricks");

        public static final TagKey<Item> ESSENCE_AXE = essenceItemTag("essence_axe");
        public static final TagKey<Item> ESSENCE_BOW = essenceItemTag("essence_bow");
        public static final TagKey<Item> ESSENCE_HOE = essenceItemTag("essence_hoe");
        public static final TagKey<Item> ESSENCE_OMNITOOL = essenceItemTag("essence_omnitool");
        public static final TagKey<Item> ESSENCE_PICKAXE = essenceItemTag("essence_pickaxe");
        public static final TagKey<Item> ESSENCE_SHEAR = essenceItemTag("essence_shears");
        public static final TagKey<Item> ESSENCE_SHOVEL = essenceItemTag("essence_shovel");
        public static final TagKey<Item> ESSENCE_SWORD = essenceItemTag("essence_sword");

        public static final TagKey<Item> ESSENCE_AXE_EMPOWERED = essenceItemTag("essence_axe_empowered");
        public static final TagKey<Item> ESSENCE_BOW_EMPOWERED = essenceItemTag("essence_bow_empowered");
        public static final TagKey<Item> ESSENCE_HOE_EMPOWERED = essenceItemTag("essence_hoe_empowered");
        public static final TagKey<Item> ESSENCE_OMNITOOL_EMPOWERED = essenceItemTag("essence_omnitool_empowered");
        public static final TagKey<Item> ESSENCE_PICKAXE_EMPOWERED = essenceItemTag("essence_pickaxe_empowered");
        public static final TagKey<Item> ESSENCE_SHEAR_EMPOWERED = essenceItemTag("essence_shears_empowered");
        public static final TagKey<Item> ESSENCE_SHOVEL_EMPOWERED = essenceItemTag("essence_shovel_empowered");
        public static final TagKey<Item> ESSENCE_SWORD_EMPOWERED = essenceItemTag("essence_sword_empowered");

        public static final TagKey<Item> ESSENCE_AXE_SUPREME = essenceItemTag("essence_axe_supreme");
        public static final TagKey<Item> ESSENCE_BOW_SUPREME = essenceItemTag("essence_bow_supreme");
        public static final TagKey<Item> ESSENCE_HOE_SUPREME = essenceItemTag("essence_hoe_supreme");
        public static final TagKey<Item> ESSENCE_OMNITOOL_SUPREME = essenceItemTag("essence_omnitool_supreme");
        public static final TagKey<Item> ESSENCE_PICKAXE_SUPREME = essenceItemTag("essence_pickaxe_supreme");
        public static final TagKey<Item> ESSENCE_SHEAR_SUPREME = essenceItemTag("essence_shears_supreme");
        public static final TagKey<Item> ESSENCE_SHOVEL_SUPREME = essenceItemTag("essence_shovel_supreme");
        public static final TagKey<Item> ESSENCE_SWORD_SUPREME = essenceItemTag("essence_sword_supreme");

        public static final TagKey<Item> ESSENCE_AXE_DIVINE = essenceItemTag("essence_axe_divine");
        public static final TagKey<Item> ESSENCE_BOW_DIVINE = essenceItemTag("essence_bow_divine");
        public static final TagKey<Item> ESSENCE_HOE_DIVINE = essenceItemTag("essence_hoe_divine");
        public static final TagKey<Item> ESSENCE_OMNITOOL_DIVINE = essenceItemTag("essence_omnitool_divine");
        public static final TagKey<Item> ESSENCE_PICKAXE_DIVINE = essenceItemTag("essence_pickaxe_divine");
        public static final TagKey<Item> ESSENCE_SHEAR_DIVINE = essenceItemTag("essence_shears_divine");
        public static final TagKey<Item> ESSENCE_SHOVEL_DIVINE = essenceItemTag("essence_shovel_divine");
        public static final TagKey<Item> ESSENCE_SWORD_DIVINE = essenceItemTag("essence_sword_divine");

        public static final TagKey<Item> MINEABLE_AXE = minecraftItemTag("mineable/axe");
        public static final TagKey<Item> MINEABLE_PICKAXE = minecraftItemTag("mineable/pickaxe");
        public static final TagKey<Item> MINEABLE_SHOVEL = minecraftItemTag("mineable/shovel");
        public static final TagKey<Item> MINEABLE_HOE = minecraftItemTag("mineable/hoe");

        public static final TagKey<Item> IRON_TIER = minecraftItemTag("needs_iron_tool");
        public static final TagKey<Item> DIAMOND_TIER = minecraftItemTag("needs_diamond_tool");
        public static final TagKey<Item> NETHERITE_TIER = forgeItemTag("needs_netherite_tool");

        public static final TagKey<Item> ENDERITE_SCRAP = essenceItemTag("enderite_scrap");
    }

    public static class EssenceBlockTags {
        public static final TagKey<Block> FORGE_MOVEABLE_BLACKLIST = forgeBlockTag("moving/blacklist");
        public static final TagKey<Block> FORGE_MOVEABLE_WHITELIST = forgeBlockTag("moving/whitelist");
        public static final TagKey<Block> RELOCATION_NOT_SUPPORTED = forgeBlockTag("relocation_not_supported");

        public static final TagKey<Block> ESSENCE_CRYSTAL_ORE = essenceBlockTag("essence_crystal_ore");
        public static final TagKey<Block> ESSENCE_WOOD_LOG = essenceBlockTag("essence_logs");
        public static final TagKey<Block> ESSENCE_ORE = essenceBlockTag("essence_ore");
        public static final TagKey<Block> ESSENCE_BRICKS = essenceBlockTag("essence_bricks");

        public static final TagKey<Block> END_STONE_REPLACEABLE = essenceBlockTag("end_stone_ore_replaceable");

        public static final TagKey<Block> OMNITOOL_BLOCKS = essenceBlockTag("omnitool_tags");
    }

    public static class EssenceTileEntityTypeTags {
        public static final TagKey<BlockEntityType<?>> IMMOVABLE = forgeTileEntityTag("relocation_not_supported");
        public static final TagKey<BlockEntityType<?>> RELOCATION_NOT_SUPPORTED = forgeTileEntityTag("immovable");
    }

    public static class EssenceEntityTags {
        public static final TagKey<EntityType<?>> BLACKLIST = essenceEntityTag("entity/blacklist");
        public static final TagKey<EntityType<?>> WHITELIST = essenceEntityTag("entity/whitelist");
    }

    public static class EssenceFluidTags {
        public static final TagKey<Fluid> FORGE_ESSENCE = forgeFluidTag("fluid/essence");
        public static final TagKey<Fluid> MY_ESSENCE = essenceFluidTag("fluid/essence");
        public static final TagKey<Fluid> FORGE_EXPERIENCE = forgeFluidTag("fluid/experience");
        public static final TagKey<Fluid> MY_EXPERIENCE = essenceFluidTag("fluid/experience");
    }

    public static class EssenceModifierTags {
        public static final TagKey<Item> NONE_TOOL = essenceItemTag("tool/none");
        public static final TagKey<Item> AXE_TOOL = essenceItemTag("tool/axe");
        public static final TagKey<Item> PICKAXE_TOOL = essenceItemTag("tool/pickaxe");
        public static final TagKey<Item> SHOVEL_TOOL = essenceItemTag("tool/shovel");

        public static final TagKey<Block> CASCADING_NONE = essenceBlockTag("cascading/none");
        public static final TagKey<Block> CASCADING_VEIN = essenceBlockTag("cascading/vein");
        public static final TagKey<Block> CASCADING_LUMBER = essenceBlockTag("cascading/lumber");
        public static final TagKey<Block> CASCADING_EXCAVATION = essenceBlockTag("cascading/excavation");
    }

    public static TagKey<Item> essenceItemTag(String path) {
        return ItemTags.create(new ResourceLocation(Essence.MOD_ID, path));
    }

    public static TagKey<Block> essenceBlockTag(String path) {
        return BlockTags.create(new ResourceLocation(Essence.MOD_ID, path));
    }

    public static TagKey<Fluid> essenceFluidTag(String path) {
        return FluidTags.create(new ResourceLocation(Essence.MOD_ID, path));
    }

    public static TagKey<EntityType<?>> essenceEntityTag(String path) {
        return TagKey.m_203882_(ForgeRegistries.ENTITIES.getRegistryKey(), new ResourceLocation(Essence.MOD_ID, path));
    }

    public static TagKey<Item> forgeItemTag(String path) {
        return ItemTags.create(new ResourceLocation("forge", path));
    }

    public static TagKey<Item> minecraftItemTag(String path) {
        return ItemTags.create(new ResourceLocation("minecraft", path));
    }

    public static TagKey<Block> forgeBlockTag(String path) {
        return BlockTags.create(new ResourceLocation("forge", path));
    }

    public static TagKey<Fluid> forgeFluidTag(String path) {
        return FluidTags.create(new ResourceLocation("forge", path));
    }

    public static TagKey<BlockEntityType<?>> forgeTileEntityTag(String path) {
        return TagKey.m_203882_(ForgeRegistries.BLOCK_ENTITIES.getRegistryKey(), new ResourceLocation("forge", path));
    }
}
