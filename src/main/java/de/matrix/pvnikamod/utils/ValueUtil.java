package de.matrix.pvnikamod.utils;

import net.minecraft.util.*;

public class ValueUtil {

    public static float floatToPercentage(float value){
        float val = (int)(value * 10000);
        val = val / 100;
        return val;
    }

    public static String rotationToDirection(double yaw){
        double d = MathHelper.wrapAngleTo180_double(yaw) + 180.0;
        d += 22.5;
        d %= 360.0;
        d /= 45.0;
        int dir = MathHelper.floor_double(d);
        switch (dir){
            case 0:
                return "N";
            case 1:
                return "NE";
            case 2:
                return "E";
            case 3:
                return "SE";
            case 4:
                return "S";
            case 5:
                return "SW";
            case 6:
                return "W";
            case 7:
                return "NW";
        }
        return "";
    }

}
