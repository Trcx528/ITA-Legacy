package com.trcx.ita.common.properties;

import net.minecraft.item.ItemStack;

/**
 * Created by JPiquette on 11/11/2014.
 */
public class CompoundMaterialProperties extends BaseProperty {

    public CompoundMaterialProperties (ItemStack is){
        super(is);
    }

    public CompoundMaterialProperties () {}

    @Override
    public Boolean addS() {return true;}

    public CompoundMaterialProperties (BaseProperty bp){
        super(bp);
    }

}
