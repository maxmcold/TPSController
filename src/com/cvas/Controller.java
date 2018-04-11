package com.cvas;

import java.io.IOException;

public class Controller implements Runnable {

    private Thread t;
    private String threadName;
    private Speedmeter sm;

    public Controller(Speedmeter speedmeter) {
        this.sm = speedmeter;

    }

    private long[] evaluateCurrentTPS() throws IOException {


        return this.sm.getCurrentTPS();
    }



    @Override
    public void run() {
        while(true) {
            try {
                long tpsArray[] = this.evaluateCurrentTPS();



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void start () {
        t = new Thread (this);
        t.setDaemon(true);
        t.start ();
    }

}
