package com.trcx.ita.common.item;

import com.trcx.ita.common.recipes.ThrusterRSEngineRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by JPiquette on 11/12/2014.
 */
public class ItemThrusterRSEngine extends CompoundMaterial{
    public ItemThrusterRSEngine (){
        GameRegistry.addRecipe(new ThrusterRSEngineRecipe());
    }
}
