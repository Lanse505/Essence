package com.teamacronymcoders.essence.common.item.wrench;

import com.teamacronymcoders.essence.client.render.tesr.itemstack.SerializableMobRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SerializedEntityItem extends Item {

    public SerializedEntityItem(Item.Properties properties) {
        super(properties);
    }

    @Nullable
    public static LivingEntity getEntityFromNBT(CompoundTag nbt, Level level) {
        if (nbt.contains("entity")) {
            EntityType<?> type = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryParse(nbt.getString("entity")));
            if (type != null) {
                Entity entity = type.create(level);
                if (entity instanceof LivingEntity) {
                    entity.load(nbt);
                    return (LivingEntity) entity;
                }
                return null;
            }
        }
        return null;
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean containsEntity(ItemStack stack) {
        return stack != null && !stack.isEmpty() && stack.hasTag() && stack.getTag().contains("entity");
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (stack.getTag() != null || containsEntity(stack)) {
            LivingEntity entity = getEntityFromNBT(stack.getTag(), level);
            if (entity instanceof Mob mob) mob.playAmbientSound();
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        if ((player != null && player.getLevel().isClientSide()) || !containsEntity(stack) || stack.getTag() == null) {
            return InteractionResult.FAIL;
        }
        LivingEntity entity = getEntityFromNBT(stack.getTag(), level);
        BlockPos spawnPosition = context.getClickedPos().offset(context.getClickedFace().getNormal());
        if (entity != null) {
            stack.shrink(1);
            entity.absMoveTo(spawnPosition.getX() + 0.5, spawnPosition.getY(), spawnPosition.getZ() + 0.5, 0, 0);
            level.addFreshEntity(entity);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return new SerializableMobRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
            }
        });
    }
}
