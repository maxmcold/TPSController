package com.mdv.throttle;

import com.mdv.utils.FilePopper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class Speedmeter implements Runnable{
    File measure;
    private Thread t;
    PrintWriter out;
    PrintWriter log;

    public Speedmeter()  {

        measure = new File(Configuration.MEASURE_FILE);
        File bufferIO = new File(Configuration.IO_FILE);
    }

    public void run() {

        try {
            log = new PrintWriter(new BufferedWriter(new FileWriter(Configuration.LOG_FILE,true)), true);
            out = new PrintWriter(new BufferedWriter(new FileWriter(measure, true)), true);
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (true){
            try {

                long count = Files.lines(Paths.get(Configuration.IO_FILE)).count();
                String meter1 = Timer.getTimer().currentTimeMillisec();
                String ms = meter1 + Configuration.DEF_STRING_TOKEN + count + "\n";
                LineNumberReader lnr = new LineNumberReader(new FileReader(new File(Configuration.MEASURE_FILE)));
                lnr.skip(Long.MAX_VALUE);

                if (lnr.getLineNumber() >= Configuration.MAX_MEASURE_DEPTH){
                    ///too much lines in the file, writer from the beginning
                    new FilePopper().popLine(ms,Configuration.MEASURE_FILE);

                }else{
                    new FilePopper().appendline(ms,Configuration.MEASURE_FILE);

                }
                Thread.sleep(Configuration.MEASURE_INTERVAL_MILLISEC);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }

    }



    public long[] getCurrentTPS() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(Configuration.MEASURE_FILE));
        Hashtable<Long, Long> ht = new Hashtable<>();
        //size array big enough to contain all measures
        long[] avgSpeed = new long[Configuration.MAX_MEASURE_DEPTH];
        String s;
        int i = 0;
        while ( null != (s = br.readLine())){

            StringTokenizer st = new StringTokenizer(s, Configuration.DEF_STRING_TOKEN);
            br.mark(s.length());

            long timestamp1 = Long.parseLong(st.nextToken().trim());
            long count1 = Long.parseLong(st.nextToken().trim());
            ht.put(new Long(timestamp1),new Long(count1));
            //if another line read it to evaluate avg speed

            if (( null != (s = br.readLine()))){
                st = new StringTokenizer(s, Configuration.DEF_STRING_TOKEN);
                long timestamp2 = Long.parseLong(st.nextToken().trim());
                long count2 = Long.parseLong(st.nextToken().trim());
                //add avg speed to speed array
                long dt = timestamp2-timestamp1;
                long dc = count2-count1;
                ///protect from divide by zero error
                avgSpeed[i] = (dt == 0) ? 0 : (dc*1000)/dt;
                i++;
                ht.put(new Long(timestamp2),new Long(count2));
                //go back to previous line
                br.reset();

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


