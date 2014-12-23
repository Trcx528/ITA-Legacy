package com.trcx.ita.common.properties;

import com.trcx.ita.common.item.ItemBasicArmor;
import com.trcx.ita.common.ITA;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.Constants;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by JPiquette on 11/8/2014.
 */
public class ITAArmorProperties extends BaseProperty {
    public short ShieldDisplayValue = 0;
    public Map<String, Double> CraftedMaterials = new HashMap<String, Double>();
    public int Durability = 0;
    public short ArmorType = 0;
    public boolean Invisible = false;
    public double RemainingFuel = 0;
    public double MaxFuel = 0;
    public double FuelRegenRate = 1;
    public NBTTagList ench = null;

    private static String NBTSHIELDDISPLAYVALUE = "shields";
    private static String NBTBASEMATERIALS = "basematerials";
    private static String NBTINVISIBLE = "invisible";
    private static String NBTFLIGHTTIME = "remainingFlight";
    private static String NBTMAXFLIGHTTIME = "maxFlight";
    private static String NBTFLIGHTRECHARGERATE ="flightRecharge";

    public void addMaterial(BaseProperty material, double qty) {
        if (this.CraftedMaterials.containsKey(material.Name)) {
            if (material.addS()) {
                this.CraftedMaterials.put(material.Name + "s", this.CraftedMaterials.get(material.Name) + qty);
                this.CraftedMaterials.remove(material.Name);
            }  else {
                this.CraftedMaterials.put(material.Name, this.CraftedMaterials.get(material.Name) + qty);
            }
        } else if (this.CraftedMaterials.containsKey(material.Name + "s")) {
            this.CraftedMaterials.put(material.Name + "s", this.CraftedMaterials.get(material.Name + "s") + qty);
        } else {
            this.CraftedMaterials.put(material.Name, qty);
        }
    }

    public void applyTypeFactors() {
        double typeArmorFactor = 0;
        double typeDurabilityFactor = 0;
        switch (this.ArmorType) {
            case (0): //boots
                typeArmorFactor = 0.120;
                typeDurabilityFactor = 1.6;
                break;
            case (1): //leggings
                typeArmorFactor = 0.305;
                typeDurabilityFactor = 1.1;
                break;
            case (2): //chestplate
                typeArmorFactor = 0.420;
                typeDurabilityFactor = 1.0;
                break;
            case (3): //helmet
                typeArmorFactor = 0.155;
                typeDurabilityFactor = 1.1;
                break;
        }
        this.MaxDurability *= typeDurabilityFactor;
        this.ArmorFactor *= typeArmorFactor;
        this.ShieldDisplayValue = (short) Math.floor(this.ArmorFactor / 4);
    }


    @Override
    public NBTTagCompound getTagCompound() {
        NBTTagCompound nbt = super.getTagCompound();
        nbt.setShort(NBTSHIELDDISPLAYVALUE, this.ShieldDisplayValue);
        nbt.setBoolean(NBTINVISIBLE, this.Invisible);
        nbt.setDouble(NBTFLIGHTTIME, this.RemainingFuel);
        nbt.setDouble(NBTMAXFLIGHTTIME, this.MaxFuel);
        nbt.setDouble(NBTFLIGHTRECHARGERATE, this.FuelRegenRate);
        if (this.ench != null)
            nbt.setTag("ench", ench);
        NBTTagCompound baseMaterials = new NBTTagCompound();
        for (String key : this.CraftedMaterials.keySet()) {
            baseMaterials.setDouble(key, this.CraftedMaterials.get(key));
        }
        nbt.setTag(NBTBASEMATERIALS, baseMaterials);
        return nbt;
    }


    @Override
    public void getToolTip(List<String> dataList) {
        //todo Clean this up
        DecimalFormat df = new DecimalFormat("#");
        dataList.add(EnumChatFormatting.BLUE + "Crafted With:");
        for (String key : this.CraftedMaterials.keySet()) {
            dataList.add(df.format(this.CraftedMaterials.get(key)) + " " + key);
        }
        dataList.add(EnumChatFormatting.GREEN + "Provides " + (double) this.ShieldDisplayValue / 2 + " shields");
        dataList.add(EnumChatFormatting.AQUA + "Enchantability: " + this.Enchantability);
        dataList.add(EnumChatFormatting.DARK_PURPLE + "Hitpoints: " + (this.MaxDurability - this.Durability) + "/" + this.MaxDurability);
        df = new DecimalFormat("#.##");
        dataList.add(EnumChatFormatting.GRAY + "Weight: " + df.format(1 + this.Weight) + "" + EnumChatFormatting.RESET);
        if (this.Invisible)
            dataList.add(EnumChatFormatting.WHITE + "" + EnumChatFormatting.ITALIC + "Invisible");
        if (this.MaxFuel > 0)
            dataList.add(EnumChatFormatting.GRAY + "Fuel: " + df.format(this.RemainingFuel) + "/" + this.MaxFuel + EnumChatFormatting.RESET);
        for (String trait: this.Traits.keySet()){
            String ttt = ITA.getTrait(trait).getToolTip(this);
            if (ttt != null)
                dataList.add(ttt + EnumChatFormatting.RESET);
        }
    }

    public ITAArmorProperties(BaseProperty bp){
        super(bp);
    }

    public ITAArmorProperties(ItemStack is) {
        super(is);
        //if (is.getItem() instanceof ItemBasicArmor) { // redundant?
        this.ArmorType = (short) (((ItemBasicArmor) is.getItem()).armorType);
        if (is.hasTagCompound()) {
            NBTTagCompound nbt = is.stackTagCompound;
            if (nbt.hasKey("ench"))
                this.ench = nbt.getTagList("ench", Constants.NBT.TAG_COMPOUND);
            this.ShieldDisplayValue = nbt.getShort(NBTSHIELDDISPLAYVALUE);
            this.Invisible = nbt.getBoolean(NBTINVISIBLE);
            this.RemainingFuel = nbt.getDouble(NBTFLIGHTTIME);
            this.MaxFuel = nbt.getDouble(NBTMAXFLIGHTTIME);
            this.FuelRegenRate = nbt.getDouble(NBTFLIGHTRECHARGERATE);
            NBTTagCompound baseMaterials = nbt.getCompoundTag(NBTBASEMATERIALS);
            Set<String> keys = baseMaterials.func_150296_c();
            for (String key : keys) {
                this.CraftedMaterials.put(key, baseMaterials.getDouble(key));
            }
            this.Durability = is.getItemDamage();
        }
    }

}
