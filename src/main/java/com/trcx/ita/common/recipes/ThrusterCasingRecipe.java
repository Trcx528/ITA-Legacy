package com.trcx.ita.common.recipes;

import com.trcx.ita.common.material.BaseMaterialProperty;
import com.trcx.ita.common.item.CompoundMaterial;
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

import java.util.*;
import java.util.List;

/**
 * Created by JPiquette on 11/12/2014.
 */
public class ThrusterCasingRecipe implements IRecipe{
    private ItemStack getOutput(InventoryCrafting inv, boolean craft) {
        List<BaseProperty> BaseProps = new ArrayList<BaseProperty>();
        //List<CompoundMaterialProperties> CompoundProps = new ArrayList<CompoundMaterialProperties>();
        int RSCount = 0;

        for (int i = 0; i < 9; i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack == null) {
                return null;
            } else if (stack.getItem() == Item.getItemFromBlock(Blocks.redstone_block) && (i == 1 || i == 4 || i == 7)) {
                RSCount++;
            } else if (stack.getItem() instanceof CompoundMaterial) {
                BaseProps.add(new CompoundMaterialProperties(stack));
            } else if (OreDictionary.getOreIDs(stack).length > 0) {
                int[] ids = OreDictionary.getOreIDs(stack);
                boolean found = false;
                for (int id : ids) {
                    if (OreDictionary.getOreName(id).startsWith("ingot")) {
                        BaseProps.add(ITA.getMaterialProperties(OreDictionary.getOreName(id)));
                        found = true;
                        break;
                    }
                }
                if (!found)
                    return null;
            } else {
                return null;
            }
        }
        if (RSCount != 3){
            return null;
        }

        ItemStack returnStack = new ItemStack(ITA.ThrusterCasing);
        CompoundMaterialProperties returnProps = new CompoundMaterialProperties(Miscellaneous.calculateBaseProps(BaseProps));
        returnProps.Enchantability = 0;
        returnProps.ArmorFactor = 0D;
        returnProps.Name = "Thruster Casing";
        returnStack.stackTagCompound = returnProps.getTagCompound();

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
