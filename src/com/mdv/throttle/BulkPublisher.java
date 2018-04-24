package com.mdv.throttle;



import com.sun.org.apache.bcel.internal.generic.LADD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;

public class BulkPublisher extends Thread implements Controllable{
    Logger logger = new Logger();
    private static Stack<Publisher> publishers = new Stack();

    int bulkInit = 10; //default
    public BulkPublisher(int bi){
        this.setDaemon(true);
        this.bulkInit = bi;
    }

    @Override
    public void start(){
        for (int i = 0; i < this.bulkInit; i++) {
            Publisher p = new Publisher("pub-" + i);
            publishers.push(p);
            p.start();

        }


    }
    public boolean increase(){
        return this.addPublisher();
    }

    @Override
    public boolean decrease() {
        return this.popPublisher();
    }

    public boolean popPublisher(){
        if (publishers.empty()) return false;
        publishers.pop();
        return true;
    }

    public boolean addPublisher(){


        //gets the last added index in the stack
        Publisher p = (publishers.empty()) ? new Publisher("pub-1") : publishers.peek();

        //Evaluate the last index in the thread to create new one
        int nextToken;
        String tname = p.getName();
        StringTokenizer st = new StringTokenizer(tname, "-");
        if (st.nextToken() != null){
            String s = st.nextToken();
            if (s != null) {
                int lastToken = Integer.parseInt(s);
                nextToken = ++lastToken;
                publishers.push(new Publisher("pub-"+nextToken));

                try {
                    logger.log("adding thread pub-"+nextToken);
                    logger.log("Publisher Stack size: "+publishers.size());
                } catch (IOException e) {
                    //TODO:obviously change this system out
                    e.printStackTrace();
                }


            }
        } else { //if you cannot get the token name just do not do anything
            //TODO: improve this threadname management. Like this is shit
            return false;
        }
        return true;




    }

}
