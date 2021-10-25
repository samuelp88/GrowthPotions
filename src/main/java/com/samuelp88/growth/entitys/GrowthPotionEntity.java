package com.samuelp88.growth.entitys;

import com.samuelp88.growth.Growth;
import com.samuelp88.growth.Items.GrowthPotionItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.stream.Stream;

public class GrowthPotionEntity extends PotionEntity {

    private Boolean HITED = false;
    public GrowthPotionEntity(World worldIn, LivingEntity livingEntityIn) {
        super(worldIn, livingEntityIn);
    }

    public GrowthPotionEntity(EntityType<GrowthPotionEntity> growthPotionEntityEntityType, World world) {
        super(growthPotionEntityEntityType, world);
    }

    protected boolean canGrowByMe(Block block) {
        return (block instanceof IGrowable) && !(block instanceof GrassBlock);
    };

    protected void applyGrow(Block block, BlockPos blockPos, BlockState blockState) {
        IGrowable growableBlock = (IGrowable) block;
        growableBlock.performBonemeal((ServerWorld) this.level, new Random(), blockPos, blockState);
    }

    @Override
    protected void onHit(RayTraceResult pResult) {
        if(!this.level.isClientSide) {
            AxisAlignedBB axisalignedbb = this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
            Stream<BlockPos> blockPosStream = BlockPos.betweenClosedStream(axisalignedbb);
            this.level.levelEvent(2007, this.blockPosition(), 3593824);
            blockPosStream.forEach((BlockPos blockPosition) -> {
                BlockState blockState = this.level.getBlockState(blockPosition);
                Block block = blockState.getBlock();
                if(this.canGrowByMe(block)) {
                    this.applyGrow(block, blockPosition, blockState);
                }

            });

        }
        this.remove();
    }
}
