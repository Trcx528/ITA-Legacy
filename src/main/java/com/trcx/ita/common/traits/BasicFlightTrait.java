package com.trcx.ita.common.traits;

import com.trcx.ita.common.material.BaseProperty;
import com.trcx.ita.common.material.ITAArmorProperties;
import com.trcx.ita.client.ITAKeybindings;
import com.trcx.ita.common.utility.KeyStates;
import com.trcx.ita.common.utility.KeySync;
import com.trcx.ita.common.utility.Miscellaneous;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by JPiquette on 11/12/2014.
 */
public class BasicFlightTrait extends BaseTrait {

    public static final int MAX_FLIGHTTIME = 200; // ticks
    public static final double SUGGESTED_MAXVERTICALSPEED = 1.0;

    private Random rand = new Random();

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

    private void consumeFuel (ITAArmorProperties props, Boolean Hovering, double fuelFactor){
        if (Hovering) { // consume a little less fuel when hovering
            props.RemainingFlight -= props.Resistance * 0.75 * fuelFactor;
        } else {
            props.RemainingFlight -= props.Resistance * fuelFactor;
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

            ITAArmorProperties ArmorProps = new ITAArmorProperties(is);
            double Weight = Miscellaneous.getPlayerWeight(player) + 1;
            boolean hasFuel = ArmorProps.RemainingFlight >= ArmorProps.Resistance * fuelFactor;
            double climbRate = 0.04D * Weight * traitWeight;
            double maxSpeed = SUGGESTED_MAXVERTICALSPEED;

            //allow a little flight no matter what
            climbRate = Math.max(climbRate, 0.085); // just barely enough to get off the ground
            maxSpeed = Math.max(maxSpeed, 0.15);

            if (KeyState.FLY && hasFuel) {
                if (player.motionY < -1D) { // make it kick on hard when falling
                    player.motionY = Math.min(player.motionY + (climbRate * 8), maxSpeed);
                    consumeFuel(ArmorProps, false, fuelFactor * 8);
                } else {
                    consumeFuel(ArmorProps, false, fuelFactor);
                    player.motionY = Math.min(player.motionY + climbRate, maxSpeed);
                }
                player.fallDistance = 0F;
            } else if (KeyState.HOVER && hasFuel && !player.onGround && !(player.motionY < 0.9 && player.motionY > 0.7)) {
                //yeah I know in some corner cases there is sufficient fuel to hover but has fuel doesn't check for that...
                //I don't care though, it's close enough
                if (KeyState.DESCEND && hasFuel && !player.onGround) {
                    consumeFuel(ArmorProps, false, fuelFactor);
                    player.motionY = Math.max(player.motionY - 0.15, -0.4D);
                    player.fallDistance = 0F;
                } else {
                    consumeFuel(ArmorProps, true, fuelFactor);
                    player.motionY = ((rand.nextInt(20) - 10) / 68D); // just to give it some character
                    player.fallDistance = 0F;
                }
            } else {
                if (ArmorProps.RemainingFlight < MAX_FLIGHTTIME)
                    if (player.onGround && !(ArmorProps.RemainingFlight < 7)) {
                        ArmorProps.RemainingFlight++;
                    } else {
                        ArmorProps.RemainingFlight += 0.05; // cooldown
                    }
            }

            is.stackTagCompound = ArmorProps.getTagCompound();
        }
    }
}
