package de.matrix.pvnikamod.modutils;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;

public class ZoomUtils {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    private final float defaultLevel = 3.0f;
    private Float currentLevel;
    private Float defaultMouseSensitivity;
    private float zoomDefault;
    private Boolean defaultSmooth;
    public boolean blockItemScrolling;

    public ZoomUtils(){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    public float changeFOV(float currentFOV){
        this.zoomDefault = (float) this.config.zoomSettings.defaultZoom;
        GameSettings gameSettings = this.mc.gameSettings;
        if (this.currentLevel == null) {
            this.currentLevel = zoomDefault;
        }

        if(PvnikaMod.zoomKey.isKeyDown()){
            if(this.defaultSmooth == null){
                this.defaultSmooth = gameSettings.smoothCamera;
            }
            if(this.defaultMouseSensitivity == null) {
                this.defaultMouseSensitivity = gameSettings.mouseSensitivity;
            }
            if(this.defaultSmooth || this.config.zoomSettings.smooth){
                gameSettings.smoothCamera = true;
            }
            gameSettings.mouseSensitivity = defaultMouseSensitivity * 0.7f;
            return currentFOV / currentLevel;
        } else {
            this.currentLevel = (float) this.config.zoomSettings.defaultZoom;
            if (this.defaultMouseSensitivity != null){
                gameSettings.mouseSensitivity = this.defaultMouseSensitivity;
                this.defaultMouseSensitivity = null;
            }
            if(this.defaultSmooth == null){
                this.defaultSmooth = gameSettings.smoothCamera;
            }
            gameSettings.smoothCamera = this.defaultSmooth;

            return currentFOV;
        }

    }

    public void mouseEvent(int strength){
        if (PvnikaMod.zoomKey.isKeyDown() && this.config.zoomSettings.scrollable){
            this.blockItemScrolling = true;
            if (this.currentLevel == null){
                this.currentLevel = (float) this.config.zoomSettings.defaultZoom;
            }

            if (strength > 0){
                this.currentLevel = this.currentLevel * 1.1f;
            } else
            if (strength < 0){
                this.currentLevel = this.currentLevel * 0.9f;
            }

            this.currentLevel = MathHelper.clamp_float(this.currentLevel, 1.0f, 50.0f);
        } else {
            this.blockItemScrolling = false;
        }
    }

    public void toggleSmoothZoom(){
        boolean smoothZoom = this.config.zoomSettings.smooth;
        this.config.zoomSettings.smooth = !smoothZoom;
    }

    public void toggleScrollable(){
        boolean scrollable = this.config.zoomSettings.scrollable;
        this.config.zoomSettings.scrollable = !scrollable;
    }

}
