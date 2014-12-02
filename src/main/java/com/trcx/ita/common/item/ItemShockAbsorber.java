package com.trcx.ita.common.item;

import com.trcx.ita.common.properties.ITAArmorProperties;
import com.trcx.ita.common.traits.TraitNames;
import com.trcx.ita.common.utility.Miscellaneous;

/**
 * Created by Trcx on 12/1/2014.
 */
public class ItemShockAbsorber extends CompoundMaterial {
    @Override
    public Boolean isValidForType(int ArmorType) {
        return ArmorType == Miscellaneous.ARMOR_TYPE_BOOTS;
    }

    @Override
    public void addProperties(ITAArmorProperties props) {
        props.MaxFuel += 100;
        props.addTrait(TraitNames.ABILITY_SHOCK_ABSORB, 1.0D);
    }
}
