package com.yanny.age.stone.entities;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;

import javax.annotation.Nonnull;

class TargetAggressorGoal<T extends MobEntity> extends NearestAttackableTargetGoal<PlayerEntity> {
    private final Class<? extends IBecomeAngry> entity;

    TargetAggressorGoal(@Nonnull T entity, @Nonnull Class<? extends IBecomeAngry> clazz) {
        super(entity, PlayerEntity.class, true);
        this.entity = clazz;
    }

    @Override
    public boolean shouldExecute() {
        return entity.cast(goalOwner).isAngry() && super.shouldExecute() && goalOwner.world.getDifficulty() != Difficulty.PEACEFUL;
    }
}
