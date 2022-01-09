package com.teamacronymcoders.essence.common.entity;

import com.google.common.collect.Sets;
import com.teamacronymcoders.essence.api.modifier.item.extendable.ItemArrowCoreModifier;
import com.teamacronymcoders.essence.common.capability.EssenceCoreCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpectralArrowItem;
import net.minecraft.world.item.TippedArrowItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.Set;

public class ModifiableArrowEntity extends Arrow {

    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(ModifiableArrowEntity.class, EntityDataSerializers.INT);
    private Potion potion = Potions.EMPTY;
    private final Set<MobEffectInstance> customPotionEffects = Sets.newHashSet();
    private boolean fixedColor;
    private ItemStack bowStack = ItemStack.EMPTY;

    public ModifiableArrowEntity(EntityType<ModifiableArrowEntity> type, Level level) {
        super(type, level);
    }

    public ModifiableArrowEntity(Level level, double x, double y, double z, ItemStack bowStack, ItemStack arrowStack) {
        super(level, x, y, z);
        this.bowStack = bowStack.copy();
        this.setArrowEffects(arrowStack);
        if (arrowStack.getItem() instanceof TippedArrowItem || arrowStack.getItem() instanceof SpectralArrowItem) {
            this.pickup = Pickup.DISALLOWED;
        }
    }

    public ModifiableArrowEntity(Level level, LivingEntity shooter, ItemStack bowStack, ItemStack arrowStack) {
        super(level, shooter);
        this.bowStack = bowStack;
        this.setArrowEffects(arrowStack);
        if (arrowStack.getItem() instanceof TippedArrowItem || arrowStack.getItem() instanceof SpectralArrowItem) {
            this.pickup = Pickup.DISALLOWED;
        }
    }

    public void setArrowEffects(ItemStack stack) {
        if (stack.getItem() instanceof TippedArrowItem) {
            this.potion = PotionUtils.getPotion(stack);
            Collection<MobEffectInstance> collection = PotionUtils.getMobEffects(stack);
            if (!collection.isEmpty()) {
                this.customPotionEffects.addAll(collection);
            }
            int i = getCustomColor(stack);
            if (i == -1) {
                this.refreshColor();
            } else {
                this.setFixedColor(i);
            }
        } else if (stack.getItem() == Items.ARROW) {
            this.potion = Potions.EMPTY;
            this.customPotionEffects.clear();
            this.entityData.set(COLOR, -1);
        }
    }

    public static int getCustomColor(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        return compoundTag != null && compoundTag.contains("CustomPotionColor", 99) ? compoundTag.getInt("CustomPotionColor") : -1;
    }

    private void refreshColor() {
        this.fixedColor = false;
        if (this.potion == Potions.EMPTY && this.customPotionEffects.isEmpty()) {
            this.entityData.set(COLOR, -1);
        } else {
            this.entityData.set(COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.customPotionEffects)));
        }
    }

    public void addEffect(MobEffectInstance effect) {
        this.customPotionEffects.add(effect);
        this.entityData.set(COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.customPotionEffects)));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(COLOR, -1);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.spawnPotionParticles(1);
                }
            } else {
                this.spawnPotionParticles(2);
            }
        } else if (this.inGround && this.inGroundTime != 0 && !this.customPotionEffects.isEmpty() && this.inGroundTime >= 600) {
            this.level.broadcastEntityEvent(this, (byte) 0);
            this.potion = Potions.EMPTY;
            this.customPotionEffects.clear();
            this.entityData.set(COLOR, -1);
        }
    }

    private void spawnPotionParticles(int particleCount) {
        int i = this.getColor();
        if (i != -1 && particleCount > 0) {
            double d0 = (double) (i >> 16 & 255) / 255.0D;
            double d1 = (double) (i >> 8 & 255) / 255.0D;
            double d2 = (double) (i >> 0 & 255) / 255.0D;
            for (int j = 0; j < particleCount; ++j) {
                this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
            }

        }
    }

    public int getColor() {
        return this.entityData.get(COLOR);
    }

    private void setFixedColor(int color) {
        this.fixedColor = true;
        this.entityData.set(COLOR, color);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.potion != Potions.EMPTY && this.potion != null) {
            compound.putString("Potion", ForgeRegistries.POTIONS.getKey(this.potion).toString());
        }
        if (this.fixedColor) {
            compound.putInt("Color", this.getColor());
        }
        if (!this.customPotionEffects.isEmpty()) {
            ListTag tags = new ListTag();
            for (MobEffectInstance effectinstance : this.customPotionEffects) {
                tags.add(effectinstance.save(new CompoundTag()));
            }
            compound.put("CustomPotionEffects", tags);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Potion", 8)) {
            this.potion = PotionUtils.getPotion(compound);
        }
        for (MobEffectInstance effectinstance : PotionUtils.getAllEffects(compound)) {
            this.addEffect(effectinstance);
        }
        if (compound.contains("Color", 99)) {
            this.setFixedColor(compound.getInt("Color"));
        } else {
            this.refreshColor();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!(result.getEntity() instanceof LivingEntity)) return;
        for (MobEffectInstance effectinstance : this.potion.getEffects()) {
            LivingEntity living = (LivingEntity) result.getEntity();
            living.addEffect(new MobEffectInstance(effectinstance.getEffect(), Math.max(effectinstance.getDuration() / 8, 1), effectinstance.getAmplifier(), effectinstance.isAmbient(), effectinstance.isVisible()));
        }
        if (!this.customPotionEffects.isEmpty()) {
            for (MobEffectInstance instance : this.customPotionEffects) {
                LivingEntity living = (LivingEntity) result.getEntity();
                living.addEffect(instance);
            }
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    /**
     * Handler for {@link Level#broadcastEntityEvent(Entity, byte)}
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
        if (id == 0) {
            int i = this.getColor();
            if (i != -1) {
                double d0 = (double) (i >> 16 & 255) / 255.0D;
                double d1 = (double) (i >> 8 & 255) / 255.0D;
                double d2 = (double) (i >> 0 & 255) / 255.0D;
                for (int j = 0; j < 20; ++j) {
                    this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
                }
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    // func_230299_a_ = onCollide
    // func_234616_v_ = getShooter
    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        Entity shooter = this.getOwner();
        bowStack.getCapability(EssenceCoreCapability.ITEMSTACK_MODIFIER_HOLDER).map(holder -> holder.getModifierInstances().stream().filter(instance -> instance.getModifier() instanceof ItemArrowCoreModifier)).ifPresent(instances -> instances.forEach(instance -> {
            ItemArrowCoreModifier modifier = (ItemArrowCoreModifier) instance.getModifier();
            modifier.onCollide(bowStack, this, (Player) shooter, result, instance);
        }));
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
