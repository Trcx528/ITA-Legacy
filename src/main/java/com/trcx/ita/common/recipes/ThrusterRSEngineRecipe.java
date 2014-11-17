package com.trcx.ita.common.recipes;

import com.trcx.ita.common.item.ItemAlloy;
import com.trcx.ita.common.material.BaseMaterialProperty;
import com.trcx.ita.common.material.BaseProperty;
import com.trcx.ita.common.material.CompoundMaterialProperties;
import com.trcx.ita.common.ITA;
import com.trcx.ita.common.utility.ColorHelper;
import com.trcx.ita.common.utility.Miscellaneous;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JPiquette on 11/12/2014.
 */
public class ThrusterRSEngineRecipe  implements IRecipe {
    private ItemStack getOutput(InventoryCrafting inv, boolean craft){
        List<BaseProperty> BaseProps = new ArrayList<BaseProperty>();
        int pistonCount = 0;
        boolean RedStoneBlock = false;
        for (int i=0; i<9; i++){
            ItemStack stack = inv.getStackInSlot(i);
            if (stack == null){
                return null;
            } else if ((stack.getItem() == Item.getItemFromBlock(Blocks.piston) && (i == 3 || i == 5))){
                pistonCount ++;
            } else if ((stack.getItem() == Item.getItemFromBlock(Blocks.redstone_block)) && i==4) {
                RedStoneBlock = true;
            } else if (stack.getItem() instanceof ItemAlloy){
                BaseProps.add(new CompoundMaterialProperties(stack));
            } else {
                int[] ids = OreDictionary.getOreIDs(stack);
                boolean found = false;
                for (int id: ids){
                    String Name = OreDictionary.getOreName(id);
                    if (Name.startsWith("ingot")){
                        found = true;
                        BaseProps.add(ITA.getMaterialProperties(Name));
                        break;
                    }
                }
                if (!found)
                    return null;
            }
        }
        if (!(RedStoneBlock && (pistonCount == 2)))
            return null;

        ItemStack returnStack = new ItemStack(ITA.ThrusterRSEngine);
        CompoundMaterialProperties returnProps = new CompoundMaterialProperties(Miscellaneous.calculateBaseProps(BaseProps));
        returnProps.Name = "Redstone Engine";
        returnProps.ArmorFactor = 0D;
        returnProps.Enchantability = 0;
        if (craft)
            returnStack.stackSize = 1;

        returnStack.stackTagCompound = returnProps.getTagCompound();

        return returnStack.copy();
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
