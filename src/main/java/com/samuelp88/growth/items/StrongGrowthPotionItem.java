package com.samuelp88.growth.items;

import com.samuelp88.growth.entities.GrowthPotionEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class StrongGrowthPotionItem extends GrowthPotionItem {

    public static String registryName = "strong_growth_potion";

    public StrongGrowthPotionItem(Properties properties, String name) {
        super(properties, name);
    }

    @Override
    protected GrowthPotionEntity createEntityInstance(World worldIn, LivingEntity entityIn) {
        return new GrowthPotionEntity(worldIn, entityIn);
    }

}
