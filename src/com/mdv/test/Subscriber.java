package com.mdv.test;

import com.mdv.io.Queue;
import com.mdv.throttle.Configuration;

import java.io.*;

public class Subscriber implements Runnable {

    private Thread t;
    private String threadName;
    File f = new File(Configuration.LOG_FILE);
    PrintWriter out;
    Queue queue;


    public Subscriber( String name, Queue q) {
        this.queue = q;
        threadName = name;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(f,true)), true);
        } catch (IOException e) {
            e.printStackTrace();
        }//*/



    }

    public void run() {

        try {


            while (true) {
                this.popline();
                Thread.sleep(Configuration.SUBSCRIBE_INTERVAL_MILLISEC);

            }

        } catch (InterruptedException e) {
            out.println("Thread " + threadName + " interrupted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //implements FIFO logic
    private synchronized void popline() throws IOException{
        this.queue.popMessage();

    }

    //Implements LIFO logic
    private synchronized void getline() throws IOException{
        this.queue.getMessage();


    }
    public void start () {
        //out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.setDaemon(true);
            t.start ();
        }
    }

}
