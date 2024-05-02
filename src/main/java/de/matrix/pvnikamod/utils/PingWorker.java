package de.matrix.pvnikamod.utils;

import de.matrix.pvnikamod.config.RuntimeSettings;

public class PingWorker implements Runnable{

    private boolean run = true;
    public String address;
    public String lastAddress;
    public transient int lastPing;
    private PingCall pingCall;

    public PingWorker(){
    }

    public PingWorker(String address){
        this.address = address;
    }

    public void setAddress(String address){
        this.address = address;
    }


    public void setRun(boolean run){
        this.run = run;
    }

    @Override
    public void run() {

        while (this.run){

            try{

                if(RuntimeSettings.connectedToServer) {

                    if (this.address != null && !this.address.equals(this.lastAddress)) {
                        this.pingCall = new PingCall(this.address);
                        this.lastAddress = this.address;
                        this.address = null;
                    }

                    if (this.pingCall != null) {
                        this.lastPing = this.pingCall.getPing();
                    }

                } else {
                    this.lastPing = 0;
                }

                Thread.sleep(5000l);

            }
            catch (Exception exception){
                exception.printStackTrace();
            }

        }

    }

    public int getLastPing(){
        if (this.pingCall == null)
            this.lastPing = 0;
        return this.lastPing;
    }

}
