package com.trcx.ita.common.item;

import com.trcx.ita.common.properties.ITAArmorProperties;
import com.trcx.ita.common.utility.Miscellaneous;

/**
 * Created by JPiquette on 11/20/2014.
 */
public class ItemSprintBooster extends CompoundMaterial{

    @Override
    public Boolean isValidForType(int ArmorType) {return ArmorType != Miscellaneous.ARMOR_TYPE_HELMET;}

    @Override
    public void addProperties(ITAArmorProperties props) {
        props.addTrait("sprintAcceleration", 1.0D);
    }
}
