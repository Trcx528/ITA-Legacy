package com.trcx.ita.common.traits;

import com.trcx.ita.common.ITA;
import com.trcx.ita.common.properties.BaseProperty;
import com.trcx.ita.common.properties.PlayerProperties;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;

/**
 * Created by Trcx on 12/1/2014.
 */
public class ShockAbsorb extends BaseTrait {
    public ShockAbsorb(String name) {
        super(name);
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    private static double SHOCK_ABSORB_ENERGY = 20;

    @SubscribeEvent
    public void onFall(LivingFallEvent e){
        if (e.entityLiving instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) e.entityLiving;
            PlayerProperties pp = new PlayerProperties(player);
            if (pp.Traits.containsKey(ITA.getTrait(TraitNames.ABILITY_SHOCK_ABSORB))) {
                if (e.distance > 3D) { // don't consume energy if not needed
                    double traitWeight = pp.Traits.get(ITA.getTrait(TraitNames.ABILITY_SHOCK_ABSORB));
                    double energyDesired = (SHOCK_ABSORB_ENERGY * e.distance * pp.Resistance)/ traitWeight;
                    double energyToConsume = Math.min(pp.Fuel, energyDesired);
                    double ratio = energyToConsume / energyDesired;
                    pp.consumeFuel(energyToConsume / pp.Resistance);
                    e.distance -= e.distance * ratio;
                }
            }
        }
    }

    @Override
    public String getToolTip(BaseProperty BP) {
        return "";
    }
}
