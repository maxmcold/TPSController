package com.mdv.test;


import com.mdv.data.Message;
import com.mdv.io.Queue;
import com.mdv.logging.Logger;
import com.mdv.throttle.Configuration;
import com.mdv.throttle.Producer;

import java.io.*;

public class Publisher implements Producer, Runnable {

    private Thread t;
    private String threadName;
    File f = new File(Configuration.IO_FILE);
    File l = new File(Configuration.LOG_FILE);
    PrintWriter out;
    PrintWriter log;
    Logger logger = new Logger();
    private Queue queue;
    private boolean exec = true;
    int waitTime = Configuration.PUBLISH_INTERVAL_MILLISEC;


    public Publisher( String name, Queue q) {
        threadName = name;
        this.queue = q;


        //out.println("Creating " +  threadName );

    }
    public void stop(){
        this.exec = false;
    }
    public String getName(){
        return this.threadName;
    }
    public void slower(int millisec){
        this.waitTime += millisec;


    }
    public void faster(int millisec){
        if(this.waitTime > millisec)
            this.waitTime -= millisec;

    }


    public void run() {

         try {
            int i = 1;
            while(this.exec) {
                this.writeLine();

                Thread.sleep(this.waitTime);
            }

        } catch (InterruptedException e) {
            logger.log("Thread " +  threadName + " interrupted.");
        } catch (IOException e) {

         }

    }
    public void sleep(long millisec){
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            logger.log(e.getMessage());
        }
    }
    public boolean isActive(){
        return this.exec;
    }
    private synchronized void writeLine() throws IOException {

        this.queue.pushMessage(Message.getMessage(threadName,true));



    }

    public void  setQueue(Queue q){
        this.queue = q;
    }
    public void reStart(){
        this.exec = true;
    }

    public void start () {

        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}

