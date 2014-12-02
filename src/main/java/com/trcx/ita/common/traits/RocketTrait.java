package com.trcx.ita.common.traits;

import com.trcx.ita.common.properties.BaseProperty;
import com.trcx.ita.common.properties.PlayerProperties;
import com.trcx.ita.common.utility.KeyStates;
import com.trcx.ita.common.utility.KeySync;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Trcx on 11/21/2014.
 */
public class RocketTrait extends BaseTrait {

    public static final double MAX_ROCKETSPEED = 1D;

    public RocketTrait(String Name){
        super(Name);
    }

    @Override
    public void tick(double traitWeight, EntityPlayer player, int counter) {
        PlayerProperties PP = new PlayerProperties(player);
        KeyStates ks = KeySync.PlayerKeyStates.get(player.getDisplayName());
        if (ks != null && ks.ROCKET && player.motionY <= MAX_ROCKETSPEED * traitWeight && PP.consumeFuel(10D * traitWeight)){
            player.motionY += (0.8 - (PP.Weight * 0.1)) * traitWeight;
            player.fallDistance = 0F;
            player.motionZ *= 0.6;
            player.motionX *= 0.6;
        }
    }

    @Override
    public String getToolTip(BaseProperty BP) {
        return "To Infinity And Beyond!";
    }
}
