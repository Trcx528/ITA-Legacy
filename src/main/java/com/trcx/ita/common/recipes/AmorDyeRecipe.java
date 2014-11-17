package com.trcx.ita.common.recipes;

import com.trcx.ita.common.material.ITAArmorProperties;
import com.trcx.ita.common.ITA;
import com.trcx.ita.common.utility.ColorHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by JPiquette on 11/12/2014.
 */
public class AmorDyeRecipe implements IRecipe {

    private static String[] DyeColors = new String[16];

    public void setupDyes(){
        DyeColors[0] = "#191919";
        DyeColors[1] = "#993333";
        DyeColors[2] = "#667F33";
        DyeColors[3] = "#664C33";
        DyeColors[4] = "#334CB2";
        DyeColors[5] = "#7F3FB2";
        DyeColors[6] = "#4C7F99";
        DyeColors[7] = "#999999";
        DyeColors[8] = "#4C4C4C";
        DyeColors[9] = "#F27FA5";
        DyeColors[10] = "#7FCC19";
        DyeColors[11] = "#E5E533";
        DyeColors[12] = "#6699D8";
        DyeColors[13] = "#B24CD8";
        DyeColors[14] = "#D87F33";
        DyeColors[15] = "#FFFFFF";
    }

    private ItemStack getOutput(InventoryCrafting inv, boolean shouldCreate){
        setupDyes();
        int GlassCount = 0;
        ItemStack stack = null;
        Map<String, Double> ColorMap = new HashMap<String, Double>();
        for (int i=0; i<9; i++){
            ItemStack invStack = inv.getStackInSlot(i);
            if (invStack != null) {
                if (invStack.getItem() == ITA.BasicBoots ||
                        invStack.getItem() == ITA.BasicLeggings ||
                        invStack.getItem() == ITA.BasicChestplate ||
                        invStack.getItem() == ITA.BasicHelmet) {
                    if (stack == null) {
                        stack = invStack;
                    } else {
                        return null;
                    }
                } else if (invStack.getItem() == Items.dye) {
                    String color = DyeColors[invStack.getItemDamage()];
                    if (ColorMap.containsKey(color)) {
                        ColorMap.put(color, ColorMap.get(color) + 1);
                    } else {
                        ColorMap.put(color, 1D);
                    }
                }else if (invStack.getItem() == Item.getItemFromBlock(Blocks.glass)){
                    GlassCount ++;
                } else {
                    return null;
                }
            }
        }
        if (ColorMap.size() == 0 && !(GlassCount == 8) )
            return null;
        if (stack != null){
            ITAArmorProperties props = new ITAArmorProperties(stack.copy());
            if (GlassCount == 8){
                props.Invisible = true;
            } else {
                props.ColorHex = ColorHelper.hexFromInt(ColorHelper.getAvgColor(ColorMap));
                props.Invisible = false;
            }
            ItemStack returnStack = null;
            switch (props.ArmorType){
                case 3:
                    returnStack = new ItemStack(ITA.BasicBoots);
                    break;
                case 2:
                    returnStack = new ItemStack(ITA.BasicLeggings);
                    break;
                case 1:
                    returnStack = new ItemStack(ITA.BasicChestplate);
                    break;
                case 0:
                    returnStack = new ItemStack(ITA.BasicHelmet);
                    break;
            }
            returnStack.stackTagCompound = props.getTagCompound();
            returnStack.setItemDamage(stack.getItemDamage());
            if (shouldCreate)
                returnStack.stackSize = 1;
            return returnStack.copy();
        }
        return null;
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
