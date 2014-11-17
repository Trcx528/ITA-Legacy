package com.trcx.ita.common.item;

import com.trcx.ita.common.recipes.ThrusterCasingRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by JPiquette on 11/12/2014.
 */
public class ItemThrusterCasing extends CompoundMaterial {
    public ItemThrusterCasing(){
        GameRegistry.addRecipe(new ThrusterCasingRecipe());
    }
}
