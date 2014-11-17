package com.trcx.ita.common.item;

import com.trcx.ita.common.ITA;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by JPiquette on 11/10/2014.
 */
public class ItemAlloyBinder extends Item{
    // just a simple item for use in crafting
    public ItemAlloyBinder() {
        super();
        ItemStack AlloyBinderIS = new ItemStack(ITA.AlloyBinder);
        ItemStack Iron = new ItemStack(Items.iron_ingot);
        ItemStack Lapis = new ItemStack(Items.dye);
        Lapis.setItemDamage(4);
        ItemStack Glowstone = new ItemStack(Items.glowstone_dust);
        ItemStack Diamond = new ItemStack(Items.diamond);
        AlloyBinderIS.stackSize = 5;
        GameRegistry.addShapedRecipe(AlloyBinderIS, "ili", "gdg", "ili", 'i', Iron, 'l', Lapis, 'g', Glowstone, 'd', Diamond);
        GameRegistry.addShapedRecipe(AlloyBinderIS,"igi", "ldl", "igi", 'i', Iron, 'l', Lapis, 'g',Glowstone, 'd', Diamond);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }
}
