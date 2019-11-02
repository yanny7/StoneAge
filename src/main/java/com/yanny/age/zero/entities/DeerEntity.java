package com.yanny.age.zero.entities;

import com.yanny.age.zero.subscribers.EntitySubscriber;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarrotBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DeerEntity extends AnimalEntity {

    public DeerEntity(EntityType<DeerEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(@Nonnull AgeableEntity ageable) {
        return EntitySubscriber.deer.create(world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new DeerEntity.AvoidEntityGoal<>(this, PlayerEntity.class, 12.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new DeerEntity.AvoidEntityGoal<>(this, WolfEntity.class, 10.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new DeerEntity.AvoidEntityGoal<>(this, MonsterEntity.class, 6.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(5, new DeerEntity.RaidFarmGoal(this));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2F);
    }

    static class AvoidEntityGoal<T extends LivingEntity> extends net.minecraft.entity.ai.goal.AvoidEntityGoal<T> {
        AvoidEntityGoal(DeerEntity deer, Class<T> p_i46403_2_, float p_i46403_3_, double p_i46403_4_, double p_i46403_6_) {
            super(deer, p_i46403_2_, p_i46403_3_, p_i46403_4_, p_i46403_6_);
        }
    }

    static class RaidFarmGoal extends MoveToBlockGoal {
        private final DeerEntity deer;
        private boolean wantsToRaid;
        private boolean canRaid;

        RaidFarmGoal(DeerEntity deer) {
            super(deer, 0.7F, 16);
            this.deer = deer;
        }

        @Override
        public boolean shouldExecute() {
            if (this.runDelay <= 0) {
                if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.deer.world, this.deer)) {
                    return false;
                }

                this.canRaid = false;
                this.wantsToRaid = true;
            }

            return super.shouldExecute();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.canRaid && super.shouldContinueExecuting();
        }

        @Override
        public void tick() {
            super.tick();
            this.deer.getLookController().setLookPosition((double)this.destinationBlock.getX() + 0.5D, this.destinationBlock.getY() + 1, (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.deer.getVerticalFaceSpeed());
            if (this.getIsAboveDestination()) {
                World world = this.deer.world;
                BlockPos blockpos = this.destinationBlock.up();
                BlockState blockstate = world.getBlockState(blockpos);
                Block block = blockstate.getBlock();
                if (this.canRaid && block instanceof CarrotBlock) {
                    Integer integer = blockstate.get(CarrotBlock.AGE);
                    if (integer == 0) {
                        world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
                        world.destroyBlock(blockpos, true);
                    } else {
                        world.setBlockState(blockpos, blockstate.with(CarrotBlock.AGE, integer - 1), 2);
                        world.playEvent(2001, blockpos, Block.getStateId(blockstate));
                    }
                }

                this.canRaid = false;
                this.runDelay = 10;
            }

        }

        @Override
        protected boolean shouldMoveTo(IWorldReader worldIn, @Nonnull BlockPos pos) {
            Block block = worldIn.getBlockState(pos).getBlock();
            if (block == Blocks.FARMLAND && this.wantsToRaid && !this.canRaid) {
                pos = pos.up();
                BlockState blockstate = worldIn.getBlockState(pos);
                block = blockstate.getBlock();
                if (block instanceof CarrotBlock && ((CarrotBlock)block).isMaxAge(blockstate)) {
                    this.canRaid = true;
                    return true;
                }
            }

            return false;
        }
    }
}
