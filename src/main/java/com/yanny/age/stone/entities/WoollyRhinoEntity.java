package com.yanny.age.stone.entities;

import com.yanny.age.stone.subscribers.EntitySubscriber;
import com.yanny.age.stone.subscribers.SoundSubscriber;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WoollyRhinoEntity extends WildAnimalEntity {

    public WoollyRhinoEntity(@Nonnull EntityType<WoollyRhinoEntity> type, @Nonnull World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(@Nonnull AgeableEntity ageable) {
        return EntitySubscriber.woolly_rhino.create(world);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.fromItems(Items.HAY_BLOCK), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new AgroTargetGoal(this, WoollyRhinoEntity.class));
        this.targetSelector.addGoal(2, new TargetAggressorGoal<>(this, WoollyRhinoEntity.class));
    }

    public void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    @Override
    public boolean attackEntityAsMob(@Nonnull Entity entityIn) {
        //noinspection ConstantConditions
        this.playSound(SoundSubscriber.woolly_rhino_hit, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);

        if (entityIn instanceof LivingEntity) {
            ((LivingEntity) entityIn).knockBack(this, 2.0F,
                    MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180F)),
                    -MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180F)));
        }

        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0F);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundSubscriber.woolly_rhino_ambient;
    }

    @Override
    public SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn) {
        return SoundSubscriber.woolly_rhino_hit;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundSubscriber.woolly_rhino_death;
    }

    @Override
    public void playStepSound(@Nonnull BlockPos pos, @Nonnull BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.HAY_BLOCK;
    }
}
