package com.trcx.ita.common.item;

import com.trcx.ita.common.properties.ITAArmorProperties;
import com.trcx.ita.common.utility.Miscellaneous;


/**
 * Created by JPiquette on 11/19/2014.
 */
public class ItemStepAssist extends CompoundMaterial {

    @Override
    public void addProperties(ITAArmorProperties props) {
        props.addTrait("stepAssist", 1);
    }

    @Override
    public Boolean isValidForType(int ArmorType) {return ArmorType == Miscellaneous.ARMOR_TYPE_BOOTS; }
}
