package de.matrix.pvnikamod.listener;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;

public class Implementation {

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

}
