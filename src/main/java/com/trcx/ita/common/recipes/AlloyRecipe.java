package com.trcx.ita.common.recipes;

import com.trcx.ita.common.item.ItemAlloyBinder;
import com.trcx.ita.common.properties.BaseProperty;
import com.trcx.ita.common.properties.CompoundMaterialProperties;
import com.trcx.ita.common.ITA;
import com.trcx.ita.common.utility.Miscellaneous;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JPiquette on 11/10/2014.
 */
public class AlloyRecipe implements IRecipe {

    //TODO make this a configurable value
    private final static double alloyModifier = 0.375; // average of 4 ingots * 1.5


    private ItemStack getOutput(InventoryCrafting inv, boolean craft){
        List<BaseProperty> materialProps = new ArrayList<BaseProperty>();

        for(int i=0; i<9; i++){
            ItemStack stack = inv.getStackInSlot(i);
            if (stack == null)
                return null;
            if (((i==0)||(i==2)||i==4||i==6||i==8) && (stack.getItem() instanceof ItemAlloyBinder)){
                //NOOP
            } else if((i==1)||(i==3)||(i==5)||(i==7)){
                int[] ids = OreDictionary.getOreIDs(inv.getStackInSlot(i));
                for (int id: ids){
                    String oreDictName = OreDictionary.getOreName(id);
                    if (oreDictName.startsWith("ingot")) {
                        materialProps.add(ITA.getMaterialProperties(OreDictionary.getOreName(id)));
                    }
                }
            } else {
                return null;
            }
        }


        ItemStack returnStack = new ItemStack(ITA.Alloy);
        CompoundMaterialProperties props = new CompoundMaterialProperties(Miscellaneous.calculateBaseProps(materialProps));
        props.Name = "Compound Alloy";

        props.ArmorFactor *= alloyModifier;
        props.MaxDurability *= alloyModifier;

        returnStack.stackTagCompound = props.getTagCompound();
        if (craft)
            returnStack.stackSize = 1;

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
