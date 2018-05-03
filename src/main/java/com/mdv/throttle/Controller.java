package com.mdv.throttle;

import com.mdv.io.Queue;
import com.mdv.logging.Logger;
import com.mdv.test.Publisher;

import java.io.*;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Stack;

public class Controller implements Runnable {

    private Thread t;
    private String threadName;
    private Speedmeter sm;
    private Controllable controllable;
    Logger logger = new Logger();
    private Queue queue;



    public Controller(Speedmeter speedmeter,Controllable bp, Queue q) {

        try {
            this.init();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.sm = speedmeter;
        this.controllable = bp;
        this.queue = q;


    }


    private float[] evaluateCurrentTPS() throws IOException {
        //the average speed is evaluated across 2 adiacent rows of the meter file. Thus total size is MAX_MEASURE_DEPTH-1
        float[] avgSpeed = new float[Configuration.MAX_MEASURE_DEPTH-1];

        try {
             avgSpeed = this.sm.getCurrentTPS();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return avgSpeed;
    }






    @Override
    public void run() {

        while(true) {

            //check is metering is running, if not create one
            if (null == this.sm) this.sm = new Speedmeter(this.queue);

            try {
                float tpsArray[] = this.evaluateCurrentTPS();



                float sum = 0;
                int countAvgItems = 1;
                for (int i =0; i < tpsArray.length ; i++){

                    if (tpsArray[i] != 0) {
                        sum += tpsArray[i];
                        countAvgItems++;
                    }

                    //out.append(String.valueOf(tpsArray[i]) + " ");
                    //out.flush();
                }
                float avg = sum / countAvgItems;

                //TODO: for now check only average TPS

                float diff = avg-Configuration.REFERENCE_TPS;
                logger.log("AvgTPS/Reference:"+avg+"/"+Configuration.REFERENCE_TPS+ " Difference:" + diff);


                if (diff > 0 ) { //too high TPS
                    this.controllable.decrease();
                    //logger.log("Too much hig TPS decreasing one Thread...");

                }
                if (diff < 0 && -diff > Configuration.DIFF_TOLERANCE) { //too high TPS
                    this.controllable.increase();
                    //logger.log("Too much low TPS increasing one Thread...");

                }

                //Are you closer to queue limit?

                Stack<Producer> stack = this.controllable.getProducers();
                Iterator<Producer> iterator = stack.iterator();
                Producer tmpPub;
                while (iterator.hasNext()) {
                    tmpPub = iterator.next();
                    if (this.queue.getCurrentSize() >= this.queue.getLimit() * 0.8) {
                        logger.log("Getting closer to queue limit: sleeping thread "+tmpPub.getName()+" | currentSize:"+  this.queue.getCurrentSize());
                       tmpPub.stop();
                    } else { //we are safe: restart all threads
                        if (!tmpPub.isActive()) {
                            logger.log("safe queue size: restarting thread " + tmpPub.getName() + " | currentSize:" + this.queue.getCurrentSize());
                            tmpPub.start();
                        }


                    }
                }







                Thread.sleep(Configuration.CONTROL_INTERVAL);




            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void start () {
        t = new Thread (this);
        t.setDaemon(true);
        t.start ();
    }

    /**
     * Perform initial cleaning of environment
     * In current implementation:
     *
     * clean meter file
     *
     */
    private void init() throws FileNotFoundException{
        PrintWriter writer = new PrintWriter(Configuration.MEASURE_FILE);
        writer.print("");
        writer.close();




    }

}
