package com.samuelp88.growth.utils;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class GrowthPotionUtils {

    public static void GrowthPotionEffect(World level, Block block, BlockPos blockPos, BlockState blockState) {
        if((block instanceof IGrowable) && !(block instanceof GrassBlock)) {
            IGrowable growableBlock = (IGrowable) block;
            growableBlock.performBonemeal((ServerWorld) level, new Random(), blockPos, blockState);
        }
    }

    public static void StrongGrowthPotionEffect(World level, Block block, BlockPos blockPos, BlockState blockState) {
        if(((block instanceof CropsBlock) || block instanceof SaplingBlock)) {
            if(block instanceof CropsBlock) {
                CropsBlock cropBlock = (CropsBlock) block;
                level.setBlock(blockPos, cropBlock.getStateForAge(cropBlock.getMaxAge()), 2);
            }
            else {
                BlockState advancedBlockState = blockState;
                SaplingBlock sapplingBlock = (SaplingBlock) block;
                if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(level, new Random(), blockPos)) return;
                if(blockState.getValue(SaplingBlock.STAGE) == 0) {
                    level.setBlock(blockPos, blockState.cycle(SaplingBlock.STAGE), 4);
                    advancedBlockState = level.getBlockState(blockPos);
                }

                sapplingBlock.advanceTree((ServerWorld) level, blockPos, advancedBlockState, new Random());
            }
        }
    }
}
