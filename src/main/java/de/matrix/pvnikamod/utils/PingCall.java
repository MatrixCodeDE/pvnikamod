package de.matrix.pvnikamod.utils;

import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.multiplayer.ServerAddress;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class PingCall {

    private final InetSocketAddress address;
    private String rawAddress;

    public PingCall(String address){
        ServerAddress serverAddress = ServerAddress.fromString(address);
        this.address = new InetSocketAddress(serverAddress.getIP(), serverAddress.getPort());
        this.rawAddress = address;
    }

    public int getPing(){

        Socket socket = new Socket();

        try{
            if (this.address == null || this.rawAddress.equals(""))
                return 0;


            long start = System.currentTimeMillis();
            socket.connect(this.address, 1000);
            socket.close();
            long end = System.currentTimeMillis();
            return (int) (end - start);
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

            return -1;
        }

    }

}
