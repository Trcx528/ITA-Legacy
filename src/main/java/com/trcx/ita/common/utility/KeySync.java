package com.trcx.ita.common.utility;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JPiquette on 11/15/2014.
 */
public class KeySync {

    public static final int FLY=0;
    public static final int DESCEND=1;
    public static final int HOVER=2;
    public static final int SPRINTACC=3;
    public static final int NIGHTVISION = 4;
    public static final int ROCKET= 5;

    public static Map<String,KeyStates> PlayerKeyStates = new HashMap<String, KeyStates>();


    public static void setKey(String playerName, int Key, boolean val){
        if (!PlayerKeyStates.containsKey(playerName))
            PlayerKeyStates.put(playerName, new KeyStates());
        KeyStates ks = PlayerKeyStates.get(playerName);
        switch (Key){
            case FLY:
                ks.FLY = val;
                break;
            case DESCEND:
                ks.DESCEND = val;
                break;
            case HOVER:
                ks.HOVER = val;
                break;
            case SPRINTACC:
                ks.SPRINTACC = val;
                break;
            case NIGHTVISION:
                ks.NIGHTVISION = val;
                break;
            case ROCKET:
                ks.ROCKET = val;
                break;
        }
    }
}
