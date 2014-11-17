package com.trcx.ita.common.traits;

import net.minecraft.util.DamageSource;

/**
 * Created by JPiquette on 11/5/2014.
 */
public class GenericSpecialProtection {

    public DamageSource damageSource;
    public double percentReduction = 0.0;
    public double    baseReduction = 0;
    public boolean absorbAllDamage = false;

    public GenericSpecialProtection(DamageSource source){
        this.damageSource = source;
    }

    public GenericSpecialProtection(DamageSource source, boolean absorbAllDamage){
        this.damageSource = source;
        this.absorbAllDamage = absorbAllDamage;
    }

    public GenericSpecialProtection(DamageSource source, double baseReduction, double percentReduction){
        this.damageSource = source;
        this.baseReduction = baseReduction;
        this.percentReduction = percentReduction;
    }

    public double getDamageReductionRatio(DamageSource source, double damage){
        if (source == damageSource){
            if (absorbAllDamage)
                return 1.0;
            double reducedDamage = baseReduction;
            reducedDamage += (damage-baseReduction) * (percentReduction);
            return reducedDamage/damage;
        }
        return 0;
    }

}
