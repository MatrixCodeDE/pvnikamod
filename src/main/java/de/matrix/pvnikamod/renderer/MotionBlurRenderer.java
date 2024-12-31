//Source https://github.com/Sk1erLLC/MotionBlur

package de.matrix.pvnikamod.renderer;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.config.RuntimeSettings;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.ClientNotify;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import de.matrix.pvnikamod.modutils.VisualsUtils;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class MotionBlurRenderer {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    private Map domainResourceManagers;
    private int ticks;
    private final String domainName = "pmotionblur";

    public MotionBlurRenderer() {
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    public void hookDomainResourceManagers(){
        try {
            Field[] fields = SimpleReloadableResourceManager.class.getDeclaredFields();

            for (Field field : fields) {
                if (field.getType() == Map.class) {
                    field.setAccessible(true);
                    this.domainResourceManagers = (Map) field.get(Minecraft.getMinecraft().getResourceManager());
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMotionBlur(){
        addMotionBlur(false);
    }

    public void addMotionBlur(boolean changed){
        if (VisualsUtils.isFastRenderEnabled()){
            RuntimeSettings.motionBlurAmount = 0;
            if (changed)
                ClientNotify.tempChat("OF FastRender is enabled, MotionBlur won't work!");
            return;
        }
        if (this.mc.entityRenderer.getShaderGroup() != null){
            this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
        try {
            Method method = ReflectionHelper.findMethod(EntityRenderer.class, this.mc.entityRenderer, new String[]{"loadShader", "func_175069_a"}, new Class[]{ResourceLocation.class});
            if (this.domainResourceManagers != null && this.domainResourceManagers.containsKey(this.domainName)) {
                method.invoke(this.mc.entityRenderer, new ResourceLocation(this.domainName, this.domainName));
                this.mc.entityRenderer.getShaderGroup().createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            }
        } catch (Exception ignored) {
        }
    }

    public void onTick(){
        if (RuntimeSettings.updateMotionBlur){
            RuntimeSettings.updateMotionBlur = false;
            addMotionBlur(true);
        }
        if (domainResourceManagers == null) {
            hookDomainResourceManagers();
        }else{
            if (!domainResourceManagers.containsKey(this.domainName)) {
                this.domainResourceManagers.put(this.domainName, new MotionBlurResourceManager());
                addMotionBlur();
            }
        }
    }

    public void onKey(){
        if (this.config.visualsSettings.motionBlur > 0 && this.mc.thePlayer != null && GameSettings.isKeyDown(mc.gameSettings.keyBindTogglePerspective)){
            addMotionBlur();
        }
    }
}
