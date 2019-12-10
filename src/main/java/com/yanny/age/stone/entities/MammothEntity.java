package com.yanny.age.stone.entities;

import com.yanny.age.stone.Reference;
import com.yanny.age.stone.subscribers.EntitySubscriber;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MammothEntity extends WildAnimalEntity {
    private static final SoundEvent MAMMOTH_AMBIENT = new SoundEvent(new ResourceLocation(Reference.MODID, "mammoth_ambient"));
    private static final SoundEvent MAMMOTH_HIT = new SoundEvent(new ResourceLocation(Reference.MODID, "mammoth_hit"));
    private static final SoundEvent MAMMOTH_DEATH = new SoundEvent(new ResourceLocation(Reference.MODID, "mammoth_death"));

    public MammothEntity(EntityType<MammothEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(@Nonnull AgeableEntity ageable) {
        return EntitySubscriber.mammoth.create(world);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.fromItems(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new AgroTargetGoal(this, MammothEntity.class));
        this.targetSelector.addGoal(2, new TargetAggressorGoal<>(this, MammothEntity.class));
    }

    public void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.playSound(MAMMOTH_HIT, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0F);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return MAMMOTH_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return MAMMOTH_HIT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return MAMMOTH_DEATH;
    }

    @Override
    public void playStepSound(@Nonnull BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.WHEAT;
    }
}
