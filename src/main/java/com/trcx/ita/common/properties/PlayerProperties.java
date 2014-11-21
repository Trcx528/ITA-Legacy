package com.trcx.ita.common.properties;

import com.trcx.ita.common.ITA;
import com.trcx.ita.common.traits.BaseTrait;
import com.trcx.ita.common.traits.TraitNames;
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
    public boolean StepAssist = false;
    public double Resistance = 0D;

    private Map<ITAArmorProperties, ItemStack> armorMap = new HashMap<ITAArmorProperties, ItemStack>();
    public Map<BaseTrait, Double> Traits = new HashMap<BaseTrait, Double>();

    public PlayerProperties(EntityPlayer player){
        int resCount = 0;
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
                    if (props.Resistance != -1000D) {
                        this.Resistance += props.Resistance;
                        resCount ++;
                    }
                    for (String trait: props.Traits.keySet()){
                        addTrait(ITA.getTrait(trait), props.Traits.get(trait));
                    }
                    armorMap.put(new ITAArmorProperties(is), is);
                    if (props.Traits.containsKey(TraitNames.ABILITY_BASIC_STEPASSIST))
                        this.StepAssist = true;
                }
            }
        }
        this.Regen /= armorMap.size(); //the regen function will be called by each piece of armor
        this.Weight /= 4;
        this.Weight ++ ;
        this.Resistance /= resCount;
    }

    private void addTrait(BaseTrait Trait, Double Weight){
        if (this.Traits.containsKey(Trait)){
            this.Traits.put(Trait, this.Traits.get(Trait) + Weight);
        } else {
            this.Traits.put(Trait, Weight);
        }
    }

    public boolean consumeFuel(double amount){
        amount *= this.Resistance;
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
        regenRemaining += 1 - this.Resistance;
        if (this.Fuel <= 10 || slow)
            regenRemaining *= 0.05; // cooldown
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
