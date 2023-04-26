package de.matrix.pvnikamod.utils;

import java.awt.*;

public class ColorUtil {

    public static String rgbToHex(int r, int g, int b){
        String hex = String.format("%02x%02x%02x", r, g, b);
        return hex;
    }

    public static int[] hexToRGB(String hex){
        int r = Integer.valueOf(hex.substring(0,2), 16);
        int g = Integer.valueOf(hex.substring(2,4), 16);
        int b = Integer.valueOf(hex.substring(4,6), 16);
        return new int[]{r, g, b};
    }

    public static int rgbToDec(int r, int g, int b){
        int dec = r*65536 + g*256 + b;
        return dec;
    }

    public static int[] decToRGB(int dec){
        int r = dec / 65536;
        int g = (dec % 65536) / 256;
        int b = (dec % 65536) % 256;
        return new int[]{r, g, b};
    }

    public static float[] rgbToHSB(int r, int g, int b){
        float[] hsbvals = Color.RGBtoHSB(r, g, b, null);
        return hsbvals;
    }

    public static int[] hsbToRGB(float hue, float saturation, float brightness){
        int enc = Color.HSBtoRGB(hue, saturation, brightness);
        int r = (enc >> 16) & 0xFF;
        int g = (enc >> 8) & 0xFF;
        int b = (enc >> 0) & 0xFF;
        return new int[]{r, g, b};
    }

    public static int hsbToDec(float hue, float saturation, float brightness){
        int[] rgb = hsbToRGB(hue, saturation, brightness);
        int dec = rgbToDec(rgb[0], rgb[1], rgb[2]);
        return dec;
    }

    public static int colorToDec(Color color){
        int dec = rgbToDec(color.getRed(), color.getGreen(), color.getBlue());
        return dec;
    }

}
