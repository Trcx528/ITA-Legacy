package com.trcx.ita.common.recipes;

import com.trcx.ita.common.material.BaseProperty;
import com.trcx.ita.common.material.CompoundMaterialProperties;
import com.trcx.ita.common.ITA;
import com.trcx.ita.common.utility.Miscellaneous;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JPiquette on 11/12/2014.
 */
public class ThrusterRecipe implements IRecipe {

    private ItemStack getOutput(InventoryCrafting inv, boolean craft){
        List<BaseProperty> props = new ArrayList<BaseProperty>();
        for (int slotID = 0; slotID < 9; slotID++){
            ItemStack stack = inv.getStackInSlot(slotID);
            if (stack != null) {
                switch (slotID) {
                    case 0:
                        return null;
                    case 2:
                        return null;
                    case 1:
                        if (stack.getItem() == ITA.ThrusterRSEngine) {
                            props.add(new CompoundMaterialProperties(stack));
                        } else {
                            return null;
                        }
                        break;
                    case 3:
                        if (stack.getItem() == ITA.ThrusterCasing) {
                            props.add(new CompoundMaterialProperties(stack));
                        } else {
                            return null;
                        }
                        break;
                    case 4:
                        if (stack.getItem() != Items.iron_ingot)
                            return null;
                        break;
                    case 5:
                        if (stack.getItem() == ITA.ThrusterCasing) {
                            props.add(new CompoundMaterialProperties(stack));
                        } else {
                            return null;
                        }
                        break;
                    case 6:
                        if (stack.getItem() == ITA.ThrusterCasing) {
                            props.add(new CompoundMaterialProperties(stack));
                        } else {
                            return null;
                        }
                        break;
                    case 7:
                        if (stack.getItem() != Items.iron_ingot)
                            return null;
                        break;
                    case 8:
                        if (stack.getItem() == ITA.ThrusterCasing) {
                            props.add(new CompoundMaterialProperties(stack));
                        } else {
                            return null;
                        }
                        break;
                }
            } else if (slotID == 0){
                //NOOP
            } else if (slotID == 2){
                //NOOP
            } else {
                return null;
            }
            //IDK why the code below didn't work....it was so much nicer...
            /*if (stack == null && !(slotID == 0 || slotID == 2)){
                return null;
            } else if ((slotID == 1) && stack.getItem() == ITAHandler.ThrusterRSEngine){
                props.add(new CompoundMaterialProperties(stack));
            } else if ((slotID == 3 || slotID == 5 || slotID == 6 || slotID == 8) && stack.getItem() == ITAHandler.ThrusterCasing){
                props.add(new CompoundMaterialProperties(stack));
            } else if ((slotID == 4 || slotID == 7) && stack.getItem() == Items.iron_ingot) {
                // NOOP
            } else {
                return null;
            }*/
        }

        ItemStack returnStack = new ItemStack(ITA.Thruster);
        CompoundMaterialProperties returnProp = new CompoundMaterialProperties(Miscellaneous.calculateBaseProps(props));
        returnProp.Name = "Thruster";
        returnProp.Enchantability = 0;
        returnProp.ArmorFactor = 0D;
        returnProp.addTrait("basicFlight", 0.5);
        returnStack.stackTagCompound = returnProp.getTagCompound();
        if (craft)
            returnStack.stackSize = 1 ;

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
