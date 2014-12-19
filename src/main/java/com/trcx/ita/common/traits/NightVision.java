package com.trcx.ita.common.traits;

import com.trcx.ita.common.properties.BaseProperty;
import com.trcx.ita.common.properties.PlayerProperties;
import com.trcx.ita.common.utility.KeyStates;
import com.trcx.ita.common.utility.KeySync;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * Created by JPiquette on 11/21/2014.
 */
public class NightVision extends BaseTrait {

    private static final PotionEffect potionEffect = new PotionEffect(Potion.nightVision.getId(),1,1);

    public NightVision(String Name){
        super(Name);
    }

    @Override
    public String getToolTip(BaseProperty BP) {
        return "I See You";
    }

    @Override
    public void tick(double traitWeight, PlayerProperties pp, int counter) {
        KeyStates KeyState;
        if (KeySync.PlayerKeyStates.containsKey(pp.player.getDisplayName())) {
            KeyState = KeySync.PlayerKeyStates.get(pp.player.getDisplayName());
            if (KeyState.NIGHTVISION &&  pp.Fuel > 12D && pp.consumeFuel(2)){ // wait for the recharge cool down to wear off
                pp.player.addPotionEffect(potionEffect);
            }
        }

    }
}
