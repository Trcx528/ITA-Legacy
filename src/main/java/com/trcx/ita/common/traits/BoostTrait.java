package com.trcx.ita.common.traits;

import com.trcx.ita.common.Main;
import com.trcx.ita.common.network.PacketKey;
import com.trcx.ita.common.properties.BaseProperty;
import com.trcx.ita.common.properties.PlayerProperties;
import com.trcx.ita.common.utility.KeyStates;
import com.trcx.ita.common.utility.KeySync;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by JPiquette on 11/20/2014.
 */
public class BoostTrait extends BaseTrait {

    public static final double MAX_TRAVEL_SPEED =2.5D;

    public static final int GROUND_ONLY = 1;
    public static final int AIR_ONLY = 2;
    public static final int WATER_ONLY = 3;
    public int Type;

    public BoostTrait(String Name, int Type){
        super(Name);
        this.Type = Type;
    }

    @Override
    public String getToolTip(BaseProperty BP) { return "Whiz! Bang! Zoom!"; }

    @Override
    public void tick(double tw, EntityPlayer player, int counter) {
        KeyStates KeyState;
        if (KeySync.PlayerKeyStates.containsKey(player.getDisplayName())) {
            KeyState = KeySync.PlayerKeyStates.get(player.getDisplayName());
            PlayerProperties PP = new PlayerProperties(player);
            boolean canDo = false;
            switch (this.Type){
                case GROUND_ONLY:
                    canDo = player.onGround && !player.isInWater();
                    break;
                case AIR_ONLY:
                    canDo = !player.onGround && !player.isInWater();
                    tw /= 2;
                    break;
                case WATER_ONLY:
                    canDo = player.isInWater();
                    break;
            }
            if (canDo)
                canDo = (player.motionX != 0 || player.motionZ != 0);

            if (KeyState.SPRINTACC && canDo && PP.consumeFuel(10D * tw)) {
                double multiplier = 1 + (PP.Weight * 1);
                if (Math.abs(multiplier * player.motionX) <= MAX_TRAVEL_SPEED && Math.abs(multiplier * player.motionZ) <=MAX_TRAVEL_SPEED) {
                    player.motionX *= multiplier;
                    player.motionZ *= multiplier;
                }
            }
        } else {
            //get the player in the map so next tick it'll recharge
            KeySync.setKey(Minecraft.getMinecraft().thePlayer.getDisplayName(), KeySync.FLY, false);
            Main.Network.sendToServer(new PacketKey.KeyMessage(KeySync.FLY, false));
        }
    }
}
