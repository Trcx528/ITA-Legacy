package com.trcx.ita.common.utility;

import com.trcx.ita.common.material.BaseMaterialProperty;
import com.trcx.ita.common.ITA;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JPiquette on 11/8/2014.
 */
public class ColorHelper {

    public static Color colorFromHex(String hex){
        return Color.decode(hex);
    }

    public static void addToColorMap(Map<String, Double> ColorMap, String Hex, Double Weight){
        if (ColorMap.containsKey(Hex)){
            ColorMap.put(Hex, Weight + ColorMap.get(Hex));
        } else {
            ColorMap.put(Hex, Weight);
        }
    }

    public static int intFromHex(String hex){
        Color c = colorFromHex(hex);
        return Integer.parseInt(String.format("%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()), 16);
    }

    public static String hexFromInt(int dColor){
        return "#" + Integer.toHexString(dColor);
    }

    public static int getAvgColor(Map<String, Double> colors){
        double total = 0;
        int r =0;
        int g =0;
        int b =0;
        for (String key: colors.keySet()){
            Color materialColor = colorFromHex(key);
            r += (materialColor.getRed() * colors.get(key));
            g += (materialColor.getGreen() * colors.get(key));
            b += (materialColor.getBlue() * colors.get(key));
            total += colors.get(key);
        }
        if (total == 0)
            return Integer.parseInt("0000FF", 16);

        r /= total;
        g /= total;
        b /= total;

        return Integer.parseInt(String.format("%02x%02x%02x", r, g, b), 16);
    }

    public static int getArmorColor(Map<String, Double> armorIngots){
        int totalIngots=0;
        int red=0;
        int green=0;
        int blue=0;
        armorIngots = new HashMap<String, Double>(armorIngots);
        for (String key: armorIngots.keySet()){
            BaseMaterialProperty ip = ITA.getMaterialProperties(key);
            Color ingotColor = colorFromHex(ip.ColorHex);
            totalIngots += armorIngots.get(key);
            red += (armorIngots.get(key) * ingotColor.getRed());
            green += (armorIngots.get(key) * ingotColor.getGreen());
            blue += (armorIngots.get(key) * ingotColor.getBlue());
        }
        if (totalIngots == 0)
            return Integer.parseInt("00FF00",16);
        red /= totalIngots;
        green /= totalIngots;
        blue /= totalIngots;

        return Integer.parseInt(String.format("%02x%02x%02x", red, green, blue), 16);
    }
}
