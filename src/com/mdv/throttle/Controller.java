package com.mdv.throttle;

import com.mdv.logging.Logger;

import java.io.*;
import java.text.ParseException;

public class Controller implements Runnable {

    private Thread t;
    private String threadName;
    private Speedmeter sm;
    private Controllable controllable;
    Logger logger = new Logger();

    public Controller(Speedmeter speedmeter,Controllable bp) {

        try {
            this.init();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.sm = speedmeter;
        this.controllable = bp;


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
            try {
                float tpsArray[] = this.evaluateCurrentTPS();


                //TODO: temporary code, find a way to log it somewhere else

                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Configuration.AVG_SPEED_LOG_FILE,true)), true);
                float sum = 0;
                int countAvgItems = 0;
                for (int i =0; i < tpsArray.length ; i++){

                    if (tpsArray[i] != 0) {
                        sum += tpsArray[i];
                        countAvgItems++;
                    }

                    //out.append(String.valueOf(tpsArray[i]) + " ");
                    //out.flush();
                }
                float avg = sum / countAvgItems;
                out.print(" => avgTPS="+ avg + "\n");
                out.close();

                //TODO: for now check only average TPS

                float diff = avg-Configuration.REFERENCE_TPS;
                logger.log("AvgTPS/Reference:"+avg+"/"+Configuration.REFERENCE_TPS+ " Difference:" + diff);


                //TODO: the most simple algorithm for now: add/remove one publisher
                if (diff > 0 && diff > Configuration.DIFF_TOLERANCE) { //too high TPS
                    this.controllable.decrease();
                    logger.log("Too much hig TPS decreasing one Thread...");

                }
                if (diff < 0 && -diff > Configuration.DIFF_TOLERANCE) { //too high TPS
                    this.controllable.increase();
                    logger.log("Too much low TPS increasing one Thread...");

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

        writer = new PrintWriter(Configuration.AVG_SPEED_LOG_FILE);
        writer.print("");
        writer.close();



    }

}
