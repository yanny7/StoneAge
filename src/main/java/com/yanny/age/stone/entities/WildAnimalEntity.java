package com.yanny.age.stone.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

abstract class WildAnimalEntity extends AnimalEntity implements IBecomeAngry {
    static final DataParameter<Integer> GENERATION = EntityDataManager.createKey(BoarEntity.class, DataSerializers.VARINT);

    private int angerLevel;
    private UUID angerTargetUUID;

    WildAnimalEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void updateAITasks() {
        LivingEntity livingentity = this.getAttackTarget();
        if (livingentity != null && this.getDistanceSq(livingentity) < 16.0D) {
            this.calculateRotationYaw(livingentity.posX, livingentity.posZ);
            this.moveController.setMoveTo(livingentity.posX, livingentity.posY, livingentity.posZ, this.moveController.getSpeed());
        }

        if (this.isAngry()) {
            --this.angerLevel;
            if (!this.isAngry()) {
                this.setRevengeTarget(null);
                this.setAttackTarget(null);
                goalSelector.getRunningGoals().forEach(PrioritizedGoal::resetTask);
            }
        }

        if (this.isAngry() && this.angerTargetUUID != null && livingentity == null) {
            PlayerEntity playerentity = this.world.getPlayerByUuid(this.angerTargetUUID);
            this.setRevengeTarget(playerentity);
            this.attackingPlayer = playerentity;
            this.recentlyHit = this.getRevengeTimer();
        }
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();

            if (entity instanceof PlayerEntity && !((PlayerEntity)entity).isCreative() && this.canEntityBeSeen(entity)) {
                this.becomeAngryAt(entity);
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public void writeAdditional(@Nonnull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putShort("Anger", (short)this.angerLevel);
        compound.putInt("Generation", dataManager.get(GENERATION));
        if (this.angerTargetUUID != null) {
            compound.putString("HurtBy", this.angerTargetUUID.toString());
        } else {
            compound.putString("HurtBy", "");
        }
    }

    @Override
    public void readAdditional(@Nonnull CompoundNBT compound) {
        super.readAdditional(compound);
        this.angerLevel = compound.getShort("Anger");
        this.setGeneration(compound.getInt("Generation"));
        String s = compound.getString("HurtBy");
        if (!s.isEmpty()) {
            this.angerTargetUUID = UUID.fromString(s);
            PlayerEntity playerentity = this.world.getPlayerByUuid(this.angerTargetUUID);
            this.setRevengeTarget(playerentity);
            if (playerentity != null) {
                this.attackingPlayer = playerentity;
                this.recentlyHit = this.getRevengeTimer();
            }
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(GENERATION, 0);
    }

    @Override
    public void setRevengeTarget(@Nullable LivingEntity livingBase) {
        super.setRevengeTarget(livingBase);
        if (livingBase != null) {
            this.angerTargetUUID = livingBase.getUniqueID();
        }
    }

    @Override
    public boolean becomeAngryAt(Entity entity) {
        this.angerLevel = this.nextRand();
        if (entity instanceof LivingEntity) {
            this.setRevengeTarget((LivingEntity)entity);
        }

        return true;
    }

    @Override
    public boolean isAngry() {
        return this.angerLevel > 0;
    }

    void setGeneration(int generation) {
        dataManager.set(GENERATION, generation);
    }

    private int nextRand() {
        return 100 + this.rand.nextInt(100);
    }

    private void calculateRotationYaw(double x, double z) {
        this.rotationYaw = (float)(MathHelper.atan2(z - this.posZ, x - this.posX) * (double)(180F / (float)Math.PI)) - 90.0F;
    }
}
