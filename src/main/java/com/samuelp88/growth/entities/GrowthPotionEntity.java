package com.samuelp88.growth.entities;

import com.samuelp88.growth.holder.ItemHolder;
import com.samuelp88.growth.utils.GrowthPotionEffects;
import com.samuelp88.growth.utils.GrowthPotionUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.stream.Stream;

public class GrowthPotionEntity extends PotionEntity {

    private GrowthPotionEffects growthEffect;

    public GrowthPotionEntity(World worldIn, LivingEntity livingEntityIn) {
        super(worldIn, livingEntityIn);
    }

    public GrowthPotionEntity(EntityType<GrowthPotionEntity> growthPotionEntityEntityType, World world) {
        super(growthPotionEntityEntityType, world);
    }

    protected void getEffectByItem() {
        Item potionItem = this.getItem().getItem();
        if(potionItem == ItemHolder.GROWTH_POTION_ITEM) {
            this.growthEffect = GrowthPotionEffects.GrowthPotion;
        }
        else if(potionItem == ItemHolder.STRONG_GROWTH_POTION_ITEM) {
            this.growthEffect = GrowthPotionEffects.StrongGrowthPotion;
        }
    }

    protected void executePotionEffect(Block block, BlockPos blockPos, BlockState blockState) {
        if(growthEffect == null) return;
        switch (growthEffect) {
            case GrowthPotion:
                GrowthPotionUtils.GrowthPotionEffect(level, block, blockPos, blockState);
                break;
            case StrongGrowthPotion:
                GrowthPotionUtils.StrongGrowthPotionEffect(level, block, blockPos, blockState);
                break;
        }
    }

    @Override
    protected void onHit(RayTraceResult pResult) {
        if(!this.level.isClientSide) {
            getEffectByItem();
            AxisAlignedBB axisalignedbb = this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
            Stream<BlockPos> blockPosStream = BlockPos.betweenClosedStream(axisalignedbb);
            this.level.levelEvent(2007, this.blockPosition(), 3593824);
            blockPosStream.forEach((BlockPos blockPosition) -> {
                BlockState blockState = this.level.getBlockState(blockPosition);
                Block block = blockState.getBlock();
                //Executes potion effect
                executePotionEffect(block, blockPosition, blockState);
            });

        }
        this.remove();
    }
}
