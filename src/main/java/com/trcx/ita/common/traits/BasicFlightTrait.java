package com.trcx.ita.common.traits;

import com.trcx.ita.common.Main;
import com.trcx.ita.common.properties.BaseProperty;
import com.trcx.ita.common.properties.ITAArmorProperties;
import com.trcx.ita.common.network.PacketKey;
import com.trcx.ita.common.properties.PlayerProperties;
import com.trcx.ita.common.utility.KeyStates;
import com.trcx.ita.common.utility.KeySync;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by JPiquette on 11/12/2014.
 */
public class BasicFlightTrait extends BaseTrait {

    public static final double SUGGESTED_MAXVERTICALSPEED = 1.0;

    public BasicFlightTrait(String name) {
        super(name);
    }

    @Override
    public String getToolTip(BaseProperty BP) {
        if (BP instanceof ITAArmorProperties){
            ITAArmorProperties AP = (ITAArmorProperties) BP;
            double FlightLevel = AP.Traits.get("basicFlight");
            DecimalFormat df = new DecimalFormat("#.#");
            return EnumChatFormatting.GREEN + "Flight " + df.format(FlightLevel);
        } else {
            return "I Believe I Can Fly!";
        }
    }

    @Override
    public void tick(double tw, EntityPlayer player, int counter, ItemStack is) {
        KeyStates KeyState;
        if (KeySync.PlayerKeyStates.containsKey(player.getDisplayName())) {
            KeyState = KeySync.PlayerKeyStates.get(player.getDisplayName());

            //make additional thruster much more effective and nerf only 1 thruster
            double fuelFactor = tw < 0.6 ? 2.5 : tw;
            double traitWeight = tw * tw;

            PlayerProperties playerProps = new PlayerProperties(player);
            double climbRate = 0.04D * playerProps.Weight * traitWeight;
            double maxSpeed = SUGGESTED_MAXVERTICALSPEED;

            //allow a little flight no matter what
            climbRate = Math.max(climbRate, 0.085); // just barely enough to get off the ground
            maxSpeed = Math.max(maxSpeed, 0.15);

            if (KeyState.FLY) {
                if (player.motionY < -0.799D && playerProps.consumeFuel(fuelFactor*8)) { // make it kick on hard when falling
                    player.motionY = Math.min(player.motionY + (climbRate * 8), maxSpeed);
                    player.fallDistance = 0F;
                } else if (playerProps.consumeFuel(fuelFactor)) { //kick on soft
                    player.motionY = Math.min(player.motionY + climbRate, maxSpeed);
                    player.fallDistance = 0F;
                }
            } else if (KeyState.HOVER && !player.onGround) {
                if (KeyState.DESCEND && !player.onGround && playerProps.consumeFuel(fuelFactor)) {
                    player.motionY = Math.max(player.motionY - 0.15, -0.4D);
                    player.fallDistance = 0F;
                } else if(playerProps.consumeFuel(fuelFactor)) {
                    // cubing the weight makes light materials fall even slower
                    // and heavy materials fall even faster
                    player.motionY = -0.05 * (playerProps.Weight * playerProps.Weight * playerProps.Weight);
                    player.fallDistance = 0F;
                }
            } else {
                playerProps.regenFuel(!player.onGround);
            }
        } else {
            //get the player in the map so next tick it'll recharge
            KeySync.setKey(Minecraft.getMinecraft().thePlayer.getDisplayName(), KeySync.FLY, false);
            Main.Network.sendToServer(new PacketKey.KeyMessage(KeySync.FLY, false));
        }
    }
}
