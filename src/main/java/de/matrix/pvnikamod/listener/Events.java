package de.matrix.pvnikamod.listener;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.gui.GuiClientOptions;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.renderer.CrosshairRenderer;
import de.matrix.pvnikamod.modutils.ParticlesUtils;
import de.matrix.pvnikamod.modutils.ZoomUtils;
import de.matrix.pvnikamod.renderer.RenderManager;
import de.matrix.pvnikamod.utils.BooleanColor;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.GuiModList;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class Events {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    public final CrosshairRenderer crosshairRenderer;
    private final ZoomUtils zoomUtils;
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
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event){
        if (this.config.movement_toggleSneak){
            KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSneak.getKeyCode(), toggled_sneak);
        }
        if (this.config.movement_toggleSprint){
            KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSprint.getKeyCode(), toggled_sprint);
        }
        Item targetItem = this.implementation.getTargetBlockItem();
        boolean isBed = targetItem != null && targetItem == Item.getItemById(355);
        boolean isCore = targetItem != null && targetItem == Item.getItemById(138);
        this.crosshairRenderer.isBed = isBed;
        this.crosshairRenderer.isCore = isCore;
        float dmgValue = 0.0f;

    }

    @SubscribeEvent
    public void trackKeyInputs(InputEvent.KeyInputEvent event){
        if (PvnikaMod.openPvnikaGui.isPressed()){
            this.mc.displayGuiScreen(new GuiClientOptions(null));
        } else
        if (this.mc.gameSettings.keyBindSneak.isPressed()){
            if (this.config.movement_toggleSneak){
                toggled_sneak = !toggled_sneak;
            } else {
                toggled_sneak = false;
            }
        }
        if (this.mc.gameSettings.keyBindSprint.isPressed()){
            if (this.config.movement_toggleSprint){
                toggled_sprint = !toggled_sprint;
            } else {
                toggled_sprint = false;
            }
        }
    }

    @SubscribeEvent
    public void trackMouseInputs(InputEvent.MouseInputEvent event){
        this.zoomUtils.mouseEvent(Mouse.getDWheel());
        if (this.mc.gameSettings.keyBindSneak.isPressed()){
            if (this.config.movement_toggleSneak){
                toggled_sneak = !toggled_sneak;
            } else {
                toggled_sneak = false;
            }
        }
        if (this.mc.gameSettings.keyBindSprint.isPressed()){
            if (this.config.movement_toggleSprint){
                toggled_sprint = !toggled_sprint;
            } else {
                toggled_sprint = false;
            }
        }
    }

    @SubscribeEvent
    public void onBreak(BlockEvent.BreakEvent event){
        System.out.println();
    }
}
