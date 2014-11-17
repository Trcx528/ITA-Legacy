package com.trcx.ita.common.material;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JPiquette on 11/4/2014.
 */
public class BaseMaterialProperty extends BaseProperty{

    public Map<String, Integer> Traits = new HashMap<String, Integer>();
    public String OreDictionaryName;

    public BaseMaterialProperty(String oreDictionaryName, double armorFactor, short enchantability, double weight, short MaxDurability, String Color, double Resistance){
        super(oreDictionaryName, armorFactor, enchantability, weight, MaxDurability, Color, Resistance);
        this.OreDictionaryName = oreDictionaryName;
        this.Name = "";
        for (String namepart: oreDictionaryName.substring(5).split("(?=\\p{Upper})")){
            if (this.Name == ""){
                this.Name = namepart;
            } else {
                this.Name = this.Name + " " + namepart;
            }
        }
        if (this.Name.charAt(0) == ' '){ // I can't figure out why this is needed but whatever works
            this.Name = this.Name.substring(1);
        }
    }
}
