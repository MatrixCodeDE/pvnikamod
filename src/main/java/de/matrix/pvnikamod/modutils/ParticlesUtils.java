package de.matrix.pvnikamod.modutils;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class ParticlesUtils {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    public ParticlesUtils(){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    public void toggleAlwaysParticles() {
        boolean alwaysParticles = this.config.particleSettings.alwaysParticles;
        this.config.particleSettings.alwaysParticles = !alwaysParticles;
    }

    public void toggleSharpnessParticles() {
        boolean alwaysSharpnessParticles = this.config.particleSettings.alwaysSharpnessParticles;
        this.config.particleSettings.alwaysSharpnessParticles = !alwaysSharpnessParticles;
    }

    public void onAttack(AttackEntityEvent event) {
        Entity entity = event.target;
        if (entity instanceof EntityLivingBase) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            boolean criticalHit = player.fallDistance > 0.0F && !player.isPotionActive(Potion.blindness);
            float enchantment = EnchantmentHelper.getModifierForCreature(player.getHeldItem(), ((EntityLivingBase) entity).getCreatureAttribute());
            for (int i = 0; i < this.config.particleSettings.particleMultiplier; i++) {
                if (criticalHit || this.config.particleSettings.alwaysParticles) {
                    Minecraft.getMinecraft().thePlayer.onCriticalHit(entity);
                }

                if (enchantment > 0.0F || this.config.particleSettings.alwaysSharpnessParticles) {
                    Minecraft.getMinecraft().thePlayer.onEnchantmentCritical(entity);
                }
                if (Minecraft.getDebugFPS() < 30){
                    break;
                }
            }
        }
    }

}
