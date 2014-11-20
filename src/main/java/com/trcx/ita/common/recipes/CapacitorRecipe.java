package com.trcx.ita.common.recipes;

import com.trcx.ita.common.ITA;
import com.trcx.ita.common.item.CompoundMaterial;
import com.trcx.ita.common.properties.BaseProperty;
import com.trcx.ita.common.properties.CompoundMaterialProperties;
import com.trcx.ita.common.utility.Miscellaneous;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JPiquette on 11/19/2014.
 */
public class CapacitorRecipe implements IRecipe {

    private ItemStack getOutput(InventoryCrafting inv, boolean craft){
        List<BaseProperty> BaseProps = new ArrayList<BaseProperty>();
        for(int i=0; i<9; i++){
            ItemStack is=inv.getStackInSlot(i);
            if (is == null)
                return null;
            if (is.getItem() == ITA.ThrusterCasing){
                BaseProps.add(new BaseProperty(is));
            } else if (is.getItem() != Item.getItemFromBlock(Blocks.redstone_block)){
                return null;
            }
        }

        ItemStack retStack = new ItemStack(ITA.BasicCapacitor);
        CompoundMaterialProperties props = new CompoundMaterialProperties(Miscellaneous.calculateBaseProps(BaseProps));
        props.Name = "Basic Capacitor";
        retStack.stackTagCompound = props.getTagCompound();
        if (craft)
            retStack.stackSize = 1;
        return retStack;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        return getOutput(inv, false) != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return getOutput(inv, true);
    }

    @Override
    public int getRecipeSize() {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
