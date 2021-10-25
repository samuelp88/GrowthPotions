package com.samuelp88.growth.entitys;

import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class StrongGrowthPotionEntity extends GrowthPotionEntity{

    public StrongGrowthPotionEntity(World worldIn, LivingEntity livingEntityIn) {
        super(worldIn, livingEntityIn);
    }


    public StrongGrowthPotionEntity(EntityType<GrowthPotionEntity> growthPotionEntityEntityType, World world) {
        super(growthPotionEntityEntityType, world);
    }

    @Override
    protected void applyGrow(Block block, BlockPos blockPos, BlockState blockState) {
        if(block instanceof CropsBlock) {
            CropsBlock cropBlock = (CropsBlock) block;
            this.level.setBlock(blockPos, cropBlock.getStateForAge(cropBlock.getMaxAge()), 2);
        }
        else {
            BlockState advancedBlockState = blockState;
            SaplingBlock sapplingBlock = (SaplingBlock) block;
            if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(this.level, new Random(), blockPos)) return;
            LOGGER.info(blockState.getValue(SaplingBlock.STAGE));
            if(blockState.getValue(SaplingBlock.STAGE) == 0) {
                this.level.setBlock(blockPos, blockState.cycle(SaplingBlock.STAGE), 4);
                advancedBlockState = level.getBlockState(blockPos);
            }

            sapplingBlock.advanceTree((ServerWorld) this.level, blockPos, advancedBlockState, new Random());
        }
    }

    @Override
    protected boolean canGrowByMe(Block block) {
        return ((block instanceof CropsBlock) || block instanceof SaplingBlock) && !(block instanceof GrassBlock);
    }

}
