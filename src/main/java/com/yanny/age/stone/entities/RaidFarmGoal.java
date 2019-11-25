package com.yanny.age.stone.entities;

import net.minecraft.block.*;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;

class RaidFarmGoal<T extends AnimalEntity> extends MoveToBlockGoal {
    private final T entity;
    private final Class<? extends CropsBlock> cropsBlock;
    private final IntegerProperty ageProperty;
    private boolean wantsToRaid;
    private boolean canRaid;

    RaidFarmGoal(T entity, Class<? extends CropsBlock> cropsBlock, IntegerProperty ageProperty) {
        super(entity, 0.7F, 16);
        this.entity = entity;
        this.cropsBlock = cropsBlock;
        this.ageProperty = ageProperty;
    }

    @Override
    public boolean shouldExecute() {
        if (this.runDelay <= 0) {
            if (!ForgeEventFactory.getMobGriefingEvent(this.entity.world, this.entity)) {
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
        this.entity.getLookController().setLookPosition(
                (double)this.destinationBlock.getX() + 0.5D,
                this.destinationBlock.getY() + 1,
                (double)this.destinationBlock.getZ() + 0.5D,
                10.0F, (float)this.entity.getVerticalFaceSpeed());

        if (this.getIsAboveDestination()) {
            World world = this.entity.world;
            BlockPos blockpos = this.destinationBlock.up();
            BlockState blockstate = world.getBlockState(blockpos);
            Block block = blockstate.getBlock();

            if (this.canRaid && cropsBlock.isAssignableFrom(block.getClass())) {
                Integer integer;

                if (block instanceof BeetrootBlock) {
                    integer = blockstate.get(BeetrootBlock.BEETROOT_AGE);
                } else {
                    integer = blockstate.get(ageProperty);
                }

                if (integer == 0) {
                    world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
                    world.destroyBlock(blockpos, true);
                } else {
                    world.setBlockState(blockpos, blockstate.with(ageProperty, integer - 1), 2);
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

            if (cropsBlock.isAssignableFrom(block.getClass()) && cropsBlock.cast(block).isMaxAge(blockstate)) {
                this.canRaid = true;
                return true;
            }
        }

        return false;
    }
}
