package com.mdv.throttle;

import com.mdv.io.Queue;
import com.mdv.logging.Logger;
import com.mdv.utils.Timer;
import com.mdv.utils.FilePopper;
import sun.security.krb5.Config;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.StringTokenizer;

public class Speedmeter implements Runnable{
    File measure;
    private Thread t;//PrintWriter out;

    Logger logger = new Logger();
    Timer timer = Timer.getTimer();
    Queue queue;

    public Speedmeter(Queue q)  {
        this.queue = q;

        //hardcoded:write measure on file
        measure = new File(Configuration.MEASURE_FILE);

    }





    /**
     * Change the interval any queue measure is taken
     * warning: will change the whole static Configuration value.
     * @param interval
     */

    public void setMeasureIntervalMillisec(int interval){
        Configuration.MEASURE_INTERVAL_MILLISEC = interval;

    }

    public void run() {




        while (true){
            try {

                //gets current messages in queue
                int count = this.queue.getCurrentSize();


                String ms = timer.getFormettedDateTime() + " " + Configuration.DEF_STRING_TOKEN + count + "\n";

                // Metering file cannot grow forever. Thus use circular writing
                //get current number of lines
                LineNumberReader lnr = new LineNumberReader(new FileReader(new File(Configuration.MEASURE_FILE)));
                lnr.skip(Long.MAX_VALUE);

                //if not max number append, otherwise pop
                if (lnr.getLineNumber() >= Configuration.MAX_MEASURE_DEPTH){
                    ///too much lines in the file, pop a line and then add the last
                    new FilePopper().popLine(ms,Configuration.MEASURE_FILE);

                }else{
                    //not yet max numbers, append the line
                    new FilePopper().appendline(ms,Configuration.MEASURE_FILE);

                }

                Thread.sleep(Configuration.MEASURE_INTERVAL_MILLISEC);
            } catch (IOException e) {
                logger.log(e.getMessage());
            } catch (InterruptedException e) {
                logger.log(e.getMessage());
            }
        }

    }

    /**
     *
     * @return float array of transactions per second evaluated across subsequent queue size count at defined MEASURE interval
     * @throws IOException
     * @throws ParseException
     */

    public synchronized float[] getCurrentTPS() throws IOException, ParseException {

        BufferedReader br = new BufferedReader(new FileReader(Configuration.MEASURE_FILE));

        //size array big enough to contain all measures
        //the average speed is evaluated across 2 sibling rows of the meter file. Thus total size is MAX_MEASURE_DEPTH-1
        float[] avgSpeed = new float[Configuration.MAX_MEASURE_DEPTH-1];
        String s = null;
        int i = 0;

        while ( null != (s = br.readLine())){

            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

            StringTokenizer st = new StringTokenizer(s, Configuration.DEF_STRING_TOKEN);
            br.mark(s.length());

            Date date1 = format.parse(st.nextToken().trim());
            long count1 = Long.parseLong(st.nextToken().trim());

            //if another line read it to evaluate avg speed

            if (( null != (s = br.readLine()))) {
                st = new StringTokenizer(s, Configuration.DEF_STRING_TOKEN);
                if (st.hasMoreTokens()) {
                    String tmpToken = st.nextToken();
                    Date date2 = format.parse(tmpToken);


                    //long timestamp2 = (t1 != null) ? Long.parseLong(t1.trim()) : 0;
                    String t2 = st.nextToken();
                    long count2 = (t2 != null) ? Long.parseLong(t2.trim()) : 0;
                    //add avg speed to speed array
                    float dt = date2.getTime() - date1.getTime(); // date difference in milliseconds
                    float dc = count2 - count1; //evaluate the message count difference
                    ///protect from divide by zero error
                    avgSpeed[i++] = (dt == 0) ? 0 : (dc * 1000) / dt;
                    //i++;

                    //go back to previous line
                    br.reset();

                }
            }






        }
        br.close();

        return avgSpeed;
    }

    public void start () {
        t = new Thread (this);
        t.setDaemon(true);
        t.start ();
    }

}


