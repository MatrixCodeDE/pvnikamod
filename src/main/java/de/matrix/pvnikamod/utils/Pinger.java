package de.matrix.pvnikamod.utils;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.NetworkPlayerInfo;

public class Pinger {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    private transient PingWorker pingWorker;
    private transient Thread pingThread;


    public Pinger(){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.pingWorker = new PingWorker();
        this.pingThread = new Thread(this.pingWorker);
        this.pingThread.start();

    }




    public long serverDataPing(ServerData serverData){
        return serverData.pingToServer;
    }

    /*public void refreshPing(){
        Socket socket = new Socket();

        try{
            if (this.address == null)
                this.resetConnection();
            if (this.address == null){
                this.resetConnection();
                this.lastPing = -2;
                return;
            }


            long start = System.currentTimeMillis();
            socket.connect(this.address, 1000);
            socket.close();
            long end = System.currentTimeMillis();
            this.lastPing = (int) (end - start);
            this.lastUpdate = end;
            PvnikaMod.sendLog("Pinged: " + this.lastPing);
        }catch (IOException ioexception)
        {
            if (!socket.isClosed())
            {
                try
                {
                    socket.close();
                }
                catch (IOException var4)
                {
                    ;
                }
            }

            if (ioexception instanceof SocketTimeoutException)
            {
                ;
            }

            this.lastPing = -1;
        }
    }*/

    public int getPlayerPing(NetworkPlayerInfo networkPlayerInfoIn){
        int ping;
        if ((networkPlayerInfoIn.getGameProfile().getId().equals(this.mc.thePlayer.getGameProfile().getId()))){
            this.pingWorker.setAddress(this.mc.getCurrentServerData().serverIP);
            ping = this.pingWorker.getLastPing();
        } else {
            ping = networkPlayerInfoIn.getResponseTime();
        }
        return ping;
    }

}
