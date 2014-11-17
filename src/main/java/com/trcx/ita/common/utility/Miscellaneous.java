package com.trcx.ita.common.utility;

import com.trcx.ita.common.material.BaseMaterialProperty;
import com.trcx.ita.common.material.BaseProperty;
import com.trcx.ita.common.material.ITAArmorProperties;
import com.trcx.ita.common.ITA;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JPiquette on 11/13/2014.
 */
public class Miscellaneous {

    public static double getPlayerWeight(EntityPlayer player) {
        double totalWeight = 0;
        for (int i = 0; i < 4; i++) {
            ItemStack is = player.getCurrentArmor(i);
            if (is != null) {
                if (is.getItem() == ITA.BasicBoots || is.getItem() == ITA.BasicLeggings ||
                        is.getItem() == ITA.BasicChestplate || is.getItem() == ITA.BasicHelmet) {
                    totalWeight += new ITAArmorProperties(is).Weight;
                }
            }
        }
        totalWeight /= 4;
        return totalWeight;
    }

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
            returnProp.Resistance += prop.Resistance;
            ColorHelper.addToColorMap(ColorMap, prop.ColorHex, 1D);
        }
        returnProp.ColorHex = ColorHelper.hexFromInt(ColorHelper.getAvgColor(ColorMap));
        if (props.size() != 0) {
            returnProp.Weight /= props.size();
            returnProp.Enchantability /= props.size();
            returnProp.Resistance /= props.size();
            returnProp.MaxDurability /= props.size();
        }
        return returnProp;
    }
}
