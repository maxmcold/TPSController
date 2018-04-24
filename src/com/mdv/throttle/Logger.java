package com.mdv.throttle;

import java.io.*;

public class Logger {

    PrintWriter log;
    File l = new File(Configuration.LOG_FILE);

    public Logger()  {



    }
    public synchronized void log(String out) throws IOException{

        log = new PrintWriter(new BufferedWriter(new FileWriter(l, true)), false);
        log.append(out+"\n");
        log.flush();
        log.close();

    }
}
