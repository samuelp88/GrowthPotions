package com.samuelp88.growth.brewrecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.BrewingRecipe;

import javax.annotation.Nonnull;

public class GrowthBrewRecipe extends BrewingRecipe {
    public GrowthBrewRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
        super(input, ingredient, output);
    }

    @Override
    public boolean isInput(@Nonnull ItemStack stack)
    {
        ItemStack potion = PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.WATER);
        return ItemStack.tagMatches(stack, potion);
    }

}
