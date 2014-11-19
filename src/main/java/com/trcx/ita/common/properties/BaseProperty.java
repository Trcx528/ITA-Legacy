package com.trcx.ita.common.properties;

import com.trcx.ita.common.ITA;
import com.trcx.ita.common.utility.ColorHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by JPiquette on 11/4/2014.
 */
public class BaseProperty {


    public Map<String, Double> Traits = new HashMap<String, Double>();
    public double ArmorFactor = 0D; // percentage to protect against per ingot used 24 ingots per full set
    public short Enchantability = 0;
    public double Weight = 0D;
    public short MaxDurability = 0;
    public String ColorHex = "#00AA00";
    public double Resistance = 1.0;
    public String Name = "New Property";

    private static String NBTWEIGHT = "Weight";
    private static String NBTENCHANTABILITY = "Enchantability";
    private static String NBTTRAITS = "Traits";
    private static String NBTARMORFACTOR = "ArmorFactor";
    private static String NBTCOLOR = "color";
    private static String NBTDURABILITY = "MaxDurability";
    private static String NBTRESISTANCE = "Resistance";
    private static String NBTNAME = "name";


    public void zeroAllValues(){
        this.ArmorFactor = 0D;
        this.Enchantability = 0;
        this.Weight = 0D;
        this.MaxDurability = 0;
        this.ColorHex = "#00AA00";
        this.Resistance = 0D;
        this.Name = "Zeroed";
    }


    public BaseProperty(BaseProperty bp){
        this.ArmorFactor = bp.ArmorFactor;
        this.Enchantability = bp.Enchantability;
        this.Weight = bp.Weight;
        this.MaxDurability = bp.MaxDurability;
        this.ColorHex = bp.ColorHex;
        this.Resistance = bp.Resistance;
        this.Name = bp.Name;
        this.Traits = bp.Traits;
    }


    public NBTTagCompound getTagCompound(){
        NBTTagCompound returnNBT = new NBTTagCompound();
        returnNBT.setShort(NBTENCHANTABILITY, this.Enchantability);
        returnNBT.setDouble(NBTWEIGHT, this.Weight);
        returnNBT.setDouble(NBTARMORFACTOR, this.ArmorFactor);
        returnNBT.setInteger(NBTCOLOR, ColorHelper.intFromHex(this.ColorHex));
        returnNBT.setShort(NBTDURABILITY, this.MaxDurability);
        returnNBT.setDouble(NBTRESISTANCE, this.Resistance);
        returnNBT.setString(NBTNAME, this.Name);
        NBTTagCompound traits = new NBTTagCompound();
        for (String trait: this.Traits.keySet()){
            traits.setDouble(trait, this.Traits.get(trait));
        }
        returnNBT.setTag(NBTTRAITS, traits);
        return returnNBT;
    }

    public BaseProperty(){}

    public BaseProperty(ItemStack is){
        if (is.hasTagCompound()){
            NBTTagCompound nbt = is.stackTagCompound;
            this.Enchantability = nbt.getShort(NBTENCHANTABILITY);
            this.Weight = nbt.getDouble(NBTWEIGHT);
            this.ArmorFactor = nbt.getDouble(NBTARMORFACTOR);
            this.ColorHex = ColorHelper.hexFromInt(nbt.getInteger(NBTCOLOR));
            this.MaxDurability = nbt.getShort(NBTDURABILITY);
            this.Resistance = nbt.getDouble(NBTRESISTANCE);
            this.Name = nbt.getString(NBTNAME);
            NBTTagCompound traits = nbt.getCompoundTag(NBTTRAITS);
            Set<String> keys = traits.func_150296_c();
            for (String key: keys){
                this.Traits.put(key, traits.getDouble(key));
            }
        }
    }

    public void getToolTip(List<String> dataList){
        DecimalFormat df = new DecimalFormat("#.##");
        if (this.ArmorFactor > 0D)
            dataList.add(EnumChatFormatting.GREEN + "Armor Factor: " + df.format(this.ArmorFactor));
        if (this.Enchantability > 0)
            dataList.add(EnumChatFormatting.AQUA + "Enchantability: " + this.Enchantability);
        if (this.MaxDurability > 0)
            dataList.add(EnumChatFormatting.DARK_PURPLE + "Durability: " + this.MaxDurability);
        dataList.add(EnumChatFormatting.GRAY + "Weight: " + df.format(1 + this.Weight));
        if (this.Resistance > 0D)
            dataList.add(EnumChatFormatting.RED + "Resistance: " + df.format(this.Resistance));
        for (String trait: this.Traits.keySet()){
            String ttt = ITA.getTrait(trait).getToolTip(null);
            if (ttt != null)
                dataList.add(ttt + EnumChatFormatting.RESET);
        }
    }

    public void addTrait(String traitName,double weight){
        if (this.Traits.containsKey(traitName)){
            this.Traits.put(traitName, weight + this.Traits.get(traitName));
        } else {
            this.Traits.put(traitName, weight);
        }
    }

    public BaseProperty(String Name, double armorFactor, short enchantability, double weight, short MaxDurability, String ColorHex, double Resistance) {
        this.Name = Name;
        this.ColorHex = ColorHex;
        this.ArmorFactor = armorFactor;
        this.Enchantability = enchantability;
        this.Weight = weight;
        this.MaxDurability = MaxDurability;
        this.Resistance = Resistance;
    }
}
