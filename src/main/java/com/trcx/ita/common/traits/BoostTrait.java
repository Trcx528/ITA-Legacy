package com.trcx.ita.common.traits;

import com.trcx.ita.common.Main;
import com.trcx.ita.common.network.PacketKey;
import com.trcx.ita.common.properties.BaseProperty;
import com.trcx.ita.common.properties.PlayerProperties;
import com.trcx.ita.common.utility.KeyStates;
import com.trcx.ita.common.utility.KeySync;
import net.minecraft.client.Minecraft;

/**
 * Created by JPiquette on 11/20/2014.
 */
public class BoostTrait extends BaseTrait {

    public static final double MAX_TRAVEL_SPEED =2.5D;

    public static final int GROUND_ONLY = 1;
    public static final int AIR_ONLY = 2;
    public int Type;

    public BoostTrait(String Name, int Type){
        super(Name);
        this.Type = Type;
    }

    @Override
    public String getToolTip(BaseProperty BP) { return "Whiz! Bang! Zoom!"; }

    @Override
    public void tick(double tw, PlayerProperties pp, int counter) {
        KeyStates KeyState;
        if (KeySync.PlayerKeyStates.containsKey(pp.player.getDisplayName())) {
            KeyState = KeySync.PlayerKeyStates.get(pp.player.getDisplayName());
            PlayerProperties PP = new PlayerProperties(pp.player);
            boolean canDo = false;
            switch (this.Type){
                case GROUND_ONLY:
                    canDo = pp.player.onGround;
                    tw *= 2;
                    break;
                case AIR_ONLY:
                    canDo = !pp.player.onGround;
                    tw /= 2;
                    break;
            }
            if (canDo)
                canDo = (pp.player.motionX != 0 || pp.player.motionZ != 0);

            if (KeyState.SPRINTACC && canDo && PP.consumeFuel(10D * tw)) {
                double multiplier = 1 + (PP.Weight * 1);
                if (Math.abs(multiplier * pp.player.motionX) <= MAX_TRAVEL_SPEED && Math.abs(multiplier * pp.player.motionZ) <=MAX_TRAVEL_SPEED) {
                    pp.player.motionX *= multiplier;
                    pp.player.motionZ *= multiplier;
                }
            }
        } else {
            //get the pp.player in the map so next tick it'll recharge
            KeySync.setKey(Minecraft.getMinecraft().thePlayer.getDisplayName(), KeySync.FLY, false);
            Main.Network.sendToServer(new PacketKey.KeyMessage(KeySync.FLY, false));
        }
    }
}
