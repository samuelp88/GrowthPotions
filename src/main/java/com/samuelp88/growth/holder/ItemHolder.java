package com.samuelp88.growth.holder;

import com.samuelp88.growth.Growth;
import com.samuelp88.growth.items.GrowthPotionItem;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Growth.MODID)
public class ItemHolder {

    public static void init() {
        return;
    }

    @ObjectHolder("growth_potion")
    public static GrowthPotionItem GROWTH_POTION_ITEM = null;

    @ObjectHolder("strong_growth_potion")
    public static GrowthPotionItem STRONG_GROWTH_POTION_ITEM = null;

}
