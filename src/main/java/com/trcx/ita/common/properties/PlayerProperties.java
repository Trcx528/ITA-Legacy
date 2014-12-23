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
    public EntityPlayer player;

    private Map<ITAArmorProperties, ItemStack> armorMap = new HashMap<ITAArmorProperties, ItemStack>();
    public Map<BaseTrait, Double> Traits = new HashMap<BaseTrait, Double>();

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
                    for (String trait: props.Traits.keySet()){
                        addTrait(ITA.getTrait(trait), props.Traits.get(trait));
                    }
                    armorMap.put(new ITAArmorProperties(is), is);
                    if (props.Traits.containsKey(TraitNames.ABILITY_BASIC_STEPASSIST))
                        this.StepAssist = true;
                }
            }
        }
        this.Weight /= 4;
        this.Weight ++ ;
        if (Double.isNaN(this.Fuel))
            this.Fuel = 0;
    }

    private void addTrait(BaseTrait Trait, Double Weight){
        if (this.Traits.containsKey(Trait)){
            this.Traits.put(Trait, this.Traits.get(Trait) + Weight);
        } else {
            this.Traits.put(Trait, Weight);
        }
    }

    public void save(){
        double fuelPer = this.Fuel/armorMap.size();
        for (ITAArmorProperties props: armorMap.keySet()){
            props.RemainingFuel = fuelPer;
            armorMap.get(props).stackTagCompound = props.getTagCompound();
        }
    }

    public boolean consumeFuel(double amount){
        if (amount > this.Fuel) {
            return false;
        } else {
            this.Fuel -= amount;
            return true;
        }
    }


    public void regenFuel() {
        if (this.Regen > 0) {
            double regenRemaining = this.Regen;
            if (this.Fuel <= 10 || !player.onGround)
                regenRemaining *= 0.05; // cooldown
            this.Fuel = Math.min(this.Fuel + regenRemaining, this.MaxFuel);
        }
    }

}
