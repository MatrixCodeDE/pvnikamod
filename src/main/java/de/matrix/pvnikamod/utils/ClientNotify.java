package de.matrix.pvnikamod.utils;

import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ClientNotify {

    public static void tempChat(String message){
        String msg = EnumChatFormatting.WHITE + "[" + PvnikaMod.getInstance().modMetadata.name + EnumChatFormatting.WHITE + "]" + " " + message;
        EntityPlayer ep = Minecraft.getMinecraft().thePlayer;
        if (Minecraft.getMinecraft().thePlayer != null){
            ep.addChatMessage(new ChatComponentText(msg));
        }
    }

}
