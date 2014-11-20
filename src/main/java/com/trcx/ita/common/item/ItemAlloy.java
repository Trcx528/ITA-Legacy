package com.trcx.ita.common.item;

import com.trcx.ita.common.recipes.AlloyRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by JPiquette on 11/12/2014.
 */
public class ItemAlloy extends CompoundMaterial {
    public ItemAlloy(){
        GameRegistry.addRecipe(new AlloyRecipe());
    }
}
