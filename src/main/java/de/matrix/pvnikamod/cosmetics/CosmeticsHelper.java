package de.matrix.pvnikamod.cosmetics;


import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class CosmeticsHelper{

    public static void bindToTorso(EntityPlayer player, float scale, float partialTicks) {
        float size = 0.5F;
        GL11.glScalef(size, size, size);

        GL11.glTranslatef(0.0F / size, 1.9F / size, 0F / size);

        float yaw = interpolateRotation(player.prevRenderYawOffset, player.renderYawOffset, partialTicks);
        GL11.glRotatef(-yaw, 0.0F, 1.0F, 0.0F);


        if (player.isSneaking()) {
            GL11.glRotatef(30.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.17F, 0.7F);
        }

        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
    }

    public static void bindToTorso(EntityPlayer player, ModelPlayer modelPlayer, float scale, float partialTicks) {
        ModelRenderer back = modelPlayer.bipedBody;
        GL11.glTranslatef(back.rotationPointX * scale + back.offsetX,
                back.rotationPointY * scale + back.offsetY,
                back.rotationPointZ * scale + back.offsetZ);

        // Rotationen anwenden
        if (back.rotateAngleZ != 0.0F) {
            GL11.glRotatef(back.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
        }

        if (back.rotateAngleY != 0.0F) {
            GL11.glRotatef(back.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
        }

        if (back.rotateAngleX != 0.0F) {
            GL11.glRotatef(back.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
        }
    }

    public static void bindToHand(EntityPlayer player, float scale, float partialTicks) {
        float size = 0.5F;
        float handOffsetX = 0.0F;
        float handOffsetY = 1.9F;
        float handOffsetZ = 0.0F;

        GL11.glScalef(size, size, size);
        GL11.glTranslatef(handOffsetX / size, handOffsetY / size, handOffsetZ / size);

        float handYaw = interpolateRotation(player.prevRenderYawOffset, player.renderYawOffset, partialTicks);
        GL11.glRotatef(-handYaw, 0.0F, 1.0F, 0.0F);

        if (player.isSneaking()) {
            GL11.glRotatef(30.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.2F, 0.7F);
        }

        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
    }

    private static float interpolateRotation(float prevYaw, float yaw, float partialTicks) {
        float f = yaw - prevYaw;

        while (f < -180.0F) {
            f += 360.0F;
        }

        while (f >= 180.0F) {
            f -= 360.0F;
        }

        return prevYaw + partialTicks * f;
    }

    public static float wingAccelerator(Entity entity){
        float mod = 0.0f;

        if(entity.prevDistanceWalkedModified != entity.distanceWalkedModified){
            if(entity.onGround){
                mod += 10f;
            } else {
                mod += 15f;
            }
        }

        if(entity.isSprinting()){
            mod += 10f;
        }

        return mod;
    }

    public enum Bindings{
        TORSO,
        HEAD,
        LEFT_ARM,
        RIGHT_ARM,
        LEFT_LEG,
        RIGHT_LEG
    }

}
