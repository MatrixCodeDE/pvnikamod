package de.matrix.pvnikamod.utils;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.model.b3d.B3DModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class IngameUtils {

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

    // Thanks to https://gist.github.com/Dalethium/bc650696960c53c86e1137beec7280b1?permalink_comment_id=2110247#file-getlookedatentity-java
    public static Entity getLookedAtEntity(double blockReachDistance) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null)
            return null;
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

}
