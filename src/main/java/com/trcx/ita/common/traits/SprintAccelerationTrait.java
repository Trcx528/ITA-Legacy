package com.trcx.ita.common.traits;

import com.trcx.ita.common.Main;
import com.trcx.ita.common.network.PacketKey;
import com.trcx.ita.common.properties.BaseProperty;
import com.trcx.ita.common.properties.PlayerProperties;
import com.trcx.ita.common.utility.KeyStates;
import com.trcx.ita.common.utility.KeySync;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by JPiquette on 11/20/2014.
 */
public class SprintAccelerationTrait extends BaseTrait {

    public SprintAccelerationTrait(String Name){
        super (Name);
    }

    public static final double MAX_TRAVEL_SPEED =2.0D;

    @Override
    public String getToolTip(BaseProperty BP) { return "Whiz! Bang! Zoom!"; }

    @Override
    public void tick(double tw, EntityPlayer player, int counter, ItemStack is) {
        KeyStates KeyState;
        if (KeySync.PlayerKeyStates.containsKey(player.getDisplayName())) {
            KeyState = KeySync.PlayerKeyStates.get(player.getDisplayName());
            PlayerProperties PP = new PlayerProperties(player);
            if (KeyState.SPRINTACC && player.onGround && PP.consumeFuel(10D * tw)) {
                double ratio = player.motionZ/player.motionX;
                double mX = Math.min(Math.abs(player.motionX) * tw * 5, MAX_TRAVEL_SPEED - ((PP.Weight -1) * 0.4));
                if (player.motionX <= 0){
                    mX = -mX;
                }
                player.motionX = mX;
                player.motionZ = mX * ratio;
            }
        } else {
            //get the player in the map so next tick it'll recharge
            KeySync.setKey(Minecraft.getMinecraft().thePlayer.getDisplayName(), KeySync.FLY, false);
            Main.Network.sendToServer(new PacketKey.KeyMessage(KeySync.FLY, false));
        }
    }
}
