package com.trcx.ita.common.item;

import com.trcx.ita.common.ITA;
import com.trcx.ita.common.properties.ITAArmorProperties;
import com.trcx.ita.common.recipes.CapacitorRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by JPiquette on 11/19/2014.
 */
public class ItemCapacitor extends CompoundMaterial{

    public ItemCapacitor(){
        GameRegistry.addRecipe(new CapacitorRecipe());
    }

    @Override
    public void addProperties(ITAArmorProperties props) {
        props.MaxFuel += 400D;
    }
}
