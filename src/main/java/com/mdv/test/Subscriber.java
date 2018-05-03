package com.mdv.test;

import com.mdv.io.Queue;
import com.mdv.logging.Logger;
import com.mdv.throttle.Configuration;

import java.io.*;

public class Subscriber implements Runnable {

    private Thread t;
    private String threadName;
    Logger logger = new Logger();

    Queue queue;


    public Subscriber( String name, Queue q) {
        this.queue = q;
        threadName = name;




    }

    public void run() {

        try {


            while (true) {
                this.popline();
                //logger.log("subcribed one message from "+this.threadName);

                Thread.sleep(Configuration.SUBSCRIBE_INTERVAL_MILLISEC);

            }

        } catch (InterruptedException e) {
            logger.log("Thread " + threadName + " interrupted.");
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
