package com.trcx.ita.common.traits;

/**
 * Created by JPiquette on 11/5/2014.
 */
public class GenericPotionEffect {

    public int effectID;
    public int potencyLevel = 0;
    public int effectDuration = 100; // 5 seconds
    public int effectFrequency = 0;
    public boolean effectIsRandom = false;
    public int randomWeight = 0;

    public GenericPotionEffect(int potionEffectID){
        this.effectID = potionEffectID;
    }

    public GenericPotionEffect(int potionEffectID, boolean effectIsRandom, int randomWeight, int effectDuration){
        this.effectID = potionEffectID;
        this.effectIsRandom = effectIsRandom;
        this.randomWeight = randomWeight;
        this.effectDuration = effectDuration;
    }

    public GenericPotionEffect(int potionEffectID, int frequency, int duration, int potencyLevel){
        this.effectID = potionEffectID;
        this.effectFrequency = frequency;
        this.effectDuration = duration;
        this.potencyLevel = potencyLevel;
    }
}
