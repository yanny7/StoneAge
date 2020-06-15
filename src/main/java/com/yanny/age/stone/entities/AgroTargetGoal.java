package com.yanny.age.stone.entities;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.Difficulty;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

class AgroTargetGoal extends TargetGoal {
    private static final EntityPredicate predicate = (new EntityPredicate()).setLineOfSiteRequired().setUseInvisibilityCheck();
    private int revengeTimerOld;
    private final Class<? extends IBecomeAngry> reinforcement;
    private final Class<?>[] excludedReinforcementTypes;

    AgroTargetGoal(@Nonnull CreatureEntity creatureIn, @Nonnull Class<? extends IBecomeAngry> reinforcement, @Nonnull Class<?>... excludeReinforcement) {
        super(creatureIn, true);
        this.reinforcement = reinforcement;
        this.excludedReinforcementTypes = excludeReinforcement;
        this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean shouldExecute() {
        int i = this.goalOwner.getRevengeTimer();
        LivingEntity livingentity = this.goalOwner.getRevengeTarget();

        if (i != this.revengeTimerOld && livingentity != null) {
            for(Class<?> oclass : this.excludedReinforcementTypes) {
                if (oclass.isAssignableFrom(livingentity.getClass())) {
                    return false;
                }
            }

            return this.isSuitableTarget(livingentity, predicate) && goalOwner.world.getDifficulty() != Difficulty.PEACEFUL;
        } else {
            return false;
        }
    }

    @Override
    public void startExecuting() {
        this.goalOwner.setAttackTarget(this.goalOwner.getRevengeTarget());
        this.target = this.goalOwner.getAttackTarget();
        this.revengeTimerOld = this.goalOwner.getRevengeTimer();
        this.unseenMemoryTicks = 300;
        this.alertOthers();
        super.startExecuting();
    }

    private void alertOthers() {
        double d0 = this.getTargetDistance();
        List<MobEntity> list = this.goalOwner.world.getEntitiesWithinAABB(this.goalOwner.getClass(),
                (new AxisAlignedBB(this.goalOwner.getPosX(), this.goalOwner.getPosY(), this.goalOwner.getPosZ(),
                        this.goalOwner.getPosX() + 1.0D, this.goalOwner.getPosY() + 1.0D, this.goalOwner.getPosZ() + 1.0D)).grow(d0, 10.0D, d0));

        for (MobEntity mobentity : list) {
            if (this.goalOwner != mobentity && mobentity.getAttackTarget() == null
                    && (!(this.goalOwner instanceof TameableEntity) || ((TameableEntity) this.goalOwner).getOwner() == ((TameableEntity) mobentity).getOwner())
                    && !mobentity.isOnSameTeam(Objects.requireNonNull(this.goalOwner.getRevengeTarget()))) {

                boolean flag = false;

                if (mobentity.getClass() == reinforcement) {
                    flag = true;
                }

                if (!flag) {
                    break;
                }

                this.setAttackTarget(mobentity, this.goalOwner.getRevengeTarget());
            }
        }
    }

    private void setAttackTarget(@Nonnull MobEntity mobIn, @Nonnull LivingEntity targetIn) {
        if (reinforcement.isAssignableFrom(mobIn.getClass()) && this.goalOwner.canEntityBeSeen(targetIn) && reinforcement.cast(mobIn).becomeAngryAt(targetIn)) {
            mobIn.setAttackTarget(targetIn);
        }
    }
}
