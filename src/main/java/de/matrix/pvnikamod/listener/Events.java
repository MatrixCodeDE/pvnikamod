package de.matrix.pvnikamod.listener;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.gui.GuiClientOptions;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.renderer.CrosshairRenderer;
import de.matrix.pvnikamod.modutils.ParticlesUtils;
import de.matrix.pvnikamod.modutils.ZoomUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class Events {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    private final CrosshairRenderer crosshairRenderer;
    private final ZoomUtils zoomUtils;
    private float playerHealth;

    public Events(){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.crosshairRenderer = new CrosshairRenderer();
        this.zoomUtils = this.mod.zoomUtils;
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event){
        ParticlesUtils particlesUtils = new ParticlesUtils();
        particlesUtils.onAttack(event);
    }

    @SubscribeEvent
    public void onClientTickEvent(TickEvent.RenderTickEvent event) {
        if (!this.config.crosshair_custom) {
            GuiIngameForge.renderCrosshairs = true;
            crosshairRenderer.clear();
        } else {
            GuiIngameForge.renderCrosshairs = false;
            if (this.mc.currentScreen == null || this.mc.currentScreen instanceof GuiChat) {
                ScaledResolution resolution = new ScaledResolution(this.mc);
                int width = resolution.getScaledWidth();
                int heigth = resolution.getScaledHeight();
                int x = width / 2;
                int y = heigth / 2;
                playerHealth = this.mc.thePlayer.getHealth();
                crosshairRenderer.render(x, y, this.playerHealth);
            }
        }
    }

    @SubscribeEvent
    public void trackKeyInputs(InputEvent.KeyInputEvent event){
        if (PvnikaMod.openPvnikaGui.isPressed()){
            this.mc.displayGuiScreen(new GuiClientOptions(null));
        }
    }

    @SubscribeEvent
    public void trackMouseInputs(InputEvent.MouseInputEvent event){
        this.zoomUtils.mouseEvent(Mouse.getDWheel());
    }
}
