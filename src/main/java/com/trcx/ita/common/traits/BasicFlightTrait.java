package com.trcx.ita.common.traits;

import com.trcx.ita.common.Main;
import com.trcx.ita.common.properties.BaseProperty;
import com.trcx.ita.common.properties.ITAArmorProperties;
import com.trcx.ita.common.network.PacketKey;
import com.trcx.ita.common.properties.PlayerProperties;
import com.trcx.ita.common.utility.KeyStates;
import com.trcx.ita.common.utility.KeySync;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.text.DecimalFormat;

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
            double FlightLevel = AP.Traits.get(TraitNames.ABILITY_BASIC_FLIGHT);
            DecimalFormat df = new DecimalFormat("#.#");
            return EnumChatFormatting.GREEN + "Flight " + df.format(FlightLevel);
        } else {
            return "I Believe I Can Fly!";
        }
    }

    @Override
    public void tick(double tw, PlayerProperties pp, int counter) {
        KeyStates KeyState;
        if (KeySync.PlayerKeyStates.containsKey(pp.player.getDisplayName())) {
            KeyState = KeySync.PlayerKeyStates.get(pp.player.getDisplayName());

            //make additional thruster much more effective and nerf only 1 thruster
            double fuelFactor = tw < 0.6 ? 2.5 : tw;
            double traitWeight = tw * tw;

            double climbRate = 0.04D * pp.Weight * traitWeight;
            double maxSpeed = SUGGESTED_MAXVERTICALSPEED;

            //allow a little flight no matter what
            climbRate = Math.max(climbRate, 0.085); // just barely enough to get off the ground
            maxSpeed = Math.max(maxSpeed, 0.15);

            if (KeyState.FLY) {
                if (pp.player.motionY < -0.799D && pp.consumeFuel(fuelFactor*8)) { // make it kick on hard when falling
                    pp.player.motionY = Math.min(pp.player.motionY + (climbRate * 8), maxSpeed);
                    pp.player.fallDistance = 0F;
                } else if (pp.consumeFuel(fuelFactor)) { //kick on soft
                    pp.player.motionY = Math.min(pp.player.motionY + climbRate, maxSpeed);
                    pp.player.fallDistance = 0F;
                }
            } else if (KeyState.HOVER && !pp.player.onGround) {
                if (KeyState.DESCEND && !pp.player.onGround && pp.consumeFuel(fuelFactor)) {
                    pp.player.motionY = Math.max(pp.player.motionY - 0.15, -0.4D);
                    pp.player.fallDistance = 0F;
                } else if(pp.consumeFuel(fuelFactor)) {
                    // cubing the weight makes light materials fall even slower
                    // and heavy materials fall even faster
                    pp.player.motionY = -0.05 * (pp.Weight * pp.Weight * pp.Weight);
                    pp.player.fallDistance = 0F;
                }
            }
        } else {
            //get the player in the map so next tick it'll recharge
            KeySync.setKey(Minecraft.getMinecraft().thePlayer.getDisplayName(), KeySync.FLY, false);
            Main.Network.sendToServer(new PacketKey.KeyMessage(KeySync.FLY, false));
        }
    }
}
