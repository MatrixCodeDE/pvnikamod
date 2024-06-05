package de.matrix.pvnikamod.modutils.modules;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.IngameUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.ISpecialArmor;

import java.util.Arrays;
import java.util.List;

public class MLGUtils extends ModuleUtils {

    private static final List<Integer> walk = Arrays.asList(16, 19, 22, 24, 27, 29, 34, 36, 38, 40, 42,
            49, 51, 53, 55, 58, 60, 62, 65, 67, 69, 72, 74, 77, 79, 82, 85, 87, 90, 93, 95, 98, 101, 104,
            106, 109, 112, 115, 118, 119, 121, 127, 130, 133, 135, 136);
    private static final List<Integer> jump = Arrays.asList(17, 20, 23, 28, 30, 31, 35, 37, 39, 41,
            43, 45, 50, 52, 54, 56, 59, 61, 63, 66, 68, 71, 73, 76, 78, 81, 83, 86, 89, 91, 94, 97, 100,
            103, 105, 107, 108, 111, 114, 117, 122, 125, 128);
    private static final List<Integer> both = Arrays.asList(10, 11, 13, 14, 15, 18, 22, 26, 33, 47);


    private static final Config config = PvnikaMod.getInstance().getConfig();
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void toggleShowDamage(){
        config.igModules.mlgModule.showDamage = !config.igModules.mlgModule.showDamage;
    }

    public MovingObjectPosition customRayTrace(double blockReachDistance, float partialTicks) {
        Vec3 vec3 = mc.thePlayer.getPositionEyes(partialTicks);
        Vec3 vec31 = mc.thePlayer.getLook(partialTicks);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * blockReachDistance, vec31.yCoord * blockReachDistance, vec31.zCoord * blockReachDistance);
        return mc.thePlayer.worldObj.rayTraceBlocks(vec3, vec32, false, true, false);
    }

    public static int getDistance(){
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null)
            return 0;
        MovingObjectPosition obj = IngameUtils.customRayTrace( 200.0, 1.0F, true);
        if (obj != null && obj.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK){
            int target = obj.getBlockPos().getY() + 1;
            int current = player.getPosition().getY();
            int diff = current - target;
            if (diff > 0){
                return diff;
            }

        }
        return 0;
    }

    private int getEnchantModifier(int type, int level){
        float f = (float)(6 + level * level) / 3.0f;
        if (type == 0)
            return MathHelper.floor_float(f * 0.75f);
        if (type == 2)
            return MathHelper.floor_float(f * 2.5f);
        return 0;
    }

    public float getDamage(){
        if (mc.thePlayer == null){
            return 0.0F;
        }
        int distance = getDistance();
        float damageMultiplier = 1.0f;
        PotionEffect potioneffect = mc.thePlayer.getActivePotionEffect(Potion.jump);
        float f = potioneffect != null ? (float)(potioneffect.getAmplifier() + 1) : 0.0f;
        int i = MathHelper.ceiling_float_int((distance - 3.0f - f) * damageMultiplier);
        float damageAmount = (float) i;

        damageAmount = ISpecialArmor.ArmorProperties.applyArmor(mc.thePlayer, mc.thePlayer.inventory.armorInventory, DamageSource.fall, (double)damageAmount);
        damageAmount = this.applyPotionDamageCalculations(damageAmount);
        damageAmount = Math.max(damageAmount - mc.thePlayer.getAbsorptionAmount(), 0.0f);

        int damage = MathHelper.floor_float(damageAmount);

        float fDamage = (float) damage / 2;
        return fDamage;
    }

    public enum mlgOptions {
        IMPOSSIBLE,
        WALK,
        JUMP,
        BOTH
    }

    public mlgOptions getOption(){
        int distance = getDistance();
        if (distance < 10 || both.contains(distance))
            return mlgOptions.BOTH;
        if (walk.contains(distance))
            return mlgOptions.WALK;
        if (jump.contains(distance))
            return mlgOptions.JUMP;
        return mlgOptions.IMPOSSIBLE;
    }

    private float applyPotionDamageCalculations(float damage) {
        DamageSource source = DamageSource.fall;
        if (source.isDamageAbsolute()) {
            return damage;
        }
        if (mc.thePlayer.isPotionActive(Potion.resistance) && source != DamageSource.outOfWorld) {
            int i = (mc.thePlayer.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
            int j = 25 - i;
            float f = damage * (float)j;
            damage = f / 25.0f;
        }
        if (damage <= 0.0f) {
            return 0.0f;
        }
        int k = EnchantmentHelper.getEnchantmentModifierDamage(mc.thePlayer.getInventory(), source);
        if (k > 20) {
            k = 20;
        }
        if (k > 0 && k <= 20) {
            int l = 25 - k;
            float f1 = damage * (float)l;
            damage = f1 / 25.0f;
        }
        return damage;
    }



}
