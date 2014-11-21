package com.trcx.ita.common.item;

import com.trcx.ita.common.properties.ITAArmorProperties;
import com.trcx.ita.common.traits.TraitNames;
import com.trcx.ita.common.utility.Miscellaneous;

/**
 * Created by Trcx on 11/21/2014.
 */
public class ItemRocketThruster extends CompoundMaterial {
    @Override
    public Boolean isValidForType(int ArmorType) {return ArmorType != Miscellaneous.ARMOR_TYPE_HELMET; }

    @Override
    public void addProperties(ITAArmorProperties props) {
        props.MaxFuel += 100;
        props.addTrait(TraitNames.ABILITY_BASIC_ROCKET, 1.0);
    }
}
