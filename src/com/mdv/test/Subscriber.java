package com.mdv.test;

import com.mdv.throttle.Configuration;

import java.io.*;

public class Subscriber implements Runnable {

    private Thread t;
    private String threadName;
    File f = new File(Configuration.LOG_FILE);
    PrintWriter out;

    public Subscriber( String name) {
        threadName = name;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(f,true)), true);
        } catch (IOException e) {
            e.printStackTrace();
        }//*/

        //out.println("Creating " +  threadName );

    }

    public void run() {

        try {
            int i = 1;

            while (true) {
                RandomAccessFile raf = new RandomAccessFile(Configuration.IO_FILE, "rw");
                //Initial write position
                long writePosition = raf.getFilePointer();
                raf.readLine();
                // Shift the next lines upwards.
                long readPosition = raf.getFilePointer();

                byte[] buff = new byte[1024];
                int n;
                while (-1 != (n = raf.read(buff))) {
                    raf.seek(writePosition);
                    raf.write(buff, 0, n);
                    readPosition += n;
                    writePosition += n;
                    raf.seek(readPosition);
                }
                raf.setLength(writePosition);
                raf.close();
                out.println("read 1 line from "+threadName+" --- waiting "+ Configuration.SUBSCRIBE_INTERVAL_MILLISEC+"  millisecs...");
                Thread.sleep(Configuration.SUBSCRIBE_INTERVAL_MILLISEC);
                i++;
            }

        } catch (InterruptedException e) {
            out.println("Thread " + threadName + " interrupted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start () {
        out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.setDaemon(true);
            t.start ();
        }
    }

}
