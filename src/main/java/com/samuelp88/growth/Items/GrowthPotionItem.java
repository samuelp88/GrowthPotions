package com.samuelp88.growth.Items;

import com.samuelp88.growth.entitys.GrowthPotionEntity;
import com.samuelp88.growth.handlers.RegistryHandler;
import com.samuelp88.growth.holder.ItemHolder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class GrowthPotionItem extends ThrowablePotionItem {

    public static String registryName = "growth_potion";

    public GrowthPotionItem(Properties properties, String name) {
        super(properties);
        this.setRegistryName(name);
    }


    protected GrowthPotionEntity createEntityInstance(World worldIn, LivingEntity entityIn) {
        return new GrowthPotionEntity(worldIn, entityIn);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!worldIn.isClientSide) {
            worldIn.playSound((PlayerEntity)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            GrowthPotionEntity potionEntity = this.createEntityInstance(worldIn, playerIn);
            potionEntity.setItem(itemstack);
            potionEntity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, -20.0F, 0.5F, 1.0F);
            worldIn.addFreshEntity(potionEntity);
        }

        playerIn.awardStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.instabuild) {
            itemstack.shrink(1);
        }

        return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide);
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            items.add(new ItemStack(this));
        }
    }
}
