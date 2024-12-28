package de.matrix.pvnikamod.cosmetics.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public abstract class CosmeticBase extends ModelBase {

    public Minecraft mc;
    public TextureManager textureManager;
    public RenderPlayer renderPlayer;
    public ModelPlayer model;


    public CosmeticBase(){
        this.mc = Minecraft.getMinecraft();
        this.textureManager = this.mc.getTextureManager();
    }

    public void render(EntityPlayer player, float scale, float partialTicks){

    }

    public void bindCosmeticTexture(ResourceLocation resource){
        this.textureManager.bindTexture(resource);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}
