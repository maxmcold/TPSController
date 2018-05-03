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
        } catch (IOException e) {
            logger.log(e.getMessage());
        }

        this.sm = speedmeter;
        this.controllable = bp;
        this.queue = q;


    }

    public void run() {

        while(true) {

            //check is metering is running, if not create one.
            if (null == this.sm) this.sm = new Speedmeter(this.queue);

            try {


                float avg = this.sm.getCurrentTPS();

                //TODO: for now check only average TPS

                float diff = Configuration.REFERENCE_TPS-avg;

                logger.log("AvgTPS/Reference:"+avg+" / "+Configuration.REFERENCE_TPS+ " Difference:" + diff);


                if (diff > Configuration.DIFF_TOLERANCE ) { //too low TPS
                    this.controllable.increase();

                }
                if (diff < 0) { //too high TPS
                    this.controllable.decrease();

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
                logger.log(e.getMessage());
            } catch (InterruptedException e) {
                logger.log(e.getMessage());
            } catch (ParseException e) {
                logger.log(e.getMessage());
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
    private void init() throws IOException{
        PrintWriter writer = new PrintWriter(Configuration.MEASURE_FILE);
        writer.print("");
        writer.close();
        this.logger.clean();


    }

}
