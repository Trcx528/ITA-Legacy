package com.trcx.ita.common.item;

import com.trcx.ita.common.properties.ITAArmorProperties;
import com.trcx.ita.common.recipes.ThrusterRecipe;
import com.trcx.ita.common.utility.Miscellaneous;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by JPiquette on 11/12/2014.
 */
public class ItemThruster extends CompoundMaterial {
    public ItemThruster(){
        GameRegistry.addRecipe(new ThrusterRecipe());
    }

    @Override
    public Boolean isValidForType(int ArmorType) {
        return ArmorType != Miscellaneous.ARMOR_TYPE_HELMET;
    }

    @Override
    public void addProperties(ITAArmorProperties props) {
        props.MaxFuel += 100;
    }
}
