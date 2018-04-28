package com.mdv.logging;

import com.mdv.throttle.Configuration;

import java.io.*;

public class Logger {

    PrintWriter log;
    File l = new File(Configuration.LOG_FILE);

    public Logger()  {



    }
    public synchronized void log(String out) {

        try {
            log = new PrintWriter(new BufferedWriter(new FileWriter(l, true)), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.append(out+"\n");
        log.flush();
        log.close();

    }
    public void clean() throws IOException{
        log = new PrintWriter(new BufferedWriter(new FileWriter(l, false)), false);
        log.print("");
        log.flush();
        log.close();

    }
}
