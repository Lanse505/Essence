package com.teamacronymcoders.essence.api.recipe.tool;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.compat.registrate.EssenceLootTableRegistrate;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class EssenceShearingRecipe extends SerializableRecipe {

    public static final GenericSerializer<EssenceShearingRecipe> SERIALIZER = new GenericSerializer<>(new ResourceLocation(Essence.MOD_ID, "tool/essence_shearing"), EssenceShearingRecipe.class);
    public static final List<EssenceShearingRecipe> RECIPES = new ArrayList<>();

    static {
        RECIPES.add(new EssenceShearingRecipe(new ResourceLocation(Essence.MOD_ID, "shearing_horse"), EntityType.HORSE, EntityType.ZOMBIE_HORSE, EssenceLootTableRegistrate.SHEARING_HORSE.toString()));
        RECIPES.add(new EssenceShearingRecipe(new ResourceLocation(Essence.MOD_ID, "shearing_zombie_horse"), EntityType.ZOMBIE_HORSE, EntityType.SKELETON_HORSE, EssenceLootTableRegistrate.SHEARING_ZOMBIE_HORSE.toString()));

        RECIPES.add(new EssenceShearingRecipe(new ResourceLocation(Essence.MOD_ID, "shearing_villager"), EntityType.VILLAGER, EntityType.ZOMBIE, EssenceLootTableRegistrate.SHEARING_VILLAGER.toString()));
        RECIPES.add(new EssenceShearingRecipe(new ResourceLocation(Essence.MOD_ID, "shearing_zombie"), EntityType.ZOMBIE, EntityType.SKELETON, EssenceLootTableRegistrate.SHEARING_ZOMBIE.toString()));

        // Soon(tm)
        //RECIPES.add(new EssenceShearingRecipe(new ResourceLocation(Essence.MOD_ID, "shearing_player"), EntityType.PLAYER, EntityType.PLAYER, ));
    }

    public EntityType<?> from;
    public EntityType<?> to;
    public String lootTableReference;

    public EssenceShearingRecipe(ResourceLocation recipeID) {
        super(recipeID);
    }

    public EssenceShearingRecipe(ResourceLocation recipeID, EntityType<?> from, EntityType<?> to, String lootTableReference) {
        this(recipeID);
        this.from = from;
        this.to = to;
        this.lootTableReference = lootTableReference;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(Container pContainer) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public GenericSerializer<? extends SerializableRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return SERIALIZER.getRecipeType();
    }

    public boolean matches(Entity entity) {
        return entity.getType() == from;
    }

    public InteractionResult resolveRecipe(ItemStack stack, Player player, LivingEntity sheared, InteractionHand hand) {
        if (matches(sheared)) {
            Level level = sheared.getLevel();
            BlockPos pos = sheared.blockPosition();
            float xRot = sheared.getXRot();
            float yRot = sheared.getYRot();
            if (from != EntityType.PLAYER) {
                Entity entity = to.create(level);
                if (entity != null) {
                    entity.setPos(pos.getX(), pos.getY(), pos.getZ());
                    entity.setXRot(xRot);
                    entity.setYRot(yRot);
                    List<ItemStack> loot;
                    if (!level.isClientSide()) {
                        LootTables manager = player.level.getServer().getLootTables();
                        ServerLevel serverLevel = (ServerLevel) level;
                        LootContext context = new LootContext.Builder(serverLevel)
                                .withParameter(LootContextParams.THIS_ENTITY, sheared)
                                .withParameter(LootContextParams.DAMAGE_SOURCE, DamageSource.GENERIC)
                                .withParameter(LootContextParams.ORIGIN, new Vec3(pos.getX(), pos.getY(), pos.getZ()))
                                .withLuck(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack))
                                .withOptionalRandomSeed(serverLevel.getSeed())
                                .create(LootContextParamSets.ENTITY);
                        loot = manager.get(new ResourceLocation(lootTableReference)).getRandomItems(context);
                        loot.forEach(s -> {
                            ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), s);
                            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(
                                    (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F,
                                    (Essence.RANDOM.nextFloat() * 0.05F),
                                    (Essence.RANDOM.nextFloat() - Essence.RANDOM.nextFloat()) * 0.1F));
                            level.addFreshEntity(itemEntity);
                        });
                        stack.hurtAndBreak(1, sheared, broadcaster -> broadcaster.broadcastBreakEvent(hand));
                        sheared.remove(Entity.RemovalReason.DISCARDED);
                        level.addFreshEntity(entity);
                        return InteractionResult.SUCCESS;
                    }
                }
            } else {
                // Implement Player Shearing here later...
            }

        }
        return InteractionResult.PASS;
    }
}
