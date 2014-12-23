package com.trcx.ita.common.utility;

import com.trcx.ita.common.properties.BaseProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JPiquette on 11/13/2014.
 */
public class Miscellaneous {

    public static final int ARMOR_TYPE_HELMET = 3;
    public static final int ARMOR_TYPE_CHESTPLATE = 2;
    public static final int ARMOR_TYPE_LEGGIGNGS = 1;
    public static final int ARMOR_TYPE_BOOTS = 0;

    public static BaseProperty calculateBaseProps(List<BaseProperty> props){
        Map<String, Double> ColorMap = new HashMap<String, Double>();
        BaseProperty returnProp = new BaseProperty();
        returnProp.zeroAllValues();
        for (BaseProperty prop: props){
            for(String traitName: prop.Traits.keySet()){
                returnProp.addTrait(traitName, prop.Traits.get(traitName));
            }
            returnProp.ArmorFactor += prop.ArmorFactor;
            returnProp.MaxDurability += prop.MaxDurability;
            returnProp.Enchantability += prop.Enchantability;
            returnProp.Weight += prop.Weight;
            ColorHelper.addToColorMap(ColorMap, prop.ColorHex, 1D);
        }
        returnProp.ColorHex = ColorHelper.hexFromInt(ColorHelper.getAvgColor(ColorMap));
        if (props.size() != 0) {
            returnProp.Weight /= props.size();
            returnProp.Enchantability /= props.size();
            returnProp.MaxDurability /= props.size();
        }
        return returnProp;
    }
}
