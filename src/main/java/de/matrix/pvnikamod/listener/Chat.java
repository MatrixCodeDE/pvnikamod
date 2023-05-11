package de.matrix.pvnikamod.listener;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Chat {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    private final String[] triggers = new String[]{"1st Killer - ", "1st Place - ", "Winner: ", " - Damage Dealt - ", "Winning Team -", "1st - ", "Winners: ", "Winner: ", "Winning Team: ", " won the game!", "Top Seeker: ", "1st Place: ", "Last team standing!", "Winner #1 (", "Top Survivors", "Winners - "};
    private final String[] winTriggers = new String[]{" (Win)"};
    private boolean end;
    private boolean win;
    private int delay = -1;


    public Chat(){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    //@SubscribeEvent
    public void onEvent(Event event){
        if (event instanceof TickEvent.RenderTickEvent){
            waiter();
        } else
        if (event instanceof ClientChatReceivedEvent){
            handleChat(((ClientChatReceivedEvent) event).message);
        }
    }

    public void handleChat(IChatComponent chatComponent){
        String str = EnumChatFormatting.getTextWithoutFormattingCodes(chatComponent.getUnformattedText());
        if (str != null && ( str.startsWith(" ") || str.startsWith("+") ) ){
            if (!this.end) {
                for (String trig : this.triggers){
                    if (str.contains(trig)) {
                        PvnikaMod.sendLog("Pvnika Endgame");
                        this.end = true;
                        this.delay = 5;
                        break;
                    }
                }
            }
            for (String trig : this.winTriggers){
                if (str.contains(trig)) {
                    PvnikaMod.sendLog("Pvnika Wingame");
                    this.win = true;
                    break;
                }
            }
        }
    }

    public void waiter(){
        if(delay <= 0 && delay != -1){
            delay = -1;
            PvnikaMod.sendLog("Pvnika GG: "+win);
            if (win){
                sendMessage("gg");
                PvnikaMod.sendLog("Pvnika GG should be triggered");
            } else {
                PvnikaMod.sendLog("Pvnika GG should not be triggered");
            }
            win = false;
            end = false;
        } else
        if (delay > 0){
            delay -= 1;
        }
    }

    public void sendMessage(String message){
        if(message != null)
            this.mc.thePlayer.sendChatMessage(message);
    }

}
