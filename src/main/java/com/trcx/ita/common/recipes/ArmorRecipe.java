package com.trcx.ita.common.recipes;

import com.trcx.ita.common.item.CompoundMaterial;
import com.trcx.ita.common.item.ItemThruster;
import com.trcx.ita.common.properties.*;
import com.trcx.ita.common.ITA;
import com.trcx.ita.common.traits.BasicFlightTrait;
import com.trcx.ita.common.utility.Miscellaneous;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JPiquette on 11/4/2014.
 */
public class ArmorRecipe implements IRecipe {

    private ItemStack getOutput(InventoryCrafting inv, boolean shouldCreate){
        int[] nullSlots = new int[9];
        //Map<String, Double> materials = new HashMap<String, Double>();
        List<CompoundMaterial> CompoundMaterials = new ArrayList<CompoundMaterial>();
        List<BaseProperty> BaseProps = new ArrayList<BaseProperty>();
        List<String> MaterialList = new ArrayList<String>();

        double Resistance = 0D;
        int ResistanceCount = 0;

        for (int i=0; i<9; i++) {
            if (inv.getStackInSlot(i) == null) {
                nullSlots[i] = -1;
            } else {
                if (inv.getStackInSlot(i).getItem() instanceof CompoundMaterial) {
                    nullSlots[i] = 1;
                    CompoundMaterialProperties cmp = new CompoundMaterialProperties(inv.getStackInSlot(i));
                    CompoundMaterial cm = (CompoundMaterial) inv.getStackInSlot(i).getItem();
                    CompoundMaterials.add(cm);
                    BaseProps.add(cmp);
                    MaterialList.add(cmp.Name);
                    if (inv.getStackInSlot(i).getItem() instanceof ItemThruster){
                        Resistance += cmp.Resistance;
                        ResistanceCount ++;
                    }
                } else {
                    int[] ids = OreDictionary.getOreIDs(inv.getStackInSlot(i));
                    for (int id : ids) {
                        String oreDictName = OreDictionary.getOreName(id);
                        if (oreDictName.startsWith("ingot")) {
                            nullSlots[i] = 1;
                            BaseProps.add(ITA.getMaterialProperties(oreDictName));
                            MaterialList.add(ITA.getMaterialProperties(oreDictName).Name);
                            break;
                        }
                    }
                }
            }
        }

        //Make Sure that we're not overriding another recipe
        /*List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
        for (IRecipe recipe: recipeList){
            if(!(recipe instanceof BasicRecipe)) {
                if(recipe.getCraftingResult(inv) != null){
                    return null;
                }
            }
        }*/

        short armorType = getTypeFromSlots(nullSlots);
        ItemStack returnStack = new ItemStack(Item.getItemById(0));
        ITAArmorProperties ap =  new ITAArmorProperties(Miscellaneous.calculateBaseProps(BaseProps));
        for (CompoundMaterial cm: CompoundMaterials){
            if (!cm.isValidForType(armorType))
                return null;
            cm.addProperties(ap);
        }
        ap.ArmorType = armorType;
        switch (armorType) {
            case -1:
                return null;
            case Miscellaneous.ARMOR_TYPE_HELMET:
                returnStack = new ItemStack(ITA.BasicHelmet);
                break;
            case Miscellaneous.ARMOR_TYPE_CHESTPLATE:
                returnStack = new ItemStack(ITA.BasicChestplate);
                break;
            case Miscellaneous.ARMOR_TYPE_LEGGIGNGS:
                returnStack = new ItemStack(ITA.BasicLeggings);
                break;
            case Miscellaneous.ARMOR_TYPE_BOOTS:
                returnStack = new ItemStack(ITA.BasicBoots);
                break;
        }
        //the helper function averaged durability for use, undo that
        ap.MaxDurability *= BaseProps.size();
        ap.applyTypeFactors();
        ap.Resistance = -1000D;
        if (ResistanceCount != 0)
            ap.Resistance = Resistance/ResistanceCount;
        returnStack.getItem().setMaxDamage(ap.MaxDurability);
        if (ap.Traits.containsKey("basicFlight"))
            ap.RemainingFuel = BasicFlightTrait.MAX_FLIGHTTIME;

        for (String material: MaterialList){
            ap.addMaterial(material, 1D);
        }


        returnStack.stackTagCompound = ap.getTagCompound();
        if (shouldCreate)
            returnStack.stackSize = 1;

        return returnStack.copy();
    }

    private short getTypeFromSlots(int[] slots){
        if ((slots[0] == 1 && slots[1] == 1 && slots[2] == 1 && slots[3] == 1 && slots[4] == -1 && slots[5] == 1 && slots[6] == -1 && slots[7] == -1 && slots[8] == -1 ) ||
                (slots[0] == -1 && slots[1] == -1 && slots[2] == -1 && slots[3] == 1 && slots[4] == 1 && slots[5] == 1 && slots[6] == 1 && slots[7] == -1 && slots[8] == 1 )){
            return 3;
        } else if (slots[0] == 1 && slots[1] == -1 && slots[2] == 1 && slots[3] == 1 && slots[4] == 1 && slots[5] == 1 && slots[6] == 1 && slots[7] == 1 && slots[8] == 1 ){
            return 2;
        } else if (slots[0] == 1 && slots[1] == 1 && slots[2] == 1 && slots[3] == 1 && slots[4] == -1 && slots[5] == 1 && slots[6] == 1 && slots[7] == -1 && slots[8] == 1 ){
            return 1;
        } else if ((slots[0] == 1 && slots[1] == -1 && slots[2] == 1 && slots[3] == 1 && slots[4] == -1 && slots[5] == 1 && slots[6] == -1 && slots[7] == -1 && slots[8] == -1) ||
                (slots[0] == -1 && slots[1] == -1 && slots[2] == -1 && slots[3] == 1 && slots[4] == -1 && slots[5] == 1 && slots[6] == 1 && slots[7] == -1 && slots[8] == 1)){
            return 0;
        }
        return -1;
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
