package com.trcx.ita.common.item;

import com.trcx.ita.common.recipes.ThrusterRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by JPiquette on 11/12/2014.
 */
public class ItemThruster extends CompoundMaterial {
    public ItemThruster(){
        GameRegistry.addRecipe(new ThrusterRecipe());
    }
}
