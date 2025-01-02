package de.matrix.pvnikamod.listener;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Chat {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    private final String[] triggers = new String[]{"1st Killer - ", "1st Place - ", "Winner: ", " - Damage Dealt - ", "Winning Team -", "1st - ", "Winners: ", "Winner: ", "Winning Team: ", " won the game!", "Top Seeker: ", "1st Place: ", "Last team standing!", "Winner #1 (", "Top Survivors", "Winners - ", "Your team won!", "Your team lost!", "Most Kills:"};
    private final String[] winTriggers = new String[]{" (Win)", "Your team won!"};
    private boolean win;
    private long timesend = 0;
    private long deactivate = 0;


    public Chat(){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    @SubscribeEvent
    public void onEvent(Event event){
        if (event instanceof TickEvent.RenderTickEvent){
            waiter();
        } else
        if (event instanceof ClientChatReceivedEvent){
            handleChat(((ClientChatReceivedEvent) event).message);
        }
    }

    public void handleChat(IChatComponent chatComponent){
        if (this.deactivate > 0){
            return;
        }
        String str = EnumChatFormatting.getTextWithoutFormattingCodes(chatComponent.getUnformattedText());
        if (str != null && ( str.startsWith(" ") || str.startsWith("+") ) ){
            if (this.timesend <= 0) {
                for (String trig : this.triggers){
                    if (str.contains(trig)) {
                        this.timesend = System.currentTimeMillis() + this.config.chatSettings.delay;
                        break;
                    }
                }
            }
            for (String trig : this.winTriggers){
                if (str.contains(trig)) {
                    this.win = true;
                    if (this.timesend <= 0){
                        this.timesend = System.currentTimeMillis() + this.config.chatSettings.delay;
                    }
                    break;
                }
            }
        }
    }

    public void waiter(){
        if(this.canSend()){
            this.timesend = 0;
            this.deactivate = System.currentTimeMillis() + 5000;
            String[] texts = this.config.chatSettings.autoTexts;
            int[] modes = this.config.chatSettings.autoModes;
            for (int cnt = 0; cnt < texts.length; cnt++) {
                switch (modes[cnt]){
                    case 0: // Off
                        break;
                    case 1: // Always
                        sendMessage(texts[cnt]);
                        break;
                    case 2: // Win
                        if (this.win){
                            sendMessage(texts[cnt]);
                        }
                        break;
                    case 3: // Lose
                        if (!this.win){
                            sendMessage(texts[cnt]);
                        }
                        break;
                }
            }
            this.win = false;
        }
        if (this.deactivate > 0 && System.currentTimeMillis() > this.deactivate){
            this.deactivate = 0;
        }
    }

    public void sendMessage(String message){
        if(message != null && !message.isEmpty())
            this.mc.thePlayer.sendChatMessage(message);
    }

    public boolean enabledSend(){
        return (this.deactivate == 0) || (System.currentTimeMillis() > this.deactivate);
    }

    public boolean canSend(){
        return this.timesend > 0 && System.currentTimeMillis() > this.timesend && this.enabledSend();
    }

}
