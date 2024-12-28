package de.matrix.pvnikamod.listener;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.config.RuntimeSettings;
import de.matrix.pvnikamod.gui.GuiClientOptions;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.cosmetics.CosmeticsManager;
import de.matrix.pvnikamod.renderer.CrosshairRenderer;
import de.matrix.pvnikamod.modutils.ParticlesUtils;
import de.matrix.pvnikamod.modutils.ZoomUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class Events {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    public final CrosshairRenderer crosshairRenderer;
    private final ZoomUtils zoomUtils;
    private final ParticlesUtils particlesUtils;
    private final CosmeticsManager cosmeticsRenderer;
    private Implementation implementation;
    private float playerHealth;

    private boolean toggled_sneak = false;
    private boolean toggled_sprint = false;

    public Events(){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.crosshairRenderer = new CrosshairRenderer();
        this.zoomUtils = this.mod.zoomUtils;
        this.implementation = new Implementation();
        this.particlesUtils = new ParticlesUtils();
        this.cosmeticsRenderer = new CosmeticsManager();
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event){
        if (!event.target.isInvisible()) {
            particlesUtils.onAttack(event);
        }
    }

    @SubscribeEvent
    public void onClientTickEvent(TickEvent.RenderTickEvent event) {
        if (!this.config.crosshairSettings.activated) {
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
    public void onClientTickEvent(TickEvent.ClientTickEvent event){
        if ( this.mc.thePlayer != null && !this.mc.isIntegratedServerRunning() ){
            RuntimeSettings.connectedToServer = true;
        } else {
            RuntimeSettings.connectedToServer = false;
        }
    }

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event){
        if (this.config.movementSettings.toggleSneak){
            KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSneak.getKeyCode(), toggled_sneak);
        }
        if (this.config.movementSettings.toggleSprint){
            KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSprint.getKeyCode(), toggled_sprint);
        }
    }

    @SubscribeEvent
    public void trackKeyInputs(InputEvent.KeyInputEvent event){
        if (PvnikaMod.openPvnikaGui.isPressed()){
            this.mc.displayGuiScreen(new GuiClientOptions(null));
        } else
        if (this.mc.gameSettings.keyBindSneak.isPressed()){
            if (this.config.movementSettings.toggleSneak){
                toggled_sneak = !toggled_sneak;
            } else {
                toggled_sneak = false;
            }
        }
        if (this.mc.gameSettings.keyBindSprint.isPressed()){
            if (this.config.movementSettings.toggleSprint){
                toggled_sprint = !toggled_sprint;
            } else {
                toggled_sprint = false;
            }
        }
        if (PvnikaMod.primaryTargetKey.isPressed()){
            this.mod.hitboxRenderer.assignPrimaryTarget();
        }
    }

    @SubscribeEvent
    public void trackMouseInputs(InputEvent.MouseInputEvent event){
        this.zoomUtils.mouseEvent(Mouse.getDWheel());
        if (this.mc.gameSettings.keyBindSneak.isPressed()){
            if (this.config.movementSettings.toggleSneak){
                toggled_sneak = !toggled_sneak;
            } else {
                toggled_sneak = false;
            }
        }
        if (this.mc.gameSettings.keyBindSprint.isPressed()){
            if (this.config.movementSettings.toggleSprint){
                toggled_sprint = !toggled_sprint;
            } else {
                toggled_sprint = false;
            }
        }
    }

    @SubscribeEvent
    public void onConnect(PlayerEvent.PlayerLoggedInEvent event){

        RuntimeSettings.connectedToServer = true;
    }

    @SubscribeEvent
    public void onDisconnect(PlayerEvent.PlayerLoggedOutEvent event){
        RuntimeSettings.connectedToServer = false;
    }

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Post event) {
        EntityPlayer player = event.entityPlayer;
        cosmeticsRenderer.renderCosmetics(player, event.renderer,event.partialRenderTick);
    }

}
