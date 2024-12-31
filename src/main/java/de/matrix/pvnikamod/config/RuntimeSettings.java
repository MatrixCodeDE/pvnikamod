package de.matrix.pvnikamod.config;

import java.lang.reflect.Field;

public class RuntimeSettings {

    public static boolean igModSlided;
    public static boolean renderEnderPearl;
    public static boolean connectedToServer;
    public static Field ofFastRenderField;
    public static int motionBlurAmount;
    public static boolean updateMotionBlur;


    public static void init(){
        igModSlided = false;
        renderEnderPearl = false;
        connectedToServer = false;
        motionBlurAmount = 0;
        updateMotionBlur = true;
    }

}
