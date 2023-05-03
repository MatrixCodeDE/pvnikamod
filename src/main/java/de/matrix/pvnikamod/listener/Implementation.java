package de.matrix.pvnikamod.listener;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.ValueUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;

public class Implementation {

    private PvnikaMod mod;
    private Config config;
    private Minecraft mc;
    private float currentBlockDamage;

    public Implementation(){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }



    public Item getTargetBlockItem()
    {
        WorldClient worldclient = Minecraft.getMinecraft().theWorld;

        if (worldclient != null && Minecraft.getMinecraft().objectMouseOver != null)
        {
            BlockPos blockpos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();

            if (blockpos != null)
            {
                IBlockState iblockstate = worldclient.getBlockState(blockpos);

                if (iblockstate != null)
                {
                    return iblockstate.getBlock().getItem(worldclient, blockpos);
                }
            }
        }

        return null;
    }

    public float getCurrentBlockDamage(){
        return this.currentBlockDamage;
    }

    public void setCurrentBlockDamage(float value){
        this.currentBlockDamage = value;
    }

    public float getFloatBlockBreakPercentage(){
        return ValueUtil.floatToPercentage(this.currentBlockDamage);
    }

    public int getIntBlockBreakPercentage(){
        return (int) ValueUtil.floatToPercentage(this.currentBlockDamage);
    }

}
