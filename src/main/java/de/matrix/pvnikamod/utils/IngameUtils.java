package de.matrix.pvnikamod.utils;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class IngameUtils {

    private static Minecraft mc = Minecraft.getMinecraft();
    private static final Map<EntityPlayer, Float> previousSwingProgress = new HashMap<>();
    private static Map<EntityPlayer, Long> lastHitTime = new HashMap<>();
    private static Long lastDamageTime = 0L;


    public static MovingObjectPosition customRayTrace(double blockReachDistance, float partialTicks){
        return customRayTrace(blockReachDistance, partialTicks, false);
    }

    public static MovingObjectPosition customRayTrace(double blockReachDistance, float partialTicks, boolean stopOnFluid) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null)
            return null;
        Vec3 vec3 = player.getPositionEyes(partialTicks);
        Vec3 vec31 = player.getLook(partialTicks);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * blockReachDistance, vec31.yCoord * blockReachDistance, vec31.zCoord * blockReachDistance);
        return player.worldObj.rayTraceBlocks(vec3, vec32, false, true, false);
    }

    public static Entity getLookedAtEntity(double blockReachDistance) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        return getLookedAtEntity(player, blockReachDistance);
    }

    // Thanks to https://gist.github.com/Dalethium/bc650696960c53c86e1137beec7280b1?permalink_comment_id=2110247#file-getlookedatentity-java
    public static Entity getLookedAtEntity(Entity player, double blockReachDistance) {
        if (player == null){
            return null;
        }
        Vec3 eyeVec = player.getPositionEyes(1.0f);
        Vec3 lookVec = player.getLook(1.0f);
        Vec3 diffVec = eyeVec.addVector(lookVec.xCoord * blockReachDistance, lookVec.yCoord * blockReachDistance, lookVec.zCoord * blockReachDistance);
        Vec3 resultVec = null;
        MovingObjectPosition trace = player.worldObj.rayTraceBlocks(eyeVec, diffVec, false, true, false);
        double distA = blockReachDistance;
        Entity pointedEntity = null;
        if (trace != null)
            distA = trace.hitVec.distanceTo(player.getPositionEyes(1.0f));

        List<Entity> list = player.worldObj.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().addCoord(lookVec.xCoord * blockReachDistance, lookVec.yCoord * blockReachDistance, lookVec.zCoord * blockReachDistance).expand(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>() {
            public boolean apply(Entity entity) {
                return entity != null && entity.canBeCollidedWith();
            }
        }));

        for (Entity currEntity : list) {
            float collisionBoxSize = currEntity.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = currEntity.getEntityBoundingBox().expand(collisionBoxSize, collisionBoxSize, collisionBoxSize);
            MovingObjectPosition raytraceresult = axisalignedbb.calculateIntercept(eyeVec, diffVec);

            if (axisalignedbb.isVecInside(eyeVec)) {
                if (distA >= 0.0D) {
                    pointedEntity = currEntity;
                    resultVec = raytraceresult == null ? eyeVec : raytraceresult.hitVec;
                    distA = 0.0D;
                }
            } else if (raytraceresult != null) {
                double distB = eyeVec.distanceTo(raytraceresult.hitVec);

                if (distB < distA || distA == 0.0D) {
                    if (currEntity == player.ridingEntity && !player.canRiderInteract()) {
                        if (distA == 0.0D) {
                            pointedEntity = currEntity;
                            resultVec = raytraceresult.hitVec;
                        }
                    } else {
                        pointedEntity = currEntity;
                        resultVec = raytraceresult.hitVec;
                        distA = distB;
                    }
                }
            }
        }
        return pointedEntity;
    }

    public static Entity getRTLock(){
        return getLookedAtEntity(200.0);
    }

    public static boolean isEntityExisting(Entity entity){
        World world = Minecraft.getMinecraft().theWorld;
        if (world == null || entity == null)
            return false;
        for (Entity wEntity: world.loadedEntityList){
            if (entity == wEntity){
                return true;
            }
        }
        return false;
    }

    public static boolean isPlayerAttack(EntityPlayer player){
        float currentSwingProgress = player.swingProgress;
        boolean trigger = false;

        if (currentSwingProgress == 0){
            previousSwingProgress.remove(player);
        } else {
            float lastSwingProgress = previousSwingProgress.getOrDefault(player, 10.0F);
            if (currentSwingProgress < lastSwingProgress){
                lastHitTime.put(player, System.currentTimeMillis());
                if (isRecentlyHit(player)) {
                    trigger = true;
                }
                previousSwingProgress.put(player, currentSwingProgress);
            }
        }

        return trigger;
    }

    public static boolean isPlayeraAttack(EntityPlayer player){
        boolean trigger = false;
        float sp = player.swingProgress;
        if (sp == (float) 1 / 6){
            lastHitTime.put(player, System.currentTimeMillis());
            if (isRecentlyHit(player)) {
                trigger = true;
            }
        }
        return trigger;
    }

    public static void updateLastDamageTime(){
        lastDamageTime = System.currentTimeMillis();
    }

    public static float playerHitDistance(EntityPlayer player){
        Vec3 playerPos = player.getPositionEyes(1.0f);
        Vec3 mcPlayerPos = mc.thePlayer.getPositionEyes(1.0f);

        double deltaX = playerPos.xCoord - mcPlayerPos.xCoord;
        double deltaY = playerPos.yCoord - mcPlayerPos.yCoord;
        double deltaZ = playerPos.zCoord - mcPlayerPos.zCoord;

        float dist = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        return dist;
        /* Maybe for the future to detect if player maybe cheats
        if (isHitTrace(player)){
            return dist;
        } else {
            return dist;
        }*/
    }

    public static boolean isHitTrace(EntityPlayer player){
        return (getLookedAtEntity(player, 5.0F) == mc.thePlayer);
    }

    private static boolean isRecentlyHit(EntityPlayer player) {
        Long lastHit = lastHitTime.get(player);
        if (lastHit != null){
            return (Math.abs(lastHit - lastDamageTime) < 100);
        }
        return false;
    }

    public static double[] transformRelativeCoordinates(EntityPlayerSP player, float partialTicks, double relativeX, double relativeY, double relativeZ){
        float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks;
        float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;

        // Umwandlung von Grad in Radiant
        float yawRadians = (float) Math.toRadians(-yaw);
        float pitchRadians = (float) Math.toRadians(-pitch);

        // Berechnung der relativen Richtungsvektoren basierend auf yaw und pitch
        double xzLen = Math.cos(pitchRadians);
        double offsetX = relativeX * Math.cos(yawRadians) - relativeZ * Math.sin(yawRadians) * xzLen;
        double offsetY = relativeY + relativeZ * Math.sin(pitchRadians);
        double offsetZ = relativeX * Math.sin(yawRadians) + relativeZ * Math.cos(yawRadians) * xzLen;

        return new double[]{offsetX, offsetY, offsetZ};
    }

}
