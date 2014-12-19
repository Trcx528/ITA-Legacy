package com.trcx.ita.common.traits;

import com.trcx.ita.common.properties.BaseProperty;
import com.trcx.ita.common.properties.PlayerProperties;
import com.trcx.ita.common.utility.KeyStates;
import com.trcx.ita.common.utility.KeySync;

/**
 * Created by Trcx on 11/21/2014.
 */
public class RocketTrait extends BaseTrait {

    public static final double MAX_ROCKETSPEED = 1D;

    public RocketTrait(String Name){
        super(Name);
    }

    @Override
    public void tick(double traitWeight, PlayerProperties pp, int counter) {
        KeyStates ks = KeySync.PlayerKeyStates.get(pp.player.getDisplayName());
        if (ks != null && ks.ROCKET && pp.consumeFuel(10D * traitWeight) && pp.player.motionY <= MAX_ROCKETSPEED * traitWeight){
            pp.player.motionY += (0.8 - (pp.Weight * 0.1)) * traitWeight;
            pp.player.fallDistance = 0F;
            pp.player.motionZ *= 0.6;
            pp.player.motionX *= 0.6;
        }
    }

    @Override
    public String getToolTip(BaseProperty BP) {
        return "To Infinity And Beyond!";
    }
}
