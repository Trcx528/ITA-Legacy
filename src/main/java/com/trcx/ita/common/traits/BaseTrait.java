package com.trcx.ita.common.traits;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.trcx.ita.common.material.BaseProperty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

import java.util.Random;

/**
 * Created by JPiquette on 11/10/2014.
 */
public class BaseTrait {

    public String traitName = "";
    private String toolTip;
    private GenericPotionEffect[] potionEffects;
    private GenericSpecialProtection[] specialProtections;
    private Random rand = new Random();

    public BaseTrait(String name){ this.traitName = name;};

    public BaseTrait(String traitName, String traitToolTip, GenericPotionEffect[] potionEffects, GenericSpecialProtection[] specialProtections){
        this.traitName = traitName;
        this.toolTip = traitToolTip;
        this.potionEffects = potionEffects;
        this.specialProtections = specialProtections;
    }

    public void tick(double traitWeight, EntityPlayer player, int counter, ItemStack is) {
        if (potionEffects != null) {
            for (GenericPotionEffect effect : potionEffects) {
                if ((counter % 20) == 1) {
                    if (rand.nextInt(2000) <= Math.floor(traitWeight)) {
                        player.addPotionEffect(new PotionEffect(effect.effectID, effect.effectDuration, effect.potencyLevel));
                    }
                }

            }
        }
    }

    public double getDamageRatio(DamageSource source, double damage, double traitWeight){
        double absorbRatio = 0.0;
        if (specialProtections != null) {
            for(GenericSpecialProtection sp: specialProtections){
                sp.baseReduction = 0;
                sp.percentReduction = (double) traitWeight/100;
                absorbRatio += sp.getDamageReductionRatio(source,damage);
            }
        }
        return absorbRatio;
    }

    public String getToolTip(BaseProperty BP){
        return this.toolTip + ChatFormatting.RESET;
    }

}
