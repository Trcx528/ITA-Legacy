package com.trcx.ita.common.properties;

import com.trcx.ita.common.ITA;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JPiquette on 11/18/2014.
 */
public class PlayerProperties {

    //we can make these public because a new object is created each tick so they will always be up to date
    public double Weight = 0D;
    public double Fuel = 0D;
    public double MaxFuel = 0D;
    public double Regen = 0D;

    private Map<ITAArmorProperties, ItemStack> armorMap = new HashMap<ITAArmorProperties, ItemStack>();
    private EntityPlayer player;

    public PlayerProperties(EntityPlayer player){
        this.player = player;
        for (int i = 0; i < 4; i++) {
            ItemStack is = player.getCurrentArmor(i);
            if (is != null) {
                if (is.getItem() == ITA.BasicBoots || is.getItem() == ITA.BasicLeggings ||
                        is.getItem() == ITA.BasicChestplate || is.getItem() == ITA.BasicHelmet) {
                    ITAArmorProperties props = new ITAArmorProperties(is);
                    this.Weight += props.Weight;
                    this.Fuel += props.RemainingFuel;
                    this.Regen += props.FuelRegenRate;
                    this.MaxFuel += props.MaxFuel;
                    armorMap.put(new ITAArmorProperties(is), is);
                }
            }
        }
        this.Regen /= armorMap.size(); //the regen function will be called by each piece of armor
        this.Weight /= 4;
        this.Weight ++ ;
    }

    public boolean consumeFuel(double amount){
        for (ITAArmorProperties props: armorMap.keySet()){
            if (amount <= 0)
                break;
            if (props.RemainingFuel < amount){
                amount -= props.RemainingFuel;
                props.RemainingFuel = 0;
            } else {
                props.RemainingFuel -= amount;
                amount = 0;
            }
            armorMap.get(props).stackTagCompound = props.getTagCompound();
        }
        return amount <= 0;
    }


    public void regenFuel(boolean slow){
        double regenRemaining = this.Regen;
        if (this.Fuel <= 10 || slow)
            regenRemaining *= 0.1; // cooldown
        for (ITAArmorProperties props: armorMap.keySet()){
            if (regenRemaining <= 0)
                break;
            if(props.RemainingFuel < props.MaxFuel){
                if (props.RemainingFuel + regenRemaining <= props.MaxFuel){
                    props.RemainingFuel += regenRemaining;
                    regenRemaining = 0;
                } else {
                    double consumeAmt = props.MaxFuel - props.RemainingFuel;
                    regenRemaining -= consumeAmt;
                    props.RemainingFuel = props.MaxFuel;
                }
                armorMap.get(props).stackTagCompound = props.getTagCompound();
            }
        }

    }

}
