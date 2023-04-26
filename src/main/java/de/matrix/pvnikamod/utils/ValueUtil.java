package de.matrix.pvnikamod.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ValueUtil {

    public static float floatToPercentage(float value){
        float val = (int)(value * 10000);
        val = val / 100;
        return val;
    }

}
