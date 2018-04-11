package com.cvas;


import java.io.*;

public class Publisher implements Runnable{

    private Thread t;
    private String threadName;
    File f = new File(Configuration.IO_FILE);
    File l = new File(Configuration.LOG_FILE);
    PrintWriter out;
    PrintWriter log;

    public Publisher( String name) {
        threadName = name;


        //out.println("Creating " +  threadName );

    }

    public void run() {

        //out.println("Running " +  threadName );
        try {
            int i = 1;
            while(true) {
                try {
                    out = new PrintWriter(new BufferedWriter(new FileWriter(f,true)), true);
                    log = new PrintWriter(new BufferedWriter(new FileWriter(l,true)), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.println("Message: " + threadName + ", " + i++);
                // Let the thread sleep for a while.
                out.close();
                Thread.sleep(Configuration.PUBLISH_INTERVAL_MILLISEC);
            }

        }catch (InterruptedException e) {
            log.println("Thread " +  threadName + " interrupted.");
        }
        log.println("Thread " +  threadName + " exiting.");
        log.close();
    }

    public void start () {

        if (t == null) {
            t = new Thread (this, threadName);
            //t.setDaemon(true);
            t.start ();
        }
    }
}

