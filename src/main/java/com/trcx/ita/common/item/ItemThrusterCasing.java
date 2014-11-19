package com.trcx.ita.common.item;

import com.trcx.ita.common.properties.ITAArmorProperties;
import com.trcx.ita.common.recipes.ThrusterCasingRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by JPiquette on 11/12/2014.
 */
public class ItemThrusterCasing extends CompoundMaterial {
    public ItemThrusterCasing(){
        GameRegistry.addRecipe(new ThrusterCasingRecipe());
    }

    @Override
    public void addProperties(ITAArmorProperties props) {
        if (props.MaxFuel == 0)
            props.MaxFuel = 200D;
        props.MaxFuel += 100D;
    }
}
